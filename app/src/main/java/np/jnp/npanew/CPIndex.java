//CID://+v@@@R~:                                                   //~v@@@I~
package np.jnp.npanew;                                             //+v@@@R~

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.content.SharedPreferences;

import np.jnp.npanew.R;


public class CPIndex {                                            //~5921I~//~v@@@R~
                                                                 //~5921I~
public static       int MAXHISTR    =20;                            //~5921I~//~v@@@R~
public static       int MAXLEVEL    =20;                           //~v@@@R~
public static       int MAXSCORE    =20;                           //~v@@@R~
public static final int KEY_LEN     =5;                            //~v@@@I~
public static final int SCORE_LISTOFFS=3;                          //~v@@@R~
public static final int SCORE_LISTLEN =5;                          //~v@@@I~
public static final int SCORE_LEN     =8;                          //~v@@@I~
public static final int LEVEL_LEN     =1;                          //~v@@@I~
public static final String[] displayLevel={"   ","Esy","Med","Hrd","H+ ","H++"};//~v@@@I~
                                                                   //~v@@@I~
public static final int INDEXID_HISTORY=0;                         //~v@@@I~
public static final int INDEXID_SCORE  =1;                         //~v@@@I~
public static final int INDEXID_LEVEL  =2;                         //~v@@@I~
//Qno+Qdata+Timestamp+seed+level+maxscore+score                    //~v@@@R~
public static final int H_OFFS_QDATA=KEY_LEN;                      //~v@@@R~
public static final int H_LEN_QDATA =81;                           //~v@@@I~
public static final int H_OFFS_TS   =H_OFFS_QDATA+H_LEN_QDATA;     //~v@@@I~
public static final int H_LEN_TS    =13;    //yymmdd-hhmmss        //~v@@@R~
public static final int H_OFFS_TSLIST=H_OFFS_TS+2;                 //~v@@@I~
public static final int H_LEN_TSLIST=9;    //mmdd-hhmm             //~v@@@R~
public static final int H_OFFS_SEED =H_OFFS_TS+H_LEN_TS;           //~v@@@I~
public static final int H_LEN_SEED  =5; //max 32767                //~v@@@I~
public static final int H_OFFS_LEVEL =H_OFFS_SEED+H_LEN_SEED;      //~v@@@R~
public static final int H_OFFS_MAXSCORE =H_OFFS_LEVEL+LEVEL_LEN;   //~v@@@R~
public static final int H_OFFS_SCORE =H_OFFS_MAXSCORE+SCORE_LEN;  //~v@@@I~
public static final int H_OFFS_EOL  =H_OFFS_SCORE+SCORE_LEN;       //~v@@@I~
public static final int H_ENTRYSZ   =H_OFFS_EOL+1;                 //~v@@@I~
//Qno+Score+level+maxscore                                         //~v@@@R~
public static final int S_OFFS_SCORE=KEY_LEN;                      //~v@@@I~
public static final int S_OFFS_LEVEL=S_OFFS_SCORE+SCORE_LEN;       //~v@@@R~
public static final int S_OFFS_UID  =S_OFFS_LEVEL+LEVEL_LEN;       //~v@@@R~
public static final int S_LEN_UID   =3;                            //~v@@@R~
public static final int S_OFFS_MAXSCORE=S_OFFS_UID+S_LEN_UID;      //~v@@@I~
public static final int S_OFFS_EOL  =S_OFFS_MAXSCORE+SCORE_LEN;    //~v@@@I~
public static final int S_ENTRYSZ   =S_OFFS_EOL+1;                 //~v@@@I~
//Qno+Maxscore+level+score                                         //~v@@@R~
public static final int L_OFFS_MAXSCORE=KEY_LEN;                   //~v@@@I~
public static final int L_OFFS_LEVEL=L_OFFS_MAXSCORE+SCORE_LEN;    //~v@@@R~
public static final int L_OFFS_SCORE=L_OFFS_LEVEL+LEVEL_LEN;       //~v@@@R~
public static final int L_LEN_SCORE =8;                            //~v@@@R~
public static final int L_OFFS_EOL  =L_OFFS_SCORE+L_LEN_SCORE;     //~v@@@R~
public static final int L_ENTRYSZ   =L_OFFS_EOL+1;                 //~v@@@I~
                                                                   //~v@@@I~
private static final String strPrefKeyQNO="QuestionNo";            //~v@@@I~
                                                                   //~v@@@I~
                                                                   //~v@@@I~
public static final String FNM_INDEX_LEVEL="CPI_level";            //~v@@@I~
public static final String FNM_INDEX_SCORE="CPI_score";            //~v@@@I~
public static final String FNM_INDEX_HISTR="CPI_histr";            //~v@@@I~
private StringBuffer sbLevel,sbHistory,sbScore;                                      //~v@@@I~
public String strQno=null;                                         //~v@@@I~
public int    intQno;                                              //~v@@@I~
                                    //~v@@@I~
                                                                   //~v@@@I~
//*************************************************************************//~v@@@R~
public CPIndex()                                             //~5921R~//~v@@@R~
{
    initIndexBuffer();                                              //~v@@@I~
    readwriteQNo(0/*read*/);                                  //~v@@@I~
    intQno=new Integer(strQno);                                     //~v@@@I~
}
//**********************************************************************//~v@@@I~
//*preference read/write                                               *//~v@@@I~
//**********************************************************************//~v@@@I~
private String readwriteQNo(int Popt)                              //~v@@@I~
{                                                                  //~v@@@I~
	//******************                                               //~v@@@I~
	SharedPreferences pref=WnpView.context.getSharedPreferences("PreferencesEx",Context.MODE_PRIVATE);//~v@@@I~

	switch (Popt)                                                  //~v@@@I~
    {                                                              //~v@@@I~
    case 0:	//read from preference                                 //~v@@@I~
        strQno=pref.getString(strPrefKeyQNO,"00000"/*default value*/);//~v@@@R~
        break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	intQno++;                                                  //~v@@@I~
	    strQno=new DecimalFormat("00000").format(intQno);          //~v@@@I~
		SharedPreferences.Editor editor=pref.edit();
		editor.putString(strPrefKeyQNO,strQno);                    //~v@@@I~
        editor.commit();                                           //~v@@@I~
    }                                                              //~v@@@I~
    return strQno;                                                 //~v@@@I~
}//readwriteQNo                                                    //~v@@@I~
//*************************************************************************//~v@@@I~
private void initIndexBuffer()                                     //~v@@@R~
{                                                                //~0914R~//~0A21R~
    sbScore=readIndex(FNM_INDEX_SCORE);                    //~v@@@I~
    sbHistory=readIndex(FNM_INDEX_HISTR);                    //~v@@@I~
    sbLevel=readIndex(FNM_INDEX_LEVEL);                    //~v@@@I~
}                                                                  //~v@@@I~
//*************************************************************************//~v@@@I~
private void updateIndex(int Pindexid)                             //~v@@@R~
{                                                                  //~v@@@I~
	switch(Pindexid)                                               //~v@@@I~
	{                                                              //~v@@@I~
	case INDEXID_SCORE:                                            //~v@@@I~
	    writeIndex(FNM_INDEX_SCORE,sbScore);                       //~v@@@I~
    	break;                                                     //~v@@@I~
	case INDEXID_LEVEL:                                            //~v@@@I~
	    writeIndex(FNM_INDEX_LEVEL,sbLevel);                       //~v@@@I~
    	break;                                                     //~v@@@I~
	default:                                                       //~v@@@I~
    	writeIndex(FNM_INDEX_HISTR,sbHistory);                     //~v@@@I~
    }                                                              //~v@@@I~
}                                                                  //~v@@@I~
//**********************************************************************//~v@@@I~
private StringBuffer readIndex(String Pfname)                      //~v@@@I~
{                                                                  //~v@@@I~
    BufferedReader br=null;                                        //~v@@@I~
    InputStream in=null;                                           //~v@@@I~
    String line;                                                   //~v@@@I~
    StringBuffer sb=null;
    int len;//~v@@@I~
//******************                                               //~v@@@I~
    try                                                            //~v@@@I~
    {                                                              //~v@@@I~
        in=WnpView.context.openFileInput(Pfname);                  //~v@@@I~
        br=new BufferedReader(new InputStreamReader(in,"utf-8"));  //~v@@@I~
        line=br.readLine();                                        //~v@@@I~
        len=line.length();                                          //~v@@@I~
    	sb=new StringBuffer(len);                                  //~v@@@I~
        sb.append(line);                                           //~v@@@I~
        br.close();
    }    //~v@@@I~
    catch(FileNotFoundException e)                                 //~v@@@I~
    {                                                              //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }                                                              //~v@@@I~
    catch (Exception e)                                            //~v@@@I~
    {                                                              //~v@@@I~
		e.printStackTrace();                                       //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }//catch                                                       //~v@@@I~
                                             //~v@@@I~
    if (sb==null)                                                  //~v@@@I~
    	sb=new StringBuffer();                                  //~v@@@I~
                      //~v@@@I~
    return sb;                                                     //~v@@@I~
}//readIndex                                                       //~v@@@I~
//**********************************************************************//~v@@@I~
private void writeIndex(String Pfname,StringBuffer Psb)            //~v@@@I~
{                                                                  //~v@@@I~
    PrintWriter   fw=null;                                         //~v@@@I~
    OutputStream out=null;                                         //~v@@@I~
//******************                                               //~v@@@I~
    try                                                            //~v@@@I~
    {                                                              //~v@@@I~
        out=WnpView.context.openFileOutput(Pfname,Context.MODE_PRIVATE);//~v@@@I~
        fw=new PrintWriter(new OutputStreamWriter(out,"UTF-8"));   //~v@@@I~
        fw.print(Psb);                              //~v@@@I~
    }                                                              //~v@@@I~
    catch (Exception e)                                            //~v@@@I~
    {                                                              //~v@@@I~
		e.printStackTrace();                                       //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }//catch                                                       //~v@@@I~
    if (fw!=null)                                                  //~v@@@I~
    	fw.close();                                                //~v@@@I~
}//writeIndex                                                      //~v@@@I~
//**********************************************************************//~v@@@I~
private String getFilename(String Pkey)                            //~v@@@R~
{                                                                  //~v@@@I~
	String rc="Q"+Pkey;                                            //~v@@@R~
//    System.out.println("filename="+rc);                          //~v@@@R~
	return rc;                                                     //~v@@@R~
}//getFilename                                                     //~v@@@I~
//**********************************************************************//~v@@@I~
private void deleteEntry(String Pkey)                              //~v@@@R~
{   
//******************                                               //~v@@@I~
	String pfnm=getFilename(Pkey);                                 //~v@@@R~
    WnpView.context.deleteFile(pfnm);                                              //~v@@@I~
}//writeIndex                                                      //~v@@@I~
//**********************************************************************//~v@@@I~
public String makeEntry(int Pindexid,CPattern Ppat)                //~v@@@R~
{                                                                  //~v@@@I~
    String str,key;                                                    //~v@@@R~
	StringBuffer sbentry;                                          //~v@@@I~
    int oldhindex;                                                  //~v@@@I~
//******************                                               //~v@@@I~
	switch(Pindexid)                                               //~v@@@I~
    {                                                              //~v@@@I~
    case INDEXID_SCORE:                                            //~v@@@I~
		sbentry=new StringBuffer(S_ENTRYSZ);                       //~v@@@I~
    	sbentry.append(Ppat.strQuestionNo);                        //~v@@@I~
	    str=String.format("%8d",Ppat.Score);                       //~v@@@R~
    	sbentry.append(str);                                       //~v@@@R~
	    str=String.format("%d",Ppat.intProbLevel);                 //~v@@@R~
        sbentry.append(str);                                       //~v@@@R~
        sbentry.append(Ppat.strUserID);                            //~v@@@I~
	    str=String.format("%8d",Ppat.ScoreMax);                    //~v@@@I~
        sbentry.append(str);                                  //~v@@@I~
    	break;                                                     //~v@@@I~
    case INDEXID_LEVEL:                                            //~v@@@I~
		sbentry=new StringBuffer(L_ENTRYSZ);                       //~v@@@I~
    	sbentry.append(strQno);                                    //~v@@@I~
	    str=String.format("%8d",Ppat.ScoreMax);                    //~v@@@I~
    	sbentry.append(str);                                       //~v@@@R~
	    str=String.format("%d",Ppat.intProbLevel);                 //~v@@@I~
        sbentry.append(str);                                       //~v@@@I~
    	sbentry.append("        "); //score                        //~v@@@I~
    	break;                                                     //~v@@@I~
    default:	//time seq                                         //~v@@@R~
		sbentry=new StringBuffer(H_ENTRYSZ);                       //~v@@@I~
    	sbentry.append(strQno);                                    //~v@@@I~
    	str=Qdata2str(Ppat);                                       //~v@@@M~
    	sbentry.append(str);                                       //~v@@@I~
        oldhindex=compareEq(sbHistory,H_ENTRYSZ,str,H_OFFS_QDATA,H_LEN_QDATA);    //search same Q//~v@@@R~
        if (oldhindex<0)                                           //~v@@@R~
        {                                                          //~v@@@I~
        	key=readwriteQNo(1);	//new QuestionNo               //~v@@@R~
            Ppat.strQuestionNo=key;                                //~v@@@I~
        }                                                          //~v@@@I~
        else                                                       //~v@@@I~
        {                                                          //~v@@@I~
        	int offs=oldhindex*H_ENTRYSZ;                          //~v@@@I~
        	key=sbHistory.substring(offs,offs+KEY_LEN);            //~v@@@I~
        }                                                          //~v@@@I~
        sbentry.replace(0,KEY_LEN,key);                            //~v@@@I~
    	str=(new SimpleDateFormat("yyMMdd-HHmmss")).format(new Date());//~v@@@M~
    	sbentry.append(str);                                       //~v@@@I~
	    str=String.format("%5d",Ppat.Seed);                        //~v@@@I~
    	sbentry.append(str);                                       //~v@@@I~
	    str=String.format("%d",Ppat.intProbLevel);                 //~v@@@I~
    	sbentry.append(str);                                       //~v@@@I~
	    str=String.format("%8d",Ppat.ScoreMax);                    //~v@@@I~
    	sbentry.append(str);                                       //~v@@@I~
    	sbentry.append("        ");    //score(8)                  //~v@@@R~
    	break;                                                     //~v@@@I~
    }                                                              //~v@@@I~
    sbentry.append(";");      //EOL                                //~v@@@I~
    String rc=sbentry.toString();                                  //~v@@@I~
//    System.out.println("entry="+rc);                             //~v@@@R~
    return rc;                                                     //~v@@@R~
}//makeEntry                                                       //~v@@@I~
//**********************************************************************//~v@@@I~
public String Qdata2str(CPattern Ppat)                             //~v@@@R~
{                                                                  //~v@@@I~
                                                                   //~v@@@I~
    int [][] ppint;                                                //~v@@@I~
    int ii,jj;                                                //~v@@@R~
    StringBuffer sb;                                               //~v@@@R~
    String str;                                                    //~v@@@I~
//******************
	sb=new StringBuffer(H_LEN_QDATA);                              //~v@@@I~
    ppint=Ppat.QuestionData;                                       //~v@@@I~
    for (ii=0;ii<Wnp.MAP_SIZE;ii++)                                //~v@@@I~
    {                                                              //~v@@@I~
    	for (jj=0;jj<Wnp.MAP_SIZE;jj++)                            //~v@@@I~
        {                                                          //~v@@@I~
        	sb.append(ppint[ii][jj]);                              //~v@@@R~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
    str=sb.toString();                                             //~v@@@I~
//    System.out.println("pat2X="+str);                            //~v@@@R~
    return str;                                                    //~v@@@R~
}//Qdata2str                                                       //~v@@@R~
//**********************************************************************//~v@@@I~
//*search descendant position                                      //~v@@@I~
//**********************************************************************//~v@@@I~
private int compareHigh(StringBuffer Psb,int Pentrysz,int Pkeyoffs,int Pkeylen,String Pkey)//~v@@@R~
{                                                                  //~v@@@I~
	int ii,jj,offs,offs2,maxentry;                                 //~v@@@I~
    boolean swhigh;                                                //~v@@@I~
//******************                                               //~v@@@I~
	maxentry=Psb.length()/Pentrysz;                                //~v@@@R~
//System.out.println("compHigh entry="+Pkey+"index="+Psb);         //~v@@@R~
    for (ii=0,offs=0;ii<maxentry;ii++,offs+=Pentrysz)              //~v@@@R~
    {                                                              //~v@@@I~
    	swhigh=false;                                              //~v@@@I~
    	for (jj=0,offs2=Pkeyoffs;jj<Pkeylen;jj++,offs2++)          //~v@@@I~
        {                                                          //~v@@@I~
//System.out.println("compHigh"+Pkey.charAt(jj)+":"+Psb.charAt(offs+offs2));//~v@@@R~
        	if (Pkey.charAt(jj)>Psb.charAt(offs+offs2))            //~v@@@R~
            {                                                      //~v@@@I~
            	swhigh=true;                                       //~v@@@I~
            	break;                                             //~v@@@I~
            }                                                      //~v@@@I~
        	if (Pkey.charAt(jj)<Psb.charAt(offs+offs2))            //~v@@@I~
            	break;                                             //~v@@@I~
        }                                                          //~v@@@I~
        if (swhigh)	//high score                                   //~v@@@R~
        	break;                                                 //~v@@@I~
    }                                                              //~v@@@I~
    return ii;                                                     //~v@@@I~
}//compareHigh                                                     //~v@@@R~
//**********************************************************************//~v@@@I~
//*search on History index                                         //~v@@@R~
//*return entryNo,-1 if not found                                  //~v@@@I~
//**********************************************************************//~v@@@I~
private int compareEq(StringBuffer Psb,int Pentrysz,String Pkey,int Pkeyoffs,int Pkeylen)//~v@@@R~
{                                                                  //~v@@@I~
    int ii,offs,offs2;                                             //~v@@@I~
//******************                                               //~v@@@I~
	for (offs=0,ii=0;;)                                            //~v@@@I~
    {                                                              //~v@@@I~
		offs2=Psb.indexOf(Pkey,offs);                              //~v@@@I~
        if (offs2<0)                                               //~v@@@I~
        	break;                                                 //~v@@@I~
        ii=offs2/Pentrysz;                                         //~v@@@I~
        if (offs2-ii*Pentrysz==Pkeyoffs)                           //~v@@@I~
        	break;                                                 //~v@@@I~
        offs=offs2+1;                                              //~v@@@R~
    }                                                              //~v@@@I~
    if (offs2<0)                                                   //~v@@@I~
    	return -1;                                                 //~v@@@I~
    return ii;                                                     //~v@@@I~
}//compareEq                                                       //~v@@@R~
//**********************************************************************//~v@@@I~
public void insertIndex(CPattern Ppat)                              //~v@@@I~
{                                                                  //~v@@@I~
	int ii,entno,offs,swupdate,oldQno,newQno;                      //~v@@@R~
    String newentry,key;                               //~v@@@R~
	StringBuffer sb;                                         //~v@@@R~
//******************                                               //~v@@@I~
	try                                                            //~v@@@I~
    {                                                              //~v@@@I~
//history entry
		oldQno=intQno;//~v@@@I~
        newentry=makeEntry(INDEXID_HISTORY,Ppat);
        newQno=intQno;//~v@@@I~                           //~v@@@I~
    //*history                                                     //~v@@@R~
        swupdate=0;                                                //~v@@@I~
        sb=sbHistory;                                             //~v@@@R~
        if (oldQno!=newQno)//new question                                           //~v@@@R~
        {                                                          //~v@@@I~
        	swupdate=1;                                            //~v@@@I~
        	sb.insert(0,newentry);                                    //~v@@@R~
        }                                                          //~v@@@I~
        entno=sb.length()/H_ENTRYSZ;                               //~v@@@R~
        if (entno>MAXHISTR)                                        //~v@@@R~
        {                                                          //~v@@@R~
            for (ii=MAXHISTR,offs=ii*H_ENTRYSZ;ii<entno;ii++)        //~v@@@R~
            {                                                      //~v@@@R~
                key=sb.substring(offs,offs+KEY_LEN);               //~v@@@R~
                if (compareEq(sbScore,S_ENTRYSZ,key,0,KEY_LEN)<0   //~v@@@R~
                &&  compareEq(sbLevel,L_ENTRYSZ,key,0,KEY_LEN)<0)  //~v@@@R~
                {                                                  //~v@@@R~
        			swupdate=1;                                    //~v@@@I~
                    deleteEntry(key);                        //~v@@@R~
                    sb.delete(ii*H_ENTRYSZ,(ii+1)*H_ENTRYSZ);      //~v@@@R~
                }                                                  //~v@@@R~
                else                                               //~v@@@R~
                    offs+=H_ENTRYSZ;                               //~v@@@R~
            }                                                      //~v@@@R~
        }                                                          //~v@@@R~
        if (swupdate==1)                                              //~v@@@I~
        	updateIndex(INDEXID_HISTORY);                         //~v@@@R~
    }                                                                        //~v@@@R~
    catch (Exception e)                                            //~v@@@I~
    {                                                              //~v@@@I~
		e.printStackTrace();                                       //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }//catch                                                       //~v@@@I~
}//insertIndex                                                     //~v@@@I~
//**********************************************************************//~v@@@I~
public void updateIndexLevel(CPattern Ppat)                       //~v@@@I~
{                                                                  //~v@@@I~
	int ii,entno,swupdate=0,offs;                                  //~v@@@R~
    String entry,key,level,maxscore;                               //~v@@@R~
	StringBuffer sb;                                               //~v@@@I~
//******************                                               //~v@@@I~
	try                                                            //~v@@@I~
    {                                                              //~v@@@I~
    //*level                                                       //~v@@@I~
        sb=sbLevel;                                                //~v@@@I~
        entry=makeEntry(INDEXID_LEVEL,Ppat);                       //~v@@@I~
        level=entry.substring(L_OFFS_LEVEL,L_OFFS_LEVEL+LEVEL_LEN);//~v@@@R~
        maxscore=entry.substring(L_OFFS_MAXSCORE,L_OFFS_MAXSCORE+SCORE_LEN);//~v@@@I~
//update History index to set LEVEL                                //~v@@@R~
        key=entry.substring(0,KEY_LEN);                            //~v@@@I~
        if ((ii=compareEq(sbHistory,H_ENTRYSZ,key,0,KEY_LEN))>=0)    //~v@@@I~
        {                                                          //~v@@@I~
        	offs=ii*H_ENTRYSZ;                                     //~v@@@I~
            sbHistory.replace(offs+H_OFFS_LEVEL,offs+H_OFFS_LEVEL+LEVEL_LEN,level);//~v@@@R~
            sbHistory.replace(offs+H_OFFS_MAXSCORE,offs+H_OFFS_MAXSCORE+SCORE_LEN,maxscore);//~v@@@I~
        	updateIndex(INDEXID_HISTORY);                          //~v@@@I~
        }                                                          //~v@@@I~
//update level index                                               //~v@@@I~
        swupdate=0;                                                //~v@@@M~
        if ((ii=compareEq(sb,L_ENTRYSZ,key,0,KEY_LEN))<0)          //~v@@@I~
        {                                                          //~v@@@I~
        	ii=compareHigh(sb,L_ENTRYSZ,L_OFFS_MAXSCORE,SCORE_LEN,maxscore);//~v@@@R~
        	if (ii<MAXLEVEL)                                       //~v@@@R~
        	{                                                      //~v@@@R~
        		swupdate=1;                                        //~v@@@R~
        		sb.insert(ii*L_ENTRYSZ,entry);                     //~v@@@R~
        	}                                                      //~v@@@R~
        }                                                          //~v@@@I~
        entno=sb.length()/L_ENTRYSZ;                               //~v@@@M~
        if (entno>MAXLEVEL)                                        //~v@@@M~
        {                                                          //~v@@@M~
            sb.setLength(MAXLEVEL*L_ENTRYSZ);                      //~v@@@M~
            swupdate=1;                                            //~v@@@I~
        }                                                          //~v@@@M~
        if (swupdate==1)                                           //~v@@@I~
        	updateIndex(INDEXID_LEVEL);                            //~v@@@I~
    }                                                              //~v@@@I~
    catch (Exception e)                                            //~v@@@I~
    {                                                              //~v@@@I~
		e.printStackTrace();                                       //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }//catch                                                       //~v@@@I~
}//updateIndexLevel                                                     //~v@@@I~
//**********************************************************************//~v@@@I~
public void registerScore(CPattern Ppat)                           //~v@@@I~
{                                                                  //~v@@@I~
	int ii,entno,swupdate=0,offs;                    //~v@@@I~
    String entry,key,score;                                  //~v@@@R~
	StringBuffer sb;                                               //~v@@@I~
//******************                                               //~v@@@I~
	try                                                            //~v@@@I~
    {                                                              //~v@@@I~
    //*score                                                       //~v@@@I~
        sb=sbScore;                                                //~v@@@I~
        entry=makeEntry(INDEXID_SCORE,Ppat);                       //~v@@@I~
//update History index to set SCORE                                //~v@@@R~
        key=entry.substring(0,KEY_LEN);                            //~v@@@I~
        score=entry.substring(S_OFFS_SCORE,S_OFFS_SCORE+SCORE_LEN);//~v@@@R~
        if ((ii=compareEq(sbHistory,H_ENTRYSZ,key,0,KEY_LEN))>=0)    //~v@@@I~
        {                                                          //~v@@@I~
        	offs=ii*H_ENTRYSZ;                                     //~v@@@I~
            sbHistory.replace(offs+H_OFFS_SCORE,offs+H_OFFS_SCORE+SCORE_LEN,score);//~v@@@R~
        	updateIndex(INDEXID_HISTORY);                          //~v@@@I~
        }                                                          //~v@@@I~
//update Level index to set SCORE                                  //~v@@@R~
        if ((ii=compareEq(sbLevel,L_ENTRYSZ,key,0,KEY_LEN))>=0)    //~v@@@I~
        {                                                          //~v@@@I~
        	offs=ii*L_ENTRYSZ;                                     //~v@@@I~
            sbLevel.replace(offs+L_OFFS_SCORE,offs+L_OFFS_SCORE+L_LEN_SCORE,score);//~v@@@I~
        	updateIndex(INDEXID_LEVEL);                            //~v@@@I~
        }                                                          //~v@@@I~
//update score index                                               //~v@@@I~
        ii=compareHigh(sb,S_ENTRYSZ,S_OFFS_SCORE,SCORE_LEN,score); //~v@@@R~
        if (ii<S_ENTRYSZ)                                          //~v@@@I~
        {                                                          //~v@@@I~
        	swupdate=1;                                            //~v@@@I~
        	sb.insert(ii*S_ENTRYSZ,entry);                         //~v@@@I~
        }                                                          //~v@@@I~
        entno=sb.length()/S_ENTRYSZ;                               //~v@@@I~
        if (entno>MAXSCORE)                                        //~v@@@I~
        {                                                          //~v@@@I~
        	swupdate=1;                                            //~v@@@I~
            sb.setLength(MAXSCORE*S_ENTRYSZ);                      //~v@@@I~
        }                                                          //~v@@@I~
        if (swupdate==1)                                           //~v@@@I~
        	updateIndex(INDEXID_SCORE);                            //~v@@@R~
    }                                                              //~v@@@I~
    catch (Exception e)                                            //~v@@@I~
    {                                                              //~v@@@I~
		e.printStackTrace();                                       //~v@@@I~
    	System.out.println("writeIndex exception"+e.toString());   //~v@@@I~
    }//catch                                                       //~v@@@I~
}//registerScore                                                   //~v@@@R~
//**********************************************************************//~v@@@I~
//*get inedx list for dialog ListView output                       //~v@@@I~
//**********************************************************************//~v@@@I~
public String[] getIndexList(int Psubmenuid)                       //~v@@@I~
{                                                                  //~v@@@I~
	StringBuffer sb,sbentry;                                       //~v@@@R~
    String [] strList;                                             //~v@@@I~
    int entrysz,ii,entryno,offs,intlevel;                          //~v@@@R~
//    char[] cmaxscore=new char[SCORE_LEN],ckey=new char[KEY_LEN],cscore=new char[SCORE_LEN],cts=new char[H_LEN_TSLIST];//~v@@@R~
    char[] cmaxscore=new char[SCORE_LEN],ckey=new char[KEY_LEN],cscore=new char[SCORE_LEN];//~v@@@I~
    char[] cts=new char[H_LEN_TS];                                 //~v@@@I~
    char[] clevel=new char[LEVEL_LEN],cuid=new char[S_LEN_UID];    //~v@@@R~
//**********************                                           //~v@@@I~
	switch(Psubmenuid)                                             //~v@@@I~
    {                                                              //~v@@@I~
    case CPattern.FILE_LIST_LEVEL:        //old hard->easy         //~v@@@R~
    	sb=sbLevel;                                                //~v@@@I~
        entrysz=L_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    case CPattern.FILE_LIST_SCORE:        //list score             //~v@@@I~
    	sb=sbScore;                                                //~v@@@I~
        entrysz=S_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	sb=sbHistory;                                              //~v@@@I~
        entrysz=H_ENTRYSZ;                                         //~v@@@I~
    }                                                              //~v@@@I~
    entryno=sb.length()/entrysz;                                     //~v@@@I~
    strList=new String[entryno];                                     //~v@@@I~
    sbentry=new StringBuffer(80);                                  //~v@@@I~
                                                                   //~v@@@I~
    for (ii=0,offs=0;ii<entryno;ii++,offs+=entrysz)                //~v@@@I~
    {                                                              //~v@@@I~
    	sbentry.setLength(0);                                      //~v@@@I~
        switch(Psubmenuid)                                         //~v@@@I~
        {                                                          //~v@@@I~
        case CPattern.FILE_LIST_LEVEL:        //old hard->easy     //~v@@@I~
            sb.getChars(offs,offs+KEY_LEN,ckey,0);                 //~v@@@I~
            sb.getChars(offs+L_OFFS_MAXSCORE,offs+L_OFFS_MAXSCORE+SCORE_LEN,cmaxscore,0);//~v@@@R~
            sb.getChars(offs+L_OFFS_LEVEL,offs+L_OFFS_LEVEL+LEVEL_LEN,clevel,0);//~v@@@R~
            intlevel=(int)clevel[0]-0x30;                          //~v@@@I~
            sb.getChars(offs+L_OFFS_SCORE,offs+L_OFFS_SCORE+L_LEN_SCORE,cscore,0);//~v@@@I~
            sbentry.append(ckey,0,KEY_LEN);                        //~v@@@I~
            sbentry.append(":");                                   //~v@@@I~
            sbentry.append(cmaxscore,SCORE_LISTOFFS,SCORE_LISTLEN);//~v@@@R~
            sbentry.append("-");                                   //~v@@@I~
            sbentry.append(displayLevel[intlevel]);                //~v@@@R~
            sbentry.append(":");                                   //~v@@@I~
            if (cscore[L_LEN_SCORE-1]!=' ')                        //~v@@@R~
            	sbentry.append(cscore,SCORE_LISTOFFS,SCORE_LISTLEN);//~v@@@R~
            break;                                                 //~v@@@I~
        case CPattern.FILE_LIST_SCORE:        //list score         //~v@@@I~
            sb.getChars(offs,offs+KEY_LEN,ckey,0);                 //~v@@@I~
            sb.getChars(offs+S_OFFS_SCORE,offs+S_OFFS_SCORE+SCORE_LEN,cscore,0);//~v@@@R~
            sb.getChars(offs+S_OFFS_LEVEL,offs+S_OFFS_LEVEL+LEVEL_LEN,clevel,0);//~v@@@R~
            intlevel=(int)clevel[0]-0x30;                          //~v@@@I~
            sb.getChars(offs+S_OFFS_UID,offs+S_OFFS_UID+S_LEN_UID,cuid,0);//~v@@@I~
            sb.getChars(offs+S_OFFS_MAXSCORE,offs+S_OFFS_MAXSCORE+SCORE_LEN,cmaxscore,0);//~v@@@I~
            sbentry.append(ckey,0,KEY_LEN);                        //~v@@@I~
            sbentry.append(":");                                   //~v@@@I~
            sbentry.append(cuid,0,S_LEN_UID);                      //~v@@@R~
//          sbentry.append("-");                                   //~v@@@R~
            sbentry.append(cscore,SCORE_LISTOFFS,SCORE_LISTLEN);   //~v@@@R~
            sbentry.append("/");                                   //~v@@@I~
            sbentry.append(cmaxscore,SCORE_LISTOFFS,SCORE_LISTLEN);//~v@@@I~
            sbentry.append(":");                                   //~v@@@I~
            sbentry.append(displayLevel[intlevel]);                //~v@@@I~
            break;                                                 //~v@@@I~
        default:                              //timeseq            //~v@@@R~
            sb.getChars(offs,offs+KEY_LEN,ckey,0);                 //~v@@@R~
//          sb.getChars(offs+H_OFFS_TSLIST,offs+H_OFFS_TSLIST+H_LEN_TSLIST,cts,0);//~v@@@R~
            sb.getChars(offs+H_OFFS_TS,offs+H_OFFS_TS+H_LEN_TS,cts,0);//~v@@@I~
            sb.getChars(offs+H_OFFS_LEVEL,offs+H_OFFS_LEVEL+LEVEL_LEN,clevel,0);//~v@@@R~
            intlevel=(int)clevel[0]-0x30;                          //~v@@@I~
            sb.getChars(offs+H_OFFS_SCORE,offs+H_OFFS_SCORE+SCORE_LEN,cscore,0);//~v@@@R~
//          sbentry.append(ckey,0,KEY_LEN);                        //~v@@@R~
//          sbentry.append(":");                                   //~v@@@R~
            sbentry.append(cts,0,H_LEN_TSLIST);                    //~v@@@I~
            sbentry.append(":");                                   //~v@@@I~
            sbentry.append(displayLevel[intlevel]);                //~v@@@I~
//          sbentry.append(":");                                   //~v@@@R~
            if (cscore[SCORE_LEN-1]!=' ')                          //~v@@@R~
            	sbentry.append(cscore,SCORE_LISTOFFS,SCORE_LISTLEN);//~v@@@R~
        }                                                          //~v@@@I~
        strList[ii]=sbentry.toString();                            //~v@@@I~
    }                                                              //~v@@@I~
    return strList;                                                //~v@@@I~
}//getIndexList(int Pmenuid)                                       //~v@@@I~
//**********************************************************************//~v@@@I~
//*get inedx list for dialog ListView output                       //~v@@@I~
//**********************************************************************//~v@@@I~
public String indexEntrySelected(int Pmsgsw,int Psubmenuid,int Ppos)//~v@@@R~
{                                                                  //~v@@@I~
	StringBuffer sb;
	String key,entryHistory;//~v@@@I~
    int entrysz,ii,entryno,offs;                                   //~v@@@I~
//**********************                                           //~v@@@I~
	switch(Psubmenuid)                                             //~v@@@I~
    {                                                              //~v@@@I~
    case CPattern.FILE_LIST_LEVEL:        //old hard->easy         //~v@@@I~
    	sb=sbLevel;                                                //~v@@@I~
        entrysz=L_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    case CPattern.FILE_LIST_SCORE:        //list score             //~v@@@I~
    	sb=sbScore;                                                //~v@@@I~
        entrysz=S_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	sb=sbHistory;                                              //~v@@@I~
        entrysz=H_ENTRYSZ;                                         //~v@@@I~
    }                                                              //~v@@@I~
    entryno=sb.length()/entrysz;                                   //~v@@@I~
    if (Ppos>=entryno)                                             //~v@@@I~
    	return null;                                                    //~v@@@I~
    offs=Ppos*entrysz;                                             //~v@@@I~
    key=sb.substring(offs,offs+KEY_LEN);                           //~v@@@I~
    if (entrysz!=H_ENTRYSZ)	//timeseq,contains qdata)              //~v@@@I~
    {                                                              //~v@@@I~
        if ((ii=compareEq(sbHistory,H_ENTRYSZ,key,0,KEY_LEN))<0)   //~v@@@I~
        {                                                          //~v@@@I~
        	if (Pmsgsw==1)                                         //~v@@@I~
            {                                                      //~v@@@I~
        		String emsg=WnpView.context.getText(R.string.ErrNosavedDataKey).toString();//~v@@@R~
				ButtonDlg.simpleAlertDialog(emsg+key,null);        //~v@@@R~
            }                                                      //~v@@@I~
        	return null;                                                //~v@@@I~
        }                                                          //~v@@@I~
        offs=ii*H_ENTRYSZ;                                         //~v@@@I~
    }                                                              //~v@@@I~
    entryHistory=sbHistory.substring(offs,offs+H_ENTRYSZ);         //~v@@@R~
    return entryHistory;                                           //~v@@@R~
}//indexEntrySelected                                              //~v@@@R~
//**********************************************************************//~v@@@I~
//*get inedx list for dialog ListView output                       //~v@@@I~
//**********************************************************************//~v@@@I~
public int deleteIndexEntry(int Psubmenuid,int Ppos)               //~v@@@I~
{                                                                  //~v@@@I~
	StringBuffer sb;                                               //~v@@@I~
	String key,deletedentry;                          //~v@@@I~
    int entrysz,entryno,offs;                                   //~v@@@I~
//**********************                                           //~v@@@I~
	switch(Psubmenuid)                                             //~v@@@I~
    {                                                              //~v@@@I~
    case CPattern.FILE_LIST_LEVEL:        //old hard->easy         //~v@@@I~
    	sb=sbLevel;                                                //~v@@@I~
        entrysz=L_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    case CPattern.FILE_LIST_SCORE:        //list score             //~v@@@I~
    	sb=sbScore;                                                //~v@@@I~
        entrysz=S_ENTRYSZ;                                         //~v@@@I~
        break;                                                     //~v@@@I~
    default:                                                       //~v@@@I~
    	sb=sbHistory;                                              //~v@@@I~
        entrysz=H_ENTRYSZ;                                         //~v@@@I~
    }                                                              //~v@@@I~
    entryno=sb.length()/entrysz;                                   //~v@@@I~
    if (Ppos>=entryno)                                             //~v@@@I~
    	return 0;                                                  //~v@@@I~
    offs=Ppos*entrysz;                                             //~v@@@I~
    deletedentry=sb.substring(offs,offs+entrysz);                  //~v@@@I~
    if (entrysz==H_ENTRYSZ)	//timeseq,contains qdata               //~v@@@I~
    {                                                              //~v@@@I~
	    key=deletedentry.substring(0,KEY_LEN);                     //~v@@@I~
        if (compareEq(sbScore,S_ENTRYSZ,key,0,KEY_LEN)>=0)         //~v@@@I~
        {                                                          //~v@@@I~
        	String emsg=WnpView.context.getText(R.string.ErrRemainsHighscoreList).toString();//~v@@@I~
            String maxscore=deletedentry.substring(H_OFFS_SCORE,H_OFFS_SCORE+SCORE_LEN);//~v@@@I~
			ButtonDlg.simpleAlertDialog(emsg+maxscore,null);       //~v@@@I~
        	return -1;	//bypass                                   //~v@@@I~
        }                                                          //~v@@@I~
        else                                                       //~v@@@I~
        if (compareEq(sbLevel,L_ENTRYSZ,key,0,KEY_LEN)>=0)         //~v@@@I~
        {                                                          //~v@@@I~
        	String emsg=WnpView.context.getText(R.string.ErrRemainsLevelList).toString();//~v@@@I~
            String score=deletedentry.substring(H_OFFS_MAXSCORE,H_OFFS_MAXSCORE+SCORE_LEN);//~v@@@I~
			ButtonDlg.simpleAlertDialog(emsg+score,null);          //~v@@@I~
        	return -1;	//bypass                                   //~v@@@I~
        }                                                          //~v@@@I~
        else                                                       //~v@@@I~
        {                                                          //~v@@@I~
	    	sb.delete(offs,offs+entrysz);                          //~v@@@I~
            deleteEntry(key);                                      //~v@@@I~
        }                                                          //~v@@@I~
    }                                                              //~v@@@I~
    else                                                           //~v@@@I~
	    sb.delete(offs,offs+entrysz);                              //~v@@@I~
    return 1;                                                      //~v@@@I~
}//deleteIndexEntry                                                //~v@@@I~
//*******************************                                  //~0A21I~
}//class CPIndex                                                  //~5921I~//~v@@@R~
