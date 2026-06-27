package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import java.sql.SQLException;
import java.util.List;
public class RicercaProfiloCTRL {
    private final StudenteRepository studenteRepository;
    public RicercaProfiloCTRL(StudenteRepository r) { this.studenteRepository = r; }
    public List<StudenteEntity> cerca(String nome) throws SQLException { return studenteRepository.searchByNomePublic(nome); }
    public List<StudenteEntity> cercaConFiltro(String nome, String strumento) throws SQLException {
        return studenteRepository.searchByNomeAndStrumentoPublic(nome, strumento);
    }
    public List<StudenteEntity> cercaConFiltri(String nome, String corso, String anno, boolean laureato) throws SQLException {
        return studenteRepository.searchByNomeAndFiltriPublic(nome, corso, anno, laureato);
    }
}
