package com.tuempresa.proyecto.dto;

import com.tuempresa.proyecto.entity.EstadoPrograma;
import com.tuempresa.proyecto.entity.Programa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaResponseDTO {

    private Long id;
    private String nombre;
    private String descripcion;
    private String area;
    private LocalDate fechaInicio;
    private LocalDate fechaTermino;
    private Integer horasRequeridas;
    private EstadoPrograma estado;

    private Long totalAlumnos;
    private Long totalProfesores;

    // Método de conversión desde entidad a DTO
    public static ProgramaResponseDTO fromEntity(Programa programa) {
        ProgramaResponseDTO dto = new ProgramaResponseDTO();
        dto.setId(programa.getId());
        dto.setNombre(programa.getNombre());
        dto.setDescripcion(programa.getDescripcion());
        dto.setArea(programa.getArea());
        dto.setFechaInicio(programa.getFechaInicio());
        dto.setFechaTermino(programa.getFechaTermino());
        dto.setHorasRequeridas(programa.getHorasRequeridas());
        dto.setEstado(programa.getEstado());
        dto.setTotalAlumnos(0L);
        dto.setTotalProfesores(0L);
        return dto;
    }
}