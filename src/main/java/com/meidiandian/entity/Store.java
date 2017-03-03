package com.meidiandian.entity;

public class Store {
	
	private int id;
	private int userID;
	private String storeName;
	private String storeHours;
	private String storeAddress;
	private double cost;
	private int postTime;
	private int beginPost;
	private String storeNotice;
	private String storeImage;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getStoreHours() {
		return storeHours;
	}
	public void setStoreHours(String storeHours) {
		this.storeHours = storeHours;
	}
	public String getStoreAddress() {
		return storeAddress;
	}
	public void setStoreAddress(String storeAddress) {
		this.storeAddress = storeAddress;
	}
	public double getCost() {
		return cost;
	}
	public void setCost(double cost) {
		this.cost = cost;
	}
	public int getPostTime() {
		return postTime;
	}
	public void setPostTime(int postTime) {
		this.postTime = postTime;
	}
	public int getBeginPost() {
		return beginPost;
	}
	public void setBeginPost(int beginPost) {
		this.beginPost = beginPost;
	}
	public String getStoreNotice() {
		return storeNotice;
	}
	public void setStoreNotice(String storeNotice) {
		this.storeNotice = storeNotice;
	}
	public String getStoreImage() {
		return storeImage;
	}
	public void setStoreImage(String storeImage) {
		this.storeImage = storeImage;
	}
	
}
