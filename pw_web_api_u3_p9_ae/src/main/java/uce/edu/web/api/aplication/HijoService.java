package uce.edu.web.api.aplication;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import uce.edu.web.api.domain.Hijo;
import uce.edu.web.api.infraestructure.HijoRepository;

@ApplicationScoped
public class HijoService {
    
    @Inject
    private HijoRepository hijoRepository;

    public List<Hijo> buscarPorIdEstudiante(Long estudianteId) {
        return this.hijoRepository.buscarPorIdEstudiante(estudianteId);
    }


}
