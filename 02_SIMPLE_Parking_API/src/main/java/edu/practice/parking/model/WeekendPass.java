package edu.practice.parking.model;

public class WeekendPass extends ParkingPass {
    private final int flatCents;

    public WeekendPass(long id, String registration, int flatCents) {
        super(id, registration);
        this.flatCents = flatCents;
    }

    public int getFlatCents() {
        return flatCents;
    }

    @Override
    public int priceCents(int stayMinutes) {

        long finalFee = 0;
        if (stayMinutes <= 0) {
            throw new IllegalArgumentException("Sta minutes must be positive");
        }
        if (stayMinutes <= 480) {
            finalFee = flatCents;
        }
        if (stayMinutes > 480) {
            long additionalHours = ((stayMinutes - 480L) + 59L) / 60L;
            finalFee = flatCents + additionalHours * 150L;
        }

        return Math.toIntExact(finalFee);
    }





}
