package pe.edu.utp.minimarket.Infra.Security;

import jakarta.validation.constraints.NotNull;

public record DataLogin (
        @NotNull String email,
        @NotNull String password
) {
}
