package com.meidiandian.service;

import java.util.List;
import java.util.Map;

import com.meidiandian.entity.OrderComment;

/**
 * 订单评价服务层接口
 * 
 * @author zys
 *
 */
public interface IOrderCommentService {

	/**
	 * 保存评论信息
	 * 
	 * @param orderComment
	 */
	public void saveOrderComment(OrderComment orderComment);

	/**
	 * 更新订单的评论状态
	 * 
	 * @param map
	 */
	public void updateOrderCommentStatus(Map<String, String> map);

	/**
	 * 根据用户id查询出用户评论过的订单
	 * 
	 * @param userID
	 * @return
	 */
	public List<OrderComment> findCommentOrder(int userID);
}
