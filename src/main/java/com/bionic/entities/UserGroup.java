package com.bionic.entities;

import javax.persistence.*;

/**
 * Created by rondo104 on 22.01.2016.
 */

@NamedNativeQueries({
        @NamedNativeQuery(name = "getAllGroups",
        query = "SELECT ug.id, ug.group_name FROM user_group ug"),
        @NamedNativeQuery(name = "getUsersIdByGroup",
                query = "SELECT ug.user_id FROM quizzes.user_group ug " +
                        "WHERE ug.group_name = :groupName")
})
@Entity
@Table(name = "user_group", catalog = "quizzes")
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "group_name", nullable = false)
    private String groupName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
