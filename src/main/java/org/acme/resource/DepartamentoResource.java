package org.acme.resource;

import java.util.List;

import org.acme.entity.Chamado;
import org.acme.entity.Departamento;
import org.acme.service.DepartamentoService;

import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/departamentos")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DepartamentoResource {

    @Inject
    DepartamentoService departamentoService;

    @GET
    @Authenticated
    public List<Departamento> listarDepartamentos() {
        return Departamento.listAll();
    }

    @POST
    @Path("/criar")
    public Response criarChamado(@Valid Departamento departamento) {
        Departamento departamentoCriado = departamentoService.criarDP(departamento);
        return Response.ok().entity(departamentoCriado).build();
    }

}
