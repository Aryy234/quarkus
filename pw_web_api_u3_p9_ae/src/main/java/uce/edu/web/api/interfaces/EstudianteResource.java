package uce.edu.web.api.interfaces;

import java.util.List;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import uce.edu.web.api.aplication.EstudianteService;

@Path("/estudiantes")
public class EstudianteResource {

    @Inject
    private EstudianteService estudianteService;

    @GET
    @Path("/todos")
    public List<?> listarTodos() {
        return estudianteService.listarTodos();
    }

}
