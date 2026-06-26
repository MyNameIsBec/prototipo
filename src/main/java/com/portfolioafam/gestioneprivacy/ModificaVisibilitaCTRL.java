package com.portfolioafam.gestioneprivacy;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import com.portfolioafam.util.SessionManager;
import java.sql.SQLException;
public class ModificaVisibilitaCTRL {
    private final StudenteRepository studenteRepository;
    public ModificaVisibilitaCTRL(StudenteRepository r) { this.studenteRepository = r; }
    public void modificaVisibilita(String visibilita) throws SQLException {
        String cf = SessionManager.getInstance().getCf();
        studenteRepository.findByCf(cf).ifPresent(s -> { s.setVisibilitaProfilo(visibilita); try { studenteRepository.save(s); } catch (SQLException e) { throw new RuntimeException(e); } });
    }
}
