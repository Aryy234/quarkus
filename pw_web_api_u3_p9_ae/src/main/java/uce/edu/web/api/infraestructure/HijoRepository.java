package uce.edu.web.api.infraestructure;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import uce.edu.web.api.domain.Hijo;

@ApplicationScoped
public class HijoRepository implements PanacheRepository<Hijo> {
    public List<Hijo> buscarPorIdEstudiante(Long estudianteId) {
        return list("estudiante.id", estudianteId);
    }

}
