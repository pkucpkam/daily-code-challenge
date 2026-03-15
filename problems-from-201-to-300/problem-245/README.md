# 192. Word Frequency

**Difficulty:** Medium

Write a bash script to calculate the frequency of each word in a text file words.txt.

For simplicity sake, you may assume:

## Assumptions

- words.txt contains only lowercase characters and space ' ' characters.
- Each word must consist of lowercase characters only.
- Words are separated by one or more whitespace characters.

## Example

Assume that words.txt has the following content:

```text
the day is sunny the the
the sunny is is
```

Your script should output the following, sorted by descending frequency:

```text
the 4
is 3
sunny 2
day 1
```

## Note

Don't worry about handling ties, it is guaranteed that each word's frequency count is unique.

## Follow up

Could you write it in one-line using Unix pipes?

## Best Solution

```bash
tr -s '[:space:]' '\n' < words.txt | sort | uniq -c | sort -rn | awk '{print $2, $1}'
```

### Readable Script Version

```bash
#!/usr/bin/env bash
tr -s '[:space:]' '\n' < words.txt \
| sort \
| uniq -c \
| sort -rn \
| awk '{print $2, $1}'
```