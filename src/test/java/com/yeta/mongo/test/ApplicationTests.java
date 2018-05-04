package com.yeta.mongo.test;

import com.yeta.mongo.data.jpa.SampleDataRestApplication;
import com.yeta.mongo.data.jpa.dao.HistoryRecordMongoTemplate;
import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SampleDataRestApplication.class)
public class ApplicationTests {

    private static final String TEST_COLLECTION = "test";

    private HistoryRecordMongoTemplate template;

    @Autowired
    public void setTemplate(HistoryRecordMongoTemplate template) {
        this.template = template;
    }

    @Test
    public void mongoTemplateTry() {
        HistoryRecord historyRecord = new HistoryRecord();
        historyRecord.setName("Test Record");
        template.insert(historyRecord, TEST_COLLECTION);
        historyRecord = template.findById(historyRecord.getId(), TEST_COLLECTION);
        Assert.assertNotNull(historyRecord);
        template.remove(historyRecord, TEST_COLLECTION);
    }

}