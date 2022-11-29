package deque;

public class ArrayDeque<T> implements Deque<T> {

    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int size;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        nextFirst = 0;
        nextLast = 1;
        size = 0;
    }

    private void resize(int capacity) {
        T[] a = (T[]) new Object[capacity];
        for (int i = 0; i < size; i += 1) {
            a[i] = items[i];
        }
        nextFirst = a.length - 1;
        nextLast = size;
        items = a;

    }

    @Override
    public void addFirst(T x) {
        if (size == items.length) {
            resize(size * 2);
        }
        items[nextFirst] = x;
        if (nextFirst - 1 < 0) {
            nextFirst = items.length - 1;
        } else {
            nextFirst--;
        }
        size++;
    }

    @Override
    public void addLast(T x) {
        if (size == items.length) {
            resize(items.length * 2);
        }
        items[nextLast] = x;
        size++;

        nextLast = (nextLast + 1) % items.length;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        String result = "";
        for (int i = 0; i < items.length; i++) {
            result += items[i];
            result += " ";
        }
        System.out.println(result);
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = (nextFirst + 1) % items.length;
        T result = items[nextFirst];
        items[nextFirst] = null;
        if (items.length > 16 && (double) size / items.length < 0.25) {
            resize(items.length / 2);
        }
        size--;
        return result;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        if (nextLast - 1 < 0) {
            nextLast = items.length - 1;
        } else {
            nextLast--;
        }
        T last = items[nextLast];
        items[nextLast] = null;
        nextLast = nextLast;
        if (items.length > 16 && (double) size / items.length < 0.25) {
            resize(items.length / 2);
        }
        size--;
        return last;
    }

    @Override
    public T get(int index) {
        if(!isEmpty()) {
            if (index > nextFirst || index < nextLast) {
                if (items[index] != null) {
                    return items[index];
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Deque) {
            if(((Deque<T>)o).size() != size()) {
                return false;
            }
            for (int i = 0; i < size; i++) {
                if(((Deque<T>)o).get(i) != get(i)) {
                    return false;
                }
                continue;
            }
            return true;
        } else {
            return false;
        }
    }

}