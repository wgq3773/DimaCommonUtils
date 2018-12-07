package com.dima.commons.learn.concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @Description: Java并发编程的艺术P84
 */
public class MultiThread {

	public static void main(String[] args) {
		// 获取Java线程管理ThreadMXBean
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		// 获取线程和线程堆栈信息
		ThreadInfo[] dumpAllThreads = threadMXBean.dumpAllThreads(false, false);
		// 遍历线程信息，打印线程ID和线程名称信息
		for (ThreadInfo threadInfo : dumpAllThreads) {
			System.out.println("[" + threadInfo.getThreadId() + "]" + threadInfo.getThreadName());
			/**
			 * [5]Attach Listener 		// 负责接收到外部的命令，而对该命令进行执行的并且吧结果返回给发送者
			 * [4]Signal Dispatcher 	// 分发处理发送给JVM信号的线程
			 * [3]Finalizer 			// 调用对象finalize方法的线程
			 * [2]Reference Handler		// 清除Reference的线程，主要用于处理引用对象本身（软引用、弱引用、虚引用）的垃圾回收问题
			 * [1]main					// main线程，用户程序入口
			 */
		}
	}
}