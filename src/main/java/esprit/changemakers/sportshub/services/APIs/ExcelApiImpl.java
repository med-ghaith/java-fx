package esprit.changemakers.sportshub.services.APIs;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.util.*;

/**
 * @author Jozef
 */
public class ExcelApiImpl {

    public ExcelApiImpl() {
    }

    public void readExcel() {
        FileInputStream file;
        Workbook workbook = null;
        {
            try {
                file = new FileInputStream(new File("src/main/resources/assets/dataSets/ChatBotDataSet.xlsx"));
                workbook = new XSSFWorkbook(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Sheet sheet = workbook.getSheetAt(0);

        Map<Integer, List<String>> data = new HashMap<>();
        int i = 0;
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for (Cell cell : row) {
                switch (cell.getCellType()) {
                    case STRING:
                        System.out.println(data.get(new Integer(i)).add(cell.getRichStringCellValue().getString()));
                        System.out.println("noice");
                        break;
                    case NUMERIC:
                        if (DateUtil.isCellDateFormatted(cell)) {
                            data.get(i).add(cell.getDateCellValue() + "");
                        } else {
                            data.get(i).add(cell.getNumericCellValue() + "");
                        }
                        System.out.println("mala num");
                        break;
                    case BOOLEAN:
                        data.get(i).add(cell.getBooleanCellValue() + "");
                        System.out.println("mala bool");
                        break;
                    case FORMULA:
                        data.get(i).add(cell.getCellFormula() + "");
                        System.out.println("netsawarch l7a9");
                        break;
                    default:
                        data.get(new Integer(i)).add(" ");
                }
            }
            i++;
        }
    }

    /**
     * To read a full excel file using it's path
     * */
    public static void readExemple(String filename) {
        // Excel file name. You can create a file name with a full
        // path information.
        // exemple of path
        //String filename = "E:\\2021_2021_ESPRIT_3A21\\PI_Dev\\0_Code\\1.1_sports-hubTestChatBotWithExcel\\src\\main\\resources\\assets\\dataSets\\ChatBotDataSet.xlsx";

        // Create an ArrayList to store the data read from Excel sheet.
        List<List<XSSFCell>> sheetData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filename)) {
            // Create an Excel workbook from the file system.
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            XSSFSheet sheet = workbook.getSheetAt(0);

            // When we have a sheet object in hand we can iterator on
            // each sheet's rows and on each row's cells. We store the
            // data read on an ArrayList so that we can print the
            // content of the Excel to the console.
            Iterator<Row> rows = sheet.rowIterator();
            while (rows.hasNext()) {
                XSSFRow row = (XSSFRow) rows.next();
                Iterator<Cell> cells = row.cellIterator();

                List<XSSFCell> data = new ArrayList<>();
                while (cells.hasNext()) {
                    XSSFCell cell = (XSSFCell) cells.next();
                    data.add(cell);
                }
                sheetData.add(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        showExcelData(sheetData);
    }

    public static void showExcelData(List<List<XSSFCell>> sheetData) {
        // Iterates the data and print it out to the console.
        for (List<XSSFCell> data : sheetData) {
            for (XSSFCell cell : data) {
                System.out.println(cell);
            }
        }
    }

    // A method returning a list representing a column
    public static List<String> readColumnByName(String columnName) {
        // Excel file name. You can create a file name with a full
        // path information.
        String filename = "src/main/resources/assets/dataSets/ChatBotDataSet.xlsx";
        List<String> values = null;
        // Create an ArrayList to store the data read from Excel sheet.
        List<List<XSSFCell>> sheetData = new ArrayList<>();

        try (FileInputStream fis = new FileInputStream(filename)) {
            // Create an Excel workbook from the file system.
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            // Get the first sheet on the workbook.
            XSSFSheet sheet = workbook.getSheetAt(0);
            values = new ArrayList<String>();
            for(Row r : sheet) {
                Cell c = r.getCell(getColumnNumber(sheet,columnName));
                if(c != null) {
                    if(c.getCellType() == CellType.STRING) {
                        values.add(c.getStringCellValue());
                    }else if(c.getCellType() == CellType.NUMERIC) {
                        values.add(String.valueOf((int) c.getNumericCellValue()));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return values;
    }

    // MAYBE THERE IS A BETTER WAY OF DOING IT, BUT SINCE THE API DONT HAVE A WAY TO ITERATE OVER
    // COLUMNS WE CREATED A METHOD TO RETURN THE NUMBER OF THE COLUMN STARTING WITH A SPECEFIC STRING
    public static int getColumnNumber(Sheet sheet,String columnName){
        Iterator<Row> rows = sheet.rowIterator();
        if (rows.hasNext()) {
            XSSFRow row = (XSSFRow) rows.next();
            Iterator<Cell> cells = row.cellIterator();
            int i=0;
            while (cells.hasNext()) {
                XSSFCell cell = (XSSFCell) cells.next();
                if(cell.getStringCellValue().equals(columnName)){
                    return i;
                }else {
                    i++;
                }
            }
        }
        return -1;
    }

    public static int getFirstColumnEmptyCellNumber(Sheet sheet, int columNumber){
        int i = 0;
        for(Row r : sheet) {
            Cell c = r.getCell(columNumber);
            if(c == null || c.getCellType() == CellType.BLANK ) {
                return i;
            }else{
                i++;
            }
        }
        return -1;
    }
    public static void modify(String fileName,int rowNumber,int columnNumber,String value){
        try(InputStream inputStream = new FileInputStream(fileName)) {
            XSSFWorkbook workbook = XSSFWorkbookFactory.createWorkbook(OPCPackage.open(inputStream));
            XSSFSheet sheet =(XSSFSheet) workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(rowNumber);
            if(row == null){
                row = sheet.createRow(rowNumber);
            }
            XSSFCell cell = row.getCell(columnNumber);
            if(cell == null) {
                cell = row.createCell(columnNumber);
                System.out.println("cell created");
            }

            cell.setCellValue(value);
            System.out.println("value setted");
            try(OutputStream outputStream = new FileOutputStream(fileName)) {
                workbook.write(outputStream);
                System.out.println("value written");
                outputStream.close();
                workbook.close();
            }
        }catch (IOException | InvalidFormatException ex) {
            ex.printStackTrace();
        }
    }

}








