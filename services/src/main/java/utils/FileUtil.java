package utils;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class FileUtil {

    public static File createFile(String filename, byte[] bytes) throws IOException {
        File file = new File(filename);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileUtils.writeByteArrayToFile(file, bytes);
        return file;
    }
}
