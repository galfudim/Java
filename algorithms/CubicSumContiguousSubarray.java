public class SumOfSubarray {

	public static int cubicApproach(int[] arr) {
		int n = arr.length, max = 0;
		
		for (int i = 0; i < n; i++) {
			for (int j = i; j < n; j++) {
				int s = 0;
				for (int k = i; k <= j; k++) {
					s += arr[k];
				}
				max = Math.max(max, s);
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[] arr = {3, -4, 6, 1, -2, 4, 3};
		System.out.println("The largest sum in a contiguous subarray in this "
				+ "array is " + cubicApproach(arr));
	}
}