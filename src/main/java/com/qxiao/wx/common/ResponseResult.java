package com.qxiao.wx.common;

import java.io.Serializable;

import com.spring.web.controller.ResponseMessage;

public class ResponseResult<T> extends ResponseMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private T data;

	public ResponseResult(T data) {
		super();
		this.data = data;
	}

	public T getData() {
		return data;
	}

}
