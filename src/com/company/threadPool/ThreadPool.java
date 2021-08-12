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
            threads.add(new Thread(new threadRunnable()));
        }
        for(Thread thread : threads) {
            System.out.println(thread.getName() + " started");
            thread.start();
        }
    }

    void run(Integer data) {
        try {
            producerSemaphore.acquire();
            mutex.acquire();
            functions.add(data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            mutex.release();
            producerSemaphore.release();
        }
    }


    private class threadRunnable implements Runnable {
//        Integer i;

        @Override
        public void run() {
            while (true) {
                try {
                    consumerSemaphore.acquire();
                    mutex.acquire();
                    Integer i = (functions.poll());
                    mutex.release();
                    System.out.println(consumerSemaphore.toString() + " -- " + i);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    consumerSemaphore.release();
                }
            }
        }
    }
}

