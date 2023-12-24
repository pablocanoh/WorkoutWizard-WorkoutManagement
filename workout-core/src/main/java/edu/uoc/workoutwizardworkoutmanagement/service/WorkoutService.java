package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import edu.uoc.workoutwizardworkoutmanagement.domain.Workout;
import edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary;
import edu.uoc.workoutwizardworkoutmanagement.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import uoc.edu.commons.JwtTokenUtil;

@Service
public class WorkoutService {

    @Autowired
    private RoutineClient routineClient;

    @Autowired
    private WorkoutRepository workoutDiaryRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public Optional<WorkoutDiary> findWorkoutDiary(UUID userId) {
        return workoutDiaryRepository.findTopByUserIdOrderByCreatedDateDesc(userId);
    }

    public UUID addWorkout(Workout workout, String jwtToken) {
        final var userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
        final var diary = findWorkoutDiary(userId).orElseThrow();
        final var routine = routineClient.getRoutine(diary.getRoutineId(), jwtToken);
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

    public UUID createWorkoutDiary(UUID routineId, UUID userId) {
        final var newDiary = WorkoutDiary.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .workouts(List.of())
                .createdDate(Instant.now())
                .routineId(routineId).build();

        return workoutDiaryRepository.save(newDiary).getRoutineId();
    }
}
