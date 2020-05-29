package np.jnp.npanew;                                                //~2718R~//+0528R~
//CID://+va15R~:                                                   //~va15I~
//*************************************************************    //~va15I~
//va15:051112 if return maked data when multisolution detected,it is redundant;delete redundancy option add//~va15I~
//*************************************************************    //~va15I~
public class M99 {
public static final int  M99F_LIMIT=0x01;		//out of candidate by the reason of SETMAX limit//~5922M~
public static final int  M99F_PATERN=0x02;		//pos requested by patern file//~5922M~
public static final int  M99F_PATERNUSER=0x04;	//with 0x02;user specified position//~va15I~
public			  int    flag;		//flag for make                //~5A01R~
public            int    fnum;    	//fixed number tbl,0 if not fixed//~5A01R~
public            int    fseq;    	//fixed seqno for each pos     //~5A01R~
public            int    dlvl;    	//difficulty level             //~5A01R~
public            int    cmsk;    	//candidate bit mask for each pos//~5A01R~
public            int    ccnt;    	//candidate count for each pos //~5A01R~
public            int    pair;    	//candidate paired once(to avoid repeat)//~5A01R~
public M99(){                                                      //~5A01I~
				clear();	                                       //~5A01I~
}                                                                  //~5A01I~
public void clear(){                                               //~5A01I~
                           flag=0;		//flag for make            //~5A01I~
                           fnum=0;    	//fixed number tbl,0 if not fixed//~5A01I~
                           fseq=0;    	//fixed seqno for each pos //~5A01I~
                           dlvl=0;    	//difficulty level         //~5A01I~
                           cmsk=0;    	//candidate bit mask for each pos//~5A01I~
                           ccnt=0;    	//candidate count for each pos//~5A01I~
                           pair=0;    	//candidate paired once(to avoid repeat)//~5A01I~
}                                                                  //~5A01I~
public void copy(M99 Psrc){                                        //~5A01I~
                           flag=Psrc.flag;		//flag for make    //~5A01I~
                           fnum=Psrc.fnum;    	//fixed number tbl,0 if not fixed//~5A01I~
                           fseq=Psrc.fseq; 	//fixed seqno for each pos//~5A01I~
                           dlvl=Psrc.dlvl; 	//difficulty level     //~5A01I~
                           cmsk=Psrc.cmsk; 	//candidate bit mask for each pos//~5A01I~
                           ccnt=Psrc.ccnt; 	//candidate count for each pos//~5A01I~
                           pair=Psrc.pair; 	//candidate paired once(to avoid repeat)//~5A01I~
}                                                                  //~5A01I~
}//class M99                                                       //~5922M~
