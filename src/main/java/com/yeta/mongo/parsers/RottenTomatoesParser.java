package com.yeta.mongo.parsers;

import com.yeta.mongo.domain.HistoryRecord;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class RottenTomatoesParser implements AbstractParser {

    public static final String ROTTEN_TOMATOES_TOP100_COLLECTION = "rottentomatoes_top100";
    public static final String ROTTEN_TOMATOES_LINK = "https://www.rottentomatoes.com/";
    public static final String ROTTEN_TOMATOES_FIRST_TOP100_LINK = "https://www.rottentomatoes.com/top/bestofrt/";

    @Override
    public Collection<HistoryRecord> parse(String url) {
        try {
            Document rottenTomatoesTop100page = Jsoup.connect(ROTTEN_TOMATOES_FIRST_TOP100_LINK).get();
            Elements top100rows = rottenTomatoesTop100page.
                    select("div.panel-body.content_body.allow-overflow>table>tbody>tr>td:not([class])>a");
            List<HistoryRecord> historyRecords = new ArrayList<>();
            Date date = new Date();
            for (int i = 0; i < top100rows.size(); i++) {
                HistoryRecord historyRecord = new HistoryRecord();
                historyRecord.setPosition(i + 1);
                historyRecord.setDate(date);

                Element a = top100rows.get(i);
                historyRecord.setName(a.text());
                historyRecord.setUrl(ROTTEN_TOMATOES_LINK + a.attr("href"));
                historyRecords.add(historyRecord);
            }
            return historyRecords;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
