//CID://+v@@@R~:                                                   //~v@@@I~
package np.jnp.npanew;                                             //+v@@@R~

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


//CID://+v0.4R~:                                                   //~v0.4R~
//*************************************************************
//*XNP.c   Number Place Puzzle
//*format
//*      xnp  datafile
//*           datafile fmt
//*            9*9 matrix seperated by comma.set '0' for space.
//*            a line is  like as  7,8,0,0,0,0,0,5,2
//*************************************************************
//*000104 xnp v0.1:no rgid when no patern data                     //~v0.4I~
//*000103 xnp v0.1:Win(C++) support                                //~v0.1I~
//*v100 first
//*************************************************************
public class xnp {                                                 //~v0.4I~
private static final String VERSION="v1.2";                         //~v0.4R~
private static final String PGMID="xnp";                            //~v0.4R~
//private static final String OSTYPE="J";                             //~v0.4I~
//private static final String __DATE__="2005/10/01";                  //~v0.4I~
                                                                   //~v0.4I~
private static final int  MAP_SIZE=9;                              //~v0.4I~
private static final int  PEG_MAX        =81;      //9x9=81 ぺグの最大数//~v0.4I~//~v@@@R~
                                                                   //~v0.4I~
//private static final int BUFFSZ=512;                               //~v0.4I~
//private static final int MAXNUMSTRSZ=5;                            //~v0.4I~
private static final int GBL_UERR_DBCSSET=0x02;     //external set //~5A01I~
private static final int GBL_UERR_DBCSMODE=0x04;//dbcs             //~5A01I~
//*************************************************************
//********************
private static	String Spgmver;                                          //~v0.4R~
private static  int  Soptions=xnpsub.XNP_RIGID|xnpsub.XNP_PRINTIDATA;     //~v0.4R~
private static  int  Sqmakesw=0;                                          //~v0.4R~
private static  int  Ssetlimit=xnpsub2.SETMAX;      //dencity             //~v0.4R~
private static  int  Srepeatmax=0;		//loop max time limit(seconds)     //~v0.4R~
private static  int  Stmsgfreq=0;  		//time msg frequency each 10 seconds//~v0.4R~
private static  int  Sseed=0;          		//random-no seed           //~v0.4R~
private static  int  Splan=0;          		//strategy of search pos for make//~v0.4R~
private static  int  Srchkmaxlevel=0;          		//strategy of search pos for make//~v0.4R~
private static  int  Slevel=0;
private static xnpsub asub1=new xnpsub(MAP_SIZE);                  //~5A01R~
private static xnpsub2 asub2=new xnpsub2(asub1,MAP_SIZE);          //~5A01R~
private static int Guerropt=0;                                     //~5A01I~
//********************
public static void main(String[] parmp) {                          //~v0.4I~
    int parmno,posparmno=0,atoiwk,sortopt=0,cptrpos,idx,parmc,parmlen;//~5A01R~
    String cptr,fnm=null;                                       //~v0.4R~
    char ch;                                                       //~v0.4I~
//**********************
    parmc=parmp.length;                                          //~v0.4I~
	Spgmver="# "+PGMID+":"+VERSION+": ";                           //~v0.4R~
//#ifdef DPMI                 //DPMI version                       //~v0.4R~
//    putenv("LFN=Y");                 //need for ufileisvfat      //~v0.4R~
//    ufileisvfat(ISVFAT_FORCE);      //force vfat                 //~v0.4R~
//#endif                      //DPMI or not                        //~v0.4R~
	if (parmc<1)
    {
    	help();
        System.exit (4);                                           //~v0.4R~
    }
//*******************************
//* parm process                *
//*******************************
	for (parmno=0;parmno<parmc;parmno++)
	{
  		cptr=parmp[parmno];
        cptrpos=0;                                                 //~v0.4I~
        parmlen=cptr.length();                                     //~5A01I~
  		if(cptr.charAt(cptrpos)=='-')                              //~v0.4R~
  		{//option
    		ch=cptr.charAt(++cptrpos);                      //first option byte//~v0.4R~

			switch(Character.toUpperCase(ch))             //option
    		{
    		case '?':
        		help();
        		System.exit(8);                                     //~v0.4R~
                break;
    		case 'D':       //set limit
                Ssetlimit=xnpsub.uatoi(cptr.substring(cptrpos+1)); //~5A01R~
                if (Ssetlimit==0)                                  //~v0.4R~
                	Ssetlimit=xnpsub2.SETMAX;                      //~v0.4R~
                else
	                if (Ssetlimit<3)
//                  	uerrexit("Limit should be >=3.",null);     //~v0.4R~//~v@@@R~
                    	uerrexit("Limit should be >=3.");          //~v@@@I~
                break;
    		case 'G':               //strategy                     //~0116I~
			    switch(Character.toUpperCase(cptr.charAt(cptrpos+1)))            //~v0.4R~
    			{                                                  //~0116M~
    			case 0:                                            //~0116M~
    				Splan=0;                                       //~0116M~
        			break;                                         //~0116M~
    			case 'N':		//minimum                          //~0116M~
            		atoiwk=xnpsub.uatoi(cptr.substring(cptrpos+2));//~5A01R~
                	if (atoiwk!=0)                                 //~v0.4R~
                		Splan=-atoiwk;                             //~0116M~
                    else                                           //~0116M~
                    	Splan=-9;                                  //~0116M~
        			break;                                         //~0116M~
    			case 'X':		//maximum                          //~0116M~
            		atoiwk=xnpsub.uatoi(cptr.substring(cptrpos+2));//~5A01R~
                	if (atoiwk!=0)                                 //~v0.4R~
    					Splan=atoiwk;                              //~0116M~
                    else                                           //~0116M~
    					Splan=9;                                   //~0116M~
        			break;                                         //~0116M~
				default:                                           //~0116M~
//              	uerrexit("Make strategy option parm("+cptr.substring(cptrpos)+") err",null);//~v0.4R~//~v@@@R~
                	uerrexit("Make strategy option parm("+cptr.substring(cptrpos)+") err");//~v@@@I~
                }                                                  //~0116M~
                break;                                             //~0116M~
    		case 'M':          // /M
                Sqmakesw=1;
                if (cptrpos+1<parmlen)                             //~5A01I~
	            	Slevel=Character.toUpperCase(cptr.charAt(cptrpos+1));//~5A01R~
                else                                               //~5A01I~
                	Slevel=0;                                      //~5A01I~
                if (Slevel!=0)                                     //~v0.4R~
                    if (!(Slevel=='H' || Slevel=='M' || Slevel=='E'))
//                      uerrexit("Make level option("+cptr.substring(cptrpos+1)+") err",null);//~v0.4R~//~v@@@R~
                        uerrexit("Make level option("+cptr.substring(cptrpos+1)+") err");//~v@@@I~
                break;
    		case 'P':
                Soptions|=xnpsub.XNP_PILOT;                        //~v0.4R~
                break;
    		case 'R':			// /R
                Soptions|=xnpsub.XNP_REDUNDANTCK;                  //~v0.4R~
                Srchkmaxlevel=xnpsub.uatoi(cptr.substring(cptrpos+1));//~5A01R~
                break;
    		case 'S':
            	atoiwk=xnpsub.uatoi(cptr.substring(cptrpos+1));    //~5A01R~
                if (atoiwk!=0)                                     //~v0.4R~
                	Sseed=atoiwk;
                break;
    		case 'T':           //timeout                          //~0116I~
            	atoiwk=xnpsub.uatoi(cptr.substring(cptrpos+1));    //~5A01R~
                if (atoiwk!=0)                                     //~v0.4R~
                	Srepeatmax=atoiwk;                             //~0116M~
                if ((idx=cptr.substring(cptrpos+1).indexOf('/'))!=0)//~v0.4R~
                {                                                  //~0116M~
            		atoiwk=xnpsub.uatoi(cptr.substring(idx+1));    //~5A01R~
                	if (atoiwk!=0)                                 //~v0.4R~
                		Stmsgfreq=atoiwk;                          //~0116M~
				}                                                  //~0116M~
                break;                                             //~0116M~
    		case 'Y':
                while(++cptrpos<parmlen)                           //~5A01R~
                {
	                ch=cptr.charAt(cptrpos);                       //~5A01I~
                    switch(Character.toUpperCase(ch))             //option
                    {
                    case '9':
                        Guerropt|=GBL_UERR_DBCSSET;     //external set
                        Guerropt|=GBL_UERR_DBCSMODE;//dbcs
                        break;
			   		case 'P':
            		    Soptions|=xnpsub.XNP_PRINTIDATA;           //~v0.4R~
                		break;
			   		case 'R':
            		    Soptions|=xnpsub.XNP_RIGID;                //~v0.4R~
                		break;
			   		case 'S':                                      //~v0.1I~
                    	sortopt=1;                                 //~v0.1I~
            		    Soptions|=xnpsub.XNP_SORT;                 //~v0.4R~
                		break;                                     //~v0.1I~
                    case 'T':
                        Soptions|=xnpsub.XNP_TEST;                 //~v0.4R~
                        break;
                    default :                           //not option
//                      uerrexit("undefined option parm("+parmp[parmno]+")",null);//~v0.4R~//~v@@@R~
                        uerrexit("undefined option parm("+parmp[parmno]+")");//~v@@@I~
                    }
                }
                break;
    		case 'N':
                while(++cptrpos<parmlen)                           //~5A01I~
                {                                                  //~5A01I~
	                ch=cptr.charAt(cptrpos);                       //~5A01I~
                    switch(Character.toUpperCase(ch))             //option
                    {
                    case '9':
                        Guerropt|=GBL_UERR_DBCSSET;     //external set
                        Guerropt&=~GBL_UERR_DBCSMODE;//sbcs
                        break;
			   		case 'P':
            		    Soptions&=~xnpsub.XNP_PRINTIDATA;          //~v0.4R~
                		break;
			   		case 'R':
            		    Soptions&=~xnpsub.XNP_RIGID;               //~v0.4R~
                		break;
			   		case 'S':                                      //~v0.1I~
                    	sortopt=1;                                 //~v0.1I~
            		    Soptions&=~xnpsub.XNP_SORT;                //~v0.4R~
                		break;                                     //~v0.1I~
                    case 'T':
                        Soptions&=~xnpsub.XNP_TEST;                //~v0.4R~
                        break;
                    default :                           //not option
//                      uerrexit("undefined option parm("+parmp[parmno]+")",null);//~v0.4R~//~v@@@R~
                        uerrexit("undefined option parm("+parmp[parmno]+")");//~v@@@I~
                    }
              	}
                break;
    		default	:                           //not option
//    			uerrexit("undefined option parm("+parmp[parmno]+")",null);//~v0.4R~//~v@@@R~
      			uerrexit("undefined option parm("+parmp[parmno]+")");//~v@@@I~
            }//sw
    	}//flag
      	else
      	{//positional parmno
        	posparmno++;
        	switch (posparmno)
        	{
        	case  1:          //first parm
          		fnm=cptr;
          		break;
            default:
//            	uerrexit("too many positional parameter("+cptr+")",null);//~v0.4R~//~v@@@R~
              	uerrexit("too many positional parameter("+cptr+")");//~v@@@I~
            }//switch by parmno
  		}//option or posparm
	}//for all parm
    if (Sqmakesw!=0)                                               //~v0.4R~
    {                                                              //~v0.1I~
	    if (sortopt==0)                                            //~v0.4R~
            Soptions|=xnpsub.XNP_SORT;	//default for make         //~v0.4R~
        xnpqmkmain(fnm);
	}                                                              //~v0.1I~
    else
    {
//    	if (fnm==null)                                             //~v0.4R~
//      	uerrexit("Specify Question data file name",            //~v@@@R~
//  					"問題データファイル名を指定して下さい");//~v@@@R~
//        	uerrexit(pContext.getText(R.string.ErrSpecifyFnm).toString();//~v@@@I~
    	xnpansmain(fnm);		//fill initial data
	}
    titlemsg();
    return;
}//main
//private static void HELPMSG(String Pemsg,String Pjmsg)                    //~v0.4I~//~v@@@R~
//{                                                                  //~v0.4I~//~v@@@R~
//    String errmsg;                                                 //~v0.4I~//~v@@@R~
//       //~5A01I~                                                 //~v@@@R~
//    if ((Guerropt & GBL_UERR_DBCSSET)!=0                           //~5A01I~//~v@@@R~
//    &&  (Guerropt&GBL_UERR_DBCSMODE)==0)                           //~5A01I~//~v@@@R~
//        errmsg=Pemsg;                                              //~5A01R~//~v@@@R~
//    else                                                           //~5A01I~//~v@@@R~
//    if (Pjmsg==null)                                               //~v0.4I~//~v@@@R~
//        errmsg=Pemsg;                                              //~5A01R~//~v@@@R~
//    else                                                           //~v0.4I~//~v@@@R~
//        errmsg=Pjmsg;                                             //~5A01R~//~v@@@R~
//    System.out.print(errmsg);                                      //~5A01R~//~v@@@R~
//}                                                                  //~v0.4I~//~v@@@R~
//public static void uerrexit(String Pemsg,String Pjmsg)             //~5A01R~//~v@@@R~
public static void uerrexit(String Pemsg)                          //~v@@@I~
{                                                                  //~v0.4I~
//  uerrmsg(Pemsg,Pjmsg);                                          //~5A01R~//~v@@@R~
    uerrmsg(Pemsg);                                                //~v@@@I~
	System.exit(8);                                                //~v0.4I~
}                                                                  //~v0.4I~
//public static String uerrmsg(String Pemsg,String Pjmsg)            //~5A01R~//~v@@@R~
public static String uerrmsg(String Pemsg)                         //~v@@@I~
{                                                                  //~v0.4I~
	String errmsg;                                                 //~5A01I~
//    if ((Guerropt & GBL_UERR_DBCSSET)!=0                           //~5A01I~//~v@@@R~
//    &&  (Guerropt&GBL_UERR_DBCSMODE)==0)                           //~5A01I~//~v@@@R~
//        errmsg=Pemsg;                                              //~5A01I~//~v@@@R~
//    else                                                           //~5A01I~//~v@@@R~
//    if (Pjmsg==null)                                               //~5A01I~//~v@@@R~
		errmsg=Pemsg;                                              //~5A01I~
//    else                                                           //~5A01I~//~v@@@R~
//        errmsg=Pjmsg;                                             //~5A01I~//~v@@@R~
//    System.out.println(Spgmver+errmsg);                            //~5A01R~//~v@@@R~
    return errmsg;                                                 //~5A01I~
}                                                                  //~v0.4I~
static void titlemsg()                                                    //~v0.4R~
{
//        HELPMSG(                                                   //~v0.4R~//~v@@@R~
//"# "+PGMID+":"+VERSION+"("+OSTYPE+"):"+__DATE__+": ==== Number Place Puzzle =============== by EBURI-syo\n",//~5A01R~//~v@@@R~
//"# "+PGMID+":"+VERSION+"("+OSTYPE+"):"+__DATE__+": ==== ナンバープレース パズル ======== by 木八小 学 ==\n");//~5A01R~//~v@@@R~
    return;                                                        //~v0.4R~
}//titlemsg
static void help()                                                        //~v0.4R~
{
//************
//    titlemsg();                                                  //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"This program solves Number Place Puzzle,or make Puzzle automaticaly.\n",//~v@@@R~
//"ナンバープレース パズルを解いたり問題を自動生成したりするプログラムです\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"\n",                                                            //~v@@@R~
//"\n");                                                           //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"format: "+PGMID+" [file-name] [-option ...]\n",                   //~v0.4R~//~v@@@R~
//"形式  : "+PGMID+" [ファイル名] [-オプション ...]\n");             //~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"  file-name:Problem data or optional patern data to make puzzle.\n",//~v@@@R~
//" ファイル名:問題データ或は問題作成のときのオプションのパターンデータ。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//".option for solution.\n",                                       //~v@@@R~
//"・解答用のオプション。\n");                          //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -R      : Redundancy chk.\n",                                  //~v0.4R~//~v@@@R~
//"   -R      : Redundancy chk;無くてもよいデータの有無をチェック。\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//".option for making puzzle.\n",                                  //~v@@@R~
//"・問題作成用のオプション。\n");                    //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -Dcnt   : Density;max count in each box,row,col.\n",           //~v0.4R~//~v@@@R~
//"   -Dcnt   : Density;同じ行、列、箱に最大いくつまで置いてよいか。\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             Default is 5,Minimum 3.\n",                        //~v@@@R~
//"             省略値は 5 、最小値は 3。\n");           //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -M[x]   : Request of Make Puzzle.\n",                          //~v0.4R~//~v@@@R~
//"   -M[x]   : Make;問題作成モードの指示。\n");                     //~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             By patern file,specify position where digit is placed if you want.\n",//~v@@@R~
//"             パターンファイルを指定すれば数字を置く場所を指定できます。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             x:Dificulty level request. x=H(hard), M(medium) or E(easy).\n",//~v@@@R~
//"             x:作成する問題のレベルの指定。x=H(難), M(中) または E(易)．\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -Sseed  : Seed of random-number generation.\n",                //~v0.4R~//~v@@@R~
//"   -Sseed  : Seed;乱数発生の種の数字。\n");                       //~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             Default is time-stamp.Use it to get same result from same data.\n",//~v@@@R~
//"             省略値は時刻。同じデータで同じ結果を得るには同じ種を使用する。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//".option for both.\n",                                           //~v@@@R~
//"・共通のオプション。\n");                             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -P      : Pilot;print intermediate step.\n",                   //~v0.4R~//~v@@@R~
//"   -P      : Pilot;解答にいたるまでのまでの途中の1手1手を出力。\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -Tl[/f] : Timeout limit.Default is in parenthesis.\n",         //~v0.4R~//~v@@@R~
//"   -Tl[/f] : Timeout;制限時間(秒),括弧内は解答/作成それぞれのモードでの省略値。\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             l:limit seconds("+xnpsub.ANS_TIMEOUT+"/"+xnpsub.MAKE_TIMEOUT+" sec),f:iteration msg frequency("+xnpsub.ANS_MSGFREQ+"/"+xnpsub.MAKE_MSGFREQ+" sec).\n",//~5A01R~//~v@@@R~
//"             l:制限時間("+xnpsub.ANS_TIMEOUT+"/"+xnpsub.MAKE_TIMEOUT+"秒), f:経過MSGの表示頻度("+xnpsub.ANS_MSGFREQ+"/"+xnpsub.MAKE_MSGFREQ+"秒毎)。\n");//~5A01R~//~v@@@R~

//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"   -Yx,-Nx : Yes/No:x is following option.Default is in ( )\n",   //~v0.4R~//~v@@@R~
//"   -Yx,-Nx : 次の様なオプションが Yes か No か を指定する。括弧内は省略値\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"      x:p  : print data file contents.\n",                      //~v@@@R~
//"      x:p  : データファイルの内容を出力する.(/Yp)\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"      x:r  : Accept pattern as firmed.loosely use pattern data if -Nr.(-Yr)\n",//~v0.4R~//~v@@@R~
//"      x:r  : パターンデータを遵守して作成する。/Nrのときは目安とする。(-Yr)\n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"      x:s  : Sort probrem data.(/Ys for Make mode,/Ns for Answer mode)\n",//~v0.1I~//~v@@@R~
//"      x:s  : 問題データを整列する.(作成モードで/Ys、解答モードで/Ns)\n");//~v0.1I~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"  (Ex.) : "+PGMID+" xnpdata.#99 -p;  "+PGMID+" -m -d3; "+PGMID+" -m pat#01 -Np; \n",//~v0.4R~//~v@@@R~
//"  (例)  : "+PGMID+" xnpdata.#99 -p;  "+PGMID+" -m -d3; "+PGMID+" -m pat#01 -Np; \n");//~v0.4R~//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//".data fmt   : specify 9 x 9 matrix.\n",                         //~v@@@R~
//"・データ形式 : 9 x 9 の行列を指定します。\n");  //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"  (Ex.)        # Ex (19)\n",                                    //~v@@@R~
//"  (例)         # 例題 19。\n");                             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"                 7  8  _    _  _  _    _  5  2\n",              //~v@@@R~
//"                 7  8  _    _  _  _    _  5  2\n");             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"                 2  _  _    _  5  _    _  _  3\n",              //~v@@@R~
//"                 2  _  _    _  5  _    _  _  3\n");             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"                 _  _  _    4  _  6    _  _  _\n",              //~v@@@R~
//"                 _  _  _    4  _  6    _  _  _\n");             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"               #==============================\n",              //~v@@@R~
//"               #==============================\n");             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"                 _  _  1    _  _  _    4  _  _\n",              //~v@@@R~
//"                 _  _  1    _  _  _    4  _  _\n");             //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"\n",                                                            //~v@@@R~
//"\n");                                                           //~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             .Space line is ignored,first '#' means comment line.\n",//~v@@@R~
//"             .空白行は無視されます、最初が '#' はコメント行です。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             .Another input format is 81 byte continuous digit string per line.\n",//~v@@@R~
//"             .1行に81個の連続した数字の文字列を指定する形式でもOKです。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             .A file may contain multiple quetion/patern data.\n",//~v@@@R~
//"             .ファイルには複数の問題或いはパターンデータを書けます。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//"             .Non zero digit means the point where digit is placed.\n",//~v@@@R~
//"             .問題作成のパターンデータの場合設定したい所に 0 以外の数字を置く。\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//" When too long time is expired,retry after break(Ctrl+C).\n",   //~v@@@R~
//" 問題作成で時間がかかり過ぎるようなら、一旦ブレーク(Ctrl+C)して再試行してみて\n");//~v@@@R~
//HELPMSG(                                                           //~v0.4R~//~v@@@R~
//" Random-number is used,so another seed may change result.\n",   //~v@@@R~
//" 下さい。乱数を使用しているので種を変えて与えるのも方法です。\n");//~v@@@R~
	return;
}//help
static //*************************************************************
int xnpansmain(String Pfnm)                                        //~v0.4R~
{
	int[][] datatbl=new int[MAP_SIZE][MAP_SIZE];     	//number place	init data table//~v0.4R~
	NPWORKT npwt=new NPWORKT();			//worktbl                  //~v0.4R~
    int  qcnt,failedcnt;                                           //~v0.4R~
    int[][] pi;                                                    //~v0.4I~
	BufferedReader pfh=null;                                            //~v0.4R~
//**********************
	try {                                                          //~v0.4I~
		pfh=new BufferedReader(new FileReader(Pfnm));              //~v0.4I~
    }                                                              //~v0.4I~
    catch(FileNotFoundException e)                                 //~v0.4I~
    {                                                              //~v0.4I~
    	uerrexit(Pfnm+" Open failed");                        //~v0.4R~//~v@@@R~
    }                                                              //~v0.4I~
    if ((Soptions & xnpsub.XNP_PILOT)==0)                          //~v0.4R~
    	Soptions|=(xnpsub.XNP_PILOT|xnpsub.XNP_PILOTNOMSG);        //~v0.4R~
	Soptions|=(Srchkmaxlevel<<12);
    pi=datatbl;                                                    //~v0.4R~
    if (Srepeatmax==0)                                             //~v0.4R~
    	Srepeatmax=xnpsub.ANS_TIMEOUT;                             //~5A01R~
    if (Stmsgfreq==0)                                              //~v0.4R~
    	Stmsgfreq=xnpsub.ANS_MSGFREQ;                              //~5A01R~
	for (qcnt=0,failedcnt=0;;)	//until eof
    {
        if (xnpreaddata(pfh,pi)!=0)                                //~v0.4R~
        	break;
		qcnt++;
    	if (asub1.xnpgetanswer(Soptions & xnpsub.XNP_SOLVMASK,pi,npwt,Stmsgfreq,Srepeatmax)!=0)//~v0.4R~
        	failedcnt++;
	}
//    if (qcnt>1)                                                  //~v@@@R~
//        uerrmsg("Result: "+(qcnt-failedcnt)+" success in "+qcnt+" questions",//~v0.4R~//~v@@@R~
//                "結果: "+(qcnt-failedcnt)+" 問 成功 ( "+qcnt+" 問中)");//~v0.4R~//~v@@@R~

	inputclose(pfh);                                               //~5A01R~
    return failedcnt;
}//xnpmain
static //*********************
//*read initial data **
//*********************
int  xnpreaddata(BufferedReader Ppfh,int[][] Pdata)                //~v0.4R~
{
    String buff,pc;                                            //~v0.4R~
    int ii=0,jj,fldno,row,col;                             //~v0.4R~
    int[][] pi;                                                    //~v0.4I~
//************
try{                                                               //~v0.4I~
    for (ii=0,pi=Pdata;ii<9;)
    {
    	if ((buff=Ppfh.readLine())==null)	//read 1 line              //~v0.4R~
        	break;

                                        //~v0.4R~
        if ((Soptions & xnpsub.XNP_PRINTIDATA)!=0)                 //~v0.4R~
            uerrmsg("===>"+buff);                             //~v0.4R~
		pc=buff.trim();
		if (pc.length()==0)
			continue;//~v0.4R~
        if (pc.charAt(0)=='#')                                     //~v0.4R~
            continue;
		if (pc.length()==PEG_MAX)	//81 digt and lf               //~v0.4R~
        {
			for (jj=0;jj<PEG_MAX;jj++)                             //~v0.4R~
            {
            	row=jj/MAP_SIZE;                                   //~v0.4I~
            	col=jj%MAP_SIZE;                                   //~v0.4I~
                if (pc.charAt(jj)>='0' && pc.charAt(jj)<='9')      //~v0.4R~
    	    		pi[row][col]=pc.charAt(jj)-'0';                //~v0.4R~
                else
                	pi[row][col]=0;                                //~v0.4R~
            }
            ii=9;
            break;		//return
        }
//printf("data=%s\n",buff);
		StringTokenizer st=new StringTokenizer(pc," \t");        //~v0.4I~
        fldno=st.countTokens();                                     //~v0.4I~

        if (fldno<=0)	//no data or all space
            continue;
		if (fldno!=9)
        	uerrexit("data format err;"+buff);                //~v0.4R~//~v@@@R~
		for (jj=0;jj<9;jj++)                                       //~v0.4R~
        	pi[ii][jj]=xnpsub.uatoi(st.nextToken());               //~5A01R~
        ii++;
    }
}                                                                  //~v0.4I~
catch(IOException e)                                               //~v0.4I~
{                                                                  //~v0.4I~
	uerrexit("Read IO exception");                            //~v0.4I~//~v@@@R~
}                                                                  //~v0.4I~
    if (ii==0)                                                     //~v0.4R~
    	return -1;			//eof
    if (ii<9)
        uerrexit("required just 9 line for each probrem data");//~v0.4R~//~v@@@R~
    return 0;
}//xnpreaddata
private static void  inputclose(BufferedReader Ppfh)                      //~5A01I~
{                                                                  //~5A01I~
//************                                                     //~5A01I~
	try{                                                           //~5A01I~
    		Ppfh.close();                                          //~5A01I~
    }                                                              //~5A01I~
	catch(IOException e)                                           //~5A01I~
	{                                                              //~5A01I~
		uerrexit("Input Close I/O exception");                //~5A01I~//~v@@@R~
	}                                                              //~5A01I~
}//inputclose                                                      //~5A01I~
//*********************                                            //~5A01I~
//*make puzzle       **
//*********************
static int xnpqmkmain(String Ppfnm)                                //~5A01R~
{
	BufferedReader pfh=null;                                            //~v0.4R~
	int[][]  qpattbl=new int[MAP_SIZE][MAP_SIZE],pi;     	//patern reques table//~v0.4R~
	NPWORKT npwt=new NPWORKT();			//worktbl                  //~v0.4R~
    int rc,actseed;                                                        //~v0.4R~
    int[] actseedtb=new int[1];		//output selected seed             //~v0.4R~
//**********************
//init
//patern specification
    if (Srepeatmax==0)                                             //~v0.4R~
    	Srepeatmax=xnpsub.MAKE_TIMEOUT;                            //~5A01R~
    if (Stmsgfreq==0)                                              //~v0.4R~
    	Stmsgfreq=xnpsub.MAKE_MSGFREQ;                             //~5A01R~
	rc=4;
    if (Ppfnm!=null)                                                  //~v0.4R~
    {
    	pi=qpattbl;                                                //~v0.4R~
		try {                                                      //~v0.4I~
		    pfh=new BufferedReader(new FileReader(Ppfnm));                      //~v0.4R~
   		 }                                                         //~v0.4I~
    	catch(FileNotFoundException e)                             //~v0.4I~
    	{                                                          //~v0.4I~
    		uerrexit(Ppfnm+" Open failed");                   //~v0.4I~//~v@@@R~
    	}                                                          //~v0.4I~
		for (;;)
        {
    		if (xnpreaddata(pfh,pi)!=0)			//fill initial data//~v0.4R~
            	break;
			if ((rc=asub2.xnpmakequestion(Soptions & xnpsub.XNP_MAKEMASK, //~v0.4R~
				npwt,pi,Ssetlimit,Stmsgfreq,Srepeatmax,Sseed,Splan,Slevel,actseedtb))!=0)//~v0.4R~
                break;
		}
		inputclose(pfh);                                           //~5A01R~
    }
    else
		rc=asub2.xnpmakequestion(Soptions & xnpsub.XNP_MAKEMASK & ~xnpsub.XNP_RIGID,//~v0.4R~
				npwt,null,Ssetlimit,Stmsgfreq,Srepeatmax,Sseed,Splan,Slevel,actseedtb);//~v0.4R~
	if (rc!=0)                                                     //~v0.4R~
		uerrexit("Sorry,Make failed,Try one more.");          //~v0.4R~//~v@@@R~
    actseed=actseedtb[0];                                          //~v0.4I~
	uerrmsg("Actualy used seed="+actseed);                    //~5A01R~//~v@@@R~
    return 0;
}//xnpqmkmain
}//class xnp                                                       //~v0.4I~
