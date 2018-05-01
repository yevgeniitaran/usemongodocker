package com.yeta.mongo.data.jpa.controller;

import com.yeta.mongo.data.jpa.domain.Employee;
import com.yeta.mongo.data.jpa.service.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@ConfigurationProperties
@RestController
public class EmployeeController {


    @Value("${application.message}")
    String message;

    @Value("${application.appname}")
    String appname;

    @Autowired
    private EmployeeRepository employeeRepository;

    @RequestMapping("/")
    String home() {
        return "Hello World....- " + message + " " + appname;
    }

    @RequestMapping(value = "/employee", method = RequestMethod.POST)
    public Employee create(@RequestBody Employee employee) {
        return employeeRepository.save(employee);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{employeeId}")
    public Employee get(@PathVariable String employeeId) {
        return employeeRepository.findOne(employeeId);
    }
}