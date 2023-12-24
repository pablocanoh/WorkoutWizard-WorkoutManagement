package com.example.workoutclient.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static okhttp3.RequestBody.create;

public class InsightsClient {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    public InsightsClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public List<InsightsDataPoint> getInsights() {
        final var request = new Request.Builder()
                .url(baseUrl + "/insights")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            assert response.body() != null;
            return objectMapper.readValue(response.body().byteStream(), List.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
