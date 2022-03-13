package com.learn.streams;

import com.learn.streams.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Slf4j
@Component
public class AppCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ResourceService resourceService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        log.info("Customers:");
        resourceService.findAllCustomers()
                .forEach(c -> log.info(c.toString()));

        log.info("Orders:");
        resourceService.findAllOrders()
                .forEach(o -> log.info(o.toString()));

        log.info("Products:");
        resourceService.findAllProducts()
                .forEach(p -> log.info(p.toString()));
    }
}
