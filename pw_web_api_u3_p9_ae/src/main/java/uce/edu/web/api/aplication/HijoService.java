package uce.edu.web.api.aplication;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.domain.Hijo;
import uce.edu.web.api.representation.HijoRepresentation;
import uce.edu.web.api.infraestructure.HijoRepository;

@ApplicationScoped
public class HijoService {
    
    @Inject
    private HijoRepository hijoRepository;

    public List<HijoRepresentation> buscarPorIdEstudiante(Long estudianteId) {
        return this.hijoRepository.buscarPorIdEstudiante(estudianteId)
            .stream()
            .map(this::mapperToHr)
            .toList();
    }

    private HijoRepresentation mapperToHr(Hijo hijo) {
        HijoRepresentation hr = new HijoRepresentation();
        hr.setId(hijo.getId());
        hr.setNombre(hijo.getNombre());
        hr.setApellido(hijo.getApellido());
        hr.setFechaNacimiento(hijo.getFechaNacimiento());
        hr.setProvincia(hijo.getProvincia());
        hr.setGenero(hijo.getGenero());
        return hr;
    }


}
