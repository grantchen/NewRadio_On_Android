/*
ID: lazydom1
LANG: JAVA
TASK: DirHelper.java
Created on: 2012-10-18-下午3:20:49
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.IO;

import java.io.File;

/*
 * 文件夹的辅助类
 * 
 */
public class DirHelper {
	
	private final static String basePath = "/mnt/sdcard/";
	
	/**
     * 在SD卡上创建目录
     * 
     * @param dirName
     */
    public static File createDir(String dirName) {
            File dir = new File(basePath + dirName);
            dir.mkdir();
            return dir;
    }

    /**
     * 判断SD卡上的文件夹是否存在
     */
    public static boolean isExist(String fileName) {
            File file = new File(basePath + fileName);
            return file.exists();
    }

}

