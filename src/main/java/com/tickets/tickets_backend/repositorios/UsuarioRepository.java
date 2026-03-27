package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.Usuario;
import com.tickets.tickets_backend.modelos.enumeraciones.EstadoUsuario;
import com.tickets.tickets_backend.modelos.enumeraciones.TipoUsuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByCorreo(String correo);

    Optional<Usuario> findByNumeroDocumento(String numeroDocumento);

    List<Usuario> findByTipoUsuario(TipoUsuario tipoUsuario);

    Page<Usuario> findByIdComunidad(Integer idComunidad, Pageable pageable);

    List<Usuario> findByIdComunidad(Integer idComunidad);

    List<Usuario> findByEstado(EstadoUsuario estado);

    boolean existsByCorreo(String correo);

    boolean existsByNumeroDocumento(String numeroDocumento);

    boolean existsByTipoUsuario(TipoUsuario tipoUsuario);

    long countByTipoUsuario(TipoUsuario tipoUsuario);

    @Query("SELECT u FROM Usuario u WHERE u.nombre LIKE %:nombre% OR u.apellido LIKE %:nombre%")
    List<Usuario> buscarPorNombre(@Param("nombre") String nombre);
}
