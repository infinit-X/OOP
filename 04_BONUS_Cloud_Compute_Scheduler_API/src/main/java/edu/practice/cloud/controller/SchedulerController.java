package edu.practice.cloud.controller;

import edu.practice.cloud.model.ComputeJob;
import edu.practice.cloud.model.NewGpuJob;
import edu.practice.cloud.model.SchedulePlan;
import edu.practice.cloud.service.SchedulerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * WEEK 2, 3, 6, 7 CONCEPT: Thin REST Controller.
 * Only speaks HTTP (URLs, status codes, path/query/body binding).
 * Delegates 100% of business logic to SchedulerService.
 */
@RestController
@RequestMapping("/api/jobs")
public class SchedulerController {
    private final SchedulerService service;

    public SchedulerController(SchedulerService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<ComputeJob> all() {
        return service.all();
    }

    @GetMapping("/{id}/cost")
    public Map<String, Integer> cost(@PathVariable("id") long id) {
        return Map.of("costCents", service.costCents(id));
    }

    @PostMapping("/gpu")
    @ResponseStatus(HttpStatus.CREATED)
    public ComputeJob addGpu(@RequestBody NewGpuJob request) {
        return service.addGpuJob(request);
    }

    @GetMapping("/schedule")
    public SchedulePlan schedule(
            @RequestParam("capacity") int capacity,
            @RequestParam(name = "urgentOnly", defaultValue = "false") boolean urgentOnly) {
        return service.schedule(capacity, urgentOnly);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") long id) {
        service.deleteJob(id);
    }
}
