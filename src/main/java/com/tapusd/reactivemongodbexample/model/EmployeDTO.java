package com.tapusd.reactivemongodbexample.model;

import java.time.LocalDateTime;

public class EmployeDTO {
    private String id;
    private String name;
    private Long salary;
    private LocalDateTime localDateTime;

    public EmployeDTO(String id, String name, Long salary, LocalDateTime localDateTime) {
        this.id = id;
        this.name = name;
        this.salary = salary;
        this.localDateTime = localDateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

}
