package com.bionic.DAO;

import com.bionic.entities.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * package: com.bionic.DAO
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 29.11.2015
 */

@Repository("roleDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class RoleDAO extends AbstractDAO<Role> {
    public RoleDAO() {
        super(Role.class);
    }
}
