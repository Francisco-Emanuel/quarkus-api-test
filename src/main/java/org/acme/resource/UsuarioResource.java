package org.acme.resource;

import java.time.Duration;

import org.acme.dto.LoginRequestDTO;
import org.acme.entity.Usuario;
import org.acme.service.UsuarioService;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @Inject
    UsuarioService usuarioService;

    @POST
    @Path("/register")
    public Response resgister(LoginRequestDTO request) {
        usuarioService.createUser(request.name, request.password);
        return Response.status(Response.Status.CREATED).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO request) {
        Usuario user = Usuario.find("username", request.name).firstResult();
        if (user != null && BcryptUtil.matches(request.password, user.password)) {
            String token = Jwt.issuer("https://acme.org/issuer")
                              .upn(user.name)
                              .expiresIn(Duration.ofHours(1))
                              .sign();

            return Response.ok("{\"token\": \"" + token + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
