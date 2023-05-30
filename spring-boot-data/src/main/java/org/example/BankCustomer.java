package org.example;

import jakarta.validation.constraints.NotNull;

public record BankCustomer(@NotNull String name, @NotNull int age, @NotNull String city,@NotNull String state, @NotNull String profession) {
}
