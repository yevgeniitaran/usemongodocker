package com.yeta.mongo.test;

import com.yeta.mongo.TopHistoryApplication;
import com.yeta.mongo.dataaccess.HistoryRecordMongoTemplate;
import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.parsers.RottenTomatoesParser;
import com.yeta.mongo.utils.DateHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static com.yeta.mongo.parsers.RottenTomatoesParser.ROTTEN_TOMATOES_TOP100_COLLECTION;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TopHistoryApplication.class)
public class JsoupSaveDataTest {

    private static Logger logger = LogManager.getLogger(JsoupSaveDataTest.class);

    private HistoryRecordMongoTemplate template;

    @Autowired
    public void setTemplate(HistoryRecordMongoTemplate template) {
        this.template = template;
    }

    @Test
    public void connect_Jsoup_Successful() {
        try {
            Document doc = Jsoup.connect("http://example.com").get();
            Assert.assertTrue(doc.select("p").size() > 0);
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void parse_RottenTomatoesParser_ReturnsHundredRecords() {
        RottenTomatoesParser parser = new RottenTomatoesParser();
        Collection<HistoryRecord> result = parser.parse(RottenTomatoesParser.ROTTEN_TOMATOES_FIRST_TOP100_LINK);
        Assert.assertEquals(result.size(), 100);
    }

    @Test
    public void insert_Top100Records_successful() {
        RottenTomatoesParser parser = new RottenTomatoesParser();
        Collection<HistoryRecord> top100records = parser.parse(RottenTomatoesParser.ROTTEN_TOMATOES_FIRST_TOP100_LINK);
        template.insert(top100records, ROTTEN_TOMATOES_TOP100_COLLECTION);
    }

    @Test
    public void findRecordsThatLeftTopFromDate_PossibleNoData_Successful() {
        Date date = DateHelper.getNewDateShiftingDay(new Date(), -5);
        Collection<HistoryRecord> leftTopRecords = template.findRecordsThatLeftTopFromDate(date);
        logger.info("leftTopRecords: {}", leftTopRecords);
    }
}


