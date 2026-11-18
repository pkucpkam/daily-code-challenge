import java.util.List;

/*
// Definition for Employee.
class Employee {
    public int id;
    public int importance;
    public List<Integer> subordinates;
};
*/

class Solution {
    public int getImportance(List<Employee> employees, int id) {
        // Ánh xạ trực tiếp id -> Employee với O(1) tuyệt đối (vì 1 <= id <= 2000)
        Employee[] empById = new Employee[2001];
        for (Employee emp : employees) {
            empById[emp.id] = emp;
        }

        return dfs(empById, id);
    }

    private int dfs(Employee[] empById, int id) {
        Employee emp = empById[id];
        int totalImportance = emp.importance;

        // Cộng dồn độ quan trọng của tất cả cấp dưới trực tiếp và gián tiếp
        for (int subId : emp.subordinates) {
            totalImportance += dfs(empById, subId);
        }

        return totalImportance;
    }
}
