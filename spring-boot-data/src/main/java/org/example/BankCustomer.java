package org.example;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public record BankCustomer(@NotNull String name, @NotNull int age, @NotNull String city,@NotNull String state, @NotNull String profession) implements Serializable {
}
