package com.challenge.literatura.repository;

import com.challenge.literatura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findByNombreAndApellido(String nombre, String apellido);

    List<Autor> findByAnoNacimientoLessThanEqualAndAnoFallecimientoGreaterThanEqual(int anoNacimiento, int anoFallecimiento);

    List<Autor> findByAnoNacimientoLessThanEqualAndAnoFallecimientoIsNull(int anoNacimiento);
}
