package util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

public class UniqueBlockingQueueTest {

    UniqueBlockingQueue<String> queue = new UniqueBlockingQueue<>();

    /**
     * Test that the queue supports add and remove operations with maintained order
     * 
     * @throws InterruptedException if interrupted while waiting
     */
    @Test
    void testBasicQueueUse() throws InterruptedException {
        assertTrue(queue.add("A"));
        assertTrue(queue.add("B"));
        assertEquals("A", queue.take());
        assertEquals("B", queue.take());
    }

    /**
     * Test that the queue doesn't add one item twice
     */
    @Test
    void testUnique() {
        assertTrue(queue.add("A"));
        assertFalse(queue.add("A"));
    }

    /**
     * Test that the queue doesn't add one item twice even when the first is no
     * longer in the queue
     * 
     * @throws InterruptedException if interrupted while waiting
     */
    @Test
    void testNoDuplicatesAfterRemoved() throws InterruptedException {
        assertTrue(queue.add("A"));
        queue.take();
        assertFalse(queue.add("A"));
    }

    /**
     * Test that the queue blocks properly
     *
     * @throws InterruptedException if execution of the threads is interrupted
     */
    @Test
    void testBlocking() throws InterruptedException {
        String[] result = { null };

        Thread producer = new Thread(() -> {
            try {
                Thread.sleep(1000);
                queue.add("A");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                result[0] = queue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();

        assertEquals(result[0], "A");
    }

    /**
     * Test that elements are unique in multithreaded environment
     * 
     * @throws InterruptedException if interrupted while waiting
     */
    @Test
    @Timeout(value = 5)
    void testUniqueMultithreaded() throws InterruptedException {
        final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
        final int size = 100_000;
        try (ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS)) {
            for (int i = 0; i < NUM_THREADS; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < size; j++) {
                        queue.add(String.valueOf(j));
                    }
                });
            }
        }

        Set<String> elements = new HashSet<>();
        for (int i = 0; i < size; i++) {
            assertTrue(elements.add(queue.take()));
        }
        assertEquals(size, elements.size());
    }

}
