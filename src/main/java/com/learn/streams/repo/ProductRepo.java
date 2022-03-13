package com.learn.streams.repo;

import java.util.List;

import com.learn.streams.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {

    List<Product> findAll();
}