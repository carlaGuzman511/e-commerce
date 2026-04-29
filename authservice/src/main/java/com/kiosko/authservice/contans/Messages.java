package com.kiosko.authservice.contans;

public class Messages {

    public static class Auth {
        public static final String LOGIN_ATTEMPT = "Login attempt for email: %s";
        public static final String LOGIN_SUCCESS = "Login successful for user: %s";
        public static final String INVALID_CREDENTIALS = "Invalid email or password";
        public static final String ACCOUNT_INACTIVE = "Account is inactive";
        public static final String ACCOUNT_LOCKED = "Account locked for user: %s after %d failed attempts";

        public static final String REGISTRATION_ATTEMPT = "Registration attempt for email: %s";
        public static final String EMAIL_ALREADY_REGISTERED = "Email already registered";
        public static final String USERNAME_ALREADY_TAKEN = "Username already taken";
        public static final String CLIENT_ROLE_NOT_FOUND = "CLIENT role not found";
        public static final String REGISTRATION_SUCCESS = "User registered successfully: %s (email verification pending)";

        public static final String INVALID_REFRESH_TOKEN = "Invalid or expired refresh token";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String REFRESH_TOKEN_NOT_FOUND = "Refresh token not found";
        public static final String REFRESH_TOKEN_INVALID = "Refresh token is expired or revoked";
        public static final String TOKEN_REFRESHED = "Token refreshed for user: %s";

        public static final String LOGOUT_SUCCESS = "User logged out, refresh token revoked";
    }

    public static class User {
        public static final String USER_NOT_FOUND = "User not found";
        public static final String EMAIL_ALREADY_USED = "Email is already in use";
        public static final String USERNAME_ALREADY_USED = "Username is already in use";
        public static final String PROFILE_UPDATED = "User profile updated successfully";
        public static final String NOT_FOUND = "User not found";
        public static final String USERNAME_ALREADY_USED_TAKEN = "Username already taken";
        public static final String NOT_FOUND_BY_ID = "User not found with id: %d";;
        public static final String CREATE_ATTEMPT = "Creating new user: %s";
        public static final String CREATE_SUCCESS = "User created successfully: %s";

        public static final String UPDATE_ATTEMPT = "Updating user: %d";
        public static final String UPDATE_SUCCESS = "User updated successfully: %s";

        public static final String DELETE_ATTEMPT = "Deleting user: %d";
        public static final String DELETE_SUCCESS = "User deactivated successfully: %s";

        public static final String STATUS_TOGGLE_ATTEMPT = "Toggling user status: %d to %s";
        public static final String STATUS_TOGGLE_SUCCESS = "User status changed to %s: %s";

        public static final String ASSIGN_ROLE_ATTEMPT = "Assigning roles to user: %d";
        public static final String ASSIGN_ROLE_SUCCESS = "Roles assigned successfully to user: %s";
        public static final String REMOVE_ROLE_ATTEMPT = "Removing role %d from user %d";
        public static final String REMOVE_ROLE_SUCCESS = "Role removed successfully from user: %s";

        public static final String ASSIGN_PERMISSION_ATTEMPT = "Assigning permissions to user: %d";
        public static final String ASSIGN_PERMISSION_SUCCESS = "Permissions assigned successfully to user: %s";
        public static final String REMOVE_PERMISSION_ATTEMPT = "Removing permission %d from user %d";
        public static final String REMOVE_PERMISSION_SUCCESS = "Permission removed successfully from user: %s";

        public static final String SEARCH_ATTEMPT = "Searching users with query: %s";
    }

    public static class Role {
        public static final String ROLE_NOT_FOUND = "Role not found";
        public static final String ROLE_ASSIGNED = "Role assigned successfully";
        public static final String ROLE_ASSIGNMENT_FAILED = "Failed to assign role to user";
        public static final String CREATE_ATTEMPT = "Creating new role: %s";
        public static final String CREATE_SUCCESS = "Role created successfully: %s";
        public static final String UPDATE_ATTEMPT = "Updating role: %d";
        public static final String UPDATE_SUCCESS = "Role updated successfully: %s";
        public static final String DELETE_ATTEMPT = "Deleting role: %d";
        public static final String DELETE_SUCCESS = "Role deleted successfully: %s";
        public static final String ASSIGN_ATTEMPT = "Assigning permissions to role: %d";
        public static final String ASSIGN_SUCCESS = "Permissions assigned successfully to role: %s";
        public static final String REMOVE_ATTEMPT = "Removing permission %d from role %d";
        public static final String REMOVE_SUCCESS = "Permission removed successfully from role: %s";
        public static final String NOT_FOUND_BY_ID = "Role not found with id: %d";
        public static final String ALREADY_EXISTS = "Role already exists with name: %s";
        public static final String DELETE_CONFLICT = "Cannot delete role that is assigned to users. Please remove this role from all users first.";
    }

    public static class Token {
        public static final String TOKEN_EXPIRED = "Token has expired";
        public static final String TOKEN_INVALID = "Token is invalid";
        public static final String TOKEN_GENERATED = "Token generated successfully";
    }

    public static class Global {
        public static final String UNEXPECTED_ERROR = "An unexpected error occurred";
        public static final String ACCESS_DENIED = "Access denied";
        public static final String OPERATION_SUCCESS = "Operation completed successfully";
    }
    public static class Permission {
        public static final String CREATE_ATTEMPT = "Creating new permission: %s:%s";
        public static final String CREATE_SUCCESS = "Permission created successfully: %s";
        public static final String UPDATE_ATTEMPT = "Updating permission: %d";
        public static final String UPDATE_SUCCESS = "Permission updated successfully: %s";
        public static final String DELETE_ATTEMPT = "Deleting permission: %d";
        public static final String DELETE_SUCCESS = "Permission deleted successfully: %s";
        public static final String NOT_FOUND_BY_ID = "Permission not found with id: %d";
        public static final String ALREADY_EXISTS = "Permission already exists: %s:%s";
        public static final String DELETE_CONFLICT = "Cannot delete permission that is assigned to roles. Please remove this permission from all roles first.";
    }
    public static class Profile {
        public static final String UPDATE_ATTEMPT = "Updating profile for user: %s";
        public static final String UPDATE_SUCCESS = "Profile updated successfully for user: %s";
        public static final String PASSWORD_CHANGE_ATTEMPT = "Changing password for user: %s";
        public static final String PASSWORD_CHANGE_SUCCESS = "Password changed successfully for user: %s";
        public static final String INVALID_CURRENT_PASSWORD = "Current password is incorrect";
        public static final String PASSWORDS_MUST_DIFFER = "New password must be different from current password";
    }


}
