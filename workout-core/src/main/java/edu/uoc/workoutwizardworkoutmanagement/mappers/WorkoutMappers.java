package edu.uoc.workoutwizardworkoutmanagement.mappers;

import com.example.workoutclient.dto.AddWorkoutRequest;

import java.util.UUID;

import static edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutExercise.*;

public class WorkoutMappers {
    private WorkoutMappers() {}

    public static edu.uoc.workoutwizardworkoutmanagement.domain.Workout transform(AddWorkoutRequest workout) {
        return edu.uoc.workoutwizardworkoutmanagement.domain.Workout.builder()
                .id(UUID.randomUUID())
                .workoutDayNumber(workout.getWorkoutDayNumber())
                .exercises(workout.getExercises()
                        .values()
                        .stream()
                        .map(integerDoubleMap ->  builder()
                                .id(UUID.randomUUID())
                                .weights(integerDoubleMap)
                                .build()).toList())
                .build();
    }
}
