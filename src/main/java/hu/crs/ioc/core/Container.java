package hu.crs.ioc.core;

public interface Container {
    void addBean(String name, Object bean);

    Object getBean(String name);

    void addBean(Object bean);

    <T> T getBean(Class<T> clazz);
}
