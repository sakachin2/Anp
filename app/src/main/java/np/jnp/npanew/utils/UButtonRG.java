//*CID://+va80R~:                             update#=   60;       //~vaKQR~//+va80R~
//*************************************************************************//~v106I~
//va80 240219 selectable BGM                                       //+va80I~
//2022/03/19 vakQ notify update of rule when client received       //~vaKQI~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//*************************************************************************//~v106I~
//RadioButtonGroup:make group RadioButton at anyplace              //~1AfcI~
//package com.btmtest.gui;                                           //~9211R~//~vaKQR~
package np.jnp.npanew.utils;                                       //~vaKQI~

import android.view.ViewGroup;
import android.widget.CompoundButton;                              //~1AfcM~
import android.widget.CompoundButton.OnCheckedChangeListener;      //~1AfcM~
import android.widget.RadioButton;

//import com.btmtest.R;                                            //~vaKQR~
import np.jnp.npanew.R;                                            //~vaKQI~
//import com.btmtest.utils.Dump;                                     //~9211R~//~vaKQR~
//import com.btmtest.utils.UView;                                  //~vaKQR~

//********************************************************************                                                  //~1114I~//~9214M~
//*user controled radio group                                      //~9214I~
//********************************************************************//~9214M~
public class UButtonRG                                              //~1AfcR~//~9211R~
{                                                                  //~1217R~
    private OnCheckedChangeListener rbListener;                    //~1AfcM~
    private int[] btnIDs,userIDs;                                  //~1AfcI~
    private RadioButton[] buttons;                                 //~1AfcI~
    private int btnctr=0,maxEntry;                                 //~1AfcI~
    private ViewGroup layoutView;                                  //~1AfcI~
    private int defaultIndex=0,currentIndex=0;                     //~1AfcR~
    private int currentButtonID;                                   //~9306I~
    private boolean swFixed;                                       //~9306I~
//**********************************************************       //~9306I~
    public UButtonRG(ViewGroup Playout,int Pmaxentry)               //~1AfcR~//~9211R~
    {                                                            //~1126R~//~1217R~
    	layoutView=Playout;                                        //~1AfcI~
    	maxEntry=Pmaxentry;                                        //~1AfcI~
    	btnIDs=new int[Pmaxentry];                                 //~1AfcI~
    	userIDs=new int[Pmaxentry];                                //~1AfcI~
    	buttons=new RadioButton[Pmaxentry];                        //~1AfcI~
    	rbListener=new OnCheckedChangeListener()                   //~1AfcM~
        	{                                                      //~1AfcM~
				@Override                                          //~1AfcM~
            	public void onCheckedChanged(CompoundButton Pbtn,boolean Pchecked)//~1AfcM~
                {                                                  //~1AfcM~
                	try                                            //~9228I~
                    {                                              //~9228I~
                        RadioButton rb=(RadioButton)Pbtn;              //~1AfcM~//~9228R~
                        int id=rb.getId();                             //~1AfcM~//~9228R~
                        if (Dump.Y) Dump.println("UButtonRG:onCheckChanged Pchecked="+Pchecked+",swFixed="+swFixed+",layoutid="+Integer.toHexString(id));//~1AfcI~//~9211R~//~9228R~//~9306R~
                        if (swFixed)                               //~9306I~
                        {                                          //~9306I~
							if (Pchecked && id!=currentButtonID)   //~9306I~
                            {                                      //~9306I~
								rb.setChecked(false);              //~9306I~
	                        	Utils.showToast(R.string.Err_StatusFixed);//~9306I~//+va80R~
                            }                                      //~9306I~
                            else                                   //~9306I~
							if (!Pchecked && id==currentButtonID)  //~9306I~
                            {                                      //~9306I~
								rb.setChecked(true);                //~9306I~
	                        	Utils.showToast(R.string.Err_StatusFixed);//~9306I~//+va80R~
                            }                                      //~9306I~
                        }                                          //~9306I~
                        else                                       //~9306I~
                        {                                          //~9902I~
                        	if (Pchecked)                                  //~1AfcI~//~9228R~//~9902R~
                            	resetChecked(id);                          //~1AfcR~//~9228R~//~9902R~
                            CommonListener.onCheckedChanged(Pbtn,Pchecked);//~9902I~
                        }                                          //~9902I~
                    }                                              //~9228I~
                    catch(Exception e)                             //~9228I~
                    {                                              //~9228I~
                        Dump.println(e,"UButtonRG.onCheckedChanged");//~9228I~
                    }                                              //~9228I~
                }                                                  //~1AfcM~
        	};                                                     //~1AfcM~
        if (Dump.Y) Dump.println("UButtonRG:constructor maxentry="+Pmaxentry);//~1AfcI~//~9211R~
    }                                                              //~1217I~
	//***************************************************************************//~1AfcI~
    public int add(int Puserid,int Playoutid)                      //~1AfcI~
    {                                                              //~1AfcI~
    	RadioButton btn;                                           //~1AfcI~
    	if (btnctr>=maxEntry)                                      //~1AfcI~
        	return -1;                                             //~1AfcI~
        userIDs[btnctr]=Puserid;                                   //~1AfcI~
        btnIDs[btnctr]=Playoutid;                                  //~1AfcI~
    	btn=(RadioButton)layoutView.findViewById(Playoutid);       //~1AfcI~
        if (btn==null)                                             //~1AfcI~
        	return -2;                                             //~1AfcI~
        buttons[btnctr]=btn;                                       //~1AfcI~
        btn.setOnCheckedChangeListener(rbListener);                //~1AfcI~
        if (Dump.Y) Dump.println("UButtonRG:add btnctr="+btnctr+",Puserid="+Puserid+",layoutid="+Integer.toHexString(Playoutid));//~1AfcI~//~9211R~
        btnctr++;                                                  //~1AfcI~
        return btnctr-1;                                           //~1AfcI~
    }                                                              //~1AfcI~
	//***************************************************************************//~1AfcI~
    public int setDefault(int Puserid)                             //~1AfcI~
    {                                                              //~1AfcI~
    	int rc=-1;                                                  //~1AfcI~
    	defaultIndex=0;                                            //~1AfcI~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
            if (Puserid==userIDs[ii])                              //~1AfcI~
            {                                                      //~1AfcI~
		    	defaultIndex=ii;                                   //~1AfcI~
                rc=ii;                                             //~1AfcI~
                break;                                             //~1AfcI~
            }                                                      //~1AfcI~
        }                                                          //~1AfcI~
        currentIndex=defaultIndex;                                  //~1AfcI~
        if (Dump.Y) Dump.println("UButtonRG:setDefault Puserid="+Puserid+",defaultIndex="+defaultIndex+",rc="+rc);//~1AfcI~//~9211R~
        return rc;                                                 //~1AfcI~
    }//setDefault                                                  //~1AfcI~
	//***************************************************************************//~9303I~
    public void setDefaultChk(int Puserid)                         //~9303I~
    {                                                              //~9303I~
	    setDefault(Puserid);                                       //~9303I~
        buttons[currentIndex].setChecked(true);                    //~9303I~
        if (Dump.Y) Dump.println("UButtonRG:setDefaultChk Puserid="+Puserid+",currentIndex="+currentIndex);//~9303I~
    }//setDefaultChk                                               //~9303I~
	//***************************************************************************//~1AfcM~
	public void setChecked(int Puserid)                           //~1AfcR~
    {                                                              //~1AfcM~
    	RadioButton btn;                                           //~1AfcI~
        int idx=defaultIndex;                                      //~1AfcI~
    //******************                                           //~1AfcI~
        if (Dump.Y) Dump.println("UButtonRG:setChecked Puserid="+Puserid+",idx="+idx);//~1AfcI~//~9211R~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
            if (Puserid==userIDs[ii])                              //~1AfcI~
                idx=ii;                                            //~1AfcI~
        }                                                          //~1AfcM~
        btn=buttons[idx];                                          //~1AfcI~
		btn.setChecked(true);  //listener reset other btn          //~1AfcI~
        currentIndex=idx;                                          //~1AfcI~
        currentButtonID=btn.getId();                               //~9306I~
    }                                                              //~9704R~
	//***************************************************************************//~9306I~
	public void setChecked(int Puserid,boolean PswFixed)           //~9306I~
    {                                                              //~9306I~
        if (Dump.Y) Dump.println("UButtonRG:setChecked Puserid="+Puserid+",swFixed="+PswFixed);//~9306I~
		setChecked(Puserid);                                       //~9306I~
    	swFixed=PswFixed;                                          //~9306M~
    }                                                              //~9704R~
	//***************************************************************************//~9704I~
	public void setFixed(boolean PswFixed)                         //~9704I~
    {                                                              //~9704I~
        if (Dump.Y) Dump.println("UButtonRG:setFixed swFixed="+PswFixed);//~9704I~
    	swFixed=PswFixed;                                          //~9704I~
    }                                                              //~9704I~
	//***************************************************************************//~9704I~
	public boolean getFixed()                                      //~9704R~
    {                                                              //~9704I~
        if (Dump.Y) Dump.println("UButtonRG:getFixed swFixed="+swFixed);//~9704I~
    	return swFixed;                                            //~9704I~
    }                                                              //~9704I~
	//***************************************************************************//~1AfcI~
	private void resetChecked(int Playoutid)                       //~1AfcR~
    {                                                              //~1AfcI~
    	RadioButton btn;                                           //~1AfcI~
    //******************                                           //~1AfcI~
		if (Dump.Y) Dump.println("UButtonRG:resetChecked Playoutid="+Integer.toHexString(Playoutid));//~1AfcR~//~9211R~
    	for (int ii=0;ii<btnctr;ii++)                              //~1AfcR~
        {                                                          //~1AfcI~
        	if (Playoutid==btnIDs[ii])                             //~1AfcI~
            	currentIndex=ii;                                   //~1AfcI~
            else                                                   //~1AfcI~
            {                                                      //~1AfcI~
				buttons[ii].setChecked(false);                     //~1AfcR~
		        if (Dump.Y) Dump.println("UButtonRG:setChecked(false) idx="+ii);//~1AfcR~//~9211R~
            }                                                      //~1AfcI~
        }                                                          //~1AfcI~
    }//setChecked                                                  //~1AfcI~
	//***************************************************************************//~1AfcM~
    public int getChecked()                                        //~1AfcR~
    {                                                              //~1AfcM~
    	int userid=userIDs[currentIndex];                               //~1AfcR~
        if (Dump.Y) Dump.println("UButtonRG:getChecked userid="+userid+",currentIndex="+currentIndex);//~1AfcR~//~9211R~
        return userid;                                             //~1AfcI~
	}//getCheckedRadioButtonId                                     //~1AfcI~
	//***************************************************************************//~9702I~
    public boolean setEnabled(int Ppos,boolean PswEnable)          //~9702I~
    {                                                              //~9702I~
    	boolean rc=false;                                          //~9702I~
    	if (Ppos>=0 && Ppos<btnctr)                                //~9702I~
        {                                                          //~9702I~
        	rc=true;                                               //~9702I~
    		buttons[Ppos].setEnabled(PswEnable);                   //~9702I~
        }                                                          //~9702I~
		if (Dump.Y) Dump.println("UButtonRG:setEnabled pos="+Ppos+",enable="+PswEnable+",rc="+rc);//~9702I~
        return rc;                                                 //~9702I~
	}//setEnabled                                                  //~9702I~
	//***************************************************************************//~vaKQI~
    public boolean setBGUpdated(int PcolorBG,int PposOld)          //~vaKQR~
    {                                                              //~vaKQI~
        boolean rc=false;                                          //~vaKQI~
        if (PposOld!=currentIndex)                                 //~vaKQI~
        {                                                          //~vaKQI~
        	if (currentIndex<buttons.length)                       //~vaKQR~
				buttons[currentIndex].setBackgroundColor(PcolorBG);//~vaKQR~
        	if (PposOld<buttons.length)                            //~vaKQR~
				buttons[PposOld].setBackgroundColor(PcolorBG);     //~vaKQR~
            rc=true;                                               //~vaKQI~
        }                                                          //~vaKQI~
		if (Dump.Y) Dump.println("UButtonRG:setBGUpdated rc="+rc+",posOld="+PposOld+",currentIndex="+currentIndex+",color="+Integer.toHexString(PcolorBG));//~vaKQI~
        return rc;                                                 //~vaKQI~
	}                                                              //~vaKQI~
}//class                                                           //~1121R~
