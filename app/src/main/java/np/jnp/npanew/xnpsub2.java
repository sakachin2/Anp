//CID://+@@@@R~:                                                   //~@@@@I~
package np.jnp.npanew;                                             //+@@@@R~

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Date;

import android.content.Context;
import android.graphics.Point;

import np.jnp.npanew.R;
//CID://+va16R~:                                                   //~va16R~
//*************************************************************    //~va02M~
//*XNPSUB2.c   Number Place Puzzle(Make puzzle)                    //~va02M~
//*************************************************************    //~va02M~
//va16:051113 option to drop redundancy to make                    //~va16I~
//va15:051112 if return maked data when multisolution detected,it is redundant;delete redundancy option add//~va15I~
//v042:051112 (BUG)when plan<0 serch not limited place by unit count dail//~v042I~
//v034:051109 performance;try new make logico use once reached answer;//~va10I~
//va10:051107 for performance method9 select effective path        //~va10I~
//va09:051104 abondan v09(no effect),try method7                   //~va09I~
//va08:051104 for 5*5 s not effective;abondon                      //~va07I~
//va07:051104 timeout is not effective;abondon                     //~va07I~
//va06:051104 make performance up;chk elapsed time at each method9 depth and shift to next candidate//~va06I~
//v026:051105 (BUG)lmay loop when patern specified make            //~v026I~
//va05:051104 make performance up;no more search required when MULTISOL found//~va05I~
//va04:051104 print elapsed time                                   //~va04I~
//va02:051029 accept middle button as drop down number             //~va02I~
//va01:051013 5*5 support                                          //~va01I~
//wnp-v010:051003 Static init missing cause 2nd result is not same as 1st even seed is same//~va02M~
//wnp-v009:051002 rand dose not same rand sequence for same seed when multithread//~va02M~
//*v104 new pattern for each level matching                        //~va02M~
//*v103 protect copy(set same num on top-right of box3 and box7)   //~va02M~
//*v102 output seed for retry                                      //~va02M~
//*v101 make performance;once create random pater data,then try make//~va02M~
//*v100 first                                                      //~va02M~
//*************************************************************    //~va02M~



public class xnpsub2 {                                             //~v104M~
public static       int SETMAX;        //max count in box/line     //~va02R~
private static final int SrandP=673;                               //~v009I~
private static final int SrandQ=944;                               //~v009I~
private static       int SrandR=1;		//ranom number             //~v009I~
public  long         Smakestarttime;                               //~va04I~
                                                                   //~v104M~
//********************
public static       int MINSELECTSTART;   //men method after this number of place filled//~v042R~
public static final int LEVELCHNGPAT=     20;   //if over change pattern data//~v104R~
private static final int MSG_CHK_FREQ=10;                          //~5A01I~
private static int MAKELOGIC1=1;   	//select candidate considering candidate count(min if plan<0,amx if plan>0)//~v034R~
//private static int MAKELOGIC2=2;	//select place same sequense determind at init(randomize)//~v034I~//~@@@@R~
private static int MAKELOGIC=MAKELOGIC1;                           //~v034I~
                             //~v034I~
                                                                   //~v104I~
private static  int BOARDTYPE;                                     //~va02I~
private static  int PEG_MAX;                                       //~5A01R~
private static  int MAP_SIZE;                                      //~5A01R~
private static  int MAKE_DEPTH;                                    //~va01I~
                                                                   //~va07I~
private static final int INITMAX3=0;       //nomax                 //~va09R~
private static final int INITMAX4=0;       //nomax                 //~va09R~
private static final int INITMAX5=0;     //skip chk xnpsolution up to 300 position//~va09R~
private static       int INITMAX=0;     //if not 0;select candidate which drop most candidate if selceted that.//~v034R~
private static       int PRECHKMODE=1;  //try most effective path  //~va10I~//~@@@@R~
//********************
private  int Stestsw=0;                                            //~5A01R~
private  int[] Smask={0,0x01,0x02,0x04,0x08,0x10,0x20,0x40,0x80,              //1+8=9//~va02I~
                        0x100,0x200,0x400,0x800,0x1000,0x2000,0x4000,0x8000, //+8=17//~va02I~
                        0x10000,0x20000,0x40000,0x80000,0x100000,0x200000,0x400000,0x800000, //+8=25//~va02I~
                        0x1000000};                                //~va02I~

private  int Srigidsw=0;                                           //~5A01R~
private  int Ssetlimit=0;                                          //~5A01R~
private  int Spospatcnt=0;   		                               //~5A01R~
private  int Spospatcnt_u=0;       //by the user                   //~5A01R~
private  int[][] Spospattbl=null;                                  //~5A01R~
private  int Stmsgfreq=0;		//loop msg frequency               //~5A01R~
private  int Srepeatmax=0;		//loop max                         //~5A01R~
private  int Splan=0;			//strategy of search pos           //~5A01R~
private  int Sloopcnt=0;                                           //~5A01R~
private  long Stm0=0,Stm=0;                                        //~5A01R~
private  int[][] Spptbl=null;                                      //~5A01R~
private  int[] Spis0=null;                                         //~va02I~
//private Random srand=null;                                       //~v009R~
private  xnpsub asub1=null;                                        //~5930I~
private int Stotloop=0;                                            //~5A01I~
private long Stottm0=0,Smsgtm=0,tottm=0;                           //~5A01I~
private NPWORKT Snpwtmake0=new NPWORKT();                          //~va01I~
private int[][] Sqouttbl;     	//number place	init data table    //~va01I~
private NPWORKT Snpwt0mql=new NPWORKT();                           //~va01I~
private	NPWORKT Snpwtmql=new NPWORKT();                            //~va01I~
public 	NPWORKT Snpwtmultisol=new NPWORKT();                       //~va05R~
private	NPWORKT[] Snpwtmqs;                                        //~va01I~
private NPWORKT Snpwtsetuppat=new NPWORKT();	//work to chk limit//~va01I~
private	int[][] Spic1,Spic2,Spic3;                                 //~va01R~
private int[] Spospatwk;                                           //~va01I~
private int    Sdelredundant;                                      //~va16R~
private int Sinitmaxmqsuberr=0;	//mqsub once called                //~va07I~
private Point Spt1=new Point();                                    //~v034R~
private Point Spt2=new Point();                                    //~v034R~
private Context pContext;                                          //~@@@@I~
//~5A01I~
//********************
//*************************************************************
public xnpsub2(xnpsub Psub1,int Psize){                            //~5A01R~
    int ii;                                                        //~va01I~
//*********************************                                //~va01I~
	pContext=WnpView.context;                                      //~@@@@I~
	BOARDTYPE=Psize;                                               //~va02I~
    MAP_SIZE=BOARDTYPE*BOARDTYPE;                                  //~va02R~
    PEG_MAX=MAP_SIZE*MAP_SIZE;                                     //~5A01R~
    MAKE_DEPTH=PEG_MAX;                                            //~va01I~
    SETMAX=MAP_SIZE-BOARDTYPE;   //max initial number set count per box/line//~va01R~
    if (BOARDTYPE==3)                                              //~va07I~
        INITMAX=INITMAX3;                                          //~va07I~
    else                                                           //~va07I~
    if (BOARDTYPE==4)                                              //~va07I~
        INITMAX=INITMAX4;                                          //~va07I~
    else                                                           //~va07I~
        INITMAX=INITMAX5;                                          //~va07I~
	MINSELECTSTART=MAP_SIZE*(BOARDTYPE-1);  //minimum candidate place select start count(BOARDTYPE in each BOX)//~v042R~
    asub1=Psub1;                                                   //~5930I~
    asub1.asub2=this;			//back ref                         //~va05I~
	Spptbl=new int[PEG_MAX][2];                                    //~5930R~
    pptblclear();                                                  //~5930I~
    Spis0=new int[PEG_MAX];                                        //~v034R~
	Sqouttbl=new int[MAP_SIZE][MAP_SIZE];     	//number place	init data table//~va01I~
	Snpwtmqs=new NPWORKT[MAKE_DEPTH];     	//number place	init data table//~va01R~
                                                                   //~va01I~
    for (ii=0;ii<MAKE_DEPTH;ii++)                                  //~va01I~
        Snpwtmqs[ii]=new NPWORKT();                                //~va01I~
	Spic1=new int[MAKE_DEPTH][MAP_SIZE];                           //~va01R~
	Spic2=new int[MAKE_DEPTH][MAP_SIZE];                           //~va01R~
	Spic3=new int[MAKE_DEPTH][MAP_SIZE];                           //~va01R~
	Spospatwk=new int[PEG_MAX];                                    //~va01I~
}                                                                  //~v104I~
private void pptblclear(){                                         //~5930I~
    for (int ii=0;ii<PEG_MAX;ii++)                                 //~5930I~
    for (int kk=0;kk<2       ;kk++)                                //~5930M~
		Spptbl[ii][kk]=0;                                          //~5930I~
}                                                                  //~5930I~
//***************************************************************
//* search candidate for make puzzle
//* rc:0 ok,-1:end,4:err
//***************************************************************
int xnpmakequestion(int Popt,NPWORKT Ppnpwt,int[][] Ppattbl,int Psetlimit,//~v104R~
					int Ptmsgfreq,int Prepeatmax,int Pseed,int Pplan,int Plevel,int[] Pactseed)//~v104R~
{
	NPWORKT npwt0=Snpwtmake0;                                      //~va01R~
    long tm;                                                       //~v104R~
    int  rc,randompattern;                                         //~v104R~
    int[][] pi;                                                    //~v104I~
	int[][] qouttbl=Sqouttbl;     	//number place	init data table//~va01R~
    int seed;                                                      //~v104R~
    String str1,str2,str3;                                         //~0A08I~
//************
    Smakestarttime=(new Date()).getTime();                           //~va04I~
 	Spospatcnt=0;                          //                      //~v010I~
 	Spospatcnt_u=0;       //by the user    //                      //~v010I~
	Stotloop=0;                                                    //~v010I~
	Stestsw=Popt & xnpsub.XNP_TEST;                                //~v104R~
	Srigidsw=Popt & xnpsub.XNP_RIGID;                              //~v104R~
    Stmsgfreq=Ptmsgfreq;
    Srepeatmax=Prepeatmax;
    Sloopcnt=0;
    Splan=Pplan;
    Ssetlimit=Psetlimit;	//limit count in same row/col/box      //~v101M~
    if (Pseed!=0)                                                  //~v104R~
    	tm=(long)Pseed;                                            //~v104R~
    else
        tm=System.currentTimeMillis();                             //~v104R~
    seed=(int)(tm & 0x7fff);                                       //~v104R~
    if ((Popt & xnpsub.XNP_DELREDUNDANT)!=0)                                   //~va16I~
		Sdelredundant=1;                                           //~va16I~
    else                                                           //~va16I~
		Sdelredundant=0;                                           //~va16I~
    Pactseed[0]=seed;                                              //~v104R~
//  srand=new Random(seed);                                        //~v009R~
    usrand(seed);                                                  //~v009I~

//printf("plan=%d,cnt=%d\n",Splan,Spospatcnt);
//init npwt
    xnpsub.boardnumclear(qouttbl);                                 //~v104R~
    pi=qouttbl;                                                    //~v104R~
    asub1.xnpansinit((Popt & ~(xnpsub.XNP_PILOT|xnpsub.XNP_SORT))|xnpsub.XNP_SOLOFMAKE,pi,Ppnpwt);//~5930R~
    Ppnpwt.stat|=NPWORKT.STAT_MAKE;        //make mode             //~v104R~

    Stm0=System.currentTimeMillis();                               //~v104I~
    npwt0.copy(Ppnpwt);                                            //~5A01R~
    for (;;)                                                       //~v101I~
    {                                                              //~v101I~
	    Ppnpwt.copy(npwt0);                                        //~5A01R~
	  if (MAKELOGIC==2)                                           //~va10I~
      {                                                            //~v034I~
		randompattern=xnpmqsetuppat2(Ppnpwt,Ppattbl,Psetlimit);    //~v034M~
        Popt|=xnpsub.XNP_MAKELOGIC2;                               //~v034M~
      }                                                            //~v034I~
      else                                                         //~v034M~
		randompattern=xnpmqsetuppat(Ppnpwt,Ppattbl,Psetlimit);     //~v104R~
		rc=xnpmqlvl(Popt & ~xnpsub.XNP_SORT,Ppnpwt,Plevel,pi,randompattern);	//difficulty level matching//~v104R~
//System.out.println("xnpmqlvl rc="+rc+",randomepattern="+randompattern);//~va01R~
//asub1.xnptrace(Ppnpwt);                                          //~va01R~
//if (Stestsw!=0)                                                  //~va16R~
//{                                                                //~va16R~
//    System.out.println("xnpmqlvl rc="+rc+",randomepattern="+randompattern);//~va16R~
//    asub1.xnptrace(Ppnpwt);                                      //~va16R~
//}                                                                //~va16R~
        if (rc!=-2)				//level unmatch;retry by another pattern//~v104I~
        {                                                          //~v104I~
            if (Ppattbl!=null||Splan!=0)     //pattern specified   //~v104R~
                break;                                             //~v104R~
            if (rc==0)                                             //~v104R~
                break;                                             //~v104R~
		}                                                          //~v104I~
	    xnpsub.boardnumclear(qouttbl);                             //~5930I~
    }                                                              //~v101I~
    if (rc==0)                                                     //~va15I~
    	if (Sdelredundant!=0)                                         //~va15I~
        	rc=xnpdelallredundant(Ppnpwt,qouttbl);                 //~va15R~
    Stm=System.currentTimeMillis();                                //~v104I~
    str1=pContext.getText(R.string.HdrMakeQ).toString();//~0A08I~//~@@@@R~
    str2=pContext.getText(R.string.HdrRepeat).toString();//~0A08I~//~@@@@R~
    str3=pContext.getText(R.string.HdrSecElapsed).toString();//~0A08I~//~@@@@R~
//    xnpsub.uerrmsg("=== Make Question == "+(new DecimalFormat(" ###0")).format(Sloopcnt)+" times repeated,"//~v104R~
//				+(new DecimalFormat("####0")).format((int)(Stm-Stm0)/1000)+" sec expired. ===",//~v104I~
//            "=== 問題の作成 == "+(new DecimalFormat(" ###0")).format(Sloopcnt)+" 回繰り返し,"//~v104R~//~0A08R~//~@@@@R~
//                +(new DecimalFormat("####0")).format((int)(Stm-Stm0)/1000)+" 秒使用 ===");//~v104I~//~0A08R~//~@@@@R~
      xnpsub.uerrmsg(str1                +(new DecimalFormat(" ###0")).format(Sloopcnt)+str2//~0A08I~
                  +(new DecimalFormat("####0")).format((int)(Stm-Stm0)/1000)+str3);//~0A08I~
	if (rc==0)                                                     //~v104R~
    {
        pi=qouttbl;                                                //~v104R~
        if ((Popt & xnpsub.XNP_PILOT)==0)                          //~v104R~
            Popt|=(xnpsub.XNP_PILOT|xnpsub.XNP_PILOTNOMSG);        //~v104R~
        Popt|=xnpsub.XNP_ANSOFMAKE;                                //~v104R~
                                            //~va01I~
        asub1.xnpgetanswer(Popt,pi,Ppnpwt,0,0); //success          //~5930R~
//System.out.println("xnpmakequestion rc="+rc2);                   //~va01R~
//asub1.xnptrace(Ppnpwt);                                          //~va01R~
    }
    return rc;
}//xnpmakequestion
//***************************************************************  //~va15I~
//* delete all redundant place                                     //~va15I~
//*ret  :1 if random patern created                                //~va15I~
//***************************************************************  //~va15I~
int xnpdelallredundant(NPWORKT Ppnpwt,int [][]Pqouttbl)            //~va15R~
{                                                                  //~va15I~
	int [][] pi=Pqouttbl;                                          //~va15R~
	M99[][]  pm99;                                                 //~va15I~
    NPWORKT  pnpwt=new NPWORKT();	//get answer                   //~va15I~
	M99      m99;                                                  //~va15I~
	int[]    rlist; //=new int[XNP_MAXRLIST*2];                    //~va15I~
    int ii,jj,rc,rlistcnt,patcnt=0,opt,row,col,selecttype,delpos,selectcnt;//~va15R~
//*****************************                                    //~va15I~
	pnpwt.copy(Ppnpwt);                                            //~va15I~
	xnpsub.boardnumclear(pi);                                      //~va15R~
    pm99=Ppnpwt.m99;     //answer                                  //~va15I~
//pickup init data                                                 //~va15I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va15I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va15I~
        {                                                          //~va15I~
        	m99=pm99[ii][jj];                                      //~va15I~
            if (m99.dlvl==xnpsub.LEVEL_INIT)	//questiondata             //~va15I~
            	pi[ii][jj]=m99.fnum;                               //~va15R~
            if ((m99.flag & M99.M99F_PATERNUSER)==0)               //~va15R~
            if ((m99.flag & M99.M99F_PATERN)!=0)                   //~va15I~
                patcnt++;                                          //~va15I~
        }                                                          //~va15I~
    if (patcnt==PEG_MAX||patcnt==0)                                //~va16R~
        selecttype=0;       //select all except user patern        //~va15R~
    else                                                           //~va15R~
    	if (Srigidsw==0)                                           //~va16I~
	        selecttype=2;       //select from all                  //~va16I~
        else                                                       //~va16I~
	        selecttype=1;       //select except user pattern but at first not patern data//~va16R~
//System.out.println("del all redundant Spat="+Spospatcnt+",patcnt="+patcnt+",idata="+Ppnpwt.idatano);//~va16R~
//System.out.println("selecttype="+selecttype);                    //~va16R~
//asub1.xnptrace(pnpwt);                                           //~va16R~
//redundancy chk                                                   //~va15I~
    opt=(xnpsub.XNP_NOPRINTIN|xnpsub.XNP_NOPRINTOUT|xnpsub.XNP_REDUNDANTCK|xnpsub.XNP_DELREDUNDANT|xnpsub.XNP_RLEVELMASK);//~va16R~
    for (;;)                                                       //~va15R~
    {                                                              //~va15R~
        rc=asub1.xnpgetanswer(opt,pi,pnpwt,0,0); //msgfreq=repeatmax=0//~va15R~
        if (rc!=0)                                                 //~va15R~
//          xnpsub.uerrexit("redundancy check failed",null);       //~va15R~//~0A08R~
            xnpsub.uerrexit("redundancy check failed");            //~0A08I~
        rlistcnt=pnpwt.rclistcnt;                                  //~va15R~
        rlist=pnpwt.rclist;                                        //~va15R~
//System.out.println(MakeElapsedTime()+"del all redundant rcnt="+rlistcnt);//~va16R~
//asub1.xnptrace(pnpwt);                                           //~va16R~
        if (rlistcnt==0)                                           //~va15R~
        {                                                          //~va15I~
//asub1.xnptrace(pnpwt);                                           //~va16R~
            break;                                                 //~va15R~
        }                                                          //~va15I~
        selectcnt=xnpdelallredundantsearchpos(Ppnpwt,rlist,rlistcnt,selecttype,-1);//~va16R~
        if (selectcnt==0)   //redundant is user patern position    //~va15R~
        {                                                          //~va16I~
	        Board.GblNpsubMsg=null;                                      //~va16I~
            break;                                                 //~va15R~
        }                                                          //~va16I~
        delpos=urand()%selectcnt;       //to be fixed seq pos;     //~va15R~
//System.out.println("del all randpos="+delpos+"/"+selectcnt);     //~va16R~
        delpos=xnpdelallredundantsearchpos(Ppnpwt,rlist,rlistcnt,selecttype,delpos);//~va16R~
        row=rlist[delpos*2];                                       //~va15R~
        col=rlist[delpos*2+1];                                     //~va15R~
//System.out.println("del all selected delpos="+delpos+",row="+row+",col="+col);//~va16R~
        pi[row][col]=0;                                            //~va15R~
//      Board.GblNpsubMsg="冗長検査中、残り "+(rlistcnt-1)+" 箇所";       //~va16I~//~0A08R~//~@@@@R~
        Board.GblNpsubMsg=pContext.getText(R.string.RedundancyChecking).toString()+//~0A08I~//~@@@@R~
						" "+(rlistcnt-1) ;                         //~0A08I~
    }                                                              //~va15R~
//System.out.println(MakeElapsedTime()+"no redundant generated");  //~va16R~
	Ppnpwt.copy(pnpwt);                                            //~va15I~
    Ppnpwt.rclist=null;                                             //~va15I~
    Ppnpwt.rclistcnt=0;                                             //~va15I~
    Ppnpwt.rclevel=0;                                               //~va15I~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~va15I~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va15I~
        {                                                          //~va15I~
            if (pm99[ii][jj].dlvl==xnpsub.LEVEL_INIT)              //~va15I~
                pi[ii][jj]=pm99[ii][jj].fnum;                      //~va15I~
            else                                                   //~va15I~
                pi[ii][jj]=0;      //for retry case                //~va15I~
        }                                                          //~va15I~
//asub1.xnptrace(Ppnpwt);                                          //~va16R~
    return 0;			//random pattern created                   //~va15I~
}//xnpdelallredundant                                              //~va15I~
//***************************************************************  //~va15I~
//* search proper redundant place                                  //~va15I~
//*ret  :1 if random patern created                                //~va15I~
//***************************************************************  //~va15I~
int xnpdelallredundantsearchpos(NPWORKT Ppnpwt,int [] Prlist,int Prlistcnt,int Pselecttype,int Pdelpos)//~va15I~
{                                                                  //~va15I~
	M99[][]  pm99;                                                 //~va15I~
	M99      m99;                                                  //~va15I~
    int ii,jj,kk,ll,selectcnt=0;                                   //~va15I~
//****************************                                     //~va15I~
    pm99=Ppnpwt.m99;     //answer                                  //~va15I~
    for (ll=0;ll<3;ll++)    //at first no patern data              //~va16R~
    {                                                              //~va15I~
        for (kk=0;kk<Prlistcnt;kk++)                               //~va15I~
        {                                                          //~va15I~
            ii=Prlist[kk*2];                                       //~va15I~
            jj=Prlist[kk*2+1];                                     //~va15I~
            m99=pm99[ii][jj];                                      //~va15I~
//System.out.println("redundant pos="+kk+"=("+ii+","+jj+")="+m99.fnum+",flag="+xnpsub.ui2x("%08x",m99.flag));//~va16R~
			if (Pselecttype==2 && ll!=2)	//not rigid select user patern at last//~va16R~
	            if ((m99.flag & M99.M99F_PATERNUSER)!=0)    //ignore user patern data//~va16R~
    	            continue;                                      //~va16R~
//System.out.println("redundant selcnt="+selectcnt+",ll="+ll);     //~va16R~
            if (Pselecttype==1 && ll==0)    //select non paterndata //~va15I~
            {                                                      //~va15I~
                if ((m99.flag & M99.M99F_PATERN)==0)               //~va15I~
                    selectcnt++;                                   //~va15I~
            }                                                      //~va15I~
            else                                                   //~va15I~
                selectcnt++;                                       //~va15I~
            if (Pdelpos>=0  && Pdelpos+1==selectcnt)               //~va16I~
            	return kk;			                               //~va16M~
        }                                                          //~va15I~
        if (selectcnt!=0)                                          //~va15I~
            break;                                                 //~va15I~
    }                                                              //~va15I~
//System.out.println("redundant return selcnt="+selectcnt+",ll="+ll);//~va16R~
    return selectcnt;                                              //~va15I~
}//xnpdelallredundantsearchpos                                     //~va15I~
//***************************************************************  //~v101I~
//* level matching of maked question with request level            //~v101I~
//* rc:0 ok,-1:end,-2:retry by new pattern,4:err                   //~v104R~
//***************************************************************  //~v101I~
int xnpmqlvl(int Popt,NPWORKT Ppnpwt,int Plevel,int [][]Pqouttbl,int Prandompattern)//~v104R~
{                                                                  //~v101I~
	NPWORKT npwt0=Snpwt0mql;                                       //~va01R~
	NPWORKT npwt=Snpwtmql;                                         //~va01R~
	M99[][]  pm99;                                                 //~v104R~
    int  ii,jj,rc,level,retrycnt;                                  //~v104R~
    int[][]  pi;                                                   //~v104I~
//*********************                                            //~v101I~
    npwt0.copy(Ppnpwt);  //save initial status                     //~5A01R~
    for (retrycnt=0;;retrycnt++)        //retry until level match  //~v104R~
    {                                                              //~v101M~
    	Ppnpwt.copy(npwt0);				//reset by org             //~5A01R~
      if ((Popt & xnpsub.XNP_MAKELOGIC2)!=0)                       //~v034R~
        rc=xnpmqsub2(Ppnpwt,0);		//1st srch pos                 //~v034R~
      else                                                         //~v034R~
      if (INITMAX!=0)                                              //~va07I~
        rc=xnpmqsubinitmax(Ppnpwt,Prandompattern,0);		//1st srch pos//~va07I~
      else                                                         //~va07I~
        rc=xnpmqsub(Ppnpwt,Prandompattern,0,0);		//1st srch pos //~va05R~
//System.out.println("xnpmqlvl:xnpmqsub rc="+rc+",Plevel="+Plevel+",level="+Ppnpwt.level);//~va01R~
        if (rc!=0)                                                 //~v104R~
        	break;                                                 //~v101M~
//get question table                                               //~v101M~
        pm99=Ppnpwt.m99;     //answer                              //~v104R~
        pi=Pqouttbl;                                               //~v101I~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va02R~
            for (jj=0;jj<MAP_SIZE;jj++)                            //~va02R~
            {                                                      //~5A01I~
                if (pm99[ii][jj].dlvl==xnpsub.LEVEL_INIT)          //~5930R~
                    pi[ii][jj]=pm99[ii][jj].fnum;                  //~v104R~
                else                                               //~v101M~
                    pi[ii][jj]=0;      //for retry case            //~v104R~
            }                                                      //~5A01I~
		if (Plevel==0)	//no level specification                   //~v104R~
        	break;                                                 //~v101M~
        pi=Pqouttbl;                                               //~v101I~
        rc=asub1.xnpgetanswer(Popt|(xnpsub.XNP_PILOT|xnpsub.XNP_PILOTNOMSG|xnpsub.XNP_ANSOFMAKE|xnpsub.XNP_NOPRINTIN|xnpsub.XNP_NOPRINTOUT),//~5930R~
						pi,npwt,0,0); //success                    //~v104R~
        level=npwt.level;                                          //~v101M~
//printf("rc=%d level=%04x\n",rc,level);                           //~v101M~
        if ((level & (xnpsub.LEVEL_HARD_S|xnpsub.LEVEL_HARDP1_S|xnpsub.LEVEL_HARDP2_S))!=0)//~5930R~
        {                                                          //~v101M~
        	if (Plevel=='H')                                       //~v101M~
            	break;                                             //~v101M~
		}                                                          //~v101M~
        else                                                       //~v101M~
        	if ((level & xnpsub.LEVEL_MEDIUM_S)!=0)                //~5930R~
            {                                                      //~v101M~
        		if (Plevel=='M')                                   //~v101M~
            		break;                                         //~v101M~
			}                                                      //~v101M~
            else                                                   //~v101M~
	        	if ((level & xnpsub.LEVEL_EASY_S)!=0)              //~5930R~
        			if (Plevel=='E')                               //~v101M~
            			break;                                     //~v101M~
    	Stm=System.currentTimeMillis();                            //~v104I~
        String str1=pContext.getText(R.string.RetryMake).toString();//~0A08I~//~@@@@R~
//      String str2=pContext.getText(R.string.SecElapsed).toString();//~@@@@R~
//      xnpsub.uerrmsg("=== Made,but level unmatch,try next("+(new DecimalFormat("####0")).format((int)((long)Stm-(long)Stm0))+" sec expired). ===",//~5930R~//~0A08R~
//              "=== レベルが合わないのでリトライします("+(new DecimalFormat("####0")).format((int)((long)Stm-(long)Stm0))+" 秒経過) ===");//~v104R~//~0A08R~//~@@@@R~
        xnpsub.uerrmsg(str1+(new DecimalFormat("##0")).format(retrycnt+1));//~0A08I~//~@@@@R~
		if (Prandompattern!=0)                                     //~v104R~
	    	if (retrycnt>=LEVELCHNGPAT)                            //~v104R~
            {                                                      //~v104I~
//printf("new pattern\n");            	                           //~v104R~
    			return -2;				//request change max       //~v104I~
			}                                                      //~v104I~
	}                                                              //~v101M~
    return rc;                                                     //~v101I~
}//xnpmqlvl                                                        //~v101I~
//***************************************************************  //~va07I~
//* search candidate for make puzzle                               //~va07I~
//* rc:0 ok,-1:end,4:err                                           //~va07I~
//***************************************************************  //~va07I~
int xnpmqsubinitmax(NPWORKT Ppnpwt,int Psw1st,int Pdepth)          //~va07I~
{                                                                  //~va07I~
    int   row,col,kk,ccnt,cmsk,rc,num;                    //~va07I~
    int[]   pic1,pic2,pic20,pic3;                                  //~va07I~
    int   row2,col2;                                           //~va07I~
    NPWORKT pnpwt;                                                 //~va07I~
//************                                                     //~va07I~
	if (Pdepth==0)                                                 //~va07I~
        	Sinitmaxmqsuberr=0;                                    //~va07I~
//System.out.println("Ppnpwt="+Ppnpwt);                            //~va07I~
//malloc recursive work and save data                              //~va07I~
//  pnpwt=(NPWORKT)umalloc((UINT)(sizeof(NPWORKT)+sizeof(int)*3*MAP_SIZE));//~va07I~
    pnpwt=Snpwtmqs[Pdepth];                                        //~va07I~
	pic1=Spic1[Pdepth];                                            //~va07I~
    pic2=pic20=Spic2[Pdepth];                                      //~va07I~
    pic3=Spic3[Pdepth];   		         	//random seq           //~va07I~
                                                                   //~va07I~
	pnpwt.copy(Ppnpwt);	//save for retry                           //~va07I~
//determin point to be filled                                      //~va07I~
    if ((rc=xnpsrchpos(Ppnpwt,Spt1,Spt2,Psw1st))!=0)               //~v034R~
    {                                                              //~va07I~
//System.out.println(MakeElapsedTime()+":srchpos fail rc="+rc);    //~va10R~
//  	asub1.xnptrace(Ppnpwt);                                    //~va07I~
//      System.exit(8);                                            //~va07I~
        pnpwt=null;                                                //~va07I~
    	return rc;					//stepback;                    //~va07I~
    }                                                              //~va07I~
    row=Spt1.x;col=Spt1.y;row2=Spt2.x;col2=Spt2.y;                 //~v034R~
    cmsk=Ppnpwt.m99[row][col].cmsk;                                //~va07I~
//listup candidate                                                 //~va07I~
	ccnt=0;                                                        //~va07I~
    for (kk=1;kk<=MAP_SIZE;kk++)                                   //~va07I~
        if ((cmsk & Smask[kk])!=0)                                 //~va07I~
            pic1[ccnt++]=kk;                                       //~va07I~
//random seq of candidate                                          //~va07I~
	xnprandseq(ccnt,pic3);			//get random seq               //~va07I~
    for (kk=0;kk<ccnt;kk++)                                        //~va07I~
    	pic2[kk]=pic1[pic3[kk]];	//random seq of candidate no   //~va07I~
//System.out.println("mqsub ccnt="+ccnt);                          //~va07I~
//try up to end                                                    //~va07I~
	for (kk=0,pic2=pic20;kk<ccnt;kk++)                             //~va07I~
    {                                                              //~va07I~
    	num=pic2[kk];                                              //~va07I~
	    Ppnpwt.copy(pnpwt);	//parm value                           //~va07I~
  		rc=asub1.xnpfix(row,col,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~va07I~
//System.out.println("mqsubinitmax fix depth="+Pdepth+",rc="+rc+",idatano="+Ppnpwt.idatano+",row="+row+",col="+col+",num="+num);//~va10R~
//xnpprintcan(Ppnpwt);                                             //~va07I~
		if (rc!=0)					//dup or inconsistent(made candidate to 0)//~va07I~
        	continue;                                              //~va07I~
    	if (row2>=0)		//patern srch                          //~va07I~
        {                                                          //~va07I~
        	rc=                                                    //~va07I~
			asub1.xnpfix(row2,col2,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~va07I~
			if (rc!=0)					//dup or inconsistent(made candidate to 0)//~va07I~
    	    	continue;                                          //~va07I~
        }                                                          //~va07I~
        rc=xnpmqsubinitmax(Ppnpwt,0,Pdepth+1);	//level down       //~va07I~
//System.out.println(MakeElapsedTime()+":mqsubinitmax mqsubinitmax returned rc="+rc+",depth="+Pdepth+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~va10R~
		if (rc==0)                                                 //~va07I~
        	break;                                                 //~va07I~
        if (Sinitmaxmqsuberr!=0)	//mqsub once called            //~va07I~
        	break;					//no more set init fix         //~va07I~
	}//all candidate at random                                     //~va07I~
    if (rc!=0)                                                     //~va07I~
    {                                                              //~va07I~
		Ppnpwt.copy(pnpwt);	//try xnpsolution                      //~va07I~
//System.out.println(MakeElapsedTime()+":mqsubinitmax mqsub call depth="+Pdepth+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",row="+row+",col="+col);//~va10R~
        rc=xnpmqsub(Ppnpwt,0,Pdepth+1,0);	//level down           //~va07I~
//System.out.println(MakeElapsedTime()+":mqsubinitmax mqsub returned depth="+Pdepth+",rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",row="+row+",col="+col);//~va10R~
		if (rc!=0)                                                    //~va07I~
        	Sinitmaxmqsuberr=1;                                    //~va07I~
	}                                                              //~va07I~
    return rc;                                                     //~va07I~
}//xnpmqsubinitmax                                                 //~va07I~
//***************************************************************  //~v034R~
//* search candidate for make puzzle(MAKELOGIC=2 case)             //~v034R~
//* no next place selection logic(sequentialy select by the randamly set up at init)//~v034I~
//* rc:0 ok,-1:end,4:err                                           //~v034R~
//***************************************************************  //~v034R~
int xnpmqsub2(NPWORKT Ppnpwt,int Pdepth)                           //~v034R~
{                                                                  //~v034R~
    int   row,col,kk,ll,ccnt,cmsk,rc,num,morecnt;                  //~v034R~
    int[]   pic1,pic2,pic3;                                        //~v034R~
                                            //~v034R~
    NPWORKT pnpwt;                                                 //~v034R~
                                //~v034R~
//************                                                     //~v034R~
//System.out.println("Ppnpwt="+Ppnpwt);                            //~v034R~
//malloc recursive work and save data                              //~v034R~
    pnpwt=Snpwtmqs[Pdepth];                                        //~v034R~
	pic1=Spic1[Pdepth];                                            //~v034R~
    pic2=Spic2[Pdepth];                                            //~v034R~
    pic3=Spic3[Pdepth];   		         	//random seq           //~v034R~
                                                                   //~v034R~
//determin point to be filled                                      //~v034R~
    if ((rc=xnpsrchpos(Ppnpwt,Spt1,Spt2,Pdepth))!=0)    //return pos of depth//~v034R~
    	return rc;					//stepback;                    //~v034R~
    row=Spt1.x;col=Spt1.y;                                         //~v034R~
    cmsk=Ppnpwt.m99[row][col].cmsk;                                //~v034R~
	ccnt=0;                                                        //~v034R~
    for (kk=1;kk<=MAP_SIZE;kk++)                                   //~v034R~
        if ((cmsk & Smask[kk])!=0)                                 //~v034R~
            pic1[ccnt++]=kk;                                       //~v034R~
    if (ccnt==0)                                                   //~v034R~
    {                                                              //~v034R~
    	if (Ppnpwt.seqno==PEG_MAX)                                 //~v034R~
        	return 0;                                              //~v034R~
	    rc=xnpmqsub2(Ppnpwt,Pdepth+1);	//level down(teporary fix) //~v034R~
//System.out.println(MakeElapsedTime()+":mqsub2 fixed more returned rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col);//~v034R~
        return rc;                                                 //~v034R~
    }                                                              //~v034R~
//random seq of candidate                                          //~v034R~
	xnprandseq(ccnt,pic3);			//get random seq               //~v034R~
    for (kk=0;kk<ccnt;kk++)                                        //~v034R~
    	pic2[kk]=pic1[pic3[kk]];	//random seq of candidate no   //~v034R~
//System.out.println("mqsub ccnt="+ccnt);                          //~v034R~
//try up to end                                                    //~v034R~
	pnpwt.copy(Ppnpwt);	//save for retry                           //~v034R~
//search num to fix                                                //~v034R~
	morecnt=0;                                                     //~v034R~
	for (kk=0;kk<ccnt;kk++)                                        //~v034R~
    {                                                              //~v034R~
    	num=pic2[kk];                                              //~v034R~
	    Ppnpwt.copy(pnpwt);	//parm value                           //~v034R~
  		rc=asub1.xnpfix(row,col,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~v034R~
//System.out.println("mqsub2 fix rc="+rc+",row="+row+",col="+col+",num="+num);//~v034R~
    	if (rc!=0)					//dup or inconsistent(made candidate to 0)//~v034R~
        {                                                          //~v034R~
        	pic2[kk]=0;	//skip at next loop                        //~v034R~
        	continue;                                              //~v034R~
        }                                                          //~v034R~
        rc=asub1.xnpsolution(xnpsub.XNP_SOLOFMAKE|xnpsub.XNP_PATHCHK,Ppnpwt,0);	//no rc=12 returned//~v034R~
//System.out.println(MakeElapsedTime()+":mqsub2 xnpsolution rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~v034R~
	    if (rc==0)                                                 //~v034R~
        {                                                          //~v034R~
        	pic2[kk]=0;	//skip at next loop                        //~v034R~
            break;                                                 //~v034R~
        }                                                          //~v034R~
		if (rc==4)		//err,not step down required               //~v034R~
	        morecnt++;                                             //~v034R~
        else                                                       //~v034R~
        	pic2[kk]=0;	//skip at next loop                        //~v034R~
	}//all candidate at random                                     //~v034R~
    if (kk<ccnt)	//all fixed                                    //~v034R~
    	return 0;                                                  //~v034R~
    if (morecnt==0)	//all err                                      //~v034R~
    	return 8;                                                  //~v034R~
    ll=0;                                                          //~v034I~
	for (kk=0;kk<ccnt;kk++)                                        //~v034R~
    {                                                              //~v034R~
    	num=pic2[kk];                                              //~v034R~
        if (num==0)         //fix err or solution err              //~v034R~
        	continue;		                                       //~v034R~
        ll++;                                                      //~v034I~
	    Ppnpwt.copy(pnpwt);	//parm value                           //~v034R~
  		rc=asub1.xnpfix(row,col,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~v034R~
//System.out.println("mqsub2 fix more rc="+rc+",row="+row+",col="+col+",num="+num);//~v034R~
        if (rc!=0)                                                 //~v034R~
        	continue;		//may not occur                        //~v034R~
        rc=asub1.xnpsolution(xnpsub.XNP_SOLOFMAKE|xnpsub.XNP_PATHCHK,Ppnpwt,0);	//no rc=12 returned//~v034R~
//System.out.println(MakeElapsedTime()+":mqsub2 more xnpsolution rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~v034R~
        if (rc!=4)                                                 //~v034R~
        	continue;		//may not occur                        //~v034R~
//System.out.println(MakeElapsedTime()+" mqsub2 returned depth "+Pdepth+",rc="+rc+"loop="+ll+"/"+morecnt+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",row="+row+",col="+col+",num="+num);//~va16R~
	    rc=xnpmqsub2(Ppnpwt,Pdepth+1);	//level down(teporary fix) //~v034R~
//System.out.println(MakeElapsedTime()+" mqsub2 returned depth "+Pdepth+",rc="+rc+"loop="+kk+"/"+ccnt+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",row="+row+",col="+col+",num="+num);//~v034R~
	    if (rc==0)                                                 //~v034R~
        	break;                                                 //~v034R~
    }//all candidate at random                                     //~v034R~
    if (kk==ccnt)                                                  //~v034R~
    	rc=8;                                                      //~v034R~
    return rc;                                                     //~v034R~
}//xnpmqsub2                                                       //~v034R~
//***************************************************************
//* search candidate for make puzzle
//* rc:0 ok,-1:end,4:err
//***************************************************************
int xnpmqsub(NPWORKT Ppnpwt,int Psw1st,int Pdepth,int Psolfound)   //~va05R~
{
    int   row,col,kk,ccnt,cmsk,rc,num,solfound;
    int[]   pic1,pic2,pic3;                                        //~va10R~
    int   row2,col2;                                               //~v103R~
    NPWORKT pnpwt;                                                 //~v104R~
    Point pt1=new Point(),pt2=new Point();                         //~v104I~
//************
//System.out.println("Ppnpwt="+Ppnpwt);                            //~va02R~
//malloc recursive work and save data
//  pnpwt=(NPWORKT)umalloc((UINT)(sizeof(NPWORKT)+sizeof(int)*3*MAP_SIZE));//~va02R~
    pnpwt=Snpwtmqs[Pdepth];                                        //~va01R~
	pic1=Spic1[Pdepth];                                            //~va01R~
    pic2=Spic2[Pdepth];                                            //~va10R~
    pic3=Spic3[Pdepth];   		         	//random seq           //~va01R~

//determin point to be filled
    if ((rc=xnpsrchpos(Ppnpwt,pt1,pt2,Psw1st))!=0)                 //~v104R~
    {
//System.out.println(MakeElapsedTime()+":srchpos fail rc="+rc);    //~va10R~
//  	asub1.xnptrace(Ppnpwt);                                    //~va02R~
//      System.exit(8);                                            //~va02R~
    	return rc;					//stepback;
    }
    row=pt1.x;col=pt1.y;row2=pt2.x;col2=pt2.y;                     //~v104I~
    cmsk=Ppnpwt.m99[row][col].cmsk;                                //~v104R~
  if (Psolfound!=0)		//multi solution found                     //~va05I~
  {                                                                //~va05I~
    pic2[0]=Snpwtmultisol.m99[row][col].fnum;	//found solution   //~va10R~
    ccnt=1;                                                        //~va05I~
//System.out.println(MakeElapsedTime()+":mqsub xnpsolution multi sol data depth="+Pdepth+",row="+row+",col="+col+",rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",num="+pic2[0]);//~va10R~
  }                                                                //~va05I~
  else                                                             //~va05I~
  {                                                                //~va05I~
//listup candidate
	ccnt=0;
    for (kk=1;kk<=MAP_SIZE;kk++)                                   //~va02R~
        if ((cmsk & Smask[kk])!=0)                                 //~v104R~
            pic1[ccnt++]=kk;                                       //~v104R~
                                                                   //~va10I~
   if (PRECHKMODE==1 && Psw1st==0)  //try most effective path      //~va10I~
    ccnt=xnpsortbyeffectiveness(ccnt,pic1,pic2,pic3,Ppnpwt,pnpwt,row,col);//~va10R~
   else                                                            //~va10I~
   {                                                               //~va10I~
//random seq of candidate
	xnprandseq(ccnt,pic3);			//get random seq
    for (kk=0;kk<ccnt;kk++)
    	pic2[kk]=pic1[pic3[kk]];	//random seq of candidate no   //~v104R~
   }                                                               //~va10I~
  }                                                                //~va05I~
//System.out.println("mqsub ccnt="+ccnt);                          //~va01R~
//try up to end
	pnpwt.copy(Ppnpwt);	//save for retry                           //~va10M~
    rc=8;	//for the case ccnt=0;                                 //~va10I~
	for (kk=0;kk<ccnt;kk++)                                        //~va10R~
    {
    	Ppnpwt.stat &=~(NPWORKT.STAT_MULTISOL|NPWORKT.STAT_DATA_CONFLICT);//~va01I~
    	num=pic2[kk];                                              //~v104R~
	    Ppnpwt.copy(pnpwt);	//parm value                           //~5A01R~
  		rc=asub1.xnpfix(row,col,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~5930R~
//System.out.println("mqsub fix rc="+rc+",row="+row+",col="+col+",num="+num);//~va01R~
//xnpprintcan(Ppnpwt);
		if (rc!=0)					//dup or inconsistent(made candidate to 0)//~v104R~
        	continue;
    	if (row2>=0)		//patern srch                          //~v103I~
        {                                                          //~va07I~
        	rc=                                                    //~va07I~
			asub1.xnpfix(row2,col2,num,Ppnpwt,xnpsub.LEVEL_INIT,null);//~5930R~
			if (rc!=0)					//dup or inconsistent(made candidate to 0)//~va07I~
    	    	continue;                                          //~va07I~
        }                                                          //~va07I~
//      rc=asub1.xnpsolution(Ppnpwt,0);                            //~va05R~
//        opt=xnpsub.XNP_SOLOFMAKE|xnpsub.XNP_M9TIMEOUT|Psolfound; //~va07R~
//        rc=asub1.xnpsolution(opt,Ppnpwt,0); //once try with timeout chk//~va07R~
//System.out.println(MakeElapsedTime()+":mqsub xnpsolution with timeout rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~va06R~
//      if (rc==xnpsub.RC_M9TIMEOUT)  //no solution found          //~va07R~
//      {                                                          //~va07R~
        rc=asub1.xnpsolution(xnpsub.XNP_SOLOFMAKE|Psolfound,Ppnpwt,0);	//no rc=12 returned//~va05R~
//System.out.println(MakeElapsedTime()+":mqsub xnpsolution rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~va10R~
//      }                                                          //~va07R~
if (Stestsw!=0)                                                       //~5A01I~
	asub1.xnpprintcan(Ppnpwt);                                           //~5A01R~
if (Stestsw!=0)                                                    //~5A02I~
	asub1.xnptrace(Ppnpwt);                                        //~5A02I~
        if (rc==0)				//success                          //~v104R~
        {                                                          //~va01I~
//asub1.xnptrace(Ppnpwt);                                          //~va01R~
        	break;
        }                                                          //~va01I~
		if (rc!=4 && rc!=12)//4:more required,8:data conflict,12:multiple solution
        	continue;	//try next data
        solfound=Psolfound;                                        //~va05I~
        if (Psolfound==0)	//use 1st solution only                //~va05I~
            if (rc==12) //solution found                           //~va05R~
            {                                                      //~va05R~
                Snpwtmultisol.copy(asub1.Smethod9npwtok[asub1.Smethod9npwtokdepth]);//get first solution//~va05R~
                solfound=xnpsub.XNP_MULTISOL; 	//solution found,but multiple;xnpsolution select data from multisol//~va05R~
            }                                                      //~va05R~
//asub1.xnptrace(Ppnpwt);                                          //~va01R~
    	Ppnpwt.stat &=~(NPWORKT.STAT_MULTISOL|NPWORKT.STAT_DATA_CONFLICT);//~v104R~
        rc=xnpmqsub(Ppnpwt,0,Pdepth+1,solfound);	//level down   //~va05R~
//System.out.println(MakeElapsedTime()+":mqsub mqsub returned rc="+rc+",idatano="+Ppnpwt.idatano+",seqno="+Ppnpwt.seqno+",depth="+Pdepth+",row="+row+",col="+col+",num="+num);//~va10R~
        if (rc<=0)		//ok or loop limit
        	break;
	}//all candidate at random
//System.out.println("mqsub return rc="+rc+",seqno="+Ppnpwt.seqno);//~va01R~
//if (Ppnpwt.seqno>70)                                             //~v104R~
//    xnptrace(Ppnpwt);
//xnpprintcan(Ppnpwt);
	pnpwt=null;                                                    //~5930R~
    return rc;
}//xnpmqsub
//***************************************************************  //~va10I~
//* candidate sort by effectiveness                                //~va10I~
//* return output entry count                                      //~va10R~
//***************************************************************  //~va10I~
int  xnpsortbyeffectiveness(int Pccnt,int []Ppicin,int []Ppicout,int []Ppicwk,NPWORKT Ppnpwt,NPWORKT Ppnpwtwk,int Prow,int Pcol)//~va10R~
{                                                                  //~va10I~
    int ii,kk,num,ccnt=0,maxseq=-1,seq,rc,nextmax;            //~va10R~
    int[] pis0;                                                    //~va10I~
//*****************************                                    //~va10I~
	Arrays.fill(Ppicwk,0);                                         //~va10R~
    pis0=Ppicwk;		//new int[Pmax];                           //~va10R~
//get result fixed count                                           //~va10I~
//System.out.println("effective chk ccnt="+Pccnt+",row="+Prow+",col="+Pcol);//~va10R~
	for (kk=0;kk<Pccnt;kk++)                                       //~va10I~
    {                                                              //~va10I~
    	num=Ppicin[kk];                                            //~va10I~
		Ppnpwtwk.copy(Ppnpwt);	//pass work                        //~va10I~
  		rc=asub1.xnpfix(Prow,Pcol,num,Ppnpwtwk,xnpsub.LEVEL_INIT,null);//~va10I~
//System.out.println("effective chk fix rc="+rc+",num="+num+",seqno="+Ppnpwtwk.seqno);//~va10R~
		if (rc!=0)					//dup or inconsistent(made candidate to 0)//~va10I~
        	continue;                                              //~va10I~
        rc=asub1.xnpsolution(xnpsub.XNP_SOLOFMAKE|xnpsub.XNP_PATHCHK,Ppnpwtwk,0);	//no rc=12 returned//~va10I~
//System.out.println("effective chk sol rc="+rc+",seqno="+Ppnpwtwk.seqno+",num="+num);//~va10R~
//System.out.println(MakeElapsedTime()+":sortby effective return xnpsolution rc="+rc+",idatano="+Ppnpwt.idatano+",row="+Prow+",col="+Pcol+",num="+num);//~va10R~
		if (rc==8)//4:more required,8:data conflict,12:multiple solution//~va10I~
        	continue;                                              //~va10I~
        seq=Ppnpwtwk.seqno;                                        //~va10I~
        if (seq>maxseq)                                            //~va10I~
        	maxseq=seq;                                            //~va10I~
        pis0[kk]=seq;                                              //~va10I~
    }                                                              //~va10I~
    if (maxseq<0)	//no valid data                                //~va10I~
    	return 0;                                                  //~va10I~
//sort by seqno                                                    //~va10I~
	nextmax=maxseq;                                                //~va10I~
	for (ii=0;ii<Pccnt;ii++)                                       //~va10I~
    {                                                              //~va10I~
    	maxseq=nextmax;                                            //~va10I~
//System.out.println("effective chk selected max="+maxseq);        //~va10R~
        if (maxseq<=0)                                             //~va10R~
        	break;                                                 //~va10M~
    	nextmax=-1;                                                //~va10R~
		for (kk=0;kk<Pccnt;kk++)                                   //~va10I~
        {                                                          //~va10I~
	        seq=pis0[kk];                                          //~va10I~
        	if (seq==maxseq)                                       //~va10I~
            {                                                      //~va10I~
            	Ppicout[ccnt++]=Ppicin[kk];                        //~va10R~
//System.out.println("effective chk selected num="+Ppicin[kk]);    //~va10R~
			}                                                      //~va10I~
            else                                                   //~va10I~
            	if (seq<maxseq)                                    //~va10I~
	            	if (seq>nextmax)                               //~va10R~
                		nextmax=seq;                               //~va10R~
//System.out.println("effective chk selected seq="+seq+",nextmax="+nextmax);//~va10R~
        }                                                          //~va10I~
    }                                                              //~va10I~
//System.out.println("effective sorted="+Ppicout);                 //~va10R~
    return ccnt;                                                   //~va10R~
}//xnpsortbyeffectiveness                                          //~va10I~
//***************************************************************
//* randomize
//*   get random seq
//***************************************************************
void xnprandseq(int Pmax,int[]Prandseq)                            //~v104R~
{
    int ii,jj,randno;                                              //~5930R~
    int[] pis0,pit;                                                     //~va02R~
//*****************************
    if (Pmax<=1)                                                   //~5A01R~
    {
        Prandseq[0]=0;                                             //~5930R~
        return;
    }
    pis0=Spis0;		//new int[Pmax];                               //~va02R~
    for (ii=0;ii<Pmax;ii++)
    	pis0[ii]=ii;                                               //~5930R~
    pit=Prandseq;
    for (ii=Pmax,jj=0;ii>1;ii--)                                   //~5A01R~
    {
//  	randno=srand.nextInt(ii);  	//0<=---<ii                    //~v009R~
    	randno=urand()%ii;            	//0<=---<ii                //~v009I~
//System.out.println("rand xnprandseq randno="+randno);            //~v010R~
//      pis=pis0+randno;                                           //~v104R~
//      *pit++=*pis;                                               //~v104R~
        pit[jj]=pis0[randno];                                      //~v104I~
        jj++;                                                      //~5A01I~
//      for (;++pis<pise;)                                         //~v104R~
        for (;++randno<Pmax;)                                      //~v104I~
//      	*(pis-1)=*pis;        //drop used and shift            //~v104R~
        	pis0[randno-1]=pis0[randno];        //drop used and shift//~5930R~
    }
    pit[jj]=pis0[0];				//last one                     //~v104R~
    pis0=null;                                                     //~v104R~
    return;
}//xnprandseq
//***************************************************************
//* search free pos randomly
//*   max 5 in a box/line
//* rc:4 no free pos found,8:patern exausted,-1:loop limit
//***************************************************************
int xnpsrchpos(NPWORKT Ppnpwt,Point Ppt1,Point Ppt2,int Psw1st)    //~v104R~
{
	M99[][]  pm990;                                                //~v104R~
	M99      pm99;                                                 //~v104I~
    int   ii,jj=0,kk=0,seqposno,seqno,entno,rc;              //~v042R~
//*****************************
if (xnpsub.Gblwnpmode!=0)                                          //~5A01R~
{                                                                  //~5A01I~
	Sloopcnt++;                                                    //~v104M~
	if (xnpsub.GblSubthreadStopReq!=0)	//thread communication with wnp//~v010R~
        if (xnpsub.GblSubthreadStopReq==2)	//timeout              //~v010R~
//      	xnpsub.uerrexit("Time Expired",                        //~5930R~//~0A08R~
//  	    		"タイムアウト");                               //~v104I~//~0A08R~//~@@@@R~
        	xnpsub.uerrexit(pContext.getText(R.string.Timeout).toString());//~0A08I~//~@@@@R~
        else                                                       //~v104I~
//      	xnpsub.uerrexit("Forced Thread Termination",           //~5930R~//~0A08R~
//  	    		"強制終了");                                   //~v104R~//~0A08R~//~@@@@R~
        	xnpsub.uerrexit(pContext.getText(R.string.ErrBreaked).toString());//~0A08I~//~@@@@R~
}                                                                  //~5A01I~
else                                                               //~5A01R~
{                                                                  //~5A01I~
    Sloopcnt++;                                                    //~5A01R~
    if (Stotloop==0)                                                 //~5A01R~
        Smsgtm=Stottm0=System.currentTimeMillis();                 //~5A01R~
    if ((++Stotloop % MSG_CHK_FREQ)==0)                            //~5A01R~
    {                                                              //~5A01R~
        tottm=System.currentTimeMillis();                          //~5A01R~
        if (Stmsgfreq!=0 && tottm-Smsgtm>=Stmsgfreq)   //each msg freq//~5A01R~
        {                                                          //~5A01R~
//          System.err.println("processing ... "+(new DecimalFormat("####0")).format(Stotloop)+" times trial,"+(new DecimalFormat("###0")).format((int)(tottm-Stottm0)/1000)+" sec expired.");//~5A01R~//~@@@@R~
            Smsgtm=tottm;                                          //~5A01R~
        }                                                          //~5A01R~
        if (Srepeatmax!=0 && tottm-Stottm0>=Srepeatmax)               //~5A01R~
        {                                                          //~5A01R~
	        String str1=pContext.getText(R.string.Timeout).toString()+";";//~0A08I~//~@@@@R~
	        String str2=pContext.getText(R.string.SecElapsed).toString()+"===";//~@@@@I~
//          xnpsub.uerrmsg("Timeout;"+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+" sec expired. ===",//~5A01R~//~0A08R~
//                  "時間切れ;"+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+" 秒経過 ===");//~5A01R~//~0A08R~//~@@@@R~
            xnpsub.uerrmsg(str1+(new DecimalFormat("####0")).format((int)(tottm-Stottm0))+str2);//~0A08I~//~@@@@R~
            return -1;              //stop calc                    //~5A01R~
        }                                                          //~5A01R~
    }                                                              //~5A01R~
}                                                                  //~5A01R~
	Ppt2.x=-1;			//id of no pattern                         //~v104R~
    seqno=Ppnpwt.seqno;                                            //~v104R~
    pm990=Ppnpwt.m99;     //answer                                 //~v104R~
    pm99=pm990[0][0];                                              //~5930I~
//determin point to be filled
    if (seqno>=PEG_MAX)                                            //~va02R~
    {
		asub1.xnpprintcan(Ppnpwt);                                 //~5930R~
//      xnpsub.uerrexit("Int Logic Err;seqno >="+PEG_MAX+",posseq="+seqno,null);//~va02R~//~0A08R~
        xnpsub.uerrexit("Int Logic Err;seqno >="+PEG_MAX+",posseq="+seqno);//~0A08I~
    }
    if (MAKELOGIC==2)                                              //~v034R~
    {                                                              //~v034R~
        if (Psw1st>=Spospatcnt)                                    //~v034R~
            return 8;                                              //~v034R~
    	Ppt1.x=Spptbl[Psw1st][0];	//parm sw1st is depth          //~v034R~
    	Ppt1.y=Spptbl[Psw1st][1];	//parm sw1st is depth          //~v034R~
        return 0;                                                  //~v034R~
    }                                                              //~v034R~
//printf("srch seqno=%d\n",seqno);
    if (Spospatcnt!=0 && Spospatcnt!=PEG_MAX)	//patern specified(except no patern and strategy)//~va02R~
    {
//select pos in the limit count randomly                           //~v034I~
        for (;;)    //chk limit count                              //~v104I~
        {                                                          //~v104I~
            rc=1;                                                  //~v104R~
            if (Psw1st!=0)     //1st time                          //~v104R~
                rc=xnpsrchpatpos1st(Ppnpwt,Ppt1,0);//select top-right//~v104R~
            if (rc!=0) //not random pattern                        //~v104R~
                rc=xnpsrchpatpos(Ppnpwt,Ppt1);                     //~5930R~
			ii=Ppt1.x;                                             //~v104R~
			jj=Ppt1.y;                                             //~v104R~
//System.out.println(":patpos rc="+rc+",seq="+seqno+",row="+ii+",col="+jj);//~va10R~
        	kk=ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE;                //~va02R~
            if (rc==0 && Psw1st!=0)                                //~v104R~
                rc=xnpsrchpatpos1st(Ppnpwt,Ppt2,1);//select bottom-left//~v104R~
//System.out.println("patpos2 rc="+rc);                            //~va10R~
            if (rc!=0 || Srigidsw!=0 || Ssetlimit==0)              //~v104R~
                return rc;                                         //~v104I~
        	if ((Ppnpwt.icnt[0][ii]<Ssetlimit)                     //~v104R~
        	&&  (Ppnpwt.icnt[1][jj]<Ssetlimit)                     //~v104R~
        	&&  (Ppnpwt.icnt[2][kk]<Ssetlimit))                    //~v104R~
                return 0;                                          //~v104I~
            Ppnpwt.m99[ii][jj].flag |= M99.M99F_LIMIT;	//out of select//~5930R~
//printf("over limit(>%d) seq=%d,row=%d,col=%d\n",Ssetlimit,seqno,*Pprow,*Ppcol);//~v104R~
        }//limit cnt                                               //~v104I~
    }//patern residual
//*no patern case(Spospatcnt=PEG_MAX)                              //~v034R~
    entno=PEG_MAX-seqno;                                           //~v042R~
    for (;;)                                                       //~v042R~
    {                                                              //~v042R~
    	if (Splan>0                      //get from max candidate place
        ||  (Splan<0 && seqno>MINSELECTSTART))//at first randow,chng to min method
        {                                //to avoid dencity
        	Point pt=new Point();                                  //~5930I~
        	rc=xnpsrchpatpos(Ppnpwt,pt);                           //~5930R~
			if (rc!=0)	//no place                                 //~v104R~
            {                                                      //~v104I~
//    			asub1.xnptrace(Ppnpwt);                            //~va16R~
//              xnpsub.uerrexit("Internal Logic Err;No place found for plan at seq("+seqno+")",null);//~v042R~
//System.out.println("xnpsrchpos1 No position by count limit");    //~va16R~
                return 4;       //step back try and err            //~v042I~
            }                                                      //~v104I~
            ii=pt.x;jj=pt.y;                                       //~5930I~
            pm99=pm990[ii][jj];                                    //~v104R~
//printf("by plan posno=%d,(%d,%d)\n",seqno,ii,jj);
        }
        else
        {
//          seqposno=srand.nextInt(entno);         //to be fixed seq pos;//~v009R~
//          seqposno=urand()%entno;         //to be fixed seq pos; //~v042R~
            seqposno=urand()%PEG_MAX;       //to be fixed seq pos; //~v042I~
//System.out.println("rand xnpsrchpos seqposno="+seqposno);        //~v010R~
//printf("posno=%d\n",seqposno);
//          for (ii=0,kk=seqposno;ii<MAP_SIZE;ii++)                //~v042R~
            ii=seqposno/MAP_SIZE;                                  //~v042I~
            jj=seqposno%MAP_SIZE;                                  //~v042I~
         for (kk=0;kk<2;kk++)                                      //~v042I~
         {                                                         //~v042I~
//          for (ii=0,kk=seqposno;ii<MAP_SIZE;ii++)                //~v042I~
            for (;ii<MAP_SIZE;ii++)                                //~v042I~
            {
//              for (jj=0;jj<MAP_SIZE;jj++)                        //~v042R~
                for (;jj<MAP_SIZE;jj++)                            //~v042I~
                {                                                  //~v104I~
                	pm99=pm990[ii][jj];                            //~v104I~
                    if (pm99.fnum==0     //not fixed               //~v104R~
                    &&  (pm99.flag & M99.M99F_LIMIT)==0)           //~5930R~
//                      if (kk--==0)            //rand pos         //~v042R~
                            break;
                }                                                  //~v104I~
//              if (kk<0)                                          //~v042R~
                if (jj<MAP_SIZE)                                   //~v042I~
                    break;
                jj=0;	//next loop start                          //~v042I~
            }//row
            if (ii<MAP_SIZE && jj<MAP_SIZE)                        //~v042R~
            	break;                                             //~v042I~
            ii=0;	//next loop start from (0,0)                   //~v042I~
            jj=0;                                                  //~v042I~
          }//kk                                                    //~v042I~
//          if (kk>=0)                                             //~v042R~
            if (kk>=2)                                             //~v042I~
            {                                                      //~v034I~
//    			asub1.xnptrace(Ppnpwt);                            //~va16R~
//              xnpsub.uerrexit("Internal Logic Err;Residual place("+kk+") of "+seqposno+" and seq("+seqno+")",null);//~v042R~
//System.out.println("xnpsrchpos2 No position by count limit");    //~va16R~
                return 4;                                          //~v042I~
            }                                                      //~v034I~
		}//no plan
        if (pm99.cmsk==0)		//has no candidate                 //~v104R~
        {                                                          //~va01I~
//System.out.println(MakeElapsedTime()+":srchpos no candidate");   //~va10R~
            return 4;           //step back required
        }                                                          //~va01I~
//limit chk                                                        //~v042R~
        if ((Ppnpwt.icnt[0][ii]>=Ssetlimit)                        //~v042R~
        ||  (Ppnpwt.icnt[1][jj]>=Ssetlimit)                        //~v042R~
        ||  (Ppnpwt.icnt[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE]>=Ssetlimit))//~v042R~
        {                                                          //~v042R~
            pm99.flag|=M99.M99F_LIMIT;      //out of candidate     //~v042R~
            entno--;                                               //~v042R~
            if (entno==0)                                          //~v042R~
            {                                                      //~v042R~
//System.out.println(MakeElapsedTime()+":entno=0,setlimit="+Ssetlimit);//~v042R~
                return 4;               //step back                //~v042R~
            }                                                      //~v042R~
//printf("seqno=%d,seqposno=%d,fixed(%d or %d or %d) over limit(%d) at (%d,%d)\n",//~v042R~
//          seqno,seqposno,                                        //~v042R~
//          Ppnpwt.icnt[0][ii],Ppnpwt.icnt[1][jj],Ppnpwt.icnt[2][ii/BOARDTYPE*BOARDTYPE+jj/BOARDTYPE],//~v042R~
//              Ssetlimit,ii,jj);                                  //~v042R~
//xnptrace(Ppnpwt);                                                //~v042R~
            continue;                                              //~v042R~
        }                                                          //~v042R~
    	break;                                                     //~v042R~
    }//limitation                                                  //~v042R~
                                                                   //~v042I~
    Ppt1.x=ii;                                                     //~v104R~
    Ppt1.y=jj;                                                     //~v104R~
    return 0;
}//xnpsrchpos
//***************************************************************
//* search free pos randomly from the pattern specified(it may be all if no patern and plan=0)//~v034R~
//* select at first user patern data(no rigid and added from other up to limit)//~v034R~
//* and select lowest(if plan<0) or highest(plan>0) candidate place checking limit count//~v034I~
//* if multiple place found select randomly.                       //~v034I~
//* rc:4:all fixed
//***************************************************************
int xnpsrchpatpos(NPWORKT Ppnpwt,Point Ppt)                        //~v104R~
{
	M99[][]  pm990;                                                //~v104R~
	M99  pm99;                                                     //~v104I~
    int   ii,kk,pospatc,randno,entno,row,col,minmaxccnt,minmaxccnt0,ccnt;//~v104R~
    int[][] ppat;                                                  //~5930R~
//*****************************
    pm990=Ppnpwt.m99;     //answer                                 //~v104R~
    ppat=Spospattbl;
//get lowest candidateno
	if (Splan<0)
    	minmaxccnt0=MAP_SIZE+1;                                    //~va02R~
	else
    	minmaxccnt0=-1;
    if (Spospatcnt_u!=0 && Spospatcnt_u<Spospatcnt)	//no rigid and user pattern//~v104R~
		pospatc=Spospatcnt_u;	//search at first in the range of user//~v104I~
    else	                                                       //~v104I~
		pospatc=Spospatcnt;		//search all                       //~v104I~
  	kk=0;                                                          //~v104I~
    minmaxccnt=minmaxccnt0;                                        //~v104I~
  for (ii=0;ii<2;ii++)                                        //~v104I~
  {                                                                //~v104I~
    for (;kk<pospatc;kk++)		//patern exauste                   //~v104R~
    {
    	row=ppat[kk][0];                                           //~5930R~
        col=ppat[kk][1];                                           //~5930R~
        pm99=pm990[row][col];                                      //~v104R~
        if ((ccnt=pm99.ccnt)==0) //fixed                           //~v104R~
			continue;
        if ((pm99.flag & M99.M99F_LIMIT)!=0)                       //~5930R~
			continue;                                              //~v104I~
		if (Splan<0)	//search lowest ccntno
        {
			if (minmaxccnt>ccnt)
        		minmaxccnt=ccnt;
		}
        else           //search having highest ccnt
        {
			if (minmaxccnt<ccnt)
        		minmaxccnt=ccnt;
		}
	}
//printf("Splan=%d,%d ?= %d\n",Splan,minmaxccnt,minmaxccnt0);
    if (minmaxccnt==minmaxccnt0)
    {                                                              //~v104I~
	    if (pospatc<Spospatcnt)	//no rigid and user pattern        //~v104I~
        {                                                          //~v104I~
//  		xnptrace(Ppnpwt);                                      //~v104R~
			pospatc=Spospatcnt;		//search all                   //~v104I~
            continue;				//search later half            //~v104I~
		}                                                          //~v104I~
    	return 4;			//all fixed
    }                                                              //~v104I~
    break;                                                         //~v104I~
  }//2 times loop                                                  //~v104I~
//count lowest candidate place
    ppat=Spospattbl;
    for (kk=0,entno=0;kk<pospatc;kk++)		//patern exauste       //~v104R~
    {
    	row=ppat[kk][0];                                           //~5930R~
        col=ppat[kk][1];                                           //~5930R~
        pm99=pm990[row][col];                                      //~v104R~
        if ((ccnt=pm99.ccnt)==0) //fixed                           //~v104R~
			continue;
        if ((pm99.flag & M99.M99F_LIMIT)!=0)                       //~5930R~
			continue;                                              //~v104I~
        if (pm99.ccnt==minmaxccnt) //fixed                         //~v104R~
        	entno++;
	}
//select plase by random no                                        //~v104R~
	if (entno==1)
    	randno=0;
	else
//  	randno=srand.nextInt(entno);                               //~v009R~
        randno=urand()%entno;         //to be fixed seq pos;       //~v009I~
//System.out.println("rand xnpsrchpatpos randno="+randno);         //~v010R~
    ppat=Spospattbl;
    for (kk=0,entno=0;kk<pospatc;kk++)		//patern exauste       //~v104R~
    {
    	row=ppat[kk][0];                                           //~5930R~
        col=ppat[kk][1];                                           //~5930R~
        pm99=pm990[row][col];                                      //~v104R~
        if ((pm99.flag & M99.M99F_LIMIT)!=0)                       //~5930R~
			continue;                                              //~v104I~
        if (pm99.ccnt==minmaxccnt) //fixed                         //~v104R~
        	if (entno++==randno)
            {
            	Ppt.x=row;                                         //~v104R~
            	Ppt.y=col;                                         //~v104R~
//printf("lowest ccnt=%d,entno=%d,randno=%d,row=%d,col=%d\n",minmaxccnt,entno,randno,row,col);//~v104R~
                return 0;
			}
	}
    return 8;       //logic err
}//xnpsrchpatpos
//***************************************************************  //~v103I~
//* search top-right and bottom-left of pattern                    //~v103I~
//* rc:4:no pattern data on the box                                //~v103I~
//***************************************************************  //~v103I~
int xnpsrchpatpos1st(NPWORKT Ppnpwt,Point Ppt,int Ptype)           //~v104R~
{                                                                  //~v103I~
    int   ii,jj=0,rc;                                                //~v103R~
	M99[][]  pm99;                                                 //~v104R~
//*****************************                                    //~v103I~
    pm99=Ppnpwt.m99;     //answer                                  //~v104R~
    rc=4;                                                          //~v103I~
    if (Ptype==0)		//top right box                            //~va02R~
        for (ii=0;ii<BOARDTYPE;ii++)                               //~va02R~
        {                                                          //~v103I~
            for (jj=MAP_SIZE-1;jj>MAP_SIZE-BOARDTYPE;jj--)         //~va02R~
                if ((pm99[ii][jj].flag & M99.M99F_PATERN)!=0)      //~5930R~
                {                                                  //~v103I~
                	rc=0;                                          //~v103I~
                    break;                                         //~v103I~
				}                                                  //~v103I~
            if (rc==0)                                             //~v104R~
                break;                                             //~v103I~
        }                                                          //~v103I~
	else              //bottom left box                            //~va02R~
        for (ii=MAP_SIZE-BOARDTYPE;ii<MAP_SIZE;ii++)               //~va02R~
        {                                                          //~v103I~
            for (jj=BOARDTYPE;jj>=0;jj--)                          //~va02R~
                if ((pm99[ii][jj].flag & M99.M99F_PATERN)!=0)      //~5930R~
                {                                                  //~v103I~
                	rc=0;                                          //~v103I~
                    break;                                         //~v103I~
				}                                                  //~v103I~
            if (rc==0)                                             //~v104R~
                break;                                             //~v103I~
        }                                                          //~v103I~
    if (rc==0)                                                     //~v104R~
    {                                                              //~v103I~
    	Ppt.x=ii;                                                  //~v104R~
    	Ppt.y=jj;                                                  //~v104R~
    }                                                              //~v103I~
    return rc;                                                     //~v103I~
}//xnpsrchpatpos1st                                                //~v103I~
//***************************************************************  //~v101I~
//* setup pattern data(Spptbl)                                     //~v034R~
//*  if patern specified and rigid,chk the count is over minimum   //~v034I~
//*  if no patern specified and plan!=0 set all place sequentialy  //~v034I~
//*  if patern specified and no rigid or no patern and plan=0,     //~v034I~
//      select sequntialy user specified place                     //~v034I~
//*     then add randomly up to count over the limit in all unit.  //~v034I~
//*parm1:out pattern adat area                                     //~v101I~
//*parm2:limit in row/col/box                                      //~v101I~
//*ret  :1 if random patern created Spptbl,Spospatcnt(always!=0)   //~v034R~
//***************************************************************  //~v101I~
int xnpmqsetuppat(NPWORKT Ppnpwt,int[][] Ppatdata,int Psetlimit)   //~v104R~
{                                                                  //~v101I~
	M99[][]  pm99;                                                 //~v104R~
    int pos,ii,jj,kk,ibox,posmax;          //~v104R~
                                                  //~5930I~
    int[][] pi;                                                    //~5930R~
    int[] pipat;                                                   //~5930I~
	NPWORKT npwt=Snpwtsetuppat;	//work to chk limit                //~va01R~
    int[] pospat=Spospatwk;                                        //~va01R~
    int maskr,maskc,maskb;
    String str1,str2;//~v104I~
//*****************************                                    //~v101I~
    pptblclear();                                                  //~5930I~
    Spospatcnt=0;                                                  //~v101I~
    pm99=Ppnpwt.m99;     //answer                                  //~v104R~
//System.out.println("rand xnpmqsetuppat entry="+Ppatdata+",rigitsw="+Srigidsw+",setlimit="+Psetlimit);//~va10R~
//patern specified                                                 //~v101I~
    if (Ppatdata!=null)		//patern specified                     //~v104R~
    {                                                              //~v101I~
    	for (pi=Ppatdata,ii=0;ii<MAP_SIZE;ii++)                    //~va02R~
        	for (jj=0;jj<MAP_SIZE;jj++)                            //~va02R~
            	if (pi[ii][jj]!=0)		//place to be filled       //~v104R~
                {                                                  //~v101I~
            		Spptbl[Spospatcnt][0]=ii;                      //~5930I~
            		Spptbl[Spospatcnt++][1]=jj;                    //~5930I~
//                  pm99[ii][jj].flag|=M99.M99F_PATERN;            //~va15R~
                    pm99[ii][jj].flag|=(M99.M99F_PATERN|M99.M99F_PATERNUSER);//~va15I~
				}                                                  //~v101I~
		Spospattbl=Spptbl;                                         //~v104R~
	}                                                              //~v101I~
    if (Srigidsw!=0)                                               //~v104R~
        if (Ppatdata==null || Spospatcnt<xnpsub.XNP_MINIDATA)      //~5930R~
        {                                                          //~0A08I~
        	str1=pContext.getText(R.string.ErrYR1).toString();//~0A08I~//~@@@@R~
        	str2=pContext.getText(R.string.ErrYR2).toString();//~0A08I~//~@@@@R~
//          xnpsub.uerrexit("Specify minimum "+xnpsub.XNP_MINIDATA+" pattern data if /Yr",//~5930R~//~0A08R~
//                  "/Yr の時は少なくとも "+xnpsub.XNP_MINIDATA+" 個のパターン指定が必要");//~v104R~//~0A08R~//~@@@@R~
            xnpsub.uerrexit(str1+xnpsub.XNP_MINIDATA+str2);        //~0A08I~
        }                                                          //~0A08I~
	                                                               //~v104I~
    if (Ppatdata!=null)		//patern specified                     //~5930R~
	    if (Srigidsw!=0)                                           //~v104R~
        	return 0;                                              //~v104I~
//strategy specified                                               //~v101I~
if (MAKELOGIC!=2)                                                  //~v034R~
  if (Ppatdata==null)		//patern specified                     //~v104R~
    if (Splan!=0)          //strategy specified                    //~v104R~
    {                                                              //~v101I~
        for (ii=0;ii<MAP_SIZE;ii++)                                //~va02R~
            for (jj=0;jj<MAP_SIZE;jj++)                            //~va02R~
            {                                                      //~v101I~
                Spptbl[Spospatcnt][0]=ii;                          //~5930I~
                Spptbl[Spospatcnt++][1]=jj;                        //~5930I~
            }                                                      //~v101I~
        Spospattbl=Spptbl;       //all place as patern             //~v104R~
        return 0;                                                  //~v104R~
    }                                                              //~v101I~
//create pattern                                                   //~v101I~
	Srigidsw=0;                                                    //~v101I~
	Arrays.fill(pospat,0);                                         //~5930R~
	if ((posmax=Psetlimit)==0)                                     //~v104R~
    	posmax=SETMAX;                                             //~v101I~
	npwt.clear();                                                  //~5930R~
//System.out.println("Spospatcnt="+Spospatcnt);                    //~va10R~
//  for (maskr=0,maskc=0,maskb=0,kk=0;;kk++)                       //~v026R~
    for (maskr=0,maskc=0,maskb=0,kk=0;kk<PEG_MAX;kk++)             //~v026I~
    {                                                              //~v101I~
      if (kk<Spospatcnt)                                           //~v104I~
    	pos=Spptbl[kk][0]*MAP_SIZE+Spptbl[kk][1];		//user specified pattern//~va02R~
      else                                                         //~v104I~
//  	pos=srand.nextInt(PEG_MAX);         //to be fixed seq pos; //~v009R~
        pos=urand()%PEG_MAX;       //to be fixed seq pos;          //~v009I~
//System.out.println("rand xnpmqsetuppat pos="+pos);               //~v010R~
        if (pospat[pos]!=0)	//already filled                       //~5930R~
        	continue;                                              //~v101I~
        ii=pos/MAP_SIZE;                                           //~v034I~
        jj=pos%MAP_SIZE;                                           //~v034I~
        ibox=(ii/BOARDTYPE*BOARDTYPE)+jj/BOARDTYPE;                //~v034I~
        if (MAKELOGIC==2)                                          //~v034I~
        {                                                          //~v034I~
        	if (npwt.icnt[0][ii]>=posmax)                          //~v034I~
            	continue;                                          //~v034I~
        	if (npwt.icnt[1][jj]>=posmax)                          //~v034I~
            	continue;                                          //~v034I~
        	if (npwt.icnt[2][ibox]>=posmax)                        //~v034I~
            	continue;                                          //~v034I~
        }                                                          //~v034I~
      if (kk<Spospatcnt)                                           //~v104I~
        pospat[pos]=1;	//of user                                  //~5930R~
      else                                                         //~v104I~
        pospat[pos]=2;	//of /Nr                                   //~5930R~
//      ii=pos/MAP_SIZE;                                           //~v034R~
//      jj=pos%MAP_SIZE;                                           //~v034R~
//      ibox=(ii/BOARDTYPE*BOARDTYPE)+jj/BOARDTYPE;                //~v034R~
        if (++npwt.icnt[0][ii]>=posmax)                            //~v104R~
        	maskr|=Smask[ii+1];                                    //~v104R~
        if (++npwt.icnt[1][jj]>=posmax)                            //~v104R~
        	maskc|=Smask[jj+1];                                    //~v104R~
        if (++npwt.icnt[2][ibox]>=posmax)                          //~v104R~
        	maskb|=Smask[ibox+1];                                  //~v104R~
        if (maskr==xnpsub.ALL_CANDIDATE                            //~5930R~
        &&  maskc==xnpsub.ALL_CANDIDATE                            //~5930R~
        &&  maskb==xnpsub.ALL_CANDIDATE)                           //~5930R~
	      if (kk>=Spospatcnt)                                      //~v104I~
            break;                                                 //~v101I~
//printf("mask=%04x,%04x,%04x\n",maskr,maskc,maskb);               //~v104R~
	}                                                              //~v101I~
    Spospatcnt_u=Spospatcnt;                                       //~v104R~
    Spospatcnt=0;                                                  //~v104I~
  for (kk=1;kk<=2;kk++)            //at first user pattern         //~v104I~
  {                                                                //~v104I~
    for (pipat=pospat,ii=0;ii<MAP_SIZE;ii++)                       //~va02R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~va02R~
            if (pipat[ii*MAP_SIZE+jj]==kk)        //place to be filled//~5930R~
            {                                                      //~v101I~
                Spptbl[Spospatcnt][0]=ii;                          //~v101I~
                Spptbl[Spospatcnt++][1]=jj;                        //~v101I~
                pm99[ii][jj].flag|=M99.M99F_PATERN;                //~5930R~
                if (kk==1)                                         //~va15I~
	                pm99[ii][jj].flag|=M99.M99F_PATERNUSER;        //~va15I~
            }                                                      //~v101I~
  }	                                                               //~v104I~
    Spospattbl=Spptbl;                                             //~v104R~
//printf("Spospatcnt=%d,posmax=%d\n",Spospatcnt,posmax);           //~v104R~
    return 1;			//random pattern created                   //~v104R~
}//xnpmqsetuppat                                                   //~v101I~
//***************************************************************  //~v034R~
//* sort postion to be slected                                     //~v034R~
//*ret  :1 if random patern created                                //~v034R~
//***************************************************************  //~v034R~
int xnpmqsetuppat2(NPWORKT Ppnpwt,int[][] Ppatdata,int Psetlimit)  //~v034R~
{                                                                  //~v034R~
	M99[][]  pm99;                                                 //~v034R~
    int ii,jj,kk,ll,rc;                                        //~v034R~
    int[] randseq=Spospatwk;                                       //~v034R~
    int[][] pptblwk;                                               //~v034R~
    int pospatcnt=0;                                               //~v034R~
//*****************************                                    //~v034R~
	rc=xnpmqsetuppat(Ppnpwt,Ppatdata,Psetlimit);                   //~v034R~
	pptblwk=new int[PEG_MAX][2];                                   //~v034R~
    pm99=Ppnpwt.m99;     //answer                                  //~v034R~
//System.out.println("pospat user="+Spospatcnt_u+",all="+Spospatcnt);//~v034R~
//user patern                                                      //~v034R~
    if (Spospatcnt_u!=0)	//user and additional pattern          //~v034R~
    {                                                              //~v034R~
        xnprandseq(Spospatcnt_u,randseq);    //get random sequence 0-->PEG_MAX//~v034R~
        for (kk=0;kk<Spospatcnt_u;kk++) //pattern specified        //~v034R~
        {                                                          //~v034R~
            pptblwk[kk][0]=Spptbl[randseq[kk]][0];                 //~v034R~
            pptblwk[kk][1]=Spptbl[randseq[kk]][1];                 //~v034R~
        }                                                          //~v034R~
    }                                                              //~v034R~
//not rigid and by pos count in unit                               //~v034R~
    if (Spospatcnt>Spospatcnt_u)                                   //~v034R~
    {                                                              //~v034R~
        xnprandseq(Spospatcnt-Spospatcnt_u,randseq);               //~v034R~
        for (kk=Spospatcnt_u,ll=0;kk<Spospatcnt;kk++,ll++) //pattern specified//~v034R~
        {                                                          //~v034R~
            pptblwk[kk][0]=Spptbl[Spospatcnt_u+randseq[ll]][0];    //~v034R~
            pptblwk[kk][1]=Spptbl[Spospatcnt_u+randseq[ll]][1];    //~v034R~
        }                                                          //~v034R~
    }                                                              //~v034R~
//all residual pos                                                 //~v034R~
	pospatcnt=0;                                                   //~v034R~
    for (ii=0;ii<MAP_SIZE;ii++)                                    //~v034R~
        for (jj=0;jj<MAP_SIZE;jj++)                                //~v034R~
        {                                                          //~v034R~
            if ((pm99[ii][jj].flag & M99.M99F_PATERN)==0)          //~v034R~
            {                                                      //~v034R~
    	        Spptbl[pospatcnt][0]=ii;                           //~v034R~
	            Spptbl[pospatcnt++][1]=jj;                         //~v034R~
            }                                                      //~v034R~
        }                                                          //~v034R~
    xnprandseq(pospatcnt,randseq);                                 //~v034R~
    for (kk=0;kk<pospatcnt;kk++) //residual position               //~v034R~
    {                                                              //~v034R~
        pptblwk[Spospatcnt+kk][0]=Spptbl[randseq[kk]][0];          //~v034R~
        pptblwk[Spospatcnt+kk][1]=Spptbl[randseq[kk]][1];          //~v034R~
    }                                                              //~v034R~
    for (ii=0;ii<PEG_MAX;ii++)                                     //~v034R~
    {                                                              //~v034R~
        Spptbl[ii][0]=pptblwk[ii][0];                              //~v034R~
        Spptbl[ii][1]=pptblwk[ii][1];                              //~v034R~
//System.out.println("pospat ii="+ii+"=("+Spptbl[ii][0]+","+Spptbl[ii][1]+")");//~v034R~
    }                                                              //~v034R~
	pptblwk=null;                                                  //~v034R~
	randseq=null;                                                  //~v034R~
    return rc;			//random pattern created                   //~v034R~
}//xnpmqsetuppat2                                                  //~v034R~
//**************************************************               //~v009I~
//*rand init                                                       //~v009I~
//**************************************************               //~v009I~
public static void usrand(int seed)                                //~v009I~
{                                                                  //~v009I~
    SrandR=seed;                                                   //~v009I~
//System.out.println("rand srand ********seed="+seed);             //~v010R~
}                                                                  //~v009I~
//**************************************************               //~v009I~
//*rand  get 0,nnn,nn0  from result                                //~v009I~
//* Mixed congruential method                                      //~v009I~
//**************************************************               //~v009I~
public static int  urand()                                     //~v009I~
{                                                                  //~v009I~
    int r=(SrandR=(SrandP*SrandR+SrandQ)/10%100000);	//Xn=(A*Xn-1+C)/M   M:max number//~v009I~
//System.out.println("****rand="+r);                               //~v010R~
    return r;                                                      //~v009I~
}                                                                  //~v009I~
//**************************************************               //~va04I~
//*rand  get 0,nnn,nn0  from result                                //~va04I~
//* Mixed congruential method                                      //~va04I~
//**************************************************               //~va04I~
public long MakeElapsedTime()                                      //~va04I~
{                                                                  //~va04I~
    return (new Date()).getTime()-Smakestarttime;                  //~va04I~
}                                                                  //~va04I~
}//class xnpsub2                                                   //~v104I~
