package edu.practice.cloud.model;

import java.util.List;

public class SchedulePlan {
    private int capacityUnits;
    private int unitsUsed;
    private int unitsRemaining;
    private int totalCostCents;
    private List<ScheduleLine> selected;
    private List<Long> skipped;

    public SchedulePlan() {
    }

    public SchedulePlan(int capacityUnits, int unitsUsed, int unitsRemaining,
                        int totalCostCents, List<ScheduleLine> selected, List<Long> skipped) {
        this.capacityUnits = capacityUnits;
        this.unitsUsed = unitsUsed;
        this.unitsRemaining = unitsRemaining;
        this.totalCostCents = totalCostCents;
        this.selected = selected;
        this.skipped = skipped;
    }

    public int getCapacityUnits() { return capacityUnits; }
    public int getUnitsUsed() { return unitsUsed; }
    public int getUnitsRemaining() { return unitsRemaining; }
    public int getTotalCostCents() { return totalCostCents; }
    public List<ScheduleLine> getSelected() { return selected; }
    public List<Long> getSkipped() { return skipped; }
}
