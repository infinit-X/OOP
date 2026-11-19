package edu.practice.cloud.model;

/**
 * WEEK 4 & 5 CONCEPT: Abstract Parent Class ("What a class IS").
 * Holds shared fields, a protected constructor for super(...),
 * and abstract methods that every concrete subclass MUST override.
 * Also implements the Billable interface contract.
 */
public abstract class ComputeJob implements Billable {
    private final long id;
    private final String name;
    private final int durationMinutes;

    protected ComputeJob(long id, String name, int durationMinutes) {
        this.id = id;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    // Abstract methods: every subclass must provide its own formula
    public abstract int computeUnits();

    public abstract int priority();
}
