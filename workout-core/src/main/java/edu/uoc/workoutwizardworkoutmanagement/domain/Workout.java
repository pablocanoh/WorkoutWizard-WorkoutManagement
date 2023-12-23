package edu.uoc.workoutwizardworkoutmanagement.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Workout {

    @Id
    private UUID id;

    @Column(nullable = false)
    private Integer workoutDayNumber;

    @Column(nullable = false)
    private Instant workoutDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_day_id")
    private List<WorkoutExercise> exercises;

}
