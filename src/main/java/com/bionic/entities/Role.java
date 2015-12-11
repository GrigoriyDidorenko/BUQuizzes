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
        ADMINISTRATOR(1, "ADMINISTRATOR"),
        TRAINER(2, "TRAINER"),
        STUDENT(3, "STUDENT"),
        RESTRICTED_ADMINISTRATOR(4, "RESTRICTED_ADMINISTRATOR"),
        RESTRICTED_TRAINER(5, "RESTRICTED_TRAINER");

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


    public static Map<String, Role> getRoleMap() {
        return roleMap;
    }

    private static Map<String, Role> roleMap = new HashMap<>();
    static {
        for(Role role : values())
            roleMap.put(role.name(), role);
    }

    public static Role findByName(String name){
        return roleMap.get(name);
    }


}
