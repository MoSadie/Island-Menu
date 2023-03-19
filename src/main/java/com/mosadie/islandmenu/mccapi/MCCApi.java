package com.mosadie.islandmenu.mccapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mosadie.islandmenu.client.IslandMenuClient;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class MCCApi {

    private final Gson gson;
    private String baseUrl;

    private final HttpClient httpClient;

    private EventInfo eventInfoCache;
    private ParticipantsInfo participantsInfoCache;

    public MCCApi() {
        this("https://api.mcchampionship.com");
        //this("https://a2fbee59-441a-4c76-8378-9669cc2646d6.mock.pstmn.io");
    }

    public MCCApi(String baseUrl) {
        gson = new GsonBuilder().setPrettyPrinting().create();
        this.baseUrl = baseUrl;

        httpClient = HttpClient.newBuilder().build();

        eventInfoCache = null;
        participantsInfoCache = null;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public EventInfo getEventInfo() {
        if (eventInfoCache != null) {
            IslandMenuClient.LOGGER.debug("Returned event info from cache.");
            return eventInfoCache;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/v1/event")).header("accept", "application/json").GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            EventInfo eventInfoResponse = gson.fromJson(response.body(), EventInfo.class);

            if (eventInfoResponse != null && eventInfoResponse.code == 200) {
                eventInfoCache = eventInfoResponse;
                IslandMenuClient.LOGGER.info("Cached Event info!");
                return eventInfoCache;
            }

            IslandMenuClient.LOGGER.warn("Failed to get event info! " + response.body());
            return null;
        } catch (IOException | InterruptedException | IllegalArgumentException | JsonSyntaxException e) {
            IslandMenuClient.LOGGER.warn("Failed to fetch event info! " + e.getMessage(), e);
            return null;
        }
    }

    public ParticipantsInfo getParticipantsInfo() {
        if (participantsInfoCache != null) {
            IslandMenuClient.LOGGER.debug("Returned participant info from cache.");
            return participantsInfoCache;
        }

        try {
            HttpRequest request = HttpRequest.newBuilder(URI.create(baseUrl + "/v1/participants")).header("accept", "application/json").GET().build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            ParticipantsInfo participantsInfoResponse = gson.fromJson(response.body(), ParticipantsInfo.class);

            if (participantsInfoResponse != null && participantsInfoResponse.code == 200) {
                participantsInfoCache = participantsInfoResponse;
                IslandMenuClient.LOGGER.info("Cached Participant info!");
                return participantsInfoCache;
            }

            IslandMenuClient.LOGGER.warn("Failed to get participants info! " + response.body());
            return null;
        } catch (IOException | InterruptedException | IllegalArgumentException | JsonSyntaxException e) {
            IslandMenuClient.LOGGER.warn("Failed to fetch participants info! " + e.getMessage(), e);
            return null;
        }
    }
}
