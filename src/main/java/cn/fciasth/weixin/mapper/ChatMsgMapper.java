package cn.fciasth.weixin.mapper;

import cn.fciasth.weixin.pojo.ChatMsg;
import cn.fciasth.weixin.pojo.ChatMsgExample;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface ChatMsgMapper {
    int countByExample(ChatMsgExample example);

    int deleteByExample(ChatMsgExample example);

    int insert(ChatMsg record);

    int insertSelective(ChatMsg record);

    List<ChatMsg> selectByExample(ChatMsgExample example);

    int updateByExampleSelective(@Param("record") ChatMsg record, @Param("example") ChatMsgExample example);

    int updateByExample(@Param("record") ChatMsg record, @Param("example") ChatMsgExample example);
}