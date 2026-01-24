package uce.edu.web.api.aplication;

import java.time.LocalDateTime;
import java.util.List;

import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import uce.edu.web.api.domain.Estudiante;
import uce.edu.web.api.infraestructure.EstudianteRepository;
import uce.edu.web.api.representation.EstudianteRepresentation;

@ApplicationScoped
public class EstudianteService {

    @Inject
    private EstudianteRepository estudianteRepository;

    public List<EstudianteRepresentation> listarTodos() {
        return estudianteRepository.listAll().stream()
            .map(this::mapperToEr)
            .toList();
    }

    public EstudianteRepresentation consultarPorId(Integer id) {
        Estudiante estudiante = estudianteRepository.findById(id.longValue());
        return estudiante != null ? this.mapperToEr(estudiante) : null;
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
            if (estudiante.getProvincia() != null) {
                estudianteExistente.setProvincia(estudiante.getProvincia());
            }
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
            if (estudiante.getProvincia() != null) {
                estudianteExistente.setProvincia(estudiante.getProvincia());
            }
        }
    }

    public List<EstudianteRepresentation> buscarPorProvincia(String provincia) {
        return estudianteRepository.find("provincia", provincia).list().stream()
            .map(this::mapperToEr)
            .toList();
    }

    public List<EstudianteRepresentation> buscarPorProvinciaGenero(String provincia, String genero) {
        return estudianteRepository.find("provincia = ?1 and genero = ?2", provincia, genero).list().stream()
            .map(this::mapperToEr)
            .toList();
    }

    public List<EstudianteRepresentation> listarOrdenados(String campo, String orden) {
        Sort.Direction direction = "desc".equalsIgnoreCase(orden) ? Sort.Direction.Descending : Sort.Direction.Ascending;
        Sort sort = Sort.by(campo, direction);
        return estudianteRepository.listAll(sort).stream()
            .map(this::mapperToEr)
            .toList();
    }

    public List<EstudianteRepresentation> buscarPorRangoEdad(Integer edadMin, Integer edadMax) {
        LocalDateTime fechaMax = LocalDateTime.now().minusYears(edadMin);
        LocalDateTime fechaMin = LocalDateTime.now().minusYears(edadMax + 1);
        return estudianteRepository.find("fechaNacimiento >= ?1 and fechaNacimiento <= ?2", fechaMin, fechaMax).list().stream()
            .map(this::mapperToEr)
            .toList();
    }

    private EstudianteRepresentation mapperToEr(Estudiante estudiante){
        EstudianteRepresentation estudianteRep = new EstudianteRepresentation();
        estudianteRep.setId(estudiante.getId());
        estudianteRep.setNombre(estudiante.getNombre());
        estudianteRep.setApellido(estudiante.getApellido());
        estudianteRep.setFechaNacimiento(estudiante.getFechaNacimiento());
        estudianteRep.provincia = estudiante.getProvincia();
        estudianteRep.genero = estudiante.getGenero();
        return estudianteRep;
    }

}
