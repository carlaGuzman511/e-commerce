package com.kiosco.order_service.service;

import com.kiosco.order_service.domain.Order;
import com.kiosco.order_service.domain.OrderItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class OrderCalculator {

    public void calculateTotals(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal discount = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            item.setOrder(order);
            item.setTenantId(order.getTenantId());
            total = total.add(item.getTotal());
            if (item.getDiscount() != null) {
                discount = discount.add(item.getDiscount().multiply(BigDecimal.valueOf(item.getQuantity())));
            }
        }

        order.setTotal(total);
        order.setDiscounts(discount);
    }
}
