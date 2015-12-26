package com.bionic.DAO;

import com.bionic.entities.CategoryTest;
import com.bionic.entities.OneTimeTest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rondo104 on 26.12.2015.
 */
@Repository("oneTimeTestDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class OneTimeTestDAO extends AbstractDAO<OneTimeTest>  {

    public OneTimeTestDAO() {
        super(OneTimeTest.class);
    }

}
