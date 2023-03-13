//*CID://+va58R~: update#= 112;                                    //~va58R~
//**********************************************************************//~1107I~
//va58 221104 copy Alert from X:Ahsv to avoid fragmentmanager      //~va58I~
//1Ah2 2020/05/31 for Android9(Pie)-api28(PlayStore requires),deprected. DialogFragment,Fragmentmanager//~1Ah2I~
//1A89k2015/03/01 Ajagoc:2015/02/28 confirm session disconnect when unpair//~1A89I~
//**********************************************************************//~1107I~
package np.jnp.npanew.utils;                                       //~va58I~


import java.util.ArrayList;
import np.jnp.npanew.R;                                            //~va58I~

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
//**********************************************************************//~1107I~
public class Alert                                            //~1107R~//~1211R~//~@@@@R~
//  implements URunnableI                                          //~@@@2I~//+va58R~
{                                                                  //~0914I~
	public static final int BUTTON_POSITIVE =0x01;                      //~1211I~//~1212R~
	public static final int BUTTON_NEGATIVE =0x02;                      //~1211I~//~1212R~
	public static final int BUTTON_CLOSE    =0x04;                         //~1211I~//~1212R~
	public static final int SHOW_DIALOG     =0x08;                 //~1212R~
	public static final int ITEM_SELECTED   =0x10;                       //~1211I~//~1212R~
	public static final int MULTIPLE_CHOICE =0x20;                     //~1211I~//~1212R~
	public static final int EXIT            =0x40;                 //~1212I~
	public static final int NO_TITLE        =0x80;                 //~1212I~
	
	private AlertI callback;                                  //~1425R~//~@@@@R~
	private View selectedView;                                     //~1425R~
    private ListView listview;                                     //~1425R~
    private int flag;                                              //~1425R~
    private String title,text;                                     //~@@@2I~
                                                                   //~1211I~
	AlertDialog pdlg;                                              //~1425R~
    int selectedPos=-1;                                            //~1211R~
//******************                                               //~1121I~
//**********************************                               //~1211I~
//*                                                                //~1211R~
//**********************************                               //~1211I~
	public Alert()                                           //~1211I~//~@@@@R~
    {                                                              //~1211I~
    }                                                              //~1211I~
	public Alert(AlertI Pcallback)                       //~1211I~ //~@@@@R~
    {                                                              //~1211I~
    	callback=Pcallback;                                        //~1211I~
    }                                                              //~1211I~
//===============================================================================//~v@@@I~//~1212I~
//simple alertdialog                                               //~v@@@I~//~1212I~
//===============================================================================//~v@@@I~//~1212I~
    public static void simpleAlertDialog(AlertI Pcallback,String Ptitle,String Ptext,int Pflag)//~1212I~//~@@@@R~
    {                                                              //~1212I~
    //***********                                                  //~1212I~
    	Alert ajagoalert=new Alert(Pcallback);           //~1212I~ //~@@@@R~
//      ajagoalert.createAlertDialog(Ptitle,Ptext,Pflag);          //~1212I~//~@@@2R~
		ajagoalert.title=Ptitle;                                   //~@@@2I~
		ajagoalert.text=Ptext;                                     //~@@@2I~
//  	URunnable.setRunFuncDirect(ajagoalert,ajagoalert,Pflag);	//callback is runFunc()//~@@@2I~//+va58R~
		ajagoalert.createAlertDialog(ajagoalert.title,ajagoalert.text,Pflag);//+va58I~
    }                                                              //~1212I~
////===============================================================================//~@@@2I~//+va58R~
////=run on UIThread                                                 //~@@@2I~//+va58R~
////===============================================================================//~@@@2I~//+va58R~
//    public void runFunc(Object Pobj,int Pint)                           //~@@@2I~//+va58R~
//    {                                                              //~@@@2I~//+va58R~
//        Alert ajagoalert=(Alert)Pobj;                            //+va58R~
//        ajagoalert.createAlertDialog(ajagoalert.title,ajagoalert.text,Pint);    //~@@@2I~//+va58R~
//    }                                                              //~@@@2I~//+va58R~
//===============================================================================//~1212I~
    public static void simpleAlertDialog(AlertI Pcallback,String Ptitle,int Ptextid,int Pflag)//~1212I~//~@@@@R~
    {                                                              //~1212I~
    //***********                                                  //~1212I~
    	String text=AG.resource.getString(Ptextid);                //~1212I~
	    simpleAlertDialog(Pcallback,Ptitle,text,Pflag);            //~1212I~
    }                                                              //~1212I~
//===============================================================================//~v1B9I~//~1A89I~
    public static void simpleAlertDialog(AlertI Pcallback,int Ptitleid,int Ptextid,int Pflag)//~v1B9I~//~1A89I~
    {                                                              //~v1B9I~//~1A89I~
    //***********                                                  //~v1B9I~//~1A89I~
    	String title=AG.resource.getString(Ptitleid);              //~v1B9I~//~1A89I~
    	String text=AG.resource.getString(Ptextid);                //~v1B9I~//~1A89I~
	    simpleAlertDialog(Pcallback,title,text,Pflag);             //~v1B9I~//~1A89I~
    }                                                              //~v1B9I~//~1A89I~
//**********************************                               //~1211I~
//*simple msg popup                                                //~1211I~
//**********************************                               //~1211I~
	private void createAlertDialog(String Ptitle,String Ptext,int Pflag)//~1211R~//~1212R~
    {                                                              //~1211I~
		AlertDialog.Builder builder=new AlertDialog.Builder(AG.context);//~1211I~
    	builder.setMessage(Ptext);                                         //~v@@@I~//~1211I~
        setButton(builder,Pflag);                                  //~1212I~
    	pdlg=builder.create();                                                  //~v@@@I~//~1211I~
    	if (Ptitle!=null)                                              //~v@@@I~//~1211I~//~1212M~
    		pdlg.setTitle(Ptitle);                                      //~v@@@I~//~1211I~//~1212I~
        else                                                       //~1212M~
            if ((Pflag & NO_TITLE)!=0)                             //~1212M~
	        	pdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);//~1212I~
            else                                                   //~1212M~
	    		pdlg.setTitle(AG.resource.getString(R.string.app_name));//~1212M~
    	pdlg.show();                                                    //~v@@@I~//~1211I~//~1212R~
    }                                                              //~1211I~
//**********************************                               //~1212I~
	private void setButton(AlertDialog.Builder Pbuilder,int Pflag) //~1212I~
    {                                                              //~1212I~
    	flag=Pflag;                                                //~1212I~
        if ((Pflag & BUTTON_POSITIVE)!=0)                          //~1212I~
        {                                                          //~1212I~
            Pbuilder.setPositiveButton("OK",new DialogInterface.OnClickListener()//~1212I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                                pdlg.dismiss();    //~1212I~
                                                if (callback!=null)//~1212I~
	                                            	callback.alertButtonAction(BUTTON_POSITIVE,selectedPos);//~1212R~
                                                if ((flag & EXIT)!=0)//~1212I~
//                                              	Utils.finish();//~1212I~//~1309R~//~@@@@R~//~@@@2R~
                                                	Utils.stopFinish();//~@@@2I~
                                            }                      //~1212I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
        if ((Pflag & BUTTON_CLOSE)!=0)                             //~1212I~
        {                                                          //~1212I~
            Pbuilder.setPositiveButton("Close",new DialogInterface.OnClickListener()//~1212I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                                pdlg.dismiss();    //~1212I~
                                                if (callback!=null)//~1212I~
		                                            callback.alertButtonAction(BUTTON_CLOSE,selectedPos);//~1212R~
                                            }                      //~1212I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
        if ((Pflag & BUTTON_NEGATIVE)!=0)                          //~1212I~
        {                                                          //~1212I~
            Pbuilder.setNegativeButton("Cancel",new DialogInterface.OnClickListener()//~1212I~
                                        {                          //~1212I~
                                                                   //~1212I~
                                            public void onClick(DialogInterface Pdlg,int buttonID)//~1212I~
                                            {                      //~1212I~
                                                pdlg.dismiss();    //~1212I~
                                                if (callback!=null)//~1212I~
		                                            callback.alertButtonAction(BUTTON_NEGATIVE,selectedPos);//~1212R~
                                            }                      //~1212I~
                                        }                          //~1212I~
                                     );                            //~1212I~
        }                                                          //~1212I~
    }//setButton                                                   //~1212I~
////**********************************                               //~1211I~//+va58R~
////*listview popup                                                  //~1211I~//+va58R~
////**********************************                               //~1211I~//+va58R~
//    public void createAltertDialog(String Ptitle,int Pflag,ArrayList<String> Pstrarray)//~1211I~//+va58R~
//    {                                                              //~1211I~//+va58R~
//        ArrayAdapter<String> adapter;                              //~1211I~//+va58R~
//    //**********************************                           //~1211I~//+va58R~
//        AlertDialog.Builder builder=new AlertDialog.Builder(AG.context);//~0A21I~//~1211I~//+va58R~
//        listview=new ListView(AG.context);                         //~1211R~//+va58R~
//        if ((Pflag & MULTIPLE_CHOICE)!=0)                            //~1211I~//~1212R~//+va58R~
//        {                                                          //~1211I~//+va58R~
//            adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowIdMultipleChoice,Pstrarray);//~1211I~//~1212R~//+va58R~
//        }                                                          //~1211I~//+va58R~
//        else                                                       //~1211I~//+va58R~
//        {                                                          //~1211I~//+va58R~
//            adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowId,Pstrarray);//~1211R~//+va58R~
//        }                                                          //~1211I~//+va58R~
//        listview.setAdapter(adapter);                              //~1211I~//+va58R~
//        if ((Pflag & MULTIPLE_CHOICE)!=0)                           //~1211I~//~1212R~//+va58R~
//        {                                                          //~1211I~//+va58R~
//            listview.setItemsCanFocus(false);   //chkbox focus:disable-->text enable onClick//~1211I~//+va58R~
//            listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);  //~1211I~//+va58R~
//        }                                                          //~1211I~//+va58R~
//        else                                                       //~1211I~//+va58R~
//        {                                                          //~1211I~//+va58R~
//            adapter=new ArrayAdapter<String>(AG.context,AG.listViewRowId,Pstrarray);//~1211I~//+va58R~
//        }                                                          //~1211I~//+va58R~
//        setItemListener(listview);                                 //~1211I~//+va58R~
//        LinearLayout layout=new LinearLayout(AG.context);          //~1211I~//+va58R~
//        layout.setOrientation(LinearLayout.VERTICAL);              //~1211I~//+va58R~
//        layout.addView(listview);                                  //~1211I~//+va58R~
//        builder.setView(layout);                                   //~1211I~//+va58R~
//        setButton(builder,Pflag);                                  //~1212I~//+va58R~
//        pdlg=builder.create();                                     //~1211I~//~1212M~//+va58R~
//        if (Ptitle!=null)                                          //~1211I~//+va58R~
//            pdlg.setTitle(Ptitle);                              //~1211I~//~1212R~//+va58R~
//        else                                                       //~1211I~//+va58R~
//            if ((Pflag & NO_TITLE)!=0)                             //~1212I~//+va58R~
//                pdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);    //~1211I~//~1212R~//+va58R~
//            else                                                   //~1212I~//+va58R~
//                pdlg.setTitle(AG.resource.getString(R.string.app_name));//~1212R~//+va58R~
//        if ((Pflag & SHOW_DIALOG)!=0)                               //~1211I~//~1212R~//+va58R~
//            show();                                                //~1211R~//+va58R~
//    }//createAlertDialog                                           //~1211R~//+va58R~
//**********************************************************************//~1211I~
	public void show()                                             //~1211I~
    {                                                              //~1211I~
		pdlg.show();                                               //~1211I~
    }                                                              //~1211I~
//**********************************************************************//~1211I~
	public ListView getListView()                                  //~1211I~
    {                                                              //~1211I~
		return listview;                                           //~1211I~
    }                                                              //~1211I~
//**********************************************************************//~v@@@I~//~1211I~
//*setup ItemListener class                                              //~v@@@I~//~1211I~
//**********************************************************************//~v@@@I~//~1211I~
	private void setItemListener(ListView Plistview)                   //~1211I~
    {                                                              //~1211I~
    	Plistview.setOnItemClickListener(                              //~1211I~
        	new AdapterView.OnItemClickListener()                              //~1211I~
				{                                                  //~1211I~
                	@Override                                      //~1211I~
					public void onItemClick(AdapterView<?> arg0,View Plv, int Ppos, long arg3)//~1211R~
					{                                              //~1211I~
                    	int rc;                                    //~1211I~
                        if (Dump.Y) Dump.println("submenu onitemclisck itemno="+Ppos);//~1506R~
                        selectedPos=Ppos;                          //~1211I~
                        selectedView=Plv;                          //~1211I~
	                    rc=callback.alertButtonAction(ITEM_SELECTED,selectedPos);//~1211I~//~1212R~
                        if (rc==1)	//close dialog                 //~1211I~
                            pdlg.dismiss();                        //~1211I~
                    }
				}
        	                             );//~1211I~
	}                                                              //~1211I~
//**********************************************************************//~1211I~
//*setup ItemListener class                                        //~1211I~
//**********************************************************************//~1211I~
	public ListView getSelectedView()                             //~1211I~
    {                                                              //~1211I~
    	return (ListView) selectedView;                                       //~1211I~
	}                                                              //~1211I~
//===============================================================================//~1Ah2I~
	public static void  showMessage(String Ptitle,String Ptext)    //~1Ah2I~
    {                                                              //~1Ah2I~
        if (Dump.Y) Dump.println("Alert:showMessage:"+Ptext);      //~1Ah2I~
		int flag=BUTTON_CLOSE;                                     //~1Ah2I~
    	simpleAlertDialog(null,Ptitle,Ptext,flag);              //~1Ah2I~
    }                                                              //~1Ah2I~
//===============================================================================//~1Ah2I~
    public static void showMessage(int Ptitleid,int Ptextid)       //~1Ah2I~
    {                                                              //~1Ah2I~
    //***********                                                  //~1Ah2I~
    	String text=Utils.getStr(Ptextid);                         //~1Ah2I~
        showMessage(Ptitleid,text);                                //~1Ah2I~
    }                                                              //~1Ah2I~
//===============================================================================//~1Ah2I~
    public static void showMessage(int Ptitleid,String Ptext)      //~1Ah2I~
    {                                                              //~1Ah2I~
    //***********                                                  //~1Ah2I~
    	String title;                                              //~1Ah2I~
        if (Ptitleid==-1)                                          //~1Ah2I~
            title="";                                              //~1Ah2I~
        else                                                       //~1Ah2I~
        if (Ptitleid==0)                                           //~1Ah2I~
            title=Utils.getStr(R.string.app_name);                 //~1Ah2R~
        else                                                       //~1Ah2I~
	    	title=Utils.getStr(Ptitleid);                          //~1Ah2I~
        showMessage(title,Ptext);                                //~1Ah2I~
    }                                                              //~1Ah2I~
}//class Alert                                                //~1211R~//~@@@@R~
