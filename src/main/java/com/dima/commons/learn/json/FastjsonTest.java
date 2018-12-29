package com.dima.commons.learn.json;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

//import net.sf.json.JSONObject;
/**
 * import com.alibaba.fastjson.JSON;
 * 1、public static final Object parse(String text); // 把JSON文本parse为JSONObject或者JSONArray
 * 2、public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
 * 3、public static final <T> T parseObject(String text, Class<T> clazz); // 把JSON文本parse为JavaBean
 * 4、public static final JSONArray parseArray(String text); // 把JSON文本parse成JSONArray
 * 5、public static final <T> List<T> parseArray(String text, Class<T> clazz); //把JSON文本parse成JavaBean集合
 * 6、public static final String toJSONString(Object object); // 将JavaBean序列化为JSON文本
 * 7、public static final String toJSONString(Object object, boolean prettyFormat); // 将JavaBean序列化为带格式的JSON文本
 * 8、public static final Object toJSON(Object javaObject); 将JavaBean转换为JSONObject或者JSONArray。
 */
/**
 * Title: FastjsonTest
 * Description: 
 * @author Dima
 * @date 2018年12月7日 下午10:48:25
 */
public class FastjsonTest {
	
	@Test
	public void testJsonArray(){
		JSONObject jsonObject1 = new JSONObject();
		jsonObject1.put("aaa", "111");
		jsonObject1.put("bbb", "222");
		jsonObject1.put("ccc", "333");
		System.out.println(jsonObject1);//{"aaa":"111","ccc":"333","bbb":"222"}
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("ddd", "444");
		jsonObject2.put("eee", "555");
		jsonObject2.put("fff", "666");
		System.out.println(jsonObject2);//{"eee":"555","ddd":"444","fff":"666"}
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject1);
		jsonArray.add(jsonObject2);
		System.out.println(jsonArray);//[{"aaa":"111","ccc":"333","bbb":"222"},{"eee":"555","ddd":"444","fff":"666"}]
		
		JSONObject object = (JSONObject) jsonArray.get(0);
		System.out.println(object);//{"aaa":"111","ccc":"333","bbb":"222"}
		String aaa = (String) object.get("aaa");
		System.out.println(aaa);//111
	}
	
	//1、public static final Object parse(String text); // 把JSON文本转换为JSONObject或者JSONArray
	@Test
	public void test1(){
		String text = "{\"name\":\"测试商品\",\"orderDate\":1531107795426,\"orderId\":\"0001\"}";
		JSONObject parse = (JSONObject) JSON.parse(text);
		System.out.println(parse);
		System.out.println(parse.get("name"));
		System.out.println(parse.get("orderDate"));
		System.out.println(parse.get("orderId"));
	}
	
	//2、public static final JSONObject parseObject(String text)； // 把JSON文本parse成JSONObject
	@Test
	public void test2(){
		String text = "{\"name\":\"测试商品\",\"orderDate\":1531107795426,\"orderId\":\"0001\"}";
		JSONObject parseObject = JSON.parseObject(text);
		System.out.println(parseObject);
		System.out.println(parseObject.get("name"));
		System.out.println(parseObject.get("orderDate"));
		System.out.println(parseObject.get("orderId"));
	}

	public static void main(String[] args) {
		// 将map转换为json字符串
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("key1", "value1");
		params.put("key2", "value2");
		String paramsJsonString = JSON.toJSONString(params);
		System.out.println("将map集合转换为json的结果：");
		System.out.println(paramsJsonString);
		System.out.println();
		
		// 将json字符串转换为map
		Map<String, String> respMap = JSON.parseObject(paramsJsonString, new TypeReference<Map<String,String>>(){});
		System.out.println("将json转换为map的结果：");
		System.out.println(respMap);
		System.out.println();
		
		
		// 将pojo转换为json字符串
		Order order = new Order();
		order.setName("测试商品");
		order.setOrderDate(new Date());
		order.setOrderId("0001");
		String orderJson = JSON.toJSONString(order);
		System.out.println("将POJO转换为json的结果：");
		System.out.println(orderJson);
		System.out.println();
		
		// 将json字符串转换为POJO
		Order parseObject = JSON.parseObject(orderJson, new TypeReference<Order>() {});
		System.out.println("将json字符串转换为POJO的结果：");
		System.out.println(parseObject);
		System.out.println();
		
		
		// JSONObject
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("jsonObjectKey1", "jsonObjectValue1");
		jsonObject.put("jsonObjectKey2", "jsonObjectValue2");
		jsonObject.put("jsonObjectKey3", "jsonObjectValue3");
		System.out.println(jsonObject);
		JSONObject jsonObject2 = new JSONObject();
		jsonObject2.put("jsonObject2Key1", "jsonObject2Value1");
		jsonObject2.put("jsonObject2Key2", "jsonObject2Value2");
		jsonObject2.put("jsonObject2Key3", "jsonObject2Value3");
		System.out.println(jsonObject2);
		
		// JSONArray
		JSONArray jsonArray = new JSONArray();
		jsonArray.add(jsonObject);
		jsonArray.add(jsonObject2);
		System.out.println(jsonArray);
	}
	
	/**
	 * @Title: testJsonLib 
	 * @Description: 使用json-lib解析json数据
	 * 注：该方式不会将嵌套的json重新排序
	 * 下面这两个包不能同时导
	 * //import net.sf.json.JSONObject;
	 * //import com.alibaba.fastjson.JSONObject;
	 */
	@Test
	public void testJsonLib(){
		String retStr = "{\"sign\":\"CMxlvSKI0hRgZqiZgKIe\",\"ysepay_df_single_query_response\":{\"code\":\"10000\",\"msg\":\"Success\",\"out_trade_no\":\"CST20266\",\"trade_status\":\"TRADE_SUCCESS\",\"trade_status_description\":\"交易成功\",\"total_amount\":\"3.65\",\"account_date\":\"20180919\",\"trade_no\":\"102180919894544092\",\"fee\":\"1.00\",\"partner_fee\":\"0.00\",\"payee_fee\":\"0.00\",\"payer_fee\":\"1.00\"}}";
		/**
		 * 方式一：json-lib
		 */
		//import net.sf.json.JSONObject;
//		JSONObject retStrJson = JSONObject.fromObject(retStr);
//		String checkData = retStrJson.optString("ysepay_df_single_query_response");
//		System.out.println(checkData);
		
		/**
		 * 方式二：fastjson
		 */
		//如果使用fastjson的话，会对内部的数据重新排序，及时使用LinkedHashMap也不行
		//import com.alibaba.fastjson.JSONObject;
		Map<String, String> map = JSON.parseObject(retStr, new TypeReference<Map<String, String>>(){});
		String string = map.get("ysepay_df_single_query_response");
		System.out.println(string);
	}
}
