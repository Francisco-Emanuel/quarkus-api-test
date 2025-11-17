package org.acme.resource;

import java.util.List;
import java.util.Optional;

import org.acme.entity.Chamado;
import org.acme.entity.Departamento;
import org.acme.service.DepartamentoService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/departamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Authenticated
public class DepartamentoResource {

    @Inject
    DepartamentoService departamentoService;

    @GET
    public List<Departamento> listarDepartamentos() {
        return Departamento.listAll();
    }

    @POST
    public Response criarDepartamento(@Valid Departamento departamento) {
        Departamento departamentoCriado = departamentoService.criarDP(departamento);
        return Response.status(Status.CREATED).entity(departamentoCriado).build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizarDP(@PathParam("id") Long id, @Valid Departamento dadosNovos) {
        Optional<Departamento> deptoAtualizadoOpt = departamentoService.atualizarDepartamento(id, dadosNovos);

        return deptoAtualizadoOpt
                .map(depto -> Response.ok(depto).build())
                .orElse(Response.status(Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("/{id}")
    public Response deletarDP(@PathParam("id") Long id) {
        boolean deletado = departamentoService.deletarDP(id);

        if (deletado) {
            return Response.status(Status.NO_CONTENT).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
