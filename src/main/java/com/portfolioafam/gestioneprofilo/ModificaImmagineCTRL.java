package com.portfolioafam.gestioneprofilo;

import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;

import java.sql.SQLException;

public class ModificaImmagineCTRL {

    private static final long MAX_SIZE = 5 * 1024 * 1024;

    private final StudenteRepository studenteRepository;

    public ModificaImmagineCTRL(StudenteRepository studenteRepository) {
        this.studenteRepository = studenteRepository;
    }

    public void modificaImmagine(byte[] immagine, String tipoFile) throws SQLException, ValidationException {
        if (tipoFile == null || (!tipoFile.equalsIgnoreCase("jpg")
                && !tipoFile.equalsIgnoreCase("jpeg")
                && !tipoFile.equalsIgnoreCase("png"))) {
            throw new ValidationException("File non valido. Formati: JPG, PNG. Dimensione max: 5MB");
        }
        if (immagine.length > MAX_SIZE) {
            throw new ValidationException("File non valido. Formati: JPG, PNG. Dimensione max: 5MB");
        }
        String cf = SessionManager.getInstance().getCf();
        StudenteEntity s = studenteRepository.findByCf(cf)
                .orElseThrow(() -> new IllegalStateException("Profilo non trovato"));
        s.setImmagineProfilo(immagine);
        studenteRepository.save(s);
    }

    public static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}
