package edu.uoc.workoutwizardworkoutmanagement.controllers;

import com.example.workoutclient.dto.AddWorkoutRequest;
import com.example.workoutclient.dto.CreateWorkoutDiary;
import com.example.workoutclient.dto.WorkoutDiary;
import edu.uoc.workoutwizardworkoutmanagement.mappers.WorkoutMappers;
import edu.uoc.workoutwizardworkoutmanagement.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("diary/active")
    public WorkoutDiary getActiveDiary() {
        return WorkoutMappers.transform(workoutService.getWorkoutDiary());
    }

    @PostMapping
    public UUID addWorkout(@RequestBody AddWorkoutRequest workout) {
        return workoutService.addWorkout(WorkoutMappers.transform(workout));
    }

    @PostMapping("diary")
    public UUID addWorkout(@RequestBody CreateWorkoutDiary request) {
        return workoutService.createWorkoutDiary(request.getRoutineId());
    }
}
