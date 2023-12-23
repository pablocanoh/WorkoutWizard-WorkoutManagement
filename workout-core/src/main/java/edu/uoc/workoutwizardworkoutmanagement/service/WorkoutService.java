package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import edu.uoc.workoutwizardworkoutmanagement.domain.Workout;
import edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary;
import edu.uoc.workoutwizardworkoutmanagement.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class WorkoutService {

    @Autowired
    private RoutineClient routineClient;

    @Autowired
    private WorkoutRepository workoutDiaryRepository;

    public WorkoutDiary getWorkoutDiary() {
        return workoutDiaryRepository.findAll().stream().findFirst().orElseThrow();
    }

    public UUID addWorkout(Workout workout) {
        final var diary = getWorkoutDiary();
        final var routine = routineClient.getRoutine(diary.getRoutineId());
        final var frequency = routine.blocks().size();
        final var dayNumber = diary.getWorkouts().size() % frequency;
        final var workoutDay = workout.toBuilder()
                .workoutDayNumber(dayNumber)
                .workoutDate(Instant.now())
                .exercises(workout.getExercises())
                .build();

        diary.getWorkouts().add(workoutDay);

        return workoutDiaryRepository.save(diary).getRoutineId();
    }

    public WorkoutDiary getAllWorkouts() {
        return workoutDiaryRepository.findAll().stream().findFirst().orElseThrow();
    }

    public UUID createWorkoutDiary(UUID routineId) {
        final var newDiary = WorkoutDiary.builder().id(UUID.randomUUID()).workouts(List.of()).routineId(routineId).build();

        return workoutDiaryRepository.save(newDiary).getRoutineId();
    }
}
