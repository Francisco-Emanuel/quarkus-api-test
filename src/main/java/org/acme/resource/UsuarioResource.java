package org.acme.resource;

import java.time.Duration;
import java.util.List;

import org.acme.dto.LoginRequestDTO;
import org.acme.entity.Usuario;
import org.acme.service.UsuarioService;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.security.Authenticated;
import io.smallrye.jwt.build.Jwt;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {
    @Inject
    UsuarioService usuarioService;

    @GET
    @Authenticated
    public List<Usuario> listarUsuarios() {
        return usuarioService.listarUsuarios();
    }

    @GET
    @Path("/{id}")
    @Authenticated
    public Response buscarUsuarioPorId(@PathParam("id") Long id) {
        return usuarioService.buscarUsuarioPorId(id)
                .map(usuario -> Response.ok(usuario).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @POST
    @Path("/register")
    public Response resgister(@Valid LoginRequestDTO request) {
        try {
            Usuario usuarioCriado = usuarioService.createUser(request.name, request.password);
            return Response.status(Status.CREATED).entity(usuarioCriado).build();
        } catch (NotFoundException e) {
            return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    public Response login(LoginRequestDTO request) {
        Usuario user = Usuario.find("name", request.name).firstResult();
        if (user != null && BcryptUtil.matches(request.password, user.password)) {
            String token = Jwt.issuer("https://acme.org/issuer")
                    .upn(user.name)
                    .expiresIn(Duration.ofHours(1))
                    .sign();

            return Response.ok("{\"token\": \"" + token + "\"}").build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @PUT
    @Path("/{id}")
    @Authenticated
    public Response atualizarUsuario(@PathParam("id") Long id, @Valid LoginRequestDTO dto) {
        return usuarioService.atualizarUsuario(id, dto)
                .map(usuario -> Response.ok(usuario).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    @Authenticated
    public Response deletarUsuario(@PathParam("id") Long id) {
        if (usuarioService.deletarUsuario(id)) {
            return Response.status(Status.NO_CONTENT).build();
        }
        return Response.status(Status.NOT_FOUND).build();
    }
}
