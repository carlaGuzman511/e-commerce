package com.kiosco.order_service.constants;

public final class Messages {

    private Messages() {
    }

    public static final String PRODUCT_RETRIEVED = "Product retrieved successfully";
    public static final String PRODUCT_NOT_FOUND = "Product not found for this tenant";
    public static final String ORDER_CREATED = "Order created successfully";
    public static final String ORDER_UPDATED = "Order updated successfully";
    public static final String ORDER_RETRIEVED = "Order retrieved successfully";
    public static final String ORDERS_RETRIEVED = "Orders retrieved successfully";
    public static final String ORDER_CANCELED = "order cancelled successfully";
    public static final String ORDER_STATUS_UPDATED = "order status updated successfully";
    public static final String ORDER_NOT_FOUND = "Order not found for this tenant";
    public static final String CART_CREATED = "Cart created successfully";
    public static final String CART_RETRIEVED = "Cart retrieved successfully";
    public static final String CART_UPDATED = "Cart updated successfully";
    public static final String CART_DELETED = "Cart deleted successfully";
    public static final String CART_NOT_FOUND = "Cart not found for this tenant";
    public static final String PRODUCT_NOT_FOUND_IN_BRANCH = "Product not found in branch";
    public static final String STOCK_RETRIEVED = "Stock retrieved";
    public static final String STOCK_RESERVED = "Stock reserved";
    public static final String STOCK_RESTORED = "Stock restored";
    public static final String INSUFFICIENT_STOCK = "Insufficient stock for product ";
    public static final String RESTORE_EXCEEDS_RESERVED = "Cannot restore more items than reserved";
    public static final String INVALID_REQUEST = "Invalid request data";
    public static final String TENANT_REQUIRED = "Tenant ID is required";
    public static final String PRODUCT_PRICE_INVALID = "The price must be greater than zero.";
    public static final String PRODUCT_STOCK_NEGATIVE = "Stock value cannot be negative.";
    public static final String STOCK_NOT_FOUND = "Stock not found for the specified branch and variant.";
    public static final String INVALID_ATTRIBUTES_JSON = "Invalid JSON format for product variant attributes.";
    public static final String BRANCH_TENANT_MISMATCH = "The selected branch does not belong to this tenant.";
    public static final String STOCK_INSUFFICIENT = "Insufficient stock available for this variant.";
    public static final String STOCK_INVALID = "Branch, variant, and quantity must be valid.";
    public static final String STOCK_EMPTY_LIST = "Stock update list cannot be empty.";
    public static final String STOCK_QUANTITY_INVALID = "Quantity must be greater than zero.";
    public static final String STOCK_RESERVE_EMPTY_LIST = "Stock update list cannot be empty.";
    public static final String STOCK_RESERVE_QUANTITY_INVALID = "Quantity must be greater than zero.";
    public static final String STOCK_RESERVE_COMPLETED = "Stock reservation command completed successfully.";
    public static final String STOCK_CHECK_COMPLETED = "Stock check completed successfully.";
    public static final String STOCK_STOCK_NEGATIVE = "Requested quantity cannot be negative.";
    public static final String VARIANT_ID_REQUIRED = "Variant ID must be provided.";
    public static final String INVALID_PAYMENT_METHOD = "Invalid Payment Method : ";
    public static final String INVALID_DELIVERY_METHOD = "Invalid Delivery Method : ";
    public static final String INVALID_TENANT_ID = "Invalid Tenant Id";
    public static final String INVALID_ORDER_STATUS_CHANGE = "Cannot change status from ";
    public static final String FAILED_TO_VERIFY_STOCK = "Failed to verify stock for product ";
    public static final String FAILED_TO_RESERVE_REQUESTED_STOCK = "Failed to reserve requested stock for product ";
    public static final String FAILED_TO_RESTORE_CANCELED_STOCK = "Failed to restore canceled stock for product ";
    public static final String INVENTORY_SERVICE_CLIENT = "Inventory service client error: ";
    public static final String INVENTORY_SERVICE_INTERNAL= "Inventory service internal error: ";
    public static final String INVENTORY_SERVICE_UNREACHABLE = "Inventory service unreachable: ";
    public static final String UNEXPECTED_ERROR = "Unexpected error while communicating with inventory service: ";
    public static final String PRODUCT_ID_SHOULD_NOT_BE_NULL = "productId should not be null";
    public static final String QUANTITY_SHOULD_NOT_BE_NULL = "quantity should not be null";
    public static final String QUANTITY_SHOULD_NOT_BE_NEGATIVE = "quantity should not be negative";
    public static final String PRICE_SHOULD_NOT_BE_NULL = "price should not be null";
    public static final String PRICE_SHOULD_NOT_BE_NEGATIVE = "price should not be negative";
    public static final String DISCOUNT_SHOULD_NOT_BE_NEGATIVE = "discount should not be negative";
    public static final String DISCOUNT_SHOULD_NOT_BE_NULL = "discount should not be null";
    public static final String ORDER_STATUS_IS_REQUIRED = "order status is required";
    public static final String EXTERNAL_SERVICE_ERROR = "External service error";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String CONSTRAINT_VIOLATION = "Constraint violation";
}