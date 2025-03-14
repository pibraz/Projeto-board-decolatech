package br.com.bio.persistence.dao;

import br.com.bio.dto.BoardColumnDTO;
import br.com.bio.persistence.entity.BoardColumnEntity;
import br.com.bio.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.bio.persistence.entity.BoardColumnKindEnum.findByName;
import static java.util.Objects.isNull;

public class BoardColumnDAO {
    private final Connection connection;

    public BoardColumnDAO(Connection connection) {
        this.connection = connection;
    }

    public BoardColumnEntity insert(final BoardColumnEntity entity) throws SQLException {
        String query = "INSERT INTO board_columns (name, `order`, kind, board_id) VALUES (?,?,?,?)";
        try (var statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            int i = 1;
            statement.setString(i++, entity.getName());
            statement.setInt(i++, entity.getOrder());
            statement.setString(i++, entity.getKind().name());
            statement.setLong(i++, entity.getBoard().getId());
            statement.executeUpdate();
            try (var rs = statement.getGeneratedKeys()) {
                if (rs.next()) {
                    entity.setId(rs.getLong(1));
                }
            }
            return entity;
        }
    }

    public List<BoardColumnEntity> findByBoardId(final Long boardId) throws SQLException {
        List<BoardColumnEntity> entities = new ArrayList<>();
        var sql = "SELECT id, name, `order`, kind FROM board_columns WHERE board_id = ? ORDER BY `order`";
        try (var statement = connection.prepareStatement(sql)) {
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()) {
                var entity = new BoardColumnEntity();
                entity.setId(resultSet.getLong("id"));
                entity.setName(resultSet.getString("name"));
                entity.setOrder(resultSet.getInt("order"));
                entity.setKind(findByName(resultSet.getString("kind")));
                entities.add(entity);
            }
            return entities;
        }
    }

    public List<BoardColumnDTO> findByBoardIdWithDetails(final Long boardId) throws SQLException {
        List<BoardColumnDTO> dtos = new ArrayList<>();
        var sql =
                """
                SELECT bc.id,
                       bc.name,
                       bc.kind,
                       (SELECT COUNT(c.id)
                               FROM CARDS c
                              WHERE c.board_columns_id = bc.id) cards_amount
                  FROM board_columns
                 WHERE board_id = ?
                 ORDER BY `order`;
                """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            while (resultSet.next()){
                var dto = new BoardColumnDTO(
                        resultSet.getLong("bc.id"),
                        resultSet.getString("bc.name"),
                        findByName(resultSet.getString("bc.kind")),
                        resultSet.getInt("cards_amount")
                );
                dtos.add(dto);
            }
            return dtos;
        }
    }

    public Optional<BoardColumnEntity> findById(final Long boardId) throws SQLException{
        var sql =
                """
                SELECT bc.name,
                       bc.kind,
                       c.id,
                       c.title,
                       c.description
                  FROM boards_columns bc
                  LEFT JOIN CARDS c
                    ON c.board_columns_id = bc.id
                 WHERE bc.id = ?;
                """;
        try(var statement = connection.prepareStatement(sql)){
            statement.setLong(1, boardId);
            statement.executeQuery();
            var resultSet = statement.getResultSet();
            if (resultSet.next()){
                var entity = new BoardColumnEntity();
                entity.setName(resultSet.getString("bc.name"));
                entity.setKind(findByName(resultSet.getString("bc.kind")));
                do {
                    var card = new CardEntity();
                    if (isNull(resultSet.getString("c.title"))){
                        break;
                    }
                    card.setId(resultSet.getLong("c.id"));
                    card.setTitle(resultSet.getString("c.title"));
                    card.setDescription(resultSet.getString("c.description"));
                    entity.getCards().add(card);
                }while (resultSet.next());
                return Optional.of(entity);
            }
            return Optional.empty();
        }
    }
}