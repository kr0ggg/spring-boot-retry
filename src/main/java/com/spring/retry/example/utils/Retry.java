package com.spring.retry.example.utils;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class Retry {
    private static final int RETRY = 3;
    private static final long DELAY = 1000l;

    private static final Logger log = Logger.getLogger(Retry.class.getName());

    @FunctionalInterface
    public interface RunnableWithException {
        void run() throws Exception;
    }

    public static <V> V retry(Callable<V> callable, Throwable throwable, String message) {
        return retryLogics(callable, throwable, message);
    }

    public static void retry(RunnableWithException runnable, Throwable throwable, String message) {
        retryLogics(() -> {
            runnable.run();
            return null;
        }, throwable, message);
    }

    private static <T> T retryLogics(Callable<T> callable, Throwable throwable, String message) {
        int counter = 0;

        while (counter < RETRY) {
            try {
                return callable.call();
            } catch (Exception e) {
                counter++;
//                log.error("retry " + counter + " / " + RETRY + ", " + message, e);

                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }

        throw new RuntimeException(throwable);
    }
}