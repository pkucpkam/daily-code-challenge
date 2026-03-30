# 211. Design Add and Search Words Data Structure - Giải thích lời giải tối ưu

## Ý tưởng tốt nhất: Trie + DFS cho wildcard `.`

Ta cần 2 thao tác:

- `addWord(word)`: thêm từ mới.
- `search(word)`: tìm từ, trong đó `.` có thể đại diện cho bất kỳ chữ cái nào.

Nếu dùng `HashSet` hoặc danh sách thường, truy vấn có `.` sẽ tốn kém vì phải so khớp nhiều từ.
Giải pháp chuẩn và tối ưu là dùng **Trie (prefix tree)**.

## Cấu trúc dữ liệu

Mỗi node Trie có:

- `children[26]`: con ứng với `'a'..'z'`.
- `isWord`: đánh dấu node này kết thúc một từ hợp lệ.

`WordDictionary` giữ một node gốc `root`.

## Cách làm

1. **addWord(word)**
- Đi từ `root`, với mỗi ký tự `c`:
  - Tính chỉ số `idx = c - 'a'`.
  - Nếu chưa có node con ở `idx` thì tạo mới.
  - Di chuyển xuống node con đó.
- Sau khi đi hết từ, đặt `isWord = true`.

2. **search(word)**
- Dùng DFS với trạng thái `(node, pos)`:
  - Nếu `pos == word.length()`: trả về `node.isWord`.
  - Nếu ký tự hiện tại là chữ cái thường:
	- Đi đúng nhánh tương ứng 1 ký tự.
  - Nếu ký tự là `.`:
	- Thử tất cả node con khác `null`.
	- Chỉ cần một nhánh trả về `true` là kết luận `true`.

## Vì sao đúng?

- Trie đảm bảo mỗi đường đi từ gốc mô tả đúng một tiền tố.
- `addWord` xây đúng đường đi của từ và đánh dấu điểm kết thúc.
- Với `search`:
  - Ký tự thường bắt buộc đi đúng 1 nhánh tương ứng.
  - `.` cho phép thử toàn bộ nhánh ký tự tại vị trí đó.
- DFS duyệt đúng toàn bộ các khả năng hợp lệ theo định nghĩa wildcard.
Vì vậy kết quả `true/false` là chính xác.

## Độ phức tạp

Gọi `L` là độ dài từ:

- `addWord`: `O(L)` thời gian, `O(L)` bộ nhớ tăng thêm trong trường hợp tạo node mới.
- `search`:
  - Trung bình: gần `O(L)`.
  - Trường hợp xấu nhất có wildcard: `O(26^k * L)` với `k` là số dấu `.`.
  - Theo đề bài `k <= 2`, nên thực tế vẫn rất nhanh.

## Java code

```java
class WordDictionary {

	private static class TrieNode {
		TrieNode[] children = new TrieNode[26];
		boolean isWord;
	}

	private final TrieNode root;

	public WordDictionary() {
		this.root = new TrieNode();
	}

	public void addWord(String word) {
		TrieNode node = root;
		for (int i = 0; i < word.length(); i++) {
			int idx = word.charAt(i) - 'a';
			if (node.children[idx] == null) {
				node.children[idx] = new TrieNode();
			}
			node = node.children[idx];
		}
		node.isWord = true;
	}

	public boolean search(String word) {
		return searchDfs(word, 0, root);
	}

	private boolean searchDfs(String word, int pos, TrieNode node) {
		if (node == null) {
			return false;
		}

		if (pos == word.length()) {
			return node.isWord;
		}

		char ch = word.charAt(pos);
		if (ch == '.') {
			for (TrieNode child : node.children) {
				if (child != null && searchDfs(word, pos + 1, child)) {
					return true;
				}
			}
			return false;
		}

		return searchDfs(word, pos + 1, node.children[ch - 'a']);
	}
}
```
