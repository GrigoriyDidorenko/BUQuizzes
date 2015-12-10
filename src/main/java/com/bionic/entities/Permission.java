package com.bionic.entities;

/**
 * package: com.bionic.entities
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 06.12.2015
 */
public enum Permission {
    PASS_THE_TEST(1,"passTheTest"), EDIT_THE_TEST(2,"editTheTest");

    private long id;
    private String name;

    private Permission(long id, String name) {
        this.id = id;
        this.name = name;
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
