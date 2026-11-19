import edu.practice.dispatch.model.AirMission;
import edu.practice.dispatch.repository.MissionRepository;
import edu.practice.dispatch.repository.MissionRepository.*;
import edu.practice.dispatch.model.GroundMission;
import org.springframework.stereotype.Repository;

public static void main(String[] args) {
    AirMission am = new AirMission(103, 8, 1, 3, true);
    System.out.println("101 Energy: " + am.energyUnits() + " | Priority: " + am.priority());


}
