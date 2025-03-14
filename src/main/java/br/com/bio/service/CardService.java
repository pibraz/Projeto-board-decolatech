package br.com.bio.service;

import br.com.bio.dto.BoardColumnInfoDTO;
import br.com.bio.dto.CardDetailsDTO;
import br.com.bio.exceptions.CardBlockedException;
import br.com.bio.exceptions.CardFinishedException;
import br.com.bio.exceptions.EntityNotFoundException;
import br.com.bio.persistence.dao.BlockDAO;
import br.com.bio.persistence.dao.CardDAO;
import br.com.bio.persistence.entity.CardEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static br.com.bio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.bio.persistence.entity.BoardColumnKindEnum.FINAL;

public class CardService {
    private final Connection connection;

    public CardService (Connection connection) {
        this.connection = connection;
    }

    public CardEntity create(final CardEntity entity) throws SQLException {
        try {
            var cardDAO = new CardDAO(connection);
            cardDAO.insert(entity);
            connection.commit();
            return entity;
        } catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    public void moveToNextColumn(final Long cardId, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try{
            var cardDAO = new CardDAO(connection);
            var cardDetails = getCardDetailsDTO(cardId);
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.getId().equals(cardDetails.getColumnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (currentColumn.getKind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            var nextColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.getOrder() == currentColumn.getOrder() + 1)
                    .findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
            cardDAO.moveToColumn(nextColumn.getId(), cardId);
            connection.commit();
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    private CardDetailsDTO getCardDetailsDTO(Long cardId) throws SQLException, CardBlockedException {
        var cardDAO = new CardDAO(connection);
        var cardDetailsDTOOptional = cardDAO.findById(cardId);
        var cardDetails = cardDetailsDTOOptional.orElseThrow(
                () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
        );
        if (cardDetails.isBlocked()){
            var message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
            throw new CardBlockedException(message);
        }
        return cardDetails;
    }

    public void cancel(final Long cardId, final Long cancelColumnId ,
                       final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException{
        try{
            var cardDAO = new CardDAO(connection);
            var cardDetailsOptional = cardDAO.findById(cardId);
            var cardDetails = cardDetailsOptional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(cardId))
            );
            if (cardDetails.isBlocked()){
                var message = "O card %s está bloqueado, é necesário desbloquea-lo para mover".formatted(cardId);
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.getId().equals(cardDetails.getColumnId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("O card informado pertence a outro board"));
            if (currentColumn.getKind().equals(FINAL)){
                throw new CardFinishedException("O card já foi finalizado");
            }
            boardColumnsInfo.stream()
                    .filter(bc -> bc.getOrder() == currentColumn.getOrder() + 1)
                    .findFirst().orElseThrow(() -> new IllegalStateException("O card está cancelado"));
            cardDAO.moveToColumn(cancelColumnId, cardId);
            connection.commit();
        }catch (SQLException ex){
            connection.rollback();
            throw ex;
        }
    }

    public void block(final Long id, final String reason, final List<BoardColumnInfoDTO> boardColumnsInfo) throws SQLException {
        try{
            var cardDAO = new CardDAO(connection);
            var cardDetailsOptional = cardDAO.findById(id);
            var cardDetails = cardDetailsOptional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (cardDetails.isBlocked()){
                var message = "O card %s já está bloqueado".formatted(id);
                throw new CardBlockedException(message);
            }
            var currentColumn = boardColumnsInfo.stream()
                    .filter(bc -> bc.getId().equals(cardDetails.getColumnId()))
                    .findFirst()
                    .orElseThrow();
            if (currentColumn.getKind().equals(FINAL) || currentColumn.getKind().equals(CANCEL)){
                var message = "O card está em uma coluna do tipo %s e não pode ser bloqueado"
                        .formatted(currentColumn.getKind());
                throw new IllegalStateException(message);
            }
            var blockDAO = new BlockDAO(connection);
            blockDAO.block(reason, id);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

    public void unblock(final Long id, final String reason) throws SQLException {
        try{
            var cardDAO = new CardDAO(connection);
            var cardDetailsOptional = cardDAO.findById(id);
            var cardDetails = cardDetailsOptional.orElseThrow(
                    () -> new EntityNotFoundException("O card de id %s não foi encontrado".formatted(id))
            );
            if (!cardDetails.isBlocked()){
                var message = "O card %s não está bloqueado".formatted(id);
                throw new CardBlockedException(message);
            }
            var blockDAO = new BlockDAO(connection);
            blockDAO.unblock(reason, id);
            connection.commit();
        }catch (SQLException ex) {
            connection.rollback();
            throw ex;
        }
    }

}