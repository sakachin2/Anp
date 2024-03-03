//*CID://+va80R~:                             update#=  232;       //~va80R~
//************************************************************************
//va80 240219 selectable BGM                                       //~va80I~
//2023/01/30 vavx (BUG)chk intent=null when image picker canceled  //~vavxI~
//2023/01/30 vavw (BUG)prefDialog:selectProfile popups searchPrinter.//~vavwI~
//2023/01/23 vavb (Bug)Exception at Umediastore if query count is 0 //~vavbI~//~vavwR~
//2022/10/31 vau0 Ahsv-1amj 2022/10/31 explicitly say READ_PERMISSION when missing for change BGM//~vau0R~
//2022/09/24 var8 display profile icon                             //~var8I~
//2021/09/19 vae9 1ak2(access external audio file) for BTMJ        //~vae9I~
//2021/09/13 vae2 BGM for BTMJ5                                    //~vae2I~
//1ak2 2021/09/04 access external audio file
//************************************************************************
package np.jnp.npanew.utils;                                       //~va80I~

//import static com.btmtest.StaticVars.AG;                           //~1aK2I~//~va80R~
//import static com.btmtest.dialog.PrefSetting.*;                  //~va80R~
import static np.jnp.npanew.OptionBGM.*;                           //~va80I~
//import static com.btmtest.game.GConst.*;                           //~1aK2I~//~va80R~
//import com.btmtest.R;                                              //~1aK2I~//~va80R~
import np.jnp.npanew.R;                                            //~va80I~
//import com.btmtest.dialog.UPicker;                               //~va80R~
//import com.btmtest.dialog.PrefSetting;                             //~1aK2I~//~va80R~
import np.jnp.npanew.OptionBGM;                                    //~va80I~
import np.jnp.npanew.jnp;                                          //~va80I~

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;

import static android.app.Activity.*;

import androidx.annotation.RequiresApi;

//~1110I~
//@TargetApi(30)                                                   //~var8R~
public class UMediaStore
//        implements UPicker.UPickerI                              //~va80R~
{
    private static final Uri uriAudioMedia=MediaStore.Audio.Media.EXTERNAL_CONTENT_URI; //sdcard/Music or /SDcard/Download from API-1
//  private static final Uri uriDownloadMedia=MediaStore.Downloads.EXTERNAL_CONTENT_URI;//from api29
    private static final Uri uriImageMedia=MediaStore.Images.Media.EXTERNAL_CONTENT_URI;//~var8R~
//  private static final Uri uriImageMedia=MediaStore.Downloads.EXTERNAL_CONTENT_URI; //from api29 TODO test//~var8I~
    private   Uri uriDownloadMedia;
	private static final String CN="UMediaStore.";
    private static final int COL_ID=0;
    private static final int COL_TITLE=1;
    private static final int COL_ARTIST=2;
    private static final int COL_ISMUSIC=3;
    private static final int COL_MIMETYPE=4;
    private static final int COL_SIZE=5;
    private static final int COL_DURATION=6;
    private static final String[] COLUMNS_AUDIO=
					{MediaStore.Audio.Media._ID,
                     MediaStore.Audio.Media.TITLE,
                     MediaStore.Audio.Media.ARTIST,
                     MediaStore.Audio.Media.IS_MUSIC,
                     MediaStore.Audio.Media.MIME_TYPE,
                     MediaStore.Audio.Media.SIZE,
                     MediaStore.Audio.Media.DURATION,
                    };
    private static final int COL_ID_DOWNLOAD=0;
    private static final int COL_TITLE_DOWNLOAD=1;
    private static final int COL_MIMETYPE_DOWNLOAD=2;
    private static final int COL_SIZE_DOWNLOAD=3;
    private static final String[] COLUMNS_DOWNLOAD=
					{MediaStore.MediaColumns._ID,
					 MediaStore.MediaColumns.TITLE,
					 MediaStore.MediaColumns.MIME_TYPE,
					 MediaStore.MediaColumns.SIZE,
                    };

    private String[] colsAudio;
	private ContentResolver CR;
//  private boolean swTest=false;	//TODO test
    private boolean swPaused;
    private ArrayList<AudioFile> audioFiles;
//  private Uri uriPicked,uriStarted,uriClickedUPicker;            //~vae9R~
    private Uri           uriStarted,uriClickedUPicker;            //~vae9I~
//  private AudioFile afPicked;                                    //~vae9R~
    private MediaPlayer MP;
//  private MainFrameOptions MFO;                                  //~1aK2R~
//  private PrefSetting      MFO;                                  //~1aK2I~//~vae2R~
    private UMediaStoreI      callback;                             //~vae2I~
    private String[] strsUserBGMUri=new String[MAX_USERBGM];       //~vae9R~//~vae2R~
    private String[] strsUserBGMTitle=new String[MAX_USERBGM];     //~vae9R~//~vae2R~
    private Uri[]    urisUserBGM=new Uri[MAX_USERBGM];             //~vae2R~
    private Uri uriImageBase;                                      //~var8I~
//**********************************                               //~v@@@I~//~1ak3I~//~vae2I~
    public interface UMediaStoreI                                   //~v@@@R~//~1ak3I~//~vae2R~
    {                                                              //~v@@@I~//~1ak3I~//~vae2I~
		void BGMSelected(Uri PitemUri,AudioFile PaudioFile);  //~v@@@R~                //~1ak3R~//~vae2R~
		void ImageSelected(Uri Puri,String Pid,String PdisplayName,String Ptimestamp,String Pszie);//~var8R~
    }                                                              //~v@@@I~//~1ak3I~//~vae2I~
//********************************************************
    public class AudioFile
    {
        public String title,artist,mimeType;
        public long id;
        public boolean isMusic;
        public long size;
        public int min,sec,duration;
        public AudioFile(String Pid,String Ptitle,String Partist,String PisMusic,String PmimeType,Long Psize,String Pduration)
        {
	        title=Ptitle; artist=Partist; mimeType=PmimeType;
            isMusic=Utils.parseInt(PisMusic,0)!=0;
            id=Utils.parseLong(Pid,0L);
            size=Psize;
            duration=Utils.parseInt(Pduration,0); //millisec
            int s=duration/1000;	//sec
            min=s/60;
            sec=s%60;
        }
        public String toString()
        {
	        return "id="+id+",title="+title+",artist="+artist+",isMusic="+isMusic+",mimeType="+mimeType+",size="+size+",duration="+min+"."+sec;
        }
    }
//********************************************************
    public UMediaStore()
    {
        if (Dump.Y) Dump.println(CN+"constructor osVersion="+AG.osVersion);
        AG.aUMediaStore=this;
//      if (swTest
//      ||  AG.osVersion>= Build.VERSION_CODES.R) //>=Android-11 api30
        try
        {
	        init();
        }
        catch(NoClassDefFoundError e)
        {
            Dump.println(e,CN+"constructor NoClassDefFoundError");
        }
    }
//********************************************************
    private static UMediaStore getInstance()
    {
        UMediaStore ms=AG.aUMediaStore;
	    if (Dump.Y) Dump.println(CN+"getInstance AG.aUMediaStore="+Utils.toString(ms));
        if (ms==null)
        	ms=new UMediaStore();
        return ms;
    }
//********************************************************
    private void init()
    {
        if (Dump.Y) Dump.println(CN+"init");
        if (AG.osVersion>= 29) //Android10
//  		uriDownloadMedia=MediaStore.Downloads.EXTERNAL_CONTENT_URI;//from api29//~var8R~
    		uriDownloadMedia=getUriDownloadMedia29();               //~var8I~
	    CR=AG.context.getContentResolver();
        colsAudio=COLUMNS_AUDIO;
//      String strUri= Prop.getPreference(AG.PKEY_BGM_STRURI,"");  //~1aK2R~
//      String strUri= Utils.getPreference(PREFKEY_BGM_STRURI,""); //~1aK2I~//~vae2R~
//      if (strUri.compareTo("")!=0)                               //~vae2R~
//      	uriPicked=Uri.parse(strUri);                           //~vae2R~
//        uriImageBase=uriImageMedia;                                //~var8I~//~va80R~
//        if (AG.osVersion>= Build.VERSION_CODES.Q) //>=Android-10(Q)=api29//~var8I~//~va80R~
//        {                                                          //~var8I~//~va80R~
//            if (Dump.Y) Dump.println(CN+"requestPickupImage API29 volume_EXTERNAL_PRIMARY="+MediaStore.VOLUME_EXTERNAL_PRIMARY);//~var8I~//~va80R~
//            uriImageBase=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);//~var8I~//~va80R~
//        }                                                          //~var8I~//~va80R~
		setupBGMPreference();                                      //~va80I~
    }
//********************************************************         //~vae2I~
	@TargetApi(29)                                                 //~var8I~
	private Uri getUriDownloadMedia29()                         //~var8I~
    {                                                              //~var8I~
		return MediaStore.Downloads.EXTERNAL_CONTENT_URI;//from api29//~var8I~
    }                                                              //~var8I~
//********************************************************         //~va80I~
    private void setupBGMPreference()                              //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println(CN+"setupBGMPreference");         //~va80I~
        OptionBGM.getBGMPreference();                              //~va80I~
    }                                                              //~va80I~
////********************************************************         //~var8I~//~va80R~
//    public void setupUserBGM()                                    //~vae2I~//~va80R~
//    {                                                              //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"setUpUserBGM");               //~vae2I~//~va80R~
////      PrefSetting.setupUserBGM(AG.prefProp,strsUserBGMUri,strsUserBGMTitle);  //get effective prefProp value after PrefSetting Closed//~vae2R~//~va80R~
//        AG.aOptionBGM.getBGMPreference(strsUserBGMUri,strsUserBGMTitle);    //get effective prefProp value after PrefSetting Closed//~va80R~
//        for (int ii=0;ii<MAX_USERBGM;ii++)                         //~vae2I~//~va80R~
//        {                                                          //~vae2I~//~va80R~
//            String struri=strsUserBGMUri[ii];                  //~vae2I~//~va80R~
//            if (struri.equals(""))                               //~va80R~
//                urisUserBGM[ii]=null;                            //~va80R~
//            else                                                 //~va80R~
//                urisUserBGM[ii]=Uri.parse(struri);               //~va80R~
//        }                                                          //~vae2I~//~va80R~
//    }                                                              //~vae2I~//~va80R~
////********************************************************         //~vae2I~//~va80R~
////* fromBGMList                                                    //~vae2I~//~va80R~
////********************************************************         //~vae2I~//~va80R~
//    public void resetOption()                                      //~vae2R~//~va80R~
//    {                                                              //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"resetOption");                //~vae2R~//~va80R~
//        setupUserBGM();                                            //~vae2I~//~va80R~
//    }                                                              //~vae2I~//~va80R~
////********************************************************         //~vae2I~//~va80R~
////* fromBGMList                                                    //~vae2I~//~va80R~
////********************************************************         //~vae2I~//~va80R~
//    public void recoverOption()                                    //~vae2I~//~va80R~
//    {                                                              //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"recoverOption");              //~vae2I~//~va80R~
//        setupUserBGM();                                            //~vae2I~//~va80R~
//    }                                                              //~vae2I~//~va80R~
//********************************************************
    public void testTest()
    {
        if (Dump.Y) Dump.println(CN+"test");
	    audioFiles=listAudioFile(uriAudioMedia);
        openAudioFiles(audioFiles);
        if (audioFiles.size()!=0)
        {
        	int pos=audioFiles.size()-1;
        	Uri uri= ContentUris.withAppendedId(uriAudioMedia,audioFiles.get(pos).id);
//          play(uri);
        }
//      requestPickup();
    }
//********************************************************
	private ArrayList<AudioFile> listAudioFile(Uri PuirDir)
    {
    	int ctrLine=0;
	    if (Dump.Y) Dump.println(CN+"listAudioFile");
        int[] idxCol=new int[colsAudio.length];
//      String selection="${MediaStore.Audio.Media.IS_MUSIC} > 0}";
        String selection=null;
        Cursor cursor=CR.query(uriAudioMedia,colsAudio,selection,null,null);
        if (Dump.Y) Dump.println(CN+"listAudioFile uriAudioMedia="+uriAudioMedia+",cursor="+Utils.toString(cursor));
        ArrayList<AudioFile> members=new ArrayList<AudioFile>(0/*initial ctr*/);
        if (cursor!=null && cursor.moveToFirst())
        {
//  	    String path=cursor.getString(0);
//  	    if (Dump.Y) Dump.println(CN+"listAudioFile path="+Utils.toString(path));
//          File f=new File(path);
//  	    if (Dump.Y) Dump.println(CN+"listAudioFile File="+f);
        	for (int ii=0;ii<colsAudio.length;ii++)
            {
            	idxCol[ii]=cursor.getColumnIndex(colsAudio[ii]);
		        if (Dump.Y) Dump.println(CN+"listAudioFile colidx ii="+ii+",col="+colsAudio[ii]+",idx="+idxCol[ii]);
            }
            ctrLine=cursor.getCount();
        	members=new ArrayList<AudioFile>(ctrLine);
		    if (Dump.Y) Dump.println(CN+"listAudioFile ctrLine="+ctrLine);
            for (int ii=0;ii<ctrLine;ii++)
            {
                if (Dump.Y) Dump.println(CN+"listAudioFile ii="+ii+",title="+cursor.getString(idxCol[COL_TITLE])+",isMusic="+cursor.getString(idxCol[COL_ISMUSIC]));
                boolean isMusic=Utils.parseInt(cursor.getString(idxCol[COL_ISMUSIC]),0)!=0;
                if (isMusic)
                {
                	AudioFile af=newAudioFile(cursor,idxCol);
	                members.add(af);
                }
                cursor.moveToNext();
            }
        }
        if (cursor!=null)
            cursor.close();
//        if (members.size()>0)   //TODO test
//        {
//            uriPicked=ContentUris.withAppendedId(uriAudioMedia,members.get(0).id);
//            if ((AG.Options & AG.OPTIONS_BGM)!=0)
//                startBGM();
//        }
		if (Dump.Y) Dump.println(CN+"listAudioFile exit members size="+members.size());
        return members;
    }
//********************************************************
	public static Uri getMemberUri(Uri PuriDir,long Pid)
    {
    	Uri uri=ContentUris.withAppendedId(PuriDir,Pid);
		if (Dump.Y) Dump.println(CN+"getMemberUri uriDir="+PuriDir+",id="+Pid+",memberUri="+uri);
        return uri;
    }
//********************************************************
//*TODO test
//********************************************************
	private void listDownloadFile()
    {
    	int ctrLine=0;
	    if (Dump.Y) Dump.println(CN+"listDownloadFile uriDownload="+uriDownloadMedia);
        if (uriDownloadMedia==null)
        	return;
        String[] cols=COLUMNS_DOWNLOAD;
        int[] idxCol=new int[cols.length];
        String selection=null;
        Cursor cursor=CR.query(uriDownloadMedia,cols,selection,null,null);
        if (Dump.Y) Dump.println(CN+"listDownloadFile uriDownloadMedia="+uriDownloadMedia+",cursor="+Utils.toString(cursor));
        ArrayList<AudioFile> members=new ArrayList<AudioFile>(0/*initial ctr*/);
        if (cursor!=null && cursor.moveToFirst())
        {
        	for (int ii=0;ii<colsAudio.length;ii++)
            {
            	idxCol[ii]=cursor.getColumnIndex(cols[ii]);
		        if (Dump.Y) Dump.println(CN+"listDownloadFile colidx ii="+ii+",col="+cols[ii]+",idx="+idxCol[ii]);
            }
            ctrLine=cursor.getCount();
        	members=new ArrayList<AudioFile>(ctrLine);
		    if (Dump.Y) Dump.println(CN+"listDownloadFile ctrLine="+ctrLine);
            for (int ii=0;ii<ctrLine;ii++)
            {
                if (Dump.Y) Dump.println(CN+"listDownloadFile ii="+ii+",title="+cursor.getString(idxCol[COL_TITLE_DOWNLOAD])+",mimeType="+cursor.getString(idxCol[COL_MIMETYPE_DOWNLOAD]));
                cursor.moveToNext();
            }
        }
        if (cursor!=null)
            cursor.close();
    }
//********************************************************
	private AudioFile newAudioFile(Cursor Pcursor,int[] PidxCol)
    {
        if (Dump.Y) Dump.println(CN+"newAudioFile id="+Pcursor.getString(PidxCol[COL_ID])+
					",title="+Pcursor.getString(PidxCol[COL_TITLE])+	//title
					",artist="+Pcursor.getString(PidxCol[COL_ARTIST])+	//artist
					",isMusic="+Pcursor.getString(PidxCol[COL_ISMUSIC])+	//isMusic
					",mimetype="+Pcursor.getString(PidxCol[COL_MIMETYPE])+   //mime_type
					",size="+Pcursor.getLong(PidxCol[COL_SIZE])+   //mime_type
					",duration="+Pcursor.getString(PidxCol[COL_DURATION]));   //mime_type
        AudioFile af=new AudioFile(
					Pcursor.getString(PidxCol[COL_ID]),	//id
					Pcursor.getString(PidxCol[COL_TITLE]),	//title
					Pcursor.getString(PidxCol[COL_ARTIST]),	//artist
					Pcursor.getString(PidxCol[COL_ISMUSIC]),	//isMusic
					Pcursor.getString(PidxCol[COL_MIMETYPE]),   //mime_type
					Pcursor.getLong(PidxCol[COL_SIZE]),   //mime_type
					Pcursor.getString(PidxCol[COL_DURATION])   //mime_type
					);
        if (Dump.Y) Dump.println(CN+"newAudioFile AudioFile="+af.toString());
        return af;
    }
//********************************************************
	private AudioFile getAudioInfo(Uri Puri)
    {
	    if (Dump.Y) Dump.println(CN+"getAudioInfo Puri="+Puri);
        int[] idxCol=new int[colsAudio.length];
        Cursor cursor=CR.query(Puri,colsAudio,null,null,null);
        AudioFile af=null;
        if (Dump.Y) Dump.println(CN+"getAudioInfo cursor="+Utils.toString(cursor));
        if (cursor!=null && cursor.moveToFirst())
        {
        	for (int ii=0;ii<colsAudio.length;ii++)
            {
            	idxCol[ii]=cursor.getColumnIndex(colsAudio[ii]);
		        if (Dump.Y) Dump.println(CN+"listAudioInfo colidx ii="+ii+",col="+colsAudio[ii]+",idx="+idxCol[ii]);
            }
		    if (Dump.Y) Dump.println(CN+"getAudioInfo ctrLine="+cursor.getCount());
            af=newAudioFile(cursor,idxCol);
        }
        if (cursor!=null)
            cursor.close();
        return af;
    }
//********************************************************
	private void openAudioFiles(ArrayList<AudioFile> Plist)
    {
	    if (Dump.Y) Dump.println(CN+"openAudioFiles");
        for (AudioFile af:Plist)
        {
            if (Dump.Y) Dump.println(CN+"openAudioFiles AudioFile="+af.toString());
        	if (!af.isMusic)
            	continue;
        	Uri uri= ContentUris.withAppendedId(uriAudioMedia,af.id);
            openAudioFile(uri);
        }
    }
//********************************************************
	private void openAudioFile(Uri Puri)
    {
	    if (Dump.Y) Dump.println(CN+"openAudioFile uri="+Puri);
        try
        {
        	InputStream is=CR.openInputStream(Puri);
        	InputStreamReader isr=new InputStreamReader(is);
        	BufferedReader br=new BufferedReader(isr);
            br.close();
        }
        catch(FileNotFoundException e)
        {
        	Dump.println(e,CN+"openAudioFile FileNotFound:"+Puri);
        }
        catch(IOException e)
        {
        	Dump.println(e,CN+"openAudioFile IOException:"+Puri);
        }
	    if (Dump.Y) Dump.println(CN+"openAudioFile exit");
    }
////********************************************************         //~vae9I~//~va80R~
//    public void playBGMTop()                                       //~vae9I~//~va80R~
//    {                                                              //~vae9I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"playBGMTop");                 //~vae9I~//~va80R~
//        int soundid=PrefSetting.getSoundIDUser(0);                 //~vae9I~//~va80R~
//        if (soundid>=0)                                            //~vae9I~//~va80R~
//            playSound(soundid);                                    //~vae9R~//~va80R~
//    }                                                              //~vae9I~//~va80R~
////********************************************************         //~vae2I~//~va80R~
////*soundid=MusicNo=idx of urisUserBGM                              //~vae9I~//~va80R~
////********************************************************         //~vae9I~//~va80R~
//    public void playSound(int Psoundid)                            //~vae2I~//~va80R~
//    {                                                              //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"playSound soundid="+Psoundid);//~vae2I~//~va80R~
//        if (Psoundid>=0 && Psoundid<MAX_USERBGM)                   //~vae2I~//~va80R~
//        {                                                          //~vae2I~//~va80R~
//            Uri uri=urisUserBGM[Psoundid];                         //~vae2I~//~va80R~
//            if (uri!=null && !uri.equals(""))                      //~vae2R~//~va80R~
//                play(uri);                                         //~vae2I~//~va80R~
//        }                                                          //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"playSound exit");             //~vae2I~//~va80R~
//    }                                                              //~vae2I~//~va80R~
//********************************************************         //~va80I~
//*soundid=MusicNo=idx of urisUserBGM                              //~va80I~
//********************************************************         //~va80I~
    public void playSound(String PstrUri)                          //~va80I~
    {                                                              //~va80I~
        if (Dump.Y) Dump.println(CN+"playSound uri="+PstrUri);     //~va80I~
		Uri uri=Uri.parse(PstrUri);                                //~va80I~
        play(uri);                                                 //~va80R~
        if (Dump.Y) Dump.println(CN+"playSound uri="+PstrUri+" exit");//~va80I~
    }                                                              //~va80I~
//********************************************************         //~va80R~
    private void play(Uri Puri)                                    //~va80R~
    {                                                              //~va80R~
        if (Dump.Y) Dump.println(CN+"play uri="+Puri+",uriStarted="+uriStarted+",MP="+Utils.toString(MP));//~vae9R~//~va80R~
        if (AG.aBGMList.swNoSound)                                 //+va80I~
        	return;                                                //+va80I~
        if (MP!=null & uriStarted!=null)                           //~vae9R~//~va80R~
        {                                                          //~vae9I~//~va80R~
//          if (!AG.aSound.BGM.swVolChanged && uriStarted.equals(Puri) )//~vae9R~//~va80R~
            if (uriStarted.equals(Puri) )                          //~va80I~
                return;                                             //~vae9I~//~va80R~
            else                                                   //~vae9I~//~va80R~
                stopBGM(false/*swpause*/);                         //~vae9I~//~va80R~
        }                                                          //~vae9I~//~va80R~
//        float vol=AG.aSound.BGM.levelVolume;                       //~vae9I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"play vol="+vol);              //~vae9I~//~va80R~
//        if (vol==(float)0.0)                                       //~vae9I~//~va80R~
//            return;                                                //~vae9I~//~va80R~
        MediaPlayer mp=new MediaPlayer();                          //~va80R~
        MP=mp;                                                     //~va80R~
        final Uri uriPlay=Puri;                                    //~va80R~
//      mp.setAudioStreamType(AudioManager.STREAM_MUSIC);          //~va80R~
        setStreamType(mp);                                         //~va80R~
        try                                                        //~va80R~
        {                                                          //~va80R~
//          mp.setVolume(vol,vol);  //left and right volume        //~vae9I~//~va80R~
            mp.setDataSource(AG.context,Puri); //IOException if FileNotFound//~va80R~
//          mp.setDataSource(AG.aMain.getApplicationContext(),Puri); //IOException if FileNotFound//~va80R~
//          mp.prepare();                                          //~va80R~
            mp.setLooping(true);                                   //~va80R~
            mp.setOnPreparedListener(                              //~va80R~
                    new MediaPlayer.OnPreparedListener()           //~va80R~
                    {                                              //~va80R~
                        @Override                                  //~va80R~
                        public void onPrepared(MediaPlayer Pmp)    //~va80R~
                        {                                          //~va80R~
                            if (Dump.Y) Dump.println(CN+"play.onPrepared mp.start()");//~va80R~
                            Pmp.seekTo(0);                         //~va80R~
                            Pmp.start();                           //~va80R~
                            uriStarted=uriPlay;                    //~va80R~
                        }                                          //~va80R~
                    });                                            //~va80R~
            mp.setOnErrorListener(                                 //~va80R~
                    new MediaPlayer.OnErrorListener()              //~va80R~
                    {                                              //~va80R~
                        @Override                                  //~va80R~
                        public boolean onError(MediaPlayer Pmp,int Pwhat,int Pextra)//~va80R~
                        {                                          //~va80R~
                            if (Dump.Y) Dump.println(CN+"play.onError@@@@ what="+Pwhat+",extra="+Pextra);//~va80R~
                            resetMP(Pmp,true/*swStop*/);           //~va80R~
                            boolean rc=true;    //app processed    //~va80R~
                            return rc;                             //~va80R~
                        }                                          //~va80R~
                    });                                            //~va80R~
            mp.setOnCompletionListener(                            //~va80R~
                    new MediaPlayer.OnCompletionListener()         //~va80R~
                    {                                              //~va80R~
                        @Override                                  //~va80R~
                        public void onCompletion(MediaPlayer Pmp)  //~va80R~
                        {                                          //~va80R~
                            if (Dump.Y) Dump.println(CN+"play.onCompletion");//~va80R~
                            resetMP(Pmp,false/*swStop*/);          //~va80R~
                        }                                          //~va80R~
                    });                                            //~va80R~
            mp.prepareAsync();                                     //~va80R~
        }                                                          //~va80R~
        catch(IllegalArgumentException e)                          //~va80R~
        {                                                          //~va80R~
            Dump.printlnNoMsg(e,CN+"play IlleagalArgumentException:"+Puri);//~var8R~//~va80R~
        }                                                          //~va80R~
        catch(IllegalStateException e)                             //~va80R~
        {                                                          //~va80R~
            Dump.printlnNoMsg(e,CN+"play IlleagalStateException:"+Puri);//~var8R~//~va80R~
        }                                                          //~va80R~
        catch(IOException e)                                       //~va80R~
        {                                                          //~va80R~
            Dump.printlnNoMsg(e,CN+"play IOException:"+Puri);      //~var8R~//~va80R~
        }                                                          //~va80R~
//      mp.start();                                                //~va80R~
        if (Dump.Y) Dump.println(CN+"play exit");                  //~va80R~
    }                                                              //~va80R~
//********************************************************
    private void resetMP(MediaPlayer Pmp,boolean PswStop)
    {
	    if (Dump.Y) Dump.println(CN+"resetMP uriStarted="+Utils.toString(uriStarted)+",MP="+Pmp);
        if (Pmp==null)
        {
		    if (Dump.Y) Dump.println(CN+"resetMP return by MP=null");
        	return;
        }
        Uri uri=uriStarted;
        try
        {
		    if (Dump.Y) Dump.println(CN+"resetMP stop");
        	if (PswStop)
        		Pmp.stop();
        }
        catch(IllegalArgumentException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalArgumentException:"+Utils.toString(uri));//~var8R~
        }
        catch(IllegalStateException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalStateException:"+Utils.toString(uri));//~var8R~
        }
        try
        {
		    if (Dump.Y) Dump.println(CN+"resetMP reset");
        	Pmp.reset();
        }
        catch(IllegalArgumentException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalArgumentException:"+Utils.toString(uri));//~var8R~
        }
        catch(IllegalStateException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalStateException:"+Utils.toString(uri));//~var8R~
        }
        try
        {
		    if (Dump.Y) Dump.println(CN+"resetMP release");
        	Pmp.release();
        }
        catch(IllegalArgumentException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalArgumentException:"+Utils.toString(uri));//~var8R~
        }
        catch(IllegalStateException e)
        {
        	Dump.printlnNoMsg(e,CN+"play IlleagalStateException:"+Utils.toString(uri));//~var8R~
        }
        MP=null;
    }
//********************************************************
	private void setStreamType(MediaPlayer Pplayer)
    {
        if (AG.osVersion>= 26) //Android8.0 Oreo=Api26
        	setStreamType_26(Pplayer);
        else
        	setStreamType_25(Pplayer);
    }
    @SuppressWarnings("deprecation")
	private void setStreamType_25(MediaPlayer Pplayer)
    {
	    if (Dump.Y) Dump.println(CN+"setStreamPlayer_25");
        Pplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }
	@TargetApi(26)     //KitKat
	private void setStreamType_26(MediaPlayer Pplayer)
    {
	    if (Dump.Y) Dump.println(CN+"setStreamPlayer_26");
        Pplayer.setAudioAttributes(
            new AudioAttributes
                .Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build());
//        AudioAttributes.Builder ab=new AudioAttributes.Builder();
//        ab.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
//        ab.setUsage(AudioAttributes.USAGE_MEDIA);
//        AudioAttributes aa=ab.build();
//        Pplayer.setAudioAttributes(aa);
    }
//********************************************************
	public boolean requestPickup(boolean PswUPicker)                 //~vae9R~//~va80R~
    {
	    if (Dump.Y) Dump.println(CN+"requestPickup osVersion="+AG.osVersion+",swUPicker="+PswUPicker);//~vae9R~
        AG.swRequestedBGMPermission=false;                          //~va80I~
        if (!AG.swGrantedExternalStorageRead && !jnp.chkPermission())//~va80I~
        {                                                          //~va80I~
    		jnp.requestPermission();                               //~va80I~
            AG.swRequestedBGMPermission=true;                       //~va80R~
            return false;                                          //~va80R~
        }                                                          //~va80I~
//      if (AG.osVersion>= 30) Android-11(R)                       //~vae9R~
        if (PswUPicker)                                            //~vae9R~
        {
//            UPicker.newInstance(uriAudioMedia,this).showDialog();//~va80R~
            return false;
        }
      if (false)//TODO test
      {
        Intent intent2=new Intent(Intent.ACTION_OPEN_DOCUMENT);   //can not play on picker,invalid cusrsor data for GetString()
        intent2.addCategory(Intent.CATEGORY_OPENABLE);
//      intent2.setType("*/*");
        intent2.setType("audio/*");
        AG.activity.startActivityForResult(intent2,AG.ACTIVITY_REQUEST_PICKUP_AUDIO);
      }
      else
      if (false)//TODO test
      {
        Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);    //can not play on picker,invalid cusrsor data for GetString()
        intent2.setType("*/*");
        AG.activity.startActivityForResult(intent2,AG.ACTIVITY_REQUEST_PICKUP_AUDIO);
      }
      else
      if (false) //TODO test
      {
        Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);    //can not play on picker,invalid cusrsor data
        intent2.setType("audio/*");
        AG.activity.startActivityForResult(intent2,AG.ACTIVITY_REQUEST_PICKUP_AUDIO);
      }
      else
      {
        Intent intent=new Intent(Intent.ACTION_PICK,uriAudioMedia);// can play on picker to select music
//      intent.setType("audio/*");
        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_AUDIO);
      }
	    if (Dump.Y) Dump.println(CN+"requestPickup exit");
        return true;                                               //~va80I~
    }
//********************************************************         //~var8I~
	private void requestPickupImage()                              //~var8I~
    {                                                              //~var8I~
	    if (Dump.Y) Dump.println(CN+"requestPickupImage osVersion="+AG.osVersion+",uri="+uriImageMedia);//~var8R~
     	String action=Intent.ACTION_PICK;                          //~var8I~
     	uriImageBase=uriImageMedia;                                //~var8R~
        if (AG.osVersion>= Build.VERSION_CODES.Q) //>=Android-10(Q)=api29//~var8I~
        {                                                          //~var8I~
		    if (Dump.Y) Dump.println(CN+"requestPickupImage API29 volume_EXTERNAL_PRIMARY="+MediaStore.VOLUME_EXTERNAL_PRIMARY);//~var8I~
        	uriImageBase=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);//~var8R~
        }                                                          //~var8I~
     	Intent intent=new Intent(action,uriImageBase);             //~var8R~
        intent.setType("image/*");  //avoid video              //~@@@1R~//~var8I~
        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_IMAGE);//~var8I~
	    if (Dump.Y) Dump.println(CN+"requestPickupImage exit action="+action+",uriImageBase="+uriImageBase+",intent="+intent+",data="+intent.getData()+",component="+intent.getComponent());//~vavwR~
	    if (Dump.Y) Dump.println(CN+"requestPickupImage exit strUri="+intent.getDataString());//~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImage exit type="+intent.getType());//~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImage exit package="+intent.getPackage());//~vavwI~
    }                                                              //~var8I~
//********************************************************         //~vavwI~
	private void requestPickupImageBySelectedPicker(Intent Pintent)//~vavwI~
    {                                                              //~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImageBySelectedPicker sVersion="+AG.osVersion+",uri="+uriImageMedia);//~vavwI~
     	uriImageBase=uriImageMedia;                                //~vavwI~
        if (AG.osVersion>= Build.VERSION_CODES.Q) //>=Android-10(Q)=api29//~vavwI~
        {                                                          //~vavwI~
		    if (Dump.Y) Dump.println(CN+"requestPickupImage API29 volume_EXTERNAL_PRIMARY="+MediaStore.VOLUME_EXTERNAL_PRIMARY);//~vavwI~
        	uriImageBase=MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);//~vavwI~
        }                                                          //~vavwI~
      if (true)                                                    //~vavwI~
      {                                                            //~vavwI~
     	Intent intent=new Intent(Intent.ACTION_PICK);              //~vavwI~
        intent.setType("image/*");  //avoid video                  //~vavwI~
        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_IMAGE);//~vavwI~
        Pintent=intent;                                            //~vavwI~
      }                                                            //~vavwI~
      else                                                         //~vavwI~
      if (true)                                                    //~vavwI~
      {                                                            //~vavwI~
     	Intent intent=new Intent(Intent.ACTION_PICK,uriImageBase);             //~vavwI~
        intent.setType("image/*");  //avoid video                  //~vavwI~
        ComponentName cmp=Pintent.getComponent();                  //~vavwI~
        intent.setComponent(cmp);                                  //~vavwI~
     	intent.setData(uriImageBase);                              //~vavwI~
        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_IMAGE);//~vavwI~
        Pintent=intent;                                            //~vavwI~
      }                                                            //~vavwI~
      else                                                         //~vavwI~
      {                                                            //~vavwI~
     	Pintent.setData(uriImageBase);                             //~vavwI~
        Pintent.setType("image/*");  //avoid video                 //~vavwI~
        AG.activity.startActivityForResult(Pintent,AG.ACTIVITY_REQUEST_PICKUP_IMAGE);//~vavwI~
      }                                                            //~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImageBySelectedPicker exit uriImageBase="+uriImageBase+",intent="+Pintent+",data="+Pintent.getData()+",component-"+Pintent.getComponent());//~vavwR~
	    if (Dump.Y) Dump.println(CN+"requestPickupImageBySelectedPicker exit strUri="+Pintent.getDataString());//~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImageBySelectedPicker exit type="+Pintent.getType());//~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestPickupImageBySelectedPicker exit package="+Pintent.getPackage());//~vavwI~
    }                                                              //~vavwI~
////********************************************************       //~var8R~
//    private void requestPickupImage_Test()                       //~var8R~
//    {                                                            //~var8R~
//        if (Dump.Y) Dump.println(CN+"requestPickupImage osVersion="+AG.osVersion+",uri="+uriImageMedia);//~var8R~
////      Intent intent=new Intent(Intent.ACTION_PICK,uriImageMedia);//~var8R~
////      Intent intent=new Intent(Intent.ACTION_OPEN_DOCUMENT,uriImageMedia); //TODO test//~var8R~
////      String action=Intent.ACTION_GET_CONTENT;  //TODO test    //~var8R~
//        String action=Intent.ACTION_OPEN_DOCUMENT;               //~var8R~
//        Uri uri=uriImageMedia;                                   //~var8R~
//        Intent intent=new Intent(action,null);   //TODO test     //~var8R~
//        intent.setType("image/*");  //avoid video                //~var8R~
//        intent.addCategory(Intent.CATEGORY_OPENABLE);            //~var8R~
//        intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);//~var8R~
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);  //~var8R~
//        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_IMAGE);//~var8R~
//        if (Dump.Y) Dump.println(CN+"requestPickupImage exit action="+action+",uri="+uri);//~var8R~
//    }                                                            //~var8R~
//********************************************************
//*from AMain
//********************************************************
	public static void onActivityResult(int Prequest,int Presult,Intent Pintent)
    {
	    if (Dump.Y) Dump.println(CN+"onActivityResult request="+Prequest+",result="+Presult);
        UMediaStore ums=getInstance();
        if (Presult==RESULT_OK)
        {
        	Uri uri=Pintent.getData();
    		AudioFile af=ums.getAudioInfo(uri);
            if (af==null)
            {
            	invalidSelection(uri);
            	return;
            }
            if (!af.isMusic)
            {
		    	if (Dump.Y) Dump.println(CN+"onActivityResult OK but not Music="+af.toString());
            }
//  		ums.afPicked=af;                                       //~vae9R~
//          ums.uriPicked=uri;                                     //~vae9R~
////          if ((AG.Options & AG.OPTIONS_BGM)!=0)                  //~1aK2R~//~vae2R~
//            if (!PrefSetting.isNoBGM())                            //~1aK2I~//~vae2R~
//            {                                                    //~vae2R~
//                ums.play(uri);                                   //~vae2R~
//            }                                                    //~vae2R~
//            if (ums.MFO!=null)                                   //~vae2R~
//            {                                                    //~vae2R~
//                ums.MFO.setBGMTitle(ums.afPicked.title,uri.toString()/*save to preference by OK button*/);//~vae2R~
//            }                                                    //~vae2R~
			if (ums.callback!=null)                                //~vae2I~
            	ums.callback.BGMSelected(uri,af);                  //~vae2R~
		    if (Dump.Y) Dump.println(CN+"onActivityResult OK uri="+uri);
        }
//      ums.MFO=null;                                              //~vae2R~
	    if (Dump.Y) Dump.println(CN+"onActivityResult exit");
    }
//********************************************************         //~var8I~
//*from AMain                                                      //~var8I~
//********************************************************         //~var8I~
	public static void onActivityResultImage(int Presult,Intent Pintent)//~var8I~
    {                                                              //~var8I~
	    if (Dump.Y) Dump.println(CN+"onActivityResultImage result="+Presult+",intent="+Pintent);//~vavxR~
        if (Pintent==null)                                         //~vavxI~
        {                                                          //~vavxI~
			if (Dump.Y) Dump.println(CN+"onActivityResultImage null Intent");//~vavxI~
        	return;                                                //~vavxI~
        }                                                          //~vavxI~
        if (Presult==RESULT_OK)                                    //~var8I~
        {                                                          //~var8I~
	        UMediaStore ums=getInstance();                         //~var8I~
        	Uri uri=Pintent.getData();                             //~var8I~
            String path=uri.getPath();                         //~@@@1I~//~var8I~
		    if (Dump.Y) Dump.println(CN+"onActivityResultResultImage uri="+uri+",path="+path);//~var8I~
			if (ums.callback!=null)                                //~var8I~
            {                                                      //~var8I~
				ums.getSetectedImage(uri);                                  //~2923I~//~var8R~
            }                                                      //~var8I~
        }                                                          //~var8I~
	    if (Dump.Y) Dump.println(CN+"onActivityResultImage exit"); //~var8I~
    }                                                              //~var8I~
//********************************************************         //~var8I~
//*from onActivityResult                                           //~var8I~
//********************************************************         //~var8I~
    private void getSetectedImage(Uri Puri)                             //~2923I~//~var8R~
    {                                                              //~2923I~//~var8I~
//* DATA is deprecated at Api29                                    //~var8I~
        if (Dump.Y) Dump.println(CN+"getSelectedImage uri="+Puri+",path="+Puri.getPath()+",encoded="+Puri.getEncodedPath());//~var8I~
		String[] columns={MediaStore.Images.Media._ID,             //~var8R~
							MediaStore.Images.Media.DISPLAY_NAME,  //~var8I~
							MediaStore.Images.Media.SIZE,          //~var8I~
							MediaStore.Images.Media.DATE_ADDED,    //~var8I~
							MediaStore.Images.Media.DATE_MODIFIED, //~var8R~
							MediaStore.Images.Media.DATE_TAKEN,    //~var8I~
							};                                     //~var8I~
        Cursor cursor=CR.query(Puri,columns,null,null,null,null);//~2923I~//~var8R~
        cursor.moveToFirst();                              //~2923I~//~var8I~
        int colIdx=cursor.getColumnIndex(columns[0]);      //~2923I~//~var8I~
        String id=cursor.getString(colIdx);              //~2923I~ //~var8R~
        int colIdx1=cursor.getColumnIndex(columns[1]);     //~@@@1I~//~var8R~
        String name=cursor.getString(colIdx1);             //~@@@1I~//~var8R~
        int colIdx2=cursor.getColumnIndex(columns[2]);     //~@@@1I~//~var8R~
        String size=cursor.getString(colIdx2);             //~@@@1I~//~var8R~
        int colIdx3=cursor.getColumnIndex(columns[3]);             //~var8R~
        String timeAdd=cursor.getString(colIdx3);                  //~var8R~
        int colIdx4=cursor.getColumnIndex(columns[4]);             //~var8I~
        String timeMod=cursor.getString(colIdx4);                  //~var8I~
        int colIdx5=cursor.getColumnIndex(columns[5]);             //~var8R~
        String timeTaken=cursor.getString(colIdx5);                //~var8I~
        cursor.close();
        String ts=timeAdd==null ? timeMod : timeAdd;               //~var8M~
        if (ts==null)                                              //~var8I~
        	ts=timeTaken;                                          //~var8I~
        if (ts==null)                                              //~var8M~
            ts="0";                                                //~var8M~
        if (size==null)                                            //~var8I~
            size="0";                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"getSelected ts="+ts+",tsAdd="+timeAdd+",tsMod="+timeMod+",tsTaken="+timeTaken);//~var8I~
        if (Dump.Y) Dump.println(CN+"getSelected column="+Utils.toString(columns));//~var8I~
        if (Dump.Y) Dump.println(CN+"getSelected uri="+Puri+",id="+id+",displayName="+name+",size="+size+",time="+ts);//~var8R~
        callback.ImageSelected(Puri,id,name,ts,size);              //~var8R~
    }                                                              //~2923I~//~var8I~
//********************************************************         //~var8I~
    private Bitmap loadBMP29(Uri Puri,String Pid)                  //~var8R~
    {                                                              //~var8I~
//* DATA is deprecated at Api29                                    //~var8I~
        if (Dump.Y) Dump.println(CN+"loadBMP29 by ID id="+Pid+",uri="+Puri+",path="+Puri.getPath());//~var8R~
     	Uri uri=uriImageBase;
        long lid=Long.parseLong(Pid);                              //~var8R~
        Uri uriID=ContentUris.withAppendedId(uri,lid);              //~var8M~
        if (Dump.Y) Dump.println(CN+"loadBMP29 uri="+uri+",path="+uri.getPath());//~var8I~
        if (Dump.Y) Dump.println(CN+"loadBMP29 uriID="+uriID+",path="+uriID.getPath());//~var8I~
		String[] columns={MediaStore.Images.Media._ID,             //~var8I~
							MediaStore.Images.Media.DISPLAY_NAME,  //~var8I~
							MediaStore.Images.Media.SIZE,          //~var8I~
							MediaStore.Images.Media.DATE_ADDED,    //~var8I~
							MediaStore.Images.Media.DATE_MODIFIED, //~var8I~
							};                                     //~var8I~
        Cursor cursor=CR.query(uriID,columns,null,null,null,null); //~var8R~
        if (cursor.getCount()==0)                                     //~vavbI~
        {                                                          //~vavbI~
        	if (Dump.Y) Dump.println(CN+"loadBMP29 getCount==0, return null");//~vavbI~
            return null;                                           //~vavbI~
        }                                                          //~vavbI~
        cursor.moveToFirst();                                      //~var8I~
        int colIdx=cursor.getColumnIndex(columns[0]);              //~var8I~
        long id=cursor.getLong(colIdx);                            //~var8I~
//2023/01/23 vavb (Bug)Exception at Umediastore if qury count is 0 //~vavbI~
        int colIdx1=cursor.getColumnIndex(columns[1]);             //~var8I~
        String name=cursor.getString(colIdx1);                     //~var8I~
        int colIdx2=cursor.getColumnIndex(columns[2]);             //~var8I~
        String size=cursor.getString(colIdx2);                     //~var8I~
        int colIdx3=cursor.getColumnIndex(columns[3]);             //~var8I~
        String timeAdd=cursor.getString(colIdx3);                  //~var8I~
        int colIdx4=cursor.getColumnIndex(columns[4]);             //~var8I~
        String timeMod=cursor.getString(colIdx4);                  //~var8I~
        cursor.close();                                            //~var8I~
        String ts=timeAdd==null ? timeMod : timeAdd;               //~var8I~
        if (ts==null)                                              //~var8I~
            ts="0";                                                //~var8I~
        if (size==null)                                            //~var8I~
            size="0";                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"loadBMP29 ts="+ts+",tsAdd="+timeAdd+",tsMod="+timeMod);//~var8R~
        if (Dump.Y) Dump.println(CN+"loadBMP29 column="+Utils.toString(columns));//~var8R~
        if (Dump.Y) Dump.println(CN+"loadBMP29 id="+id+",displayName="+name+",size="+size+",time="+ts);//~var8R~
        Bitmap bm=loadBMP29(uriID);
        return bm;//~var8I~
    }                                                              //~var8I~
////********************************************************       //~var8I~
//    private void getSetectedImage(Uri Puri)                      //~var8I~
//    {                                                            //~var8I~
//        String[] columns={MediaStore.Images.Media.DATA/*absolute filesystem path*/,//~var8I~
//                            MediaStore.Images.Media.DISPLAY_NAME,//~var8I~
//                            MediaStore.Images.Media.SIZE,        //~var8I~
//                            MediaStore.Images.Media.DATE_ADDED,  //~var8I~
//                            MediaStore.Images.Media.DATE_MODIFIED,//~var8I~
//                            MediaStore.Images.Media.RELATIVE_PATH,//~var8I~
//                            MediaStore.Images.Media._ID,         //~var8I~
//                            MediaStore.Images.Media.DATE_TAKEN,  //~var8I~
//                            };                                   //~var8I~
//        Cursor cursor=CR.query(Puri,columns,null,null,null,null);//~var8I~
//        cursor.moveToFirst();                                    //~var8I~
//        int colIdx=cursor.getColumnIndex(columns[0]);            //~var8I~
//        String path=cursor.getString(colIdx);                    //~var8I~
//        int colIdx1=cursor.getColumnIndex(columns[1]);           //~var8I~
//        String name=cursor.getString(colIdx1);                   //~var8I~
//        int colIdx2=cursor.getColumnIndex(columns[2]);           //~var8I~
//        String size=cursor.getString(colIdx2);                   //~var8I~
//        int colIdx3=cursor.getColumnIndex(columns[3]);           //~var8I~
//        String timeAdd=cursor.getString(colIdx3);                //~var8I~
//        int colIdx4=cursor.getColumnIndex(columns[4]);           //~var8I~
//        String timeMod=cursor.getString(colIdx4);                //~var8I~
//        int colIdx5=cursor.getColumnIndex(columns[5]);           //~var8I~
//        String rp=cursor.getString(colIdx5);                     //~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected rp="+rp);       //~var8I~
//        int colIdx6=cursor.getColumnIndex(columns[6]);           //~var8I~
//        long   id=cursor.getLong(colIdx6);                       //~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected id="+id);       //~var8I~
//        int colIdx7=cursor.getColumnIndex(columns[7]);           //~var8I~
//        String timeTaken=cursor.getString(colIdx7);              //~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected timeTaken="+timeTaken);//~var8I~
//        Uri uriid=ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,id);//~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected id uri ="+uriid+",path="+uriid.getPath()+",Puri.path="+Puri.getPath());//~var8I~
////      path=uriid.getPath();   //TODO test                      //~var8I~
//        cursor.close();                                          //~var8I~
//        Bitmap bm;                                               //~var8I~
//        try {                                                    //~var8I~
//            bm = MediaStore.Images.Media.getBitmap(CR, Puri);    //~var8I~
//        }catch(Exception e){Dump.println(CN+"getBMP");bm=null;}  //~var8I~
//            if (bm!=null) if (Dump.Y) Dump.println(CN+"getSelected getBitmap w="+bm.getWidth()+",h="+bm.getHeight());//~var8I~
//        String ts=timeAdd==null ? timeMod : timeAdd;             //~var8I~
//        if (ts==null)                                            //~var8I~
//            ts=getFileTimestamp(path);                           //~var8I~
//        if (ts==null)                                            //~var8I~
//            ts="0";                                              //~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected tsAdd="+timeAdd+",tsMod="+timeMod+",ts="+ts);//~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected column="+Utils.toString(columns));//~var8I~
//        if (Dump.Y) Dump.println(CN+"getSelected path="+path+",displayName="+name+",size="+size+",time="+ts);//~var8I~
//        callback.ImageSelected(Puri,path,name,ts,size);          //~var8I~
//    }                                                            //~var8I~
//********************************************************         //~var8M~
	public static Bitmap loadBMP(String Ppath)                     //~var8I~
    {                                                              //~var8M~
	    if (Dump.Y) Dump.println(CN+"loadBMP path="+Ppath);        //~var8M~
        Bitmap bm=BitmapFactory.decodeFile(Ppath);                 //~var8M~
        return bm;                                                 //~var8M~
    }                                                              //~var8M~
//********************************************************         //~var8M~
	public Bitmap loadBMP_Stream(Uri Puri)                         //~var8R~
    {                                                              //~var8M~
	    if (Dump.Y) Dump.println(CN+"loadBMP_Stream uri="+Puri);   //~var8R~
        Bitmap bm=null;                                            //~var8M~
        Uri uri=Puri;                                              //~var8M~
        try                                                        //~var8M~
        {                                                          //~var8M~
        	InputStream is=CR.openInputStream(uri);                //~var8M~
            if (is!=null)                                          //~var8M~
            {                                                      //~var8M~
        		BufferedInputStream bis=new BufferedInputStream(is);//~var8M~
            	bm= BitmapFactory.decodeStream(bis);               //~var8M~
	            bis.close();                                       //~var8M~
            }                                                      //~var8M~
        }                                                          //~var8M~
        catch(FileNotFoundException e)                             //~var8M~
        {                                                          //~var8M~
        	Dump.printlnNoMsg(e,CN+"loadBMP_Stream FileNotFound:"+uri);//~var8R~
        }                                                          //~var8M~
        catch(IOException e)                                       //~var8M~
        {                                                          //~var8M~
        	Dump.printlnNoMsg(e,CN+"loadBMP_Stream IOException:"+uri);//~var8R~
        }                                                          //~var8M~
	    if (Dump.Y) Dump.println(CN+"loadBMP_Stream exit bm="+bm); //~var8R~
        return bm;                                                 //~var8M~
    }                                                              //~var8M~
//********************************************************         //~var8I~
	public Bitmap loadBMP_Descriptor(Uri Puri)                     //~var8I~
    {                                                              //~var8I~
	    if (Dump.Y) Dump.println(CN+"loadBMP_Descriptor uri="+Puri);//~var8I~
        Bitmap bm=null;                                            //~var8I~
        FileDescriptor fd=null;                                                 //~var8I~
        try                                                        //~var8I~
        {                                                          //~var8I~
			ParcelFileDescriptor pfd=CR.openFileDescriptor(Puri,"r");//~var8I~
            fd=pfd.getFileDescriptor();                         //~var8I~
            bm=BitmapFactory.decodeFileDescriptor(fd);             //~var8I~
        }                                                          //~var8I~
        catch(IOException e)                                       //~var8I~
        {                                                          //~var8I~
        	Dump.printlnNoMsg(e,CN+"loadBMP_Descriptor IOException:"+Puri);//~var8R~
        }                                                          //~var8I~
		if (Dump.Y) Dump.println(CN+"loadBMP_Descriptor fd="+fd+",bm="+bm);//~var8I~
        if (bm!=null) if (Dump.Y) Dump.println(CN+"loadBMP_Descriptor ww="+bm.getWidth()+",hh="+bm.getHeight());//~var8I~
        return bm;                                                 //~var8I~
    }                                                              //~var8I~
//********************************************************         //~var8I~
    public Bitmap loadBMP(Uri Puri,String Pid)                     //~var8R~
    {                                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"loadBMP uri="+Puri+",osVersion="+AG.osVersion);//~var8R~
        Bitmap bm=null;                                            //~var8R~
        try                                                        //~var8I~
        {                                                          //~var8I~
            if (AG.osVersion>= Build.VERSION_CODES.Q) //>=Android-10(Q)=api29//~var8R~
                bm=loadBMP29(Puri,Pid);                            //~var8R~
            else                                                   //~var8R~
                bm=loadBMP28(Puri);                                //~var8R~
		}                                                          //~var8I~
        catch(Exception e)                                         //~var8I~
        {                                                          //~var8I~
        	Dump.printlnNoMsg(e,CN+"loadBMP Exception uri="+Puri+",id="+Pid);//~var8R~
        }                                                          //~var8I~
        if (Dump.Y) Dump.println(CN+"getBitmap bm="+bm);           //~var8R~
        if (bm!=null) if (Dump.Y) Dump.println(CN+"loadBMP w="+bm.getWidth()+",h="+bm.getHeight());//~var8I~
        return bm;                                                 //~var8R~
    }                                                              //~var8I~
//********************************************************         //~var8I~
//*api>=29=Q=Android10                                             //~var8R~
//********************************************************         //~var8I~
	@TargetApi(29)                                                 //~var8R~
//  public Bitmap loadBMP29(Uri Puri)                              //~var8R~//~vavbR~
    private Bitmap loadBMP29(Uri Puri)                             //~vavbI~
    {                                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"getBitmap29 uri="+Puri);      //~var8R~
        Bitmap bm=null;                                            //~var8I~
//        if (false)//TODO test                                    //~var8R~
//        {                                                        //~var8R~
//            bm=loadBMP_Stream(Puri);                             //~var8R~
//        }                                                        //~var8R~
//        else                                                     //~var8R~
//        if (true) //todo                                         //~var8R~
//        {                                                        //~var8R~
//            bm=loadBMP_Descriptor(Puri);                         //~var8R~
//        }                                                        //~var8R~
//        else                                                     //~var8R~
        try                                                        //~var8I~
		{                                                          //~var8I~
	        if (Dump.Y) Dump.println(CN+"getBitmap29 by ImageDecoder");//~var8I~
        	ImageDecoder.Source src=ImageDecoder.createSource(CR, Puri);//~var8I~
            if (Dump.Y) Dump.println(CN+"loadBMP29 createSource src="+src);//~var8R~
            bm=ImageDecoder.decodeBitmap(src);                     //~var8I~
	        if (AG.osVersion>= 26) //=Oreo:Android8                //~var8R~
	            bm=convertHardwareBitmap(bm);                       //~var8R~
        }                                                          //~var8I~
        catch(IOException e)                                       //~var8I~
        {                                                          //~var8I~
            Dump.printlnNoMsg(e,CN+"loadBMP29 IOE uri="+Puri); //~var8R~
        }                                                          //~var8I~
        catch(Exception e)                                         //~var8I~
        {                                                          //~var8I~
            Dump.printlnNoMsg(e,CN+"loadBMP29 Exception uri="+Puri);//~var8R~
        }                                                          //~var8I~
        if (Dump.Y) Dump.println(CN+"loadBMP29 bm="+bm);           //~var8R~
        if (bm!=null) if (Dump.Y) Dump.println(CN+"loadBMP29 w="+bm.getWidth()+",h="+bm.getHeight());//~var8R~
        return bm;                                                 //~var8I~
    }                                                              //~var8I~
	@TargetApi(26)                                                 //~var8I~
    //*************************************************            //~var8I~
    public Bitmap convertHardwareBitmap(Bitmap Pbmp)               //~var8I~
    {                                                              //~var8I~
        if (Pbmp==null)                                            //~var8I~
        	return Pbmp;                                           //~var8I~
        if (Dump.Y) Dump.println(CN+"convertHardwareBitmap Pbmp.config="+Pbmp.getConfig()+",const="+Bitmap.Config.HARDWARE);//~var8R~
        if (Pbmp.getConfig()!=Bitmap.Config.HARDWARE)              //~var8I~
        	return Pbmp;                                           //~var8I~
        Bitmap bm=Pbmp.copy(Bitmap.Config.ARGB_8888,false/*isMutable*/);//~var8R~
        Pbmp.recycle();                                            //~var8I~
        if (Dump.Y) Dump.println(CN+"convertHardwareBitmap exit bmp.config="+bm.getConfig());//~var8R~
        return bm;                                                 //~var8I~
    }                                                              //~var8I~
//********************************************************         //~var8I~
//*api<=28                                                         //~var8R~
//********************************************************         //~var8I~
    @SuppressWarnings("deprecation")                               //~va42I~//~var8R~
//  public Bitmap loadBMP28(Uri Puri)                              //~var8R~//~vavbR~
    private Bitmap loadBMP28(Uri Puri)                             //~vavbI~
    {                                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"getBitmap28 by MediaStore.Images.Media.getBitmap uri="+Puri);//~var8R~
        Bitmap bm=null;                                            //~var8I~
        try                                                        //~var8I~
		{                                                          //~var8I~
        	bm = MediaStore.Images.Media.getBitmap(CR, Puri);   //deprecated api29=A10=Q//~var8R~
        }                                                          //~var8I~
        catch(IOException e)                                       //~var8I~
        {                                                          //~var8I~
            Dump.printlnNoMsg(e,CN+"loadBMP28 IOE uri="+Puri); //~var8R~
        }                                                          //~var8I~
        catch(Exception e)                                         //~var8I~
        {                                                          //~var8I~
            Dump.printlnNoMsg(e,CN+"loadBMP28 Exception uri="+Puri);//~var8R~
        }                                                          //~var8I~
        if (Dump.Y) Dump.println(CN+"getBitmap bm="+bm);           //~var8I~
        if (bm!=null) if (Dump.Y) Dump.println(CN+"loadBMP28 w="+bm.getWidth()+",h="+bm.getHeight());//~var8R~
        return bm;                                                 //~var8I~
    }                                                              //~var8I~
//********************************************************         //~var8I~
    private static String getFileTimestamp(String Ppath)           //~var8I~
    {                                                              //~var8I~
        if (Dump.Y) Dump.println(CN+"getFileTimestamp path="+Ppath);//~var8I~
    	long ts=0L;                                                //~var8I~
    	try                                                        //~var8I~
        {                                                          //~var8I~
        	File f=new File(Ppath);                                //~var8I~
            if (f.exists())                                        //~var8I~
            	ts=f.lastModified();                               //~var8I~
        }                                                          //~var8I~
        catch (Exception e)                                        //~var8I~
        {                                                          //~var8I~
        	Dump.printlnNoMsg(e,CN+"getFileTimestamp path="+Ppath);//~var8R~
        }                                                          //~var8I~
        String rc=ts==0L ? null : Long.toString(ts);               //~var8I~
        if (Dump.Y) Dump.println(CN+"getFileTimestamp rc="+rc+",path="+Ppath);//~var8I~
        return rc;                                                 //~var8I~
    }                                                              //~var8I~
////********************************************************       //~va80R~
//    @Override //UPickerI                                         //~va80R~
//    public void itemSelected(Uri PitemUri)                       //~va80R~
//    {                                                            //~va80R~
//        if (Dump.Y) Dump.println(CN+"itemSelected PitemUri="+PitemUri);//~va80R~
////        resetMP(MP,true/*PswStop*/);                             //~vae2R~//~va80R~
////        Uri uri=PitemUri;                                        //~vae2R~//~va80R~
////        uriPicked=uri;                                           //~vae2R~//~va80R~
////        afPicked=getAudioInfo(uri);                              //~vae2R~//~va80R~
////        if (afPicked==null)                                      //~vae2R~//~va80R~
////        {                                                        //~vae2R~//~va80R~
////            invalidSelection(uri);                               //~vae2R~//~va80R~
////            return;                                              //~vae2R~//~va80R~
////        }                                                        //~vae2R~//~va80R~
//////      if ((AG.Options & AG.OPTIONS_BGM)!=0)                      //~1aK2R~//~vae2R~//~va80R~
////        if (!PrefSetting.isNoBGM())                                //~1aK2I~//~vae2R~//~va80R~
////        {                                                        //~vae2R~//~va80R~
////            play(uri);                                           //~vae2R~//~va80R~
////        }                                                        //~vae2R~//~va80R~
////        if (MFO!=null)                                           //~vae2R~//~va80R~
////        {                                                        //~vae2R~//~va80R~
////            MFO.setBGMTitle(afPicked.title,uri.toString()/*save to preference by OK button*/);//~vae2R~//~va80R~
////        }                                                        //~vae2R~//~va80R~
////        MFO=null;                                                //~vae2R~//~va80R~
//        Uri uri=PitemUri;                                          //~vae2I~//~va80R~
//        if (callback!=null)                                        //~vae2I~//~va80R~
//        {                                                          //~vae2I~//~va80R~
//            AudioFile af=getAudioInfo(uri);                        //~vae2I~//~va80R~
//            callback.BGMSelected(uri,af);                          //~vae2R~//~va80R~
//        }                                                          //~vae2I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"itemSelected exit");        //~va80R~
//    }                                                            //~va80R~
////********************************************************       //~va80R~
////*from UPicker for API30                                        //~va80R~
////********************************************************       //~va80R~
//    public void itemClicked(Uri PitemUri)                        //~va80R~
//    {                                                            //~va80R~
//        if (Dump.Y) Dump.println(CN+"itemSelected PitemUri="+PitemUri);//~va80R~
//        Uri uri=PitemUri;                                        //~va80R~
//        uriClickedUPicker=uri;                                   //~va80R~
//        AudioFile af=getAudioInfo(uri);                          //~va80R~
//        if (af==null)                                            //~va80R~
//        {                                                        //~va80R~
//            invalidSelection(uri);                               //~va80R~
//            return;                                              //~va80R~
//        }                                                        //~va80R~
//        resetMP(MP,true/*PswStop*/);                             //~va80R~
//        play(uri);                                               //~va80R~
//        if (Dump.Y) Dump.println(CN+"itemClicked exit");         //~va80R~
//    }                                                            //~va80R~
//    public void itemCanceled()                                   //~va80R~
//    {                                                            //~va80R~
//        if (Dump.Y) Dump.println(CN+"itemCanceled");             //~va80R~
//        resetMP(MP,true/*PswStop*/);                             //~va80R~
//        if (Dump.Y) Dump.println(CN+"itemCanceled exit");        //~va80R~
//    }                                                            //~va80R~
////********************************************************       //~vae9R~
////*from MainFrameOptions                                         //~vae9R~
////********************************************************       //~vae9R~
//    public static void startBGM()                                //~vae9R~
//    {                                                            //~vae9R~
//        UMediaStore ums=getInstance();                           //~vae9R~
//        if (Dump.Y) Dump.println(CN+"startBGM swpaused="+ums.swPaused+",uriPicked="+Utils.toString(ums.uriPicked)+",MP="+Utils.toString(ums.MP));//~vae9R~
//        if (ums.uriPicked!=null)                                 //~vae9R~
//        {                                                        //~vae9R~
//            if (ums.MP==null)                                    //~vae9R~
//                ums.play(ums.uriPicked);                         //~vae9R~
//            else                                                 //~vae9R~
//                if (ums.swPaused)                                //~vae9R~
//                    ums.MP.start();                              //~vae9R~
//        }                                                        //~vae9R~
//        else                                                     //~vae9R~
//        {                                                        //~vae9R~
//            if (Dump.Y) Dump.println(CN+"startBGM no uri picked yet");//~vae9R~
//        }                                                        //~vae9R~
//    }                                                            //~vae9R~
////********************************************************         //~vae9I~//~va80R~
////*from MainActivity at Resume                                     //~vae9I~//~va80R~
////********************************************************         //~vae9I~//~va80R~
//    private void startBGM()                                        //~vae9I~//~va80R~
//    {                                                              //~vae9I~//~va80R~
//        if (Dump.Y) Dump.println(CN+"startBGM swNoSound="+AG.aSound.BGM.swNoSound+",swUserBGN="+AG.aSound.BGM.swUserBGM+",uriStarted="+Utils.toString(uriStarted));//~vae9I~//~va80R~
//        if (AG.aSound.BGM.swNoSound)                               //~vae9I~//~va80R~
//            return;                                                //~vae9I~//~va80R~
//        if (!AG.aSound.BGM.swUserBGM)                              //~vae9I~//~va80R~
//            return;                                                //~vae9I~//~va80R~
//        if (uriStarted!=null)                                      //~vae9I~//~va80R~
//        {                                                          //~vae9I~//~va80R~
//            if (MP==null)                                          //~vae9I~//~va80R~
//                play(uriStarted);                                  //~vae9I~//~va80R~
//            else                                                   //~vae9I~//~va80R~
//                if (swPaused)                                      //~vae9I~//~va80R~
//                    MP.start();                                    //~vae9I~//~va80R~
//        }                                                          //~vae9I~//~va80R~
//        else                                                       //~vae9I~//~va80R~
//        {                                                          //~vae9I~//~va80R~
////          int soundID=PrefSetting.getSoundIDUser();              //~vae9I~//~va80R~
//            int soundID=AG.aButtonDlg.aOptionBGM.getSoundIDUser();//~va80R~
//            playSound(soundID);                                    //~vae9I~//~va80R~
//        }                                                          //~vae9I~//~va80R~
//    }                                                              //~vae9I~//~va80R~
//********************************************************
	private void pauseBGM()
    {
        try
        {
		    if (Dump.Y) Dump.println(CN+"pauseBGM MP="+Utils.toString(MP));
            if (MP!=null)
            {
	        	MP.pause();
                swPaused=true;
            }
        }
        catch(Exception e)
        {
        	Dump.printlnNoMsg(e,CN+"pause");                       //~var8R~
        }
    }
//********************************************************
	public static void stopBGM(boolean PswPause)
    {
        UMediaStore ums=getInstance();
	    if (Dump.Y) Dump.println(CN+"stopBGM swPause="+PswPause+",MP="+Utils.toString(ums.MP));//~vae9R~
        if (ums.MP!=null)
        {
        	if (PswPause)
            	ums.pauseBGM();
            else
		        ums.resetMP(ums.MP,true/*swStop*/);
        }
        else
        {
		    if (Dump.Y) Dump.println(CN+"stopBGM MP=null");
        }
    }
//********************************************************
//  public static void changeBGM(MainFrameOptions Pmfo)            //~1aK2R~
//  public static void changeBGM(PrefSetting Pmfo)                 //~1aK2I~//~vae2R~
    public static void changeBGM(UMediaStoreI Pcallback,boolean PswUPicker)           //~vae2I~//~vae9R~
    {
        UMediaStore ums=getInstance();
	    if (Dump.Y) Dump.println(CN+"changeBGM AG.swGrantedExternalStorageRead="+AG.swGrantedExternalStorageRead+",MP="+Utils.toString(ums.MP));//~vae9R~
//        if (!AG.swGrantedExternalStorageRead)                    //~va80R~
//        {                                                        //~va80R~
////          UView.showToast(R.string.ErrNoExternalStorageReadPermission);//~1amjI~//~vau0R~//~va80R~
//            Utils.showToast(R.string.ErrNoExternalStorageReadPermission);//~va80R~
//            return;                                              //~va80R~
//        }                                                        //~va80R~
        stopBGM(false/*swPause*/);
//      ums.MFO=Pmfo;                                              //~vae2R~
        ums.callback=Pcallback;                                    //~vae2I~
        ums.requestPickup(PswUPicker);                             //~vae9R~//~va80R~
    }
//********************************************************         //~var8I~
    public static void selectImage(UMediaStoreI Pcallback)         //~var8I~
    {                                                              //~var8I~
        UMediaStore ums=getInstance();                             //~var8I~
	    if (Dump.Y) Dump.println(CN+"selectImage AG.swGrantedExternalStorageRead="+AG.swGrantedExternalStorageRead);//~var8I~
        if (!AG.swGrantedExternalStorageRead)                      //~var8I~
        {                                                          //~var8I~
//          UView.showToast(R.string.ErrNoExternalStoragePermission);//~var8I~//~va80R~
            Utils.showToast(R.string.ErrNoExternalStoragePermission);//~va80R~
        	return;                                                //~var8I~
        }                                                          //~var8I~
        ums.callback=Pcallback;                                    //~var8I~
        ums.requestPickupImage();                                  //~var8I~
    }                                                              //~var8I~
////********************************************************       //~vae9R~
//    public static String getCurrentTitle()                       //~vae9R~
//    {                                                            //~vae9R~
//        UMediaStore ums=getInstance();                           //~vae9R~
//        if (Dump.Y) Dump.println(CN+"getCurrentTitle MP="+Utils.toString(ums.MP)+",AudioFile="+Utils.toString(ums.afPicked));//~vae9R~
//        String rc="";                                            //~vae9R~
//        if (ums.afPicked!=null)                                  //~vae9R~
//            rc=ums.afPicked.title;                               //~vae9R~
//        if (Dump.Y) Dump.println(CN+"getCurrentTitle rc="+rc);   //~vae9R~
//        return rc;                                               //~vae9R~
//    }                                                            //~vae9R~
//********************************************************
	public static ArrayList<AudioFile> getMemberList(Uri PuriDir)
    {
        UMediaStore ums=getInstance();
	    if (Dump.Y) Dump.println(CN+"getMemberList uriDir="+PuriDir);
		ArrayList<AudioFile> list=ums.listAudioFile(PuriDir);
        return list;
    }
//********************************************************
	public static void onPause()
    {
	    if (Dump.Y) Dump.println(CN+"onPause");
    	stopBGM(true/*pause*/);                                    //~vae2R~
    }
//********************************************************         //~vae2I~
	public static void onDestroy()                                 //~vae2I~
    {                                                              //~vae2I~
	    if (Dump.Y) Dump.println(CN+"onDestropy");                 //~vae2I~
    	stopBGM(false/*pause*/);                                   //~vae2R~
    }                                                              //~vae2I~
//********************************************************         //~va80R~
    public static void onResume()                                  //~va80R~
    {                                                              //~va80R~
        if (Dump.Y) Dump.println(CN+"onResume AG.swGrantedExternalStorageRead="+AG.swGrantedExternalStorageRead);//~va80R~
//        if (!AG.swGrantedExternalStorageRead)                    //~va80R~
//            return;                                              //~va80R~
//        UMediaStore ums=getInstance();                           //~va80R~
//        ums.startBGM();                                            //~vae9I~//~va80R~
    }                                                              //~va80R~
//********************************************************
    private static void invalidSelection(Uri Puri)
    {
//  	UView.showToast(Utils.getStr(R.string.ErrInvalidAudioSelection," "+Puri));//~va80R~
    	Utils.showToast(Utils.getStr(R.string.ErrInvalidAudioSelection," "+Puri.toString()));//~va80R~
    }
//********************************************************         //~vavwI~
    public static void selectImagePicker(UMediaStoreI Pcallback)   //~vavwI~
    {                                                              //~vavwI~
        UMediaStore ums=getInstance();                             //~vavwI~
	    if (Dump.Y) Dump.println(CN+"selectImagePicker");          //~vavwI~
        if (!AG.swGrantedExternalStorageRead)                      //~vavwI~
        {                                                          //~vavwI~
//          UView.showToast(R.string.ErrNoExternalStoragePermission);//~vavwI~//~va80R~
            Utils.showToast(R.string.ErrNoExternalStoragePermission);//~va80R~
        	return;                                                //~vavwI~
        }                                                          //~vavwI~
        ums.callback=Pcallback;                                    //~vavwI~
        ums.requestSelectImagePicker();                            //~vavwI~
    }                                                              //~vavwI~
//********************************************************         //~vavwI~
	private void requestSelectImagePicker()                        //~vavwR~
    {                                                              //~vavwI~
	    if (Dump.Y) Dump.println(CN+"requestSelectImagePicker osversion="+AG.osVersion);//~vavwR~
     	String action=Intent.ACTION_PICK_ACTIVITY;                 //~vavwI~
     	Intent intent=new Intent(action);                          //~vavwI~
        intent.putExtra(Intent.EXTRA_INTENT,new Intent(Intent.ACTION_PICK));//~vavwI~
        intent.putExtra(Intent.EXTRA_TITLE,Utils.getStr(R.string.Msg_selectImagePicker));//~vavwR~
        AG.activity.startActivityForResult(intent,AG.ACTIVITY_REQUEST_PICKUP_ACTION);//~vavwR~
	    if (Dump.Y) Dump.println(CN+"requestSelectImagePickup exit");//~vavwR~
    }                                                              //~vavwI~
//********************************************************         //~vavwI~
//*from AMain                                                      //~vavwI~
//********************************************************         //~vavwI~
	public static void onActivityResultSelectImagePicker(int Presult,Intent Pintent)//~vavwI~
    {                                                              //~vavwI~
	    if (Dump.Y) Dump.println(CN+"onActivityResultSelectImagePicker result="+Presult+",intent="+Pintent+",data="+Pintent.getData()+",component="+Pintent.getComponent());//~vavwR~
        if (Presult==RESULT_OK)   //-1                             //~vavwR~
        {                                                          //~vavwI~
		    if (Dump.Y) Dump.println(CN+"onActivityResultSelectImagePicker RESULT_OK");//~vavwI~
	        UMediaStore ums=getInstance();                         //~vavwI~
			ums.requestPickupImageBySelectedPicker(Pintent);       //~vavwR~
        }                                                          //~vavwI~
    }                                                              //~vavwI~
}//class
