package br.com.bio.service;

import br.com.bio.persistence.dao.BoardColumnDAO;
import br.com.bio.persistence.dao.BoardDAO;
import br.com.bio.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;

public class BoardService {

    private final Connection connection;

    public BoardService(Connection connection) {
        this.connection = connection;
    }

    public BoardEntity insert(final BoardEntity entity) throws SQLException {
        try {
            var boardDAO = new BoardDAO(connection);
            var boardColumnDAO = new BoardColumnDAO(connection);
            boardDAO.insert(entity);
            var columns = entity.getBoardColumns().stream()
                    .peek(c -> c.setBoard(entity)).toList();
            for (var column : columns) {
                boardColumnDAO.insert(column);
            }
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }

        return entity;
    }

    public boolean delete(final Long id) throws SQLException {
        try {
            var boardDAO = new BoardDAO(connection);
            boolean boardExists = boardDAO.existsById(id);
            if(!boardExists) {
                return false;
            }
            boardDAO.delete(id);
            connection.commit();
            return true;
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        }
    }
}