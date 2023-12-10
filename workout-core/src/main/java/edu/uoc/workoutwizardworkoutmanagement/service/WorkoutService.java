package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import edu.uoc.workoutwizardworkoutmanagement.domain.Workout;
import edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary;
import edu.uoc.workoutwizardworkoutmanagement.repositories.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WorkoutService {

    @Autowired
    private RoutineClient routineClient;

    @Autowired
    private WorkoutRepository workoutDiaryRepository;

    public WorkoutDiary getWorkoutDiary(UUID id) {
        return workoutDiaryRepository.findById(id).orElseThrow();
    }

    public UUID addWorkout(Workout workout, UUID diaryId) {
        final var diary = getWorkoutDiary(diaryId);
        final var routine = routineClient.getRoutine(diary.getRoutineId());
        final var frequency = routine.getDays();
        final var dayNumber = diary.getWorkouts().size() % frequency;
        final var workoutDay = workout.toBuilder()
                .workoutDayNumber(dayNumber)
                .build();

        diary.getWorkouts().add(workoutDay);

        return workoutDiaryRepository.save(diary).getRoutineId();
    }

    public WorkoutDiary getAllWorkouts() {
        return workoutDiaryRepository.findAll().get(0);
    }
}
