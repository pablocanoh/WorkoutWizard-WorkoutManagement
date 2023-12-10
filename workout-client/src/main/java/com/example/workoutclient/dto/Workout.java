package com.example.workoutclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    private UUID id;
    private Integer workoutDayNumber;private Instant workoutDate;private List<WorkoutExercise> exercises;

}
