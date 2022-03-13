package com.learn.streams;

import com.learn.streams.models.Product;
import com.learn.streams.repo.ProductRepo;
import com.learn.streams.service.ResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@DataJpaTest
class LearnStreamsApplicationTests {

	@Autowired
	ResourceService resourceService;
}
