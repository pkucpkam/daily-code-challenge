class Solution {
    public boolean hasAlternatingBits(int n) {
        // Dịch phải 1 bit và XOR với chính nó:
        // Nếu các bit xen kẽ nhau (10101...), kết quả x sẽ là dãy toàn bit 1 (11111...).
        long x = (long) n ^ (n >> 1);

        // Kiểm tra xem x có dạng toàn bit 1 (2^k - 1) hay không:
        // Nếu x toàn bit 1 thì x + 1 sẽ có dạng 1000... (lũy thừa của 2),
        // khi đó x & (x + 1) sẽ luôn bằng 0.
        return (x & (x + 1)) == 0;
    }
}
