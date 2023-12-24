package edu.uoc.workoutwizardworkoutmanagement.controllers;

import com.example.workoutclient.dto.AddWorkoutRequest;
import com.example.workoutclient.dto.CreateWorkoutDiary;
import com.example.workoutclient.dto.WorkoutDiary;
import edu.uoc.workoutwizardworkoutmanagement.mappers.WorkoutMappers;
import edu.uoc.workoutwizardworkoutmanagement.service.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import uoc.edu.commons.JwtTokenUtil;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/workout")
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("diary/active")
    public WorkoutDiary getActiveDiary(@RequestHeader("Authorization") String jwtToken) {
        return WorkoutMappers
                .transform(workoutService.findWorkoutDiary(jwtTokenUtil.getUserIdFromToken(jwtToken))
                .orElseThrow());
    }

    @PostMapping
    public UUID addWorkout(@RequestBody AddWorkoutRequest workout, @RequestHeader("Authorization") String jwtToken) {
        return workoutService.addWorkout(WorkoutMappers.transform(workout), jwtToken);
    }

    @PostMapping("diary")
    public UUID addWorkout(@RequestBody CreateWorkoutDiary request, @RequestHeader("Authorization") String jwtToken) {
        final var userId = jwtTokenUtil.getUserIdFromToken(jwtToken);
        return workoutService.createWorkoutDiary(request.getRoutineId(), userId);
    }
}
