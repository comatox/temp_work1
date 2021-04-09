package kr.co.esjee.mediaportal.common.component.xls;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.esjee.mediaportal.webapps.model.common.CommonXlsViewVO;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("xlsView")
public class XlsView extends AbstractXlsxView {

	private final int HEADER_ROW_SIZE = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook wb, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		response.setHeader("Content-Disposition", "attachment; filename=\"미디어포털_엑셀다운로드_" + dtf.format(LocalDateTime.now()) + ".xls\"");

		XSSFWorkbook workbook = (XSSFWorkbook) wb;

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		String fileName = "미디어포털_엑셀다운로드_" + dtf.format(LocalDateTime.now()) + ".xlsx";
		String userAgent = request.getHeader("User-Agent");
		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
			// ie
			fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
		} else {
			fileName = URLEncoder.encode(fileName, "utf-8");
		}
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");

		CommonXlsViewVO xlsViewVO = (CommonXlsViewVO) model.get("data");

		XSSFSheet sheet = workbook.createSheet("미디어포털_엑셀다운로드");

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(BorderStyle.THIN);
		headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle cellStyleDefault = workbook.createCellStyle();
		cellStyleDefault.cloneStyleFrom(headerStyle);
		cellStyleDefault.setFillPattern(FillPatternType.NO_FILL);
		DataFormat fmt = workbook.createDataFormat();
		cellStyleDefault.setDataFormat(fmt.getFormat("@"));

		Row headerRow = sheet.createRow(0);

		String[] headerNameArr = xlsViewVO.getHeaderNameArr();
		Integer[] columnWidthArr = xlsViewVO.getColumnWidthArr();

		List<Integer> columnWidthAutoList = new ArrayList<>();
		for (int i = 0; i < headerNameArr.length; i++) {
			Cell headerCell = headerRow.createCell(i);
			headerCell.setCellValue(headerNameArr[i]);
			headerCell.setCellStyle(headerStyle);

			if (columnWidthArr != null && (columnWidthArr.length) > i) {
				if (columnWidthArr[i] == 0) {
					columnWidthAutoList.add(i);
				} else {
					sheet.setColumnWidth(i, columnWidthArr[i]);
				}
			} else {
				sheet.setColumnWidth(i, 6000);
			}
		}

		String[] columnNameArr = xlsViewVO.getColumnNameArr();
		List<?> dataList = xlsViewVO.getDataList();

		ObjectMapper om = new ObjectMapper();

		for (int i = 0; i < dataList.size(); i++) {
			try {
				Map<String, Object> data = om.convertValue(dataList.get(i), new TypeReference<Map<String, Object>>() {
				});
				Row row = sheet.createRow(i + HEADER_ROW_SIZE);
				for (int j = 0; j < columnNameArr.length; j++) {
					Cell cell = row.createCell(j);
					String value = String.valueOf(data.get(columnNameArr[j]));
					if (value.length() == 14 && columnNameArr[j].endsWith("Date")) {
						String newValue = value.substring(0, 4) + "." + value.substring(4, 6) + "." + value.substring(6, 8);
						value = newValue;
					}
					cell.setCellValue(value);
					cell.setCellStyle(cellStyleDefault);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		}

		if (columnWidthAutoList.size() > 0) {
			for (Integer columnPos : columnWidthAutoList) {
				sheet.autoSizeColumn(columnPos);
			}
		}

		sheet.addIgnoredErrors(new CellRangeAddress(0, HEADER_ROW_SIZE + dataList.size(), 0, columnNameArr.length),
				IgnoredErrorType.NUMBER_STORED_AS_TEXT);
	}
}
