package com.mosadie.islandmenu.mccapi;

import java.util.Date;

public class EventInfo {
    int code;
    EventData data;

    public EventData getData() {
        return data;
    }

    public static class EventData {
        Date date;
        String event;
        String updateVideo;

        public Date getDate() {
            return date;
        }

        public String getEvent() {
            return event;
        }

        public String getUpdateVideo() {
            return updateVideo;
        }
    }
}
