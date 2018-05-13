package com.yeta.mongo.dataaccess;

import org.springframework.data.mongodb.core.MongoOperations;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HistoryRecordTemplateFactory {

    private static Map<String, HistoryRecordMongoTemplate> templateCache = new ConcurrentHashMap<>();

    private HistoryRecordTemplateFactory() {}

    public static HistoryRecordMongoTemplate newInstance(MongoOperations mongoOperations, String collectionName) {
        if (templateCache.containsKey(collectionName)) {
            return templateCache.get(collectionName);
        } else {
            HistoryRecordMongoTemplate historyRecordMongoTemplate = new HistoryRecordMongoTemplate(mongoOperations, collectionName);
            templateCache.put(collectionName, historyRecordMongoTemplate);
            return historyRecordMongoTemplate;
        }
    }
}
