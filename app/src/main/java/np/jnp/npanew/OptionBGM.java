//CID://+va81R~:          update#=    181                          //+va81R~
//*********************************************************************//~va30I~
//va81 240303 add BGM kizuki                                       //+va81I~
//va80 240219 selectable BGM                                       //~va80I~
//*********************************************************************//~va30I~
package np.jnp.npanew;                                              //~va30R~//~va40R~//~va41R~

import np.jnp.npanew.R;                                               //~va30I~//~va41R~
import np.jnp.npanew.utils.AG;                                     //~va41R~
import np.jnp.npanew.utils.Dump;                                      //~va40I~//~va41R~
import np.jnp.npanew.utils.Utils;                                  //~va41R~
import np.jnp.npanew.utils.UButton;                                //~va80I~
import np.jnp.npanew.utils.UMediaStore;                            //~va80I~
import np.jnp.npanew.utils.UButtonRG;                              //~va80I~
import np.jnp.npanew.utils.URadioGroup;                            //~va80I~

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;                                      //~v@@@I~//~va80I~
import android.net.Uri;                                            //~va80I~

import static np.jnp.npanew.utils.BGMList.*;                       //~va41R~

//********************************************************************//~va01I~
//va23:051221 langauge ctl(English and japanese)                   //~va23I~
//va16:051113 option to drop redundancy to make                    //~va16R~
//v030:051108 enable clear when make mode to back to anss mode     //~v030I~
//va03:051104 dencity by slider                                    //~va03I~
//va01:051013 5*5 support                                          //~va01I~
//v008:051002 answer chk fail if I'll try button is not pressed because answer data is not set//~5A02I~
//            So,disable Next Button if I'll try is not pressed    //~5A02I~
//********************************************************************//~va01I~

public class OptionBGM                                             //~va80R~
    	implements UMediaStore.UMediaStoreI                        //~vae9R~//~va80I~
            ,UButton.UButtonI                                  //~vat0I~//~va80I~
{                                                                  //~va80R~
    //**********************************************               //~va80I~
    private static String CN="OptionBGM:";                         //~va80R~
    public static final int MAX_USERBGM=6;                         //~va80R~
    public static final String KEY_SELECTED="SelectedBGM";         //~va80I~
    public static final String KEY_STARTBGM="StartBGM";            //~va80I~
    public static final String KEY_DEFAULTBGM="DefaultBGM";        //~va80I~
    private static final int[] IDS_LLUSERBGM=new int[/*MAX_USERBGM*/]{R.id.llUserBGM0,R.id.llUserBGM1,R.id.llUserBGM2,R.id.llUserBGM3,R.id.llUserBGM4,R.id.llUserBGM5};//~va80R~
    private static final String[] keyUris=new String[/*MAX_USERBGM*/]{"UserBGMUri0","UserBGMUri1","UserBGMUri2","UserBGMUri3","UserBGMUri4","UserBGMUri5"};//~va80R~
    private static final String[] keyTitles=new String[/*MAX_USERBGM*/]{"UserBGMTitle0","UserBGMTitle1","UserBGMTitle2","UserBGMTitle3","UserBGMTitle4","UserBGMTitle5"};//~va80R~
    private              String NOTITLE;                           //~vai0I~//~va80I~
    private static final String NOTITLE_ENG=Utils.getStr(R.string.NoUserBGMENG);//~vai0I~//~va80I~
    private static final String NOTITLE_JPN=Utils.getStr(R.string.NoUserBGMJPN);//~vai0I~//~va80I~
    protected int[] rgIDs={R.id.rgBGM0,R.id.rgBGM1,R.id.rgBGM2,R.id.rgBGM3,R.id.rgBGM4,R.id.rgBGM5};//0:default//~va80R~
//  protected int[] rgBGMstartIDs={R.id.rbBGMstart1,R.id.rbBGMstart2};//~va80I~//+va81R~
    protected int[] rgBGMstartIDs={R.id.rbBGMstart1,R.id.rbBGMstart2,R.id.rbBGMstart3};//+va81I~
    protected int[] rgBGMdefaultIDs={R.id.rbBGMdefault,R.id.rbBGMdefaultStart};//~va80I~
                                                                   //~va80I~
    private TextView[] tvsUserBGMTitle=new TextView[MAX_USERBGM];  //~vae9M~//~va80I~
    private Button[]  btnsUserBGM=new Button[MAX_USERBGM];         //~vae9R~//~va80I~
    private Button[]  btnsUserBGMDelete=new Button[MAX_USERBGM];   //~vae9I~//~va80I~
    private static String[]  strUris=new String[MAX_USERBGM];      //~va80R~
    private static String[]  strTitles=new String[MAX_USERBGM];    //~va80R~
    private static int selectedBGM;                                //~va80I~
    private static int startBGM;                                   //~va80I~
    private static int defaultBGM;                                 //~va80I~
    SharedPreferences pref;                                        //~va80I~
    Dialog dlg;                                                    //~va80I~
    View layout;                                                   //~va80I~
    int  userBGMButtonNumber;                                      //~va80I~
    Button btnClicked;                                             //~va80I~
    private UButtonRG UBRG;                                        //~va80R~
	private URadioGroup rgBGMstart;                                //~va80R~
	private URadioGroup rgBGMdefault;                              //~va80I~
    //**********************************************               //~va80I~
	public static OptionBGM setup(Dialog Pdlg,View PlayoutView)    //~va80R~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println("OptionBGM.setup");               //~va80I~
    	OptionBGM aOptionBGM=new OptionBGM();                      //~va80R~
        AG.aOptionBGM=aOptionBGM;                                  //~va80I~
        aOptionBGM.dlg=Pdlg;                                       //~va80R~
        aOptionBGM.layout=PlayoutView;                             //~va80R~
        aOptionBGM.init();                                         //~va80R~
        return aOptionBGM;                                         //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	private void init()                                            //~va80R~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println("OptionBGM.init");                //~va80R~
        NOTITLE=Utils.getStr(R.string.NoUserBGM);                  //~vai0I~//~va80I~
        UBRG=new UButtonRG((ViewGroup)layout,rgIDs.length);        //~va80R~
	    for (int ii=0;ii<rgIDs.length;ii++)                        //~va80R~
        {                                                          //~va80I~
			UBRG.add(ii,rgIDs[ii]);                                //~va80R~
        }                                                          //~va80I~
        for (int ii=0;ii<MAX_USERBGM;ii++)                         //~va80R~
        {                                                          //~va80I~
	    	LinearLayout ll=(LinearLayout)Utils.findViewById(layout,IDS_LLUSERBGM[ii]);//~va80R~
	    	tvsUserBGMTitle[ii]=(TextView)Utils.findViewById(ll,R.id.tvUserBGM);//~va80R~
	    	btnsUserBGM[ii]=UButton.bind(ll,R.id.btnUserBGM,this); //~va80I~
	    	btnsUserBGMDelete[ii]=UButton.bind(ll,R.id.btnUserBGMDelete,this);//~va80I~
        }                                                          //~va80I~
	    rgBGMstart=new URadioGroup(layout,R.id.rgBGMstart,0/*parm for listener*/,rgBGMstartIDs);//~va80R~
	    rgBGMdefault=new URadioGroup(layout,R.id.rgBGMdefault,0/*parm for listener*/,rgBGMdefaultIDs);//~va80I~
        showBGM();                                                 //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80M~
    //*from UmediaStore.init setup static preference               //~va80I~
    //**********************************************               //~va80I~
	public static void getBGMPreference()                          //~va80R~
    {                                                              //~va80M~
        if (Dump.Y) Dump.println(CN+"getBGMPreference");           //~va80I~
        for (int ii=0;ii<MAX_USERBGM;ii++)                         //~va80R~
        {                                                          //~va80M~
        	String uri=getPreference(keyUris[ii],"");              //~va80R~
        	String title=getPreference(keyTitles[ii],"");          //~va80R~
            strUris[ii]=uri;                                       //~va80R~
            strTitles[ii]=title;                                   //~va80R~
        }                                                          //~va80M~
        selectedBGM=getPreference(KEY_SELECTED,0);                 //~va80R~
        startBGM=getPreference(KEY_STARTBGM,0);                    //~va80I~
        defaultBGM=getPreference(KEY_DEFAULTBGM,0);                //~va80R~
    }                                                              //~va80M~
    //**********************************************               //~va80I~
	public static String getSoundUri()                             //~va80R~
    {                                                              //~va80I~
        String rc=null;                                            //~va80R~
        if (selectedBGM>=1 && selectedBGM<MAX_USERBGM)  //0:default//~va80R~
            if (!strUris[selectedBGM].equals(""))                  //~va80R~
                rc=strUris[selectedBGM];                           //~va80R~
        if (Dump.Y) Dump.println(CN+"getSoundUri rc="+rc);         //~va80R~
        return rc;                                                 //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	public static int getSoundID()                                 //~va80R~
    {                                                              //~va80I~
    	int rc=SOUNDID_BGM_START;                                  //~va80R~
        if (startBGM==1)                                           //~va80I~
    		rc=SOUNDID_BGM_START2;                                 //~va80I~
        else                                                       //+va81I~
        if (startBGM==2)                                           //+va81I~
    		rc=SOUNDID_BGM_START3;                                 //+va81I~
        if (Dump.Y) Dump.println(CN+"getSoundID startBGM="+startBGM+",rc="+rc);//~va80R~
        return rc;                                                 //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	public static int getSoundIDdefault()                          //~va80I~
    {                                                              //~va80I~
    	int rc=0;                                                  //~va80I~
        if (selectedBGM==0)	//default                              //~va80I~
        {                                                          //~va80I~
	    	rc=SOUNDID_BGM_THINKING;                               //~va80I~
        	if (defaultBGM==1)                                     //~va80I~
	    		rc=getSoundID();	//START or START1              //~va80I~
        }                                                          //~va80I~
        if (Dump.Y) Dump.println(CN+"getSoundIDDefault selectedBGM="+selectedBGM+",defaultBGM="+defaultBGM+",rc="+rc);//~va80I~
        return rc;                                                 //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	public void onOK()                                             //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println(CN+"onOK");                       //~va80I~
        for (int ii=0;ii<MAX_USERBGM;ii++)                         //~va80R~
        {                                                          //~va80I~
        	String keyUri=keyUris[ii];                             //~va80R~
        	String keyTitle=keyTitles[ii];                         //~va80R~
        	putPreference(keyUri,strUris[ii]);                     //~va80I~
        	putPreference(keyTitle,strTitles[ii]);                 //~va80I~
        }                                                          //~va80I~
	    selectedBGM=getBGMSelected();                              //~va80I~
        putPreference(KEY_SELECTED,selectedBGM);                   //~va80I~
	    startBGM=getBGMstart();                                    //~va80I~
	    defaultBGM=getBGMdefault();                                //~va80I~
        putPreference(KEY_STARTBGM,startBGM);                      //~va80I~
        putPreference(KEY_DEFAULTBGM,defaultBGM);                  //~va80R~
        if (AG.aButtonDlg.optBGM>0)                                //~va80I~
    		AG.aBGMList.play();                                    //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	private void showBGM()                                         //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println(CN+"showBGM");                    //~va80I~
        for (int ii=0;ii<MAX_USERBGM;ii++)                         //~va80R~
        {                                                          //~va80I~
            if (ii==0)                                             //~va80I~
	        	UBRG.setEnabled(0,true);                           //~va80I~
            else                                                   //~va80I~
        	if (strUris[ii].equals(""))                            //~va80R~
            {                                                      //~va80I~
	        	UBRG.setEnabled(ii,false);                         //~va80R~
                tvsUserBGMTitle[ii].setText(NOTITLE);              //~va80M~
            }                                                      //~va80I~
            else                                                   //~va80I~
            {                                                      //~va80I~
	        	UBRG.setEnabled(ii,true);                          //~va80R~
	        	tvsUserBGMTitle[ii].setText(strTitles[ii]);         //~va80I~
            }                                                      //~va80I~
        }                                                          //~va80I~
	    UBRG.setChecked(selectedBGM);                              //~va80I~
	    rgBGMstart.setCheckedID(startBGM,false/*fixed*/);          //~va80I~
	    rgBGMdefault.setCheckedID(defaultBGM,false/*fixed*/);      //~va80I~
    }                                                              //~va80I~
    //******************************************                   //~va80I~
    private int getBGMSelected()                                   //~va80R~
    {                                                              //~va80I~
    	int selected=UBRG.getChecked();                          //~va80R~
        if (Dump.Y) Dump.println(CN+"getBGMSelected selected="+selected);//~va80R~
        return selected;                                           //~va80I~
    }                                                              //~va80I~
    //******************************************                   //~va80I~
    private int getBGMstart()                                      //~va80I~
    {                                                              //~va80I~
    	int chkid=rgBGMstart.getCheckedID();                         //~va80I~
        if (Dump.Y) Dump.println(CN+"getBGMstart checkedID="+chkid);//~va80R~
        return chkid;                                              //~va80I~
    }                                                              //~va80I~
    //******************************************                   //~va80I~
    private int getBGMdefault()                                    //~va80I~
    {                                                              //~va80I~
    	int chkid=rgBGMdefault.getCheckedID();                     //~va80I~
        if (Dump.Y) Dump.println(CN+"getBGMdefault checkedID="+chkid);//~va80I~
        return chkid;                                              //~va80I~
    }                                                              //~va80I~
    //**********************************************               //~va80I~
	public int getSoundIDUser()                                    //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println(CN+"getSoundIDUser");             //~va80I~
        for (int ii=1;ii<MAX_USERBGM;ii++)                         //~va80R~
        {                                                          //~va80I~
        	String title=getPreference(keyTitles[ii],"");          //~va80R~
            if (title.equals(""))                                  //~va80I~
            {                                                      //~va80I~
	        	tvsUserBGMTitle[ii].setText(NOTITLE);              //~va80I~
            }                                                      //~va80I~
            else                                                   //~va80I~
            {                                                      //~va80I~
	        	tvsUserBGMTitle[ii].setText(keyTitles[ii]);        //~va80R~
            }                                                      //~va80I~
        }                                                          //~va80I~
    	userBGMButtonNumber=getBGMSelected();                      //~va80R~
        return userBGMButtonNumber;                                //~va80I~
    }                                                              //~va80I~
    //**************************************                       //~v@@@I~//~va80I~
    @Override                                                      //~v@@@I~//~va80I~
    public void onClickButton(Button Pbutton)                 //~v@@@I~//~va80R~
    {                                                              //~v@@@I~//~va80I~
        btnClicked=Pbutton;                                        //~va80I~
    	int btnid=Pbutton.getId();                                 //~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.onClickButton btnid="+Integer.toHexString(btnid));//~v@@@I~//~va80R~
        switch(btnid)                                          //~vae9I~//~va80R~
        {                                                          //~vae9I~//~va80I~
        case R.id.btnUserBGM:                                      //~vae9I~//~va80I~
            userBGMButtonNumber=getBGMButtonNumber();              //~vae9I~//~va80I~
        	boolean swUPicker=false;                               //~va80R~
    	    UMediaStore.changeBGM(this,swUPicker);	//callback BGMSelected                           //~1Ak2R~//~vae9R~//~va80R~
            break;                                                 //~vae9I~//~va80I~
        case R.id.btnUserBGMDelete:                                //~vae9I~//~va80I~
            userBGMButtonNumber=getBGMButtonNumber();              //~vae9I~//~va80I~
	    	deleteBGM();                                           //~vae9I~//~va80I~
            break;                                                 //~vae9I~//~va80I~
        }                                                          //~vae9I~//~va80I~
    }                                                              //~v@@@I~//~va80I~
    //********************************************************     //~va80R~
    //*from jnp,when swRequestedBGMPermission ON                   //~va80I~
    //********************************************************     //~va80I~
    public void granted()                                          //~va80I~
    {                                                              //~va80I~
    	if (Dump.Y) Dump.println(CN+"granted numBtn="+userBGMButtonNumber);//~va80I~
        if (userBGMButtonNumber>0 && userBGMButtonNumber<MAX_USERBGM)//~va80R~
//  		UMediaStore.changeBGM(this,false);                     //~va80R~
    	    AG.aUMediaStore.requestPickup(false);               //~va80I~
    }                                                              //~va80I~
    //**************************************                       //~vae9I~//~va80I~
    private int getBGMButtonNumber()                               //~vae9I~//~va80I~
    {                                                              //~vae9I~//~va80I~
        Button btn=btnClicked;  //UFDlg protected                  //~vae9I~//~va80I~
        int llid=((View)(btn.getParent())).getId();                //~vae9R~//~va80I~
        int num=-1;                                                //~vae9I~//~va80I~
        for (int ii=1;ii<MAX_USERBGM;ii++)                         //~vae9R~//~va80R~
        {                                                          //~vae9I~//~va80I~
	    	if (llid==IDS_LLUSERBGM[ii])                           //~vae9I~//~va80I~
            {                                                      //~vae9I~//~va80I~
            	num=ii;                                            //~vae9I~//~va80I~
            	break;                                             //~vae9I~//~va80I~
            }                                                      //~vae9I~//~va80I~
        }                                                          //~vae9I~//~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.getButtonNumber llid="+Integer.toHexString(llid)+",btnNumber="+num);//~vae9I~//~va80I~
        return num;                                                //~vae9I~//~va80I~
    }                                                              //~vae9I~//~va80I~
    //**************************************                       //~var8I~//~va80I~
    @Override //UMediastoreI                                       //~var8I~//~va80I~
    public void ImageSelected(Uri Puri,String Pid,String PdisplayName,String PtimestampAdded,String Psize)//~var8R~//~va80I~
    {                                                              //~var8I~//~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.ImageSelected uri="+Puri+",id="+Pid+",displayName="+PdisplayName+",timeStampAdded="+PtimestampAdded+",size="+Psize);//~var8R~//~va80R~
    }                                                              //~var8I~//~va80I~
    //**************************************                       //~vae9I~//~va80I~
    @Override //UMediastoreI                                       //~vae9I~//~va80I~
    public void BGMSelected(Uri Puri, UMediaStore.AudioFile PaudioFile)         //~vae9I~//~va80I~
    {                                                              //~vae9I~//~va80I~
      try                                                          //~va80I~
      {                                                            //~va80I~
        int num=userBGMButtonNumber;                               //~vae9R~//~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.BGMSelected btnNo="+num+",AudioFile="+PaudioFile.toString()+",uri="+Puri);//~vae9I~//~va80I~
        if (num>=1 && num<MAX_USERBGM)                             //~vae9R~//~va80R~
        {                                                          //~vae9I~//~va80I~
        	if (PaudioFile!=null)                                  //~vae9I~//~va80I~
            {                                                      //~vae9I~//~va80I~
        		String title=PaudioFile.title;                     //~vae9R~//~va80I~
        		String artist=PaudioFile.artist;                   //~vae9I~//~va80I~
            	if (artist!=null && !artist.equals(""))            //~vae9R~//~va80I~
            		title=title+" ("+artist+")";                   //~vae9R~//~va80I~
	        	strTitles[num]=title;                       //~vae9I~//~va80R~
	        	strUris[num]=Puri.toString();               //~vae9I~//~va80I~
	    		tvsUserBGMTitle[num].setText(title);               //~vae9I~//~va80I~
	        	UBRG.setEnabled(num,true);                         //~va80I~
            }                                                      //~vae9I~//~va80I~
        }                                                          //~vae9I~//~va80I~
      }                                                            //~va80I~
      catch(Exception e)                                           //~va80I~
      {                                                            //~va80I~
          Dump.println(e,CN+"BGMSeelected");                       //~va80I~
      }                                                            //~va80I~
    }                                                              //~vae9I~//~va80I~
    //**************************************                       //~vae9I~//~va80I~
    public void deleteBGM()                                        //~vae9I~//~va80I~
    {                                                              //~vae9I~//~va80I~
        int num=userBGMButtonNumber;                               //~vae9I~//~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.deleteBGM btnNo="+num+",title="+Utils.toString(strTitles)+",Puri="+strUris);//~vae9I~//~va80R~
        strTitles[num]="";                                  //~vai0I~//~va80R~
	    strUris[num]="";                                    //~vae9I~//~va80R~
	    UBRG.setEnabled(num,false);                                //~va80I~
	    tvsUserBGMTitle[num].setText(NOTITLE);                     //~vae9I~//~va80I~
    }                                                              //~vae9I~//~va80I~
    //**********************************************************************//~va80I~
    private static String getPreference(String Pkey,String Pdefault)//~va80R~
    {                                                              //~va80I~
        String rc=Utils.getPreference(Pkey,Pdefault);              //~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.getPreference key="+Pkey+",rc="+rc);//~va80I~
        return rc;	                                               //~va80I~
    }                                                              //~va80I~
    //**********************************************************************//~va80I~
    private static int getPreference(String Pkey,int Pdefault)     //~va80R~
    {                                                              //~va80I~
        int rc=Utils.getPreference(Pkey,Pdefault);                 //~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.getPreference key="+Pkey+",rc="+rc);//~va80I~
        return rc;                                                 //~va80I~
    }                                                              //~va80I~
    //**********************************************************************//~va80I~
    private void putPreference(String Pkey,String Pvalue)          //~va80I~
    {                                                              //~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.putPreference key="+Pkey+",val="+Pvalue);//~va80I~
        Utils.putPreference(Pkey,Pvalue);                          //~va80I~
    }                                                              //~va80I~
    //**********************************************************************//~va80I~
    private void putPreference(String Pkey,int Pvalue)             //~va80I~
    {                                                              //~va80I~
    	if (Dump.Y) Dump.println("OptionBGM.putPreference key="+Pkey+",val="+Pvalue);//~va80I~
        Utils.putPreference(Pkey,Pvalue);                          //~va80I~
    }                                                              //~va80I~
}//class                                                           //~v@@@R~
