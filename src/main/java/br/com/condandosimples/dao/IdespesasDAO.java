package br.com.condandosimples.dao;

import br.com.condandosimples.model.Categoria;
import br.com.condandosimples.model.Despesas;

import java.util.List;
import java.util.Optional;

public interface IdespesasDAO {

    Despesas save (Despesas despesas);
    boolean update (Despesas despesas);
    boolean delete (Long id);
    List<Despesas> findALL();
    Optional<Despesas> findById(Long id);
    List<Despesas> findCategoria(Categoria categoria);



}
