package br.com.bio.dto;

import java.util.List;

public class BoardDetailsDTO {
    private Long id;
    private String name;
    private List<BoardColumnDTO> columns;

    public BoardDetailsDTO() {}

    public BoardDetailsDTO(Long id, String name, List<BoardColumnDTO> columns) {
        this.id = id;
        this.name = name;
        this.columns = columns;
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

    public List<BoardColumnDTO> getColumns() {
        return columns;
    }

    public void setColumns(List<BoardColumnDTO> columns) {
        this.columns = columns;
    }
}