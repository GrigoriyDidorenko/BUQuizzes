package com.bionic.DTO;

/**
 * package: com.bionic.DTO
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */
public class UserDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String cell;
    private String position;
    private int role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (id != userDTO.id) return false;
        if (role != userDTO.role) return false;
        if (!firstName.equals(userDTO.firstName)) return false;
        if (!lastName.equals(userDTO.lastName)) return false;
        if (!email.equals(userDTO.email)) return false;
        if (!password.equals(userDTO.password)) return false;
        if (cell != null ? !cell.equals(userDTO.cell) : userDTO.cell != null) return false;
        return !(position != null ? !position.equals(userDTO.position) : userDTO.position != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (cell != null ? cell.hashCode() : 0);
        result = 31 * result + (position != null ? position.hashCode() : 0);
        result = 31 * result + role;
        return result;
    }
}
