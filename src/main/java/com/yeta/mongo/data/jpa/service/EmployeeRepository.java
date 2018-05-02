package com.yeta.mongo.data.jpa.service;

import com.yeta.mongo.data.jpa.domain.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

//public interface EmployeeRepository extends MongoRepository<Employee, String> {
//
//}

public interface EmployeeRepository extends MongoRepository<Employee,String> {

    @Query(value="{'name':?0}")
    public List<Employee> someMethod(String id);
}
