package com.kiosco.order_service.dto.mapper.converter;

import com.kiosco.order_service.enums.DeliveryMethod;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class DeliveryMethodConverter implements AttributeConverter<DeliveryMethod, Integer> {

    @Override
    public Integer convertToDatabaseColumn(DeliveryMethod status) {
        return status != null ? status.getCode() : null;
    }

    @Override
    public DeliveryMethod convertToEntityAttribute(Integer code) {
        return code != null ? DeliveryMethod.fromCode(code) : null;
    }
}