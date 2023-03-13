//CID://+va67R~:          update#=     60                          //+va67R~
//***************************************************************  //~va40I~
//va67 230303 add Stop memu item                                   //+va67I~
//va57 221103 onBackPressed isdeprecated at android13              //~va57I~
//va56 221103 Finish by Back button                                //~va56I~
//va42:200524 google play accept over apilevel:26(android-8.0); optionmenu was deprecated(onCreateOptionmenu is not called)//~va43I~
//            from api 11(android-3.0) supports actionbar support option menu//~va43I~
//***************************************************************  //~va40I~
package np.jnp.npanew;                                                //~2718R~//~0523R~//~va43R~

import static android.window.OnBackInvokedDispatcher.PRIORITY_DEFAULT;

import np.jnp.npanew.BuildConfig;
import np.jnp.npanew.R;                                               //~0523I~//~va43R~
import np.jnp.npanew.utils.Alert;
import np.jnp.npanew.utils.Dump;                                   //~va43R~
import np.jnp.npanew.utils.AG;                                        //~0523I~//~va43R~
import np.jnp.npanew.utils.Utils;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.window.OnBackInvokedCallback;

//import androidx.appcompat.app.AppCompatActivity;                 //~va57R~
import android.app.Activity;                                       //~va57M~
import androidx.activity.OnBackPressedCallback;
import android.view.Window.Callback;                               //~va56I~

import java.text.SimpleDateFormat;
import java.util.Date;


public class jnp extends Activity                              //~0914R~//~va57R~
//public class jnp extends AppCompatActivity                       //~va57R~
//  implements android.view.View.OnKeyListener                     //~va56R~
                      implements Callback//~1122I~                 //~@@@@R~//~va56I~
{                                                                  //~va57I~
	public static final int KEYCODE_BACK   =android.view.KeyEvent.KEYCODE_BACK;   //back//~v@@@R~//~1212I~//~va56I~
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
		if (Dump.Y) Dump.println("jnp.onCreate osversion="+AG.osVersion);//~va56I~
//        if (AG.osVersion>=33)                                      //~va57I~//~va56R~
//            setBackPressedListener();                              //~va57I~//~va56R~
//        else                                                     //~va56R~
//            addKeyListener();                                    //~va56R~
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
		if (Dump.Y) Dump.println("jnp.appendTimestampMade date="+sts+",title="+rc);//~va43I~//~va56R~
		return rc;                                                 //~va43I~
    }                                                              //~va43I~
////*************************                                        //~8C03I~//~va56I~//~va57R~//~va56R~
//    @Override                                                      //~8C03I~//~va56I~//~va57R~//~va56R~
//    @SuppressWarnings("deprecation")                             //~va57R~//~va56R~
//    public void onBackPressed()                                  //~va57R~//~va56R~
//    {                                                              //~8C03I~//~va56I~//~va57R~//~va56R~
//        if (Dump.Y) Dump.println("jnp.onBackPressed");             //~va56I~//~va57R~//~va56R~
//        onExit();                                                  //~8C03I~//~va56I~//~va57R~//~va56R~
//    }                                                              //~8C03I~//~va56I~//~va57R~//~va56R~
//******************                                               //~0914I~//~1212I~//~va56I~
//    @Override                                                      //~1116I~//~va56R~
//    public boolean onKey(View Pview, int Pkeycode, android.view.KeyEvent Pevent)//~v@@@I~//~1116I~//~va56R~
//    {                                                            //~va56R~
//        boolean rc=false;                                        //~va56R~
//        try                                                        //~1430I~//~va56R~
//        {                                                          //~1430I~//~va56R~
//            if (Dump.Y) Dump.println("jnp.onkey keycode="+Pkeycode+",viewid="+Integer.toString(Pview.getId(),16)+",isfocus="+Pview.isFocused()); //~v@@@I~//~1116I~//~1506R~//~2B04R~//~va56R~
//            int action=Pevent.getAction();                          //~v@@@R~//~1430R~//~va56R~
//            switch(action)                                     //~v@@@I~//~1430R~//~va56R~
//            {                                                  //~v@@@I~//~1430R~//~va56R~
//            case android.view.KeyEvent.ACTION_UP:                           //~v@@@I~//~1430R~//~va56R~
//                rc=KeyUp(Pkeycode,Pevent);                 //~1430R~//~va56R~
//                break;                                         //~v@@@I~//~1430R~//~va56R~
//            default:                                           //~v@@@I~//~1430R~//~va56R~
//            }                                                  //~v@@@I~//~1430R~//~va56R~
//        }                                                          //~1430I~//~va56R~
//        catch(Exception e)                                         //~1430I~//~va56R~
//        {                                                          //~1430I~//~va56R~
//            Dump.println(e,"jnp.onKey exception");            //~1430I~//~va56R~
//        }                                                          //~1430I~//~va56R~
//        return rc;           //~v@@@I~                             //~1317R~//~va56R~
//    }                                                            //~va56R~
    public boolean KeyUp(int PkeyCode, KeyEvent event)             //~1212I~//~va56R~
	{                                                              //~1212I~//~va56I~
        if (Dump.Y) Dump.println("jnp.KeyUp keycode="+PkeyCode);   //~va56R~
        boolean rc=false;                                          //~va56I~
	    if (PkeyCode==KEYCODE_BACK) //back key                     //~va56I~
        {                                                          //~va56I~
        	onExit();                                              //~va56I~
            rc=true;                                               //~va56I~
        }                                                          //~va56I~
        return rc;                                                 //~va56R~
    }                                                              //~0914I~//~1212I~//~va56I~
//**********************************************************       //~va56I~
//  Callback                                                       //~va56I~
//**********************************************************       //~va56I~
    @Override                                                      //~0914I~//~1212I~//~va56I~
    public boolean onKeyUp(int keyCode,KeyEvent event)             //~1212I~//~va56I~
	{                                                              //~1212I~//~va56I~
        if (Dump.Y) Dump.println("jnp.onKeyUp keycode="+keyCode); //~va56I~
    	return KeyUp(keyCode,event);                               //~va56I~
    }                                                              //~va56I~
//**********************************************************       //~8C03I~//~@@@@M~//~va56I~
//  private void onExit()                                          //~8C03I~//~@@@@M~//~va56I~//+va67R~
    public  void onExit()                                          //+va67I~
    {                                                              //~8C03I~//~@@@@M~//~va56I~
        if (Dump.Y) Dump.println("jnp.onExit");           //~9B06I~//~va56I~
        Dump.close();                                              //~va56I~
    	int flag= Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE|Alert.EXIT;//~8C03I~//~@@@@M~//~va56I~
//      Alert.showAlert(null/*title*/, Utils.getStr(R.string.Qexit),flag,null/*callback*/);//~8C03I~//~@@@@M~//~va56R~
		Alert.simpleAlertDialog(null/*callback*/,null/*title*/,R.string.Qexit,flag);//~1314I~//~@@@@R~//~va56R~
    }                                                              //~8C03I~//~@@@@M~//~va56I~
////**********************************                             //~va57R~
////*for androidx.activity                                         //~va57R~
////**********************************                             //~va57R~
//    public void setBackPressedListener()                         //~va57R~
//    {                                                            //~va57R~
//        if (Dump.Y) Dump.println("Utils.setBackPressedListener");//~va57R~
//        OnBackPressedCallback cb=new OnBackPressedCallback(true) //~va57R~
//            {                                                    //~va57R~
//                @Override                                        //~va57R~
//                public void handleOnBackPressed()                //~va57R~
//                {                                                //~va57R~
//                    if (Dump.Y) Dump.println("jnp.handleOnBackPressed");//~va57R~
//                    onExit();                                    //~va57R~
//                }                                                //~va57R~
//            };                                                   //~va57R~
//        AG.activity.getOnBackPressedDispatcher().addCallback(AG.activity,cb);//~va57R~
//    }                                                              //~va56I~//~va57R~
////**********************************                               //~va57I~//~va56R~
////*for app.activity                                                //~va57I~//~va56R~
////**********************************                               //~va57I~//~va56R~
//    @TargetApi(33)                                                 //~va57I~//~va56R~
//    public void setBackPressedListener()                           //~va57I~//~va56R~
//    {                                                              //~va57I~//~va56R~
//        if (Dump.Y) Dump.println("Utils.setBackPressedListener");  //~va57I~//~va56R~
//        OnBackInvokedCallback cb=new OnBackInvokedCallback()       //~va57I~//~va56R~
//            {                                                      //~va57I~//~va56R~
//                @Override                                          //~va57I~//~va56R~
//                public void onBackInvoked()                        //~va57I~//~va56R~
//                {                                                  //~va57I~//~va56R~
//                    if (Dump.Y) Dump.println("jnp.setBackPressedListener.onBackInvoked");//~va57I~//~va56R~
//                    onExit();                                      //~va57I~//~va56R~
//                }                                                  //~va57I~//~va56R~
//            };                                                    //~va57I~//~va56R~
//        AG.activity.getOnBackInvokedDispatcher().registerOnBackInvokedCallback(PRIORITY_DEFAULT,cb);//~va57I~//~va56R~
//    }                                                              //~va57I~//~va56R~
////**********************************                             //~va56R~
//    private void addKeyListener()                                //~va56R~
//    {                                                            //~va56R~
//        if (Dump.Y) Dump.println("jnp.addKeyListener");          //~va56R~
//        nppView.setOnKeyListener(this);                              //~1116I~//~va56R~
//    }                                                            //~va56R~
}