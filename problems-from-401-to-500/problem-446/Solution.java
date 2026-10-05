class MyCircularQueue {
    /**
     * Best Solution: Cài đặt Hàng đợi vòng (Circular Queue / Ring Buffer) bằng Mảng cố định (Fixed-size Array)
     * kết hợp con trỏ head và biến đếm kích thước count.
     * 
     * Time Complexity: O(1) cho tất cả các thao tác (enQueue, deQueue, Front, Rear, isEmpty, isFull).
     * Space Complexity: O(k) với mảng dữ liệu có dung lượng k cố định, tối ưu bộ nhớ và tận dụng CPU Cache.
     */
    private final int[] data;
    private final int capacity;
    private int head;
    private int count;

    public MyCircularQueue(int k) {
        this.capacity = k;
        this.data = new int[k];
        this.head = 0;
        this.count = 0;
    }
    
    public boolean enQueue(int value) {
        if (isFull()) {
            return false;
        }
        int tail = (head + count) % capacity;
        data[tail] = value;
        count++;
        return true;
    }
    
    public boolean deQueue() {
        if (isEmpty()) {
            return false;
        }
        head = (head + 1) % capacity;
        count--;
        return true;
    }
    
    public int Front() {
        if (isEmpty()) {
            return -1;
        }
        return data[head];
    }
    
    public int Rear() {
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

/**
 * Your MyCircularQueue object will be instantiated and called as such:
 * MyCircularQueue obj = new MyCircularQueue(k);
 * boolean param_1 = obj.enQueue(value);
 * boolean param_2 = obj.deQueue();
 * int param_3 = obj.Front();
 * int param_4 = obj.Rear();
 * boolean param_5 = obj.isEmpty();
 * boolean param_6 = obj.isFull();
 */