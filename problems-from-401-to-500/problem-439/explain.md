# 609. Find Duplicate File in System

## Idea

Files are duplicates when their contents are equal. Therefore, use a hash map whose:

- **Key** is the file content.
- **Value** is the list of paths of files with that content.

After parsing every file, return only the map values containing at least two paths.

## Parsing the Input

Each directory description has this form:

```text
directory file1.txt(content1) file2.txt(content2)
```

For each description:

1. Find the first space to separate the directory path from the file information.
2. Find the next `(` to identify the end of the file name.
3. Find the next `)` to identify the end of the content.
4. Add `directory/fileName` to the list for that content.
5. Continue after the closing `)`.

The parser scans the string directly instead of using `split(" ")`. This keeps it correct even when a file content contains spaces.

## Algorithm

```text
filesByContent = empty hash map

for each directory description:
    parse its directory path

    while there are unparsed files:
        parse the file name and content
        add directory/file name to filesByContent[content]

duplicates = empty list
for each list of paths in filesByContent:
    if the list contains at least two paths:
        add it to duplicates

return duplicates
```

## Correctness Proof

For every parsed file, the algorithm stores its complete path under a key equal to its content. Thus, two files are stored in the same list if and only if they have the same content.

A list with at least two paths therefore represents a group of duplicate files. Lists with one path cannot represent duplicates and are excluded. Consequently, the returned lists contain exactly all groups of duplicate files.

## Complexity

Let $N$ be the total number of characters in the input and $F$ the number of files.

- **Time:** $O(N)$ expected, because every input character is scanned a constant number of times and hash-map operations are expected $O(1)$.
- **Space:** $O(N)$, because the map stores every file content and every file path.

## Java Solution

```java
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
```

## Important Edge Cases

- A content group with only one file is not returned.
- The root directory works normally because its path is still present before the first space.
- Empty content is handled: the parser reads the text between `(` and `)` as an empty string.
- File contents containing spaces are handled because parsing is based on parentheses, not whitespace.
