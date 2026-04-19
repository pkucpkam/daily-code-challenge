import java.util.Iterator;

class PeekingIterator implements Iterator<Integer> {
    private final Iterator<Integer> iterator;
    private Integer nextElement;
    private boolean hasNext;

    public PeekingIterator(Iterator<Integer> iterator) {
        this.iterator = iterator;
        this.hasNext = iterator.hasNext();
        this.nextElement = this.hasNext ? iterator.next() : null;
    }

    // Returns the next element in the iteration without advancing the iterator.
    public Integer peek() {
        return nextElement;
    }

    // hasNext() and next() should behave the same as in the Iterator interface.
    // Override them if needed.
    @Override
    public Integer next() {
        Integer current = nextElement;
        if (iterator.hasNext()) {
            nextElement = iterator.next();
            hasNext = true;
        } else {
            nextElement = null;
            hasNext = false;
        }
        return current;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }
}