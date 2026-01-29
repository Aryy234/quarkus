package uce.edu.web.api.representation;

import java.time.LocalDateTime;
import java.util.List;

public class EstudianteRepresentation {
    private Long id;
    private String nombre;
    private String apellido;
    private LocalDateTime fechaNacimiento;
    public String provincia;
    public String genero;
    //http://locahost:8080/estudiantes/1/hijos

    public List<LinkDto> hijosLink;

    // Getters y Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public LocalDateTime getFechaNacimiento() {
        return fechaNacimiento;
    }
    public void setFechaNacimiento(LocalDateTime fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
    

}
