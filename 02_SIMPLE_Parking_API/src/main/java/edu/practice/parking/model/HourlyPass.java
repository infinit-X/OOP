package edu.practice.parking.model;

public class HourlyPass extends ParkingPass {
    private final int rateCents;

    public HourlyPass(long id, String registration, int rateCents) {
        super(id, registration);
        this.rateCents = rateCents;
    }

    public int getRateCents() {
        return rateCents;
    }

    @Override
    public int priceCents(int stayMinutes) {
        // TODO Task 1: round up partial hours; one-hour minimum, validate input.

        if (stayMinutes <= 0) {
            throw new IllegalArgumentException("Stay minutes must be positive");
        }
        long billableHours = (stayMinutes + 59L) / 60L;
        long finalFee = billableHours * rateCents;

        return Math.toIntExact(finalFee);
    }
}
