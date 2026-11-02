class Solution {
    public int[] constructArray(int n, int k) {
        int[] answer = new int[n];
        int left = 1;
        int right = k + 1;

        // Giai đoạn 1: Dùng k + 1 phần tử đầu tiên đan xen để tạo đúng k hiệu: k, k-1, ..., 1
        for (int i = 0; i <= k; i++) {
            if (i % 2 == 0) {
                answer[i] = left++;
            } else {
                answer[i] = right--;
            }
        }

        // Giai đoạn 2: Điền các phần tử còn lại từ k + 2 đến n theo thứ tự tăng dần
        for (int i = k + 1; i < n; i++) {
            answer[i] = i + 1;
        }

        return answer;
    }
}
