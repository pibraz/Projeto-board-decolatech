package br.com.bio.service;
import br.com.bio.dto.CardDetailsDTO;
import br.com.bio.persistence.dao.CardDAO;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class CardQueryService {

    private final Connection connection;

    public CardQueryService(Connection connection) {
        this.connection = connection;
    }

    public Optional<CardDetailsDTO> findById(final Long id) throws SQLException {
        var dao = new CardDAO(connection);
        return dao.findById(id);
    }
}