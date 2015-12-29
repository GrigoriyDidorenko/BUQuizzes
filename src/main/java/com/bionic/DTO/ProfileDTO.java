package com.bionic.DTO;

/**
 * Created by Sergiy on 08.12.2015.
 */
public class ProfileDTO {

    //TODO delete userId
    private long userId;
    private String firstName;
    private String lastName;


    public ProfileDTO(long userId, String firstName, String lastName) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {

        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfileDTO that = (ProfileDTO) o;

        if (userId != that.userId) return false;
        if (!firstName.equals(that.firstName)) return false;
        return lastName.equals(that.lastName);

    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

}