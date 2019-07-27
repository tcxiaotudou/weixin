package cn.fciasth.weixin.mapper;

import cn.fciasth.weixin.pojo.MyFriends;
import cn.fciasth.weixin.pojo.MyFriendsExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface MyFriendsMapper {
    int countByExample(MyFriendsExample example);

    int deleteByExample(MyFriendsExample example);

    int insert(MyFriends record);

    int insertSelective(MyFriends record);

    List<MyFriends> selectByExample(MyFriendsExample example);

    int updateByExampleSelective(@Param("record") MyFriends record, @Param("example") MyFriendsExample example);

    int updateByExample(@Param("record") MyFriends record, @Param("example") MyFriendsExample example);
}