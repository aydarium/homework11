package ru.aydar;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.zip.ZipFile;

@DisplayName("Проверки файлов, запакованных в zip-архив")
public class ZippedFilesParsingTest {
    private final ClassLoader cl = ZippedFilesParsingTest.class.getClassLoader();
    private final String zipName = "files.zip";

    private InputStream getFileStreamFromZip (String zipName, String fileName) throws IOException
    {
        URL file = cl.getResource(zipName);
        ZipFile zipFile = new ZipFile(file.getPath());
        return zipFile.getInputStream(zipFile.getEntry(fileName));
    }

    @Test
    @DisplayName("Проверка PDF-файла")
    void pdfParsingTest() throws Exception {
        try (InputStream is = getFileStreamFromZip(zipName,"dummy.pdf"))
        {
            PDF pdf = new PDF(is);
            Assertions.assertEquals("Evangelos Vlachogiannis", pdf.author);
        }
    }

    @Test
    @DisplayName("Проверка XLSX-файла (Excel)")
    void xlsxParsingTest() throws Exception {
        try (InputStream is = getFileStreamFromZip(zipName,"food.xlsx"))
        {
            XLS xls = new XLS(is);
            Assertions.assertEquals(
                    "Yuck",
                    xls.excel.getSheet("Sheet1")
                            .getRow(6)
                           .getCell(2)
                            .getStringCellValue()
            );
        }
    }

    @Test
    @DisplayName("Проверка CSV-файла")
    void csvParsingTest() throws Exception {
        try (InputStream is = getFileStreamFromZip(zipName,"food.csv");
             InputStreamReader isr = new InputStreamReader(is);
             CSVReader csvReader = new CSVReader(isr))
        {
            List<String[]> content = csvReader.readAll();
            Assertions.assertArrayEquals(
                    new String[]{"Carrot", "Vegetable"},content.get(2)
            );
        }
    }
}
