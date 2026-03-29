class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] indegree = new int[numCourses];
        int[][] graph = new int[numCourses][];
        int[] outCount = new int[numCourses];

        for (int[] edge : prerequisites) {
            outCount[edge[1]]++;
        }

        for (int i = 0; i < numCourses; i++) {
            graph[i] = new int[outCount[i]];
        }

        int[] nextIndex = new int[numCourses];
        for (int[] edge : prerequisites) {
            int course = edge[0];
            int pre = edge[1];
            graph[pre][nextIndex[pre]++] = course;
            indegree[course]++;
        }

        int[] queue = new int[numCourses];
        int head = 0;
        int tail = 0;

        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                queue[tail++] = i;
            }
        }

        int[] order = new int[numCourses];
        int idx = 0;

        while (head < tail) {
            int cur = queue[head++];
            order[idx++] = cur;

            for (int next : graph[cur]) {
                indegree[next]--;
                if (indegree[next] == 0) {
                    queue[tail++] = next;
                }
            }
        }

        return idx == numCourses ? order : new int[0];
    }
}