package com.yeta.mongo.test;

import com.mongodb.MongoClient;
import com.yeta.mongo.data.jpa.SampleDataRestApplication;
import com.yeta.mongo.data.jpa.controller.CollectionsConfiguration;
import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import com.yeta.mongo.data.jpa.service.HistoryRecordRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.UnknownHostException;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleDataRestApplication.class)
public class ApplicationTests {

    private HistoryRecordRepository repo;
    private MongoTemplate template;

    @Autowired
    public void setRepo(HistoryRecordRepository repo) {
        this.repo = repo;
    }

    @Autowired
    public void setTemplate(MongoTemplate template) {
        this.template = template;
    }

    @Test
    public void contextLoads() {
        CollectionsConfiguration.setCollection("try_test");
        List<HistoryRecord> historyRecords = repo.findByName("test");
        Assert.assertEquals(historyRecords.size(), 0);
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("test");
        repo.save(historyRecord);
        historyRecords = repo.findByName("test");
        Assert.assertEquals(historyRecords.size(), 1);
        repo.delete(historyRecords);
    }

    @Test
    public void mongoTemplateTry() {
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("1234");
        template.insert(historyRecord, "testmymy");
    }

}