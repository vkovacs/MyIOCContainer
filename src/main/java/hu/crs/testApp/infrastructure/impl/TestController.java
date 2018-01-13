package hu.crs.testApp.infrastructure.impl;

import hu.crs.testApp.infrastructure.TestService;

public class TestController {
    private TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    public void hello() {
        System.out.println("Hello, I'm TestController, using: ");
        testService.hello();
    }
}
