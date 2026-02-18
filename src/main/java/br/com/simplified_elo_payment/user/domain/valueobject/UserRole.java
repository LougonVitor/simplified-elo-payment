package br.com.simplified_elo_payment.user.domain.valueobject;

public enum UserRole {
    ADMIN("ADMIN"),
    COMMON("COMMON");

    private final String role;

    UserRole (String role) {
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }

    public static UserRole getEnumValue (String role) {
        return UserRole.valueOf(role.toUpperCase());
    }
}