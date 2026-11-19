package edu.practice.dispatch.service;

import edu.practice.dispatch.model.*;
import edu.practice.dispatch.repository.*;
import edu.practice.dispatch.exception.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DispatchService {
    private final MissionRepository repo;

    public DispatchService(MissionRepository repo) {
        this.repo = repo;
    }

    public Collection<DispatchMission> all() {
        return repo.all();
    }

    public int energy(long id) {
        // TODO Task 3: retrieve and calculate energy; raise MissionNotFoundException if absent.

        DispatchMission mission = repo.find(id);
        if (mission == null) {
            throw new MissionNotFoundException(id);
        }
        return mission.energyUnits();

    }

    public DispatchMission addAir(NewAirMission request) {
        // TODO Task 3: validate numerical values and unique ID; calculate once to reject overflow.

        if (request == null) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (request.getId() < 1 || request.getId() > 10000) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (request.getDistanceKm() < 1 || request.getDistanceKm() > 10000) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (request.getLoadKg() < 0 || request.getLoadKg() > 10000) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (request.getWindPenalty() < 0 || request.getWindPenalty() > 10000) {
            throw new IllegalArgumentException("Invalid Request");
        }
        if (repo.exists(request.getId())) {
            throw new IllegalArgumentException("ID Already exists");
        }

        AirMission newMission = new AirMission(request.getId(),
                request.getDistanceKm(),
                request.getLoadKg(),
                request.getWindPenalty(),
                request.getCritical());
        newMission.energyUnits();
        repo.save(newMission);
        return newMission;

    }

    public DispatchPlan plan(int capacity, boolean urgentOnly) {
        // TODO Task 2: filter optional urgent missions (priority >=3), sort priority DESC,
        // energy ASC, ID ASC; select greedily if energy fits; preserve skipped IDs.

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }

        List<DispatchMission> eligible = new ArrayList<>();
        for (DispatchMission m : repo.all()) {
            if (!urgentOnly || m.priority() >= 3) {
                eligible.add(m);
            }
        }

        eligible.sort(this::compareMissions);

        List<Long> skipped = new ArrayList<>();
        List<PlanLine> selected = new ArrayList<>();
        int energyUsed = 0;
        for (DispatchMission e : eligible) {
            if (e.energyUnits() <= capacity - energyUsed) {
                selected.add(new PlanLine(e.getId(), e.getClass().getSimpleName(), e.priority(), e.energyUnits()));
                energyUsed += e.energyUnits();
            }
            else skipped.add(e.getId());
        }

        int energyRemaining = capacity - energyUsed;
        return new DispatchPlan(capacity, energyUsed, energyRemaining, selected, skipped);

    }

    // Helper method for plan()
    private int compareMissions(DispatchMission first, DispatchMission second) {
        int result = Integer.compare(second.priority(), first.priority());
        if (result != 0) {
            return result;
        }
        result = Integer.compare(first.energyUnits(), second.energyUnits());
        if (result != 0) {
            return result;
        }
        return Long.compare(first.getId(), second.getId());
    }

//    public static void main(String[] args) {
//        DispatchService m = new DispatchService(new MissionRepository());
//        System.out.println(m.plan(50, true).getEnergyUsed());

}


