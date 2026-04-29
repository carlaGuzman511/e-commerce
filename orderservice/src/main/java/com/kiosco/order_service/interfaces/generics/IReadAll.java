package com.kiosco.order_service.interfaces.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IReadAll<T> {
    public Page<T> getAll(Pageable pageable);
}