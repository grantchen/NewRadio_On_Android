/*
ID: lazydom1
LANG: JAVA
TASK: DirHelper.java
Created on: 2012-10-14-下午7:57:31
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.IO;

import java.io.File;

public class DirHelper {

	private static final String SDPATH = android.os.Environment.getExternalStorageDirectory()+"/"; 
	
	/**
     * 在SD卡上创建目录
     * 
     * @param dirName
     */
    public static File creatSDDir(String dirName) {
            File dir = new File(SDPATH + dirName);
            dir.mkdir();
            return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isFileExist(String fileName) {
            File file = new File(SDPATH + fileName);
            return file.exists();
    }
}

