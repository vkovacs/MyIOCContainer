package hu.crs.core;

import hu.crs.ioc.core.Container;
import hu.crs.ioc.core.exception.BeanAlreadyExistsException;
import hu.crs.ioc.core.exception.NoSuchBeanException;
import hu.crs.ioc.core.impl.ContainerImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

public class ContainerTest {

    private Container container;

    @Rule
    public TestRule containerRule = ((base, description) -> new Statement() {
        @Override
        public void evaluate() throws Throwable {
            container = new ContainerImpl();
            base.evaluate();
        }
    });

    @Test
    public void containerShouldAddBean() {
        container.addBean("bean", new Object());
        Object bean = container.getBean("bean");
        assertThat(bean, is(notNullValue()));
    }

    @Test(expected = NoSuchBeanException.class)
    public void shouldReturnErrorIfNoBeanByNamePresent() {
        container.getBean("noBeanByThisName");
    }

    @Test(expected = BeanAlreadyExistsException.class)
    public void shouldReturnErrorBecauseBeanAlreadyExists() {
        container.addBean("bean", new Object());
        container.addBean("bean", new Object());
    }
}
