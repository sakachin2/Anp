//*CID://+va06R~:                             update#=  123;       //~va06R~
//*************************************************************************//~v106I~
//2020/04/27 va06:BGM                                              //~va06I~
//*************************************************************************//~va06I~
package np.jnp.npanew.utils;                                       //~va06R~

import np.jnp.npanew.R;
import np.jnp.npanew.ButtonDlg;                                    //~va06R~
//import np.jnp.npa.R;                                             //+va06R~
import java.util.Arrays;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class BGMList                                               //~va06I~
{                                                                  //~1327R~
//****************                                                 //~1A08I~
    public static final int SOUNDID_BGM_START=11;                  //~va06R~
    public static final int SOUNDID_BGM_THINKING=12;               //~va06I~
    public static final int SOUNDID_ANS_GOOD=21;                   //~va06I~
    public static final int SOUNDID_ANS_NG  =22;                   //~va06R~
    private Tables[] Ssoundtbl={                                   //~9C02I~
    				new Tables(SOUNDID_BGM_START,                 R.raw.bgm_start),//use MediaPlayer for BGM(long audio)//~va06R~
    				new Tables(SOUNDID_BGM_THINKING,              R.raw.bgm_thinking),//use MediaPlayer for BGM(long audio)//~va06I~
    				new Tables(SOUNDID_ANS_GOOD,                  R.raw.sound_good),//~va06I~
    				new Tables(SOUNDID_ANS_NG,                    R.raw.sound_ng),//~va06I~
                    };                                             //~1A08I~

	@SuppressLint("ParserError")
    private boolean swNoSound;                                     //~va06I~
                                                          //~1327I~
    private MediaPlayer currentPlayer;                             //~va06R~
    private int currentID;                                         //~va06R~
    private float levelVolume=(float)0.0; //0--1.0                      //~1327I~//~9C02R~//~va06R~
	private boolean[] swPrepared=new boolean[Ssoundtbl.length];    //~9C03I~
	private int typeBGM;                                           //~va06I~
	private boolean swVolChanged;                                  //~va06R~
	private boolean swOnce;                                        //~va06I~
    //*****************************************************************//~9C03I~//~va06R~
	public BGMList()                                               //~va06R~
	{                                                              //~1327R~
        if (Dump.Y) Dump.println("BGMList constructor");           //~va06I~
    	resetOption();                                             //~va06I~
	}
    //******************************************************************//~va06I~
    public void resetOption()                                      //~va06I~
    {                                                              //~va06I~
        swNoSound= ButtonDlg.isNoBGM();                            //~va06R~
        float v=ButtonDlg.getBGMVolume();                          //~va06R~
        swVolChanged=v!=levelVolume;                               //~va06I~
        levelVolume=v;                                             //~va06I~
        if (swNoSound)                                             //~va06I~
			stopAll();                                             //~va06I~
    	if (Dump.Y) Dump.println("BGMList.resetOption swNoSound="+swNoSound+",volume="+levelVolume+",oldVol="+v);//~va06R~
    }                                                              //~va06I~
//***************************                                      //~9C03I~
  	public synchronized void play(int Psoundid)                    //~va06I~
	{                                                              //~9C02R~
        if (Dump.Y) Dump.println("BGMList.play swNosound="+swNoSound+",id="+Psoundid+",currentID="+currentID+",isPlaying="+isPlaying(Psoundid));     //~9C02I~//~9C03R~//~va06R~
        if (swNoSound || Psoundid<0) //BGM for before startgame    //~va06R~
        {                                                          //~va06I~
	    	stopSound();                                           //~va06I~
        	return;                                                //~va06I~
        }                                                          //~va06I~
		if (Psoundid!=currentID || swVolChanged || currentPlayer==null || !currentPlayer.isPlaying())//~va06R~
        {                                                          //~va06I~
    		stopSound();                                           //~va06R~
    		playSound(Psoundid);                                   //~va06R~
        }                                                          //~va06I~
	}
//***************************                                      //~va06I~
  	public synchronized void playOnce(int Psoundid)                //~va06I~
	{                                                              //~va06I~
        if (Dump.Y) Dump.println("BGMList.playOnce swNosound="+swNoSound+",id="+Psoundid+",currentID="+currentID+",isPlaying="+isPlaying(Psoundid));//~va06I~
        if (swNoSound || Psoundid<0) //BGM for before startgame    //~va06I~
        {                                                          //~va06I~
	    	stopSound();                                           //~va06I~
        	return;                                                //~va06I~
        }                                                          //~va06I~
        swOnce=true;                                               //~va06I~
    	playSound(Psoundid);                                       //~va06I~
        swOnce=false;                                              //~va06I~
	}                                                              //~va06I~
//***************************                                      //~va06I~
    public boolean isPlaying(int Psoundid)                         //~va06I~
	{                                                              //~9C03I~
      	MediaPlayer p=(MediaPlayer)Tables.find(Ssoundtbl,Psoundid,null);//~va06I~
        if (p==null)                                               //~9C03I~
        	return false;                                          //~9C03I~
        boolean rc=p.isPlaying();                                  //~9C03I~
        if (Dump.Y) Dump.println("BGMList.isPlaying soundid="+Psoundid+",rc="+rc);//~9C03I~//~va06R~
		return rc;
	}                                                              //~9C03I~
//***************************                                      //~1327I~
//*Mediaplayer              *                                      //~1327I~
//***************************                                      //~1327I~
    synchronized                                                   //~1327I~
    private void playSound(int Psoundid)                           //~va06I~
    {                                                              //~1327I~
  	    if (Dump.Y) Dump.println("BGMList.playSound start: id="+Psoundid+",volume="+levelVolume);        //~1A3bI~//~9C02R~//~9C03R~//~va06R~
    	int idx=Tables.findByNumKey(Ssoundtbl,Psoundid);                            //~va06I~
        if (idx<0)                                                 //~9C03I~
        	return;                                                //~1327I~
        if (levelVolume==(float)0.0)                                //~9C03I~
        	return;                                                //~9C03I~
        int id=Ssoundtbl[idx].getId();	//resource id                             //~9C03I~//~va06R~
        MediaPlayer player=(MediaPlayer)(Ssoundtbl[idx].getObject());//~9C03R~
        try                                                        //~1327I~
        {                                                          //~1327I~
         if (player==null)                                         //~1A08I~
         {                                                         //~1A08I~
        	player=MediaPlayer.create(AG.context,id);              //~1327I~
            Ssoundtbl[idx].setObject(player);                      //~9C03I~
            swVolChanged=true;                                     //~va06I~
            swPrepared[idx]=true;                                  //~9C03R~
        	if (Dump.Y) Dump.println("Sound.playSound create player="+Utils.toString(player));//~9C02I~//~9C03M~
         }                                                         //~1A08I~
        if (Dump.Y) Dump.println("Sound.playSound player="+Utils.toString(player));//~9C02I~
        if (swVolChanged)                                          //~va06I~
        {                                                          //~9C02R~
            player.setVolume(levelVolume,levelVolume);	//left and right volume//~9C02I~
        	if (Dump.Y) Dump.println("Sound.playSound setVolume level="+levelVolume);//~9C02I~
        }                                                          //~9C02R~
  	     	if (Dump.Y) Dump.println("BGMList.playSound before prepare swPrepared="+swPrepared);//~9C03I~//~va06R~
            if (!swPrepared[idx]) //prepared after crweated        //~9C03R~
            {                                                      //~9C03I~
	        	player.prepare();                                  //~9C03R~
	            swPrepared[idx]=true;                              //~9C03I~
  	     		if (Dump.Y) Dump.println("BGMList.playSound after prepare");//~9C03R~//~va06R~
            }                                                      //~9C03I~
        	player.seekTo(0);                                      //~1327I~
  	     	if (Dump.Y) Dump.println("BGM.playSound after seekTo");//~9C03I~//~va06R~
        	player.start();                                         //~1327I~
  	     	if (Dump.Y) Dump.println("BGMList.playSound after start");//~9C03I~//~va06R~
//      	player.setLooping(true);                               //~va06R~
        	player.setLooping(!swOnce);                            //~va06I~
  	     	if (Dump.Y) Dump.println("BGMList.playSound after setListener="+this.toString());//~9C03I~//~va06R~
  	     	if (Dump.Y) Dump.println("playSound end:id="+Psoundid+",player="+ Utils.toString(player));       //~v106I~//~9C03R~
          if (!swOnce)                                             //~va06I~
          {                                                        //~va06I~
    		currentPlayer=player;                                  //~va06I~
    		currentID=Psoundid;                                    //~va06I~
          }                                                        //~va06I~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,"BGMList.playSound Exception id="+Psoundid);          //~1327I~//~1A08R~//~va06R~
        }                                                          //~1327I~
    }                                                              //~va06I~
    //*******************                                          //~va06I~
    public void stopAll()                                          //~va06I~
    {                                                              //~va06I~
        if (Dump.Y) Dump.println("BGMList.stopAll");               //~va06I~
        try                                                        //~va06I~
        {                                                          //~va06I~
            for (int ii=0;ii<Ssoundtbl.length;ii++)                //~va06I~
            {                                                      //~va06I~
                MediaPlayer player=(MediaPlayer)(Ssoundtbl[ii].getObject());//~va06I~
                if (player!=null)                                  //~va06I~
                    stopSound(player);                             //~va06I~
            }                                                      //~va06I~
        }                                                          //~va06I~
        catch(Exception e)                                         //~va06I~
        {                                                          //~va06I~
        	Dump.println(e,"BGMList.stopAll");                      //~va06R~
        }                                                          //~va06I~
    }                                                              //~va06I~
	//*******************                                          //~1327I~
    private void stopSound()                    //~9C03I~          //~va06R~
    {                                                              //~1327I~
        if (Dump.Y) Dump.println("BGMList stopSound currentPlayer="+Utils.toString(currentPlayer));//~va06I~
    	if (currentPlayer!=null && currentPlayer.isPlaying())     //~va06I~
			stopSound(currentPlayer);                              //~va06I~
    }                                                              //~va06I~
	//*******************                                          //~va06I~
    synchronized                                                   //~va06I~
    private void stopSound(MediaPlayer Pplayer)                    //~va06I~
    {                                                              //~va06I~
        currentID=0;                                               //~va06I~
	    MediaPlayer player=Pplayer;                                //~9C03I~
        int idx=Tables.find(Ssoundtbl,Pplayer);	                       //~9C03I~
        if (Dump.Y) Dump.println("BGMList stopSound idx="+idx+",player="+Utils.toString(player));//~9C02R~//~9C03R~//~va06R~
    	if (player==null || !player.isPlaying())                  //~va06I~
        	return;                                                 //~1327I~
        try                                                        //~1327I~
        {                                                          //~1327I~
        	player.stop();                                         //~1327I~
        	if (Dump.Y) Dump.println("BGMList.stopSound after stop");//~9C03I~//~va06R~
        	player.prepare();                                      //~1A08M~//~9C03R~
            if (idx!=-1)                                           //~9C03I~
	        	swPrepared[idx]=true;                              //~9C03I~
        	if (Dump.Y) Dump.println("BGMList.stopSound after prepare");//~9C03I~//~va06R~
        }                                                          //~1327I~
        catch(Exception e)                                          //~1327I~
        {                                                          //~1327I~
        	Dump.println(e,"id="+Ssoundtbl[idx]+"+Sound Stop Exception player="+Utils.toString(Pplayer));          //~1327I~//~9C03R~
        }                                                          //~1327I~
    }                                                              //~1327I~
}

