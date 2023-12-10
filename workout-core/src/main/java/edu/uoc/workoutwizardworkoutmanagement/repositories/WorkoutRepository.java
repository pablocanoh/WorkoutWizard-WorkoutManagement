package edu.uoc.workoutwizardworkoutmanagement.repositories;

import edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WorkoutRepository extends JpaRepository<WorkoutDiary, UUID> {
}
