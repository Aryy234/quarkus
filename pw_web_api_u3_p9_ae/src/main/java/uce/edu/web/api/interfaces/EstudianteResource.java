package uce.edu.web.api.interfaces;

import java.util.List;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import uce.edu.web.api.aplication.EstudianteService;
import uce.edu.web.api.aplication.HijoService;
import uce.edu.web.api.domain.Estudiante;
import uce.edu.web.api.representation.EstudianteRepresentation;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;
    
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("")
    @RolesAllowed("admin")
    public Response listarTodos() {
        List<EstudianteRepresentation> estudiantes = estudianteService.listarTodos();
        return Response.ok(this.construirLinks(estudiantes)).build();
    }
    private List<EstudianteRepresentation> construirLinks(List<EstudianteRepresentation> lista) {
        lista.forEach(this::construirLinks);
        return lista;
    }

    @GET
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response consultarPorId(@PathParam("id") Integer id) {
        EstudianteRepresentation estudiante = estudianteService.consultarPorId(id);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        
        return this.construirLinks(estudiante)
            != null ? Response.ok(this.construirLinks(estudiante)).build()
            : Response.status(Response.Status.NOT_FOUND).build();
    }

    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response guardarEstudiante(Estudiante estudiante) {
        estudianteService.crearEstudiante(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response actualizarEstudiante(@PathParam("id") Integer id, Estudiante estudiante) {
        EstudianteRepresentation estudianteActualizado = estudianteService.consultarPorId(id);
        if (estudianteActualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.actualizarEstudiante(id, estudiante);
        return Response.ok(estudianteActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @RolesAllowed("admin")
    public Response eliminarEstudiante(@PathParam("id") Integer id) {
        EstudianteRepresentation estudiante = estudianteService.consultarPorId(id);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.eliminarEstudiante(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/provincia")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response buscarPorProvincia(@QueryParam("provincia") String provincia) {
        List<EstudianteRepresentation> estudiantes = estudianteService.buscarPorProvincia(provincia);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/provincia-genero")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response buscarPorProvinciaGenero(
            @QueryParam("provincia") String provincia,
            @QueryParam("genero") String genero) {
        List<EstudianteRepresentation> estudiantes = estudianteService.buscarPorProvinciaGenero(provincia, genero);
        return Response.ok(estudiantes).build();
    }
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed("admin")
    public Response actualizarParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        EstudianteRepresentation estudianteExistente = estudianteService.consultarPorId(id);
        if (estudianteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.actualizarParcial(id, estudiante);
        return Response.ok(estudianteExistente).build();
    }

    @GET
    @Path("/ordenados")
    @RolesAllowed("admin")
    public Response listarOrdenados(
            @QueryParam("campo") String campo,
            @QueryParam("orden") String orden) {
        // Valores por defecto
        if (campo == null || campo.isEmpty()) {
            campo = "id";
        }
        if (orden == null || orden.isEmpty()) {
            orden = "asc";
        }
        List<EstudianteRepresentation> estudiantes = estudianteService.listarOrdenados(campo, orden);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/rango-edad")
    @RolesAllowed("admin")
    public Response buscarPorRangoEdad(
            @QueryParam("min") Integer edadMin,
            @QueryParam("max") Integer edadMax) {
        // Validaciones básicas
        if (edadMin == null) {
            edadMin = 0;
        }
        if (edadMax == null) {
            edadMax = 100;
        }
        List<EstudianteRepresentation> estudiantes = estudianteService.buscarPorRangoEdad(edadMin, edadMax);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/{idEstudiante}/hijos")
    @RolesAllowed("admin")
    public List<uce.edu.web.api.representation.HijoRepresentation> buscarPorIdEstudiante(@PathParam("idEstudiante") Long idEstudiante) {
        return this.hijoService.buscarPorIdEstudiante(idEstudiante);
    }

    private EstudianteRepresentation construirLinks(EstudianteRepresentation er){
        String self = this.uriInfo.getBaseUriBuilder()
            .path(EstudianteResource.class)
            .path(EstudianteResource.class, "consultarPorId")
            .build(er.getId())
            .toString();

        String hijos = this.uriInfo.getBaseUriBuilder()
            .path(EstudianteResource.class)
            .path(EstudianteResource.class, "buscarPorIdEstudiante")
            .build(er.getId())
            .toString();

        er.hijosLink = List.of(
            new uce.edu.web.api.representation.LinkDto(self, "self"),
            new uce.edu.web.api.representation.LinkDto(hijos, "hijos")
        );
        return er;
    }

}
