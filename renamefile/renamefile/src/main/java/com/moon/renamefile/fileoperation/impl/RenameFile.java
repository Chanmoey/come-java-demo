package com.moon.renamefile.fileoperation.impl;

import com.moon.renamefile.fileoperation.FileOperation;
import com.moon.renamefile.renamerule.RenameRule;

import java.io.File;

/**
 * @author Chanmoey
 * @date 2022年04月19日
 */
public class RenameFile implements FileOperation {

    private final RenameRule renameRule;

    public RenameFile(RenameRule renameRule) {
        this.renameRule = renameRule;
    }

    public void renameFile(File file) {
        if (file == null) {
            throw new NullPointerException();
        }
        this.renameRule.rename(file);
    }

    public void renameFileInDir(String filePath) {
        RenameFile.checkPath(filePath);
        File file = new File(filePath);
        if (file.isFile()) {
            this.renameFile(file);
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File subFile : files) {
                if (subFile.isFile()) {
                    this.renameFile(subFile);
                }
            }
        }
    }

    public void renameAllFileInDir(String filePath) {
        RenameFile.checkPath(filePath);
        File file = new File(filePath);
        if (file.isFile()) {
            this.renameFile(file);
            return;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return;
            }
            for (File subFile : files) {
                if (subFile.isFile()) {
                    this.renameFile(subFile);
                } else {
                    this.renameAllFileInDir(subFile.getPath());
                }
            }
        }
    }

    public static void checkPath(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            throw new IllegalArgumentException();
        }
    }
}
