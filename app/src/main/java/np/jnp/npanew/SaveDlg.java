//*CID://+va62R~:                             update#=   16;       //~va62I~
//va62 230228 set filename to save                                 //~va62I~
//********************************************************************//~va62I~
package np.jnp.npanew;                                             //~va62R~

import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;

import np.jnp.npanew.utils.AG;
import np.jnp.npanew.utils.Alert;
import np.jnp.npanew.utils.AlertI;
import np.jnp.npanew.utils.Dump;
import np.jnp.npanew.utils.UButton;

public class SaveDlg extends android.app.Dialog                    //~va62R~
        implements  UButton.UButtonI, AlertI                       //~va62R~
{                                                                  //~va62R~
    private static final int LAYOUTID=R.layout.savedlg;            //~va62I~
    private static final int LID_FILENAME=R.id.etFilenameSave;     //~va62I~
	private ButtonDlg aButtonDlg;                                  //~va62R~
	private String filename,filenameSave;                          //~va62R~
	private EditText etFilename;                                   //~va62I~
	private Button btnOK,btnCancel;                                //~va62I~
	public SaveDlg(ButtonDlg Pdlg,String Pfnm)                    //~va62R~
	{
        super(AG.context);                                                              //~va62R~
    	aButtonDlg=Pdlg;                                           //~va62R~
        filename=(Pfnm==null ? "#null": Pfnm);                     //+va62I~
        setLayout();                                               //~va62I~
        setOnDismissListener((OnDismissListener) new dismissListener(this)); //~1326I~//~@@@@R~//~va62I~
        if (Dump.Y) Dump.println("SavaDlg.constructor fnm="+Pfnm); //~va62I~
	}
    private void setLayout()                          //~1214I~    //~1310I~//~va62I~
    {                                                              //~1214I~//~1310M~//~va62I~
    	LayoutInflater inflater= AG.activity.getLayoutInflater();    //~va62I~
        View layoutview=inflater.inflate(LAYOUTID,null);//~1410R~//~@@@@R~//~va62I~
        setContentView(layoutview);                        //~1214I~//~1310I~//~1325R~//~va62I~
        etFilename=layoutview.findViewById(LID_FILENAME);           //~va62I~
        etFilename.setText(filename);                              //~va62I~
        setTitle(R.string.Title_SaveDlg);                          //~va62I~
    	setModal();                                                //~va62R~
	    btnOK=UButton.bind(layoutview,R.id.Save,this);                //~va62I~
	    btnCancel=UButton.bind(layoutview,R.id.Cancel,this);       //~va62R~
    }                                                              //~1214I~//~1310M~//~va62I~
//****************************************                                  //~1326I~//~1330R~//~va62I~
    public class dismissListener                                   //~1326I~//~va62I~
    		implements OnDismissListener                           //~1326I~//~va62I~
	{                                                              //~1326I~//~va62I~
    	Dialog dialog;                                             //~@@@@I~//~va62I~
        public dismissListener(Dialog Pdialog)                     //~@@@@I~//~va62I~
        {                                                          //~@@@@I~//~va62I~
        	dialog=Pdialog;                                         //~@@@@I~//~va62I~
        }                                                          //~@@@@I~//~va62I~
        @Override                                                  //~1326I~//~va62I~
        public void onDismiss(DialogInterface Pdialog)             //~1326I~//~va62I~
        {                                                          //~1326I~//~va62I~
			if (Dump.Y) Dump.println("SaveDlg.onDismiss"); //~1326I~//~1506R~//~va62I~
        }                                                          //~va62I~
    }                                                              //~1326I~//~va62I~
//****************************************                         //~va62I~
    private void setModal()                                             //~va62I~
    {                                                              //~va62I~
	    setCancelable(false);                                    //~v@@@R~//~va62I~
	    setCanceledOnTouchOutside(false);                          //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    @Override //UButtonI                                           //~va62I~
    public void onClickButton(Button Pbutton)                      //~va62I~
	{                                                              //~va62I~
    	boolean rc=true;                                           //~va62I~
        if (Dump.Y) Dump.println("SavaDlg.onClickButton:"+Pbutton.getText());//~va62I~
    	try                                                        //~va62I~
        {                                                          //~va62I~
        	int id=Pbutton.getId();                                //~va62I~
        	switch(id)                                             //~va62I~
            {                                                      //~va62I~
            case R.id.Save:                                        //~va62I~
                onClickSave();                                     //~va62I~
                break;                                             //~va62I~
            case R.id.Cancel:                                      //~va62I~
                onClickCancel();                                   //~va62I~
                break;                                             //~va62I~
            }                                                      //~va62I~
        }                                                          //~va62I~
        catch(Exception e)                                         //~va62I~
        {                                                          //~va62I~
            Dump.println(e,"SaveDlg:onClick:"+Pbutton.getText());  //~va62I~
        }                                                          //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    private void onClickCancel()                                   //~va62I~
	{                                                              //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.onClickCancel");         //~va62I~
	    dismiss();                                                 //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    private void onClickSave()                                     //~va62I~
	{                                                              //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.onClickSave");           //~va62I~
    	String fnm=etFilename.getText().toString();                     //~9722I~//~va62I~
        if (!isExist(fnm))                                         //~va62I~
        {                                                          //~va62I~
	    	doSave(fnm);                                    //~va62I~
	    	dismiss();                                             //~va62R~
            return;                                                //~va62I~
        }                                                          //~va62I~
        confirmOverride(fnm);                                      //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    private void doSave(String Pfnm)                               //~va62I~
	{                                                              //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.doSvae fnm="+Pfnm);      //~va62I~
        aButtonDlg.saveFile(Pfnm);                                  //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    private boolean isExist(String Pfnm)                        //~va62I~
	{                                                              //~va62I~
        File f=new File(AG.context.getFilesDir(),Pfnm);              //~va62I~
        boolean rc=f.exists();                                    //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.isExistSave rc="+rc+",fnm="+Pfnm);//~va62I~
        return rc;                                                 //~va62I~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    private void confirmOverride(String Pfnm)                   //~va62I~
	{                                                              //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.confirmOverride fnm="+Pfnm);//~va62I~
        filenameSave=filename;                                     //+va62R~
        int flag= Alert.BUTTON_POSITIVE|Alert.BUTTON_NEGATIVE;          //~va62I~
        String msg=filenameSave+" : "+AG.resource.getString(R.string.Msg_ConfirmSave);//~va62I~
        String title=AG.resource.getString(R.string.Title_SaveDlg);//~va62I~
    	Alert.simpleAlertDialog(this/*AlertI*/,title,msg,flag);    //~va62R~
    }                                                              //~va62I~
    //******************************************                   //~va62I~
    @Override                                                      //~va62I~
	public int alertButtonAction(int PbuttonID,int Pitempos)      //~va62I~
    {                                                              //~va62I~
        if (Dump.Y) Dump.println("SaveDlg.alertButtonAction btnid="+PbuttonID);//~va62I~
        if (PbuttonID==Alert.BUTTON_POSITIVE)                      //~va62I~
        {                                                          //~va62I~
		    doSave(filenameSave);                                  //~va62I~
            dismiss();                                             //~va62I~
        }                                                          //~va62I~
	    return 0;
    }                                                              //~va62I~
}

