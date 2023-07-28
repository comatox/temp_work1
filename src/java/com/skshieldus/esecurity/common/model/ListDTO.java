package com.skshieldus.esecurity.common.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ListDTO<T> {

    private List<T> list;

    private Integer totalCount;

    public static ListDTO getInstance(List list, Integer totalCount) {
        return ListDTO.builder().list(list).totalCount(totalCount).build();
    }

}
