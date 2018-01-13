package hu.crs.ioc.core.impl;

import hu.crs.ioc.core.Container;
import hu.crs.ioc.core.exception.BeanAlreadyExistsException;
import hu.crs.ioc.core.exception.NoSuchBeanException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ContainerImpl implements Container {
    private Map<String, Object> container = new HashMap<>();

    @Override
    public void addBean(String name, Object bean) {
        Object storedBean = container.get(name);
        if (storedBean != null) {
            throw new BeanAlreadyExistsException();
        }
        container.put(name, bean);
    }

    @Override
    public Object getBean(String name) {
        Optional<Object> bean = Optional.ofNullable(container.get(name));
        return bean.orElseThrow(NoSuchBeanException::new);
    }

    @Override
    public void addBean(Object bean) {
        String canonicalName = bean.getClass().getCanonicalName();
        addBean(canonicalName, bean);
    }

    @Override
    public Object getBean(Class<?> clazz) {
        String canonicalName = clazz.getCanonicalName();
        return getBean(canonicalName);
    }
}
