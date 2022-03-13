package com.learn.streams.repo;

import java.util.List;

import com.learn.streams.models.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {

    List<Order> findAll();
}
