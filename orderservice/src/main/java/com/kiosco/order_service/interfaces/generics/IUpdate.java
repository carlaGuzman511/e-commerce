package com.kiosco.order_service.interfaces.generics;

public interface IUpdate<R, T, L> {
    public T update(L id, R request);
}
