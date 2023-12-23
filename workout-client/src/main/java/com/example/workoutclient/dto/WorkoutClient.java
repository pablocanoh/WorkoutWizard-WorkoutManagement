package com.example.workoutclient.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.UUID;

import static okhttp3.RequestBody.create;

public class WorkoutClient {

    private final OkHttpClient client = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final String baseUrl;

    public WorkoutClient(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public UUID createWorkoutDiary(UUID routineId) {
        final Request request;
        try {
            request = new Request.Builder()
                    .url(baseUrl + "/workout/diary")
                    .method("POST", create(
                            okhttp3.MediaType.parse("application/json"),
                            objectMapper.writeValueAsString(CreateWorkoutDiary.builder()
                                    .routineId(routineId)
                                    .build())
                    ))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            assert response.body() != null;
            return objectMapper.readValue(response.body().byteStream(), UUID.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
