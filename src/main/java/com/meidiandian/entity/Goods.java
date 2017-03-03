package com.meidiandian.entity;
/**
 * 商品类
 * @author zys
 *
 */
public class Goods {
	
	private int id;
	private int storeID;
	private String goodsName;
	private String goodsPrice;
	private String goodsImage;
	private int soldNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getStoreID() {
		return storeID;
	}
	public void setStoreID(int storeID) {
		this.storeID = storeID;
	}
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	public String getGoodsPrice() {
		return goodsPrice;
	}
	public void setGoodsPrice(String goodsPrice) {
		this.goodsPrice = goodsPrice;
	}
	public String getGoodsImage() {
		return goodsImage;
	}
	public void setGoodsImage(String goodsImage) {
		this.goodsImage = goodsImage;
	}
	public int getSoldNumber() {
		return soldNumber;
	}
	public void setSoldNumber(int soldNumber) {
		this.soldNumber = soldNumber;
	}
	
}
