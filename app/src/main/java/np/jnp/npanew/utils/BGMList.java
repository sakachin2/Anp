//*CID://+va81R~:                             update#=  149;       //+va81R~
//*************************************************************************//~v106I~
//va81 240303 add BGM kizuki                                       //+va81I~
//va80 240219 selectable BGM                                       //~va80I~
//2020/04/27 va06:BGM                                              //~va06I~
//*************************************************************************//~va06I~
package np.jnp.npanew.utils;                                       //~va06R~

import np.jnp.npanew.R;
import np.jnp.npanew.ButtonDlg;                                    //~va06R~
import np.jnp.npanew.OptionBGM;                                    //~va80I~
//import np.jnp.npa.R;                                             //~va06R~
import java.util.Arrays;
import android.annotation.SuppressLint;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;

public class BGMList                                               //~va06I~
{                                                                  //~1327R~
//****************                                                 //~1A08I~
    private static final String CN="BGMList";                      //~va80R~
    public static final int SOUNDID_BGM_START=11;                  //~va80I~
    public static final int SOUNDID_BGM_THINKING=12;               //~va06I~
    public static final int SOUNDID_BGM_START2=13;                 //~va80I~
    public static final int SOUNDID_BGM_START3=14;                 //+va81I~
    public static final int SOUNDID_ANS_GOOD=21;                   //~va06I~
    public static final int SOUNDID_ANS_NG  =22;                   //~va06R~
    private Tables[] Ssoundtbl={                                   //~9C02I~
    				new Tables(SOUNDID_BGM_START,                 R.raw.bgm_start),//use MediaPlayer for BGM(long audio)//~va06R~
    				new Tables(SOUNDID_BGM_THINKING,              R.raw.bgm_thinking),//use MediaPlayer for BGM(long audio)//~va06I~
    				new Tables(SOUNDID_ANS_GOOD,                  R.raw.sound_good),//~va06I~
    				new Tables(SOUNDID_ANS_NG,                    R.raw.sound_ng),//~va06I~
    				new Tables(SOUNDID_BGM_START2,                R.raw.bgm_mizuchu_kouka_trim),//~va80R~
    				new Tables(SOUNDID_BGM_START3,                R.raw.bgm_kizuki_kouka4instrument),//+va81I~
                    };                                             //~1A08I~

	@SuppressLint("ParserError")
//  private boolean swNoSound;                                     //~va06I~//~va80R~
    public  boolean swNoSound;                                     //~va80I~
                                                          //~1327I~
    private MediaPlayer currentPlayer;                             //~va06R~
    private MediaPlayer pausedPlayer;                              //~va80I~
    private int currentID;                                         //~va06R~
//  private float levelVolume=(float)0.0; //0--1.0                      //~1327I~//~9C02R~//~va06R~//~va80R~
    public  float levelVolume=(float)0.0; //0--1.0                 //~va80I~
	private boolean[] swPrepared=new boolean[Ssoundtbl.length];    //~9C03I~
	private int typeBGM;                                           //~va06I~
//	private boolean swVolChanged;                                  //~va06R~//~va80R~
  	public  boolean swVolChanged;                                  //~va80I~
	private boolean swOnce;                                        //~va06I~
	private boolean swThinking;                                    //~va80I~
	private boolean swOptionBGM;                                   //~va80I~
	public  boolean swUserBGM=true;                                //~va80I~
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
//*from OptionBGM onOK()                                           //~va80I~
//***************************                                      //~va80I~
  	public synchronized void play()                                //~va80I~
	{                                                              //~va80I~
    	if (Dump.Y) Dump.println(CN+"play swThinking="+swThinking);//~va80I~
    	if (swThinking)                                            //~va80I~
			play(SOUNDID_BGM_THINKING);                            //~va80R~
        else                                                       //~va80I~
			play(SOUNDID_BGM_START);                               //~va80R~
    }                                                              //~va80I~
//***************************                                      //~va80I~
  	public synchronized void play(int Psoundid)                    //~va06I~
	{                                                              //~9C02R~
        if (Dump.Y) Dump.println("BGMList.play swNosound="+swNoSound+",id="+Psoundid+",currentID="+currentID+",isPlaying="+isPlaying(Psoundid));     //~9C02I~//~9C03R~//~va06R~
        if (Psoundid==SOUNDID_BGM_THINKING)                        //~va80M~
        	swThinking=true;                                       //~va80M~
        if (swNoSound || Psoundid<0) //BGM for before startgame    //~va06R~
        {                                                          //~va06I~
	    	stopSound();                                           //~va06I~
        	return;                                                //~va06I~
        }                                                          //~va06I~
//        if (Psoundid!=currentID || swVolChanged || currentPlayer==null || !currentPlayer.isPlaying())//~va06R~//~va80R~
//        {                                                          //~va06I~//~va80R~
    		stopSound();                                           //~va06R~
          		if (Psoundid==SOUNDID_BGM_THINKING)                //~va80R~
            	{                                                  //~va80R~
            		String uri=OptionBGM.getSoundUri();            //~va80R~
                	if (uri!=null)                                 //~va80R~
                	{                                              //~va80R~
	                	AG.aUMediaStore.playSound(uri);            //~va80R~
                    	return;                                    //~va80R~
                    }                                              //~va80I~
                    else                                           //~va80I~
                    {                                              //~va80I~
            			int soundid=OptionBGM.getSoundIDdefault(); //~va80R~
                		if (soundid!=0)                            //~va80R~
                		{                                          //~va80R~
			    			playSound(soundid);                    //~va80R~
                    		return;                                //~va80R~
                    	}                                          //~va80R~
                    }                                              //~va80I~
                }                                                  //~va80I~
                else                                               //~va80I~
          		if (Psoundid==SOUNDID_BGM_START)                   //~va80I~
            	{                                                  //~va80I~
            		int soundid=OptionBGM.getSoundID();            //~va80I~
                	if (soundid!=0)                                //~va80I~
                	{                                              //~va80I~
			    		playSound(soundid);                        //~va80I~
                    	return;                                    //~va80I~
                    }                                              //~va80I~
                }                                                  //~va80I~
    		playSound(Psoundid);                                   //~va06R~
//        }                                                          //~va06I~//~va80R~
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
	        UMediaStore.stopBGM(false);                            //~va80I~
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
        currentPlayer=null;                                        //~va80I~
        UMediaStore.stopBGM(false);                                //~va80I~
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
    //******************************************************************//~vaeaR~//~va80I~
    public void onResume()                                         //~vaeaR~//~va80I~
    {                                                              //~vaeaR~//~va80I~
    	if (Dump.Y) Dump.println("BGMList.onResume swNoSound="+swNoSound);//~vaeaR~//~va80I~
        if (swNoSound)                                             //~vaqiI~//~va80I~
        {                                                          //~vaqiI~//~va80I~
	    	if (Dump.Y) Dump.println("BGMList.onResume return by swNoSound");//~vaqiI~//~va80I~
        	return;                                                //~vaqiI~//~va80I~
        }                                                          //~vaqiI~//~va80I~
        try                                                        //~vaeaI~//~va80I~
        {                                                          //~vaeaI~//~va80I~
            if (pausedPlayer!=null)                                //~vaeaI~//~va80I~
            {                                                      //~vaeaI~//~va80I~
                pausedPlayer.start();                              //~vaeaI~//~va80I~
                pausedPlayer=null;                                 //~vaeaI~//~va80I~
            }                                                      //~vaeaI~//~va80I~
            else                                                   //~va80I~
            	play();	        //start or thinking                //~va80I~
        }                                                          //~vaeaI~//~va80I~
        catch(Exception e)                                         //~vaeaI~//~va80I~
        {                                                          //~vaeaI~//~va80I~
        	Dump.println(e,"BGMList.onResume");                    //~vaeaI~//~va80I~
        }                                                          //~vaeaI~//~va80I~
    }                                                              //~vaeaR~//~va80I~
    //******************************************************************//~vaeaI~//~va80I~
    public void onPause()                                          //~vaeaI~//~va80I~
    {                                                              //~vaeaI~//~va80I~
    	if (Dump.Y) Dump.println("BGMList.onPause swNoSound="+swNoSound);//~vaeaR~//~va80I~
        try                                                        //~vaeaI~//~va80I~
        {                                                          //~vaeaI~//~va80I~
            if (currentPlayer!=null)                               //~vaeaI~//~va80I~
            {                                                      //~vaeaI~//~va80I~
                currentPlayer.pause();                             //~vaeaI~//~va80I~
            }                                                      //~vaeaI~//~va80I~
	    	pausedPlayer=currentPlayer;                            //~vaeaI~//~va80I~
        }                                                          //~vaeaI~//~va80I~
        catch(Exception e)                                         //~vaeaI~//~va80I~
        {                                                          //~vaeaI~//~va80I~
        	Dump.println(e,"BGMList.onPause");                     //~vaeaI~//~va80I~
        }                                                          //~vaeaI~//~va80I~
    }                                                              //~vaeaI~//~va80I~
}

