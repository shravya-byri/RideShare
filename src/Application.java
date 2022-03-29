import java.util.ArrayList;

public class Application {
    public ArrayList<User> users = new ArrayList<>();
    public ArrayList<Vehicle> vehicles = new ArrayList<>();
    public ArrayList<Ride> rides = new ArrayList<>();

    public void addUser(String name, User.Gender gender , int age) {
     User newUser = new User(name,gender,age);
     users.add(newUser);
    }

    public void addVehicle(String userId,String vehicleType, String vehicleNumber) throws Exception {
        if(!doesUserExist(userId)) throw new Exception("user not found");

        Vehicle newVehicle = new Vehicle(userId,vehicleType,vehicleNumber);
        vehicles.add(newVehicle);

    }

     boolean doesUserExist(String userId) {
        for(int i = 0; i < users.size();i++) {
            if(users.get(i).id.equals(userId)) {
                return true;
            }
        }
        return false;
    }

    public void offerRide(
            String vehicleId,
            String origin,
            String destination,
            Integer availableSeats
    ) throws Exception {
       if (doesVehiclehaveAnActiveRide(vehicleId)) throw new Exception("vehicle already has an active ride");

       Ride newRide = new Ride(vehicleId,origin,destination,availableSeats);
       rides.add(newRide);

    }

    boolean doesVehiclehaveAnActiveRide(String vehicleId) {
        for(int i = 0; i < rides.size();i++) {
            if (
                    rides.get(i).vehicleId.equals(vehicleId) &&
                    rides.get(i).status.equals(Ride.RideStatus.Active)
            ) {
                return true;
            }
        }
        return false;
    }




    public String selectRide(
             String passengerUserId,
             String origin,
             String destination,
             Integer requiredSeats,
             RideSelectionStrategy rideSelectionStrategy,
             String preferredVehicleType
    ) throws Exception {
    //get all matching rides
        Ride selectedRide;
        ArrayList<Ride> matchingRides = getAllMatchingRides(origin,destination,requiredSeats);
        if (matchingRides.isEmpty()) throw new Exception("No matching rides found");
        if(rideSelectionStrategy == RideSelectionStrategy.MostVacant) {
            selectedRide = selectMostVacant(matchingRides);
        } else selectedRide = selectMostPreferred(matchingRides,preferredVehicleType);
        if(selectedRide == null) throw new Exception("No ride found");

      selectedRide.availableSeats = selectedRide.availableSeats-requiredSeats;
      selectedRide.passengerUserIds.add(passengerUserId);

      return getVehicleNumber(selectedRide);
    }

    String getVehicleNumber(Ride selectedRide) {
        String vehicleNumber = null;
        String vehicleId = selectedRide.vehicleId;
        for(int i = 0; i < vehicles.size(); i++) {
            if (vehicles.get(i).vehicleId.equals(vehicleId)) {
                vehicleNumber = vehicles.get(i).number;
            }
        }
        return vehicleNumber;
    }


    Ride selectMostVacant(ArrayList<Ride> matchingRides) {
        Ride ans = null;
        int mostVacancy = Integer.MIN_VALUE;
        for(int i = 0; i < matchingRides.size();i++) {
            if(matchingRides.get(i).availableSeats > mostVacancy) {
                mostVacancy = matchingRides.get(i).availableSeats;
                ans = matchingRides.get(i);
            }
        }
        return ans;
    }

    Ride selectMostPreferred(ArrayList<Ride> matchingRides, String preferredVehicleType) {
        for(int i = 0; i < matchingRides.size(); i++)
        if(getVehicleTypeFromVehicleId(matchingRides.get(i).vehicleId).equals(preferredVehicleType)) {
            return matchingRides.get(i);
        }
        return null;
    }

    String getVehicleTypeFromVehicleId(String vehicleId) {
        String type = null;
        for(int i = 0; i < vehicles.size();i++) {
           if(vehicles.get(i).vehicleId.equals(vehicleId)) {
              type = vehicles.get(i).type;
           }

        }
        return type;
    }


    public enum RideSelectionStrategy{
        PreferredVehicle,
        MostVacant
    }


     ArrayList<Ride> getAllMatchingRides(String origin, String destination,Integer requiredSeats) {
        ArrayList<Ride> res = new ArrayList<>();

        for(int i = 0; i < rides.size(); i++) {
            Ride currRide = rides.get(i);
            if(
                    currRide.origin.equals(origin) &&
                    currRide.destination.equals(destination) &&
                    currRide.availableSeats >= requiredSeats &&
                    currRide.status.equals(Ride.RideStatus.Active)
            )
                res.add(currRide);
        }
        return res;
    }

    public void endRide(String rideId) {
        for(int i = 0; i < rides.size(); i++) {
            if (
                    rides.get(i).rideId.equals(rideId) &&
                    rides.get(i).status.equals(Ride.RideStatus.Active)
            ) {
                rides.get(i).status = Ride.RideStatus.Ended;
            }
        }
    }

    public void printRideStats() {
        System.out.println("Vehicles which offered ride");

        for (int i = 0; i < rides.size(); i++) {
            if(rides.get(i).status.equals(Ride.RideStatus.Ended)){
                System.out.println(getVehicleNumber(rides.get(i)));
            }
        }
            System.out.println("Ids of passengers who took ride ");
            for (int j = 0; j < rides.size(); j++) {
                if (!rides.get(j).passengerUserIds.isEmpty())
                    System.out.println(getPassengerNamesFromId(rides.get(j).passengerUserIds.get(0)));
            }
        }

    String getPassengerNamesFromId(String passengerId) {
        String ans = null;
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).id.equals(passengerId)) {
                    ans = users.get(i).name;
            }
        }
           return ans;
    }
}


