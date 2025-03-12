//CID://+va80R~:          update#=     75                          //~va80R~
//***************************************************************  //~va40I~
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

//import androidx.appcompat.app.AppCompatActivity;                 //~va57R~
import android.app.Activity;                                       //~va57M~
import androidx.activity.OnBackPressedCallback;
import android.view.Window.Callback;                               //~va56I~

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Arrays;                                           //~va80I~


public class jnp extends Activity                              //~0914R~//~va57R~
//public class jnp extends AppCompatActivity                       //~va57R~
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
			lockOrientation();                                     //+va80I~
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
        if(Dump.Y) Dump.println(CN+"onPause");                     //~va80I~
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
        AG.aBGMList.stopAll();                                     //~va43I~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"onDestroy");                          //~va80I~
      }                                                            //~va80I~
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
        boolean orichanged=(Pcfg.orientation==Configuration.ORIENTATION_PORTRAIT)!=(Wnp.Sswportrate==true);//~0C06I~
        if (orichanged)                                            //~0C07I~
        	orientation=Pcfg.orientation;                          //~0C07I~
        else                                                       //~0C07I~
        	orientation=0;                                         //~0C07I~
    	nppView.orientationChanged(orientation);                    //~0C06R~//~0C07R~
        AG.setScreenSize();                                        //~va40I~
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
//*****************************************************************************************//+va80I~
	private void lockOrientation()                                 //+va80I~
    {                                                              //+va80I~
        if (Dump.Y) Dump.println("jnp.lockOrientation");           //+va80I~
        int req;                                                   //+va80I~
        req= ActivityInfo.SCREEN_ORIENTATION_LOCKED;                //+va80I~
        setRequestedOrientation(req);                              //+va80I~
    }                                                              //+va80I~
}