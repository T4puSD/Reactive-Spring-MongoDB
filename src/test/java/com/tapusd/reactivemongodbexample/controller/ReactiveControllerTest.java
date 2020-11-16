package com.tapusd.reactivemongodbexample.controller;

import com.tapusd.reactivemongodbexample.domain.Employee;
import com.tapusd.reactivemongodbexample.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootTest
class ReactiveControllerTest {

    private final EmployeeRepository employeeRepository;
    private final List<Employee> employeeList = Arrays.asList(
            new Employee("1", "John", 20000L),
            new Employee("2", "Don", 18000L),
            new Employee("3", "Ketty", 16000L)
    );

    @Autowired
    public ReactiveControllerTest(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @BeforeEach
    void saveEmployees(){
        employeeRepository.deleteAll().subscribe(null,null,()->{
//            employeeRepository.saveAll(employeeList);
           employeeList.forEach(employee -> {
               employeeRepository.save(employee).subscribe(System.out::println);
           });
        });
    }

    @Test
    void findAllTest(){
        StepVerifier.create(employeeRepository.findAll())
                .expectComplete()
                .verify();
    }

    @Test
    void findOne(){
        StepVerifier.create(employeeRepository.findById("1"))
                .expectComplete()
                .verifyThenAssertThat()
                .tookLessThan(Duration.ofMillis(100));
    }
}
