//*CID://+va80R~: update#= 304;                                    //~va80R~
//**********************************************************************//~1107I~
//va80 240219 selectable BGM                                       //~va80I~
//va56 221103 Finish by Back button                                //~va56I~
//va54 221103 BTMJ-1aj0 androd11(api30) deprecated at api30;getDefaultDisplay, display.getSize(), display/getMetrics()//~va54I~
//va53 221103 Ahsv-1amf deprecated api33; PackageManager.getApplicationInfo//~vat2I~//~va53I~
//@@01 20181105 for BTMJ3                                            //~@@01I~
//**********************************************************************//~1107I~//~v106M~
package np.jnp.npanew.utils;                                       //~@@01R~
                                                                   //~va80I~
import np.jnp.npanew.R;                                            //~va80I~

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Insets;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.annotation.TargetApi;                               //~1aj0I~//~va54I~
import android.view.WindowMetrics;
import android.Manifest;                                           //~va80I~

import java.text.SimpleDateFormat;                                 //~@@01I~
import java.util.Date;                                             //~@@01I~
import androidx.core.app.ActivityCompat;                           //~va80I~
import android.widget.Toast;                                       //~v@@@I~//~va80I~
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
//**********************                                           //~v@@@I~//~va80I~
    public static String getStr(int Presid,String P1)              //~v@@@I~//~va80I~
	{                                                              //~v@@@I~//~va80I~
    	return AG.resource.getString(Presid,P1);                   //~v@@@I~//~va80I~
    }                                                              //~v@@@I~//~va80I~
//**********************                                           //~v@@@I~//~va80I~
    public static String getStr(int Presid,int P1)                 //~v@@@I~//~va80I~
	{                                                              //~v@@@I~//~va80I~
    	return AG.resource.getString(Presid,P1);                   //~v@@@I~//~va80I~
    }                                                              //~v@@@I~//~va80I~
//**********************                                           //~v@@@I~//~va80I~
    public static String getStr(int Presid,String P1,String P2)    //~v@@@I~//~va80I~
	{                                                              //~v@@@I~//~va80I~
    	return AG.resource.getString(Presid,P1,P2);                //~v@@@I~//~va80I~
    }                                                              //~v@@@I~//~va80I~
//**********************                                           //~vae7I~//~va80I~
    public static String getStr(int Presid,int P1,int P2)          //~vae7I~//~va80I~
	{                                                              //~vae7I~//~va80I~
    	return AG.resource.getString(Presid,P1,P2);                //~vae7I~//~va80I~
    }                                                              //~vae7I~//~va80I~
//***********************************************************************//~@@01I~
	@SuppressWarnings("deprecation")                               //~vat2I~//~va53I~
    public static boolean isDebuggable(Context ctx)                //~@@01I~
    {                                                              //~@@01I~
        PackageManager manager = ctx.getPackageManager();          //~@@01I~
        ApplicationInfo appInfo = null;                            //~@@01I~
        try                                                        //~@@01I~
        {                                                          //~@@01I~
		  if (AG.osVersion>=33)                                    //~vat2I~//~va53I~
            appInfo = getApplicationInfo33(manager,ctx);               //~vat2I~//~va53I~
          else                                                     //~vat2I~//~va53I~
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
//  	Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~@@01I~//~va54R~
    	Display display=getDefaultDisplay();                       //~va54I~
        Point p=new Point();                                       //~@@01I~
//      display.getSize(p); //Real_height - NavigationBar_Height_underneath//~@@01R~//~va54R~
        getDisplaySize(display,p);                                 //~va54I~
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
//  	Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~@@01I~//~va54R~
    	Display display=getDefaultDisplay();                       //~va54I~
        Point p=new Point();                                       //~@@01I~
		if (Build.VERSION.SDK_INT>=19)   //Navigationbar can be hidden//~v@@@R~//~@@01I~
        {                                                          //~v@@@I~//~@@01I~
//      	display.getRealSize(p); //api17:4.2.2 JELLY bean mr1  //~v@@@R~//~@@01R~//~va54R~
        	getRealSize(display,p); //api17:4.2.2 JELLY bean mr1  //~vam6I~//~1am3I~//~va54I~
	        if (Dump.Y) Dump.println("Utils:getScrRealSize getRealSize() w="+p.x+",h="+p.y);//~v@@@I~//~@@01R~//~va54R~
        }                                                          //~v@@@I~//~@@01I~
        else                                                       //~v@@@I~//~@@01I~
        {                                                          //~v@@@I~//~@@01I~
			DisplayMetrics m=new DisplayMetrics();                 //~v@@@R~//~@@01I~
//  		display.getMetrics(m);                                //~v@@@R~//~@@01R~//~va54R~
    		displayGetMetrics(display,m);                         //~1aj0R~//~va54I~
        	p.x=m.widthPixels;                         //~v@@@R~   //~@@01R~
        	p.y=m.heightPixels;                       //~v@@@R~    //~@@01R~
	        if (Dump.Y) Dump.println("Utils:getScrRealSize Displaymetrics w="+p.x+",h="+p.y);//~v@@@I~//~@@01R~//~va54R~
        }                                                          //~v@@@I~//~@@01I~
        return p;                                                  //~@@01I~
    }                                                              //~v@@@I~//~@@01I~
    //*******************************************************      //~1aj0R~//~va54I~
    @SuppressWarnings("deprecation")                               //~1aj0R~//~va54I~
    private static void displayGetMetrics(Display Pdisplay,DisplayMetrics Pmetrics)//~1aj0R~//~va54I~
    {                                                              //~1aj0R~//~va54I~
	    if (Dump.Y) Dump.println("Utils:displayGetMetrics");       //~1aj0R~//~va54I~//~va56R~
		Pdisplay.getMetrics(Pmetrics);                             //~1aj0R~//~va54I~
    }                                                              //~1aj0R~//~va54I~
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
    //*******************************************************      //~1aj0I~//~va54I~
	public static Display getDefaultDisplay()                      //~1aj0I~//~va54I~
    {                                                              //~1aj0I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDefaultDisplay");       //~1aj0I~//~va54I~//~va56R~
    	Display d;                                                 //~1aj0I~//~va54I~
		if (Build.VERSION.SDK_INT>=30)   //android30(R)            //~1aj0I~//~va54I~
			d=getDefaultDisplay30();                               //~1aj0I~//~va54I~
        else                                                       //~1aj0I~//~va54I~
			d=getDefaultDisplay29();                               //~1aj0I~//~va54I~
        return d;                                                  //~1aj0I~//~va54I~
    }                                                              //~1aj0I~//~va54I~
//***********************************************************************//~vat2I~//~va53I~
	@TargetApi(33)                                                 //~vat2I~//~va53I~
    public static ApplicationInfo getApplicationInfo33(PackageManager Pmgr,Context Pcontext)//~vat2I~//~va53I~
    	throws PackageManager.NameNotFoundException                               //~vat2I~//~va53I~
    {                                                              //~vat2I~//~va53I~
    	if (Dump.Y) Dump.println("Utils.getApplicationInfo33");    //~vat2I~//~va53I~
    	int flagMgr=0;	//TODO  ?                                  //~vat2R~//~va53I~
    	PackageManager.ApplicationInfoFlags flags=PackageManager.ApplicationInfoFlags.of(flagMgr);//~vat2I~//~va53I~
    	ApplicationInfo appInfo = Pmgr.getApplicationInfo(Pcontext.getPackageName(),flags);//~vat2I~//~va53I~
    	if (Dump.Y) Dump.println("Utils.getApplicationInfo33 appinfo="+appInfo);//~vat2I~//~va53I~
        return appInfo;                                            //~vat2I~//~va53I~
    }                                                              //~vat2I~//~va53I~
    //*******************************************************      //~vam6I~//~va54I~
	public static WindowManager getWindowManager()                 //~vam6I~//~va54I~
    {                                                              //~vam6I~//~va54I~
		WindowManager wm=(WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE));//~vam6I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getWindowManager mgr="+wm);//~vam6I~//~va54I~//~va56R~
        return wm;                                                 //~va54I~
    }                                                              //~vam6I~//~va54I~
    //*******************************************************      //~1aj0I~//~va54I~
    @SuppressWarnings("deprecation")                               //~1aj0I~//~va54I~
	public static Display getDefaultDisplay29()                    //~1aj0I~//~va54I~
    {                                                              //~1aj0I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDefaultDisplay29");     //~1aj0I~//~va54I~//~va56R~
//  	Display display=((WindowManager)(AG.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~1aj0I~//~vam6R~//~va54I~
    	Display display=getWindowManager().getDefaultDisplay();    //~vam6I~//~va54I~
        return display;                                            //~1aj0I~//~va54I~
    }                                                              //~1aj0I~//~va54I~
    //*******************************************************      //~1aj0I~//~va54I~
    @TargetApi(Build.VERSION_CODES.R)   //>=30                     //~1aj0I~//~va54I~
	public static Display getDefaultDisplay30()                    //~1aj0I~//~va54I~
    {                                                              //~1aj0I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDefaultDisplay30");     //~1aj0I~//~va54I~//~va56R~
		Display display=AG.context.getDisplay();                   //~1aj0I~//~va54I~
        return display;                                            //~1aj0I~//~va54I~
    }                                                              //~1aj0I~//~va54I~
//**********************************                               //~1A6pI~//~va54I~
    public static void getDisplaySize(Display Pdisplay,Point Ppoint)//~1A6pI~//~va54I~
    {                                                              //~1A6pI~//~va54I~
//      Pdisplay.getSize(Ppoint);                                    //~1A6pI~//~v@@@M~//~1aj0R~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize");          //~1aj0R~//~va54I~//~va56R~
		if (Build.VERSION.SDK_INT>=31)                             //~vam6I~//~1am3I~//~va54I~
			getDisplaySize31(Pdisplay,Ppoint);                     //~vam6I~//~1am3I~//~va54I~
        else                                                       //~vam6I~//~1am3I~//~va54I~
		if (Build.VERSION.SDK_INT>=30)   //android30(R)            //~1aj0R~//~va54I~
			getDisplaySize30(Pdisplay,Ppoint);                     //~1aj0R~//~va54I~
        else                                                       //~1aj0R~//~va54I~
			getDisplaySize29(Pdisplay,Ppoint);                     //~1aj0R~//~va54I~
   }                                                               //~1aj0R~//~va54I~
    //*******************************************************      //~1aj0R~//~va54I~
    @SuppressWarnings("deprecation")                               //~1aj0R~//~va54I~
	public static void getDisplaySize29(Display Pdisplay,Point Ppoint)//~1aj0R~//~va54I~
    {                                                              //~1aj0R~//~va54I~
        Pdisplay.getSize(Ppoint);                                  //~1aj0R~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize29 point="+Ppoint.toString());//~1aj0R~//~va54I~//~va56R~
    }                                                              //~1aj0R~//~va54I~
    //*******************************************************      //~1aj0R~//~va54I~
    @SuppressWarnings("deprecation")                               //~vam6I~//~1am3I~//~va54I~
    @TargetApi(Build.VERSION_CODES.R)   //>=30                     //~1aj0R~//~va54I~
    public static void getDisplaySize30(Display Pdisplay,Point Ppoint)//~1aj0R~//~va54I~
    {                                                              //~1aj0R~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize30");        //~1aj0R~//~1am8I~//~va54I~//~va56R~
        WindowMetrics wm=AG.activity.getWindowManager().getCurrentWindowMetrics();//~1aj0R~//~1am8I~//~va54I~
	    int ww0=wm.getBounds().width();                             //~1aj0R~//~1am8I~//~va54I~
	    int hh0=wm.getBounds().height();                              //~1aj0I~//~1am8I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize30 windowMetrics ww="+ww0+",hh="+hh0);//~1aj0I~//~1am8I~//~va54I~//~va56R~
//        Rect rectDecor=getDecorViewRect();                         //~vaegI~//~1am8I~//~va54R~
//        Insets insetnavi=wm.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.navigationBars());//TODO test//~vaefI~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 insetnavi="+insetnavi.toString());//~vaefI~//~1am8I~//~va54R~//~va56R~
//        Insets insetstatus=wm.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.statusBars());//TODO test//~vaefI~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 insetstatus="+insetstatus.toString());//~vaefI~//~1am8I~//~va54R~//~va56R~
//        Insets insetnaviv=wm.getWindowInsets().getInsets(WindowInsets.Type.navigationBars());//TODO test//~vaefI~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 insetnaviv="+Utils.toString(insetnaviv));//~vaefI~//~1am8I~//~va54R~//~va56R~
//        Insets insetstatusv=wm.getWindowInsets().getInsets(WindowInsets.Type.statusBars());//TODO test//~vaefI~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 insetstatus visible="+Utils.toString(insetstatusv));//~vaefI~//~1am8I~//~va54R~//~va56R~
//        Insets insetsys=wm.getWindowInsets().getInsetsIgnoringVisibility(WindowInsets.Type.systemBars());//TODO test//~1aj0R~//~vaefR~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 insetsys="+insetsys.toString());//~1aj0R~//~vaefR~//~1am8I~//~va54R~//~va56R~
//                                                                   //~vaefI~//~1am8I~//~va54R~
        Insets inset=wm.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());//~vaefR~//~1am8I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize30 inset systemGesture="+Utils.toString(inset));//~vaefR~//~1am8I~//~va54I~//~va56R~
                                                                   //~vaefI~//~1am8I~//~va54I~
        int ww,hh;                                                 //~vaefI~//~1am8I~//~va54I~
//        AG.swNavigationbarGestureMode=inset.left!=0 && inset.right !=0 && inset.top!=0 && inset.bottom!=0;//~vaefM~//~1am8I~//~va54R~
        ww=ww0-inset.left-inset.right;                             //~vaefI~//~1am8I~//~va54I~
        hh=hh0-inset.bottom;  //fullscreen(no title) mode,bottom is 3button/gesture navigationbar//~vaefR~//~1am8I~//~va54I~
        if (ww0>hh0)	//landscape                                //~vaefI~//~1am8I~//~va54I~
            ww=ww0; //fill hidden navigationbar, but right buttons has to be shift to left//~vaegR~//~1am8I~//~va54R~
        else                                                       //~vaefI~//~1am8I~//~va54I~
	        ww=ww0;                                                //~vaefI~//~1am8I~//~va54I~
//        AG.scrNavigationbarBottomHeightA11=inset.bottom;           //~vaefI~//~1am8I~//~va54R~
//        int marginLR;                                              //~vaegI~//~1am8I~//~va54R~
//        if (AG.swNavigationbarGestureMode)                         //~vaegI~//~1am8I~//~va54R~
//        {                                                          //~vaegI~//~1am8I~//~va54R~
//            int left=rectDecor.left;                               //~vaegI~//~1am8I~//~va54R~
//            int right=ww0-rectDecor.right;                         //~vaegI~//~1am8I~//~va54R~
//            marginLR=Math.max(left,right);                         //~vaegI~//~1am8I~//~va54R~
//            if (Dump.Y) Dump.println("Utils:getDisplaySize30 gesture mode marginLR="+marginLR);//~vaegI~//~1am8I~//~va54R~//~va56R~
//        }                                                          //~vaegI~//~1am8I~//~va54R~
//        else  //3button mode                                       //~vaegI~//~1am8I~//~va54R~
//        {                                                          //~vaegI~//~1am8I~//~va54R~
//            marginLR=ww0-(rectDecor.right-rectDecor.left);         //~vaegI~//~1am8I~//~va54R~
//            if (Dump.Y) Dump.println("Utils:getDisplaySize30 3 button mode marginLR="+marginLR);//~vaegI~//~vataR~//~1am8I~//~va54R~//~va56R~
//        }                                                          //~vaegI~//~vataR~//~1am8I~//~va54R~
//        AG.scrNavigationbarRightWidthA11=marginLR;              //~vaefR~//~vaegI~//~1am8I~//~va54R~
        Ppoint.x=ww; Ppoint.y=hh;                                  //~1aj0R~//~1am8I~//~va54I~
//        AG.scrStatusBarHeight=inset.top;                           //~1aj0I~//~1am8I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 navigationbar bottomHA11="+AG.scrNavigationbarBottomHeightA11+",leftWA11="+AG.scrNavigationbarLeftWidthA11+",rightWA11="+AG.scrNavigationbarRightWidthA11+",swgesturemode="+AG.swNavigationbarGestureMode);//~vaefR~//~vaegR~//~1am8I~//~va54R~//~va56R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize30 point="+Ppoint.toString()+",statusBarHeight="+AG.scrStatusBarHeight);//~vaefI~//~1am8I~//~va54R~//~va56R~
        if (Dump.Y) Dump.println("Utils:getDisplaySize30 point="+Ppoint.toString());//~va54I~//~va56R~
    }                                                              //~1A6pI~//~va54I~
    //*******************************************************      //~vam6M~//~1am3I~//~va54I~
    @TargetApi(31)   //>=31                                        //~vam6M~//~1am3I~//~va54I~
    public static void getDisplaySize31(Display Pdisplay,Point Ppoint)//~vam6M~//~1am3I~//~va54I~
    {                                                              //~vam6M~//~1am3I~//~va54I~
        WindowMetrics metrics=getRealMetrics_from31(Pdisplay);     //~vam6M~//~1am3I~//~va54I~
        Insets insetGesture=metrics.getWindowInsets().getInsets(WindowInsets.Type.systemGestures());//~vam6I~//~1am3I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize31 insetGesture="+insetGesture);//~vam6I~//~1am3I~//~va54I~//~va56R~
//        AG.swNavigationbarGestureMode=insetGesture.left!=0 && insetGesture.right !=0 && insetGesture.top!=0 && insetGesture.bottom!=0;//~vam6I~//~1am3I~//~va54R~
                                                                   //~vam6I~//~1am3I~//~va54I~
		Rect bounds=metrics.getBounds();                           //~vam6M~//~1am3I~//~va54I~
	    int ww0=bounds.width();                                    //~vam6M~//~1am3I~//~va54I~
	    int hh0=bounds.height();                                   //~vam6M~//~1am3I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize31 bounds="+bounds);//~vam6I~//~1am3I~//~va54I~//~va56R~
        WindowInsets windowInsets=metrics.getWindowInsets();       //~vam6M~//~1am3I~//~va54I~
        Insets inset=windowInsets.getInsetsIgnoringVisibility      //~vam6M~//~1am3I~//~va54I~
						(WindowInsets.Type.navigationBars()|WindowInsets.Type.displayCutout());//~vam6M~//~1am3I~//~va54I~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize31 inset="+inset);//~vataI~//~1am3I~//~va54I~//~va56R~
        int insetWW=inset.right+inset.left;                        //~vam6M~//~1am3I~//~va54I~
        int insetHH=inset.top+inset.bottom;                        //~vam6M~//~1am3I~//~va54I~
//        Rect rectDecor=getDecorViewRect();                         //~vam6M~//~vataM~//~1am3I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize31 rectRecor="+rectDecor.toString());//~vataI~//~1am3I~//~va54R~//~va56R~
	    if (Dump.Y) Dump.println("Utils:getDisplaySize31 insetWW="+insetWW+",insetHH="+insetHH+",insets="+inset);//~vam6M~//~1am3I~//~va54I~//~va56R~
                                                                   //~vam6M~//~1am3I~//~va54I~
        int ww=ww0-insetWW;                                        //~vam6I~//~1am3I~//~va54I~
        int hh=hh0-insetHH;                                        //~vam6I~//~1am3I~//~va54I~
//      if (ww0>hh0)	//landscape                                //~vam6I~//~1am3I~//~va54I~//~va56R~
//          ww=ww0; //fill hidden navigationbar, but right buttons has to be shift to left//~vam6I~//~1am3I~//~va54I~//~va56R~
//      else                                                       //~vam6I~//~1am3I~//~va54I~//~va56R~
//          ww=ww0;                                                //~vam6I~//~1am3I~//~va54I~//~va56R~
//        AG.scrNavigationbarBottomHeightA11=inset.bottom;           //~vam6M~//~1am3I~//~va54R~
//        int marginLR;                                              //~vam6M~//~1am3I~//~va54R~
//        int left=inset.left;                                       //~vateI~//~1am3I~//~va54R~
//        int right=inset.right;                                     //~vateI~//~1am3I~//~va54R~
//        marginLR=Math.max(left,right);                             //~vateI~//~1am3I~//~va54R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize33 swPortrait="+AG.portrait+",marginLR="+marginLR+",left="+left+",right="+right);//~vateI~//~1am3I~//~va54R~//~va56R~
//        AG.scrNavigationbarRightWidthA11=marginLR;                 //~vam6M~//~1am3I~//~va54R~
//        AG.scrStatusBarHeight=inset.top;                           //~vam6M~//~1am3I~//~va54R~
                                                                   //~vam6M~//~1am3I~//~va54I~
        Ppoint.x=ww;                                               //~vam6R~//~1am3I~//~va54I~
        Ppoint.y=hh;                                               //~vam6R~//~1am3I~//~va54I~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize31 navigationbar bottomHA11="+AG.scrNavigationbarBottomHeightA11+",leftWA11="+AG.scrNavigationbarLeftWidthA11+",rightWA11="+AG.scrNavigationbarRightWidthA11+",swgesturemode="+AG.swNavigationbarGestureMode);//~vam6M~//~1am3I~//~va54R~//~va56R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize31 point="+Ppoint.toString()+",statusBarHeight="+AG.scrStatusBarHeight);//~vam6M~//~1am3I~//~va54R~//~va56R~
//        if (Dump.Y) Dump.println("Utils:getDisplaySize31 point="+Ppoint.toString()+",bounds="+bounds+",insets="+inset);//~vam6M~//~1am3I~//~va54R~//~va56R~
        if (Dump.Y) Dump.println("Utils:getDisplaySize31 point="+Ppoint.toString());//~va54I~//~va56R~
    }                                                              //~vam6M~//~1am3I~//~va54I~
    //*******************************************************************//~vam6I~//~1am3I~//~va54I~
    @TargetApi(31)                                                 //~vam6I~//~1am3I~//~va54I~
    public static WindowMetrics getRealMetrics_from31(Display Pdisplay)//~vam6I~//~1am3I~//~va54I~
    {                                                              //~vam6I~//~1am3I~//~va54I~
		WindowManager mgr=getWindowManager();                      //~vam6I~//~1am3I~//~va54I~
        WindowMetrics metrics=mgr.getCurrentWindowMetrics();       //~vam6I~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealMetrics_31 metrics="+metrics);//~vam6R~//~1am3I~//~va54I~//~va56R~
        return metrics;                                            //~vam6I~//~1am3I~//~va54I~
    }                                                              //~vam6I~//~1am3I~//~va54I~
    //*******************************************************************//~vam6I~//~1am3I~//~va54I~
    //*for APi>=19                                                 //~vam6I~//~1am3I~//~va54I~
    //*******************************************************************//~vam6I~//~1am3I~//~va54I~
    private static void getRealSize(Display Pdisplay,Point Ppoint) //~vam6I~//~1am3I~//~va54I~
    {                                                              //~vam6I~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize apiLevel="+Build.VERSION.SDK_INT);//~vam6I~//~1am3I~//~va54I~//~va56R~
		if (Build.VERSION.SDK_INT>=31)   //Navigationbar can be hidden//~vam6I~//~1am3I~//~va54I~
		    getRealSize_from31(Pdisplay,Ppoint);                   //~vam6I~//~1am3I~//~va54I~
        else                                                       //~vam6I~//~1am3I~//~va54I~
		    getRealSize_19To30(Pdisplay,Ppoint);                   //~vam6I~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize exit point="+Ppoint);//~vam6I~//~1am3I~//~va54I~//~va56R~
    }                                                              //~vam6I~//~1am3I~//~va54I~
    //*******************************************************************//~vam6I~//~1am3I~//~va54I~
    @SuppressWarnings("deprecation")                               //~vam6R~//~1am3I~//~va54I~
    @TargetApi(19)                                                 //~vam6I~//~1am3I~//~va54I~
    private static void getRealSize_19To30(Display Pdisplay,Point Ppoint)//~vam6I~//~1am3I~//~va54I~
    {                                                              //~vam6I~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize_upto30 display="+Pdisplay);//~vam6I~//~1am3I~//~va54I~//~va56R~
		Pdisplay.getRealSize(Ppoint);                              //~vam6I~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize_upto30 exit point="+Ppoint);//~vam6I~//~1am3I~//~va54I~//~va56R~
    }                                                              //~vam6I~//~1am3I~//~va54I~
    //*******************************************************************//~vam6I~//~1am3I~//~va54I~
    @TargetApi(31)                                                 //~vam6I~//~1am3I~//~va54I~
    private static void getRealSize_from31(Display Pdisplay,Point Ppoint)//~vam6I~//~1am3I~//~va54I~
    {                                                              //~vam6I~//~1am3I~//~va54I~
        WindowMetrics metrics=getRealMetrics_from31(Pdisplay);     //~vam6R~//~1am3I~//~va54I~
        Rect rect=metrics.getBounds();                             //~vam6R~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize_from31 display="+Pdisplay+",metrics="+metrics+",getBounds="+rect);//~vam6R~//~1am3I~//~va54I~//~va56R~
        Ppoint.x=rect.right-rect.left;                              //~vam6R~//~1am3I~//~va54I~
        Ppoint.y=rect.bottom-rect.top;                              //~vam6R~//~1am3I~//~va54I~
		if (Dump.Y) Dump.println("Utils.getRealSize_from31 exit point="+Ppoint);//~vam6I~//~1am3I~//~va54I~//~va56R~
    }                                                              //~vam6I~//~1am3I~//~va54I~
//**********************************                               //~va56I~
//*from Alert,replyed Yes                                          //~va56I~
//**********************************                               //~va56I~
	public static void stopFinish()		//from Alert by Stop:Yes   //~va56I~
    {                                                              //~va56I~
    	if (Dump.Y) Dump.println("Utils stopFinish");              //~va56I~
       	try                                                        //~va56I~
        {                                                          //~va56I~
        	AG.aMainActivity.finish();                       //~va56I~
        }                                                          //~va56I~
        catch (Exception e)                                        //~va56I~
        {                                                          //~va56I~
        	Dump.println(e,"stopFinish");                          //~va56I~
            AG.aMainActivity.finish();                                              //~va56I~
        }                                                          //~va56I~
    }                                                              //~va56I~
    //*************************************************            //~@@01I~//~va80I~
    public static int parseInt(String Pstr,int Pdefault)           //~@@01R~//~va80I~
    {                                                              //~@@01I~//~va80I~
    	int ii;                                                    //~@@01I~//~va80I~
        try                                                        //~@@01I~//~va80I~
        {                                                          //~@@01I~//~va80I~
    		ii=Integer.parseInt(Pstr);                             //~@@01I~//~va80I~
        }                                                          //~@@01I~//~va80I~
        catch(Exception e)                                         //~@@01I~//~va80I~
        {                                                          //~@@01I~//~va80I~
//      	Dump.println(e,"parseInt str="+Pstr);                  //~@@01R~//~va80I~
        	if (Dump.Y) Dump.println("Utils.parseInt str="+Pstr+",e="+e.toString());//~@@01R~//~va80I~
        	ii=Pdefault;                                           //~@@01R~//~va80I~
        }                                                          //~@@01I~//~va80I~
        return ii;                                                 //~@@01I~//~va80I~
    }                                                              //~@@01I~//~va80I~
    //*************************************************            //~vak2I~//~1ak2I~//~va80I~
    public static long parseLong(String Pstr,long Pdefault)        //~vak2I~//~1ak2I~//~va80I~
    {                                                              //~vak2I~//~1ak2I~//~va80I~
    	long ii;                                                   //~vak2I~//~1ak2I~//~va80I~
        try                                                        //~vak2I~//~1ak2I~//~va80I~
        {                                                          //~vak2I~//~1ak2I~//~va80I~
    		ii=Long.parseLong(Pstr);                               //~vak2I~//~1ak2I~//~va80I~
        }                                                          //~vak2I~//~1ak2I~//~va80I~
        catch(Exception e)                                         //~vak2I~//~1ak2I~//~va80I~
        {                                                          //~vak2I~//~1ak2I~//~va80I~
        	if (Dump.Y) Dump.println("Utils.parseLong str="+Pstr+",e="+e.toString());//~vak2I~//~1ak2I~//~va80I~
        	ii=Pdefault;                                           //~vak2I~//~1ak2I~//~va80I~
        }                                                          //~vak2I~//~1ak2I~//~va80I~
        return ii;                                                 //~vak2I~//~1ak2I~//~va80I~
    }                                                              //~vak2I~//~1ak2I~//~va80I~
//**********************************************************       //~va80I~
    public static void showToast(String Ptext)                     //~va80I~
    {                                                              //~va80I~
		showToastShort(Ptext);                                     //~va80I~
    }                                                              //~va80I~
//**********************************************************       //~v@@@M~//~va80I~
    public static void showToast(int Presid)                       //~v@@@M~//~va80I~
    {                                                              //~v@@@M~//~va80I~
		showToastShort(Presid);                                 //~v@@@R~//~va80I~
    }                                                              //~v@@@M~//~va80I~
//**********************************************************       //~va80I~
    public static void showToast(int Presid,String Ptext)          //~va80I~
    {                                                              //~va80I~
		showToastShort(Presid,Ptext);                              //~va80I~
    }                                                              //~va80I~
//**********************************************************       //~v@@@I~//~va80I~
    public static void showToastShort(int Presid)                  //~v@@@I~//~va80I~
    {                                                              //~v@@@I~//~va80I~
		showToastShort(Presid,"");                                 //~v@@@R~//~va80R~
    }                                                              //~v@@@I~//~va80I~
//**********************************************************       //~v@@@M~//~va80M~
    public static void showToastShort(int Presid,String Ptext)          //~v@@@M~//~va80I~
    {                                                              //~v@@@M~//~va80M~
        String msg=Utils.getStr(Presid)+Ptext;                     //~v@@@M~//~va80M~
    	if (Dump.Y) Dump.println("showToast msg="+msg);            //~v@@@M~//~va80M~
    	showToastShort(msg);                                       //~va80I~
    }                                                              //~v@@@M~//~va80M~
//**********************************************************       //~v@@@I~//~va80M~
    public static void showToastShort(String Ptext)                //~v@@@I~//~va80M~
    {                                                              //~v@@@I~//~va80M~
    	if (Dump.Y) Dump.println("showToast msg="+Ptext);          //~v@@@I~//~va80M~
		Toast.makeText(AG.context,Ptext,Toast.LENGTH_SHORT).show();//~1514R~//~v@@@I~//~va80I~
    }                                                              //~v@@@I~//~va80M~
//**********************************************************       //~v@@@M~//~va80M~
    public static void showToastLong(int Presid)                   //~v@@@M~//~va80M~
    {                                                              //~v@@@M~//~va80M~
		showToastLong(Presid,"");                                  //~v@@@M~//~va80M~
    }                                                              //~v@@@M~//~va80M~
//**********************************************************       //~v@@@M~//~va80M~
    public static void showToastLong(int Presid,String Ptext)      //~v@@@M~//~va80M~
    {                                                              //~v@@@M~//~va80M~
        String msg=Utils.getStr(Presid)+Ptext;                     //~v@@@M~//~va80M~
    	if (Dump.Y) Dump.println("showToastLong msg="+msg);        //~v@@@M~//~va80M~
    	showToastLong(msg);                                        //~va80M~
    }                                                              //~v@@@M~//~va80M~
//**********************************************************       //~v@@@M~//~va80I~
    public static void showToastLong(String Ptext)                 //~v@@@M~//~va80I~
    {                                                              //~v@@@M~//~va80I~
    	if (Dump.Y) Dump.println("showToastLong msg="+Ptext);      //~v@@@M~//~va80I~
		Toast.makeText(AG.context,Ptext,Toast.LENGTH_LONG).show();//~1514I~//~v@@@I~//~va80I~
    }                                                              //~v@@@M~//~va80I~
//****************                                                 //~1416I~//~1Ad7R~//~v@@@I~//+va80M~
    public static View findViewById(View Playout,int Pid)          //~1416I~//~1Ad7R~//~v@@@I~//+va80I~
    {                                                              //~1416I~//~1Ad7R~//~v@@@I~//+va80M~
        View v=Playout.findViewById(Pid);                          //~1416I~//~1Ad7R~//~v@@@I~//~9416R~//+va80M~
        if (Dump.Y) Dump.println("findViewById rc==null?="+(v==null?"true":"false")+",id="+Integer.toHexString(Pid));//~9416I~//+va80I~
        return v;                                                  //~9416I~//+va80M~
    }                                                              //~1416I~//~1Ad7R~//~v@@@I~//+va80M~
}//class Utils                                                //~1309R~//~v@@@R~
