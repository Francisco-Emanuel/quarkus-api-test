package org.acme.resource;

import java.util.List;

import org.acme.entity.Chamado;
import org.acme.service.ChamadoService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/chamados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ChamadoResource {

    @Inject
    ChamadoService chamadoService;

    @Inject JsonWebToken jwt;

    @GET
    @Authenticated
    public List<Chamado> listarChamados() {
        return Chamado.listAll();
    }

    @POST
    @Authenticated
    public Response abrirChamado(@Valid Chamado chamado) {
        String username = jwt.getName();
        Chamado chamadoCriado =  chamadoService.abrirChamado(chamado, username);

        return Response.ok().entity(chamadoCriado).build();
    }
}
