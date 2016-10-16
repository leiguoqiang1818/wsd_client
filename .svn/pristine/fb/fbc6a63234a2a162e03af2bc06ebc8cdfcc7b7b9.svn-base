package com.example.wsd_client.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 实体类：封装用户信息,并且进行序列化
 * @author wsd_leiguoqiang
 */
public class YW_ClientInfo implements Serializable{

	/**
	 * 版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 用户数量合计
	 */
	private int total;

	/**
	 * 封装用户详细信息的集合
	 */
	private List<ClientInfo> result =new ArrayList<ClientInfo>();


	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}


	public List<ClientInfo> getResult() {
		return result;
	}

	public void setResult(List<ClientInfo> result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "YW_ClientInfo [total=" + total + ", lists=" + result + "]";
	}
	
	/**
	 * 自定义清楚用户信息
	 */
	public void deleteClientInfo(){
		setTotal(0);
		setResult(new ArrayList<ClientInfo>());
	}
}

/***
 * YWC_ClientID int ID 编号 
 * YWC_ClientCode Varchar（20） 客户代码 
 * YWC_ClientName Varchar（60） 客户名称 
 * YWC_LinkMan Varchar（20） 联系人 
 * YWC_LinkPhone Varchar（20） 电话 
 * YWC_PassWord Varchar(50) 密码 
 * YWC_CDate datetime 帐户设立日期
 * YWC_Status int 是否有 效 (0:有 效；1 无效) 
 * YWC_InputOwner int 建立人员 
 * YWC_DeptID Int 建立部门 
 * YWC_CorpCert Varchar(30) 企业营业执照 
 * YWC_AreaCode Varchar(10) 销售区域
 */
