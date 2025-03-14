package br.com.bio.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static br.com.bio.persistence.entity.BoardColumnKindEnum.CANCEL;
import static br.com.bio.persistence.entity.BoardColumnKindEnum.INITIAL;

public class BoardEntity {

    private Long id;
    private String name;
    private List<BoardColumnEntity> boardColumns = new ArrayList<>();

    public BoardEntity() {

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

    public List<BoardColumnEntity> getBoardColumns() {
        return boardColumns;
    }

    public void setBoardColumns(List<BoardColumnEntity> boardColumns) {
        this.boardColumns = boardColumns;
    }

    private BoardColumnEntity getFilteredColumn(Predicate<BoardColumnEntity> filter) {
        return boardColumns.stream()
                .filter(filter)
                .findFirst().orElseThrow();

    }
    public BoardColumnEntity getInitialColumn(){
        return getFilteredColumn(bc -> bc.getKind().equals(INITIAL));
    }

    public BoardColumnEntity getCancelColumn(){
        return getFilteredColumn(bc -> bc.getKind().equals(CANCEL));
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardEntity that = (BoardEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "BoardEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}