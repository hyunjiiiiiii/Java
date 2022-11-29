package deque;

public class LinkedListDeque<T> implements Deque<T> {

    private Node head;
    private Node tail;
    private int size;
    public LinkedListDeque() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node newHead = new Node(item);
        Node currentHead = this.head;

//        if (size == 0) {
//            newHead.previous = null;
//            newHead.next = null;
//        }
        if (currentHead != null) {
            currentHead.previous = newHead;
            newHead.next = currentHead;
        }
        this.head = newHead;

        if (this.tail == null) {
            this.tail = newHead;
        }

        size += 1;
    }

    @Override
    public void addLast(T item) {
        Node newTail = new Node(item);
        Node currentTail = this.tail;

        if (currentTail != null) {
            currentTail.next = newTail;
            newTail.previous = currentTail;
        }

        this.tail = newTail;

        if (this.head == null) {
            this.head = newTail;
        }

        size += 1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        Node current = this.head;
        String result = "";

        while (current != null) {
            result += current.item;
            result += " ";
            current = current.next;
        }

        System.out.println(result);
    }

    @Override
    public T removeFirst() {

        // null <-> H[1] <-> [2] <-> [3]T <-> null
        if (this.head != null  && size > 0) {
            T toRemove = this.head.item;
            // head = 1
            this.head = this.head.next;
            // head = 2

            Node newHead = this.head;
            if (newHead == null) {
                this.tail = null;
                this.head = null;
            }
            // head = 2

            // null <-> H[2] <-> [3]T <-> null
            if (newHead != null) {
                newHead.previous = null;
            }
            size -= 1;
            return toRemove;
        }

        return null;
    }

    @Override
    public T removeLast() {
        if (this.tail != null && size > 0) {
            T toRemove = this.tail.item;

            this.tail = this.tail.previous;

            Node newTail = this.tail;

            if (newTail == null) {
                this.head = null;
                this.tail = null;
            }

            if (newTail != null) {
                newTail.next = null;
            }
            size -= 1;
            return toRemove;
        }
        return null;
    }
    @Override
    public T get(int index) {
        Node l = this.head;
        T result = null;
        int count = 0;
//while l != null
        while (l != null) {
            if (count == index) {
                result = l.item;
            }
            count++;
            l = l.next;
        }

        return result;
    }

    public T getRecursive(int index) {
        int count = 0;

        // [0] [1] [2] c= 0 i=2
        // getRecursive(1)
        // [0] [1] [2] c= 0 i=1
        // getRecursive(0)
        // [0] [1] [2] c= 0 i=0
        if (count == index) {
            return this.head.item;
        } else {
            this.head = this.head.next;
        }
        return getRecursive(index - 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkedListDeque lld = (LinkedListDeque) o;
        if(size == lld.size) {
            while(lld.head != null && this.head != null && lld.head.item == this.head.item) {
                lld.head = lld.head.next;
                this.head = this.head.next;
            }

            return lld.head == this.head;
        }
        return false;
    }

    private class Node {
        public T item;
        public Node previous;
        public Node next;

        public Node(T item) {
            this.item = item;
        }
    }
}

