package com.yeta.mongo.test;

import com.yeta.mongo.data.jpa.SampleDataRestApplication;
import com.yeta.mongo.data.jpa.controller.TenantGenerator;
import com.yeta.mongo.data.jpa.domain.Employee;
import com.yeta.mongo.data.jpa.service.EmployeeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleDataRestApplication.class)
public class ApplicationTests {

    @Autowired
    private EmployeeRepository repo;

    @Autowired
    private MongoTemplate template;

    @Test
    public void contextLoads() {

        // Set the collection with ABC
        TenantGenerator.tenant = "ABC";

        Employee e = new Employee();
        e.setFullName("test1");

        repo.save(e);

        Employee ee = new Employee();
        ee.setFullName("test2");

        repo.save(ee);



        List<Employee> findAll = repo.findAll();
        System.out.println(findAll.size());

        Employee eee = new Employee();
        e.setFullName("test");

        if (true) {
            throw new RuntimeException();
        }


        template.save(eee,"customercoll");

        System.out.println(repo.someMethod("test"));

        //Set collection name with XYZ
        TenantGenerator.tenant = "XYZ";

        System.out.println(repo.someMethod("test")); // PROBLEM  this should try to get from XYZ. But instead tries to fetch from ABC itself
        System.out.println(repo.findAll());

    }

}