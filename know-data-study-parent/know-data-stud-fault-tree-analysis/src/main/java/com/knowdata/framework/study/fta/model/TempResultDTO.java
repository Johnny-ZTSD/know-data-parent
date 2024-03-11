package com.knowdata.framework.study.fta.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TempResultDTO<T> {

    private List<T> result;

    private Integer index;
}