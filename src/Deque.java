import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node<Item> first, last;
    private int size = 0;

    // construct an empty deque
    public Deque() {}

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException();
        if (size++ == 0) {
            first = last = new Node<>(item);
        } else {
            Node<Item> oldFirst = first;
            first = new Node<>(item);
            first.next = oldFirst;
            oldFirst.previous = first;
        }
    }

    // add the item to the end
    public void addLast(Item item) {
        if (item == null) throw new NullPointerException();
        if (size++ == 0) {
            first = last = new Node<>(item);
        } else {
            Node<Item> oldLast = last;
            last = new Node<>(item);
            last.previous = oldLast;
            oldLast.next = last;
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (first == null) throw new NoSuchElementException();
        Item item = first.item;
        first = first.next;
        if (--size > 0) first.previous = null;
        return item;
    }

    // remove and return the item from the end
    public Item removeLast() {
        if (last == null) throw new NoSuchElementException();
        Item item = last.item;
        last = last.previous;
        if (--size > 0) last.next = null;
        return item;
    }

    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
        return new Iterator<Item>() {
            private Node<Item> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public Item next() {
                if (! hasNext()) throw new NoSuchElementException();
                Item item = current.item;
                current = current.next;
                return item;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    // unit testing
    public static void main(String[] args) {
        Deque<String> d = new Deque<>();
        d.addFirst("first");
        StdOut.print("added first. size=" + d.size() + "\n");
        StdOut.print("last: " + d.removeLast() + " size=" + d.size() + "\n");
        d.addLast("last");
        StdOut.print("added last. size=" + d.size() + "\n");
        StdOut.print("first: " + d.removeFirst() + " size=" + d.size() + "\n");
        String a[] = {"one", "two", "three", "four", "five"};
        for (String s : a) {
            d.addLast(s);
        }
        Iterator<String> it = d.iterator();
        while(it.hasNext()) {
            StdOut.print(it.next() + "\n");
        }
        d.addLast("last1");
        StdOut.print("added last1. size=" + d.size() + "\n");
        d.addLast("last2");
        StdOut.print("added last2. size=" + d.size() + "\n");
        StdOut.print("first: " + d.removeFirst() + " size=" + d.size() + "\n");
        StdOut.print("first: " + d.removeFirst() + " size=" + d.size() + "\n");
        while (!d.isEmpty()) {
            StdOut.print(d.removeLast() + "\n");
        }
        StdOut.print("size=" + d.size() + "\n");
    }

    private static class Node<i> {
        private Node(i item) {
            this.item = item;
        }
        private i item;
        private Node<i> previous, next;
    }
}
