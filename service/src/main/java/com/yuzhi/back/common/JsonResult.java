package com.yuzhi.back.common;

import java.io.Serializable;

@SuppressWarnings("serial")
public class JsonResult<T> implements Serializable {

	private int code;
	private String msg;
	private T body;
	
	public JsonResult() {
	}
	
	public JsonResult(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
	public JsonResult(int code, String msg, T body) {
		this.code = code;
		this.msg = msg;
		this.body = body;
	}

    public static JsonResult success() {
        return new JsonResult(ResultCode.SUCCESS_CODE, ResultCode.SUCCESS_MSG);
    }

    public static JsonResult error() {
        return new JsonResult(ResultCode.ERROR_CODE, ResultCode.ERROR_MSG);
    }
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getBody() {
		return body;
	}
	public void setBody(T body) {
		this.body = body;
	}

	@Override
    public String toString() {
		String bodystr = "null";
		if (body != null)
			bodystr = body.toString();
        return "JsonResult{" +
                "code=" + code +
                ", message=" + msg +
                ", body=" + bodystr +
                "}";
    }
	
}
