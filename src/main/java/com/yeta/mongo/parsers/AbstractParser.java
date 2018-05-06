package com.yeta.mongo.parsers;

import com.yeta.mongo.domain.HistoryRecord;

import java.util.Collection;

public interface AbstractParser {

    Collection<HistoryRecord> parse(String url);
}
