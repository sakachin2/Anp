//CID://+va80R~:          update#=    134                          //~va80R~
//*********************************************************************//~va30I~
//va80 240219 selectable BGM                                       //~va80I~
//va70 230313 set dialog width for portrait only                   //~va70I~
//va69 230305 add support tool update candidate fgrom menu         //~va69I~
//va64 230302 implement stepback                                   //~va63I~
//va63 230302 clear make also by reset                             //~va63R~
//va62 230228 set filename to save                                 //~va62I~
//va61 230228 add function clear before Try!                       //~va61I~
//va60 230228 add function reset and stepback                      //~va60I~
//va59 221104 Num key is not shown on emulator(by kjeyboard hidden on manifest)//~va59I~
//va41:200523 BGM                                                  //~va41I~
//va40:200523 (Adjust Button text base line and size)              //~va40I~
//va30:120717 (NPA21 fontsize depending screen size)               //~va30I~
//*********************************************************************//~va30I~
package np.jnp.npanew;                                              //~va30R~//~va40R~//~va41R~

import np.jnp.npanew.R;                                               //~va30I~//~va41R~
import np.jnp.npanew.utils.AG;                                     //~va41R~
import np.jnp.npanew.utils.Dump;                                      //~va40I~//~va41R~
import np.jnp.npanew.utils.Utils;                                  //~va41R~

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

public class ButtonDlg {         //~5925R~                         //~va23R~

  private static final String CN="ButtonDlg";                      //+va80R~
//  private static final String FONTTYPE="ＭＳ 明朝";              //+va80I~
//  private static final String FONTTYPE="Dialog";                 //~va23R~
//    private static String FONTTYPE=wnp.Sfontnamee;                 //~va23R~
//	private JPanel jContentPane = null;
  	public static final int BUTTON_CTR=5;                          //~v@@@R~
  	public static final int BUTTON_CTR2=7; 	//string array,add back and reset//~va60I~
    private static final int BTNID_MAKE=0;                         //~v@@@I~
    private static final int BTNID_START=1;                        //~v@@@I~
    private static final int BTNID_NEXT=2;                         //~v@@@I~
    private static final int BTNID_ANS=3;                          //~v@@@I~
//  private static final int BTNID_CLEAR=4;                        //~v@@@R~
    private static final int BTNID_RCHK =4;                        //~v@@@I~
    private static final int BTNID_BACK =5;                        //~va60I~
    private static final int BTNID_RESET=6;                        //~va60I~
    private static final int BTNID_BACKON=BTNID_START;             //~va60I~
    private static final int BTNID_RESETON=BTNID_RCHK;             //~va60R~
    private static final int BTNID_STRIDX_NEXT=2;  //"Next" override BtnText:"Clear"//~va61R~//~va62R~//~va63R~//~va64R~
    private static final int BTNID_STRIDX_CLEAR=6;                 //~va62I~//~va63R~//~va64R~
                                                                   //~v@@@I~
  	public static final int BUTTON_STATUS_CTR=3;                   //~v@@@I~
  	public static final int BUTTON_STATUS_DISABLE=0;               //~v@@@I~
  	public static final int BUTTON_STATUS_ENABLE=1;                //~v@@@I~
  	public static final int BUTTON_STATUS_PUSHED=2;                //~v@@@I~
                                                                   //~v@@@I~
	private static final int  COL_BUTTON_ENABLE  =Color.rgb(  0,  0,  0);  //enable//~v@@@I~
	private static final int  COL_BUTTON_DISABLE =Color.rgb( 80, 80, 80);  //disable//~v@@@R~
	private static final int  COL_BUTTON_PUSHED  =Color.rgb(250,100,  0);  //disable//~v@@@R~
	private static final int  COL_BUTTON_BG_OUTLINE =Color.rgb( 50, 50, 80);  //enable//~v@@@R~
	private static final int  COL_BUTTON_BG_ENABLE  =Color.rgb(200,200,200);  //enable//~v@@@R~
	private static final int  COL_BUTTON_BG_DISABLE =Color.rgb(100,100,100);  //disable//~v@@@R~
	private static final int  COL_BUTTON_BG_PUSHED  =Color.rgb( 50,250,200);  //disable//~v@@@R~
                                                                   //~v@@@I~
	private static final int  COL_BTNNUM_BG          =Color.argb(  64,128,10,10);//~v@@@R~
	private static final int  COL_BTNNUM_FG          =Color.argb( 200,40,10,200);//~v@@@R~
	private static final int  COL_BTNNUM_BG_PUSHED   =Color.argb(  64, 50,250,200);//~v@@@I~
	private static final int  COL_BTNNUM_FG_PUSHED   =Color.argb( 255,40,10,255);//~v@@@I~
                                                                   //~v@@@I~
    private static final int BUTTON2D1=2;                          //~v@@@R~
    private static final int BUTTON2D2=4;                          //~v@@@R~
    private static final int BUTTON2D3=1;  //pushed                //~v@@@R~
    private static final int BUTTON2D4=2;  //pushed                //~v@@@I~
                                                                   //~v@@@I~
    public static final int BTN_NUM_CTR=12;  //1-9,0,*(@),#(SYM)   //~v@@@R~
    public static final int BTN_NUM_0=10;                          //~v@@@R~
    public static final int BTN_NUM_SAME=11;                       //~v@@@R~
    public static final int BTN_NUM_MEMO=12;                       //~v@@@R~
                                                                   //~va41I~
    public static final String PKEY_BGM="optionBGM";               //~va41I~
                                                                   //~v@@@I~
                                                                   //~v@@@I~
  	public static int BUTTON_W,BUTTON_H,BUTTON_GAP,BUTTON_WG,BUTTON_POSY1,BUTTON_POSY2;                           //~v@@@I~
//  public static int BUTTON_POSX1,BUTTON_TEXTSZ,BUTTON_TEXTBASE;  //~v@@@R~//~va40R~
    private int BUTTON_POSX1,BUTTON_TEXTSZ,BUTTON_TEXTBASE;        //~va40I~
	private   Paint pButtonPaintTextEnable=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
	private   Paint pButtonPaintTextDisable=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
	private   Paint pButtonPaintTextPushed=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
	private   Paint pButtonPaintOutline=new Paint();						//box//~v@@@R~
	private   Paint pButtonPaintEnable=new Paint();						//box//~v@@@R~
	private   Paint pButtonPaintDisable=new Paint();						//box//~v@@@R~
	private   Paint pButtonPaintPushed=new Paint();                //~v@@@R~
                                                                   //~v@@@I~
	private   Paint pBtnNumPaint=new Paint();						//box//~v@@@I~
	private   Paint pBtnNumPaintPushed=new Paint();						//box//~v@@@I~
	private   Paint pBtnNumPaintText=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
	private   Paint pBtnNumPaintTextPushed=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
                                                                   //~v@@@I~
	private Rect[] pButtonRect;                                    //~v@@@I~
	private String[] pButtonText;                                  //~v@@@I~
	private String[] pButtonTextAll;                               //~va60I~
	private final static String[] strBtnNumText={"1","2","3","4","5","6","7","8","9","0","H","M"};//~v@@@R~
//	private final static String[] strBtnNumText2={"*@","#"};       //~v@@@I~//~va41R~
	private int [] pButtonStatus;                                  //~v@@@I~
	public  int gameStatus;                                        //~va60R~
  	private WnpView pView;                                                    //~5925R~//~v@@@R~
	private Board pBoard;                                                  //~5925R~//~v@@@R~
	public  CPIndex  pPIndex;                                      //~v@@@R~
    private Canvas pDC;                                            //~v@@@I~
    private Dialog  pDlg,pDlgfl;                                   //~v@@@R~
//    private int fileListEntryProcessOption;                      //~v@@@R~
                                      //~v@@@I~
    private AlertDialog pListDlg;                                  //~v@@@I~
    //    Wnpctl  pWnpctl;                                             //~5A04R~
//    int      pswdok;        //to pswd chk                          //~5A04R~//~v@@@R~
      private int intRadioLVL=0;                                   //~v@@@I~
      private static final String strPrefKeyLVL="Level";           //~v@@@I~
      private boolean   sw_RIGID = false;                          //~v@@@R~
      private static final String strPrefKeyRIGID="Rigid";         //~v@@@I~
//    private String strTextSEED ="";                              //~v@@@R~
      private String strTextMAXSAVE ="";                           //~v@@@I~
      private static final int MIN_MAXSAVE=10;                     //~v@@@I~
      private static final int DEF_MAXSAVE=20;                     //~v@@@I~
      private int    intMaxsave=DEF_MAXSAVE;                       //~v@@@R~
//    private static final String strPrefKeySEED="Seed";           //~v@@@R~
      private static final String strPrefKeyMAXSAVE="Maxsave";     //~v@@@I~
      private String strTextTIMEOUT ="";                           //~v@@@R~
      private static final String strPrefKeyTIMEOUT="Timeout";     //~v@@@I~
      public static String strTextUID ="You";                      //~v@@@R~
      private int intRadioDEN=0;                                   //~v@@@R~
      private static final String strPrefKeyDEN="Dencity";         //~v@@@I~
//	  private int checkctlerr=0;                                     //~5A13I~//~v@@@R~
      private boolean sw_NoRedundant = false;                      //~v@@@R~
      public  static boolean sw_DispNumButton = false;             //~v@@@R~
      private static final String strPrefKeyNoRedundant="NoRedundant";//~v@@@I~
      private static final String strPrefKeyDispNumButton="DispNumButton";//~v@@@I~
      private int subMenuId;                                       //~v@@@I~
      private int textbase_BTN_NUM;                                //~v@@@I~
      private int pushedNumBtn=0;                                  //~v@@@R~
      public static int optBGM=-1;                                 //~va41R~
      private boolean swClear;                                     //~va61I~//~va63R~//~va64R~
      private boolean swReset;                                     //~va63I~
      private boolean swResetComplete;                             //~va63I~
	/**
	 * This is the default constructor
	 */
//	public ButtonDlg() {
//		super();
//		initialize();
//	}
//**Constructor                                                    //~va41I~
	public ButtonDlg(WnpView PView,Board PBoard){
//  	super();                                                   //~v@@@R~
                                                                   //~5925I~
//        pswdok=0;                                                  //~5A04R~//~v@@@R~
		pView=PView;                                               //~5925R~
		pBoard=PBoard;                                             //~5925R~
		pPIndex=pBoard.pPIndex;                                    //~v@@@I~
        initialize(true);                                              //~5925M~//~v@@@R~
//		setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);//~5A05I~
                                                                   //~5925I~
		optionReadWrite(0/*read*/);                                //~v@@@I~
    	CPIndex.MAXHISTR=intMaxsave;                               //~v@@@I~
    	CPIndex.MAXLEVEL=intMaxsave;                               //~v@@@I~
    	CPIndex.MAXSCORE=intMaxsave;                               //~v@@@I~
  		InitBtnStatus();                                           //~5925I~//~v@@@R~
        AG.aButtonDlg=this;                                        //~va80I~
	}
    //*********************************************                //~v@@@R~
	public  void initialize(boolean Pswinitdlg)                        //~v@@@R~//~va41R~
	{                                                              //~v@@@I~
    	int ii;                                                    //~v@@@I~
    //*****************                                            //~v@@@I~
//		this.setSize(170, 430);
//		this.setPreferredSize(new java.awt.Dimension(156,420));
//		this.setResizable(false);
//      if (wnp.Sjlang)                                              //~va23R~
//		this.setTitle("ナンプレＤＬＧ");                    //~va30R~
//      else                                                         //~va23R~
//		this.setTitle("NP Dialog");                                //~va23R~
//		this.setVisible(true);
//		this.setContentPane(getJContentPane());
		BUTTON_GAP=Wnp.LAYOUT_HGAP;                                //~v@@@M~
//  	BUTTON_W=((Wnp.FRAME_W-Wnp.BOARD_LEFT*2)+BUTTON_GAP)/BUTTON_CTR-BUTTON_GAP;//~v@@@M~
    	BUTTON_W=((Wnp.BUTTONS_W-Wnp.BOARD_LEFT*2)+BUTTON_GAP)/BUTTON_CTR-BUTTON_GAP;//~v@@@M~
                                                                   //~v@@@M~
//  	BUTTON_POSX1=Wnp.BOARD_LEFT;                               //~v@@@M~
    	BUTTON_POSX1=Wnp.L_BUTTONS_X;                              //~v@@@M~
		BUTTON_WG=(BUTTON_W+BUTTON_GAP);	//for button offset calc//~v@@@M~
		BUTTON_H=Wnp.BTN0BOX_H;                                    //~v@@@M~
		BUTTON_POSY1=Wnp.L_BUTTONS_Y;                              //~v@@@M~
		BUTTON_POSY2=BUTTON_POSY1+BUTTON_H;                        //~v@@@M~
		BUTTON_TEXTSZ=BUTTON_H-14;                                 //~v@@@M~
//  	BUTTON_TEXTBASE=BUTTON_H-10;                               //~v@@@M~//~va40R~
//*init buttons                                                    //~v@@@I~
      if (Pswinitdlg)                                              //~v@@@I~
      {                                                            //~v@@@I~
        pButtonStatus=new int[BUTTON_CTR];                         //~v@@@I~
        pButtonRect=new Rect[BUTTON_CTR];                          //~v@@@I~
      }                                                            //~v@@@I~
        for (ii=0;ii<BUTTON_CTR;ii++)                              //~v@@@I~
        {   
          if (Pswinitdlg)                                          //~v@@@I~
          {                                                        //~v@@@I~
        	pButtonRect[ii]=new Rect();//~v@@@I~
            pButtonStatus[ii]=0;        //disabled                 //~v@@@I~
          }                                                        //~v@@@I~
        	ButtonToScreen(ii,pButtonRect[ii]);                    //~v@@@I~
        }                                                          //~v@@@I~
//      pButtonText=WnpView.contextR.getStringArray(R.array.ButtonText);//~v@@@R~//~va60R~
        pButtonTextAll=WnpView.contextR.getStringArray(R.array.ButtonText);//~va60I~
        pButtonText=new String[BUTTON_CTR];                        //~va60I~
        for (ii=0;ii<BUTTON_CTR;ii++)                          //~va60I~
            pButtonText[ii]=new String(pButtonTextAll[ii]);        //~va60I~
        BUTTON_TEXTSZ=adustButtonText(BUTTON_TEXTSZ);              //~va30M~
    	pButtonPaintOutline.setColor(COL_BUTTON_BG_OUTLINE);   //outer line//~v@@@I~
    	pButtonPaintEnable.setColor(COL_BUTTON_BG_ENABLE);   //outer line//~v@@@I~
    	pButtonPaintDisable.setColor(COL_BUTTON_BG_DISABLE);   //outer line//~v@@@I~
    	pButtonPaintPushed.setColor(COL_BUTTON_BG_PUSHED);   //outer line//~v@@@I~
                                                                   //~v@@@I~
        pButtonPaintTextEnable.setTextSize(BUTTON_TEXTSZ);         //~v@@@I~
        pButtonPaintTextDisable.setTextSize(BUTTON_TEXTSZ);        //~v@@@I~
        pButtonPaintTextPushed.setTextSize(BUTTON_TEXTSZ);         //~v@@@I~
        pButtonPaintTextEnable.setColor(COL_BUTTON_ENABLE);        //~v@@@I~
        pButtonPaintTextDisable.setColor(COL_BUTTON_DISABLE);      //~v@@@R~
        pButtonPaintTextPushed.setColor(COL_BUTTON_PUSHED);        //~v@@@I~
                                                                   //~v@@@I~
    	pBtnNumPaint.setColor(COL_BTNNUM_BG);                      //~v@@@R~
    	pBtnNumPaintPushed.setColor(COL_BTNNUM_BG_PUSHED);         //~v@@@I~
        pBtnNumPaintText.setColor(COL_BTNNUM_FG);                  //~v@@@I~
        pBtnNumPaintText.setTextSize(Wnp.BTN_NUM_H);               //~v@@@I~
        pBtnNumPaintText.setTypeface(Wnp.Sfontstyle_BTN_NUM);      //~v@@@I~
        pBtnNumPaintTextPushed.setColor(COL_BTNNUM_FG_PUSHED);     //~v@@@M~
        pBtnNumPaintTextPushed.setTextSize(Wnp.BTN_NUM_H);         //~v@@@I~
        pBtnNumPaintTextPushed.setTypeface(Wnp.Sfontstyle_BTN_NUM);//~v@@@I~
//  	textbase_BTN_NUM=Wnp.BTN_NUM_H-(int)Math.ceil(pBtnNumPaintText.getFontMetrics().descent/2);//~v@@@R~//~va41R~
      if (Pswinitdlg)                                              //~v@@@M~
      {                                                            //~v@@@M~
//      	sw_DispNumButton=(WnpView.Sconfig_keyboard==Configuration.KEYBOARD_NOKEYS);	//display num button if no keyboard//~v@@@I~//~va59R~
        	sw_DispNumButton=true;                                 //~va59I~
    	if (Dump.Y) Dump.println("ButtonDlg.initialize Sconfig_keyboard="+WnpView.Sconfig_keyboard+",Configuration.KEYBOARD_NOKEYS="+Configuration.KEYBOARD_NOKEYS);//~va41I~
        String msg=WnpView.contextR.getString(R.string.InfoHelp);  //~v@@@I~
//      Toast.makeText(WnpView.context,msg,Toast.LENGTH_SHORT).show();//~v@@@I~//~va41R~
        Toast.makeText(WnpView.context,msg,Toast.LENGTH_LONG).show();//~va41I~
      }                                                            //~v@@@I~
	}//initialize                                                  //~v@@@R~
//===============================================================================//~va30I~
int adustButtonText(int Psz)                                       //~va30I~
{                                                                  //~va30I~
	float flenmax=0.0f;                                            //~va30I~
    String strmax=null;                                                 //~va30I~
    int sz;                                                        //~va30I~
//*******************                                              //~va30I~
	Paint paint=new Paint();						//box          //~va30I~
    paint.setTextSize(Psz);                                        //~va30I~
//	for (int ii=0;ii<BUTTON_CTR;ii++)                              //~va30I~//~va60R~
  	for (int ii=0;ii<BUTTON_CTR2;ii++)                             //~va60I~
    {                                                              //~va30I~
//		String str=pButtonText[ii];                                //~va30I~//~va60R~
  		String str=pButtonTextAll[ii];                             //~va60I~
    	float flen=paint.measureText(str,0,str.length());         //~va30I~
        if (flen>flenmax)                                          //~va30I~
        {                                                          //~va30I~
        	flenmax=flen;                                          //~va30I~
            strmax=str;                                            //~va30I~
        }                                                          //~va30I~
    }                                                              //~va30I~
//  int width=BUTTON_W-1;                                              //~va30I~//~va41R~
    int width=BUTTON_W-BUTTON2D1*2-BUTTON2D2;                      //~va41R~
    sz=WnpView.adjustTextsz(1,paint,width,strmax);                 //~va30I~
//  int descent=(int)paint.getFontMetrics().descent;               //~va40I~//~va41R~
//	BUTTON_TEXTBASE=BUTTON_H-descent;                              //~va40I~//~va41R~
  	BUTTON_TEXTBASE=getTextBaseLine(paint,BUTTON_H);               //~va41I~
    if (Dump.Y) Dump.println("ButtonDlg.adjustButtonText BUTTON_TEXTBASE="+BUTTON_TEXTBASE);//~va41I~
    return sz;                                                     //~va30I~
}//adustButtonText                                                 //~va30I~
//===============================================================================//~va41I~
public static int getTextBaseLine(Paint Ppaint,int PlineHeight)    //~va41R~
{                                                                  //~va41I~
    int descent=(int)Ppaint.getFontMetrics().descent;              //~va41I~
    int ascent=-(int)Ppaint.getFontMetrics().ascent;               //~va41I~
    double rate=(double)PlineHeight/(descent+ascent);              //~va41R~
    int base=(int)(ascent*rate);                                   //~va41R~
    if (Dump.Y) Dump.println("ButtonDlg.getTextBaseLine ascent="+ascent+",descent="+descent+",rate="+rate+",lineH="+PlineHeight+",base="+base);//~va41R~
    return base;                                                    //~va41I~
}                                                                  //~va41I~
//===============================================================================//~v@@@I~
//=button position                                                 //~v@@@I~
//===============================================================================//~v@@@I~
public void orientationChanged()                                   //~v@@@R~
{                                                                  //~v@@@I~
	initialize(false);                                              //~v@@@I~
}//orientationChanged                                              //~v@@@I~
//===============================================================================//~v@@@I~
//=button position                                                 //~v@@@I~
//===============================================================================//~v@@@I~
private void ButtonToScreen(int bx,Rect Prect)                     //~v@@@R~
{                                                                  //~v@@@I~
    Prect.left=BUTTON_POSX1+bx*BUTTON_WG;                          //~v@@@R~
    Prect.right=Prect.left+BUTTON_W-1;                             //~v@@@I~
    Prect.top=BUTTON_POSY1;                                        //~v@@@I~
    Prect.bottom=Prect.top+BUTTON_H-1;                             //~v@@@I~
}                                                                  //~v@@@I~
//===============================================================================*///~v@@@I~
//=detect button push                                              //~v@@@I~
//=return :button#,-1 if out of range                              //~v@@@I~
//===============================================================================*///~v@@@I~
private int ScreenToButton(int wx,int wy)                          //~v@@@R~
{                                                                  //~v@@@I~
	int ii,x,xx;                                                //~v@@@I~
//***********************	                                       //~v@@@I~
    x=(wx - BUTTON_POSX1);                                         //~v@@@I~
                                  //~v@@@I~
    if ( x < 0 || wy < BUTTON_POSY1 || wy >= BUTTON_POSY2 )        //~v@@@R~
        return -1;                                                 //~v@@@I~
    for (ii=0,xx=0;ii<BUTTON_CTR;ii++,xx+=BUTTON_WG)               //~v@@@I~
    {                                                              //~v@@@I~
        if (x>=xx && x<xx+BUTTON_W)                                //~v@@@R~
        	break;                                                 //~v@@@I~
    }                                                              //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.ScreenToButton ii="+ii);   //~va62I~
    if (ii>=BUTTON_CTR)                                            //~v@@@I~
        return -1;                                                 //~v@@@I~
    return ii;                                                     //~v@@@I~
}                                                                  //~v@@@I~
//=======================================================================*///~5925I~
public void	OnMake()                                           //~5925I~//~va23R~
{                                                                  //~5925I~
//    int pswdrc;                                                    //~5A05I~
//********************                                             //~5A05I~
//    pswdok=1;  //@@@@                                                  //~va16R~
//    if (pswdok==0) //pswd valid chk not yet                        //~5A04R~
//    {                                                              //~5A04R~
//        pWnpctl=new Wnpctl(this);                                //~5A04R~
//        pswdrc=pWnpctl.CheckCtl();	                               //~5A13R~
//        if (pswdrc==0)                                             //~5A13R~
//		    pswdok=1;                                              //~5A13M~
//        else                                                       //~5A13I~
//        {                                                          //~5A13I~
//			if (checkctlerr!=0)	//2nd time                         //~5A13I~
//	            return;		//not generate                         //~5A13R~
//			checkctlerr++;                                         //~5A13I~
//        }                                                          //~5A13I~
//    }                                                              //~5A04R~
	if (Dump.Y) Dump.println("ButtonDlg.onMake");                  //~va60I~
    resetButton(true/*afterMake}*/);                                            //~va60I~//~va63R~
    pView.OnMake();                                   //~5925R~
    OnGo();                                                        //~5925I~
    swClear=true;                                                  //~va61I~//~va63R~//~va64R~
    pButtonText[BTNID_NEXT]=pButtonTextAll[BTNID_STRIDX_CLEAR];//"Clear"//~va61I~//~va62R~//~va63R~//~va64R~
	if (Dump.Y) Dump.println("ButtonDlg.onMake ButtonText[NEXT]="+pButtonText[BTNID_NEXT]);//~va62I~
}                                                                  //~5925I~
void	OnGo()                                             //~5925I~
{                                                                  //~5925I~
	String str=new String("");                                                  //~5925I~
    int ival=0;                                                    //~va01I~
//*******************                                              //~5925I~
    pBoard.Flags&=~Board.F_ONXX;                                   //~5925R~
    pBoard.Flags|=Board.F_ONGO;                                    //~5925R~
    if ((pBoard.Flags&Board.F_MODEMAKE)!=0)                        //~5925R~
    {                                                              //~5925I~
                                                                   //~5925I~
//        if (Check_RIGID.isSelected())                               //~5925R~//~v@@@R~
          if (sw_RIGID)                                            //~v@@@I~
  	    	pBoard.Flags|=Board.F_RIGID;                           //~5925R~//~v@@@R~
          else                                                       //~5925I~//~v@@@R~
  	    	pBoard.Flags&=~Board.F_RIGID;                          //~5925R~//~v@@@R~
//        if (NoRedundant.isSelected())                              //~va16R~//~v@@@R~
          if (sw_NoRedundant)                                      //~v@@@I~
  	    	pBoard.Flags|=Board.F_NOREDUNDANT;                     //~va16R~//~v@@@R~
          else                                                       //~va16R~//~v@@@R~
  	    	pBoard.Flags&=~Board.F_NOREDUNDANT;                    //~va16R~//~v@@@R~
                                                                   //~5925I~
    	pBoard.Flags&=~Board.F_LVLMASK;                            //~5925R~
//        if (RadioLVL0.isSelected())                                //~5925R~//~v@@@R~
          if (intRadioLVL==0)                                      //~v@@@R~
  	    	pBoard.Flags|=Board.F_LVL0;                            //~5925R~//~v@@@R~
          else                                                       //~5925I~//~v@@@R~
//        if (RadioLVL1.isSelected())                                //~5925R~
          if (intRadioLVL==1)                                      //~v@@@R~
  	    	pBoard.Flags|=Board.F_LVL1;                            //~5925R~//~v@@@R~
          else                                                       //~5925I~//~v@@@R~
//        if (RadioLVL2.isSelected())                                //~5925I~
          if (intRadioLVL==2)                                      //~v@@@R~
  	    	pBoard.Flags|=Board.F_LVL2;                            //~5925R~//~v@@@R~
          else                                                       //~5925I~//~v@@@R~
//        if (RadioLVL3.isSelected())                                //~5925I~//~v@@@R~
          if (intRadioLVL==3)                                      //~v@@@R~
  	    	pBoard.Flags|=Board.F_LVL3;                            //~5925R~//~v@@@R~
          else                                                       //~5925I~//~v@@@R~
  	    	pBoard.Flags|=Board.F_LVL0;  //default                 //~5925R~//~v@@@R~
                                                                   //~5925I~
          pBoard.Flags&=~Board.F_DENMASK;                          //~va03R~//~v@@@R~
//        if (RadioDEN0.isSelected())                              //~va03R~//~v@@@R~
          if (intRadioDEN==0)                                      //~v@@@R~
              pBoard.Flags|=Board.F_DEN0;                          //~va03R~//~v@@@R~
          else                                                     //~va03R~//~v@@@R~
//        if (RadioDEN3.isSelected())                              //~va03R~//~v@@@R~
          if (intRadioDEN==3)                                      //~v@@@R~
              pBoard.Flags|=Board.F_DEN3;                          //~va03R~//~v@@@R~
          else                                                     //~va03R~//~v@@@R~
//        if (RadioDEN4.isSelected())                              //~va03R~
          if (intRadioDEN==4)                                      //~v@@@R~
              pBoard.Flags|=Board.F_DEN4;                          //~va03R~//~v@@@R~
          else                                                     //~va03R~//~v@@@R~
//        if (RadioDEN5.isSelected())                              //~va03R~//~v@@@R~
          if (intRadioDEN==5)                                      //~v@@@R~
              pBoard.Flags|=Board.F_DEN5;                          //~va03R~//~v@@@R~
          else                                                     //~va03R~//~v@@@R~
              pBoard.Flags|=Board.F_DEN0;  //default               //~va03R~//~v@@@R~
//        str=TextDencity.getText();                                 //~va03I~
        ival=0;                                                    //~va03R~
        if (!str.equals(""))                                       //~va03I~
			ival=Integer.parseInt(str);                            //~va03I~
        if (ival==0)                                               //~va03I~
	        ival=xnpsub2.SETMAX;                                   //~va03I~
        pBoard.Dencity=ival;                                       //~va03I~
                                                                   //~5925I~
//      str=TextSEED.getText();                             //~5925R~//~v@@@R~
//      str=strTextSEED;                                           //~v@@@R~
        ival=0;	                                                   //~5928I~
//        if (!str.equals(""))                                       //~5928I~//~v@@@R~
//            try{                                                   //~5928I~//~v@@@R~
//                ival=Integer.parseInt(str);                        //~5928I~//~v@@@R~
//            }                                                      //~5928I~//~v@@@R~
//            catch(NumberFormatException e)                          //~5928I~//~v@@@R~
//            {                                                      //~5928I~//~v@@@R~
////            if (wnp.Sjlang)                                      //~va23R~//~v@@@R~
////              JOptionPane.showMessageDialog(null,"問題番号(乱数の種)が数値でない","NP", JOptionPane.ERROR_MESSAGE);//~5930R~//~v@@@R~//~va30R~
////              else                                                 //~va23R~//~v@@@R~
////              JOptionPane.showMessageDialog(null,"Prob No(seed of rand) numeric error","NP", JOptionPane.ERROR_MESSAGE);//~va23R~//~v@@@R~
//                return;                                            //~5928R~//~v@@@R~
//            }                                                      //~5928I~//~v@@@R~
        pBoard.Seed=ival;                                          //~5928R~
    }                                                              //~5925I~
    gettimeout();                                                  //~va01R~
	PostMessage(Board.IDC_GO);                                     //~5925R~
	GiveFocus();                                                   //~5925I~
}//OnGO                                                            //~5925I~//~v@@@R~
private void gettimeout()                                          //~va01I~
{                                                                  //~va01I~
    int ival=0;                                                    //~va01I~
	String str =new String("");                                                    //~va01I~
//*************************                                        //~va01I~
//    str=TextTIMEOUT.getText();                                     //~va01M~//~v@@@R~
      str=strTextTIMEOUT;                                          //~v@@@I~
    if (!str.equals("")){                                          //~va01M~
        try{                                                       //~va01M~
            ival=Integer.parseInt(str);                            //~va01M~
        }                                                          //~va01M~
        catch(NumberFormatException e)                             //~va01M~
        {                                                          //~va01M~
//          if (wnp.Sjlang)                                          //~va23R~
//            JOptionPane.showMessageDialog(null,"制限時間が数値でない","NP", JOptionPane.ERROR_MESSAGE);//~va01M~//~va30R~
//          else                                                     //~va23R~
//            JOptionPane.showMessageDialog(null,"Time Limit numeric error","NP", JOptionPane.ERROR_MESSAGE);//~va23R~
            return;                                                //~va01M~
        }                                                          //~va01M~
    }                                                              //~va01M~
    pBoard.Timeout=ival;                                           //~va01M~
}                                                                  //~va01I~
//=======================================================================//~5925I~
public  void	OnStop()                                           //~5925I~//~va23R~
{                                                                  //~5925I~
	PostMessage(Board.IDC_STOP);                                   //~5925R~
	GiveFocus();                                                   //~5925I~
}                                                                  //~5925I~
public  void    OnRchk()                                           //~5925I~//~v@@@R~
{                                                                  //~5925I~//~v@@@R~
    pBoard.Flags&=~Board.F_ONXX;                                   //~5925R~//~v@@@R~
    pBoard.Flags|=Board.F_ONRCHK;                                  //~5925R~//~v@@@R~
    gettimeout();                                                  //~va01I~//~v@@@R~
    PostMessage(Board.IDC_RCHK);                                   //~5925R~//~v@@@R~
    GiveFocus();                                                   //~5925I~//~v@@@R~
}                                                                  //~5925I~//~v@@@R~
public  void	OnNext()                                           //~5925I~//~va23R~
{                                                                  //~5925I~
	if (Dump.Y) Dump.println("ButtonDlg.OnNext flags="+Integer.toHexString(pBoard.Flags));//~va60I~
    pBoard.Flags&=~Board.F_ONXX;                                   //~5925R~
    pBoard.Flags|=Board.F_ONNEXT;                                  //~5925R~
    gettimeout();                                                  //~va01I~
	PostMessage(Board.IDC_NEXT);                                   //~5925R~
	GiveFocus();                                                   //~5925I~
}                                                                  //~5925I~
public  void	OnClear()                                          //~5925I~//~va23R~
{                                                                  //~5925I~
	if (Dump.Y) Dump.println("ButtonDlg.OnClear flags="+Integer.toHexString(pBoard.Flags));//~va62I~
    pBoard.Flags&=~(Board.F_ONXX-Board.F_ONTRY);                   //~5925R~
	PostMessage(Board.IDC_CLEAR);                                  //~5925R~
	GiveFocus();                                                   //~5925I~
}                                                                  //~5925I~
//**************************************************************   //~va60M~
//*back toat start                                                 //~va60M~
//*if memo exist, reset by twice push                              //~va60M~
//**************************************************************   //~va60M~
void	OnReset()                                                  //~va60I~
{                                                                  //~va60M~
	if (Dump.Y) Dump.println("ButtonDlg.OnReset");                 //~va64I~
	PostMessage(Board.IDC_RESET);                                  //~va60I~
	GiveFocus();                                                   //~va60I~
}//OnReset                                                         //~va60I~
//**************************************************************   //~va60I~
//*ctrInput=0 & ctrMemo=0                                          //~va64I~
//**************************************************************   //~va64I~
void	resetCompleted(boolean PswAlreadyReset)                    //~va60I~
{                                                                  //~va60I~
//    if (Dump.Y) Dump.println("ButtonDlg.resetComplete mode="+pBoard.GetMode()+",swAlreadyReset="+PswAlreadyReset+",swResetComplete="+swResetComplete);//~va63R~//~va64R~
//    if (PswAlreadyReset)                                            //~va60I~//~va64R~
//    {                                                              //~va60I~//~va64R~
//        pView.OnResetCompleted();                                  //~va60R~//~va64R~
//        if (swResetComplete)                                       //~va63I~//~va64R~
//        {                                                        //~va64R~
//            resetButton(true/*afterMake*/);                      //~va64R~
//            OnClear();  //clear Question                           //~va63I~//~va64R~
//            WnpView.aBoard.resetCompleted(true/*clearQuestion*/);//~va64R~
//        }                                                        //~va64R~
//        else                                                       //~va63I~//~va64R~
//        {                                                        //~va64R~
//            resetButton(false/*afterMake*/);                                             //~va60I~//~va63R~//~va64R~
//            swResetComplete=true;                                  //~va63I~//~va64R~
//            WnpView.aBoard.resetCompleted(false);                //~va64R~
//        }                                                        //~va64R~
//    }                                                              //~va60I~//~va64R~
    if (Dump.Y) Dump.println("ButtonDlg.resetComplete mode="+pBoard.GetMode());//~va64I~
    resetButton(true/*afterMake*/);                                //~va64I~
    OnClear();  //clear Question                                   //~va64I~
}//resetCompleted                                                  //~va64R~
//**************************************************************   //~va60I~
private void resetButton(boolean PswAfterMake)                     //~va60I~//~va63R~
{                                                                  //~va60I~
	if (Dump.Y) Dump.println("ButtonDlg.resetButton entry swAfterMake="+PswAfterMake+",gameStatus="+gameStatus+",swReset="+swReset);//~va64R~
    gameStatus=0;                                                  //~va60I~
    pButtonText[BTNID_BACKON]=pButtonTextAll[BTNID_START];         //~va60I~
    if (PswAfterMake)                                              //~va63I~
    {                                                              //~va63I~
		swReset=false;  	//reset button inactive,RCHK active    //~va63I~//~va64I~
	    pButtonText[BTNID_RESETON]=pButtonTextAll[BTNID_RCHK];         //~va60I~//~va63I~
        pButtonText[BTNID_NEXT]=pButtonTextAll[BTNID_STRIDX_CLEAR];//"Clear"//~va62I~//~va63R~//~va64I~
		swClear=true;                                                  //~va62I~//~va63R~//~va64I~
    }                                                              //~va63I~
	if (Dump.Y) Dump.println("ButtonDlg.resetButton ButtonText[BACKON]="+pButtonText[BTNID_BACKON]);//~va62I~
	if (Dump.Y) Dump.println("ButtonDlg.resetButton ButtonText[RESETON]="+pButtonText[BTNID_RESETON]);//~va62I~
	if (Dump.Y) Dump.println("ButtonDlg.resetButton ButtonText[NEXT]="+pButtonText[BTNID_NEXT]);//~va62I~
	if (Dump.Y) Dump.println("ButtonDlg.resetButton gameStatus="+gameStatus);//~va62I~//~va63R~
}//OnReset                                                         //~va60I~
//**************************************************************   //~va60I~
void	OnBack()                                                   //~va60I~
{                                                                  //~va60I~
	if (Dump.Y) Dump.println("ButtonDlg.OnBack");                  //~va64I~
	PostMessage(Board.IDC_BACK);                                   //~va64R~
}//OnReset                                                         //~va60I~
//**************************************************************   //~va60I~
private void	OnRestore()                                        //~5925I~
{                                                                  //~5925I~
    pBoard.Flags&=~Board.F_ONXX;                                   //~5925R~
	PostMessage(Board.IDC_RESTORE);                                //~5925R~
	GiveFocus();                                                   //~5925I~
}                                                                  //~5925I~
//**************************************************************   //~va64I~
public void	boardCreated()                                         //~va64R~
{                                                                  //~va64I~
	if (Dump.Y) Dump.println("ButtonDlg.boardCReated mode="+pBoard.GetMode()+",flag="+Integer.toHexString(pBoard.Flags));//~va64R~
    swReset=false;                                                 //~va64M~
    swClear=false;                                                 //~va64I~
    swResetComplete=false;                                         //~va64I~
	if (pBoard.GetMode()==Board.MODE_KEYINANS)                       //~va64R~
		setButtonBack();                                           //~va64R~
	if (Dump.Y) Dump.println("ButtonDlg.boardCReated exit mode="+pBoard.GetMode());//~va64I~
}                                                                  //~va64I~
public  void	OnSetend()                                         //~5925I~//~va23R~
{                                                                  //~5925I~
    pBoard.Flags&=~Board.F_ONXX;                                   //~5925R~
    pBoard.Flags|=Board.F_ONTRY;                                   //~5925R~
	PostMessage(Board.IDC_SETEND);                                 //~5925R~
	GiveFocus();                                                   //~5925I~
//  AG.aBGMList.play(SOUNDID_BGM_THINKING);                        //~va40I~//~va64R~
	setButtonBack();                                               //~va62I~
    swClear=false;                                                 //~va61I~//~va63R~//~va64R~
    pButtonText[BTNID_NEXT]=pButtonTextAll[BTNID_STRIDX_NEXT];	//set "Next"//~va61I~//~va62R~//~va63R~//~va64R~
	if (Dump.Y) Dump.println("ButtonDlg.OnSetend ButtonText[NEXT]="+pButtonText[BTNID_NEXT]);//~va62I~
}                                                                  //~5925I~
public void setButtonBack()                                      //~va62I~//~va64R~
{                                                                  //~va62I~
    gameStatus=BTNID_START;                                        //~va62I~
    swReset=true;  	//reset button active                          //~va63I~
    pButtonText[BTNID_NEXT]=pButtonTextAll[BTNID_NEXT];          //~va62I~//~va64R~
    pButtonText[BTNID_BACKON]=pButtonTextAll[BTNID_BACK];          //~va64I~
    pButtonText[BTNID_RESETON]=pButtonTextAll[BTNID_RESET];        //~va62I~
    AG.aBGMList.play(SOUNDID_BGM_THINKING);                        //~va64I~
	if (Dump.Y) Dump.println("ButtonDlg.setButtonBack ButtonText[NEXT]="+pButtonText[BTNID_NEXT]);//~va62I~//~va64R~
	if (Dump.Y) Dump.println("ButtonDlg.setButtonBack ButtonText[BACKON]="+pButtonText[BTNID_BACKON]);//~va64I~
	if (Dump.Y) Dump.println("ButtonDlg.setButtonBack ButtonText[RESETON]="+pButtonText[BTNID_RESETON]);//~va62I~
}                                                                  //~va62I~
//private void    OnSort()                                           //~5925I~//~v@@@R~
//{                                                                  //~5925I~//~v@@@R~
//    PostMessage(Board.IDC_SORT);                                   //~5925R~//~v@@@R~
//    GiveFocus();                                                   //~5925I~//~v@@@R~
//}                                                                  //~5925I~//~v@@@R~
//=======================================================================//~5925I~
//        ButtonDlg::SetDlgBtn                                       //~5925I~//~va30R~//~va41R~
//=======================================================================//~5925I~
public int SetDlgBtn()                                            //~5925I~
{                                                                  //~5925I~
    boolean enable,rchkenable,onmake,numon,setend,subthread,nogoing;  //~5925I~//~v@@@R~
                                                                   //~5925I~
    int mode=pBoard.GetMode();                                     //~5925R~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn Mode="+mode+",swReset="+swReset+",swClear="+swClear+",gameStatus="+gameStatus);//~va64I~
    numon=(pBoard.pPat!=null && pBoard.pPat.ChkData());                  //~5925R~
    subthread=((pBoard.Flags & Board.F_THREAD)!=0);                //~5925R~
    onmake=((pBoard.Flags & Board.F_MODEMAKE)!=0);                 //~5925R~
    nogoing=!(subthread || (pBoard.Flags & Board.F_ONGO)!=0);//thread or go timer//~5925R~
    int ctrInput=pBoard.getCtrInput();                             //~va62I~
    boolean swFirstAnswer=mode==Board.MODE_KEYINANS && ctrInput==1;       //~va62I~
    if (swFirstAnswer)                                             //~va62I~
    {                                                              //~va62I~
		setButtonBack();                                           //~va62I~
    }                                                              //~va62I~
                                                                   //~5925I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn flags="+Integer.toHexString(pBoard.Flags)+",mode="+mode);//~va60I~//~va64R~
    enable=false;                                                      //~5925I~
//    BtnANSWER.setEnabled(enable);//dummy                           //~5925R~
//  enable=nogoing;                                                //~5925I~//~v@@@R~
//    enable=nogoing && ((pBoard.Flags & Board.F_ONTRY)==0);//disable after onTry//~v@@@I~//~va60R~
    enable=false;                                                  //~va60I~
    if (nogoing)                                                   //~va60I~
		if (((pBoard.Flags & Board.F_ONTRY)==0) //disable after onTry//~va60I~
        ||  (ctrInput==0 && pBoard.getCtrMemo()==0)    //~va60R~   //~va62R~
        )                                                          //~va60I~
        	enable=true;                                           //~va60I~
//    BtnMAKE.setEnabled(enable);                                    //~5925R~
	pButtonStatus[BTNID_MAKE]=(enable?1:0);                              //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable MAKE="+pButtonStatus[BTNID_MAKE]);//~va62I~//~va64R~
                                                                   //~5925I~
                                                                   //~5925I~
//    RadioLVL0.setEnabled(nogoing);                                 //~5925I~
//    RadioLVL1.setEnabled(nogoing);                                 //~5925I~
//    RadioLVL2.setEnabled(nogoing);                                 //~5925I~
//    RadioLVL3.setEnabled(nogoing);                                 //~5925I~
//    RadioDEN0.setEnabled(nogoing);                               //~va03R~
//    RadioDEN3.setEnabled(nogoing);                               //~va03R~
//    RadioDEN4.setEnabled(nogoing);                               //~va03R~
//    RadioDEN5.setEnabled(nogoing);                               //~va03R~
//    TextSEED.setEnabled(nogoing);                                  //~5925I~
                                                                   //~5925I~
//    Check_RIGID.setEnabled(nogoing);                               //~5925R~
                                                                   //~va16I~
//	if (wnp.BOARDTYPE==wnp.BOARDTYPE3)                             //~va16I~
//    {                                                              //~va16I~
//        NoRedundant.setSelected(true);                                 //~va16I~
//	    NoRedundant.setEnabled(false);                             //~va16I~
//    }                                                              //~va16I~
//    else                                                           //~va16I~
//	    NoRedundant.setEnabled(nogoing);                           //~va16R~
                                                                   //~5925I~
//    BtnSTOP.setEnabled(subthread);                                //~5925R~
                                                                   //~5925I~
//  enable=(numon && nogoing);                                     //~v030R~
    enable=((numon||onmake) && nogoing);                           //~v030R~
//System.out.println("enable clear numon="+numon+",onmake="+onmake+",enable="+enable);//~v030R~
//    BtnCLEAR.setEnabled(enable);                                   //~5925R~
//  pButtonStatus[BTNID_CLEAR]=(enable?1:0);                       //~v@@@R~
                                                                   //~5925I~
    enable=(nogoing && pBoard.pPat!=null && (pBoard.pPat.Name!="新規")); //~5925R~//~va30R~
//    BtnRESTORE.setEnabled(enable);                             //~5925R~
                                                                   //~5925I~
    setend=(!onmake && mode<Board.MODE_ENDQDATA && numon && !subthread); //~5925I~
//    BtnSETEND.setEnabled(setend);	//I'll try button              //~5925R~
  if (swReset)	                                                   //~va64I~
	pButtonStatus[BTNID_START]=pBoard.isBackAvailable()?1:0;           //~va64I~
  else                                                             //~va64I~
  if (!setend && gameStatus==BTNID_START)                          //~va60R~
	pButtonStatus[BTNID_START]=1;                                  //~va60I~
  else                                                             //~va62I~
  if (mode==Board.MODE_KEYINANS)                                   //~va62I~
	pButtonStatus[BTNID_START]=1;                                  //~va62I~
  else                                                             //~va60I~
	pButtonStatus[BTNID_START]=(setend?1:0);                            //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable START="+pButtonStatus[BTNID_START]+",swReset="+swReset+",numon="+numon+",setend="+setend+",gameStatus="+gameStatus);//~va62R~//~va64R~
                                                                   //~5925I~
    rchkenable=(setend || (mode==Board.MODE_ENDQDATA && !onmake))        //~5925I~
               && nogoing                                          //~5925I~
               && (pBoard.Flags & (Board.F_ONTRY|Board.F_ERROR))==0; //~5925R~
//System.out.println("enable RCHK numon="+numon+",setend="+setend+",mode="+mode+",onmake="+onmake+",nogoing="+nogoing+",flag="+pBoard.Flags);//~va03R~
//    BtnRCHK.setEnabled(rchkenable);                                //~5925R~
  if (!rchkenable && gameStatus==BTNID_START)                      //~va60R~
	pButtonStatus[BTNID_RCHK]=1;                                   //~va60I~
  else                                                             //~va62I~
  if (mode==Board.MODE_KEYINANS)                                   //~va62I~
	pButtonStatus[BTNID_RCHK]=1;   //reset button                  //~va62I~
  else                                                             //~va60I~
  if (mode==Board.MODE_ENDQDATA)                                   //~va63I~
	pButtonStatus[BTNID_RCHK]=1;   //reset button                  //~va63I~
  else                                                             //~va63I~
	pButtonStatus[BTNID_RCHK]=(rchkenable?1:0);                    //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable mode="+mode+",RCHK="+pButtonStatus[BTNID_RCHK]);//~va63I~//~va64R~
                                                                   //~5925I~
	enable=(mode==Board.MODE_KEYINANS||mode==Board.MODE_ENDQDATA)  //~5A02R~
		   && pBoard.NumCount<Wnp.PEG_MAX                              //~5925R~
           && nogoing                                              //~5925I~
           && (pBoard.Flags & (Board.F_ERROR|Board.F_MODEMAKE))==0;  //~5925R~
//System.out.println("enable NEXT mode="+mode+",numcount="+pBoard.NumCount+",nogoing="+nogoing+",flag="+pBoard.Flags);//~va03R~
//    BtnNEXT.setEnabled(enable);                                    //~5925R~
    if (swClear && pBoard.NumCount!=0)                                                   //~va61I~//~va63R~//~va64R~
    	enable=true;                                               //~va61I~//~va63R~//~va64R~
	pButtonStatus[BTNID_NEXT]=(enable?1:0);                              //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable NEXT="+pButtonStatus[BTNID_NEXT]);//~va62I~//~va64R~
                                                                   //~5925I~
//  enable=(rchkenable|| nogoing||(mode==MODE_KEYINANS && pBoard.NumCount==PEG_MAX));//~5925R~
    enable=(rchkenable||(mode==Board.MODE_KEYINANS && pBoard.NumCount==Wnp.PEG_MAX));//~5925R~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable="+enable+",rchkenable="+rchkenable+",mode="+mode+",NumCount="+pBoard.NumCount);//~va70I~
    enable=enable && !(mode==Board.MODE_OUTANS && pBoard.NumCount==Wnp.PEG_MAX)//~5925R~
           && !((pBoard.Flags & Board.F_ERROR)!=0 && numon);	//go avail when err but num=0//~5925R~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable="+enable+",Flags="+Integer.toHexString(pBoard.Flags)+",numon="+numon);//~va70I~
//    BtnGO.setEnabled(enable);                                      //~5925R~
	pButtonStatus[BTNID_ANS]=(enable?1:0);                               //~v@@@I~
	if (Dump.Y) Dump.println("ButtonDlg.SetDlgBtn enable ANS="+pButtonStatus[BTNID_ANS]);//~va62I~//~va64R~
    if (enable)                                                   //~va40I~//~va41I~
    	AG.aBGMList.stopAll();                                     //~va40I~//~va41M~
                                                                   //~5925I~
    enable=nogoing;                                                //~5925I~
//    TextTIMEOUT.setEnabled(enable);                                //~5925R~
    enable=nogoing && numon;                                       //~5925I~
//    BtnSORT.setEnabled(enable);                                    //~5925R~
                                                                   //~5925I~
    return 0;                                                     //~5925I~
}                                                                  //~5925I~
//=======================================================================//~5925I~
private void  InitBtnStatus()                                      //~5925I~//~va23R~//~v@@@R~
{                                                                  //~5925I~//~va23R~//~v@@@R~
    SetDlgBtn();                                                   //~5925I~//~va23R~//~v@@@R~
//                                                                   //~5925I~//~va23R~
//    if (!RadioLVL0.isSelected())                                   //~5925I~//~va23R~
//    if (!RadioLVL1.isSelected())                                   //~5925I~//~va23R~
//    if (!RadioLVL2.isSelected())                                   //~5925I~//~va23R~
//    if (!RadioLVL3.isSelected())                                   //~5925I~//~va23R~
//        RadioLVL0.setSelected(true);                               //~5925I~//~va23R~
//                                                                   //~5925I~//~va23R~
////    if (!RadioDEN0.isSelected())                                 //~va03R~//~va23R~
////    if (!RadioDEN3.isSelected())                                 //~va03R~//~va23R~
////    if (!RadioDEN4.isSelected())                                 //~va03R~//~va23R~
////    if (!RadioDEN5.isSelected())                                 //~va03R~//~va23R~
////        RadioDEN0.setSelected(true);                             //~va03R~//~va23R~
//                                                                   //~5925I~//~va23R~
}                                                                  //~5925I~//~v@@@R~
private void  GiveFocus()                                      //~5925R~
{                                                  //~5925R~
//	wnp.show();    	                               //~5925I~       //~va23R~
}                                                                  //~5925R~
//*****************************************************************//~5925I~
//private void OnPswdEnter()                                         //~5925I~//~va23R~
//{                                                                  //~5925I~//~va23R~
//    OnMake();                                                      //~5925I~//~va23R~
//}                                                                  //~5925I~//~va23R~
private void PostMessage(final int Preqid){

//    Runnable dofinished=new Runnable(){                     //~5925R~//~va23R~
//        public void run(){                                       //~va23R~
//            String estr;//~5925I~                                //~va23R~
//            try {                                                //~va23R~
//                pView.Dlgreq(Preqid);                                 //~5925I~//~va23R~
//            }                                                    //~va23R~
//            catch(Exception e){                                  //~va23R~
//                estr=e.toString();                               //~va23R~
//            }                                                    //~va23R~
//        }                                                          //~5925I~//~va23R~
//    };                                                             //~5925I~//~va23R~
////  SwingUtilities.invokeLater(dofinished);                        //~5925I~//~va23R~
//**new Handler() cause "Can't call handler inside the thread that has not callerd Looper.prepare()"//~va23I~
	pView.Dlgreq(Preqid);                                          //~va23R~
	                                       //~va23I~
}                                                                  //~5925I~


//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
public void OnDraw(Canvas pCanvas)                                 //~v@@@R~
{                                                                  //~v@@@I~
//*********************************                                //~v@@@I~
	pDC=pCanvas;                                                   //~v@@@I~
	for (int ii=0;ii<BUTTON_CTR;ii++)                                  //~v@@@I~
    {                                                              //~v@@@I~
		drawButton(ii);                                        //~v@@@I~
    }                                                              //~v@@@I~
}//OnDraw                                                          //~v@@@I~
//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
public void drawButton(int PbuttonNo)                              //~v@@@I~
{                                                                  //~v@@@I~
    int x,y,xe,ye,tx,ty,status,x1=0,x2=0,y1,y2;                     //~v@@@R~
    Rect rect;                                                     //~v@@@I~
    Paint paint=null;                                                   //~v@@@I~
    String str;                                                    //~v@@@I~
                //~v@@@I~
//*********************************                                //~v@@@I~
    rect=pButtonRect[PbuttonNo];                                    //~v@@@I~
    status=pButtonStatus[PbuttonNo];                               //~v@@@I~
    str=pButtonText[PbuttonNo];                                    //~v@@@I~
                                                                   //~v@@@I~
    x=rect.left;                                                   //~v@@@I~
    y=rect.top;                                                    //~v@@@I~
    xe=rect.right;                                                 //~v@@@I~
    ye=rect.bottom;                                                //~v@@@I~
    tx=x+BUTTON2D1*2;                                              //~v@@@R~
    ty=y+BUTTON_TEXTBASE;                                          //~v@@@R~
    paint=pButtonPaintOutline;                                           //~v@@@I~
	pDC.drawRect(x,y,xe,ye,paint);                                 //~v@@@I~
    switch(status)                                                //~v@@@I~
    {                                                              //~v@@@I~
    case BUTTON_STATUS_DISABLE:	                                       //~v@@@I~
    	paint=pButtonPaintDisable;                                 //~v@@@R~
        x1=x+BUTTON2D1;                                            //~v@@@I~
        x2=xe-BUTTON2D2;                                           //~v@@@I~
        y1=y+BUTTON2D1;                                            //~v@@@I~
        y2=ye-BUTTON2D2;                                           //~v@@@I~
		pDC.drawRect(x1,y1,x2,y2,paint);                           //~v@@@R~
        paint=pButtonPaintTextDisable;  //text paint               //~v@@@R~
    	break;                                                     //~v@@@I~
    case BUTTON_STATUS_ENABLE:                                            //~v@@@I~
    	paint=pButtonPaintEnable;                                  //~v@@@R~
        x1=x+BUTTON2D1;                                            //~v@@@I~
        x2=xe-BUTTON2D2;                                           //~v@@@I~
        y1=y+BUTTON2D1;                                            //~v@@@I~
        y2=ye-BUTTON2D2;                                           //~v@@@I~
		pDC.drawRect(x1,y1,x2,y2,paint);                           //~v@@@I~
        paint=pButtonPaintTextEnable;                                   //~v@@@I~
    	break;                                                     //~v@@@I~
    case BUTTON_STATUS_PUSHED:                                     //~v@@@R~
    	paint=pButtonPaintEnable;                                       //~v@@@R~
        x1=x+BUTTON2D1;                                            //~v@@@I~
        x2=xe-BUTTON2D2;                                           //~v@@@I~
        y1=y+BUTTON2D1;                                            //~v@@@I~
        y2=ye-BUTTON2D2;                                           //~v@@@I~
		pDC.drawRect(x1,y1,x2,y2,paint);                           //~v@@@I~
    	paint=pButtonPaintPushed;                                       //~v@@@R~
        x1=x1-BUTTON2D3;                                           //~v@@@I~
        x2=x2+BUTTON2D4;                                           //~v@@@R~
        y1=y1-BUTTON2D3;                                           //~v@@@I~
        y2=y2+BUTTON2D4;                                           //~v@@@R~
		pDC.drawRect(x1,y1,x2,y2,paint);                           //~v@@@I~
        paint=pButtonPaintTextPushed;                                   //~v@@@I~
        tx+=2;                                                     //~v@@@I~
        ty+=2;                                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    }                                                              //~v@@@I~
    int strw=(int)paint.measureText(str,0,str.length());           //~va41I~
    int shift=(x2-x1-strw)/2;                                      //~va41I~
    if (tx>x1 && shift>0)                                          //~va41I~
    	tx+=shift;                                                 //~va41I~
    pDC.drawText(str,tx,ty,paint);
    if (Dump.Y) Dump.println("ButtonDlg.drawButton buttonNo="+PbuttonNo+",str="+str+",tx="+tx+",ty="+ty);//~va41I~
}//drawButton    //~v@@@I~
//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
//*****************************************                        //~v@@@I~
public void drawNumBtn(Canvas pCanvas)                             //~v@@@I~
{                                                                  //~v@@@I~
    int x,y,xe,ye,tx,ty,txoffs,tyoffs;                 //~v@@@R~
    Paint paintbg,paintfg;                                         //~v@@@R~
    //*********************************                                //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.drawNumBtn sw_DispNumButton="+sw_DispNumButton);//~va41I~
    if (!sw_DispNumButton)                                         //~v@@@I~
    	return;                                                    //~v@@@I~
    textbase_BTN_NUM=getTextBaseLine(pBtnNumPaintText,Wnp.BTN_NUM_H);//~va41I~
    if (Dump.Y) Dump.println("ButtonDlg.drawNumBtn textbase_BTN_NUM="+textbase_BTN_NUM);//~va41I~
	pDC=pCanvas;                                                   //~v@@@I~
	x=Wnp.BTN_NUM_X;                                                    //~v@@@I~
	y=Wnp.BTN_NUM_Y;                                                   //~v@@@I~
    txoffs=(Wnp.BTN_NUM_W-Wnp.BTN_NUM_H)/2+Wnp.GAP;                    //~v@@@R~
    tyoffs=textbase_BTN_NUM;                                       //~v@@@R~
	for (int ii=0;ii<BTN_NUM_CTR;ii++,x=xe+Wnp.BTN_NUM_GAP)        //~v@@@R~
    {                                                              //~v@@@I~
    	xe=x+Wnp.BTN_NUM_W;                                            //~v@@@I~
	    ye=y+Wnp.BTN_NUM_H;                                        //~v@@@I~
      if ((ii==(BTN_NUM_SAME-1) && pBoard.Shiftkey!=0)             //~v@@@R~
      ||  (ii==(BTN_NUM_MEMO-1) && pBoard.Controlkey!=0)           //~v@@@R~
      ||  (ii<BTN_NUM_0 && ii==(pushedNumBtn-1))               //~v@@@R~
      )                                                            //~v@@@I~
      {                                                            //~v@@@I~
        paintbg=pBtnNumPaintPushed;                                //~v@@@I~
        paintfg=pBtnNumPaintTextPushed;                            //~v@@@I~
      }                                                            //~v@@@I~
      else                                                         //~v@@@I~
      {                                                            //~v@@@I~
        paintbg=pBtnNumPaint;                                      //~v@@@I~
        paintfg=pBtnNumPaintText;                                  //~v@@@I~
      }                                                            //~v@@@I~
        pDC.drawRect(x,y,xe,ye,paintbg);                           //~v@@@R~
    	if (Dump.Y) Dump.println("ButtonDlg.drawNumBtn ii="+ii+",x="+x+",y="+y+",x2="+xe+",ye="+ye);//~va41I~
        tx=x+txoffs;                                               //~v@@@M~
        ty=y+tyoffs;                                               //~v@@@M~
//        if (ii<10)                                                 //~v@@@R~//~va41R~
//        {                                                          //~v@@@I~//~va41R~
        	paintfg.setTextSize(Wnp.BTN_NUM_H);                    //~v@@@I~
	    	pDC.drawText(strBtnNumText[ii],tx,ty,paintfg);         //~v@@@R~
//        }                                                          //~v@@@I~//~va41R~
//        else                                                       //~v@@@I~//~va41R~
//        {                                                          //~v@@@I~//~va41R~
//            paintfg.setTextSize(Wnp.BTN_NUM_H/2);                  //~v@@@I~//~va41R~
//            pDC.drawText(strBtnNumText[ii],tx+txoffs/2,ty-tyoffs/2,paintfg);//~v@@@R~//~va41R~
//            pDC.drawText(strBtnNumText2[ii-10],tx,ty,paintfg);     //~v@@@I~//~va41R~
//        }                                                          //~v@@@I~//~va41R~
    }                                                              //~v@@@I~
    pushedNumBtn=0;                                                //~v@@@I~
}//drawNumBtn                                                      //~v@@@I~
//===============================================================================*///~v@@@I~
//=detect button push                                              //~v@@@I~
//=return :button#,-1 if out of range                              //~v@@@I~
//===============================================================================*///~v@@@I~
private int ScreenToNumBtn(int wx,int wy)                          //~v@@@I~
{                                                                  //~v@@@I~
	int ii,x,y,xe,ye;                                                 //~v@@@I~
//***********************                                          //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.ScreenToNumButton wx="+wx+",wy="+wy);//~va41I~
    x=Wnp.BTN_NUM_X;                                               //~v@@@I~
    y=Wnp.BTN_NUM_Y;                                            //~v@@@I~
	ye=y+Wnp.BTN_NUM_H;                                           //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.ScreeToNumBtn BTN_NUM_X="+Wnp.BTN_NUM_X+",BTN_NUM_Y="+Wnp.BTN_NUM_Y+",BTN_NUM_H="+Wnp.BTN_NUM_H+",ye="+ye);//~va41I~
    if ( wx<x||wy<y||wy>=ye)                                       //~v@@@I~
        return -1;                                                 //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.ScreeToNumBtn BTN_NUM_GAP="+Wnp.BTN_NUM_GAP+",BTN_NUM_W="+Wnp.BTN_NUM_W);//~va41I~
	for (ii=0;ii<BTN_NUM_CTR;ii++,x=xe+Wnp.BTN_NUM_GAP)            //~v@@@R~
    {                                                              //~v@@@I~
    	xe=x+Wnp.BTN_NUM_W;                                        //~v@@@I~
	    if (Dump.Y) Dump.println("ButtonDlg.ScreeToNumBtn ii="+ii+",xe="+xe);//~va41I~
        if (wx>=x && wx<xe)                                        //~v@@@I~
        	break;                                                 //~v@@@I~
    }                                                              //~v@@@I~
    if (ii>=BTN_NUM_CTR)                                           //~v@@@R~
        return -1;                                                 //~v@@@I~
    ii++;                                                          //~v@@@I~
    return ii;     //1-->9,10(0),11(*),12(#)                       //~v@@@R~
}//ScreenToNumBtn                                                  //~v@@@I~
//===============================================================================//~v@@@I~
//chk numbtn  pushed                                               //~v@@@I~
//rc:pushed num btrn,-1:out of range                               //~v@@@I~
//===============================================================================//~v@@@I~
public int NumBtnPush(int Pnum,Point point)                        //~v@@@R~
{                                                                  //~v@@@I~
    int    btnNo;                                 //~v@@@I~
//****************                                                 //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.NumBtnPush num="+Pnum+",sw_DispNumButton="+sw_DispNumButton);//~va41I~
    if (!sw_DispNumButton)	//not num btn display option           //~v@@@I~
    	return -1;                                                 //~v@@@I~
    if ((btnNo=ScreenToNumBtn(point.x,point.y))<0)	//out of button//~v@@@I~
    {                                                              //~v@@@I~
    	return -1;                                                 //~v@@@I~
    }                                                              //~v@@@I~
    if (Pnum==-1)	//action down                                  //~v@@@I~
    	pushedNumBtn=btnNo;                                        //~v@@@R~
    if (Dump.Y) Dump.println("ButtonDlg.NumBtnPush btnNo="+btnNo+",pushedNumBtn="+pushedNumBtn);//~va41I~
    return btnNo;                                                  //~v@@@I~
}//NumBtnPush                                                      //~v@@@I~
//===============================================================================//~v@@@I~
//Pnum:-1:ACTION_DOWN,-2:ACTION_UP                                 //~v@@@I~
//rc<0:out of board                                                //~v@@@I~
//rc==0:not ACTION_DOWN (chk point only)                           //~v@@@I~
//rc==1:ACTION_DONE (csr moved)                                    //~v@@@I~
//===============================================================================//~v@@@I~
public int ButtonPush(Point point,int Pnum)                        //~v@@@R~
{                                                                  //~v@@@I~
    int    btnNo,status;                                                  //~v@@@I~
//****************                                                 //~v@@@I~
    if ((btnNo=ScreenToButton(point.x,point.y))<0)	//out of button//~v@@@I~
    	return -1;                                                 //~v@@@I~
    status=pButtonStatus[btnNo];                                   //~v@@@I~
    if (Dump.Y) Dump.println("ButtonDlg.ButtonPush swReset="+swReset+",btnNo="+btnNo+",status="+status+",Pnum="+Pnum+",mode="+pBoard.GetMode());//~va62R~//~va63R~//~va64R~
    if (Pnum==-1)                                                  //~v@@@I~
    {                                                              //~v@@@I~
    	switch(status)                                             //~v@@@I~
        {                                                          //~v@@@I~
        case BUTTON_STATUS_ENABLE:                                 //~v@@@I~
    		pButtonStatus[btnNo]=BUTTON_STATUS_PUSHED;             //~v@@@I~
            break;                                                 //~v@@@I~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
    else                                                           //~v@@@I~
    {                                                              //~v@@@I~
    	if (status!=BUTTON_STATUS_DISABLE)                         //~v@@@R~
        {                                                          //~v@@@I~
    		pButtonStatus[btnNo]=BUTTON_STATUS_ENABLE;             //~v@@@I~
            switch(btnNo)                                          //~v@@@R~
            {                                                      //~v@@@R~
            case BTNID_MAKE:                                       //~v@@@R~
                OnMake();                                          //~v@@@R~
                break;                                             //~v@@@R~
            case BTNID_START:                                      //~v@@@R~
              if (gameStatus==BTNID_START)                         //~va60I~
              	OnBack();                                          //~va60I~
              else                                                 //~va60I~
                OnSetend();                                        //~v@@@R~
                break;                                             //~v@@@R~
            case BTNID_NEXT:                                       //~v@@@R~
    	      if (swClear)                                         //~va61I~//~va63R~//~va64R~
                OnClear();                                         //~va61I~//~va63R~//~va64R~
              else                                                 //~va61I~//~va63R~//~va64R~
                OnNext();                                          //~v@@@R~
                break;                                             //~v@@@R~
            case BTNID_ANS:                                        //~v@@@R~
                OnGo();                                            //~v@@@R~//~va41R~
                break;                                             //~v@@@R~
//          case BTNID_CLEAR:                                      //~v@@@R~
//              OnClear();                                         //~v@@@R~
            case BTNID_RCHK:                                       //~v@@@I~
//            if (gameStatus==BTNID_START)                         //~va60M~//~va63R~
              if (swReset)                                         //~va63I~
              	OnReset();                                         //~va60M~
              else                                                 //~va60M~
                OnRchk();                                          //~v@@@I~
                break;                                             //~v@@@R~
            }                                                      //~v@@@R~
    		pBoard.SameNum=0;	//reset samenum effect             //~v@@@I~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
    return 1;                                                      //~v@@@I~
}                                                                  //~v@@@I~
//===============================================================================//~v@@@I~
//option dialog from menu                                          //~v@@@I~
//===============================================================================//~v@@@I~
public void OnMenuOption()                       //~v@@@R~
{                                                                  //~v@@@I~
//***********                                                      //~v@@@I~
//    Dialog dlg=new Dialog(WnpView.context);                      //~v@@@R~
//    dlg.setContentView(R.layout.dlgoption);                      //~v@@@R~
//    dlg.setTitle(WnpView.context.getText(R.string.OptionDialogTitle).toString());//~v@@@R~
//    dlg.show();                                                  //~v@@@R~
                             //~v@@@I~
                                          //~v@@@I~
	LayoutInflater factory=LayoutInflater.from(WnpView.context);          //~v@@@I~
    final View dlgoptionView=factory.inflate(R.layout.dlgoption,null);  //~v@@@I~
	SetDlgBtnMenu(dlgoptionView);                                  //~v@@@I~
                                                                   //~v@@@I~
    AlertDialog.Builder builder=new AlertDialog.Builder(WnpView.context);//~v@@@R~
    builder.setTitle(WnpView.context.getText(R.string.OptionDialogTitle).toString());//~v@@@R~
    builder.setView(dlgoptionView);                                //~v@@@R~
    builder.setPositiveButton("OK",new DialogInterface.OnClickListener()//~v@@@R~
								{                                  //~v@@@I~
                               		
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {  
                                    	optionOnOK();     //~v@@@R~
                                    	dlg.dismiss();
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                         //~v@@@I~
    builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()//~v@@@R~
								{                                  //~v@@@I~
                                	public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                		dlg.dismiss();
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                         //~v@@@I~
    pDlg=builder.create();                                          //~v@@@R~
//  pDlg.requestWindowFeature(Window.FEATURE_NO_TITLE);            //~v@@@R~
    OptionBGM.setup(pDlg,dlgoptionView);                           //~va80R~
    pDlg.show();                                                   //~v@@@R~
}//OnMenuOption                                                    //~v@@@R~
//**********************************************************************//~v@@@I~
//* get dialog box option                                              *//~v@@@I~
//**********************************************************************//~v@@@I~
private void optionOnOK()                                          //~v@@@R~
{
	RadioGroup rg;                                                 //~v@@@I~
	CheckBox   cb;                                                 //~v@@@I~
	EditText   et;                                                 //~v@@@I~
    int id;                                 //~v@@@I~
//******************                                               //~v@@@I~
	if (Dump.Y) Dump.println(CN+"onOK");                           //+va80I~
//*level                                                           //~v@@@I~
	rg=(RadioGroup)pDlg.findViewById(R.id.RadioGroupLevel);	       //~v@@@R~
    id=rg.getCheckedRadioButtonId();                               //~v@@@I~
    switch(id)                                                     //~v@@@I~
    {                                                              //~v@@@I~
    case R.id.RadioButtonLE:                                       //~v@@@I~
        intRadioLVL=1;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    case R.id.RadioButtonLM:                                       //~v@@@I~
        intRadioLVL=2;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    case R.id.RadioButtonLH:                                       //~v@@@I~
        intRadioLVL=3;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
        intRadioLVL=0;                                             //~v@@@I~
    }                                                              //~v@@@I~
//*dencity                                                         //~v@@@I~
	rg=(RadioGroup)pDlg.findViewById(R.id.RadioGroupDencity);      //~v@@@R~
    id=rg.getCheckedRadioButtonId();                               //~v@@@I~
    switch(id)                                                     //~v@@@I~
    {                                                              //~v@@@I~
    case R.id.RadioButtonD3:                                       //~v@@@I~
        intRadioDEN=3;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    case R.id.RadioButtonD4:                                       //~v@@@I~
        intRadioDEN=4;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    case R.id.RadioButtonD5:                                       //~v@@@I~
        intRadioDEN=5;                                             //~v@@@I~
    	break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
        intRadioDEN=0;                                             //~v@@@I~
    }                                                              //~v@@@I~
//*pattern chkbox                                                  //~v@@@I~
	cb=(CheckBox)pDlg.findViewById(R.id.CheckBoxPattern);          //~v@@@R~
    sw_RIGID=cb.isChecked();                                       //~v@@@I~
//*redundancy chkbox                                               //~v@@@I~
	cb=(CheckBox)pDlg.findViewById(R.id.CheckBoxRedundancy);       //~v@@@R~
    sw_NoRedundant=cb.isChecked();                                 //~v@@@I~
//*display num button                                              //~v@@@R~
	cb=(CheckBox)pDlg.findViewById(R.id.CheckBoxDispNumButton);    //~v@@@I~
    sw_DispNumButton=cb.isChecked();                               //~v@@@I~
//*display num button                                              //~va41I~
	cb=(CheckBox)pDlg.findViewById(R.id.CheckBoxBGM);              //~va41I~
    optBGM=cb.isChecked() ? 1:0;                                   //~va41R~
    AG.aBGMList.resetOption();                                     //~va41I~
////*timeout                                                       //~v@@@R~
//    et=(EditText)pDlg.findViewById(R.id.EditTextTimeout);        //~v@@@R~
//    strTextTIMEOUT=et.getText().toString();                      //~v@@@R~
//*Randomseed                                                      //~v@@@I~
	et=(EditText)pDlg.findViewById(R.id.EditTextMaxsave);          //~v@@@R~
    strTextMAXSAVE=et.getText().toString();                        //~v@@@R~
    int ival=0;                                                        //~v@@@I~
    if (!strTextMAXSAVE.equals(""))                                //~v@@@I~
        try{                                                       //~v@@@I~
                ival=Integer.parseInt(strTextMAXSAVE.trim());      //~v@@@R~
        }                                                          //~v@@@I~
        catch(NumberFormatException e)                             //~v@@@I~
        {                                                          //~v@@@I~
        }                                                          //~v@@@I~
    if (ival<MIN_MAXSAVE)                                          //~v@@@I~
        ival=MIN_MAXSAVE;                                          //~v@@@I~
    intMaxsave=ival;                                               //~v@@@I~
    CPIndex.MAXHISTR=ival;                                         //~v@@@I~
    CPIndex.MAXLEVEL=ival;                                         //~v@@@I~
    CPIndex.MAXSCORE=ival;                                         //~v@@@I~
//*UserID                                                          //~v@@@I~
	et=(EditText)pDlg.findViewById(R.id.EditTextUserid);           //~v@@@I~
    strTextUID=et.getText().toString();                            //~v@@@I~
	optionReadWrite(1/*write*/);                                   //~v@@@I~
    AG.aOptionBGM.onOK();                                          //~va80I~
    pView.Invalidate(false);                                       //~v@@@R~
}//optionOnOK                                                      //~v@@@R~
//=======================================================================//~v@@@I~
// option dialog enable/disable                                    //~v@@@I~
//=======================================================================//~v@@@I~
public int SetDlgBtnMenu(View Pview)                               //~v@@@R~
{                                                                  //~v@@@I~
	RadioGroup rg;                                                 //~v@@@I~
	CheckBox   cb;                                                 //~v@@@I~
	EditText   et;                                                 //~v@@@I~
    int id;                                                        //~v@@@I~
 //**************                                                  //~v@@@I~
 //*Level                                                          //~v@@@I~
	rg=(RadioGroup)Pview.findViewById(R.id.RadioGroupLevel);       //~v@@@R~
    switch(intRadioLVL)                                            //~v@@@I~
    {                                                              //~v@@@I~
    case 1:                                                        //~v@@@I~
    	id=R.id.RadioButtonLE;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    case 2:                                                        //~v@@@I~
    	id=R.id.RadioButtonLM;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    case 3:                                                        //~v@@@I~
    	id=R.id.RadioButtonLH;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	id=R.id.RadioButtonLN;                                     //~v@@@I~
    }                                                              //~v@@@I~
    rg.check(id);                                                  //~v@@@I~
 //*Dencity                                                        //~v@@@I~
	rg=(RadioGroup)Pview.findViewById(R.id.RadioGroupDencity);     //~v@@@R~
    switch(intRadioDEN)                                            //~v@@@I~
    {                                                              //~v@@@I~
    case 3:                                                        //~v@@@I~
    	id=R.id.RadioButtonD3;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    case 4:                                                        //~v@@@I~
    	id=R.id.RadioButtonD4;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    case 5:                                                        //~v@@@I~
    	id=R.id.RadioButtonD5;                                     //~v@@@I~
    	break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	id=R.id.RadioButtonDN;                                     //~v@@@I~
    }                                                              //~v@@@I~
    rg.check(id);                                                  //~v@@@I~
//*pattern chkbox                                                  //~v@@@I~
	cb=(CheckBox)Pview.findViewById(R.id.CheckBoxPattern);         //~v@@@R~
    cb.setChecked(sw_RIGID);                                            //~v@@@I~
//*redundancy chkbox                                               //~v@@@I~
	cb=(CheckBox)Pview.findViewById(R.id.CheckBoxRedundancy);      //~v@@@R~
    cb.setChecked(sw_NoRedundant);                                      //~v@@@I~
//*NumButton                                                       //~va41R~
	cb=(CheckBox)Pview.findViewById(R.id.CheckBoxDispNumButton);      //~v@@@I~
    cb.setChecked(sw_DispNumButton);                               //~v@@@I~
//*BGM                                                             //~va41I~
	cb=(CheckBox)Pview.findViewById(R.id.CheckBoxBGM);             //~va41I~
    cb.setChecked(!isNoBGM());                                     //~va41R~
////*timeout                                                       //~v@@@R~
//    et=(EditText)Pview.findViewById(R.id.EditTextTimeout);       //~v@@@R~
//    et.setText(strTextTIMEOUT);                                  //~v@@@R~
//*Randomseed                                                      //~v@@@I~
//  et=(EditText)Pview.findViewById(R.id.EditTextRandomseed);      //~v@@@R~
    et=(EditText)Pview.findViewById(R.id.EditTextMaxsave);         //~v@@@I~
    et.setText(String.format("%3d",intMaxsave));                   //~v@@@R~
//*UserID                                                          //~v@@@I~
	et=(EditText)Pview.findViewById(R.id.EditTextUserid);          //~v@@@I~
    et.setText(strTextUID);                                        //~v@@@I~
    return 0;                                                      //~v@@@I~
}//SetDlgBtnMenu                                                   //~v@@@I~
//**********************************************************************//~v@@@I~
//*preference read/write                                               *//~v@@@I~
//**********************************************************************//~v@@@I~
private void optionReadWrite(int Popt)                             //~v@@@I~
{                                                                  //~v@@@I~
//******************                                               //~v@@@I~
	SharedPreferences pref=WnpView.context.getSharedPreferences("PreferencesEx",Context.MODE_PRIVATE);//~v@@@I~
    if (Popt==1)    //write                                        //~v@@@I~
    {                                                              //~v@@@I~
    	SharedPreferences.Editor editor=pref.edit();                //~v@@@I~
        editor.putString(strPrefKeyTIMEOUT,strTextTIMEOUT);        //~v@@@I~
//      editor.putString(strPrefKeySEED,strTextSEED);              //~v@@@R~
        editor.putInt(strPrefKeyMAXSAVE,intMaxsave);               //~v@@@I~
        editor.putInt(strPrefKeyLVL,intRadioLVL);                  //~v@@@I~
        editor.putInt(strPrefKeyDEN,intRadioDEN);                  //~v@@@I~
        editor.putBoolean(strPrefKeyNoRedundant,sw_NoRedundant);   //~v@@@I~
        editor.putBoolean(strPrefKeyDispNumButton,sw_DispNumButton);//~v@@@I~
        editor.putBoolean(strPrefKeyRIGID,sw_RIGID);               //~v@@@I~
        editor.commit();                                           //~v@@@I~
        Utils.putPreference(PKEY_BGM, optBGM);                    //~va41R~
    }                                                              //~v@@@I~
    else                                                           //~v@@@I~
    {                                                              //~v@@@I~
        strTextTIMEOUT=pref.getString(strPrefKeyTIMEOUT,""/*default value*/);//~v@@@I~
//      strTextSEED=pref.getString(strPrefKeySEED,"");             //~v@@@R~
        intMaxsave=pref.getInt(strPrefKeyMAXSAVE,DEF_MAXSAVE);     //~v@@@R~
        intRadioLVL=pref.getInt(strPrefKeyLVL,0);                  //~v@@@I~
        intRadioDEN=pref.getInt(strPrefKeyDEN,0);                  //~v@@@I~
        sw_NoRedundant=pref.getBoolean(strPrefKeyNoRedundant,false);//~v@@@I~
        sw_DispNumButton=pref.getBoolean(strPrefKeyDispNumButton,sw_DispNumButton);//~v@@@R~
        sw_RIGID=pref.getBoolean(strPrefKeyRIGID,false);           //~v@@@I~
        isNoBGM();	//set optBGM                                   //~va41I~
    }                                                              //~v@@@I~
}//optionReadWrite                                                 //~v@@@I~
                                                                   //~v@@@I~
//===============================================================================//~v@@@I~
//simple alertdialog                                               //~v@@@I~
//===============================================================================//~v@@@I~
public static void simpleAlertDialog(String Ptext,String Ptitle)   //~v@@@R~
{                                                                  //~v@@@I~
//***********                                                      //~v@@@I~
    AlertDialog.Builder dlg=new AlertDialog.Builder(NppView.context);//~v@@@R~
    dlg.setMessage(Ptext);                                         //~v@@@I~
    if (Ptitle!=null)                                              //~v@@@I~
    	dlg.setTitle(Ptitle);                                      //~v@@@I~
    dlg.setPositiveButton("Close",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                                                   //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                    	dlg.dismiss();             //~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    dlg.create();                                                  //~v@@@I~
    dlg.show();                                                    //~v@@@I~
}                                                                  //~v@@@I~
//===============================================================================//~v@@@I~
//simple alertdialog                                               //~v@@@I~
//===============================================================================//~v@@@I~
public static void simpleExitAlertDialog(String Ptext)             //~v@@@R~
{                                                                  //~v@@@I~
//***********                                                      //~v@@@I~
    AlertDialog.Builder dlg=new AlertDialog.Builder(NppView.context);//~v@@@I~
    dlg.setMessage(Ptext);                                         //~v@@@I~
    dlg.setPositiveButton("Close",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                                                   //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                    	dlg.dismiss();             //~v@@@I~
                						((Activity)NppView.context).finish();                  //~@@@@R~//~v@@@R~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    dlg.create();                                                  //~v@@@I~
    dlg.show();                                                    //~v@@@I~
}                                                                  //~v@@@I~
//===============================================================================//~v@@@I~
//simple alertdialog                                               //~v@@@I~
//===============================================================================//~v@@@I~
public static void simpleYNExitAlertDialog(String Ptext)           //~v@@@I~
{                                                                  //~v@@@I~
//***********                                                      //~v@@@I~
    AlertDialog.Builder dlg=new AlertDialog.Builder(NppView.context);//~v@@@I~
    dlg.setMessage(Ptext);                                         //~v@@@I~
    dlg.setPositiveButton("Yes",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                                                   //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                    	dlg.dismiss();             //~v@@@I~
                						((Activity)NppView.context).finish();//~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    dlg.setNegativeButton("No",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                                                   //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                    	dlg.dismiss();             //~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    dlg.create();                                                  //~v@@@I~
    dlg.show();                                                    //~v@@@I~
}                                                                  //~v@@@I~
//**********************************                               //~0A21I~//~v@@@M~
//* file submenu selected                                          //~0A21I~//~v@@@M~
//**********************************                               //~0A21I~//~v@@@M~
public void FileSubmenu(int Psubmenuid)                        //~0A21I~//~v@@@R~
{                                                                  //~0A21I~//~v@@@M~
	String pfile;                                                  //~0A21I~//~v@@@M~
    CPattern ppat;                                                 //~v@@@I~
    String msg;                                                    //~v@@@I~
//************************                                         //~0A21I~//~v@@@M~
    if (Dump.Y) Dump.println("ButtonDlg.FileSubmenu menuid="+Psubmenuid);//~va62I~
	subMenuId=Psubmenuid;                                          //~v@@@I~
    ppat=WnpView.aBoard.pPat;                                      //~v@@@I~
	switch(Psubmenuid)                                                   //~0A21I~//~v@@@R~
    {                                                              //~0A21I~//~v@@@M~
    case CPattern.FILE_SAVE:                                       //~v@@@R~
	    if (Dump.Y) Dump.println("ButtonDlg.FileSubmenu Save strQuestionNo="+ppat.strQuestionNo);//~va62I~
//  	pfile="Save#"+ppat.strQuestionNo;                                        //~0A21I~//~v@@@R~//~va62R~
    	if (ppat.strQuestionNo==null)                              //~va62I~
        {                                                          //~va62I~
			msg=WnpView.contextR.getString(R.string.Err_NullQNo);//~va62I~
			Toast.makeText(WnpView.context,msg,Toast.LENGTH_LONG).show();//~va62I~
                                                                   //~va62I~
		}                                                          //~va62I~
    	pfile=ppat.strQuestionNo!=null ? "#"+ppat.strQuestionNo : null;//~va62R~
        (new SaveDlg(this,pfile)).show();                          //~va62R~
//  	ppat.Serialize(pfile,1/*write*/);                               //~0A21R~//~v@@@R~//~va62R~
//      prefIO(1/*write*/,CPattern.FILE_LAST_NAME,pfile);	               //~0A21R~//~v@@@R~//~va62R~
//      msg="#"+ppat.strQuestionNo+WnpView.contextR.getString(R.string.InfoSaved);//~v@@@R~//~va62R~
//      Toast.makeText(WnpView.context,msg,Toast.LENGTH_SHORT).show();//~v@@@I~//~va62R~
    	break;                                                     //~0A21I~//~v@@@M~
    case CPattern.FILE_RELOAD:          //load saved                                  //~0A21R~//~v@@@R~
        pfile=prefIO(0/*read*/,CPattern.FILE_LAST_NAME,"");                 //~0A21R~//~v@@@R~
        if (pfile.length()==0)                                          //~0A21I~//~v@@@R~
        {                                                          //~0A21I~//~v@@@R~
            String emsg=WnpView.context.getText(R.string.ErrNosavedData).toString();//~0A21I~//~v@@@R~
            ButtonDlg.simpleAlertDialog(emsg,null);                     //~0A21I~//~v@@@R~
        }                                                          //~0A21I~//~v@@@R~
        else                                                       //~0A21I~//~v@@@R~
        {                                                          //~v@@@I~
            ppat.Serialize(pfile,0/*read*/);                            //~0A21R~//~v@@@R~
    		OnRestore();                                           //~v@@@I~
        	msg="#"+ppat.strQuestionNo+WnpView.contextR.getString(R.string.InfoRestored);//~v@@@I~
	        Toast.makeText(WnpView.context,msg,Toast.LENGTH_SHORT).show();//~v@@@I~
        }                                                          //~v@@@I~
    	break;                                                    //~0108R~//~v@@@M~
    case CPattern.FILE_LIST_TIMESEQ:      //old timeseq                                 //~0A21R~//~v@@@R~
    case CPattern.FILE_LIST_LEVEL:        //old easy->hard                              //~0A21R~//~v@@@R~
    case CPattern.FILE_LIST_SCORE:        //list score                                  //~0A21R~//~v@@@R~
		FileList(Psubmenuid,0);                                    //~v@@@R~
    	break;                                                     //~0A21I~//~v@@@M~
    default:                                                       //~0A21I~//~v@@@M~
    	return;                                                    //~0A21I~//~v@@@M~
    }                                                              //~0A21I~//~v@@@M~
}//FileSubmenu                                                     //~0A21I~//~v@@@M~
//**********************************                               //~va62I~
public void saveFile(String Pfnm)                                  //~va62I~
{                                                                  //~va62I~
    if (Dump.Y) Dump.println("ButtonDlg.saveFile fnm="+Pfnm);      //~va62I~
    CPattern ppat=WnpView.aBoard.pPat;                                      //~va62I~
    ppat.Serialize(Pfnm,1/*write*/);                              //~va62I~
    prefIO(1/*write*/,CPattern.FILE_LAST_NAME,Pfnm);               //~va62I~
    String msg=Pfnm+WnpView.contextR.getString(R.string.InfoSaved);       //~va62I~
    Toast.makeText(WnpView.context,msg,Toast.LENGTH_SHORT).show(); //~va62I~
}                                                                  //~va62I~
//**********************************                               //~v@@@I~
//* file submenu selected;create submenu listview                  //~v@@@R~
//**********************************                               //~v@@@I~
private void FileList(int Psubmenuid,int Ppos)                     //~v@@@R~
{                                                                  //~v@@@I~
	Context context=WnpView.context;                               //~v@@@I~
//************************                                         //~v@@@I~
	final String[] fileListStringArray=pPIndex.getIndexList(Psubmenuid); //~v@@@R~
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.listrowf,fileListStringArray);//~v@@@R~
    ListView listview=new ListView(context);                       //~v@@@I~
    listview.setAdapter(adapter);                                  //~v@@@I~
    listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);            //~v@@@I~
    listview.setFocusableInTouchMode(true);                            //~v@@@I~
                                                                   //~v@@@I~
    FileListClick listenerc=new FileListClick(Psubmenuid);         //~v@@@R~
    listview.setOnItemClickListener(listenerc);                    //~v@@@R~
//    FileListSelected listeners=new FileListSelected(Psubmenuid);   //~v@@@I~
//    listview.setOnItemSelectedListener(listeners);                 //~v@@@I~
                                                                   //~v@@@I~
    listview.setOnKeyListener(new OnKeyListener()                    //~v@@@I~
    	{                                                          //~v@@@I~
        	public boolean onKey(View Pview,int keyCode,KeyEvent event)//~v@@@I~
            {                                                      //~v@@@I~
            	int action,itempos;                                //~v@@@I~
                //**********************                           //~v@@@I~
//                System.out.println("List onKey keycode="+keyCode);//~v@@@R~
                action=event.getAction();                          //~v@@@R~
                switch(action)                                     //~v@@@I~
                {                                                  //~v@@@I~
                case KeyEvent.ACTION_UP:                           //~v@@@I~
                    switch(keyCode)                                //~v@@@I~
                    {                                              //~v@@@I~
                    case KeyEvent.KEYCODE_DEL:                     //~v@@@I~
                    case KeyEvent.KEYCODE_0:                       //~v@@@I~
//                    	System.out.println("listview onkey:item="+((ListView)Pview).getSelectedItemId()+",pos="+((ListView)Pview).getSelectedItemPosition());//~v@@@R~
                    	itempos=((ListView)Pview).getSelectedItemPosition();//~v@@@I~
                        int rc=pPIndex.deleteIndexEntry(subMenuId,itempos);//~v@@@R~
                        if (rc==1)	//deleted                      //~v@@@I~
                        {                                          //~v@@@I~
                        	pListDlg.dismiss();                    //~v@@@M~
        					String entryText=fileListStringArray[itempos];//~v@@@R~
        					String msg2=entryText+WnpView.contextR.getString(R.string.InfoDeleted);//~v@@@R~
        					Toast.makeText(WnpView.context,msg2,Toast.LENGTH_SHORT).show();//~v@@@I~
							FileList(subMenuId,itempos);	//re-draw list//~v@@@R~
                        }                                          //~v@@@I~
                        return true;                               //~v@@@I~
                    case KeyEvent.KEYCODE_DPAD_CENTER:                  //~v@@@I~
//                        itempos=((ListView)Pview).getSelectedItemPosition();//~v@@@R~
//                        System.out.println("listview onkey:item="+itempos);//~v@@@R~
//                        String entryHistory=pPIndex.indexEntrySelected(1,subMenuId,itempos);//~v@@@R~
//                        if (entryHistory!=null)                  //~v@@@R~
//                        {                                        //~v@@@R~
//                            pBoard.pPat.SerializeFromIndex(entryHistory);//~v@@@R~
//                            pBoard.Flags|=Board.F_READINDX; //for craeteboard//~v@@@R~
//                            OnRestore();    //-->wnpview,createboard//~v@@@R~
//                        }                                        //~v@@@R~
//                        pListDlg.dismiss();                      //~v@@@R~
//						OnItemClick will be called;                //~v@@@I~
                        break;                                     //~v@@@I~
                    default:                                       //~v@@@I~
                    }                                              //~v@@@I~
                    break;                                         //~v@@@I~
                default:                                           //~v@@@I~
                }                                                  //~v@@@I~
                return false;           //~v@@@I~
            }                                                      //~v@@@I~
    	});                                                        //~v@@@I~
                                                                   //~v@@@I~
    LinearLayout layout=new LinearLayout(context);                  //~v@@@I~
    layout.setOrientation(LinearLayout.VERTICAL);                  //~v@@@I~
    layout.addView(listview);                                      //~v@@@I~
    listview.setSelection(Ppos);                                   //~v@@@I~
                                                                   //~v@@@I~
    AlertDialog.Builder dlg=new AlertDialog.Builder(context);      //~v@@@I~
    dlg.setTitle(NppMenu.strarrayFsubmenu[Psubmenuid]);               //~v@@@R~
    dlg.setView(layout);                                           //~v@@@I~
                                                                   //~v@@@I~
    dlg.setPositiveButton("Close",new DialogInterface.OnClickListener()//~v@@@R~
								{                                  //~v@@@I~
                                    //********************         //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
                                    	dlg.dismiss();             //~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    pListDlg=dlg.create();                                         //~v@@@R~
    listenerc.setDlg(pListDlg);                                    //~v@@@R~
//    listeners.setDlg(pListDlg);                                    //~v@@@I~
    pListDlg.show();                                               //~v@@@R~
	setDialogWidth(pListDlg);                                      //~va69I~
}//FileList                                                        //~v@@@I~
//**********************************************************************//~0A21I~//~v@@@M~
//*preference read/write                                               *//~0A21I~//~v@@@M~
//**********************************************************************//~0A21I~//~v@@@M~
private String prefIO(int Popt,String Pkey,String Pvalue)          //~0A21I~//~v@@@M~
{                                                                  //~0A21I~//~v@@@M~
	String rc;                                                     //~0A21I~//~v@@@M~
//******************                                               //~0A21I~//~v@@@M~
	SharedPreferences pref=WnpView.context.getSharedPreferences("PreferencesEx",Context.MODE_PRIVATE);//~0A21I~//~v@@@M~
    if (Popt==1)    //write                                        //~0A21I~//~v@@@M~
    {                                                              //~0A21I~//~v@@@M~
    	SharedPreferences.Editor editor=pref.edit();               //~0A21I~//~v@@@M~
        editor.putString(Pkey,Pvalue);                             //~0A21I~//~v@@@M~
        editor.commit();                                           //~0A21I~//~v@@@M~
        rc=null;                                                   //~0A21I~//~v@@@M~
    }                                                              //~0A21I~//~v@@@M~
    else                                                           //~0A21I~//~v@@@M~
    {                                                              //~0A21I~//~v@@@M~
        rc=pref.getString(Pkey,""/*default value*/);               //~0A21I~//~v@@@M~
    }                                                              //~0A21I~//~v@@@M~
    if (Dump.Y) Dump.println("ButtonDlg.prefIO key="+Pkey+",val="+Pvalue+",rc="+rc);//~va62R~
    return rc;                                                     //~0A21I~//~v@@@M~
}//prefIO                                                          //~v@@@I~
//**********************************************************************//~v@@@I~
//**********************************************************************//~v@@@I~
//**********************************************************************//~v@@@I~
//*ItemListener class In touchmode                                 //~v@@@R~
//**********************************************************************//~v@@@I~
class FileListClick implements OnItemClickListener                 //~v@@@R~
{                                                                  //~v@@@I~
	private int submenuId;                                         //~v@@@I~
	private AlertDialog dlg;                                       //~v@@@R~
    String msg;                                                    //~v@@@I~
//***********************                                          //~v@@@I~
	public FileListClick(int Psubmenuid)                           //~v@@@R~
    {                                                              //~v@@@I~
    	submenuId=Psubmenuid;                                      //~v@@@I~
    }                                                              //~v@@@I~
    //********************                                         //~v@@@I~
	public void setDlg(AlertDialog Pdlg)                           //~v@@@R~
    {                                                              //~v@@@I~
    	dlg=Pdlg;                                                  //~v@@@I~
    }                                                              //~v@@@I~
    //********************                                         //~v@@@I~
	public void onItemClick(AdapterView<?> Padapter,View Pview,int Ppos,long Pid)//~v@@@I~
    {                                                              //~v@@@I~
//    	System.out.println("FileList click="+Ppos);                //~v@@@R~
        String entryHistory=pPIndex.indexEntrySelected(0,submenuId,Ppos);//~v@@@R~
        String entryText=(String) ((TextView)Pview).getText();     //~v@@@R~
        fileListSelectedDialog(dlg,submenuId,Ppos,entryHistory,entryText);//~v@@@R~
//        Padapter.setSelection(Ppos);   //display on top of list  //~v@@@R~
//        View sv=Padapter.getSelectedView();
//        System.out.println("Pview="+Pview+",selectedView="+Padapter.getSelectedView());//~v@@@I~
//        Padapter.setSelected(true);    //hilight with setSelection//~v@@@R~
//        Padapter.requestFocusFromTouch();                        //~v@@@R~
    }                                                              //~v@@@I~
////**********************************************************************//~v@@@R~
////*ItemListener class in Non touch mode                          //~v@@@R~
////**********************************************************************//~v@@@R~
//class FileListSelected implements OnItemSelectedListener         //~v@@@R~
//{                                                                //~v@@@R~
//    private int submenuId;                                       //~v@@@R~
//    private AlertDialog dlg;                                     //~v@@@R~
////***********************                                        //~v@@@R~
//    public FileListSelected(int Psubmenuid)                      //~v@@@R~
//    {                                                            //~v@@@R~
//        submenuId=Psubmenuid;                                    //~v@@@R~
//    }                                                            //~v@@@R~
//    //********************                                       //~v@@@R~
//    public void setDlg(AlertDialog Pdlg)                         //~v@@@R~
//    {                                                            //~v@@@R~
//        dlg=Pdlg;                                                //~v@@@R~
//    }                                                            //~v@@@R~
//    //********************                                       //~v@@@R~
//    public void onItemSelected(AdapterView<?> Padapter,View Pview,int Ppos,long Pid)//~v@@@R~
//    {                                                            //~v@@@R~
//        System.out.println("FileList selected="+Ppos);           //~v@@@R~
//        Padapter.setSelection(Ppos);   //display on top of list  //~v@@@R~
//        Pview.setSelected(true);    //hilight with setSelection  //~v@@@R~
//    }                                                            //~v@@@R~
//    public void onNothingSelected(AdapterView<?> Padapter)       //~v@@@R~
//    {                                                            //~v@@@R~
//    }                                                            //~v@@@R~
//}//class FileListSelected                                        //~v@@@R~
//===============================================================================//~v@@@I~
//File list selected;open/delete/cancel dialog                     //~v@@@I~
//===============================================================================//~v@@@I~
public void fileListSelectedDialog(final AlertDialog Pparent,final int PsubmenuId,final int Ppos,final String PentryHistory,final String PentryText)//~v@@@R~
{                                                                  //~v@@@I~
	String msg;                                                    //~v@@@I~
//***********                                                      //~v@@@I~
    AlertDialog.Builder builder=new AlertDialog.Builder(WnpView.context);//~v@@@I~
    if (PentryHistory!=null)                                       //~v@@@I~
    {                                                              //~v@@@I~
        if (PsubmenuId==CPattern.FILE_LIST_TIMESEQ)      //old timeseq//~v@@@I~
    		msg=WnpView.contextR.getString(R.string.QOpenDelete)+PentryHistory.substring(0,CPIndex.KEY_LEN)+":"+PentryText;//~v@@@I~
        else                                                       //~v@@@I~
    		msg=WnpView.contextR.getString(R.string.QOpenDelete)+PentryText;//~v@@@R~
    }                                                              //~v@@@I~
    else                                                           //~v@@@I~
        msg=WnpView.contextR.getString(R.string.ErrNoListData)+PentryText;//~v@@@R~
    builder.setMessage(msg);                                       //~v@@@I~
    if (PentryHistory!=null)                                       //~v@@@I~
    {                                                              //~v@@@I~
    	builder.setPositiveButton("Open",new DialogInterface.OnClickListener()//~v@@@R~
								{                                  //~v@@@I~
                                                                   //~v@@@I~
    								public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
//  								    fileListEntryProcessOption=1;	//open//~v@@@R~
                						pBoard.pPat.SerializeFromIndex(PentryHistory);//~v@@@R~
                						pBoard.Flags|=Board.F_READINDX; //for craeteboard//~v@@@I~
                						OnRestore();    //-->wnpview,createboard//~v@@@I~
                                    	pDlgfl.dismiss();          //~v@@@R~
                                        Pparent.dismiss();         //~v@@@I~
        								String msg2=PentryText+WnpView.contextR.getString(R.string.InfoOpened);//~v@@@R~
        								Toast.makeText(WnpView.context,msg2,Toast.LENGTH_SHORT).show();//~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    }                                                              //~v@@@I~
    builder.setNeutralButton("Delete",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                	public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
//  								    fileListEntryProcessOption=-1;	//delete//~v@@@R~
                						int rc=pPIndex.deleteIndexEntry(PsubmenuId,Ppos);//~v@@@R~
                        				if (rc==1)	//deleted      //~v@@@I~
                                        {                          //~v@@@I~
                                			pDlgfl.dismiss();      //~v@@@R~
                                        	Pparent.dismiss();     //~v@@@R~
        									String msg2=PentryText+WnpView.contextR.getString(R.string.InfoDeleted);//~v@@@R~
        									Toast.makeText(WnpView.context,msg2,Toast.LENGTH_SHORT).show();//~v@@@R~
                                        }                          //~v@@@I~
                                        else                       //~v@@@I~
                                			pDlgfl.dismiss();      //~v@@@I~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    builder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()//~v@@@I~
								{                                  //~v@@@I~
                                	public void onClick(DialogInterface dlg,int buttonID)//~v@@@I~
                                    {                              //~v@@@I~
//  								    fileListEntryProcessOption=0;	//cancel//~v@@@R~
                                		pDlgfl.dismiss();          //~v@@@I~
//      								String msg2=PentryText+WnpView.contextR.getString(R.string.InfoCanceled);//~v@@@R~
//      								Toast.makeText(WnpView.context,msg2,Toast.LENGTH_SHORT).show();//~v@@@R~
                                    }                              //~v@@@I~
                                }                                  //~v@@@I~
                          );                                       //~v@@@I~
    pDlgfl=builder.create();                                       //~v@@@R~
    pDlgfl.requestWindowFeature(Window.FEATURE_NO_TITLE);          //~v@@@R~
    pDlgfl.show();                                                 //~v@@@R~
}//fileListSelectedDialog                                          //~v@@@I~
}//class FileListClick                                             //~v@@@M~
    //**********************************************                   //~v@@@R~//~va41R~
    public static boolean isNoBGM()                                //~va41I~
    {                                                              //~va41I~
    	if (optBGM<0)                                              //~va41I~
        	optBGM=getOptBGM();                                    //~va41I~
    	boolean swNoBGM=optBGM==0;                                 //~va41I~
        if (Dump.Y) Dump.println("ButtonDlg.isNoBGM swNoBGM="+swNoBGM);//~va41I~
        return swNoBGM;                                            //~va41I~
    }                                                              //~va41I~
    //**********************************************               //~va41I~
    public static int getOptBGM()                                  //~va41I~
    {                                                              //~va41I~
        int opt= Utils.getPreference(PKEY_BGM,1/*default on*/);     //~va41I~
        if (Dump.Y) Dump.println("ButtonDlg.getOptBGM opt="+opt);  //~va41I~
        return opt;                                                //~va41I~
    }                                                              //~va41I~
    //**********************************************               //~va41I~
    public static float getBGMVolume()                             //~va41I~
    {                                                              //~va41I~
        float vol=(float)0.9;                                      //~va41I~
        if (Dump.Y) Dump.println("ButtonDlg.getBGMVolume vol="+vol);//~va41I~
        return vol;                                                //~va41I~
    }                                                              //~va41I~
    //**********************************************               //~va69I~
    //*from filememu button                                        //~va69I~
    //**********************************************               //~va69I~
    public void updateMemo()                                       //~va69I~
    {                                                              //~va69I~
        if (Dump.Y) Dump.println("ButtonDlg.updateMemo");          //~va69I~
		PostMessage(Board.IDC_UPDATE_MEMO);                        //~va69I~
    }                                                              //~va69I~
    //**********************************************               //~va69I~
	private void setDialogWidth(AlertDialog Pdlg)                       //~va69I~
    {                                                              //~va69I~
        if (Dump.Y) Dump.println("ButtonDlg.setDialogWidth");      //~va69I~
    	Rect rect=new Rect();                                      //~va69I~
        Window window=AG.activity.getWindow();                     //~va69I~
        window.getDecorView().getWindowVisibleDisplayFrame(rect);  //~va69I~
        if (Dump.Y) Dump.println("ButtonDlg.setDialogWidth rect="+rect+",width="+rect.width()+",height="+rect.height());//~va69I~//~va70R~
      if (rect.width()<rect.height())                              //~va70I~
        Pdlg.getWindow().setLayout((int)(rect.width()*0.98f), ViewGroup.LayoutParams.WRAP_CONTENT);//~va69R~
    }                                                              //~va69I~
}//class                                                           //~v@@@R~
