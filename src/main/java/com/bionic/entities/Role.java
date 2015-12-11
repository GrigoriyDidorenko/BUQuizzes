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
    ADMINISTRATOR,
    TRAINER,
    STUDENT,
    RESTRICTED_ADMINISTRATOR,
    RESTRICTED_TRAINER;


    private static Map<String, Role> roleMap = new HashMap<>();
    static {
        for(Role role : values())
            roleMap.put(role.name(), role);
    }

    public static Role findByName(String name){
        return roleMap.get(name);
    }


    Role(){}

}
