package com.moon.renamefile.fileoperation;

import com.moon.renamefile.fileoperation.impl.RenameFile;
import com.moon.renamefile.renamerule.RenameRule;
import com.moon.renamefile.renamerule.impl.RemoveLeadDigit;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

/**
 * @author Chanmoey
 * @date 2022年05月16日
 */
class RenameFileTest {

    @Test
    void test_renameFile() {
        RenameRule renameRule = new RemoveLeadDigit();
        FileOperation renameFile = new RenameFile(renameRule);
        File file = new File("E:\\renametest\\dir\\ccc.txt");
        renameFile.renameFile(file);
    }

    @Test
    void test_renameFileInDir() {
        RenameRule renameRule = new RemoveLeadDigit();
        FileOperation renameFile = new RenameFile(renameRule);
        renameFile.renameFileInDir("E:\\renametest\\dir");
    }

    @Test
    void test_renameAllFileInDir() {
        RenameRule renameRule = new RemoveLeadDigit();
        FileOperation renameFile = new RenameFile(renameRule);
        renameFile.renameAllFileInDir("E:\\renametest\\all");
    }

    @Test
    void test_printDir() {
        File file = new File("E:\\renametest\\all\\dir");
        String[] names = file.list();
        File[] files = file.listFiles();
        System.out.println(Arrays.toString(names));
        for (File f : files) {
            if (f.isDirectory()) {
                System.out.println(f.getPath());
            }
        }
    }

    @Test
    void test_allFileName() {
        RenameRule renameRule = new RemoveLeadDigit();
        FileOperation renameFile = new RenameFile(renameRule);
        renameFile.renameAllFileInDir("E:\\学校相关\\TSP");
    }
}
