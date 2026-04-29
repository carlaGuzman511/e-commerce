package com.kiosco.order_service.domain;

import com.kiosco.order_service.enums.DeliveryMethod;
import com.kiosco.order_service.enums.OrderStatus;
import com.kiosco.order_service.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table( name = "orders",
        schema = "dbo",
        indexes = {
            @Index(name = "idx_orders_tenant_status", columnList = "tenant_id,order_status_id"),
            @Index(name = "idx_orders_tenant_user", columnList = "tenant_id,user_id")
        })
public class Order extends Tenant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_status_id", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

    @Column(name = "delivery_method_id", nullable = false)
    private DeliveryMethod deliveryMethod;

    @Column(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "discounts", nullable = true)
    private  BigDecimal discounts;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    @Embedded
    private ShippingAddress shippingAddress;

    public void addItem(OrderItem item) {
        item.setOrder(this);
        this.items.add(item);
    }

    public void initialize(String tenantId, OrderStatus orderStatus) {
        this.tenantId = tenantId;
        this.orderStatus = orderStatus;
    }
}