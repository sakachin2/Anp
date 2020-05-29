//CID://+va43R~:          update#=     27                          //~va43R~
//***************************************************************  //~va40I~
//va42:200524 google play accept over apilevel:26(android-8.0); optionmenu was deprecated(onCreateOptionmenu is not called)//~va43I~
//            from api 11(android-3.0) supports actionbar support option menu//~va43I~
//***************************************************************  //~va40I~
package np.jnp.npanew;                                                //~2718R~//~0523R~//~va43R~

import np.jnp.npanew.BuildConfig;
import np.jnp.npanew.R;                                               //~0523I~//+va43R~
import np.jnp.npanew.utils.Dump;                                   //~va43R~
import np.jnp.npanew.utils.AG;                                        //~0523I~//~va43R~

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.Date;


public class jnp extends Activity {                                //~0914R~
	private NppView nppView;
	private NppMenu nppMenu;//~0914R~
//    private static final int featureIDs=0                          //~va40I~//~va43R~
//                                    |  Window.FEATURE_LEFT_ICON    //~va40R~//~va43R~
////                                  |   Window.FEATURE_NO_TITLE    //~va40R~//~va43R~
////                                  |   Window.FEATURE_CONTEXT_MENU//~va40I~//~va43R~
////                                  |   Window.FEATURE_ACTION_BAR  //~va40I~//~va43R~
////                                  |   Window.FEATURE_ACTION_BAR_OVERLAY//~va40I~//~va43R~
//                                        ;                          //~va40I~//~va43R~
                                     //~0915I~
//**                                                               //~0915I~
    @Override                                                     //~0915I~
    public void onCreate(Bundle icicle)                            //~0914R~
	{                                                              //~0914I~
//    	System.out.println("jnp onCretae");                        //~0C06I~//~0C12R~
    	super.onCreate(icicle);	                                    //~0914R~
//  	requestWindowFeature(Window.FEATURE_LEFT_ICON);             //~0914R~//~0915R~//~0A09R~//~va40R~
//  	requestWindowFeature(featureIDs);                          //~va40I~//~va43R~
        Dump.openExOnlyTerminal();	//write exception only to Terminal//~va40I~
        new AG().init(this);  //Dump=Y if debuggable                //~0523I~//~va40R~
        nppView=new NppView(this);
        nppMenu=new NppMenu();//~0914I~
        setContentView(nppView);                                   //~0914R~
        addTitle();                                                //~va43I~
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);//setup menu               //~0915I~//~0A05R~
//  	this.getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,R.drawable.wnp);//~0A09I~//~va43R~
    }                                                              //~0914I~
//**                                                               //~0523I~
    @Override                                                      //~0C06I~
    protected void onDestroy()                                     //~0C06I~
	{                                                              //~0C06I~
//    	System.out.println("jnp onDestroy");                       //~0C06I~//~0C12R~
    	super.onDestroy();                                         //~0C06I~
        AG.aBGMList.stopAll();                                     //~va43I~
    }                                                              //~0C06I~
//**                                                               //~0915I~
    @Override                                                     //~0915I~
    public boolean onCreateOptionsMenu(Menu menu)                           //~0915I~
	{  
    	super.onCreateOptionsMenu(menu);
    	nppMenu.init(menu,nppView);		//setup menu		               //~0915R~//~0A05R~
    	return true;
	}                                                              //~0915I~
//**                                                               //~0915I~
    @Override                                                     //~0915I~
    public boolean onOptionsItemSelected(MenuItem item)            //~0915I~
	{                                                              //~0915I~
    	nppMenu.selected(item);		//setup menu                   //~0915I~//~0A19R~
    	return true;
	}                                                              //~0915I~
//**                                                               //~0C06I~
    @Override                                                      //~0C06I~
    public void onConfigurationChanged(Configuration Pcfg)         //~0C06I~
	{                                                              //~0C06I~
    	int orientation;                                           //~0C07I~
    	super.onConfigurationChanged(Pcfg);                        //~0C06I~//~0C07M~
//    	System.out.println("jnp configuration changed");           //~0C06I~//~0C12R~
        boolean orichanged=(Pcfg.orientation==Configuration.ORIENTATION_PORTRAIT)!=(Wnp.Sswportrate==true);//~0C06I~
        if (orichanged)                                            //~0C07I~
        	orientation=Pcfg.orientation;                          //~0C07I~
        else                                                       //~0C07I~
        	orientation=0;                                         //~0C07I~
    	nppView.orientationChanged(orientation);                    //~0C06R~//~0C07R~
        AG.setScreenSize();                                        //~va40I~
	}                                                              //~0C06I~
//**************************************                         //~0A21R~//~va43R~
    private void addTitle()                                             //~va43R~
    {                                                            //~0A21R~//~va43R~
    	String title=addTimeStamp();                               //~va43I~
    	setTitle(title);                                           //~va43I~
    }                                                              //~va43I~
    //*************************                                    //~va43I~
    private String addTimeStamp()                                  //~va43I~
    {                                                              //~va43I~
        Date ts=new Date(BuildConfig.TIMESTAMP);                   //~va43I~
//      SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd-HH.mm.ss");//~va43R~
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");   //~va43I~
        String sts=sdf.format(ts);                                 //~va43I~
        String rc=AG.resource.getString(R.string.AppTitle,         //~va43I~
        								AG.resource.getString(R.string.app_name),//~va43I~
        								AG.resource.getString(R.string.AppVersion),//~va43I~
                                        sts);                      //~va43I~
		if (Dump.Y) Dump.println("MainView.appendTimestampMade date="+sts+",title="+rc);//~va43I~
		return rc;                                                 //~va43I~
    }                                                              //~va43I~
}