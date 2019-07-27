package cn.fciasth.weixin.service.impl;

import cn.fciasth.weixin.bo.ResultBO;
import cn.fciasth.weixin.enums.MsgSignFlagEnum;
import cn.fciasth.weixin.enums.SearchFriendsStatusEnum;
import cn.fciasth.weixin.mapper.ChatMsgMapper;
import cn.fciasth.weixin.mapper.FriendsRequestMapper;
import cn.fciasth.weixin.mapper.MyFriendsMapper;
import cn.fciasth.weixin.mapper.UsersMapper;
import cn.fciasth.weixin.mapper.cust.FriendsRequestMapperCust;
import cn.fciasth.weixin.netty.ChatMsg;
import cn.fciasth.weixin.pojo.*;
import cn.fciasth.weixin.service.IUserService;
import cn.fciasth.weixin.utils.*;
import cn.fciasth.weixin.vo.FriendRequestVO;
import cn.fciasth.weixin.vo.MyFriendsVO;
import cn.fciasth.weixin.vo.UsersVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private MyFriendsMapper myFriendsMapper;

    @Autowired
    private ChatMsgMapper chatMsgMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;

    @Autowired
    private FriendsRequestMapperCust friendsRequestMapperCust;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FastDFSClient fastDFSClient;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        UsersExample example = new UsersExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Users> result = usersMapper.selectByExample(example);
        return result.size() != 0 ? true : false;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultBO login(Users user) throws Exception {
        UsersExample example = new UsersExample();
        example.createCriteria().andUsernameEqualTo(user.getUsername())
                                .andPasswordEqualTo(MD5Utils.getMD5Str(user.getPassword()));
        List<Users> result = usersMapper.selectByExample(example);
        if (result.size() == 0){
            return ResultBO.errorMsg("用户名或密码错误");
        }
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(result.get(0), usersVO);
        return ResultBO.ok(usersVO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultBO register(Users user) throws Exception {
        user.setId(UUID.randomUUID().toString().replaceAll("-",""));
        user.setNickname(user.getUsername());
        //设置默认头像
        user.setFaceImage("M00/00/00/CmhQYlzAA3SAZYQ6AABtzURKDAQ683_80x80.png");
        user.setFaceImageBig("M00/00/00/CmhQYlzAA3SAZYQ6AABtzURKDAQ683.png");
        // 生成一个唯一的二维码
        String qrCodePath = "D:\\weixin\\QrCode\\weixin_" +  user.getId() + "_qrcode.png";
        qrCodeUtils.createQRCode(qrCodePath, "weixin_qrcode:" + user.getUsername());
        MultipartFile qrCodeFile = FileUtils.fileToMultipart(qrCodePath);
        String qrCodeUrl = "";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeUrl);
        user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
        usersMapper.insertSelective(user);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(user, usersVO);
        return ResultBO.ok(usersVO);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users updateUserInfo(Users user) {
        UsersExample example = new UsersExample();
        example.createCriteria().andIdEqualTo(user.getId());
        usersMapper.updateByExampleSelective(user, example);
        return queryUserById(user.getId());
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public ResultBO searchFriend(String myUserId, String friendUsername) {
        //判断搜索用户名存不存在，是否为自己，是否已为好友
        Users myFriend = queryUserByUsername(friendUsername);
        if (myFriend == null){
            return ResultBO.errorMsg(SearchFriendsStatusEnum.USER_NOT_EXIST.msg);
        }
        if (myFriend.getId().equals(myUserId)){
            return ResultBO.errorMsg(SearchFriendsStatusEnum.NOT_YOURSELF.msg);
        }
        MyFriendsExample myFriendsExample = new MyFriendsExample();
        myFriendsExample.createCriteria().andMyUserIdEqualTo(myUserId).andMyFriendUserIdEqualTo(myFriend.getId());
        List<MyFriends> myFriends = myFriendsMapper.selectByExample(myFriendsExample);
        if (myFriends.size() != 0){
            return ResultBO.errorMsg(SearchFriendsStatusEnum.ALREADY_FRIENDS.msg);
        }
        UsersVO myFriendUserVO = new UsersVO();
        BeanUtils.copyProperties(myFriend, myFriendUserVO);
        return ResultBO.ok(myFriendUserVO);
    }

    @Override
    public ResultBO sendFriendRequest(String myUserId, String friendUsername) {
        //应该再校验一次好友信息的合法性

        //根据用户名把朋友信息查询出来
        Users friend = queryUserByUsername(friendUsername);
        FriendsRequestExample example = new FriendsRequestExample();
        example.createCriteria().andSendUserIdEqualTo(myUserId).andAcceptUserIdEqualTo(friend.getId());
        List<FriendsRequest> friendsRequests = friendsRequestMapper.selectByExample(example);
        if (friendsRequests.size() != 0) {
            return ResultBO.errorMsg("请勿重复发送好友请求");
        }
        //如果不是你的好友，并且好友记录没有添加，则新增好友请求记录
        FriendsRequest request = new FriendsRequest();
        request.setId(UUID.randomUUID().toString().replaceAll("-",""));
        request.setSendUserId(myUserId);
        request.setAcceptUserId(friend.getId());
        request.setRequestDateTime(new Date());
        friendsRequestMapper.insertSelective(request);
        return ResultBO.ok();
    }

    @Override
    public List<FriendRequestVO> queryFriendRequestList(String userId) {
        List<FriendRequestVO> friendRequestVOS = friendsRequestMapperCust.queryFriendRequestList(userId);
        return friendRequestVOS;
    }

    @Override
    public void deleteFriendRequest(String sendUserId, String acceptUserId) {
        FriendsRequestExample example = new FriendsRequestExample();
        example.createCriteria().andSendUserIdEqualTo(sendUserId).andAcceptUserIdEqualTo(acceptUserId);
        friendsRequestMapper.deleteByExample(example);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void passFriendRequest(String sendUserId, String acceptUserId) {
        saveFriends(sendUserId, acceptUserId);
        saveFriends(acceptUserId, sendUserId);
        deleteFriendRequest(sendUserId, acceptUserId);
    }

    @Override
    public List<MyFriendsVO> queryMyFriends(String userId) {
        List<MyFriendsVO> myFirends = friendsRequestMapperCust.queryMyFriends(userId);
        return myFirends;
    }

    @Override
    public String saveMsg(ChatMsg chatMsg) {
        cn.fciasth.weixin.pojo.ChatMsg msgDB = new cn.fciasth.weixin.pojo.ChatMsg();
        String msgId = UUID.randomUUID().toString().replaceAll("-","");
        msgDB.setId(msgId);
        msgDB.setAcceptUserId(chatMsg.getReceiverId());
        msgDB.setSendUserId(chatMsg.getSenderId());
        msgDB.setCreateTime(new Date());
        msgDB.setSignFlag(MsgSignFlagEnum.unsign.type.toString());
        msgDB.setMsg(chatMsg.getMsg());
        chatMsgMapper.insertSelective(msgDB);
        return msgId;
    }

    @Override
    public void updateMsgSigned(List<String> msgIdList) {
        friendsRequestMapperCust.batchUpdateMsgSigned(msgIdList);
    }

    @Override
    public List<cn.fciasth.weixin.pojo.ChatMsg> getUnReadMsgList(String acceptUserId) {
        ChatMsgExample example = new ChatMsgExample();
        example.createCriteria().andSignFlagEqualTo("0").andAcceptUserIdEqualTo(acceptUserId);
        List<cn.fciasth.weixin.pojo.ChatMsg> result = chatMsgMapper.selectByExample(example);
        return result;
    }

    private void saveFriends(String sendUserId, String acceptUserId) {
        MyFriends myFriends = new MyFriends();
        myFriends.setId(UUID.randomUUID().toString().replaceAll("-",""));
        myFriends.setMyFriendUserId(acceptUserId);
        myFriends.setMyUserId(sendUserId);
        myFriendsMapper.insert(myFriends);
    }

    private Users queryUserById(String userId){
        UsersExample example = new UsersExample();
        example.createCriteria().andIdEqualTo(userId);
        List<Users> users = usersMapper.selectByExample(example);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }

//    private ResultBO FriendRequestIsValid(String myUserId, String friendUsername){
//        //判断搜索用户名存不存在，是否为自己，是否已为好友
//        Users myFriend = queryUserByUsername(friendUsername);
//        if (myFriend == null){
//            return ResultBO.errorMsg(SearchFriendsStatusEnum.USER_NOT_EXIST.msg);
//        }
//        if (myFriend.getId().equals(myUserId)){
//            return ResultBO.errorMsg(SearchFriendsStatusEnum.NOT_YOURSELF.msg);
//        }
//        MyFriendsExample myFriendsExample = new MyFriendsExample();
//        myFriendsExample.createCriteria().andMyUserIdEqualTo(myUserId).andMyFriendUserIdEqualTo(myFriend.getId());
//        List<MyFriends> myFriends = myFriendsMapper.selectByExample(myFriendsExample);
//        if (myFriends.size() != 0){
//            return ResultBO.errorMsg(SearchFriendsStatusEnum.ALREADY_FRIENDS.msg);
//        }
//        return ResultBO.ok();
//    }

    private Users queryUserByUsername(String username){
        UsersExample example = new UsersExample();
        example.createCriteria().andUsernameEqualTo(username);
        List<Users> users = usersMapper.selectByExample(example);
        if (users.size() == 0){
            return null;
        }
        return users.get(0);
    }
}
