package com.yeta.mongo.controller;

import com.yeta.mongo.dataaccess.HistoryRecordMongoTemplate;
import com.yeta.mongo.dataaccess.HistoryRecordTemplateFactory;
import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.utils.DateHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Date;

@Component
@ConfigurationProperties
@RestController
public class HistoryRecordController {

    private static final Logger LOG = LogManager.getLogger(HistoryRecordController.class);

    private MongoOperations mongoOperations;

    @Autowired
    public void setHistoryRecordMongoTemplate(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @RequestMapping(
            value = "/api/historyrecords/{collectionName}",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<HistoryRecord>> getHistoryRecords(@PathVariable("collectionName") String collectionName) {
        HistoryRecordMongoTemplate historyRecordMongoTemplate = HistoryRecordTemplateFactory.newInstance(mongoOperations, collectionName);
        Collection<HistoryRecord> historyRecords = historyRecordMongoTemplate.findAll(collectionName);
        return new ResponseEntity<>(historyRecords, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/api/historyrecords/bydate/{collectionName}",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<HistoryRecord>> getHistoryRecordsByDate(
            @PathVariable("collectionName") String collectionName, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        HistoryRecordMongoTemplate historyRecordMongoTemplate = HistoryRecordTemplateFactory.newInstance(mongoOperations, collectionName);
        LOG.debug("Searching for history records on {}", date);
        Date endDate = DateHelper.getNewDateShiftingDay(date, 1);
        Query query = new Query(Criteria.where("date").gte(date).lt(endDate));
        Collection<HistoryRecord> historyRecords = historyRecordMongoTemplate.findByQuery(query, collectionName);
        return new ResponseEntity<>(historyRecords, HttpStatus.OK);
    }

    @RequestMapping(
            value="/api/historyrecords/topfromdate/{collectionName}",
            method = RequestMethod.GET)
    public ResponseEntity<Collection<HistoryRecord>> getTopChangesFromDate(
            @PathVariable("collectionName") String collectionName, @RequestParam("date") @DateTimeFormat(pattern="yyyy-MM-dd") Date date) {
        HistoryRecordMongoTemplate historyRecordMongoTemplate = HistoryRecordTemplateFactory.newInstance(mongoOperations, collectionName);
        Collection<HistoryRecord> result = historyRecordMongoTemplate.findTopRecordsPreviousPositions(date);
        result.addAll(historyRecordMongoTemplate.findRecordsThatLeftTopFromDate(date));
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}