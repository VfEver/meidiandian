package com.meidiandian.entity;

import java.util.Date;

/**
 * 预订订单
 * @author zys
 *
 */
public class PreOrder {
	
	private int preOrderID;
	private int storeID;
	private int userID;
	private String username;
	private Date preOrderTime;
	private int manageStatus;
	private Date beginTime;
	private Date endTime;
	private int needSeats;
	private double preCost;
	
	public int getPreOrderID() {
		return preOrderID;
	}
	public void setPreOrderID(int preOrderID) {
		this.preOrderID = preOrderID;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Date getPreOrderTime() {
		return preOrderTime;
	}
	public void setPreOrderTime(Date preOrderTime) {
		this.preOrderTime = preOrderTime;
	}
	public int getManageStatus() {
		return manageStatus;
	}
	public void setManageStatus(int manageStatus) {
		this.manageStatus = manageStatus;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public int getNeedSeats() {
		return needSeats;
	}
	public void setNeedSeats(int needSeats) {
		this.needSeats = needSeats;
	}
	public double getPreCost() {
		return preCost;
	}
	public void setPreCost(double preCost) {
		this.preCost = preCost;
	}
	
}
