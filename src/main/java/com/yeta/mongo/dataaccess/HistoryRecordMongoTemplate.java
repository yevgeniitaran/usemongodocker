package com.yeta.mongo.dataaccess;

import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.parsers.RottenTomatoesParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

public class HistoryRecordMongoTemplate {

    private static Logger LOG = LogManager.getLogger(HistoryRecordMongoTemplate.class);

    private static final String COLLECTION_NAME_PREFIX = "historyrecord_";

    private MongoOperations mongoOperations;
    private String collectionName;

    HistoryRecordMongoTemplate(MongoOperations mongoOperations, String collectionName) {
        this.mongoOperations = mongoOperations;
        this.collectionName = collectionName;
    }

    private String getCollectionName() {
        return COLLECTION_NAME_PREFIX + collectionName;
    }

    public void insert(HistoryRecord historyRecord, String collectionName) {
        mongoOperations.insert(historyRecord, getCollectionName());
    }

    public HistoryRecord findById(String id, String collectionName) {
        return mongoOperations.findById(id, HistoryRecord.class, getCollectionName());
    }

    public void remove(HistoryRecord historyRecord, String collectionName) {
        mongoOperations.remove(historyRecord, getCollectionName());
    }

    public List<HistoryRecord> findByQuery(Query query, String collectionName) {
        return mongoOperations.find(query, HistoryRecord.class, getCollectionName());
    }

    public void insert(Collection<HistoryRecord> collection, String collectionName) {
        mongoOperations.insert(collection, getCollectionName());
    }

    public Collection<HistoryRecord> findAll(String collectionName) {
        return mongoOperations.findAll(HistoryRecord.class, getCollectionName());
    }

    public AggregationResults<HistoryRecord> aggregate(Aggregation aggregation, String collectionName) {
        return mongoOperations.aggregate(aggregation, getCollectionName(), HistoryRecord.class);
    }

    public Collection<HistoryRecord> findTopRecords() {
        SortOperation sortByCount = sort(Sort.Direction.DESC, "date");
        LimitOperation limitToTop = limit(100);
        Aggregation aggregation = Aggregation.newAggregation(sortByCount, limitToTop);
        AggregationResults<HistoryRecord> results = aggregate(aggregation, RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION);
        return results.getMappedResults().stream()
                .sorted(Comparator.comparingInt(HistoryRecord::getPosition))
                .collect(Collectors.toList());
    }

    public Collection<HistoryRecord> findTopRecordsPreviousPositions(Date date) {
        Collection<HistoryRecord> recordWithPreviousPositions = findTopRecords();
        for (HistoryRecord historyRecord : recordWithPreviousPositions) {
            Query query = new Query(Criteria.where("date").gt(date)
                    .and("name").is(historyRecord.getName()))
                    .with(new Sort(Sort.Direction.ASC, "date"));
            List<HistoryRecord> result = findByQuery(query, RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION);
            if (result.size() > 0) {
                historyRecord.setPreviousPosition(result.get(0).getPosition());
            }
        }
        return recordWithPreviousPositions;
    }

    public Collection<HistoryRecord> findRecordsThatLeftTopFromDate(Date date) {
        Collection<HistoryRecord> topRecords = findTopRecords();
        Query query = new Query(Criteria.where(("date")).gt(date))
                .with(new Sort(Sort.Direction.ASC, "date"));
        List<HistoryRecord> allRecordsSinceDate = findByQuery(query, RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION);
        Collection<String> topNames = topRecords
                .stream().map(HistoryRecord::getName)
                .collect(Collectors.toList());
        Collection<HistoryRecord> result = new ArrayList<>();
        for (HistoryRecord record : allRecordsSinceDate) {
            if (!topNames.contains(record.getName())) {
                result.add(record);
            }
        }
        return result;
    }
}
