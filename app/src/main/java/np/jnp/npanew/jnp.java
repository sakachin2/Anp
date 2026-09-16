//CID://+vaa9R~:          update#=     90                          //~vaa9R~
//***************************************************************  //~va40I~
//vaa9 2026/09/08 fix initial rotation                             //~vaa9I~
//vaa8 2026/09/08 (Bug)app remains after destry main               //~vaa8I~
//vaa7 2026/09/07 API36:orientation change occurs. realsize was not updated(portrait was changed)//~vaa7I~
//1aqw 2026/09/06 AHSV 2026/08/26 reduce icon size on actionbar(androidx)//~1aqwI~
//1aq9 2026/09/06 AHSV 2026/08/14 waring ;call super; this also activated call onActivityResult of registerForActivityResult//~1aq9I~
//vaa5 2026/09/06 android16 changed on back button process         //~vaa5I~
//va80 240219 selectable BGM                                       //~va80I~
//va67 230303 add Stop memu item                                   //~va67I~
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
import np.jnp.npanew.utils.UPermission;                            //~va80I~
import np.jnp.npanew.utils.UMediaStore;                            //~va80I~

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.window.OnBackInvokedCallback;
import android.content.Intent;                                     //~va80I~
import android.graphics.Bitmap;                                    //~1aqwI~
import android.graphics.BitmapFactory;                             //~1aqwI~

//import androidx.appcompat.app.AppCompatActivity;                 //~va57R~
import android.app.Activity;                                       //~va57M~
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.ActionBar;                           //~1a98R~//~1aqwI~
import androidx.appcompat.app.AppCompatActivity;                   //~1aq9I~
import android.view.Window.Callback;                               //~va56I~

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;                                           //~va80I~


//public class jnp extends Activity                              //~0914R~//~va57R~//~1aq9R~
//public class jnp extends AppCompatActivity                       //~va57R~
public class jnp extends AppCompatActivity                         //~1aq9I~
//  implements android.view.View.OnKeyListener                     //~va56R~
                      implements Callback//~1122I~                 //~@@@@R~//~va56I~
{                                                                  //~va57I~
    public static final int PERMISSION_EXTERNAL_STORAGE=2;         //~9B09I~//~va80I~
    public static final int PERMISSION_EXTERNAL_STORAGE_READ=3; //ReadOnly   //~1Ak2I~//~1ak2I~//~va80I~
	public static final int KEYCODE_BACK   =android.view.KeyEvent.KEYCODE_BACK;   //back//~v@@@R~//~1212I~//~va56I~
	public static final String CN="jnp";                           //~va80I~
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
		setCallback_BackPress();                                    //~vay5I~//~vaq6I~//~vaa5I~
		setTitlebarIcon();                                     //~1a98R~//~1aqwI~
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
		new UMediaStore();   //-->OptionBGM.getBGMPreference()     //~va80R~
        chkPermission();                                           //~va80I~
    }                                                              //~0914I~
//**                                                               //~0523I~
	//*************************                                    //~va80I~
    @Override                                                      //~va80I~
    public void onResume()                                         //~va80I~
    {                                                              //~va80I~
        if(Dump.Y) Dump.println(CN+"onResume");                    //~va80I~
        super.onResume();                                          //~va80I~
      	try                                                        //~va80I~
      	{                                                          //~va80I~
        	UMediaStore.onResume();	//chk Sound.playBGM            //~va80I~
        	AG.aBGMList.onResume();                                //~va80I~
			lockOrientation();                                     //~va80I~
      	}                                                          //~va80I~
      	catch(Exception e)                                         //~va80I~
      	{                                                          //~va80I~
      		Dump.println(e,CN+"onResume");                         //~va80I~
      	}                                                          //~va80I~
    }                                                              //~va80I~
	//*************************                                    //~va80I~
    @Override                                                      //~va80I~
    public void onPause()                                          //~va80I~
    {                                                              //~va80I~
        if(Dump.Y) Dump.println(AG.LN());                     //~va80I~//+vaa9R~
    	try                                                        //~va80I~
        {                                                          //~va80I~
	        UMediaStore.onPause();                                 //~va80I~
    	    AG.aBGMList.onPause();                                 //~va80I~
        	super.onPause();                                       //~va80I~
        }                                                          //~va80I~
        catch(Exception e)                                         //~va80I~
        {                                                          //~va80I~
        	Dump.println(e,CN+"onPause");                          //~va80I~
        }                                                          //~va80I~
    }                                                              //~va80I~
	//*************************                                    //~va80I~
    @Override                                                      //~0C06I~
    protected void onDestroy()                                     //~0C06I~
	{                                                              //~0C06I~
      try                                                          //~va80I~
      {                                                            //~va80I~
//    	System.out.println("jnp onDestroy");                       //~0C06I~//~0C12R~
    	super.onDestroy();                                         //~0C06I~
        if(Dump.Y) Dump.println(AG.LN()+"AG.status="+AG.status);//~vaa8R~//+vaa9R~
        AG.aBGMList.stopAll();                                     //~va43I~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onDestroy");                          //~va80I~
      }                                                            //~va80I~
//    Dump.close();                                                //~vaa8R~
//        if (AG.status==AG.STATUS_STOPFINISH)                       //~@@@@I~//~vaa8I~
//        	Utils.exit(0,true); //System.exit() to kill myself to clear static variable//~1413I~//~@@@@R~//~vaa8I~
      if (AG.status==AG.STATUS_STOPFINISH)                         //~vaa8I~
      	Utils.exit(0,true); //Dump close() and System.exit() to kill myself to clear static variable//~vaa8I~
    }                                                              //~0C06I~
//**                                                               //~0915I~
    @Override                                                     //~0915I~
    public boolean onCreateOptionsMenu(Menu menu)                           //~0915I~
	{  
      try                                                          //~va80I~
      {                                                            //~va80I~
    	super.onCreateOptionsMenu(menu);
    	nppMenu.init(menu,nppView);		//setup menu		               //~0915R~//~0A05R~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onCreateOptioMenu");                  //~va80I~
      }                                                            //~va80I~
    	return true;
	}                                                              //~0915I~
//**                                                               //~0915I~
    @Override                                                     //~0915I~
    public boolean onOptionsItemSelected(MenuItem item)            //~0915I~
	{                                                              //~0915I~
      try                                                          //~va80I~
      {                                                            //~va80I~
    	nppMenu.selected(item);		//setup menu                   //~0915I~//~0A19R~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onCreateOptioMenu");                  //~va80I~
      }                                                            //~va80I~
    	return true;
	}                                                              //~0915I~
//**                                                               //~0C06I~
    @Override                                                      //~0C06I~
    public void onConfigurationChanged(Configuration Pcfg)         //~0C06I~
	{                                                              //~0C06I~
    	int orientation;                                           //~0C07I~
      try                                                          //~va80I~
      {                                                            //~va80I~
    	super.onConfigurationChanged(Pcfg);                        //~0C06I~//~0C07M~
//    	System.out.println("jnp configuration changed");           //~0C06I~//~0C12R~
      	if (Dump.Y) Dump.println(AG.LN()+"configuration changed Pcfg.orientation="+Pcfg.orientation+",Wnp.Sswportrate="+Wnp.Sswportrate);//~1aqwI~//~vaa7R~
        boolean orichanged=(Pcfg.orientation==Configuration.ORIENTATION_PORTRAIT)!=(Wnp.Sswportrate==true);//~0C06I~
        if (orichanged)                                            //~0C07I~
        	orientation=Pcfg.orientation;                          //~0C07I~
        else                                                       //~0C07I~
        	orientation=0;                                         //~0C07I~
//    	nppView.orientationChanged(orientation);                    //~0C06R~//~0C07R~//~vaa7R~
        AG.setScreenSize();                                        //~va40I~
		AG.setScreenSizeReal();                                    //~vaa7R~
      	nppView.orientationChanged(orientation);                   //~vaa7I~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onConfigurationChanged");             //~va80I~
      }                                                            //~va80I~
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
//  private void onExit()                                          //~8C03I~//~@@@@M~//~va56I~//~va67R~
    public  void onExit()                                          //~va67I~
    {                                                              //~8C03I~//~@@@@M~//~va56I~
        if (Dump.Y) Dump.println("jnp.onExit");           //~9B06I~//~va56I~
//      Dump.close();                                              //~va56I~//~vaa8R~
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
//**********************************                               //~v@@@I~//~va80I~
    public static boolean chkPermission()                          //~v@@@I~//~va80R~
    {                                                              //~v@@@I~//~va80I~
        if (Dump.Y) Dump.println("jnp.chkPermission");            //~v@@@I~//~va80I~
        boolean rc=UPermission.isPermissionGrantedExternalStorageRead();//~1ak2I~//~va80R~
        AG.swGrantedExternalStorageRead=rc;                       //~1ak2I~//~va80I~
        if (Dump.Y) Dump.println("jnp.chkPermission swGrantedExternalStorageRead=rc="+rc);//~1ak2I~//~va80I~
        return rc;                                                 //~v@@@I~//~va80I~
    }                                                              //~v@@@I~//~va80I~
//**********************************                               //~va80I~
    public static boolean requestPermission()                      //~va80R~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println("jnp.requestPermission");         //~va80I~
        boolean rc=chkPermission();                                //~va80R~
        if (!rc)                                                   //~va80I~
        {                                                          //~va80I~
          	if (AG.osVersion>=30) //scoped storage	//no chk write permission for scoped storage//~va80I~
          	{                                                      //~va80I~
		    	UPermission.requestPermissionExternalStorageRead(PERMISSION_EXTERNAL_STORAGE_READ);//~va80R~
          	}                                                      //~va80I~
          	else                                                   //~va80I~
          	{                                                      //~va80I~
		    	UPermission.requestPermissionExternalStorage(PERMISSION_EXTERNAL_STORAGE);//~va80R~
          	}                                                      //~va80I~
        }                                                          //~va80I~
        if (Dump.Y) Dump.println("jnp.requestPermission");         //~va80I~
        return rc;                                                 //~va80I~
    }                                                              //~va80I~
//***************************************************************************//~9930I~//~va80I~
	@Override                                                      //~9930I~//~va80I~
    public void onRequestPermissionsResult(int PrequestID,String[] Ptypes,int[] Presults)//~9930I~//~va80I~
    {                                                              //~9930I~//~va80I~
	    super.onRequestPermissionsResult(PrequestID,Ptypes,Presults);//~1aq9I~
      try                                                          //~va80I~
      {                                                            //~va80I~
        if (Dump.Y) Dump.println("MainActivity.onRequestPermissionResult reqid="+PrequestID+",type="+ Arrays.toString(Ptypes)+",result="+Arrays.toString(Presults));//~9930I~//~va80I~
        UPermission.onRequestPermissionResult(PrequestID,Ptypes,Presults);         //~1amsI~//~vau2R~//~va80I~
        if (Presults.length==0)  //once crashed //TODO              //~1ak4R~//~va80I~
        {                                                          //~1ak4I~//~va80I~
        	if (Dump.Y) Dump.println("MainActivity.onRequestPermissionResult@@@@ no data Length=0");//~1ak4I~//~va80I~
            return;                                                //~1ak4I~//~va80I~
        }                                                          //~1ak4I~//~va80I~
        boolean granted;                                           //~va80I~
        switch(PrequestID)                                         //~9930I~//~va80I~
        {                                                          //~9930I~//~va80I~
//        case PERMISSION_LOCATION:                                  //~9930I~//~va80I~
//            granted=UView.isPermissionGranted(Presults[0]); //~9930I~//~va80I~
//            MenuDlgConnect.grantedWifi(granted);                   //~9930R~//~va80I~
//            break;                                                 //~9930I~//~va80I~
        case PERMISSION_EXTERNAL_STORAGE:                          //~9B09I~//~va80I~
        	granted=UPermission.isPermissionGranted(Presults[0]);//~9B09I~//~va80R~
//            UFile.grantedExternalStorage(granted);                 //~9B09I~//~va80I~
            UPermission.grantedExternalStorage(granted);           //~va80R~
//            recoverProp();                                         //~vae8I~//~va80R~
			if (granted && AG.swRequestedBGMPermission)            //~va80R~
            	AG.aOptionBGM.granted();                //~va80R~
			AG.swRequestedBGMPermission=false;                     //~va80R~
        	break;                                                 //~9B09I~//~va80I~
        case PERMISSION_EXTERNAL_STORAGE_READ:                     //~vae0I~//~va80I~
        	granted=UPermission.isPermissionGranted(Presults[0]);        //~vae0I~//~va80R~
//          UFile.grantedExternalStorageRead(granted);             //~vae0I~//~va80I~
            UPermission.grantedExternalStorageRead(granted);       //~va80R~
//            AG.aUScoped.grantedExternalStorageRead(granted);        //~vae0I~//~va80R~
//            recoverProp();                                         //~vae8I~//~va80R~
			if (granted && AG.swRequestedBGMPermission)            //~va80R~
            	AG.aOptionBGM.granted();                //~va80R~
			AG.swRequestedBGMPermission=false;                     //~va80R~
        	break;                                                 //~vae0I~//~va80I~
//        case PERMISSION_BLUETOOTH:  //API31                        //~vam8I~//~va80I~
//            BTI.grantedPermission(Ptypes,Presults);                //~vas0I~//~va80I~
//            break;                                                 //~vam8I~//~va80I~
        }                                                          //~9930I~//~va80I~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onConfigurationChanged");             //~va80I~
      }                                                            //~va80I~
    }                                                              //~9930I~//~va80I~
//***************************************************************************//~v107I~//~1ak5I~//~va80I~
	@Override                                                      //~v107I~//~1ak5I~//~va80I~
    public void onActivityResult(int requestCode, int resultCode, Intent data)//~1ak2R~//~va80I~
	{                                                              //~1ak2I~//~va80I~
        if(Dump.Y) Dump.println("MainActivity.onActivityResult req="+requestCode+",result="+ resultCode+",intent="+data);//~v107I~//~1A6aR~//~1ak5I~//~vaf0R~//~vavwR~//~va80I~
     try                                                           //~vavwI~//~va80I~
     {                                                             //~vavwI~//~va80I~
      if (requestCode==AG.ACTIVITY_REQUEST_PICKUP_AUDIO)           //~1Ak2I~//~1ak2I~//~va80I~
      {                                                            //~1Ak2I~//~1ak2I~//~va80I~
        if(Dump.Y) Dump.println("jnp.onActivityResult AUDIO");//~1ak2I~//~vaf0R~//~va80I~
        UMediaStore.onActivityResult(requestCode,resultCode,data); //~1Ak2I~//~1ak2I~//~va80I~
      }                                                            //~1Ak2I~//~1ak2I~//~va80I~
      else                                                         //~1Ak2I~//~1ak2I~//~va80I~
//      if (requestCode==AG.ACTIVITY_REQUEST_PICKUP_IMAGE)           //~var8I~//~va80R~
//      {                                                            //~var8I~//~va80R~
//        if(Dump.Y) Dump.println("jnp.onActivityResult Image");//~var8I~//~va80R~
//        UMediaStore.onActivityResultImage(resultCode,data);//~var8I~//~va80R~
//      }                                                            //~var8I~//~va80R~
//      else                                                         //~vavwR~//~va80R~
//      if (requestCode==AG.ACTIVITY_REQUEST_PICKUP_ACTION)          //~vavwR~//~va80R~
//      {                                                            //~vavwR~//~va80R~
//        if(Dump.Y) Dump.println("jnp.onActivityResult Pickup_Action");//~vavwR~//~va80R~
//        UMediaStore.onActivityResultSelectImagePicker(resultCode,data);//~vavwR~//~va80R~
//      }                                                            //~vavaI~//~va80R~
//      else                                                         //~var8I~//~va80R~
//      if (requestCode>AG.ACTIVITY_REQUEST_SCOPED && requestCode<AG.ACTIVITY_REQUEST_SCOPED_LAST)//~1Ak1I~//~1ak1I~//~va80R~
//      {                                                            //~1Ak1I~//~1ak1I~//~va80R~
//        AG.aUScoped.onActivityResult(requestCode,resultCode,data); //~1Ak1R~//~1ak1I~//~va80R~
//        recoverProp();                                             //~vae8M~//~va80R~
//      }                                                            //~1Ak1I~//~1ak1I~//~va80R~
//      else                                                         //~1Ak1I~//~1ak1I~//~va80R~
//      {                                                            //~1Ak1I~//~1ak1I~//~va80R~
//        if (AG.aBTI!=null)                                       //~v107R~//~@@@@R~//~1ak5I~//~va80R~
//            AG.aBTI.activityResult(requestCode,resultCode,data); //~v107R~//~@@@@R~//~1ak5I~//~va80R~
//      }                                                            //~1ak1R~//~va80R~
	   super.onActivityResult(requestCode,resultCode,data);	//if not called,compile err//~1ak2I~//~va80I~
     }                                                             //~vavwI~//~va80I~
     catch(Exception e)                                            //~vavwI~//~va80I~
     {                                                             //~vavwI~//~va80I~
        Dump.println(e,"jnp.OnActivityResult reqCode="+requestCode+",resultCode="+resultCode+",intent="+data);//~vavwI~//~va80I~
     }                                                             //~vavwI~//~va80I~
    }                                                              //~v107I~//~1ak5I~//~va80I~
//*****************************************************************************************//~vaa9I~
	private int getOrientation()                                   //~vaa9I~
    {                                                              //~vaa9I~
        int cur=AG.resource.getConfiguration().orientation;        //~1aqzI~//~vaa9I~
        if (Dump.Y) Dump.println(AG.LN()+"current="+cur);//~1aqzI~ //~vaa9I~
        return cur;                                                //~vaa9I~
    }                                                              //~vaa9I~
//*****************************************************************************************//~va80I~
	private void lockOrientation()                                 //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println("jnp.lockOrientation");           //~va80I~
        int req;                                                   //~va80I~
      if (true)                                                    //~vaa9I~
      {                                                            //~vaa9I~
      	req=getOrientation();                                      //~vaa9I~
      }                                                            //~vaa9I~
      else                                                         //~vaa9I~
        req= ActivityInfo.SCREEN_ORIENTATION_LOCKED;                //~va80I~
        setRequestedOrientation(req);                              //~va80I~
    }                                                              //~va80I~
//*****************************************************************/~8C03I~//~vau2R~//~vaq6I~//~vaa5I~
//*deprecated but androidx.activity.ComponentActivity supports this.//~vau2I~//~vaq6I~//~vaa5I~
//*OnBackPressedDispatcher is called before onBackPressed.         //~vau2I~//~vaq6I~//~vaa5I~
//*****************************************************************/~8C03I~//~vau2I~//~vaq6I~//~vaa5I~
	private void setCallback_BackPress()                           //~vay5I~//~vaq6I~//~vaa5I~
    {                                                              //~vay5I~//~vaq6I~//~vaa5I~
        if (Dump.Y) Dump.println(AG.LN()+"osVersion="+AG.osVersion);//~vaq6I~//~vaa5I~
    	OnBackPressedCallback cb=new OnBackPressedCallback(true/*false:disable*/)//~vay5R~//~vaq6I~//~vaa5I~
        {                                                          //~vay5I~//~vaq6I~//~vaa5I~
        	@Override                                              //~vay5I~//~vaq6I~//~vaa5I~
            public void handleOnBackPressed()                      //~vay5I~//~vaq6I~//~vaa5I~
            {                                                      //~vay5I~//~vaq6I~//~vaa5I~
		        if (Dump.Y) Dump.println(AG.LN());//~vay5I~        //~vaq6R~//~vaa5I~
            	onBackPressedAll();                                //~vay5I~//~vaq6I~//~vaa5I~
            }                                                      //~vay5I~//~vaq6I~//~vaa5I~
        };                                                          //~vay5I~//~vaq6I~//~vaa5I~
        getOnBackPressedDispatcher().addCallback(this,cb);        //~vay5R~//~vaq6I~//~vaa5I~
    }                                                              //~vay5I~//~vaq6I~//~vaa5I~
//*****************************************************************/~8C03I~//~vaq6I~//~vaa5I~
    private void onBackPressedAll()                                //~vay5I~//~vaq6I~//~vaa5I~
    {                                                              //~vay5I~//~vaq6I~//~vaa5I~
        if (Dump.Y) Dump.println(AG.LN());                         //~vaq6I~//~vaa5I~
        onExit();                                                  //~1aq9R~
    }                                                              //~vay5I~//~vaq6I~//~vaa5I~
//*****************************************************************************************//~1a98R~//~1aqwI~
	private void setTitlebarIcon()                                 //~1a98R~//~1aqwI~
    {                                                              //~1a98R~//~1aqwI~
        if (Dump.Y) Dump.println(AG.LN());                         //~1a98R~//~1aqwI~
        ActionBar ab=getSupportActionBar();                        //~1a98R~//~1aqwI~
        if (Dump.Y) Dump.println(AG.LN()+"AcionBar="+ab);          //~1aqwI~
        if (Dump.Y) Dump.println(AG.LN()+"dip2px="+AG.dip2pix);    //~1aqwI~
        if (ab!=null)                                              //~1a98R~//~1aqwI~
        {                                                          //~1a98R~//~1aqwI~
        	ab.setDisplayShowHomeEnabled(true);                    //~1a98R~//~1aqwI~
          if (true)                                                //~1aqwI~
          {                                                        //~1aqwI~
          	ab.setLogo(R.mipmap.ic_launcher);                      //~1a98R~//~1aqwI~
          }                                                        //~1aqwI~
          else                                                     //~1aqwI~
		  if (false)                                               //~1aqwI~
          {                                                        //~1aqwI~
          	Bitmap bm=BitmapFactory.decodeResource(AG.resource,R.mipmap.ic_launcher_foreground);//~1aqwI~
        	if (Dump.Y) Dump.println(AG.LN()+"bm="+bm);            //~1aqwI~
            int hhdp=Utils.getActionBarHeight();                   //~1aqwI~
            int hhpix=(int)(hhdp*AG.dip2pix);                      //~1aqwI~
        	if (Dump.Y) Dump.println(AG.LN()+"hhdp="+hhdp+",hhpix="+hhpix);//~1aqwI~
          	Bitmap bmscaled=Bitmap.createScaledBitmap(bm,hhpix,hhpix,true);//~1aqwI~
          	android.graphics.drawable.BitmapDrawable bmdraw=new android.graphics.drawable.BitmapDrawable(AG.resource,bmscaled);//~1aqwI~
          	ab.setLogo(bmdraw);                                    //~1aqwI~
          }                                                        //~1aqwI~
          else                                                     //~1aqwI~
          {                                                        //~1aqwI~
          	Bitmap bmsrc=BitmapFactory.decodeResource(AG.resource,R.mipmap.ic_launcher_foreground);//~1aqwI~
        	if (Dump.Y) Dump.println(AG.LN()+"bmsrc="+bmsrc+",width="+bmsrc.getWidth()+",height="+bmsrc.getHeight());//~1aqwI~
          	android.graphics.drawable.BitmapDrawable bmsrcdraw=new android.graphics.drawable.BitmapDrawable(AG.resource,bmsrc);//~1aqwI~
          	ab.setLogo(bmsrcdraw);                                 //~1aqwI~
          }                                                        //~1aqwI~
        	ab.setDisplayUseLogoEnabled(true);                     //~1a98R~//~1aqwI~
        }                                                          //~1a98R~//~1aqwI~
    }                                                              //~1a98R~//~1aqwI~
}