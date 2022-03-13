package com.learn.streams.service;

import com.learn.streams.models.Customer;
import com.learn.streams.models.Order;
import com.learn.streams.models.Product;
import com.learn.streams.repo.CustomerRepo;
import com.learn.streams.repo.OrderRepo;
import com.learn.streams.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    @Autowired
    private CustomerRepo customerRepos;

    @Autowired
    private OrderRepo orderRepos;

    @Autowired
    private ProductRepo productRepos;

    public List<Customer> findAllCustomers() {
        return customerRepos.findAll();
    }

    public List<Order> findAllOrders() {
        return orderRepos.findAll();
    }

    public List<Product> findAllProducts() {
        return productRepos.findAll();
    }
}
