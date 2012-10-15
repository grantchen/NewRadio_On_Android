/*
ID: lazydom1
LANG: JAVA
TASK: AuthorEntry.java
Created on: 2012-10-15-下午6:50:31
Author: lazydomino@163.com(pisces)
*/

package org.Pisces.XMLparser;

public class AuthorEntry {

	private String Cname;
	private String Ename;
	private String program_name;
	private String summary;
	private int tot_program;
	public AuthorEntry() {
		Cname = "";
		Ename = "";
		program_name = "";
		summary = "";
		tot_program = 0;
	}
	public AuthorEntry(String cname, String ename, String program_name,
			String summary, int tot_program) {
		super();
		Cname = cname;
		Ename = ename;
		this.program_name = program_name;
		this.summary = summary;
		this.tot_program = tot_program;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public String getEname() {
		return Ename;
	}
	public void setEname(String ename) {
		Ename = ename;
	}
	public String getProgram_name() {
		return program_name;
	}
	public void setProgram_name(String program_name) {
		this.program_name = program_name;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public int getTot_program() {
		return tot_program;
	}
	public void setTot_program(int tot_program) {
		this.tot_program = tot_program;
	}
	
	public String toString()
	{
		return program_name+"  共"+tot_program+"个节目";
	}
	
}

