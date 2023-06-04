package com.mosadie.islandmenu.mccapi;

import com.mosadie.islandmenu.client.IslandMenuClient;

import java.util.UUID;

public class ParticipantsInfo {
    int code;
    ParticipantsData data;

    public ParticipantsData getData() {
        return data;
    }

    public Teams getPlayerTeam(String name) {
        for(Teams team : Teams.values()) {
            for (ParticipantsData.Participant participant : data.getTeam(team)) {
                if (participant.getUsername().equalsIgnoreCase(name))
                    return team;
            }
        }

        return Teams.NONE;
    }

    public Teams getPlayerTeam(UUID uuid) {
        if (uuid == null) {
            return Teams.NONE;
        }

        String uuidString = uuid.toString();
        for(Teams team : Teams.values()) {
            for (ParticipantsData.Participant participant : data.getTeam(team)) {
                if (uuid.equals(participant.getUuid()))
                    return team;
            }
        }

        return Teams.NONE;
    }

    public ParticipantsData.Participant getPlayer(String name) {
        for(Teams team : Teams.values()) {
            for (ParticipantsData.Participant participant : data.getTeam(team)) {
                if (name.equalsIgnoreCase(participant.getUsername())) {
                    return participant;
                }
            }
        }

        return null;
    }

    public ParticipantsData.Participant getPlayer(UUID uuid) {
        if (uuid == null) {
            return null;
        }

        for(Teams team : Teams.values()) {
            for (ParticipantsData.Participant participant : data.getTeam(team)) {
                if (uuid.equals(participant.getUuid())) {
                    return participant;
                }
            }
        }

        return null;
    }

    public static class ParticipantsData {
        Participant[] RED;
        Participant[] ORANGE;
        Participant[] YELLOW;
        Participant[] LIME;
        Participant[] GREEN;
        Participant[] CYAN;
        Participant[] AQUA;
        Participant[] BLUE;
        Participant[] PURPLE;
        Participant[] PINK;
        Participant[] SPECTATORS;
        Participant[] NONE;

        public Participant[] getTeam(Teams team) {
            switch (team) {
                case RED -> {
                    return RED;
                }

                case ORANGE -> {
                    return ORANGE;
                }

                case YELLOW -> {
                    return YELLOW;
                }

                case LIME -> {
                    return LIME;
                }

                case GREEN -> {
                    return GREEN;
                }

                case CYAN -> {
                    return CYAN;
                }

                case AQUA -> {
                    return AQUA;
                }

                case BLUE -> {
                    return BLUE;
                }

                case PURPLE -> {
                    return PURPLE;
                }

                case PINK -> {
                    return PINK;
                }

                case SPECTATORS -> {
                    return SPECTATORS;
                }

                case NONE -> {
                    return NONE;
                }

                default -> {
                    return new Participant[0];
                }
            }
        }

        public static class Participant {
            String username;
            String uuid;
            String stream;

            public String getUsername() {
                return username;
            }

            public UUID getUuid() {
                if (uuid != null)
                    return UUID.fromString(uuid.replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5"));
                else
                    return null;
            }

            public String getStream() {
                return stream;
            }
        }
    }
}
