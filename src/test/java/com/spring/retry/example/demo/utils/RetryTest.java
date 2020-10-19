package com.spring.retry.example.demo.utils;

import com.spring.retry.example.utils.Retry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class RetryTest {

    @Test
    public void testRunnable() throws Exception {
        Retry.retry(() -> System.out.println("hi"), new IOException(), "error occurs");
    }

    @Test()
    public void testRunnableWithException() throws Exception {

        Assertions.assertThrows(Exception.class, () ->
            Retry.retry(() -> {
                throw new Exception("oops");
            }, new IOException(), "error occurs")
        );
    }

    @Test
    public void testCallable() throws Exception {
        String result = Retry.retry(() -> "hi", new IOException(), "error occurs");
        System.out.println(result);

        List<String> results = Retry.retry(() -> Arrays.asList("hi1", "hi2", "hi3"), new IOException(), "error occurs");
        results.forEach(str -> System.out.println("List : " + str));
    }

    @Test()
    public void testCallableException() throws Exception {

        Assertions.assertThrows(Exception.class, () -> {
            String result = Retry.retry(() -> {
                throw new Exception("oops");
            }, new IOException(), "error occurs");
        });
    }

}