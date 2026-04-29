package com.kiosco.order_service.enums;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.exception.InvalidDeliveryMethodException;

public enum DeliveryMethod {
    StorePickup(1),
    AvailableAtAnotherBranch(2),
    Delivery(3);

    private final int code;

    DeliveryMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static DeliveryMethod fromCode(int code) {
        for (DeliveryMethod method : values()) {
            if (method.getCode() == code) return method;
        }
        throw new InvalidDeliveryMethodException(Messages.INVALID_DELIVERY_METHOD + code);
    }
}
