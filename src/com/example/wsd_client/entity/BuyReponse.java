package com.example.wsd_client.entity;


/**
 * 实体类：向服务器做购买请求后返回的数据
 * @author wsd_leiguoqiang
 */
public class BuyReponse {
	/**
	 * 返回的数据条数
	 */
	private String total;
	/**
	 * 返回结果字符串
	 */
	private String result;
	/**
	 * 返回状态原因
	 */
	private String reason;
	
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}

}