package cn.fciasth.weixin.netty;

import lombok.Data;

import java.io.Serializable;

@Data
public class DataContent implements Serializable {

	private static final long serialVersionUID = 8021381444738260454L;

	private Integer action;		// 动作类型
	private ChatMsg chatMsg;	// 用户的聊天内容entity
	private String extand;		// 扩展字段
}
