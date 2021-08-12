package com.company.threadPool;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.Semaphore;

public class ThreadPool {
    private final int MAX_SEMAPHORE = 16;
    private final Semaphore producerSemaphore = new Semaphore(MAX_SEMAPHORE);
    private final Semaphore consumerSemaphore = new Semaphore(MAX_SEMAPHORE);
    private final Semaphore mutex = new Semaphore(1);
    private Queue<Integer> functions = new ArrayDeque<>();
    private List<Thread> threads = new ArrayList<>();


    public ThreadPool() {
        init();
    }

    private void init() {
        for (int i = 0; i < MAX_SEMAPHORE; i++) {
            threads.add(new Thread(new ThreadRunnable()));
        }
        for (Thread thread : threads) {
            System.out.println(thread.getName() + " started");
            thread.start();
        }
    }

    void run(Integer data) {
        try {
            producerSemaphore.acquire();
            mutex.acquire();
            functions.add(data);
            mutex.release();
            consumerSemaphore.acquire();
            producerSemaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }


    private class ThreadRunnable implements Runnable {

        @Override
        public void run() {
            while (true) {
                try {

                    mutex.acquire();
                    Integer i = (functions.poll());
                    mutex.release();
                    System.out.println( " -- " + i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    consumerSemaphore.release();
                }
            }
        }
    }
}
