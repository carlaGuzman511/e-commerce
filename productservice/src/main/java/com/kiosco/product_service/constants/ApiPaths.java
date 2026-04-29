package com.kiosco.product_service.constants;

public final class ApiPaths {

    private ApiPaths() {
    }

    public static final String API_BASE = "/api/v1";

    public static final class Product {
        private Product() {
        }

        public static final String BASE = API_BASE + "/products";
        public static final String BY_ID = "/id/{id}";
        public static final String BRANCH_STOCK = "/branch/{branchId}/{productId}/stock";
        public static final String BRANCH_STOCK_RESERVE = "/stock/reserve";
        public static final String BRANCH_STOCK_RESTORE = "/stock/restore";
        public static final String BRANCH_STOCK_CHECK = "/stock/check";
        public static final String SEARCH = "/search";
        public static final String ACTIVE_WITH_STOCK = "/with-stock";
    }

    public static final class Category {
        private Category() {
        }

        public static final String BASE = API_BASE + "/categories";
        public static final String BY_ID = "/{id}";
    }

    public static final class Media {
        private Media() {
        }

        public static final String BASE = API_BASE + "/media";
        public static final String UPLOAD = BASE + "/upload";
        public static final String ALL = BASE + "/all";
        public static final String DELETE = BASE + "/{publicId}";
        public static final String BATCH = BASE + "/_batch";
    }

    public static final class Tenant {
        private Tenant() {}

        public static final String BASE = API_BASE + "/stores";
        public static final String STORE_BY_ID = BASE + "/{storeId}";
        public static final String BRANCH_BY_ID = STORE_BY_ID + "/branches/{branchId}";
    }

}
