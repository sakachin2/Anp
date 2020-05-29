//*CID://+va30R~: update#=   2;                                   //~va32R~//~va30I~
//******************************************************************************//~va01I~//~va30M~
//v032:051109 when method9 depth overflow multi-sol rc should be returned? if once after solution found//~v032I~//~va30M~
//            and for not make max depth should not be limited     //~v032I~//~va30M~
//va06:051104 make performance up;chk elapsed time at each method9 depth and shift to next candidate//~va06I~//~va30M~
//v021:051102 redundancy chk:display also reduduncy depth count    //~v021I~//~va30M~
//va01:051013 5*5 support                                          //~va01I~//~va30M~
//******************************************************************************//~va01I~//~va30M~
package              np.jnp.npanew ;                         //~v032R~//~2718R~//+va30R~
import java.util.Arrays;

public class NPWORKT{                                              //~5A01R~
                                                                   //~5A01I~
private static final int MAP_SIZE=Wnp.MAP_SIZE;                    //~va01R~
                                                                   //~5A01I~
public static final int STAT_DATA_CONFLICT    =0x1000;             //~5922M~
public static final int STAT_MULTISOL         =0x2000;             //~5922M~
public static final int STAT_TIMEOUT          =0x4000;             //~5922M~
public static final int STAT_M9TIMEOUT        =0x8000;             //~va06I~
public static final int STAT_PILOT            =0x0100;             //~5922M~
public static final int STAT_PILOTMSG         =0x0200;             //~5922M~
public static final int STAT_MAKE             =0x0400;             //~5922M~
public static final int STAT_RCHK             =0x0800;             //~5922M~
public static final int STAT_ANSOFMAKE        =0x0010;             //~5922M~
public static final int STAT_MAX_TAELVL       =0x0020;             //~v032I~
//public static final int MAX_TAELVL            =15;               //~v032R~
public			int    taelvl=0;			//try and err depth    //~5A01I~
public			int    stat=0;		//process status               //~5A01I~
public		  	String    method=null;   		//candidate bit reseted method//~5A01R~
public         int    seqno=0;     		//seqno when a number is fixed//~5A01R~
public         int    idatano=0;   		//initial datano           //~5A01R~
public         int    level=0;   		//dificulty level          //~5A01R~
public         int    rclistcnt=0;                                 //~5A01I~
public         int    rclevel=0;                                   //~v021I~
public          M99[][]   m99=new M99[MAP_SIZE][MAP_SIZE];    	//per place//~5A01R~
public         int[][]    fmsk=new int[3][MAP_SIZE];    	//fixed number mask for each row,col,box//~5A01R~
public         int[][]    fcnt=new int[3][MAP_SIZE];    	//fixed count  for each row,col,box//~5A01R~
public         int[][]    icnt=new int[3][MAP_SIZE];    	//initial data cnt for make puzzle//~5A01R~
public         int[]      rclist; 			//static area in xnpsub//~5922R~
	public NPWORKT(){                                              //~5A01I~

		for (int ii=0;ii<MAP_SIZE;ii++)                            //~5A01R~
    		for (int jj=0;jj<MAP_SIZE;jj++)                        //~5A01R~
            	m99[ii][jj]=new M99();                             //~5A01I~
    }                                                              //~5A01I~
	public void clear(){
		taelvl=0;			//try and err depth                    //~5A01I~
		stat=0;		//process status                               //~5A01I~
        method=new String("");                                     //~5A01M~
		seqno=0;
		idatano=0;
		level=0;
		rclistcnt=0;                                               //~5A01M~
		rclevel=0;                                                 //~v021I~
		for (int ii=0;ii<MAP_SIZE;ii++)                            //~5A01R~
			for (int jj=0;jj<MAP_SIZE;jj++)                        //~5A01R~
				m99[ii][jj].clear();                               //~5A01R~
		for (int ii=0;ii<3;ii++)	{
			Arrays.fill(fmsk[ii],0);
			Arrays.fill(fcnt[ii],0);
			Arrays.fill(icnt[ii],0);
		}
		rclist=null;                                               //~5A01R~
		
	}
//    public Object clone(){                                       //~5A01R~
//        Object npwt=null;                                        //~5A01R~
//        try{                                                     //~5A01R~
//            npwt=(NPWORKT)super.clone();                         //~5A01R~
//        }                                                        //~5A01R~
//        catch(CloneNotSupportedException e){                     //~5A01R~
//            xnpsub.uerrexit("Clone not supported",null);         //~5A01R~
//        }                                                        //~5A01R~
//        return npwt;                                             //~5A01R~
//    }                                                            //~5A01R~
	public void copy(NPWORKT Psrc)                                 //~5A01I~
    {                                                              //~5A01I~
		taelvl		=Psrc.taelvl;			//try and err depth    //~5A01I~
		stat		=Psrc.stat;		//process status               //~5A01I~
        if (Psrc.method!=null)                                     //~5A01I~
  			method	=new String(Psrc.method);   		//candidate bit reseted method//~5A01I~
        else                                                       //~5A01I~
        	method	=new String("");                               //~5A01R~
   		seqno		=Psrc.seqno;     		//seqno when a number is fixed//~5A01I~
   		idatano		=Psrc.idatano;   		//initial datano       //~5A01I~
   		level		=Psrc.level;   		//dificulty level          //~5A01I~
   		rclistcnt	=Psrc.rclistcnt;                               //~5A01I~
   		rclevel     =Psrc.rclevel;                                 //~v021I~
		for (int ii=0;ii<MAP_SIZE;ii++)                            //~5A01I~
			for (int jj=0;jj<MAP_SIZE;jj++)                        //~5A01I~
				m99[ii][jj].copy(Psrc.m99[ii][jj]);                //~5A01I~
		for (int ii=0;ii<3;ii++)	{                              //~5A01I~
			System.arraycopy(Psrc.fmsk[ii],0,fmsk[ii],0,MAP_SIZE); //~5A01I~
			System.arraycopy(Psrc.fcnt[ii],0,fcnt[ii],0,MAP_SIZE); //~5A01I~
			System.arraycopy(Psrc.icnt[ii],0,icnt[ii],0,MAP_SIZE); //~5A01I~
		}                                                          //~5A01I~
        rclist=Psrc.rclist;                                        //~5A01I~
    }                                                              //~5A01I~
}
