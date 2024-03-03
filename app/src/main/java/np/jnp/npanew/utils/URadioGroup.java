//*CID://+va80R~:                             update#=   72;       //+va80R~
//*************************************************************************//~v106I~
//va80 240219 selectable BGM                                       //+va80I~
//2022/08/06 vapx add Psuedo-Tennpai:No option                     //~vapxI~
//2022/03/19 vakQ notify update of rule when client received       //~vakQI~
//1Afc 2016/09/22 like V1C1 fuego, add ray as player               //~1AfcI~
//*************************************************************************//~v106I~
//RadioButtonGroup:make group RadioButton at anyplace              //~1AfcI~
//package com.btmtest.gui;                                           //~9211R~//+va80R~
package np.jnp.npanew.utils;                                       //+va80I~

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RadioGroup;

//import com.btmtest.R;                                            //+va80R~
import np.jnp.npanew.R;                                            //+va80I~
//import com.btmtest.utils.Dump;                                     //~9211R~//+va80R~
//import com.btmtest.utils.UView;                                  //+va80R~

//***********************************************************************//~9214I~
//*android controlled RadioGroup                                   //~9214I~
//***********************************************************************//~9214I~
public class URadioGroup                                              //~1AfcR~//~9211R~//~9214R~
{                                                                  //~1217R~
    private OnCheckedChangeListener rbListener;                    //~1AfcM~
//  private RadioGroup radioGroup;                                  //~9214I~//~vakQR~
    public  RadioGroup radioGroup;                                 //~vakQI~
    private URadioGroupI listener;                                 //~9214R~
    private int URGParm;                                           //~9214R~
    private boolean swFixed;                                       //~9226I~
    private int idFixed;                                           //~9226I~
    private int[] rbIDs;                                           //~9306I~
    private View rgLayout;                                         //~9531I~
//*******************************                                  //~9214I~
    public interface URadioGroupI                                  //~9214R~
    {                                                              //~9214I~
        void onChangedURG(int PbtnID,int Pparm);//~9214R~
    }                                                              //~9214I~
//********************************************************************//~9214I~
//*android control radio group with listener interface             //~9214I~
//********************************************************************//~9214I~
    public URadioGroup(View Playout, int Pid,int Pparm)            //~9214R~
    {                                                              //~9214I~
    	rgLayout=Playout;                                          //~9531I~
		radioGroup=(RadioGroup)Utils.findViewById(Playout,Pid);    //~9214I~
        URGParm=Pparm;                                             //~9214R~
		setListener(createListener());                             //~9902I~
        if (Dump.Y) Dump.println("URadioGroup.constructor id="+Integer.toHexString(Pid));//~9214R~//~vakQR~
    }                                                              //~9214I~
//********************************************************************//~9306I~
    public URadioGroup(View Playout, int Pid,int Pparm,int[] PIDs) //~9306I~
    {                                                              //~9306I~
    	rgLayout=Playout;                                          //~9531I~
		radioGroup=(RadioGroup)Utils.findViewById(Playout,Pid);    //~9306I~
        URGParm=Pparm;                                             //~9306I~
        rbIDs=PIDs;                                                //~9306I~
		setListener(createListener());                             //~9902I~
        if (Dump.Y) Dump.println("URadioGroup.constructor id="+Integer.toHexString(Pid));//~9306I~//~vakQR~
    }                                                              //~9306I~
	//***************************************************************************//~1AfcM~
	public void setChecked(int Prbid)                           //~1AfcR~//~9214R~
    {                                                              //~1AfcM~
		radioGroup.check(Prbid);                                   //~9214R~
        if (Dump.Y) Dump.println("URadioGroup.setChecked id="+Integer.toHexString(Prbid));//~9214I~//~vakQR~
    }//setChecked                                                  //~1AfcI~
	//***************************************************************************//~9320I~
	public void resetWithoutListener()                             //~9320R~
    {                                                              //~9320I~
    	if (rbListener!=null)                                      //~9320I~
	        radioGroup.setOnCheckedChangeListener(null);           //~9320I~
		radioGroup.check(-1);                                      //~9320I~
    	if (rbListener!=null)                                      //~9320I~
	        radioGroup.setOnCheckedChangeListener(rbListener);     //~9320I~
        if (Dump.Y) Dump.println("URadioGroup.restWithoutListener");//~9320I~//~vakQR~
    }                                                              //~9320I~
	//***************************************************************************//~9226I~
	public void setChecked(int Prbid,boolean PswFixed)             //~9226I~
    {                                                              //~9226I~
        if (Dump.Y) Dump.println("URadioGroup.setChecked swFixed="+PswFixed+",id="+Integer.toHexString(Prbid));//~9226I~//~0302M~//~vakQR~
		radioGroup.check(Prbid);                                   //~9226I~
        swFixed=PswFixed;                                          //~9226I~
        idFixed=Prbid;                                             //~9226I~
        if (swFixed)                                               //~9226I~
        {                                                          //~9226I~
        	if (rbListener==null)                                  //~9226I~
			    setListener(createListener());                     //~9226I~
        }                                                          //~9226I~
    }                                                              //~9704R~
	//***************************************************************************//~9311I~
	public void setFixed(boolean PswFixed)                         //~9311I~
    {                                                              //~9311I~
        swFixed=PswFixed;                                          //~9311I~
        if (Dump.Y) Dump.println("URadioGroup.setFixed swFixed="+PswFixed+",idFixed="+Integer.toHexString(idFixed));//~9311R~//~9A14R~//~vakQR~
    }                                                              //~9704R~
	//***************************************************************************//~9704I~
	public boolean getFixed()                                      //~9704R~
    {                                                              //~9704I~
        if (Dump.Y) Dump.println("URadioGroup.getFixed swFixed="+swFixed);//~9704I~
        return swFixed;                                            //~9704I~
    }                                                              //~9704I~
	//***************************************************************************//~9306I~
	public void setCheckedID(int Pid,boolean PswFixed)             //~9306I~
    {                                                              //~9306I~
        if (Dump.Y) Dump.println("URadioGroup.setCheckedID swFixed="+PswFixed+",id="+Pid);//~9306I~//~0302I~//~vakQR~
    	int rbid;                                                  //~9306I~
    	if (rbIDs!=null && Pid>=0 && Pid<rbIDs.length)             //~9306I~
			rbid=rbIDs[Pid];                                      //~9306I~
        else                                                       //~9306I~
			rbid=-1;                                               //~9306I~
		setChecked(rbid,PswFixed);                                 //~9306I~
    }                                                              //~9704R~
	//***************************************************************************//~9225I~
	public void setEnabled(boolean Penable)                        //~9225I~
    {                                                              //~9225I~
		radioGroup.setEnabled(Penable);                            //~9225I~
        if (Dump.Y) Dump.println("URadioGroup.setEnabled enable="+Penable);//~9225I~//~vakQR~
    }                                                              //~9707R~
	//***************************************************************************//~vapxR~
	public void setEnabledAll(boolean Penable)                     //~vapxR~
    {                                                              //~vapxR~
    	for (int ii=0;ii<radioGroup.getChildCount();ii++)         //~vapxR~
        {                                                          //~vapxR~
			radioGroup.getChildAt(ii).setEnabled(Penable);        //~vapxR~
        }                                                          //~vapxR~
        if (Dump.Y) Dump.println("URadioGroup.setEnabledAll enable="+Penable);//~vapxR~
    }                                                              //~vapxR~
	//***************************************************************************//~9531I~
	public void setEnabledButton(int Prbid,boolean Penable)              //~9531I~
    {                                                              //~9531I~
        if (Dump.Y) Dump.println("URadioGroup.setEnabledButton rbid="+Integer.toHexString(Prbid)+",enable="+Penable);//~9531I~//~9707R~//~vakQR~
		RadioButton rb=(RadioButton)Utils.findViewById(rgLayout,Prbid);//~9531I~
		rb.setEnabled(Penable);                                    //~9531I~
    }                                                              //~9707R~
	//***************************************************************************//~9531I~
	public void setEnabled(int Pid,boolean Penable)                //~9531I~
    {                                                              //~9531I~
        if (Dump.Y) Dump.println("URadioGroup.setEnabled id="+Pid+",enable="+Penable);//~9531I~//~vakQR~
    	int rbid;                                                  //~9531I~
    	if (rbIDs!=null && Pid>=0 && Pid<rbIDs.length)             //~9531I~
        {                                                          //~9531I~
			rbid=rbIDs[Pid];                                       //~9531I~
			setEnabledButton(rbid,Penable);                              //~9531I~
        }                                                          //~9531I~
    }                                                              //~9707R~
	//***************************************************************************//~9705I~
	public void setVisibility(int Pid,int Pvisibility)             //~9705I~
    {                                                              //~9705I~
        if (Dump.Y) Dump.println("URadioGroup.setVisivility id="+Pid+",visibility="+Pvisibility);//~9705I~//~vakQR~
    	int rbid;                                                  //~9705I~
    	if (rbIDs!=null && Pid>=0 && Pid<rbIDs.length)             //~9705I~
        {                                                          //~9705I~
			rbid=rbIDs[Pid];                                       //~9705I~
			Utils.findViewById(rgLayout,rbid).setVisibility(Pvisibility);//~9705I~
        }                                                          //~9705I~
    }                                                              //~9707R~
	//***************************************************************************//~1AfcM~
    public int getChecked()                                        //~1AfcR~
    {                                                              //~1AfcM~
    	int id=radioGroup.getCheckedRadioButtonId();                    //~9214I~
        if (Dump.Y) Dump.println("URadioGroup.getChecked id="+Integer.toHexString(id));//~9214R~//~vakQR~
        return id;                                             //~1AfcI~//~9214R~
	}                                                              //~9214R~
	//***************************************************************************//~9306I~
    public int getCheckedID()                                      //~9306I~
    {                                                              //~9306I~
    	int rc=-1;                                                 //~9306I~
    	int id=radioGroup.getCheckedRadioButtonId();               //~9306I~
        if (rbIDs!=null)                                           //~9306I~
        	rc=searchID(id);                                       //~9306I~
        if (Dump.Y) Dump.println("URadioGroup.getChecked rc="+rc); //~9306I~//~vakQR~
        return rc;                                                 //~9306I~
	}                                                              //~9306I~
	//***************************************************************************//~9306I~
    public int searchID(int Pid)                                       //~9306I~
    {                                                              //~9306I~
    	int rc=-1;                                                 //~9306I~
    	for (int ii=0;ii<rbIDs.length;ii++)                        //~9306I~
        {                                                          //~9306I~
        	if (Pid==rbIDs[ii])                                    //~9306I~
            {                                                      //~9306I~
            	rc=ii;                                             //~9306I~
                break;                                             //~9306I~
            }                                                      //~9306I~
        }                                                          //~9306I~
        if (Dump.Y) Dump.println("URadioGroup.searchID id="+Integer.toHexString(Pid)+",rc="+rc);//~9306I~//~vakQR~
        return rc;                                                 //~9306I~
	}                                                              //~9306I~
	//***************************************************************************//~9214I~
    public OnCheckedChangeListener createListener()                //~9214I~
    {                                                              //~9214I~
        if (Dump.Y) Dump.println("URadioGroup.createListener");    //~9226I~//~vakQR~
    	RadioGroup.OnCheckedChangeListener l=                               //~9214R~
    	new OnCheckedChangeListener()                              //~9214I~
        	{                                                      //~9214I~
				@Override                                          //~9214I~
            	public void onCheckedChanged(RadioGroup Pbtn,int Prbid)//~9214I~
                {                                                  //~9214I~
                	try                                            //~9228I~
                    {                                              //~9228I~
                        if (Dump.Y) Dump.println("URadioGroup.onCheckChanged swFixed="+swFixed+",btnid="+Integer.toHexString(Prbid));//~9214R~//~9226R~//~9228R~//~vakQR~
                        if (swFixed)                                   //~9226I~//~9228R~
                        {                                              //~9226I~//~9228R~
                            if (Prbid!=idFixed)                        //~9226I~//~9228R~
                            {                                          //~9226I~//~9228R~
                                setChecked(idFixed);                   //~9226I~//~9228R~
                                Utils.showToast(R.string.Err_StatusFixed);//~v1EjI~//~9226I~//~9228R~
                            }                                          //~9226I~//~9228R~
                        }                                              //~9226I~//~9228R~
                        else                                           //~9226I~//~9228R~
                        if (listener!=null)                              //~9311I~
                            listener.onChangedURG(Prbid,URGParm);  //~9214R~//~9226R~//~9228R~
                        else                                       //~9902I~
                        	CommonListener.onChangedURG(Prbid);    //~9902I~
                    }                                              //~9228I~
                    catch(Exception e)                             //~9228I~
                    {                                              //~9228I~
                        Dump.println(e,"URadioGroup.onCheckedChanged");//~9228I~
                    }                                              //~9228I~
                }                                                  //~9214I~
        	};                                                     //~9214I~
        if (Dump.Y) Dump.println("URadioGroup.createListener listener="+l.toString());//~9214R~//~vakQR~
        rbListener=l;                                              //~9214I~
        return l;                                                  //~9214I~
    }                                                              //~9214I~
    //**********************************************               //~9214I~
    private void setListener(OnCheckedChangeListener Plistener)//~9214I~
    {                                                              //~9214I~
    	rbListener=Plistener;                                      //~9214I~
        radioGroup.setOnCheckedChangeListener(Plistener);          //~9214I~
    }                                                              //~9214I~
    //**********************************************               //~9214I~
    public void setListener(URadioGroupI PURGlistener)             //~9214R~
    {                                                              //~9214I~
    	listener=PURGlistener;                                     //~9214R~
        if (rbListener==null)                                       //~9214I~
        {                                                          //~9214I~
		    setListener(createListener());                         //~9214I~
        }                                                          //~9214I~
    }                                                              //~9214I~
    //**********************************************               //~9214I~
    public void setListener(OnCheckedChangeListener Plistener,URadioGroupI PURGlistener)//~9214R~
    {                                                              //~9214I~
	    setListener(Plistener);                                    //~9214I~
	    setListener(PURGlistener);                                 //~9214R~
    }                                                              //~9214I~
}//class                                                           //~1121R~
