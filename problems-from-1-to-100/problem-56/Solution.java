import java.util.LinkedList;

class MyQueue {

    private LinkedList<Integer> front;
    private LinkedList<Integer> back;

    public MyQueue() {
        front = new LinkedList<>();
        back = new LinkedList<>();
    }
    
    public void push(int x) {
        back.push(x);
    }
    
    public int pop() {
        if (front.isEmpty()) {
            while (!back.isEmpty()) {
                front.push(back.pop());
            }
        }
        return front.pop();
    }
    
    public int peek() {
        if (front.isEmpty()) {
            while (!back.isEmpty()) {
                front.push(back.pop());
            }
        }
        return front.peek();
    }
    
    public boolean empty() {
        return front.isEmpty() && back.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */