package com.moon.renamefile.renamerule.impl;

import com.moon.renamefile.exception.ChangeNameFailException;
import com.moon.renamefile.renamerule.RenameRule;

import java.io.File;
import java.util.Objects;

/**
 * @author Chanmoey
 * @date 2022年05月16日
 */
public class RemoveLeadDigit implements RenameRule {

    private String defaultName;

    @Override
    public void rename(File file) {
        String parent = file.getParent();
        String originalName = file.getName();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < originalName.length(); i++) {
            if (!Character.isDigit(originalName.charAt(i))) {
                sb.append(originalName.substring(i));
                break;
            }
        }

        // 如果原来的文件名全是数字，就给个默认名字。如果原来的名字本来就不是数字开头，就跳过。
        String name;
        if (sb.isEmpty()) {
            name = parent + '\\' + Objects.requireNonNullElseGet(defaultName, () -> String.valueOf(System.currentTimeMillis()));
        } else if (sb.length() == originalName.length()){
            return;
        } else {
            name = parent + '\\' + sb;
        }

        boolean changeName = file.renameTo(new File(name));
        if (!changeName) {
            throw new ChangeNameFailException("修改名字失败，原文件路径: " + parent + originalName);
        }
    }

    public void setDefaultName(String name) {
        this.defaultName = name;
    }

    public void setDefaultName() {
        this.defaultName = String.valueOf(System.currentTimeMillis());
    }
}
