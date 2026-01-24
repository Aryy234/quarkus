package uce.edu.web.api.interfaces;

import java.util.List;

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
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import uce.edu.web.api.aplication.EstudianteService;
import uce.edu.web.api.aplication.HijoService;
import uce.edu.web.api.domain.Estudiante;
import uce.edu.web.api.domain.Hijo;

@Path("/estudiantes")
@Produces(MediaType.APPLICATION_JSON)
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @Inject
    private HijoService hijoService;

    @GET
    @Path("")
    public Response listarTodos() {
        List<Estudiante> estudiantes = estudianteService.listarTodos();
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/{id}")
    public Response consultarPorId(@PathParam("id") Integer id) {
        Estudiante estudiante = estudianteService.consultarPorId(id);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(estudiante).build();
    }

    
    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response guardarEstudiante(Estudiante estudiante) {
        estudianteService.crearEstudiante(estudiante);
        return Response.status(Response.Status.CREATED).entity(estudiante).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEstudiante(@PathParam("id") Integer id, Estudiante estudiante) {
        Estudiante estudianteActualizado = estudianteService.consultarPorId(id);
        if (estudianteActualizado == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.actualizarEstudiante(id, estudiante);
        return Response.ok(estudianteActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    public Response eliminarEstudiante(@PathParam("id") Integer id) {
        Estudiante estudiante = estudianteService.consultarPorId(id);
        if (estudiante == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.eliminarEstudiante(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/provincia")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorProvincia(@QueryParam("provincia") String provincia) {
        List<Estudiante> estudiantes = estudianteService.buscarPorProvincia(provincia);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/provincia-genero")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarPorProvinciaGenero(
            @QueryParam("provincia") String provincia,
            @QueryParam("genero") String genero) {
        List<Estudiante> estudiantes = estudianteService.buscarPorProvinciaGenero(provincia, genero);
        return Response.ok(estudiantes).build();
    }
    @PATCH
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        Estudiante estudianteExistente = estudianteService.consultarPorId(id);
        if (estudianteExistente == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        estudianteService.actualizarParcial(id, estudiante);
        return Response.ok(estudianteExistente).build();
    }

    @GET
    @Path("/ordenados")
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
        List<Estudiante> estudiantes = estudianteService.listarOrdenados(campo, orden);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/rango-edad")
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
        List<Estudiante> estudiantes = estudianteService.buscarPorRangoEdad(edadMin, edadMax);
        return Response.ok(estudiantes).build();
    }

    @GET
    @Path("/{idEstudiante}/hijos")
    public List<Hijo> buscarPorIdEstudiante(@PathParam("idEstudiante") Long idEstudiante) {
        return this.hijoService.buscarPorIdEstudiante(idEstudiante);
    }

}
