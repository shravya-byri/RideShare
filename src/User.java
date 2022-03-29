import java.util.UUID;

public class User {
     String id;
     String name;
     Gender gender;
     int age;
    public User(String name,Gender gender,int age) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
    public enum Gender{F,M,O}

}
