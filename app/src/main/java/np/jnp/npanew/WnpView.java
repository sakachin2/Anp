//CID://+va6aR~:          update#=     35                          //~va6aR~
//*********************************************************************//~va30I~
//va6a 230310 show all memo by long press                          //~va6aI~
//va64 230302 implement stepback                                   //~va64I~
//va60 230228 add function reset and stepback                      //~va60I~
//va59 221104 Num key is not shown on emulator(by kjeyboard hidden on manifest)//~va59I~
//va52 221103 BTMJ-1aj1 androd11(api30) deprecated at api30;Handler default constructor(requires parameter)//~va52I~
//va44:200525 display overflow when scrHeigh=1024                  //~va44I~
//va30:120717 (NPA21 fontsize depending screen size)               //~va30I~
//*********************************************************************//~va30I~
package np.jnp.npanew;                                                    //~0915I~//~va30R~//~va44R~

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetrics;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.os.Looper;                                          //~1aj1I~//~va52I~

import np.jnp.npanew.R;
import np.jnp.npanew.utils.Dump;                                   //~va44R~

//CID://+va23R~:                                                   //~va23R~
//*****************************************************************//~v012I~
//va23:051221 langauge ctl(English and japanese)                   //~va23I~
//va17:051113 reverse by rbutton on num button                     //~va17I~
//va01:051013 5*5 support                                          //~va01I~
//v012:051004 dosenot write answer when in solving                 //~v012I~
//*****************************************************************//~v012I~


public class WnpView extends View{                                                 //~5921I~//~0915R~
                                                                 //~5921I~
//private static final boolean TRUE=true;                            //~5924I~
private static final boolean FALSE=false;                          //~5924I~
public static  Board aBoard=null;                                  //~5922I~
//    CWnpmfcDoc* GetDocument();                                   //~5921I~
private   CPattern pPattern;                                      //~5921R~
public    ButtonDlg  pButtonDlg;                                  //~5921I~//~v@@@R~
private   boolean        BtnStatus;
private   int            ElapsedTime;                              //~5923I~
private   NppView pCP;                                          //~5927R~//~0915R~
public  Wnp pwnp=null;                                            //~5925R~//~0915R~//~v@@@R~
public  Handler wnpviewcallback;                                   //~v@@@I~
//private  int Soldnum=-1,num;                                     //~v010R~
//public   CDocument pDoc;                                           //~5929R~//~0915R~
//private TextView LabelFilename,LabelStatus1,LabelStatus2,LabelLevel;//~0915I~//~v@@@R~
public  static Context context=null;                                    //~v@@@R~
private static Canvas  pCanvas;                                           //~v@@@I~
private Bitmap bgimage;                                            //~v@@@I~
private Rect bgimagerect;                                          //~v@@@I~
private Rect viewrect;  
public  static Resources contextR;                                 //~v@@@R~
public  static int   Sconfig_keyboard;                             //~0B16I~//~v@@@M~
private Paint pDCpaintSign,pDCpaintFilename,pDCpaintLevel;                                        //~v@@@I~
private String strSign;                                            //~v@@@I~
private int strSignYbase,strSignWidth,strFilenameYbase;                //~v@@@I~
private int mmElapsed=0;                                           //~v@@@I~
private String strElapsed=null;                                    //~v@@@I~
private int oldHH,oldWW;                                           //~va44I~
/*===================================================================//~9C25I~
===================================================================*///~9C25I~
//                                                                 //~5921R~
/////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////
public WnpView(Context context ,NppView cp,Wnp Pwnp)                              //~5925R~//~0915R~
{
    /*                                                             //~9C25I~
    -----------------------------------------------------------    //~9C25I~
    -----------------------------------------------------------*/  //~9C25I~
    super(context);                                                //~v@@@I~
    WnpView.context=context;
	pCP=cp;                                                        //~5927R~
    pwnp=Pwnp; 
//  wnpviewcallback=new Handler();                                 //~v@@@I~//~va52R~
    wnpviewcallback=new Handler(Looper.getMainLooper());	//on MainThread//~va52I~
    contextR=context.getResources();                                                               //~v@@@I~
//  Sconfig_keyboard=contextR.getConfiguration().keyboard;         //~v@@@I~//~va59R~
//  if (Dump.Y) Dump.println("WnpView.constructor Sconfig_keyboard="+Sconfig_keyboard);//~va52I~//~va59R~
//*background image                                                //~v@@@I~
    bgimage=BitmapFactory.decodeResource(contextR,R.drawable.hanami);       //~v@@@I~
    bgimagerect=new Rect(0,0,bgimage.getWidth(),bgimage.getHeight());//~v@@@I~
//*filename/level                                                //~v@@@I~
	pDCpaintFilename=new Paint(Paint.ANTI_ALIAS_FLAG);	//text     //~v@@@R~
	pDCpaintFilename.setColor(Board.COL_TXT_FILENAME);             //~v@@@R~
                                                                   //~v@@@I~
	pDCpaintLevel   =new Paint();                                  //~v@@@I~
	pDCpaintLevel.setColor(Board.COL_TXT_LEVEL);                   //~v@@@R~
//*sign                                                            //~v@@@M~
	pDCpaintSign=new Paint(Paint.ANTI_ALIAS_FLAG);	//text         //~v@@@R~
	pDCpaintSign.setColor(Board.COL_TXT_SIGN);                     //~v@@@M~
//                                                                 //~v@@@R~
    Dialog dialogMain=new Dialog(context);                         //~v@@@R~
    dialogMain.setContentView(R.layout.wnpmain);//portrait //~5925R~//~v@@@R~
//  LabelFilename=(TextView)dialogMain.findViewById(R.id.LabelFilename);//~0915I~//~v@@@R~
//  LabelLevel=(TextView)dialogMain.findViewById(R.id.LabelLevel);    //~0915I~//~v@@@R~
//    LabelStatus1=(TextView)dialogMain.findViewById(R.id.LabelStatus1);     //~0915I~//~v@@@R~
//    LabelStatus2=(TextView)dialogMain.findViewById(R.id.LabelStatus2);     //~0915I~//~v@@@R~
	orientationChanged(true);                                       //~v@@@I~
    aBoard=new Board(this);                                        //~5922R~
    ElapsedTime=0;                                                 //~5923I~
    aBoard.SetNum(1,this);                                         //~5923R~
//    pDoc=new CDocument();                                           //~5926I~//~0915R~
//    pDoc.OnNewDocument();                                          //~5926I~//~0915R~
	OnCreate();                                                    //~5927I~
	OnUpdate(true);	//craete board from pattern                    //~v012R~
}

//public void ~CWnpmfcView()                                       //~5921R~
//{                                                                //~5921R~
//}                                                                //~5921R~
///////////////////////////////////////////////////////////////////////////////~v@@@I~
public void orientationChanged(boolean Pswinitdlg)                      //~v@@@I~
{                                                                  //~v@@@I~
	resize(Pswinitdlg);                                            //~va44I~
}                                                                  //~va44I~
private void resize(boolean Pswinitdlg)                            //~va44I~
{                                                                  //~va44I~
//*filename/level                                                  //~v@@@I~
	pDCpaintFilename.setTextSize(Wnp.NAME_TEXTSZ);                 //~v@@@M~
//  strFilenameYbase=Wnp.L_FILENAME_Y+Wnp.NAME_LINEH-(int)Math.ceil(pDCpaintFilename.getFontMetrics().descent);//~v@@@M~//~va44R~
    strFilenameYbase=Wnp.L_FILENAME_Y+ButtonDlg.getTextBaseLine(pDCpaintFilename,Wnp.NAME_HEIGHT);//~va44I~
	pDCpaintLevel.setTextSize(Wnp.NAME_TEXTSZ);                    //~v@@@M~
//*sign                                                            //~v@@@I~
	pDCpaintSign.setTextSize(Wnp.SIGN_HEIGHT);                     //~v@@@M~
    strSign=context.getText(R.string.MySign).toString();           //~v@@@M~
    strSignWidth=(int)(pDCpaintSign.measureText(strSign,0,strSign.length())+0.99);//~v@@@M~
//  strSignYbase=Wnp.FRAME_H-(int)Math.ceil(pDCpaintSign.getFontMetrics().descent);//~v@@@M~//~va44R~
    strSignYbase=Wnp.L_SIGN_Y+ButtonDlg.getTextBaseLine(pDCpaintSign,Wnp.SIGN_HEIGHT);//~va44R~
    if (!Pswinitdlg)                                               //~v@@@I~
    	aBoard.initsize(0/*not crteate*/);	//                     //~v@@@I~
	if (Dump.Y) Dump.println("WnpView.resize FILENAME_Y="+Wnp.L_FILENAME_Y+",filenameYBase="+strFilenameYbase+",SIGN_Y="+Wnp.L_SIGN_Y+",signBase="+strSignYbase);//~va44R~
                                                                   //~va44I~
}//orientationChanged                                              //~v@@@I~
/////////////////////////////////////////////////////////////////////////////

public void OnDraw(Canvas g)                                     //~5926R~
{
    int mm,ss;                                                     //~v@@@I~
	int scrWidth,scrHeight;                                        //~v@@@I~
//*********************                                            //~v@@@I~
	pCanvas=g;                                                     //~v@@@I~
    if (Dump.Y) Dump.println("WnpView.onDraw ww="+getWidth()+",hh="+getHeight());//~va30I~
	scrWidth=Wnp.FRAME_W;                                          //~v@@@R~
	scrHeight=Wnp.FRAME_H;                                         //~v@@@R~
    if (scrHeight!=oldHH || scrWidth!=oldWW)                       //~va44I~
    {                                                              //~va44I~
    	oldHH=scrHeight; oldWW=scrWidth;                           //~va44I~
		resize(false);                                             //~va44I~
    }                                                              //~va44I~
    viewrect=new Rect(0,0,scrWidth,scrHeight);                   //~v@@@R~
	g.drawBitmap(bgimage,bgimagerect/*src*/,viewrect/*tgt*/,null);//~v@@@I~
	strElapsed=null;                                               //~v@@@I~
    if (ElapsedTime!=0)                                               //~5923I~//~v@@@I~
    {                                                              //~5923I~//~v@@@I~
    	ss=ElapsedTime%60;                                     //~5923I~//~v@@@I~
    	mm=ElapsedTime/60;                                     //~5923I~//~v@@@I~
        if (ss!=0)                                                    //~5923I~//~v@@@I~
            mm++;                                                  //~5923I~//~v@@@I~
		if (mm!=mmElapsed)                                         //~v@@@I~
        {                                                          //~v@@@I~
        	mmElapsed=mm;                                          //~v@@@I~
			strElapsed=" "+mm+context.getText(R.string.MinElapsed).toString();//~v@@@R~
        }                                                          //~v@@@I~
	}                                                              //~5923I~//~v@@@I~
    ShowMovement();                                                //~0101R~
    aBoard.Show(g);                                                //~5926R~
                                                                   //~0102I~
    pButtonDlg.drawNumBtn(g);	//draw num button                  //~v@@@I~
    DrawPatternName(g);                                      //~0102I~
    pButtonDlg.OnDraw(g);                                          //~v@@@R~
}

/////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////

private int OnCreate()                //~5921R~
{
                                         //~9C31M~
    OpenBtnDlg();                                                   // <---//~9C31M~//~0915R~//~v@@@R~

	return 0;
}


                                                                   //~9C28I~
private void EnableNumButton(boolean enable)                       //~5921R~
{                                                                  //~0101I~
	if (BtnStatus==enable)                                         //~0102I~
    	return;                                                    //~0102I~
	BtnStatus=enable;                                              //~0102I~
//    pCP.BtnNum0.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum1.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum2.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum3.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum4.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum5.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum6.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum7.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum8.setEnabled(enable);                                //~5927R~//~0915R~
//    pCP.BtnNum9.setEnabled(enable);                                //~5927R~//~0915R~
//    if (Wnp.BOARDTYPE>=Wnp.BOARDTYPE4)                             //~va01I~//~0915R~
//    {                                                              //~va01I~//~0915R~
//        pCP.BtnNum10.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum11.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum12.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum13.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum14.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum15.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum16.setEnabled(enable);                           //~va01I~//~0915R~
//    }                                                              //~va01I~//~0915R~
//    if (Wnp.BOARDTYPE>=Wnp.BOARDTYPE5)                             //~va01I~//~0915R~
//    {                                                              //~va01I~//~0915R~
//        pCP.BtnNum17.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum18.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum19.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum20.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum21.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum22.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum23.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum24.setEnabled(enable);                           //~va01I~//~0915R~
//        pCP.BtnNum25.setEnabled(enable);                           //~va01I~//~0915R~
//    }                                                              //~va01I~//~0915R~
}                                                                  //~0101I~
                                                                   //~0101I~
private void ShowMovement()                                        //~5921R~
{
    /*                                                             //~0110M~
    ----------------------------------------------------           //~0110M~
    ----------------------------------------------------*/         //~0110M~
    String s;                                                      //~5921R~
    if ((aBoard.Flags & Board.F_THREAD)!=0)	//in waiting make thread       //~0110I~
    {                                                              //~0110I~
        long ctime=System.currentTimeMillis();                        //~0110I~
        long span=ctime-aBoard.StartTime;                     //~0110I~
     if (span>1000)                                                //~v@@@I~
     {                                                             //~v@@@I~
//    if (Wnp.Sjlang)                                              //~va23I~//~0915R~//~v@@@R~
//      s=span/1000+" 秒経過";              //~0110I~              //~v@@@R~//~va30R~
//    else                                                         //~va23I~//~v@@@R~
//      s=span/1000+" sec elapsed";                                //~va23I~//~v@@@R~
        s=span/1000+context.getText(R.string.HdrSecElapsed).toString();//~v@@@I~
      }                                                            //~v@@@I~
      else                                                         //~v@@@I~
      	s=null;                                                    //~v@@@R~
	}                                                              //~0110I~
    else                                                           //~0110I~
    	if (((aBoard.Flags & Board.F_MODEMAKE)!=0) && aBoard.GetMode()==Board.MODE_ENDQDATA)//~0129R~
//         if (Wnp.Sjlang)                                           //~va23I~//~0915R~//~v@@@R~
//            s="問題 "+pPattern.Seed;                   //~0129R~ //~v@@@R~//~va30R~
//         else                                                      //~va23I~//~v@@@R~
//            s="Prob "+pPattern.Seed;                               //~va23I~//~v@@@R~
           s=context.getText(R.string.HdrSecElapsed).toString()+pPattern.Seed;//~v@@@I~
        else                                                       //~0129I~
        {                                                          //~v@@@I~
//         if (Wnp.Sjlang)                                           //~va23I~//~0915R~//~v@@@R~
//            s="残り"+(Wnp.PEG_MAX-aBoard.GetPath())+"手";      //~0129R~//~0915R~//~v@@@R~//~va30R~
//         else                                                      //~va23I~//~v@@@R~
//            s=(Wnp.PEG_MAX-aBoard.GetPath())+"remains";            //~va23R~//~0915R~//~v@@@R~
           s=(Wnp.PEG_MAX-aBoard.GetPath())+context.getText(R.string.HdrRemains).toString();//~v@@@I~
           if (strElapsed!=null)                                   //~v@@@I~
           		s+=strElapsed;                                     //~v@@@I~
        }                                                          //~v@@@I~

  if (s!=null)                                                     //~v@@@I~
    LabelStatus1.setText(s);                                     //~0101R~//~0915R~
                                                                   //~0101I~
    String smsg=aBoard.GetMsg();                                  //~0101I~
    LabelStatus2.setText(smsg);                                  //~0101I~//~0915R~
                                                                 //~0110I~
}



/*                                                                 //~9C25I~
=======================================================================//~9C25I~
    ＜ CWnpmfcView::OpenBtnDlg ＞                               <--- 関数の追加//~9C25R~//~va30R~
=======================================================================//~9C25I~
=======================================================================*///~9C25I~
private void  OpenBtnDlg()                                         //~5921R~//~0915R~//~v@@@R~
{                                                                  //~9C25I~//~0915R~//~v@@@R~
    pButtonDlg = new ButtonDlg(this,aBoard );                   //~9C25I~//~0915R~//~v@@@R~
////  pButtonDlg.Create();                                          //~9C25I~//~0915R~
////  pButtonDlg.ShowWindow( SW_SHOW );                             //~9C25I~//~0915R~
//    Dimension rp;                                                //~0915R~
//    Point pt=pwnp.getLocation();//~9C31I~                        //~0915R~
//    rp=pwnp.getSize();  //get window pos of parent                 //~5927R~//~0915R~
//    pt.x+=rp.width;                                                //~5927R~//~0915R~
//    pt.y+=Wnp.TOOLBAR_HEIGHT;                                    //~0915R~
//    pButtonDlg.setLocation(pt);                                  //~0915R~
//    pButtonDlg.show();                                           //~0915R~
//                                                                 //~0915R~
}                                                                  //~9C25I~//~0915R~//~v@@@R~
/*                                                                 //~9C25I~
=======================================================================//~9C25I~
    ＜ CWnpmfcVieww::CloseBtnDlg ＞                              <--- 関数の追加//~9C25R~//~va30R~
=======================================================================//~9C25I~
=======================================================================*///~9C25I~
//private void    CloseBtnDlg()                                      //~5921R~
//{                                                                  //~9C25I~
//    if (pButtonDlg==null)                                               //~9C31I~
//    	return;                                                    //~9C31I~
//    pButtonDlg.DestroyWindow();                                   //~9C25I~
//    pButtonDlg = null;                                             //~9C25I~
//}                                                                  //~9C25I~
/*                                                                 //~9C25I~
=======================================================================//~9C25I~
=======================================================================*///~9C25I~
private void OnNum0()                                              //~5921R~
{                                                                  //~9C25I~
    aBoard.SetNum(0,this);           //force                       //~5923I~
//    SetPegCursor(0);                                               //~9C25I~
//    SetFocus();                                                    //~5923I~
}                                                                  //~9C25I~
//private void OnNumx(int Pbtnno)                                    //~v012I~
//{                                                                  //~v012M~
//    SetPegCursor(Pbtnno);                                          //~v012I~
//}                                                                  //~v012I~

//=======================================================================*///~9C25I~
//private void SetPegCursor(int Pnum)                                //~5924R~//~0915R~
//{                                                                  //~9C25I~//~0915R~
//    int num=Pnum;                                                  //~v010R~//~0915R~
//    if ((aBoard.Flags & Board.F_MODEMAKE)!=0)                            //~5924R~//~0915R~
//        num=0;                                                     //~5923I~//~0915R~
////  if (num==Soldnum)                                              //~v010R~//~0915R~
////      return;                                                    //~v010R~//~0915R~
//    if (Pnum!=0)                                                      //~5923I~//~0915R~
//        aBoard.SetNum(Pnum,null);                                     //~5923I~//~0915R~
////  pwnp.BGpaneFG.setCursor(pwnp.cursortbl[num]);                  //~va23R~//~0915R~
//    pCP.setCursor(pwnp.cursortbl[num]);                            //~va23R~//~0915R~
//}                                                                  //~9C25I~//~0915R~
//=======================================================================*///~9C25I~
private void OnAnswer()                                            //~5921R~
{                                                                  //~0101I~
	ElapsedTime=0;                                                 //~5923I~
	aBoard.OnAnswer(1/*make end*/);                                             //~0101I~//~v@@@R~
    BtnProc();                                                     //~0101I~
}                                                                  //~0101I~
public void OnMake()                                               //~5925R~
{	                                                               //~0101I~
	ElapsedTime=0;                                                 //~5923I~
	aBoard.OnMake();                                               //~0101I~
    BtnProc();                                                     //~0101I~
}                                                                  //~0101I~
//=======================================================================*///~va60I~
//=from Dlg, resetCompleted                                        //~va60I~
//=======================================================================*///~va60I~
public void OnResetCompleted()                                     //~va60R~
{                                                                  //~va60I~
//	ElapsedTime=0;                                                 //~va60I~
    if (Dump.Y) Dump.println("WnpView.OnResetCompleted");          //~va64I~
	aBoard.OnResetCompleted();                                     //~va60R~
    BtnProc();                                                     //~va60I~
}                                                                  //~va60I~
private void OnRchk()                                              //~5921R~
{                                                                  //~0101I~
	aBoard.OnRchk(0);                                              //~0122R~
    BtnProc();                                                     //~0101I~
}                                                                  //~0101I~
private void OnNext()                                              //~5921R~
{                                                                  //~0101I~
	aBoard.OnNext(0);                                              //~0122R~
    BtnProc();                                                     //~0101I~
}                                                                  //~0101I~
private void OnGo(){                                               //~5921R~
	aBoard.OnGo(0);                                                //~0122R~
    BtnProc();                                                     //~0101I~
//    pDoc.SetModifiedFlag(true);                                    //~v012I~
}                                                                  //~0101I~
public void OnEndThread(){                                        //~5921R~
int rc;                                                            //~5923I~
	rc=aBoard.OnEndThread();                                       //~5923R~
    if (rc!=0)			//make normal end                              //~5923I~
		OnAnswer();                                                //~5923I~
    BtnProc();                                                     //~0109I~
}                                                                  //~0109I~
public void OnTimer(int Msgid) {                              //~5924R~//~0915R~//~v@@@R~
    ElapsedTime=aBoard.OnTimer(Msgid);                                  //~5924R~//~0915R~//~v@@@R~
    BtnProc();                                                     //~0109I~//~0915R~//~v@@@R~
}                                                                  //~0109I~//~0915R~//~v@@@R~
private void OnSetend()                                            //~5921R~
{                                                                  //~0101I~
    ElapsedTime=0;                                                 //~5923I~
	aBoard.OnSetend(0);                                            //~0130R~
    BtnProc();                                                     //~0101I~
}                                                                  //~0101I~
private void OnBack()                                              //~va64I~
{                                                                  //~va64I~
	aBoard.OnBack();                                               //~va64I~
    BtnProc();                                                     //~va64I~
}                                                                  //~va64I~
private void updateMemo()                                          //~va64I~
{                                                                  //~va64I~
	aBoard.updateMemo();                                           //~va64I~
    Invalidate( FALSE );                                           //~va64I~
}                                                                  //~va64I~
private void OnSort()                                              //~5921R~
{                                                                  //~0130I~
//    if (aBoard.OnSort())    //success                              //~5923I~//~0915R~
//        GetDocument().SetModifiedFlag(true);//avoid popup           //~5923I~//~0915R~
    BtnProc();                                                     //~0130I~
}                                                                  //~0130I~
private void OnStop()                                              //~5921R~
{                                                                  //~0110I~
	aBoard.OnStop();                                               //~0110I~
    BtnProc();                                                     //~0110I~
}                                                                  //~0110I~
private void OnClear()                                             //~5921R~
{                                                                  //~9C25I~
	ElapsedTime=0;                                                 //~5923I~
    aBoard.OnClear();                                                //~9C31I~
    BtnProc();                                                     //~0101I~
 //   pDoc.SetModifiedFlag(true);                                    //~v012I~
}                                                                  //~9C25I~
private void OnReset()                                             //~va60I~
{                                                                  //~va60I~
    if (Dump.Y) Dump.println("WnpView.OnReset");                   //~va64I~
//  ElapsedTime=0;                                                 //~va60I~
    aBoard.OnReset();                                              //~va60I~
    BtnProc();                                                     //~va60I~
}                                                                  //~va60I~
private void OnRestore()                                           //~5921R~
{                                                                  //~0123I~
//    CDocument pdoc=GetDocument();                                  //~5924R~//~0915R~
//    pPattern->RestoreOrgData();         //restore original data  //~0129R~//~v@@@R~
//    aBoard.CreateBoard(pPattern);                                //~0129R~//~v@@@R~//~va64R~
      aBoard.CreateBoardOnRestore(pPattern);                       //~va64I~
//    pdoc.SetModifiedFlag(FALSE);//avoid popup             //~5924R~//~v@@@R~
//    String str=pdoc.GetPathName();	//current filename             //~5924R~//~v@@@R~
//  pdoc.SetPathName("c:\\xnpdmy");	//avoid bypass by the reason alraedy open//~v012R~
//    pdoc.OnOpenDocument(this,str,true); //true:restore             //~v012R~//~0915R~
    BtnProc();                                                     //~0123I~
}                                                                  //~0123I~
//=======================================================================*///~0101M~
private void BtnProc()                                             //~5921R~
{                                                                  //~0101M~
    pButtonDlg.SetDlgBtn();		//set Clear btn status             //~5924R~//~v@@@R~
	boolean numbtnon=aBoard.ShowNumBtn();                              //~0101R~
//System.out.println("BtnProc ShowNumBtn rc="+(numbtnon?1:0));     //~va01R~
	EnableNumButton(numbtnon);                                     //~0110R~
//    if (!numbtnon)			//button disable                       //~0101I~
//		SetPegCursor(0);                                            //~0101I~
 //   else                                                           //~5923I~
//		SetPegCursor(aBoard.GetNum());                             //~5923I~
    Invalidate( FALSE );                                           //~0101M~
}	                                                               //~0101M~
//=======================================================================*///~5927I~
//=return true if on board                                         //~v@@@I~
//=======================================================================*///~v@@@I~
public boolean OnLButtonDown(int nFlags, Point point)                 //~5927R~//~v@@@R~
{                                                                  //~9C28I~
    int rc,num;                                                    //~v@@@R~
//************************                                         //~v@@@I~
    if (Dump.Y) Dump.println("WnpView.OnLButtonDown nFlags="+nFlags);//~va60I~
    if (nFlags==1)  //ACTION_DOWN                                  //~v@@@R~
        num=-1;     //move cursor                                  //~v@@@I~
    else            //ACTION_UP                                    //~v@@@R~
        num=-2;     //point chk only                               //~v@@@I~
	rc=aBoard.DropDown(point,num);                                 //~v@@@R~
   	pButtonDlg.SetDlgBtn();		//set Clear btn status         //~5924R~//~v@@@R~
//    if (GetDocument().pPattern.GetModified()!=0) //chk data modified  //~5924R~//~0915R~
//        GetDocument().SetModifiedFlag(true);   //notify doc updated    //~5924R~//~0915R~
                                                                   //~0123I~
    if (rc<0)                         //~9C30I~                    //~v@@@R~
    {                                                              //~v@@@I~
    	rc=pButtonDlg.ButtonPush(point,num);                       //~v@@@I~
        if (rc<0)                                                  //~v@@@I~
    		return false;	//out of board                         //~v@@@R~
                                                                   //~v@@@I~
    }                                                              //~v@@@I~
    if (rc==1)  //ACTION_DONE                                      //~v@@@R~
    	Invalidate( FALSE );                                           //~9C31I~//~v@@@R~
    return true;                                                   //~v@@@I~
}                                                                  //~9C28I~
//=======================================================================*///~va6aI~
//=at ACTION_UP                                                    //~va6aI~
//=======================================================================*///~va6aI~
public void chkLongPress(Point Ppoint)                             //~va6aI~
{                                                                  //~va6aI~
    int rc;                                                        //+va6aR~
//************************                                         //~va6aI~
    if (Dump.Y) Dump.println("WnpView.chkLongPress point="+Ppoint);//~va6aI~
	rc=aBoard.chkLongPress(Ppoint);                                //~va6aI~
    if (rc==1)  //processed                                        //+va6aR~
    	Invalidate( FALSE );                                       //~va6aI~
}                                                                  //~va6aI~
//=======================================================================*///~5923I~
public void OnRButtonDown(int nFlags, Point point)                 //~5927R~
{                                                                  //~5923I~
	boolean rc;                                                       //~5923I~
//**********************                                           //~5923I~
	rc=aBoard.SetMemo(point,-1);	//by mouse                     //~5923I~
                          //~5923I~
    if (rc)		//need invalidate                                  //~5923I~
    	Invalidate( FALSE );                                       //~5923I~
}                                                                  //~5923I~
//=======================================================================*///~v010I~
public void OnMouseWheel(int Pdest)                                //~v010I~
{                                                                  //~v010I~
	int btnnum=aBoard.GetNum();                                    //~v010I~
    if (Pdest>0)   //forward                                       //~v010I~
        btnnum++;                                                  //~v010I~
    else                                                           //~v010I~
        btnnum--;                                                  //~v010I~
    if (btnnum<0)                                                  //~v010I~
        btnnum=Wnp.MAP_SIZE;                                       //~v012R~//~0915R~
    else                                                           //~v010I~
        if (btnnum==Wnp.MAP_SIZE+1)                                //~va01R~//~0915R~
        	btnnum=0;                                              //~v010I~
    if (btnnum==0)                                                 //~v010I~
		OnNum0();                                                  //~v010I~
//    else                                                           //~v010I~
//	    SetPegCursor(btnnum);                                      //~v010I~
}                                                                  //~v010I~
                                                                 //~9C30I~
//private boolean OnSetCursor(int nHitTest,int message)  //~5924R~
//{                                                                  //~9C30I~
//	SetPegCursor(aBoard.GetNum());                                 //~9C30I~
//	return TRUE;                                                   //~9C30I~
//}                                                                  //~9C30I~
                                                                   //~9C30I~
public  void OnUpdate(boolean Prestoresw)                          //~v012R~
{
//    pPattern=GetDocument().pPattern;                               //~5924R~//~0915R~
	pPattern=new CPattern();                                       //~0915I~
		aBoard.CreateBoard(pPattern);                              //~v012R~
    	BtnProc();                                                 //~v012R~
    if (Prestoresw)                                                //~v012R~
    	aBoard.ActSeed=pPattern.Seed;	//from read file           //~v012I~
}
private void DrawPatternName(Canvas pDC)                             //~5921R~
{                                                                  //~0102I~
	String pno,str;
	int x,y;
	//****************************
//  if (aBoard.ActSeed!=0)                                            //~5923I~//~v@@@R~
  	if (aBoard.pPat.strQuestionNo!=null)                           //~v@@@I~
//  	pno=" (#"+aBoard.ActSeed+")";                      //~5923I~//~v@@@R~
//		pno="#"+aBoard.ActSeed;                                    //~v@@@R~
  		pno="#"+aBoard.pPat.strQuestionNo;                         //~v@@@I~
    else                                                           //~5923I~
    	pno="";                                                 //~5923I~
//    if (ElapsedTime!=0)                                               //~5923I~//~v@@@R~
//    {                                                              //~5923I~//~v@@@R~
//        int ss=ElapsedTime%60;                                     //~5923I~//~v@@@R~
//        int mm=ElapsedTime/60;                                     //~5923I~//~v@@@R~
//        if (ss!=0)                                                    //~5923I~//~v@@@R~
//            mm++;                                                  //~5923I~//~v@@@R~
////      if (Wnp.Sjlang)                                              //~va23I~//~0915R~//~v@@@R~
////        pno=pno+" "+mm+"分経過";                   //~5923I~   //~v@@@R~//~va30R~
////      else                                                         //~va23I~//~v@@@R~
////        pno=pno+" "+mm+"min";                                      //~va23R~//~v@@@R~
//          pno=pno+" "+mm+context.getText(R.string.HdrMinutes).toString();//~v@@@R~
//    }                                                              //~5923I~//~v@@@R~
    if (aBoard.Score!=0)                                              //~5923I~//~v@@@R~
    	pno=pno+",Score:"+aBoard.Score+"/"+aBoard.ScoreMax;//~5923I~
    else                                                           //~5923I~
    	if (aBoard.ScoreMax!=0)                                       //~5923I~
    		pno=pno+",MaxScore:"+aBoard.ScoreMax;   //~5923I~
//    String str=GetDocument().pPattern.Name;                        //~v012R~//~0915R~
//    int pos=str.lastIndexOf(System.getProperty("file.separator")); //~v012R~
//    if (pos>0)                                                     //~v012I~
//    	str=str.substring(pos+1);                                  //~v012I~
//*filename                                                        //~v@@@I~
//  str="";                                                        //~v@@@R~
//  str="Name["+str+"]"+pno;                                       //~v012I~//~v@@@R~
    str="["+pno+"]";                                               //~v@@@I~
                //~5924R~
//  pDC.setColor(new Color(0,0,0));                                //~5927R~
//	pDC.SetBkMode(TRANSPARENT);                                    //~5924R~
//  pDC.drawString(str,wnp.NAME_POSX,wnp.NAME_POSY);               //~5927R~
//  LabelFilename.setText(str);                                //~5927I~//~0915R~//~v@@@R~
    x=Wnp.L_FILENAME_X;                                                //~v@@@R~
    y=strFilenameYbase;                                            //~v@@@I~
//  pDC.drawText(str,x,y,pDCpaintFilename);                        //~v@@@R~//~va30R~
    int posxfilename=x;                                            //~va30I~
    int posyfilename=y;                                            //~va30I~
    String strfilename=str;                                        //~va30I~
//*level                                                           //~v@@@I~
//  LabelLevel.setText(aBoard.sProbLevel);                         //~v@@@M~
    str=aBoard.sProbLevel;                                         //~v@@@I~
    x=Wnp.FRAME_W-(int)(pDCpaintLevel.measureText(str,0,str.length())+0.99);//~v@@@R~
    y=strFilenameYbase;                                            //~v@@@I~
//    System.out.println("draw level="+str);                       //~v@@@R~
    pDC.drawText(str,x,y,pDCpaintLevel);                           //~v@@@R~
    adjustTextsz(1/*maxline*/,pDCpaintFilename,x-posxfilename,strfilename);//~va30R~
    pDC.drawText(strfilename,posxfilename,posyfilename,pDCpaintFilename);//~va30I~
//*sign                                                            //~v@@@I~
//  pDC.setColor(Board.COL_TXT_SIGN);                              //~5927R~//~v@@@R~
//  str="          By "+wnp.MY_SIGN;                               //~5927R~
//  pDC.drawString(str,Wnp.SIGN_POSX,wnp.SIGN_POSY);               //~5927R~//~0915R~
    x=Wnp.FRAME_W-strSignWidth;                                    //~v@@@R~
    y=strSignYbase;                                                //~v@@@R~
    pDC.drawText(strSign,x,y,pDCpaintSign);                        //~v@@@I~
}                                                                  //~0102I~
//CFont GetMemoFont()                                            //~5924R~
//{                                                                  //~5923I~
//	return MemoFont;                                               //~5924R~
//}                                                                  //~5923I~
//*************************************************************************//~va30I~
public static int adjustTextsz(int Pmaxline,Paint Ppaint,int Pwidth/*1 linewidth*/,String Pstr)//~va30R~
{                                                                  //~va30I~
	int strlen,sz;                                                    //~va30I~
	float flen,fmultilinewidth;                                    //~va30R~
//****************                                                 //~va30I~
	sz=(int)Ppaint.getTextSize();                                       //~va30I~
    strlen=Pstr.length();                                          //~va30I~
	fmultilinewidth=(float)Pwidth*Pmaxline*0.9f;	//redundancy for not monospace font//~va30I~
	while(sz>1)                                                     //~1719I~//~va30I~
    {                                                          //~1719I~//~va30I~
    	flen=Ppaint.measureText(Pstr,0,strlen);                    //~va30I~
        if (flen<(float)Pwidth)                                    //~va30I~
        	break;                                                 //~va30I~
        if (Pmaxline>1)	//multiline                                //~va30I~
        {                                                          //~va30I~
	        if (flen<fmultilinewidth)                              //~va30I~
    	    	break;                                             //~va30I~
        }                                                          //~va30I~
        sz--;                                                      //~va30I~
	    Ppaint.setTextSize(sz);                           //~1719I~//~1802R~//~va30I~
    }                                                              //~va30I~
    return sz;                                                     //~va30I~
}//adjustTextsz                                            //~va30I~
//*************************************************************************//~va30I~
public boolean OnKeyDown(int nChar,int nRepCnt,int nFlags)            //~5929R~//~v@@@R~
{
                                               //~0102M~
    int rc;                                                    //~5923I~
    if (aBoard.GetKbFocus()<0)	//no forcus                        //~5923R~
    {                                                              //~0102M~
//      if (Wnp.Sjlang)                                              //~va23I~//~0915R~//~v@@@R~
//        aBoard.SetTempMsg("マウスでキー入力位置を指定してください");//~0102M~//~v@@@R~//~va30R~
//      else                                                         //~va23I~//~v@@@R~
//        aBoard.SetTempMsg("specify keyboard focus by mouse");      //~va23I~//~v@@@R~
        aBoard.SetTempMsg("specify keyboard focus by mouse");      //~v@@@I~
	    Invalidate( FALSE );                                       //~0102M~
    	return false;                                                    //~0102M~//~v@@@R~
    }                                                              //~0102M~
//System.out.println("keydown char="+nChar+",flag="+nFlags);       //~va17R~
	rc=aBoard.KeyProc(nChar);                                      //~5923I~
    if (rc==-1)		//no samenum status changed                    //~5923I~
        return false;                                                    //~5923I~//~v@@@R~
	if (rc!=0)	//reset dlg btn status                                 //~5923I~//~v@@@R~
    	pButtonDlg.SetDlgBtn();		//set Clear btn status         //~5924R~//~v@@@R~
    Invalidate( FALSE );                                           //~0102M~
    return true;                                                   //~v@@@I~
}
//=======================================================================*///~5923I~
public boolean OnKeyUp(int nChar, int nRepCnt, int nFlags)            //~5929R~//~v@@@R~
{                                                                  //~5923I~
//System.out.println("keyup char="+nChar+",flag="+nFlags);         //~va17R~
    if (aBoard.KeyUpProc(nChar))                                   //~5923I~
    	Invalidate(FALSE);                                         //~5923I~
    return false;                                                  //~v@@@I~
}                                                                  //~5923I~
//=======================================================================*///~5923I~
//void OnParentNotify(int Pid,LPARAM pParm)                         //~5924R~
//{                                                                  //~5923I~
//	if ((Pid & 0xffff)!=WM_RBUTTONDOWN)                            //~5923I~
//		return;                                                    //~5923I~
//	NumRButton(Point(pParm));                                     //~5923I~
//	return;                                                        //~5923I~
//}                                                                  //~5923I~
//=======================================================================*///~5923I~
//=rbutton on Num button-->set same num                            //~5923I~
//=rc:1:redraw required 0:out of interest                          //~5923I~
//=======================================================================*///~5923I~
void NumRButton(int Pnum)                                          //~va17R~
{                                                                  //~va17R~
    aBoard.SetSameNum(-Pnum);     //samenum display                //~va17R~
    Invalidate( FALSE );                                           //~va17R~
    return;     //redraw                                           //~va17R~
}                                                                  //~va17R~

//public void actionPerformed(ActionEvent e) {                     //~0915R~
//    if (e.getSource() instanceof Timer)                            //~5924I~//~0915R~
//    {                                                              //~5924I~//~0915R~
//        OnTimer(e);                                                //~5924I~//~0915R~
//    }                                                              //~5924I~//~0915R~
//    else                                                           //~5924I~//~0915R~
//    {                                                              //~5924I~//~0915R~
//        String cmd=e.getActionCommand();                           //~5924R~//~0915R~
//        if (cmd.substring(0,3).equals("Num"))                      //~v012I~//~0915R~
//        {                                                          //~v012I~//~0915R~
//            int btnno=Integer.parseInt(cmd.substring(3));          //~v012I~//~0915R~
//            if (btnno==0)                                          //~v012I~//~0915R~
//                OnNum0();                                          //~v012I~//~0915R~
//            else                                                   //~v012I~//~0915R~
//                OnNumx(btnno);                                     //~v012I~//~0915R~
//        }                                                          //~v012I~//~0915R~
//    }                                                              //~v012M~//~0915R~
////  pwnp.requestFocus();    //get kbd forcus to get kbd event      //~va17R~//~0915R~
//}                                                                //~0915R~
void Invalidate(boolean Opt){                                                 //~5924I~
	pCP.invalidate();//repaint();                                                 //~5927R~
}                                                                  //~5924I~
//CDocument GetDocument(){                                    //~5924I~//~0915R~
//    return pDoc;                                                   //~5924I~//~0915R~
//}                                                                  //~5924I~//~0915R~
public void Dlgreq(int msgid){                                     //~5925R~
//	int msgid=Ppostmsgid.intValue();                               //~5925R~
  try                                                              //~v@@@I~
  {                                                                //~v@@@I~
	switch(msgid){                                                 //~5925R~
    case Board.IDC_GO:                                                   //~5925I~
    	OnGo();                                                    //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_STOP:                                                 //~5925I~
    	OnStop();                                                  //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_RCHK:                                                 //~5925I~
    	OnRchk();                                                  //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_NEXT:                                                 //~5925I~
    	OnNext();                                                  //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_CLEAR:                                                //~5925I~
    	OnClear();                                                 //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_RESET:                                          //~va60I~
    	OnReset();                                                 //~va60I~
    	break;                                                     //~va60I~
	case Board.IDC_RESTORE:                                              //~5925I~
    	OnRestore();                                               //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_SETEND:                                               //~5925I~
    	OnSetend();                                                //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_SORT:                                                 //~5925I~
    	OnSort();                                                  //~5925I~
    	break;                                                     //~5925I~
	case Board.IDC_BACK:                                           //~va64I~
    	OnBack();                                                  //~va64I~
    	break;                                                     //~va64I~
	case Board.IDC_UPDATE_MEMO:                                    //~va64I~
    	updateMemo();                                              //~va64I~
    	break;                                                     //~va64I~
    }                                                              //~5925I~
  }                                                                //~v@@@I~
  catch(RuntimeException e)                                        //~v@@@I~
  {                                                                //~v@@@I~
    e.printStackTrace();                                           //~v@@@I~
	System.out.println("Dlgreq exception"+e.toString());           //~v@@@I~
  }                                                                //~v@@@I~
}//Dlgreq                                                          //~5925I~
//********************                                             //~v@@@R~
//*draw status1                                                    //~v@@@I~
//********************                                             //~v@@@I~
    private static class LabelStatus1 {                            //~v@@@I~
        public static void setText(String s)                            //~v@@@I~
        {                                                          //~v@@@I~
        	Canvas pDC=WnpView.pCanvas;                            //~v@@@I~
            int br=Color.GRAY;                                     //~v@@@I~
        	int x,y,xe,ye,ybase;                                   //~v@@@R~
        	int textsz=Wnp.STATUS_TEXTSZ;                          //~v@@@R~
            FontMetrics fm;                                        //~v@@@I~
			Paint pDCpaintB=new Paint();	//text                 //~v@@@I~
			Paint pDCpaintT=new Paint(Paint.ANTI_ALIAS_FLAG);	//text     //~v@@@I~
		//****************                                         //~v@@@I~
		    pDCpaintB.setColor(br);   //outer line                 //~v@@@I~
    		x=Wnp.L_STATUS1_X;                                         //~v@@@I~
    		y=Wnp.L_STATUS1_Y;                                         //~v@@@I~
            ye=y+Wnp.STATUS_LINEH;                                 //~v@@@R~
            xe=x+Wnp.L_WIDTH;                                      //~v@@@R~
    		pDC.drawRect(x,y,xe,ye,pDCpaintB);                     //~v@@@I~
			pDCpaintT.setTextSize(textsz);                         //~v@@@R~
//  		pDCpaintT.setTypeface(Wnp.Sfontname);                  //~v@@@R~
			pDCpaintT.setColor(Board.COL_TXT_STATUS1);             //~v@@@R~
//          fm=pDCpaintT.getFontMetrics();                         //~v@@@I~//~va44R~
//          ybase=y+Wnp.STATUS_LINEH-(int)Math.ceil(fm.descent);   //~v@@@R~//~va44R~
            ybase=y+ButtonDlg.getTextBaseLine(pDCpaintT,Wnp.STATUS_LINEH);//~va44I~
            if (Dump.Y) Dump.println("WnpView.LabelStatus1 y="+y+",ybase="+ybase);//~va44I~
        	pDC.drawText(s,x,ybase,pDCpaintT);                     //~v@@@R~
        }                                                          //~v@@@I~
    }//LabelStatus1                                                //~v@@@I~
//********************                                             //~v@@@I~
//*draw status2                                                    //~v@@@I~
//********************                                             //~v@@@I~
    private static class LabelStatus2 {                            //~v@@@I~
        public static void setText(String s)                       //~v@@@I~
        {                                                          //~v@@@I~
        	Canvas pDC=WnpView.pCanvas;                            //~v@@@I~
            int br=Color.GRAY;                                     //~v@@@I~
        	int x,y,xe,ye,ybase;                                   //~v@@@R~
        	int textsz=Wnp.STATUS_TEXTSZ,lineno;                   //~v@@@R~
            int len,lenok,swshort,pos,ii,maxlineno,maxstrsz,linewidth;//~v@@@R~
            int [][] poslen=new int[2][10];
            int stringw;                                           //~v@@@R~
            FontMetrics fm;                                        //~v@@@I~
			Paint pDCpaintB=new Paint();	//text                 //~v@@@I~
			Paint pDCpaintT=new Paint(Paint.ANTI_ALIAS_FLAG);	//text//~v@@@I~
		//****************                                         //~v@@@I~
    		x=Wnp.L_STATUS2_X;                                     //~v@@@M~
    		y=Wnp.L_STATUS2_Y;                                     //~v@@@M~
//          maxlineno=(Wnp.FRAME_H-y+Wnp.STATUS_LINEH-1)/Wnp.STATUS_LINEH;//~v@@@R~//~va30R~
            maxlineno=(Wnp.FRAME_H-y)/Wnp.STATUS_LINEH;            //~va30I~
            if (maxlineno>2)                                       //~va30I~
	            maxlineno-=2;        //num button and sign         //~va30R~
                                                                   //~va30I~
			pDCpaintT.setTextSize(textsz);                         //~v@@@M~
		    textsz=adjustTextsz(maxlineno,pDCpaintT,Wnp.L_WIDTH,s);//~va30R~
//			pDCpaintT.setTypeface(Wnp.Sfontname);                  //~v@@@M~
			pDCpaintT.setColor(Board.COL_TXT_STATUS2);             //~v@@@M~
            fm=pDCpaintT.getFontMetrics();                         //~v@@@M~
            maxstrsz=s.length();                                   //~v@@@I~
            linewidth=Wnp.L_WIDTH;                                 //~v@@@R~
//System.out.println("density"+pDC.getDensity()+",W="+pDC.getWidth()+",H="+pDC.getHeight()) ;//~v@@@R~
            for (pos=0,lineno=0;;)                                 //~v@@@R~
            {                                                      //~v@@@R~
                for (len=linewidth/textsz,lenok=0,swshort=0;;len++)//~v@@@R~
                {                                                  //~v@@@R~
                    if (pos+len>maxstrsz)                          //~v@@@R~
                        len=maxstrsz-pos;                          //~v@@@R~
                    if (len<=0)                                    //~v@@@R~
                        break;                                     //~v@@@R~
                    stringw=(int)pDCpaintT.measureText(s,pos,pos+len/*next of end addr*/);//~v@@@R~
                    if (stringw>=linewidth)                       //~v@@@R~
                    {                                              //~v@@@R~
                        swshort=1;                                 //~v@@@R~
                        break;                                     //~v@@@R~
                    }                                              //~v@@@R~
                    lenok=len;                                     //~v@@@R~
                    if (pos+len>=maxstrsz)                         //~v@@@R~
                        break;                                     //~v@@@R~
                }                                                  //~v@@@R~
                if (lineno==maxlineno)                             //~v@@@R~
                    break;                                         //~v@@@R~
                poslen[0][lineno]=pos;                             //~v@@@R~
                poslen[1][lineno]=lenok;                           //~v@@@R~
                pos+=lenok;                                        //~v@@@R~
                lineno++;                                          //~v@@@R~
                if (swshort==0)                                    //~v@@@R~
                    break;                                         //~v@@@R~
            }                                                      //~v@@@R~
		    pDCpaintB.setColor(br);   //outer line                 //~v@@@I~
            ye=y+Wnp.STATUS_LINEH*lineno;                          //~v@@@R~
            xe=x+linewidth;                                        //~v@@@R~
    		pDC.drawRect(x,y,xe,ye,pDCpaintB);                     //~v@@@I~
                                                                   //~v@@@I~
            for (pos=0,ii=0;ii<lineno;ii++)                        //~v@@@I~
            {                                                      //~v@@@I~
//          	ybase=y+Wnp.STATUS_LINEH*(ii+1)-(int)Math.ceil(fm.descent);	//baseline,ascent is minus value up to top from baseline//~v@@@R~//~va44R~
            	ybase=y+Wnp.STATUS_LINEH*ii+ButtonDlg.getTextBaseLine(pDCpaintB,Wnp.STATUS_LINEH);//~va44I~
            	if (Dump.Y) Dump.println("WnpView.LabelStatus2 y="+y+",ybase="+ybase);//~va44I~
                pos=poslen[0][ii];                                 //~v@@@I~
                len=poslen[1][ii];                                 //~v@@@I~
        		pDC.drawText(s.substring(pos,pos+len/*addr of the next of end char*/),x,ybase,pDCpaintT);//~v@@@R~
            }                                                      //~v@@@I~
        }                                                          //~v@@@I~
    }//LabelStatus2                                                //~v@@@I~
}//class View                                                      //~5921I~
