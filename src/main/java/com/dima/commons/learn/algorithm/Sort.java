package com.dima.commons.learn.algorithm;

import org.junit.Test;

/**
 * Title: Sort 
 * Description: 选择排序
 * @author Dima
 * @date 2018年12月7日 下午10:01:22
 */
public class Sort {

	public static void main(String[] args) {
		int[] arr = { 1, 3, 2, 45, 65, 33, 12 };
		System.out.println("排序之前的数据：");
		for (int num : arr) {
			System.out.print(num + "\t");
		}
		// 选择排序
		// SelectionSort(arr);
		// 插入排序
		// InsertSort(arr);
		// 归并排序
		// MergeSort(arr, 0, arr.length - 1);
		// 冒泡排序
		// BubbleSort(arr);
		// 快速排序
		QuickSort(arr);

		System.out.println("\n\n排序之后的数据：");
		for (int num : arr) {
			System.out.print(num + "\t");
		}
	}

	/**
	 * @Title: SelectionSort
	 * @Description: 【选择排序】 a) 原理：每一趟从待排序的记录中选出最小的元素，顺序放在已排好序的序列最后，直到全部记录排序完毕。
	 *               也就是：每一趟在n-i+1(i=1，2，…n-1)个记录中选取关键字最小的记录作为有序序列中第i个记录。
	 *               基于此思想的算法主要有简单选择排序、树型选择排序和堆排序。 （这里只介绍常用的简单选择排序） b)
	 *               简单选择排序的基本思想：给定数组：int[]
	 *               arr={里面n个数据}；第1趟排序，在待排序数据arr[1]~arr[n]中选出最小的数据，将它与arrr[1]
	 *               交换； 第2趟，在待排序数据arr[2]~arr[n]中选出最小的数据，将它与r[2]交换；
	 *               以此类推，第i趟在待排序数据arr[i]~arr[n]中选出最小的数据，将它与r[i]交换，直到全部排序完成。
	 * @param arr
	 * @return int[]
	 */
	@Test
	public int[] SelectionSort(int[] arr) {
		// 外层循环，做第i趟排序
		for (int i = 0; i < arr.length - 1; i++) {
			// 内层循环，找到本轮循环的最小的数以后，再进行交换
			for (int j = i + 1; j < arr.length; j++) {
				// 如果后面的数小于前面的数,交换a[i]和a[j]
				if (arr[j] < arr[i]) {
					int temp = arr[i];
					arr[i] = arr[j];
					arr[j] = temp;
				}
			}
		}
		return arr;
	}

	/**
	 * @Title: InsertSort
	 * @Description: 【插入排序】
	 *               基本思想：通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应的位置并插入,非常类似于整理扑克牌。
	 *               在开始摸牌时，左手是空的，牌面朝下放在桌上。接着，一次从桌上摸起一张牌，并将它插入到左手一把牌中的正确位置上。
	 *               为了找到这张牌的正确位置，要将它与手中已有的牌从右到左地进行比较。无论什么时候，左手中的牌都是排好序的 b)
	 *               算法描述:详见代码中注释 时间复杂度O(n2) 最差情况：反序，需要移动n*(n-1)/2个元素
	 *               最好情况：正序，不需要移动元素
	 * @param arr
	 * @return void
	 */
	@Test
	public int[] InsertSort(int[] arr) {
		/*
		 * 首先假设第一个元素被放置在正确的位置上，这样仅需从1-n-1范围内对剩余元素进行排序。对于每次遍历，从0-i-1范围内的元素已经被排好序，
		 * 每次遍历的任务是：通过扫描前面已排序的子列表，将位置i处的元素定位到从0到i的子列表之内的正确的位置上。
		 */
		int i, j, target;
		for (i = 1; i < arr.length; i++) {
			j = i;
			// 将arr[i]复制为一个名为target的临时元素
			target = arr[i];
			// 向下扫描列表，比较这个目标值target与arr[i-1]、arr[i-2]的大小，依次类推
			while (j > 0 && target < arr[j - 1]) {
				// 大于目标值target的每个元素都会向右滑动一个位置
				arr[j] = arr[j - 1];
				j--;
			}
			// 一旦确定了正确位置j，目标值target就会被复制到这个位置
			arr[j] = target;
		}
		return arr;
	}

	/**
	 * @Title: MergeSort
	 * @Description: 【归并排序】 简介:将两个（或两个以上）有序表合并成一个新的有序表
	 *               即把待排序序列分为若干个子序列，每个子序列是有序的。然后再把有序子序列合并为整体有序序列 时间复杂度为O(nlogn)
	 *               稳定排序方式
	 * @param nums
	 *            待排序数组
	 * @param low
	 * @param high
	 * @return int[]
	 */
	@Test
	public int[] MergeSort(int[] nums, int low, int high) {
		int mid = (low + high) / 2;
		if (low < high) {
			// 左边
			MergeSort(nums, low, mid);
			// 右边
			MergeSort(nums, mid + 1, high);
			// 左右归并
			merge(nums, low, mid, high);
		}
		return nums;
	}

	@Test
	public void merge(int[] nums, int low, int mid, int high) {
		int[] temp = new int[high - low + 1];
		int i = low;// 左指针
		int j = mid + 1;// 右指针
		int k = 0;
		// 把较小的数先移到新数组中
		while (i <= mid && j <= high) {
			if (nums[i] < nums[j]) {
				temp[k++] = nums[i++];
			} else {
				temp[k++] = nums[j++];
			}
		}
		// 把左边剩余的数移入数组
		while (i <= mid) {
			temp[k++] = nums[i++];
		}
		// 把右边边剩余的数移入数组
		while (j <= high) {
			temp[k++] = nums[j++];
		}
		// 把新数组中的数覆盖nums数组
		for (int k2 = 0; k2 < temp.length; k2++) {
			nums[k2 + low] = temp[k2];
		}
	}

	/**
	 * @Title: BubbleSort
	 * @Description: 【冒泡排序】 比较相邻的元素。如果第一个比第二个大，就交换他们两个。
	 *               对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对。在这一点，最后的元素应该会是最大的数。
	 *               针对所有的元素重复以上的步骤，除了最后一个。 持续每次对越来越少的元素重复上面的步骤，直到没有任何一对数字需要比较。
	 * @param numbers
	 *            需要排序的整型数组
	 * @return void
	 */
	@Test
	public void BubbleSort(int[] numbers) {
		int temp = 0;
		int size = numbers.length;
		for (int i = 0; i < size - 1; i++) {
			for (int j = 0; j < size - 1 - i; j++) {
				if (numbers[j] > numbers[j + 1]) { // 交换两数位置
					temp = numbers[j];
					numbers[j] = numbers[j + 1];
					numbers[j + 1] = temp;
				}
			}
		}
	}

	/**
	 * @Title: QuickSort
	 * @Description: 【快速排序】 基本思想：
	 *               通过一趟排序将待排序记录分割成独立的两部分，其中一部分记录的关键字均比另一部分关键字小，则分别对这两部分继续进行排序，
	 *               直到整个序列有序。 把整个序列看做一个数组，把第零个位置看做中轴，和最后一个比，如果比它小交换，比它大不做任何处理；
	 *               交换了以后再和小的那端比，比它小不交换，比他大交换。
	 *               这样循环往复，一趟排序完成，左边就是比中轴小的，右边就是比中轴大的，然后再用分治法，分别对这两个独立的数组进行排序。
	 * @param arr
	 * @return void
	 */
	@Test
	public static void QuickSort(int[] arr) {
		if (arr.length > 0) {
			quickSort(arr, 0, arr.length - 1);
		}
	}

	/**
	 * @param numbers
	 *            带排序数组
	 * @param low
	 *            开始位置
	 * @param high
	 *            结束位置
	 */
	@Test
	public static void quickSort(int[] numbers, int low, int high) {
		if (low < high) {
			int middle = getMiddle(numbers, low, high); // 将numbers数组进行一分为二
			quickSort(numbers, low, middle - 1); // 对低字段表进行递归排序
			quickSort(numbers, middle + 1, high); // 对高字段表进行递归排序
		}
	}

	/**
	 * 查找出中轴（默认是最低位low）的在numbers数组排序后所在位置
	 * 
	 * @param numbers
	 *            带查找数组
	 * @param low
	 *            开始位置
	 * @param high
	 *            结束位置
	 * @return 中轴所在位置
	 */
	@Test
	public static int getMiddle(int[] numbers, int low, int high) {
		int temp = numbers[low]; // 数组的第一个作为中轴
		while (low < high) {
			while (low < high && numbers[high] > temp) {
				high--;
			}
			numbers[low] = numbers[high];// 比中轴小的记录移到低端
			while (low < high && numbers[low] < temp) {
				low++;
			}
			numbers[high] = numbers[low]; // 比中轴大的记录移到高端
		}
		numbers[low] = temp; // 中轴记录到尾
		return low; // 返回中轴的位置
	}

	@Test
	public void testaaa() {
		System.out.println("aaa");
	}
}
