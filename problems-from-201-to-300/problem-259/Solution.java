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

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */