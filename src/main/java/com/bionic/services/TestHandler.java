package com.bionic.services;

import com.bionic.DTO.TestDTO;
import com.bionic.entities.Permission;

import java.util.Set;

/**
 * package: com.bionic.services
 * project: Test
 * class:
 *
 * @author: Grigoriy Didorenko
 * @date: 10.02.2016
 */
public interface TestHandler<T> {

    Set<T> getAvailableTestsNames(long idStr);

    TestDTO getCurrentTest(long id, String resultIdStr);
}
