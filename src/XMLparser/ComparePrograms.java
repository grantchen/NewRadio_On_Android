/*
ID: lazydom1
LANG: JAVA
TASK: ComparePrograms.java
Created on: 2012-10-13-下午4:06:39
Author: lazydomino@163.com(pisces)
*/

package XMLparser;

import java.util.Comparator;

public class ComparePrograms implements Comparator<ProgramEntry>
{

	@Override
	public int compare( ProgramEntry a, ProgramEntry b) {
		// TODO Auto-generated method stub
		if (a.getID()>b.getID()) return -1;
		return 1;
	}
}