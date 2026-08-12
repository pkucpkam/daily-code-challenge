import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Codec {
    private static final String BASE_HOST = "http://tinyurl.com/";
    private static final String ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    private final Map<String, String> longToShort = new HashMap<>();
    private final Map<String, String> shortToLong = new HashMap<>();
    private final Random rand = new Random();

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        if (longToShort.containsKey(longUrl)) {
            return BASE_HOST + longToShort.get(longUrl);
        }

        String key = generateKey();
        while (shortToLong.containsKey(key)) {
            key = generateKey();
        }

        shortToLong.put(key, longUrl);
        longToShort.put(longUrl, key);

        return BASE_HOST + key;
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        String key = shortUrl.replace(BASE_HOST, "");
        return shortToLong.get(key);
    }

    private String generateKey() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int idx = rand.nextInt(ALPHABET.length());
            sb.append(ALPHABET.charAt(idx));
        }
        return sb.toString();
    }
}

// Your Codec object will be instantiated and called as such:
// Codec codec = new Codec();
// codec.decode(codec.encode(url));