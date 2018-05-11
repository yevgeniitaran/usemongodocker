package com.yeta.mongo.dataaccess;

import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.domain.HistoryRecordWithPreviousPosition;
import com.yeta.mongo.parsers.RottenTomatoesParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.LimitOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

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

    public AggregationResults<HistoryRecord> aggregate(Aggregation aggregation, String collectionName) {
        return mongoTemplate.aggregate(aggregation, generateCollectionName(collectionName), HistoryRecord.class);
    }

    public Collection<HistoryRecord> findTopRecords() {
        SortOperation sortByCount = sort(Sort.Direction.DESC, "date");
        LimitOperation limitToTop = limit(100);
        Aggregation aggregation = Aggregation.newAggregation(sortByCount, limitToTop);
        AggregationResults<HistoryRecord> results = aggregate(aggregation, RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION);
        return results.getMappedResults();
    }

    public Collection<HistoryRecordWithPreviousPosition> findTopRecordsPreviousPositions(Date date) {
        List<HistoryRecordWithPreviousPosition> recordWithPreviousPositions = new ArrayList<>();
        for (HistoryRecord historyRecord : findTopRecords()) {
            Query query = new Query(Criteria.where ("date").gt(date.getTime())
                    .and("name").is(historyRecord.getName()))
                    .with(new Sort(Sort.Direction.ASC, "date"));
            List<HistoryRecord> result = findByQuery(query, RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION);
            HistoryRecordWithPreviousPosition record = new HistoryRecordWithPreviousPosition(historyRecord, result.get(0).getPosition());
            recordWithPreviousPositions.add(record);
        }
        return recordWithPreviousPositions;
    }

    public Collection<HistoryRecord> findRecordsThatLeftTopFromDate(Date date) {
        Collection<HistoryRecord> topRecords = findTopRecords();
        Query query = new Query(Criteria.where(("date")).gt(date.getTime()))
                .with(new Sort(Sort.Direction.ASC,"date"));
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
