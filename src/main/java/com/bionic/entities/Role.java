package com.bionic.entities;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * package: com.bionic.entities
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 05.12.2015
 */
@Resource
public enum Role {
    ADMINISTRATOR(0, "administrator"),
    TRAINER(1, "trainer"),
    STUDENT(2, "student"),
    RESTRICTED_ADMINISTRATOR(3, "restricted_administrator"),
    RESTRICTED_TRAINER(4, "restricted_trainer");

    private long id;
    private String name;

    private static Map<Long, Role> roleMap = new HashMap<>();
    static {
        for(Role role : values())
            roleMap.put(role.id, role);
    }

    public static Role findById(long id){
        return roleMap.get(id);
    }


    Role(){}

    private Role(long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Role{" +
                "name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
