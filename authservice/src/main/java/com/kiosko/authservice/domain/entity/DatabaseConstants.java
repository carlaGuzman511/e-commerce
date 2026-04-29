package com.kiosko.authservice.domain.entity;

public final class DatabaseConstants {

    private DatabaseConstants() {}
    
    public static final String USERS_TABLE = "users";
    public static final String ROLES_TABLE = "roles";
    public static final String PERMISSIONS_TABLE = "permissions";
    public static final String USER_ROLE_TABLE = "user_role";
    public static final String USER_PERMISSION_TABLE = "user_permission";
    public static final String REFRESH_TOKENS_TABLE = "refresh_tokens";
    public static final String EMAIL_VERIFICATION_TOKENS_TABLE = "email_verification_tokens";
    public static final String LOG_TABLE = "log";

    public static final String ID_COLUMN = "id";
    public static final String TENANT_ID_COLUMN = "tenant_id";
    public static final String CREATED_AT_COLUMN = "created_at";
    public static final String UPDATED_AT_COLUMN = "updated_at";
    public static final String USER_ID_COLUMN = "user_id";
    public static final String ROLE_ID_COLUMN = "role_id";
    public static final String PERMISSION_ID_COLUMN = "permission_id";

    public static final String EMAIL_COLUMN = "email";
    public static final String USERNAME_COLUMN = "username";
    public static final String PASSWORD_COLUMN = "password";
    public static final String IMAGE_COLUMN = "image";
    public static final String ACTIVE_COLUMN = "active";
    public static final String EMAIL_VERIFIED_COLUMN = "email_verified";
    public static final String AUTH_PROVIDER_COLUMN = "auth_provider";
    public static final String PROVIDER_ID_COLUMN = "provider_id";
    public static final String FAILED_LOGIN_ATTEMPTS_COLUMN = "failed_login_attempts";
    public static final String LAST_LOGIN_AT_COLUMN = "last_login_at";
    public static final String LAST_LOGIN_IP_COLUMN = "last_login_ip";

    public static final String NAME_COLUMN = "name";
    public static final String DESCRIPTION_COLUMN = "description";

    public static final String RESOURCE_COLUMN = "resource";
    public static final String ACTION_COLUMN = "action";

    public static final String RECORD_ID_COLUMN = "record_id";
    public static final String TABLE_NAME_COLUMN = "table_name";
    public static final String PREVIOUS_VALUE_COLUMN = "previous_value";
    public static final String CURRENT_VALUE_COLUMN = "current_value";
    public static final String ACTION_LOG_COLUMN = "action";
    public static final String DESCRIPTION_LOG_COLUMN = "description";
    public static final String IP_ADDRESS_COLUMN = "ip_address";
    public static final String USER_AGENT_COLUMN = "user_agent";

    public static final String TOKEN_COLUMN = "token";
    public static final String EXPIRES_AT_COLUMN = "expires_at";
    public static final String REVOKED_COLUMN = "revoked";
    public static final String USED_COLUMN = "used";
}
