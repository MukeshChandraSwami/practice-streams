package com.learn.streams;

import com.learn.streams.models.Customer;
import com.learn.streams.models.Order;
import com.learn.streams.models.Product;
import com.learn.streams.repo.ProductRepo;
import com.learn.streams.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@DataJpaTest
class LearnStreamsApplicationTests {

	@Autowired
	ResourceService resourceService;

	@Test
	@DisplayName("Obtain a list of product with category = \"Books\" and price > 100")
	public void t1() {
		List<Product> books = resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("books") && p.getPrice() > 100)
				.collect(Collectors.toList());

		books.forEach(System.out::println);
	}

	@Test
	@DisplayName("Obtain a list of orders with product category = \"Baby\" ")
	public void t2() {
		resourceService.findAllOrders().stream()
				.filter(o -> o.getProducts().stream()
						.anyMatch(p->p.getCategory().equalsIgnoreCase("baby")))
				.forEach(System.out::println);
	}

	@Test
	@DisplayName("Obtain a list of product with category = \"toys\" and apply discount of 10%")
	public void t3() {
		resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("toys"))
				.map(p -> "Toy Name:- " + p.getName() + "\tOriginal Price:- " + p.getPrice() +
						"\tDiscounted Price:- " + (p.getPrice() - (p.getPrice() * 0.1))
				).forEach(System.out::println);
	}

	@Test
	@DisplayName("Obtain a list of products ordered by customer of tier 2 between 01-Feb-2021 and 01-Apr-2021")
	public void t4() {
		resourceService.findAllOrders().stream()
				.filter(o -> o.getOrderDate().isAfter(getDate("01-Feb-2021")) && o.getOrderDate().isBefore(getDate("01-Apr-2021")))
				.filter(o -> o.getCustomer().getTier() == 2)
				.flatMap(o -> o.getProducts().stream())
				.distinct()
				.forEach(System.out::println);
	}

	@Test
	@DisplayName("Get the cheapest products of “Books” category")
	public void t5() {
		// 1
		resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("books"))
				//.peek(System.out::println)
				.min(Comparator.comparing(Product::getPrice))
				.ifPresent(System.out::println);

		// 2
		resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("books"))
				.sorted(Comparator.comparing(Product::getPrice))
				.findFirst()
				.ifPresent(System.out::println);

		// 3
		resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("books"))
				.collect(Collectors.minBy(Comparator.comparing(Product::getPrice)))
				.ifPresent(System.out::println);
	}

	@Test
	@DisplayName("Get the 3 most recent placed order")
	public void t6() {
		resourceService.findAllOrders().stream()
				.sorted(Comparator.comparing(Order::getOrderDate).reversed())
				.limit(3)
				.forEach(System.out::println);
	}

	@Test
	@DisplayName("Get a list of orders which were ordered on 15-Mar-2021, log the order records to the console and then return its product list")
	public void t7() {
		resourceService.findAllOrders().stream()
				.filter(o -> o.getOrderDate().isEqual(getDate("15-Mar-2021")))
				.peek(o -> log.info(o.toString()))
				.flatMap(o -> o.getProducts().stream())
				.distinct()
				.forEach(System.out::println);
	}

	@Test
	@DisplayName("Calculate total lump sum of all orders placed in Feb 2021")
	public void t8() {
		double totalPrice = resourceService.findAllOrders().stream()
				.filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021,2,1)) >= 0)
				.filter(o -> o.getOrderDate().compareTo(LocalDate.of(2021,3,1)) < 0)
				.flatMap(o -> o.getProducts().stream())
				.mapToDouble(Product::getPrice)
				.sum();

		System.out.println("Total Price:- " + totalPrice);
	}

	@Test
	@DisplayName("Calculate order average payment placed on 15-Mar-2021")
	public void t9() {
		resourceService.findAllOrders().stream()
				.filter(o -> o.getOrderDate().isEqual(LocalDate.of(2021,3,15)))
				.flatMap(o -> o.getProducts().stream())
				.mapToDouble(Product::getPrice)
				.average()
				.ifPresent(System.out::println);
	}

	@Test
	@DisplayName("Obtain a collection of statistic figures (i.e. sum, average, max, min, count) for all products of category “Books” ")
	public void t10() {
		DoubleSummaryStatistics statistics = resourceService.findAllProducts().stream()
				.filter(p -> p.getCategory().equalsIgnoreCase("books"))
				.mapToDouble(Product::getPrice)
				.summaryStatistics();
		System.out.println(statistics);
	}

	@Test
	@DisplayName("Obtain a data map with order id and order’s product count")
	public void t11() {
		Map<Long, Integer> collect = resourceService.findAllOrders().stream()
				.collect(Collectors.toMap(Order::getId, o -> o.getProducts().size()));
		collect.forEach((key, val) -> System.out.println("Order Id :- " + key + " Total products :- " + val));
	}

	@Test
	@DisplayName("Produce a data map with order records grouped by customer")
	public void t12() {
		Map<Customer, List<Order>> ordersByCustomers = resourceService.findAllOrders().stream()
				.collect(Collectors.groupingBy(Order::getCustomer));
		ordersByCustomers.forEach((c, o) -> System.out.println("Customer:- " + c.getName() + " Orders:- " + o));
	}

	@Test
	@DisplayName("Produce a data map with order record and product total sum")
	public void t13() {
		Map<Order, Double> collect = resourceService.findAllOrders().stream()
				.collect(Collectors.toMap(Function.identity(), o -> o.getProducts().stream().mapToDouble(Product::getPrice).sum()));

		System.out.println(collect);
	}

	@Test
	@DisplayName("Obtain a data map with list of product name by category")
	public void t14() {
		Map<String, List<String>> collect = resourceService.findAllProducts().stream()
				.collect(Collectors.groupingBy(
						Product::getCategory,
						Collectors.mapping(Product::getName, Collectors.toList())
					)
				);

		System.out.println(collect);
	}

	@Test
	@DisplayName("Get the most expensive product by category")
	public void t15() {
		Map<String, Optional<Product>> collect = resourceService.findAllProducts().stream()
				.collect(Collectors.groupingBy(
								Product::getCategory,
								Collectors.maxBy(Comparator.comparing(Product::getPrice))
						)
				);

		System.out.println(collect);
	}

	private ChronoLocalDate getDate(String s) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy");
		return LocalDate.parse(s, formatter);
	}
}
