package org.fangsoft.util;

import java.util.Random;

public class NumberUtil {
	public static int[] uniRandomNumbers(int min, int max, int total) {
		int size = max - min;
		if (total < 1 || total > size || max <= min) {
			return new int[0];
		}
		int[] nums = new int[total];
		if (total == (size)) {
			for (int i = 0; i < total; i++) {
				nums[i] = min + i;
			}
			for (int i = 0; i < total; i++) {
				Random random = new Random(System.nanoTime());
				swap(nums, i, random.nextInt(total));
			}
			return nums;
		}
		int count = 0;
		while (count < total) {
			Random random = new Random(System.nanoTime());
			int rand = min + (int) (random.nextDouble() * size);
			if (rand >= min && !isInArray(nums, rand)) {
				nums[count++] = rand;
			}
		}

		return nums;
	}

	private static boolean isInArray(int[] nums, int target) {
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] == target)
				return true;
		}
		return false;
	}

	private static void swap(int[] ints, int i, int j) {
		int t = ints[i];
		ints[i] = ints[j];
		ints[j] = t;
	}

}
