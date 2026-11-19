package edu.practice.cloud.service;

import edu.practice.cloud.exception.JobNotFoundException;
import edu.practice.cloud.model.ComputeJob;
import edu.practice.cloud.model.GpuJob;
import edu.practice.cloud.model.NewGpuJob;
import edu.practice.cloud.model.ScheduleLine;
import edu.practice.cloud.model.SchedulePlan;
import edu.practice.cloud.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * WEEK 3, 5, 6, 7 COMBINED:
 * - Constructor Injection of the JobRepository INTERFACE (private final)
 * - Optional handling (.orElseThrow)
 * - Strict input validation (null, positive <= 0, non-blank strings, duplicate ID, overflow pre-check)
 * - Polymorphic multi-level sorting + Greedy capacity selection
 */
@Service
public class SchedulerService {
    private final JobRepository repo;

    public SchedulerService(JobRepository repo) {
        this.repo = repo;
    }

    public Collection<ComputeJob> all() {
        return repo.findAll();
    }

    public int costCents(long id) {
        // Using Optional.orElseThrow (equivalent to checking if (job == null) throw ...)
        ComputeJob job = repo.findById(id)
                .orElseThrow(() -> new JobNotFoundException(id));
        return job.costCents();
    }

    public ComputeJob addGpuJob(NewGpuJob request) {
        if (request == null) {
            throw new IllegalArgumentException("Request body is required.");
        }
        if (request.getId() <= 0 || request.getId() > 10000) {
            throw new IllegalArgumentException("ID must be between 1 and 10000.");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new IllegalArgumentException("Job name must not be blank.");
        }
        if (request.getDurationMinutes() <= 0 || request.getDurationMinutes() > 10000) {
            throw new IllegalArgumentException("durationMinutes must be between 1 and 10000.");
        }
        if (request.getGpuCount() <= 0 || request.getGpuCount() > 64) {
            throw new IllegalArgumentException("gpuCount must be between 1 and 64.");
        }
        if (repo.existsById(request.getId())) {
            throw new IllegalArgumentException("Job ID already exists: " + request.getId());
        }

        GpuJob job = new GpuJob(
                request.getId(),
                request.getName().trim(),
                request.getDurationMinutes(),
                request.getGpuCount(),
                request.isUrgent()
        );

        // Pre-validate calculations before persisting to catch any arithmetic overflow
        job.computeUnits();
        job.costCents();

        repo.save(job);
        return job;
    }

    public void deleteJob(long id) {
        if (!repo.deleteById(id)) {
            throw new JobNotFoundException(id);
        }
    }

    public SchedulePlan schedule(int capacityUnits, boolean urgentOnly) {
        if (capacityUnits <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        // 1. Filter eligible jobs first (filtered-out jobs appear in neither selected nor skipped)
        List<ComputeJob> eligible = new ArrayList<>();
        for (ComputeJob job : repo.findAll()) {
            if (!urgentOnly || job.priority() >= 3) {
                eligible.add(job);
            }
        }

        // 2. Multi-level sort: Priority DESC, ComputeUnits ASC, CostCents ASC, ID ASC
        eligible.sort(this::compareJobs);

        // 3. Greedy capacity selection (continue checking later jobs after a skip!)
        List<ScheduleLine> selected = new ArrayList<>();
        List<Long> skipped = new ArrayList<>();
        int unitsUsed = 0;
        long totalCost = 0;

        for (ComputeJob job : eligible) {
            int units = job.computeUnits();
            int cost = job.costCents();

            if (units <= capacityUnits - unitsUsed) {
                selected.add(new ScheduleLine(
                        job.getId(),
                        job.getName(),
                        job.getClass().getSimpleName(),
                        job.priority(),
                        units,
                        cost
                ));
                unitsUsed += units;
                totalCost += cost;
            } else {
                skipped.add(job.getId());
            }
        }

        int unitsRemaining = capacityUnits - unitsUsed;
        return new SchedulePlan(
                capacityUnits,
                unitsUsed,
                unitsRemaining,
                Math.toIntExact(totalCost),
                selected,
                skipped
        );
    }

    // Helper Comparator: DESC = (second, first), ASC = (first, second)
    private int compareJobs(ComputeJob first, ComputeJob second) {
        int result = Integer.compare(second.priority(), first.priority()); // Priority DESC
        if (result != 0) {
            return result;
        }
        result = Integer.compare(first.computeUnits(), second.computeUnits()); // Units ASC
        if (result != 0) {
            return result;
        }
        result = Integer.compare(first.costCents(), second.costCents()); // Cost ASC
        if (result != 0) {
            return result;
        }
        return Long.compare(first.getId(), second.getId()); // ID ASC
    }
}
