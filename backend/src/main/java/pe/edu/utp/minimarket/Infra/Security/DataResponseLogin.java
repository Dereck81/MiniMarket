package pe.edu.utp.minimarket.Infra.Security;

import pe.edu.utp.minimarket.Domain.Usuario.DataListUsuario;

public record DataResponseLogin(
        DataListUsuario user,
        String token
) {
}
