package com.yeta.mongo.test;

import com.yeta.mongo.TopHistoryApplication;
import com.yeta.mongo.dataaccess.HistoryRecordMongoTemplate;
import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.parsers.RottenTomatoesParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TopHistoryApplication.class)
public class JsoupSaveDataTest {

    private static final String ROTTEN_TOMATOES_TOP100_COLLECTION = "rottentomatoes_top100";

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
    public void parse_RottenTomatoes_ReturnsHundredRecords() {
        RottenTomatoesParser parser = new RottenTomatoesParser();
        Collection<HistoryRecord> result = parser.parse(RottenTomatoesParser.ROTTEN_TOMATOES_FIRST_TOP100_LINK);
        Assert.assertEquals(result.size(), 100);
    }

    @Test
    public void save_Top100Records_successful() {
        RottenTomatoesParser parser = new RottenTomatoesParser();
        Collection<HistoryRecord> top100records = parser.parse(RottenTomatoesParser.ROTTEN_TOMATOES_FIRST_TOP100_LINK);
        template.insert(top100records, ROTTEN_TOMATOES_TOP100_COLLECTION);
    }
}


