package edu.practice.cloud.repository;

import edu.practice.cloud.model.ComputeJob;

import java.util.Collection;
import java.util.Optional;

/**
 * WEEK 7 CONCEPT: Repository Interface Contract.
 * SchedulerService depends ONLY on this interface (Loose Coupling),
 * so an in-memory map can later be swapped for a SQL database without touching the Service.
 */
public interface JobRepository {
    Collection<ComputeJob> findAll();

    Optional<ComputeJob> findById(long id);

    boolean existsById(long id);

    void save(ComputeJob job);

    boolean deleteById(long id);
}
