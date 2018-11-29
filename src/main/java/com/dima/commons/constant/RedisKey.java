package com.dima.commons.constant;

public class RedisKey {
	
	/****************redis Hash的key********************/
	// 订单记录
	public static final String ALL_ORDER_RECORD = "all_order_record";

	/****************redis Hash的field********************/
	// 用户访问次数
	public static final String USER_VISIT_COUNT = "user_visit_count";
	
	// 用户是否支付完成
	public static final String IF_USER_PAYED = "if_user_payed";
	
	// 用户订单号
	public static final String USER_ORDER_ID = "user_order_id";
	
	// 用户订单状态
	public static final String USER_ORDER_STATUS = "user_order_status";
}
