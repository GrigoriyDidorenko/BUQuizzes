package com.bionic.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * package: com.bionic.entities
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 05.12.2015
 */
public enum Roles {
    ADMINISTRATOR(0,"administrator"),
    TRAINER(1,"trainer"),
    STUDENT(2,"student"),
    RESTRICTED_ADMINISTRATOR(3,"restricted_administrator"),
    RESTRICTED_TRAINER(4,"restricted_trainer");

    private int id;
    private String name;

    private Roles(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
