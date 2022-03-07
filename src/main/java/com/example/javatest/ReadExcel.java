package com.example.javatest;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;


public class ReadExcel {

    static SimpleDateFormat f = new SimpleDateFormat("HH:mm:ss:SSS", Locale.KOREA);

    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream("C:/Users/daou/Documents/TEST/test.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            int rowindex = 0;
            int columnindex = 0;
            XSSFSheet sheet = workbook.getSheetAt(0);
            // 행의 수
            int rows = sheet.getPhysicalNumberOfRows();

            String tmpTime = "";

            for (rowindex = 0; rowindex < rows; rowindex++) {
                //행을읽는다
                XSSFRow row = sheet.getRow(rowindex);
                if (row != null) { //셀의 수
                    int cells = row.getPhysicalNumberOfCells();
                    for (columnindex = 0; columnindex <= cells; columnindex++) { //셀값을 읽는다
                        XSSFCell cell = row.getCell(columnindex);
                        String value = "";
                        //셀이 빈값일경우를 위한 널체크
                        if (cell == null) {
                            continue;
                        } else {
                            //타입별로 내용 읽기
                            switch (cell.getCellType()) {
                                case XSSFCell.CELL_TYPE_FORMULA:
                                    value = cell.getCellFormula();
                                    break;
                                case XSSFCell.CELL_TYPE_NUMERIC:
                                    value = cell.getNumericCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_STRING:
                                    value = cell.getStringCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_BOOLEAN:
                                    value = cell.getBooleanCellValue() + "";
                                    break;
                                case XSSFCell.CELL_TYPE_ERROR:
                                    value = cell.getErrorCellValue() + "";
                                    break;
                            }
                        }
                        //System.out.println(rowindex + "번 행 : " + columnindex + "번 열 값은: " + value);



                        //Time
                        if(rowindex != 0 && columnindex == 3){
                            tmpTime = value;
                        }

                        //ElapsedTime
                        if(!tmpTime.isEmpty() && rowindex != 0 && columnindex == 4){

                            Date d1 = f.parse("10:05:10:571");
                            Date d2 = f.parse("10:05:07:334");
                            long diff = d1.getTime() - ((Date) d2).getTime();
                            long sec = diff / 1000;
                            long milsec = diff % 1000;

                            //System.out.println("파싱전" + tmpTime);
                            Date tmpDate = f.parse(tmpTime);

                            LocalDateTime localDateTime = tmpDate.toInstant() // Date -> Instant
                                    .atZone(ZoneId.systemDefault()) // Instant -> ZonedDateTime
                                    .toLocalDateTime(); // ZonedDateTime -> LocalDateTime
                            LocalDateTime localDateTime2 = localDateTime.minusNanos(Long.parseLong(value.substring(0,value.length()-2))*1000000);

                            //System.out.println(localDateTime.toString().substring(11,23));
                            System.out.println(localDateTime2.toString().substring(11,23));
                            //System.out.println(sec + "." + milsec);
                            //System.out.println();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
