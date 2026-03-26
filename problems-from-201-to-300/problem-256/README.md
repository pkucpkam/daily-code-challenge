# 208. Implement Trie (Prefix Tree)

**Độ khó:** Medium

## Đề bài

Trie (đọc là "try") hay còn gọi là prefix tree là một cấu trúc dữ liệu dạng cây, dùng để lưu trữ và truy xuất chuỗi một cách hiệu quả. Một số ứng dụng phổ biến của trie là autocomplete và spellchecker.

Hãy cài đặt lớp `Trie` với các hàm sau:

- `Trie()`
	Khởi tạo đối tượng trie.
- `void insert(String word)`
	Chèn chuỗi `word` vào trie.
- `boolean search(String word)`
	Trả về `true` nếu `word` đã được chèn trước đó, ngược lại trả về `false`.
- `boolean startsWith(String prefix)`
	Trả về `true` nếu tồn tại ít nhất một chuỗi đã chèn có tiền tố `prefix`, ngược lại trả về `false`.

## Ví dụ 1

### Input

```text
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
```

### Output

```text
[null, null, true, false, true, null, true]
```

### Giải thích

```java
Trie trie = new Trie();
trie.insert("apple");
trie.search("apple");   // return True
trie.search("app");     // return False
trie.startsWith("app"); // return True
trie.insert("app");
trie.search("app");     // return True
```

## Ràng buộc

- `1 <= word.length, prefix.length <= 2000`
- `word` và `prefix` chỉ gồm các chữ cái tiếng Anh viết thường.
- Tối đa `3 * 10^4` lời gọi cho các hàm `insert`, `search`, và `startsWith`.