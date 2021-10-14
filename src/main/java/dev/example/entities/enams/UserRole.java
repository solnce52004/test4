package dev.example.entities.enams;

public enum UserRole {
    ADMIN("admin"),
    QUEST("quest"),
    SUPPORT("support");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}