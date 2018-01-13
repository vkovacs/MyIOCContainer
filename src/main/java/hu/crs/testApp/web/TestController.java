package hu.crs.testApp.web;

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
