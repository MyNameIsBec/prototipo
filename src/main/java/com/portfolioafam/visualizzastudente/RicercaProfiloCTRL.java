package com.portfolioafam.visualizzastudente;
import com.portfolioafam.model.StudenteEntity;
import com.portfolioafam.repository.StudenteRepository;
import java.sql.SQLException;
import java.util.List;
public class RicercaProfiloCTRL {
    private final StudenteRepository studenteRepository;
    public RicercaProfiloCTRL(StudenteRepository r) { this.studenteRepository = r; }
    public List<StudenteEntity> cerca(String nome) throws SQLException { return studenteRepository.searchByNomePublic(nome); }
}
