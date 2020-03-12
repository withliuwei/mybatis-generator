package vip.liuw.mybatisenerator.util;

import java.io.File;
import java.util.Objects;

public class FileUtils {

    public static boolean deleteFile(File file) {
        boolean flag;
        if (flag = file.exists()) {
            if (file.isFile()) {
                flag = file.delete();
            } else {
                for (File childFile : Objects.requireNonNull(file.listFiles())) {
                    flag &= deleteFile(childFile);
                }
            }
        }
        return flag;
    }

}
