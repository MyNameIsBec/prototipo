package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;

public class ModificaDatiAccademiciCTRL {

    private final StudenteRepository studenteRepository;

    public ModificaDatiAccademiciCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void modificaDatiAccademici(String nuoviDati) throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        s.setDatiAccademici(nuoviDati);
        studenteRepository.save(s);
    }
}
