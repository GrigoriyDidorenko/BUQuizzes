package com.bionic.services;

import com.bionic.entities.Role;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 06.12.2015
 */
public class RoleService {

    public Role getRoleById(long id){
        for(Role role : Role.values())
            if(role.getId()==id)
                return role;
        return null;
    }
}
