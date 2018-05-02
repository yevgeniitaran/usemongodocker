package com.yeta.mongo.data.jpa.controller;

public class CollectionsConfiguration {

    private static String collection = "";

    public static String getCollection() {
        return collection;
    }

    public static void setCollection(String collection) {
        CollectionsConfiguration.collection = collection;
    }
}