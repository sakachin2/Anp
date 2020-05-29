//CID://+v@@@R~:        update#=     13 1                          //~v@@@I~
package np.jnp.npanew;                                             //+v@@@R~

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import android.content.Context;
import android.graphics.Point;

import np.jnp.npanew.R;

//CID://+v045R~:                                                   //~v045R~
//*************************************************************    //~v0.1M~
//*XNPSUB.c   Number Place Puzzle                                  //~v0.1M~
//*************************************************************    //~v0.1M~
//v045:051113 (BUG)method6x serach candidate also before(pair member ccnt is lower)//~v045I~
//va16:051112 if return maked data when multisolution detected,it is redundant;delete redundancy option add//~va16R~
//v044:051112 method6x include method8;del method8                 //~v044I~
//v041:051112 method9(try if candidate count is max count for method8/method6x//~v041I~
//v040:051111 abc ab bc patern is more general; applyed also abc abc ab//~v039I~
//            n-candidiate place exist and and other (n-1)-place exist which cadidate is all in the first place.//~v039R~
//            method7 process that case and delte from method6x and method6//~v039R~
//            method3:only one candidate in the unit               //~v039R~
//            method4:candidate count of the place is one          //~v039R~
//            method6:2 place (ab) (ab) pattern;drop candidate (ab) from other place in the unit//~v039R~
//            method8:method6 general pattern like that (abc) (abc) (abc)//~v039R~
//            method6x:method8+general method6x like  (abcd) ((a)bcd) ((b)cda) ((c)dab)//~v039R~
//v039:051111 re-consideration;v028 is lie;abondon v028 so method7 is deleted because it is same as method3//~v039I~
//v038:051110 performance;new solution metod 4-square patern such as (abcd) (abc) (bcd) (cda) (dab);abcd should be in these 4 cell//~v038I~
//v037:051110 performance;new solution metod 3-square patern such as (abc) (ab) (bc);abc should be in these 3 cell//~v037I~
//v036:051110 performance;new solution metod 4-square 3-candidate pattern candidate (a,b,c) (b,c,d) (c,d,a) (d,a,b)//~v036I~
//v035:051110 performance;new solution metod 3-square pattern candidate (a,b) (b,c) (c,a) remains case//~v035I~
//v034:051109 performance;try new make logico use once reached answer;//~v034I~
//v033:051109 performance;for answer also use once reached answer; //~v033I~
//v032:051109 tae level depth should not be limited                //~v032I~
//va14:051108 method6 should contine search next 2 pair            //~va14I~
//v031:051108 (BUG)method6 box chk no chk place exist              //~v031I~
//v029:051108 performance:method6; chk no bit to reset before call m6sub//~v029I~
//va14:051108 (BUG of va11)method6 position err for m6sub          //~v014I~
//v028:051107 (BUG)m3sub:logic valid for only box                  //~v028I~
//va12:051107 for performance;use prev result for rchk             //~va12I~
//v027:051107 (BUG)dropcan rc err(exaust cpu)                      //~v027I~
//va11:051107 for performance;apply method8 to 3-square,4-square if 4*4 or 5*5//~va11I~
//va10:051107 for performance method9 select effective path        //~va10I~
//va09:051104 abondan v09(no effect),try method7                   //~va09I~
//va06:051104 make performance up;chk elapsed time at each method9 depth and shift to next candidate//~va06I~
//va05:051104 make performance up;no more search required when MULTISOL found//~va05I~
//v021:051102 redundancy chk:display also reduduncy depth count    //~v021I~
//va01:051013 5*5 support                                          //~va01I~
//*000103 xnp v0.1:Win(C++) support                                //~v0.1M~
//*               :internal logic err for no place whenmake(err ret when no candidate)//~v0.1M~
//*v100 first                                                      //~v0.1M~
//*************************************************************    //~v0.1M~



public class xnpsub {                                              //~v0.1M~
public static final int ANS_TIMEOUT     = 15;   		//loop max time limit(seconds)//~v0.1M~
public static final int ANS_MSGFREQ     = 3;  		//no time msg frequency//~v0.1M~
public static final int MAKE_TIMEOUT    = 0xffff;		//loop max time limit(seconds)//~v0.1M~
public static final int MAKE_MSGFREQ    = 10;  		//time msg frequency each 10 seconds//~v0.1M~
                                                                   //~v0.1M~
public static       int XNP_MINIDATA;// =  18;   //minimum idata count//~va01R~
//for Popt                                                         //~v0.1M~
public static final int XNP_TEST 		=0x01;                     //~v0.1M~
public static final int XNP_PILOT		=0x02;                     //~v0.1M~
public static final int XNP_PILOTNOMSG	=0x04;                     //~v0.1M~
public static final int XNP_REDUNDANTCK =0x08;                     //~v0.1M~
public static final int XNP_RIGID       =0x10;                     //~v0.1M~
public static final int XNP_NOPRINTIN   =0x20;                     //~v0.1M~
public static final int XNP_NOPRINTOUT  =0x40;                     //~v0.1M~
public static final int XNP_PRINTIDATA  =0x80;                     //~v0.1M~
public static final int XNP_ANSOFMAKE   =0x0100;                   //~v0.1M~
public static final int XNP_SOLOFMAKE   =0x0200;                   //~v0.1M~
public static final int XNP_SORT        =0x0400;                   //~v0.1M~
public static final int XNP_MULTISOL    =0x0800;    //multisolution found//~va05I~
public static final int XNP_RLEVELMASK  =0xf000;                   //~v0.1M~
public static final int XNP_M9TIMEOUT   =0x010000;  //timeout chk  //~va06I~
public static final int XNP_PATHCHK     =0x020000;  //solution without method9//~va10I~
public static final int XNP_MAKELOGIC2  =0x040000;  //new make logic//~v034I~
public static final int XNP_DELREDUNDANT=0x080000;  //delete redundant entry//~va15I~
                                                                   //~v0.1M~
//public static final int XNP_SOLVMASK    =(XNP_TEST|XNP_PILOT|XNP_PILOTNOMSG|XNP_REDUNDANTCK|XNP_SORT);//~v021R~
public static final int XNP_SOLVMASK    =(XNP_TEST|XNP_PILOT|XNP_PILOTNOMSG|XNP_REDUNDANTCK|XNP_SORT|XNP_RLEVELMASK);//~v021I~
public static final int XNP_MAKEMASK    =(XNP_TEST|XNP_RIGID|XNP_PILOT|XNP_ANSOFMAKE|XNP_SOLOFMAKE|XNP_SORT|XNP_DELREDUNDANT);//~va16R~
                                                                   //~v0.1M~
public static       int ALL_CANDIDATE    =0x1ff;	//candidate bit mask for all 9,8,...1//~va01R~
                                                                   //~v0.1M~
//for last level summary msg                                       //~v0.1M~
public static final int LEVEL_SUM        = 0xff00;                 //~v0.1M~
public static final int LEVEL_EASY_S     = 0x0100;                 //~v0.1M~
public static final int LEVEL_MEDIUM_S   = 0x0200;                 //~v0.1M~
public static final int LEVEL_HARD_S     = 0x0400;                 //~v0.1M~
public static final int LEVEL_HARDP1_S   = 0x0800;                 //~v0.1M~
public static final int LEVEL_HARDP2_S   = 0x1000;                 //~v0.1M~
//for pilot msg(each time on/off)                                  //~v0.1M~
public static final int LEVEL_FIX        = 0xff;                   //~v0.1M~
public static final int LEVEL_EASY_F     = 0x01;                   //~v0.1M~
public static final int LEVEL_MEDIUM_F   = 0x02;                   //~v0.1M~
public static final int LEVEL_HARD_F     = 0x04;                   //~v0.1M~
public static final int LEVEL_HARDP1_F   = 0x08;                   //~v0.1M~
public static final int LEVEL_HARDP2_F   = 0x10;                   //~v0.1M~
                                                                   //~v0.1M~
public static final int LEVEL_INIT       =0;    	//for dificulty level,initial data//~v0.1M~
                                                                   //~v0.1M~
public static final int LEVEL_EASY       =(LEVEL_EASY_S|LEVEL_EASY_F);//~v0.1M~
public static final int LEVEL_MEDIUM     =(LEVEL_MEDIUM_S|LEVEL_MEDIUM_F);//~v0.1M~
public static final int LEVEL_HARD       =(LEVEL_HARD_S|LEVEL_HARD_F)     ;//~v0.1M~
public static final int LEVEL_HARDP1     =(LEVEL_HARDP1_S|LEVEL_HARDP1_F)  ;//~v0.1M~
public static final int LEVEL_HARDP2     =(LEVEL_HARDP2_S|LEVEL_HARDP2_F)   ;//~v0.1M~
                                                                   //~v0.1I~
                                                                   //~v0.1I~
private static  int LOOPPROT;      //81                       //~v0.1R~
private static       int MAX_DEPTH=1;                              //~va12R~//~v@@@R~
private static final int MSG_CHK_FREQ=10;                          //~5A01I~
                                                                   //~va06I~
public  static final int RC_M9FOUNDANS=1;                          //~v033R~
public  static final int RC_M9BACKTO0=4;                           //~v033I~
public  static final int RC_M9TIMEOUT=24;                          //~v033I~
public  static final int RC_M9NOCANDIDATE=28; 
private static final int PAIRXBASE=10;                              //~v038I~
private static final int MAXPAIRCHK3=5;                            //~v041R~
private static final int MAXPAIRCHK4=8;                            //~v041R~
private static final int MAXPAIRCHK5=13;                           //~v041R~
private static       int MAXPAIRCHK;                                //~v041R~
private static       int METHOD8USE=0;                             //~v044I~
//******************************************:::                    //~v0.1M~
private static int BOARDTYPE;                                      //~va01I~
private static int MAP_SIZE;                                       //~va01M~
private static int PEG_MAX;                                        //~5A01R~
public static  int XNP_MAXRLIST;                              //~v0.1I~
public static  int MM_LAST;                                        //~va01I~
public static  int M9TIMEOUT=5000;		//10 second M_LAST;        //~va06R~
//*************************************************                //~v0.1M~
public static int GblSubthreadStopReq=0;	//thread communication with wnp//~5A01R~
public static int Gblwnpmode=0;	//thread communication with wnp    //~5A01I~
private  int Stestsw=0;                                            //~5A01R~
private  int Srchkmaxlevel=0;                                      //~5A01R~
private  int Stmsgfreq=0;		//loop msg frequency                   //~v0.1R~
private  int Srepeatmax=0;		//loop max                         //~v0.1R~
private  int Stotloop=0;                                             //~v0.1R~
private  long Stottm0=0;                                         //~5930R~
private  int Srlistcnt=0;       //redundancy list count            //~5A01R~
private  int Srlistcntold=0,Srchkold;       //redundancy list count         //~va12I~
private  int Srprintcase;     //redundancy list count              //~v021I~
private  int[] Srlist; //=new int[XNP_MAXRLIST*2];                 //~v021R~
private  int[][] Srlistold;                                        //~va12I~
private  int[][] Spdataold;                                        //~va12I~
private  int Smaxrclvl=0;       //max redundancy level detected    //~5A01R~
private  int[] Smask={0,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,              //1+8=9//~va01R~
                        0x100,0x200,0x400,0x800,0x1000,0x2000,0x4000,0x8000, //+8=17//~va01I~
                        0x10000,0x20000,0x40000,0x80000,0x100000,0x200000,0x400000,0x800000, //+8=25//~va01I~
                        0x1000000};	                               //~va01I~
private  int[][] Srchkwk=new int[MAX_DEPTH][4]; //max 10 depth,row,col,iold,inew//~v0.1R~
private long Smsgtm=0,tottm=0;                                     //~5A01I~
private NPWORKT[] Srchknpwt=new NPWORKT[MAX_DEPTH];                //~va01I~
private int[][][] Srchkdata=new int[MAX_DEPTH][][];                //~va01I~
private NPWORKT[]   Smethod9npwt;                                  //~va05R~
public  NPWORKT[]   Smethod9npwtok;                                //~va05R~
public  NPWORKT     Smethod9npwtwk;                                //~va10I~
private int[][]     Smethod9clist;                                 //~va01R~
private int[]       Smethod9effectivechkout;                       //~va10I~
private int[]       Smethod9effectivechkwk;                        //~va10I~
public  int         Smethod9npwtokdepth;                           //~va05I~
public  int         Smethod9timeoutdepth;                          //~va06I~
public  long[]      Smethod9starttime;                             //~va06I~
public  int[]       Smethod8num=new int[10];                               //~va11I~
public  int[]       Smethod8mask=new int[10];                              //~va11I~
private Point[]    Sm6xpt;                                        //~v038I~
public  xnpsub2     asub2=null; 
private Context pContext;//~va05R~
//********************
public  xnpsub(int Psize){                                         //~5A01R~
		pContext=WnpView.context;                         //~v@@@I~
        BOARDTYPE=Psize;                                           //~va01R~
    	MAP_SIZE=BOARDTYPE*BOARDTYPE;                              //~va01R~
    	MM_LAST=MAP_SIZE-1;                                        //~va01I~
    	PEG_MAX=MAP_SIZE*MAP_SIZE;                                   //~5A01R~
		LOOPPROT=PEG_MAX;                                          //~v0.1I~
		XNP_MAXRLIST=PEG_MAX;                                      //~v0.1I~
		if (BOARDTYPE==3)                                          //~va01I~
        {                                                          //~va11I~
			ALL_CANDIDATE=0x1ff;	//candidate bit mask for all 9,8,...1//~va01I~
            MAXPAIRCHK=MAXPAIRCHK3;                                //~v041R~
        }                                                          //~va11I~
        else                                                       //~va01I~
		if (BOARDTYPE==4)                                          //~va01I~
        {                                                          //~va11I~
			ALL_CANDIDATE=0xffff;   //candidate bit mask for all 16,15,...1//~va01I~
            MAXPAIRCHK=MAXPAIRCHK4;                                //~v041R~
        }                                                          //~va11I~
        else                                                       //~va01I~
		if (BOARDTYPE==5)                                          //~va01I~
        {                                                          //~va11I~
			ALL_CANDIDATE=0x1ffffff;   //candidate bit mask for all 25,24,...1//~va01I~
            MAXPAIRCHK=MAXPAIRCHK5;                                //~v041R~
        }                                                          //~va11I~
		XNP_MINIDATA=(BOARDTYPE-1)*MAP_SIZE;  //minimum idata count 18/48/100//~va01I~
		Srlist=new int[XNP_MAXRLIST*2];                            //~v021I~
		Srlistold=new int[MAP_SIZE][MAP_SIZE];                     //~va12I~
        Arrays.fill(Srlist,0);                                     //~5A01R~
        for (int ii=0;ii<MAX_DEPTH;ii++)                           //~5A01I~
        {                                                          //~va01I~
	        Arrays.fill(Srchkwk[ii],0);                            //~5A01I~
            Srchknpwt[ii]=new NPWORKT();                           //~va01I~
		    Srchkdata[ii]=new int[MAP_SIZE][MAP_SIZE];             //~va01I~
        }                                                          //~va01I~
		Smethod9npwt=new NPWORKT[PEG_MAX];                         //~va05R~
		Smethod9npwtok=new NPWORKT[PEG_MAX];                       //~va05R~
		Smethod9npwtwk=new NPWORKT();                              //~va10I~
		Smethod9clist=new int[PEG_MAX][MAP_SIZE];                  //~va01I~
		Smethod9effectivechkout=new int[MAP_SIZE];                 //~va10I~
		Smethod9effectivechkwk =new int[MAP_SIZE];                 //~va10I~
		Smethod9starttime=new long[PEG_MAX];                       //~va06I~
        for (int ii=0;ii<PEG_MAX;ii++)                             //~va01I~
        {                                                          //~va01I~
			Smethod9npwt[ii]=new NPWORKT();                        //~va05R~
			Smethod9npwtok[ii]=new NPWORKT();                      //~va05R~
        }                                                          //~va01I~
        Spdataold=new int[MAP_SIZE][MAP_SIZE];                     //~va12I~
        Sm6xpt=new Point[MAP_SIZE];                                //~v038I~
        for (int ii=0;ii<MAP_SIZE;ii++)                            //~v038I~
	        Sm6xpt[ii]=new Point();                                //~v038I~
}//constructor                                                     //~v0.1I~//~v@@@R~
//**********************************************************************
//* solution more human like
//* rc:0-ok, 4-not fixed exist
//**********************************************************************
//int xnpgetanswer(int Popt,int *Pdata,PNPWORKT Ppnpwt,int Ptmsgfreq,int Prepeatmax)//~v0.1I~
public int xnpgetanswer(int Popt,int[][]Pdata,NPWORKT Ppnpwt,int Ptmsgfreq,int Prepeatmax)//~5930R~
{                                                                  //~5930I~
    int      rc,ii,jj,chngdatacnt;                                 //~va12R~
    String str1,str2,str3;                                                   //~v@@@I~
//************
    asub2.Smakestarttime=(new Date()).getTime();	//for elapsed calc//~va10I~
    Stmsgfreq=Ptmsgfreq;                                           //~5A03R~
    Srepeatmax=Prepeatmax;                                         //~5A03R~
	Stotloop=0;                                                    //~5A03R~
	Srlistcnt=0;                                                   //~v0.1I~
    rc=xnpgasub(Popt & ~XNP_REDUNDANTCK,Pdata,Ppnpwt);
    if ((Popt & XNP_REDUNDANTCK)==0 || rc!=0)                      //~v0.1R~
    	return rc;
//redundancy chk
	if (Gblwnpmode!=0)                                             //~v045I~
    	if ((Popt & XNP_DELREDUNDANT)==0)                          //~v045I~
        {                                                          //~v@@@I~
//      	uerrmsg("Redundant Check in progress","冗長検査中");   //~v045R~//~v@@@R~
    		str1=pContext.getText(R.string.ErrRedundancyCheckingInP).toString();//~v@@@R~
        	uerrmsg(str1);                                        //~v@@@R~
        }                                                          //~v@@@I~
    Smaxrclvl=-1;         //redundancy level
    Srprintcase=0;                                                 //~v021I~
    asub2.Smakestarttime=(new Date()).getTime();	//for elapsed calc//~va10I~
//System.out.println(asub2.MakeElapsedTime()+":rchk start");       //~va12R~
    Srchkold=0;                                                    //~va12I~
//System.out.println("old rlcitcnt="+Srlistcntold);                //~va12R~
	if (Gblwnpmode!=0 && Srlistcntold!=0)                          //~va12I~
	{                                                              //~va12I~
	    chngdatacnt=0;                                             //~va12I~
    	for (ii=0;ii<MAP_SIZE;ii++)                                //~va12I~
        {                                                          //~va12I~
    		for (jj=0;jj<MAP_SIZE;jj++)                            //~va12I~
            {                                                      //~va12I~
//System.out.println("row="+ii+",col="+jj+",new="+Pdata[ii][jj]+",old="+Spdataold[ii][jj]);//~va12R~
	    		if (Pdata[ii][jj]!=Spdataold[ii][jj])              //~va12I~
            	{                                                  //~va12I~
            		if (chngdatacnt!=0)	//multi change             //~va12I~
                    {                                              //~v045I~
                    	Srchkold=0;                               //~v045I~
                    	break;                                     //~va12I~
                    }                                              //~v045I~
                	chngdatacnt++;                                 //~va12I~
		    		if (Pdata[ii][jj]!=0 || Spdataold[ii][jj]==0)	//not deleted case//~va12I~
                    	break;		                               //~va12I~
                    if (Srlistold[ii][jj]!=1)	//previously redundant 1st level//~va12I~
                        break;                                     //~va12I~
//System.out.println("prev redundant deleted row="+ii+",col="+jj+",num="+Spdataold[ii][jj]);//~v045R~
                    Srlistold[ii][jj]=0;                           //~va12I~
                    Srchkold=1;                                    //~va12I~
                }                                                  //~va12I~
            }                                                      //~va12I~
            if (jj<MAP_SIZE)	//dup change                       //~va12I~
            	break;                                             //~va12I~
        }                                                          //~va12I~
    }                                                              //~va12I~
//System.out.println("Srlistol="+Srlistold);                       //~v045R~
	xnprchk(Popt,Pdata,0,0,Ppnpwt);	//depth=0,start pos=0
    if (Smaxrclvl<0)                                               //~v021I~
        Smaxrclvl=0;                                               //~v021I~
//  if (Smaxrclvl!=0)                                              //~v021R~
        if (Srlistcnt!=0)                                             //~v0.1I~
        {                                                          //~va12I~
          if (MAX_DEPTH==1)                                        //~va12I~
          {                                                        //~v@@@I~
//      	uerrmsg("At least one of "+Srlistcnt+" is redundant.", //~v014R~//~v@@@R~
//  				Srlistcnt+" 個のうち少なくとも１つは冗長です。");//~v014R~//~v@@@R~
    		str1=pContext.getText(R.string.ErrRedundancy1).toString();//~v@@@R~
    		str2=pContext.getText(R.string.ErrRedundancy2).toString();//~v@@@R~
      		uerrmsg(str1+Srlistcnt+str2);                          //~v@@@R~
          }                                                        //~v@@@I~
          else                                                     //~va12I~
          if (Smaxrclvl>=MAX_DEPTH)                                //~va01I~
          {                                                        //~v@@@I~
//  			uerrmsg("Max Redundancy Level="+Smaxrclvl+" (out of "+Srlistcnt+")",//~va01I~//~v@@@R~
//  		        "冗長レベルは "+Smaxrclvl+"以上,レベル１は "+Srlistcnt+" 箇所。");//~va01I~//~v@@@R~
    		str1=pContext.getText(R.string.ErrRedundancy3).toString();//~v@@@R~
    		str2=pContext.getText(R.string.ErrRedundancy4).toString();//~v@@@R~
    		str3=pContext.getText(R.string.ErrRedundancy5).toString();//~v@@@R~
    			uerrmsg(str1+Smaxrclvl+str2+Srlistcnt+str3);       //~v@@@R~
                                                                   //~v@@@I~
		  }                                                        //~v@@@I~
          else                                                     //~va01I~
          {                                                        //~v@@@I~
    		str1=pContext.getText(R.string.ErrRedundancy3).toString();//~v@@@R~
    		str2=pContext.getText(R.string.ErrRedundancy6).toString();//~v@@@R~
    		str3=pContext.getText(R.string.ErrRedundancy5).toString();//~v@@@R~
//  			uerrmsg("Max Redundancy Level="+Smaxrclvl+" (out of "+Srlistcnt+")",//~va01R~//~v@@@R~
//  		        "冗長レベルは "+Smaxrclvl+",レベル１は "+Srlistcnt+" 箇所。");//~va01R~//~v@@@R~
    			uerrmsg(str1+Smaxrclvl+str2+Srlistcnt+str3);       //~v@@@R~
          }                                                        //~v@@@I~
        }                                                          //~va12I~
		else                                                       //~v0.1I~
        {                                                          //~v@@@I~
    		str1=pContext.getText(R.string.ErrRedundancy7).toString();//~v@@@R~
//  		uerrmsg("No Redundancy",                               //~v0.1I~//~v@@@R~
//  		        "冗長な場所は有りません。");                   //~5930R~//~v@@@R~
    		uerrmsg(str1);                                         //~v@@@R~
        }                                                          //~v@@@I~
if (Gblwnpmode!=0)                                                 //~5A01R~
{                                                                  //~5A01I~
    if (Smaxrclvl<0)    //no rwedundancy                           //~va01I~
        Smaxrclvl=0;                                               //~va01I~
	Ppnpwt.rclistcnt=Srlistcnt;                                    //~v0.1R~
	Ppnpwt.rclist=Srlist;                                          //~v0.1R~
	Ppnpwt.rclevel=Smaxrclvl;                                      //~v021I~
    Srlistcntold=Srlistcnt;                                        //~va12I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va12I~
    {                                                              //~va12I~
	    System.arraycopy(Pdata[ii],0,Spdataold[ii],0,MAP_SIZE);    //~va12R~
	    Arrays.fill(Srlistold[0],0);                                 //~va12I~
    }                                                              //~va12I~
    for (ii=0;ii<Srlistcnt;ii++)                                   //~va12I~
    	Srlistold[Srlist[ii*2]][Srlist[ii*2+1]]=1; //redundant pos //~va12I~
}                                                                  //~5A01I~
    return 0;
}//xnpgetanswer
//**********************************************************************
//* redundancy chk
//* rc:0-ok, 4-not fixed exist
//**********************************************************************
private int  xnprchk(int Popt,int[][] Pdata,int Prlevel,int Pstartpos,NPWORKT Ppnpwt)//~va01R~
{
//  int      idata[9][9];                                          //~v0.1R~
    int      pi,loop,iold,inew,row,col,rlevel=0;                     //~v0.1R~
    int rc=0,rc2;                                                  //~va01R~
    int[][] pi0;                                                   //~5930R~
	NPWORKT pnpwt; 
	String str1,str2;//~v0.1R~
//************
//System.out.println("xnprchk depth="+Prlevel);                    //~va12R~
    if (Prlevel>=MAX_DEPTH)
    {
	  if (Gblwnpmode==0)                                           //~v045I~
      {                                                            //~v045I~
        xnprprint(Prlevel);        //print redundancy              //~v021I~
//      uerrmsg("redundancy level is over "+MAX_DEPTH,             //~va01R~//~v@@@R~
//      			"冗長レベルが "+MAX_DEPTH+" 以上です");        //~va01R~//~v@@@R~
    	str1=pContext.getText(R.string.ErrRedundancy8).toString(); //~v@@@R~
    	str2=pContext.getText(R.string.ErrRedundancy9).toString(); //~v@@@R~
        uerrmsg(str1+MAX_DEPTH+str2);                              //~v@@@R~
      }                                                            //~v045I~
        return 4;                                                  //~va01I~
    }
//  pi0=new int[MAP_SIZE][MAP_SIZE];                               //~va01R~
    pi0=Srchkdata[Prlevel];                                         //~va01I~
//  pnpwt=new NPWORKT();                                           //~va01R~
    pnpwt=Srchknpwt[Prlevel];                                      //~va01I~
    pnpwt.clear();                                                 //~va01I~
	boardnumcopy(pi0,Pdata);                                       //~v021R~
    for (loop=Pstartpos;loop<PEG_MAX;loop++)//no need to start from 0//~v0.1R~
    {
        row=loop/MAP_SIZE;                                         //~va01R~
        col=loop%MAP_SIZE;                                         //~va01R~
    	pi=pi0[row][col];                                          //~5930R~
    	if (pi==0)	//no init data                                 //~v0.1R~
        	continue;
//System.out.println("rchk level="+Prlevel+",loop="+loop);         //~v045R~
        if (Prlevel==0)                                            //~va12R~
        {                                                          //~va12I~
//System.out.println("rchk level=0 row="+row+",col="+col+",num="+pi);//~v045R~
			if (Srchkold!=0 && Srlistold[row][col]==0)	//not previous redundant data//~va12I~
            {                                                      //~va12I~
//System.out.println("skip prev not redundant row="+row+",col="+col+",num="+pi);//~v045R~
            	continue;                                          //~va12I~
            }                                                      //~va12I~
        }                                                          //~va12I~
        iold=pi;                                                   //~v0.1R~
        pi0[row][col]=0;  //try drop one data                      //~5930R~
        rc2=xnpgasub((Popt|XNP_NOPRINTIN|XNP_NOPRINTOUT)&~XNP_PILOT,pi0,pnpwt);//~va01I~
//System.out.println("xnprchk gasub rc="+rc+",Plevel="+Prlevel+",loop="+loop+",num="+iold+",row="+row+",col="+col);//~va01R~
        if (rc2==0)                                                //~va01R~
        {
        	if (Prlevel==0)                                        //~va12I~
            {                                                      //~va12I~
				Srlist[Srlistcnt*2]=row;                           //~va12I~
				Srlist[Srlistcnt*2+1]=col;                         //~va12I~
				Srlistcnt++;                                       //~va12I~
			}                                                      //~va12I~
            inew=pnpwt.m99[row][col].fnum;   //fixed num           //~v0.1R~
            Srchkwk[Prlevel][0]=row;    //save redundant point
            Srchkwk[Prlevel][1]=col;
            Srchkwk[Prlevel][2]=iold;
            Srchkwk[Prlevel][3]=inew;
	        rlevel=Prlevel+1;
            if (rlevel>Smaxrclvl)
                Smaxrclvl=rlevel;
//System.out.println("fwd level="+rlevel+",max="+Smaxrclvl+",old="+iold+",new="+inew);//~va01R~
//level down
			if (rlevel<Srchkmaxlevel)
            {                                                      //~va01I~
	            rc=xnprchk(Popt,pi0,Prlevel+1,loop+1,pnpwt);       //~va01R~
	            if (rc!=0)                                         //~va01R~
	            	if (Prlevel==0)	//serach level 1               //~va01I~
                    	rc=0;                                      //~va01I~
                    else                                           //~va01I~
	                	break;                                     //~va01R~
            }                                                      //~va01I~
            else
            {
                xnprprint(rlevel);        //redundancy list        //~v021I~
                xnpprintnum(0,0,0,pnpwt,0,null);   //print initial data
                xnpprintnum(2,row,col,pnpwt,0,null);   //print last data
            }
		}//redundancy
        pi0[row][col]=iold;		//recov                            //~5930R~
	}//all idata
//System.out.println("back Plevel="+Prlevel+",max="+Smaxrclvl);    //~va01R~
    if (rlevel==Smaxrclvl  //first back step
    &&  rlevel<Srchkmaxlevel
    &&  Smaxrclvl>=0)
    {
        xnprprint(rlevel);		//redundancy list                  //~v021I~
	    xnpprintnum(0,0,0,pnpwt,0,null);	//print initial data
	}
//  ufree(pnpwt);                                                  //~v0.1R~
//  pnpwt=null;                                                    //~va01R~
//System.out.println("xnprchk return  rc="+rc+",Plevel="+Prlevel); //~va01R~
    return rc;                                                     //~va01R~
}//xnprchk
//**********************************************************************
//* redundancy chk list print
//* rc:0-ok, 4-not fixed exist
//**********************************************************************
private void xnprprint(int Plevel)                                 //~v021R~
{
if (Gblwnpmode!=0)                                                 //~5A01R~
{                                                                  //~5A01I~
	return;                                                        //~va12I~
//  if (Plevel==1                                                  //~va12R~
//  ||  Srlistcnt==0                                               //~va12R~
//  ||  (Srlist[(Srlistcnt-1)*2]!=Srchkwk[0][0] && Srlist[(Srlistcnt-1)*2+1]!=Srchkwk[0][1]))//~va12R~
//  {                                                              //~va12R~
//    Srlist[Srlistcnt*2]=Srchkwk[0][0];      //x                  //~va12R~
//    Srlist[Srlistcnt*2+1]=Srchkwk[0][1];  //y                    //~va12R~
//    Srlistcnt++;                                                 //~va12R~
//  }                                                              //~va12R~
}                                                                  //~5A01I~
else                                                               //~5A01R~
{                                                                  //~5A01I~
    int[][] pi;                                                    //~5A01R~
    int ii; 
    String str1,str2;//~5A01I~
//************                                                     //~5A01R~
  	if (Plevel==1                                                  //~v021I~
    ||  Srlistcnt==0                                               //~v021I~
    ||  (Srlist[(Srlistcnt-1)*2]!=Srchkwk[0][0] && Srlist[(Srlistcnt-1)*2+1]!=Srchkwk[0][1]))//~v021I~
  	{                                                              //~v021I~
		Srlist[Srlistcnt*2]=Srchkwk[0][0];   	//x                //~v021I~
		Srlist[Srlistcnt*2+1]=Srchkwk[0][1];  //y                  //~v021I~
		Srlistcnt++;                                               //~v021I~
  	}                                                              //~v021I~
    Srprintcase++;                                                 //~v021I~
//  uerrmsg("---Redundancy List  CaseNo="+Srprintcase+" ---",      //~v021R~//~v@@@R~
//          "---冗長性検査結果  CaseNo="+Srprintcase+" ---");      //~v021R~//~v@@@R~
    str1=pContext.getText(R.string.PrintRedundancy1).toString() ;   //~v@@@R~
    str2=pContext.getText(R.string.PrintRedundancy2).toString();   //~v@@@R~
    uerrmsg(str1+Srprintcase+str2);                                //~v@@@R~
    pi=Srchkwk;                                                    //~5A01R~
    for (ii=0;ii<Smaxrclvl;ii++)                                   //~5A01R~
    {                                                              //~5A01R~
        if (pi[ii][2]!=pi[ii][3]) //number changed                 //~5A01R~
            System.out.println("#"+(new DecimalFormat("#0")).format(ii+1)+"    ("+(pi[ii][0]+1)+","+(pi[ii][1]+1)+")(!="+pi[ii][2]+")  ");//~5A01R~
        else                                                       //~5A01R~
            System.out.println("#"+(new DecimalFormat("#0")).format(ii+1)+"    ("+(pi[ii][0]+1)+","+(pi[ii][1]+1)+")(=="+pi[ii][2]+")  ");//~5A01R~
    }                                                              //~5A01R~
}                                                                  //~5A01I~
    return;
}//xnprprint
//**********************************************************************
//* solution more human like
//* rc:0-ok, 4-not fixed exist, 8:data conflict, 12:multiple solution
//**********************************************************************
private int xnpgasub(int Popt,int[][] Pdata,NPWORKT Ppnpwt)        //~5930R~
{
    int rc,opt;
    String str1,str2;
//************
//*init by parm data
    rc=xnpansinit(Popt,Pdata,Ppnpwt);

    Srchkmaxlevel=(Popt>>12);		//max rchk level
    if ((Popt & XNP_NOPRINTIN)==0)                                 //~v0.1R~
	    xnpprintnum(0,0,0,Ppnpwt,0,null);	//print initial data
    if (rc!=0)
    {
	    if ((Popt & XNP_NOPRINTIN)!=0)		//not printed          //~v0.1R~
		    xnpprintnum(0,0,0,Ppnpwt,0,null);	//print initial data
        if (rc==16)                                                //~v0.1I~
        {                                                          //~v@@@I~
    		str1=pContext.getText(R.string.ErrMinimum1).toString();//~v@@@R~
    		str2=pContext.getText(R.string.ErrMinimum2).toString();//~v@@@R~
//          uerrexit("Specify minimum "+XNP_MINIDATA+" init data", //~5930R~//~v@@@R~
//          		"少なくとも "+XNP_MINIDATA+" 個の指定が必要"); //~5930R~//~v@@@R~
            uerrexit(str1+XNP_MINIDATA+str2);                      //~v@@@I~
        }                                                          //~v@@@I~
        else                                                       //~v0.1I~
        {                                                          //~v@@@I~
    		str1=pContext.getText(R.string.ErrQData).toString();   //~v@@@R~
//          uerrexit("Initial data error(rc="+rc+")",              //~5930R~//~v@@@R~
//          		"問題データに誤りがあります(rc="+rc+")");      //~5930R~//~v@@@R~
            uerrexit(str1+rc+")");                                 //~v@@@R~
        }                                                          //~v@@@I~
	}
//resolution
//if (Stestsw!=0)                                                  //~5A02R~
//{                                                                //~5A02R~
//    System.out.println("xnpgasub:before resolution");            //~5A02R~
//    xnptrace(Ppnpwt);                                            //~5A02R~
//}                                                                //~5A02R~
//	rc=xnpsolution(Ppnpwt,0);                                      //~va05R~
  	rc=xnpsolution(Popt,Ppnpwt,0);                                 //~va05I~
//if (Stestsw!=0)                                                  //~5A02R~
//{                                                                //~5A02R~
//    System.out.println("xnpgasub:after resolution rc="+rc);      //~5A02R~
//    xnptrace(Ppnpwt);                                            //~5A02R~
//}                                                                //~5A02R~
    if ((Ppnpwt.stat & (NPWORKT.STAT_MULTISOL|NPWORKT.STAT_TIMEOUT))==0)//print require at last//~v0.1R~
        if ((Popt & XNP_NOPRINTOUT)==0)                            //~v0.1R~
        {
            if (rc!=0)
            {
                if (rc==4) 		//space remain
                    opt=-2;     //failed (last chk failed,space remain)
                else            //conflict
                    opt=-1;     //aborted
            }
            else
                opt=2;      //success
            xnpprintnum(opt,0,0,Ppnpwt,0,null);    //print answer at last
        }
    return rc;
}//xnpgasub
//*************************************
//*work tbl init by initial data
//*rc: data dup err 16:too few data                                //~v0.1R~
//*************************************
public int xnpansinit(int Popt,int[][] Pdata,NPWORKT Ppnpwt)       //~5930R~
{
	M99[][] pm99;                                                  //~v0.1R~
    int[][] pi;                                                    //~5930R~
	int ii,jj;                                                     //~v0.1R~
//********************************
    Stestsw=Popt & XNP_TEST;
//    if ((Popt & XNP_SOLOFMAKE)!=0)                                 //~v0.1R~
//    {                                                              //~v0.1I~
//    	Srepeatmax=ANS_TIMEOUT;                                    //~v0.1I~
//    	Stmsgfreq=ANS_MSGFREQ;                                     //~v0.1I~
//        Stotloop=0;                                                //~v0.1R~
//	}                                                              //~v0.1I~
    if ((Popt & XNP_SORT)!=0)                                      //~v0.1R~
        if (xnpsortdata(Pdata,Pdata)!=0)                              //~v0.1R~
        	return 4;			//init data err                    //~v0.1I~
//tbl init
	Ppnpwt.clear();                                                //~v0.1R~
//if (Stestsw!=0)                                                  //~5A02R~
//{                                                                //~5A02R~
//    System.out.println("xnpgasub: before ansinit");              //~5A02R~
//    xnptrace(Ppnpwt);                                            //~5A02R~
//}                                                                //~5A02R~
    if ((Popt & XNP_PILOT)!=0)                                     //~v0.1R~
    {
    	Ppnpwt.stat|=NPWORKT.STAT_PILOT;                           //~v0.1R~
		if ((Popt & XNP_PILOTNOMSG)==0)                            //~v0.1R~
    		Ppnpwt.stat|=NPWORKT.STAT_PILOTMSG;                    //~v0.1R~
	}
    if ((Popt & XNP_REDUNDANTCK)!=0)                               //~v0.1R~
    	Ppnpwt.stat|=NPWORKT.STAT_RCHK;                            //~v0.1R~
    if ((Popt & XNP_ANSOFMAKE)!=0)                                 //~v0.1R~
    	Ppnpwt.stat|=NPWORKT.STAT_ANSOFMAKE;                       //~v0.1R~
	pm99=Ppnpwt.m99;                                               //~v0.1R~
	for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
		for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
        	pm99[ii][jj].cmsk=ALL_CANDIDATE;		//all number is candidate at init//~v0.1R~
        	pm99[ii][jj].ccnt=MAP_SIZE;					//candidate count//~va01R~
		}
//*set initial data
	for (ii=0,pi=Pdata;ii<MAP_SIZE;ii++)                           //~va01R~
		for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        	if (pi[ii][jj]!=0)				//initial data         //~5930R~
            {
            	if (xnpfix(ii,jj,pi[ii][jj],Ppnpwt,LEVEL_INIT,null)!=0)//set fixed data//~5930R~
                	return 4;		//dup err                      //~v0.1R~
			}
    if (Ppnpwt.idatano<XNP_MINIDATA)                               //~v0.1R~
        return 16;                                                 //~v0.1I~
	return 0;                                                      //~v0.1R~
}//xnpansinit
//**********************************************************************//~v0.1I~
//* sort probrem data                                              //~v0.1I~
//**********************************************************************//~v0.1I~
public int xnpsortdata(int[][] Ppdata,int[][] Ppdatamask)         //~v0.1M~
//int xnpsortdata(int *Ppdata,int *Ppdatamask)                     //~v0.1R~
{                                                                  //~v0.1I~
    int ii,jj,row,col,num;                                                 //~v0.1R~
    int[][] pi,pimask;                                               //~v0.1I~
    int[] map=new int[MAP_SIZE+1];                                 //~va01R~
    int[] sortwk=new int[MAP_SIZE+1];                              //~va01R~
//************                                                     //~v0.1I~
//*assign seqno                                                    //~v0.1I~
    Arrays.fill(sortwk,0);                               //~v0.1I~
    Arrays.fill(map,0);  //for the case fo number appeared  //~v0.1I~
    for (ii=0,jj=0,pi=Ppdata,pimask=Ppdatamask;ii<PEG_MAX;ii++)//~v0.1R~
    {   
    	row=ii/MAP_SIZE;
    	col=ii%MAP_SIZE;//~v0.1I~
    	num=pi[row][col];                                                //~v0.1R~
        if (num<0 || num>MAP_SIZE)                                 //~va01R~
        	return 4;                                              //~v0.1I~
        if (num==0)                                                  //~v0.1I~
            continue;                                              //~v0.1I~
		if (pimask[row][col]==0)	//not prob data                        //~v0.1R~
        	continue;                                              //~v0.1I~
        if (sortwk[num]!=0)	//not first time appeared              //~v0.1R~
        	continue;                                              //~v0.1I~
        sortwk[num]=1;	//not first time appeared                  //~v0.1I~
        map[++jj]=num;	//translate num to jj                      //~v0.1I~
    }                                                              //~v0.1I~
//*reverse translation                                             //~v0.1I~
    Arrays.fill(sortwk,0);                                         //~v0.1R~
    for (ii=1;ii<=MAP_SIZE;ii++)                                   //~va01R~
    	sortwk[map[ii]]=ii;                                        //~v0.1I~
//*translation                                                     //~v0.1I~
    for (ii=0,pi=Ppdata;ii<PEG_MAX;ii++)                           //~v0.1R~
    {   
    	row=ii/MAP_SIZE;
    	col=ii%MAP_SIZE;//~v0.1I~
    	num=pi[row][col];                                                //~v0.1R~
        if (num==0)                                                //~v0.1R~
        	continue;                                              //~v0.1I~
        pi[row][col]=sortwk[num];                                        //~v0.1R~
    }                                                              //~v0.1I~
    return 0;                                                      //~v0.1I~
}//xnpsortdata                                                     //~v0.1I~
//**********************************************************************
//* solution more human like
//* rc:0-ok, 4:free space remain(xnplastchk rc),8:data conflict,12:multi solution
//*    20:answer timeout                                           //~v0.1I~
//**********************************************************************
//public int xnpsolution(NPWORKT Ppnpwt,int Pm9depth)              //~va05R~
public int xnpsolution(int Popt,NPWORKT Ppnpwt,int Pm9depth)       //~va05I~
{
    int loop,oldseq,num,rc,rcw=0,ii;                               //~va11R~
//************
//System.out.println("xnpsolution entry depth="+Pm9depth+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno);//~va01R~
//xnptrace(Ppnpwt);                                                //~va01R~
    for (loop=0;loop<LOOPPROT;loop++)
    {
        oldseq=Ppnpwt.seqno;                                       //~v0.1R~
if (Stestsw!=0)                                                    //~5A02I~
{                                                                  //~5A02I~
//System.out.println("xnpsolution:loop="+loop+",max="+LOOPPROT);   //~v045R~
	xnptrace(Ppnpwt);                                              //~5A02I~
}                                                                  //~5A02I~
        if ((Ppnpwt.stat & NPWORKT.STAT_PILOT)!=0)                 //~v0.1R~
        {
//try always from easy method
			for (;;)
            {
            	if ((rcw=xnpmethod1(Ppnpwt))==0)//all last remaind place
                	break;				//try next bethod
                if (rcw==-1) 			//fix err
                	return 8;
			}

            for (num=1;num<=MAP_SIZE;num++)    //all number        //~va01R~
            {
                if ((rcw=xnpmethod2box(Ppnpwt,num))==1) //free place in box
                    break;              //fixed,retry from first
	            if (rcw==-1)				//fix err
    	        	return 8;				//conflict
			}
            if (num<=MAP_SIZE)                 //fix occured       //~va01R~
                continue;               //retry from first

            if ((rcw=xnpmethod1x(Ppnpwt))==1)    //search only one remained in row+col+box
                continue;               //retry from first
            if (rcw==-1)				//fix err
            	return 8;				//conflict

            for (num=1;num<=MAP_SIZE;num++)    //all number        //~va01R~
            {
                if ((rcw=xnpmethod2line(Ppnpwt,num))==1) //free place in row or col
                    break;              //fixed,retry from first
	            if (rcw==-1)				//fix err
    	        	return 8;				//conflict
			}
            if (num<=MAP_SIZE)                 //fix occured       //~va01R~
                continue;               //retry from first
        }//pilot mode

        for (num=1;num<=MAP_SIZE;num++)    //all number            //~va01R~
        {
//System.out.println("method3 call loop="+loop+",num="+num);       //~va11R~
            if ((rcw=xnpmethod3(Ppnpwt,num))==1) //free place row+col+box
                break;  			//fixed,retry from first
            if (rcw==-1)                //fix err
                return 8;               //conflict
		}
        if (num<=MAP_SIZE)					//fix occured          //~va01R~
            continue;               //retry from first

		if ((rcw=xnpmethod4(Ppnpwt))==1)	    //search single candidate
            continue;               //retry from first
        if (rcw==-1)                //fix err
            return 8;               //conflict

		if ((rcw=xnpmethod6(Ppnpwt))!=0)	    //drop candidate by matching pair//~v0.1R~
    	{
        	if (rcw<0)                  //no candidate place       //~v0.1I~
            	return 8;                                          //~v0.1I~
    		rcw=xnpmethod4(Ppnpwt);
	        if (rcw==-1)                //fix err
    	        return 8;               //conflict
            continue;               //retry from first
    	}
//        if ((rcw=xnpmethod7(Ppnpwt))!=0)  //search only one candidate place//~v039R~
//        {                                                        //~v039R~
//            if (rcw<0)                  //fix failed             //~v039R~
//                return 8;                                        //~v039R~
//            continue;               //retry from first           //~v039R~
//        }                                                        //~v039R~
      if (METHOD8USE!=0)                                           //~v044R~
      {                                                            //~v044I~
        for (ii=3;ii<=MAXPAIRCHK;ii++)                             //~v041R~
            if ((rcw=xnpmethod8(Ppnpwt,ii))!=0)        //drop candidate by matching pair//~va11R~
            {                                                      //~va11I~
	        	if (rcw<0)                  //fix failed           //~va11I~
    	        	return 8;                                      //~va11I~
        	    break;                                             //~v044R~
            }                                                      //~va11I~
        if (rcw!=0)                                                   //~v044I~
            continue;                                              //~v044I~
      }                                                            //~v044I~

      for (ii=3;ii<=MAXPAIRCHK;ii++)                               //~v041R~
		if ((rcw=xnpmethod6x(Ppnpwt,ii))!=0)	    //4 square case//~v038R~
    	{                                                          //~v036I~
        	if (rcw<0)                  //no candidate place       //~v036I~
            	return 8;                                          //~v036I~
            break;                                                 //~v044R~
    	}                                                          //~v036I~
        if (rcw!=0)                                                   //~v044I~
            continue;                                              //~v044I~
                                                                   //~v044I~
        if (Ppnpwt.seqno==PEG_MAX)	//end                          //~v0.1R~
        	break;
        if (oldseq!=Ppnpwt.seqno)	//progress                     //~v0.1R~
        	continue;
                                                                   //~va10I~
		if ((Popt & XNP_PATHCHK)!=0)  //solution without method9   //~va10I~
        	break;                                                 //~va10I~
//  	rc=xnpmethod9(Ppnpwt,Pm9depth);                            //~va05R~
    	rc=xnpmethod9(Popt,Ppnpwt,Pm9depth);                       //~va05I~
        if (rc==RC_M9FOUNDANS)                                     //~v033I~
        	return 0;                                              //~v033I~
        if (rc==RC_M9TIMEOUT)                                      //~va06I~
        	return rc;                                             //~va06I~
        if (rc==RC_M9NOCANDIDATE)                                  //~va10I~
        	return 8;                                              //~va10I~
//      if (rc==4)		//not 1st level                            //~v033R~
        if (rc==RC_M9BACKTO0)		//not 1st level                //~v033I~
        	return 0;	//return now by success rc
        if ((Ppnpwt.stat & NPWORKT.STAT_TIMEOUT)!=0)               //~v0.1R~
            return 20;                 //timeout                   //~v0.1I~
	    if (rc==0)		//failed
        	break;
	}
//System.out.println("xnpsolution return idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno);//~va01R~
//System.out.println("xnpsolution return stat="+(Ppnpwt.stat&(NPWORKT.STAT_DATA_CONFLICT|NPWORKT.STAT_MULTISOL))+",loop="+loop);//~va01R~
    if ((Ppnpwt.stat & NPWORKT.STAT_DATA_CONFLICT)!=0)             //~v0.1R~
        return 8;                  //problem err
    if ((Ppnpwt.stat & NPWORKT.STAT_MULTISOL)!=0)                  //~v0.1R~
        return 12;                 //multi solution err
    return xnplastchk(Ppnpwt);     //rc=0 or 4 or 8
}//xnpsolution
//**********************************************************************
//* fill last remained space in the row/col/box for all row
//* (search last empty place in  row,col or box)
//* rc:1:fixed a number,0:no progress -1:fix err
//**********************************************************************
private int xnpmethod1(NPWORKT Ppnpwt)                             //~v0.1R~
{
    M99[][] pm99;                                                  //~v0.1R~
	int  ii,jj,ibox;                                               //~v0.1R~
	int  row,col,fixctr=0;
    int[] pilc;                                                    //~v0.1I~
//************************
//row
    pm99=Ppnpwt.m99;
    pilc=Ppnpwt.fcnt[0];      //line(row) fixed cnt                //~v0.1R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
        if (pilc[ii]!=MM_LAST) 				//not the case only one remained//~va01R~
        	continue;
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
            if (pm99[ii][jj].fnum==0)        //not fixed           //~v0.1R~
            {
	            if (xnpfixbymask(ii,jj,pm99[ii][jj].cmsk,Ppnpwt,LEVEL_EASY,"m1R")!=0)//~v0.1R~
			    	return -1;
            	fixctr++;			//rc
            }
    }//row
//col
    pilc=Ppnpwt.fcnt[1];      //line(col) fixed cnt                //~v0.1R~
       //matrix of each plase                      //~v0.1I~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va01R~
    {
        if (pilc[jj]!=MM_LAST) 				//not the case only one remained//~va01R~
        	continue;
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va01R~
        {                                                          //~v0.1I~
            if (pm99[ii][jj].fnum==0)        //not fixed           //~v0.1R~
            {
	            if (xnpfixbymask(ii,jj,pm99[ii][jj].cmsk,Ppnpwt,LEVEL_EASY,"m1C")!=0)//~v0.1R~
			    	return -1;
            	fixctr++;			//rc
            }
        }                                                          //~v0.1I~
    }//col
//box
    pilc=Ppnpwt.fcnt[2];      //line(box) fixed cnt                //~v0.1R~
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va01R~
    {
        if (pilc[ibox]!=MM_LAST) 				//not the case only one remained//~va01R~
        	continue;
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va01R~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va01R~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~va01R~
        {                                                          //~v0.1I~

            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
	            if (pm99[ii][jj].fnum==0)        //not fixed       //~v0.1R~
                {
                    if (xnpfixbymask(ii,jj,pm99[ii][jj].cmsk,Ppnpwt,LEVEL_EASY,"m1B")!=0)//~v0.1R~
				    	return -1;
	            	fixctr++;			//rc
                }
        }                                                          //~v0.1I~
    }//all box
    return fixctr;		//no one fixed
}//xnpmethod1
//**********************************************************************
//* last unused number in the sum set of row,col and box.
//* rc:1:fixed a number,0:no progress,-1:fix err
//**********************************************************************
private int xnpmethod1x(NPWORKT Ppnpwt)                            //~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   ii,jj,mask;
//************
	pm99=Ppnpwt.m99;		//candidate count                      //~v0.1R~
	for (ii=0;ii<MAP_SIZE;ii++)                                    //~5A02R~
		for (jj=0;jj<MAP_SIZE;jj++)                                //~5A02R~
        	if (pm99[ii][jj].fnum==0)		//not fixed            //~5A02R~
            {
//       Ppnpwt.fmsk[0][ii],Ppnpwt.fmsk[1][jj],Ppnpwt.fcnt[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE]);//~va01R~
                mask=Ppnpwt.fmsk[0][ii]                            //~v0.1R~
                    	|Ppnpwt.fmsk[1][jj]                        //~v0.1R~
                    	|Ppnpwt.fmsk[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE];	//used number mask//~va01R~
                mask=ALL_CANDIDATE & ~mask;	        //unused number mask
                if ((mask & (mask-1))==0)	//only 1 bit or 0 bit on//~v0.1R~
                	if (mask!=0)			//only 1 bit on
                {
    	            if (xnpfixbymask(ii,jj,mask,Ppnpwt,LEVEL_MEDIUM,"m1x")!=0)
				    	return -1;
                    return 1;
				}
			}//num not fixed
    return 0;		//no fix
}//xnpmethod1x
//***************************************************************
//* for A number,
//* search remained place in a box which is out of effect from filled row and col
//* (box is more easy to find than in row or col)
//* rc :1:one place fixed,0:no place fixed,-1:fix err
//***************************************************************
private int xnpmethod2box(NPWORKT Ppnpwt,int Pnum)                 //~v0.1R~
{
    M99[][] pm99;                                                  //~v0.1R~
	int  ii,jj,ibox,mask,cctr;                                     //~v0.1R~
	int  row,col,frow=0,fcol=0;
    int[] pilm;                                                    //~v0.1I~
//************
    mask=Smask[Pnum];
    pilm=Ppnpwt.fmsk[2];      //line(col) fixed mask               //~v0.1R~
    pm99=Ppnpwt.m99;       //matrix of each plase                  //~v0.1I~
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va01R~
    {
        if ((pilm[ibox] & mask)!=0)  //already fixed in this box   //~v0.1R~
        	continue;
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va01R~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va01R~
        for (cctr=0,ii=row;ii<row+BOARDTYPE;ii++)                  //~va01R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
            {
	            if (pm99[ii][jj].fnum==0       //not yet fixed     //~v0.1R~
	    		&&  (Ppnpwt.fmsk[0][ii] & mask)==0      //line(row) fixed mask//~v0.1R~
	    		&&  (Ppnpwt.fmsk[1][jj] & mask)==0)      //line(row) fixed mask//~v0.1R~
                {
                    cctr++;
                    frow=ii;
                    fcol=jj;
                }
			}
        if (cctr==1)        //only one candidate
        {
            if (xnpfix(frow,fcol,Pnum,Ppnpwt,LEVEL_EASY,"m2B")!=0)
				return -1;
            return 1;
        }
    }//all box
    return 0;		//failed
}//xnpmethod2box
//***************************************************************
//* for A number,
//* search place in row/col where is free from col/row using filled mask
//* rc :1:some place fixed,0:no place fixed,-1:fix err
//***************************************************************
private int xnpmethod2line(NPWORKT Ppnpwt,int Pnum)                //~v0.1R~
{
    M99[][] pm99;                                                  //~v0.1R~
	int  ii,jj,mask,cctr;                                          //~v0.1R~
	int  frow=0,fcol=0;
    int[] pilm,pilm2;                                              //~v0.1I~
//************
    mask=Smask[Pnum];
//printf("****Pnum=%d,mask=%04x\n",Pnum,mask);
//row
    pilm=Ppnpwt.fmsk[0];      //line(row) fixed mask               //~v0.1R~
	pm99=Ppnpwt.m99;       //matrix of each plase                  //~v0.1I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
        if ((pilm[ii] & mask)!=0)  //already fixed in this row     //~v0.1R~
        	continue;
	    pilm2=Ppnpwt.fmsk[1];      //line(col) fixed mask          //~v0.1R~
        for (cctr=0,jj=0;jj<MAP_SIZE;jj++)                         //~va01R~
        {
            if (pm99[ii][jj].fnum!=0)        //already fixed          //~v0.1R~
                continue;
            if ((Ppnpwt.fmsk[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE] & mask)!=0) //num is on box//~va01R~
                continue;
            if ((pilm2[jj] & mask)!=0)     //num is on column      //~v0.1R~
                continue;
            cctr++;
            fcol=jj;
//printf("easy row row=%d,col=%d,num=%d,mask=%04x,nummask=\n",ii,jj,Pnum,pm99.cmsk,mask);//~v0.1R~
		}//col
        if (cctr==1)        //only one candidate in row
        {
            if (xnpfix(ii,fcol,Pnum,Ppnpwt,LEVEL_MEDIUM,"m2R")!=0)
				return -1;
            return 1;
        }
    }//row
//col
    pilm=Ppnpwt.fmsk[1];      //line(col) fixed mask               //~v0.1R~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va01R~
    {
        if ((pilm[jj] & mask)!=0)  //already fixed in this col     //~v0.1R~
        	continue;
	    pilm2=Ppnpwt.fmsk[0];      //line(row) fixed mask          //~v0.1R~
        for (cctr=0,ii=0;ii<MAP_SIZE;ii++)                         //~va01R~
        {
//printf("(%d,%d) num=%d, mask=%04x\n",ii,jj,pm99.fnum,mask);      //~v0.1R~
            if (pm99[ii][jj].fnum!=0)        //already fixed          //~v0.1R~
                continue;
//printf("fmsk2=%04x\n",Ppnpwt.fmsk[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE]);//~va01R~
            if ((Ppnpwt.fmsk[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE] & mask)!=0) //num is on box//~va01R~
                continue;
//printf("plim2 row mask=%04x\n",*pilm2);
            if ((pilm2[ii] & mask)!=0)  	//num is on the row    //~v0.1R~
                continue;
            cctr++;
            frow=ii;
//printf("cctr=%d\n",cctr);
		}//row
        if (cctr==1)        //only one candidate
        {
            if (xnpfix(frow,jj,Pnum,Ppnpwt,LEVEL_MEDIUM,"m2C")!=0)
				return -1;
            return 1;
        }
    }//col
    return 0;		//failed
}//xnpmethod2line
//***************************************************************
//* for A number,
//* search place in row/col where is free from box and col/row
//*   using candidate mask.
//* And,drop mask on the line/box when the mask is all enclosed in the box/line
//* rc :1:some place fixed,0:no place fixed,-1:fix err
//***************************************************************
private int xnpmethod3(NPWORKT Ppnpwt,int Pnum)                    //~v0.1R~
{
    M99[][] pm99;                                                  //~v0.1R~
	int  ii,jj,mask,cctr,rcw;                                      //~v0.1R~
	int  frow=0,fcol=0,ibox,row,col,samerowsw,samecolsw;
    int sameboxsw;                                                 //~v039I~
    int[] pilm;                                                    //~v0.1I~
//************
//System.out.println("method3 num="+Pnum+",seqno="+Ppnpwt.seqno);	//~va11R~
    mask=Smask[Pnum];
    pm99=Ppnpwt.m99;       //matrix of each plase                  //~v028I~
//row                                                              //~v039R~
    pilm=Ppnpwt.fmsk[0];      //line(row) fixed mask               //~v039R~
    pm99=Ppnpwt.m99;       //matrix of each plase                  //~v039R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~v039R~
    {                                                              //~v039R~
        if ((pilm[ii] & mask)!=0)  //already fixed in this row     //~v039R~
            continue;                                              //~v039R~
        sameboxsw=1;                                               //~v039R~
        for (cctr=0,jj=0;jj<MAP_SIZE;jj++)                         //~v039R~
        {                                                          //~v039R~
            if ((pm99[ii][jj].cmsk & mask)==0)  //not candidate    //~v039R~
                continue;                                          //~v039R~
            if (cctr!=0)    //not first time candidate             //~v039R~
                if (fcol/BOARDTYPE!=jj/BOARDTYPE)                  //~v039R~
                    sameboxsw=0;                                   //~v039R~
            cctr++;                                                //~v039R~
            fcol=jj;                                               //~v039R~
        }//col                                                     //~v039R~
        if (cctr==1)        //only one candidate                   //~v039R~
        {                                                          //~v039R~
            if (xnpfix(ii,fcol,Pnum,Ppnpwt,LEVEL_HARD,"m3R")!=0)   //~v039R~
                return -1;                                         //~v039R~
            return 1;                                              //~v039R~
        }                                                          //~v039R~
        if (sameboxsw!=0)       //to be dropped for other box      //~v039R~
            if ((rcw=xnpm3sub(Ppnpwt,0,ii,fcol,Pnum))!=0)   //0:box by row//~v039R~
                return rcw;                                        //~v039R~
    }//row                                                         //~v039R~
//col                                                              //~v039R~
    pilm=Ppnpwt.fmsk[1];      //line(col) fixed mask               //~v039R~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~v039R~
    {                                                              //~v039R~
        if ((pilm[jj] & mask)!=0)  //already fixed in this col     //~v039R~
            continue;                                              //~v039R~
        sameboxsw=1;                                               //~v039R~
        for (cctr=0,ii=0;ii<MAP_SIZE;ii++)                         //~v039R~
        {                                                          //~v039R~
            if ((pm99[ii][jj].cmsk & mask)==0)  //not candidate    //~v039R~
                continue;                                          //~v039R~
            if (cctr!=0)    //not first time candidate             //~v039R~
                if (frow/BOARDTYPE!=ii/BOARDTYPE)                  //~v039R~
                    sameboxsw=0;                                   //~v039R~
//printf("COL num=%d,cctr=%d,row=%d,col=%d,mask=%04x\n",Pnum,cctr,ii,jj,pm99.cmsk);//~v039R~
            cctr++;                                                //~v039R~
            frow=ii;                                               //~v039R~
        }//row                                                     //~v039R~
        if (cctr==1)        //only one candidate                   //~v039R~
        {                                                          //~v039R~
            if (xnpfix(frow,jj,Pnum,Ppnpwt,LEVEL_HARD,"m3R")!=0)   //~v039R~
                return -1;                                         //~v039R~
            return 1;                                              //~v039R~
        }                                                          //~v039R~
        if (sameboxsw!=0)                                          //~v039R~
            if ((rcw=xnpm3sub(Ppnpwt,1,frow,jj,Pnum))!=0)   //1:box by col//~v039R~
                return rcw;     //1:changed -1:fix err             //~v039R~
    }//col                                                         //~v039R~
//box
    pilm=Ppnpwt.fmsk[2];      //box fixed mask                     //~v0.1R~
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va01R~
    {
        if ((pilm[ibox] & mask)!=0)  //already fixed in this box   //~v0.1R~
        	continue;
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va01R~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va01R~
        samerowsw=1;
        samecolsw=1;
        for (cctr=0,ii=row;ii<row+BOARDTYPE;ii++)                  //~va01R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
            {
            	if ((pm99[ii][jj].cmsk & mask)==0)  //not candidate//~v0.1R~
            		continue;
                if (cctr!=0)    //not first time candidate
                {
                    if (ii!=frow)
                        samerowsw=0;
                    if (jj!=fcol)
                        samecolsw=0;
                }
                cctr++;
                frow=ii;
                fcol=jj;
//System.out.println("method3 box ibox="+ibox+",frow="+frow+",fcol="+fcol+",ccntr="+cctr+",samecol="+samecolsw+",samerow="+samerowsw+",mask="+ui2x("%08x",pm99[ii][jj].cmsk)+",Pnum="+Pnum);//~va16R~
			}
//System.out.println("method3 box ibox="+ibox+",frow="+frow+",fcol="+fcol+",ccntr="+cctr+",samecol="+samecolsw+",samerow="+samerowsw+",Pnum="+Pnum);//~v014R~
        if (cctr==0)                                               //~v028I~
            continue;                                              //~v028I~
        if (cctr==1)        //only one candidate
        {
            if (xnpfix(frow,fcol,Pnum,Ppnpwt,LEVEL_HARD,"m3B")!=0)
				return -1;
            return 1;
        }
        if (samerowsw!=0)
        	if ((rcw=xnpm3sub(Ppnpwt,2,frow,fcol,Pnum))!=0)	//2:row by box
        		return rcw;		//1:changed -1:fix err
        if (samecolsw!=0)
        	if ((rcw=xnpm3sub(Ppnpwt,3,frow,fcol,Pnum))!=0)	//2:col by box
        		return rcw;		//1:changed -1:fix err
    }//all box
    return 0;		//failed
}//xnpmethod3
//**********************************************************************
//* drop candidate mask
//* case:0:candidate in row is in the range of a box?
//*      1:candidate in col is in the range of a box?
//*      2:candidate in box is in the range of a row/col
//* rc:1:droped a candidate,0:no advancing,-1:fix err
//**********************************************************************
private int xnpm3sub(NPWORKT Ppnpwt,int Pcase,int Prow,int Pcol,int Pnum)//~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   ii,jj,mask,box,chngsw=0,rcw;                     //~v0.1R~
//************
	mask=Smask[Pnum];
    pm99=Ppnpwt.m99;       //matrix of each plase                  //~v0.1I~
//System.out.println("method3 case="+Pcase+",row="+Prow+",col="+Pcol+",num="+Pnum);//~v014I~
	switch(Pcase)
    {
//    case 0:         //in row,same box                            //~v028R~
//        row=Prow/BOARDTYPE*BOARDTYPE;                            //~v028R~
//        col=Pcol/BOARDTYPE*BOARDTYPE;                            //~v028R~

//        for (ii=row;ii<row+BOARDTYPE;ii++)                       //~v028R~
//            for (jj=col;jj<col+BOARDTYPE;jj++)                   //~v028R~
//            {                                                    //~v028R~
//                if ((pm99[ii][jj].cmsk & mask)==0)  //not candidate//~v028R~
//                    continue;                                    //~v028R~
//                if (ii==Prow)                                    //~v028R~
//                    continue;                                    //~v028R~
//                chngsw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,mask,"m3sr",LEVEL_HARD));//~v028R~
//                if (rcw<0)                                       //~v028R~
//                    return -1;                                   //~v028R~
//            }                                                    //~v028R~
//        break;                                                   //~v028R~
//    case 1:         //in col,same box                            //~v028R~
//        row=Prow/BOARDTYPE*BOARDTYPE;                            //~v028R~
//        col=Pcol/BOARDTYPE*BOARDTYPE;                            //~v028R~
//        for (ii=row;ii<row+BOARDTYPE;ii++)                       //~v028R~
//            for (jj=col;jj<col+BOARDTYPE;jj++)                   //~v028R~
//            {                                                    //~v028R~
//                if ((pm99[ii][jj].cmsk & mask)==0)  //not candidate//~v028R~
//                    continue;                                    //~v028R~
//                if (jj==Pcol)                                    //~v028R~
//                    continue;                                    //~v028R~
//                chngsw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,mask,"m3sc",LEVEL_HARD));//~v028R~
//                if (rcw<0)                                       //~v028R~
//                    return -1;                                   //~v028R~
//            }                                                    //~v028R~
//        break;                                                   //~v028R~
    case 2:			//in box,same row
        box=Pcol/BOARDTYPE;                                        //~va01R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
            if ((pm99[Prow][jj].cmsk & mask)==0)  //not candidate  //~v0.1R~
                continue;
            if (jj/BOARDTYPE==box)                                 //~va01R~
                continue;
            chngsw+=(rcw=xnpdropcan(Ppnpwt,Prow,jj,mask,"m3sbr",LEVEL_HARD));//~v0.1R~
            if (rcw<0)                                             //~v0.1I~
                return -1;                                         //~v0.1I~
		}//row
        break;
    case 3:			//in box,same col
        box=Prow/BOARDTYPE;                                        //~va01R~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va01R~
        {
            if ((pm99[ii][Pcol].cmsk & mask)==0)  //not candidate  //~v0.1R~
                continue;
            if (ii/BOARDTYPE==box)                                 //~va01R~
                continue;
            chngsw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol,mask,"m3sbc",LEVEL_HARD));//~v0.1R~
            if (rcw<0)                                             //~v0.1I~
                return -1;                                         //~v0.1I~
		}//row
        break;
    }//case
    if (chngsw!=0)
    	return xnpmethod4(Ppnpwt);
    return 0;
}//xnpm3sub
//**********************************************************************
//* search place where cnadidate count=1
//* rc:1:fixed a number,0:no advancing,-1:fix err
//**********************************************************************
private int xnpmethod4(NPWORKT Ppnpwt)                             //~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   ii,jj,mask;
//************
	pm99=Ppnpwt.m99;		//candidate count                      //~v0.1R~
//xnptrace(Ppnpwt);
	for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
		for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        	if (pm99[ii][jj].ccnt==1)                              //~v0.1R~
            {
            	mask=pm99[ii][jj].cmsk;                            //~v0.1R~
                if (xnpfixbymask(ii,jj,mask,Ppnpwt,LEVEL_HARD,"m4 ")!=0)
					return -1;
            	return 1;
			}
    return 0;		//0:no status changed
}//xnpmethod4
//**********************************************************************
//* search pair of 2 place has same 2 candidate                    //~v039R~
//* can be drop the two candidate from the unit.                   //~v039R~
//* for ex,when 2 place have candidate (3,5) on the same row/col,
//* the (3,5) should be droped from candidate from the other box of same line.
//* rc:0-not found,1:droped candidate,-1:data conflict             //~v0.1R~
//**********************************************************************
private int xnpmethod6(NPWORKT Ppnpwt)                             //~v0.1R~
{
	M99[][]  pm99;                                           //~v0.1R~
    M99      m99;                                                   //~va14I~
    int   ii,jj,kk,ll,ibox,row,col,mask,rcw;                    //~v035R~
    int cmask;                               //~v035I~
//************
//search pair in the row
    pm99=Ppnpwt.m99;        //candidate count                      //~v0.1R~
                                              //~v0.1I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {                                                              //~v029I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
        	m99=pm99[ii][jj];                                      //~va14I~
//printf("pair srch row=%d,col=%d, ccnt=%d,mask=%04x\n",ii,jj,pm99.ccnt,pm99.cmsk);//~v0.1R~
            if (m99.ccnt==2)  //2 candidate                        //~va14R~
        	if (m99.pair!=1)	//not once processed               //~va14R~
            {
                mask=m99.cmsk;                                     //~va14R~
//                cmaskxor=mask;                                   //~v039R~
//                cmaskor=mask;                                    //~v039R~
                for (kk=jj+1;kk<MAP_SIZE;kk++)                     //~va01R~
                {
		        	m99=pm99[ii][kk];                              //~v035I~
//printf("pair srch row=%d, col=%d-%d,mask=%04x-%04x\n",ii,jj,kk,mask,pm99p.cmsk);//~v0.1R~
                    cmask=m99.cmsk;                                //~v035I~
                    if (cmask==mask)  //pair with 2 candidate      //~v035R~
                    {                                              //~va11I~
                        if ((rcw=xnpm6sub(0,mask,ii,jj,ii,kk,Ppnpwt))!=0)  //0:row//~v014R~
                        {                                          //~v014R~
                            if (rcw<0)                             //~v014R~
                                return -1;                         //~v014R~
                            return 1;                              //~va14R~
                        }                                          //~v014R~
                    }                                              //~va11I~
//                    else                                         //~v039R~
//                    {                                            //~v039R~
//                        if (Ppnpwt.fcnt[0][ii]<MAP_SIZE-3)  //over 3 place remqins//~v039R~
//                        if (m99.ccnt==2)  //2 candidate          //~v039R~
//                        {                                        //~v039R~
//                            cmaskxor^=cmask; //pair with 2 candidate//~v039R~
//                            cmaskor|=cmask; //pair with 2 candidate//~v039R~
//                          if (xnpbitcount(cmaskor)==3)  //1 bit changes//~v039R~
//                          {                                      //~v039R~
//                            for (mm=kk+1;mm<MAP_SIZE;mm++)       //~v039R~
//                            {                                    //~v039R~
//                                m99=pm99[ii][mm];                //~v039R~
//                                cmask=m99.cmsk;                  //~v039R~
//                                if (cmask==cmaskxor  //pair with 2 candidate//~v039R~
//                                ||  cmask==cmaskor)  //pair with 2 candidate(abc,ab,bc patern)//~v039R~
//                                {                                //~v039R~
////if (m99.ccnt==3)                                               //~v039R~
////{                                                              //~v039R~
////System.out.println("row1 abc ab bc ");                         //~v039R~
////}                                                              //~v039R~
//                                    if ((rcw=xnpm6sub2(0,cmaskor,ii,jj,ii,kk,ii,mm,Ppnpwt))!=0)  //0:row//~v039R~
//                                    {                            //~v039R~
//                                        if (rcw<0)               //~v039R~
//                                            return -1;           //~v039R~
//                                        return 1;                //~v039R~
//                                    }                            //~v039R~
//                                }                                //~v039R~
//                            }                                    //~v039R~
//                            for (mm=0;mm<kk;mm++)   //search before 3 candidate cell//~v039R~
//                            {                                    //~v039R~
//                                m99=pm99[ii][mm];                //~v039R~
//                                cmask=m99.cmsk;                  //~v039R~
//                                if (cmask==cmaskor)  //pair with 2 candidate(abc,ab,bc patern)//~v039R~
//                                {                                //~v039R~
////if (m99.ccnt==3)                                               //~v039R~
////{                                                              //~v039R~
////System.out.println("row2 abc ab bc ");                         //~v039R~
////}                                                              //~v039R~
//                                    if ((rcw=xnpm6sub2(0,cmaskor,ii,jj,ii,kk,ii,mm,Ppnpwt))!=0)  //0:row//~v039R~
//                                    {                            //~v039R~
//                                        if (rcw<0)               //~v039R~
//                                            return -1;           //~v039R~
//                                        return 1;                //~v039R~
//                                    }                            //~v039R~
//                                }                                //~v039R~
//                            }                                    //~v039R~
//                          }//1bit differ                         //~v039R~
//                        }//2 cndidate                            //~v039R~
//                    }//mask unmatch                              //~v039R~
                }//right of a place
            }//2 candidate
        }//col
    }                                                              //~v029I~
//search pair in the col
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va01R~
    {
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va01R~
        {
        	m99=pm99[ii][jj];                                      //~va14I~
//printf("pair srch row=%d,col=%d,ccnt=%d, mask=%04x\n",ii,jj,pm99.ccnt,pm99.cmsk);//~v0.1R~
            if (m99.ccnt==2)                                       //~va14R~
            if (m99.pair!=1)	//not once processed               //~va14R~
            {
                mask=m99.cmsk;                                     //~va14R~
//                cmaskxor=mask;                                   //~v039R~
//                cmaskor=mask;                                    //~v039R~
                for (kk=ii+1;kk<MAP_SIZE;kk++)                     //~va01R~
                {
		        	m99=pm99[kk][jj];                              //~v035I~
//printf("pair srch col=%d, row=%d-%d,mask=%04x,%04x\n",jj,ii,kk,mask,pm99p.cmsk);//~v0.1R~
                    cmask=m99.cmsk;                                //~v035I~
                    if (cmask==mask)  //pair with 2 candidate      //~v035I~
                    {                                              //~va11I~
                        if ((rcw=xnpm6sub(1,mask,ii,jj,kk,jj,Ppnpwt))!=0)  //0:col//~v014R~
                        {                                          //~v014R~
                            if (rcw<0)                             //~v014R~
                                return -1;                         //~v014R~
                            return 1;                              //~va14R~
                        }                                          //~v014R~
                    }                                              //~va11I~
//                    else                                         //~v039R~
//                    {                                            //~v039R~
//                        if (Ppnpwt.fcnt[1][jj]<MAP_SIZE-3)  //over 3 place remqins//~v039R~
//                        if (m99.ccnt==2)  //2 candidate          //~v039R~
//                        {                                        //~v039R~
//                            cmaskxor^=cmask; //pair with 2 candidate//~v039R~
//                            cmaskor|=cmask; //pair with 2 candidate//~v039R~
//                          if (xnpbitcount(cmaskor)==3)  //1 bit changes//~v039R~
//                          {                                      //~v039R~
//                            for (mm=kk+1;mm<MAP_SIZE;mm++)       //~v039R~
//                            {                                    //~v039R~
//                                m99=pm99[mm][jj];                //~v039R~
//                                cmask=m99.cmsk;                  //~v039R~
//                                if (cmask==cmaskxor  //pair with 2 candidate//~v039R~
//                                ||  cmask==cmaskor)  //pair with 2 candidate(abc,ab,bc patern)//~v039R~
//                                {                                //~v039R~
//                                    if ((rcw=xnpm6sub2(1,cmaskor,ii,jj,kk,jj,mm,jj,Ppnpwt))!=0)  //0:row//~v039R~
//                                    {                            //~v039R~
//                                        if (rcw<0)               //~v039R~
//                                            return -1;           //~v039R~
//                                        return 1;                //~v039R~
//                                    }                            //~v039R~
//                                }                                //~v039R~
//                            }                                    //~v039R~
//                            for (mm=0;mm<kk;mm++)   //search before 3 candidate cell//~v039R~
//                            {                                    //~v039R~
//                                m99=pm99[mm][jj];                //~v039R~
//                                cmask=m99.cmsk;                  //~v039R~
//                                if (cmask==cmaskor)  //pair with 2 candidate(abc,ab,bc patern)//~v039R~
//                                {                                //~v039R~
////if (m99.ccnt==3)                                               //~v039R~
////{                                                              //~v039R~
////System.out.println("col2 abc ab bc ");                         //~v039R~
////}                                                              //~v039R~
//                                    if ((rcw=xnpm6sub2(1,cmaskor,ii,jj,kk,jj,mm,jj,Ppnpwt))!=0)  //0:row//~v039R~
//                                    {                            //~v039R~
//                                        if (rcw<0)               //~v039R~
//                                            return -1;           //~v039R~
//                                        return 1;                //~v039R~
//                                    }                            //~v039R~
//                                }                                //~v039R~
//                            }                                    //~v039R~
//                          }//1bit differ                         //~v039R~
//                        }//2 cndidate                            //~v039R~
//                    }//mask unmatch                              //~v039R~
                }
            }//pair has same two candidate
        }//all row
    }//all col
//search pair in the box
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va01R~
    {
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va01R~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va01R~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~va01R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
            {                                                      //~va14I~
	        	m99=pm99[ii][jj];                                  //~va14I~
            	if (m99.ccnt==2)                                   //~va14R~
        		if (m99.pair!=1)	//not once processed           //~va14R~
                {
//System.out.println("method6 box top row=="+ii+",col="+jj);       //~v031I~
                	mask=m99.cmsk;                                 //~va14R~
//                    cmaskxor=mask;                               //~v039R~
//                    cmaskor=mask;                                //~v039R~
                    ll=jj+1;                                       //~v039R~
                	for (kk=ii;kk<row+BOARDTYPE;kk++)              //~v039R~
                    {                                              //~v031I~
                    	for (;ll<col+BOARDTYPE;ll++)               //~v031I~
                        {                                          //~v031I~
				        	m99=pm99[kk][ll];                      //~v035I~
		                    cmask=m99.cmsk;                        //~v035I~
//System.out.println("method6 box nex chk row=="+kk+",col="+ll);   //~v031I~
                    		if (m99.cmsk==mask)  //pair with same twe candidate//~v035R~
                            {                                      //~va11I~
//System.out.println("method6 box mask="+ui2x("%08x",pm99p[kk][ll].cmsk)+",row1="+ii+",col1="+jj+",row2="+kk+",col2="+ll);//~va16R~
                                if ((rcw=xnpm6sub(2,mask,ii,jj,kk,ll,Ppnpwt))!=0)  //0:col//~v014R~
                                {                                  //~v014R~
                                    if (rcw<0)                     //~v014R~
                                        return -1;                 //~v014R~
  			                        return 1;                      //~va14R~
                                }                                  //~v014R~
                            }                                      //~va11I~
//                            else                                 //~v039R~
//                            {                                    //~v039R~
//                                if (Ppnpwt.fcnt[2][ibox]<MAP_SIZE-3)    //over 3 place remqins//~v039R~
//                                if (m99.ccnt==2)  //2 candidate  //~v039R~
//                                {                                //~v039R~
//                                    cmaskxor^=cmask; //pair with 2 candidate//~v039R~
//                                    cmaskor|=cmask; //pair with 2 candidate//~v039R~
//                                    mm=(kk*MAP_SIZE+ll+1)/MAP_SIZE;//~v039R~
//                                    nn=(kk*MAP_SIZE+ll+1)%MAP_SIZE;//~v039R~
//                                  if (xnpbitcount(cmaskor)==3)  //1 bit changes//~v039R~
//                                  {                              //~v039R~
//                                    for (;mm<row+BOARDTYPE;mm++) //~v039R~
//                                        for (;nn<col+BOARDTYPE;nn++)//~v039R~
//                                        {                        //~v039R~
//                                            m99=pm99[mm][nn];    //~v039R~
//                                            cmask=m99.cmsk;      //~v039R~
//                                            if (cmask==cmaskxor)  //pair with 2 candidate//~v039R~
//                                            {                    //~v039R~
////if (m99.ccnt==3)                                               //~v039R~
////{                                                              //~v039R~
////System.out.println("box1 abc ab bc ");                         //~v039R~
////}                                                              //~v039R~
//                                                if ((rcw=xnpm6sub2(2,cmaskor,ii,jj,kk,ll,mm,nn,Ppnpwt))!=0)  //0:row//~v039R~
//                                                {                //~v039R~
//                                                    if (rcw<0)   //~v039R~
//                                                        return -1;//~v039R~
//                                                    return 1;    //~v039R~
//                                                }                //~v039R~
//                                            }                    //~v039R~
//                                        }                        //~v039R~
//                                    for (mm=row;mm<row+BOARDTYPE;mm++)//~v039R~
//                                        for (nn=col;nn<col+BOARDTYPE;nn++)//~v039R~
//                                        {                        //~v039R~
//                                            m99=pm99[mm][nn];    //~v039R~
//                                            cmask=m99.cmsk;      //~v039R~
//                                            if (cmask==cmaskor)  //pair with 2 candidate(abc,ab,bc patern)//~v039R~
//                                            {                    //~v039R~
////if (m99.ccnt==3)                                               //~v039R~
////{                                                              //~v039R~
////System.out.println("box2 abc ab bc ");                         //~v039R~
////}                                                              //~v039R~
//                                                if ((rcw=xnpm6sub2(2,cmaskor,ii,jj,kk,ll,mm,nn,Ppnpwt))!=0)  //0:row//~v039R~
//                                                {                //~v039R~
//                                                    if (rcw<0)   //~v039R~
//                                                        return -1;//~v039R~
//                                                    return 1;    //~v039R~
//                                                }                //~v039R~
//                                            }                    //~v039R~
//                                        }                        //~v039R~
//                                  }//1 bit differ                //~v039R~
//                                }//2 cndidate                    //~v039R~
//                            }//mask unmatch                      //~v039R~
                        }                                          //~v039R~
                        ll=col;                                    //~v031I~
                    }//row in box                                  //~v031I~
                }
            }//jj                                                  //~va14I~
    }//all box
    return 0;
}//xnpmethod6
//**********************************************************************
//* drop candidate by pair,return candidate updated flag
//* rc:0-not changed,1:droped candidate,-1:data conflict           //~v0.1R~
//**********************************************************************
private int xnpm6sub(int Pcase,int Pmask,int Prow1,int Pcol1,int Prow2,int Pcol2,NPWORKT Ppnpwt)//~v0.1R~
{
	int ii,jj,row,col,mask,maskon1,maskon2,changesw=0,num1,num2,rcw;//~v0.1R~
    M99[][] pm99;                                                      //~v029I~
//************
	pm99=Ppnpwt.m99;                                               //~v029I~
  if (Ppnpwt.m99[Prow1][Pcol1].pair==1)	//once processed           //~va14R~
  	return 0;                                                      //~v014R~
//System.out.println("method6sub Pcase="+Pcase+",mask="+ui2x("%08x",Pmask)+",row1="+Prow1+",col1="+Pcol1+",row2="+Prow2+",col2="+Pcol2);//~va16R~
    Ppnpwt.m99[Prow1][Pcol1].pair=1;	//once processed id        //~v0.1R~
    Ppnpwt.m99[Prow2][Pcol2].pair=1;	//once processed id        //~v0.1R~
//get 2 candidate
    mask=Pmask;
    for (num1=1;num1<=MAP_SIZE;num1++)                             //~va01R~
    	if ((mask>>=1)==0)
        	break;
    mask=Pmask-Smask[num1];
    for (num2=1;num2<=MAP_SIZE;num2++)                             //~va01R~
    	if ((mask>>=1)==0)
        	break;
//printf("mask=%04x,num1=%d,num2=%d\n",Pmask,num1,num2);
	maskon1=Smask[num1];
	maskon2=Smask[num2];

    if (Pcase==0)               //pair in the row
    {
//row
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
            if (jj!=Pcol1 && jj!=Pcol2)
            {
              if ((pm99[Prow1][jj].cmsk & Pmask)!=0)                    //~v029I~
              {                                                    //~v029I~
                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,maskon1,"m6r",LEVEL_HARDP1));//~v0.1R~
            	if (rcw<0)                                         //~v0.1I~
                	return -1;                                     //~v0.1I~
                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,maskon2,"m6r",LEVEL_HARDP1));//~v0.1R~
            	if (rcw<0)                                         //~v0.1I~
                	return -1;                                     //~v0.1I~
              }                                                    //~v029I~
			}
    }//row
    if (Pcase==1)               //pair in the row
    {
//col
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va01R~
            if (ii!=Prow1 && ii!=Prow2)
            {
              if ((pm99[ii][Pcol1].cmsk & Pmask)!=0)                    //~v029I~
              {                                                    //~v029I~
                changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,maskon1,"m6c",LEVEL_HARDP1));//~v014R~
            	if (rcw<0)                                         //~v0.1I~
                	return -1;                                     //~v0.1I~
                changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,maskon2,",m6c",LEVEL_HARDP1));//~v0.1R~
            	if (rcw<0)                                         //~v0.1I~
                	return -1;                                     //~v0.1I~
              }                                                    //~v029I~
			}
    }//col
//box
    row=Prow1/BOARDTYPE*BOARDTYPE;         //top col of box        //~va01R~
    col=Pcol1/BOARDTYPE*BOARDTYPE;        //left col of box        //~va01R~
    if (row==Prow2/BOARDTYPE*BOARDTYPE && col==Pcol2/BOARDTYPE*BOARDTYPE)    //in same box?//~va01R~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~va01R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
                if (!(ii==Prow1 && jj==Pcol1)
                &&  !(ii==Prow2 && jj==Pcol2))
                {
	 	          if ((pm99[ii][jj].cmsk & Pmask)!=0)                   //~v029I~
              	  {                                                //~v029I~
                	changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,maskon1,"m6b",LEVEL_HARDP1));//~v0.1R~
	            	if (rcw<0)                                     //~v0.1I~
    	            	return -1;                                 //~v0.1I~
                	changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,maskon2,"m6b",LEVEL_HARDP1));//~v0.1R~
	            	if (rcw<0)                                     //~v0.1I~
    	            	return -1;                                 //~v0.1I~
                  }                                                //~v029I~
                }
    return (changesw!=0?1:0);
}//xnpm6sub
////**********************************************************************//~v039R~
////* drop candidate by pair,return candidate updated flag         //~v039R~
////* rc:0-not changed,1:droped candidate,-1:data conflict         //~v039R~
////**********************************************************************//~v039R~
//private int xnpm6sub2(int Pcase,int Pmask,int Prow1,int Pcol1,int Prow2,int Pcol2,int Prow3,int Pcol3,NPWORKT Ppnpwt)//~v039R~
//{                                                                //~v039R~
//    int ii,jj,row,col,mask,maskon1,maskon2,maskon3,changesw=0,num1,num2,num3,rcw;//~v039R~
//    int notsamebox=0,ibox;                                       //~v039R~
//    M99[][] pm99;                                                //~v039R~
////************                                                   //~v039R~
//    pm99=Ppnpwt.m99;                                             //~v039R~
//    if (Ppnpwt.m99[Prow1][Pcol1].pair==1)   //once processed     //~v039R~
//        return 0;                                                //~v039R~
////System.out.println("method6sub2 Pcase="+Pcase+",mask="+ui2x("%08x",Pmask)+",row1="+Prow1+",col1="+Pcol1+",row2="+Prow2+",col2="+Pcol2+",row3="+Prow3+",col3="+Pcol3);//~va16R~
////xnptrace(Ppnpwt);                                              //~v039R~
//    Ppnpwt.m99[Prow1][Pcol1].pair=1;    //once processed id      //~v039R~
//    Ppnpwt.m99[Prow2][Pcol2].pair=1;    //once processed id      //~v039R~
//    Ppnpwt.m99[Prow3][Pcol3].pair=1;    //once processed id      //~v039R~
////get 3 candidate                                                //~v039R~
//    mask=Pmask;                                                  //~v039R~
//    for (num1=1;num1<=MAP_SIZE;num1++)                           //~v039R~
//        if ((mask>>=1)==0)                                       //~v039R~
//            break;                                               //~v039R~
//    mask=Pmask-Smask[num1];                                      //~v039R~
//    for (num2=1;num2<=MAP_SIZE;num2++)                           //~v039R~
//        if ((mask>>=1)==0)                                       //~v039R~
//            break;                                               //~v039R~
//    mask=Pmask-Smask[num1]-Smask[num2];                          //~v039R~
//    for (num3=1;num3<=MAP_SIZE;num3++)                           //~v039R~
//        if ((mask>>=1)==0)                                       //~v039R~
//            break;                                               //~v039R~
////printf("mask=%04x,num1=%d,num2=%d\n",Pmask,num1,num2);         //~v039R~
//    maskon1=Smask[num1];                                         //~v039R~
//    maskon2=Smask[num2];                                         //~v039R~
//    maskon3=Smask[num3];                                         //~v039R~
//                                                                 //~v039R~
//    if (Pcase==0)               //pair in the row                //~v039R~
//    {                                                            //~v039R~
//        ibox=Pcol1/BOARDTYPE;                                    //~v039R~
////row                                                            //~v039R~
//        for (jj=0;jj<MAP_SIZE;jj++)                              //~v039R~
//            if (jj!=Pcol1 && jj!=Pcol2 && jj!=Pcol3)             //~v039R~
//            {                                                    //~v039R~
//              if ((pm99[Prow1][jj].cmsk & Pmask)!=0)             //~v039R~
//              {                                                  //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,maskon1,"m6r",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,maskon2,"m6r",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,maskon3,"m6r",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//              }                                                  //~v039R~
//            }                                                    //~v039R~
//            else                                                 //~v039R~
//                if (ibox!=jj/BOARDTYPE)                          //~v039R~
//                    notsamebox=1;                                //~v039R~
//                                                                 //~v039R~
//    }//row                                                       //~v039R~
//    else                                                         //~v039R~
//    if (Pcase==1)               //pair in the row                //~v039R~
//    {                                                            //~v039R~
////col                                                            //~v039R~
//        ibox=Prow1/BOARDTYPE;                                    //~v039R~
//        for (ii=0;ii<MAP_SIZE;ii++)                              //~v039R~
//            if (ii!=Prow1 && ii!=Prow2 && ii!=Prow3)             //~v039R~
//            {                                                    //~v039R~
//              if ((pm99[ii][Pcol1].cmsk & Pmask)!=0)             //~v039R~
//              {                                                  //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,maskon1,"m6c",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,maskon2,",m6c",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//                changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,maskon3,",m6c",LEVEL_HARDP1));//~v039R~
//                if (rcw<0)                                       //~v039R~
//                    return -1;                                   //~v039R~
//              }                                                  //~v039R~
//            }                                                    //~v039R~
//            else                                                 //~v039R~
//                if (ibox!=ii/BOARDTYPE)                          //~v039R~
//                    notsamebox=1;                                //~v039R~
//    }//col                                                       //~v039R~
//    if (Pcase==1||notsamebox==0)                                 //~v039R~
//    {                                                            //~v039R~
////box                                                            //~v039R~
//    row=Prow1/BOARDTYPE*BOARDTYPE;         //top col of box      //~v039R~
//    col=Pcol1/BOARDTYPE*BOARDTYPE;        //left col of box      //~v039R~
//    if (row==Prow2/BOARDTYPE*BOARDTYPE && col==Pcol2/BOARDTYPE*BOARDTYPE)    //in same box?//~v039R~
//        for (ii=row;ii<row+BOARDTYPE;ii++)                       //~v039R~
//            for (jj=col;jj<col+BOARDTYPE;jj++)                   //~v039R~
//                if (!(ii==Prow1 && jj==Pcol1)                    //~v039R~
//                &&  !(ii==Prow2 && jj==Pcol2)                    //~v039R~
//                &&  !(ii==Prow3 && jj==Pcol3))                   //~v039R~
//                {                                                //~v039R~
//                  if ((pm99[ii][jj].cmsk & Pmask)!=0)            //~v039R~
//                  {                                              //~v039R~
//                    changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,maskon1,"m6b",LEVEL_HARDP1));//~v039R~
//                    if (rcw<0)                                   //~v039R~
//                        return -1;                               //~v039R~
//                    changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,maskon2,"m6b",LEVEL_HARDP1));//~v039R~
//                    if (rcw<0)                                   //~v039R~
//                        return -1;                               //~v039R~
//                    changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,maskon3,"m6b",LEVEL_HARDP1));//~v039R~
//                    if (rcw<0)                                   //~v039R~
//                        return -1;                               //~v039R~
//                  }                                              //~v039R~
//                }                                                //~v039R~
//    }                                                            //~v039R~
//    return (changesw!=0?1:0);                                    //~v039R~
//}//xnpm6sub2                                                     //~v039R~
//**********************************************************************//~v039M~
//* abc,bcd,cda,dab case (parmcnt=4)                               //~v039I~
//* rc:0-not found,1:droped candidate,-1:data conflict             //~v039M~
//**********************************************************************//~v039M~
private int xnpmethod6x(NPWORKT Ppnpwt,int Ppairxcnt)              //~v039M~
{                                                                  //~v039M~
	M99[][]  pm99;                                                 //~v039M~
    M99      m99;                                                  //~v039M~
    int   ii,jj,kk,ll,ibox,row,col,rcw,pairxid,pairxcnt=0,morecnt; //~v041R~
    int mask1,mask2;          //~v039M~
//************                                                     //~v039M~
	pairxid=PAIRXBASE+Ppairxcnt;                                   //~v039M~
//search pair in the row                                           //~v039M~
    pm99=Ppnpwt.m99;        //candidate count                      //~v039M~
    morecnt=MAP_SIZE-Ppairxcnt;	//no reset bit(no remaining than 4)//~v041I~
//row                                                              //~v039M~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~v039M~
    {                                                              //~v039M~
    	if (Ppnpwt.fcnt[0][ii]>=morecnt)	//remains bit to be reset//~v041I~
        	continue;                                              //~v039M~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~v045R~
        {                                                          //~v039M~
        	m99=pm99[ii][jj];                                      //~v039M~
            if (m99.ccnt!=Ppairxcnt  //3 candidate                 //~v039R~
        	||  m99.pair==pairxid)	//not once processed           //~v039M~
            	continue;                                          //~v039M~
            mask1=m99.cmsk;                                        //~v039M~
            if ((mask1|Ppnpwt.fmsk[0][jj])==ALL_CANDIDATE)	//no other candidate to be reset//~v041I~
            	continue;                                          //~v041I~
            Sm6xpt[0].x=ii; Sm6xpt[0].y=jj;                        //~v039I~
			pairxcnt=1;                                            //~v039I~
//System.out.println("m6xr 1st iseqno="+Ppnpwt.idatano+" paircnt="+Ppairxcnt+" pos=("+ii+","+jj+") mask="+ui2x("%08x",mask1));//~va16R~
            for (kk=0;kk<MAP_SIZE;kk++)//search 2nd                //~v045R~
            {                                                      //~v039M~
                if (kk==jj)                                        //~v045I~
                    continue;                                      //~v045I~
	        	m99=pm99[ii][kk];                                  //~v039M~
		    	mask2=m99.cmsk;                                    //~v039M~
                if (mask2==0||(mask1|mask2)!=mask1)	//not contained in the first place candidate//~v039R~
                	continue;                                      //~v039I~
                Sm6xpt[pairxcnt].x=ii; Sm6xpt[pairxcnt].y=kk;      //~v039I~
				pairxcnt++;                                        //~v039I~
//System.out.println("m6xr 2nd iseqno="+Ppnpwt.idatano+" paircnt="+pairxcnt+"/"+Ppairxcnt+" pos=("+ii+","+kk+") mask1="+ui2x("%08x",mask1)+",mask2="+ui2x("%08x",mask2));//~va16R~
            }                                                      //~v039I~
            if (pairxcnt==Ppairxcnt)	//pair found               //~v039R~
            {                                                      //~v039R~
                rcw=xnpm6xsub(0,mask1,Sm6xpt,pairxcnt,Ppnpwt);  //0:row//~v039R~
                if (rcw<0)                                         //~v039R~
                    return -1;                                     //~v039R~
                if (rcw!=0)                                        //~v039R~
                    return 1;                                      //~v039R~
            }//pair found                                          //~v039R~
        }//col1                                                    //~v039M~
    }                                                              //~v039M~
//col                                                              //~v039M~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~v039M~
    {                                                              //~v039M~
    	if (Ppnpwt.fcnt[1][jj]>=morecnt)	//remains bit to be reset//~v041I~
        	continue;                                              //~v039M~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~v045R~
        {                                                          //~v039M~
        	m99=pm99[ii][jj];                                      //~v039M~
            if (m99.ccnt!=Ppairxcnt  //3 candidate                 //~v039R~
        	||  m99.pair==pairxid)	//not once processed           //~v039M~
            	continue;                                          //~v039M~
            mask1=m99.cmsk;                                        //~v039M~
            if ((mask1|Ppnpwt.fmsk[1][ii])==ALL_CANDIDATE)	//no other candidate to be reset//~v041I~
            	continue;                                          //~v041I~
            Sm6xpt[0].x=ii; Sm6xpt[0].y=jj;                        //~v039I~
			pairxcnt=1;                                            //~v039I~
//System.out.println("m6xc 1st iseqno="+Ppnpwt.idatano+" paircnt="+Ppairxcnt+" pos=("+ii+","+jj+") mask="+ui2x("%08x",mask1));//~va16R~
            for (kk=0;kk<MAP_SIZE;kk++)                            //~v045R~
            {                                                      //~v039M~
                if (kk==ii)                                        //~v045I~
                    continue;                                      //~v045I~
	        	m99=pm99[kk][jj];                                  //~v039M~
		    	mask2=m99.cmsk;                                    //~v039I~
                if (mask2==0||(mask1|mask2)!=mask1)	//not contained in the first place candidate//~v039R~
                	continue;                                      //~v039I~
                Sm6xpt[pairxcnt].x=kk; Sm6xpt[pairxcnt].y=jj;      //~v039I~
				pairxcnt++;                                        //~v039I~
//System.out.println("m6xc 2nd iseqno="+Ppnpwt.idatano+" paircnt="+pairxcnt+"/"+Ppairxcnt+" pos=("+ii+","+kk+") mask1="+ui2x("%08x",mask1)+",mask2="+ui2x("%08x",mask2));//~va16R~
            }                                                      //~v039I~
            if (pairxcnt==Ppairxcnt)    //pair found               //~v039R~
            {                                                      //~v039R~
                rcw=xnpm6xsub(1,mask1,Sm6xpt,pairxcnt,Ppnpwt);  //1:col//~v039R~
                if (rcw<0)                                         //~v039R~
                    return -1;                                     //~v039R~
                if (rcw!=0)                                        //~v039R~
                    return 1;                                      //~v039R~
            }//pair found                                          //~v039R~
        }//col1                                                    //~v039M~
    }                                                              //~v039M~
//search pair in the box                                           //~v039M~
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~v039M~
    {                                                              //~v039M~
    	if (Ppnpwt.fcnt[2][ibox]>=morecnt)	//remains bit to be reset//~v041I~
        	continue;                                              //~v039M~
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~v039M~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~v039M~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~v039M~
        {                                                          //~v039M~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~v039M~
            {                                                      //~v039M~
	        	m99=pm99[ii][jj];                                  //~v039M~
	            if (m99.ccnt!=Ppairxcnt  //3 candidate             //~v039R~
    	    	||  m99.pair==pairxid)	//not once processed       //~v039M~
            		continue;                                      //~v039M~
            	mask1=m99.cmsk;                                    //~v039M~
	            if ((mask1|Ppnpwt.fmsk[2][ibox])==ALL_CANDIDATE)	//no other candidate to be reset//~v041I~
    	        	continue;                                      //~v041I~
    	        Sm6xpt[0].x=ii; Sm6xpt[0].y=jj;                    //~v039I~
				pairxcnt=1;                                        //~v039I~
//System.out.println("m6xb 1st iseqno="+Ppnpwt.idatano+" paircnt="+Ppairxcnt+" pos=("+ii+","+jj+") mask="+ui2x("%08x",mask1));//~va16R~
                for (kk=row;kk<row+BOARDTYPE;kk++)                 //~v045R~
                {                                                  //~v039M~
                    for (ll=col;ll<col+BOARDTYPE;ll++)             //~v045R~
                    {                                              //~v039M~
                		if (kk==ii && ll==jj)                       //~v045I~
		                    continue;                              //~v045I~
                        m99=pm99[kk][ll];                          //~v039M~
				    	mask2=m99.cmsk;                            //~v039I~
                		if (mask2==0||(mask1|mask2)!=mask1)	//not contained in the first place candidate//~v039R~
                			continue;                              //~v039I~
                		Sm6xpt[pairxcnt].x=kk; Sm6xpt[pairxcnt].y=ll;//~v039I~
						pairxcnt++;                                //~v039I~
//System.out.println("m6xb 2nd iseqno="+Ppnpwt.idatano+" paircnt="+pairxcnt+"/"+Ppairxcnt+" pos=("+ii+","+kk+") mask1="+ui2x("%08x",mask1)+",mask2="+ui2x("%08x",mask2));//~va16R~
                    }                                              //~v039I~
                    if (pairxcnt==Ppairxcnt)    //pair found       //~v039R~
                    {                                              //~v039R~
                        rcw=xnpm6xsub(2,mask1,Sm6xpt,pairxcnt,Ppnpwt);  //1:col//~v039R~
                        if (rcw<0)                                 //~v039R~
                            return -1;                             //~v039R~
                        if (rcw!=0)                                //~v039R~
                            return 1;                              //~v039R~
                    }                                              //~v039R~
                    ll=col;                                        //~v039I~
                }//kk                                              //~v039M~
            }//jj                                                  //~v039M~
        }//ii                                                      //~v039M~
    }//all box                                                     //~v039M~
    return 0;                                                      //~v039M~
}//xnpmethod6x                                                     //~v039M~
//**********************************************************************//~v036I~
//* drop candidate by pair,return candidate updated flag           //~v036I~
//* rc:0-not changed,1:droped candidate,-1:data conflict           //~v036I~
//**********************************************************************//~v036I~
private int xnpm6xsub(int Pcase,int Pmask,Point[] Ppt,int Ppairxcnt,NPWORKT Ppnpwt)//~v038R~
{                                                                  //~v036I~
	M99[][]  pm99; M99 m99;                                                //~v038I~
	int ii,jj,kk,row,col,changesw=0,rcw,ibox,inboxcnt=0;      //~v038R~
    int tmppairxid=PAIRXBASE+PEG_MAX;                              //~v038I~
//************                                                     //~v038I~
    pm99=Ppnpwt.m99;        //candidate count                      //~v038I~
//System.out.println("m6xsub pair="+Ppairxcnt+",Pcase="+Pcase+",row="+Ppt[0].x+",col="+Ppt[0].y+",mask="+ui2x("%08X",Pmask));//~v045R~
//get 3-4 candidate                                                //~v038I~
    for (kk=0;kk<Ppairxcnt;kk++)                                    //~v038I~
    {                                                              //~v038I~
    	pm99[Ppt[kk].x][Ppt[kk].y].pair=tmppairxid;	//temporary fix id//~v038I~
//System.out.println("6xsub paircnt="+Ppairxcnt+",ii="+kk+",row="+Ppt[kk].x+",col="+Ppt[kk].y+",mask="+ui2x("%08x",pm99[Ppt[kk].x][Ppt[kk].y].cmsk));//~va16R~
    }                                                              //~v038I~
    if (Pcase==0)               //pair in the row                  //~v038I~
    {                                                              //~v038I~
//row                                                              //~v038I~
        ii=Ppt[0].x;                                               //~v038I~
    	ibox=Ppt[0].y/BOARDTYPE;                                   //~v038I~
	    for (jj=0;jj<MAP_SIZE;jj++)                                //~v038I~
        {                                                          //~v038I~
        	m99=pm99[ii][jj];                                      //~v038I~
            if (m99.pair==tmppairxid)                              //~v038R~
            {                                                      //~v038I~
                if (jj/BOARDTYPE==ibox)                            //~v038I~
                    inboxcnt++;                                    //~v038I~
            }                                                      //~v038I~
            else                                                   //~v038I~
            {                                                      //~v038I~
           		if ((m99.cmsk & Pmask)!=0)                         //~v038I~
                {                                                  //~v038I~
//System.out.println("before mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
	                changesw+=(rcw=xnpdropcanmulti(Ppnpwt,ii,jj,Pmask,"m6xr",LEVEL_HARDP2));//~v038I~
            		if (rcw<0)                                     //~v038I~
                		return -1;                                 //~v038R~
//if (Ppairxcnt==6)                                                //~v045R~
//{                                                                //~v045R~
//System.out.println("changesw="+changesw+" after mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
//}                                                                //~v045R~
                }                                                  //~v038I~
			}                                                      //~v038I~
        }                                                          //~v038I~
    }//row                                                         //~v038I~
    else                                                           //~v038I~
    if (Pcase==1)               //pair in the row                  //~v038I~
    {                                                              //~v038I~
//col                                                              //~v038I~
        jj=Ppt[0].y;                                               //~v038I~
    	ibox=Ppt[0].x/BOARDTYPE;                                   //~v038I~
	    for (ii=0;ii<MAP_SIZE;ii++)                                //~v038I~
        {                                                          //~v038I~
        	m99=pm99[ii][jj];                                      //~v038I~
            if (m99.pair==tmppairxid)                              //~v038I~
            {                                                      //~v038I~
                if (ii/BOARDTYPE==ibox)                            //~v038I~
                    inboxcnt++;                                    //~v038I~
            }                                                      //~v038I~
            else                                                   //~v038I~
            {                                                      //~v038I~
           		if ((m99.cmsk & Pmask)!=0)                         //~v038I~
                {                                                  //~v038I~
//System.out.println("before mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
	                changesw+=(rcw=xnpdropcanmulti(Ppnpwt,ii,jj,Pmask,"m6xc",LEVEL_HARDP2));//~v038I~
            		if (rcw<0)                                     //~v038I~
                		return -1;                                 //~v038I~
//if (Ppairxcnt==6)                                                //~v045R~
//{                                                                //~v045R~
//System.out.println("changesw="+changesw+" mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
//}                                                                //~v045R~
                }                                                  //~v038I~
			}                                                      //~v038I~
        }                                                          //~v038I~
    }//col                                                         //~v038I~
    if (Pcase==2||inboxcnt==Ppairxcnt)//same box in row/col        //~v038R~
    {                                                              //~v038I~
//box                                                              //~v038I~
        row=Ppt[0].x/BOARDTYPE*BOARDTYPE;         //top col of box    //~v038I~
        col=Ppt[0].y/BOARDTYPE*BOARDTYPE;        //left col of box    //~v038I~
    	ibox=row/BOARDTYPE*BOARDTYPE+col/BOARDTYPE;                //~v041R~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~v038I~
        {                                                          //~v038I~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~v038I~
            {                                                      //~v038I~
                m99=pm99[ii][jj];                                  //~v038I~
                if (m99.pair!=tmppairxid)                          //~v038I~
                {                                                  //~v038I~
                    if ((m99.cmsk & Pmask)!=0)                     //~v038I~
                    {                                              //~v038I~
//System.out.println("before mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
                        changesw+=(rcw=xnpdropcanmulti(Ppnpwt,ii,jj,Pmask,"m6xb",LEVEL_HARDP2));//~v038I~
                        if (rcw<0)                                 //~v038I~
	                		return -1;                             //~v038I~
//if (Ppairxcnt==6)                                                //~v045R~
//{                                                                //~v045R~
//System.out.println("changesw="+changesw+" mask="+ui2x("%08x",m99.cmsk)+" by "+ui2x("%08x",Pmask)+" row="+ii+" col="+jj);//~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
//}                                                                //~v045R~
                    }                                              //~v038I~
                }                                                  //~v038I~
            }                                                      //~v038I~
        }                                                          //~v038I~
    }                                                              //~v038I~
    for (kk=0;kk<Ppairxcnt;kk++)                                    //~v038I~
    	pm99[Ppt[kk].x][Ppt[kk].y].pair=PAIRXBASE+Ppairxcnt;	//used id//~v038I~
//if (Ppairxcnt==6)                                                //~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
    return (changesw!=0?1:0);                                      //~v038I~
}//xnpm6xsub                                                       //~v036I~
//**********************************************************************//~va11I~
//* search pair with same candidate;It mean other place of the line//~va11I~
//* can be drop the two candidate.                                 //~va11I~
//* rc:0-not found,1:droped candidate,-1:data conflict             //~va11I~
//**********************************************************************//~va11I~
private int xnpmethod8(NPWORKT Ppnpwt,int Psamecnt)                //~va11I~
{                                                                  //~va11I~
	M99[][]  pm99;                                                 //~va11R~
    int   ii,jj,kk,ll,ibox,row,col,mask,rcw,ccnt,morecnt;          //~v037R~
//************                                                     //~va11I~
//search pair in the row                                           //~va11I~
    pm99=Ppnpwt.m99;        //candidate count                      //~va11I~
    morecnt=MAP_SIZE-Psamecnt;                                     //~v029I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va11I~
    {                                                              //~v029I~
    	if (Ppnpwt.fcnt[0][ii]>=morecnt)	//remains bit to be reset//~v041R~
        	continue;                                              //~v029I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va11I~
        {                                                          //~va11I~
            if (pm99[ii][jj].ccnt==Psamecnt)  //3 or 4 candidate   //~va11I~
	        if (pm99[ii][jj].pair!=Psamecnt)	//not once processed//~va11I~
            {                                                      //~va11I~
                mask=pm99[ii][jj].cmsk;                            //~va11I~
                ccnt=1;                                            //~va11I~
                for (kk=jj+1;kk<MAP_SIZE;kk++)                     //~va11I~
                    if (pm99[ii][kk].cmsk==mask)  //pair with 2 candidate//~va11R~
                    	ccnt++;                                    //~va11I~
//                    else                                         //~v037R~
//                        if ((pm99[ii][kk].cmsk & mask)!=0)  //pair with 2 candidate//~v037R~
//                            resetsw=1;                           //~v037R~
//                if (ccnt==Psamecnt && resetsw!=0)                //~v037R~
                if (ccnt==Psamecnt)	//flag set required            //~v037I~
                	if ((rcw=xnpm8sub(0,mask,ii,jj,Psamecnt,Ppnpwt))!=0)  //0:row//~va11I~
                    {                                              //~va11I~
                     	if (rcw<0)                                 //~va11I~
                            	return -1;                         //~va11I~
                        	return 1;                              //~va11I~
					}                                              //~va11I~
            }                                                      //~va11I~
        }//col                                                     //~va11I~
    }                                                              //~v029I~
//search pair in the col                                           //~va11I~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va11I~
    {                                                              //~v029I~
    	if (Ppnpwt.fcnt[1][jj]>=morecnt)	//remains bit to be reset//~v041R~
        	continue;                                              //~v029I~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va11I~
        {                                                          //~va11I~
            if (pm99[ii][jj].ccnt==Psamecnt)                       //~va11I~
	        if (pm99[ii][jj].pair!=Psamecnt)	//not once processed//~va11I~
            {                                                      //~va11I~
                mask=pm99[ii][jj].cmsk;                            //~va11I~
                ccnt=1;                                            //~va11I~
                for (kk=ii+1;kk<MAP_SIZE;kk++)                     //~va11I~
                    if (pm99[kk][jj].cmsk==mask)  //pair with same twe candidate//~va11R~
                        ccnt++;                                    //~va11I~
//                    else                                         //~v037R~
//                        if ((pm99[kk][jj].cmsk & mask)!=0)       //~v037R~
//                            resetsw=1;                           //~v037R~
//                if (ccnt==Psamecnt && resetsw!=0)                //~v037R~
                if (ccnt==Psamecnt)                                //~v037I~
                	if ((rcw=xnpm8sub(1,mask,ii,jj,Psamecnt,Ppnpwt))!=0)  //0:col//~va11I~
                    {                                              //~va11I~
                        if (rcw<0)                                 //~va11I~
                            return -1;                             //~va11I~
                        return 1;                                  //~va11I~
                    }                                              //~va11I~
            }//pair has same two candidate                         //~va11I~
        }//all row                                                 //~va11I~
    }                                                              //~v029I~
//search pair in the box                                           //~va11I~
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va11I~
    {                                                              //~va11I~
    	if (Ppnpwt.fcnt[2][ibox]>=morecnt)	//remains bit to be reset//~v041R~
        	continue;                                              //~v029I~
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va11I~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va11I~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~va11I~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va11I~
            	if (pm99[ii][jj].ccnt==Psamecnt)                   //~va11I~
		        if (pm99[ii][jj].pair!=Psamecnt)	//not once processed//~va11I~
                {                                                  //~va11I~
                	mask=pm99[ii][jj].cmsk;                        //~va11I~
                    ccnt=1;                                        //~va11I~
                    ll=jj+1;                                       //~v039I~
                    for (kk=ii;kk<row+BOARDTYPE;kk++)              //~va11I~
                    {                                              //~v039I~
                    	for (;ll<col+BOARDTYPE;ll++)               //~v039R~
                    		if (pm99[kk][ll].cmsk==mask)  //pair with same twe candidate//~va11R~
                        		ccnt++;                            //~va11I~
//                            else                                 //~v037R~
//                                if ((pm99[kk][ll].cmsk & mask)!=0)//~v037R~
//                                    resetsw=1;                   //~v037R~
						ll=col;                                    //~v039I~
					}                                              //~v039I~
//                    if (ccnt==Psamecnt && resetsw!=0)            //~v037R~
                    if (ccnt==Psamecnt)                            //~v037I~
                        if ((rcw=xnpm8sub(2,mask,ii,jj,Psamecnt,Ppnpwt))!=0)  //0:col//~va11I~
		                {                                          //~va11I~
         			        if (rcw<0)                             //~va11I~
                    	    	return -1;                         //~va11I~
                        	return 1;                              //~va11I~
                        }                                          //~va11I~
                }                                                  //~va11I~
    }//all box                                                     //~va11I~
    return 0;                                                      //~va11I~
}//xnpmethod8                                                      //~va11I~
//**********************************************************************//~va11I~
//* drop candidate by pair,return candidate updated flag           //~va11I~
//* rc:0-not changed,1:droped candidate,-1:data conflict           //~va11I~
//**********************************************************************//~va11I~
private int xnpm8sub(int Pcase,int Pmask,int Prow1,int Pcol1,int Psamecnt,NPWORKT Ppnpwt)//~va11I~
{                                                                  //~va11I~
	M99[][]  pm99;                                                 //~va11I~
	int ii,jj,kk,row,col,mask,changesw=0,ccnt,rcw,ibox,notsamebox=0;//~v037R~
//************                                                     //~va11I~
    pm99=Ppnpwt.m99;        //candidate count                      //~va11I~
//System.out.println("method8sub samecnt="+Psamecnt+",Pcase="+Pcase+",mask="+ui2x("%08X",Pmask)+",row1="+Prow1+",col1="+Pcol1);//~v045R~
//if (Psamecnt>=6)                                                 //~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
//get 3-4 candidate                                                //~va11I~
    for (ii=1,ccnt=0;ii<=MAP_SIZE;ii++)                            //~v014R~
    	if ((Pmask & Smask[ii])!=0)                                     //~va11I~
        {                                                          //~va11I~
        	Smethod8num[ccnt]=ii;                                  //~v014R~
        	Smethod8mask[ccnt++]=Smask[ii];                        //~v014R~
        }                                                          //~va11I~
    if (Pcase==0)               //pair in the row                  //~va11I~
    {                                                              //~va11I~
//row                                                              //~va11I~
    	ibox=Pcol1/BOARDTYPE;                                      //~v037I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va11I~
            if (pm99[Prow1][jj].cmsk==Pmask)                       //~v027R~
            {                                                      //~v037I~
            	if (ibox!=jj/BOARDTYPE)                            //~v037I~
                	notsamebox=1;                                  //~v037I~
		        pm99[Prow1][jj].pair=Psamecnt;	//protect loop     //~v027M~
            }                                                      //~v037I~
            else                                                   //~v027I~
            {                                                      //~va11I~
//System.out.println("method8 ROW row="+Prow1+",col="+jj);         //~va12R~
              if ((pm99[Prow1][jj].cmsk & Pmask)!=0)               //~v029I~
				for (kk=0;kk<ccnt;kk++)                            //~va11I~
                {                                                  //~va11I~
                	mask=Smethod8mask[kk];                         //~va11I~
	                changesw+=(rcw=xnpdropcan(Ppnpwt,Prow1,jj,mask,"m8r",LEVEL_HARDP2));//~va11R~
            		if (rcw<0)                                     //~va11R~
                		return -1;                                 //~va11R~
                }                                                  //~va11I~
			}                                                      //~va11I~
    }//row                                                         //~va11I~
    else                                                           //~va11I~
    if (Pcase==1)               //pair in the row                  //~va11I~
    {                                                              //~va11I~
//col                                                              //~va11I~
    	ibox=Prow1/BOARDTYPE;                                      //~v037I~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va11I~
            if (pm99[ii][Pcol1].cmsk==Pmask)                       //~va11R~
            {                                                      //~v037I~
            	if (ibox!=ii/BOARDTYPE)                            //~v037I~
                	notsamebox=1;                                  //~v037I~
		        pm99[ii][Pcol1].pair=Psamecnt;	//protect loop     //~v027M~
            }                                                      //~v037I~
            else                                                   //~v027I~
            {                                                      //~va11I~
//System.out.println("method8 COL row="+ii+",col="+Pcol1);         //~va12R~
              if ((pm99[ii][Pcol1].cmsk & Pmask)!=0)	//remains bit to be reset//~v029I~
				for (kk=0;kk<ccnt;kk++)                            //~va11I~
                {                                                  //~va11I~
                	mask=Smethod8mask[kk];                         //~va11I~
    	            changesw+=(rcw=xnpdropcan(Ppnpwt,ii,Pcol1,mask,"m8c",LEVEL_HARDP2));//~v014R~
        	  	  	if (rcw<0)                                     //~va11R~
                		return -1;                                 //~va11R~
                }                                                  //~va11I~
			}                                                      //~va11I~
    }//col                                                         //~va11I~
    if (Pcase==2||notsamebox==0)	//same box in row/col          //~v037I~
    {                                                              //~va11I~
//box                                                              //~va11I~
        row=Prow1/BOARDTYPE*BOARDTYPE;         //top col of box    //~va11R~
        col=Pcol1/BOARDTYPE*BOARDTYPE;        //left col of box    //~va11R~
        for (ii=row;ii<row+BOARDTYPE;ii++)                         //~va11R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va11R~
                if (pm99[ii][jj].cmsk==Pmask)                      //~va11R~
			        pm99[ii][jj].pair=Psamecnt;	//protect loop     //~v027M~
                else                                               //~v027I~
                {                                                  //~va11R~
//System.out.println("method8 BOX row="+ii+",col="+jj);            //~va12R~
                  if ((pm99[ii][jj].cmsk & Pmask)!=0)              //~v029I~
					for (kk=0;kk<ccnt;kk++)                        //~va11I~
                	{                                              //~va11I~
                		mask=Smethod8mask[kk];                     //~va11I~
                    	changesw+=(rcw=xnpdropcan(Ppnpwt,ii,jj,mask,"m8b",LEVEL_HARDP2));//~va11R~
                    	if (rcw<0)                                 //~va11R~
                        	return -1;                             //~va11R~
                	}                                              //~va11I~
                }                                                  //~va11R~
    }                                                              //~va11I~
//if (Psamecnt>=6)                                                 //~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
    return (changesw!=0?1:0);                                      //~va11I~
}//xnpm8sub                                                        //~va11I~
//***************************************************************
//* try and err method
//* rc :1 sunccess,0: fail,4:success(multi level),-1:multiple solution or timeout//~v0.1R~
//* rc :28:all candidate prechk invalid                            //~va10I~
//***************************************************************
//private int xnpmethod9(NPWORKT Ppnpwt,int Pm9depth)              //~va05R~
private int xnpmethod9(int Popt,NPWORKT Ppnpwt,int Pm9depth)       //~va05I~
{
    int   row,col,kk,ccnt,cmsk,rc,num;
    int   okcnt,taelvl,level,lasttaelvl;                           //~v033R~
    int   multisolfnum;                                            //~va05I~
    NPWORKT pnpwt=null,pnpwtok;                                         //~v0.1R~
    String mcase;                                                  //~5930R~
    int[] pic;                                                     //~v0.1I~
    int[] clist;                                                   //~va01I~
    Point pt=new Point(0,0);                                                      //~v0.1I~
    int m9timeout=0;                                               //~va06I~
//************
	if (Pm9depth==0)         	//top level                        //~va05I~
		Smethod9npwtokdepth=0;	//success result to determin make data//~va05I~
    if (xnpmethod9timeoutchk(Popt,Pm9depth)!=0)                       //~va06I~
    {                                                              //~va06I~
    	return RC_M9TIMEOUT;                                       //~va06I~
    }                                                              //~va06I~
    clist=Smethod9clist[Pm9depth];                                 //~va01R~
    taelvl=Ppnpwt.taelvl;                                          //~v0.1R~
//System.out.println("xmethod9 Try and error depth="+taelvl);      //~v029R~
//    if (taelvl==NPWORKT.MAX_TAELVL)//max level reached           //~v032R~
//    {                                                            //~v032R~
//System.out.println("xmethod9 Try and error depth reached max="+taelvl+",depth="+Pm9depth);//~v032R~
//xnptrace(Ppnpwt);                                                //~v032R~
//        return 0;                                                //~v032R~
//    }                                                            //~v032R~
    taelvl++;
    if ((rc=xnpsrchleast(Ppnpwt,pt))<=0)                           //~v0.1R~
    	return rc;                                                 //~v0.1R~
    row=pt.x;col=pt.y;                                             //~v0.1I~
//System.out.println("method9 Depth="+Pm9depth+",row="+row+",col="+col+",ccnt="+Ppnpwt.m99[row][col].ccnt);//~va12R~
//if ((Popt & XNP_MULTISOL)!=0)	//multisol once save to Snpwtmultisol//~va15R~
  if ((Popt & XNP_MULTISOL)!=0 	//multisol once save to Snpwtmultisol//~va15I~
  &&  (Ppnpwt.stat & NPWORKT.STAT_MAKE)!=0)        //make mode     //~va15I~
  {								//set multisol data to  top of  candidate list//~va05M~
  	multisolfnum=asub2.Snpwtmultisol.m99[row][col].fnum;           //~va05M~
    cmsk=Ppnpwt.m99[row][col].cmsk;                                //~va05M~
	ccnt=0;                                                        //~va05M~
    if ((cmsk & Smask[multisolfnum])!=0)                           //~va05M~
    	clist[ccnt++]=multisolfnum;                                //~va05M~
//System.out.println("method9 multisol data idatano="+Ppnpwt.idatano+",row="+row+",col="+col+",num="+multisolfnum+",ccnt="+ccnt);//~v045R~
    for (kk=1;kk<=MAP_SIZE;kk++)                                   //~va05M~
    	if (kk!=multisolfnum)                                      //~va05M~
        	if ((cmsk & Smask[kk])!=0)                             //~va05M~
            	clist[ccnt++]=kk;                                  //~va05M~
  }                                                                //~va05M~
  else                                                             //~va05M~
  {                                                                //~va05M~
    if ((Ppnpwt.stat & NPWORKT.STAT_MAKE)!=0)   //make mode        //~v0.1R~
    {                                                              //~v033I~
//System.out.println("method9 make Depth="+Pm9depth+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",row="+row+",col="+col+",ccnt="+Ppnpwt.m99[row][col].ccnt);//~v036R~
//      if (Ppnpwt.m99[row][col].ccnt>2)   //for performance       //~v033R~
//      if (Ppnpwt.m99[row][col].ccnt>BOARDTYPE)   //for performance//~v041R~
        if (Ppnpwt.m99[row][col].ccnt>MAXPAIRCHK)  //for performance//~v041I~
        {                                                          //~v033I~
            return 0;
        }                                                          //~v033I~
    }                                                              //~v033I~
//listup candidate
	ccnt=0;
    cmsk=Ppnpwt.m99[row][col].cmsk;                                //~v0.1R~
    for (kk=1;kk<=MAP_SIZE;kk++)                                   //~va01R~
        if ((cmsk & Smask[kk])!=0)                                 //~v0.1R~
            clist[ccnt++]=kk;
//System.out.println("method 9 sort");                             //~va12R~
	ccnt=asub2.xnpsortbyeffectiveness(ccnt,clist,Smethod9effectivechkout,Smethod9effectivechkwk,Ppnpwt,Smethod9npwtwk,row,col);//~va10R~
    System.arraycopy(Smethod9effectivechkout,0,clist,0,ccnt);      //~va10I~
//System.out.println("method 9 after sort ccnt="+ccnt+",top="+clist[0]);//~va12R~
  }                                                                //~va05I~
//try up to end
//  pnpwt=new NPWORKT();                                           //~va01R~
//  pnpwtok=new NPWORKT();                                         //~va01R~
    pnpwt=Smethod9npwt[Pm9depth];                                  //~va05R~
    pnpwtok=Smethod9npwtok[Pm9depth];                              //~va05R~
//for (kk=0;kk<ccnt;kk++)                                          //~v033R~
//System.out.println("method9 clist="+clist[kk]);                  //~v033R~
//xnptrace(Ppnpwt);                                                //~v033R~
                                                                   //~va14I~
	for (kk=0,pic=clist,okcnt=0;kk<ccnt;kk++)                      //~v0.1R~
    {
//System.out.println("method9 kk="+kk+"<"+ccnt);                   //~va01R~
    	num=pic[kk]; 
    	pnpwt.copy(Ppnpwt);                                        //~5A01I~
	    pnpwt.taelvl=taelvl;                                       //~v0.1R~
        if (Stestsw==0)
	        pnpwt.stat&=~NPWORKT.STAT_PILOTMSG;		//no pilot msg when try and err//~v0.1R~
//printf("tryander start level=%d,row=%d,col=%d,num=%d\n",taelvl,row,col,num);
//xnpprintcan(pnpwt);
//printf("hard2 try and err fix\n");
//System.out.println("method9 before fix idatano="+pnpwt.idatano+",seqno="+pnpwt.seqno);//~v029R~
        if (Pm9depth==0)                                             //~v033I~
            level=LEVEL_HARD;                                      //~v033I~
        else                                                       //~v033I~
        if (Pm9depth<=2)                                             //~v033I~
            level=LEVEL_HARDP1;                                    //~v033I~
        else                                                       //~v033I~
            level=LEVEL_HARDP2;                                    //~v033I~
        mcase="m9:"+(new DecimalFormat("#0")).format(Pm9depth+1).toString();//~v033I~
//		rc=xnpfix(row,col,num,pnpwt,LEVEL_HARD,"m9");              //~v033R~
  		rc=xnpfix(row,col,num,pnpwt,level,mcase);                  //~v033I~
//System.out.println("method9 after fix idatano="+pnpwt.idatano+",seqno="+pnpwt.seqno+",rc="+rc+",row="+row+",col="+col+",num="+num);//~v033R~
		if (rc!=0)					//dup or inconsistent(made candidate to 0)
        	continue;
//System.out.println(asub2.MakeElapsedTime()+":method9 call xnpresolution depth="+Pm9depth+",idatano="+pnpwt.idatano);//~v045R~
//      rc=xnpsolution(pnpwt,Pm9depth+1);                          //~va05R~
        rc=xnpsolution(Popt,pnpwt,Pm9depth+1);                     //~va05I~
//printf("tryanderr end level=%d,row=%d,col=%d,num=%d,rc=%d\n",taelvl,row,col,num,rc);
//System.out.println(asub2.MakeElapsedTime()+":method9 xnpsolution idatano="+pnpwt.idatano+",seqno="+pnpwt.seqno+",depth="+Pm9depth+",okcnt="+okcnt+",rc="+rc+",stat="+(pnpwt.stat&(NPWORKT.STAT_DATA_CONFLICT|NPWORKT.STAT_MULTISOL)));//~v045R~
        if (rc==RC_M9TIMEOUT)                                      //~va06I~
        {                                                          //~va06I~
        	if (Pm9depth>Smethod9timeoutdepth)                     //~va06I~
            {                                                      //~va06I~
//System.out.println(asub2.MakeElapsedTime()+":M9TIMEOUT return ");//~va12R~
            	return rc;          //back to timeout level        //~va06I~
            }                                                      //~va06I~
//System.out.println(asub2.MakeElapsedTime()+":M9TIMEOUT continue");//~va12R~
            m9timeout=1;                                           //~va06R~
            continue;		//try next candidate                   //~va06R~
        }                                                          //~va06I~
		if ((pnpwt.stat & NPWORKT.STAT_MULTISOL)!=0)   //multiple solution//~v0.1R~
        {
        	okcnt=0;			//err process
			Ppnpwt.stat |= NPWORKT.STAT_MULTISOL;   //multiple solution//~v0.1R~
        	break;
		}
		if ((pnpwt.stat & NPWORKT.STAT_TIMEOUT)!=0)   //multiple solution//~v0.1R~
        {                                                          //~v0.1I~
        	okcnt=0;			//err process                      //~v0.1I~
			Ppnpwt.stat |= NPWORKT.STAT_TIMEOUT;   //timeout       //~v0.1R~
        	break;                                                 //~v0.1I~
		}                                                          //~v0.1I~
        if (rc==0)				//success
        {
//      	oknum=num;                                             //~v033R~
        	okcnt++;
//System.out.println("method9 solution rc=0 :depth="+Pm9depth+",seqno="+pnpwt.seqno+",okcnt="+okcnt+",row="+row+",col="+col+",num="+num);//~v033R~
//xnptrace(pnpwtok);                                               //~v033R~
            if (Stestsw!=0)
                xnpprintnum(2,0,0,pnpwt,0,null);   //print last data
            if (okcnt==1)
            {                                                      //~va05I~
				pnpwtok.copy(pnpwt);                               //~5A01I~
                if (Smethod9npwtokdepth==0)	//first success(deepest level)//~va05I~
                {                                                  //~v044I~
	                Smethod9npwtokdepth=Pm9depth;	//success result to determin make data//~va05I~
//System.out.println("1st set okdepth="+Smethod9npwtokdepth);      //~v045R~
                }                                                  //~v044I~
//  			if ((Popt & XNP_SOLOFMAKE)!=0)	//called from make //~va05R~
//              	break;					//accept 1st solution  //~va05R~
            }                                                      //~va05I~
			else				//question has multiple solution
            {
            	if (Stestsw!=0)
                	System.out.println("Vague data");
                okcnt=0;        //err process
                Ppnpwt.stat |= NPWORKT.STAT_MULTISOL;   //multiple solution//~v0.1R~
                if ((Ppnpwt.stat & (NPWORKT.STAT_RCHK|NPWORKT.STAT_MAKE))==0)    //not redundancy chk//~v0.1R~
                {
                    xnpprintnum(-3,0,0,pnpwtok,0,null);   //print last data
                    xnpprintnum(-4,0,0,pnpwt,0,null);   //print last data
                }
                break;
            }
		}
	}//all candidate at random
	lasttaelvl=pnpwtok.taelvl;                                     //~v0.1R~
	pnpwt=null;
	if ((Ppnpwt.stat & NPWORKT.STAT_TIMEOUT)!=0)   //multiple solution//~v0.1R~
    	return -1;                                                 //~v0.1I~
    if (m9timeout==1)                                                 //~va06I~
        if (okcnt==1)   //once timeout once success                //~va06I~
        {                                                          //~va06I~
            okcnt=0;        //err process                          //~va06I~
            Ppnpwt.stat|= NPWORKT.STAT_MULTISOL;   //multiple solution//~va06I~
        }                                                          //~va06I~
        else                                                       //~va06I~
            if ((Ppnpwt.stat & NPWORKT.STAT_MULTISOL)==0)   //no solution//~va06I~
	        	return RC_M9TIMEOUT;                               //~va06R~
    if (okcnt==0)
      if (ccnt==0)                                                 //~va10I~
      	return RC_M9NOCANDIDATE;                                    //~va10I~
      else                                                         //~va10I~
    	return 0;	//failed
    Ppnpwt.method=null;       //clear previos reset method            //~v0.1R~
//printf("tryanderr okcnt=%d,taelvl=%d\n",okcnt,taelvl);
    if (taelvl!=1)
    {
		Ppnpwt.taelvl=lasttaelvl;                                  //~v0.1R~
//System.out.println("xmethod9 Try and error rc=4 taelvl="+taelvl+",lasttaelvl="+lasttaelvl+",depth="+Pm9depth);//~v033R~
//xnptrace(Ppnpwt);                                                //~v033R~
//      return 4;			//step up now                          //~v033R~
        return RC_M9BACKTO0;			//back to depth 0          //~v033I~
	}
//if depth=0 return ok result                                      //~v033I~
	Ppnpwt.copy(Smethod9npwtok[Smethod9npwtokdepth]);	//success result to determin make data//~v033I~
//System.out.println("okdepth="+Smethod9npwtokdepth);              //~v045R~
//xnptrace(Ppnpwt);                                                //~v045R~
//    if (lasttaelvl==1)                                           //~v033R~
//        level=LEVEL_HARD;                                        //~v033R~
//    else                                                         //~v033R~
//    if (lasttaelvl<=3)                                           //~v033R~
//        level=LEVEL_HARDP1;                                      //~v033R~
//    else                                                         //~v033R~
//        level=LEVEL_HARDP2;                                      //~v033R~
//    mcase=(new DecimalFormat("m9:#0")).format(lasttaelvl).toString();//~v033R~
//xnpprintcan(pnpwt);
//  xnpfix(row,col,oknum,Ppnpwt,level,mcase);                      //~v033R~
//  return 1;                                                      //~v033R~
    return RC_M9FOUNDANS;                                          //~v033I~
}//xnpmethod9
//**********************************************************       //~va06I~
// timeout chk at each depth                                       //~va06I~
//**********************************************************       //~va06I~
private int xnpmethod9timeoutchk(int Popt,int Pdepth)              //~va06I~
{                                                                  //~va06I~
    long ctime,et;                                                 //~va06R~
    int ii;                                                        //~va06R~
//***********************************                              //~va06I~
	if ((Popt & XNP_M9TIMEOUT)==0)  //no timeout chk mode          //~va06I~
    	return 0;					                               //~va06I~
    if (Pdepth==0)                                                 //~va06I~
    {                                                              //~va06I~
        Smethod9timeoutdepth=0;                                    //~va06I~
    	Arrays.fill(Smethod9starttime,0);                          //~va06I~
    }                                                              //~va06I~
    ctime=(new Date()).getTime();                                  //~va06I~
    Smethod9starttime[Pdepth]=ctime;                               //~va06I~
    for (ii=Pdepth-1;ii>0;ii--)                                    //~va06I~
    {                                                              //~va06I~
	    et=ctime-Smethod9starttime[ii];                            //~va06I~
//System.out.println("m9timeout depth="+ii+",et="+et+",ct="+ctime);//~va06R~
	    if (et>M9TIMEOUT)	//top level has more allowance         //~va06I~
		    break;                                                 //~va06I~
    }                                                              //~va06I~
    if (ii<=0)                                                     //~va06I~
    	return 0;                                                  //~va06I~
    Smethod9timeoutdepth=ii;                                       //~va06R~
    for (ii=Pdepth-1;ii>0;ii--)                                    //~va06I~
    	Smethod9starttime[ii]=ctime;                               //~va06I~
//System.out.println(asub2.MakeElapsedTime()+":M9TIMEOUT depth="+Pdepth+",timeoutlevel="+Smethod9timeoutdepth);//~va12R~
    return 4;		//timeout                                      //~va06I~
}//xnpmethod9timeoutchk                                            //~va06I~
//**********************************************************************
//* search smallest candidate number place
//* rc:1:found,0:not found,-1:timeout                              //~v0.1R~
//**********************************************************************
private int xnpsrchleast(NPWORKT Ppnpwt,Point Ppt)                 //~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   ii,jj,minccnt,ccnt;
    String str1,str2;
//************
if (Gblwnpmode!=0)                                                 //~5A01R~
{                                                                  //~5A01I~
	if (GblSubthreadStopReq!=0)	//thread communication with wnp    //~5A01I~
        if (GblSubthreadStopReq==2)	//timeout                      //~v0.1I~
//      	uerrexit("Time Expired",                               //~5930R~//~v@@@R~
//  	    		"タイムアウト");                               //~v0.1I~//~v@@@R~
        	uerrexit(pContext.getText(R.string.ErrTimeout).toString());//~v@@@R~
        else                                                       //~v0.1I~
//      	uerrexit("Forced Thread Termination",                  //~v0.1I~//~v@@@R~
//  	    		"強制終了");                                   //~v0.1I~//~v@@@R~
        	uerrexit(pContext.getText(R.string.ErrBreaked).toString());//~v@@@R~
}                                                                  //~5A01I~
else                                                               //~5A01R~
{                                                                  //~5A01I~
    if (Stotloop==0)                                                 //~5A01R~
        Smsgtm=Stottm0=System.currentTimeMillis();                         //~5A01R~
    if ((++Stotloop % MSG_CHK_FREQ)==0)                            //~5A01R~
    {                                                              //~5A01R~
        tottm=System.currentTimeMillis();                          //~5A01R~
        if (Stmsgfreq!=0 && tottm-Smsgtm>=Stmsgfreq)    //each msg freq//~5A01R~
        {                                                          //~5A01R~
//          System.err.println("processing ... "+(new DecimalFormat("####0")).format(Stotloop)+" times trial,"+(new DecimalFormat("###0")).format((int)(tottm-Stottm0)/1000)+" sec expired.");//~5A01R~//~v@@@R~
            Smsgtm=tottm;                                          //~5A01R~
        }                                                          //~5A01R~
        if (Srepeatmax!=0 && tottm-Stottm0>=Srepeatmax)            //~5A01R~
        {                                                          //~5A01R~
            if ((Ppnpwt.stat & NPWORKT.STAT_MAKE)==0)              //~5A01R~
            {                                                      //~5A01R~
//              uerrmsg("Timeout;"+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+" sec expired. ===",//~5A01R~//~v@@@R~
//                      "時間切れ;"+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+" 秒経過 ===");//~5A01R~//~v@@@R~
    			str1=pContext.getText(R.string.Timeout).toString();//~v@@@R~
    			str2=pContext.getText(R.string.SecElapsed).toString();//~v@@@R~
                uerrmsg(str1+";"+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+str2+". ===");//~v@@@R~
                Ppnpwt.stat|=NPWORKT.STAT_TIMEOUT;                 //~5A01R~
            }                                                      //~5A01R~
            return -1;              //stop calc                    //~5A01R~
        }                                                          //~5A01R~
    }                                                              //~5A01R~
}                                                                  //~5A01R~
    pm99=Ppnpwt.m99;        //candidate count                      //~v0.1R~
    for (ii=0,minccnt=MAP_SIZE+1;ii<MAP_SIZE;ii++)                 //~va01R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
            ccnt=pm99[ii][jj].ccnt;                                //~v0.1R~
            if (ccnt!=0 && ccnt<minccnt)                           //~v0.1R~
            {
            	minccnt=ccnt;
                Ppt.x=ii;                                          //~v0.1R~
                Ppt.y=jj;                                          //~v0.1R~
            }
		}
    return ((minccnt!=MAP_SIZE+1)?1:0); 	//found                //~va01R~
}//xnpsrchleast
////**********************************************************************//~va06R~
////* search smallest candidate number place                       //~va06R~
////* rc:1:found,0:not found,-1:timeout                            //~va06R~
////**********************************************************************//~va06R~
//private int xnpsrchnextfixed(NPWORKT Ppnpwt,int Pcurseqno,Point Ppt)//~va06R~
//{                                                                //~va06R~
//    M99[][]  pm99;                                               //~va06R~
//    int   nextfix,ii,jj;                                         //~va06R~
////************                                                   //~va06R~
//    pm99=Ppnpwt.m99;        //candidate count                    //~va06R~
//    nextfix=Pcurseqno+1;                                         //~va06R~
//    for (ii=0;ii<MAP_SIZE;ii++)                                  //~va06R~
//    {                                                            //~va06R~
//        for (jj=0;jj<MAP_SIZE;jj++)                              //~va06R~
//            if (pm99[ii][jj].fseq==nextfix)                      //~va06R~
//            {                                                    //~va06R~
//                Ppt.x=ii;                                        //~va06R~
//                Ppt.y=jj;                                        //~va06R~
//                return 1;                                        //~va06R~
//            }                                                    //~va06R~
//    }                                                            //~va06R~
//    return 0;   //err                                            //~va06R~
//}//xnpsrchnextfixed                                              //~va06R~
//**********************************************************************//~v038I~
//* multibit dropcan                                               //~v038I~
//* can be drop the two candidate.                                 //~v038I~
//* rc:0-not found,1:droped candidate,-1:data conflict             //~v038I~
//**********************************************************************//~v038I~
public int xnpdropcanmulti(NPWORKT Ppnpwt,int Prow,int Pcol,int Pmask,String Pcase,int Plevel)//~v038I~
{                                                                  //~v038I~
	int ii,mask,changesw=0,rcw; 
	M99 m99;//~v038I~
//************                                                     //~v038I~
    m99=Ppnpwt.m99[Prow][Pcol];                                    //~v038R~
//System.out.println("dropmulti case="+Pcase+",row="+Prow+",Col="+Pcol+",Pmask="+ui2x("%08x",Pmask)+",tmask="+ui2x("%08x",m99.cmsk));//~va16R~
	for (ii=1;ii<=MAP_SIZE;ii++)                                   //~v038R~
    {                                                              //~v038I~
	    mask=m99.cmsk;        //candidate count                    //~v038I~
    	if ((mask & (Pmask & Smask[ii]))!=0)                       //~v038I~
    	{//~v038I~
	    	changesw+=(rcw=xnpdropcan(Ppnpwt,Prow,Pcol,Smask[ii],Pcase,Plevel));//~v038I~
//System.out.println("dropmulti "+ui2x("%08x",mask)+"-->"+ui2x("%08x",Ppnpwt.m99[Prow][Pcol].cmsk)+" by "+ui2x("%08x",Pmask));//~va16R~
          	if (rcw<0)                                         //~v038I~
               	return -1;
    	}//~v038I~
    }                                                              //~v038I~
//xnptrace(Ppnpwt);                                                //~v039R~
    return (changesw!=0?1:0);                                      //~v038I~
}//xnpdropcanmulti                                                 //~v038I~
//**********************************************************************
//* drop candidate bit sub
//* rc:0-not changed,1:droped candidate,-1:reached to candidate cond//~v0.1R~
//**********************************************************************
private int xnpdropcan(NPWORKT Ppnpwt,int Prow,int Pcol,int Pmask,String Pcase,int Plevel)//~v0.1R~
{
    M99 m99;                                                       //~v0.1I~
    int rc=0;                                                      //~v027I~
//***************************
	m99=Ppnpwt.m99[Prow][Pcol];                                    //~v0.1R~
//if (Prow==1 && Pcol==3)                                          //~v038R~
//{                                                                //~v038R~
//System.out.println("dropcan case="+Pcase+",row="+Prow+",col="+Pcol+",mask="+ui2x("%08X",Pmask)+",cmask="+ui2x("%08x",m99.cmsk));//~va16R~
//xnptrace(Ppnpwt);                                                //~v038R~
//}                                                                //~v038R~
    if ((m99.cmsk & Pmask)!=0)                                          //~v0.1R~
    {
        if (Stestsw!=0)
        {
			xnpprintcan(Ppnpwt);
//  		System.out.println("dropc row="+Prow+",col="+Pcol+",mask="+ui2x("%08x",Pmask)+",case="+Pcase);//~v028R~
                                                                   //~5930I~
		}
//if (Prow==12 && Pcol==8)                                         //~v014R~
//{                                                                //~v014R~
//System.out.println("dropcan case="+Pcase+",row="+Prow+",col="+Pcol+",before mask="+ui2x("%08X",m99.cmsk)+",req mask="+ui2x("%08x",Pmask));//~va16R~
//xnptrace(Ppnpwt);                                                //~v014R~
//}                                                                //~v014R~
        m99.cmsk&=(ALL_CANDIDATE-Pmask);                           //~v0.1R~
//if (Prow==12 && Pcol==8)                                         //~v014R~
//{                                                                //~v014R~
//System.out.println("dropcan case="+Pcase+",row="+Prow+",col="+Pcol+",after mask="+ui2x("%08X",m99.cmsk));//~v014R~
//}                                                                //~v014R~
		m99.ccnt--;                             //~v0.1R~
		if (m99.ccnt==0)                          //~v0.1R~
        {                                                          //~va11I~
//System.out.println("dropcan fail");                              //~v029R~
//xnptrace(Ppnpwt);                                                //~v029R~
        	return -1;                                             //~v0.1I~
        }                                                          //~va11I~
        Ppnpwt.level|=Plevel;  //hard sw on after prev             //~v0.1R~
        Ppnpwt.method=Pcase;     //                                //~v0.1R~
//xnpprintcan(Ppnpwt);
		rc=1;		//status changed                               //~v027I~
    }
    else                                                           //~v014I~
    {                                                              //~v014I~
//System.out.println("dropcan rc=0  mask="+ui2x("%08x",m99.cmsk)); //~va16R~
//xnptrace(Ppnpwt);                                                //~v029R~
    }                                                              //~v014I~
//xnptrace(Ppnpwt);                                                //~va11R~
//  return 1;                                                      //~v027R~
    return rc;                                                     //~v027I~
}//xnpdropcan
//***************************************************************
//* interface to xnpfix by  candidate mask
//* rc : 0:ok ,-1:dup err 4:inconsistent
//***************************************************************
public int xnpfixbymask(int Prow,int Pcol,int Pmask,NPWORKT Ppnpwt,int Plevel,String Pcase)//~5930R~
{
	int num,mask;
//************
	mask=Pmask;
    for (num=1;num<=MAP_SIZE;num++)                                //~va01R~
        if ((mask>>=1)==0)
            break;
	return xnpfix(Prow,Pcol,num,Ppnpwt,Plevel,Pcase);
}//xnpfixbymask
//***************************************************************
//* set fixed number,drop other place candidate
//* rc : 0:ok ,-1:dup err,4:inconsistent(made cnadidate cnt=0)
//***************************************************************
public int xnpfix(int Prow,int Pcol,int Pnum,NPWORKT Ppnpwt,int Plevel,String Pcase)//~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    M99 m99;                                                       //~v0.1I~
	int ii,jj,boxrow,boxcol,ibox,maskon,maskof,rc=0,row,col,errsw1=0;
	String str1,str2,str3,str4;
//************
//if (Prow==1 && Pcol==3)                                          //~v038R~
//{                                                                //~v038R~
//System.out.println("xnpfixdropcan row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase);//~v038R~
//xnptrace(Ppnpwt);                                                //~v038R~
//}                                                                //~v038R~
//  if (Stestsw)
//  	xnptrace(Ppnpwt);
//xnpprintcan(Ppnpwt);
	maskon=Smask[Pnum];
	maskof=ALL_CANDIDATE-maskon;
    boxrow=Prow/BOARDTYPE;                                         //~va01R~
    boxcol=Pcol/BOARDTYPE;                                         //~va01R~
    ibox=boxrow*BOARDTYPE+boxcol;                                  //~va01R~
//set fixed info
    Ppnpwt.level |=Plevel;		//dificulty level                  //~v0.1R~
    Ppnpwt.m99[Prow][Pcol].fnum=Pnum;				//number fixed //~v0.1R~
    if (Plevel==LEVEL_INIT)		//initial data setting
    	Ppnpwt.idatano++;                                          //~v0.1R~
    Ppnpwt.m99[Prow][Pcol].fseq=++Ppnpwt.seqno;     //seqno up     //~v0.1R~
//dup chk before fixed mask on
	if ((Ppnpwt.fmsk[0][Prow] & maskon)!=0	//already fixed the number in the row//~v0.1R~
	||  (Ppnpwt.fmsk[1][Pcol] & maskon)!=0	//already fixed the number in the col//~v0.1R~
	||  (Ppnpwt.fmsk[2][ibox] & maskon)!=0)	//already fixed the number in the box//~v0.1R~
    {
	    if (Ppnpwt.taelvl!=0)                                         //~v0.1R~
            return -1;
        if ((Ppnpwt.stat & (NPWORKT.STAT_RCHK|NPWORKT.STAT_MAKE))!=0)    //not redundancy chk//~v0.1R~
            return -1;
    	if (Plevel!=LEVEL_INIT)		//initial data setting
        {
	    	xnpprintnum(1,Prow,Pcol,Ppnpwt,Plevel,null);	//print answer as err
        	str1=pContext.getText(R.string.ErrDuplicated1).toString();//~v@@@R~
        	str2=pContext.getText(R.string.ErrDuplicated2).toString();//~v@@@R~
        	str3=pContext.getText(R.string.ErrDuplicated3).toString();//~v@@@R~
        	str4=pContext.getText(R.string.ErrDuplicated4).toString();//~v@@@R~
//          uerrexit("\nNumber "+Pnum+" at ("+Prow+","+Pcol+") is duplicated !!\n",//~5930R~//~v@@@R~
//          		"\nナンバー "+Pnum+" が  "+Prow+" 行, "+Pcol+" 列 にセットできません!!\n");//~5930R~//~v@@@R~
            uerrexit("\n"+str1+Pnum+str2+Prow+str3+Pcol+str4+"\n");//~v@@@I~
		}
        if (Stestsw!=0)
            System.out.println("fix failed dup data row="+Prow+",col="+Pcol+",num="+Pnum);//~5930R~
        rc=-1;			//initial data case
	}
    Ppnpwt.fmsk[0][Prow]|=maskon; //fixed number mask,row          //~v0.1R~
	Ppnpwt.fmsk[1][Pcol]|=maskon; //fixed number mask,col          //~v0.1R~
	Ppnpwt.fmsk[2][ibox]|=maskon;//fixed number mask,box           //~v0.1R~
    Ppnpwt.fcnt[0][Prow]++; //fixed number count,row               //~v0.1R~
	Ppnpwt.fcnt[1][Pcol]++; //fixed number count,col               //~v0.1R~
	Ppnpwt.fcnt[2][ibox]++;//fixed number count,box                //~v0.1R~
    if (Plevel==LEVEL_INIT)		//initial data setting
    {
	    Ppnpwt.icnt[0][Prow]++;//init data cnt for make puzzle     //~v0.1R~
        Ppnpwt.icnt[1][Pcol]++;//init data cnt for make puzzle     //~v0.1R~
        Ppnpwt.icnt[2][ibox]++;//init data cnt for make puzzle     //~v0.1R~
    }
    Ppnpwt.m99[Prow][Pcol].cmsk=0;                   //no candidate//~v0.1R~
    Ppnpwt.m99[Prow][Pcol].ccnt=0;                                 //~v0.1R~
    Ppnpwt.m99[Prow][Pcol].dlvl=Plevel;   //0:init,                //~v0.1R~
//drop candidate
//row
    pm99=Ppnpwt.m99;                                               //~v0.1R~
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va01R~
    {                                                              //~v0.1I~
    	m99=pm99[Prow][jj];                                        //~v0.1I~
        if ((m99.cmsk & maskon)!=0) //now candidate                //~v0.1R~
        {
//printf("row can off num=%d,(%d,%d) by (%d,%d) mask=%04x\n",Pnum,Prow,jj,Prow,Pcol,pm99.cmsk);//~v0.1R~
//if (Prow==12 && jj==0)                                           //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ row fix mask off before="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
            m99.cmsk &=maskof; //drop candidate                    //~v0.1R~
//if (Prow==12 && jj==0)                                           //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ row fix mask off after="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
            if (m99.ccnt!=0)                                       //~v0.1R~
            {
                if (--m99.ccnt==0)                                 //~v0.1R~
                    rc=4;           //no candidate place
                if (Stestsw!=0)
                    xnpcmchk(Ppnpwt,m99.ccnt,m99.cmsk);            //~v0.1R~
            }
            else
                errsw1=1;
        }
    }                                                              //~v0.1I~
//col
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {                                                              //~v0.1I~
    	m99=pm99[ii][Pcol];                                        //~5A01R~
        if ((m99.cmsk & maskon)!=0) //now candidate                 //~v0.1R~
        {
//printf("col can off num=%d,(%d,%d) by (%d,%d) mask=%04x\n",Pnum,ii,Pcol,Prow,Pcol,pm99.cmsk);//~v0.1R~
//if (ii==12 && Pcol==0)                                           //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ col fix mask off before="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
            m99.cmsk &=maskof; //drop candidate                    //~v0.1R~
//if (ii==12 && Pcol==0)                                           //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ col fix mask off after="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
            if (m99.ccnt!=0)                                          //~v0.1R~
            {
                if (--m99.ccnt==0)                                 //~v0.1R~
                    rc=4;           //no candidate place
                if (Stestsw!=0)
                    xnpcmchk(Ppnpwt,m99.ccnt,m99.cmsk);            //~v0.1R~
            }
            else
                errsw1=1;
        }
    }                                                              //~v0.1I~
//box
    row=boxrow*BOARDTYPE;                                          //~va01R~
    col=boxcol*BOARDTYPE;                                          //~va01R~

    for (ii=row;ii<row+BOARDTYPE;ii++)                             //~va01R~
    {                                                              //~v0.1I~
        for (jj=col;jj<col+BOARDTYPE;jj++)                         //~va01R~
        {                                                          //~v0.1I~
	    	m99=pm99[ii][jj];                                      //~v0.1I~
            if ((m99.cmsk & maskon)!=0) //now candidate            //~v0.1R~
            {
//if (ii==12 && jj==0)                                             //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ box fix mask off before="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
//printf("box can off num=%d,(%d,%d) by (%d,%d) mask=%04x\n",Pnum,ii,jj,Prow,Pcol,pm99.cmsk);//~v0.1R~
                m99.cmsk &=maskof; //drop candidate                //~v0.1R~
//if (ii==12 && jj==0)                                             //~v014R~
//{                                                                //~v014R~
//System.out.println("@@@@ box fix mask off after="+ui2x("%08x",m99.cmsk)+",row="+Prow+",col="+Pcol+",num="+Pnum+",case="+Pcase+",level="+Plevel);//~va16R~
//}                                                                //~v014R~
                if (m99.ccnt!=0)                                      //~v0.1R~
                {
                    if (--m99.ccnt==0)                             //~v0.1R~
                        rc=4;           //no candidate place
                    if (Stestsw!=0)
                        xnpcmchk(Ppnpwt,m99.ccnt,m99.cmsk);        //~v0.1R~
                }
                else
                    errsw1=1;
            }
        }                                                          //~v0.1I~
    }                                                              //~v0.1I~
	if ((Ppnpwt.stat & NPWORKT.STAT_PILOTMSG)!=0)                  //~v0.1R~
    	if (Plevel!=LEVEL_INIT)		//initial data setting
		    xnpprintnum(1,Prow,Pcol,Ppnpwt,Plevel,Pcase);	//print intermediate
    Ppnpwt.level &=~LEVEL_FIX; //once chked of each fix case       //~v0.1R~
    if (errsw1!=0)
    {
    	xnptrace(Ppnpwt);
//      uerrexit("Internal Logic Err;cmsk and ccnt conflict",null);		//inconsistent//~5930R~//~v@@@R~
        uerrexit("Internal Logic Err;cmsk and ccnt conflict");		//inconsistent//~v@@@I~
	}
    if (rc!=0)
        Ppnpwt.stat|=NPWORKT.STAT_DATA_CONFLICT;            //problem err//~v0.1R~
//xnpprintcan(Ppnpwt);
//printf("pnpwt=%08x,level=%04x\n",Ppnpwt,Ppnpwt.level);           //~v0.1R~
    return rc;		//ok
}//xnpfix
//***************************************************************
//* count mask conflict chk for internal logic err when Stestsw on
//* rc : 1 if err
//***************************************************************
private void xnpcmchk(NPWORKT Ppnpwt,int Pcnt,int Pmsk)            //~v0.1R~
{
    int ii,cnt;
//************
    for (ii=1,cnt=0;ii<=MAP_SIZE;ii++)                             //~va01R~
    	if ((Pmsk & Smask[ii])!=0)                                 //~v0.1R~
        	cnt++;
	if (cnt!=Pcnt)
    {
    	xnptrace(Ppnpwt);
        uerrexit("Internal Logic Err;count("+Pcnt+") and mask("+ui2x("%04x",Pmsk)+") conflict"//~5A02R~
//      		null);                                             //~5930R~//~v@@@R~
        		);                                                 //~v@@@I~
	}
	return;
}//xnpcmchk
//***************************************************************
//* last validity chk
//* rc : 0:all fixed,4:free space exist 8:conflict data
//***************************************************************
private int xnplastchk(NPWORKT Ppnpwt)                             //~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   mask,maskon,ii,jj,ibox,row,col,conflictsw=0,incompletesw=0;
//************
//System.out.println("lastchk before");                            //~va01R~
//xnptrace(Ppnpwt);                                                //~va01R~
//row
	pm99=Ppnpwt.m99;       //matrix of each plase                  //~v0.1R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
        for (jj=0,mask=0;jj<MAP_SIZE;jj++)                         //~va01R~
        {
        	maskon=Smask[pm99[ii][jj].fnum];                       //~v0.1R~
            if ((mask & maskon)!=0)                                //~v0.1R~
            	conflictsw=1;
            else
            	mask|=maskon;
		}//col
        if (mask!=ALL_CANDIDATE)        //only one candidate
        	incompletesw=1;
    }//row
//col
    for (jj=0;jj<MAP_SIZE;jj++)                                    //~va01R~
    {
        for (ii=0,mask=0;ii<MAP_SIZE;ii++)                         //~va01R~
        {
        	maskon=Smask[pm99[ii][jj].fnum];                       //~v0.1R~
            if ((mask & maskon)!=0)                                //~v0.1R~
            	conflictsw=1;
            else
            	mask|=maskon;
		}//row
        if (mask!=ALL_CANDIDATE)        //only one candidate
        	incompletesw=1;
    }//col
//box
    for (ibox=0;ibox<MAP_SIZE;ibox++)  //all box                   //~va01R~
    {
        row=(ibox/BOARDTYPE)*BOARDTYPE;     //box top row          //~va01R~
        col=(ibox%BOARDTYPE)*BOARDTYPE;     //box left col         //~va01R~
        for (ii=row,mask=0;ii<row+BOARDTYPE;ii++)                  //~va01R~
            for (jj=col;jj<col+BOARDTYPE;jj++)                     //~va01R~
            {
	        	maskon=Smask[pm99[ii][jj].fnum];                   //~v0.1R~
            	if ((mask & maskon)!=0)                            //~v0.1R~
            		conflictsw=1;
            	else
            		mask|=maskon;
			}
        if (mask!=ALL_CANDIDATE)        //only one candidate
        	incompletesw=1;
	}//all box
//System.out.println("lastchk after conflict="+conflictsw+",incomp="+incompletesw);//~va01R~
//xnptrace(Ppnpwt);                                                //~va01R~
    if (conflictsw!=0)
    	return 8;
    if (incompletesw!=0)
    	return 4;
    return 0;		//ok
}//xnplastchk
//***************************************************************
//* print final result
//* opt : 0:init data,1:intermediate result,2:last success data,
//*        -1:last failed,-2:aborted,-3/-4:multiple solution
//***************************************************************
private void xnpprintnum(int Popt,int Prow,int Pcol,NPWORKT Ppnpwt,int Plevel,String Pcase)//~v0.1R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    M99 m99;                                                       //~v0.1I~
    int   ii,jj,level,num,rchkmode;
    String   clvl,clvl2,subcase;                                   //~5929R~
    DecimalFormat fmt=new DecimalFormat("#0"); 
    String str1,str2;//~5930I~
//************
	if ((subcase=Ppnpwt.method)==null)                                  //~v0.1R~
    	subcase="";		//candidate bit reset method not worked
    else
    	Ppnpwt.method=null;		//printed                          //~v0.1R~
	rchkmode=Ppnpwt.stat & NPWORKT.STAT_RCHK;                      //~v0.1R~
	switch(Popt)
    {
    case 0:
//  	uerrmsg("------ Question ---- "+Ppnpwt.idatano+" space --",//~5930R~//~v@@@R~
//  	        "------ 問題 ---- "+Ppnpwt.idatano+" space --");   //~5930R~//~v@@@R~
    	str1=pContext.getText(R.string.PrintOutQ1).toString();     //~v@@@R~
    	str2=pContext.getText(R.string.PrintOutQ2).toString();     //~v@@@R~
    	uerrmsg(str1+Ppnpwt.idatano+str2);                         //~v@@@R~
        break;
    case 1:
	    if ((Ppnpwt.level & LEVEL_HARDP2_F)!=0)	//hard sw on after prev//~v0.1R~
        	clvl="Hard++";
        else
	    if ((Ppnpwt.level & LEVEL_HARDP1_F)!=0)	//hard sw on after prev//~v0.1R~
        	clvl="Hard+";
        else
	    if ((Ppnpwt.level & LEVEL_HARD_F)!=0)	//hard sw on after prev//~v0.1R~
        	clvl="Hard";
        else
        if ((Plevel & LEVEL_MEDIUM_F)!=0)                          //~v0.1R~
            clvl="Medium";
        else
            clvl="Easy";
		System.out.print("Seq=+"+fmt.format(Ppnpwt.seqno-Ppnpwt.idatano)+" ("+Ppnpwt.seqno+") ("+clvl);//~5930I~
        if ((Stestsw!=0 && Pcase!=null))                                 //~v0.1R~
        	System.out.println(" "+Pcase+" "+subcase+"---");       //~5930R~
        else
        	System.out.println("");                                //~5930R~
        break;
    case 2:
    case -3: 		//multiple solution
    case -4: 		//multiple solution
    	str1=pContext.getText(R.string.PrintAnswer).toString();    //~v@@@R~
//  	uerrmsg("------ Answer ------\n",                          //~v@@@R~
//  			"------ 答 ------");                              //~v@@@R~
    	uerrmsg(str1+"\n");                                        //~v@@@I~
        break;
	default:
    	if (Popt==-1)
        {                                                          //~v@@@I~
//          uerrmsg("---- Question data conflict! ----",           //~v@@@R~
//  				"---- 問題のデータに矛盾が有ります----");//~v@@@R~
	    	str1=pContext.getText(R.string.PrintConflict).toString();//~v@@@R~
            uerrmsg(str1);                                         //~v@@@R~
        }                                                          //~v@@@I~
//  	uerrmsg("------ Aborted -----",                            //~v@@@R~
//  			"------ 頓挫 ------");                           //~v@@@R~
	    str1=pContext.getText(R.string.PrintAbort).toString();     //~v@@@R~
    	uerrmsg(str1);                                             //~v@@@R~
    }
  if (Gblwnpmode==0)                                               //~5A13I~
  {                                                                //~5A13I~
    pm99=Ppnpwt.m99;     //answer                                 //~v0.1R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
        System.out.print("      ");                                //~5930R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
        	m99=pm99[ii][jj];                                      //~v0.1I~
            num=m99.fnum;                                          //~v0.1R~
            if (num!=0)
                if (Popt==0)
					if (m99.dlvl==LEVEL_INIT)                      //~v0.1R~
                    	System.out.print("  "+num);                //~5A01R~
                    else
						if (m99.flag==M99.M99F_PATERN)                 //~v0.1R~
		                	System.out.print(" +"+num);            //~5930R~
                        else
		                	System.out.print("  _");               //~5930R~
                else
					if (rchkmode!=0)
						if (m99.dlvl==LEVEL_INIT)                  //~v0.1R~
                            System.out.print(" *"+num);            //~5930R~
                        else
	                        if (ii==Prow && jj==Pcol) //placed now
	                            System.out.print(" ="+num);        //~5930R~
    	                    else
                            	System.out.print("  "+num);        //~5930R~
                    else
                        if (Popt==1 && ii==Prow && jj==Pcol) //placed now
                            System.out.print(" *"+num);            //~5930R~
                        else
                        	if (Popt==2)
                        		if (m99.dlvl==LEVEL_INIT)          //~v0.1R~
                            		System.out.print("  "+num);    //~5930R~
                            	else
                            		System.out.print(" *"+num);    //~5930R~
                            else
                            	System.out.print("  "+num);                  //~5930R~
            else
                System.out.print("  _");                           //~5930R~
            if (jj%BOARDTYPE==BOARDTYPE-1)                         //~va01R~
                System.out.print("  ");                            //~5930R~
        }//a line
        if (ii%BOARDTYPE==BOARDTYPE-1)                             //~va01R~
            System.out.println("\n");                              //~5930R~
        else
            System.out.println("");                                //~5A01R~
    }//all line
  }//!wnp mode                                                     //~5A13I~

	switch(Popt)
    {
    case 2: 		//success
//printf("case 2:ppnpwt=%08x,level=%04x\n",Ppnpwt,Ppnpwt.level);   //~v0.1R~
        if ((Ppnpwt.stat & NPWORKT.STAT_PILOT)!=0)                 //~v0.1R~
        {
            level=Ppnpwt.level;                                    //~v0.1R~
            if ((level & LEVEL_HARDP2_S)!=0)                       //~v0.1R~
                clvl="Hard++";
            else
            if ((level & LEVEL_HARDP1_S)!=0)                       //~v0.1R~
                clvl="Hard+";
            else
            if ((level & LEVEL_HARD_S)!=0)                         //~v0.1R~
                clvl="Hard";
            else
            if ((level & LEVEL_MEDIUM_S)!=0)                       //~v0.1R~
                clvl="Medium";
            else
                clvl="Easy";
        	if ((Ppnpwt.stat & NPWORKT.STAT_ANSOFMAKE)!=0)         //~v0.1R~
            	clvl2="Level=";
            else
            	clvl2="";
        }
        else
        	clvl2=
            clvl="";
        if ((Ppnpwt.stat & NPWORKT.STAT_ANSOFMAKE)!=0)             //~v0.1R~
        {                                                          //~v@@@I~
//  		uerrmsg("====== Make Success ======  "+clvl2+clvl+" !!",//~5930R~//~v@@@R~
//  	    	    "====== 作成完了 ======  "+clvl2+clvl+" !!");  //~5930R~//~v@@@R~
    		str1=pContext.getText(R.string.PrintSuccessMake1).toString();//~v@@@R~
    		str2=pContext.getText(R.string.PrintSuccessMake2).toString();//~v@@@R~
    		uerrmsg(str1+clvl2+clvl+str2);                         //~v@@@R~
        }                                                          //~v@@@I~
        else                                                       //~v0.1I~
        {                                                          //~v@@@I~
    		str1=pContext.getText(R.string.PrintSuccess1).toString();//~v@@@R~
    		str2=pContext.getText(R.string.PrintSuccess2).toString();//~v@@@R~
//  		uerrmsg("====== Success ======  "+clvl2+clvl+" !!",    //~5930R~//~v@@@R~
//  	    	    "====== 完了 ======  "+clvl2+clvl+" !!");      //~5930R~//~v@@@R~
    		uerrmsg(str1+clvl2+clvl+str2);                         //~v@@@R~
        }                                                          //~v@@@I~
        break;
	case -2:
        if (Stestsw!=0)
            xnptrace(Ppnpwt);
		xnpprintcan(Ppnpwt);
    	str1=pContext.getText(R.string.PrintFailure1).toString();  //~v@@@R~
    	str2=pContext.getText(R.string.PrintFailure2).toString();  //~v@@@R~
//  	uerrmsg("?????? Failure ("+(PEG_MAX-Ppnpwt.seqno)+" space remained) ??????",//~5930R~//~v@@@R~
//  	        "?????? 失敗です ( "+(PEG_MAX-Ppnpwt.seqno)+" 個 残プレース) ??????");//~5930R~//~v@@@R~
    	uerrmsg(str1+(PEG_MAX-Ppnpwt.seqno)+str2);                 //~v@@@R~
//      ubell();                                                   //~5930R~
        break;
	case -3:
//      uerrmsg(" OR\n",                                           //~v@@@R~
//  			" と\n");                                         //~v@@@R~
        uerrmsg(pContext.getText(R.string.PrintOR).toString());     //~v@@@I~
        break;
	case -4:
//  	uerrmsg("?????? Question data is vague ??????",            //~v@@@R~
//  	        "?????? 問題データにあいまいな個所が有ります ??????");//~v@@@R~
    	uerrmsg(pContext.getText(R.string.PrintVague).toString()); //~v@@@R~
//      ubell();                                                   //~5930R~
	}
    return;
}//xnpprintnum
//***************************************************************
//* print candidate
//***************************************************************
public void xnpprintcan(NPWORKT Ppnpwt)                            //~5930R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    StringBuffer clist;                                            //~5A02R~
    int   ii,jj,kk,cmsk,cctr,pos; 
    String str1;//~5A02R~
//************
//  uerrmsg("------ Remaining Candidate List ------\n",            //~v@@@R~
//  		"------ 残った場所に置く事が出来るナンバー ------\n");//~v@@@R~
    str1=pContext.getText(R.string.PrintCandidate).toString();     //~v@@@R~
	uerrmsg(str1+"\n");                                            //~v@@@R~

    pm99=Ppnpwt.m99;     //answer                                  //~v0.1R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
        System.out.print("      ");                                //~5A02R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {
            if (pm99[ii][jj].fnum!=0)                                 //~v0.1R~
                System.out.print("("+pm99[ii][jj].fnum+")        ");//~5A02R~
            else
            {
                cmsk=pm99[ii][jj].cmsk;                            //~v0.1R~
    			clist=new StringBuffer("          ");              //~5A02I~
                clist.replace(0,1,"_");                             //~5A02I~
                for (pos=0,cctr=0,kk=1;kk<=MAP_SIZE;kk++)          //~va01R~
                    if ((cmsk & Smask[kk])!=0)                     //~v0.1R~
                    {
                        if (cctr!=0)
                        {                                          //~5A02I~
                            clist.replace(pos,pos+1,",");          //~5A02R~
                            pos++;                                 //~5A02I~
                        }                                          //~5A02I~
                        cctr++;
                        clist.replace(pos,pos+1,Integer.toString(kk));//~5A02R~
                        pos++;                                     //~5A02I~
                    }
                clist.setLength(10);                               //~5A02R~
                System.out.print(clist.toString()+" ");            //~5A02R~
            }
            if (jj%BOARDTYPE==BOARDTYPE-1)                         //~va01R~
                System.out.print("  ");                            //~5A02I~
        }//a line
        if (ii%BOARDTYPE==BOARDTYPE-1)                             //~va01R~
	        System.out.println("\n");                              //~5A02R~
        else                                                       //~5A02I~
	        System.out.println("");                                //~5A02I~
    }//all line
    return;
}//xnpprintcan
//***************************************************************
//* print final result
//***************************************************************
public void xnptrace(NPWORKT Ppnpwt)                               //~5930R~
{
	M99[][]  pm99;                                                 //~v0.1R~
    int   ii,jj,num;                                               //~va05R~
    int[][] pi;                                                    //~v0.1R~
    StringBuffer sb;                                               //~v0.1I~
    //************
	System.out.println("------ seq="+Ppnpwt.seqno+" ----");        //~5930R~

    System.out.println("\n num     ");                             //~v0.1R~
    pm99=Ppnpwt.m99;                                               //~v0.1R~
    sb=new StringBuffer();                                         //~v0.1I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v038I~
        	System.out.println("");                                //~v038I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {                                                          //~va01I~
            num=pm99[ii][jj].fnum;                                 //~va05I~
            if (num>=10)                                           //~va05R~
	            sb.append("  "+num);                               //~va05R~
            else                                                   //~va01I~
	            sb.append("   "+num);                              //~va05R~
            if ((jj+1)%BOARDTYPE==0)                               //~va01R~
	            sb.append("  ");                                   //~va01I~
        }                                                          //~va01I~
        System.out.println(sb.toString());                         //~v0.1I~
		sb.setLength(0);                                           //~v0.1I~
    }//all line

    System.out.println("\n cmask   ");                             //~v036M~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~v036M~
    {                                                              //~v036M~
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v036I~
        	System.out.println("");                                //~v036I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~v036M~
        {                                                          //~v036M~
	    	if (jj!=0 && (jj%BOARDTYPE)==0)                        //~v036I~
	            sb.append("   ");                                  //~v036I~
//          printf("  %04x ",pm99->cmsk);                          //~v036M~
            sb.append("  "+ui2x("%08x",pm99[ii][jj].cmsk)+" ");    //~va16R~
        }                                                          //~v036M~
        System.out.println(sb.toString());                         //~v036M~
		sb.setLength(0);                                           //~v036M~
    }//all line                                                    //~v036M~
                                                                   //~v036I~
    System.out.println("\n fixed seqno");                          //~va05I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va05I~
    {                                                              //~va05I~
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v038I~
        	System.out.println("");                                //~v038I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va05I~
        {                                                          //~va05I~
            num=pm99[ii][jj].fseq;                                 //~va05I~
            if (num>=100)                                          //~va05I~
	            sb.append("  "+num);                               //~va05I~
            else                                                   //~va05I~
            if (num>=10)                                           //~va05I~
	            sb.append("   "+num);                              //~va05I~
            else                                                   //~va05I~
	            sb.append("    "+num);                             //~va05I~
            if ((jj+1)%BOARDTYPE==0)                               //~va05I~
	            sb.append("  ");                                   //~va05I~
        }                                                          //~va05I~
        System.out.println(sb.toString());                         //~va05I~
		sb.setLength(0);                                           //~va05I~
    }//all line                                                    //~va05I~

    System.out.println("\n ccntr   ");                             //~v0.1R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v038I~
        	System.out.println("");                                //~v038I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {                                                          //~v045I~
	    	if (jj!=0 && (jj%BOARDTYPE)==0)                        //~v045I~
	            sb.append("   ");                                  //~v045I~
            sb.append("  "+pm99[ii][jj].ccnt);                     //~5A01R~
        }                                                          //~v045I~
        System.out.println(sb.toString());                         //~v0.1I~
		sb.setLength(0);                                           //~v0.1R~
    }//all line

    System.out.println("\n level   ");                             //~5930R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va01R~
    {
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v038I~
        	System.out.println("");                                //~v038I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {                                                          //~v045I~
	    	if (jj!=0 && (jj%BOARDTYPE)==0)                        //~v045I~
	            sb.append("   ");                                  //~v045I~
            sb.append("  "+ui2x("%02x",pm99[ii][jj].dlvl)+" ");    //~5A02R~
        }                                                          //~v045I~
        System.out.println(sb.toString());                         //~v0.1I~
		sb.setLength(0);                                           //~v0.1I~
    }//all line

    System.out.println("\n flag    ");                             //~v041I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~v041I~
    {                                                              //~v041I~
    	if (ii!=0 && (ii%BOARDTYPE)==0)                            //~v041I~
        	System.out.println("");                                //~v041I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~v041I~
        {                                                          //~v045I~
	    	if (jj!=0 && (jj%BOARDTYPE)==0)                        //~v045I~
	            sb.append("   ");                                  //~v045I~
            sb.append("  "+ui2x("%02x",pm99[ii][jj].flag)+" ");    //~v041I~
        }                                                          //~v045I~
        System.out.println(sb.toString());                         //~v041I~
		sb.setLength(0);                                           //~v041I~
    }//all line                                                    //~v041I~
                                                                   //~v041I~
    System.out.println("\n line mask");                            //~v0.1R~
    pi=Ppnpwt.fmsk;                                                //~v0.1R~
    for (ii=0;ii<3;ii++)                                           //~va01R~
    {
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va01R~
        {                                                          //~v045I~
	    	if (jj!=0 && (jj%BOARDTYPE)==0)                        //~v045I~
	            sb.append("   ");                                  //~v045I~
            sb.append("  "+ui2x("%04X",pi[ii][jj])+" ");           //~5A02R~
        }                                                          //~v045I~
        System.out.println(sb.toString());                         //~v0.1I~
		sb.setLength(0);                                           //~v0.1I~
    }//all line

    return;
}//xnptrace
//***************************************************************  //~va05I~
//***************************************************************  //~va05I~
public static void boardnumcopy(int [][]Ptgt,int [][] Psrc)        //~v021R~
{                                                                  //~5930I~
	for (int ii=0;ii<MAP_SIZE;ii++)                                //~5930I~
    	System.arraycopy(Psrc[ii],0,Ptgt[ii],0,MAP_SIZE);          //~v021R~
}                                                                  //~5930I~
public static void boardnumclear(int [][] Ptgt)                    //~v021I~
{                                                                  //~v021I~
	for (int ii=0;ii<MAP_SIZE;ii++)                                //~v021I~
    	Arrays.fill(Ptgt[ii],0);                                   //~v021I~
}                                                                  //~v021I~
//public static String uerrmsg(String Pemsg,String Pjmsg)                  //~5930I~//~v@@@R~
public static String uerrmsg(String Pemsg)                         //~v@@@I~
{                                                                  //~5930I~
	if (Gblwnpmode==0)                                             //~5A01I~
    {                                                              //~5A01I~
//  	return xnp.uerrmsg(Pemsg,Pjmsg);                           //~5A01I~//~v@@@R~
    	return xnp.uerrmsg(Pemsg);                                 //~v@@@I~
    }                                                              //~5A01I~
	String errmsg;                                                 //~5930I~
//  if (Wnp.Sjlang)                                                  //~v045I~//~v@@@R~
//    if (Pjmsg==null)                                               //~5930I~//~v@@@R~
//        errmsg=Pemsg;                                              //~5930I~//~v@@@R~
//    else                                                           //~5930I~//~v@@@R~
//        errmsg=Pjmsg;                                              //~5930I~//~v@@@R~
//  else                                                             //~v045I~//~v@@@R~
//    errmsg=Pemsg;                                                  //~v045I~//~v@@@R~
    errmsg=Pemsg;                                                  //~v@@@I~
    Board.GblNpsubMsg=errmsg;                                      //~5930R~
//  JOptionPane.showMessageDialog(null,errmsg,"NP", JOptionPane.ERROR_MESSAGE);//~5A02R~
    return errmsg;                                                 //~5930I~
}                                                                  //~5930I~
//public static void uerrexit(String Pemsg,String Pjmsg) throws uerrexit_excp//~5930M~//~v@@@R~
public static void uerrexit(String Pemsg) throws uerrexit_excp     //~v@@@I~
{                                                                  //~5930M~
	if (Gblwnpmode==0)                                             //~5A01I~
    {                                                              //~5A01I~
//  	xnp.uerrexit(Pemsg,Pjmsg);                                 //~5A01I~//~v@@@R~
    	xnp.uerrexit(Pemsg);                                       //~v@@@I~
    }                                                              //~5A01I~
    else                                                           //~5A02I~
    {                                                              //~5A02I~
//  	String errmsg=uerrmsg(Pemsg,Pjmsg);                        //~5A02R~//~v@@@R~
    	String errmsg=uerrmsg(Pemsg);                              //~v@@@I~
		throw new uerrexit_excp(errmsg);                           //~5A02R~
    }                                                              //~5A02I~
}                                                                  //~5930M~
public static int uatoi(String Pstr)                               //~5A01I~
{                                                                  //~5A01I~
	String str="";                                                 //~5A01I~
	char ch;                                                       //~5A01I~
    int sign=0,charcnt=0;                                          //~5A01I~
                                                                   //~5A01I~
    int len=Pstr.length();                                         //~5A01I~
    for (int ii=0;ii<len;ii++)                                     //~5A01I~
    {                                                              //~5A01I~
    	ch=Pstr.charAt(ii);                                        //~5A01I~
        if (ch=='+')                                               //~5A01I~
        {                                                          //~5A01I~
        	if (charcnt==0 && sign==0)                             //~5A01I~
            {                                                      //~5A01I~
            	sign=1;                                            //~5A01I~
                continue;                                          //~5A01I~
            }                                                      //~5A01I~
                                                                   //~5A01I~
        }                                                          //~5A01I~
        else                                                       //~5A01I~
        if (ch=='-')                                               //~5A01I~
        {                                                          //~5A01I~
        	if (charcnt==0 && sign==0)                             //~5A01I~
            {                                                      //~5A01I~
            	sign=-1;                                           //~5A01I~
                continue;                                          //~5A01I~
            }                                                      //~5A01I~
                                                                   //~5A01I~
        }                                                          //~5A01I~
        else                                                       //~5A01I~
        if (ch==' ')                                               //~5A01I~
        {                                                          //~5A01I~
        	if (charcnt==0 && sign==0)                             //~5A01I~
            	continue;                                          //~5A01I~
        }                                                          //~5A01I~
        if (ch<'0' || ch>'9')                                      //~5A01I~
        	break;                                                 //~5A01I~
        charcnt++;                                                 //~5A01I~
        str+=Character.toString(ch);                               //~5A01I~
    }                                                              //~5A01I~
    if (charcnt==0)                                                //~5A01I~
    	return 0;                                                  //~5A01I~
    return Integer.parseInt(str);                                  //~5A01I~
}//uatoi                                                           //~5A01I~
//**************************************************               //~v036M~
//*rand init                                                       //~v036M~
//**************************************************               //~v036M~
public int xnpbitcount(int Pmask)                                  //~v036M~
{                                                                  //~v036M~
	int numcnt=0;                                                  //~v036M~
//***************                                                  //~v036M~
    for (int ii=1;ii<=MAP_SIZE;ii++)                               //~v036M~
    	if ((Pmask & Smask[ii])!=0)                                //~v036R~
        	numcnt++;                                              //~v036M~
//System.out.println("bitcount ="+numcnt+",mask="+ui2x("%08x",Pmask));//~va16R~
    return numcnt;                                                 //~v036M~
}//xnpbitcount                                                     //~v036M~
//**********************************************************       //~5A02I~
//*get hex digit string                                            //~5A02I~
//*fmt parm "%pnnx" p:0 or space, nn:output digit count, x:X or x  //~5A02I~
//**********************************************************       //~5A02I~
public static String ui2x(String Pfmt,int Pint)                    //~5A02I~
{  
	String str,xstr;                                               //~5A02R~
    int digitpos=2,len;                                            //~5A02R~
//***********************************                              //~5A02I~
    len=Pfmt.length();                                             //~5A02I~
	if (len<1||Pfmt.charAt(0)!='%')                                //~5A02R~
    	return "?"; 
    len--;                                                         //~5A02I~
	xstr=Integer.toHexString(Pint);                                //~5A02R~
    if (Pfmt.charAt(len)=='X')                                     //~5A02R~
    	xstr=xstr.toUpperCase();                                   //~5A02R~
    else                                                           //~5A02I~
    	if (Pfmt.charAt(len)!='x')                                 //~5A02I~
	    	return "?";                                            //~5A02I~
    len--;                                                         //~5A02I~
	if (Pfmt.charAt(1)==' ')                                       //~5A02I~
    {                                                              //~5A02I~
		str="        "+xstr;          //~5A02I~
        len--;                                                     //~5A02I~
    }                                                              //~5A02I~
    else                                                           //~5A02I~
	if (Pfmt.charAt(1)=='0')                                       //~5A02I~
    {                                                              //~5A02I~
		str="00000000"+xstr;           //~5A02I~
        len--;                                                     //~5A02I~
    }                                                              //~5A02I~
    else
    {                                                              //~5A02I~
		str="        "+xstr;                                        //~5A02I~
    	digitpos=1;                                                //~5A02R~
    }                                                              //~5A02I~
    if (len==0)                                                    //~5A02I~
    	return xstr;                                               //~5A02R~
	int substrpos=str.length()-Integer.parseInt(Pfmt.substring(digitpos,digitpos+len));//~5A02R~
    if (substrpos<0)                                               //~5A02R~
    	substrpos=0;                                               //~5A02R~
    str=str.substring(substrpos);                                  //~5A02R~
    return str;                                                    //~5A02I~
}//ui2x                                                            //~5A06R~
}//class xnpsub                                                    //~v0.1M~
