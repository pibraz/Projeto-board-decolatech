package br.com.bio.dto;

import br.com.bio.persistence.entity.BoardColumnKindEnum;

public class BoardColumnInfoDTO {
    private Long id;
    private int order;
    private BoardColumnKindEnum kind;

    public BoardColumnInfoDTO() {
    }

    public BoardColumnInfoDTO(Long id, int order, BoardColumnKindEnum kind) {
        this.id = id;
        this.order = order;
        this.kind = kind;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public BoardColumnKindEnum getKind() {
        return kind;
    }

    public void setKind(BoardColumnKindEnum kind) {
        this.kind = kind;
    }
}