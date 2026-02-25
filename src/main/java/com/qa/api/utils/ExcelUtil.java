package com.qa.api.utils;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelUtil {
    private static FileInputStream fip;
    private static Workbook workBook;
    private static Sheet sheet;

    public static Object[][] getData(String fileName, String sheetName){
        String filePath="./src/test/resources/testdata/"+fileName+".xlsx";
        Object[][] objectData=null;
        try {
            fip=new FileInputStream(filePath);
            workBook= WorkbookFactory.create(fip);
            sheet=workBook.getSheet(sheetName);

            int rows=sheet.getLastRowNum();
            int column=sheet.getRow(0).getLastCellNum();

           objectData = new Object[rows][column];

            for(int i=0;i<rows;i++){
                for(int j=0;j<column;j++){
                    objectData[i][j]=new DataFormatter().formatCellValue(sheet.getRow(i+1).getCell(j)).trim();;
                }
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException("File not found:"+filePath +":"+e.getMessage());
        }catch (IOException e) {
            throw new RuntimeException("File not found:"+filePath +":"+e.getMessage());
        }
        return objectData;
    }
}
