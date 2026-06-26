package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public class VisualizzaProfiloCTRL {

    private final StudenteRepository studenteRepository;

    public VisualizzaProfiloCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public StudenteEntity getProfilo() throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        if (cf == null) {
            throw new IllegalStateException("Nessuna sessione attiva");
        }
        Optional<StudenteEntity> opt = studenteRepository.findByCf(cf);
        return opt.orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
    }
}
