package hu.crs.ioc.core.impl;

import hu.crs.ioc.core.Container;
import hu.crs.ioc.core.exception.BeanAlreadyExistsException;
import hu.crs.ioc.core.exception.NoSuchBeanException;

import hu.crs.ioc.core.exception.NotClearBeanDefinitonException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    @SuppressWarnings("unchecked")
    public <T> T getBean(Class<T> clazz) {
        String canonicalName = clazz.getCanonicalName();
        T bean;
        if (clazz.isInterface()) {
            List<T> beansImplementsAnInterface = getBeansImplementsAnInterface(clazz);
            if (beansImplementsAnInterface.size() == 1) {
                return beansImplementsAnInterface.get(0);
            }
            throw new NotClearBeanDefinitonException();
        } else {
            bean = (T) getBean(canonicalName);
            return bean;
        }

    }

    @SuppressWarnings("unchecked")
    public <T> List<T> getBeansImplementsAnInterface(Class<T> clazz) {
        List<T> beansImplementingTheInterface = new ArrayList<>();

        for (Object object : container.values()) {
            if (clazz.isInstance(object)) {
                beansImplementingTheInterface.add((T) object);
            }
        }
        return beansImplementingTheInterface;
    }
}
