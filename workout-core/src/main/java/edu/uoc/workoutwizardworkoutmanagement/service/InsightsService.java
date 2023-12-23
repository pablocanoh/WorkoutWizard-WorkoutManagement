package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import edu.uoc.workoutwizardworkoutmanagement.domain.InsightsDataPoint;
import edu.uoc.workoutwizardworkoutmanagement.domain.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static edu.uoc.workoutwizardworkoutmanagement.domain.InsightsDataPoint.emptyInsightsDataPoint;

@Service
public class InsightsService {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private RoutineClient routineClient;

    public List<InsightsDataPoint> getWeeklyInsights() {
        final var diary = workoutService.getWorkoutDiary();
        final var workouts = diary.getWorkouts().stream()
                .sorted(Comparator.comparing(Workout::getWorkoutDate))
                .toList();

        final var batchSize = routineClient.getRoutine(diary.getRoutineId()).getDays();

        return groupWorkouts(workouts, batchSize);
    }

    private List<InsightsDataPoint> groupWorkouts(List<Workout> workouts, int batchSize) {
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger groupNumber = new AtomicInteger();

        Collection<List<Workout>> grouped = workouts.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / batchSize))
                .values();

        return grouped.stream()
                .map(it -> InsightsDataPoint.builder()
                        .workouts(new ArrayList<>(it))
                        .groupNumber(groupNumber.getAndIncrement())
                        .build())
                .collect(Collectors.toList());
    }
}
