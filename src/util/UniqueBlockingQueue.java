package util;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A thread safe queue that blocks when empty and does not allow items to be
 * added twice
 */
public class UniqueBlockingQueue<T> {

    private BlockingQueue<T> queue = new LinkedBlockingQueue<>();
    private Set<T> set = Collections.synchronizedSet(new HashSet<>());

    /**
     * Add an element to the queue
     * 
     * @param element the element to add to the queue
     * @return {@code true} if the element was successfully added
     */
    public synchronized boolean add(T element) {
        if (set.contains(element)) {
            return false;
        }
        set.add(element);
        return queue.add(element);
    }

    /**
     * Remove and retrieve the next element in the queue
     * 
     * @return the next element in the queue
     * @throws InterruptedException if interrupted while waiting
     */
    public T take() throws InterruptedException {
        return queue.take();
    }

}
