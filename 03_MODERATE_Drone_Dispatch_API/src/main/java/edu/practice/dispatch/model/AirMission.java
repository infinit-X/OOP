package edu.practice.dispatch.model;

public class AirMission extends DispatchMission{
    private final int windPenalty;
    private final boolean critical;


    public AirMission(long id, int distanceKm, int loadKg,int windPenalty, boolean critical) {
        super(id, distanceKm, loadKg);
        this.windPenalty = windPenalty;
        this.critical = critical;
    }

    public boolean isCritical() {
        return critical;
    }

    public int getWindPenalty() {
        return windPenalty;
    }

    @Override
    public int energyUnits() {

        long energyUnits = 3L * getDistanceKm() + 8L * getLoadKg() + 2L * windPenalty;
        return Math.toIntExact(energyUnits);
    }

    @Override
    public int priority() {

        if (isCritical()) {
            return 5;
        }
        return 2;
    }
}
