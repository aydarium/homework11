package ru.aydar;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aydar.model.Hobbyist;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

@DisplayName("Проверка JSON-файла")
public class JsonParsingTest {
    private final ClassLoader cl = ZippedFilesParsingTest.class.getClassLoader();

    @Test
    @DisplayName("Проверка массива людей и их увлечений в JSON-файле")
    void jsonParsingTest() throws IOException {
        try (InputStream is = cl.getResourceAsStream("hobbyists.json");
             Reader reader = new InputStreamReader(is))
        {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Hobbyist> hobbyists = objectMapper.readValue(reader, new TypeReference<>(){});
            Assertions.assertEquals("Vasya", hobbyists.get(0).getFirstName());
            Assertions.assertArrayEquals(
                    new String[]{"slouching", "embroidery"},
                    hobbyists.get(0).getHobby().toArray()
            );
            Assertions.assertEquals("Doe-Doe", hobbyists.get(1).getSurname());
            Assertions.assertEquals(15, hobbyists.get(1).getAge());
        }
    }
}
