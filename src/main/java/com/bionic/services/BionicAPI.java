package com.bionic.services;

import org.springframework.stereotype.Service;

/**
 * Created by rondo104 on 22.01.2016.
 */
@Service
public class BionicAPI {

    public BionicAPI() {
    }

    private boolean PermisionUser(String login, String pass) {
        boolean access = false;

        //To DO BU API
        access = true;

        return access;
    }
}
