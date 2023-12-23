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
public class WorkoutDiary {

    @Id
    private UUID id;

    @Column
    private UUID routineId;

    @Column(nullable = false)
    private Instant createdDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "workout_diary_id")
    public List<Workout> workouts;
}
