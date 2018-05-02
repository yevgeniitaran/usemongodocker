package com.yeta.mongo.data.jpa.service;

import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

//public interface EmployeeRepository extends MongoRepository<HistoryRecord, String> {
//
//}

public interface HistoryRecordRepository extends MongoRepository<HistoryRecord,String> {

    @Query(value="{'name':?0}")
    List<HistoryRecord> someMethod(String id);

    List<HistoryRecord> findByName(String name);
}
