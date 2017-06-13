package com.critc.plat.util.excel;

import com.critc.plat.util.log.LogUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * excel工具，读取2007文件格式
 *
 * @author 孔垂云
 * @date 2017-05-23
 */
public class ExcelUtil {

    /**
     * 读取excel表头，返回一个字符串数组
     *
     * @param fileName   文件流传入参数
     * @param titleIndex excel文件头的行数,默认从第一行开始，对应excel应该是titleIndex-1
     * @return
     */
    public String[] readExcelTitle(String fileName, int titleIndex) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        InputStream is = null;
        String[] title = null;
        try {
            is = new FileInputStream(fileName);
            wb = WorkbookFactory.create(is);
            sheet = wb.getSheetAt(0);
            row = sheet.getRow(titleIndex - 1);
            // 标题总列数
            int colNum = row.getPhysicalNumberOfCells();
            title = new String[colNum];
            for (int i = 0; i < colNum; i++) {
                title[i] = getCellFormatValue(row.getCell(i));
            }
        } catch (FileNotFoundException e) {
            LogUtil.error(e);
        } catch (InvalidFormatException e) {
            LogUtil.error(e);
        } catch (IOException e) {
            LogUtil.error(e);
        } catch (Exception e) {
            LogUtil.error(e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
        }
        return title;
    }

    /**
     * * 读取Excel数据内容 *
     *
     * @param fileName  文件全路径名
     * @param dataIndex 为数据行的起始行数
     * @return List 包含单元格数据内容的Map对象
     */
    public List<String[]> readExcelContent(String fileName, int dataIndex) {
        Workbook wb = null;
        Sheet sheet = null;
        Row row = null;
        InputStream is = null;
        List<String[]> list = new ArrayList<String[]>();
        try {
            is = new FileInputStream(fileName);
            wb = WorkbookFactory.create(is);
            sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();// 得到总行数
            row = sheet.getRow(0);
            int colNum = row.getPhysicalNumberOfCells(); // 正文内容应该从第二行开始,第一行为表头的标题
            for (int i = dataIndex - 1; i <= rowNum + dataIndex - 1; i++) {
                row = sheet.getRow(i);
                if (row != null) {
                    String[] values = new String[colNum];
                    for (int j = 0; j < colNum; j++) {
                        values[j] = getCellFormatValue(row.getCell(j)).trim();
                    }
                    list.add(values);
                }
            }
        } catch (FileNotFoundException e) {
            LogUtil.error(e);
        } catch (InvalidFormatException e) {
            LogUtil.error(e);
        } catch (IOException e) {
            LogUtil.error(e);
        } finally {
            if (is != null)
                try {
                    is.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
        }
        return list;
    }

    private String getCellFormatValue(Cell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) { // 如果当前Cell的Type为NUMERIC
                case Cell.CELL_TYPE_BLANK:
                    cellvalue = "";
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(DateUtil.getJavaDate(cell.getNumericCellValue())).toString();
                    } else {
                        double val = cell.getNumericCellValue();
                        // String operaVal="";
                        if (String.valueOf(val).toUpperCase().indexOf('E') > -1) {
                            cellvalue = String.valueOf(new DecimalFormat("#.######").format(val));
                        } else {
                            cellvalue = String.valueOf(val);
                        }
                    }
                    break;
                case Cell.CELL_TYPE_STRING:
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cellvalue = cell.getRichStringCellValue().toString();
                    break;
                default:
                    cellvalue = "";// 默认的Cell值
            }
        }
        return cellvalue;
    }

    /**
     * 新建excel单元格边框的样式
     *
     * @param wb
     * @param backgroundColor
     * @param foregroundColor
     * @param halign
     * @param font
     * @return
     */
    public CellStyle createBorderCellStyle(Workbook wb, short backgroundColor, short foregroundColor, short halign, Font font) {
        CellStyle cs = wb.createCellStyle();
        cs.setAlignment(halign);
        cs.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        cs.setFillBackgroundColor(backgroundColor);
        cs.setFillForegroundColor(foregroundColor);
        cs.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cs.setFont(font);
        cs.setBorderLeft(CellStyle.SOLID_FOREGROUND);
        cs.setBorderRight(CellStyle.SOLID_FOREGROUND);
        cs.setBorderTop(CellStyle.SOLID_FOREGROUND);
        cs.setBorderBottom(CellStyle.SOLID_FOREGROUND);
        return cs;
    }

    public CellStyle getDefaultTitleStyle(Workbook workbook) {
        Font fontTitle = createFont(workbook, HSSFFont.BOLDWEIGHT_BOLD, HSSFFont.COLOR_NORMAL, (short) 20);
        CellStyle style = createBorderCellStyle(workbook, HSSFColor.WHITE.index, HSSFColor.WHITE.index, HSSFCellStyle.ALIGN_CENTER, fontTitle);
        return style;
    }

    /**
     * 创建一个字体
     *
     * @param wb
     * @param boldweight
     * @param color
     * @param size
     * @return
     */
    public Font createFont(Workbook wb, short boldweight, short color, short size) {
        Font font = wb.createFont();
        font.setBoldweight(boldweight);
        font.setColor(color);
        font.setFontHeightInPoints(size);
        return font;
    }

    /**
     * 写excel
     *
     * @param data     二维数组
     * @param fileName 要生成文件名，可以为中文
     * @param response 页面传过来的response
     */
    public void writeExcel(String[][] data, String fileName, HttpServletResponse response) {
        OutputStream os = null;
        try {
            Workbook wb = new SXSSFWorkbook(500);
            Sheet writeSheet = wb.createSheet();
            Font fontTitle = wb.createFont();
            fontTitle.setFontHeightInPoints((short) 12);
            fontTitle.setFontName("宋体");
            fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitle.setCharSet(Font.DEFAULT_CHARSET);
            CellStyle cellTitleStyle = wb.createCellStyle();
            cellTitleStyle.setFont(fontTitle);
            cellTitleStyle.setBorderBottom((short) 1);
            cellTitleStyle.setBorderLeft((short) 1);
            cellTitleStyle.setBorderRight((short) 1);
            cellTitleStyle.setBorderTop((short) 1);
            cellTitleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellTitleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

            Font fontData = wb.createFont();
            fontData.setFontHeightInPoints((short) 10);
            fontData.setFontName("宋体");
            CellStyle cellDataStyle = wb.createCellStyle();
            cellDataStyle.setFont(fontData);
            cellDataStyle.setBorderBottom((short) 1);
            cellDataStyle.setBorderLeft((short) 1);
            cellDataStyle.setBorderRight((short) 1);
            cellDataStyle.setBorderTop((short) 1);
            cellDataStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellDataStyle.setFillForegroundColor(HSSFColor.WHITE.index);

            // 写表头
            Row writeRow = writeSheet.createRow(0);
            for (int i = 0; i < data[0].length; i++) {
                Cell cell = writeRow.createCell(i);
                cell.setCellValue(data[0][i]);
                cell.setCellStyle(cellTitleStyle);
            }
            // 写数据内容
            for (int i = 1; i < data.length; i++) {
                Row dataRow = writeSheet.createRow(i);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = dataRow.createCell(j);
                    cell.setCellValue(data[i][j]);
                    cell.setCellStyle(cellDataStyle);
                }
            }

            response.setContentType("application/msexcel");
            response.reset();
            response.setHeader("content-disposition", "attachment; filename=" + new String(fileName.getBytes("GBK"), "ISO-8859-1") + ".xlsx");
            System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
            os = response.getOutputStream();
            wb.write(os);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
                response.flushBuffer();
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
    }

    /**
     * 写入指定文件名的excel
     *
     * @param data     二维数组
     * @param fileName 写入的文件名
     */
    public void writeExcel(String[][] data, String fileName) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(fileName));
            Workbook wb = new SXSSFWorkbook(500);
            Sheet writeSheet = wb.createSheet();
            Font fontTitle = wb.createFont();
            fontTitle.setFontHeightInPoints((short) 12);
            fontTitle.setFontName("宋体");
            fontTitle.setBoldweight(Font.BOLDWEIGHT_BOLD);
            fontTitle.setCharSet(Font.DEFAULT_CHARSET);
            CellStyle cellTitleStyle = wb.createCellStyle();
            cellTitleStyle.setFont(fontTitle);
            cellTitleStyle.setBorderBottom((short) 1);
            cellTitleStyle.setBorderLeft((short) 1);
            cellTitleStyle.setBorderRight((short) 1);
            cellTitleStyle.setBorderTop((short) 1);
            cellTitleStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellTitleStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);

            Font fontData = wb.createFont();
            fontData.setFontHeightInPoints((short) 10);
            fontData.setFontName("宋体");
            CellStyle cellDataStyle = wb.createCellStyle();
            cellDataStyle.setFont(fontData);
            cellDataStyle.setBorderBottom((short) 1);
            cellDataStyle.setBorderLeft((short) 1);
            cellDataStyle.setBorderRight((short) 1);
            cellDataStyle.setBorderTop((short) 1);
            cellDataStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellDataStyle.setFillForegroundColor(HSSFColor.WHITE.index);

            // 写表头
            Row writeRow = writeSheet.createRow(0);
            for (int i = 0; i < data[0].length; i++) {
                Cell cell = writeRow.createCell(i);
                cell.setCellValue(data[0][i]);
                cell.setCellStyle(cellTitleStyle);
            }
            // 写数据内容
            for (int i = 1; i < data.length; i++) {
                Row dataRow = writeSheet.createRow(i);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = dataRow.createCell(j);
                    cell.setCellValue(data[i][j]);
                    cell.setCellStyle(cellDataStyle);
                }
            }

            wb.write(out);
            out.flush();
        } catch (IOException e) {
            LogUtil.error(e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                LogUtil.error(e);
            }
        }
    }

    /**
     * 写excel，并提供下载
     *
     * @param data      二维数组
     * @param srcFile   模板
     * @param file_name 要生成的文件名
     * @param response  web端传过来的response
     * @param titleRows 表头行数
     * @param rowHeight 行高
     */
    public static void writeExcel(String[][] data, String srcFile, String file_name, HttpServletResponse response, int titleRows, int rowHeight) {
        OutputStream os = null;
        try {
            InputStream in = new FileInputStream(srcFile);
            Workbook work = new HSSFWorkbook(in);
            Sheet sheet = work.getSheetAt(0);

            // 数据样式
            Font fontData = work.createFont();
            fontData.setFontHeightInPoints((short) 10);
            fontData.setFontName("宋体");
            fontData.setBoldweight((short) 1);

            CellStyle cellDataStyle = work.createCellStyle();
            cellDataStyle.setFont(fontData);
            cellDataStyle.setBorderBottom((short) 1);
            cellDataStyle.setBorderLeft((short) 1);
            cellDataStyle.setBorderRight((short) 1);
            cellDataStyle.setBorderTop((short) 1);
            cellDataStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellDataStyle.setFillForegroundColor(HSSFColor.WHITE.index);
            cellDataStyle.setAlignment(CellStyle.ALIGN_CENTER);
            cellDataStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellDataStyle.setWrapText(true);

            Font fontDataBold = work.createFont();
            fontDataBold.setFontHeightInPoints((short) 10);
            fontDataBold.setFontName("宋体");
            fontDataBold.setBoldweight(Font.BOLDWEIGHT_BOLD);
            CellStyle cellDataStyleBold = work.createCellStyle();
            cellDataStyleBold.setFont(fontDataBold);
            cellDataStyleBold.setBorderBottom((short) 1);
            cellDataStyleBold.setBorderLeft((short) 1);
            cellDataStyleBold.setBorderRight((short) 1);
            cellDataStyleBold.setBorderTop((short) 1);
            cellDataStyleBold.setFillPattern(CellStyle.SOLID_FOREGROUND);
            cellDataStyleBold.setFillForegroundColor(HSSFColor.WHITE.index);
            cellDataStyleBold.setAlignment(CellStyle.ALIGN_CENTER);
            cellDataStyleBold.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
            cellDataStyleBold.setWrapText(true);

            // 得到行，并填充数据和表格样式
            for (int i = 0; i < data.length; i++) {
                // Row row = sheet.createRow(i + 1);// 得到行
                Row row = sheet.createRow(i + titleRows);// 得到行
                // row.setHeight((short) 1000);
                row.setHeight((short) rowHeight);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = row.createCell(j);// 得到第0个单元格
                    cell.setCellValue(data[i][j]);// 填充值
                    cell.setCellStyle(cellDataStyle);// 填充样式
                }
            }
            response.setContentType("application/msexcel");
            response.reset();
            response.setHeader("content-disposition", "attachment; filename=" + new String(file_name.getBytes("GBK"), "ISO-8859-1") + ".xls");
            System.setProperty("org.apache.poi.util.POILogger", "org.apache.poi.util.POILogger");
            os = response.getOutputStream();
            work.write(os);
            os.flush();
        } catch (FileNotFoundException e) {
            LogUtil.error(e);
            LogUtil.error("文件路径错误");
        } catch (IOException e) {
            LogUtil.error(e);
            LogUtil.error("文件输入流错误");
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LogUtil.error(e);
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
