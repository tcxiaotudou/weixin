package cn.fciasth.weixin.service;

import cn.fciasth.weixin.bo.ResultBO;
import cn.fciasth.weixin.netty.ChatMsg;
import cn.fciasth.weixin.pojo.Users;
import cn.fciasth.weixin.vo.FriendRequestVO;
import cn.fciasth.weixin.vo.MyFriendsVO;
import cn.fciasth.weixin.vo.UsersVO;

import java.util.List;

public interface IUserService {

    boolean queryUsernameIsExist(String username);

    ResultBO login(Users user) throws Exception;

    ResultBO register(Users user) throws Exception;

    Users updateUserInfo(Users user);

    ResultBO searchFriend(String myUserId, String friendUsername);

    ResultBO sendFriendRequest(String myUserId, String friendUsername);

    List<FriendRequestVO> queryFriendRequestList(String userId);

    void deleteFriendRequest(String sendUserId, String acceptUserId);

    void passFriendRequest(String sendUserId, String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);

    /**
     * @Description: 保存聊天消息到数据库
     */
    String saveMsg(ChatMsg chatMsg);

    /**
     * @Description: 批量签收消息
     */
    void updateMsgSigned(List<String> msgIdList);

    /**
     * @Description: 获取未签收消息列表
     */
    List<cn.fciasth.weixin.pojo.ChatMsg> getUnReadMsgList(String acceptUserId);
}
