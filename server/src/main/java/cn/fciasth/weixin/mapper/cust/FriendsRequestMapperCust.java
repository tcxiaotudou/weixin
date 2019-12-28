package cn.fciasth.weixin.mapper.cust;

import cn.fciasth.weixin.vo.FriendRequestVO;
import cn.fciasth.weixin.vo.MyFriendsVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface FriendsRequestMapperCust {

    List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

    List<MyFriendsVO> queryMyFriends(String userId);

    void batchUpdateMsgSigned(List<String> msgIdList);
}