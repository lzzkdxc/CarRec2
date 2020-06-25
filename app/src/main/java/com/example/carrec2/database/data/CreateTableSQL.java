package com.example.carrec2.database.data;

import static com.example.carrec2.database.db.CarDatabase.FAKE_KEY_COLOR;
import static com.example.carrec2.database.db.CarDatabase.FAKE_KEY_ID;
import static com.example.carrec2.database.db.CarDatabase.FAKE_KEY_LOGO;
import static com.example.carrec2.database.db.CarDatabase.FAKE_KEY_PLATE;
import static com.example.carrec2.database.db.CarDatabase.FAKE_KEY_TYPE;
import static com.example.carrec2.database.db.CarDatabase.FLOW_KEY_ID;
import static com.example.carrec2.database.db.CarDatabase.FLOW_KEY_LOCATION;
import static com.example.carrec2.database.db.CarDatabase.FLOW_KEY_PLATE;
import static com.example.carrec2.database.db.CarDatabase.FLOW_KEY_RECORDTIME;

public class CreateTableSQL {
    public static String table_flow_sql = String.format("create table if not exists flow(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s datetime, %s text, %s text)",
            FLOW_KEY_ID, FLOW_KEY_RECORDTIME, FLOW_KEY_LOCATION, FLOW_KEY_PLATE);


    public static String table_fake_sql = String.format("create table if not exists fake(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s text, %s text, %s text, %s text)",
            FAKE_KEY_ID, FAKE_KEY_TYPE, FAKE_KEY_COLOR, FAKE_KEY_PLATE, FAKE_KEY_LOGO);
}
