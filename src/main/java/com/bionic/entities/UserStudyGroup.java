package com.bionic.entities;

import javax.persistence.*;

/**
 * Created by rondo104 on 22.01.2016.
 */
@Entity
@Table(name = "user_study_group", catalog = "quizzes")
public class UserStudyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "groupName", nullable = false)
    private String groupName;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    private User userId;

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }
}
