package com.bionic.services;

import com.bionic.entities.Role;
import org.springframework.stereotype.Service;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 06.12.2015
 */
@Service
public class RoleService {

    public Role getRoleById(long id){
        for(Role role : Role.values())
            if(role.getId()==id)
                return role;
        return null;
    }
}
