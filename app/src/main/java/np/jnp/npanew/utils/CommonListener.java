//*CID://+DATER~:                             update#=   45;       //~v1EjI~//~9B10R~
//**************************************************************************//~v1EjI~
//**************************************************************************//~v1EjI~
//package com.btmtest.gui;                                           //~1215R~//~v1EjI~//~4219R~
package np.jnp.npanew.utils;                                       //~4219I~
                                                                   //~v1EjI~
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;

//import com.btmtest.R;                                            //~4219R~
import np.jnp.npanew.R;                                            //~4219I~
//import com.btmtest.utils.Dump;                                   //~4219R~
//import com.btmtest.utils.UView;                                  //~4219R~
//import com.btmtest.utils.Utils;                                  //~4219R~

//import static com.btmtest.StaticVars.AG;                           //~v1EjI~//+4219R~
//*******************************                                  //~v1EjI~
public class CommonListener                                        //~v1EjI~
{                                                                  //~v1EjI~
	public static final int CL_UCHECKBOX=1;         //             //~v1EjR~
	public static final int CL_URADIOGROUP=2;       //             //~v1EjR~
	public static final int CL_UBUTTONRG=3;                        //~v1EjR~
	public static final int CL_USPINNER=4;          //             //~v1EjR~
	public static final int CL_USPINBTN=5;
	public static final int CL_UEDITTEXT=6;//            //~v1EjI~
    //**************************************************************************//~v1EjR~
    public interface CommonListenerI                                      //~v@@@I~//~v1EjR~
    {                                                              //~v@@@I~//~v1EjR~
        public void onWidgetChanged(int PwidgetType,int PviewID,int Pvalue,String PStrValue);//~v1EjR~
    }                                                              //~v@@@I~//~v1EjR~
    //**************************************************************************//~v1EjR~
    public static CommonListenerI setListener(CommonListenerI Plistener)      //~v1EjR~//~9B10R~
    {                                                              //~v1EjR~
        CommonListenerI old=AG.aCommonListenerI;                   //~9B10I~
        AG.aCommonListenerI=Plistener;                             //~v1EjR~
        if (Dump.Y) Dump.println("CommonListener.setListener new="+ Utils.toString(Plistener)+",old="+Utils.toString(old));//~v1EjR~//~9B10R~
        return old;                                                //~9B10I~
    }                                                              //~v1EjR~
    //**************************************************************************//~v1EjR~
    public static CommonListenerI resetListener()                  //~v1EjR~
    {                                                              //~v1EjR~
        CommonListenerI old=AG.aCommonListenerI;                     //~v1EjR~//~9B10R~
        AG.aCommonListenerI=null;                                  //~9B10I~
        if (Dump.Y) Dump.println("CommonListener.resetListener old="+Utils.toString(old));//~v1EjR~//~9B10R~
        return old;                                                  //~v1EjR~//~9B10R~
    }                                                              //~v1EjR~
    //**************************************************************************//~v1EjR~
    public static void onChangedCB(CompoundButton Pbtn, boolean PisChecked)//~v1EjR~
    {                                                              //~v1EjR~
        if (Dump.Y) Dump.println("CommonListener.onChangedCB BtnID="+Integer.toHexString(Pbtn.getId())+",isChecked="+PisChecked);//~v1EjR~
        if (AG.aCommonListenerI!=null)                             //~v1EjR~
	        AG.aCommonListenerI.onWidgetChanged(CL_UCHECKBOX,Pbtn.getId(),PisChecked ? 1 : 0,null);//~v1EjR~
    }                                                              //~v1EjR~
    //**************************************************************************//~v1EjI~
    public static void onItemSelectedUS(int PviewID,int Ppos)                 //~v1EjR~//~9228R~//~v1EjR~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onItemSelectedUS viewID="+Integer.toHexString(PviewID)+",pos="+Ppos);//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_USPINNER,PviewID,Ppos,null);//~v1EjR~
    }                                                              //~v1EjI~
    //**************************************************************************//~v1EjI~
    public static void onNothingSelectedUS()                       //~v1EjR~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onNothingSelectdUS");//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_USPINNER,-1,-1,null);//~v1EjR~
    }                                                              //~v1EjI~
    //**************************************************************************//~v1EjI~
    public static void onChangedURG(int Prbid)                         //~v1EjI~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onChangedURG rbid="+Integer.toString(Prbid));//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_URADIOGROUP,Prbid,-1,null);//~v1EjR~
    }                                                              //~v1EjI~
    //**************************************************************************//~v1EjI~
    //*SpinButton                                                  //~v1EjI~
    //**************************************************************************//~v1EjI~
    public static void onClickButtonUSB(int PviewID)              //~v1EjI~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onClickButtonUSB viewid="+Integer.toString(PviewID));//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_USPINBTN,PviewID,-1,null);//~v1EjR~
    }                                                              //~v1EjI~
    //**************************************************************************//~v1EjI~
    //*UButtonRG                                                   //~v1EjI~
    //**************************************************************************//~v1EjI~
    public static void onCheckedChanged(CompoundButton Pbtn,boolean Pchecked)//~v1EjI~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onCheckedChanged button="+Integer.toHexString(Pbtn.getId()));//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_UBUTTONRG,Pbtn.getId(),Pchecked ? 1 :0,null);//~v1EjR~
    }                                                              //~v1EjI~
    //**************************************************************************//~v1EjI~
    //*UEditText                                                   //~v1EjI~
    //**************************************************************************//~v1EjI~
    public static void onTextChanged(EditText Pet, String Ptext)    //~v1EjI~
    {                                                              //~v1EjI~
        if (Dump.Y) Dump.println("CommonListener.onTextChanged EditText="+Integer.toHexString(Pet.getId()));//~v1EjI~
        if (AG.aCommonListenerI!=null)                             //~v1EjI~
	        AG.aCommonListenerI.onWidgetChanged(CL_UEDITTEXT,Pet.getId(),-1,Ptext);//~v1EjI~
    }                                                              //~v1EjI~
}                                                                  //~v1EjI~
