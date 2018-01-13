package hu.crs.testApp;

import hu.crs.ioc.core.Container;
import hu.crs.ioc.core.impl.ContainerImpl;
import hu.crs.testApp.infrastructure.impl.TestController;
import hu.crs.testApp.infrastructure.TestService;
import hu.crs.testApp.infrastructure.impl.TestServiceA;
import hu.crs.testApp.infrastructure.impl.TestServiceB;

public class Application {
    private Container context = new ContainerImpl();

    public static void main(String[] args) {
        Application application = new Application();
        application.initContext();
        application.start();
    }

    private void initContext() {
        context.addBean("beanA", new TestServiceA());
        context.addBean(new TestServiceB());
    }

    private void start() {
        new TestController((TestService) context.getBean("beanA")).hello();
        new TestController(context.getBean(TestServiceB.class)).hello();
    }
}
