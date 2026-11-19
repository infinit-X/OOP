package edu.practice.cloud.repository;

import edu.practice.cloud.model.ComputeJob;
import edu.practice.cloud.model.CpuJob;
import edu.practice.cloud.model.GpuJob;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WEEK 7 CONCEPT: Concrete @Repository implementation backed by ConcurrentHashMap.
 * Spring creates ONE instance of this bean and injects it anywhere JobRepository is requested.
 */
@Repository
public class InMemoryJobRepository implements JobRepository {
    private final Map<Long, ComputeJob> store = new ConcurrentHashMap<>();

    public InMemoryJobRepository() {
        // Seed sample jobs
        store.put(101L, new CpuJob(101, "Nightly-Backup", 45, 4, true));     // priority 1, units 24, cost 1200
        store.put(102L, new CpuJob(102, "API-Analytics", 30, 6, false));     // priority 3, units 24, cost 1200
        store.put(103L, new GpuJob(103, "AI-Inference", 75, 2, true));       // priority 5, units 95, cost 1400
    }

    @Override
    public Collection<ComputeJob> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public Optional<ComputeJob> findById(long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public boolean existsById(long id) {
        return store.containsKey(id);
    }

    @Override
    public void save(ComputeJob job) {
        store.put(job.getId(), job);
    }

    @Override
    public boolean deleteById(long id) {
        return store.remove(id) != null;
    }
}
