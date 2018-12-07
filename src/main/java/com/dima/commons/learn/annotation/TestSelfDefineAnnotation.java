package com.dima.commons.learn.annotation;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSelfDefineAnnotation {
	
	private static Map<Class<?>, Map<String, Object>> serviceImplFactoryMap = new HashMap<Class<?>, Map<String, Object>>();

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext appContext = new ClassPathXmlApplicationContext("classpath*:applicationContext-common.xml");
		Map<String, Object> serviceMap = new HashMap<String, Object>();
		Map<String, TestInterface> beans = appContext.getBeansOfType(TestInterface.class);
		//{testInterface1=com.dima.test.annotation.TestInterfaceImpl1@16f7c8c1, testInterface2=com.dima.test.annotation.TestInterfaceImpl2@2f0a87b3}
		System.out.println(beans);
		Set<Entry<String, TestInterface>> entrySet = beans.entrySet();
		Iterator<Entry<String, TestInterface>> iterator = entrySet.iterator();
		while (iterator.hasNext()) {
			Object interfaceServiceImpl = iterator.next().getValue();
			SelfDefineAnnotaion annotationValue = interfaceServiceImpl.getClass().getAnnotation(SelfDefineAnnotaion.class);
			if (null != annotationValue) {
				for (String falg : annotationValue.value()) {
					serviceMap.put(falg, interfaceServiceImpl);
				}
			}
		}
		serviceImplFactoryMap.put(TestInterface.class, serviceMap);
		
		TestInterface object = (TestInterface)serviceImplFactoryMap.get(TestInterface.class).get("TEST_CODE_01");
		object.sayTest();
	}
}
