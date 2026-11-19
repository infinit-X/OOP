package edu.practice.cloud.model;

/**
 * Request model for POST /api/jobs/gpu.
 * Ordinary Java class with no-arg constructor and getters/setters for Jackson.
 */
public class NewGpuJob {
    private long id;
    private String name;
    private int durationMinutes;
    private int gpuCount;
    private boolean urgent;

    public NewGpuJob() {
    }

    public NewGpuJob(long id, String name, int durationMinutes, int gpuCount, boolean urgent) {
        this.id = id;
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.gpuCount = gpuCount;
        this.urgent = urgent;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(int durationMinutes) { this.durationMinutes = durationMinutes; }

    public int getGpuCount() { return gpuCount; }
    public void setGpuCount(int gpuCount) { this.gpuCount = gpuCount; }

    public boolean isUrgent() { return urgent; }
    public void setUrgent(boolean urgent) { this.urgent = urgent; }
}
