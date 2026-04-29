package com.kiosco.tenant_service.constants;

public final class ApiPath {

    private ApiPath() {
    }

    public static final String API_BASE = "/api/v1";

    public static final class Store {
        private Store() {
        }

        public static final String BASE = API_BASE + "/stores";
        public static final String BY_ID = "/{id}";
        public static final String ACTIVE_STORE = "/{id}/active";
    }

    public static final class Branch {
        private Branch() {
        }

        public static final String BASE = API_BASE + "/stores/{storeId}/branches";
        public static final String BY_ID = "/{branchId}";
        public static final String ACTIVE_BRANCH = "/{branchId}/active";
    }
}
