package com.kiosco.tenant_service.interfaces.generics;

public interface IRead<T, L> {
    public T getById(L id);
}
