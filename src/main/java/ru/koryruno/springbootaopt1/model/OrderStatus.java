package ru.koryruno.springbootaopt1.model;

import java.util.Optional;

public enum OrderStatus {

    PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED, COMPLETED;

    public static Optional<OrderStatus> from(String stringOrderAction) {
        for (OrderStatus state : values()) {
            if (state.name().equalsIgnoreCase(stringOrderAction)) {
                return Optional.of(state);
            }
        }
        return Optional.empty();
    }

}
