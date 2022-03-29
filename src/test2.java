public class test2 {
    public static void main(String[] args) throws Exception {
        Application app = new Application();
        app.addUser("Rohan", User.Gender.M,36);
        app.addVehicle(app.users.get(0).id,"Swift","KA-01-12345");
        app.addUser("Shashank", User.Gender.M,29);
        app.addVehicle(app.users.get(1).id,"Baleno", "TS-05-62395");
        app.addUser("Nandini", User.Gender.F,29);
        app.addUser("Shipra", User.Gender.F, 27);
        app.addVehicle(app.users.get(3).id, "Polo", "KA-05-41491");
        app.addVehicle(app.users.get(3).id,"Activa","KA-12-12332");
        app.addUser("Gaurav", User.Gender.M, 29);
        app.addUser("Rahul", User.Gender.M, 35);
        app.addVehicle(app.users.get(5).id,"XUV","KA-05-1234");

        //offering rides
        app.offerRide(app.vehicles.get(0).vehicleId,"Hyderabad","Bangalore",1);
        app.offerRide(app.vehicles.get(3).vehicleId,"Bangalore","Mysore",1);
        app.offerRide(app.vehicles.get(2).vehicleId,"Bangalore","Mysore",2);
        app.offerRide(app.vehicles.get(1).vehicleId,"Hyderabad","Bangalore",2);
        app.offerRide(app.vehicles.get(4).vehicleId,"Hyderabad","Bangalore",5);
        try {
            app.offerRide(app.vehicles.get(0).vehicleId,"Bangalore","Pune",1);
        } catch (Exception e) {
            System.out.println("vehicle already has an active ride");
        }

       //selecting rides
         System.out.println(app.selectRide(app.users.get(2).id,"Bangalore","Mysore",1, Application.RideSelectionStrategy.MostVacant,null));
         System.out.println(app.selectRide(app.users.get(4).id,"Bangalore","Mysore",1, Application.RideSelectionStrategy.PreferredVehicle,"Activa"));
         try {
             System.out.println(app.selectRide(app.users.get(1).id, "Mumbai", "Bangalore", 1, Application.RideSelectionStrategy.MostVacant, null));
         }catch (Exception e) {
             System.out.println("No rides found");
         }
         System.out.println(app.selectRide(app.users.get(0).id,"Hyderabad","Bangalore",1, Application.RideSelectionStrategy.PreferredVehicle,"Baleno"));
        try {
            System.out.println(app.selectRide(app.users.get(1).id, "Hyderabad", "Bangalore", 1, Application.RideSelectionStrategy.PreferredVehicle, "Polo"));
        } catch (Exception e) {
            System.out.println("No rides with preferred vehicle is available");
        }

        // end rides
        app.endRide(app.rides.get(0).rideId);
        app.endRide(app.rides.get(1).rideId);
        app.endRide(app.rides.get(2).rideId);
        app.endRide(app.rides.get(3).rideId);

        //print stats
        app.printRideStats();

    }
}
