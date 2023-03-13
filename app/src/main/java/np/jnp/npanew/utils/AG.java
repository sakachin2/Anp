//*CID://+va56R~:                             update#=  452;       //~va56R~
//******************************************************************************************************************//~v101R~
//va56 221103 Finish by Back button                                //~va56I~
//va54 221103 Dump to cache/Dump.txt                               //~va54I~
//va53 221103 BTMJ-vat2 deprecated api33; PackageManager.getApplicationInfo//~vat2I~//~va53I~
//@@01 20181105 for BTMJ3                                          //~@@01I~
//******************************************************************************************************************//~v101I~
//*Globals *****                                             //~1107I~//~1Ad7R~
//********************                                             //~1107I~
package np.jnp.npanew.utils;                                          //~1Ad8R~//~@@01R~
import np.jnp.npanew.R;                                               //~@@01R~
import android.app.Activity;
import android.content.res.Resources;                              //~@@01I~
import android.content.Context;                                    //~@@01I~
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
//import androidx.fragment.app.FragmentManager;                    //~va56R~
//import android.app.FragmentManager;                              //+va56R~

import np.jnp.npanew.WnpView;                                      //~@@01R~
import np.jnp.npanew.jnp;                                          //~@@01R~

//**************************                                       //~1120I~
public class AG                                                    //~1107R~
{                                                                  //~1109R~
    public static Activity activity;                                 //~1109I~//~1111R~//~1Ad7R~//~v@@@R~//~@@01R~
    public static Context context;                                 //~1111I~//~1Ad7R~//~@@01R~
    public static Resources  resource;                             //~@@01I~
    public static jnp aMainActivity;                               //~@@01I~
    public static boolean isDebuggable;                            //~@@01I~
    public static BGMList aBGMList;                                //~@@01I~
    public static String    appName,appNameE;                      //~@@01R~
    public static int scrWidth,scrHeight;                          //~@@01I~
    public static int scrWidthReal,scrHeightReal;                  //~@@01I~
    public static boolean portrait;                                //~@@01I~
    public static int actionBarHeight;                             //~@@01I~
    public static int titleBarHeight;                              //~@@01I~
    public static int osVersion;                                   //~vab0I~//~v101I~//~1Ad7R~//~v@@@R~//~@@01M~//~va53I~
//  public static FragmentManager fragmentManager;                 //+va56R~
////************************************                             //~@@@@I~//~1Ad7R~
    public AG()                                                    //~@@01I~
    {                                                              //~@@01I~
    }                                                              //~@@01I~
    public void init(jnp Pmain)                        //~1402I~//~v107R~//~@@@@R~//~1Ad7R~//~@@01R~
    {                                                              //~1402I~//~1Ad7R~
//******************************************************           //~@@@@I~//~1Ad7R~//~@@01R~
        osVersion=Build.VERSION.SDK_INT;                //~vab0I~  //~v101I~//~1Ad7R~//~v@@@R~//~va53I~
        aMainActivity=Pmain;                                            //~1109I~//~1329R~//~1402I~//~@@@@R~//~1Ad7R~
        activity=(Activity)Pmain;                                //~1402I~//~@@@@R~//~1Ad7R~//~v@@@R~
        context=(Context)Pmain;                                  //~1402I~//~@@@@R~//~1Ad7R~
        resource=Pmain.getResources();                                //~1109I~//~1329R~//~1402I~//~@@@@R~//~1Ad7R~//~@@01M~
        WnpView.contextR=resource;                                 //~@@01I~
        appName=Utils.getStr(R.string.app_name);                   //~@@01M~
        appNameE=Utils.getStr(R.string.app_nameE);                 //~@@01M~
        isDebuggable=Utils.isDebuggable(context);             //~v107I~//~@@@@R~//~1Ad7R~
        if (isDebuggable)                                          //~@@01R~
//      	Dump.open("");	//write all to Terminal log,not exception only//~@@01R~//~va54R~
            Dump.open("Dump.txt",false/*swSD*/);	//write to internal dir//~1Ak1R~//~va54I~
		setScreenSize();                                           //~@@01M~
        actionBarHeight=Utils.getActionBarHeight();                //~@@01I~
        titleBarHeight=Utils.getTitleBarHeight();                  //~@@01I~
		setScreenSizeReal();                                       //~@@01I~
        aBGMList=new BGMList();                                    //~@@01I~
//      fragmentManager=aMainActivity.getSupportFragmentManager(); //~va56R~
//      fragmentManager=aMainActivity.getFragmentManager();        //+va56R~
    }                                                              //~@@01I~
//******************************************************           //~@@01I~
	public static void setScreenSize()                             //~@@01R~
    {                                                              //~@@01I~
        Point p=Utils.getScrSize();                                //~@@01R~
        scrWidth=p.x;	//by pixel                                 //~@@01I~
        scrHeight=p.y;   //                                        //~@@01I~
        if (Dump.Y) Dump.println("AG.setScreenSize w="+p.x+",h="+p.y);//~@@01R~
        portrait=(AG.scrWidth<AG.scrHeight);                       //~@@01I~
    }                                                              //~@@01I~
//******************************************************           //~@@01I~
	public static void setScreenSizeReal()                         //~@@01I~
    {                                                              //~@@01I~
        Point p=Utils.getScrRealSize();                            //~@@01I~
        scrWidthReal=p.x;	//by pixel                             //~@@01I~
        scrHeightReal=p.y;   //                                    //~@@01I~
        if (Dump.Y) Dump.println("AG.setScreenSizeReal w="+p.x+",h="+p.y);//~@@01I~
    }                                                              //~@@01I~
}//class AG                                                        //~1107R~
