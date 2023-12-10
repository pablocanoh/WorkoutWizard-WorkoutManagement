package edu.uoc.workoutwizardworkoutmanagement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class WorkoutDiary {

    @Id
    private UUID id;

    @Column
    private UUID routineId;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_diary_id")
    public List<Workout> workouts;

    public Workout getLastWorkout() {
        return workouts.get(workouts.size() - 1);
    }
}