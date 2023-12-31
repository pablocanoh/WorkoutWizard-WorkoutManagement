package com.example.workoutclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class InsightsDataPoint {
    private int groupNumber;
    private ArrayList<Workout> workouts;
    private double totalLoad;

    public static InsightsDataPoint emptyInsightsDataPoint() {
        return new InsightsDataPointBuilder().workouts(new ArrayList<>()).build();
    }

    public void addWorkout(Workout workout) {
        this.workouts.add(workout);
        this.totalLoad += workout.getExercises().stream()
                .flatMap(exercise -> exercise.getWeights().values().stream())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    public static class InsightsDataPointBuilder {
        private ArrayList<Workout> workouts;
        private double totalLoad;


        public InsightsDataPointBuilder workouts(ArrayList<Workout> workouts) {
            this.workouts = workouts;
            this.totalLoad = workouts.stream()
                    .flatMap(workout -> workout.getExercises().stream()
                            .flatMap(exercise -> exercise.getWeights().values().stream()))
                    .mapToDouble(Double::doubleValue)
                    .sum();

            return this;
        }
    }

}
