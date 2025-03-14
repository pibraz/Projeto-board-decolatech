package br.com.bio.service;

import br.com.bio.dto.BoardColumnDTO;
import br.com.bio.dto.BoardDetailsDTO;
import br.com.bio.persistence.dao.BoardColumnDAO;
import br.com.bio.persistence.dao.BoardDAO;
import br.com.bio.persistence.entity.BoardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class BoardQueryService {

    private final Connection connection;

    public BoardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<BoardEntity> findById(final Long id) throws SQLException {
        var boardDAO = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        var boardExists = boardDAO.findById(id);


        if(boardExists.isPresent()) {
            var entity = boardExists.get();
            entity.setBoardColumns(boardColumnDAO.findByBoardId(entity.getId()));
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    public Optional<BoardDetailsDTO> showBoardDetails(final Long id) throws SQLException {
        var boardDAO = new BoardDAO(connection);
        var boardColumnDAO = new BoardColumnDAO(connection);
        Optional<BoardEntity> boardEntity = boardDAO.findById(id);

        if (boardEntity.isPresent()){
            BoardEntity entity = boardEntity.get();
            List<BoardColumnDTO> columns =
                    boardColumnDAO.findByBoardIdWithDetails(entity.getId());
            var dto = new BoardDetailsDTO(entity.getId(), entity.getName(), columns);
            return Optional.of(dto);
        }
        return Optional.empty();
    }
}