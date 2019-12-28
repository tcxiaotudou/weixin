package cn.fciasth.weixin.mapper;

import cn.fciasth.weixin.pojo.FriendsRequest;
import cn.fciasth.weixin.pojo.FriendsRequestExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface FriendsRequestMapper {
    int countByExample(FriendsRequestExample example);

    int deleteByExample(FriendsRequestExample example);

    int insert(FriendsRequest record);

    int insertSelective(FriendsRequest record);

    List<FriendsRequest> selectByExample(FriendsRequestExample example);

    int updateByExampleSelective(@Param("record") FriendsRequest record, @Param("example") FriendsRequestExample example);

    int updateByExample(@Param("record") FriendsRequest record, @Param("example") FriendsRequestExample example);
}