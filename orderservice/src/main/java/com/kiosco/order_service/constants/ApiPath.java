package com.kiosco.order_service.constants;

public final class ApiPath {

    private ApiPath() {
    }

    public static final String API_BASE = "/api/v1";

    public static final class Order {
        private Order() {
        }

        public static final String BASE = API_BASE + "/orders";
        public static final String BY_ID = "/{id}";
        public static final String UPDATE_ORDER_STATUS = "/{id}/status";
        public static final String CANCEL_ORDER = "/{id}/cancel";
    }

    public static final class Product {
        private Product() {
        }

        public static final String BASE = API_BASE + "/products";
        public static final String BY_ID = "/{id}";
        public static final String BRANCH_STOCK = "/branch/{branchId}/{productId}/stock";
        public static final String BRANCH_STOCK_RESERVE = "/stock/reserve";
        public static final String BRANCH_STOCK_RESTORE = "/stock/restore";
        public static final String BRANCH_STOCK_CHECK = "/stock/check";
    }
}