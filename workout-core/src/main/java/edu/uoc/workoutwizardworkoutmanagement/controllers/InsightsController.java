package edu.uoc.workoutwizardworkoutmanagement.controllers;

import edu.uoc.workoutwizardworkoutmanagement.domain.InsightsDataPoint;
import edu.uoc.workoutwizardworkoutmanagement.service.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/insights")
public class InsightsController {

    @Autowired
    private InsightsService insightsService;

    @GetMapping
    public List<InsightsDataPoint> getInsights() {
        return insightsService.getWeeklyInsights();
    }
}
