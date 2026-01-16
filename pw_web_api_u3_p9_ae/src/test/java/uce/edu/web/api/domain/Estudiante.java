package uce.edu.web.api.domain;

import java.time.LocalDateTime;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Estudiante")
@SequenceGenerator(name = "estudiante_seq", sequenceName = "estudiante_secuencia", allocationSize = 1)    
public class Estudiante extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estudiante_seq")
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;



}
