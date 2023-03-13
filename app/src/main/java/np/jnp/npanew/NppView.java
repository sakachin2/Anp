//*CID://+va6aR~: update#=   60;                                   //+va6aR~
//*****************************************************************//~va30I~
//va6a 230310 show all memo by long press                          //+va6aI~
//va42:200524 google play accept over apilevel:26(android-8.0); optionmenu was deprecated(onCreateOptionmenu is not called)//~va42I~
//va30:120717 (NPA21 fontsize depending screen size)               //~va30I~
//*****************************************************************//~va30I~
package np.jnp.npanew;                                                //~va30R~//~va42R~

import android.app.Activity;
import android.content.Context;                                    //~0913I~
import android.content.res.Configuration;
                           //~0913I~
import android.graphics.Canvas;
import android.graphics.Point;
                                 //~0913I~
import android.view.KeyEvent;
import android.view.View;

import android.view.MotionEvent;//~0913I~

import np.jnp.npanew.utils.AG;                                     //~va42R~
import np.jnp.npanew.utils.Dump;                                   //~va42R~
import np.jnp.npanew.utils.Utils;                                  //~va42R~
//~0913I~


public class NppView extends View                                  //~0914R~
{                                                                  //~0914I~
    private static final long TH_LONGPRESS=1000L;	//1sec         //+va6aI~
	private boolean swinitialized;                                 //~0A05R~
	private int orientation=0;                                     //~@@@@R~
	public static Context context;//~0915I~                        //~@@@@R~
	public static Activity activity;                               //~va30I~
	private Wnp wnp;                                               //~0915R~
	public  WnpView wnpView;//~0914R~                              //~@@@@R~
//    private int touchX,touchY,touchAction,ballX,ballY,ballAction;                                             //~0914R~
    private Point point;                                           //~@@@@I~
    private static final int BOARDTYPE=3;                              //~0914R~
//  public int layoutHeight,layoutWidth;                           //~va42R~
    public int hhActionBar;                                        //~va42I~
//  private int oldLayoutWidth;                                    //~va42R~
    private int oldHHActionBar;                                    //~va42I~
    private long tsDown;	//ACTION_DOWN timestamp by millisec    //+va6aI~
                                                                   //~0914I~
	public NppView(Context context)                                //~0914R~
    {                                                              //~0914I~
    	super(context);
        swinitialized=false;                                           //~0A05I~//~@@@@M~
    	NppView.context=context;//~0914I~
    	NppView.activity=(Activity)context;                        //~va30I~
       	setFocusable(true);                                        //~0A05R~
       	setFocusableInTouchMode(true);                             //~0A05R~
        point=new Point(0,0);                                      //~@@@@I~
    	initWnp();                                                 //~va42R~
    }                                                              //~0914I~
//******************                                               //~0915I~
    @Override                                                      //~0914R~
    protected void onDraw(Canvas canvas)                           //~0914I~
    {    
    	if (Dump.Y) Dump.println("NppView.onDraw swinitialized="+swinitialized+",ww="+getWidth()+",hh="+getHeight());//~va42I~
    	if (Dump.Y) Dump.println("NppView.onDraw actionBarHeight="+Utils.getActionBarHeight()+",titleBarHeight="+Utils.getTitleBarHeight());//~va42I~
    	hhActionBar=Utils.getActionBarHeight()+Utils.getTitleBarHeight();//~va42R~
        if (hhActionBar!=oldHHActionBar)                           //~va42I~
        {                                                          //~va42I~
        	oldHHActionBar=hhActionBar;                            //~va42I~
            wnp.initialize(hhActionBar);                           //~va42I~
            wnpView.pButtonDlg.initialize(false);                  //~va42I~
        }                                                          //~va42I~
    	if (!swinitialized)                                                //~0915R~//~0A05R~
        	return;                                                //~0915I~
//        layoutWidth=getWidth();                                  //~va42R~
//        layoutHeight=getHeight();                                //~va42R~
//        if (oldLayoutWidth!=layoutWidth)                         //~va42R~
//        {                                                        //~va42R~
//            wnp.initialize();                                    //~va42R~
//            oldLayoutWidth=layoutWidth;                          //~va42R~
//        }                                                        //~va42R~
//    	if (Dump.Y) Dump.println("NppView.onDraw AG.scr ww="+layoutWidth+",hh="+layoutHeight);//~va42I~
        if (orientation!=0)                                        //~@@@@R~
        {                                                          //~@@@@I~
        	if ((orientation==Configuration.ORIENTATION_PORTRAIT)!=(getHeight()>getWidth()))//~@@@@I~
            {                                                      //~@@@@I~
            	invalidate();                                      //~@@@@I~
                return;                                            //~@@@@I~
            }                                                      //~@@@@I~
        	orientation=0;                                         //~@@@@R~
        	wnp.orientationChanged();                              //~@@@@M~
        	wnpView.orientationChanged(false/*not initdlg*/);      //~@@@@I~
        	wnpView.pButtonDlg.orientationChanged();               //~@@@@R~
        }                                                          //~@@@@I~
    	wnpView.OnDraw(canvas);                                 //~0914I~//~0915R~
    }                                                              //~0914I~
//******************                                               //~0915I~
    @Override                                                      //~0915I~
    public void onWindowFocusChanged(boolean hasFocus)          //~0915I~
    {                                                              //~0915I~
    	int ww=getWidth();                                         //~@@@@I~
//    System.out.println("scrwidth"+ww);                           //~@@@@R~
    	if (ww!=0)                       //~0915I~         //~0A05R~//~@@@@R~
        {                                                          //~@@@@I~
    		if (!swinitialized)                                    //~0A05R~
            {                                                      //~@@@@I~
//  			if (getWidth()>getHeight()) //landscape            //~@@@@R~
//          		ButtonDlg.simpleExitAlertDialog(context.getText(R.string.ErrNotPortrate).toString());//~@@@@R~
            	initWnp(); 	//wait until getWidth() returns screen width//+0915I~                                   //~0915I~//~0A05R~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
    }                                                              //~0915I~
//******************                                               //~0915I~
	public void initWnp()                                          //~0915I~
    {                                                              //~0915I~
        swinitialized=true;                                                //~0915I~//~0A05R~
        wnp=new Wnp(context,this,BOARDTYPE);                       //~0915I~
        wnpView=new WnpView(context,this,wnp);                     //~0915I~
    }                                                              //~0915I~
//******************                                               //~@@@@I~
	public void orientationChanged(int Porientation)               //~@@@@R~
    {                                                              //~@@@@I~
//    	System.out.println("nppview orichanged ori="+Porientation+",scrwidth="+getWidth()+",h="+getHeight());//~@@@@R~
        orientation=Porientation;    //getwidth() reflect after invalidate//~@@@@R~
        this.invalidate();                                         //~@@@@M~
    }                                                              //~@@@@I~
//******************                                               //~0914I~
    @Override                                                      //~0914I~
    public boolean onKeyDown(int keyCode,KeyEvent event){          //~0914I~
    	if (Dump.Y) Dump.println("NppView.onKeyDown keycode="+keyCode);//~va42I~
        if (swinitialized)                  //~0914I~              //~0A05R~
            if (wnpView.OnKeyDown(keyCode,event.getRepeatCount(),event.getMetaState()))	//true//~0A05I~
            	return true;                                       //~0A05I~
        return super.onKeyDown(keyCode,event);                     //~0914I~
    }                                                              //~0914I~
//******************                                               //~0914I~
    @Override                                                      //~0914I~
    public boolean onKeyUp(int keyCode,KeyEvent event){            //~0914I~
    	if (Dump.Y) Dump.println("NppView.onKeyUp keycode="+keyCode);//~va42I~
        if (swinitialized)                                         //~@@@@I~
            if (wnpView.OnKeyUp(keyCode,event.getRepeatCount(),event.getMetaState()))	//true//~@@@@I~
            	return true;                                       //~@@@@I~
        return super.onKeyUp(keyCode,event);                       //~0914I~
    }                                                              //~0914I~
//******************                                               //~0914I~
    @Override                                                      //~0914I~
    public boolean onTouchEvent(MotionEvent event){                //~0914I~
    int flag,action;                                               //~@@@@I~
    //********************                                         //~@@@@I~
        if (swinitialized)                                         //~@@@@I~
        {                                                          //~@@@@I~
            long tsEvent=event.getEventTime();   //milliSec        //+va6aI~
            point.x=(int)event.getX();                             //~@@@@I~
            point.y=(int)event.getY();                             //~@@@@I~
            action=event.getAction();                              //~@@@@R~
            if (action!=MotionEvent.ACTION_OUTSIDE)                //~@@@@I~
            {                                                      //~@@@@I~
            	if (action==MotionEvent.ACTION_DOWN)              //~@@@@R~
                {                                                  //+va6aI~
                    flag=1;                                        //~@@@@I~
                    tsDown=tsEvent;                                //+va6aI~
                }                                                  //+va6aI~
                else                                               //~@@@@I~
            	if (action==MotionEvent.ACTION_UP)                //~@@@@I~
                {                                                  //+va6aI~
                    flag=0;                                        //~@@@@I~
                    long tsElapsed=tsEvent-tsDown;                 //+va6aI~
                    if (tsElapsed>TH_LONGPRESS)                    //+va6aI~
                    	chkLongPress(point);                       //+va6aI~
                }                                                  //+va6aI~
                else                                               //~@@@@I~
                    flag=-1;                                       //~@@@@I~
                if (flag>=0)                                       //~@@@@I~
        			if (wnpView.OnLButtonDown(flag,point))         //~@@@@R~
	        			return true;                                               //~0914I~//~@@@@R~
            }                                                      //~@@@@I~
        }                                                          //~@@@@I~
        return false;                                              //~@@@@I~
    }                                                              //~0914I~
////******************                                               //~0914I~//~@@@@R~
//    @Override                                                      //~0914I~//~@@@@R~
//    public boolean onTrackballEvent(MotionEvent event){            //~0914I~//~@@@@R~
//        ballX=(int)event.getX()*100;                               //~0914I~//~@@@@R~
//        ballY=(int)event.getY()*100;                               //~0914I~//~@@@@R~
//        ballAction=event.getAction();                              //~0914I~//~@@@@R~
//        return true;                                               //~0914I~//~@@@@R~
//    }                                                              //~0914I~//~@@@@R~
    private void chkLongPress(Point Ppoint)                        //+va6aI~
    {                                                              //+va6aI~
        wnpView.chkLongPress(Ppoint);                              //+va6aI~
    }                                                              //+va6aI~
}//NppView                                                         //~0914R~
