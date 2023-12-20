package edu.uoc.workoutwizardworkoutmanagement.integration;

import com.example.routineclient.RoutineClient;
import com.example.routineclient.dtos.Routine;
import com.example.routineclient.dtos.RoutineDay;
import com.example.workoutclient.dto.AddWorkoutRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.uoc.workoutwizardworkoutmanagement.repositories.WorkoutRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AddWorkoutRequestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WorkoutRepository workoutRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RoutineClient routineClient;

    private final UUID routineId = UUID.randomUUID();

    @Test
    public void testGetDiary() throws Exception {
        workoutRepository.deleteAll();
        when(routineClient.getRoutine(routineId))
                .thenReturn(new Routine(routineId, List.of(
                        new RoutineDay(UUID.randomUUID(), List.of()),
                        new RoutineDay(UUID.randomUUID(), List.of())
                )));
        var diaryId = initDiary();

        var workout = AddWorkoutRequest.builder()
                .exercises(List.of(WorkoutExercise.builder()
                        .weights(Map.of(1, 11.0, 2, 11.0))
                        .build()))
                .build();

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 1, 0);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 2, 1);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 3, 0);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 4, 1);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 5, 0);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 6, 1);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 7, 0);

        // add workout
        addWorkout(workout, diaryId);
        verifyWorkoutAdded(diaryId, 8, 1);
    }

    private void addWorkout(AddWorkoutRequest workout, UUID diaryId) throws Exception {
        mockMvc.perform(post("/workout")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(AddWorkoutRequest.builder()
                                .workout(workout)
                                .diaryId(diaryId)
                                .build())))
                .andExpect(status().isOk());
    }

    private UUID initDiary() {
        var diaryId = UUID.randomUUID();
        var mockDiary = edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary.builder()
                .id(diaryId)
                .routineId(routineId)
                .workouts(List.of())
                .build();

        // init diary
        return workoutRepository.save(mockDiary).getId();
    }

    private void verifyWorkoutAdded(UUID diaryId, int total, int dayNumber) {
        var workouts = workoutRepository.findById(diaryId).orElseThrow().getWorkouts();
        assertThat(workouts.size()).isEqualTo(total);

        var lastWorkout = workouts.get(total - 1);
        assertThat(lastWorkout.getWorkoutDayNumber()).isEqualTo(dayNumber);
    }
}
