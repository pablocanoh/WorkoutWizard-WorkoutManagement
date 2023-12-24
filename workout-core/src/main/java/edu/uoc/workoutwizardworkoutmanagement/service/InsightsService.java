package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import com.example.workoutclient.dto.InsightsDataPoint;
import com.example.workoutclient.dto.Workout;
import edu.uoc.workoutwizardworkoutmanagement.mappers.WorkoutMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class InsightsService {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private RoutineClient routineClient;

    public List<InsightsDataPoint> getInsights() {
        final var diary = WorkoutMappers.transform(workoutService.getWorkoutDiary());
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
