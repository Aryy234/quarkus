package uce.edu.web.api.aplication;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.domain.Estudiante;
import uce.edu.web.api.infraestructure.EstudianteRepository;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<Estudiante> listarTodos() {
        return estudianteRepository.listAll();
    }

    public Estudiante consultarPorId(Integer id) {
        return estudianteRepository.findById(id.longValue());
    }

    @Transactional
    public void crearEstudiante(Estudiante estudiante) {
        estudianteRepository.persist(estudiante);
    }

    @Transactional
    public void actualizarEstudiante(Integer id, Estudiante estudiante) {
        Estudiante estudianteExistente = estudianteRepository.findById(id.longValue());
        if (estudianteExistente != null) {
            estudianteExistente.setNombre(estudiante.getNombre());
            estudianteExistente.setApellido(estudiante.getApellido());
            estudianteExistente.setFechaNacimiento(estudiante.getFechaNacimiento());
            // JPA detecta automáticamente los cambios y los persiste
        }
    }

    @Transactional
    public void eliminarEstudiante(Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id.longValue());
        if (estudiante != null) {
            estudianteRepository.delete(estudiante);
        }
    }

    @Transactional
    public void actualizarParcial(Integer id, Estudiante estudiante) {
        Estudiante estudianteExistente = estudianteRepository.findById(id.longValue());
        if (estudianteExistente != null) {
            // Solo actualiza los campos que no son nulos
            if (estudiante.getNombre() != null) {
                estudianteExistente.setNombre(estudiante.getNombre());
            }
            if (estudiante.getApellido() != null) {
                estudianteExistente.setApellido(estudiante.getApellido());
            }
            if (estudiante.getFechaNacimiento() != null) {
                estudianteExistente.setFechaNacimiento(estudiante.getFechaNacimiento());
            }
        }
    }

}
