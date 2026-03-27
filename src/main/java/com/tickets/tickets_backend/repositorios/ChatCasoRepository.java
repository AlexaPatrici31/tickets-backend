package com.tickets.tickets_backend.repositorios;

import com.tickets.tickets_backend.modelos.entidades.ChatCaso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatCasoRepository extends JpaRepository <ChatCaso, Integer> {
}
