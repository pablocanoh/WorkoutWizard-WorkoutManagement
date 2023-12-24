package edu.uoc.workoutwizardworkoutmanagement.service;

import com.example.routineclient.RoutineClient;
import com.example.workoutclient.dto.InsightsDataPoint;
import com.example.workoutclient.dto.Workout;
import edu.uoc.workoutwizardworkoutmanagement.mappers.WorkoutMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uoc.edu.commons.JwtTokenUtil;

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

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public List<InsightsDataPoint> getInsights(String jwtToken) {
        final var userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
        final var diary = workoutService.findWorkoutDiary(userId);

        if (diary.isEmpty()) {
            return List.of();
        }
        ;
        final var workouts = WorkoutMappers.transform(diary.get()).getWorkouts().stream()
                .sorted(Comparator.comparing(Workout::getWorkoutDate))
                .toList();

        final var batchSize = routineClient.getRoutine(diary.get().getRoutineId(), jwtToken).blocks().size();

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
