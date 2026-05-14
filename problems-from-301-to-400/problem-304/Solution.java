class Solution {
    public boolean canMeasureWater(int x, int y, int target) {
        // Nếu tổng lượng nước tối đa nhỏ hơn target thì không thể đong được
        if (x + y < target) {
            return false;
        }
        
        // Theo định lý Bézout, target có thể đong được nếu và chỉ nếu
        // target là bội số của GCD(x, y)
        return target % gcd(x, y) == 0;
    }

    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}