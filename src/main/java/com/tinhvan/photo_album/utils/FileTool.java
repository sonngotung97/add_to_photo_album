package com.tinhvan.photo_album.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

public class FileTool {
    public static String createName(String name, String extension, boolean addTime) {

        return (!addTime) ? (name + "." + extension) : (name + "_" + DateTool.format(new Date().getTime(), DateTool.PATTERN_FULL_2) + "." + extension);
    }

    public static File createFile(String path, String outputFileName) throws IOException {
        Files.createDirectories(Paths.get(path));
        File file = new File(path + File.separator + outputFileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }
}
