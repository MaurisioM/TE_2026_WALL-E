package com.tuempresa.proyecto.repository;

import com.tuempresa.proyecto.entity.EstadoPrograma;
import com.tuempresa.proyecto.entity.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long> {

    List<Programa> findByEstado(EstadoPrograma estado);

    boolean existsByNombre(String nombre);
}