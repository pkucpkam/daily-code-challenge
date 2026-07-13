class Solution {
    public String validIPAddress(String queryIP) {
        if (queryIP.chars().filter(ch -> ch == '.').count() == 3) {
            return validateIPv4(queryIP);
        } else if (queryIP.chars().filter(ch -> ch == ':').count() == 7) {
            return validateIPv6(queryIP);
        }
        return "Neither";
    }

    private String validateIPv4(String IP) {
        String[] nums = IP.split("\\.", -1);
        for (String x : nums) {
            if (x.length() == 0 || x.length() > 3) return "Neither";
            if (x.charAt(0) == '0' && x.length() != 1) return "Neither";
            for (char ch : x.toCharArray()) {
                if (!Character.isDigit(ch)) return "Neither";
            }
            if (Integer.parseInt(x) > 255) return "Neither";
        }
        return "IPv4";
    }

    private String validateIPv6(String IP) {
        String[] nums = IP.split(":", -1);
        for (String x : nums) {
            if (x.length() == 0 || x.length() > 4) return "Neither";
            for (char ch : x.toCharArray()) {
                boolean isDigit = ch >= '0' && ch <= '9';
                boolean isUppercaseAF = ch >= 'A' && ch <= 'F';
                boolean isLowerCaseAF = ch >= 'a' && ch <= 'f';
                if (!(isDigit || isUppercaseAF || isLowerCaseAF)) {
                    return "Neither";
                }
            }
        }
        return "IPv6";
    }
}