//CID://+va6aR~:         update#=      8                           //+va6aR~
//*******************************************************************************//~va42I~
//va6a 230310 show all memo by long press                          //+va6aI~
//va69 230305 add support tool update candidate fgrom menu         //~va69I~
//va67 230303 add Stop memu item                                   //~va67I~
//va42:200524 google play accept over apilevel:26(android-8.0); optionmenu was deprecated(onCreateOptionmenu is not called)//~va42I~
//*******************************************************************************//~va42I~
package np.jnp.npanew;                                                //~v@@@R~//~va42R~

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import np.jnp.npanew.R;
import np.jnp.npanew.utils.AG;                                     //~va42R~
import np.jnp.npanew.utils.Dump;                                   //~va42R~


public class NppMenu                                               //~0915R~
{                                                                  //~0915I~
//    public static final int  MENU_OPTION=0;                            //~0915I~//~va42R~
//    public static final int  MENU_FILE=1;                            //~0915I~//~va42R~
//    public static final int  MENU_HELP=2;                           //~0915I~//~va42R~
    public static final int  MENU_OPTION=R.id.Menu_Option;         //~va42I~
    public static final int  MENU_FILE  =R.id.Menu_File;           //~va42I~
    public static final int  MENU_HELP  =R.id.Menu_Help;           //~va42I~
    public static final int  MENU_STOP  =R.id.Menu_Stop;           //~va67I~
//    public static final int  MENU_CTR=3;                            //~0915I~//~va42R~
    public static final int  DLGID_FILESUBMENU=1;                  //~0A21I~
    public  static String[] strarrayFsubmenu=null;                      //~0A20I~//~0A21R~
//    private static String []menuDesc;                            //~0A10R~//~va42R~
//    private static int menuId[]={                                 //~0915R~//~va42R~
//                MENU_OPTION,                                      //~0915I~//~0A10R~//~va42R~
//                MENU_FILE,                                         //~0915I~//~va42R~
//                MENU_HELP,                                         //~0915I~//~va42R~
//                MENU_CTR};                                       //~va42R~
//    private static int icons[]={                                   //~0915R~//~va42R~
//                                android.R.drawable.ic_menu_preferences, //Option,//~0915R~//~0A10R~//~va42R~
//                                android.R.drawable.ic_menu_save,//FILE//~0915I~//~va42R~
//                                android.R.drawable.ic_menu_help, //~va42R~
//                                0                           };     //~0915R~//~va42R~
	private NppView nppView;                                       //~0A05I~
	private WnpView wnpView;                                       //~0A05I~
	private ButtonDlg pButtonDlg;                                  //~0A05I~
//***************
                                                   //~0A05I~
//**************                                                   //~0915I~
//**************                                                   //~0A20I~
//**************                                                   //~0A20I~
 public  void init(Menu menu,NppView PnppView)                            //~0914R~//~0915R~//~0A05R~
	{                                                              //~0914I~
        String str;                                                //~0915I~
    //********************                                         //~0A05I~
    	nppView=PnppView;                                          //~0A05I~
//        menuDesc=WnpView.contextR.getStringArray(R.array.MenuText); //~0A10I~//~0A20R~//~va42R~
        strarrayFsubmenu=WnpView.contextR.getStringArray(R.array.FileSubmenuText);//~0A20I~//~va42R~
//        for (int ii=0;ii<MENU_CTR;ii++)                                //~0915I~//~va42R~
//        {                                                          //~0915I~//~va42R~
//            str=menuDesc[ii];                               //~0915I~//~va42R~
//            int id=menuId[ii];                                     //~0915I~//~va42R~
//            MenuItem item=menu.add(0,id,0,str);                    //~0915I~//~va42R~
//            item.setIcon(icons[ii]);                               //~0915R~//~va42R~
//        }                                                          //~0915I~//~va42R~
        onCreateOptionMenu_V11(menu);                              //~va42I~
        wnpView=nppView.wnpView;                                   //~0A05I~
        pButtonDlg=wnpView.pButtonDlg;                             //~0A05I~
    }                                                              //~0914I~
//**************                                                   //~0915I~
//**************                                                   //~0A20I~
//**************                                                   //~0A20I~
 	public  int selected(MenuItem item)                        //~0915R~//~0A19R~//~0A20R~
	{                                                              //~0915I~
        int itemid=0;
	    try                                                        //~0A20I~
        {                                                          //~0A20I~
            itemid=item.getItemId();                               //~0915I~//~0A20R~
                                                                   //~0A20I~
            switch(itemid)                                             //~0915I~//~0A20R~
            {                                                          //~0915I~//~0A20R~
            case    MENU_OPTION:                                      //~0915I~//~0A10R~//~0A20R~
                wnpView.pButtonDlg.OnMenuOption();                     //~0A10I~//~0A19R~//~0A20R~
                break;                                                 //~0915I~//~0A20R~
            case    MENU_FILE:                                         //~0915I~//~0A20R~
//              showDialog(DLGID_FILESUBMENU);                     //~0A21R~
				OnMenuFile();                                      //~0A21I~
                break;                                                //~0915I~//~0A20R~
            case    MENU_HELP:                                         //~0915I~//~0A20R~
                wnpView.pwnp.OnAbout();                                //~0A10I~//~0A20R~
                break;                                                 //~0915I~//~0A20R~
            case    MENU_STOP:                                     //~va67I~
                AG.aMainActivity.onExit();                         //~va67I~
                break;                                             //~va67I~
//            case    MENU_CTR:                                         //~0915I~//~0A20R~//~va42R~
            }                                                          //~0915I~//~0A20R~
        }                                                          //~0A20I~
//  	catch(RuntimeException e)                                  //~0A20I~//~va67R~
    	catch(Exception e)                                         //~va67I~
		{                                                          //~0A20I~
//      	e.printStackTrace();                                   //~0A20I~//~va42R~
//  		System.out.println("NppMenuSelected exception"+e.toString());//~0A20I~//~va42R~
    		Dump.println(e,"NppMenu.selected itemid="+Integer.toHexString(itemid));//~va42I~
        }                                                          //~0A20I~
        return 0;                                                  //~0915I~
    }//selected                                                    //~0A20R~
////**********************************                             //~0A21R~
////* from onCreateDialog                                          //~0A21R~
////**********************************                             //~0A21R~
//public Dialog MenuOnCreateDialog(Context Pcontext,int Pdlgid)    //~0A21R~
//{                                                                //~0A21R~
//    Dialog pdlg=null;                                            //~0A21R~
//    switch(Pdlgid)                                               //~0A21R~
//    {                                                            //~0A21R~
//    case DLGID_FILESUBMENU:                                      //~0A21R~
//        OnMenuFile();                                            //~0A21R~
//        break;                                                   //~0A21R~
//    default:                                                     //~0A21R~
//    }                                                            //~0A21R~
//    return pdlg;                                                 //~0A21R~
//}                                                                //~0A21R~
//**********************************                               //~0A20I~
//* list file submenu                                              //~0A20I~
//**********************************                               //~0A20I~
private void OnMenuFile()                                          //~0A20I~
{                                                                  //~0A20I~
//    Dialog dlg=new Dialog(WnpView.context);                        //~0A20I~//~0A21R~
//    dlg.setContentView(R.layout.dlgfsub0);                         //~0A20I~//~0A21R~
//    dlg.setTitle(WnpView.context.getText(R.string.FileSubmenuTitle).toString());//~0A20I~//~0A21R~
//                                                                   //~0A20I~//~0A21R~
//    ArrayList<String> arlist=new ArrayList<String>(strarrayFsubmenu.length);//~0A20I~//~0A21R~
//    arlist.addAll(Arrays.asList(strarrayFsubmenu));                //~0A20I~//~0A21R~
//    ArrayAdapter<String> adapter=new ArrayAdapter<String>(WnpView.context,android.R.layout.simple_list_item_1,arlist);//~0A20I~//~0A21R~
//                                                                   //~0A20I~//~0A21R~
//    ListView listview=(ListView)dlg.findViewById(R.id.ListViewFsubmenu);//~0A20I~//~0A21R~
//    listview.setAdapter(adapter);                                  //~0A20I~//~0A21R~
//    listview.setOnItemClickListener(                               //~0A20I~//~0A21R~
//                                new AdapterView.OnItemClickListener()//~0A20I~//~0A21R~
//                                    {                              //~0A20I~//~0A21R~
//                                        public void onItemClick(AdapterView<?> parent,//~0A20I~//~0A21R~
//                                                                View Pview,int Ppos,long Pid)//~0A20I~//~0A21R~
//                                        {                          //~0A20I~//~0A21R~
//                                            getFileSubmenu(Ppos);  //~0A20I~//~0A21R~
//                                        }                          //~0A20I~//~0A21R~
//                                    }                              //~0A20I~//~0A21R~
//                                );                                 //~0A20I~//~0A21R~
//                                                                   //~0A20I~//~0A21R~
//    dlg.show();                                                    //~0A20I~//~0A21R~
                                                                   //~0B02I~
//*******                                                          //~0B02I~
//    AlertDialog.Builder builder=new AlertDialog.Builder(WnpView.context);//~0A21I~//~v@@@I~
//    builder.setTitle(WnpView.context.getText(R.string.FileSubmenuTitle).toString());//~0A21I~//~v@@@I~
//    builder.setPositiveButton("Close",new DialogInterface.OnClickListener()//~0A21I~//~v@@@I~
//                                {                                  //~0A21I~//~v@@@I~
//                                                                   //~0A21I~//~v@@@I~
//                                    public void onClick(DialogInterface dlg,int buttonID)//~0A21I~//~v@@@I~
//                                    {                              //~0A21I~//~v@@@I~
//                                        dlg.dismiss();             //~0A21I~//~v@@@I~
//                                    }                              //~0A21I~//~v@@@I~
//                                }                                  //~0A21I~//~v@@@I~
//                          );                                       //~0A21I~//~v@@@I~
//    builder.setItems(strarrayFsubmenu,                              //~0A21I~//~v@@@I~
//                    new DialogInterface.OnClickListener()          //~0A21I~//~v@@@I~
//                        {                                          //~0A21I~//~v@@@I~
//                            public void onClick(DialogInterface Pdlg,int Pitem)//~0A21I~//~v@@@I~
//                            {                                      //~0A21I~//~v@@@I~
//                                System.out.println("submenu clic itemno="+Pitem);//~0A21I~//~v@@@I~
//                                selectedFileSubmenu(Pitem);        //~0A21I~//~v@@@I~
//                            }                                      //~0A21I~//~v@@@I~
//                        }                                          //~0A21I~//~v@@@I~
//                   );                                              //~0A21I~//~v@@@I~
//    AlertDialog pdlg=builder.create();                              //~0A21I~//~v@@@I~
//    pdlg.show();                                                   //~0A21I~//~v@@@I~
//*******                                                          //~v@@@I~
                                                              //~0B02I~
	AlertDialog.Builder builder=new AlertDialog.Builder(WnpView.context);//~0A21I~
                                                                   //~0B02I~
	ArrayAdapter<String> adapter=new ArrayAdapter<String>(WnpView.context,R.layout.fsubmlr,strarrayFsubmenu);//~0B02I~//~v@@@R~
    ListView listview=new ListView(WnpView.context);                       //~0B02I~
    listview.setAdapter(adapter);                                  //~0B02I~
    FsubmenuList listener=new FsubmenuList();                      //~0B02I~//~v@@@R~
    listview.setOnItemClickListener(listener);                     //~0B02I~
    LinearLayout layout=new LinearLayout(WnpView.context);                 //~0B02I~//~va67R~
//  ViewGroup.LayoutParams parm=new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100);;//~va67R~
//  layout.setLayoutParams(parm);//todo test                       //~va67R~
    layout.setOrientation(LinearLayout.VERTICAL);                  //~0B02I~
    layout.addView(listview);                                      //~0B02I~
    builder.setView(layout);                                       //~0B02I~
                                                                   //~0B02I~
    builder.setPositiveButton("Close",new DialogInterface.OnClickListener()//~0A21I~
								{                                  //~0A21I~//~v@@@R~
                                                                   //~0A21I~
    								public void onClick(DialogInterface Pdlg,int buttonID)//~0A21I~//~v@@@R~
                                    {                              //~0A21I~
                                    	Pdlg.dismiss();             //~0A21I~//~v@@@R~
                                    }                              //~0A21I~
                                }                                  //~0A21I~
                          );                                       //~0A21I~
// if (AG.isDebuggable)                                              //~va69I~//+va6aR~
//    builder.setNegativeButton("SetMemo",new DialogInterface.OnClickListener()//~va69I~//+va6aR~
//                                {                                  //~va69I~//+va6aR~
//                                                                   //~va69I~//+va6aR~
//                                    public void onClick(DialogInterface Pdlg,int buttonID)//~va69I~//+va6aR~
//                                    {                              //~va69I~//+va6aR~
//                                        setMemo();                 //~va69I~//+va6aR~
//                                        Pdlg.dismiss();            //~va69I~//+va6aR~
//                                    }                              //~va69I~//+va6aR~
//                                }                                  //~va69I~//+va6aR~
//                          );                                       //~va69I~//+va6aR~
    AlertDialog pdlg=builder.create();                              //~0A21I~//~v@@@R~
    pdlg.requestWindowFeature(Window.FEATURE_NO_TITLE);            //~0B02I~
    listener.setDlg(pdlg);                                          //~v@@@I~
    pdlg.show();                                                   //~0A21I~
}//OnMenuFile()                                                    //~0A20I~
//**********************************************************************//~v@@@I~
//*ItemListener class                                              //~v@@@I~
//**********************************************************************//~v@@@I~
class FsubmenuList implements OnItemClickListener
{//~v@@@I~
    private AlertDialog pdlg;                                      //~v@@@I~
	public void setDlg(AlertDialog Pdlg)                           //~v@@@I~
    {                                                              //~v@@@I~
    	pdlg=Pdlg;                                                 //~v@@@I~
    }                                                              //~v@@@I~
                                                                   //~v@@@I~
	public void onItemClick(AdapterView<?> arg0,                   //~v@@@I~
							View arg1, int Ppos, long arg3)        //~v@@@I~
	{                                                              //~v@@@I~
//    	System.out.println("submenu click itemno="+Ppos);          //~v@@@R~
        selectedFileSubmenu(Ppos);                                 //~v@@@I~
        pdlg.dismiss();                                            //~v@@@I~
	}                                                              //~v@@@I~
                                                               //~v@@@I~
}//class FileListSelected                                          //~v@@@I~
//**********************************                               //~0A20I~
//* file submenu selected                                          //~0A21R~
//**********************************                               //~0A20I~
private void selectedFileSubmenu(int Ppos)                              //~0A20I~//~0A21R~
{                                                                  //~0A20I~
	int reqid;                                                     //~0A21I~
//************************                                         //~0A21I~
	switch(Ppos)                                                   //~0A20I~
    {                                                              //~0A20I~
    case 0:          //save                                              //~0A20I~//~0A21R~
    	reqid=CPattern.FILE_SAVE;                                  //~0A21I~
    	break;                                                     //~0A20I~
    case 1:          //load saved                                  //~0A21I~
    	reqid=CPattern.FILE_RELOAD;                                //~0A21I~
    	break;                                                     //~0A21I~
    case 2:          //list score                                  //~0A21I~//~0A24I~
    	reqid=CPattern.FILE_LIST_SCORE;                            //~0A21I~//~0A24M~
    	break;                                                     //~0A21I~//~0A24M~
    case 3:          //old hard->easy                              //~0A21I~//~0A24R~
    	reqid=CPattern.FILE_LIST_LEVEL;                            //~0A21I~//~0A24R~
    	break;                                                     //~0A21I~
    case 4:          //old timeseq                                 //~0A21I~//~0A24I~
    	reqid=CPattern.FILE_LIST_TIMESEQ;                          //~0A21I~//~0A24M~
    	break;                                                     //~0A21I~//~0A24M~
    default:                                                       //~0A20I~
    	return;                                                    //~0A21I~
    }                                                              //~0A20I~
    pButtonDlg.FileSubmenu(reqid);                                 //~0A21R~
}//selectedFileSubmenu                                                  //~0A20I~//~0A21R~
//******************************************************************://~0A20I~
 	public void onCreateOptionMenu_V11(Menu Pmenu)                 //~1A51R~//~va42I~
	{                                                              //~1A51R~//~va42I~
        MenuInflater inf=AG.activity.getMenuInflater();            //~1A51R~//~va42I~
        inf.inflate(R.menu.actionbar,Pmenu);                       //~1A51R~//~va42I~
    }                                                              //~1A51R~//~va42I~
////******************************************************************://~va69I~//+va6aR~
//    public void setMemo()                                          //~va69I~//+va6aR~
//    {                                                              //~va69I~//+va6aR~
//        pButtonDlg.updateMemo();                                   //~va69I~//+va6aR~
//    }                                                              //~va69I~//+va6aR~
}//class NppMenu                                                         //~0915R~//~0A20R~