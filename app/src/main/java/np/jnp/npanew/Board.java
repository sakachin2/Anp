//CID://+va45R~:                                                   //~va45R~
//******************************************************************************//~v004I~
//va45:200526 sound at answered                                    //~va45I~
//va23:051221 langauge ctl(English and japanese)                   //~va23I~
//va21:051114 on success;display "eburi"                           //~va21I~
//va20:051113 kbd input over 9(2 digit input); Shift+,Ctl+(memo),S+C(same num)//~va20I~
//va19:051113 (BUG)memo for 4*4                                    //~va19I~
//va16:051113 option to drop redundancy to make                    //~va16R~
//v043:051112 logic of select minimum candidate place yield crowded unit,so take maximum candidate logic(plan>0)//~v043I~
//v041:051112 try minimum candidate select plan(plan<0)            //~v041I~
//v030:051108 enable clear when make mode to back to anss mode     //~v030I~
//v025:051104 (BUG)at start no input accepted(after automake input avail)//~v025I~
//va03:051104 dencity by slider                                    //~va03I~
//v024:051103 (BUG)restore also score when restore pending game    //~v024I~
//v023:051102 (BUG)open doc ignored after make interrupted by timeout//~v023I~
//v022:051102 redundancy chk:max depth parm missing(no depth chk done at xnpsub)//~v022I~
//v021:051102 redundancy chk:display also reduduncy depth count    //~v021I~
//v020:051102 (BUG)redundant chk display also answer               //~v021I~
//va01:051013 5*5 support                                          //~va01I~
//v019:051101 change color of patern input                         //~va19I~
//v012:051004 dosenot write answer when in solving                 //~v012I~
//vj01:050923 java conversion                                      //~vj01I~
//v004:050922 (BUG) Mode=MODE_NOINPUT; -->Mode=MODE_NOINPUT;       //~v004I~
//v003:000312 clear err only when err exist                        //~v004I~
//v002:000312 enter ans mode after make automaticaly               //~v004I~
//******************************************************************************//~v004I~
//*       ** Board.cpp **                                          //~v004I~
//******************************************************************************//~v004I~
package np.jnp.npanew;                                                //~v@@@R~//~va23M~//~va45R~
import java.util.Arrays;                                           //~va23M~
                                                                   //~va23M~
import android.graphics.*;                                         //~va23M~
import android.view.KeyEvent;                                      //~va23M~

import np.jnp.npanew.R;
import np.jnp.npanew.utils.AG;                                     //+va45R~

import static np.jnp.npanew.utils.BGMList.*;                       //+va45R~

public class Board implements Runnable{                            //~vj01R~

//*************************                                        //~5921I~
//*macro                                                           //~5921I~
//*************************                                        //~5921I~
public static final int MODE_INIT     = 0;      //no data exist    //~5921I~
public static final int MODE_NOINPUT  = 1;      //file input end   //~5921I~
public static final int MODE_FILEDATA = 2;      //file input end   //~5921I~
public static final int MODE_KEYINDATA= 3;      //manual data      //~5921I~
public static final int MODE_ENDQDATA = 4;      //made data or id of go answer//~5921I~
public static final int MODE_KEYINANS = 5;      //manual answer input data//~5921I~
public static final int MODE_OUTANS   = 6;      //solved answer data//~5921I~
                                                                   //~5921I~
//public static final int PATTERN_ID    = 10;                      //~va01R~
public static       int PATTERN_ID    = (Wnp.MAP_SIZE+1);          //~va01R~
public static final int F_ONTRY   = 0x01;                          //~5921I~
public static final int F_ONGO    = 0x02;	    //next ans by GO   //~5921I~
public static final int F_ONNEXT  = 0x04 ;                         //~5921I~
public static final int F_ONRCHK  = 0x08 ;    //ans by rchkmode    //~5921I~
public static final int F_ONXX    = 0x0f ;    //ans by rchkmode    //~5921I~
public static final int F_THREAD  = 0x10 ;                         //~5921I~
public static final int F_ANSCHK  = 0x20 ;                         //~5921I~
public static final int F_MODEMAKE= 0x40 ;    //make or answer mode//~5921I~
public static final int F_MODEANS = 0x80 ;    //make or answer mode//~5921I~
	                                                               //~5921I~
public static final int F_LVLMASK = 0x0f00 ;                       //~5921I~
public static final int F_LVL0    = 0x0100 ;  //difficuty level    //~5921I~
public static final int F_LVL1    = 0x0200 ;  //difficuty level    //~5921I~
public static final int F_LVL2    = 0x0400 ;  //difficuty level    //~5921I~
public static final int F_LVL3    = 0x0800 ;  //difficuty level    //~5921I~
                                                                   //~5921I~
public static final int F_DENMASK = 0x0f0000 ;                   //~va03R~//~v@@@R~
public static final int F_DEN0    = 0x010000 ;  //density        //~va03R~//~v@@@R~
public static final int F_DEN3    = 0x020000 ;  //density        //~va03R~//~v@@@R~
public static final int F_DEN4    = 0x040000 ;  //density        //~va03R~//~v@@@R~
public static final int F_DEN5    = 0x080000 ;  //density        //~va03R~//~v@@@R~
                                                                   //~5921I~
public static final int F_RIGID   = 0x100000 ;  //rigid option     //~5921I~
                                                                   //~5921I~
public static final int F_ERROR   = 0x01000000; //err detected      //~5921I~
public static final int F_READFILE= 0x02000000; //pending data read//~vj01I~
public static final int F_NOREDUNDANT= 0x04000000; //pending data read//~va16R~
public static final int F_READINDX= 0x08000000; //qdata restore from index file//~v@@@I~
// enum OBJ_STAT { ST_EMPTY,ST_QDATA,ST_ANS,ST_PAT,ST_ERR,ST_RCHK,GRID1,GRID2};//~5921I~
public static final int ST_EMPTY=0;                                //~vj01R~
public static final int ST_QDATA=1;                                //~vj01R~
public static final int ST_ANS=2;                                  //~vj01R~
public static final int ST_PAT=3;                                  //~vj01R~
public static final int ST_ERR=4;                                  //~vj01R~
public static final int ST_RCHK=5;                                 //~vj01R~
public static final int ST_NEXT=6;                                 //~vj01R~
public static final int GRID1=7;                                   //~vj01R~
public static final int GRID2=8;                                   //~vj01R~
public static final int GRID1_MAKE=9;                              //~vj01R~
public static final int GRID2_MAKE=10;                             //~vj01R~
                                                                   //~v004I~
public static final int IDC_GO=1;	//timer 50ms
public static final int IDC_MAKE=2;
public static final int IDC_ANSWER=3;
public static final int IDC_SETEND=4;  
public static final int IDC_SUCCESS=5;   //~vj01I~
public static final int IDC_RCHK=6;                                //~vj01I~
public static final int IDC_NEXT=7;                                //~vj01I~
public static final int IDC_CLEAR=8;                               //~vj01R~
public static final int IDC_RESTORE=9;                             //~vj01I~
public static final int IDC_SORT=10;                               //~vj01I~
public static final int IDC_STOP=11;                               //~vj01I~
public static final int IDC_SAVE=12;                               //~v@@@I~
                                                                   //~v@@@I~
public static final int NEXT_VALUE=1000;                           //~v@@@I~
                                                                   //~v@@@I~
public static final int KEYCODE_MAKE    =KeyEvent.KEYCODE_Q;     //Question//~v@@@R~
public static final int KEYCODE_START   =KeyEvent.KEYCODE_T;     //Start//~v@@@R~
public static final int KEYCODE_NEXT    =KeyEvent.KEYCODE_N;     //Help//~v@@@R~
public static final int KEYCODE_ANSWER  =KeyEvent.KEYCODE_A;     //Done//~v@@@R~
public static final int KEYCODE_RCHK    =KeyEvent.KEYCODE_R;     //Done//~v@@@I~
public static final int KEYCODE_CLEAR   =KeyEvent.KEYCODE_BACK;   //back//~v@@@R~
public static final int KEYCODE_BREAK   =KeyEvent.KEYCODE_B;     //Break//~v@@@R~
public static final int KEYCODE_SAMEKEY =KeyEvent.KEYCODE_STAR;    //~v@@@R~
public static final int KEYCODE_SAMEKEY2=KeyEvent.KEYCODE_AT;      //~v@@@I~
public static final int KEYCODE_SAMEKEY3=KeyEvent.KEYCODE_H;       //~v@@@R~
public static final int KEYCODE_MEMOKEY =KeyEvent.KEYCODE_POUND;   //~v@@@R~
//public static final int KEYCODE_MEMOKEY2=KeyEvent.KEYCODE_SYM;   //~v@@@R~
public static final int KEYCODE_MEMOKEY3=KeyEvent.KEYCODE_M;       //~v@@@I~
                                                                   //~v004I~
private static final boolean TRUE=true;                            //~v004I~
private static final boolean FALSE=false;                          //~v004I~
//==============================================================================//~5921I~
//                                                                 //~va23R~
//==============================================================================//~5921I~

public static 	String GblNpsubMsg;                                 //~5921I~
public static 	int GblSubthreadsw;                                //~5921I~

//*************************                                        //~5921I~
//*member                                                          //~5921I~
//*************************                                        //~5921I~
public    NPWORKT Npworkt=new NPWORKT();                           //~vj01R~
public    String  sProbLevel;                                      //~5923I~
public    int     intProbLevel=0;                                  //~v@@@R~
public   int   ActSeed;                                            //~5923I~
public   WnpView    pView;                                           //~5922R~//~0914R~//~v@@@R~
                                                                   //~v@@@I~
private   Paint pDCpaintT=new Paint(Paint.ANTI_ALIAS_FLAG);	//text //~v@@@I~
private   Paint pDCpaintB=new Paint();						//box  //~v@@@I~
                                                                   //~5921I~
public     int      	FixedSeq;		//fixed seqence for next   //~5921I~
public     int      	MaxFixed;		//fixed seqence            //~5921I~
public     int     Flags;                                          //~5921I~
public 	int     Seed;                                              //~5921I~
public 	int     Timeout;                                           //~5921I~
public     long  StartTime;                                        //~5922R~
public     CPattern    pPat;                                       //~5922R~
public     int      	NumCount;      //Num set count             //~5921I~
public     int      Score;                                         //~5923I~
public     int      ScoreMax;                                      //~5923I~
public     int      NumTextFontsz,NumTextPosx,NumTextPosx10,NumTextPosy;//~va01R~
public     int      Dencity;                                       //~va03I~
public     CPIndex  pPIndex;                                       //~v@@@I~
                                                                   //~5921I~
private   Hole[][]    BoardMap= new Hole[ Wnp.MAP_SIZE ][ Wnp.MAP_SIZE ];//~va01R~

private   int         Mode;                                        //~5921I~
private   int         CurNum;        //current num of cursor       //~5921I~
private  String sMsg,sTempMsg,sMsgOut,sSuccessMsg;               //~5921I~
private   int      KbFocus;                                        //~5923R~
//private   int   ActSeed;                                         //~5923R~
private   int      NpsubRc;                                        //~5921I~
public    int      SameNum;    //reset by ButtonPush              //~5923I~//~v@@@R~
public    int      Shiftkey;                                       //~5923I~//~v@@@R~
public    int      Controlkey;                                     //~va20I~//~v@@@R~
private   int      CongMsg;   //when wnswe ok                      //~5923I~
private   long    TryStartTime;                                    //~5923I~
private   int      TryTimeSpan;    //sec                           //~5923I~
public    int      Penalty;                                        //~5923I~//~v@@@R~
//private   String   Ssamenumstr=null,Sover10numstr=null,Smemonumstr=null;//~va20I~
private   NPWORKT  Npwtryans=new NPWORKT();                        //~v025R~
private static final int PENALTY_NOTIME=   4;   //6*5=20min is standard(no time penalty)//~5923I~
//    score is multiplyed by 30min/elapsed time(5min unit)         //~5923I~
private static final int PENALTY_SAMENUM=  1;                      //~5923I~
private static final int PENALTY_MEMO=     5;                      //~5923I~
private static final int PENALTY_TIME=    50;   //for each 5 min late//~5923I~
private static final int PENALTY_ERR=    100;   //for each 5 min late//~5923I~
                                                                   //~vj01I~
private static final int MAX_CONG= 6;                              //~vj01I~
	                                                               //~5921I~
//color                                                            //~5922M~
public static int    COL_BG_EMPTY          ;                     //~vj01R~//~0914R~
public static int    COL_BG_Q              ;                     //~vj01R~//~0914R~
public static int    COL_BG_P              ;                     //~vj01R~//~0914R~
public static int    COL_BG_A              ;                     //~vj01R~//~0914R~
public static int    COL_BG_E              ;                     //~vj01R~//~0914R~
public static int    COL_BG_RCHK           ;                     //~vj01R~//~0914R~
public static int    COL_BG_NEXT           ;                     //~vj01R~//~0914R~
public static int    COL_BG_FORCUS         ;                     //~vj01R~//~0914R~
public static int    COL_BG_SAMENUM        ;                     //~vj01R~//~0914R~
public static int    COL_NUM_Q             ;                     //~vj01R~//~0914R~
public static int    COL_NUM_P             ;                     //~vj01R~//~0914R~
public static int    COL_NUM_A             ;                     //~vj01R~//~0914R~
public static int    COL_NUM_E             ;                     //~vj01R~//~0914R~
public static int    COL_NUM_RCHK          ;                     //~vj01R~//~0914R~
public static int    COL_NUM_NEXT          ;                     //~vj01R~//~0914R~
public static int    COL_GRID_BOX          ;                     //~vj01R~//~0914R~
public static int    COL_GRID_NUM          ;                     //~vj01R~//~0914R~
public static int    COL_GRID_BOX_MAKE     ;                     //~vj01R~//~0914R~
public static int    COL_GRID_NUM_MAKE     ;                     //~vj01R~//~0914R~
public static int    COL_BG_DLG            ;                     //~vj01R~//~0914R~
public static int    COL_TXT_DLG           ;                     //~vj01R~//~0914R~
public static int    COL_TXT_SIGN          ;                     //~vj01R~//~0914R~
public static int    COL_TXT_STATUS1       ;                       //~v@@@I~
public static int    COL_TXT_STATUS2       ;                       //~v@@@I~
public static int    COL_TXT_FILENAME      ;                       //~v@@@I~
public static int    COL_TXT_LEVEL         ;                       //~v@@@I~
                                                                   //~0109R~
                                                                   //~0101I~
private int  OBJ_COLOR[] = {COL_BG_EMPTY                      //~5921R~
						,COL_BG_Q                                  //~9C31R~
						,COL_BG_A                                  //~9C31R~
						,COL_BG_P                                  //~0101I~
						,COL_BG_E                                  //~0101I~
						,COL_BG_RCHK                               //~0109I~
						,COL_BG_NEXT                               //~v004I~
						,COL_GRID_BOX                              //~0102I~
						,COL_GRID_NUM                              //~v004R~
						,COL_GRID_BOX_MAKE                         //~v004I~
						,COL_GRID_NUM_MAKE};                       //~v004I~

private int[][] pdata;                                             //~vj01R~
private NppTimer timer_ANS=null,timer_MAKE=null,timer_GO=null,timer_SETEND=null,timer_SUCCESS=null;//~vj01R~//~0915R~
private int timerid=0;
private Object[] plistans,plistmake;  
private xnpsub  sub1=new xnpsub(Wnp.BOARDTYPE);                    //~va01R~
private xnpsub2 sub2=new xnpsub2(sub1,Wnp.BOARDTYPE);              //~va01R~//~0914R~
private Point wkpoint=new Point();                                 //~v004I~
private int[][]	Stempdata=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];     //~va01R~//~0914R~
private int[] temp=new int[Wnp.MAP_SIZE+1];                        //~va19I~//~0914R~
private int Scongdispsw=0;                                         //~va21R~
private final int Scongdisptime=300; 	//300ms                    //~va23R~
//private final String[] Scongdispstrj={"え","ぶ","り","木八"};      //~va23R~//~v@@@R~
//private final String[] Scongdispstre={"E","Bu","Ri","Eburi","Primary","School"};//~va23R~//~v@@@R~
private String[] Scongdispstr;                               //~va23I~//~v@@@R~
public  static String[] Slevelstr;                                 //~v@@@I~
private final int COL_CONG_STR=Color.rgb(0,170,60);              //~va21I~//~0914R~
private final int COL_CONG_STR2=Color.rgb(0,20,20);              //~va21I~//~0914R~
private       int  Slevelsummary=0;                                //~va21I~
//*************************                                        //~5921I~
//*method                                                          //~5921I~
//*************************                                        //~5921I~
// グローバル関数                                           //~v@@@R~
//void    BoardToScreen( int bx, int by, int* px, int* py );       //~5921R~
//void    ShowObject( CDC* pDC, int bx, int by,                    //~v004R~
//boolean    ScreenToBoard( int wx, int wy, int* pbx, int* pby );    // <---//~vj01R~
//unsigned int MakeThread(LPVOID Pboard);                          //~vj01R~
//unsigned int AnsThread(LPVOID Plist);                            //~vj01R~
static {                                                           //~5922I~
 COL_BG_EMPTY          =Color.rgb(  0, 30,  0);                    //~vj01R~//~0914R~
 COL_BG_Q              =Color.rgb(  0,  0, 50);                    //~vj01R~//~0914R~
 COL_BG_P              =Color.rgb(  0,  0,200);                    //~va19R~//~0914R~
 COL_BG_A              =Color.rgb(  0,  0,150);                    //~vj01R~//~0914R~
 COL_BG_E              =Color.rgb(255,255,255);                    //~vj01R~//~0914R~
 COL_BG_RCHK           =Color.rgb(  0,  0, 50);                    //~vj01R~//~0914R~
 COL_BG_NEXT           =Color.rgb(  0,  0,100);                    //~vj01R~//~0914R~
 COL_BG_FORCUS         =Color.rgb(255,255,255);                    //~vj01R~//~0914R~
 COL_BG_SAMENUM        =Color.rgb(255,255,  0);                    //~vj01R~//~0914R~
 COL_NUM_Q             =Color.rgb(  0,255,  0);                    //~vj01R~//~0914R~
 COL_NUM_P             =Color.rgb(  0,255,  0);                    //~vj01R~//~0914R~
 COL_NUM_A             =Color.rgb(255,255,255);                    //~vj01R~//~0914R~
 COL_NUM_E             =Color.rgb(255,  0,  0);                    //~vj01R~//~0914R~
 COL_NUM_RCHK          =Color.rgb(255,255,  0);                    //~vj01R~//~0914R~
 COL_NUM_NEXT          =Color.rgb(255,255, 50);                    //~vj01R~//~0914R~
 COL_GRID_BOX          =Color.rgb(  0,130,130);                    //~va03R~//~0914R~
 COL_GRID_NUM          =Color.rgb(  0, 70, 70);                    //~v025R~//~0914R~
 COL_GRID_BOX_MAKE     =Color.rgb( 23,230,230);                    //~vj01R~//~0914R~
 COL_GRID_NUM_MAKE     =Color.rgb( 13,130,130);                    //~vj01R~//~0914R~
 COL_BG_DLG            =Color.rgb(  0, 50,  0);                    //~vj01R~//~0914R~
 COL_TXT_DLG           =Color.rgb(200,200,200);                    //~vj01R~//~0914R~
 COL_TXT_STATUS1       =Color.rgb(  0,  0,  0);                    //~v@@@I~
 COL_TXT_STATUS2       =Color.rgb( 50, 10, 10);                    //~v@@@I~
 COL_TXT_SIGN          =Color.rgb(  0, 70,  0);                    //~vj01R~//~0914R~
 COL_TXT_FILENAME      =Color.rgb(  0,  0,  0);                    //~v@@@I~
 COL_TXT_LEVEL         =Color.rgb( 80,  0, 80);                    //~v@@@R~
}//static initializer                                              //~5922I~
/*                                                                 //~9C09I~
===============================================================================
    ＜ Board::Board ＞                                           //~v@@@R~
===============================================================================
===============================================================================*/
public Board(WnpView Pview)                                      //~5922R~
{
	pView=Pview;                                                   //~va21I~
//    if (Wnp.BOARDTYPE==Wnp.BOARDTYPE3)                             //~va01I~//~0914R~//~v@@@R~
//    {                                                              //~va01I~//~v@@@R~
		NumTextPosx=Wnp.NUMTEXT_MARGIN*8;                          //~va01I~//~0914R~
		NumTextPosx10=Wnp.NUMTEXT_MARGIN*8;                        //~va01I~//~0914R~
//  	NumTextPosy=(int) (Wnp.OBJECT_SIZE-4);                             //~va01I~//~0914R~//~v@@@R~
//  	NumTextPosy=(int)(Wnp.NUMTEXT_SIZE*9)/10;    //*0.9 baseline//~v@@@R~
//  	NumTextFontsz=32;                                          //~va01R~//~v@@@R~
//  	NumTextFontsz=Wnp.NUMTEXT_SIZE;                            //~v@@@R~
//    }                                                              //~va01I~//~v@@@R~
//    else                                                           //~va01I~//~v@@@R~
//    if (Wnp.BOARDTYPE==Wnp.BOARDTYPE3)                             //~va01I~//~0914R~//~v@@@R~
//    {                                                              //~va01I~//~v@@@R~
//        NumTextPosx10=Wnp.NUMTEXT_MARGIN*6;                        //~va01R~//~0914R~//~v@@@R~
//        NumTextPosx=Wnp.NUMTEXT_MARGIN*10;                         //~va01R~//~0914R~//~v@@@R~
//        NumTextPosy=(int) (Wnp.OBJECT_SIZE-5);                             //~va01I~//~0914R~//~v@@@R~
//        NumTextFontsz=26;                                          //~va01R~//~v@@@R~
//    }                                                              //~va01I~//~v@@@R~
//    else                                                           //~va01I~//~v@@@R~
//    {                                                              //~va01I~//~v@@@R~
//        NumTextPosx10=Wnp.NUMTEXT_MARGIN*2;                        //~va01R~//~0914R~//~v@@@R~
//        NumTextPosx=Wnp.NUMTEXT_MARGIN*8;                          //~va01R~//~0914R~//~v@@@R~
//        NumTextPosy=(int) (Wnp.OBJECT_SIZE-6);                             //~va01I~//~0914R~//~v@@@R~
//        NumTextFontsz=22;                                          //~va01R~//~v@@@R~
//    }                                                              //~va01I~//~v@@@R~
    initsize(0);                                                   //~v@@@I~
    pPat = null;                                                   //~vj01R~
    for ( int x = 0; x < Wnp.MAP_SIZE; x ++ )                      //~va01R~//~0914R~
        for ( int y = 0; y < Wnp.MAP_SIZE; y ++ )                  //~va01R~//~0914R~
            BoardMap[ y ][ x ] = new Hole( x, y );                 //~0102R~
    NumCount=0;                                                    //~0101R~
    Mode=MODE_INIT;                                                //~0116R~
    KbFocus=0;                                                     //~0211R~
    sMsg="";                                                       //~0102R~
    sTempMsg="";                                                   //~0102R~
    sSuccessMsg="";                                                //~0211R~
    sProbLevel="";                                                 //~0211I~
    intProbLevel=0;                                                //~v@@@I~
    Flags=0;                                                       //~0109R~
    SameNum=0;                                                     //~0206R~
    CurNum=1;       //cursol number                                //~0212I~
    Shiftkey=0;                                                    //~0206I~
    Controlkey=0;                                                  //~va20I~
    ActSeed=0;                                                     //~0212I~
    MaxFixed=FixedSeq=0;                                           //~0109I~
    TryTimeSpan=0;                                    //~0212I~
    Score=0;                                                       //~0213I~
    ScoreMax=0;                                                    //~0226I~
    xnpsub.Gblwnpmode=1;    //call from wnp                        //~vj01I~
	Npwtryans.clear();                                             //~v025I~
//    if (Wnp.Sjlang)                                                //~va23I~//~0914R~//~v@@@R~
//        Scongdispstr=Scongdispstrj;                                //~va23I~//~v@@@R~
//    else                                                           //~va23I~//~v@@@R~
//        Scongdispstr=Scongdispstre;                                //~va23I~//~v@@@R~
    Scongdispstr=WnpView.contextR.getStringArray(R.array.SuccessMsg);//~v@@@I~
	Slevelstr=WnpView.contextR.getStringArray(R.array.Levelstr);   //~v@@@I~
    pPIndex=new CPIndex();                                         //~v@@@I~
}
//**************************************************************   //~v@@@I~
void initsize(int Pcreatesw)                                       //~v@@@I~
{                                                                  //~v@@@I~
    NumTextPosy=(int)(Wnp.NUMTEXT_SIZE*9)/10;    //*0.9 baseline   //~v@@@I~
    NumTextFontsz=Wnp.NUMTEXT_SIZE;                                //~v@@@I~
}//initsize                                                        //~v@@@I~
//**************************************************************   //~v@@@I~
String GetMsg()                                                    //~v004R~
{                                                                  //~0101I~
    String csnull="";                                             //~0102R~
    if ((Flags&F_THREAD)!=0)                                       //~v004R~
    {                                                              //~va16I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        sMsgOut="考え中、‘停止’ボタンで中断します";              //~v003R~//~v@@@R~
//     else                                                          //~va23I~//~v@@@R~
//        sMsgOut="In meditation, use 'Stop' to break it.";          //~va23I~//~v@@@R~
  	  if ((Flags & F_MODEANS)!=0)        //answer requres no long time//~v@@@I~
    	sMsgOut="";                    //avoid flickering          //~v@@@I~
      else                                                         //~v@@@I~
      {                                                            //~v@@@I~
		sMsgOut=WnpView.context.getText(R.string.Meditating).toString();//~v@@@I~
	    if (GblNpsubMsg!=null)                                     //~va16I~
    		sMsgOut=sMsgOut+"("+GblNpsubMsg+")";                   //~va16I~
      }                                                            //~v@@@I~
    }                                                              //~va16I~
    else                                                           //~0130I~
	if (!sTempMsg.equals(csnull))                                  //~va01R~
    {                                                              //~0102I~
    	sMsgOut=sTempMsg;                                         //~0102I~
        sTempMsg=csnull;                                           //~0102R~
	}                                                              //~0102I~
    else                                                           //~0102I~
    	sMsgOut=sMsg;                                             //~0102I~
//System.out.println("GetMsg="+sMsgOut+",sTempMsg="+sTempMsg+",flag="+(Flags&F_THREAD));//~va03R~//~v@@@R~
	return sMsgOut;                                                //~0102R~
}                                                                  //~0101I~
void SetTempMsg(String Pmsg)                                       //~v004R~
{                                                                  //~0102I~
//System.out.println("SetTempMsg="+Pmsg);                          //~va03R~//~v@@@R~
	sTempMsg=Pmsg;                                                 //~0102I~
}                                                                  //~0102I~
/*
===============================================================================
    ＜ Board::~Board ＞                                          //~v@@@R~
===============================================================================
===============================================================================*/
//void _Board()                                                    //~vj01R~
//{                                                                //~vj01R~
//    for ( int x = 0; x < wnp.MAP_SIZE; x ++ )                    //~vj01R~
//        for ( int y = 0; y < wnp.MAP_SIZE; y ++ )                //~vj01R~
//            delete  BoardMap[ y ][ x ];                          //~vj01R~
//}                                                                //~vj01R~
//===============================================================================//~0206R~
//return Mode at entry                                             //~0206R~
//===============================================================================*///~0206R~
void    CreateBoard( CPattern pat )                               //~v004R~
{
    int oldmode=Mode;                                              //~0206I~
    int pendsw=0;                                                  //~v012I~
    boolean swindx;                                                //~v@@@I~
//**********************************                               //~v012R~
    swindx=(Flags & F_READINDX)!=0;	//restore from index file      //~v@@@I~
    if (swindx)                                                    //~v@@@I~
    	Flags &=~F_READINDX;		//restore from index file      //~v@@@I~
    pPat = pat;
    ActSeed=pat.Seed;	//it my be from file                       //~v012I~
    NumCount=0;                                                    //~0101R~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {
            if ( BoardMap[ by ][ bx ]!=null )                      //~vj01R~
            {
//              delete BoardMap[ by ][ bx ];                       //~vj01R~
                BoardMap[ by ][ bx ] = null;                       //~vj01R~
            }
            BoardMap[ by ][ bx ] = new Hole( bx, by );
            int num;                                               //~9C09I~
            if ( (num=pPat.QuestionData[ by ][ bx ])!=0 )          //~vj01R~
            {
                BoardMap[ by ][ bx ].SetNum(num,MODE_FILEDATA,0); //~0116R~
                NumCount++;                                        //~0101R~
            }
            else                                                   //~0116I~
                BoardMap[ by ][ bx ].SetNum(0,0,0);               //~0116I~
        }
    if (NumCount!=0)                                               //~vj01R~
    {                                                              //~0116I~
        if (oldmode==MODE_INIT)                                    //~0206I~
            OnAnswer(0/*create board*/);                                            //~0206I~//~v@@@R~
//      else                                                       //~v023R~
//  		if ((Flags & F_MODEMAKE)==0)                           //~v023R~
//      		Flags|=F_MODEANS;                                  //~v023R~
	    Mode=MODE_KEYINDATA;          //next is keyin              //~0116R~
    	Flags&=~F_MODEMAKE;                                        //~v023I~
        Flags|=F_MODEANS;                                          //~v023I~
    }                                                              //~0116I~
//  else                                                           //~v023R~
//      if (!(Flags & F_MODEANS))                                  //~v003R~
//          Flags|=F_MODEMAKE;                                     //~v003R~
//  	if ((Flags & F_MODEMAKE)==0)                               //~v023R~
//      	Flags|=F_MODEANS;                                      //~v023R~
    Flags&=~(F_ERROR|F_ONXX);                                      //~0130R~
//restore pending data                                             //~v012I~
    for ( int ii = 0; ii < Wnp.MAP_SIZE; ii ++ )                   //~va01R~//~0914R~
        for ( int jj = 0; jj < Wnp.MAP_SIZE; jj ++ )               //~va01R~//~0914R~
        {                                                          //~v012I~
        	if (pPat.PendingData[ii][jj]!=0)                          //~v012I~
            {                                                      //~v012I~
            	pendsw=1;                                          //~v012I~
                break;                                             //~v012I~
            }                                                      //~v012I~
        }                                                          //~v012I~
    if (pendsw!=0)                                                    //~v012I~
    {                                                              //~v012I~
   		Flags&=~F_ONXX;                                            //~v012I~
    	Flags|=F_ONTRY;                                            //~v012I~
    	Flags|=F_READFILE;                                         //~v012I~
        OnSetend(0);    //get anser data                           //~v012I~//~v@@@R~
//      OnSetend(2);    //restart                                  //~v@@@R~
        ScoreMax=pPat.ScoreMax;                                   //~v024I~
//        System.out.println("createboad scoremax="+ScoreMax);     //~v@@@R~
    }                                                              //~v012I~
    sProbLevel="";                                                 //~0211I~
    intProbLevel=0;                                                //~v@@@I~
    if (swindx)                                                    //~v@@@M~
    {                                                              //~v@@@I~
   		Flags&=~F_ONXX;                                            //~v@@@I~
//  	Flags|=F_ONTRY;                                            //~v@@@R~
        ScoreMax=pPat.ScoreMax;                                    //~v@@@M~
        intProbLevel=pPat.intProbLevel;                            //~v@@@I~
	    sProbLevel=pPat.strProbLevel;                              //~v@@@I~
//        System.out.println("createboad index scoremax="+ScoreMax);//~v@@@R~
    }                                                              //~v@@@I~
}//createboard                                                     //~v@@@R~
//******************************************                       //~v012I~
//restore pending data                                             //~v012I~
//******************************************                       //~v012I~
void    Restoreprevinput( )                                        //~v012I~
{                                                                  //~v012I~
	int inputnum,keyinsw=0;                                                  //~v012I~//~v@@@R~
//***********************                                          //~v012I~
    for ( int ii = 0; ii < Wnp.MAP_SIZE; ii ++ )                   //~va01R~//~0914R~
        for ( int jj = 0; jj < Wnp.MAP_SIZE; jj ++ )               //~va01R~//~0914R~
        {                                                          //~v012I~
        	inputnum=pPat.PendingData[ii][jj];                     //~v012I~
            if (inputnum!=0)                                          //~v012I~
            {                                                      //~v012I~
            	keyinsw=1;                                         //~v@@@I~
        	  if (pPat.NextData[ii][jj]!=0) //by enxt button          //~v@@@I~
                BoardMap[ii][jj].SetNum(inputnum,MODE_KEYINANS,Hole.HOLE_NEXT);//~v@@@I~
              else                                                 //~v@@@I~
                BoardMap[ii][jj].SetNum(inputnum,MODE_KEYINANS,Hole.HOLE_PENDDATA);//~v024R~
                NumCount++;                                        //~v012I~
            }                                                      //~v012I~
        }                                                          //~v012I~
    Penalty=pPat.savedPenalty;                                     //~v@@@R~
    if (keyinsw!=0)                                                   //~v@@@I~
    	Mode=MODE_KEYINANS;                                        //~v@@@I~
}                                                                  //~v012I~
////===============================================================================*///~0122R~
void    ResetErr(int Pnextclearsw)                                 //~v004R~
{                                                                  //~0122R~
	int err;                                                  //~0212I~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~0212I~
        	err=BoardMap[ by ][ bx ].GetErr();                    //~0212R~
            if (err==0)                                              //~0212I~
            	continue;                                          //~0212I~
            if (err==Hole.HOLE_PENDDATA) 	//keep file input pending data//~v024I~
            	continue;                                          //~v024I~
            err=(err/Hole.HOLE_NEXT)*Hole.HOLE_NEXT;	//clear other than NEXT//~vj01R~
        	if (Pnextclearsw!=0)                                   //~vj01R~
            	if (err>=Hole.HOLE_NEXT)                           //~vj01R~
                	err-=Hole.HOLE_NEXT;                           //~vj01R~
    	    BoardMap[ by ][ bx ].SetNum(-1,-1,err);               //~0212R~
		}	                                                       //~0212I~
}                                                                  //~0122R~
//===============================================================================*///~0102I~
//===============================================================================*///~0103I~
//=for Question case                                               //~0116I~
//===============================================================================*///~0116I~
void    SetAllAnswer()                                             //~v004R~
{                                                                  //~0103I~
	M99  pm99;                                                    //~0103I~
    NPWORKT pnpwt;                                                //~0103I~
    int  mode,err,num;                                             //~0122R~
//*********                                                        //~0103I~
    pnpwt=Npworkt;                                                 //~v004R~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~0103I~
            Hole phole=BoardMap[ bx ][ by ];                       //~vj01R~
		    pm99=pnpwt.m99[bx][by];     //answer                   //~v004R~
            num=phole.GetNum();          //init data              //~0130I~
            if (num==0)					//may set ans	           //~vj01R~
        		if ((Flags&F_MODEANS)!=0)    //ignore ans when make mode//~v004R~
            		num=pm99.fnum;		//result                   //~0130R~
            err=0;                                                 //~0108I~
            if (num!=0)                                            //~vj01R~
                if (MaxFixed!=0 && MaxFixed!=Wnp.PEG_MAX)  //err   //~va01R~//~0914R~
                {                                                  //~0122R~
                    if (MaxFixed==pm99.fseq && NpsubRc!=12)  //err stopped not multi sol//~0130R~
                        err=Hole.HOLE_ERR;                         //~vj01R~
                    if (pm99.fnum==0||pm99.dlvl==xnpsub.LEVEL_INIT)	//	init data err//~vj01R~
                    	mode=phole.GetState();                           //~0122R~
                    else                                           //~0122I~
                    	mode=MODE_OUTANS;                          //~0123R~
                }                                                  //~0122R~
                else                                               //~0122R~
                    if (pm99.dlvl==xnpsub.LEVEL_INIT)              //~vj01R~
                        mode=-1;    //no change                    //~0122I~
                    else                                           //~0122I~
						if ((Flags&F_ONRCHK)!=0) //set all when rchk mode//~v004R~
//                          mode=MODE_OUTANS;                      //~v020R~
                            mode=-1;	//nochange                 //~v020R~
                        else                                       //~0108R~
                            mode=-1;	//nochange                 //~0116R~
			else                                                   //~0103I~
            	mode=0;                                            //~0103I~
            if (mode>=0)	//set data                             //~0116I~
            {                                                      //~0123I~
            	phole.SetNum(num,mode,err);                       //~0122R~
            }                                                      //~0123I~
                                                                   //~0103I~
        }                                                          //~0103I~
	if ((Flags & F_ONRCHK)!=0 && MaxFixed==Wnp.PEG_MAX)            //~va01R~//~0914R~
    {                                                              //~0129I~
//      Mode=MODE_OUTANS;                                          //~v020R~
//  	FixedSeq=MaxFixed;                                         //~v020R~
//      SaveAnswer();                                              //~v020R~
        MaxFixed=FixedSeq=pnpwt.idatano;		//initial no       //~v020R~
	    pnpwt.seqno=MaxFixed;                                      //~v020R~
    }                                                              //~0129I~
    else                                                           //~0109I~
//        if (!(Flags & F_ONTRY))                                  //~0130R~
        {                                                          //~0130R~
            if (MaxFixed!=Wnp.PEG_MAX)  //err                      //~va01R~//~0914R~
                FixedSeq=MaxFixed;                                 //~0130R~
            else                                                   //~0130R~
                FixedSeq=pnpwt.idatano;                           //~0130R~
            if (MaxFixed!=pnpwt.idatano)                          //~0130R~
                Mode=MODE_OUTANS;                                  //~0130R~
        }                                                          //~0130R~
                                                                   //~0129I~
}//SetAllAnswer                                                    //~0103I~//~v@@@R~
//===============================================================================*///~v012I~
//get current input number                                         //~v012I~
//===============================================================================*///~v012I~
void    GetInpnum(int [][]Pinpnum)                                   //~v012I~
{                                                                  //~v012I~
	Hole phole;                                                    //~v012I~
    int  ii,jj;                                                    //~v012I~
//*******************************                                  //~v012I~
    for (ii=0;ii<Wnp.MAP_SIZE;ii++)                                //~va01R~//~0914R~
        for (jj=0;jj<Wnp.MAP_SIZE;jj++)                            //~va01R~//~0914R~
        {                                                          //~v012I~
	    	phole=BoardMap[ii][jj];                                //~v012I~
            if (phole!=null)                                             //~v012I~
            {                                                      //~v@@@I~
	    		Pinpnum[ii][jj]=phole.GetNum();                    //~v012I~
                if (phole.GetErr()==Hole.HOLE_NEXT)                     //~v@@@I~
	    			Pinpnum[ii][jj]+=NEXT_VALUE;                   //~v@@@I~
            }                                                      //~v@@@I~
            else                                                   //~v012I~
	    		Pinpnum[ii][jj]=0;                                 //~v012I~
        }                                                          //~v012I~
}                                                                  //~v012I~
//===============================================================================*///~0129I~
//save hole answer data to pattern for answer write                //~0129I~
//===============================================================================*///~0129I~
void    SaveAnswer()                                               //~v004R~
{                                                                  //~0129I~
    for (int ii=0;ii<Wnp.MAP_SIZE;ii++)                            //~va01R~//~0914R~
        for (int jj=0;jj<Wnp.MAP_SIZE;jj++)                        //~va01R~//~0914R~
	    	pPat.SetAnsData(ii,jj,BoardMap[ii][jj].GetNum());    //~0129I~
}                                                                  //~0129I~
//===============================================================================*///~0312I~
//save xnp result answer                                           //~0312I~
//===============================================================================*///~0312I~
void    SaveAnswerXnp()                                            //~v004R~
{                                                                  //~0312I~
//*******************                                              //~0312I~
    for (int ii=0;ii<Wnp.MAP_SIZE;ii++)                            //~va01R~//~0914R~
        for (int jj=0;jj<Wnp.MAP_SIZE;jj++)                        //~va01R~//~0914R~
	    	pPat.SetAnsData(ii,jj,Npworkt.m99[ii][jj].fnum);     //answer//~0312R~
}                                                                  //~0312I~
//===============================================================================*///~0129I~
//=che i'll try answer                                             //~0129I~
//===============================================================================*///~0129I~
int CheckAnswer(int Pallsw)                                        //~v004R~
{                                                                  //~0129I~
    int   ii,jj,err=0,num;                                         //~0312R~
    Hole phole;                                                    //~vj01R~
//************                                                     //~0129I~
	ResetErr(0);	//once clear err                               //~0212I~
//row                                                              //~0129I~
    for (ii=0;ii<Wnp.MAP_SIZE;ii++)                                //~va01R~//~0914R~
        for (jj=0;jj<Wnp.MAP_SIZE;jj++)                            //~va01R~//~0914R~
	    {//~0312R~
        	phole=BoardMap[ii][jj];
		    num=phole.GetNum();                                   //~0312R~
			if (num!=pPat.GetAnsData(ii,jj))                      //~0312I~
		    	if (Pallsw!=0 || num!=0)                           //~vj01R~
                {                                                  //~0312I~
	                err=1;                                         //~0312I~
            		phole.SetNum(-1,-1,Hole.HOLE_ERR);             //~vj01R~
                }                                                  //~0312I~
	    }	//~0312I~
	if (Pallsw!=0)                                                 //~vj01R~
    {                                                              //~va45I~
        int soundid;                                               //~va45I~
        if (err==0)                                                //~vj01R~
        {                                                          //~0312R~
            CongMsg=0;                                             //~0312R~
            OnSuccess();                                           //~0312R~
            Mode=MODE_OUTANS;                                      //~0312R~
	        long ctime=System.currentTimeMillis();                 //~vj01R~
            long ts=ctime-TryStartTime;                            //~vj01R~
            TryTimeSpan=(int)(ts/1000);                            //~vj01R~
            if (timer_SETEND!=null)                                //~vj01I~
            {                                                      //~vj01I~
            	timer_SETEND.stop();                               //~vj01R~
                timer_SETEND=null;                                 //~vj01I~
            }                                                      //~vj01I~
            CalcScore();                                           //~0312R~
            pPat.Score=Score;                                      //~v@@@I~
	        pPIndex.registerScore(pPat);                           //~v@@@I~
        	soundid=SOUNDID_ANS_GOOD;                              //~va45I~
        }                                                          //~0312R~
        else                                                       //~0312R~
        {                                                          //~0312R~
            Flags|=F_ERROR;                                        //~0312R~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            SetTempMsg("誤解？");                                  //~0312R~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            SetTempMsg("Correct？");                               //~va23I~//~v@@@R~
			SetTempMsg(WnpView.context.getText(R.string.CorrectQ).toString());                //~v@@@I~
        	soundid=SOUNDID_ANS_NG;                                //~va45I~
        }                                                          //~0312R~
		AG.aBGMList.playOnce(soundid);                        //~va40I~//~va45I~
    }                                                              //~va45I~
    return err;                                                    //~0312I~
}//CheckAnswer                                                     //~vj01R~
//=============================================================================*///~0211I~
int     OnSuccess()                                                //~v004R~
{                                                                  //~0211I~
	int[] waittm={1,1,1,2,1,3};                          //~vj01R~
    int col;                                                       //~vj01R~
//************************                                         //~va21I~
//  if (Wnp.Sjlang)                                                  //~va23I~//~0914R~//~v@@@R~
//    SetTempMsg("正解！");                                          //~0227I~//~v@@@R~
//  else                                                             //~va23I~//~v@@@R~
//    SetTempMsg("Done！");                                          //~va23I~//~v@@@R~
	SetTempMsg(WnpView.context.getText(R.string.Done).toString());   //~v@@@I~
//System.out.println("onsuccess  repeat="+CongMsg+",score="+Score+",max="+ScoreMax);//~v@@@R~
    if (CongMsg>=MAX_CONG)                                         //~0211R~
    {                                                              //~0211I~
        Scongdispsw=0;                                             //~va21I~
        if (CongMsg==MAX_CONG)  //last                             //~va21R~
        {                                                          //~va21R~
    		if ((Slevelsummary & (xnpsub.LEVEL_HARDP2_S|xnpsub.LEVEL_HARDP1_S))!=0)//~va21R~
        	if (Score>(ScoreMax/2))                                //~va21I~
            {                                                      //~va21I~
                Scongdispsw=1;                                     //~va21R~
                timerid=IDC_SUCCESS;                               //~va21R~
//              timer_SUCCESS=new Timer(Scongdisptime,pView);   //100ms//~va21R~//~0915R~
                timer_SUCCESS=new NppTimer(timerid,Scongdisptime,this);   //100ms//~0915I~
//              timer_SUCCESS.setCoalesce(true);                   //~va21R~//~0915R~
                timer_SUCCESS.setRepeats(false);                   //~va21R~
                timer_SUCCESS.start();                             //~va21R~
                CongMsg++;                                         //~va21R~
                return 0;                                          //~va21R~
            }                                                      //~va21I~
        }                                                          //~va21R~
        return TryTimeSpan;                                        //~0212R~
	}                                                              //~0211I~
    col=OBJ_COLOR[ST_QDATA];                                       //~0914R~
//  col=((col & 0x0000ffff)<<8)+((col & 0x00ff0000)>>16);          //~0211R~//~v@@@R~
    col=Color.rgb(Color.green(col),Color.blue(col),Color.red(col));//~v@@@I~
    OBJ_COLOR[ST_QDATA]=col;                            //~vj01R~  //~0914R~
                                                                   //~0211R~
    col=OBJ_COLOR[ST_ANS];                                         //~0914R~
//  col=((col & 0x0000ffff)<<8)+((col & 0x00ff0000)>>16);          //~0211R~//~v@@@R~
    col=Color.rgb(Color.green(col),Color.blue(col),Color.red(col));//~v@@@I~
    OBJ_COLOR[ST_ANS]=col;                                         //~0914R~
                                                                   //~0211R~
    col=OBJ_COLOR[ST_NEXT];                                        //~0914R~
//  col=((col & 0x0000ffff)<<8)+((col & 0x00ff0000)>>16);          //~0211I~//~v@@@R~
    col=Color.rgb(Color.green(col),Color.blue(col),Color.red(col));//~v@@@I~
    OBJ_COLOR[ST_NEXT]=col;                                        //~0914R~
                                                                   //~0211I~
    col=OBJ_COLOR[GRID1];                                          //~0914R~
//System.out.println("onsuccess  oldgrid1="+Integer.toString(Color.red(col),16)+Integer.toString(Color.green(col),16)+Integer.toString(Color.blue(col),16));//~v@@@R~
//  col=((col & 0x0000ffff)<<8)+((col & 0x00ff0000)>>16);          //~0211R~//~v@@@R~
    col=Color.rgb(Color.green(col),Color.blue(col),Color.red(col));//~v@@@I~
//System.out.println("onsuccess  newgrid1="+Integer.toString(Color.red(col),16)+Integer.toString(Color.green(col),16)+Integer.toString(Color.blue(col),16));//~v@@@R~
    OBJ_COLOR[GRID1]=col;                                         //~0914R~
                                                                   //~0211R~
    col=OBJ_COLOR[GRID2];                                          //~0914R~
//System.out.println("onsuccess  oldgrid2="+Integer.toString(Color.red(col),16)+Integer.toString(Color.green(col),16)+Integer.toString(Color.blue(col),16));//~v@@@R~
//  col=((col & 0x0000ffff)<<8)+((col & 0x00ff0000)>>16);          //~0211R~//~v@@@R~
    col=Color.rgb(Color.green(col),Color.blue(col),Color.red(col));//~v@@@I~
//System.out.println("onsuccess  newgrid2="+Integer.toString(Color.red(col),16)+Integer.toString(Color.green(col),16)+Integer.toString(Color.blue(col),16));//~v@@@R~
    OBJ_COLOR[GRID2]=col;                                          //~0914R~
//    if (CongMsg!=0)                                              //~va21R~
//        if (timer_SUCCESS!=null)                                 //~va21R~
//        {                                                        //~va21R~
//            timer_SUCCESS.stop();                                //~va21R~
//            timer_SUCCESS=null;                                  //~va21R~
//        }                                                        //~va21R~
    timerid=IDC_SUCCESS;                                           //~vj01I~
//  timer_SUCCESS=new Timer(waittm[CongMsg]*500,pView);            //~vj01I~//~0915R~
    timer_SUCCESS=new NppTimer(timerid,waittm[CongMsg]*500,this);  //~0915I~
//    timer_SUCCESS.setCoalesce(true);                               //~vj01I~//~0915R~
//  timer_SUCCESS.setRepeats(true);                                //~va21R~
    timer_SUCCESS.setRepeats(false);                               //~va21I~
    timer_SUCCESS.start();                                         //~vj01I~
    CongMsg++;                                                     //~0211R~
    return 0;                                                      //~0212I~
}                                                                  //~0211I~
//===============================================================================*///~0108I~
int SetNextAnswer(int Pfixedseq)                                   //~v004R~
{                                                                  //~0108I~
	M99  pm99;                                                     //~vj01R~
    NPWORKT pnpwt;                                                 //~vj01R~
	Hole phole;                                                    //~vj01R~
    int  bx,by;                                                    //~0123R~
//*********                                                        //~0108I~
    pnpwt=Npworkt;                                                 //~v004R~
//reset previous last id                                           //~0108I~
    for (bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                        //~va01R~//~0914R~
        for (by = 0; by < Wnp.MAP_SIZE; by ++ )                    //~va01R~//~0914R~
        {                                                          //~0108I~
		    pm99=pnpwt.m99[bx][by];     //answer                   //~v004R~
            if (pm99.fnum!=0)                                      //~vj01R~
            {                                                      //~0108I~
            	if (pm99.fseq==Pfixedseq-1)                       //~0108I~
                {                                                  //~0108I~
		            phole=BoardMap[ bx ][ by ];                    //~0108I~
                    if (phole.GetState()==MODE_OUTANS)            //~0116R~
	            		phole.SetNum(pm99.fnum,MODE_OUTANS,0);//reset err//~0123I~
            	}                                                  //~0108I~
            }                                                      //~0108I~
        }                                                          //~0108I~
    for (bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                        //~va01R~//~0914R~
        for (by = 0; by < Wnp.MAP_SIZE; by ++ )                    //~va01R~//~0914R~
        {                                                          //~0108I~
		    pm99=pnpwt.m99[bx][by];     //answer                   //~v004R~
            if (pm99.fnum!=0)                                      //~vj01R~
            {                                                      //~0108I~
            	if (pm99.fseq==Pfixedseq)                         //~0108I~
                {                                                  //~0108I~
		            phole=BoardMap[ bx ][ by ];                    //~0108I~
            		phole.SetNum(pm99.fnum,MODE_OUTANS,0);       //~0116R~
                    KbFocus=bx*Wnp.MAP_SIZE+by;    //set forcus    //~va01R~//~0914R~
                    NumCount++;                                    //~0123I~
                    return 0;                                      //~0108I~
            	}                                                  //~0108I~
            }                                                      //~0108I~
        }                                                          //~0108I~
	return 4;                                                      //~0108I~
}                                                                  //~0108I~
/*
===============================================================================
    ＜ Board::Show ＞                                            //~v@@@R~
===============================================================================
===============================================================================*/
void    Show(Canvas pDC)                                         //~vj01R~//~0914R~//~0915R~
{
    int sz,idx;                                                    //~va21R~
    long tm;                                                       //~va21I~
//*******************                                              //~va21I~
//  Paint pDCpaint=new Paint(Paint.ANTI_ALIAS_FLAG);               //~v@@@R~
    if (Scongdispsw!=0)	//display success last char                //~va21R~
    {                                                              //~va21I~
        tm=System.currentTimeMillis()/1000;                        //~va21I~
        idx=(int)tm%4;		//0,1,2,3                              //~va21R~
        if (idx==3)                                                //~va21I~
        {                                                          //~va21I~
	        sz=(int) ((Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.MAP_SIZE/2);           //~va21I~//~0914R~
            if (!Wnp.Sjlang)                                       //~va23I~//~0914R~
              	sz/=2;                                             //~va23I~
			pDCpaintT.setTextSize(sz);//~va23R~                          //~0914R~//~v@@@R~
			pDCpaintT.setTypeface(Wnp.Sfontname);                               //~0914I~//~v@@@R~
        	pDCpaintT.setColor(COL_CONG_STR2);                           //~va21I~//~v@@@R~
        	pDC.drawText(Scongdispstr[idx],0,sz,pDCpaintT);                //~va21I~//~v@@@R~
            if (!Wnp.Sjlang)                                       //~va23I~//~0914R~
            {                                                      //~va23I~
	        	pDC.drawText(Scongdispstr[idx+1],sz/2,sz+sz,pDCpaintT);    //~va23I~//~v@@@R~
	        	pDC.drawText(Scongdispstr[idx+2],sz,sz+sz+sz,pDCpaintT);   //~va23I~//~v@@@R~
            }                                                      //~va23I~
        }                                                          //~va21I~
        else                                                       //~va21I~
        {                                                          //~va21I~
	        sz=(int) ((Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.BOARDTYPE);            //~va21I~//~0914R~
			pDCpaintT.setTextSize(sz);                                   //~0914I~//~v@@@R~
			pDCpaintT.setTypeface(Wnp.Sfontname);                            //~0914I~//~v@@@R~
        	pDCpaintT.setColor(COL_CONG_STR);                            //~va21R~//~v@@@R~
        	pDC.drawText(Scongdispstr[idx],idx*sz,(idx+1)*sz,pDCpaintT);   //~va21R~//~v@@@R~
        }                                                          //~va21I~
    	return;                                                    //~va21I~
    }                                                              //~va21I~
//System.out.println("Board:Show samenum="+SameNum);                                //~v012R~//~v@@@R~
    int samenum=SameNum;                                           //~0212I~
    if ((Flags & F_MODEMAKE)!=0)                                   //~v004R~
        samenum=0;                                                 //~0212I~
    ShowGrid(pDC);                                                 //~0101I~
                                                                   //~0205I~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {
            if ( BoardMap[ by ][ bx ]!=null )                      //~vj01R~
            {                                                      //~va03I~
//System.out.println("call hole show bx="+bx+",by="+by+",samenum="+samenum);//~va03R~
                BoardMap[ by ][ bx ].Show( pDC,                    //~vj01R~
                                            (KbFocus==by*Wnp.MAP_SIZE+bx)?1:0,//~va01R~//~0914R~
                                             samenum,              //~0212R~
							                 (Flags&F_MODEANS)!=0||Mode==MODE_ENDQDATA);//~v004R~
            }                                                      //~va03I~
        }
}//Show                                                            //~v@@@R~
//=============================================================================*///~9C09I~
void    ShowGrid(Canvas pDC)                                    //~vj01R~//~0914R~//~0915R~
{                                                                  //~9C09M~
    int bx;                          //~9C09M~                     //~0915R~
    int   x,y,xe,ye;                                               //~0915R~
	int    br1M=OBJ_COLOR[GRID1_MAKE];                             //~va23R~//~0914R~
    int    br2M=OBJ_COLOR[GRID2_MAKE];                             //~va23R~//~0914R~
	int    br1A=OBJ_COLOR[GRID1];                                  //~va23R~//~0914R~
    int    br2A=OBJ_COLOR[GRID2];                                  //~va23R~//~0914R~
    int    br;                                                     //~vj01I~//~0914R~
//  Paint pDCpaint=new Paint();                                                               //~vj01I~//~0915R~//~v@@@R~
    int   old_br=pDCpaintB.getColor();                                   //~vj01R~//~0914R~//~0915R~//~v@@@I~
//System.out.println("old:"+Integer.toString(old_br,16));          //~v@@@R~
    if ((Flags & F_MODEMAKE)!=0)                                   //~v004R~
    	br=br1M;   //inner grid;save cuurrent                      //~vj01R~
    else                                                           //~0212I~
    	br=br1A;   //inner grid;save cuurrent                      //~vj01R~
//System.out.println("grid1:"+Integer.toString(br,16));            //~v@@@R~
    BoardToScreen( 0,0,wkpoint);                                   //~va23R~
    x=wkpoint.x;y=wkpoint.y;                                       //~v004I~
    pDCpaintB.setColor(br);   //outer line                               //~va03I~//~v@@@R~
    xe=x+(Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.MAP_SIZE-1;                 //~0915R~
    ye=y+(Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.MAP_SIZE-1;                 //~0915R~
    pDC.drawRect( x-Wnp.GAP, y-Wnp.GAP,xe,ye,pDCpaintB);            //~0915R~//~v@@@R~
    if ((Flags & F_MODEMAKE)!=0)                                   //~vj01R~
    	br=br2M;				//outer grid                       //~vj01R~
    else                                                           //~0212I~
    	br=br2A;				//outer grid                       //~vj01R~
    pDCpaintB.setColor(br);   //inner grid;save cuurrent                 //~vj01I~//~v@@@R~
//System.out.println("grid2:"+Integer.toString(br,16));            //~v@@@R~
    for ( bx = 0; bx <Wnp.MAP_SIZE; bx ++ )      //virtical line   //~va01R~//~0914R~
    {                                                              //~9C09I~
    	BoardToScreen( bx/Wnp.BOARDTYPE*Wnp.BOARDTYPE,bx%Wnp.BOARDTYPE*Wnp.BOARDTYPE, wkpoint);//~va23R~//~0914R~
	    x=wkpoint.x;y=wkpoint.y;                                   //~v004I~
	    xe=x+(Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.BOARDTYPE-Wnp.GAP-1;    //~0915R~
	    ye=y+(Wnp.OBJECT_SIZE+Wnp.GAP)*Wnp.BOARDTYPE-Wnp.GAP-1;    //~0915R~
	    pDC.drawRect( x, y,xe,ye,pDCpaintB);//~v012R~//~0914R~      //~0915R~//~v@@@R~
	}                                                              //~9C09R~
    br=old_br;                                                     //~vj01I~//~0915R~//~v@@@R~
    pDCpaintB.setColor(br);                                              //~vj01R~//~0915R~//~v@@@R~
}                                                                  //~9C09M~
                                                                 //~9C09I~
//=============================================================================*///~0101I~
//condition to NumBtn dispable                                     //~0101I~
// return numbutton enable                                         //~0116I~
//=============================================================================*///~0101I~
boolean  ShowNumBtn()                                              //~vj01R~
{                                                                  //~0101I~
	boolean enable=FALSE;                                          //~vj01R~
    if ((Flags&F_THREAD)!=0)                                       //~v004R~
    	return enable;		//false                                //~0129I~
                                                                   //~0130I~
    switch (Mode)                                                  //~0101I~
    {                                                              //~0101I~
    case MODE_INIT:                                                //~0102I~
        enable=TRUE;                                               //~v003I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        sMsg="問題を設定してください。手入力かファイルメニューか‘自動作問’。";//~vj01R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        sMsg="Set question data by manual input,thru file menu or pressing 'Make'.";//~va23I~//~v@@@R~
		sMsg=WnpView.context.getText(R.string.RequestSetQ).toString();//~v@@@I~
        break;                                                     //~0102I~
    case MODE_NOINPUT:                                             //~0122R~
//		System.out.println("MODE_INPUT stopreq="+xnpsub.GblSubthreadStopReq);//~v@@@R~
        if ((Flags&F_MODEANS)!=0)                                  //~v004R~
        {                                                          //~0122R~
            enable=TRUE;                                           //~0122R~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sMsg="問題データを設定してください";                   //~vj01R~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sMsg="Set question data.";                             //~va23I~//~v@@@R~
			sMsg=WnpView.context.getText(R.string.RequestSetQ0).toString();//~v@@@I~
        }                                                          //~0122R~
        else                                                       //~0122R~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sMsg="パターンーデータを設定してください";             //~vj01R~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sMsg="Set pattern mask.";                              //~va23I~//~v@@@R~
			sMsg=WnpView.context.getText(R.string.RequestPattern).toString();//~v@@@I~
        break;                                                     //~0122R~
    case MODE_FILEDATA:		//just after file in                   //~0116R~
		if ((Flags&F_MODEANS)!=0)                                  //~v004R~
	    	enable=TRUE;                                           //~0116I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        sMsg="修正がなければ‘試答！’を押してから開始してください";//~vj01R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        sMsg="If there is no modification,press 'Try!' then timer starts.";//~va23I~//~v@@@R~
		sMsg=WnpView.context.getText(R.string.RequestStart).toString();//~v@@@I~
        break;                                                     //~0101I~
    case MODE_KEYINDATA:	//input from screen                    //~0116I~
        if ((Flags&F_MODEANS)!=0)                                  //~v004R~
            enable=TRUE;                                           //~0129R~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        sMsg="設定完了なら‘試答！’を押してから開始してください"; //~vj01R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        sMsg="If end of question data setup,press 'Try!' then timer starts.";//~va23I~//~v@@@R~
		sMsg=WnpView.context.getText(R.string.RequestStart2).toString();//~v@@@I~
        break;                                                     //~0116I~
    case MODE_ENDQDATA:                                             //~0116R~
//        if (Flags&F_MODEANS)                                     //~v003R~
//        {                                                        //~v003R~
//           if (Wnp.Sjlang)                                         //~va23I~//~0914R~//~v@@@R~
//            sMsg="答入力が終了後‘正解？’してチェックしてください";//~vj01R~//~v@@@R~
//           else                                                    //~va23I~//~v@@@R~
//            sMsg="Check your answer is correct or not by pressing 'Ans!' if input completed.";//~va23I~//~v@@@R~
			sMsg=WnpView.context.getText(R.string.RequestDone).toString();//~v@@@I~
    		enable=TRUE;                                           //~0123I~
//        }                                                        //~v003R~
//        else                                                     //~v003R~
//            sMsg.Format("作成完了,解く場合は解答モードにしてください");//~v003R~//~v@@@R~
        break;                                                     //~0116M~
    case MODE_KEYINANS:                                            //~0116I~
//        if (Flags & F_ANSCHK)                                    //~0205R~
//        {                                                        //~0205R~
//            if (NpsubRc)                                         //~0205R~
//            {                                                    //~0205R~
//                SetTempMsg("誤解？");                            //~0205R~//~v@@@R~
//                enable=TRUE;                                     //~0205R~
//            }                                                    //~0205R~
//            else                                                 //~0205R~
//            {                                                    //~0205R~
//                SetTempMsg("正解！");                            //~0205R~//~v@@@R~
//                sMsg.Format("正解！");                           //~0205R~//~v@@@R~
//            }                                                    //~0205R~
//        }                                                        //~0205R~
//        else                                                     //~0205R~
//        {                                                        //~0205R~
//            sMsg.Format("入力が完了したら‘実行’で確認してください");//~0227R~//~v@@@R~
//            enable=TRUE;                                         //~0205R~
//        }                                                        //~0205R~
        if (NumCount==Wnp.PEG_MAX)                                 //~va01R~//~0914R~
        {                                                          //~0212I~
        	if ((Flags & F_ERROR)!=0)                              //~v004R~
    			enable=TRUE;                                       //~0212I~
            else                                                   //~v012I~
//           if (Wnp.Sjlang)                                         //~va23I~//~0914R~//~v@@@R~
//            sMsg="‘正解？’で確認してください";                   //~vj01R~//~v@@@R~
//           else                                                    //~va23I~//~v@@@R~
//            sMsg="Confirm answer by 'Ans?' button.";             //~va23I~//~v@@@R~
			sMsg=WnpView.context.getText(R.string.RequestDone2).toString();//~v@@@I~
        }                                                          //~0212I~
        else                                                       //~0205I~
        {                                                          //~0205I~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sMsg="空白を埋めてください";                           //~vj01R~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sMsg="fill empty place";                               //~va23I~//~v@@@R~
			sMsg=WnpView.context.getText(R.string.FillEmpty).toString();//~v@@@I~
            enable=TRUE;                                           //~0205I~
        }                                                          //~0205I~
        break;                                                     //~0116I~
    case MODE_OUTANS:                                              //~0116I~
        if ((Flags & F_ERROR)!=0)                                  //~v004R~
    		enable=TRUE;                                           //~0130I~
	    sMsg=sSuccessMsg;                                          //~vj01R~
        break;                                                     //~0116I~
    }                                                              //~0101I~
//    if (!enable)                                                 //~0205R~
//        CurNum=0;                                                //~0205R~
//System.out.println("Mode="+Mode+",flag="+Flags+sMsg);            //~va03R~//~v@@@R~
    return enable;                                                 //~0101I~
}                                                                  //~0101I~
//===============================================================================//~0123R~
//rc<0:out of board                                                //~v@@@I~
//rc==0:not ACTION_DOWN (chk point only)                           //~v@@@I~
//rc==1:ACTION_DOWN (csr moved)                                    //~v@@@I~
//===============================================================================//~v@@@I~
int DropDown(Point point,int Pnum)                            //~vj01R~//~v@@@R~
{                                                                  //~9C09I~
    int    bx, by,newnum;                                          //~0102R~
    int    oldmode=Mode;                                           //~0123I~
    int numBtnNo;                                                  //~v@@@I~
//****************                                                 //~v@@@I~
    if (Pnum<0)         //mouse                                  //~0129R~//~v@@@R~
    {                                                              //~0102I~
	  numBtnNo=pView.pButtonDlg.NumBtnPush(Pnum,point);		//numbtnn push accepted//~v@@@R~
	  if (numBtnNo>0)		//numbtn push accepted                 //~v@@@R~
      {                                                            //~v@@@I~
        if (numBtnNo==ButtonDlg.BTN_NUM_0)                         //~v@@@I~
        	numBtnNo=0;                                            //~v@@@I~
        if (Pnum==-1)    //action down                             //~v@@@I~
        {                                                          //~v@@@I~
            if (numBtnNo==ButtonDlg.BTN_NUM_SAME)                  //~v@@@M~
                return KeyProc(KEYCODE_SAMEKEY);                   //~v@@@M~
            else                                                   //~v@@@M~
            if (numBtnNo==ButtonDlg.BTN_NUM_MEMO)                  //~v@@@M~
                return KeyProc(KEYCODE_MEMOKEY);                   //~v@@@M~
            else                                                   //~v@@@I~
            if (Shiftkey!=0          //0+x     ;;samekey highlight //~v@@@I~
            ||  Controlkey!=0)       //C+num                       //~v@@@I~
            {                                                      //~v@@@I~
            	if (numBtnNo>9)                                    //~v@@@R~
                	return KeyProc(KeyEvent.KEYCODE_0);            //~v@@@R~
            	else                                               //~v@@@R~
                	return KeyProc(numBtnNo+KeyEvent.KEYCODE_0);   //~v@@@R~
        	}                                                      //~v@@@I~
    		bx=KbFocus%Wnp.MAP_SIZE;                               //~v@@@R~
    		by=KbFocus/Wnp.MAP_SIZE;                               //~v@@@R~
        	CurNum=numBtnNo;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else          //action up                                  //~v@@@I~
        {                                                          //~v@@@I~
        	if (numBtnNo>=0 && numBtnNo<=9)                        //~v@@@R~
            	return 1;	//invalidate to draw num button        //~v@@@I~
        	return 0;   //no action at LButtonDown                 //~v@@@R~
        }                                                          //~v@@@I~
      }                                                            //~v@@@I~
      else                                                         //~v@@@I~
      {                                                            //~v@@@I~
    	if ( ! ScreenToBoard( point.x, point.y, wkpoint) )         //~v004R~
        	return -1;                                          //~va23R~//~v@@@R~
        if (Pnum==-2)	//ACTION_UP,point chk only                 //~v@@@I~
        	return 0;                                              //~v@@@I~
	    bx=wkpoint.x;by=wkpoint.y;                                 //~v004I~
	    KbFocus=by*Wnp.MAP_SIZE+bx;                                //~va01R~//~0914R~
        return 1;                                                  //~v@@@I~
      }                                                            //~v@@@I~
	}                                                              //~0102I~
	else                  //kbd input                              //~0129R~
    {                                                              //~0102I~
    	bx=KbFocus%Wnp.MAP_SIZE;                                   //~va01R~//~0914R~
    	by=KbFocus/Wnp.MAP_SIZE;                                   //~va01R~//~0914R~
    }                                                              //~0102I~
    Hole   p_hole= BoardMap[ by ][ bx ];                           //~vj01R~
    if (Pnum==-1)                                                  //~v025I~
    	newnum=CurNum;		//from mouse                           //~v025I~
	else                                                           //~v025I~
    	newnum=Pnum;                                               //~v025I~
//  if ( ! CanMove(p_hole) )                                       //~v025R~
    if ( ! CanMove(p_hole,newnum) )                                //~v025I~
        return 0;                                              //~9C28I~
                                                                   //~0116I~
	if (Mode==MODE_NOINPUT)                                        //~0122R~
    	Mode=MODE_KEYINDATA;                                       //~0116I~
    else                                                           //~0123I~
	if (Mode==MODE_ENDQDATA)                                       //~0123I~
        if ((Flags & F_MODEANS)!=0)                                //~v004R~
    		Mode=MODE_KEYINANS;                                    //~0123R~
        else                                                       //~0123I~
    		Mode=MODE_KEYINDATA;	//accept modification          //~0123I~
                                                                   //~0116I~
//  if (Pnum==-1)                                                  //~v025R~
//  	newnum=CurNum;		//from mouse                           //~v025R~
//  else                                                           //~v025R~
//  	newnum=Pnum;                                               //~v025R~
    int num=p_hole.GetNum();                                      //~0101R~
    int addv=1;                                                    //~0101I~
    if ((Flags&F_MODEMAKE)!=0)    //pattern setting                //~v004R~
        if (num!=0||newnum==-PATTERN_ID)	//already set or force reset//~vj01R~
            p_hole.SetNum(0,0,0);                                 //~0101I~
        else                                                       //~0101I~
            p_hole.SetNum(PATTERN_ID,Mode,0);                     //~0101I~
    else                     //ans mode                            //~0129R~
    {                                                              //~v@@@I~
    	if (num!=0)                                                //~vj01R~
        	if (newnum!=0 && (newnum!=num || Pnum!=-1))//accept key even if same//~v004R~
            {                                                      //~0123I~
	            p_hole.SetNum(newnum,Mode,0);                     //~0123R~
                addv=0;		//num chng,no count chng               //~0123I~
			}                                                      //~0123I~
    	    else                                                   //~0123R~
        	    p_hole.SetNum(0,0,0);                             //~0123R~
        else                                                       //~0123I~
        	if (newnum!=0)                                         //~vj01R~
	        	p_hole.SetNum(newnum,Mode,0);                     //~0123R~
            else                                                   //~0123I~
            	addv=0;                                            //~0123I~
		SetSameNum(0);	//clear samenum effect by digit input      //~v@@@I~
    }                                                              //~v@@@I~
	if (num!=0)                                                    //~vj01R~
    	NumCount-=addv;                                            //~0101R~
    else                                                           //~0101M~
    	NumCount+=addv;                                            //~0101R~
	SetNewNum(by,bx,p_hole,0,0);	//no setans	                   //~0312R~
    if (oldmode!=Mode||NumCount==Wnp.PEG_MAX||NumCount==0)         //~va01R~//~0914R~
        ShowNumBtn();                                              //~0123I~
    return 1;                                                   //~9C28I~//~v@@@R~
}                                                                  //~9C09I~
//===============================================================================//~0205M~
//== chk drop down available                                       //~0205M~
//===============================================================================//~0205M~
//boolean    CanMove(Hole pHole)                                   //~v025R~
boolean    CanMove(Hole pHole,int Pnum)                            //~v025I~
{                                                                  //~0205M~
    if ((Flags & (F_MODEANS|F_MODEMAKE))==0)                       //~v004R~
    {                                                              //~v025I~
    	if (Mode==MODE_INIT)                                       //~v025I~
        {                                                          //~v025I~
    		Mode=MODE_NOINPUT;                                     //~v025I~
            if (Pnum==0)                                           //~v025I~
			    Flags|=F_MODEMAKE;                                 //~v025I~
            else                                                   //~v025I~
			    Flags|=F_MODEANS;                                  //~v025I~
            return true;                                           //~v025I~
    	}                                                          //~v025I~
    	return FALSE;                                              //~0205M~
    }                                                              //~v025I~
	if ((Flags & F_THREAD)!=0)	//running subthread                //~v004R~
    	return FALSE;                                              //~0205M~
    if (Mode==MODE_OUTANS && (Flags & F_ERROR)==0)                 //~v004R~
    	return FALSE;                                              //~0205M~
    int state=pHole.GetState();                                   //~0205M~
    if (pHole.GetErr()==Hole.HOLE_NEXT)                            //~vj01R~
	    return FALSE;                                              //~0205M~
                                                                   //~0205M~
    if (Mode>MODE_ENDQDATA)                                        //~0205M~
    	if (state!=0 && state<MODE_ENDQDATA)                       //~v004R~
	    	return FALSE;                                          //~0205M~
    return TRUE;                                                   //~0205M~
}                                                                  //~0205M~
//===============================================================================//~0205I~
//=rc:0:bypass,1:processed(need invalidate),==============================================================================//~0212I~
//===============================================================================//~0212I~
boolean SetMemo(Point point,int Pnum)                              //~vj01R~
{                                                                  //~0205I~
    int    bx, by,newnum;                                          //~0213R~
    if (Pnum==-1)         //mouse                                  //~0205I~
    {                                                              //~0205I~
    	if ( ! ScreenToBoard( point.x, point.y, wkpoint) )         //~v004R~
        	return FALSE;                                          //~va23R~
	    bx=wkpoint.x; by=wkpoint.y;                                //~v004I~
	    KbFocus=by*Wnp.MAP_SIZE+bx;                                //~va01R~//~0914R~
	}                                                              //~0205I~
	else                  //kbd input                              //~0205I~
    {                                                              //~0205I~
    	bx=KbFocus%Wnp.MAP_SIZE;                                   //~va01R~//~0914R~
    	by=KbFocus/Wnp.MAP_SIZE;                                   //~va01R~//~0914R~
    }                                                              //~0205I~
//System.out.println("SetMemo num="+Pnum+",bx="+bx+",by="+by);     //~v012R~
    Hole   p_hole= BoardMap[ by ][ bx ];                           //~vj01R~
    if (!CanSetMemo(p_hole) )                                      //~0205R~
        return TRUE;                                               //~0213R~
                                                                   //~0205I~
    if (Pnum==-1)                                                  //~0205I~
    	newnum=CurNum;		//from mouse                           //~0205I~
	else                                                           //~0205I~
    	newnum=Pnum;                                               //~0205I~
	if (p_hole.SetMemo(newnum)!=0)		//set memo                 //~vj01R~
    	CountPenalty(PENALTY_MEMO);                                //~0213I~
    return TRUE;                                                   //~0212R~
}//SetMemo                                                                  //~0205I~//~v@@@R~
//===============================================================================//~0205I~
//== chk drop down available                                       //~0205I~
//===============================================================================//~0205I~
boolean    CanSetMemo(Hole pHole)                                  //~vj01R~
{                                                                  //~0205I~
    if (Mode!=MODE_KEYINANS && Mode!=MODE_ENDQDATA)                //~0206R~
    	return FALSE;                                              //~0205I~
    if (pHole.GetNum()!=0)                                         //~vj01R~
    	return FALSE;                                              //~0212I~
                                                                   //~0212I~
    return TRUE;                                                   //~0205I~
}                                                                  //~0205I~
//===============================================================================//~0205I~
void	SetNewNum(int by,int bx,Hole phole,int Psortsw,int Psetanssw)//~vj01R~
{                                                                  //~0130I~
	if (phole.GetState()<=MODE_ENDQDATA)                               //~0213I~
		pPat.SetNewData(by,bx,phole.GetNum(),Psortsw);           //~0213R~
    else                                                           //~0213I~
    	if (Psetanssw!=0)                                          //~vj01R~
		    pPat.SetAnsData(by,bx,phole.GetNum());               //~0312R~
    Flags&=~F_ERROR;                                               //~0130I~
}                                                                  //~0130I~
//===============================================================================//~0205I~
//rc=0:key ignored(Redraw),1:key processed(Redraw),-1:key Ignored(execute default proc)//~v@@@R~
//===============================================================================//~0206I~
int	KeyProc(int nChar)                                             //~vj01R~
{                                                                  //~0102I~
	                                                     //~0102I~
	Hole phole;                                                    //~vj01R~
    int rc;                                                        //~vj01R~
    int num;                                                    //~va20I~//~v@@@R~
//*************************************                            //~va20R~
    if (KbFocus<0 || KbFocus>=Wnp.PEG_MAX)                         //~va01R~//~0914R~
        return -1;                                                 //~0206R~
                                                                   //~0206I~
//    if (nChar>=KeyEvent.VK_F1 && nChar<=KeyEvent.VK_F10)         //~va20R~
//    {                                                            //~va20R~
//        if (Shiftkey!=0)                                         //~va20R~
//            return -1;                                           //~va20R~
//        if (nChar==KeyEvent.VK_F10)                              //~va20R~
//            rc=SetMemo(null,0);                                  //~va20R~
//        else                                                     //~va20R~
//            rc=SetMemo(null,nChar-KeyEvent.VK_F1+1);             //~va20R~
//        if (!rc)                                                 //~va20R~
//            return -1;                                           //~va20R~
//        return 1;                                                //~va20R~
//    }                                                            //~va20R~
	if (nChar==KEYCODE_SAMEKEY    //*                              //~v@@@R~
	||  nChar==KEYCODE_SAMEKEY3  //s                              //~v@@@I~
	||  nChar==KEYCODE_SAMEKEY2)  //@                              //~v@@@I~
    {                                                              //~v@@@I~
    	Shiftkey++;                                                //~v@@@I~
        Controlkey=0;                                              //~v@@@I~
        if (Shiftkey>1)   //just same key                          //~v@@@R~
        {                                                          //~v@@@I~
       		Shiftkey=0;                                            //~v@@@I~
			SetSameNum(0);                                         //~v@@@I~
        }                                                          //~v@@@I~
        return 1;                                                  //~v@@@I~
    }                                                              //~v@@@I~
	if (nChar==KEYCODE_MEMOKEY   //"#"                             //~v@@@R~
	||  nChar==KEYCODE_MEMOKEY3  //m                               //~v@@@I~
//  ||  nChar==KEYCODE_MEMOKEY2  //SYM                             //~v@@@R~
    )                                                              //~v@@@M~
    {                                                              //~v@@@M~
    	Controlkey++;                                              //~v@@@M~
    	Shiftkey=0;                                                //~v@@@M~
        if (Controlkey>1)   //just memo key                        //~v@@@R~
       		Controlkey=0;                                          //~v@@@I~
        return 1;                                                  //~v@@@R~
    }                                                              //~v@@@M~
    if  (nChar==KeyEvent.KEYCODE_0                                 //~v@@@I~
    ||   nChar==KeyEvent.KEYCODE_SPACE                             //~v@@@I~
    )                                                              //~v@@@I~
    {                                                              //~v@@@I~
        if (Shiftkey>0)                                              //~v@@@I~
        {                                                          //~v@@@I~
			SetSameNum(0);                                         //~v@@@I~
        	Shiftkey=0;                                            //~v@@@I~
			return 1;	//redraw                                   //~v@@@I~
        }                                                          //~v@@@I~
        if (Controlkey>0)                                            //~v@@@I~
        {                                                          //~v@@@I~
        	Controlkey=0;                                          //~v@@@I~
			return 1;	//redraw                                   //~v@@@R~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
//  if (nChar>=KeyEvent.KEYCODE_0 && nChar<=KeyEvent.KEYCODE_9)                                //~0102I~//~v@@@R~
    if (nChar>KeyEvent.KEYCODE_0 && nChar<=KeyEvent.KEYCODE_9)     //~v@@@I~
    {                                                              //~0102I~
    	num=nChar-KeyEvent.KEYCODE_0;                                             //~va20I~//~v@@@R~
//System.out.println("numkey"+num);                                //~va20R~
//        if (Shiftkey!=0)                                         //~va20R~
//            if (nChar=='0')                                      //~va20R~
//                return SetSameNum(0)-1;                          //~va20R~
//            else                                                 //~va20R~
//                return SetSameNum('0'-nChar)-1;                  //~va20R~
//                    //no chng=-1:no redraw,chng=0:no button but redraw//~va20R~
//                                                                 //~va20R~
//  	if (Shiftkey!=0)                                           //~va20I~//~v@@@R~
//      	if (Controlkey!=0)                                     //~va20I~//~v@@@R~
//        	{                                                      //~va20I~//~v@@@R~
//          	if (Ssamenumstr==null)                             //~va20I~//~v@@@R~
//          		Ssamenumstr=Integer.toString(num);		//S+C+num (samenum)//~va20I~//~v@@@R~
//              else                                               //~va20I~//~v@@@R~
//          		Ssamenumstr+=Integer.toString(num);		//S+C+num (samenum)//~va20R~//~v@@@R~
//System.out.println("S+C="+Ssamenumstr);                          //~va20R~
//              return 1;                                          //~va20I~//~v@@@R~
//          }                                                      //~va20I~//~v@@@R~
//          else                                                   //~va20I~//~v@@@R~
//          {                                                      //~va20I~//~v@@@R~
//              if (Sover10numstr==null)                           //~va20R~//~v@@@R~
//                  Sover10numstr=Integer.toString(num);   //S+num(num 1x,2x)//~va20I~//~v@@@R~
//              else                                               //~va20I~//~v@@@R~
//                  Sover10numstr+=Integer.toString(num);   //S+num(num 1x,2x)//~va20I~//~v@@@R~
////System.out.println("S="+Sover10numstr);                          //~va20R~//~v@@@R~
//              return 1;                                          //~va20I~//~v@@@R~
//          }                                                      //~va20I~//~v@@@R~
//      else                                                       //~va20I~//~v@@@R~
//  		if (Controlkey!=0)			//C+num                    //~va20I~//~v@@@R~
//      	{                                                      //~va20I~//~v@@@R~
//      		if (Smemonumstr==null)                             //~va20R~//~v@@@R~
//          		Smemonumstr=Integer.toString(num);             //~va20I~//~v@@@R~
//              else                                               //~va20I~//~v@@@R~
//          		Smemonumstr+=Integer.toString(num);            //~va20I~//~v@@@R~
//System.out.println("C="+Smemonumstr);                            //~va20R~
//              return 1;                                          //~va20I~//~v@@@R~
//          }                                                      //~va20I~//~v@@@R~
    	if (Shiftkey!=0)          //0+x     ;;samekey highlight    //~v@@@R~
        {                                                          //~v@@@I~
            SetSameNum(-num);                                      //~v@@@M~
            Shiftkey=0;                                            //~v@@@I~
//System.out.println("samenum="+num);                              //~v@@@R~
            return 1;                                              //~v@@@I~
        }                                                          //~v@@@I~
        if (Controlkey!=0)          //C+num                        //~v@@@I~
        {                                                          //~v@@@I~
        	Controlkey=0;                                          //~v@@@I~
            if (!SetMemo(null,num))                                //~v@@@I~
	            return -1;	//no need redraw                       //~v@@@I~
//System.out.println("Memokey="+num);                              //~v@@@R~
            return 1;	//redraw                                   //~v@@@I~
        }                                                          //~v@@@I~
    	rc=DropDown(null,num);                               //~vj01R~//~v@@@R~
        if (rc==1)                                                    //~vj01R~
        	NextFocus();                                           //~0211R~
		return (rc==1?1:0);                                            //~vj01R~
	}                                                              //~0102I~
    if (nChar==KeyEvent.KEYCODE_SPACE                                   //~vj01R~//~0914R~//~v@@@R~
    ||  nChar==KeyEvent.KEYCODE_0                                  //~v@@@I~
    )                                                              //~v@@@I~
    {                                                              //~0102I~
    	rc=DropDown(null,0);                                       //~vj01R~
        if (rc==1)                                                    //~vj01R~
        	NextFocus();                                           //~0211R~
		return (rc==1?1:0);                                            //~vj01R~
	}                                                              //~0102I~
//    int from=KbFocus;                                              //~0211R~
	switch(nChar)                                                  //~0102I~
    {                                                              //~0102I~
//    case KeyEvent.VK_SHIFT:                                        //~vj01R~
//        Shiftkey=1;                                                //~0206I~//~0914R~
//        return -1;      //no need redraw                           //~0206I~//~0914R~
//    case KeyEvent.VK_CONTROL:                                      //~va20I~//~0914R~
//        Controlkey=1;                                              //~va20I~//~0914R~
//        return -1;      //no need redraw                           //~va20I~//~0914R~
    case KeyEvent.KEYCODE_HOME:                                         //~vj01R~//~0914R~
    	KbFocus=0;                                                 //~0211R~
        break;                                                     //~0102I~
//    case KeyEvent.VK_END:                                          //~vj01R~//~0914R~
//        KbFocus=Wnp.PEG_MAX-1;                                     //~va01R~//~0914R~
//        break;                                                     //~0102I~//~0914R~
    case KeyEvent.KEYCODE_DPAD_UP:                                           //~vj01R~//~0914R~
        KbFocus-=Wnp.MAP_SIZE;                                     //~va01R~//~0914R~
        if (KbFocus<0)                                             //~0211R~
        {                                                          //~0102I~
        	KbFocus+=Wnp.PEG_MAX;                                  //~va01R~//~0914R~
            PrevFocus();                                           //~0211R~
        }                                                          //~0102I~
        break;                                                     //~0102I~
    case KeyEvent.KEYCODE_DPAD_DOWN:                                         //~vj01R~//~0914R~
        KbFocus+=Wnp.MAP_SIZE;                                     //~va01R~//~0914R~
        if (KbFocus>=Wnp.PEG_MAX)                                  //~va01R~//~0914R~
        {                                                          //~0102I~
        	KbFocus-=Wnp.PEG_MAX;                                  //~va01R~//~0914R~
            NextFocus();                                           //~0211R~
        }                                                          //~0102I~
        break;                                                     //~0102I~
    case KeyEvent.KEYCODE_DPAD_LEFT:                                         //~vj01R~//~0914R~
        PrevFocus();                                               //~0211R~
        break;                                                     //~0102I~
    case KeyEvent.KEYCODE_DPAD_RIGHT:                                        //~vj01R~//~0914R~
        NextFocus();                                               //~0211R~
        break;                                                     //~0102I~
//    case KeyEvent.KEYCODE_DPAD_CENTER:                           //~v@@@R~
////      NextFocus();                                             //~v@@@R~
//        DropDown(null,0);                                        //~v@@@R~
//        break;                                                   //~v@@@R~
	case KeyEvent.KEYCODE_DEL:                                       //~vj01R~//~0914R~
	    if ((Flags&F_MODEMAKE)!=0)    //pattern setting            //~v004R~
    		DropDown(null,-PATTERN_ID);                            //~vj01R~
        else                                                       //~0129I~
        {                                                          //~0206I~
        	phole=BoardMap[KbFocus/Wnp.MAP_SIZE][KbFocus%Wnp.MAP_SIZE];//~va01R~//~0914R~
        	if (phole.GetNum()!=0)                                 //~vj01R~
    			DropDown(null,0);                                  //~vj01R~
            else                                                   //~0206I~
    			SetMemo(null,PATTERN_ID);	//map_size:clear id    //~vj01R~
        }                                                          //~0206I~
        break;                                                     //~0129I~
//    case KeyEvent.VK_INSERT:                                       //~vj01R~//~0914R~
//        return SetSameNum(1)-1;                                    //~0206R~//~0914R~
//                //no chng=-1:no redraw,chng=0:no button but redraw //~0206I~//~0914R~
//    case KeyEvent.KEYCODE_BACK:       //back space                //~vj01R~//~0914R~//~v@@@R~
//        PrevFocus();                                               //~0211R~//~v@@@R~
//        if ((Flags&F_MODEMAKE)!=0)    //pattern setting            //~v004R~//~v@@@R~
//            DropDown(null,-PATTERN_ID);                            //~vj01R~//~v@@@R~
//        else                                                       //~0129I~//~v@@@R~
//            DropDown(null,0);                                      //~vj01R~//~v@@@R~
//        break;                                                     //~0129I~//~v@@@R~
    case KEYCODE_MAKE:	//new                                      //~v@@@R~
        pView.pButtonDlg.OnMake();    //Make                       //~v@@@I~
        break;                                                     //~v@@@I~
    case KEYCODE_START:	//start                                    //~v@@@R~
        pView.pButtonDlg.OnSetend();    //Try!                     //~v@@@I~
        break;                                                     //~v@@@I~
    case KEYCODE_ANSWER:	//done                                 //~v@@@R~
        pView.pButtonDlg.OnGo();        //Ans?                     //~v@@@I~
        break;                                                     //~v@@@I~
    case KEYCODE_RCHK:	//redundancy chk                           //~v@@@I~
        pView.pButtonDlg.OnRchk();        //Ans?                   //~v@@@I~
        break;                                                     //~v@@@I~
    case KEYCODE_NEXT:	//help                                     //~v@@@R~
        pView.pButtonDlg.OnNext();      //Next                     //~v@@@R~
        break;                                                     //~v@@@I~
    case KEYCODE_CLEAR:	//back key                                 //~v@@@R~
		if ((Flags&F_THREAD)!=0)                                   //~v@@@I~
        	pView.pButtonDlg.OnStop();                             //~v@@@I~
        else                                                       //~v@@@I~
        {                                                          //~v@@@I~
			if (Mode==MODE_NOINPUT||Mode==MODE_INIT)               //~v@@@R~
            	exitDlg();                                         //~v@@@I~
        	pView.pButtonDlg.OnClear();     //Claer                //~v@@@R~
        }                                                          //~v@@@I~
        break;                                                     //~v@@@I~
    case KEYCODE_BREAK:	//reset                                    //~v@@@I~
        pView.pButtonDlg.OnStop();                                 //~v@@@I~
        break;                                                     //~v@@@I~
    default:                                                       //~0102I~
    	return -1;                                                 //~0206R~
	}                                                              //~0102I~
    return 1;                                                      //~0206R~
}//keyProc                                                         //~vj01R~
//===============================================================================//~0206M~
//rc  :TRUE:need to invalidate                                     //~0212R~
//===============================================================================//~0212I~
boolean    KeyUpProc(int nChar)                                    //~vj01R~
{                                                                  //~0211R~
//	int shiftctl;                                              //~va20R~//~v@@@R~
//*****************                                                //~va20I~
//    if (Shiftkey)                                                //~0211R~//~v@@@I~
//      if (nChar>='1' && nChar<='9')                            //~0211R~//~v@@@I~
//    switch(nChar)                                                  //~0211R~
//    {                                                              //~0211R~
//    case VK_INSERT:                                              //~0211R~
//        SetSameNum(0);                                           //~0211R~
//        break;                                                   //~0211R~
//    case KeyEvent.VK_SHIFT:                                        //~vj01R~//~0914R~
////System.out.println("keyup shift shift="+Shiftkey+",Ctl="+Controlkey);//~va20R~//~0914R~
//        shiftctl=Shiftkey;                                         //~va20I~//~0914R~
//        Shiftkey=0;                                                //~va20I~//~0914R~
//        if (Controlkey!=0)                                         //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            Controlkey=2;   //do when ctrl up                      //~va20I~//~0914R~
//            return FALSE;                                          //~va20R~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        if (shiftctl==2)    //S+C                                  //~va20R~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            num=getkbdnum(Ssamenumstr);                             //~va20I~//~0914R~
//            Ssamenumstr=null;                                      //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        else               //S+                                    //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            num=getkbdnum(Sover10numstr);                           //~va20I~//~0914R~
//            Sover10numstr=null;                                    //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        if (num<0)                                                 //~va20I~//~0914R~
//            return FALSE;                                          //~va20I~//~0914R~
////        if (SameNum)                                             //~0211R~//~0914R~
////            SetSameNum(0);                                       //~0211R~//~0914R~
//        if (shiftctl==2)    //S+C                                  //~va20R~//~0914R~
//        {                                                          //~va20I~//~0914R~
////System.out.println("keyup shift samenum="+num);                  //~va20R~//~0914R~
//            SetSameNum(-num);                                      //~va20R~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        else                                                       //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
////System.out.println("keyup shift dropdown="+num);                 //~va20R~//~0914R~
//            if (DropDown(null,num))                                 //~va20R~//~0914R~
//                NextFocus();                                       //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        break;                                                     //~va20R~//~0914R~
//    case KeyEvent.VK_CONTROL:                                      //~va20I~//~0914R~
////System.out.println("keyup ctl shift="+Shiftkey+",Ctl="+Controlkey);//~va20R~//~0914R~
//        shiftctl=Controlkey;                                       //~va20I~//~0914R~
//        Controlkey=0;                                              //~va20I~//~0914R~
//        if (Shiftkey!=0)                                        //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            Shiftkey=2;     //do when shift up                     //~va20I~//~0914R~
//            return FALSE;                                          //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        if (shiftctl==2)    //S+C                                  //~va20R~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            num=getkbdnum(Ssamenumstr);                             //~va20I~//~0914R~
//            Ssamenumstr=null;                                      //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        else               //S+                                    //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
//            num=getkbdnum(Smemonumstr);                             //~va20I~//~0914R~
//            Smemonumstr=null;                                      //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        if (num<0)                                                 //~va20I~//~0914R~
//            return FALSE;                                          //~va20I~//~0914R~
//        if (shiftctl==2)    //S+C                                  //~va20R~//~0914R~
//        {                                                          //~va20I~//~0914R~
////System.out.println("keyup ctl samenum="+num);                    //~va20R~//~0914R~
//            SetSameNum(-num);                                      //~va20R~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        else                //C+                                   //~va20I~//~0914R~
//        {                                                          //~va20I~//~0914R~
////System.out.println("keyup ctl memo="+num);                       //~va20R~//~0914R~
//            SetMemo(null,num);                                     //~va20I~//~0914R~
//        }                                                          //~va20I~//~0914R~
//        break;                                                     //~va20I~//~0914R~
//    default:                                                       //~0211R~
//        return FALSE;                                              //~0211R~
//    }                                                              //~0211R~
//  return TRUE;                                                   //~va20R~//~v@@@I~
    return FALSE;                                                  //~v@@@I~
}//KeyUpProc                                                                  //~va20I~//~v@@@R~
////===============================================================================//~va20I~//~v@@@R~
//private int getkbdnum(String Pnumstr)                              //~va20I~//~v@@@R~
//{                                                                  //~va20I~//~v@@@R~
//    int num=0,ii,len;                                              //~va20R~//~v@@@R~
////*********************                                            //~va20I~//~v@@@R~
//    if (Pnumstr==null)                                             //~va20I~//~v@@@R~
//        return -1;                                                 //~va20I~//~v@@@R~
//    len=Pnumstr.length();                                          //~va20I~//~v@@@R~
//    if (len==0)                                                    //~va20I~//~v@@@R~
//        return -1;                                                 //~va20I~//~v@@@R~
//    for (ii=0;ii<len;ii++)                                         //~va20R~//~v@@@R~
//    {                                                              //~va20I~//~v@@@R~
//        num*=10;                                                   //~va20I~//~v@@@R~
//        num+=Pnumstr.charAt(ii)-'0';                               //~va20I~//~v@@@R~
//    }                                                              //~va20I~//~v@@@R~
//    if (num<0||num>Wnp.MAP_SIZE)                                       //~va20I~//~0914R~//~v@@@R~
//        return -1;                                                 //~va20I~//~v@@@R~
////System.out.println("getkbdnum "+Pnumstr+"-->"+num);              //~va20R~//~v@@@R~
//    return num;                                                    //~va20I~//~v@@@R~
//}//int getkbdnum                                                   //~va20R~//~v@@@R~
//===============================================================================//~0205I~
void	NextFocus()                                                //~vj01R~
{                                                                  //~0102I~
	if (++KbFocus>=Wnp.PEG_MAX)                                    //~va01R~//~0914R~
    	KbFocus=0;                                                 //~0211R~
}                                                                  //~0102I~
//===============================================================================//~0205I~
void	PrevFocus()                                                //~vj01R~
{                                                                  //~0102I~
	if (--KbFocus<0)                                               //~0211R~
    	KbFocus=Wnp.PEG_MAX-1;                                     //~va01R~//~0914R~
}                                                                  //~0102I~
//===============================================================================//~0205I~
void SetNum(int Pnum,WnpView Pview)                                   //~vj01R~//~0914R~
{                                                                  //~9C28I~
    CurNum=Pnum;                                                   //~9C28I~
    if (Pview!=null)                                               //~vj01R~
    	pView=Pview;                                               //~0109I~
}                                                                  //~9C28I~
//===============================================================================//~0205I~
int GetNum()                                                       //~vj01R~
{                                                                  //~9C30I~
    return CurNum;                                                 //~9C30I~
}                                                                  //~9C30I~
//===============================================================================//~0205I~
int GetKbFocus()                                                   //~v004R~
{                                                                  //~0102M~
	if (KbFocus>=Wnp.PEG_MAX||KbFocus<0)                           //~va01R~//~0914R~
    	return -1;                                                  //~0102M~
	return KbFocus;                                                //~0211R~
}                                                                  //~0102M~
//===============================================================================//~0206I~
//parm:0:up,1:down,0>:Shift+PFK num down                           //~0211R~
//rc=0:no change,1:status change                                   //~0206I~
//===============================================================================//~0206I~
int SetSameNum(int Pdownsw)                                        //~v004R~
{                                                                  //~0206I~
    int oldnum;                                                    //~0206R~
	oldnum=SameNum;                                                //~0206I~
    if (Pdownsw==0)		//clear req                                //~vj01R~
    	SameNum=0;                                                 //~0206I~
    else                                                           //~0206I~
    {                                                              //~0206I~
        if (Pdownsw<0)                                             //~0213R~
            SameNum=-Pdownsw;                                      //~0206R~
        else                                                       //~0206R~
            SameNum=CurNum;                                        //~0206R~
    	if (oldnum==SameNum)	//same key pushed                  //~0211I~
        	SameNum=0;                                             //~0211I~
        else                                                       //~0213I~
    	    CountPenalty(PENALTY_SAMENUM);                         //~0213I~
	}                                                              //~0206I~
    return (oldnum!=SameNum)?1:0;                                 //~vj01R~
}//setsamenum                                                      //~0213R~
//===============================================================================//~0123R~
void    BoardToScreen( int bx, int by,Point Ppoint)                //~vj01R~
{
    Ppoint.x = (int) (Wnp.BOARD_LEFT + bx * ( Wnp.OBJECT_SIZE + Wnp.GAP ));//~vj01R~//~0914R~
    Ppoint.y = (int) (Wnp.BOARD_TOP  + by * ( Wnp.OBJECT_SIZE + Wnp.GAP ));//~vj01R~//~0914R~
}
//===============================================================================//~0123R~
void    ShowObject(Canvas pDC, int bx, int by, int sts,int PFocus)//~vj01R~//~0914R~//~0915R~
{
//  Paint pDCpaint=new Paint();                                    //~v@@@R~
	int x,y,xe,ye;                                                 //~0915R~
	int lw1=Wnp.FOCUS_LINE_WIDTH,lw2=Wnp.FOCUS_LINE_WIDTH*2,lw3=Wnp.FOCUS_LINE_WIDTH*3;//~0915I~
    BoardToScreen( bx, by, wkpoint);                               //~va23R~
    x=wkpoint.x; y=wkpoint.y;                                      //~v004I~
    xe=x+Wnp.OBJECT_SIZE-1;                                        //~0915R~
    ye=y+Wnp.OBJECT_SIZE-1;                                        //~0915R~
    int   old_br=pDCpaintB.getColor();                                   //~vj01R~//~0914R~//~0915R~//~v@@@R~
    int    br=OBJ_COLOR[sts];                                      //~va23R~//~0914R~
    
//System.out.println("ShowObject:Pfocus="+PFocus+",sts="+sts+",r="+br.getRed()+",g="+br.getGreen()+",b="+br.getBlue());//~va03R~
    if (PFocus!=0)                                                 //~vj01R~
    {                                                              //~0102I~
	    int   brFocus=COL_BG_FORCUS;                               //~va23R~//~0914R~
	    int   snFocus=COL_BG_SAMENUM;                              //~va23R~//~0914R~
//System.out.println("ShowObject:forcus="+PFocus+",r="+brFocus.getRed()+",g="+brFocus.getGreen()+",b="+brFocus.getBlue());//~vj01R~
//System.out.println("ShowObject:snforcus="+PFocus+",r="+snFocus.getRed()+",g="+snFocus.getGreen()+",b="+snFocus.getBlue());//~vj01R~
        switch(PFocus)                                             //~0211I~
        {                                                          //~0211I~
        case 3:		//forcus and samenum                           //~0211I~
        	pDCpaintB.setColor( snFocus);                          //~v@@@R~
        	pDC.drawRect(x,y,xe,ye,pDCpaintB);  //~vj01R~//~0914R~ //~0915R~//~v@@@R~
	    	pDCpaintB.setColor( brFocus);                                //~vj01R~//~v@@@R~
    		pDC.drawRect(x+lw1,y+lw1,xe-lw1,ye-lw1,pDCpaintB);//~vj01R~//~0914R~//~0915R~//~v@@@R~
	    	pDCpaintB.setColor( br);                                     //~vj01R~//~v@@@R~
    		pDC.drawRect( x+lw3,y+lw3,xe-lw3,ye-lw3,pDCpaintB);//~vj01R~//~0914R~//~0915R~//~v@@@R~
        	break;                                                 //~0211I~
        case 2:		//samenum                                      //~0211I~
        	pDCpaintB.setColor( snFocus);                          //~v@@@R~
        	pDC.drawRect(x,y,xe,ye,pDCpaintB);  //~vj01R~//~0914R~  //~0915R~//~v@@@R~
	    	pDCpaintB.setColor( br);                                     //~vj01R~//~v@@@R~
    		pDC.drawRect(x+lw2,y+lw2,xe-lw2,ye-lw2,pDCpaintB);//~vj01R~//~0914R~//~0915R~//~v@@@R~
        	break;                                                 //~0211I~
        default:    //focus                                        //~0211I~
        	pDCpaintB.setColor( brFocus);                          //~v@@@R~
        	pDC.drawRect(x,y,xe,ye,pDCpaintB);  //~vj01R~//~0914R~  //~0915R~//~v@@@R~
	    	pDCpaintB.setColor( br);                                     //~vj01R~//~v@@@R~
    		pDC.drawRect(x+lw1,y+lw1,xe-lw1,ye-lw1,pDCpaintB);//~vj01R~//~0914R~//~0915R~//~v@@@R~
        }                                                          //~0211I~
    }                                                              //~0102I~
    else                                                           //~0102I~
    {                                                              //~0102I~
	    pDCpaintB.setColor(br);                                          //~va03I~//~v@@@R~
    	pDC.drawRect(x,y,xe,ye,pDCpaintB);      //~vj01R~//~0914R~  //~0915R~//~v@@@R~
	}                                                              //~0102I~
	pDCpaintB.setColor( old_br );                                        //~vj01R~//~0915R~//~v@@@R~
//System.out.println("ShowObject:old,r="+old_br.getRed()+",g="+old_br.getGreen()+",b="+old_br.getBlue());//~va03R~
}
//===============================================================================*///~0123R~
boolean    ScreenToBoard( int wx, int wy, Point Ppoint)            //~vj01R~
{                                                                  //~9C09I~
    int x = (int) (wx - Wnp.BOARD_LEFT);                                   //~vj01R~//~0914R~
    int y = (int) (wy - Wnp.BOARD_TOP);                                    //~vj01R~//~0914R~
    if ( x < 0 || Wnp.BOARD_WIDTH <= x || y < 0 || Wnp.BOARD_WIDTH <= y )//~vj01R~//~0914R~
        return FALSE;                                              //~9C09I~
    int bx = (int) (x / ( Wnp.OBJECT_SIZE + Wnp.GAP ));                    //~va23R~//~0914R~
    int by = (int) (y / ( Wnp.OBJECT_SIZE + Wnp.GAP ));                    //~vj01R~//~0914R~
    int obj_x = (int) (bx * ( Wnp.OBJECT_SIZE + Wnp.GAP ));                //~va23R~//~0914R~
    int obj_y = (int) (by * ( Wnp.OBJECT_SIZE + Wnp.GAP ));                //~vj01R~//~0914R~
    if ( obj_x + Wnp.OBJECT_SIZE - 1 < x || obj_y + Wnp.OBJECT_SIZE - 1 < y )//~vj01R~//~0914R~
        return FALSE;                                              //~9C09I~
    Ppoint.x= bx;                                                  //~vj01R~
    Ppoint.y= by;                                                  //~vj01R~
    return TRUE;                                                   //~9C09I~
}                                                                  //~9C09I~
//=============================================================================//~0123R~
int	GetPath()                                                      //~v004R~
{                                                                  //~9C12I~
    int cnt=0;                                                     //~0129I~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~0129I~
            Hole phole=BoardMap[ bx ][ by ];                       //~vj01R~
            if (phole.GetNum()!=0)                                 //~vj01R~
                cnt++;                                             //~0129I~
        }                                                          //~0129I~
    return cnt;                                                    //~0129R~
}                                                                  //~9C12I~
//=============================================================================*///~9C12I~
int	GetMode()                                                      //~v004R~
{                                                                  //~9C12I~
	return Mode;                                                   //~9C12I~
}                                                                  //~9C12I~
//=============================================================================*///~9C31M~
void  OnClear()                                                    //~v004R~
{                                                                  //~9C31M~
    Score=ScoreMax=0;                                              //~0226I~
//    System.out.println("OnClear scoremax="+ScoreMax);            //~v@@@R~
	if (ErrClear()!=0)                                             //~vj01R~//~v@@@I~
    {                                                              //~v003I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        SetTempMsg("エラーをクリアーしました");                    //~v003R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        SetTempMsg("Errors are cleared");                          //~va23I~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.ErrCleared).toString());//~v@@@I~
    	return;                                                    //~v003I~
    }                                                              //~v003I~
	if (MemoClear()!=0)                                            //~vj01R~
    {                                                              //~0213I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        SetTempMsg("メモをクリアーしました");                      //~0213I~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        SetTempMsg("Memos area cleared");                          //~va23I~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.MemoCleared).toString());//~v@@@I~
    	return;                                                    //~0213I~
    }                                                              //~0213I~
    while(TRUE)                                                    //~0103I~
	{                                                              //~0103I~
//System.out.println("onclear before clearsub mode="+Mode+",Flags="+Flags);//~v030R~
		if (OnClearSub(1)!=0)		//clear current level          //~vj01R~
            break;                                                 //~0205R~
//System.out.println("onclear after clearsub mode="+Mode);         //~v030R~
        if ((Flags & F_ONTRY)!=0)  //I'll try but no input         //~v004R~
        {                                                          //~0205M~
        	Flags&=~F_ONTRY;                                       //~0205M~
            break;                                                 //~0213R~
        }                                                          //~0205M~
//System.out.println("onclear before clearback mode="+Mode);       //~v030R~
        if (OnClearBack()!=0)      //reached to first              //~vj01R~
        {                                                          //~v030I~
        	if ((Flags & F_MODEMAKE)!=0)                           //~v030I~
            {                                                      //~v030I~
        		Flags &=~F_MODEMAKE;                               //~v030I~
        		Flags |=F_MODEANS;                                 //~v030I~
            }                                                      //~v030I~
            return;                                                //~0205I~
        }                                                          //~v030I~
//System.out.println("onclear after clearback mode="+Mode);        //~v030R~
	}                                                              //~0103I~
//System.out.println("onclear defore clearback2 mode="+Mode);      //~v030R~
    OnClearBack();                                                 //~0211R~
//System.out.println("onclear after clearback2 mode="+Mode);       //~v030R~
    while(TRUE)                                                    //~0205I~
	{                                                              //~0205I~
//System.out.println("onclear before clearsub2 mode="+Mode);       //~v030R~
		if (OnClearSub(0)!=0)		//chk curr level data exist    //~vj01R~
            break;                                                 //~0205I~
//System.out.println("onclear after clearsub2 mode="+Mode);        //~v030R~
        if ((Flags & F_ONTRY)!=0)  //I'll try but no input         //~v004R~
        	return;             //step back by next clear          //~0212I~
//System.out.println("onclear before clearsub3 mode="+Mode+",flags="+Flags);//~v030R~
        if (OnClearBack()!=0)      //reached to first              //~vj01R~
            return;                                                //~0205I~
//System.out.println("onclear after clearsub3 mode="+Mode);        //~v030R~
	}                                                              //~0205I~
}                                                                  //~9C31M~
//=============================================================================*///~0103I~
//clear data of a step                                             //~0103I~
//  parm:clear option,0 if chk count                               //~0123R~
//  return count                                                   //~0123R~
//=============================================================================*///~0103I~
int OnClearSub(int Popt)                                           //~v004R~
{                                                                  //~0103I~
    Hole    p_hole;                                                //~vj01R~
    int      cnt=0,state;                                          //~0108R~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~0103I~
            p_hole = BoardMap[ by ][ bx ];                         //~0103I~
            p_hole.SetMemo(PATTERN_ID);    //clear all memo       //~0211I~
            if (p_hole.GetNum()!=0)                                //~vj01R~
            {                                                      //~0108I~
                state=p_hole.GetState();                          //~0108R~
                if (state==Mode)                                   //~0116R~
                {                                                  //~0103I~
                	cnt++;                                         //~0103I~
                    if (Popt!=0)                                   //~vj01R~
                    {                                              //~0103I~
                    	NumCount--;                                //~0103I~
                    	p_hole.SetNum(0,0,0);                     //~0103I~
						SetNewNum(by,bx,p_hole,0,1);               //~0312R~
                    }                                              //~0103I~
                }                                                  //~0103I~
			}                                                      //~0108I~
        }                                                          //~0103I~
	return cnt;
}                                                                  //~0103I~
//=============================================================================*///~v003I~
//clear err only                                                   //~v003I~
//  return cleared err count                                       //~v003I~
//=============================================================================*///~v003I~
int ErrClear()                                                     //~v004R~
{                                                                  //~v003I~
    Hole    p_hole;                                                //~vj01R~
    int      cnt=0;                                                //~v003I~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~v003I~
            p_hole = BoardMap[ by ][ bx ];                         //~v003I~
            if (p_hole.GetErr()==Hole.HOLE_ERR)                    //~vj01R~
            {                                                      //~v003I~
            	cnt++;                                             //~v003I~
                NumCount--;                                        //~v003I~
	            p_hole.SetNum(0,0,0);                             //~v003I~
            }                                                      //~v003I~
        }                                                          //~v003I~
    Flags&=~F_ERROR;                                               //~v003R~
	return cnt;                                                    //~v003I~
}                                                                  //~v003I~
//=============================================================================*///~0213I~
//clear all memo data                                              //~0213I~
//  return cleared memo count                                      //~0213I~
//=============================================================================*///~0213I~
int MemoClear()                                                    //~v004R~
{                                                                  //~0213I~
    Hole    p_hole;                                                //~vj01R~
    int      cnt=0;                                                //~0213I~
    for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                   //~va01R~//~0914R~
        for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )               //~va01R~//~0914R~
        {                                                          //~0213I~
            p_hole = BoardMap[ by ][ bx ];                         //~0213I~
            cnt+=p_hole.SetMemo(PATTERN_ID);    //clear all memo  //~0213I~
        }                                                          //~0213I~
	return cnt;                                                    //~0213I~
}                                                                  //~0213I~
//=============================================================================*///~0103I~
//mode back; return 1 when reached to first step                   //~0103I~
//=============================================================================*///~0103I~
int OnClearBack()                                                  //~v004R~
{                                                                  //~0103I~
    switch(Mode)                                                   //~0103I~
    {
	case MODE_INIT:                                                //~0116R~
	case MODE_NOINPUT:                                             //~0205I~
    case MODE_FILEDATA:                                            //~0116R~
        sProbLevel="";                                             //~0213I~
	    intProbLevel=0;                                            //~v@@@I~
        if (NumCount==0 && Mode==MODE_FILEDATA)                    //~v@@@I~
	        Mode=MODE_NOINPUT;                                     //~v@@@I~
        return 1;                                                  //~0103R~
    case MODE_KEYINDATA:                                           //~0116R~
        if (NumCount!=0)                                           //~vj01R~
	        Mode=MODE_FILEDATA;                                    //~0205R~
        else                                                       //~0205I~
	        Mode=MODE_NOINPUT;                                     //~0205I~
        Score=0;                                                   //~0213I~
        TryTimeSpan=0;                                             //~0213I~
        break;                                                     //~0103I~
    case MODE_ENDQDATA:                                            //~0116R~
        Mode=MODE_KEYINDATA;                                       //~0116I~
        break;                                                     //~0116I~
    case MODE_KEYINANS:                                            //~0116I~
        Mode=MODE_ENDQDATA;                                        //~0123R~
        break;                                                     //~0103I~
    case MODE_OUTANS:                                              //~0116R~
        Mode=MODE_KEYINANS;                                        //~0116I~
        FixedSeq=MaxFixed=0;   //strat solution                    //~0116I~
        break;                                                     //~0116I~
    }                                                              //~0103I~
    return 0;                                                      //~0103I~
}                                                                  //~0103I~
//**************************************************************** //~0116I~
void OnAnswer(int Pswmake)                                                    //~v004R~//~v@@@R~
{                                                                  //~9C31M~
    Score=0;                                              //~0226I~//~v@@@R~
  if (Pswmake==0)                                                  //~v@@@I~
    ScoreMax=0;                                                    //~v@@@I~
//    System.out.println("OnAnswer scoremax="+ScoreMax);           //~v@@@R~
	while(Mode>MODE_KEYINDATA)                                     //~0123R~
    {                                                              //~0123I~
    	OnClearSub(1);		//clear the level                      //~0123R~
        OnClearBack();      //level back                           //~0123I~
    }                                                              //~0123I~
    if (Mode==MODE_INIT)                                           //~0116I~
    	Mode=MODE_NOINPUT;                                        //~0116I~
    Flags|=F_MODEANS;                                              //~0116I~
    Flags&=~F_MODEMAKE;                                            //~0116I~
}                                                                  //~9C31M~
//**************************************************************** //~0116I~
void OnMake()                                                      //~v004R~
{                                                                  //~9C31M~
    Score=ScoreMax=0;                                              //~0226I~
//    System.out.println("OnMake scoremax="+ScoreMax);             //~v@@@R~
	while(Mode>MODE_KEYINDATA)                                     //~0123R~
    {                                                              //~0123I~
    	OnClearSub(1);		//clear the level                      //~0123I~
        OnClearBack();      //level back                           //~0123I~
    }                                                              //~0123I~
    if (Mode==MODE_INIT)                                           //~0116I~
//  	Mode==MODE_NOINPUT;                                        //~v004R~
    	Mode=MODE_NOINPUT;                                         //~v004I~
    Flags&=~F_MODEANS;                                             //~0116I~
    Flags|=F_MODEMAKE;                                             //~0116I~
                                                                   //~0116M~
}                                                                  //~9C31M~
//**************************************************************** //~0116I~
void OnSetend(int Pendthreadsw)                                    //~v004R~
{                                                                  //~0116I~
    if (Pendthreadsw!=0)                                           //~v@@@I~
    {                                                              //~0213R~
        if ((Flags & F_ERROR)!=0)    //no solution                 //~v004R~
            return;                                                //~0213R~
        SaveAnswerXnp();                                           //~0312I~
        TryStartTime=System.currentTimeMillis();                   //~vj01I~
    	if ((Flags&F_READFILE)!=0)                                 //~v@@@I~
        {                                                          //~v@@@I~
        	TryStartTime-=pPat.restartTrytimeSpan*1000;            //~v@@@I~
        	pPat.restartTrytimeSpan=0;                             //~v@@@I~
        }                                                          //~v@@@I~
        timerid=IDC_SETEND;                                        //~vj01M~
//      timer_SETEND=new Timer(60000,pView);                       //~vj01I~//~0915R~
        timer_SETEND=new NppTimer(timerid,60000,this);             //~0915I~
//        timer_SETEND.setCoalesce(true);                            //~vj01I~//~0915R~
        timer_SETEND.setRepeats(true);                             //~vj01I~
        timer_SETEND.start();                                      //~vj01I~
        sSuccessMsg="";                                            //~0213R~
        SetTempMsg("");                                            //~0213R~
        Npwtryans.copy(Npworkt);		//save for score           //~v025R~
        CalcScore();        //get max score                        //~0226I~
        pPat.ScoreMax=ScoreMax;                                    //~v@@@I~
        pPat.intProbLevel=intProbLevel;                            //~v@@@I~
//        System.out.println("OnSetEnd endthread=1 scoremax="+ScoreMax+",probno="+(pPat.strQuestionNo==null?"":pPat.strQuestionNo));//~v@@@R~
        pPIndex.updateIndexLevel(pPat);                            //~v@@@R~
        Score=0;                                                   //~0226I~
    	if ((Flags&F_READFILE)!=0)                                 //~v012I~
        {                                                          //~v012I~
	    	Flags&=~F_READFILE;                                    //~v012I~
			Restoreprevinput( );                                   //~v012I~
        }                                                          //~v012I~
//      if (Pendthreadsw==2)  //restart                                //~vj01R~//~v@@@R~
//      {                                                          //~v@@@R~
//          Mode=MODE_KEYINANS;                                    //~v@@@R~
//        	pPat.ClearAnsData();                                   //~v@@@R~
//      }                                                          //~v@@@R~
    }                                                              //~0213R~
    else                                                           //~0213R~
    {                                                              //~0213R~
    	if (timer_SETEND!=null)                                    //~vj01R~
        {                                                          //~vj01I~
	    	timer_SETEND.stop();	//confirmation                 //~vj01I~
	    	timer_SETEND=null;                                     //~vj01I~
        }                                                          //~vj01I~
        if (CurNum==0)                                             //~vj01R~
            CurNum=1;                                              //~0130R~
        Mode=MODE_ENDQDATA;                                        //~0130R~
		pPat.ClearAnsData();                                      //~0213I~
        Penalty=0;			//reset penalty                        //~0213I~
        CallAns(0,null);                                           //~vj01R~
    }                                                              //~0213R~
}//OnSetend                                                        //~0116I~//~v@@@R~
//**************************************************************** //~0109I~
//*ret :0:ok(set all):1 ok(more step),-1:err                       //~0116R~
//**************************************************************** //~0109I~
int GetSol(int Pendthreadsw)                                       //~v004R~
{                                                                  //~9C31I~
	int rc=0,opt;                                                //~0129R~
	if (Mode!=MODE_OUTANS)                                         //~0116R~
    {                      
    	if (Pendthreadsw==0)                                       //~vj01R~
        {                                                          //~0122I~
        	if ((Flags & F_ONRCHK)!=0)                             //~v004R~
//      		opt=xnpsub.XNP_REDUNDANTCK;                        //~v022R~
        		opt=xnpsub.XNP_REDUNDANTCK|xnpsub.XNP_RLEVELMASK;  //~v022I~
            else                                                   //~0130I~
            	opt=0;                                             //~0130I~
        	if (CallAns(opt,null)==0)                              //~vj01R~
                return 0;                                          //~0123I~
        	return -1;                                             //~0123I~
        }                                                          //~0122I~
    }                                                              //~0108I~
//mode answer                                                      //~0123I~
    if (MaxFixed==Wnp.PEG_MAX)                                     //~va01R~//~0914R~
        if (FixedSeq<MaxFixed)                                     //~0129I~
        {                                                          //~0129R~
            FixedSeq++;                                            //~0129R~
            SetNextAnswer(FixedSeq);                               //~0129R~
            if (FixedSeq<MaxFixed)                                 //~0129R~
                rc=1;                                              //~0129R~
            else                                                   //~0129I~
		        SaveAnswer();                                      //~0129I~
        }                                                          //~0129R~
    return rc;                                                     //~0109R~
}                                                                  //~9C31I~
//**************************************************************** //~0205I~
void OnNext(int Pendthreadsw)                                      //~v004R~
{                                                                  //~0205I~
    NPWORKT pnpwt;                                                 //~vj01R~
	M99 pm99;                                                      //~vj01R~
	int idatano,bx,by=0;                                   //~vj01R~
//mode answer                                                      //~0205I~
    if (Pendthreadsw==0)                                           //~vj01R~
    {                                                              //~0205I~
        if (Mode<MODE_ENDQDATA)                                         //~0205I~
            Mode=MODE_ENDQDATA;                                         //~0205I~
//set curr data as init data                                       //~0205I~
    	for (bx=0; bx < Wnp.MAP_SIZE; bx ++ )                      //~va01R~//~0914R~
        	for (by=0; by < Wnp.MAP_SIZE; by ++ )                  //~va01R~//~0914R~
            	Stempdata[bx][by]=BoardMap[bx][by].GetNum();         //~0205I~
                                                                   //~0205I~
        CallAns(0,Stempdata);                                       //~0205I~
        return;                                                    //~0205I~
    }                                                              //~0205I~
//*endthread                                                       //~0205I~
    pnpwt=Npworkt;                                                 //~v004R~
    idatano=pnpwt.idatano;                                        //~0205I~
    if (MaxFixed!=Wnp.PEG_MAX)	//err                              //~va01R~//~0914R~
    {                                                              //~0205I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        SetTempMsg("解答に誤りが有ります");                        //~v003R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        SetTempMsg("Incorrect answer.");                           //~va23I~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.IncorrectAns).toString());//~v@@@I~
    	return;                                                    //~0205I~
    }                                                              //~0205I~
    pm99=pnpwt.m99[0][0];     //answer                             //~vj01I~
    for (bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                        //~va01R~//~0914R~
    {                                                              //~0205I~
        for (by = 0; by < Wnp.MAP_SIZE; by ++ )                    //~va01R~//~0914R~
        {                                                          //~0205I~
            pm99=pnpwt.m99[bx][by];     //answer                   //~v004R~
            if (pm99.fnum!=0)                                      //~vj01R~
                if (pm99.fseq==idatano+1)                         //~0205I~
                    break;                                         //~0205I~
        }                                                          //~0205I~
        if (by<Wnp.MAP_SIZE)                                       //~va01R~//~0914R~
            break;                                                 //~0205I~
    }                                                              //~0205I~
    if (bx>=Wnp.MAP_SIZE)                                          //~va01R~//~0914R~
    	return;                                                    //~0205I~
    KbFocus=bx*Wnp.MAP_SIZE+by;                                    //~va01R~//~0914R~
    FixedSeq=pm99.fseq;                                           //~0205I~
    DropDown(null,pm99.fnum);                                      //~vj01R~
    bx=KbFocus%Wnp.MAP_SIZE;                                       //~va01R~//~0914R~
    by=KbFocus/Wnp.MAP_SIZE;                                       //~va01R~//~0914R~
    BoardMap[ by ][ bx ].SetNum(-1,-1,Hole.HOLE_NEXT);             //~vj01R~
    SetTempMsg("Ok");                                              //~0205I~
    return;                                                        //~0205I~
}                                                                  //~0205I~
//**************************************************************** //~0122I~
void OnGo(int Pendthreadsw)                                        //~v004R~
{                                                                  //~9C31I~
	if ((Flags&F_MODEANS)!=0)                                      //~v004R~
    {                                                              //~0109I~
    	if (NumCount==Wnp.PEG_MAX)                                 //~va01R~//~0914R~
        {                                                          //~0130I~
        	if (CheckAnswer(1)!=0)                                 //~vj01R~
		    	CountPenalty(PENALTY_ERR);                         //~0312I~
            Flags&=~F_ONGO;                                        //~0130I~
		}                                                          //~0130I~
        else                                                       //~0129I~
            if (GetSol(Pendthreadsw)==1)       //more step         //~0205R~
            {                                                      //~vj01I~
                timerid=IDC_GO;                                    //~vj01M~
//              timer_GO=new Timer(50,pView);                      //~vj01I~//~0915R~
                timer_GO=new NppTimer(timerid,50,this);                    //~0915I~
//                timer_GO.setCoalesce(true);                        //~vj01I~
                timer_GO.setRepeats(true);                         //~vj01I~
                timer_GO.start();                                  //~vj01I~
            }                                                      //~vj01I~
            else          //first start thread or err at endthread //~0130R~
            	if ((Flags & F_ERROR)!=0)//from                    //~v004R~
                	Flags&=~F_ONGO;                                //~0130R~
    }                                                              //~0109I~
    else                                                           //~0109I~
	if ((Flags&F_MODEMAKE)!=0)                                     //~v004R~
    {                                                              //~0123I~
        if (CallMake()!=0)                                         //~vj01R~
        	Flags&=~F_ONGO;                                        //~0123I~
	}                                                              //~0123I~
    else                                                           //~0109I~
//      SetTempMsg("問題設定してください");                        //~v003R~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.SetQuestion).toString());//~v@@@I~
    	                                                           //~0109I~
}                                                                  //~9C31I~
//**************************************************************** //~0122I~
void OnStop()                                                      //~v004R~
{                                                                  //~0110I~
	if ((Flags&F_THREAD)==0)                                       //~v004R~
    	return;                                                    //~0110I~
	xnpsub.GblSubthreadStopReq=1;                                  //~vj01R~
}                                                                  //~0110I~
//**************************************************************** //~0122I~
//*ret 1:req sw to ans mode(make normal end)                       //~v002I~
//**************************************************************** //~v002I~
int  OnEndThread()                                                 //~v004R~
{                                                                  //~0109I~
    M99 pm99;                                                      //~vj01R~
    NPWORKT pnpwt=Npworkt;                                         //~vj01R~
//System.out.println("OnEndThraed");                               //~v025R~
	Flags&=~F_THREAD;                                              //~0109I~
    if (NpsubRc!=0 && xnpsub.GblSubthreadStopReq==0)               //~vj01R~
        Flags|=F_ERROR;                                            //~0130I~
	                                                               //~0110I~
	if ((Flags & F_MODEMAKE)!=0)                                   //~v004R~
    {                                                              //~0122R~
    	pPat.Seed=ActSeed;                                        //~0129I~                                   //~v@@@I~
		Flags&=~F_ONGO;                                            //~0123I~
        if (NpsubRc!=0)                                            //~vj01R~
            return 0;                                              //~v002R~
        NumCount=0;                                                //~0205R~
        for ( int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )               //~va01R~//~0914R~
            for ( int by = 0; by < Wnp.MAP_SIZE; by ++ )           //~va01R~//~0914R~
            {                                                      //~0122R~
                Hole phole=BoardMap[ bx ][ by ];                   //~vj01R~
                pm99=pnpwt.m99[bx][by];     //answer               //~v004R~
                if (pm99.fnum!=0 && pm99.dlvl==xnpsub.LEVEL_INIT)  //~vj01R~
                {                                                  //~0205I~
                    phole.SetNum(pm99.fnum,MODE_KEYINDATA,0);    //~0129R~
                    NumCount++;                                    //~0205I~
				}                                                  //~0205I~
                else                                               //~0129I~
                    phole.SetNum(0,0,0);                          //~0129I~
				SetNewNum(bx,by,phole,0,1);                        //~0312R~
            }                                                      //~0122R~
        ScoreMax=CalcScoreMax(Npworkt);    //set ScoreMax          //~v@@@I~
        pPat.ScoreMax=ScoreMax;                                    //~v@@@I~
//        System.out.println("onendthread scoremax="+ScoreMax+",intProbLevel="+intProbLevel+",sProbLevel="+sProbLevel);//~v@@@R~
        FixedSeq=pnpwt.idatano;                                   //~0122R~
        Mode=MODE_ENDQDATA;                                        //~0122R~
        pPat.strUserID=ButtonDlg.strTextUID;                                //~v@@@I~
        pPIndex.insertIndex(pPat);                                 //~v@@@M~
        return 1;                                                  //~v002R~
    }                                                              //~0122I~
//answer end                                                       //~0122I~
    if ((Flags & F_ONNEXT)!=0)                                     //~v004R~
    {                                                              //~0213I~
	    MaxFixed=pnpwt.seqno;                                     //~0213I~
		OnNext(1);				//advance a step                   //~0205M~
    }                                                              //~0213I~
    else                                                           //~0205M~
    if ((Flags & F_ONTRY)!=0)                                      //~v004R~
    {                                                              //~v@@@I~
//        System.out.println("OnEndThread ontry probno="+(pPat.strQuestionNo==null?"":pPat.strQuestionNo));//~v@@@R~
    	if (pPat.strQuestionNo==null)	//user data                //~v@@@I~
        	pPIndex.insertIndex(pPat);  //assign QNo then insert time seq data//~v@@@I~
        OnSetend(1);                                               //~0213I~
    }                                                              //~v@@@I~
    else                                                           //~0213I~
    {                                                              //~0205I~
	    MaxFixed=pnpwt.seqno;                                     //~0213I~
    	SetAllAnswer();                                            //~0205R~
    	if ((Flags & F_ONRCHK)!=0)                                 //~v004R~
			OnRchk(1);		//answer thread end                    //~0205R~
    	else                                                       //~0205R~
            OnGo(1);                //answer thread end,kick timer //~0213R~
	}                                                              //~0205I~
    return 0;                                                      //~v002R~
}//OnEndThread                                                     //~0205R~
//**************************************************************** //~0122I~
//*return elapsed time                                             //~0212I~
//**************************************************************** //~0212I~
//int  OnTimer(ActionEvent e)                                      //~vj01R~//~0915R~
int  OnTimer(int msgId)                                            //~0915I~
{                                                                  //~0109I~
//  int Pid=timerid;                                                   //~vj01I~//~v@@@R~
    int Pid=msgId;                                                 //~v@@@I~
    long ctime;                                                    //~vj01R~
	long ts;                                                       //~vj01R~
    int   span=0;                                                  //~0212R~
//**************************************                           //~v@@@R~
//System.out.println("OnTimer");                                   //~v025R~
	if (Pid==IDC_SAVE)          //get elapsedtime at save;from Cpattern::Serialize//~v@@@I~
    {                                                              //~v@@@I~
		ctime=System.currentTimeMillis();                          //~v@@@I~
		ts=ctime-TryStartTime;                                     //~v@@@I~
		span=TryTimeSpan=(int)(ts/1000);                           //~v@@@I~
        if (span<0)                                                //~v@@@I~
        	span=0;                                                //~v@@@I~
    }                                                              //~v@@@I~
    else                                                           //~v@@@I~
	if (Pid==IDC_GO)                                               //~0109I~
    {                                                              //~0109I~
		if ((Flags & F_ONGO)==0)                                   //~v004R~
        	return 0;                                                //~0110I~
		if (GetSol(1)==0)		//reached to last answer           //~vj01R~
        {                                                          //~0109I~
    		if (timer_GO!=null)                                    //~vj01I~
            {                                                      //~vj01I~
    			timer_GO.stop();                                   //~vj01R~
    			timer_GO=null;                                     //~vj01I~
            }                                                      //~vj01I~
        	Flags&=~F_ONGO;                                        //~0109I~
		}                                                          //~0109I~
	}                                                              //~0109I~
    else                                                           //~0109I~
	if (Pid==IDC_MAKE||Pid==IDC_ANSWER)                            //~0122R~
    {                                                              //~0109I~
		if ((Flags & F_THREAD)==0)                                 //~v004R~
        	return 0;                                                //~0110I~
		if (Timeout==0)                                            //~vj01R~
        	return 0;                                                //~0110I~
        ctime=System.currentTimeMillis();                          //~vj01I~
		ts=ctime-StartTime;                                        //~0212I~
        if ((int)(ts/1000)>Timeout)                                //~vj01R~
			xnpsub.GblSubthreadStopReq=2;                          //~vj01R~
	}                                                              //~0109I~
    else                                                           //~0212I~
	if (Pid==IDC_SETEND)                                           //~0212I~
    {                                                              //~0212I~
		if ((Flags & F_ONTRY)!=0)                                  //~v004R~
        {                                                          //~0212I~
	        ctime=System.currentTimeMillis();                      //~vj01I~
			ts=ctime-TryStartTime;                                 //~0212I~
			span=TryTimeSpan=(int)(ts/1000);                       //~vj01R~
        }                                                          //~0212I~
        else                                                       //~0212I~
        {                                                          //~vj01I~
        	if (timer_SETEND!=null)                                //~vj01I~
            {                                                      //~vj01I~
	            timer_SETEND.stop();                               //~vj01I~
	            timer_SETEND=null;                                 //~vj01I~
            }                                                      //~vj01I~
        }                                                          //~vj01I~
	}                                                              //~0212I~
    else                                                           //~0212I~
	if (Pid==IDC_SUCCESS)                                          //~0211I~
    	span=OnSuccess();                                          //~0212R~
	return span;                                                   //~0212I~
}//OnTimer                                                         //~0212R~
//**************************************************************** //~0122I~
void OnRchk(int Pendthreadsw)                                      //~v004R~
{                                                                  //~9C31I~
	int cnt,ii,bx,by;                                              //~vj01R~
	int[]  pi;                                                     //~vj01I~
    if (Pendthreadsw==0)                                           //~vj01R~
    {	                                                           //~0122I~
    	if (GetSol(0)==0)	//submitted                            //~vj01R~
    		return;			//wait thread comp                     //~0122R~
    	Flags&=~F_ONRCHK;                                          //~0122I~
        return;                                                    //~0122I~
    }                                                              //~0122I~
    if (NpsubRc==0)       //no err                                 //~vj01R~
    {                                                              //~0109I~
	    cnt=Npworkt.rclistcnt;                                      //~0109I~
//      s="冗長度 MaxLevel== "+Npworkt.rclevel+",Level 1 は "+cnt+" 箇所";//~v023R~//~v@@@R~
//      SetTempMsg(s);                                             //~v023R~
        if (cnt!=0)                                                //~vj01R~
        {                                                          //~0109I~
            pi=Npworkt.rclist;                                      //~0109I~
            for (ii=0;ii<cnt;ii++)                                 //~0109I~
            {                                                      //~0109I~
            	bx=pi[ii+ii];                                      //~vj01I~
            	by=pi[ii+ii+1];                                    //~vj01I~
                BoardMap[ bx ][ by ].SetNum(-1,-1,Hole.HOLE_RCHK);	//redundancy err//~vj01R~
            }                                                      //~0109I~
        }                                                          //~0109I~
        ScoreMax=CalcScoreMax(Npworkt);    //set ScoreMax          //~v@@@R~
    }                                                              //~0109I~
    Flags&=~F_ONRCHK;                                               //~0109I~
}//OnRchk                                                                  //~9C31I~//~v@@@R~
//**************************************************************** //~0130I~
boolean OnSort()                                                   //~vj01R~
{                                                                  //~0130I~
    Hole phole;                                                    //~vj01R~
    int  num,bx,by,rc=0;                                           //~vj01R~
    int[][] pdata=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];             //~va01R~//~0914R~
//*********                                                        //~0130I~
    for (bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                        //~va01R~//~0914R~
        for (by = 0; by < Wnp.MAP_SIZE; by ++ )                    //~va01R~//~0914R~
        {                                                          //~0130I~
            num=BoardMap[bx][by].GetNum();                        //~0130I~
            if (num<0 || num >Wnp.MAP_SIZE)                            //~va19R~//~0914R~
            	rc=4;                                              //~0130I~
            pdata[bx][by]=num;                                     //~0130I~
		}                                                          //~0130I~
    if (rc==0)                                                     //~vj01R~
		rc=sub1.xnpsortdata(pdata,pPat.QuestionData);              //~vj01R~
    if (rc!=0)                                                     //~vj01R~
    {                                                              //~0130I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        SetTempMsg("整列できません");                              //~0130R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        SetTempMsg("Sort failed.");                                //~va23I~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.SortFailed).toString());//~v@@@I~
        return FALSE;                                              //~0212R~
	}                                                              //~0130I~
//set sorted data                                                  //~0130I~
    for (bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                        //~va01R~//~0914R~
        for (by = 0; by < Wnp.MAP_SIZE; by ++ )                    //~va01R~//~0914R~
        {                                                          //~0130I~
            phole=BoardMap[bx][by];                                //~0130R~
            phole.SetNum(pdata[bx][by],-1,-1);	//replace number   //~0130I~
            if (pPat.QuestionData[bx][by]!=0)                      //~vj01R~
				SetNewNum(bx,by,phole,1,1);//sort option           //~0312R~
        }                                                          //~0130I~
	if ((pPat.Datasw & CPattern.DATA_ANS)!=0)	//answer avail     //~vj01R~
		SaveAnswer();                                              //~v002I~
	return TRUE;                                                   //~0212I~
}                                                                  //~0130I~
//**************************************************************** //~0213I~
void CountPenalty(int Ppenalty)                                    //~v004R~
{                                                                  //~0213I~
    Penalty+=Ppenalty;                                             //~0213I~
}                                                                  //~0213I~
//**************************************************************** //~0213I~
void CalcScore()                                                   //~vj01R~
{                                                                  //~0213I~
    M99 pm99;                                                      //~vj01R~
    NPWORKT pnpwt=Npwtryans;                                       //~vj01R~
    int level,score;                                               //~0226R~
    int err,errall=0;                                              //~v024I~
//************************************                             //~v024R~
	Score=0;                                                       //~0213I~
	ScoreMax=0;                                                    //~0213I~
//    System.out.println("CalcScore clear scoremax="+ScoreMax);    //~v@@@R~
	if (pnpwt.seqno==0)	//init data                                //~v025R~
    	return;                                                    //~va03I~
    for (int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                    //~va01R~//~0914R~
        for (int by = 0; by < Wnp.MAP_SIZE; by ++ )                //~va01R~//~0914R~
        {                                                          //~0213I~
		    pm99=pnpwt.m99[bx][by];     //answer                   //~v004R~
            if (pm99.dlvl!=xnpsub.LEVEL_INIT)                  //q data//~vj01R~
            {   
				level=pm99.dlvl>>8;//~0213I~
                score=0;                                           //~0226I~
                switch(level)                                      //~0226I~
                {                                                  //~0226I~
                case xnpsub.LEVEL_EASY_F:                          //~vj01R~
                    score=5;                                       //~0226I~
                    break;                                         //~0226I~
                case xnpsub.LEVEL_MEDIUM_F:                        //~vj01R~
                    score=50;                                      //~0226I~
                    break;                                         //~0226I~
                case xnpsub.LEVEL_HARD_F:                          //~vj01R~
                    score=300;                                     //~0226R~
                    break;                                         //~0226I~
                case xnpsub.LEVEL_HARDP1_F:                        //~vj01R~
                    score=400;                                     //~0226R~
                    break;                                         //~0226I~
                case xnpsub.LEVEL_HARDP2_F:                        //~vj01R~
                    score=500;                                     //~0226R~
                    break;                                         //~0226I~
                }                                                  //~0226I~
            	ScoreMax+=score;                                   //~0226R~
		    	err=BoardMap[bx][by].GetErr();                     //~v024R~
		    	if (err==Hole.HOLE_PENDDATA)	//by next key              //~v024I~
                	errall=1;                                      //~v024I~
                else                                               //~v024I~
		    	if (err!=Hole.HOLE_NEXT)	//by next key          //~v024I~
            		Score+=score;                                  //~0226R~
			}                                                      //~0213I~
        }                                                          //~0213I~
    if (errall!=0)                                                    //~v024I~
    	Score+=pPat.Score;	//prev session score                   //~v024I~
	Score-=Penalty;                                                //~0226M~
    int min5=(TryTimeSpan+300)/300;		//by 5 min                 //~0213I~
    if (min5>PENALTY_NOTIME)                                       //~0226I~
        Score-=(min5-PENALTY_NOTIME)*PENALTY_TIME;                 //~0226I~
    if (Score==0)                                                  //~vj01R~
    	Score=1;                                                   //~0213I~
}//CalcScore                                                       //~0213I~
//**************************************************************** //~v@@@I~
private int CalcScoreMax(NPWORKT Ppnpwt)                           //~v@@@I~
{                                                                  //~v@@@I~
    M99 pm99;                                                      //~v@@@I~
    NPWORKT pnpwt=Ppnpwt;                                          //~v@@@I~
    int level,scoremax,score;                                            //~v@@@I~
 
//************************************                             //~v@@@I~
	scoremax=0;                                                    //~v@@@I~
	if (pnpwt.seqno==0)	//init data                                //~v@@@I~
    	return 0;                                                    //~v@@@I~
    for (int bx = 0; bx < Wnp.MAP_SIZE; bx ++ )                    //~v@@@I~
    {                                                              //~v@@@I~
        for (int by = 0; by < Wnp.MAP_SIZE; by ++ )                //~v@@@I~
        {                                                          //~v@@@I~
		    pm99=pnpwt.m99[bx][by];     //answer                   //~v@@@I~
            if (pm99.dlvl!=xnpsub.LEVEL_INIT)                  //q data//~v@@@I~
            {                                                      //~v@@@I~
				level=pm99.dlvl>>8;                                //~v@@@I~
                score=0;                                           //~v@@@I~
                switch(level)                                      //~v@@@I~
                {                                                  //~v@@@I~
                case xnpsub.LEVEL_EASY_F:                          //~v@@@I~
                    score=5;                                       //~v@@@I~
                    break;                                         //~v@@@I~
                case xnpsub.LEVEL_MEDIUM_F:                        //~v@@@I~
                    score=50;                                      //~v@@@I~
                    break;                                         //~v@@@I~
                case xnpsub.LEVEL_HARD_F:                          //~v@@@I~
                    score=300;                                     //~v@@@I~
                    break;                                         //~v@@@I~
                case xnpsub.LEVEL_HARDP1_F:                        //~v@@@I~
                    score=400;                                     //~v@@@I~
                    break;                                         //~v@@@I~
                case xnpsub.LEVEL_HARDP2_F:                        //~v@@@I~
                    score=500;                                     //~v@@@I~
                    break;                                         //~v@@@I~
                }                                                  //~v@@@I~
            	scoremax+=score;                                   //~v@@@I~
			}                                                      //~v@@@I~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
//    System.out.println("CalcScoreMAx scoremax="+scoremax);       //~v@@@R~
    return scoremax;                                               //~v@@@I~
}//CalcScoreMax                                                    //~v@@@I~
//************************************************************     //~0122I~
int CallAns(int Poption,int[][] Ppdata)                            //~vj01R~
{                                                                  //~0122I~
	int  opt,rc;                                         //~vj01R~
	int  tmsgfreq=1000;		//time msg frequency                   //~vj01R~
	int  repeatmax=xnpsub.ANS_TIMEOUT;  	//loop max             //~vj01R~
    Object[] plist=new Object[8];                                  //~vj01I~
	String rcmsg=new String("");                                   //~vj01R~
                                                                   //~vj01I~
    plistans=plist;                                                //~vj01I~
                                                                   //~0122I~
    if ((pdata=Ppdata)==null)                                      //~vj01R~
    {                                                              //~0205I~
		pdata=pPat.QuestionData;                                   //~vj01R~
		ResetErr(1);	//clear also HOLE_NEXT                     //~0205I~
	}                                                              //~0205I~
    else                                                           //~0205I~
		ResetErr(0);    //leave HOLE_NEXT                          //~0205I~
    Npworkt.clear();                                               //~vj01R~
                                                                   //~0122I~
    opt=(xnpsub.XNP_PILOT|xnpsub.XNP_PILOTNOMSG|xnpsub.XNP_NOPRINTIN|xnpsub.XNP_NOPRINTOUT);//~vj01R~
	opt|=Poption;                                                  //~0130R~
	opt&=xnpsub.XNP_SOLVMASK;                                      //~vj01R~
                                                                   //~0122I~
	plist[0]=this;                                                 //~0122I~
	plist[1]=new Integer(opt);                                     //~v004I~
	plist[2]=pdata;                                                //~0122I~
	plist[3]=Npworkt;                                              //~v004R~
	plist[4]=new Integer(tmsgfreq);                                //~v004I~
	plist[5]=new Integer(repeatmax);                               //~v004I~
	plist[6]=null;                                                 //~vj01R~
	plist[7]=rcmsg;                                                //~v004R~
                                                                   //~0122I~
    NpsubRc=0;                                                     //~0122I~
                                                                   //~0122I~
	Thread 	pThread=new Thread(this,Integer.toString(IDC_ANSWER)); //~vj01I~
//    if (pThread!=null)                                             //~vj01R~//~v@@@R~
//    {                                                              //~0122I~//~v@@@R~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        SetTempMsg("解答開始");                                    //~0122I~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        SetTempMsg("Started to answer");                           //~va23I~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.Started).toString());//~v@@@I~
    	Flags|=F_THREAD;                                           //~0122I~
        StartTime=System.currentTimeMillis();                      //~vj01I~
        timerid=IDC_ANSWER;                                        //~vj01I~
//      timer_ANS=new Timer(xnpsub.ANS_MSGFREQ*1000,pView);        //~vj01I~//~0915R~
        timer_ANS=new NppTimer(timerid,xnpsub.ANS_MSGFREQ*1000,this);//~0915I~
//        timer_ANS.setCoalesce(true);                               //~vj01I~
        timer_ANS.setRepeats(true);                                //~vj01I~
        timer_ANS.start();                                         //~vj01I~
    	//5 sec ms frequency                                       //~vj01I~
        rc=0;                                                      //~0122I~
//        if (Mode==MODE_KEYINANS)                                 //~0205R~
//            Flags|=F_ANSCHK;    //answer chk                     //~0205R~
//        else                                                     //~0205R~
//            Flags&=~F_ANSCHK;                                    //~0205R~
        	                                                       //~0123I~
        pThread.start();                                           //~vj01I~
//    }                                                              //~0122I~//~v@@@R~
//    else                                                           //~0122I~//~v@@@R~
//    {                                                              //~0122I~//~v@@@R~
////      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
////        SetTempMsg("スレッド作成に失敗しました");                  //~0122I~//~v@@@R~
////      else                                                         //~va23I~//~v@@@R~
////        SetTempMsg("CreateThread failed.");                        //~va23I~//~v@@@R~
//        SetTempMsg(WnpView.context.getText(R.string.CreateThreadFailed).toString());//~v@@@R~
//        rc=4;                                                      //~0122I~//~v@@@R~
//    }                                                              //~0122I~//~v@@@R~
    return rc;                                                     //~0122I~
}                                                                  //~0122I~
int AnsThread()                                                    //~vj01R~
{                                                                  //~0122I~
	Object[] plist=plistans;                                       //~vj01R~
	Board pBoard=(Board)plist[0];                                  //~vj01R~
	int   opt=((Integer)plist[1]).intValue();                      //~vj01I~
	int[][] pdata=(int[][])plist[2];                                   //~vj01R~
	NPWORKT pnpwt=(NPWORKT)plist[3];                               //~vj01R~
	int tmsgfreq=((Integer)plist[4]).intValue();                   //~vj01I~
	int repeatmax=((Integer)plist[5]).intValue();                  //~vj01I~
	int prc=0;                                                     //~vj01R~
//	String rcmsg=(String)plist[7];                                 //~vj01R~
                                                                   //~0122I~
    try                                                            //~0122I~
    {                                                              //~0122I~
    	GblSubthreadsw=1;                                          //~0122I~
		xnpsub.GblSubthreadStopReq=0;                              //~vj01R~
	    GblNpsubMsg=null;                                          //~vj01R~
                                                                   //~0122I~
	    prc=sub1.xnpgetanswer(opt,pdata,pnpwt,tmsgfreq,repeatmax); //~vj01R~
		pBoard.Npsuberr1(prc);                                     //~vj01R~
        NpsubRc=prc;                                               //~v012I~
//System.out.println("anssub rc="+prc);                            //~v012R~
	}                                                              //~0122I~
    catch(uerrexit_excp e){                                        //~vj01I~
        e.printStackTrace();                                       //~va01R~
    	Npsuberr4(e);                                              //~vj01I~
    }                                                              //~vj01I~
    catch(RuntimeException e){                                     //~vj01I~
        e.printStackTrace();                                       //~va01I~
    	Npsuberr2(e);                                              //~vj01I~
    }                                                              //~vj01I~
    if (timer_ANS!=null)                                           //~vj01I~
    {                                                              //~vj01I~
    	timer_ANS.stop();                                          //~vj01I~
    	timer_ANS=null;                                            //~vj01I~
    }                                                              //~vj01I~
	plist[6]=new Integer(prc);                                     //~vj01I~
	notifyfinished();                                              //~vj01I~
    return 0;                                                      //~0122I~
}                                                                  //~0122I~
int CallMake()                                                     //~v004R~
{                                                                  //~0109I~
	int  opt,level,den,rc;                                         //~vj01R~
	int  tmsgfreq=1000;		//time msg frequency                   //~vj01R~
	int  repeatmax=0x0fffffff;  	//loop max                     //~vj01R~
//  int  plan=9;	//search pos from max candidate                //~v041R~
//  int  plan=-9;	//search pos from min candidate                //~v043R~
    int  plan=9;	//search pos from max candidate                //~v043I~
	String rcmsg=new String("");                                   //~vj01R~
                                                                   //~0109I~
    Object[] plist=new Object[13];                                 //~vj01I~
    plistmake=plist;                                               //~vj01I~
                                                                   //~0109I~
    pPat.Seed=ActSeed=0;                                          //~0129I~
	ResetErr(1);                                                   //~0205R~
    if (pPat.ChkData())                                         //~0109I~
	    pdata=pPat.QuestionData;                                   //~vj01R~
	else                                                           //~0109I~
    	pdata=null;			//no pattern data                      //~vj01R~
    Npworkt.clear();                                               //~vj01R~
                                                                   //~0110I~
	opt=0;                                                         //~0110I~
  if (pdata!=null)                                                 //~v025I~
    if ((Flags & F_RIGID)!=0)                                      //~v004R~
	    opt|=xnpsub.XNP_RIGID;                                     //~vj01R~
                                                                   //~va16R~
    if ((Flags & F_NOREDUNDANT)!=0)                                //~va16R~
	    opt|=xnpsub.XNP_DELREDUNDANT;                              //~va16R~
                                                                   //~va16R~
	opt&=xnpsub.XNP_MAKEMASK;                                      //~vj01R~
                                                                   //~0109I~
    if ((Flags & F_LVL1)!=0)                                       //~v004R~
    	level='E';                                                 //~0109I~
    else                                                           //~0109I~
    if ((Flags & F_LVL2)!=0)                                       //~v004R~
    	level='M';                                                 //~0109I~
    else                                                           //~0109I~
    if ((Flags & F_LVL3)!=0)                                       //~v004R~
    	level='H';                                                 //~0109I~
    else                                                           //~0109I~
    	level=0;                                                   //~0109I~
                                                                   //~0109I~
//    if ((Flags & F_DEN3)!=0)                                     //~va03R~
//        den=Wnp.BOARDTYPE;                                       //~va03R~//~0914R~
//    else                                                         //~va03R~
//    if ((Flags & F_DEN4)!=0)                                     //~va03R~
//        den=Wnp.BOARDTYPE+1;                                     //~va03R~//~0914R~
//    else                                                         //~va03R~
//    if ((Flags & F_DEN5)!=0)                                     //~va03R~
//        den=Wnp.BOARDTYPE+2;                                     //~va03R~//~0914R~
//    else                                                         //~va03R~
//        den=xnpsub2.SETMAX;                                      //~va03R~
    den=Dencity;                                                   //~va03I~
                                                                   //~0109I~
	plist[0]=this;                                                 //~0109I~
	plist[1]=new Integer(opt);                                     //~v004I~
	plist[2]=Npworkt;                                              //~v004R~
	plist[3]=pdata;                                                //~0109I~
	plist[4]=new Integer(den);                                     //~v004I~
	plist[5]=new Integer(tmsgfreq);                                //~v004I~
	plist[6]=new Integer(repeatmax);                               //~v004I~
	plist[7]=new Integer(Seed);                                    //~v004I~
	plist[8]=new Integer(plan);                                    //~v004I~
	plist[9]=new Integer(level);                                   //~v004I~
	plist[10]=null;                                                //~vj01R~
	plist[11]=null;                                                //~vj01R~
	plist[12]=rcmsg;                                               //~v004R~

    NpsubRc=0;                                                 //~0110I~

	Thread pThread=new Thread(this,Integer.toString(IDC_MAKE));    //~vj01I~
//    if (pThread!=null)                                             //~vj01R~//~v@@@R~
//    {                                                              //~0109I~//~v@@@R~
//      SetTempMsg("作成開始");                                    //~0110R~//~v@@@R~
		SetTempMsg(WnpView.context.getText(R.string.MakeStarted).toString());//~v@@@I~
    	Flags|=F_THREAD;                                           //~0109I~
        StartTime=System.currentTimeMillis();                      //~vj01I~
        timerid=IDC_MAKE;                                          //~vj01I~
//      timer_MAKE=new Timer(5000,pView);                          //~vj01I~//~0915R~
        timer_MAKE=new NppTimer(timerid,5000,this);                //~0915I~
//        timer_MAKE.setCoalesce(true);                              //~vj01I~
        timer_MAKE.setRepeats(true);                               //~vj01I~
        timer_MAKE.start();                                        //~vj01I~
   //5 sec ms frequency                                            //~vj01I~
        pThread.start();                                           //~vj01I~
        rc=0;                                                      //~0123I~
//    }                                                              //~0109I~//~v@@@R~
//    else                                                           //~0109I~//~v@@@R~
//    {                                                               //~0123I~//~v@@@R~
////      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
////        SetTempMsg("スレッド作成に失敗しました");                  //~0109I~//~v@@@R~
////      else                                                         //~va23I~//~v@@@R~
////        SetTempMsg("CreateThread failed.");                        //~va23I~//~v@@@R~
//        SetTempMsg(WnpView.context.getText(R.string.CreateThreadFailed).toString());//~v@@@R~
//        rc=4;                                                      //~0123I~//~v@@@R~
//    }                                                              //~0123I~//~v@@@R~
    return rc;                                                     //~0123I~
}                                                                  //~0109I~
//**************************************************************************//~v@@@I~
int MakeThread()                                                   //~vj01R~
{
	Object[] plist=plistmake;                                      //~vj01I~
	Board pBoard=(Board)plist[0];                                  //~vj01R~
	int   opt=((Integer)plist[1]).intValue();                      //~vj01I~
	NPWORKT pnpwt=(NPWORKT)plist[2];                               //~vj01R~
	int[][] pdata=(int[][])plist[3];                               //~vj01I~
	int den=((Integer)plist[4]).intValue();                        //~vj01I~
	int tmsgfreq=((Integer)plist[5]).intValue();                   //~vj01I~
	int repeatmax=((Integer)plist[6]).intValue();                  //~vj01I~
	int seed=((Integer)plist[7]).intValue();                       //~vj01I~
	int plan=((Integer)plist[8]).intValue();                       //~vj01I~
	int level=((Integer)plist[9]).intValue();                      //~vj01I~
//  int pactseed=0;                                                //~v@@@R~
	int [] seedout=new int[1];//~vj01R~
	int prc=0;                                                     //~vj01R~
//	String rcmsg=(String)plist[12];                                //~vj01I~
    seedout[0]=0;                                                               //~0109I~
    try                                                            //~0109I~
    {                                                              //~0109I~
    	GblSubthreadsw=1;                                          //~0110R~
		xnpsub.GblSubthreadStopReq=0;                              //~vj01R~
	    GblNpsubMsg=null;                                          //~vj01R~
                                                                   //~0110I~
		prc=sub2.xnpmakequestion(opt,pnpwt,                        //~vj01R~
				pdata,den,tmsgfreq,repeatmax,seed,plan,level,seedout);//~0109I~
		pBoard.Npsuberr1(prc);                                     //~vj01R~
		NpsubRc=prc;                                               //~v012I~
		ActSeed=seedout[0];                                        //~v012I~
//System.out.println("makesub rc="+prc+",seed="+ActSeed);          //~v012R~
	}                                                              //~0109I~
    catch(uerrexit_excp e){                                        //~vj01I~
        e.printStackTrace();                                       //~va01I~
    	Npsuberr4(e);                                              //~vj01I~
    }                                                              //~vj01I~
    catch(RuntimeException e){                                     //~vj01I~
        e.printStackTrace();                                       //~va01I~
    	Npsuberr2(e);                                              //~vj01I~
    } 
//  pactseed=seedout[0];//~vj01I~                                  //~v@@@R~
    if (timer_MAKE!=null)                                          //~vj01I~
    {                                                              //~vj01I~
    	timer_MAKE.stop();                                         //~vj01I~
    	timer_MAKE=null;                                           //~vj01I~
    }                                                              //~vj01I~
	notifyfinished();                                              //~vj01I~
    return 0;                                                      //~0109I~
}//MakeThread                                                                  //~0109I~//~v@@@R~
//**************************************************************************//~v@@@I~
void Npsuberr1(int rc)                                             //~v004R~
{                                                                  //~0122M~
    String s;                                                      //~vj01R~
	String pc;                                                     //~vj01R~
    if ((pc=GblNpsubMsg)==null)                                    //~vj01R~
    	pc="";                                                     //~0122M~
    if (rc==0)                                                     //~vj01R~
    {                                                              //~0211I~
		Slevelsummary=Npworkt.level;                               //~va21I~
        s=pc;                                                      //~vj01R~
        if ((Npworkt.level & xnpsub.LEVEL_HARDP2_S)!=0)            //~vj01R~
        {                                                          //~v@@@I~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sProbLevel="難++";                                     //~0211M~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sProbLevel="H++";                                      //~va23R~//~v@@@R~
//  		sProbLevel=WnpView.context.getText(R.string.LevelH2).toString();//~v@@@R~
		    intProbLevel=5 ;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~0211M~
        if ((Npworkt.level & xnpsub.LEVEL_HARDP1_S)!=0)            //~vj01R~
        {                                                          //~v@@@I~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sProbLevel="難+";                                      //~0211M~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sProbLevel="H+";                                       //~va23R~//~v@@@R~
//  		sProbLevel=WnpView.context.getText(R.string.LevelH1).toString();//~v@@@R~
		    intProbLevel=4 ;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~0211M~
        if ((Npworkt.level & xnpsub.LEVEL_HARD_S)!=0)              //~vj01R~
        {                                                          //~v@@@I~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sProbLevel="難";                                       //~0211M~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sProbLevel="Hrd";                                      //~va23R~//~v@@@R~
//  		sProbLevel=WnpView.context.getText(R.string.LevelH).toString();//~v@@@R~
		    intProbLevel=3 ;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~0211M~
        if ((Npworkt.level & xnpsub.LEVEL_MEDIUM_S)!=0)            //~vj01R~
        {                                                          //~v@@@I~
//          if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sProbLevel="中";                                       //~0211M~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sProbLevel="Med";                                      //~va23R~//~v@@@R~
//  		sProbLevel=WnpView.context.getText(R.string.LevelM).toString();//~v@@@R~
		    intProbLevel=2 ;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~0211M~
        if ((Npworkt.level & xnpsub.LEVEL_EASY_S)!=0)              //~vj01R~
        {                                                          //~v@@@I~
//        if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~v@@@R~
//            sProbLevel="易";                                       //~0211R~//~v@@@R~
//          else                                                     //~va23I~//~v@@@R~
//            sProbLevel="Esy";                                      //~va23R~//~v@@@R~
//  		sProbLevel=WnpView.context.getText(R.string.LevelE).toString();//~v@@@R~
		    intProbLevel=1 ;                                       //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~0211I~
//      	sProbLevel="";                                         //~0211I~//~v@@@R~
		    intProbLevel=0;                                        //~v@@@R~
    	sProbLevel=Slevelstr[intProbLevel];                         //~v@@@I~
	}                                                              //~0211I~
    else                                                           //~0122M~
    {                                                              //~0211I~
//      if (Wnp.Sjlang)                                              //~va23I~//~0914R~//~v@@@R~
//        s="失敗;(rc="+rc+")"+pc;                                   //~vj01R~//~v@@@R~
//      else                                                         //~va23I~//~v@@@R~
//        s="failed;(rc="+rc+")"+pc;                                 //~va23I~//~v@@@R~
		s=WnpView.contextR.getString(R.string.Failed);        //~v@@@I~
        s+=";(rc="+rc+")"+pc;                                      //~v@@@I~
        sProbLevel="";                                             //~0211I~
		intProbLevel=0;                                            //~v@@@I~
	}                                                              //~0211I~
    pPat.strProbLevel=sProbLevel;                                  //~v@@@I~
    pPat.intProbLevel=intProbLevel;                                //~v@@@I~
//    System.out.println("msg="+s+",problevel="+sProbLevel);       //~v@@@R~
    SetTempMsg(s);                                                 //~0122M~
    sSuccessMsg=s;                                                //~0129I~
}//Npsuberr1                                                       //~v@@@R~
//**************************************************************************//~v@@@I~
private void Npsuberr2(RuntimeException e)                         //~va01R~
{                                                                  //~0122M~
//    if ((msgbuff=e.getLocalizedMessage())!=null)                 //~va01R~
//        SetTempMsg(msgbuff);                                     //~va01R~
//    else                                                         //~va01R~
//        SetTempMsg("CException発生");                            //~va01R~//~v@@@R~
    SetTempMsg(e.toString());                                      //~va01I~
    NpsubRc=253;                                                   //~0122M~
}                                                                  //~0122M~
void Npsuberr4(uerrexit_excp e)                                    //~vj01I~
{                                                                  //~vj01I~
	SetTempMsg(GblNpsubMsg);                                       //~vj01I~
    NpsubRc=254;                                                   //~vj01I~
}                                                                  //~vj01I~
private void notifyfinished(){                                     //~vj01I~
//System.out.println("notify thraed");                             //~v025R~
	final Runnable dofinished=new Runnable(){                      //~vj01I~
		public void run(){                                         //~vj01I~
			pView.OnEndThread();                                   //~vj01I~
		}                                                          //~vj01I~
	};                                                             //~vj01I~
//		SwingUtilities.invokeLater(dofinished);                    //~vj01I~
	pView.wnpviewcallback.post(dofinished);	//execute on view thread//~v@@@I~
                                                                   //~vj01I~
}                                                                  //~vj01I~
//**************************************************************** //~v@@@I~
//**************************************************************** //~v@@@I~
private void exitDlg()                                         //~v@@@I~
{                                                                  //~v@@@I~
	ButtonDlg.simpleYNExitAlertDialog(WnpView.context.getText(R.string.YNExit).toString());//~v@@@R~
	                                                               //~v@@@I~
}//exitDlg                                                         //~v@@@I~
//**************************************************************** //~vj01I~
//* class Hole                                                     //~vj01I~
//**************************************************************** //~vj01I~
private class Hole {                                               //~v004I~
    public static final int  HOLE_ERR    = 1;                      //~vj01I~
    public static final int  HOLE_RCHK   = 2;                      //~vj01I~
    public static final int  HOLE_NEXT   =16;    //used Next Button//~vj01I~
    public static final int  HOLE_NEXTERR=17;    //used Next Button//~vj01I~
	public static final int  HOLE_PENDDATA=18;   //read from file  //~v024I~

	private  int    BoardX, BoardY;                                //~vj01I~
    private  int     State;           //mode of board              //~vj01I~
    private  int     Err;           //mode of board                //~vj01I~
    private  int     Num;           //mode of board                //~vj01I~
    private  int[]   Memo=new int[4];                              //~vj01R~
    private  int     MemoCnt;                                      //~vj01I~
                                                                   //~vj01I~
Hole( int board_x, int board_y )                                   //~v004I~
{                                                                  //~v004M~
    BoardX = board_x;                                              //~v004M~
    BoardY = board_y;                                              //~v004M~
    Num=0;                                                         //~v004M~
    State=0;                                                       //~v004M~
    Err=0;                                                         //~v004M~
	MemoCnt=0;                                                     //~v004M~
	Arrays.fill(Memo,0);                                           //~vj01I~
}                                                                  //~v004M~
//===============================================================================*///~v004M~
int GetNum()                                                       //~vj01R~
{                                                                  //~v004M~
    return Num;                                                    //~v004M~
}                                                                  //~v004M~
//===============================================================================//~v004M~
//hole                                                             //~0915I~
//===============================================================================//~0915I~
void SetNum(int Pnum,int Pmode,int Perr)                           //~v004I~
{                                                                  //~v004M~
	if (Pmode>=0)                                                  //~v004M~
    	State=Pmode;                                               //~v004M~
	if (Pnum>=0)                                                   //~v004M~
    	Num=Pnum;                                                  //~v004M~
                                                                   //~v004M~
	if (Perr>=0)                                                   //~v004M~
    	if (Err>=HOLE_NEXT && Perr==HOLE_ERR)                      //~v004M~
        	Err=HOLE_NEXTERR;                                      //~v004M~
        else                                                       //~v004M~
	    	Err=Perr;                                              //~v004M~
}                                                                  //~v004M~
//===============================================================================//~v004M~
//num=0:clear first memo,10:clear all memo=====================================//~v004M~
//return:cleared count for all clear;1 if memo changed for other case//~v004M~
//===============================================================================//~v004M~
int  SetMemo(int Pnum)                                             //~v004I~
{                                                                  //~v004M~
//System.out.println("Hole:SetMemo num="+Pnum);                    //~v012R~
	int ii,jj,rc=0;                                                //~v004M~
//  int[] temp=new int[10];                                        //~va19R~
    if (Pnum==PATTERN_ID)		//clear req                        //~v004M~
    {                                                              //~v004M~
		Arrays.fill(Memo,0);                                       //~vj01I~
        ii=MemoCnt;                                                //~v004M~
		MemoCnt=0;                                                 //~v004M~
        return ii;                                                 //~v004M~
    }                                                              //~v004M~
    if (Pnum<0 || Pnum>Wnp.MAP_SIZE)                                   //~va19R~//~0914R~
    	return 0;                                                  //~v004M~
    if (Pnum!=0)                                                   //~vj01R~
    {                                                              //~v004M~
    	for (ii=0;ii<4;ii++)                                       //~v004M~
        	if (Memo[ii]==Pnum)                                    //~v004M~
            {                                                      //~v004M~
            	Memo[ii]=0;	//clear if same num                    //~v004M~
                rc=1;                                              //~v004M~
                break;                                             //~v004M~
			}                                                      //~v004M~
		if (ii==4)   			//new num                          //~v004M~
            for (ii=0;ii<4;ii++)                                   //~v004M~
                if (Memo[ii]==0)                                   //~vj01R~
                {                                                  //~v004M~
                    Memo[ii]=Pnum;                                 //~v004M~
	                rc=1;                                          //~v004M~
                    break;                                         //~v004M~
                }                                                  //~v004M~
	}                                                              //~v004M~
    else	//Pnum==0		//clear first                          //~v004M~
    	for (ii=0;ii<4;ii++)                                       //~v004M~
        	if (Memo[ii]!=0)                                       //~vj01R~
            {                                                      //~v004M~
            	Memo[ii]=0;                                        //~v004M~
                rc=1;                                              //~v004M~
                break;                                             //~v004M~
			}                                                      //~v004M~
	Arrays.fill(temp,0);                                           //~vj01R~
    for (ii=0;ii<4;ii++)                                           //~v004M~
    	temp[Memo[ii]]=1;                                          //~v004M~
	Arrays.fill(Memo,0);                                           //~vj01R~
    for (ii=1,jj=0;ii<=Wnp.MAP_SIZE;ii++)                              //~va19R~//~0914R~
        if (temp[ii]!=0)                                           //~vj01R~
            Memo[jj++]=ii;                                         //~v004M~
	MemoCnt=jj;                                                    //~v004M~
    return rc;                                                     //~v004M~
}//Hole::SetMemo                                                                  //~v004M~//~v@@@R~
//===============================================================================//~v004M~
int GetState()                                                     //~vj01R~
{                                                                  //~v004M~
    return State;                                                  //~v004M~
}                                                                  //~v004M~
//===============================================================================//~v004M~
int GetErr()                                                       //~v004I~
{                                                                  //~v004M~
    return Err;                                                    //~v004M~
}                                                                  //~v004M~
//===============================================================================//~v004M~
void    Show(Canvas pDC,int PFocus,int Psamenum,boolean Pansmode)//~vj01R~//~0914R~//~0915R~
{                                                                  //~v004M~
	String szNum;                                                  //~vj01R~
    int x, y,ii;                                                   //~v004M~
//    int   br=pDC.getColor();                                         //~vj01I~//~0914R~
//  Paint pDCpaint=new Paint(Paint.ANTI_ALIAS_FLAG);               //~v@@@R~
    int memox,memoy;                                               //~va19I~
//******************************                                   //~va19I~
//System.out.println("Focus="+PFocus+",Psamenum="+Psamenum+",pansmode="+(Pansmode?1:0));//~va03R~
    if (Num!=0)                                                    //~vj01R~
    {                                                              //~v004M~
    	if (Psamenum==Num)                                         //~v004M~
        	PFocus+=2;                                             //~v004M~
        if (Err==HOLE_ERR||Err==HOLE_NEXTERR)                      //~v004M~
        {                                                          //~v004M~
            ShowObject( pDC, BoardX, BoardY, ST_ERR,PFocus);       //~va23R~
            pDCpaintT.setColor(COL_NUM_E);                               //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_E.getRed()+",g="+COL_NUM_E.getGreen()+",b="+COL_NUM_E.getBlue());//~vj01R~
		}                                                          //~v004M~
        else                                                       //~v004M~
        if (Err==HOLE_NEXT)                                        //~v004M~
        {                                                          //~vj01R~
            ShowObject( pDC, BoardX, BoardY, ST_NEXT,PFocus);      //~va23R~
            pDCpaintT.setColor(COL_NUM_NEXT);                            //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_NEXT.getRed()+",g="+COL_NUM_NEXT.getGreen()+",b="+COL_NUM_NEXT.getBlue());//~va03R~
		}                                                          //~v004M~
        else                                                       //~v004M~
        if (Err==HOLE_RCHK)                                        //~v004M~
        {                                                          //~v004M~
            ShowObject( pDC, BoardX, BoardY, ST_RCHK,PFocus);      //~va23R~
            pDCpaintT.setColor(COL_NUM_RCHK);                            //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_RCHK.getRed()+",g="+COL_NUM_RCHK.getGreen()+",b="+COL_NUM_RCHK.getBlue());//~vj01R~
		}                                                          //~v004M~
        else                                                       //~v004M~
        if (State<=MODE_ENDQDATA)                                  //~v004M~
        {                                                          //~v004M~
            if (Pansmode)                                          //~vj01R~
				if (Num==PATTERN_ID)                               //~v004M~
			        ShowObject( pDC, BoardX, BoardY,ST_EMPTY,PFocus);//~va23R~
                else                                               //~v004M~
                {                                                  //~v004M~
                    ShowObject( pDC, BoardX, BoardY, ST_QDATA,PFocus);//~va23R~
                    pDCpaintT.setColor(COL_NUM_Q);                       //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_Q.getRed()+",g="+COL_NUM_Q.getGreen()+",b="+COL_NUM_Q.getBlue());//~vj01R~
                }                                                  //~v004M~
            else                                                   //~v004M~
            {                                                      //~v004M~
                ShowObject( pDC, BoardX, BoardY, ST_PAT,PFocus);   //~va23R~
                pDCpaintT.setColor(COL_NUM_P);                           //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_P.getRed()+",g="+COL_NUM_P.getGreen()+",b="+COL_NUM_P.getBlue());//~vj01R~
            }                                                      //~v004M~
        }                                                          //~v004M~
        else                                                       //~v004M~
        {                                                          //~v004M~
            ShowObject( pDC, BoardX, BoardY, ST_ANS,PFocus);       //~va23R~
            pDCpaintT.setColor(COL_NUM_A);                               //~vj01R~//~v@@@R~
//System.out.println("Show:Num="+Num+",r="+COL_NUM_A.getRed()+",g="+COL_NUM_A.getGreen()+",b="+COL_NUM_A.getBlue());//~vj01R~
        }                                                          //~v004M~
	}                                                              //~v004M~
	else                                                           //~v004M~
        ShowObject( pDC, BoardX, BoardY,ST_EMPTY,PFocus);          //~va23R~
//Num Text                                                         //~v004M~
    BoardToScreen( BoardX,BoardY, wkpoint);                        //~va23R~
    x=wkpoint.x; y=wkpoint.y;                                      //~v004I~
	pDCpaintT.setTextSize(NumTextFontsz);//~va23R~                       //~0914R~//~v@@@R~
	pDCpaintT.setTypeface(Wnp.Sfontname);                                    //~0914I~//~v@@@R~
    if (Num!=0)                                                    //~vj01R~
    {                                                              //~v004M~
//		pDC.SetBkMode(TRANSPARENT);                                //~vj01R~
        if (Num==PATTERN_ID)                                       //~v004M~
        {                                                          //~v004M~
            if (!Pansmode)                                         //~vj01R~
	    		szNum="X";                                          //~vj01R~//~v@@@R~
            else                                                   //~v004M~
                szNum=" ";                                         //~vj01R~//~v@@@R~
		}                                                          //~v004M~
        else                                                       //~v004M~
            if (!Pansmode)                                         //~vj01R~
	    		szNum="";                                          //~vj01R~
                                                                   //~v004M~
		else                                                       //~v004M~
                szNum=Integer.toString(Num);                       //~vj01R~
        if (Num<10)                                                //~va01I~
			pDC.drawText(szNum,x+NumTextPosx,y+NumTextPosy,pDCpaintT);   //1 digit//~va01I~//~v@@@R~
        else                                                       //~va01I~
			pDC.drawText(szNum,x+NumTextPosx10,y+NumTextPosy,pDCpaintT); //2 digit//~va01R~//~v@@@R~
	}                                                              //~v004M~
	else                                                           //~v004M~
    	if (Pansmode && MemoCnt!=0)                                //~vj01R~
        {                                                          //~v004M~
//			pDC.SetBkMode(TRANSPARENT);                            //~vj01R~
//  		pDC.setFont(new java.awt.Font(Wnp.Sfontname, java.awt.Font.PLAIN,Wnp.MEMOTEXT_SIZE));//~va23R~//~0914R~
            pDCpaintT.setColor(COL_NUM_A);                               //~vj01R~//~v@@@R~
//System.out.println("Show2:Num="+Num+",r="+COL_NUM_A.getRed()+",g="+COL_NUM_A.getGreen()+",b="+COL_NUM_A.getBlue());//~vj01R~
            for (ii=0;ii<MemoCnt;ii++)                             //~v004M~
            {                                                      //~v004M~
                if (Memo[ii]==0)                                   //~vj01R~
                    break;                                         //~v004M~
                if (Memo[ii]>=10)                                   //~va19I~
                {                                                  //~0914I~
					pDCpaintT.setTextSize(Wnp.MEMOTEXT10_SIZE);//~va23R~//~0914R~//~v@@@R~
					pDCpaintT.setTypeface(Wnp.Sfontname);                    //~0914I~//~v@@@R~
                }                                                  //~0914I~
                else                                               //~va19I~
                {                                                  //~0914I~
					pDCpaintT.setTextSize(Wnp.MEMOTEXT_SIZE);//~va23R~   //~0914R~//~v@@@R~
					pDCpaintT.setTypeface(Wnp.Sfontname);                    //~0914I~//~v@@@R~
                }                                                  //~0914I~
                szNum=Integer.toString(Memo[ii]);                  //~vj01R~
//              pDC.drawString(szNum,x+Wnp.MEMOTEXT_MARGIN*3+(ii%2!=0 ? Wnp.MEMOTEXT_SIZE-Wnp.MEMOTEXT_MARGIN:0),//~va19R~//~0914R~
                memox=Wnp.MEMOTEXT_MARGIN*3;                       //~va19R~//~0914R~
                if (ii%2!=0)  // 1,3                               //~va19R~
                {                                                  //~va19I~
                    memox+=Wnp.MEMOTEXT_SIZE-Wnp.MEMOTEXT_MARGIN;  //~va19I~//~0914R~
                	if (Memo[ii]>=10)                              //~va19I~
						memox-=(Wnp.MEMOTEXT_MARGIN-1);            //~va19R~//~0914R~
                }                                                  //~va19I~
                else          //0,2                                //~va19R~
                {                                                  //~va19I~
                	if (Memo[ii]>=10)                              //~va19R~
    					memox-=(Wnp.MEMOTEXT_MARGIN+1);            //~va19R~//~0914R~
                }                                                  //~va19I~
                memoy=(int) (Wnp.OBJECT_SIZE-2-Wnp.MEMOTEXT_MARGIN);       //~va19I~//~0914R~
                if (ii/2==0)                                      //~va19I~
                	memoy-=Wnp.MEMOTEXT_SIZE-1;                    //~va19I~//~0914R~
                pDC.drawText(szNum,x+memox,y+memoy,pDCpaintT);             //~va19I~//~v@@@R~
            }                                                      //~v004M~
			pDCpaintT.setTextSize(32);//~va23R~                          //~0914R~//~v@@@R~
			pDCpaintT.setTypeface(Wnp.Sfontname);                            //~0914I~//~v@@@R~
        }                                                          //~v004M~
//	pDC.setColor(br);                                              //~vj01I~
}//Show                                                            //~v004M~//~v@@@R~
}//class Hole                                                      //~v004I~
public void run() {                                                //~vj01I~
	String estr;
	try {                                                          //~v@@@I~
                                                     //~v@@@I~
	Thread th=Thread.currentThread();                              //~vj01I~
	int threadid=xnpsub.uatoi(th.getName());                       //~vj01R~
	if (threadid==IDC_ANSWER)                                      //~vj01I~
		AnsThread();                                               //~vj01I~
	else if(threadid==IDC_MAKE)                                    //~vj01I~
		MakeThread();                                              //~vj01I~
    }                                                              //~v@@@I~
    catch(Exception e){                                            //~v@@@I~
    	estr=e.toString();
    	System.out.println("run exception "+estr);//~v@@@I~
    }                                                               //~vj01I~//~v@@@R~
}                                                                  //~vj01I~
}//class Board                                                     //~v004I~
