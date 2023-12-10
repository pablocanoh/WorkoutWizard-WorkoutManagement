package edu.uoc.workoutwizardworkoutmanagement.domain;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutExercise {

    @Id
    private UUID id;

    @ElementCollection
    @Column
    private Map<Integer, Double> weights;
}
