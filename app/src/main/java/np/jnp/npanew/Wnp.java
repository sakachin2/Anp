//CID://+va90R~:                                                   //~va90R~
//*****************************************************************//~0A08I~
//va90 240219 selectable BGM                                       //~va90R~
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
private static final double RATE_SQUARE=1.4; //if width/height<1.2 assume portrait//~va90R~
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
public static       int BUTTON_LEFT;                               //~va90R~
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
public  static int barT,barB,barL,barR;                            //~va90R~
public static boolean swSquare;                                    //~va90R~
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
//  private int hhActionBar;                                       //~va44I~//~va90R~
    public static int hhActionBar;                                 //~va90R~
    private boolean swAdjustObjectSize;                            //~va90I~
//********************************************************************//~0B01I~//~0C06R~
// This is the default constructor                                 //~0B01I~
//********************************************************************//~0B01I~
	public Wnp(Context Pcontext,NppView PnppView,int PboardType)   //~v@@@R~//~0A08R~
    {                                                              //~0C06I~
    	BOARDTYPE=PboardType;                                  //~v@@@I~//~0C06I~
    	context=Pcontext;                                     //~v@@@I~//~0C06I~
    	nppView=PnppView;                                      //~v@@@I~//~0A08R~//~0C06I~
//      AG.aBGMList.play(SOUNDID_BGM_START); //play from resume                       //~va40I~//~va44I~//~va90R~
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
			PEG_MAX        =MAP_SIZE*MAP_SIZE;  //25x25=625        //~va90R~
//  		FRAME_W=nppView.getWidth();                                //~v@@@I~//~0915I~//~0B01M~//~2717R~
//  		FRAME_H=nppView.getHeight();                               //~v@@@I~//~0915I~//~0B01M~//~2717R~
			getScreenSize();                                       //~2717I~
//  		OBJECT_SIZE    =(FRAME_W-BOARD_LEFT*2-GAP)/MAP_SIZE-GAP;//~0915R~//~0B01R~
//          BOARD_LEFT     =(int)((FRAME_W-GAP-(OBJECT_SIZE+GAP)*MAP_SIZE)/2+GAP);		//shift to center//~0B01I~
    		if (Dump.Y) Dump.println("Wnp.initialize FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H);//~va90R~
        	swSquare=isSquare();                                   //~va90I~
            adjustEdgeModeWH();                                    //~va90R~
//          if (FRAME_W<FRAME_H)                                   //~0B01I~//~va90R~
            if (AG.scrHeightReal>AG.scrWidthReal)                //~va90I~
            {                                                      //~0B01I~
            	boardwidth=FRAME_W;                                //~0B01I~
                Sswportrate=true;                                  //~0B01I~
            }                                                      //~0B01I~
            else                                                   //~0B01I~
            {                                                      //~0B01I~
                if (!swSquare)                                     //+va90I~
    				if (AG.swEdgeToEdgeMode)                       //+va90R~
	                	FRAME_H-=barT;	//actionbar height         //+va90R~
            	boardwidth=FRAME_H;                                //~0B01I~
                Sswportrate=false;                                 //~0B01I~
            }                                                      //~0B01I~
    		if (Dump.Y) Dump.println("Wnp.initialize boardwidth="+boardwidth+",Sswportrait="+Sswportrate);//~va90R~
    		OBJECT_SIZE    =(boardwidth-BOARD_MARGIN*2)/MAP_SIZE-GAP;//~0B01R~
            if (swSquare)                                          //~va90I~
            {                                                      //~va90I~
            	if (Sswportrate)                                   //~va90R~
	            	OBJECT_SIZE=adjustObjectSize();                //~va90R~
            	else                                               //~va90R~
		            boardwidth=adjustObjectSizeSquareLandscape();  //~va90R~
            }                                                      //~va90I~
            BOARD_LEFT     =(int)(boardwidth-((OBJECT_SIZE+GAP)*MAP_SIZE-GAP))/2;//~0B01R~
        BOARD_LEFT+=barL;                                          //~va90R~
			BOARD_WIDTH    =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va01I~//~0B01R~
			BOARD_HEIGHT   =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va01I~//~0B01R~
			BOARDPANE_W    =(int) boardwidth;                      //~0B01I~
    		if (Dump.Y) Dump.println("Wnp.initialize OBJECT_SIZE="+OBJECT_SIZE+",BOARD_LEFT="+BOARD_LEFT+",BOARD_WIDTH="+BOARD_WIDTH+",BOARD_HEIGHT="+BOARD_HEIGHT+",BOARDPANE_W="+BOARDPANE_W);//~va90R~
                                                                   //~0A09I~
            BTN_NUM_GAP    =GAP;                                   //~0B02R~
//  		NAME_LINEH     =OBJECT_SIZE-GAP*2;                     //~0A08R~//~0A09M~//~0B03M~//~va44R~
    		NAME_LINEH     =(int)(OBJECT_SIZE*NAME_HEIGHT_RATE);   //~va44I~
    		NAME_HEIGHT    =NAME_LINEH-GAP*2-BOARD_MARGIN;         //~va44I~
    		NAME_TEXTSZ    =NAME_HEIGHT;                           //~va44I~
    		if (Dump.Y) Dump.println("Wnp.initialize NAME_LINEH="+NAME_LINEH+",NAME_HEIGHT="+NAME_HEIGHT+",NAME_TEXTSZ="+NAME_TEXTSZ);//~va90R~
          if (Sswportrate)                                         //~0B01R~
          {                                                        //~0B01I~
            L_FILENAME_X   =0;                                 //~0A07R~//~0A08R~//~0A09M~
			L_FILENAME_Y   =BOARD_MARGIN;                          //~0A09M~
//			BOARD_TOP      =L_FILENAME_Y+NAME_LINEH+LAYOUT_VGAP*2; //~0A09R~//~va44R~
  			BOARD_TOP      =NAME_LINEH;                            //~va44R~
          }                                                        //~0B01I~
          else                                                     //~0B01I~
          if (swSquare)                                            //~va90R~
          {                                                        //~va90I~
            L_FILENAME_X   =0;                                     //~va90I~
			L_FILENAME_Y   =BOARD_MARGIN;                          //~va90I~
  			BOARD_TOP      =NAME_LINEH;                            //~va90I~
          }                                                        //~va90I~
          else                                                     //~va90I~
          {                                                        //~0B01I~
            L_FILENAME_X   =BOARDPANE_W;                           //~0B01I~
			L_FILENAME_Y   =BOARD_MARGIN;                          //~0B01I~
			BOARD_TOP      =(FRAME_H-BOARD_HEIGHT)/2;              //~0B01R~
          }                                                        //~0B01I~
    		if (Dump.Y) Dump.println("Wnp.initialize L_FILENAME_X="+L_FILENAME_X+",L_FILENAME_Y="+L_FILENAME_Y+",BOARD_TOP="+BOARD_TOP);//~va90R~
        L_FILENAME_X+=barL;                                        //~va90R~
		L_FILENAME_Y+=barT;                                        //~va90R~
  		BOARD_TOP+=barT;                                           //~va90R~
    		if (Dump.Y) Dump.println("Wnp.initialize L_FILENAME_X="+L_FILENAME_X+",L_FILENAME_Y="+L_FILENAME_Y+",BOARD_TOP="+BOARD_TOP);//~va90R~
                                                                   //~0915I~
			BTN_W          =(int) OBJECT_SIZE;                           //~va01I~
        	BTN_W=(FRAME_W-GAP*2)/ButtonDlg.BUTTON_CTR;            //~va90I~
			BTN_H          =(int) OBJECT_SIZE;                           //~va01I~
//  		BTNSBOX_H      =(BTN_H+GAP)*MAP_SIZE+GAP;              //~va01I~//~va44R~
			BTNSBOX_W      =BTN_W+GAP+GAP;                         //~va01I~
			BTN0BOX_W      =BTNSBOX_W;                             //~va01I~
			BTN0BOX_H      =BTN_H;                                 //~va01I~
    		if (Dump.Y) Dump.println("Wnp.initialize BTN_W="+BTN_W+",BTN_H="+BTN_H+",BTNSBOX_W="+BTNSBOX_W+",BTN0BOX_W="+BTN0BOX_W+",BTN0BOX_H="+BTN0BOX_H);//~va90R~
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
    		if (Dump.Y) Dump.println("Wnp.initialize LEVEL_H="+LEVEL_H+",NUMTEXT_SIZE="+NUMTEXT_SIZE+",MEMOTEXT_SIZE="+MEMOTEXT_SIZE+",MEMOTEXT10_SIZE="+MEMOTEXT10_SIZE+",NAME_WIDTH="+NAME_WIDTH+",BOARD_BOTTOM="+BOARD_BOTTOM);//~va90R~
                                                                   //~0A07I~
          if (Sswportrate)                                         //~0B01I~
          {                                                        //~0B01I~
          	L_WIDTH        =FRAME_W;                               //~0B01I~
            L_LEVEL_Y      =L_FILENAME_Y;                          //~0A09R~
			L_BUTTONS_X    =BOARD_LEFT;                            //~0B01I~
            BUTTON_LEFT=L_BUTTONS_X;                                 //~va90I~
    	if (swAdjustObjectSize)                                    //~va90I~
        {                                                          //~va90I~
            BUTTON_LEFT=barL+GAP;                                  //~va90I~
			L_BUTTONS_X    =BUTTON_LEFT;                           //~va90I~
        }                                                          //~va90I~
//          L_BUTTONS_Y    =BOARD_BOTTOM+LAYOUT_VGAP*2;              //~0A09I~//~0B01R~//~va90R~
            L_BUTTONS_Y    =BOARD_BOTTOM+LAYOUT_VGAP*2;            //~va90R~
            L_STATUS1_Y    =BOARD_BOTTOM+LAYOUT_VGAP+BTN0BOX_H;    //~0A09R~
            STATUS_LINEH=(FRAME_H-L_STATUS1_Y)/STATUS_LINECTR;     //~0B03I~
            L_STATUS1_X    =0;                                 //~0A07R~//~0A08R~
            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;           //~0A07R~//~0A08R~//~0B03R~
            L_STATUS2_X    =0;                                 //~0A07R~//~0A08R~
//          L_SIGN_Y       =L_STATUS2_Y+STATUS_LINEH*4;              //~0A09I~//~0B03R~
            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                  //~0B03R~
            BTN_NUM_X      =BOARD_LEFT;                            //~0B02M~
    	if (swAdjustObjectSize)                                    //~va90I~
        {                                                          //~va90I~
            BTN_NUM_X      =BUTTON_LEFT;                           //~va90R~
        }                                                          //~va90I~
//          BTN_NUM_Y      =BOARD_MARGIN;                          //~0B02M~//~0B03R~
            BTN_NUM_Y      =L_STATUS2_Y+STATUS_LINEH*2;            //~0B03R~
            BTN_NUM_H      =STATUS_LINEH;                            //~0B02M~//~0B03R~
            BTN_NUM_W      =(FRAME_W-BTN_NUM_X)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~0B03I~
    		BUTTONS_W	   =BOARDPANE_W;                           //~0B03I~
    	if (swAdjustObjectSize)                                    //~va90M~
        {                                                          //~va90I~
    		BUTTONS_W	   =boardwidth;                            //~va90I~
        }                                                          //~va90I~
          }                                                        //~0B01I~
          else                                                     //~0B01I~
          {                                                        //~0B01I~
          //*Landscape                                             //~va90I~
          	L_WIDTH        =FRAME_W-BOARDPANE_W;                   //~0B01I~
            L_LEVEL_Y      =L_FILENAME_Y;                          //~0B01I~
			L_BUTTONS_X    =BOARDPANE_W;                          //~0B01I~
//  		L_BUTTONS_Y    =L_FILENAME_Y+NAME_LINEH+LAYOUT_VGAP*2; //~0B01I~//~va44R~
    		L_BUTTONS_Y    =NAME_HEIGHT;                              //~va44I~
		L_BUTTONS_Y+=barT;                                         //~va90I~
            L_STATUS1_Y    =L_BUTTONS_Y+BTN0BOX_H+LAYOUT_VGAP*2;   //~0B01I~
		L_STATUS1_Y+=barT;                                         //~va90I~
            STATUS_LINEH=NAME_LINEH;                                 //~0B03I~
            L_STATUS1_X    =BOARDPANE_W;                           //~0B01I~
            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;                //~0B01I~//~0B03R~
            L_STATUS2_X    =BOARDPANE_W;                           //~0B01I~
           if (swSquare)                                           //~va90R~
           {                                                       //~va90M~
           	int lineh=(int)(OBJECT_SIZE*NAME_HEIGHT_RATE);         //~va90M~
            BUTTON_LEFT=barL+GAP;                                  //~va90M~
            BTN_NUM_X      =BUTTON_LEFT;                           //~va90M~
            BTN_NUM_Y      =BOARD_BOTTOM+STATUS_LINEH/2;           //~va90M~
            BTN_NUM_H      =STATUS_LINEH;                          //~va90M~
            BTN_NUM_W      =(FRAME_W)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~va90M~
    		BUTTONS_W      =L_WIDTH;                               //~va90M~
            L_SIGN_Y       =BOARD_BOTTOM-lineh;                    //~va90I~
           }                                                       //~va90M~
           else                                                    //~va90M~
           {                                                       //~va90M~
            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                    //~0B01R~//~0B03R~
            L_SIGN_Y+=barT;                                        //~va90I~
            BTN_NUM_X      =BOARDPANE_W;                           //~0B02M~
            BTN_NUM_Y      =L_SIGN_Y-(NAME_LINEH+LAYOUT_VGAP*2);   //~0B02R~
            BTN_NUM_H      =STATUS_LINEH;                            //~0B02I~//~0B03R~
            BTN_NUM_W      =(FRAME_W-BOARDPANE_W)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~0B02M~//~0B03R~
    		BUTTONS_W      =L_WIDTH;                               //~0B03I~
           }                                                       //~va90I~
          }                                                        //~0B01I~
    		if (Dump.Y) Dump.println("Wnp.initialize L_WIDTH="+L_WIDTH+",L_LEVEL_Y="+L_LEVEL_Y+",L_BUTTONS_X="+L_BUTTONS_X+",L_BUTTONS_Y="+L_BUTTONS_Y+",L_STATUS1_Y="+L_STATUS1_Y+",STATUS_LINEH="+STATUS_LINEH+",L_STATUS1_X="+L_STATUS1_X+",L_STATUS2_Y="+L_STATUS2_Y);//~va90R~
    		if (Dump.Y) Dump.println("Wnp.initialize L_STATUS2_X="+L_STATUS2_X+",L_STATUS2_X="+L_STATUS2_X+",L_SIGN_Y="+L_SIGN_Y+",BTN_NUM_X="+BTN_NUM_X+",BTN_NUM_Y="+BTN_NUM_Y+",BTN_NUM_H="+BTN_NUM_H+",BTN_NUM_W="+BTN_NUM_W+",BUTTONS_W="+BUTTONS_W);//~va90R~
			STATUS_TEXTSZ    =STATUS_LINEH-LAYOUT_VGAP*2;          //~0B03I~
			SIGN_HEIGHT=STATUS_TEXTSZ;                              //~2718I~
    		if (Dump.Y) Dump.println("Wnp.initialize STATUS_TEXTSZ="+STATUS_TEXTSZ+",SIGN_HEIGHT="+SIGN_HEIGHT);//~va90R~
	}
    //**************************************************************//~va90R~
    public	void adjustEdgeModeWH()                                //~va90R~
    {                                                              //~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustEdgeModeWH gestureMode="+AG.swNavigationbarGestureMode);//~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustEdgeModeWH entry FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H);//~va90I~
    	if (AG.swEdgeToEdgeMode)                                   //~va90R~
        {                                                          //~va90R~
//          barT=AG.scrSystembarTop;                               //~va90R~
            barT=hhActionBar;                                      //~va90R~
            barB=AG.scrSystembarBottom;                            //~va90R~
            barL=AG.scrSystembarLeft;                              //~va90R~
            barR=AG.scrSystembarRight;                             //~va90R~
    		if (Dump.Y) Dump.println("Wnp.gestureMode="+AG.swNavigationbarGestureMode);//~va90R~
    		if (Dump.Y) Dump.println("Wnp.adjustEdgeModeWH systemBar top="+barT+",bottom="+barB+",left="+barL+",right="+barR);//~va90R~
//            if (AG.swNavigationbarGestureMode)                   //~va90R~
//            {                                                    //~va90R~
//                barB=0; barL=0; barR=0;                          //~va90R~
//            }                                                    //~va90R~
            if (FRAME_H>FRAME_W)    //original portrait            //~va90R~
            {                                                      //~va90R~
//              FRAME_H-=(barT+barB);                              //~va90R~
  				;                                                  //~va90I~
            }                                                      //~va90R~
            else                                                   //~va90R~
            {                                                      //~va90R~
//            	FRAME_H-=barT;                                     //~va90R~
            	FRAME_W-=(barL+barR);                              //~va90I~
            }                                                      //~va90R~
        }                                                          //~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustEdgeModeWH exit FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H);//~va90R~
    }                                                              //~va90R~
    //**************************************************************//~va90R~
    private	int adjustPortrait(int PboardWidth)                    //~va90R~
    {                                                              //~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustPortrait Sswportrait="+Sswportrate);//~va90R~
        int boardW=PboardWidth;                                    //~va90R~
        if (!Sswportrate)                                          //~va90R~
        {                                                          //~va90R~
        	if (FRAME_W*RATE_SQUARE>(double)FRAME_H)	//         //~va90R~
            {                                                      //~va90R~
				Sswportrate=true;                                  //~va90R~
                boardW=FRAME_H;                                    //~va90R~
    			if (Dump.Y) Dump.println("Wnp.adjustBoardWidth landscape by portrit image FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H);//~va90R~
        	}                                                      //~va90R~
        }                                                          //~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustPortrait return boardW="+boardW);//~va90R~
        return boardW;                                             //~va90R~
    }                                                              //~va90R~
    //**************************************************************//~va90I~
    private	boolean isSquare()                                     //~va90R~
    {                                                              //~va90I~
    	int ww=AG.scrWidthReal; int hh=AG.scrHeightReal;           //~va90I~
        boolean	swSquare=(double)(Math.max(ww,hh))/Math.min(ww,hh)<RATE_SQUARE;//~va90R~
    	if (Dump.Y) Dump.println("Wnp.isSquare swSquare="+swSquare+",ww="+ww+",hh="+hh+",rate="+RATE_SQUARE);//~va90R~
        return swSquare;                                           //~va90R~
    }                                                              //~va90I~
    //**************************************************************//~va90R~
    public	int adjustObjectSize()                                 //~va90R~
    {                                                              //~va90R~
        double ctrLine=0.0;                                        //~va90R~
        int hh=FRAME_H;                                            //~va90R~
	//**********************************                           //~va90R~
    	if (Dump.Y) Dump.println("Wnp.adjustObjectSize FRAME_H="+FRAME_H+",OBJECT_SIZE="+OBJECT_SIZE+",wdgeMode="+AG.swEdgeToEdgeMode+",barT="+barT);//~va90R~
//            OBJECT_SIZE    =(boardwidth-BOARD_MARGIN*2)/MAP_SIZE-GAP;//~va90R~
        ctrLine+=MAP_SIZE;                                         //~va90R~
    	if (AG.swEdgeToEdgeMode)                                   //~va90I~
    	    hh-=barT;                                              //~va90I~
        hh-=BOARD_MARGIN*2;                                        //~va90R~
//            BOARD_LEFT     =(int)(boardwidth-((OBJECT_SIZE+GAP)*MAP_SIZE-GAP))/2;//~va90R~
//            BOARD_WIDTH    =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va90R~
//            BOARD_HEIGHT   =(int) ( ( OBJECT_SIZE + GAP ) * MAP_SIZE - GAP );//~va90R~
        hh-=( GAP * MAP_SIZE - GAP );                              //~va90R~
//            BOARDPANE_W    =(int) boardwidth;                    //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize OBJECT_SIZE="+OBJECT_SIZE+",BOARD_LEFT="+BOARD_LEFT+",BOARD_WIDTH="+BOARD_WIDTH+",BOARD_HEIGHT="+BOARD_HEIGHT+",BOARDPANE_W="+BOARDPANE_W);//~va90R~
//                                                                 //~va90R~
//            BTN_NUM_GAP    =GAP;                                 //~va90R~
//            NAME_LINEH     =(int)(OBJECT_SIZE*NAME_HEIGHT_RATE); //~va90R~
//            NAME_HEIGHT    =NAME_LINEH-GAP*2-BOARD_MARGIN;       //~va90R~
//            NAME_TEXTSZ    =NAME_HEIGHT;                         //~va90R~
        ctrLine+=NAME_HEIGHT_RATE;      //name 0.7                 //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize NAME_LINEH="+NAME_LINEH+",NAME_HEIGHT="+NAME_HEIGHT+",NAME_TEXTSZ="+NAME_TEXTSZ);//~va90R~
//          if (Sswportrate)                                       //~va90R~
//          {                                                      //~va90R~
//            L_FILENAME_X   =0;                                   //~va90R~
//            L_FILENAME_Y   =BOARD_MARGIN;                        //~va90R~
//            BOARD_TOP      =NAME_LINEH;                          //~va90R~
//          }                                                      //~va90R~
//          else                                                   //~va90R~
//          {                                                      //~va90R~
//            L_FILENAME_X   =BOARDPANE_W;                         //~va90R~
//            L_FILENAME_Y   =BOARD_MARGIN;                        //~va90R~
//            BOARD_TOP      =(FRAME_H-BOARD_HEIGHT)/2;            //~va90R~
//          }                                                      //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize L_FILENAME_X="+L_FILENAME_X+",L_FILENAME_Y="+L_FILENAME_Y+",BOARD_TOP="+BOARD_TOP);//~va90R~
//                                                                 //~va90R~
//            BTN_W          =(int) OBJECT_SIZE;                   //~va90R~
//            BTN_H          =(int) OBJECT_SIZE;                   //~va90R~
//            BTNSBOX_W      =BTN_W+GAP+GAP;                       //~va90R~
//            BTN0BOX_W      =BTNSBOX_W;                           //~va90R~
//            BTN0BOX_H      =BTN_H;                               //~va90R~
        ctrLine+=1.0;                   //btn                      //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize BTN_W="+BTN_W+",BTN_H="+BTN_H+",BTNSBOX_W="+BTNSBOX_W+",BTN0BOX_W="+BTN0BOX_W+",BTN0BOX_H="+BTN0BOX_H);//~va90R~
//            LEVEL_H        =NAME_HEIGHT;                         //~va90R~
//            NUMTEXT_SIZE   =(int) (OBJECT_SIZE);                 //~va90R~
//            MEMOTEXT_SIZE  =(int) ((OBJECT_SIZE-MEMOTEXT_MARGIN*1)/2);//~va90R~
//            MEMOTEXT10_SIZE  =MEMOTEXT_SIZE-2;                   //~va90R~
//            NAME_WIDTH     =BOARDPANE_W-LEVEL_W;                 //~va90R~
//            BOARD_BOTTOM   =BOARD_TOP+BOARD_HEIGHT;              //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize LEVEL_H="+LEVEL_H+",NUMTEXT_SIZE="+NUMTEXT_SIZE+",MEMOTEXT_SIZE="+MEMOTEXT_SIZE+",MEMOTEXT10_SIZE="+MEMOTEXT10_SIZE+",NAME_WIDTH="+NAME_WIDTH+",BOARD_BOTTOM="+BOARD_BOTTOM);//~va90R~
//                                                                 //~va90R~
//          if (Sswportrate)                                       //~va90R~
//          {                                                      //~va90R~
//            L_WIDTH        =FRAME_W;                             //~va90R~
//            L_LEVEL_Y      =L_FILENAME_Y;                        //~va90R~
//            L_BUTTONS_X    =BOARD_LEFT;                          //~va90R~
//            L_BUTTONS_Y    =BOARD_BOTTOM+LAYOUT_VGAP*2;          //~va90R~
//            L_STATUS1_Y    =BOARD_BOTTOM+LAYOUT_VGAP+BTN0BOX_H;  //~va90R~
        hh-=LAYOUT_VGAP*2;                                         //~va90R~
//            STATUS_LINEH=(FRAME_H-L_STATUS1_Y)/STATUS_LINECTR;   //~va90R~
//            L_STATUS1_X    =0;                                   //~va90R~
//            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;            //~va90R~
//            L_STATUS2_X    =0;                                   //~va90R~
//            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                //~va90R~
//            BTN_NUM_X      =BOARD_LEFT;                          //~va90R~
//            BTN_NUM_Y      =L_STATUS2_Y+STATUS_LINEH*2;          //~va90R~
//            BTN_NUM_H      =STATUS_LINEH;                        //~va90R~
//            BTN_NUM_W      =(FRAME_W-BTN_NUM_X)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~va90R~
//            BUTTONS_W      =BOARDPANE_W;                         //~va90R~
        ctrLine+=NAME_HEIGHT_RATE*STATUS_LINECTR; //5=3status+num button+sign//~va90R~
//          }                                                      //~va90R~
//          else                                                   //~va90R~
//          {                                                      //~va90R~
//            L_WIDTH        =FRAME_W-BOARDPANE_W;                 //~va90R~
//            L_LEVEL_Y      =L_FILENAME_Y;                        //~va90R~
//            L_BUTTONS_X    =BOARDPANE_W;                         //~va90R~
//            L_BUTTONS_Y    =NAME_HEIGHT;                         //~va90R~
//            L_STATUS1_Y    =L_BUTTONS_Y+BTN0BOX_H+LAYOUT_VGAP*2; //~va90R~
//            STATUS_LINEH=NAME_LINEH;                             //~va90R~
//            L_STATUS1_X    =BOARDPANE_W;                         //~va90R~
//            L_STATUS2_Y    =L_STATUS1_Y+STATUS_LINEH;            //~va90R~
//            L_STATUS2_X    =BOARDPANE_W;                         //~va90R~
//            L_SIGN_Y       =FRAME_H-STATUS_LINEH;                //~va90R~
//            BTN_NUM_X      =BOARDPANE_W;                         //~va90R~
//            BTN_NUM_Y      =L_SIGN_Y-(NAME_LINEH+LAYOUT_VGAP*2); //~va90R~
//            BTN_NUM_H      =STATUS_LINEH;                        //~va90R~
//            BTN_NUM_W      =(FRAME_W-BOARDPANE_W)/ButtonDlg.BTN_NUM_CTR-BTN_NUM_GAP;//~va90R~
//            BUTTONS_W      =L_WIDTH;                             //~va90R~
//          }                                                      //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize L_WIDTH="+L_WIDTH+",L_LEVEL_Y="+L_LEVEL_Y+",L_BUTTONS_X="+L_BUTTONS_X+",L_BUTTONS_Y="+L_BUTTONS_Y+",L_STATUS1_Y="+L_STATUS1_Y+",STATUS_LINEH="+STATUS_LINEH+",L_STATUS1_X="+L_STATUS1_X+",L_STATUS2_Y="+L_STATUS2_Y);//~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize L_STATUS2_X="+L_STATUS2_X+",L_STATUS2_X="+L_STATUS2_X+",L_SIGN_Y="+L_SIGN_Y+",BTN_NUM_X="+BTN_NUM_X+",BTN_NUM_Y="+BTN_NUM_Y+",BTN_NUM_H="+BTN_NUM_H+",BTN_NUM_W="+BTN_NUM_W+",BUTTONS_W="+BUTTONS_W);//~va90R~
//            STATUS_TEXTSZ    =STATUS_LINEH-LAYOUT_VGAP*2;        //~va90R~
//            SIGN_HEIGHT=STATUS_TEXTSZ;                           //~va90R~
//            if (Dump.Y) Dump.println("Wnp.initialize STATUS_TEXTSZ="+STATUS_TEXTSZ+",SIGN_HEIGHT="+SIGN_HEIGHT);//~va90R~
		int objectSize=(int)(hh/ctrLine);                          //~va90R~
        int sz=Math.min(objectSize,OBJECT_SIZE);                   //~va90R~
        swAdjustObjectSize=(sz!=OBJECT_SIZE);                      //~va90I~
		if (Dump.Y) Dump.println("Wnp.adjustObjectSize hh="+hh+",ctrLine="+ctrLine+",objectSize="+objectSize+",return sz="+sz);//~va90R~
        return sz;                                                 //~va90R~
	}                                                              //~va90R~
    //**************************************************************//~va90I~
    //*object box and num button                                   //~va90I~
    //**************************************************************//~va90I~
    public	int adjustObjectSizeSquareLandscape()                  //~va90I~
    {                                                              //~va90I~
        double ctrLine=0.0;                                        //~va90I~
        int hh=FRAME_H;                                            //~va90I~
	//**********************************                           //~va90I~
    	if (Dump.Y) Dump.println("Wnp.adjustObjectSizeSquareLandscape FRAME_H="+FRAME_H+",OBJECT_SIZE="+OBJECT_SIZE+",edgeMode="+AG.swEdgeToEdgeMode+",barT="+barT);//~va90R~
    	if (AG.swEdgeToEdgeMode)                                   //~va90I~
    	    hh-=barT;                                              //~va90R~
        ctrLine+=MAP_SIZE;                                         //~va90I~
        hh-=BOARD_MARGIN*2;                                        //~va90I~
        hh-=( GAP * MAP_SIZE - GAP );                              //~va90I~
        ctrLine+=NAME_HEIGHT_RATE*3; //name+num button+sign;       //~va90R~
                                                                   //~va90I~
		int objectSize=(int)(hh/ctrLine);                          //~va90I~
        int sz=Math.min(objectSize,OBJECT_SIZE);                   //~va90I~
        swAdjustObjectSize=(sz!=OBJECT_SIZE);                      //~va90I~
        OBJECT_SIZE=sz;                                            //~va90I~
        int boardw=(OBJECT_SIZE+GAP)*MAP_SIZE;                          //~va90I~
		if (Dump.Y) Dump.println("Wnp.adjustObjectSizeSquareLandscape hh="+hh+",ctrLine="+ctrLine+",objectSize="+objectSize+",return sz="+sz+",boardw="+boardw);//~va90I~
        return boardw;                                             //~va90I~
	}                                                              //~va90I~
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
    	if (Dump.Y) Dump.println("Wnp.getScreenSize displayW="+displayW+",displayH="+displayH);//~va44I~
//      int displayW=nppView.layoutWidth;                          //~va44R~
//      int displayH=nppView.layoutHeight;                         //~va44R~
//*********                                                        //~2717I~
//*calc by button height and title size                            //~1606I~//~2717I~
	    int th=getTitleBarHeight();                                       //~1606M~//~2717I~
		w=displayW;                                           //~1606R~//~2717I~
     if (AG.swEdgeToEdgeMode)                                      //~va90R~
        h=displayH;                                                //~va90I~
     else                                                          //~va90I~
        h=displayH-th;                                             //~2717I~
        if (osVersion>=HONEYCOMB && osVersion<ICE_CREAM_SANDWICH)  //android3 api11-13//~vab0R~//~2717I~
        	bottomSpaceHeight=SYSTEMBAR_HEIGHT;                    //~vab0I~//~2717I~
        h-=bottomSpaceHeight;                                 //~vab0I~//~2717R~
  		FRAME_W=w;                                                 //~2717I~
  		FRAME_H=h;                                                 //~2717I~
    	if (Dump.Y) Dump.println("Wnp.getScreenSize FRAME_W="+FRAME_W+",FRAME_H="+FRAME_H+",bottomSpaceHeight="+bottomSpaceHeight);//~va44I~
    }                                                              //~1606I~//~2717I~
    public int getTitleBarHeight()                            //~1606I~//~2717I~
    {                                                              //~1606I~//~2717I~
//        Rect rect=new Rect();                                      //~1606I~//~2717I~//~va44R~
//        Window w=NppView.activity.getWindow();                          //~1606I~//~2717I~//~va44R~
//        View v=w.getDecorView();                                   //~1606I~//~2717I~//~va44R~
//        v.getWindowVisibleDisplayFrame(rect);                      //~1606I~//~2717I~//~va44R~
//        v=w.findViewById(android.view.Window.ID_ANDROID_CONTENT);  //~1606I~//~2717I~//~va44R~
//        return v.getTop();                            //~1606I~    //~2717I~//~va44R~
    	if (AG.swEdgeToEdgeMode)                                   //~va90R~
        	hhActionBar-=AG.scrSystembarTop;                       //~va90R~
    	if (Dump.Y) Dump.println("Wnp.getTitleBarHeight rc="+hhActionBar+",systemBarTop="+AG.scrSystembarTop);//~va90R~
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
