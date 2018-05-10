package com.yeta.mongo.domain;

public class HistoryRecordWithPreviousPosition {

    private HistoryRecord historyRecord;
    private int previousPosition;

    public HistoryRecordWithPreviousPosition(HistoryRecord historyRecord, int previousPosition) {
        this.historyRecord = historyRecord;
        this.previousPosition = previousPosition;
    }

    public HistoryRecord getHistoryRecord() {
        return historyRecord;
    }

    public int getPreviousPosition() {
        return previousPosition;
    }
}
