package com.yeta.mongo.data.jpa.service;

import com.yeta.mongo.data.jpa.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeRepository extends MongoRepository<Employee, String> {

}
