package edu.practice.dispatch.controller;

import edu.practice.dispatch.service.*;
import edu.practice.dispatch.model.*;

import java.util.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

@RestController
@RequestMapping("/missions")
public class DispatchController {
    private final DispatchService service;

    public DispatchController(DispatchService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<DispatchMission> all() {
        return service.all();
    }
    // TODO Task 4: GET /{id}/energy; POST /air (HTTP 201);
    // GET /plan?capacity=...&urgentOnly=false; delegate all logic to service.

    @GetMapping("/{id}/energy")
    public Map<String, Integer> energy(@PathVariable("id") long id) {
        return Map.of("energyUnits", service.energy(id));
    }

    @PostMapping("/air")
    @ResponseStatus(HttpStatus.CREATED)
    public DispatchMission addAir(@RequestBody NewAirMission request) {
        return service.addAir(request);
    }

    @GetMapping("/plan")
    public DispatchPlan plan(@RequestParam("capacity") int capacity,
                             @RequestParam(name = "urgentOnly", defaultValue = "false") boolean urgentOnly) {
        return service.plan(capacity, urgentOnly);
    }
}
