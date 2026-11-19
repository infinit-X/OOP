package edu.practice.cloud.model;

/**
 * Concrete Subclass 1: CpuJob
 * Demonstrates:
 * - Inheritance + super(...)
 * - Ceiling division into 15-minute billing blocks: (minutes + 14L) / 15L
 * - Safe long arithmetic + Math.toIntExact()
 */
public class CpuJob extends ComputeJob {
    private final int cores;
    private final boolean batch;

    public CpuJob(long id, String name, int durationMinutes, int cores, boolean batch) {
        super(id, name, durationMinutes);
        this.cores = cores;
        this.batch = batch;
    }

    public int getCores() {
        return cores;
    }

    public boolean isBatch() {
        return batch;
    }

    @Override
    public int computeUnits() {
        // Formula: 2 * cores * ceiling(durationMinutes / 15)
        long blocks = (getDurationMinutes() + 14L) / 15L;
        long units = 2L * cores * blocks;
        return Math.toIntExact(units);
    }

    @Override
    public int costCents() {
        // Formula: 50 cents per compute unit
        long cost = 50L * computeUnits();
        return Math.toIntExact(cost);
    }

    @Override
    public int priority() {
        // Batch jobs have lower priority (1); interactive CPU jobs have priority (3)
        return batch ? 1 : 3;
    }
}
