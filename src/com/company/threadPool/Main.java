package com.company.threadPool;

import java.util.concurrent.ThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool();
        int i =0;
        while(true) {
            threadPool.run(i++);

        }
    }
}
