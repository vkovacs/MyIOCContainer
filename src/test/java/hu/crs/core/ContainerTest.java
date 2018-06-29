package hu.crs.core;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import hu.crs.ioc.core.Container;
import hu.crs.ioc.core.exception.BeanAlreadyExistsException;
import hu.crs.ioc.core.exception.NoSuchBeanException;
import hu.crs.ioc.core.exception.NotClearBeanDefinitonException;
import hu.crs.ioc.core.impl.ContainerImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runners.model.Statement;

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

    @Test
    public void shouldAddBeanToContextByType() {
        container.addBean(10);
        Integer integer = container.getBean(Integer.class);
        assertThat(integer, is(10));
    }

    @Test(expected = BeanAlreadyExistsException.class)
    public void shouldReturnBeanAlreadyExistsExceptionIfTwoBeansWithSameTypeIsAdded() {
        container.addBean(new Object());
        container.addBean(new Object());
    }

    @Test(expected = NoSuchBeanException.class)
    public void shouldReturnNoSuchBeanExceptionIfNoBeanWithTypeIsPresent() {
        container.getBean(Integer.class);
    }

    private interface TestInterFace {
    }

    private class TestA implements TestInterFace {
    }

    private class TestB implements TestInterFace {
    }

    @Test
    public void shouldAddTwoClassesWhichImplementTheSameInterface() {
        container.addBean(new TestA());
        container.addBean(new TestB());
    }

    @Test(expected = NotClearBeanDefinitonException.class)
    public void shouldReturnNoSuchBeanDefinitionExceptionInNotClearWhichBeanShouldReturnedByType() {
        container.addBean(new TestA());
        container.addBean(new TestB());
        container.getBean(TestInterFace.class);
    }
}
