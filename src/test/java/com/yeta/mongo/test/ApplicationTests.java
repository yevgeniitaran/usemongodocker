package com.yeta.mongo.test;

import com.yeta.mongo.data.jpa.SampleDataRestApplication;
import com.yeta.mongo.data.jpa.controller.CollectionsConfiguration;
import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import com.yeta.mongo.data.jpa.service.HistoryRecordRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleDataRestApplication.class)
public class ApplicationTests {

    @Autowired
    private HistoryRecordRepository repo;

    @Autowired
    private MongoTemplate template;

    @Test
    public void contextLoads() {
        CollectionsConfiguration.setCollection("try_test");
        repo.deleteAll();
        List<HistoryRecord> historyRecords = repo.findByName("test");
        Assert.assertEquals(historyRecords.size(), 0);
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("test");
        repo.save(historyRecord);
        historyRecords = repo.findByName("test");
        Assert.assertEquals(historyRecords.size(), 1);
        repo.deleteAll();
    }

    @Test
    public void mongoTemplateTry() {

    }

}