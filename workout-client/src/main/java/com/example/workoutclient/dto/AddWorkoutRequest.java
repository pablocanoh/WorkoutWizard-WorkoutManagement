package com.example.workoutclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
@AllArgsConstructor
public class AddWorkoutRequest {

    private final UUID diaryId;
    private final Workout workout;
}
