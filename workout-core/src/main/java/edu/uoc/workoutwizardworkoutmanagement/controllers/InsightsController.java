package edu.uoc.workoutwizardworkoutmanagement.controllers;

import com.example.workoutclient.dto.InsightsDataPoint;
import edu.uoc.workoutwizardworkoutmanagement.service.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;

    @GetMapping
    public List<InsightsDataPoint> getInsights() {
        return insightsService.getInsights();
    }
}
