public class SumOfSubarray {

	public static int quadraticApproach(int[] arr) {
		int n = arr.length, max = 0;
		
		for (int i = 0; i < n; i++) {
			int s = 0;
			for (int j = i; j < n; j++) {
				s += arr[j];
				max = Math.max(max, s);
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[] arr = {3, -4, 6, 1, -2, 4, 3};
		System.out.println("The largest sum in a contiguous subarray in this "
				+ "array is " + quadraticApproach(arr));
	}
}