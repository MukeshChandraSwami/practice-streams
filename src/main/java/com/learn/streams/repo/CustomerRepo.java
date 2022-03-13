package com.learn.streams.repo;

import java.util.List;

import com.learn.streams.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

    List<Customer> findAll();
}