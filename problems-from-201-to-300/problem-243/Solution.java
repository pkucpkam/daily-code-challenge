import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
	public List<String> findRepeatedDnaSequences(String s) {
		List<String> result = new ArrayList<>();
		int n = s.length();
		if (n < 10) {
			return result;
		}

		int mask = (1 << 20) - 1; 
		int hash = 0;

		Set<Integer> seen = new HashSet<>();
		Set<Integer> added = new HashSet<>();

		for (int i = 0; i < n; i++) {
			hash = ((hash << 2) | encode(s.charAt(i))) & mask;
			if (i < 9) {
				continue;
			}

			if (!seen.add(hash) && added.add(hash)) {
				result.add(s.substring(i - 9, i + 1));
			}
		}

		return result;
	}

	private int encode(char c) {
		if (c == 'A') {
			return 0;
		}
		if (c == 'C') {
			return 1;
		}
		if (c == 'G') {
			return 2;
		}
		return 3; // 'T'
	}
}