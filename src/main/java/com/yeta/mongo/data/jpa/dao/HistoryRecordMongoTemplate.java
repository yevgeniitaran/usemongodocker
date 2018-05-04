package com.yeta.mongo.data.jpa.dao;

import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRecordMongoTemplate {

    private static final String COLLECTION_NAME_PREFIX = "history_record_";

    @Autowired
    private MongoTemplate mongoTemplate;

    protected String generateCollectionName(String collectionName) {
        return COLLECTION_NAME_PREFIX + collectionName;
    }

    public void insert(HistoryRecord historyRecord, String collectionName) {
        mongoTemplate.insert(historyRecord, generateCollectionName(collectionName));
    }

    public HistoryRecord findById(String id, String collectionName) {
        return mongoTemplate.findById(id, HistoryRecord.class, generateCollectionName(collectionName));
    }

    public void remove(HistoryRecord historyRecord, String collectionName) {
        mongoTemplate.remove(historyRecord, generateCollectionName(collectionName));
    }
}
