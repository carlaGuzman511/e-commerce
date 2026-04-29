package com.kiosco.order_service.interfaces.generics;

public interface IRead<T, L> {
    public T getById(L id);
}
