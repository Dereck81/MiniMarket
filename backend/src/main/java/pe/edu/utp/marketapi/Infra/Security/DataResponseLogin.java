package pe.edu.utp.marketapi.Infra.Security;

import pe.edu.utp.marketapi.Domain.Usuario.DataListUsuario;

public record DataResponseLogin(
        DataListUsuario user,
        String token
) {
}
