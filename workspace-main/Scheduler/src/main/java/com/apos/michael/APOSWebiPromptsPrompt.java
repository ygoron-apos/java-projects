package com.apos.michael;

import java.util.ArrayList;
import java.util.List;

public class APOSWebiPromptsPrompt {
	public String webiPrompt_Question = "";
	public String webiPrompt_PID = "";
	public String webiPrompt_Locale = "";
	public String webiPrompt_Optional = "";
	public String webiPrompt_Persistent = "";
	public String webiPrompt_IndexAware = "";
	public String webiPrompt_Order = "";
	public String webiPrompt_Response = "";
	public String webiPrompt_Origin = "";
	public String webiPrompt_Type = "";
	public List<String> DPIDList = new ArrayList<String>();
	
	public String toString() 
	{
		return "webiPrompt_Question = " + webiPrompt_Question +
		", webiPrompt_PID = " + webiPrompt_PID +
		", webiPrompt_Locale = " + webiPrompt_Locale +
		", webiPrompt_Optional = " + webiPrompt_Optional +
		", webiPrompt_Persistent = " + webiPrompt_Persistent +
		", webiPrompt_IndexAware = " + webiPrompt_IndexAware +
		", webiPrompt_Order = " + webiPrompt_Order +
		", webiPrompt_Response = " + webiPrompt_Response +
		", webiPrompt_Origin = " + webiPrompt_Origin +
		", webiPrompt_Type = " + webiPrompt_Type +
		", DPIDLIST = " + DPIDList;
	}
}
