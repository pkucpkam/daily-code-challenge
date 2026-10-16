class MyCircularDeque {
    /**
     * Best Solution: Cài đặt Hàng đợi hai đầu vòng (Circular Deque / Double-ended Ring Buffer)
     * bằng Mảng cố định (Fixed-size Array) kết hợp con trỏ head và biến đếm kích thước count.
     * 
     * Time Complexity: O(1) cho tất cả các thao tác (insertFront, insertLast, deleteFront, deleteLast, getFront, getRear, isEmpty, isFull).
     * Space Complexity: O(k) với mảng dữ liệu có dung lượng k cố định, tối ưu bộ nhớ và tận dụng CPU Cache.
     */
    private final int[] data;
    private final int capacity;
    private int head;
    private int count;

    public MyCircularDeque(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.count = 0;
    }
    
    public boolean insertFront(int value) {
        if (isFull()) {
            return false;
        }
        head = (head - 1 + capacity) % capacity;
        data[head] = value;
        count++;
        return true;
    }
    
    public boolean insertLast(int value) {
        if (isFull()) {
            return false;
        }
        int tail = (head + count) % capacity;
        data[tail] = value;
        count++;
        return true;
    }
    
    public boolean deleteFront() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        count--;
        return true;
    }
    
    public boolean deleteLast() {
        if (isEmpty()) {
            return false;
        }
        count--;
        return true;
    }
    
    public int getFront() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }
    
    public int getRear() {
        if (isEmpty()) {
            return -1;
        }
        int tail = (head + count - 1) % capacity;
        return data[tail];
    }
    
    public boolean isEmpty() {
        return count == 0;
    }
    
    public boolean isFull() {
        return count == capacity;
    }
}