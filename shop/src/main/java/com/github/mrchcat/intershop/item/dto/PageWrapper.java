package com.github.mrchcat.intershop.item.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true, value = {"pageable"})
public class PageWrapper extends PageImpl<List<ItemDto>> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PageWrapper(@JsonProperty("content") List<List<ItemDto>> content,
                    @JsonProperty("number") int page,
                    @JsonProperty("size") int size,
                    @JsonProperty("totalElements") long total) {
        super(content, PageRequest.of(page, size), total);
    }

    public PageWrapper(Page<List<ItemDto>> page) {
        super(page.getContent(), page.getPageable(), page.getTotalElements());
    }

}
