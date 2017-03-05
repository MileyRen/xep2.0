package developtool;

public class Reg {
	public static final float INITSTROGE=50f;
	public static boolean IsNullOrEmpty(String str)
	{		
		if(str==null)return true;
		else{
			return str.isEmpty();
		}
	}
	public static int RegCheck(String name,String pwd,String pwdcfm,String email )
	{
		if(IsNullOrEmpty(name)||IsNullOrEmpty(pwd)||IsNullOrEmpty(pwdcfm)||IsNullOrEmpty(email))
			return 1;  //not fill all information
		if(!pwd.equals(pwdcfm))
		    return 2;  //confirm password & password not same
		
		
		
		return 100;	
		
	}

}
