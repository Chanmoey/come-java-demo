package com.moon.renamefile.renamerule;

import java.io.File;

/**
 * @author Chanmoey
 * @date 2022年04月19日
 */
@FunctionalInterface
public interface RenameRule {

    /**
     * 重命名一个文件的具体实现接口。
     *
     * @param file 需要重命名的文件。
     */
    void rename(File file);
}
