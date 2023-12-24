package edu.uoc.workoutwizardworkoutmanagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Map;
import java.util.UUID;

@Entity
public class WorkoutExercise {

    @Id
    private UUID id;

    @ElementCollection
    @Column
    private Map<Integer, Double> weights;

    public WorkoutExercise(UUID id, Map<Integer, Double> weights) {
        this.id = id;
        this.weights = weights;
    }

    public WorkoutExercise() {}

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Map<Integer, Double> getWeights() {
        return weights;
    }

    public void setWeights(Map<Integer, Double> weights) {
        this.weights = weights;
    }

    public static WorkoutExerciseBuilder builder() {
        return new WorkoutExerciseBuilder();
    }

    public static class WorkoutExerciseBuilder {
        private UUID id;
        private Map<Integer, Double> weights;

        WorkoutExerciseBuilder() {
        }

        public WorkoutExerciseBuilder id(UUID id) {
            this.id = id;
            return this;
        }

        public WorkoutExerciseBuilder weights(Map<Integer, Double> weights) {
            this.weights = weights;
            return this;
        }

        public WorkoutExercise build() {
            return new WorkoutExercise(id, weights);
        }

        public String toString() {
            return "WorkoutExercise.WorkoutExerciseBuilder(id=" + this.id + ", weights=" + this.weights + ")";
        }
    }

}
