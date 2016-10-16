package com.example.wsd_client.entity;

/**
 * 封装账单中心获取的数据
 */
public class AccountInfo {

	/**
	 * 商品名称
	 */
	private String XSO_CementName;
	/**
	 * 下单/购买事件时间
	 */
	private String XSO_SetDate;
	/**
	 * 总价格
	 */
	private String XSO_TotalPrice;
	/**
	 * 总数量
	 */
	private int XSO_Number;
	/**
	 * 车牌号
	 */
	private String XSO_CarCode;
	
	public String getXSO_CementName() {
		return XSO_CementName;
	}
	public void setXSO_CementName(String xSO_CementName) {
		XSO_CementName = xSO_CementName;
	}
	public String getXSO_SetDate() {
		return XSO_SetDate;
	}
	public void setXSO_SetDate(String xSO_SetDate) {
		XSO_SetDate = xSO_SetDate;
	}
	public String getXSO_TotalPrice() {
		return XSO_TotalPrice;
	}
	public void setXSO_TotalPrice(String xSO_TotalPrice) {
		XSO_TotalPrice = xSO_TotalPrice;
	}
	public int getXSO_Number() {
		return XSO_Number;
	}
	public void setXSO_Number(int xSO_Number) {
		XSO_Number = xSO_Number;
	}
	public String getXSO_CarCode() {
		return XSO_CarCode;
	}
	public void setXSO_CarCode(String xSO_CarCode) {
		XSO_CarCode = xSO_CarCode;
	}
	@Override
	public String toString() {
		return "AccountInfo [XSO_CementName=" + XSO_CementName
				+ ", XSO_SetDate=" + XSO_SetDate + ", XSO_TotalPrice="
				+ XSO_TotalPrice + ", XSO_Number=" + XSO_Number
				+ ", XSO_CarCode=" + XSO_CarCode + "]";
	}
	
}
