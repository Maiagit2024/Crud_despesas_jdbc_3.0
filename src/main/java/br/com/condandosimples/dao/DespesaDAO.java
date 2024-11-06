package br.com.condandosimples.dao;

import br.com.condandosimples.infra.ConnectionFactory;
import br.com.condandosimples.model.Categoria;
import br.com.condandosimples.model.Despesas;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DespesaDAO implements IdespesasDAO {
    @Override///Garantir o rastreo do metodo
    public Despesas save(Despesas despesas) {

       try (Connection connection = ConnectionFactory.getConnection()){
        String sql = "INSERT INTO DESPESAS(descricao,data,valor,categoria)VALUES(?, ?, ?, ? )";///sql injection

          PreparedStatement prepareStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
          prepareStatement.setString(1,despesas.getDescricao());
          prepareStatement.setDate(2, java.sql.Date.valueOf(despesas.getData()));
          prepareStatement.setDouble(3,despesas.getValor());
          prepareStatement.setString(4, despesas.getCategoria().toString());

          prepareStatement.executeUpdate();

           ResultSet resultSet = prepareStatement.getGeneratedKeys();
           resultSet.next();

          Long generateDId = resultSet.getLong(1);
          despesas.setId(generateDId);


       }catch (SQLException ex){
           throw new RuntimeException(ex);

        }

        return despesas;
    }

    @Override
    public boolean update(Despesas despesas) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "UPDATE DESPESAS SET descricao = ?, data = ?, valor = ?, categoria = ? WHERE id = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setString(1, despesas.getDescricao());
            prepareStatement.setDate(2, java.sql.Date.valueOf(despesas.getData()));
            prepareStatement.setDouble(3, despesas.getValor());
            prepareStatement.setString(4, despesas.getCategoria().toString());
            prepareStatement.setLong(5, despesas.getId());

            int rowsAffected = prepareStatement.executeUpdate();
            return rowsAffected > 0; // Retorna true se pelo menos uma linha foi atualizada
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "DELETE FROM DESPESAS WHERE id = ?";

            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setLong(1, id);

            int rowsAffected = prepareStatement.executeUpdate();
            return  rowsAffected > 0;
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    @Override
    public List<Despesas> findALL() {
        List<Despesas> despesasList = new ArrayList<>();
        try (Connection conenection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM DESPESAS";
            PreparedStatement preparedStatement = conenection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Despesas despesas = new Despesas();
                despesas.setId(resultSet.getLong("id"));
                despesas.setDescricao(resultSet.getString("descricao"));
                despesas.setData(resultSet.getDate("data").toLocalDate());
                despesas.setValor(resultSet.getDouble("valor"));
                String categoriaStr = resultSet.getString("categoria");
                try {
                    despesas.setCategoria(Categoria.valueOf(categoriaStr.toUpperCase()));

                } catch (IllegalArgumentException e ) {
                    System.out.println("Categoria inválida" + categoriaStr + ". Usando 'Outras' como padrão");
                }
                despesasList.add(despesas);
            }
        } catch (SQLException ex){
            throw  new RuntimeException(ex);
        }

        return despesasList;
    }

    @Override
    public Optional<Despesas> findById(Long id) {
        try (Connection connection = ConnectionFactory.getConnection()) {
            String sql = "SELECT * FROM DESPESAS WHERE id = ?";
            PreparedStatement prepareStatement = connection.prepareStatement(sql);
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();

            if (resultSet.next()) {
                Despesas despesas = new Despesas();
                despesas.setId(resultSet.getLong("id"));
                despesas.setDescricao(resultSet.getString("descricao"));
                despesas.setData(resultSet.getDate("data").toLocalDate());
                despesas.setValor(resultSet.getDouble("valor"));
                String categoriaStr = resultSet.getString("categoria");
                try {
                    despesas.setCategoria(Categoria.valueOf(categoriaStr.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    System.out.println("Categoria inválida: " + categoriaStr + ". Usando 'OUTRAS' como padrão.");
                    despesas.setCategoria(Categoria.OUTRAS);
                }
                return Optional.of(despesas);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.empty();
    }


    @Override
    public List<Despesas> findCategoria(Categoria categoria) {
        // Implementação do método findCategoria (não fornecido)
        return null;
    }


}
