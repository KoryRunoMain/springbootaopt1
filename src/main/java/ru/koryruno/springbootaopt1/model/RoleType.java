package ru.koryruno.springbootaopt1.model;

import java.util.Optional;

public enum RoleType {

    USER, ADMIN;

    public static Optional<RoleType> from(String stringTypeAction) {
        for (RoleType state : values()) {
            if (state.name().equalsIgnoreCase(stringTypeAction)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}
