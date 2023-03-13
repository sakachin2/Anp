//*CID://+va52R~:                             update#=    1;       //+va52I~
//******************************************************************//+va52I~
//va52 221103 BTMJ-1aj1 androd11(api30) deprecated at api30;Handler default constructor(requires parameter)//+va52I~
//******************************************************************//+va52I~
package np.jnp.npanew;                                                //~2718R~//~0528R~

import android.os.Handler;
import android.os.Message;
import android.os.Looper;                                          //~1aj1I~//+va52I~

//CID://+va15R~:                                                   //~va15I~
//*************************************************************    //~va15I~
//*************************************************************    //~va15I~
public class NppTimer extends Handler                              //~0915R~
{                                                                  //~0915I~
	private int callCtr;                                  //~0915I~
	private long interval;                                         //~0915I~
	private int msgId;                                             //~0915I~
	private boolean swActive,swRepeat/*,swCoalesce*/;                           //~0915I~
	private Board board;                                          //~0915I~
    public NppTimer(Looper Plooper)                                //+va52I~
    {                                                              //+va52I~
    	super(Plooper);                                             //+va52I~
    }                                                              //+va52I~
    public NppTimer(int msgId,long interval,Board board)           //~0915I~
    {                                                              //~0915I~
        this(Looper.getMainLooper());	//on MainThread        //+va52I~
    	swActive=false;                                                //~0915I~
    	swRepeat=false;                                            //~0915I~
        this.msgId=msgId;                                          //~0915I~
        this.interval=interval;                                    //~0915I~
        this.board=board;                                          //~0915I~
        callCtr=0;                                                 //~0915I~
    }                                                              //~0915I~
	@Override                                                      //~0915I~
	public void handleMessage(Message msg)                         //~0915I~
    {                                                              //~0915I~
    	if (swActive)	                                           //~0915I~
        {                                                          //~0915I~
        	board.pView.OnTimer(msgId);                                  //~0915I~//~0A06R~
        	if (swRepeat || callCtr!=0)                              //~0915I~
        		nextMessage(interval);                              //~0915I~
            else                                                   //~0A06I~
                swActive=false;                                    //~0A06I~
            callCtr++;                                             //~0915I~
        }                                                          //~0915I~
    }                                                              //~0915I~
	public void nextMessage(long delay)                            //~0915I~
    {                                                              //~0915I~
    	removeMessages(msgId);                                      //~0915I~
        sendMessageDelayed(obtainMessage(msgId),delay);            //~0915I~
    }                                                              //~0915I~
	public void start()                                            //~0915I~
    {                                                              //~0915I~
    	swActive=true;
		nextMessage(interval);                                     //~0A06R~
    }                                                              //~0915I~
	public void stop()                                             //~0915I~
    {                                                              //~0915I~
    	swActive=false;                                                //~0915I~
    	removeMessages(msgId);                                      //~0915I~
    }                                                              //~0915I~
	public void setRepeats(boolean repeat)                         //~0915I~
    {                                                              //~0915I~
    	swRepeat=repeat;                                           //~0915I~
    }                                                              //~0915I~
//	public void setCoalesce(boolean coalesce)                      //~0915I~
//    {                                                              //~0915I~
//    	swCoalesce=coalesce;                                       //~0915I~
//   }                                                              //~0915I~
}//class NppTimer                                                       //~5922M~//~0915R~
