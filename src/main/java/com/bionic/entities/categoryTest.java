package com.bionic.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

/**
 * Created by rondo104 on 23.12.2015.
 */
@Entity
@Table(name = "category_test", catalog = "quizzes")
public class CategoryTest {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column (name = "category_name")
    private String categoryName;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryTest")
    private Collection<Test> tests;

    public Collection<Test> getTests() {
        return tests;
    }

    public void setTests(Collection<Test> tests) {
        this.tests = tests;
    }

    public CategoryTest() {
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CategoryTest)) return false;

        CategoryTest that = (CategoryTest) o;

        if (id != that.id) return false;
        return categoryName.equals(that.categoryName);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + categoryName.hashCode();
        return result;
    }
}
