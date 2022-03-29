import java.util.UUID;

public class Vehicle {
    String vehicleId;
    String userId;
    String type;
    String number;
    public Vehicle(String userid, String type,String number) {
        this.vehicleId = UUID.randomUUID().toString();
        this.userId = userid;
        this.type = type;
        this.number = number;
    }

}
