package com.example.administrator.newsreader.database;


public interface databaseSettings {
    int TI_society = 0;
    int TI_domestic = 1;
    int TI_international = 2;
    int TI_entertain = 3;
    int TI_pe = 4;
    int TI_technology = 5;
    int TI_health = 6;
    int TI_collection = 7;

    int TI_SIZE = 8;

    String[] TABLE_NAMES = {
            "society",
            "domestic",
            "international",
            "entertain",
            "pe",
            "technology",
            "health",
            "collection"
    };

    String DATABASE_NAME = "sqlite.db";
    int version = 1;
}
