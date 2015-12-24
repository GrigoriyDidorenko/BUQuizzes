package com.bionic.DAO;

import com.bionic.entities.CategoryTest;
import com.bionic.entities.Question;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by rondo104 on 23.12.2015.
 */
@Repository("categoryTestDAO")
@Transactional(propagation = Propagation.REQUIRED)
public class CategoryTestDAO extends AbstractDAO<CategoryTest>  {
    public CategoryTestDAO() {
        super(CategoryTest.class);
    }
}
