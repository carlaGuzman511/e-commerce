package com.kiosco.order_service.enums;

import com.kiosco.order_service.constants.Messages;
import com.kiosco.order_service.exception.InvalidPaymentMethodException;

public enum PaymentMethod {
    Card(1),
    QR(2),
    BankTransfer(3);

    private final int code;

    PaymentMethod(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static PaymentMethod fromCode(int code) {
        for (PaymentMethod method : values()) {
            if (method.getCode() == code) return method;
        }
        throw new InvalidPaymentMethodException(Messages.INVALID_PAYMENT_METHOD + code);
    }
}
