/*
ID: lazydom1
LANG: JAVA
TASK: PUB.java
Created on: 2012-11-12-下午5:54:16
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.IO;

import java.util.ArrayList;
/*
 * 存储公共信息的地方
 * list 存放的是当前下载列表
 * @author will
 */
public class PUB {
	//当前下载列表，防止重复开启线程下载引起的bug
		private static ArrayList<DownLoadersInfo> list = new ArrayList<DownLoadersInfo>();
		
		
		public static void add(DownLoadersInfo d)
		{
			list.add(d);
		}
		public static void remove(String url)
		{
			for(int i=0;i<list.size();i++)
			{
				DownLoadersInfo d = list.get(i);
				if(d.url.equalsIgnoreCase(url))
					list.remove(d);
			}
		}
		
		public static boolean contain(String url)
		{
			for(DownLoadersInfo d:list)
			{
				if(d.url.equalsIgnoreCase(url))
					return true;
			}
			return false;
		}
		public static Downloader getDownloader(String url)
		{
			for(DownLoadersInfo d:list)
			{
				if(d.url.equalsIgnoreCase(url))
					return d.downloader;
			}
			return null;
		}
		public static DownLoadersInfo getDownloadersInfo(int index)
		{
			return list.get(index);
		}
		public static int getSize()
		{
			return list.size();
		}
		
	
}

