package com.skshieldus.esecurity.model.common;

import com.skshieldus.esecurity.common.model.CommonDTO;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonXlsViewDTO extends CommonDTO {

    private String fileName;

    private String[] headerNameArr;

    private String[] columnNameArr;

    private Integer[] columnWidthArr;

    private List<?> dataList;

}