class Solution {
    public boolean wordPattern(String pattern, String s) {
        String[] words = s.split(" ");
        if (pattern.length() != words.length) {
            return false;
        }

        Map<Character, String> charToWord = new HashMap<>();
        Map<String, Character> wordToChar = new HashMap<>();

        for (int i = 0; i < pattern.length(); i++) {
            char c = pattern.charAt(i);
            String word = words[i];

            if (!charToWord.containsKey(c)) {
                charToWord.put(c, word);
            } else if (!charToWord.get(c).equals(word)) {
                return false;
            }

            if (!wordToChar.containsKey(word)) {
                wordToChar.put(word, c);
            } else if (wordToChar.get(word) != c) {
                return false;
            }
        }

        return true;
    }
}

// Better solution 
// class Solution {
//     public boolean wordPattern(String pattern, String s) {
//         HashMap<Character,String> charToWord= new HashMap<>();
//         HashMap<String,Character> wordToChar= new HashMap<>();
//         String[] word = s.split(" ");
//         if(pattern.length() != word.length) return false;
//         for(int i=0;i<pattern.length();i++){
//             char c= pattern.charAt(i);
//             String w = word[i];
//             if(charToWord.containsKey(c)){
//                 if(!charToWord.get(c).equals(w)) return false;
//             }else {
//                 if (wordToChar.containsKey(w)) return false;
//                 charToWord.put(c,w);
//                 wordToChar.put(w,c);
//             }

//         }
//         return true;
//     }
// }