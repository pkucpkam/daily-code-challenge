class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        java.util.List<Integer>[] graph = new java.util.ArrayList[numCourses];
        for (int i = 0; i < numCourses; i++) {
            graph[i] = new java.util.ArrayList<>();
        }

        int[] inDegree = new int[numCourses];

        for (int[] pre : prerequisites) {
            int course = pre[0];
            int prereq = pre[1];
            graph[prereq].add(course);
            inDegree[course]++;
        }

        java.util.ArrayDeque<Integer> queue = new java.util.ArrayDeque<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int finished = 0;
        while (!queue.isEmpty()) {
            int current = queue.poll();
            finished++;

            for (int next : graph[current]) {
                inDegree[next]--;
                if (inDegree[next] == 0) {
                    queue.offer(next);
                }
            }
        }

        return finished == numCourses;
    }
}