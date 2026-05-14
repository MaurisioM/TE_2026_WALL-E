package com.tuempresa.proyecto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaRequestDTO {

    @NotBlank(message = "El nombre es obligatorio y no puede estar vacío")
    private String nombre;

    private String descripcion;

    private String area;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha de término es obligatoria")
    private LocalDate fechaTermino;

    @NotNull(message = "Las horas requeridas son obligatorias")
    @Min(value = 1, message = "Las horas requeridas deben ser al menos 1")
    private Integer horasRequeridas;
}