package edu.uoc.workoutwizardworkoutmanagement.mappers;

import com.example.workoutclient.dto.Workout;
import com.example.workoutclient.dto.WorkoutExercise;

import java.util.UUID;

public class WorkoutMappers {
    private WorkoutMappers() {}

    public static edu.uoc.workoutwizardworkoutmanagement.domain.Workout transform(Workout workout) {
        return edu.uoc.workoutwizardworkoutmanagement.domain.Workout.builder()
                .id(workout.getId() == null ? UUID.randomUUID() : workout.getId())
                .workoutDayNumber(workout.getWorkoutDayNumber())
                .exercises(workout.getExercises().stream()
                        .map(WorkoutMappers::transform)
                        .toList())
                .build();
    }

    public static edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutExercise transform(WorkoutExercise workoutExercise) {
        return edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutExercise.builder()
                .id(workoutExercise.getId() == null ? UUID.randomUUID() : workoutExercise.getId())
                .weights(workoutExercise.getWeights())
                .build();
    }
}
