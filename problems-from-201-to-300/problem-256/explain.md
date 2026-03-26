# 208. Implement Trie (Prefix Tree) - Best Solution

## Ý tưởng tối ưu

Vì dữ liệu chỉ gồm chữ thường `a-z`, cấu trúc tối ưu nhất là **Trie chuẩn với mảng 26 phần tử** cho mỗi node.

Mỗi node gồm:

- `children[26]`: trỏ đến node con tương ứng từng ký tự.
- `isEnd`: đánh dấu đây có phải điểm kết thúc của một từ đã insert hay không.

Ta duy trì một `root` và thao tác như sau:

1. `insert(word)`: đi từng ký tự, nếu chưa có node con thì tạo mới.
2. `search(word)`: đi hết chuỗi, trả về `true` khi node cuối có `isEnd = true`.
3. `startsWith(prefix)`: chỉ cần đi hết prefix, không cần kiểm tra `isEnd`.

## Vì sao đây là best solution

- Tối ưu cho alphabet cố định 26 ký tự: truy cập con là `O(1)`.
- Đúng với yêu cầu bài toán và ổn định với số lần gọi lớn (`3 * 10^4`).
- Code ngắn, rõ ràng, dễ mở rộng (đếm từ, xóa từ, gợi ý autocomplete).

## Code (Java)

```java
class Trie {
    private static class Node {
        Node[] children = new Node[26];
        boolean isEnd;
    }

    private final Node root;

    public Trie() {
        root = new Node();
    }

    public void insert(String word) {
        Node current = root;
        for (int i = 0; i < word.length(); i++) {
            int idx = word.charAt(i) - 'a';
            if (current.children[idx] == null) {
                current.children[idx] = new Node();
            }
            current = current.children[idx];
        }
        current.isEnd = true;
    }

    public boolean search(String word) {
        Node node = findNode(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    private Node findNode(String s) {
        Node current = root;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (current.children[idx] == null) {
                return null;
            }
            current = current.children[idx];
        }
        return current;
    }
}
```

## Độ phức tạp

Giả sử `L` là độ dài chuỗi đầu vào (`word` hoặc `prefix`):

- `insert`: `O(L)`
- `search`: `O(L)`
- `startsWith`: `O(L)`

Bộ nhớ:

- `O(T)` với `T` là tổng số ký tự khác nhau được tạo node trong trie.

## Minh họa nhanh

Thực hiện:

1. `insert("apple")`
2. `search("apple")` -> `true`
3. `search("app")` -> `false` (vì chưa đánh dấu kết thúc từ)
4. `startsWith("app")` -> `true`
5. `insert("app")`
6. `search("app")` -> `true`

Điểm mấu chốt: prefix tồn tại không đồng nghĩa từ hoàn chỉnh đã tồn tại, nên cần cờ `isEnd`.



