package cn.fciasth.weixin.controller;

import cn.fciasth.weixin.bo.ResultBO;
import cn.fciasth.weixin.bo.UsersBO;
import cn.fciasth.weixin.enums.OperatorFriendRequestTypeEnum;
import cn.fciasth.weixin.pojo.Users;
import cn.fciasth.weixin.service.IUserService;
import cn.fciasth.weixin.utils.FastDFSClient;
import cn.fciasth.weixin.utils.FileUtils;
import cn.fciasth.weixin.vo.MyFriendsVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private FastDFSClient fastDFSClient;


    @PostMapping("/loginOrRegister")
    public ResultBO loginOrRegister(@RequestBody Users user) throws Exception {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return ResultBO.errorMsg("用户名或密码不能为空");
        }
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        ResultBO result;
        if (usernameIsExist){
            System.out.println("用户存在，登录");
            result = userService.login(user);
        }else {
            System.out.println("用户不存在，注册");
            result = userService.register(user);
        }
        return result;
    }

    @PostMapping("/uploadFaceBase64")
    public ResultBO uploadFaceBase64(@RequestBody UsersBO userBO) throws Exception {
        String base64Data = userBO.getFaceData();
        System.out.println("base64:"+userBO.getFaceData());
        String userFacePath = "D:\\weixin\\" + userBO.getUserId() + "userface64.png";
        FileUtils.base64ToFile(userFacePath, base64Data);
        MultipartFile multipartFile = FileUtils.fileToMultipart(userFacePath);
        String url = fastDFSClient.uploadBase64(multipartFile);
        System.out.println("图片URL:" + url);
        String[] arr = url.split("\\.");
        String thump = "_80x80.";
        String thumpImgUrl = arr[0] + thump + arr[1];
        Users user = new Users();
        user.setId(userBO.getUserId());
        user.setFaceImage(thumpImgUrl);
        user.setFaceImageBig(url);
        Users result = userService.updateUserInfo(user);
        return ResultBO.ok(result);
    }

    @PostMapping("/setNickname")
    public ResultBO setNickname(@RequestBody UsersBO userBO) {
        Users user = new Users();
        user.setId(userBO.getUserId());
        user.setNickname(userBO.getNickname());
        Users result = userService.updateUserInfo(user);
        return ResultBO.ok(result);
    }

    @PostMapping("/searchFriend")
    public ResultBO searchFriend(String myUserId, String friendUsername) throws Exception {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ResultBO.errorMsg("用户名不能为空");
        }
        ResultBO result = userService.searchFriend(myUserId, friendUsername);
        return result;
    }

    @PostMapping("/addFriendRequest")
    public ResultBO addFriendRequest(String myUserId, String friendUsername) throws Exception {
        if (StringUtils.isBlank(myUserId) || StringUtils.isBlank(friendUsername)) {
            return ResultBO.errorMsg("用户名不能为空");
        }
        ResultBO result = userService.sendFriendRequest(myUserId, friendUsername);
        return result;
    }

    @PostMapping("/queryFriendRequests")
    public ResultBO queryFriendRequests(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResultBO.errorMsg("userId不能为空");
        }
        return ResultBO.ok(userService.queryFriendRequestList(userId));
    }

    @PostMapping("/operFriendRequest")
    public ResultBO operFriendRequest(String acceptUserId, String sendUserId, Integer operType) {
        // 0. acceptUserId sendUserId operType 判断不能为空
        if (StringUtils.isBlank(acceptUserId) || StringUtils.isBlank(sendUserId) || operType == null) {
            return ResultBO.errorMsg("");
        }
        // 1. 如果operType 没有对应的枚举值，则直接抛出空错误信息
        if (StringUtils.isBlank(OperatorFriendRequestTypeEnum.getMsgByType(operType))) {
            return ResultBO.errorMsg("");
        }
        if (operType == OperatorFriendRequestTypeEnum.IGNORE.type) {
            // 2. 判断如果忽略好友请求，则直接删除好友请求的数据库表记录
            userService.deleteFriendRequest(sendUserId, acceptUserId);
        } else if (operType == OperatorFriendRequestTypeEnum.PASS.type) {
            // 3. 判断如果是通过好友请求，则互相增加好友记录到数据库对应的表
            // 然后删除好友请求的数据库表记录
            userService.passFriendRequest(sendUserId, acceptUserId);
        }

        // 4. 数据库查询好友列表
        List<MyFriendsVO> myFirends = userService.queryMyFriends(acceptUserId);
        return ResultBO.ok(myFirends);
    }

    @PostMapping("/myFriends")
    public ResultBO myFriends(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResultBO.errorMsg("userId不能为空");
        }
        List<MyFriendsVO> myFirends = userService.queryMyFriends(userId);
        return ResultBO.ok(myFirends);
    }

    @PostMapping("/getUnReadMsgList")
    public ResultBO getUnReadMsgList(String acceptUserId) {
        // 0. userId 判断不能为空
        if (StringUtils.isBlank(acceptUserId)) {
            return ResultBO.errorMsg("");
        }

        // 查询列表
        List<cn.fciasth.weixin.pojo.ChatMsg> unreadMsgList = userService.getUnReadMsgList(acceptUserId);

        return ResultBO.ok(unreadMsgList);
    }
}
