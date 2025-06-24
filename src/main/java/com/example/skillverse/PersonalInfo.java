package com.example.skillverse;

import java.sql.Date;

public class PersonalInfo {
    private int id;
    private String firstName, lastName, gender, email, country, city, phone, bio;
    private Date birthday;

    public PersonalInfo(int id, String firstName, String lastName, Date birthday, String gender,
                        String email, String country, String city, String phone, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.gender = gender;
        this.email = email;
        this.country = country;
        this.city = city;
        this.phone = phone;
        this.bio = bio;
    }

    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Date getBirthday() { return birthday; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getCountry() { return country; }
    public String getCity() { return city; }
    public String getPhone() { return phone; }
    public String getBio() { return bio; }
}

