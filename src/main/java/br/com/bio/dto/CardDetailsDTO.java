package br.com.bio.dto;

import java.time.OffsetDateTime;

public class CardDetailsDTO {
    private Long id;
    private String title;
    private String description;
    private boolean blocked;
    private OffsetDateTime blockedAt;
    private String blockReason;
    private int blocksAmount;
    private Long columnId;
    private String columnName;

    public CardDetailsDTO(Long id, String title, String description, boolean blocked, OffsetDateTime blockedAt, String blockReason, int blocksAmount, Long columnId, String columnName) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.blocked = blocked;
        this.blockedAt = blockedAt;
        this.blockReason = blockReason;
        this.blocksAmount = blocksAmount;
        this.columnId = columnId;
        this.columnName = columnName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public OffsetDateTime getBlockedAt() {
        return blockedAt;
    }

    public void setBlockedAt(OffsetDateTime blockedAt) {
        this.blockedAt = blockedAt;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public int getBlocksAmount() {
        return blocksAmount;
    }

    public void setBlocksAmount(int blocksAmount) {
        this.blocksAmount = blocksAmount;
    }

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
}