package com.portfolioafam.gestionecontenuti;

import com.portfolioafam.model.ContenutoEntity;
import com.portfolioafam.repository.ContenutoRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;

public class CaricaContenutiCTRL {
    private static final long MAX_SIZE = 500 * 1024 * 1024;
    private final ContenutoRepository contenutoRepository;
    public CaricaContenutiCTRL(ContenutoRepository r) { this.contenutoRepository = r; }

    public void carica(byte[] fileDati, String nome, String tipo, Long dimensione, String privacy, Long idCartella) throws SQLException, ValidationException {
        if (dimensione > MAX_SIZE) throw new ValidationException("File troppo grande (max 500MB)");
        ContenutoEntity c = new ContenutoEntity(SessionManager.getInstance().getCf(), nome, tipo, fileDati, dimensione, privacy);
        c.setIdCartella(idCartella);
        contenutoRepository.save(c);
    }
    public static class ValidationException extends Exception { public ValidationException(String m) { super(m); } }
}
