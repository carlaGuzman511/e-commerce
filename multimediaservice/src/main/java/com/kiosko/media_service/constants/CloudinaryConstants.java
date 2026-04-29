package com.kiosko.media_service.constants;

public final class CloudinaryConstants {

    private CloudinaryConstants() {}

    public static final class Options {
        private Options() {}

        public static final String FOLDER = "folder";
        public static final String USE_FILENAME = "use_filename";
        public static final String UNIQUE_FILENAME = "unique_filename";
        public static final String OVERWRITE = "overwrite";
        public static final String INVALIDATE = "invalidate";
    }

    public static final class Results {
        private Results() {}

        public static final String RESULT = "result";
        public static final String OK = "ok";
        public static final String NOT_FOUND = "not found";
        public static final String PUBLIC_ID = "public_id";
    }

    public static final class Errors {
        private Errors() {}

        public static final String UPLOAD_FAILED = "Failed to upload file";
        public static final String DELETE_FAILED = "Failed to delete file";
        public static final String INVALID_URL = "Invalid Cloudinary URL: ";
        public static final String UPLOAD_ERROR_SPANISH = "Error al subir archivo";
    }

    public static final class Logs {
        private Logs() {}

        public static final String UPLOAD_ERROR = "Error uploading file to Cloudinary: {}";
        public static final String DELETE_INFO = "Cloudinary delete result: {}";
        public static final String DELETE_ERROR = "Error deleting file from Cloudinary: {}";
    }
}
