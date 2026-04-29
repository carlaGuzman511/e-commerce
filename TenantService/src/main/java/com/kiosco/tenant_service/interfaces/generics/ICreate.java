package com.kiosco.tenant_service.interfaces.generics;

public interface ICreate<T, R> {
    public T create(R request);
}
