package hu.crs.testApp.infrastructure.impl;

import hu.crs.testApp.infrastructure.TestService;

public class TestServiceB implements TestService {
    public void hello() {
        System.out.println("Hi, I'm TestServiceB");
    }
}
