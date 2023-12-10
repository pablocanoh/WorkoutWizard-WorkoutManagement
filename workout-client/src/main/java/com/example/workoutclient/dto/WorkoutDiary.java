package com.example.workoutclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDiary {

    private UUID id;
    private UUID routineId;
    public List<Workout> workouts;
}
