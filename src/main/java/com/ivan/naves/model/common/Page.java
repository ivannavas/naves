package com.ivan.naves.model.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    private List<T> records;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totalPages;

    public Page(org.springframework.data.domain.Page<T> page) {
        this.records = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalPages = page.getTotalPages();
    }
}
