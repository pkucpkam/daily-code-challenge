# 535. Encode and Decode TinyURL

## Intuition

To build a URL shortener service like TinyURL, we need a bi-directional mapping between long original URLs and short, unique keys. 

Using **Random Base62 6-Character Keys** (`0-9`, `a-z`, `A-Z`) is the industry-standard design:
1. **Unpredictability & Security**: Unlike simple sequential counter IDs, random keys prevent URL guessing and automated scraping.
2. **Massive Capacity**: With 62 possible characters for 6 positions ($62^6 \approx 56.8$ billion unique combinations), collision probability is extremely low.
3. **Idempotency**: Maintaining a two-way mapping (`longToShort` and `shortToLong`) ensures that encoding the same URL multiple times returns the same short URL without duplicating storage.

---

## Key Insights & Algorithm

### Dual Hash Maps with Random Base62 Encoding

1. **Data Structures**:
   - `shortToLong`: A `HashMap<String, String>` mapping each unique 6-character key to its original long URL.
   - `longToShort`: A `HashMap<String, String>` mapping each long URL to its assigned 6-character key.
   - `ALPHABET`: Base62 character string (`0-9`, `a-z`, `A-Z`).

2. **`encode(longUrl)`**:
   - Check if `longUrl` already exists in `longToShort`. If present, return `http://tinyurl.com/` + stored key.
   - Generate a random 6-character key.
   - Handle rare collisions by re-generating the key if `shortToLong` already contains it.
   - Save key-to-URL and URL-to-key mappings into both HashMaps.
   - Return `http://tinyurl.com/` + key.

3. **`decode(shortUrl)`**:
   - Extract the 6-character key by removing the `http://tinyurl.com/` prefix.
   - Look up and return the original URL from `shortToLong`.

---

## Complexity Analysis

- **Time Complexity**: $\mathcal{O}(1)$ average
  - **`encode`**: Generating a 6-character key takes $\mathcal{O}(1)$ constant operations. `HashMap` lookup and insertion operate in $\mathcal{O}(1)$ average time.
  - **`decode`**: Prefix removal and `HashMap` lookup operate in $\mathcal{O}(1)$ average time.

- **Space Complexity**: $\mathcal{O}(N)$
  - Where $N$ is the number of unique URLs encoded. The two HashMaps store entry records proportional to $N$.

