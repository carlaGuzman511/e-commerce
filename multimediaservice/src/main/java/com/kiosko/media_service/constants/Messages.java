package com.kiosko.media_service.constants;

public class Messages {
    private Messages() {
    }

    public static final String MEDIA_UPLOADED = "Media uploaded successfully.";
    public static final String MEDIA_DELETED = "Media deleted successfully.";
    public static final String MEDIA_LIST_RETRIEVED = "Media list retrieved successfully.";

    public static final String MEDIA_NOT_FOUND = "Media not found for current tenant.";
    public static final String CLOUDINARY_DELETE_FAILED = "Failed to delete media from Cloudinary.";
    public static final String CLOUDINARY_UPLOAD_FAILED = "Failed to upload media to Cloudinary.";
}
