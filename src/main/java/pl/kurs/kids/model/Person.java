package pl2.kurs.model;

import java.util.List;

public class Person {
    private String firstName;
    private String lastName;
    private int age;
    private Boolean isMarried;
    private List<String> hobbies;
    private List<Kid> kids;

    public Person() {
    }

    public Person(String firstName, String lastName, int age, boolean isMarried, List<String> hobbies, List<Kid> kids) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.isMarried = isMarried;
        this.hobbies = hobbies;
        this.kids = kids;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    public boolean getIsMarried() {
        return isMarried;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public List<Kid> getKids() {
        return kids;
    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", isMarried=" + isMarried +
                ", hobbies=" + hobbies +
                ", kids=" + kids +
                '}';
    }
}
