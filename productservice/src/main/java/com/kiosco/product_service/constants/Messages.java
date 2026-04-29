package com.kiosco.product_service.constants;

public final class Messages {

    private Messages() {
    }

    public static final String CATEGORY_CREATED = "Category created successfully";
    public static final String CATEGORY_UPDATED = "Category updated successfully";
    public static final String CATEGORY_RETRIEVED = "Category retrieved successfully";
    public static final String CATEGORY_NOT_FOUND = "Category not found for this tenant";
    public static final String CATEGORY_ALREADY_EXISTS = "A category with that name already exists for this tenant";
    public static final String CATEGORY_INACTIVE = "Cannot update an inactive category";
    public static final String PRODUCT_CREATED = "Product created successfully";
    public static final String PRODUCT_RETRIEVED = "Product retrieved successfully";
    public static final String PRODUCT_UPDATED = "Product updated successfully";
    public static final String PRODUCT_DELETED = "Product deleted successfully";
    public static final String PRODUCT_NOT_FOUND = "Product not found for this tenant";
    public static final String STOCK_RESERVED = "Stock reserved";
    public static final String STOCK_RESTORED = "Stock restored";
    public static final String PRODUCT_NAME_DUPLICATE = "A product with this name already exists for this tenant.";
    public static final String PRODUCT_VARIANT_REQUIRED = "At least one variant must be added.";
    public static final String PRODUCT_PRICE_INVALID = "The price must be greater than zero.";
    public static final String PRODUCT_VARIANT_STOCK_REQUIRED = "Each variant must include stock for at least one branch.";
    public static final String PRODUCT_STOCK_NEGATIVE = "Stock value cannot be negative.";
    public static final String PRODUCTS_RETRIEVED = "Products retrieved successfully";
    public static final String PRODUCT_SEARCH_COMPLETED = "Products search completed successfully";
    public static final String PRODUCTS_WITH_STOCK_RETRIEVED = "Active products with stock retrieved successfully";
    public static final String STOCK_NOT_FOUND = "Stock not found for the specified branch and variant.";
    public static final String INVALID_ATTRIBUTES_JSON = "Invalid JSON format for product variant attributes.";
    public static final String BRANCH_NOT_FOUND = "Branch not found or does not belong to the tenant.";
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
    public static final String CREATING_PRODUCT = "Creating product '%s'";
    public static final String PRODUCT_CREATED_SUCCESS = "Product created successfully (ID: %d)";
    public static final String FETCHING_PRODUCT_BY_ID = "Fetching product by ID: %d";
    public static final String PRODUCT_FETCH_SUCCESS = "Product retrieved successfully (ID: %d)";
    public static final String DELETING_PRODUCT = "Deleting product with ID: %d";
    public static final String PRODUCT_DELETED_SUCCESS = "Product deleted successfully (ID: %d)";
    public static final String PRODUCT_NOT_FOUND_WITH_ID = "Product not found with ID: %d";
    public static final String BRANCH_NOT_FOUND_WITH_ID = "Branch not found with ID: %d";
    public static final String RESERVING_STOCK = "Reserving stock for order items";
    public static final String RESTORING_STOCK = "Restoring stock after cancellation";
    public static final String STOCK_NOT_FOUND_WITH_IDS = "Stock not found for variant %d in branch %d";
    public static final String LOG_PRODUCT_CREATED = "Product created successfully. ID: {}";
    public static final String LOG_PRODUCT_CHECK = "[{}] Checking if product '{}' exists";
    public static final String CREATING_PRODUCT_LOG = "[{}] Creating product '{}'";
    public static final String LOG_CHECKING_STOCK = "[{}] Checking stock for variant {} in branch {}";
    public static final String LOG_STOCK_STATUS = "[{}] Current stock: {}, requested: {}, available: {}";
    public static final String LOG_MEDIA_SERVICE_ERROR = "Error calling media-service: {}";
    public static final String LOG_MEDIA_FETCH_WARNING = "Unable to fetch images for variant {}: {}";
}
