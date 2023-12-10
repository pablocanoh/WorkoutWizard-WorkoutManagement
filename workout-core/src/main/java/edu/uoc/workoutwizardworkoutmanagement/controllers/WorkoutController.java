package edu.uoc.workoutwizardworkoutmanagement.controllers;

import com.example.workoutclient.dto.AddWorkoutRequest;
import edu.uoc.workoutwizardworkoutmanagement.domain.WorkoutDiary;
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

    @GetMapping("diary/{id}")
    public WorkoutDiary getDiary() {
        return workoutService.getAllWorkouts();
    }

    @PostMapping
    public UUID addWorkout(@RequestBody AddWorkoutRequest request) {
        return workoutService.addWorkout(WorkoutMappers.transform(request.getWorkout()), request.getDiaryId());
    }
}