package kr.co.esjee.mediaportal.webapps.model.common;

import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonXlsViewVO {

	private String fileName;
	private String[] headerNameArr;
	private String[] columnNameArr;
	private Integer[] columnWidthArr;
	private List<?> dataList;
	
}
