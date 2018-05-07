package com.yeta.mongo.dataaccess;

import com.yeta.mongo.domain.HistoryRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class HistoryRecordMongoTemplate {

    private static Logger LOG = LogManager.getLogger(HistoryRecordMongoTemplate.class);

    private static final String COLLECTION_NAME_PREFIX = "historyrecord_";

    private MongoTemplate mongoTemplate;

    @Autowired
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

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

    public List<HistoryRecord> findByQuery(Query query, String collectionName) {
        return mongoTemplate.find(query, HistoryRecord.class, generateCollectionName(collectionName));
    }

    public void insert(Collection<HistoryRecord> collection, String collectionName) {
        mongoTemplate.insert(collection, generateCollectionName(collectionName));
    }

    public Collection<HistoryRecord> findAll(String collectionName) {
        return mongoTemplate.findAll(HistoryRecord.class, generateCollectionName(collectionName));
    }
}
