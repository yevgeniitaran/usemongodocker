package com.yeta.mongo.data.jpa.controller;

import com.yeta.mongo.data.jpa.domain.HistoryRecord;
import com.yeta.mongo.data.jpa.service.HistoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@ConfigurationProperties
@RestController
public class HistoryRecordController {


    @Value("${application.message}")
    String message;

    @Value("${application.appname}")
    String appname;

    private HistoryRecordRepository historyRecordRepository;

    @Autowired
    public void setHistoryRecordRepository(HistoryRecordRepository historyRecordRepository) {
        this.historyRecordRepository = historyRecordRepository;
    }

    @RequestMapping("/")
    String home() {
        return "Hello World....- " + message + " " + appname;
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