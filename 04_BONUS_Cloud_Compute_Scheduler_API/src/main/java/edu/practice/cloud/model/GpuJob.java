package edu.practice.cloud.model;

/**
 * Concrete Subclass 2: GpuJob
 * Demonstrates:
 * - Tiered formula (first 60 minutes flat, extra hours billed with ceiling division)
 * - Safe long arithmetic + Math.toIntExact()
 */
public class GpuJob extends ComputeJob {
    private final int gpuCount;
    private final boolean urgent;

    public GpuJob(long id, String name, int durationMinutes, int gpuCount, boolean urgent) {
        super(id, name, durationMinutes);
        this.gpuCount = gpuCount;
        this.urgent = urgent;
    }

    public int getGpuCount() {
        return gpuCount;
    }

    public boolean isUrgent() {
        return urgent;
    }

    @Override
    public int computeUnits() {
        // Formula: 10 * gpuCount + 1 * durationMinutes
        long units = 10L * gpuCount + getDurationMinutes();
        return Math.toIntExact(units);
    }

    @Override
    public int costCents() {
        // Tiered pricing: 500 cents flat per GPU for up to 60 mins;
        // above 60 mins, add 200 cents per GPU for each commenced extra hour
        long base = 500L * gpuCount;
        if (getDurationMinutes() <= 60) {
            return Math.toIntExact(base);
        }
        long extraHours = ((getDurationMinutes() - 60L) + 59L) / 60L;
        long total = base + extraHours * 200L * gpuCount;
        return Math.toIntExact(total);
    }

    @Override
    public int priority() {
        // Urgent GPU jobs have priority 5; standard GPU jobs have priority 2
        return urgent ? 5 : 2;
    }
}
