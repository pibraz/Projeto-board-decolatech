package br.com.bio.dto;

import br.com.bio.persistence.entity.BoardColumnKindEnum;

public class BoardColumnDTO {
    private Long id;
    private String name;
    private BoardColumnKindEnum kind;
    private int cardsAmount;

    public BoardColumnDTO() {}

    public BoardColumnDTO(Long id, String name, BoardColumnKindEnum kind, int cardsAmount) {
        this.id = id;
        this.name = name;
        this.kind = kind;
        this.cardsAmount = cardsAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BoardColumnKindEnum getKind() {
        return kind;
    }

    public void setKind(BoardColumnKindEnum kind) {
        this.kind = kind;
    }

    public int getCardsAmount() {
        return cardsAmount;
    }

    public void setCardsAmount(int cardsAmount) {
        this.cardsAmount = cardsAmount;
    }
}