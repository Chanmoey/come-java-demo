package com.moon.renamefile.fileoperation;

import java.io.File;

/**
 * @author Chanmoey
 * @date 2022年04月19日
 */
public interface FileOperation {

    /**
     * 重命名单个文件。
     *
     * @param file 需要重命名的文件。
     */
    void renameFile(File file) ;

    /**
     * 重命名该目录下的所有文件, 不包括文件夹，且不递归改变该目录下的文件夹的文件。
     *
     * @param filePath 需要重命名的文件夹路径。
     */
    void renameFileInDir(String filePath);

    /**
     * 重命名该目录下的所有文件, 不包括文件夹，但递归访问所有文件。
     *
     * @param filePath 需要重命名的文件夹路径。
     */
    void renameAllFileInDir(String filePath);
}
