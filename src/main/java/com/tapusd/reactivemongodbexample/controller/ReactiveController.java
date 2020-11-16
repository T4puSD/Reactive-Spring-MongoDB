package com.tapusd.reactivemongodbexample.controller;

import com.tapusd.reactivemongodbexample.domain.Employee;
import com.tapusd.reactivemongodbexample.model.EmployeDTO;
import com.tapusd.reactivemongodbexample.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@RestController
@RequestMapping("/employee")
public class ReactiveController {
    public static final Logger LOGGER = LoggerFactory.getLogger(ReactiveController.class);

    private final EmployeeRepository employeeRepository;

    @Autowired
    public ReactiveController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping("/all")
    public Flux<Employee> getAll() {
        return employeeRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Employee> getOne(@PathVariable String id) {
        return employeeRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Mono<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        employee.setId(id);
        return employeeRepository.findById(id).flatMap(emp -> {
            emp.setName(employee.getName());
            emp.setSalary(employee.getSalary());
            LOGGER.info("Saving old employee with updated value");
            return employeeRepository.save(emp);
        }).or(employeeRepository.save(employee));
    }

    @GetMapping(value = "/{id}/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EmployeDTO> getOneEvent(@PathVariable String id) {
        return employeeRepository.findById(id)
                .flatMapMany(
                        employee -> Flux.fromStream(Stream.generate(() -> new EmployeDTO(employee.getId(), employee.getName(), employee.getSalary(), LocalDateTime.now())))
                ).delayElements(Duration.ofSeconds(5));
    }
}
