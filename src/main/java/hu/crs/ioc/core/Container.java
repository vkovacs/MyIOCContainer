package hu.crs.ioc.core;

public interface Container {
    void addBean(String name, Object bean);
    Object getBean(String name);
}
