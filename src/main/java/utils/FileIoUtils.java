package utils;

import exception.NotExistException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileIoUtils {
    private FileIoUtils() {
    }

    public static byte[] loadFileFromClasspath(String filePath) throws IOException, URISyntaxException {
        URL resource = FileIoUtils.class.getClassLoader().getResource(filePath);
        if (resource == null) {
            throw new NotExistException(filePath + "이(가) 존재하지 않습니다.");
        }

        Path path = Paths.get(resource.toURI());
        return Files.readAllBytes(path);
    }

    public static boolean hasSameContents(String filePath1, String filePath2) {
        List<String> file1 = readFile(filePath1);
        List<String> file2 = readFile(filePath2);
        return file1.size() == file2.size() && file1.containsAll(file2);
    }

    private static List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            while (line != null) {
                lines.add(line);
                line = br.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
}
