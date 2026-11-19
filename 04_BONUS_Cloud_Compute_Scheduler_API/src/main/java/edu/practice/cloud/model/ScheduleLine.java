package edu.practice.cloud.model;

public class ScheduleLine {
    private long id;
    private String name;
    private String type;
    private int priority;
    private int computeUnits;
    private int costCents;

    public ScheduleLine() {
    }

    public ScheduleLine(long id, String name, String type, int priority, int computeUnits, int costCents) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.computeUnits = computeUnits;
        this.costCents = costCents;
    }

    public long getId() { return id; }
    public String getName() { return name; }
    public String getType() { return type; }
    public int getPriority() { return priority; }
    public int getComputeUnits() { return computeUnits; }
    public int getCostCents() { return costCents; }
}
