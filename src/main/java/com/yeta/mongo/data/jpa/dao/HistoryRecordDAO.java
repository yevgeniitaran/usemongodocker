package com.yeta.mongo.data.jpa.dao;

import com.yeta.mongo.data.jpa.service.HistoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class HistoryRecordDAO {

    private MongoTemplate template;

    @Autowired
    public HistoryRecordDAO(MongoTemplate template) {
        this.template = template;
    }

}
