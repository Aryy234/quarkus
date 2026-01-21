package uce.edu.web.api.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import uce.edu.web.api.aplication.EstudianteService;
import uce.edu.web.api.domain.Estudiante;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<?> listarTodos() {
        return estudianteService.listarTodos();
    }

    @GET
    @Path("/consultar/{id}")
    public Estudiante consultarPorId(@PathParam("id") Integer id) {
        return estudianteService.consultarPorId(id);
    }

    
    @POST
    @Path("/crear")
    public void guardarEstudiante(Estudiante estudiante) {
        estudianteService.crearEstudiante(estudiante);
    }

    @PUT
    @Path("/actualizar/{id}")
    public void actualizarEstudiante(@PathParam("id") Integer id, Estudiante estudiante) {
        estudianteService.actualizarEstudiante(id, estudiante);
    }

    @DELETE
    @Path("/eliminar/{id}")
    public void eliminarEstudiante(@PathParam("id") Integer id) {
        estudianteService.eliminarEstudiante(id);
    }
//
    @PATCH
    @Path("/actualizar-parcial/{id}")
    public void actualizarParcial(@PathParam("id") Integer id, Estudiante estudiante) {
        estudianteService.actualizarParcial(id, estudiante);
    }

    @GET
    @Path("/ordenados")
    public List<Estudiante> listarOrdenados(
            @QueryParam("campo") String campo,
            @QueryParam("orden") String orden) {
        // Valores por defecto
        if (campo == null || campo.isEmpty()) {
            campo = "id";
        }
        if (orden == null || orden.isEmpty()) {
            orden = "asc";
        }
        return estudianteService.listarOrdenados(campo, orden);
    }

    @GET
    @Path("/rango-edad")
    public List<Estudiante> buscarPorRangoEdad(
            @QueryParam("min") Integer edadMin,
            @QueryParam("max") Integer edadMax) {
        // Validaciones básicas
        if (edadMin == null) {
            edadMin = 0;
        }
        if (edadMax == null) {
            edadMax = 100;
        }
        return estudianteService.buscarPorRangoEdad(edadMin, edadMax);
    }

}
