# 194. Transpose File

**Difficulty:** Medium

Given a text file `file.txt`, transpose its content.

## Assumptions

- Each row has the same number of columns.
- Each field is separated by a single space (`' '`).

## Example

If `file.txt` has the following content:

```text
name age
alice 21
ryan 30
```

Output:

```text
name alice ryan
age 21 30
```

## Best Solution

```bash
awk '{
	for (i = 1; i <= NF; i++) {
		if (NR == 1) {
			col[i] = $i
		} else {
			col[i] = col[i] " " $i
		}
	}
}
END {
	for (i = 1; i <= length(col); i++) {
		print col[i]
	}
}' file.txt
```

### Readable Script Version

```bash
#!/usr/bin/env bash

awk '{
	for (i = 1; i <= NF; i++) {
		if (NR == 1) {
			col[i] = $i
		} else {
			col[i] = col[i] " " $i
		}
	}
}
END {
	for (i = 1; i <= length(col); i++) {
		print col[i]
	}
}' file.txt
```