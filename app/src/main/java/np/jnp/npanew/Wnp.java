//CID://+va44R~:                                                   //~va43R~//~va44R~
//*****************************************************************//~0A08I~
//va44:200525 display overflow when scrHeigh=1024                  //~va44I~
//va43:200525 display.getwidth() deprecated                        //~va43I~
//va30:120717 (NPA21 fontsize depending screen size)               //~2717M~
//va23:051221 langauge ctl(English and japanese)                   //~va23I~//~2717M~
//va22:051116 board change;current path parameter format difference between dos and unix//~va22I~//~2717M~
//va18:051113 (BUG)MRU list reverse sequence when loaded           //~va08I~//~2717M~
//va01:051013 5*5 support                                          //~va01R~//~2717M~
//*****************************************************************//~va01I~//~2717M~
package np.jnp.npanew;                                                //~2718R~//~va44R~
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.view.Window;

import np.jnp.npanew.R;
import np.jnp.npanew.utils.AG;                                     //~va44R~
import np.jnp.npanew.utils.Dump;                                   //~va44R~
import np.jnp.npanew.utils.Utils;                                  //~va44R~

import static np.jnp.npanew.utils.BGMList.*;

//*************************************************************
public class  Wnp
{
public static final int SYSTEMBAR_HEIGHT=48;                   //~vab0I~//~2717I~
    public static int osVersion=Build.VERSION.SDK_INT;                //~vab0I~//~2717I~
    public static final int GINGERBREAD=9; //android2.3            //~vab7I~//~2717I~
    public static final int HONEYCOMB=11; //android3.0 (GINGERBREAD=9)//~vab0I~//~2717I~
    public static final int ICE_CREAM_SANDWICH=14; //android4.0    //~vab0I~//~2717I~
public static final int BOARDTYPE3=3;                              //~va01I~
public static final int BOARDTYPE4=4;                              //~va01I~
public static final int BOARDTYPE5=5;                              //~va01R~
public static int BOARDTYPE=BOARDTYPE3;                            //~va01R~
//public static final String MY_SIGN     ="木八小 学";               //~5922I~//~0A09R~//~0523R~
public static         int MAP_SIZE;//=BOARDTYPE*BOARDTYPE;         //~va01R~
public static         int PEG_MAX;//=MAP_SIZE*MAP_SIZE;  //25x25=625//~va23R~
                                                                   //~5922I~
public static       int   OBJECT_SIZE;//    =32-(BOARDTYPE-BOARDTYPE3)*2;//~va01R~//~0915R~
public static final   int GAP             =3;                      //~0915R~
public static final   int LAYOUT_VGAP     =1;   //clientpanel vitrticalgap//~va01R~
public static final   int LAYOUT_HGAP     =2;   //clientpanel vitrticalgap//~va01I~
public static final int BOARD_MARGIN   =3;                         //~va01R~//~0A09R~
public static final int FOCUS_LINE_WIDTH  =1;                      //~0915I~
public static final int STATUS_LINECTR=5;	//sat1,stat2(3 including num button),sign//~0B03R~
                                                                   //~5922I~
public static       int BTN_W;//          =OBJECT_SIZE;            //~va01R~
public static       int BTN_H;//          =OBJECT_SIZE;            //~va01R~
//public static       int BTNSBOX_H;//         =(BTN_H+GAP)*MAP_SIZE+GAP;//~va01R~//~va44R~
public static       int BTNSBOX_W;//         =BTN_W+GAP+GAP;       //~va01R~
public static       int BTN0BOX_W;//         =BTNSBOX_W;           //~va01R~
public static       int BTN0BOX_H;//         =BTN_H;               //~va01R~
                                                                   //~5923I~
public static       int BTN_NUM_X;   // =BOARD_MARGIN;              //~5923R~//~0B02R~
public static       int BTN_NUM_Y;   // =BOARD_MARGIN;              //~5922I~//~0B02R~
public static       int BTN_NUM_GAP;                               //~0B02I~
public static       int BTN_NUM_W;                                 //~0B02I~
public static       int BTN_NUM_H;                                 //~0B02I~
                                                                   //~5922I~
public static       int NAME_HEIGHT;//     =BTN0BOX_H;             //~va01R~
public static       double NAME_HEIGHT_RATE=0.7;//                 //~va44I~
public static       int NAME_LINEH,NAME_TEXTSZ;                    //~0A08I~
                                                                   //~va01I~
public static       int LEVEL_W=30;                                //~va01R~
public static       int LEVEL_H;//=NAME_HEIGHT;                    //~va01R~
public static       int STATUS_LINEH,STATUS_TEXTSZ;//lines bellow button//~0B03R~
                                                                   //~va01I~
                                                                   //~5922I~
public static       int   BOARD_LEFT;//      =(BOARD_MARGIN+BOARD_MARGIN);//~va01R~//~0915R~
public static       int   BOARD_TOP;//   =(BOARD_MARGIN+BOARD_MARGIN);             //~va01R~//~0915R~//~0A09R~
public static       int BOARD_WIDTH;//     =( ( OBJECT_SIZE + GAP ) * MAP_SIZE + GAP );//~va01R~
public static       int BOARD_HEIGHT;//    =( ( OBJECT_SIZE + GAP ) * MAP_SIZE + GAP );//~va01R~
public static       int BOARD_BOTTOM;                                                                   //~5922I~
//public static final int SIGN_HEIGHT     = 16;                      //~5923R~//~2718R~
public static       int SIGN_HEIGHT;                               //~2718I~
                                                                   //~5922I~
public static final int TOOLBAR_HEIGHT  =0;                        //~va01R~
public static final int STATUSBAR_HEIGHT =20;                      //~va01R~
public static double RATE_ABOUTDLG=0.95;                           //~0524I~
                                                                   //~5922I~
public static final int NUMTEXT_MARGIN  =1;                        //~5923R~
public static       int NUMTEXT_SIZE;//    =(OBJECT_SIZE);         //~va01R~
public static final int MEMOTEXT_MARGIN =1;                        //~5923I~//~0A31R~
public static       int MEMOTEXT_SIZE;//   =((OBJECT_SIZE-MEMOTEXT_MARGIN*2)/2);//~va01R~
public static       int MEMOTEXT10_SIZE;                           //~va08I~
//private static final String PROP_CURDIR="fcCurrentDir";               //~5A07I~
//private static final String PROP_POSTFIX=".properties";             //~5A07I~
public	static      int BOARDPANE_W;//=BOARD_WIDTH+BOARD_LEFT*2;   //~va01R~
//public	static      int BOARDPANE_H;//=BOARD_HEIGHT+BOARD_TOP*1;   //~va01R~//~va44R~
//public	static      int FRAMEC_H;//=NAME_HEIGHT+BOARDPANE_H+SIGN_HEIGHT+LAYOUT_VGAP*4;//~va01R~//~0B01R~
//public	static      int FRAMEC_W;//=BOARDPANE_W+BOARD_MARGIN*2+LAYOUT_HGAP+BTNSBOX_W;//~va01R~//~0B01R~
public  static      int FRAME_W;//=FRAMEC_W+40;   //frame width for 3*3//~va01R~//~0A08R~
public  static      int FRAME_H;//=FRAMEC_H+STATUSBAR_HEIGHT+60;   //~va01R~//~0A08R~
                                                                   //~0A07I~
public static       int L_WIDTH;                                   //~0B01I~
public static       int L_FILENAME_Y,L_FILENAME_X,L_LEVEL_Y,L_LEVEL_X,L_STATUS1_Y,L_STATUS1_X,L_STATUS2_Y,L_STATUS2_X;//~0A07R~//~0A08R~
public static       int L_SIGN_Y,L_BUTTONS_Y,L_BUTTONS_X,BUTTONS_W;          //~0A09R~//~0B01R~//~0B03R~
                                                                   //~va01I~
public  static      int NAME_WIDTH      =BOARDPANE_W-LEVEL_W;      //~va01R~
public  static      Thread ThreadRun;                              //~va01I~
public  static      boolean Sjlang=false;                          //~va23I~
public  static final Typeface Sfontnamee=Typeface.MONOSPACE;//SANS_SERIFString  Sfontnamee="Dialog";                  //~va23I~
public  static final Typeface Sfontstyle_BTN_NUM=Typeface.DEFAULT_BOLD;//~0B03I~
//public  static final Typeface Sfontnamej=Typeface.SERIF;//Mincho String  Sfontnamej="Dialog";                  //~va23R~//~0B03R~
//public  static final Typeface Sfontnameg=Typeface.SANS_SERIF;//gothic//~0B03R~
public  static      Typeface  Sfontname=Sfontnamee;                  //~va23I~
public  static      String  Slangparm=null;                        //~va23I~
public  static boolean Sswportrate;                                //~0B01I~
//*********************************************                    //~5922I~
//public  static JLabel LabelStatus1 = null;                         //~5922I~//~v@@@R~
//public  static JLabel LabelStatus2 = null;                         //~5922I~//~v@@@R~
//public  static  wnp awnp;                                     //~5925R~//~v@@@R~
//    public  Cursor[] cursortbl;                                    //~5925R~//~v@@@R~
//    private View aView=null;                                     //~v@@@R~
//    public String fcCurrentDir=null;                               //~5A07I~//~v@@@R~
//    //~5925I~                                                    //~v@@@R~
//    public void actionPerformed(ActionEvent e) {                 //~v@@@R~


//    }                                                            //~v@@@R~

    private Context context;                                       //~v@@@I~
    private NppView nppView;                                       //~v@@@I~
    private int hhActionBar;                                       //~va44I~
//********************************************************************//~0B01I~//~0C06R~
// This is the default constructor                                 //~0B01I~
//********************************************************************//~0B01I~
	public Wnp(Context Pcontext,NppView PnppView,int PboardType)   //~v@@@R~//~0A08R~
    {                                                              //~0C06I~
    	BOARDTYPE=PboardType;                                  //~v@@@I~//~0C06I~
    	context=Pcontext;                                     //~v@@@I~//~0C06I~
    	nppView=PnppView;                                      //~v@@@I~//~0A08R~//~0C06I~
        AG.aBGMList.play(SOUNDID_BGM_START);                        //~va40I~//~va44I~
    	initialize();                                              //~0C06I~//~va44R~
    }                                                              //~0C06I~
//********************************************************************//~0C06I~
//  private	void initialize()                                           //~0C06I~//~va44R~
    public	void initialize(int PhhActionBar)                      //~va44I~
    {                                                              //~va44I~
    	if (Dump.Y) Dump.println("Wnp.initialize PhhActionBar="+PhhActionBar);//~va44I~
    	hhActionBar=PhhActionBar;                                  //~va44I~
	    initialize();                                              //~va44I~
    }                                                              //~va44I~
    public	void initialize()                                      //~va44I~
    {                                                              //~0C06I~
            int boardwidth;                                        //~0B01I~
//**********************************                               //~0B01I~
                                                                   //~v@@@I~
			MAP_SIZE       =BOARDTYPE*BOARDTYPE;                   //~va01I~
			PEG_MAX        =MAP_SIZE*MAP_SIZE;  //25x25=625 ぺグの最大数//~va01I~//~0523R~
//  		FRAME_W=nppView.getWidth();                                //~v@@@I~//~0915I~//~0B01M~//~2717R~
//  		FRAME_H=nppView.getHeight();                               //~v@@@I~//~0915I~//~0B01M~//~2717R~
			getScreenSize();                                       //~2717I~
//  		OBJECT_SIZE    =(FRAME_W-BOARD_LEFT*2-GAP)/MAP_SIZE-GAP;//~0915R~//~0B01R~
//          BOARD_LEFT     =(int)((FRAME_W-GAP-(OBJECT_SIZE+GAP)*MAP_SIZE)/2+GAP);		//shift to center//~0B01I~
            if (FRAME_W<FRAME_H)                                   //~0B01I~
            {                                                      //~0B01I~
            	boardwidth=FRAME_W;                                //~0B01I~
                Sswportrate=true;                                  //~0B01I~
            }                                                      //~0B01I~
            else                                                   //~0B01I~
            {                                                      //~0B01I~
            	boardwidth=FRAME_H;                                //~0B01I~
                Sswportrate=false;                                 //~0B01I~
            }                                                      //~0B01I~
    		OBJECT_SIZE    =(boardwidth-BOARD_MARGIN*2)/MAP_SIZE-GAP;//~0B01R~
            BOARD_LEFT     =(int)(boardwidth-((OBJECT_SIZE+GAP)*MAP_SIZE-GAP))/2;//~0B01R~
			BOARD_WIDTH    =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va01I~//~0B01R~
			BOARD_HEIGHT   =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va01I~//~0B01R~
			BOARDPANE_W    =(int) boardwidth;                      //~0B01I~
                                                                   //~0A09I~
            BTN_NUM_GAP    =GAP;                                   //~0B02R~
//  		NAME_LINEH     =OBJECT_SIZE-GAP*2;                     //~0A08R~//~0A09M~//~0B03M~//~va44R~
    		NAME_LINEH     =(int)(OBJECT_SIZE*NAME_HEIGHT_RATE);   //~va44I~
    		NAME_HEIGHT    =NAME_LINEH-GAP*2-BOARD_MARGIN;         //~va44I~
    		NAME_TEXTSZ    =NAME_HEIGHT;                           //~va44I~
          if (Sswportrate)                                         //~0B01R~
          {                                                        //~0B01I~
            L_FILENAME_X   =0;                                 //~0A07R~//~0A08R~//~0A09M~
			L_FILENAME_Y   =BOARD_MARGIN;                          //~0A09M~
//			BOARD_TOP      =L_FILENAME_Y+NAME_LINEH+LAYOUT_VGAP*2; //~0A09R~//~va44R~
  			BOARD_TOP      =NAME_LINEH;                            //~va44R~
          }                                                        //~0B01I~
          else                                                     //~0B01I~
          {                                                        //~0B01I~
            L_FILENAME_X   =BOARDPANE_W;                           //~0B01I~
			L_FILENAME_Y   =BOARD_MARGIN;                          //~0B01I~
			BOARD_TOP      =(FRAME_H-BOARD_HEIGHT)/2;              //~0B01R~
          }                                                        //~0B01I~
                                                                   //~0915I~
			BTN_W          =(int) OBJECT_SIZE;                           //~va01I~
			BTN_H          =(int) OBJECT_SIZE;                           //~va01I~
//  		BTNSBOX_H      =(BTN_H+GAP)*MAP_SIZE+GAP;              //~va01I~//~va44R~
			BTNSBOX_W      =BTN_W+GAP+GAP;                         //~va01I~
			BTN0BOX_W      =BTNSBOX_W;                             //~va01I~
			BTN0BOX_H      =BTN_H;                                 //~va01I~
//  		NAME_HEIGHT    =BTN0BOX_H;                             //~va01I~//~0A08R~//~va44R~
//  		NAME_TEXTSZ    =NAME_LINEH-LAYOUT_VGAP*2;                //~0A08R~//~0A09R~//~va44R~
			LEVEL_H        =NAME_HEIGHT;                           //~va01I~
			NUMTEXT_SIZE   =(int) (OBJECT_SIZE);                         //~va01I~
			MEMOTEXT_SIZE  =(int) ((OBJECT_SIZE-MEMOTEXT_MARGIN*1)/2);   //~va01I~//~0A31R~
			MEMOTEXT10_SIZE  =MEMOTEXT_SIZE-2;                     //~va08R~
//  		BOARDPANE_H    =(int) (BOARD_HEIGHT+BOARD_TOP*1);              //~va01I~//~va44R~
//  		FRAMEC_H       =NAME_HEIGHT+BOARDPANE_H+SIGN_HEIGHT+LAYOUT_VGAP*4;//~va01I~//~0B01R~
//  		FRAMEC_W       =BOARDPANE_W+BOARD_MARGIN*2+LAYOUT_HGAP+BTNSBOX_W;//~va01I~//~0B01R~
//  		FRAME_W        =FRAMEC_W+40;   //frame width for 3*3   //~va01I~//~0A09R~
//  		FRAME_H        =FRAMEC_H+STATUSBAR_HEIGHT+60;          //~va01I~//~0A09R~
			NAME_WIDTH     =BOARDPANE_W-LEVEL_W;                   //~va01I~
            BOARD_BOTTOM   =BOARD_TOP+BOARD_HEIGHT;                //~0A09I~
                                                                   //~0A07I~
          if (Sswportrate)                                         //~0B01I~
          {                                                        //~0B01I~
          	L_WIDTH        =FRAME_W;                               //~0B01I~
            L_LEVEL_Y      =L_FILENAME_Y;                          //~0A09R~
			L_BUTTONS_X    =BOARD_LEFT;                            //~0B01I~
            L_BUTTONS_Y    =BOARD_BOTTOM+LAYOUT_VGAP*2;              //~0A09I~//~0B01R~
            L_STATUS1_Y    =BOARD_BOTTOM+LAYOUT_VGAP+BTN0BOX_H;    //~0A09R~
            STATUS_LINEH=(FRAME_H-L_STATUS1_Y)/STATUS_LINECTR;     //~0B03I~
            L_STATUS1_X    =0;                                 //~0A07R~//~0A08R~
            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;           //~0A07R~//~0A08R~//~0B03R~
            L_STATUS2_X    =0;                                 //~0A07R~//~0A08R~
//          L_SIGN_Y       =L_STATUS2_Y+STATUS_LINEH*4;              //~0A09I~//~0B03R~
            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                  //~0B03R~
            BTN_NUM_X      =BOARD_LEFT;                            //~0B02M~
//          BTN_NUM_Y      =BOARD_MARGIN;                          //~0B02M~//~0B03R~
            BTN_NUM_Y      =L_STATUS2_Y+STATUS_LINEH*2;            //~0B03R~
            BTN_NUM_H      =STATUS_LINEH;                            //~0B02M~//~0B03R~
            BTN_NUM_W      =(FRAME_W-BTN_NUM_X)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~0B03I~
    		BUTTONS_W	   =BOARDPANE_W;                           //~0B03I~
          }                                                        //~0B01I~
          else                                                     //~0B01I~
          {                                                        //~0B01I~
          	L_WIDTH        =FRAME_W-BOARDPANE_W;                   //~0B01I~
            L_LEVEL_Y      =L_FILENAME_Y;                          //~0B01I~
			L_BUTTONS_X    =BOARDPANE_W;                          //~0B01I~
//  		L_BUTTONS_Y    =L_FILENAME_Y+NAME_LINEH+LAYOUT_VGAP*2; //~0B01I~//~va44R~
    		L_BUTTONS_Y    =NAME_HEIGHT;                              //~va44I~
            L_STATUS1_Y    =L_BUTTONS_Y+BTN0BOX_H+LAYOUT_VGAP*2;   //~0B01I~
            STATUS_LINEH=NAME_LINEH;                                 //~0B03I~
            L_STATUS1_X    =BOARDPANE_W;                           //~0B01I~
            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;                //~0B01I~//~0B03R~
            L_STATUS2_X    =BOARDPANE_W;                           //~0B01I~
            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                    //~0B01R~//~0B03R~
            BTN_NUM_X      =BOARDPANE_W;                           //~0B02M~
            BTN_NUM_Y      =L_SIGN_Y-(NAME_LINEH+LAYOUT_VGAP*2);   //~0B02R~
            BTN_NUM_H      =STATUS_LINEH;                            //~0B02I~//~0B03R~
            BTN_NUM_W      =(FRAME_W-BOARDPANE_W)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~0B02M~//~0B03R~
    		BUTTONS_W      =L_WIDTH;                               //~0B03I~
          }                                                        //~0B01I~
			STATUS_TEXTSZ    =STATUS_LINEH-LAYOUT_VGAP*2;          //~0B03I~
			SIGN_HEIGHT=STATUS_TEXTSZ;                              //~2718I~
                                                                   //~0B01I~
               //~0A09I~
	}
//*************************                                        //~1606I~//~2717I~
	public void getScreenSize()                                     //~1606I~//~2717I~
    {                                                              //~1606I~//~2717I~
        int w,h,bottomSpaceHeight=0;                                                   //~1606I~//~2717R~
//************************                                         //~2717I~
//      Display display=((WindowManager)(NppView.context.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();//~1606I~//~2717R~//~va43R~
//      int displayW=display.getWidth();                          //~1606I~//~2717I~//~va43R~
//      int displayH=display.getHeight();                         //~1606I~//~2717I~//~va43R~
        int displayW=AG.scrWidth;                                  //~va43I~//~va44R~
        int displayH=AG.scrHeight;                                 //~va43I~//~va44R~
    	if (Dump.Y) Dump.println("Wnp.getScreenSize displayW="+displayW+",displayH="+displayH);//+va44I~
//      int displayW=nppView.layoutWidth;                          //~va44R~
//      int displayH=nppView.layoutHeight;                         //~va44R~
//*********                                                        //~2717I~
//*calc by button height and title size                            //~1606I~//~2717I~
	    int th=getTitleBarHeight();                                       //~1606M~//~2717I~
		w=displayW;                                           //~1606R~//~2717I~
        h=displayH-th;                                             //~2717I~
        if (osVersion>=HONEYCOMB && osVersion<ICE_CREAM_SANDWICH)  //android3 api11-13//~vab0R~//~2717I~
        	bottomSpaceHeight=SYSTEMBAR_HEIGHT;                    //~vab0I~//~2717I~
        h-=bottomSpaceHeight;                                 //~vab0I~//~2717R~
  		FRAME_W=w;                                                 //~2717I~
  		FRAME_H=h;                                                 //~2717I~
    	if (Dump.Y) Dump.println("Wnp.getScreenSize FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H+",bottomSpaceHeight="+bottomSpaceHeight);//+va44I~
    }                                                              //~1606I~//~2717I~
    public int getTitleBarHeight()                            //~1606I~//~2717I~
    {                                                              //~1606I~//~2717I~
//        Rect rect=new Rect();                                      //~1606I~//~2717I~//~va44R~
//        Window w=NppView.activity.getWindow();                          //~1606I~//~2717I~//~va44R~
//        View v=w.getDecorView();                                   //~1606I~//~2717I~//~va44R~
//        v.getWindowVisibleDisplayFrame(rect);                      //~1606I~//~2717I~//~va44R~
//        v=w.findViewById(android.view.Window.ID_ANDROID_CONTENT);  //~1606I~//~2717I~//~va44R~
//        return v.getTop();                            //~1606I~    //~2717I~//~va44R~
    	if (Dump.Y) Dump.println("Wnp.getTitleBarHeight rc="+hhActionBar);//+va44I~
        return hhActionBar;                                  //~va44I~
    }                                                              //~1606I~//~2717I~

//*******************************************************          //~0C06R~
public void orientationChanged()                                   //~0C06I~
{                                                                  //~0C06I~
//***********                                                      //~0C06I~
	initialize();                                                  //~0C06I~
}                                                                  //~0C06I~
//*******************************************************          //~0C06I~
public void OnAbout()                                                  //~5929I~//~v@@@R~//~0A10R~
{                                                                  //~5929I~//~v@@@R~//~0A10R~
//	String  AboutStr,AboutTitle;                                   //~0B01R~
//***********                                                      //~0A10I~
    final Dialog dlg=new Dialog(context);                                       //~0A10I~//~0B01R~
//  dlg.setTitle(context.getText(R.string.HelpAboutTitle).toString());//~0A10I~//~0B01R~
    dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);             //~0B01I~
    dlg.setContentView(R.layout.dlgabout);                         //~0A10I~//~0B01R~
    Button bv=(Button)dlg.findViewById(R.id.AboutClose);    //~0A10I~//~0B01R~//~0B02R~
    bv.setOnClickListener(new OnClickListener()      //~0B02I~
								{                                  //~0B02I~
                                    public void onClick(View view)//~0B02I~
                                    {                              //~0B02I~
                                    	dlg.dismiss();             //~0B02I~
                                    }                              //~0B02I~
                                }                                  //~0B02I~
                        );                                         //~0B02I~
    Utils.setDialogWidth(dlg,RATE_ABOUTDLG);                       //~0524I~
    dlg.show();                                                    //~0A10I~//~0B01R~
//    LayoutInflater factory=LayoutInflater.from(WnpView.context); //~0B01I~
//    final View view=factory.inflate(R.layout.dlgabout,null);     //~0B01I~
//    AlertDialog.Builder builder=new AlertDialog.Builder(WnpView.context);//~0B01I~
//    builder.setView(view);                                       //~0B01I~
//    builder.setPositiveButton("OK",new DialogInterface.OnClickListener()//~0B01I~
//                                {                                //~0B01I~
                                                                   //~0B01I~
//                                    public void onClick(DialogInterface dlg,int buttonID)//~0B01I~
//                                    {                            //~0B01I~
//                                        optionOnOK();            //~0B01I~
//                                        dlg.dismiss();           //~0B01I~
//                                    }                            //~0B01I~
//                                }                                //~0B01I~
//                          );                                     //~0B01I~
//    Dialog  dlg=builder.create();                                //~0B01I~
//    dlg.show();                                                  //~0B01I~
}//OnAbout                                                                  //~5929I~//~v@@@R~//~0A10R~
}//Class                                                           //~0B01R~
