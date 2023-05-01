public interface PriorityQueue<E extends Comparable<E>> {
    void insert(E element);

    E max();

    E removeMax();

    int size();

    boolean isEmpty();
}
