//*CID://+va80R~:                             update#=   25;       //+va80R~
//**********************************************************************
//va80 240219 selectable BGM                                       //+va80I~
//2022/11/03 vau4 Ahsv-1an0 Permission may not rescheduled if pending entry>=2//~vau4I~
//2022/11/01 vau2 Ahsv-1ams 2022/11/01 control request permission to avoid 1amh:"null permission result".(W Activity: Can request only one set of permissions at a time)//~vau2I~
//**********************************************************************
//control request permission(issue one set at a time)
//**********************************************************************
//package com.btmtest.utils;                                       //+va80R~
package np.jnp.npanew.utils;                                       //+va80R~

import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;                          //~v107R~//~vau4I~
import androidx.core.content.ContextCompat;                        //+va80R~
import android.os.Build;                                           //~vab0R~//~v101I~//+va80R~
//import static com.btmtest.StaticVars.AG;                         //+va80R~
import android.Manifest;                                           //+va80R~

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
                                                                   //+va80R~
import np.jnp.npanew.R;                                            //+va80R~

public class UPermission
{
	private final Map<Integer,RequestData> requestMap= Collections.synchronizedMap(new HashMap<>());
	private boolean swReschedule;                                  //~vau4I~
    private static boolean swRequestedExternalWrite,swRequestedExternalRead;//~1ak2I~//~vau4I~
//*************************
	public UPermission()
    {
    	AG.aUPermission=this;
    }
//*************************
	private static UPermission getInstance()
    {
    	UPermission up=AG.aUPermission;
        if (up==null)
        	up=new UPermission();
        return up;
    }
    public static void requestPermissionExternalStorage(int PrequestID)//+va80R~
    {                                                              //+va80R~
        if (Dump.Y) Dump.println("UPermission.requestPermissionExternalStorage requestid="+PrequestID);//+va80R~
        String type=Manifest.permission.WRITE_EXTERNAL_STORAGE;    //WRITE means also READ//+va80R~
	    requestPermission(type,PrequestID);                        //+va80R~
    	swRequestedExternalWrite=true;                             //+va80R~
    }                                                              //+va80R~
    //******************************************************************************//+va80R~
    public static void requestPermissionExternalStorageRead(int PrequestID)//+va80R~
    {                                                              //+va80R~
        if (Dump.Y) Dump.println("UPermission.requestPermissionExternalStorageRead requestid="+PrequestID);//+va80R~
        String type=Manifest.permission.READ_EXTERNAL_STORAGE;//~1ak2I~//~vau4R~
	    requestPermission(type,PrequestID);                        //+va80R~
    	swRequestedExternalRead=true;                              //+va80R~
    }                                                              //+va80R~
//*************************                                        //+va80R~
    public static void requestPermission(String Ptype,int PrequestID)//+va80R~
    {
        if (Dump.Y) Dump.println("UPermission.requestPermissions single type="+Ptype+",requestID="+PrequestID);
        String[] types=new String[]{Ptype};
        requestPermissions(types,PrequestID);
    }
//*************************
    public static void requestPermissions(String[] Ptypes,int PrequestID)
    {
        if (Dump.Y) Dump.println("UPermission.requestPermissions types="+ Utils.toString(Ptypes)+",requestID="+PrequestID);
        UPermission up=getInstance();
        up.requestPermissionInstance(Ptypes,PrequestID);
    }
//*************************
    public void requestPermissionInstance(String[] Ptypes,int PrequestID)
    {
        if (Dump.Y) Dump.println("UPermission.requestPermissionInstance types="+ Utils.toString(Ptypes)+",requestID="+PrequestID);
        RequestData data=new RequestData(PrequestID,Ptypes);
        requestPermissions(PrequestID,data);
    }
//*************************
    private void requestPermissions(int Pid,RequestData Pdata)
    {
        if (Dump.Y) Dump.println("UPermission.requestPermission id="+Pid+",data="+Pdata);
        if (!isPendingRequest(Pid,Pdata))
	        requestPermissionActivity(Pdata.types,Pid);
    }
//*************************
    private void requestPermissionActivity(String[] Ptypes,int Pid)
    {
        if (Dump.Y) Dump.println("UPermission.requestPermissionActivity id="+Pid+",types="+Utils.toString(Ptypes));
        ActivityCompat.requestPermissions(AG.activity,Ptypes,Pid);
    }
//*************************
//*rc:false:not pending, issue request
//*************************
    private boolean isPendingRequest(int Pid,RequestData Pdata)
    {
        if (Dump.Y) Dump.println("UPermission.isPendingRequest id="+Pid);
        boolean rc;
        Integer id=Integer.valueOf(Pid);
        if (requestMap.get(id)!=null)
        {
	        if (Dump.Y) Dump.println("UPermission.isPendingRequest @@@@ rc=true id="+Pid+" is already pending");//~vau4R~
            return true;
        }
        if (requestMap.size()==0)
        {
	        if (Dump.Y) Dump.println("UPermission.isPendingRequest rc=false by map is empty");
            rc=false;
        }
        else
        if (swReschedule)                                          //~vau4I~
        {                                                          //~vau4I~
	        if (Dump.Y) Dump.println("UPermission.isPendingRequest rc=false by NOT empty but reschedule");//~vau4I~
            rc=false;                                              //~vau4I~
        }                                                          //~vau4I~
        else                                                       //~vau4I~
        {
	        if (Dump.Y) Dump.println("UPermission.isPendingRequest @@@@ rc=true by map is NOT empty");
            rc=true;
        }
        requestMap.put(id,Pdata);
	    if (Dump.Y) Dump.println("UPermission.isPendingRequest rc="+rc);
        return rc;
    }
//*************************
    public static boolean onRequestPermissionResult(int Pid,String[] Ptypes,int[] Presults)//~vau2R~
    {
        if (Dump.Y) Dump.println("UPermission.onRequestPermissionResult id="+Pid+",result="+Utils.toString(Presults)+",type="+Utils.toString(Ptypes));//~vau2R~
        UPermission up=getInstance();
	    boolean rc=up.schedulePendingRequest(Pid);
        if (Dump.Y) Dump.println("UPermission.onRequestPermissionResult rc="+rc+",id="+Pid);
        return rc;
    }
//*************************
    public boolean schedulePendingRequest(int Pid)
    {
    	boolean rc=false;
        if (Dump.Y) Dump.println("UPermission.schedulePendingRequest id="+Pid);
        if (requestMap.get(Pid)!=null)
        {
	        requestMap.remove(Pid);
	        if (Dump.Y) Dump.println("UPermission.schedulePendingRequest remove after size="+requestMap.size());
        }
	    if (requestMap.size()!=0)
        {
        	for (Integer id:requestMap.keySet())
            {
            	if (id!=null)
                {
            		int intID=id.intValue();
		        	RequestData data=requestMap.get(intID);
		        	requestMap.remove(intID);
			        if (Dump.Y) Dump.println("UPermission.schedulePendingRequest @@@@ scheduled pending id="+intID);
                    swReschedule=true;                             //~vau4I~
				    requestPermissions(intID,data);
                    swReschedule=false;                            //~vau4I~
                	break;
                }
            }
        }
        if (Dump.Y) Dump.println("UPermission.schedulePendingRequest rc="+rc+",id="+Pid);
        return rc;
    }
    //******************************************************************************//~1ak2I~//~vau4I~
    public static boolean isPermissionGrantedExternalStorageRead() //~1ak2I~//~vau4I~
    {                                                              //~1ak2I~//~vau4I~
        String type= Manifest.permission.READ_EXTERNAL_STORAGE;    //~1ak2I~//~vau4I~
        boolean rc=isPermissionGranted(type);                      //~1ak2I~//~vau4I~
        if (Dump.Y) Dump.println("UPermission.isPermissionGrantedExternalStorageRead rc="+rc);//~1ak2I~//~vau4I~
        return rc;                                                 //~1ak2I~//~vau4I~
    }                                                              //~1ak2I~//~vau4I~
    //******************************************************************************//~9930I~//~vau4I~
    public static boolean isPermissionGranted(int Presult)         //~9930I~//~vau4I~
    {                                                              //~9930I~//~vau4I~
        boolean rc=Presult==PackageManager.PERMISSION_GRANTED;       //~9930I~//~vau4I~
        if (Dump.Y) Dump.println("UPermission.isPermissionGranted Presult="+Presult+",rc="+rc);//~9930I~//~vau4I~
        return rc;                                                 //~9930I~//~vau4I~
    }                                                              //~9930I~//~vau4I~
    //******************************************************************************//~9930I~//~vau4I~
    public static boolean isPermissionGranted(String Ptype)        //~9930I~//~vau4I~
    {                                                              //~9930I~//~vau4I~
	    if (Dump.Y) Dump.println("Uview.isPermissionGranted Build.VERSION.SDK_INIT="+Build.VERSION.SDK_INT);//~9A01I~//~vau4I~
        //PackageManager.PERMISSION_GRANTED=0; PERMISSION_DENIED=-1//~vae0R~//~vau4I~
        boolean rc= ContextCompat.checkSelfPermission(AG.activity,Ptype)== PackageManager.PERMISSION_GRANTED;//~vae0R~//~vau4I~
        if (Dump.Y) Dump.println("UPermission.isPermissionGranted type="+Ptype+",rc="+rc);//~9930I~//~vau4I~
        if (!rc)                                                   //~1ak2I~//~vau4I~
        {                                                          //~1ak2I~//~vau4I~
            if (Dump.Y) Dump.println("UPermission.isPermissionGranted shouldShowRequestPermissionRationale="+ActivityCompat.shouldShowRequestPermissionRationale(AG.activity,Ptype));//~1ak2I~//~vau4I~
        }                                                          //~1ak2I~//~vau4I~
        return rc;                                                 //~9930I~//~vau4I~
    }                                                              //~9930I~//~vau4I~
//*************************************************************************//~v@@@I~//~va80I~//~vau4M~
//* from Main.onRequestPermissionResult                            //~v@@@I~//~va80I~//~vau4M~
//* for WRITE permission                                           //~vae0I~//~va80I~//~vau4M~
//*************************************************************************//~v@@@I~//~va80I~//~vau4M~
    public static void grantedExternalStorage(boolean PswGranted)  //~v@@@I~//~va80I~//~vau4M~
    {                                                              //~v@@@I~//~va80I~//~vau4M~
    	boolean rc;                                                //~v@@@I~//~va80I~//~vau4M~
        if (Dump.Y) Dump.println("UFile.grantedExternalStorage PswGranted="+PswGranted);//~v@@@I~//~va80I~//~vau4M~
        if (!PswGranted)                                           //~v@@@I~//~va80I~//~vau4M~
        {                                                          //~v@@@I~//~va80I~//~vau4M~
//          	MainView.drawMsg(R.string.ExternalStorageForSDRequiresGranted);//~v@@@I~//~va80R~//~vau4M~
           	Utils.showToastLong(R.string.ExternalStorageForSDRequiresGranted);//~va80I~//~vau4R~
            return;                                                //~v@@@I~//~va80I~//~vau4M~
        }	                                                       //~v@@@I~//~va80I~//~vau4M~
		Utils.showToast(R.string.ExternalStorageForSDGranted);     //~v@@@I~//~va80I~//~vau4R~
        AG.swGrantedExternalStorageRead=true;                      //~va80I~//~vau4M~
//      AG.swSDAvailable=true;                                     //~vae0I~//~va80I~//~vau4M~
    }                                                              //~v@@@I~//~va80I~//~vau4M~
//*************************************************************************//~vae0I~//~va80I~//~vau4M~
//* from Main.onRequestPermissionResult                            //~vae0I~//~va80I~//~vau4M~
//* for READ permission                                            //~vae0I~//~va80I~//~vau4M~
//*************************************************************************//~vae0I~//~va80I~//~vau4M~
    public static void grantedExternalStorageRead(boolean PswGranted)//~1ak2I~//~va80I~//~vau4M~
    {                                                              //~1ak2I~//~va80I~//~vau4M~
    	boolean rc;                                                //~1ak2I~//~va80I~//~vau4M~
        if (Dump.Y) Dump.println("UFile.grantedExternalStorageRead PswGranted="+PswGranted);//~1ak2I~//~va80I~//~vau4M~
        if (!PswGranted)                                           //~1ak2I~//~va80I~//~vau4M~
        {                                                          //~1ak2I~//~va80I~//~vau4M~
          	Utils.showToastLong(R.string.ExternalStorageReadRequiresGranted);//~vae0I~//~va80R~//~vau4M~
            return;                                                //~1ak2I~//~va80I~//~vau4M~
        }                                                          //~1ak2I~//~va80I~//~vau4M~
    	Utils.showToast(R.string.ExternalStorageReadGranted);      //~vae0I~//~va80I~//~vau4R~
        AG.swGrantedExternalStorageRead=true;                      //~vae0I~//~va80I~//~vau4M~
    }                                                              //~1ak2I~//~va80I~//~vau4M~
//****************************************************************************************
//****************************************************************************************
//****************************************************************************************
    class RequestData
    {
	    private int ID;
	    private String[] types;
        //*******************************************
        RequestData(int Pid,String[] Ptypes)
        {
	    	ID=Pid; types=Ptypes;
    		if (Dump.Y) Dump.println("UPermission.RequestData.constructor id="+Pid+",types="+Utils.toString(Ptypes));
        }
    }
}//class UPermission
