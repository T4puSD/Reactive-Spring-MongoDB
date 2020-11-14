package com.tapusd.reactivemongodbexample;

import com.tapusd.reactivemongodbexample.domain.Employee;
import com.tapusd.reactivemongodbexample.repository.EmployeeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class ReactiveMongodbExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReactiveMongodbExampleApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(EmployeeRepository employeeRepository) {
        return args -> employeeRepository.deleteAll()
                .subscribe(null, null, () -> Stream.of(new Employee(UUID.randomUUID().toString(), "John", 20000L),
                        new Employee(UUID.randomUUID().toString(), "Don", 18000L),
                        new Employee(UUID.randomUUID().toString(), "Ketty", 16000L))
                        .forEach(employee -> employeeRepository.save(employee).subscribe(System.out::println)));
    }

}
