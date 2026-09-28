import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public List<List<String>> findDuplicate(String[] paths) {
        Map<String, List<String>> filesByContent = new HashMap<>();

        for (String directoryInfo : paths) {
            int separator = directoryInfo.indexOf(' ');
            String directory = directoryInfo.substring(0, separator);
            int index = separator + 1;

            while (index < directoryInfo.length()) {
                int nameEnd = directoryInfo.indexOf('(', index);
                String fileName = directoryInfo.substring(index, nameEnd);
                int contentEnd = directoryInfo.indexOf(')', nameEnd);
                String content = directoryInfo.substring(nameEnd + 1, contentEnd);

                filesByContent
                        .computeIfAbsent(content, ignored -> new ArrayList<>())
                        .add(directory + "/" + fileName);

                index = contentEnd + 1;
                if (index < directoryInfo.length() && directoryInfo.charAt(index) == ' ') {
                    index++;
                }
            }
        }

        List<List<String>> duplicates = new ArrayList<>();
        for (List<String> filePaths : filesByContent.values()) {
            if (filePaths.size() > 1) {
                duplicates.add(filePaths);
            }
        }

        return duplicates;
    }
}