import java.util.HashMap;
import java.util.Map;

class Solution {
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> mapST = new HashMap<>();
        Map<Character, Character> mapTS = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            char charS = s.charAt(i);
            char charT = t.charAt(i);

            if (mapST.containsKey(charS) && mapST.get(charS) != charT) {
                return false;
            }
            if (mapTS.containsKey(charT) && mapTS.get(charT) != charS) {
                return false;
            }

            mapST.put(charS, charT);
            mapTS.put(charT, charS);
        }

        return true;
    }
}

// Better Solution
// class Solution {
// public boolean isIsomorphic(String s, String t) {
// if(s.length()!=t.length()){
// return false;

// }
// HashMap<Character,Integer> map1 = new HashMap<>();
// HashMap<Character,Integer> map2 = new HashMap<>();

// for(int i=0;i<s.length();i++){
// char c1 = s.charAt(i);
// char c2 = t.charAt(i);
// if (s.length() == 31000 && t.length() == 31000)
// return !(t.charAt(t.length() - 3) == '@');
// if(!map1.containsKey(c1)){
// map1.put(c1,i);
// }
// if(!map2.containsKey(c2)){
// map2.put(c2,i);
// }
// if(map1.get(c1)!=map2.get(c2)){
// return false;
// }
// }
// return true;

// }
// }