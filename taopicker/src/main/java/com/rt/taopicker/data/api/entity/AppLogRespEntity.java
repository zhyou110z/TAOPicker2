package com.rt.taopicker.data.api.entity;

import java.io.Serializable;

public class AppLogRespEntity<T> implements Serializable {
	private static final long serialVersionUID = 5328934827636086473L;

	/**
	 * 操作成功标识
	 */
	private Integer state;

	/**
	 * 消息
	 */
	private String message;

	private T datas;

	public AppLogRespEntity() {
		super();
	}

	public AppLogRespEntity(Integer state, String message, T datas) {
		super();
		this.state = state;
		this.message = message;
		this.datas = datas;
	}

	public T getDatas() {
		return datas;
	}

	public void setDatas(T datas) {
		this.datas = datas;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}