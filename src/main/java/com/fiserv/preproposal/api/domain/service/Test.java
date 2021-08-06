package com.fiserv.preproposal.api.domain.service;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

public class Test {
    // static variable single_instance of type Singleton
    private static Test single_instance = null;

    // variable of type String
    @Getter
    @Setter
    public int currentLine;

    // private constructor restricted to this class itself
    private Test() {
        currentLine = 0;
    }

    // static method to create instance of Singleton class
    public static Test getInstance() {
        if (single_instance == null)
            single_instance = new Test();

        return single_instance;
    }


}
