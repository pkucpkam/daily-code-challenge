import java.util.*;

class Solution {
    public List<String> fullJustify(String[] words, int maxWidth) {
        List<String> result = new ArrayList<>();
        int i = 0;

        while (i < words.length) {
            List<String> currentLine = new ArrayList<>();
            int currentLength = 0;

            while (i < words.length) {
                int newLength = currentLength + words[i].length() + currentLine.size();
                if (newLength > maxWidth)
                    break;

                currentLine.add(words[i]);
                currentLength += words[i].length();
                i++;
            }

            result.add(createLine(currentLine, maxWidth, i == words.length));
        }

        return result;
    }

    private String createLine(List<String> words, int maxWidth, boolean isLastLine) {
        StringBuilder sb = new StringBuilder();

        if (isLastLine || words.size() == 1) {
            for (int i = 0; i < words.size(); i++) {
                sb.append(words.get(i));
                if (i < words.size() - 1)
                    sb.append(" ");
            }
            while (sb.length() < maxWidth) {
                sb.append(" ");
            }
        } else {
            int totalChars = 0;
            for (String word : words) {
                totalChars += word.length();
            }

            int totalSpaces = maxWidth - totalChars;
            int gaps = words.size() - 1;
            int spacesPerGap = totalSpaces / gaps;
            int extraSpaces = totalSpaces % gaps;

            for (int i = 0; i < words.size(); i++) {
                sb.append(words.get(i));
                if (i < words.size() - 1) {
                    for (int j = 0; j < spacesPerGap; j++) {
                        sb.append(" ");
                    }
                    if (i < extraSpaces) {
                        sb.append(" ");
                    }
                }
            }
        }

        return sb.toString();
    }
}