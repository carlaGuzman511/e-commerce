package com.kiosco.order_service.dto.mapper.converter;

import com.kiosco.order_service.enums.PaymentMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PaymentMethodConverter implements AttributeConverter<PaymentMethod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentMethod status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public PaymentMethod convertToEntityAttribute(Integer code) {
        return code != null ? PaymentMethod.fromCode(code) : null;
    }
}