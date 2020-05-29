//*CID://+@@01R~: update#= 279;                                    //~1Ac0R~//~v@@@R~//~@@01R~
//**********************************************************************//~1107I~
//@@01 20181105 for BTMJ3                                            //~@@01I~
//**********************************************************************//~1107I~//~v106M~
package np.jnp.npanew.utils;                                       //+@@01R~

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import java.text.SimpleDateFormat;                                 //~@@01I~
import java.util.Date;                                             //~@@01I~
//**********************************************************************//~1107I~
public class Utils                                            //~1309R~//~@@@@R~
{                                                                  //~0914I~
//**********************************                               //~@@01I~
//*edit date/time                                                  //~@@01I~
//**********************************                               //~@@01I~
	public static final int TS_DATE_TIME=1;                        //~@@01I~
	public static final int TS_MILI_TIME=2;                        //~@@01I~
	public static final int TS_DATE_TIME2=3;                       //~@@01I~
	private static final SimpleDateFormat fmtdt=new SimpleDateFormat("yyyyMMdd-HHmmss");//~@@01I~
	private static final SimpleDateFormat fmtdt2=new SimpleDateFormat("yyyyMMdd:HHmmss");//~@@01I~
	private static final SimpleDateFormat fmtms=new SimpleDateFormat("HHmmss.SSS");//~@@01I~
	private static final String sharedPreferenceName=AG.appNameE+"-PrivatePreference";//~@@01R~
	public static String getTimeStamp(int Popt,Date Pdate)         //~@@01I~
    {                                                              //~@@01I~
        SimpleDateFormat f;                                        //~@@01I~
    //**********************:                                      //~@@01I~
    	switch(Popt)                                               //~@@01I~
        {                                                          //~@@01I~
        case TS_DATE_TIME:                                         //~@@01I~
        	f=fmtdt;                                               //~@@01I~
            break;                                                 //~@@01I~
        case TS_DATE_TIME2:                                        //~@@01I~
        	f=fmtdt2;                                              //~@@01I~
            break;                                                 //~@@01I~
        case TS_MILI_TIME:                                         //~@@01I~
        	f=fmtms;                                               //~@@01I~
            break;                                                 //~@@01I~
        default:                                                   //~@@01I~
        	return null;                                           //~@@01I~
        }                                                          //~@@01I~
        String s=f.format(Pdate);                                  //~@@01I~
//      if (Dump.Y) Dump.println("Utils.getTimeStamp opt="+Popt+",rc="+s);//~@@01I~
        return s;                                                  //~@@01I~
    }                                                              //~@@01I~
	public static String getTimeStamp(int Popt)                    //~@@01I~
    {                                                              //~@@01I~
        return getTimeStamp(Popt,new Date());                      //~@@01I~
    }                                                              //~@@01I~
	public static String getTimeStamp(int Popt,long Ptime)         //~@@01I~
    {                                                              //~@@01I~
        return getTimeStamp(Popt,new Date(Ptime));                 //~@@01I~
    }                                                              //~@@01I~
	public static String getTimeStamp(String Pfmt,long Ptime)      //~@@01I~
    {                                                              //~@@01I~
		SimpleDateFormat f=new SimpleDateFormat(Pfmt);             //~@@01I~
        return f.format(new Date(Ptime));                          //~@@01I~
    }                                                              //~@@01I~
//**********************************                               //~1425I~
//* Digit Thread ID                                                //~1425I~
//**********************************                               //~1425I~
	public static String getThreadId()                             //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	int tid=(int)Thread.currentThread().getId();               //~1425I~
        if (tid<10)                                                //~1425I~
        	return "0"+tid;                                        //~1425I~
        return Integer.toString(tid);                              //~1425I~
    }                                                              //~1425I~
//**********************************                               //~1425I~
	public static String getThreadTimeStamp()                      //~1425I~
    {                                                              //~1425I~
    //**********************:                                      //~1425I~
    	String tidts=getThreadId()+":"+getTimeStamp(TS_MILI_TIME);  //~1425I~
        return tidts;                                              //~1425I~
    }                                                              //~1425I~
//**********************                                           //~v@@@I~//~@@01I~
    public static String getStr(int Presid)                        //~v@@@I~//~@@01I~
	{                                                              //~v@@@I~//~@@01I~
    	if (Presid==0)                                             //~@@01I~
        	return "";                                             //~@@01I~
    	return AG.resource.getString(Presid);                      //~v@@@R~//~@@01I~
    }                                                              //~v@@@I~//~@@01I~
//***********************************************************************//~@@01I~
    public static boolean isDebuggable(Context ctx)                //~@@01I~
    {                                                              //~@@01I~
        PackageManager manager = ctx.getPackageManager();          //~@@01I~
        ApplicationInfo appInfo = null;                            //~@@01I~
        try                                                        //~@@01I~
        {                                                          //~@@01I~
            appInfo = manager.getApplicationInfo(ctx.getPackageName(), 0);//~@@01I~
        }                                                          //~@@01I~
        catch (PackageManager.NameNotFoundException e)                            //~@@01I~
        {                                                          //~@@01I~
            return false;                                          //~@@01I~
        }                                                          //~@@01I~
        if ((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE)//~@@01I~
            return true;                                           //~@@01I~
        return false;                                              //~@@01I~
    }                                                              //~@@01I~
    //*************************************************            //~@@01I~
    public static String toString(Object Pobj)                     //~@@01I~
    {                                                              //~@@01I~
    	return Pobj==null ? "null" : Pobj.toString();              //~@@01I~
    }                                                              //~@@01I~
//**********************************************************************//~@@01I~
//*Preference read/write                                               *//~@@01I~
//**********************************************************************//~@@01I~
    public static String getPreference(String Pkey)                //~@@01I~
    {                                                              //~@@01I~
	    return getPreference(Pkey,"");                             //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    public static boolean getPreference(String Pkey,boolean Pdefault)//~@@01I~
    {                                                              //~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        boolean value=pref.getBoolean(Pkey,Pdefault);              //~@@01I~
        return value;                                              //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    public static int getPreference(String Pkey,int Pdefault)      //~@@01I~
    {                                                              //~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        int value=pref.getInt(Pkey,Pdefault);                      //~@@01I~
        return value;                                              //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    public static String getPreference(String Pkey,String Pdefault)//~@@01I~
    {                                                              //~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        String value=pref.getString(Pkey,Pdefault/*default value*/);//~@@01I~
        if (Dump.Y) Dump.println("getPreference:"+Pkey+"="+value); //~@@01I~
        return value;                                              //~@@01I~
    }//readwriteQNo                                                //~@@01I~
    //******************                                           //~@@01I~
    public static void putPreference(String Pkey,String Pvalue)    //~@@01I~
    {                                                              //~@@01I~
        if (Dump.Y) Dump.println("putPreference:"+Pkey+"="+Pvalue);//~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        SharedPreferences.Editor editor=pref.edit();               //~@@01I~
        editor.putString(Pkey,Pvalue);                             //~@@01I~
        editor.commit();                                           //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    public static void putPreference(String Pkey,boolean Pvalue)   //~@@01I~
    {                                                              //~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        SharedPreferences.Editor editor=pref.edit();               //~@@01I~
        editor.putBoolean(Pkey,Pvalue);                            //~@@01I~
        editor.commit();                                           //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    public static void putPreference(String Pkey,int Pvalue)       //~@@01I~
    {                                                              //~@@01I~
    	SharedPreferences pref=getPreferenceName();                //~@@01I~
        SharedPreferences.Editor editor=pref.edit();               //~@@01I~
        editor.putInt(Pkey,Pvalue);                                //~@@01I~
        editor.commit();                                           //~@@01I~
    }                                                              //~@@01I~
    //******************                                           //~@@01I~
    private static SharedPreferences getPreferenceName()           //~@@01I~
    {                                                              //~@@01I~
        return AG.context.getSharedPreferences(sharedPreferenceName,Context.MODE_PRIVATE);//~@@01I~
    }                                                              //~@@01I~
    //******************************************************************************//~@@01I~
    //*!! call from onStart                                        //~@@01I~
    //******************************************************************************//~@@01I~
    public static void setDialogWidth(Dialog Pdlg, double Prate)   //~@@01I~
    {                                                              //~@@01I~
	    int ww;                                                    //~@@01I~
	    ww=(int)(AG.scrWidth*Prate);                               //~@@01I~
        int hh= ViewGroup.LayoutParams.WRAP_CONTENT;                //~@@01I~
        if (Dump.Y) Dump.println("Uview.setDialogWidth:ww="+ww+",hh="+hh+",rate="+Prate+",scrWidth="+AG.scrWidth+",portrait="+AG.portrait);//~@@01I~
        Pdlg.getWindow().setLayout(ww,hh);                         //~@@01I~
    }                                                              //~@@01I~
//******************************************************           //~@@01I~
	public static Point getScrSize()                               //~@@01I~
    {                                                              //~@@01I~
		Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~@@01I~
        Point p=new Point();                                       //~@@01I~
        display.getSize(p); //Real_height - NavigationBar_Height_underneath//~@@01R~
        if (Dump.Y) Dump.println("Utils.getScrSize w="+p.x+",h="+p.y);//~@@01I~
        return p;                                                  //~@@01I~
    }                                                              //~@@01I~
//******************************************************           //~@@01I~
	public static int getActionBarHeight()                         //~@@01I~
    {                                                              //~@@01I~
		ActionBar ab=AG.aMainActivity.getActionBar();              //~@@01R~
        int hh=ab.getHeight();                                     //~@@01I~
        if (Dump.Y) Dump.println("Utils.getActionBarHeight hh="+hh);//~@@01I~
        return hh;                                                 //~@@01I~
    }                                                              //~@@01I~
    //*******************************************************      //~v@@@I~//~@@01I~
	public static Point getScrRealSize()         //~v@@@I~         //~@@01R~
    {                                                              //~v@@@I~//~@@01I~
		Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~@@01I~
        Point p=new Point();                                       //~@@01I~
		if (Build.VERSION.SDK_INT>=19)   //Navigationbar can be hidden//~v@@@R~//~@@01I~
        {                                                          //~v@@@I~//~@@01I~
        	display.getRealSize(p); //api17:4.2.2 JELLY bean mr1  //~v@@@R~//~@@01R~
	        if (Dump.Y) Dump.println("Utils:getScreemRealSize getRealSize() w="+p.x+",h="+p.y);//~v@@@I~//~@@01R~
        }                                                          //~v@@@I~//~@@01I~
        else                                                       //~v@@@I~//~@@01I~
        {                                                          //~v@@@I~//~@@01I~
			DisplayMetrics m=new DisplayMetrics();                 //~v@@@R~//~@@01I~
			display.getMetrics(m);                                //~v@@@R~//~@@01R~
        	p.x=m.widthPixels;                         //~v@@@R~   //~@@01R~
        	p.y=m.heightPixels;                       //~v@@@R~    //~@@01R~
	        if (Dump.Y) Dump.println("Utils:getScreenRealSize Displaymetrics w="+p.x+",h="+p.y);//~v@@@I~//~@@01R~
        }                                                          //~v@@@I~//~@@01I~
        return p;                                                  //~@@01I~
    }                                                              //~v@@@I~//~@@01I~
    //*******************************************************      //~@@01I~
    public static int getTitleBarHeight()                          //~@@01I~
    {                                                              //~@@01I~
        int hh;
        Rect rect=new Rect();                                      //~@@01I~
        android.view.Window w=AG.activity.getWindow();             //~@@01I~
        View v=w.getDecorView();                                   //~@@01I~
        v.getWindowVisibleDisplayFrame(rect);                      //~@@01I~
        if (Dump.Y) Dump.println("Utils:getTitleBarHeight  DecorView rect="+rect.toString());//~@@01I~
        v=w.findViewById(android.view.Window.ID_ANDROID_CONTENT);  //~@@01R~
        hh=v.getTop();  //titlebar+actionbar                   //~@@01R~
        if (Dump.Y) Dump.println("Utils getTitleBarHeight title+action="+hh);//~@@01I~
        hh=rect.top;    //titlebar only                        //~@@01I~
        if (Dump.Y) Dump.println("Utils getTitleBarHeight hh="+hh);//~@@01R~
        return hh;                                                 //~@@01I~
    }                                                              //~@@01I~
}//class Utils                                                //~1309R~//~v@@@R~
