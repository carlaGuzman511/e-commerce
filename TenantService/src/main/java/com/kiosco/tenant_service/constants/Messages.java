package com.kiosco.tenant_service.constants;

public final class Messages {

    private Messages() {
    }

    public static final String STORE_RETRIEVED = "Store retrieved successfully";
    public static final String STORE_NOT_FOUND = "Store not found for this tenant";
    public static final String STORE_CREATED = "Store created successfully";
    public static final String STORE_UPDATED = "Store updated successfully";
    public static final String STORES_RETRIEVED = "Stores retrieved successfully";
    public static final String INACTIVE_STORE = "Store was inactive successfully";
    public static final String ACTIVE_STORE = "Store was active successfully";
    public static final String BRANCH_NOT_FOUND = "Branch not found for this tenant";
    public static final String BRANCH_CREATED = "Branch created successfully";
    public static final String BRANCH_RETRIEVED = "Branch retrieved successfully";
    public static final String BRANCH_UPDATED = "Branch updated successfully";
    public static final String INACTIVE_BRANCH = "Branch was inactive successfully";
    public static final String ACTIVE_BRANCH = "Branch was active successfully";
    public static final String INVALID_REQUEST = "Invalid request data";
    public static final String TENANT_REQUIRED = "Tenant ID is required";
    public static final String INVALID_ATTRIBUTES_JSON = "Invalid JSON format for product variant attributes.";
    public static final String BRANCH_TENANT_MISMATCH = "The selected branch does not belong to this tenant.";
    public static final String INVALID_TENANT_ID = "Invalid Tenant Id";
    public static final String UNEXPECTED_ERROR = "Unexpected error while communicating with inventory service: ";
    public static final String EXTERNAL_SERVICE_ERROR = "External service error";
    public static final String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static final String BAD_REQUEST = "Bad Request";
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static final String CONSTRAINT_VIOLATION = "Constraint violation";
}