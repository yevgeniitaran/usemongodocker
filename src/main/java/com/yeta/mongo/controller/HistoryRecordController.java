package com.yeta.mongo.controller;

import com.yeta.mongo.domain.HistoryRecord;
import com.yeta.mongo.service.HistoryRecordRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;



@Component
@ConfigurationProperties
@RestController
public class HistoryRecordController {

    private static final Logger LOG = LogManager.getLogger(HistoryRecordController.class);

    private HistoryRecordRepository historyRecordRepository;

    @Autowired
    public void setHistoryRecordRepository(HistoryRecordRepository historyRecordRepository) {
        this.historyRecordRepository = historyRecordRepository;
    }

    @RequestMapping(value = "/historyrecord", method = RequestMethod.POST)
    public HistoryRecord create(@RequestBody HistoryRecord historyRecord) {
        return historyRecordRepository.save(historyRecord);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{historyRecordId}")
    public HistoryRecord get(@PathVariable String historyRecordId) {
        return historyRecordRepository.findOne(historyRecordId);
    }
}