package com.bionic.DTO;

/**
 * Created by Sergiy on 08.12.2015.
 */
public class ProfileDTO {

    private String firstName;
    private String lastName;


    public ProfileDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
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

        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        return !(lastName != null ? !lastName.equals(that.lastName) : that.lastName != null);

    }

    @Override
    public int hashCode() {
        int result = firstName != null ? firstName.hashCode() : 0;
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        return result;
    }
}