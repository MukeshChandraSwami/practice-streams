package com.learn.streams.controller;

import com.learn.streams.models.Customer;
import com.learn.streams.models.Order;
import com.learn.streams.models.Product;
import com.learn.streams.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.learn.streams.constants.ApiConstants.*;

@RestController
@RequestMapping(BASE_URL_RESOURCES)
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping(path = CUSTOMER)
    public List<Customer> getAllCustomers() {
        return resourceService.findAllCustomers();
    }

    @GetMapping(path = PRODUCT)
    public List<Product> getAllProducts() {
        return resourceService.findAllProducts();
    }

    @GetMapping(path = ORDER)
    public List<Order> getAllOrders() {
        return resourceService.findAllOrders();
    }
}
