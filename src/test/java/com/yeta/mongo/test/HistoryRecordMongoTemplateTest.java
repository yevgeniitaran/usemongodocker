package com.yeta.mongo.test;

import com.yeta.mongo.TopHistoryApplication;
import com.yeta.mongo.dataaccess.HistoryRecordMongoTemplate;
import com.yeta.mongo.dataaccess.HistoryRecordTemplateFactory;
import com.yeta.mongo.domain.HistoryRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TopHistoryApplication.class)
public class HistoryRecordMongoTemplateTest {

    private static final String TEST_COLLECTION = "test";

    private MongoOperations mongoOperations;

    @Autowired
    public void setHistoryRecordMongoTemplate(MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    @Test
    public void insertDelete_HistoryRecord_Complete() {
        HistoryRecordMongoTemplate template = HistoryRecordTemplateFactory.newInstance(mongoOperations, TEST_COLLECTION);
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("Test Record");
        historyRecord.setPosition(1);
        historyRecord.setPreviousPosition(2);
        template.insert(historyRecord, TEST_COLLECTION);
        historyRecord = template.findById(historyRecord.getId(), TEST_COLLECTION);
        Assert.assertNotNull(historyRecord);
        template.remove(historyRecord, TEST_COLLECTION);
    }

    @Test
    public void insertFind_DateParam_Complete() {
        HistoryRecordMongoTemplate template = HistoryRecordTemplateFactory.newInstance(mongoOperations, TEST_COLLECTION);
        Date date = new Date();
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("Test Date param");
        historyRecord.setDate(date);
        template.insert(historyRecord, TEST_COLLECTION);
        Query query = new Query(Criteria.where("date").is(date));
        List<HistoryRecord> records = template.findByQuery(query, TEST_COLLECTION);
        Assert.assertEquals(records.size(), 1);
        template.remove(records.get(0), TEST_COLLECTION);
        records = template.findByQuery(query, TEST_COLLECTION);
        Assert.assertEquals(records.size(), 0);
    }
}