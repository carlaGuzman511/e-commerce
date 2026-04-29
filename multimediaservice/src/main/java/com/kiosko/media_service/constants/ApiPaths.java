package com.kiosko.media_service.constants;

public final class ApiPaths {

    private ApiPaths() {}

    public static final String API_BASE = "/api/v1";

    public static final class Media {
        private Media() {}

        public static final String BASE = API_BASE + "/media";
        public static final String UPLOAD = "/upload";
        public static final String BATCH = "/_batch";
        public static final String BY_PUBLIC_ID = "/{publicId}";
    }
}
