package com.bionic.entities;

/**
 * package: com.bionic.entities
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 05.12.2015
 */
public enum Role {
    ADMINISTRATOR(1, "administrator"),
    TRAINER(2, "trainer"),
    STUDENT(3, "student"),
    RESTRICTED_ADMINISTRATOR(4, "restricted_administrator"),
    RESTRICTED_TRAINER(5, "restricted_trainer");

    private long id;
    private String name;

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
}
