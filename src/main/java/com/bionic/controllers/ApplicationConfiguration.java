package com.bionic.controllers;


import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

public class ApplicationConfiguration extends Application {

    private Set<Object> singletons = new HashSet<Object>();
    private Set<Class<?>> empty = new HashSet<Class<?>>();

    public ApplicationConfiguration() {
        singletons.add(new StudentController());
    }

    @Override
    public Set<Class<?>> getClasses() {
        return empty;
    }

    @Override
    public Set<Object> getSingletons() {
        return singletons;
    }
}
