package com.learn.streams;

import com.learn.streams.models.Customer;
import com.learn.streams.models.Product;
import com.learn.streams.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

@Slf4j
@DataJpaTest
public class GFGTest {

    private static final String STRING_INPUT="geeksforgeeks";

    @Autowired
    ResourceService resourceService;

    @Test
    @DisplayName("Find first element of the stream by using findFirst()")
    public void t1() {
        resourceService.findAllCustomers().stream()
                .findFirst()
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("Find first element of the stream by using reduce()")
    public void t2() {
        resourceService.findAllCustomers().stream()
                .reduce((a,b) -> a)
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("Find the last element of the stream by using reduce()")
    public void t3() {
        resourceService.findAllCustomers().stream()
                .reduce((a,b) -> b)
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("Find the last element of the stream by using skip()")
    public void t4() {
        List<Customer> allCustomers = resourceService.findAllCustomers();
        allCustomers.stream()
                .skip(allCustomers.size() - 1)
                .findFirst()
                .ifPresent(System.out::println);
    }

    @Test
    @DisplayName("Find duplicate elements in the stream by using froupingBy() and counting()")
    public void t5() {
        resourceService.findAllProducts().stream()
                .map(Product::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() > 1)
                .forEach(e -> System.out.println(e.getKey()));
    }

    @Test
    @DisplayName("Find duplicate elements in the stream by using Collections.frequency()")
    public void t6() {
        List<String> allProducts = resourceService.findAllProducts().stream()
                .map(Product::getCategory)
                .collect(Collectors.toList());

        allProducts.stream()
                .filter(p -> Collections.frequency(allProducts, p) > 1)
                .distinct()
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Calculate the occurrence of the elements")
    public void t7() {
        resourceService.findAllProducts().stream()
                .map(Product::getCategory)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .forEach((p,count) -> System.out.println("Product:- " + p + " Occurrence:- " + count));
    }

    @Test
    @DisplayName("Calculate the occurrence of the character in a string")
    public void t8() {
        char input = 'e';
        long count = STRING_INPUT.chars().mapToObj(i -> (char) i)
                .filter(chars -> chars == input)
                .count();

        System.out.println("Occurrence of :- " + input + " is :- " + count);
    }

    @Test
    @DisplayName("Generate infinite stream of doubles OR ints")
    public void t9() {
        DoubleStream.iterate(1, i -> i+1)
                .forEach(System.out::println);
    }

    @Test
    @DisplayName("Program to Iterate over a Stream with Indices in Java 8")
    public void t10() {
        AtomicInteger ai = new AtomicInteger(0);
        Map<Integer, Character> collect = STRING_INPUT.chars().mapToObj(i -> (char) i)
                .collect(Collectors.toMap(ch -> ai.getAndIncrement(), Function.identity()));
        System.out.println(collect);
    }

}
