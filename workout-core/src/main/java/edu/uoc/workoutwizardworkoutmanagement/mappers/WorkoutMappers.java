package edu.uoc.workoutwizardworkoutmanagement.mappers;

import com.example.workoutclient.dto.AddWorkoutRequest;
import com.example.workoutclient.dto.WorkoutDiary;
import com.example.workoutclient.dto.Workout;
import com.example.workoutclient.dto.WorkoutExercise;

import java.util.UUID;

import static edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutExercise.builder;
import static java.util.stream.Collectors.toList;

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

    public static WorkoutDiary transform(edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary workoutDiary) {
        return WorkoutDiary.builder()
                .id(workoutDiary.getId())
                .routineId(workoutDiary.getRoutineId())
                .createdDate(workoutDiary.getCreatedDate())
                .workouts(workoutDiary.getWorkouts().stream().map(WorkoutMappers::transform).collect(toList()))
                .build();
    }

    public static Workout transform(edu.uoc.workoutwizardworkoutmanagement.domain.Workout workout) {
        return Workout.builder()
                .id(workout.getId())
                .workoutDate(workout.getWorkoutDate())
                .workoutDayNumber(workout.getWorkoutDayNumber())
                .exercises(workout.getExercises().stream().map(WorkoutMappers::transform).collect(toList()))
                .build();
    }

    public static WorkoutExercise transform(edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutExercise workoutExercise) {
        return WorkoutExercise.builder()
                .id(workoutExercise.getId())
                .weights(workoutExercise.getWeights())
                .build();
    }
}
