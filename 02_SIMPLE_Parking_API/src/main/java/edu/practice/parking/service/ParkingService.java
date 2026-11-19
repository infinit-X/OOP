package edu.practice.parking.service;

import edu.practice.parking.model.*;
import edu.practice.parking.dto.*;
import edu.practice.parking.repo.*;
import edu.practice.parking.error.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ParkingService {
    private final PassRepository repo;

    public ParkingService(PassRepository repo) {
        this.repo = repo;
    }

    public Collection<ParkingPass> all() {
        return repo.all();
    }

    public Quote quote(long id, int minutes) {
        // TODO Task 3: validate stay, return 404 when ID missing, calculate dynamically.

        if (minutes <= 0) {
            throw new IllegalArgumentException("Stay minutes must be positive");
        }

        ParkingPass pass = repo.get(id);
        if (pass == null) {
            throw new PassNotFoundException(id);
        }

        return new Quote(pass.getId(), pass.getRegistration(), pass.getClass().getSimpleName(), minutes, pass.priceCents(minutes));
    }

    public List<Quote> allQuotes(int minutes) {
        // TODO Task 2: apply priceCents polymorphically; sort by price asc, then ID asc.

        if (minutes <= 0) {
            throw new IllegalArgumentException("Invalid amount of minutes");
        }

        List<Quote> quote = new ArrayList<>();
        for (ParkingPass pass : repo.all()) {
            quote.add(new Quote(pass.getId(), pass.getRegistration(), pass.getClass().getSimpleName(), minutes, pass.priceCents(minutes)));
        }

        quote.sort(this::compareQuotes);

        return quote;
    }

    private int compareQuotes(Quote firstQuote, Quote secondQuote) {
         int result = Integer.compare(firstQuote.priceCents(), secondQuote.priceCents());
         if (result != 0) {
             return result;
         }
         return Long.compare(firstQuote.id(), secondQuote.id());
    }

    public ParkingPass addHourly(NewHourlyPass body) {
        // TODO Task 3: validate id, registration, rate and duplicate ID before saving.

        if (body == null) {
            throw new IllegalArgumentException("Registration should not be blank");
        }
        if (body.id() <= 0) {
            throw new IllegalArgumentException("ID must be positive");
        }
        if (repo.contains(body.id())) {
            throw new IllegalArgumentException("ID Already Exists");
        }
        if (body.rateCents() <= 0) {
            throw new IllegalArgumentException("Rate must be positive");
        }
        if (body.registration() == null || body.registration().isBlank()) {
            throw new IllegalArgumentException("Registration must not be blank");
        }

        HourlyPass pass = new HourlyPass(body.id(), body.registration().trim(), body.rateCents());
        repo.save(pass);
        return pass;
    }
    // You may add a private helper method for constructing Quote objects.

}

