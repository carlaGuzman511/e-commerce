package com.kiosco.order_service.dto.mapper.converter;

import com.kiosco.order_service.enums.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStatusConverter implements AttributeConverter<OrderStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(OrderStatus status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer code) {
        return code != null ? OrderStatus.fromCode(code) : null;
    }
}
