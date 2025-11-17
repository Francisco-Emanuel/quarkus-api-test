package org.acme.resource;

import java.util.List;
import java.util.Optional;

import org.acme.dto.ChamadoRequestDTO;
import org.acme.entity.Chamado;
import org.acme.entity.Departamento;
import org.acme.service.ChamadoService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/chamados")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class ChamadoResource {

    @Inject
    ChamadoService chamadoService;

    @Inject JsonWebToken jwt;

    @GET
    public List<Chamado> listarChamados() {
        return Chamado.listAll();
    }

    @POST
    public Response abrirChamado(@Valid ChamadoRequestDTO chamado) {
        String username = jwt.getName();
        Chamado chamadoCriado =  chamadoService.abrirChamado(chamado, username);

        return Response.ok().entity(chamadoCriado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarChamado(@PathParam("id") Long id, @Valid ChamadoRequestDTO dadosNovos) {
        Optional<Chamado> chamadoAtuaOpt = chamadoService.atualizarChamado(id, dadosNovos);

        return chamadoAtuaOpt
                .map(chmd -> Response.ok(chmd).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response delChmd(@PathParam("id") Long id) {
        boolean deletado = chamadoService.delCha(id);

        if (deletado) {
            return Response.status(Status.NO_CONTENT).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
