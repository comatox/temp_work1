package com.skshieldus.esecurity.common.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skshieldus.esecurity.model.common.CommonXlsViewDTO;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

@Slf4j
@Component("xlsView")
public class XlsView extends AbstractXlsxView {

	private final int HEADER_ROW_SIZE = 1;

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook wb, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		XSSFWorkbook workbook = (XSSFWorkbook) wb;

		CommonXlsViewDTO xlsViewDTO = (CommonXlsViewDTO) model.get("data");

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
		String fileName = xlsViewDTO.getFileName() + "_" + dtf.format(LocalDateTime.now()) + ".xlsx";
		fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
//		String userAgent = request.getHeader("User-Agent");
//		if (userAgent.contains("MSIE") || userAgent.contains("Trident")) {
//			// ie
//			fileName = URLEncoder.encode(fileName, "utf-8").replaceAll("\\+", "%20");
//		} else {
//			fileName = URLEncoder.encode(fileName, "utf-8");
//		}
		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ";");

		XSSFSheet sheet = workbook.createSheet(xlsViewDTO.getFileName());

		CellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		headerStyle.setBorderBottom(BorderStyle.MEDIUM);
		headerStyle.setBottomBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderLeft(BorderStyle.THIN);
		headerStyle.setLeftBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderRight(BorderStyle.THIN);
		headerStyle.setRightBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setBorderTop(BorderStyle.THIN);
		headerStyle.setTopBorderColor(IndexedColors.GREY_40_PERCENT.getIndex());
		headerStyle.setAlignment(HorizontalAlignment.CENTER);
		headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		XSSFFont font = workbook.createFont();
		font.setFontName("Arial");
		headerStyle.setFont(font);

		CellStyle cellStyleDefault = workbook.createCellStyle();
		cellStyleDefault.cloneStyleFrom(headerStyle);
		cellStyleDefault.setFillPattern(FillPatternType.NO_FILL);
		DataFormat fmt = workbook.createDataFormat();
		cellStyleDefault.setDataFormat(fmt.getFormat("@"));
		cellStyleDefault.setVerticalAlignment(VerticalAlignment.CENTER);

		Row headerRow = sheet.createRow(0);

		String[] headerNameArr = xlsViewDTO.getHeaderNameArr();
		Integer[] columnWidthArr = xlsViewDTO.getColumnWidthArr();

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
				sheet.setColumnWidth(i, 5000);
			}
		}

		String[] columnNameArr = xlsViewDTO.getColumnNameArr();
		List<?> dataList = xlsViewDTO.getDataList();

		ObjectMapper om = new ObjectMapper();

		for (int i = 0; i < dataList.size(); i++) {
			try {
				Map<String, Object> data = om.convertValue(dataList.get(i), new TypeReference<Map<String, Object>>() {
				});
				Row row = sheet.createRow(i + HEADER_ROW_SIZE);
				row.setHeight((short) 400);
				for (int j = 0; j < columnNameArr.length; j++) {
					Cell cell = row.createCell(j);
					String value = data.get(columnNameArr[j]) != null ? String.valueOf(data.get(columnNameArr[j])) : "";
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