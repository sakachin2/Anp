//*CID://+va68R~:                             update#=   23;       //~va68R~
//*****************************************************************//~va62I~
//va68 230303 save also stack to backstep                          //~va68I~
//va66 230302 save also Memo                                       //~va66I~
//va62 230228 set filename to save                                 //~va62I~
//va23:051221 langauge ctl(English and japanese)                   //~va23I~//~va62M~
//v024:051103 (BUG)restore also score when restore pending game    //~va24I~//~va62M~
//va01:051013 5*5 support                                          //~va01I~//~va62M~
//*****************************************************************//~va01I~//~va62M~
package np.jnp.npanew;                                             //~v@@@R~

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.Stack;
import java.util.StringTokenizer;

import android.content.Context;

import np.jnp.npanew.R;
import np.jnp.npanew.utils.Dump;


public class CPattern {                                            //~5921I~
                                                                 //~5921I~
private static String SEED_PREFIX;                                 //~va23R~
private static String SCORE_PREFIX;                                //~va23R~
private static String QNO_PREFIX;                                  //~v@@@I~
private static String ELAPSEDTIME_PREFIX;                          //~v@@@I~
private static String PENALTY_PREFIX;                              //~v@@@I~
public static final int DATA_FILE=0x01;    //file read            //~5921I~
public static final int DATA_ERR =0x02;    //data has err         //~5921I~
public static final int DATA_ANS =0x10;    //answer gotten        //~5921I~
public static final int MAXLINE  =256;                            //~5921I~
//*file submenu                                                    //~0A21I~
public static final int FILE_SAVE=0;                               //~0A21I~//~v@@@R~
public static final int FILE_RELOAD=1;                             //~0A21I~//~v@@@R~
public static final int FILE_LIST_SCORE=2;                         //~0A21I~//~v@@@R~
public static final int FILE_LIST_LEVEL=3;                         //~0A21I~//~v@@@M~
public static final int FILE_LIST_TIMESEQ=4;                       //~0A21I~//~v@@@M~
public static final String FILE_LAST_NAME="LASTQ";                //~0A21I~//~v@@@R~
private static final String MEMO_HDR="Memo Data";                  //~va66I~
private static final String STACK_HDR="Stack Data ctr=";           //~va68I~
                                                                   //~5921I~
public	int[][] QuestionData=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];                      //~5922R~
public	int[][] PendingData=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];   //~5A06I~
public	int[][] NextData=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];      //~v@@@I~
public	String Name;                                               //~5921I~
public	String strQuestionNo=null;                                 //~v@@@I~
public   int   Datasw;                                             //~5921I~
public   int   Seed;                                               //~5921I~
public   int   intProbLevel;       //at make                       //~v@@@R~
public   String strProbLevel;                                      //~v@@@I~
public   String strUserID="YOU";                                   //~v@@@R~
public   int   Score,ScoreMax;                                     //~va24I~
public   int   restartTrytimeSpan=0;                               //~v@@@I~
public   int   savedPenalty=0;                                     //~v@@@I~
public int [][] AnswerData=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];    //~5922I~
public int[][][] memoData;                                        //~va66R~
public Stack<Board.Hole> stackData;                                      //~va68R~
                                                                   //~5921I~
private	int  Modifiedsw;                                           //~5921I~
private String sep=System.getProperty("line.separator");           //~va68I~

/////////////////////////////////////////////////////////////////////////////

public CPattern()                                             //~5921R~
{
	Name="New.xnp";                                                //~5A06R~
//    for (int y=0;y<wnp.MAP_SIZE;y++)                             //~5A06R~
//        for (int x=0;x<wnp.MAP_SIZE;x++)                         //~5A06R~
//            QuestionData[y][x]=0;                                //~5A06R~
	if (Wnp.Sjlang)                                                //~va23I~
		SEED_PREFIX="問題番号＝";                                  //~va23I~//~v@@@R~
    else                                                           //~va23I~
		SEED_PREFIX="Prob No=";                                    //~va23I~
	if (Wnp.Sjlang)                                                //~va23I~
		SCORE_PREFIX="スコア＝";                                   //~va23I~//~v@@@R~
    else                                                           //~va23I~
		SCORE_PREFIX="Score=";                                     //~va23I~
	QNO_PREFIX=":N:";                                              //~v@@@R~
	ELAPSEDTIME_PREFIX=":E:";                                      //~v@@@R~
	PENALTY_PREFIX=":P:";                                          //~v@@@I~
	xnpsub.boardnumclear(QuestionData);                            //~5A06I~
	xnpsub.boardnumclear(PendingData);                             //~5A06I~
	xnpsub.boardnumclear(NextData);                                //~v@@@I~
    Datasw=0;                                                      //~0108R~
    Modifiedsw=0;                                                  //~0108I~
    Seed=0;                                                        //~5923I~
}

//public void Serialize (Object ar,File Pfile,int Pwritesw)throws IOException//~5A06R~//~0914R~//~0A21R~
public void Serialize (String Pfile,int Pwritesw) //~0A21I~
{                                                                //~0914R~//~0A21R~
    int x,y;                                                     //~0914R~//~0A21R~
    BufferedReader br=null;                                           //~0914R~//~0A21R~
//  FileWriter    fw;                                            //~0914R~//~0A21R~
    PrintWriter   fw=null;                                         //~0A21I~
    OutputStream out=null;                                         //~0A21I~
    InputStream in=null;                                          //~0A21I~
    String line;                                                 //~0914R~//~0A21R~
    StringBuffer sb=new StringBuffer(MAXLINE);                   //~0914R~//~0A21R~
 //   ByteArrayOutputStream redabuff=null;                           //~0A21I~
    int  ii,jj,err=0;                                        //~0129R~//~0914R~//~0A21R~
    int mode;                                                      //~5A06I~//~0914R~//~0A21R~
    int pendsw=0;                                                  //~5A06I~//~0914R~//~0A21R~
    int[][]  inpnum=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE];                     //~5A06I~//~0914R~//~0A21R~
    Board pBoard;                                                  //~5A06I~//~0914R~//~0A21R~
    int pos;                                                       //~5A06I~//~0914R~//~0A21R~
    String token;                                                  //~5A06I~//~0914R~//~0A21R~
    String name;                                                   //~5A06I~//~0914R~//~0A21R~
    int trytimespan;                                               //~v@@@I~
//**********************************                               //~5A06I~//~0914R~//~0A21R~
    savedPenalty=0;                                                //~v@@@I~
    pBoard=WnpView.aBoard;                                            //~5A06I~//~0914R~//~0A21R~
    mode=pBoard.GetMode();                                         //~5A06I~//~0914R~//~0A21R~
//  name=Pfile.getPath();                                          //~5A06I~//~0914R~//~0A21R~
    name=Pfile;                                                    //~0A21I~
    if (Pwritesw!=0) //on output                                //~0103R~//~0914R~//~0A21R~
    {                                                              //~0102I~//~0914R~//~0A21R~
        pBoard.CalcScore();     //intermidate score calc           //~va24I~//~0914R~//~0A21R~
//chk pending data                                                 //~5A06I~//~0914R~//~0A21R~
        pBoard.GetInpnum(inpnum);   //get                          //~5A06I~//~0914R~//~0A21R~
        for (y=0;y<Wnp.MAP_SIZE;y++)                                   //~5A06I~//~0914R~//~0A21R~
        {                                                          //~5A06I~//~0914R~//~0A21R~
            for (x=0;x<Wnp.MAP_SIZE;x++)                               //~5A06I~//~0914R~//~0A21R~
            {                                                      //~5A06I~//~0914R~//~0A21R~
                if (QuestionData[y][x]==0)                         //~5A06I~//~0914R~//~0A21R~
                    if (inpnum[y][x]!=0)                           //~5A06I~//~0914R~//~0A21R~
                    {                                              //~5A06I~//~0914R~//~0A21R~
                        pendsw=1;                                  //~5A06I~//~0914R~//~0A21R~
                        break;                                     //~5A06I~//~0914R~//~0A21R~
                    }                                              //~5A06I~//~0914R~//~0A21R~
            }                                                      //~5A06I~//~0914R~//~0A21R~
        }                                                          //~5A06I~//~0914R~//~0A21R~
      try                                                          //~0A21I~
      {                                                            //~0A21I~
                                                                   //~0A21I~
//      fw=(FileWriter)ar;                                       //~0914R~//~0A21R~
        out=WnpView.context.openFileOutput(name,Context.MODE_PRIVATE);//~0A21I~
        fw=new PrintWriter(new OutputStreamWriter(out,"UTF-8"));   //~0A21I~
        line="# FileName="+name;                                   //~5A06R~//~0914R~//~0A21R~
        if (Dump.Y) Dump.println("CPattern.Serialize write Seed="+Seed+",strQuestionNo="+strQuestionNo);//~v@@@R~
      	if (pendsw==0||                                              //~5A06I~//~0914R~//~0A21R~
          	(mode==Board.MODE_OUTANS && (Datasw & DATA_ANS)!=0))  //ans avail(not modified)//~5A06I~//~0914R~//~0A21R~
      	{                                                            //~5A06I~//~0914R~//~0A21R~
        	PutLine(fw,line,2);                                        //~0102R~//~0914R~//~0A21R~
//        	if (Seed!=0)                                               //~5A06R~//~0914R~//~0A21R~//~va62R~
          	if (Seed!=0||strQuestionNo!=null)                      //~va62I~
        	{                                                          //~0129I~//~0914R~//~0A21R~
//        		if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~0A21R~
//          		line="#------ 問題データ ------("+SEED_PREFIX+Seed+")";//~5A06R~//~0914R~//~0A21R~//~v@@@R~
//        		else                                                     //~va23I~//~0914R~//~0A21R~
//          		line="#------Question Data-----("+SEED_PREFIX+Seed+")";//~va23I~//~0914R~//~0A21R~//~v@@@R~
            		line="#------Question Data-----("+SEED_PREFIX+Seed+QNO_PREFIX+strQuestionNo+")";//~v@@@I~
            		PutLine(fw,line,2);                                    //~0129R~//~0914R~//~0A21R~
        	}                                                          //~0129I~//~0914R~//~0A21R~
        	else                                                       //~0129I~//~0914R~//~0A21R~
//        	if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~0A21R~
//          	PutLine(fw,"#------ 問題データ ------",2);      //~0129R~//~0914R~//~0A21R~//~v@@@R~
//        	else                                                     //~va23I~//~0914R~//~0A21R~
            	PutLine(fw,"#------Question Data-----",2);             //~va23I~//~0914R~//~0A21R~
            for (y=0;y<Wnp.MAP_SIZE;y++)                                   //~0102I~//~0914R~//~0A21R~
            {                                                          //~0102I~//~0914R~//~0A21R~
                sb.setLength(0);                                       //~5A06R~//~0914R~//~0A21R~
                for (x=0;x<Wnp.MAP_SIZE;x++)                           //~5A06R~//~0914R~//~0A21R~
                {                                                      //~5A06R~//~0914R~//~0A21R~
                    if (QuestionData[y][x]!=0)                         //~5A06R~//~0914R~//~0A21R~
                      if (QuestionData[y][x]>=10)                      //~va01R~//~0914R~//~0A21R~
                        sb.append("  "+QuestionData[y][x]);            //~5A06R~//~0914R~//~0A21R~
                      else                                             //~va01I~//~0914R~//~0A21R~
                        sb.append("   "+QuestionData[y][x]);           //~va01I~//~0914R~//~0A21R~
                    else                                               //~5A06R~//~0914R~//~0A21R~
                        sb.append("   _");                             //~va01R~//~0914R~//~0A21R~
                                                                       //~5A06R~//~0914R~//~0A21R~
                    if (((x+1)%Wnp.BOARDTYPE)==0)                      //~va01R~//~0914R~//~0A21R~
                        sb.append("  ");                               //~va01R~//~0914R~//~0A21R~
                }                                                      //~5A06R~//~0914R~//~0A21R~
                if ((y+1)%Wnp.BOARDTYPE==0)                        //~v@@@R~
                    sb.append("\n");                               //~v@@@I~
                PutLine(fw,sb.toString(),1);                           //~5A06R~//~0914R~//~0A21R~
            }                                                          //~0102I~//~0914R~//~0A21R~
            putMemo(pBoard,fw);                                    //~va66R~//+va68M~
            putStack(pBoard,fw);                                   //+va68M~
//      	if ((Datasw & DATA_ANS)!=0) //ans avail(not modified)      //~5A06R~//~0914R~//~0A21R~
        	if (mode==Board.MODE_OUTANS && (Datasw & DATA_ANS)!=0)  //ans avail(not modified)//~5A06I~//~0914R~//~0A21R~
            {                                                          //~0129I~//~0914R~//~0A21R~
                PutLine(fw," ",2);                              //~0129I~//~0914R~//~0A21R~
//            if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~0A21R~
//              PutLine(fw,"#------ 答 ------",2);              //~0129I~//~0914R~//~0A21R~//~v@@@R~
//            else                                                     //~va23I~//~0914R~//~0A21R~
                PutLine(fw,"#-----Answer-----",2);                     //~va23I~//~0914R~//~0A21R~
                for (y=0;y<Wnp.MAP_SIZE;y++)                               //~0129I~//~0914R~//~0A21R~
                {                                                    //~0914R~//~0A21R~
                    sb.setLength(0);                                   //~5A06I~//~0914R~//~0A21R~
                    for (x=0;x<Wnp.MAP_SIZE;x++)                           //~0129I~//~0914R~//~0A21R~
                    {                                                  //~0129I~//~0914R~//~0A21R~
                        if (QuestionData[y][x]!=0)                        //~0129I~//~0914R~//~0A21R~
                          if (QuestionData[y][x]>=10)                  //~va01R~//~0914R~//~0A21R~
                            sb.append("  "+QuestionData[y][x]);        //~5923R~//~0914R~//~0A21R~
                          else                                         //~va01I~//~0914R~//~0A21R~
                            sb.append("   "+QuestionData[y][x]);       //~va01I~//~0914R~//~0A21R~
                        else                                           //~0129I~//~0914R~//~0A21R~
                            if (AnswerData[y][x]!=0)                   //~5923I~//~0914R~//~0A21R~
                              if (AnswerData[y][x]>=10)                //~va01R~//~0914R~//~0A21R~
                                sb.append(" *"+AnswerData[y][x]);      //~5923R~//~0914R~//~0A21R~
                              else                                     //~va01I~//~0914R~//~0A21R~
                                sb.append("  *"+AnswerData[y][x]);     //~va01I~//~0914R~//~0A21R~
                            else                                       //~5923I~//~0914R~//~0A21R~
                                sb.append("   _");                     //~va01R~//~0914R~//~0A21R~
                                                                       //~0129I~//~0914R~//~0A21R~
                        if (((x+1)%Wnp.BOARDTYPE)==0)                  //~va01R~//~0914R~//~0A21R~
                            sb.append("  ");                           //~va01R~//~0914R~//~0A21R~
                    }                                                  //~0129I~//~0914R~//~0A21R~
                    if ((y+1)%Wnp.BOARDTYPE==0)                        //~va01I~//~0914R~//~0A21R~
                        sb.append("\n");                               //~va01I~//~0914R~//~0A21R~
                    PutLine(fw,sb.toString(),1);                         //~0129I~//~0914R~//~0A21R~
                }                                                      //~0129I~//~0914R~//~0A21R~
            }                                                          //~0129I~//~0914R~//~0A21R~
      	}                                                            //~5A06I~//~0914R~//~0A21R~
      	else                                                         //~5A06I~//~0914R~//~0A21R~
      	{                                                            //~5A06I~//~0914R~//~0A21R~
        	trytimespan=pBoard.OnTimer(Board.IDC_SAVE); //elapsed time by second//~v@@@I~
            PutLine(fw," ",2);                                     //~5A06I~//~0914R~//~0A21R~
//            if (Seed!=0)                                         //~va24R~//~0914R~//~0A21R~
//            {                                                    //~va24R~//~0914R~//~0A21R~
//                line="#------ 途中経過 ------("+SEED_PREFIX+Seed;//~va24R~//~0914R~//~0A21R~//~v@@@R~
//                PutLine(fw,line,2);                              //~va24R~//~0914R~//~0A21R~
//            }                                                    //~va24R~//~0914R~//~0A21R~
//            else                                                 //~va24R~//~0914R~//~0A21R~
//                PutLine(fw,"#------ 途中経過 ------",2);         //~va24R~//~0914R~//~0A21R~//~v@@@R~
//        if (wnp.Sjlang)                                          //~va23I~//~0914R~//~0A21R~
//          line="#------ 途中経過 ------("+SEED_PREFIX+Seed+";"+SCORE_PREFIX+pBoard.Score+"/"+pBoard.ScoreMax;//~va24I~//~0914R~//~0A21R~//~v@@@R~
//        else                                                     //~va23I~//~0914R~//~0A21R~
//          line="#------PendingData-----("+SEED_PREFIX+Seed+";"+SCORE_PREFIX+pBoard.Score+"/"+pBoard.ScoreMax;//~va23I~//~0914R~//~0A21R~//~v@@@R~
            line="#------PendingData-----("+SEED_PREFIX+Seed+QNO_PREFIX+strQuestionNo+";"+SCORE_PREFIX+pBoard.Score+"/"+pBoard.ScoreMax+ELAPSEDTIME_PREFIX+trytimespan//~v@@@R~
											+PENALTY_PREFIX+pBoard.Penalty+")";//~v@@@I~
            PutLine(fw,line,2);                                    //~va24I~//~0914R~//~0A21R~
            for (y=0;y<Wnp.MAP_SIZE;y++)                               //~5A06I~//~0914R~//~0A21R~
            {                                                      //~5A06I~//~0914R~//~0A21R~
                sb.setLength(0);                                   //~5A06I~//~0914R~//~0A21R~
                for (x=0;x<Wnp.MAP_SIZE;x++)                           //~5A06I~//~0914R~//~0A21R~
                {                                                  //~5A06I~//~0914R~//~0A21R~
                    if (QuestionData[y][x]!=0)                     //~5A06I~//~0914R~//~0A21R~
                      if (QuestionData[y][x]>=10)                  //~va01R~//~0914R~//~0A21R~
                        sb.append("  "+QuestionData[y][x]);        //~5A06I~//~0914R~//~0A21R~
                      else                                         //~va01I~//~0914R~//~0A21R~
                        sb.append("   "+QuestionData[y][x]);       //~va01I~//~0914R~//~0A21R~
                    else                                           //~5A06I~//~0914R~//~0A21R~
                    {                                              //~v@@@I~
                    	int inpnumyx=inpnum[y][x];                 //~v@@@R~
                        if (inpnumyx!=0)                       //~5A06I~//~0914R~//~0A21R~//~v@@@R~
                        {                                          //~v@@@I~
    					 if (inpnumyx>=Board.NEXT_VALUE)           //~v@@@R~
                         {                                         //~v@@@I~
                         	inpnumyx-=Board.NEXT_VALUE;            //~v@@@I~
                          if (inpnumyx==10)                         //~v@@@R~
                            sb.append(" !"+inpnumyx);              //~v@@@R~
                          else                                     //~v@@@I~
                            sb.append("  !"+inpnumyx);             //~v@@@R~
                         }                                         //~v@@@I~
                         else                                      //~v@@@I~
                         {                                         //~v@@@I~
                          if (inpnumyx==10)                    //~va01R~//~0914R~//~0A21R~//~v@@@R~
                            sb.append(" ?"+inpnumyx);          //~5A06I~//~0914R~//~0A21R~//~v@@@R~
                          else                                     //~va01I~//~0914R~//~0A21R~
                            sb.append("  ?"+inpnumyx);         //~va01I~//~0914R~//~0A21R~//~v@@@R~
                         }                                         //~v@@@I~
                        }                                          //~v@@@I~
                        else                                       //~5A06I~//~0914R~//~0A21R~
                            sb.append("   _");                     //~va01R~//~0914R~//~0A21R~
                    }                                               //~5A06I~//~0914R~//~0A21R~//~v@@@R~
                    if (((x+1)%Wnp.BOARDTYPE)==0)                  //~va01R~//~0914R~//~0A21R~
                        sb.append("  ");                           //~va01R~//~0914R~//~0A21R~
                }                                                  //~5A06I~//~0914R~//~0A21R~
                if ((y+1)%Wnp.BOARDTYPE==0)                        //~va01I~//~0914R~//~0A21R~
                    sb.append("\n");                               //~va01I~//~0914R~//~0A21R~
                PutLine(fw,sb.toString(),1);                       //~5A06I~//~0914R~//~0A21R~
            }                                                      //~5A06I~//~0914R~//~0A21R~
            putMemo(pBoard,fw);                                    //~va68I~
            putStack(pBoard,fw);                                   //~va68I~
      	}                                                            //~5A06I~//~0914R~//~0A21R~
        Modifiedsw=0;                                              //~0129I~//~0914R~//~0A21R~
        fw.close();                                                //~0A21I~
      }//try                                                       //~0A21I~
      catch (Exception e)                                          //~0A21I~
      {                                                            //~0A21I~
      	Dump.println(e,"Serialize write");                               //~va68I~
     	try                                                        //~0A21I~
        {                                                          //~0A21I~
			e.printStackTrace();                                   //~0A21I~
        	if (fw!=null)                                          //~0A21I~
            	fw.close();                                        //~0A21I~
        }                                                          //~0A21I~
        catch(Exception e2)                                        //~0A21I~
        {                                                          //~0A21I~
        }                                                          //~0A21I~
                                                 //~0A21I~
      }//catch                                                     //~0A21I~
    }                                                              //~0102I~//~0914R~//~0A21R~
    else        //on input                                         //~0103R~//~0914R~//~0A21R~
    {                                                            //~0914R~//~0A21R~
//*read**                                                          //~va66I~
      restartTrytimeSpan=0;                                        //~v@@@I~
      try                                                          //~0A21I~
      {                                                                  //~0914R~//~0A21R~
        in=WnpView.context.openFileInput(name);                    //~0A21I~
//      br=(BufferedReader)ar;                                   //~0914R~//~0A21R~
        br=new BufferedReader(new InputStreamReader(in,"utf-8"));    //~0A21I~
        Datasw=DATA_FILE;                                          //~0129R~//~0914R~//~0A21R~
        ClearAnsData();                                            //~5923I~//~0914R~//~0A21R~
        xnpsub.boardnumclear(PendingData);                         //~5A06I~//~0914R~//~0A21R~
        xnpsub.boardnumclear(NextData);                            //~v@@@I~
        Modifiedsw=0;                                              //~0108I~//~0914R~//~0A21R~
        Score=ScoreMax=0;                                          //~va24I~//~0914R~//~0A21R~
//      try {                                                      //~5A06R~//~0914R~//~0A21R~
                                                          //~0A21I~
                                                                   //~0A21I~
        for (ii=0;;)                                               //~0102I~//~0914R~//~0A21R~
        {                                                          //~0102I~//~0914R~//~0A21R~
                                                                   //~0A21R~
            if ((line=br.readLine().trim()).equals(""))            //~5A06R~//~0914R~//~0A21R~
                continue;                                          //~5A06I~//~0914R~//~0A21R~
            if (Dump.Y) Dump.println("CPattern.Serialize read line="+line);//~v@@@I~
            if (line.charAt(0)=='#')                               //~5A06R~//~0914R~//~0A21R~
            {                                                      //~5A06I~//~0914R~//~0A21R~
                if ((pos=line.indexOf(SEED_PREFIX))>=0)            //~5A06I~//~0914R~//~0A21R~
                {                                                  //~v@@@I~
                    Seed=xnpsub.uatoi(line.substring(pos+SEED_PREFIX.length()));//~5A06R~//~0914R~//~0A21R~
                    pos=line.lastIndexOf(QNO_PREFIX);               //~v@@@I~
                    if (pos>0)                                     //~v@@@I~
                    {                                              //~v@@@I~
                    	int intQno=xnpsub.uatoi(line.substring(pos+QNO_PREFIX.length()));//~v@@@I~
                        strQuestionNo=new DecimalFormat("00000").format(intQno);//~v@@@I~
			            if (Dump.Y) Dump.println("CPattern.Serialize intQno="+intQno);//~v@@@I~
                    }                                              //~v@@@I~
                }                                                  //~v@@@I~
                if ((pos=line.indexOf(SCORE_PREFIX))>=0)           //~va24I~//~0914R~//~0A21R~
                {                                                  //~va24I~//~0914R~//~0A21R~
                    String subst=line.substring(pos+SCORE_PREFIX.length());//~va24R~//~0914R~//~0A21R~
                    Score=xnpsub.uatoi(subst);                     //~va24I~//~0914R~//~0A21R~
                    pos=subst.indexOf("/");                        //~va24R~//~0914R~//~0A21R~
                    if (pos>=0)                                    //~va24R~//~0914R~//~0A21R~
                        ScoreMax=xnpsub.uatoi(subst.substring(pos+1));//~va24I~//~0914R~//~0A21R~
                    pos=line.lastIndexOf(ELAPSEDTIME_PREFIX);      //~v@@@I~
                    if (pos>0)                                     //~v@@@I~
                    {                                              //~v@@@I~
                    	restartTrytimeSpan=xnpsub.uatoi(line.substring(pos+ELAPSEDTIME_PREFIX.length()));//~v@@@I~
                    }                                              //~v@@@I~
                    pos=line.lastIndexOf(PENALTY_PREFIX);          //~v@@@I~
                    if (pos>0)                                     //~v@@@I~
                    {                                              //~v@@@I~
                    	savedPenalty=xnpsub.uatoi(line.substring(pos+PENALTY_PREFIX.length()));//~v@@@R~
                    }                                              //~v@@@I~
                }                                                  //~va24I~//~0914R~//~0A21R~
                continue;                                          //~0102I~//~0914R~//~0A21R~
            }                                                      //~5A06I~//~0914R~//~0A21R~
//            if (line.length()==wnp.PEG_MAX) //81 digt and lf     //~va01R~//~0914R~//~0A21R~
//            {                                                    //~va01R~//~0914R~//~0A21R~
//                for (jj=0;jj<wnp.PEG_MAX;jj++)                   //~va01R~//~0914R~//~0A21R~
//                {                                                //~va01R~//~0914R~//~0A21R~
//                    ch=line.charAt(jj);                          //~va01R~//~0914R~//~0A21R~
//                    if (ch>='0' && ch<='9')                      //~va01R~//~0914R~//~0A21R~
//                        idata=ch-'0';                            //~va01R~//~0914R~//~0A21R~
//                    else                                         //~va01R~//~0914R~//~0A21R~
//                        idata=0;                                 //~va01R~//~0914R~//~0A21R~
//                    QuestionData[jj/wnp.MAP_SIZE][jj%wnp.MAP_SIZE]=idata;//~va01R~//~0914R~//~0A21R~
//                }                                                //~va01R~//~0914R~//~0A21R~
//                ii=PEG_MAX;                                      //~va01R~//~0914R~//~0A21R~
//                break;      //return                             //~va01R~//~0914R~//~0A21R~
//            }                                                    //~va01R~//~0914R~//~0A21R~
            StringTokenizer st=new StringTokenizer(line," ");    //~0914R~//~0A21R~
            int fldno=st.countTokens();//~0102I~                 //~0914R~//~0A21R~
            if (fldno<=0)   //no data or all space                 //~0102I~//~0914R~//~0A21R~
                continue;                                          //~0102I~//~0914R~//~0A21R~
            if (fldno!=Wnp.MAP_SIZE)                                   //~0102I~//~0914R~//~0A21R~
            {                                                      //~0102I~//~0914R~//~0A21R~
                err=1;                                             //~0102I~//~0914R~//~0A21R~
                continue;                                          //~0102I~//~0914R~//~0A21R~
            }                                                      //~0102I~//~0914R~//~0A21R~
            for (jj=0;jj<Wnp.MAP_SIZE;jj++)//~0102I~             //~0914R~//~0A21R~
            {                                                      //~0123I~//~0914R~//~0A21R~
                token=st.nextToken();                              //~5A06I~//~0914R~//~0A21R~
              if (token.charAt(0)=='?') //pending data             //~5A06I~//~0914R~//~0A21R~
              {                                                    //~5A06I~//~0914R~//~0A21R~
                if (token.length()>1)                              //~5A06I~//~0914R~//~0A21R~
                    PendingData[ii][jj]=xnpsub.uatoi(token.substring(1));//~5A06I~//~0914R~//~0A21R~
                else                                               //~5A06I~//~0914R~//~0A21R~
                    PendingData[ii][jj]=0;                         //~5A06I~//~0914R~//~0A21R~
              }                                                    //~5A06I~//~0914R~//~0A21R~
              else                                                 //~v@@@I~
              if (token.charAt(0)=='!') //by NEXT button           //~v@@@I~
              {                                                    //~v@@@I~
                if (token.length()>1)                              //~v@@@I~
                {                                                  //~v@@@I~
                    PendingData[ii][jj]=xnpsub.uatoi(token.substring(1));//~v@@@I~
                    NextData[ii][jj]=PendingData[ii][jj];          //~v@@@I~
                }                                                  //~v@@@I~
                else                                               //~v@@@I~
                    PendingData[ii][jj]=0;                         //~v@@@I~
              }                                                    //~v@@@I~
              else                                                 //~5A06I~//~0914R~//~0A21R~
                QuestionData[ii][jj]=xnpsub.uatoi(token);          //~5A06R~//~0914R~//~0A21R~
            }                                                      //~0123I~//~0914R~//~0A21R~
            if (++ii==Wnp.MAP_SIZE)                                    //~va01R~//~0914R~//~0A21R~
                break;                                             //~0102I~//~0914R~//~0A21R~
        }                                                          //~5A06R~//~0914R~//~0A21R~
        	memoData=readMemo(pBoard,br);                          //~va66I~
        	stackData=readStack(pBoard,br);                        //~va68I~
//      }                                                          //~5A06I~//~0914R~//~0A21R~
//      } catch (IOException e) {                                  //~5A06R~//~0914R~//~0A21R~
//          err=1;                                                 //~5A06R~//~0914R~//~0A21R~
//          e.printStackTrace();                                   //~5A06R~//~0914R~//~0A21R~
//      }                                                          //~5A06R~//~0914R~//~0A21R~
		br.close();                                                //~0A21I~
      }//try                                                       //~0A21I~
      catch (Exception e)                                          //~0A21I~
      {                                                            //~0A21I~
     	try                                                        //~0A21I~
        {                                                          //~0A21I~
			e.printStackTrace();                                   //~0A21I~
        	if (br!=null)                                          //~0A21I~
            	br.close();                                        //~0A21I~
        }                                                          //~0A21I~
        catch(Exception e2)                                        //~0A21I~
        {                                                          //~0A21I~
        }                                                          //~0A21I~
                                                 //~0A21I~
      }//catch                                                     //~0A21I~
        //~0102I~                                                //~0914R~//~0A21R~
        Name=name;                                                 //~5A06I~//~0914R~//~0A21R~
        if (err!=0)                                                   //~0102I~//~0914R~//~0A21R~
        {                                                          //~0108I~//~0914R~//~0A21R~
//        if (Wnp.Sjlang)                                          //~va23I~//~0914R~//~0A21R~
//          JOptionPane.showMessageDialog(null,"問題データに誤りがあります","NP", JOptionPane.ERROR_MESSAGE);//~5930R~//~0914R~//~0A21R~//~v@@@R~
//        else                                                     //~va23I~//~0914R~//~0A21R~
//          JOptionPane.showMessageDialog(null,"Inconsistent question data","NP", JOptionPane.ERROR_MESSAGE);//~va23I~//~0914R~//~0A21R~
			String emsg=WnpView.context.getText(R.string.ErrInconsistentQ).toString();//~0A21I~
            ButtonDlg.simpleAlertDialog(emsg,null);                     //~0A21R~//~v@@@R~
            Datasw|=DATA_ERR; //err data                           //~0129R~//~0914R~//~0A21R~
        }                                                          //~0108I~//~0914R~//~0A21R~
        else                                                       //~5A06I~//~0914R~//~0A21R~
            Modifiedsw=0;                                          //~5A06I~//~0914R~//~0A21R~
     }                                                              //~0102I~//~0914R~//~0A21R~
}//Serialize                                                     //~0914R~//~0A21R~
private void putMemo(Board Pboard,PrintWriter Pfw)                 //~va66R~
{                                                                  //~va66I~
	                                                               //~va66I~
    if (Dump.Y) Dump.println("CPattern.putMemo");                  //~va66I~
	if (Pboard.getCtrMemo()==0)                                    //~va66R~
    {                                                              //~va68I~
	    PutLine(Pfw,"#------No MemoData-----",1);                  //~va68I~
    	return;                                                    //~va66I~
    }                                                              //~va68I~
    PutLine(Pfw,"#------"+MEMO_HDR+"-----",1);                     //~va66R~
    StringBuffer sb=new StringBuffer(MAXLINE);                     //~va66I~
    for (int y=0;y<Wnp.MAP_SIZE;y++)                                   //~va66I~
    {                                                              //~va66I~
        sb.setLength(0);                                           //~va66I~
        for (int x=0;x<Wnp.MAP_SIZE;x++)                               //~va66I~
        {                                                          //~va66I~
        	int[] memo=Pboard.getMemo(y,x);                        //~va66R~
            sb.append("  ");                                       //~va66I~
            for (int ii=0;ii<Board.MAX_MEMO;ii++)                  //~va66I~
                sb.append(Integer.toString(memo[ii]));             //~va66R~
        }                                                          //~va66I~
        PutLine(Pfw,sb.toString(),1);                              //~va66I~
    }                                                              //~va66I~
}                                                                  //~va66I~
private void putStack(Board Pboard,PrintWriter Pfw)                //~va68I~
{                                                                  //~va68I~
                                                                   //~va68I~
	Stack<Board.Hole> history=Pboard.getHistory();                       //~va68R~
    int sz=history==null ? 0 : history.size();                     //~va68I~
    if (Dump.Y) Dump.println("CPattern.putStack size="+sz);        //~va68I~
    PutLine(Pfw,"#------"+STACK_HDR+sz+" -----",1);                //~va68I~
    if (sz!=0)                                                     //~va68I~
    {                                                              //~va68I~
    	Object[] ar=history.toArray(); //fist add is [0]           //~va68R~
        for (int ii=0;ii<sz;ii++)                                  //~va68I~
        {                                                          //~va68I~
        	Board.Hole h=(Board.Hole)ar[ii];                       //~va68R~
            String opr=" "+h.BoardX+h.BoardY+h.GetNum()+h.GetState()+h.GetErr();  //~va68I~
            PutLine(Pfw,opr,1);                                    //~va68I~
        }                                                          //~va68I~
    }                                                              //~va68I~
}                                                                  //~va68I~
private int[][][] readMemo(Board Pboard,BufferedReader Pbr)
{                                                                  //~va66I~
    String line="";                                                //~va66R~
    int[][][] memo=null;                                           //~va66I~
    int  err=0;                                                    //~va66I~
    if (Dump.Y) Dump.println("CPattern.readMemo");                 //~va66I~
  try                                                              //~va66I~
  {                                                                //~va66I~
    line=Pbr.readLine().trim();                                    //~va66I~
    if (Dump.Y) Dump.println("CPattern.readMemo first line="+line+",leng="+line.length());//~va66R~
    if (line.length()==0)                                            //~va66R~
    {                                                              //~va66I~
    	line=Pbr.readLine().trim();                                //~va66I~
	    if (Dump.Y) Dump.println("CPattern.readMemo 2nd line="+line);//~va66I~
	    if (line.indexOf(MEMO_HDR)<0)                              //~va66I~
			return null;                                           //~va66R~
    }                                                              //~va66I~
    memo=new int[Wnp.MAP_SIZE][Wnp.MAP_SIZE][Board.MAX_MEMO];      //~va66R~
    for (int y=0;y<Wnp.MAP_SIZE;y++)                               //~va66I~
    {                                                              //~va66I~
    	line=Pbr.readLine().trim();                                 //~va66I~
        if (Dump.Y) Dump.println("CPattern.readMemo read line="+line);//~va66I~
        StringTokenizer st=new StringTokenizer(line," ");          //~va66I~
        int fldno=st.countTokens();                                //~va66I~
        if (fldno!=Wnp.MAP_SIZE)                                   //~va66I~
        {                                                          //~va66I~
            err=1;                                                 //~va66I~
            continue;                                              //~va66I~
        }                                                          //~va66I~
        for (int x=0;x<Wnp.MAP_SIZE;x++)                           //~va66I~
        {                                                          //~va66I~
		    String token=st.nextToken();                           //~va66I~
            for (int ii=0;ii<Board.MAX_MEMO;ii++)                        //~va66I~
            {                                                      //~va66I~
            	int num=Character.getNumericValue(token.charAt(ii));//~va66I~
                if (num==0)                                        //~va66I~
                	break;                                         //~va66I~
                memo[y][x][ii]=num;                                //~va66I~
            }                                                      //~va66I~
        }//x                                                       //~va66I~
    }//y                                                           //~va66I~
  }                                                                //~va66I~
  catch (IOException e)                                            //~va66I~
  {                                                                //~va66I~
    if (Dump.Y) Dump.println("CPattern.readMemo read IOException last="+line);//~va66I~
  	return null;                                                   //~va66I~
  }                                                                //~va66I~
    return memo;                                                   //~va66R~
                                                                   //~va66I~
}                                                                  //~va66I~
private Stack<Board.Hole> readStack(Board Pboard,BufferedReader Pbr)     //~va68R~
{                                                                  //~va68I~
    String line="";                                                //~va68I~
    Stack<Board.Hole> history=null;                                      //~va68R~
    if (Dump.Y) Dump.println("CPattern.readStack");                //~va68I~
    try                                                            //~va68I~
    {                                                              //~va68I~
        line=Pbr.readLine().trim();                                //~va68I~
        if (Dump.Y) Dump.println("CPattern.readStack first line="+line+",leng="+line.length());//~va68I~
        int pos=line.indexOf(STACK_HDR);                           //~va68I~
        if (pos<0)                                                 //~va68I~
        	return null;                                           //~va68I~
        int sz=xnpsub.uatoi(line.substring(pos+STACK_HDR.length()));//~va68I~
        if (Dump.Y) Dump.println("CPattern.readStack sz="+sz);     //~va68I~
        history=new Stack<Board.Hole>();                                 //~va68I~
        for (int ii=0;ii<sz;ii++)                                  //~va68I~
        {                                                          //~va68I~
            line=Pbr.readLine().trim();                            //~va68I~
            if (Dump.Y) Dump.println("CPattern.readStack read line="+line);//~va68I~
            int x=Character.getNumericValue(line.charAt(0));       //~va68I~
            int y=Character.getNumericValue(line.charAt(1));       //~va68I~
            int num=Character.getNumericValue(line.charAt(2));     //~va68I~
            int stat=Character.getNumericValue(line.charAt(3));    //~va68I~
            int err=Character.getNumericValue(line.charAt(4));     //~va68I~
            Board.Hole h=Pboard.new Hole(x,y,num,stat,err);         //~va68R~
            if (Dump.Y) Dump.println("CPattern.readStack push="+h);//~va68I~
            history.push(h);                                      //~va68I~
        }                                                          //~va68I~
    }                                                              //~va68I~
    catch (IOException e)                                          //~va68I~
    {                                                              //~va68I~
      	if (Dump.Y) Dump.println("CPattern.readStack read IOException last="+line);//~va68I~
      	return null;                                               //~va68I~
    }                                                              //~va68I~
    if (Dump.Y) Dump.println("CPattern.readStack exit size="+history.size());//~va68I~
    return history;                                                //~va68I~
}                                                                  //~va68I~
//private void equals(CPattern src)                           //~5921R~//~0914R~
//{                                                                //~0914R~
//    Name=src.Name;                                                 //~0102R~//~0914R~
//    int x,y;                                                       //~0102I~//~0914R~
//    for (y=0;y<wnp.MAP_SIZE;y++)                                       //~0102I~//~0914R~
//        for (x=0;x<wnp.MAP_SIZE;x++)                                   //~0102I~//~0914R~
//            QuestionData[y][x]=src.QuestionData[y][x];             //~0102I~//~0914R~
//}                                                                //~0914R~
//private void PutLine (FileWriter fw,String line,int Plfno) throws IOException //~5A06R~//~0914R~//~0A21R~
private void PutLine (PrintWriter fw,String line,int Plfno)        //~0A21I~
{                                                                  //~0102I~//~0914R~//~0A21R~
    if (Dump.Y) Dump.println("CPattern.PutLine Plfno="+Plfno+",line="+line);//~v@@@I~
//  try {                                                          //~5A06R~//~0914R~//~0A21R~
//      fw.write(line,0,line.length());                          //~0914R~//~0A21R~
        fw.append(line);                                           //~0A21I~
                                    //~0102I~                    //~0914R~//~0A21R~
//      String sep=System.getProperty("line.separator");                                    //~0102R~//~0914R~//~0A21R~//~va68R~
        for (int ii=0;ii<Plfno;ii++)                                       //~0102I~//~0914R~//~0A21R~
        {                                                              //~0102I~//~0914R~//~0A21R~
//          fw.write(sep,0,sep.length());                                           //~0102R~//~0914R~//~0A21R~
            fw.append(sep);                                        //~0A21I~
        }                                                        //~0914R~//~0A21R~
//  } catch (IOException e) {                                      //~5A06R~//~0914R~//~0A21R~
//                                                                 //~5A06R~//~0914R~//~0A21R~
//      e.printStackTrace();                                       //~5A06R~//~0914R~//~0A21R~
//  }                                                              //~5A06R~//~0914R~//~0A21R~
}                                                                  //~0102I~//~0914R~//~0A21R~

public boolean ChkData()                                         //~5921R~//~0914R~
{                                                                  //~0102I~//~0914R~
    for (int y=0;y<Wnp.MAP_SIZE;y++)                                   //~0102I~//~0914R~
        for (int x=0;x<Wnp.MAP_SIZE;x++)                               //~0102I~//~0914R~
            if (QuestionData[y][x]!=0)            //~0102I~        //~0914R~
                return true;                                       //~0102I~//~0914R~
    return false;                                                  //~0102I~//~0914R~
}                                                                  //~0102I~//~0914R~
//*********************************************************************//~0103I~
public void SetNewData(int bx,int by,int num,int Psortsw)         //~5921R~
{                                                                  //~0108I~
    if ((int)QuestionData[bx][by]!=num)                                 //~0108R~
    {                                                              //~0108I~
    	QuestionData[bx][by]=num;                                  //~0108I~
    	Modifiedsw=1;                                                 //~0130I~
        Datasw&=~DATA_ANS;    //ans not avail                      //~0129I~
    }                                                              //~0108I~
}                                                                  //~0108I~
//*********************************************************************//~0129I~
public void SetAnsData(int bx,int by,int num)                      //~5922R~
{                                                                  //~0129I~
	Datasw|=DATA_ANS;		//for later modification;              //~0129I~
    AnswerData[bx][by]=num;                                        //~0129I~
}                                                                  //~0129I~
//*********************************************************************//~5923I~
public int  GetAnsData(int bx,int by)                              //~5923R~
{                                                                  //~5923I~
    return AnswerData[bx][by];                                     //~5923I~
}                                                                  //~5923I~
public void ClearAnsData()                                         //~5923R~
{                                                                  //~5923I~
	Datasw&=~DATA_ANS;		//for later modification;              //~5923I~
    for (int y=0;y<Wnp.MAP_SIZE;y++)                               //~5923I~
        for (int x=0;x<Wnp.MAP_SIZE;x++)                           //~5923I~
        	AnswerData[y][x]=0;                                    //~5923I~
}                                                                  //~5923I~
public int GetModified()                                           //~5925R~
{                                                                  //~0108I~
	int modsw=Modifiedsw;                                              //~0108I~
	Modifiedsw=0;                                                  //~0108I~
    return modsw;                                                  //~0108I~
}                                                                  //~0108I~
//*******************************                                  //~0A21I~
public void SerializeFromIndex(String PindexEntry)                 //~v@@@I~
{                                                                  //~v@@@I~
                                         //~v@@@I~
                  //~v@@@I~
    int  ii,jj,offs;                                        //~v@@@I~
           //~v@@@I~
//**********************************                               //~v@@@I~
    Datasw=DATA_FILE;                                              //~v@@@I~
    ClearAnsData();                                                //~v@@@I~
    xnpsub.boardnumclear(PendingData);                             //~v@@@I~
    xnpsub.boardnumclear(NextData);                                //~v@@@I~
    Score=0;                                                       //~v@@@R~
    Seed=xnpsub.uatoi(PindexEntry.substring(CPIndex.H_OFFS_SEED,CPIndex.H_OFFS_SEED+CPIndex.H_LEN_SEED));//~v@@@I~
    strQuestionNo=PindexEntry.substring(0,CPIndex.KEY_LEN);        //~v@@@I~
    intProbLevel=(int)PindexEntry.charAt(CPIndex.H_OFFS_LEVEL)-0x30;//~v@@@I~
    strProbLevel=Board.Slevelstr[intProbLevel];                    //~v@@@I~
    ScoreMax=xnpsub.uatoi(PindexEntry.substring(CPIndex.H_OFFS_MAXSCORE,CPIndex.H_OFFS_MAXSCORE+CPIndex.SCORE_LEN));//~v@@@I~
   	offs=CPIndex.H_OFFS_QDATA;                                     //~v@@@I~
	for (ii=0;ii<Wnp.MAP_SIZE;ii++)                                 //~v@@@I~
    {                                                              //~v@@@I~
		for (jj=0;jj<Wnp.MAP_SIZE;jj++)                            //~v@@@I~
        {                                                          //~v@@@I~
    		QuestionData[ii][jj]=(int)(PindexEntry.charAt(offs))-0x30;//~v@@@I~
            offs++;                                                //~v@@@I~
        }                                                          //~v@@@I~
                                                                   //~v@@@I~
    }                                                              //~v@@@I~
}//SerializeFromIndex                                              //~v@@@I~
                                                                   //~v@@@I~
//*******************************                                  //~v@@@I~
}//class CPattern                                                  //~5921I~
