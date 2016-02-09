package com.bionic.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.*;

/**
 * package: com.bionic.services
 *
 * @author: Balitsky Alexandr
 * @date: 12.11.2015
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "getAvailableTestsById",
                query = "SELECT u.tests FROM User u " +
                        "WHERE u.id = :id"),
        @NamedQuery(name = "getUserByEmail",
                query = "SELECT u FROM User u WHERE u.email=:email"),
})
@NamedNativeQueries({
        @NamedNativeQuery(name = "getUsersNames",
        query = "SELECT u.id, u.first_name, u.last_name FROM User u")
})
@Table(catalog = "quizzes")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "first_name", nullable = false)
    private String firstName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "cell")
    private String cell;
    @Column(name="position")
    private String position;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.ORDINAL)
    @CollectionTable(name = "User_roles"
            , joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false)
    private  Set<Role> roles;

    {
        this.roles = EnumSet.noneOf(Role.class);
    }



    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "result", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "test_id"))
    private Set<Test> tests;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Collection<UserGroup> studyGroup;

    public Collection<UserGroup> getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(Collection<UserGroup> studyGroup) {
        this.studyGroup = studyGroup;
    }

    public User() {
    }

    public User(String firstName, String lastName, String email, String cell, String position, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.cell = cell;
        this.position = position;
        this.roles = roles;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public long getId() {
        return id;
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

    public Set<Role> getRoles() {
        return roles;
    }

    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        Iterator<Role> itr=getRoles().iterator();
        while (itr.hasNext()){
            authorities.add(new SimpleGrantedAuthority("ROLE_"+itr.next().getName()));
        }
        return authorities;
    }


    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Test> getTests() {
        return tests;
    }

    public void setTests(Set<Test> tests) {
        this.tests = tests;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", cell='" + cell + '\'' +
                ", position='" + position + '\'' +
                ", roles=" + roles.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getFirstName() != null ? !getFirstName().equals(user.getFirstName()) : user.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(user.getLastName()) : user.getLastName() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(user.getEmail()) : user.getEmail() != null) return false;
        if (getPassword() != null ? !getPassword().equals(user.getPassword()) : user.getPassword() != null)
            return false;
        return !(getRoles() != null ? !getRoles().equals(user.getRoles()) : user.getRoles() != null);

    }

    @Override
    public int hashCode() {
        int result = (int) (getId() ^ (getId() >>> 32));
        result = 31 * result + (getFirstName() != null ? getFirstName().hashCode() : 0);
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + (getRoles() != null ? getRoles().hashCode() : 0);
        return result;
    }


}
