class Solution {
    public String complexNumberMultiply(String num1, String num2) {
        // Split strings by "+" or "i"
        String[] p1 = num1.split("\\+|i");
        String[] p2 = num2.split("\\+|i");
        
        // Parse real and imaginary parts
        int a = Integer.parseInt(p1[0]);
        int b = Integer.parseInt(p1[1]);
        
        int c = Integer.parseInt(p2[0]);
        int d = Integer.parseInt(p2[1]);
        
        // Apply complex multiplication formula
        int real = a * c - b * d;
        int img = a * d + b * c;
        
        // Return in expected format
        return real + "+" + img + "i";
    }
}