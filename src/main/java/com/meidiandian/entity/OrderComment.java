package com.meidiandian.entity;

import java.util.Date;

/**
 * 用户对订单的评论
 * @author zys
 *
 */
public class OrderComment {
	
	private String orderID;
	private int userID;
	private String username;
	private String comment;
	private Date commentTime;
	private double score;
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
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
	public void setUserName(String username) {
		this.username = username;
	}
	public String getCommnet() {
		return comment;
	}
	public void setCommnet(String comment) {
		this.comment = comment;
	}
	public Date getCommentTime() {
		return commentTime;
	}
	public void setCommentTime(Date commentTime) {
		this.commentTime = commentTime;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	
}
