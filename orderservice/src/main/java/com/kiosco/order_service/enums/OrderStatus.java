package com.kiosco.order_service.enums;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.exception.InvalidOrderStateException;
import com.kiosco.order_service.exception.InvalidPaymentMethodException;

public enum OrderStatus {
    PENDING(1),
    PAID(2),
    SHIPPED(3),
    DELIVERED(4),
    CANCELLED(5),
    REFUNDED(6);

    private final int code;

    OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : values()) {
            if (status.getCode() == code) return status;
        }
        throw new InvalidPaymentMethodException(Messages.INVALID_PAYMENT_METHOD + code);
    }

    public static boolean isValid(String status) {
        if (status == null) return false;
        try {
            OrderStatus.valueOf(status.toUpperCase());
            return true;
        } catch (InvalidPaymentMethodException e) {
            return false;
        }
    }
}
