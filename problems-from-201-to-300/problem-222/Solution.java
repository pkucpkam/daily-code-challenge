class Solution {
    public int maxPoints(int[][] points) {
        int n = points.length;
        if (n <= 2)
            return n;

        int ans = 1;
        for (int i = 0; i < n; i++) {
            Map<String, Integer> count = new HashMap<>();
            int duplicates = 0; 
            int localMax = 0;

            for (int j = i + 1; j < n; j++) {
                int dx = points[j][0] - points[i][0];
                int dy = points[j][1] - points[i][1];

                if (dx == 0 && dy == 0) { 
                    duplicates++;
                    continue;
                }

                int g = gcd(dx, dy);
                dx /= g;
                dy /= g;

                if (dx == 0) {
                    dy = 1;
                } else if (dy == 0) {
                    dx = 1;
                } else {
                    if (dx < 0) {
                        dx = -dx;
                        dy = -dy;
                    }
                }

                String key = dy + "/" + dx;
                int c = count.getOrDefault(key, 0) + 1;
                count.put(key, c);
                localMax = Math.max(localMax, c);
            }

            ans = Math.max(ans, localMax + duplicates + 1);
        }

        return ans;
    }

    private int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (a == 0)
            return b == 0 ? 1 : b;
        if (b == 0)
            return a;
        while (b != 0) {
            int t = a % b;
            a = b;
            b = t;
        }
        return a;
    }
}