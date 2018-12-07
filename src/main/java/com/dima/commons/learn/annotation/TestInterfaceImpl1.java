package com.dima.commons.learn.annotation;

@SelfDefineAnnotaion("TEST_CODE_01")
public class TestInterfaceImpl1 implements TestInterface {

	@Override
	public void sayTest() {
		System.out.println("TestInterfaceImpl1 sayTest...");
	}

}
