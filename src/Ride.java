import java.util.ArrayList;
import java.util.UUID;

public class Ride {
    String rideId;
    String vehicleId;
    String origin;
    String destination;
    int availableSeats;
    RideStatus status;
    ArrayList<String> passengerUserIds;
    public Ride(
            String vehicle_id,
            String origin,
            String destination,
            int available_seats
    ) {
        this.rideId = UUID.randomUUID().toString();
        this.vehicleId = vehicle_id;
        this.origin = origin;
        this.destination = destination;
        this.availableSeats = available_seats;
        this.status = RideStatus.Active;
        this.passengerUserIds = new ArrayList<>();
    }
    public enum RideStatus{
        Active,
        Ended
    }
}
