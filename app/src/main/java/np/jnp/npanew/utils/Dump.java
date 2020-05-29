//*CID://+1Ad8R~:                                   update#=   55; //~1Ad8R~
//***********************************************                  //~@@@1I~
//1Ad8 2015/07/21 (Asgts)//1A4h 2014/12/03 catch OutOfMemory(Ajagot1w)//1B0g//~1Ad8I~
//1Ab9 2015/05/09 Dump byte[]                                      //~1Ab9I~
//1A6A 2015/02/20 Another Trace option if (Dump.C) for canvas drawing//~1A6AI~
//1075:121207 control dumptrace by manifest debuggable option      //~v107I~
//@@@1 prepend timestamp/threadid                                  //~@@@1I~
//     exception dump                                              //~@@@1I~
//     optional dump by ajago menu option                          //~@@@1I~
//***********************************************                  //~@@@1I~
package np.jnp.npanew.utils;                                       //~1Ad8R~

import np.jnp.npanew.R;                                               //+1Ad8R~
import java.io.*;
import java.util.*;

import android.content.Context;
import android.widget.Toast;
//~v@@@R~

public class Dump
{                                                                  //~1Ad8R~
	public static  boolean Y;   //test before call Dump to avoid memory for parameter String//~1506I~//~1Ad8I~
	public static  boolean C;   //split Canvas drawing              //~1A6AI~//~1Ad8I~
	public static  boolean T;   //split UiTHread trace              //~1A6AI~//~1Ad8I~
                                                                   //~1Ad8I~
	private static PrintWriter Out=null;                           //~1Ad8R~
    private static boolean Terminal=false;                         //~1Ad8R~
	private static boolean exceptionOnly=false;                     //~1507R~//~1Ad8R~
	private static boolean swSD;	//write to sdcard              //~1Ad8I~
	private static boolean swNoMsg;	//no Toast                     //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public static void openEx (String file,boolean PswSD)          //~1Ad8I~
	{                                                              //~1Ad8I~
    	swSD=PswSD;                                                //~1Ad8I~
		openEx(file);                                              //~1Ad8I~
    }                                                              //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public static void openEx (String file)                        //~1504I~
	{                                                              //~1504I~
		open(file);                                                //~1504I~
      	if (AG.isDebuggable                                          //~v107R~//~1Ad8R~
      	&& (Out!=null || Terminal)                                                //~v107R~//~1Ad8R~
      	)                                                            //~v@@@I~//~1Ad8R~
      	{                                                            //~v107R~//~1Ad8R~
        	Y=true;                                                    //~v107R~//~1Ad8R~
      	}                                                            //~v107R~//~1Ad8R~
      	else                                                         //~v107R~//~1Ad8R~
      	{                                                            //~v107R~//~1Ad8R~
    		exceptionOnly=true;                                        //~1506M~//~1Ad8R~
        	Y=false;//dont call Dump except case of Exceoption         //~1506I~//~1Ad8R~
      	}                                                            //~v107R~//~1Ad8R~
    }                                                              //~1504I~
    //**************************************************************//~1Ad8I~
	public static void openExOnlyTerminal()                        //~1Ad8I~
	{                                                              //~1Ad8I~
      	Terminal=true;                                             //~1Ad8I~
    	exceptionOnly=true;                                        //~1Ad8I~
        Y=false;//dont call Dump except case of Exceoption         //~1Ad8I~
    }                                                              //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public static void open(String Pfile,boolean PswSD)            //~1Ad8I~
	{                                                              //~1Ad8I~
    	swSD=PswSD;                                                //~1Ad8I~
		open(Pfile);                                               //~1Ad8I~
    }                                                              //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public static void open(String file)                           //~1Ad8R~
	{                                                              //~1329R~
    	exceptionOnly=false;//not exception only                   //~1506I~
        if (Terminal)                                              //~1Ad8I~
        {                                                          //~1Ad8I~
			Y = true; //call Dump                                  //~1Ad8I~
        	return;                                                //~1Ad8I~
        }                                                          //~1Ad8I~
    	if (Out!=null)                                             //~1329I~
        	return;                                                //~1329I~
        if (file.equals(""))                                       //~1Ad8I~
        {                                                          //~1Ad8I~
            Terminal=true;                                         //~1Ad8I~
			Y = true; //call Dump                                  //~1Ad8I~
        }   		                                               //~1Ad8I~
        else                                                       //~1Ad8I~
        {                                                          //~1Ad8I~
//            try                                                        //~1329I~//~1Ad8R~
//            {                                                          //~1227R~//~1Ad8R~
//                FileOutputStream out;                            //~1Ad8R~
//                if (swSD)                                        //~1Ad8R~
//                    out = UFile.openOutputSD("",file); // /sdcard//~1Ad8R~
//                else                                             //~1Ad8R~
//                    out = UFile.openOutputData(file, Context.MODE_WORLD_READABLE); // ../files//~1Ad8R~
//                if (out != null)                                 //~1Ad8R~
//                {//~1313R~                                       //~1Ad8R~
//                    Out = new PrintWriter(new OutputStreamWriter(out, "UTF-8"), true/*autoFlash*/);//~1227I~//~1309R~//~1Ad8R~
//                    Out.println("Locale: " + Locale.getDefault() + "\n");//~1Ad8R~
//                    Y = true; //call Dump                                //~1506I~//~1Ad8R~
//                }                                                //~1Ad8R~
//            }                                                    //~1Ad8R~
//            catch (IOException e)                                //~1Ad8R~
//            {                                                    //~1Ad8R~
//                Out=null;                                        //~1Ad8R~
//                System.out.println("Dump open failed");                //~1329I~//~1Ad8R~
//                Terminal=true;                                   //~1Ad8R~
//            }                                                    //~1Ad8R~
		}
	}
    //**************************************************************//~1Ad8I~
	public synchronized static void println (String s)             //~1305R~
	{                                                              //~1228R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
                                                                   //~1504I~
	    String tidts=null,tid;                                     //~v@@@I~
  		if (Out!=null)                                             //~1425R~
        {                                                          //~1425I~
	    	tidts=Utils.getThreadTimeStamp();          //~1425I~   //~v@@@R~
			Out.println(tidts+":"+s);                              //~1425I~
        }                                                          //~1425I~
  		if (Terminal)                                              //~1511R~
        {                                                          //~1425I~
	    	tidts= Utils.getThreadTimeStamp();                      //~1Ad8I~
            System.out.println(tidts+":"+s);                         //~1425I~//~v@@@R~//~1Ad8R~
        }                                                          //~1425I~
	}
    //**************************************************************//~1Ad8I~
    private static void byte2string(StringBuffer Psb,int Poutoffs,byte[] Pbytes,int Pinpoffs,int Plen)//~1Ab9I~
    {                                                              //~1Ab9I~
    	String s;                                                  //~1Ab9I~
		try {                                                      //~1Ab9I~
			s = new String(Pbytes,Pinpoffs,Plen,"US-ASCII");       //~1Ab9I~
		} catch (UnsupportedEncodingException e) {                 //~1Ab9I~
			e.printStackTrace();                                   //~1Ab9I~
            return;                                                //~1Ab9I~
		}                                                          //~1Ab9I~
        for (int ii=0;ii<16;ii++)                                  //~1Ab9I~
        {                                                          //~1Ab9I~
            if (ii<Plen)                                           //~1Ab9I~
            {                                                      //~1Ab9I~
        		char ch=s.charAt(ii);                              //~1Ab9I~
//              System.out.println("ch="+ch);                      //~1Ab9I~
            	if (ch<' '|| ch>=0x7f)                             //~1Ab9I~
	            	Psb.setCharAt(ii+Poutoffs,'.');                //~1Ab9I~
            	else                                               //~1Ab9I~
            		Psb.setCharAt(ii+Poutoffs,ch);                 //~1Ab9I~
            }                                                      //~1Ab9I~
            else                                                   //~1Ab9I~
	            Psb.setCharAt(ii+Poutoffs,' ');                    //~1Ab9I~
        }                                                          //~1Ab9I~
    }                                                              //~1Ab9I~
//                                       00 00 00 00 - 00 00 00 00 - 00 00 00 00 - 00 00 00 00 - *0123456789abcdef*//~1Ab9I~
    //**************************************************************//~1Ad8I~
    private static final String dumpfmt="            -             -             -               *                *";//~1Ab9I~
	public synchronized static void println (String Ptitle,byte[] Pbytes)//~1Ab9I~
    {                                                              //~1Ab9I~
    	println(Ptitle,Pbytes,0,Pbytes.length);                    //~1Ab9I~
    }                                                              //~1Ab9I~
    //**************************************************************//~1Ad8I~
	public synchronized static void println(String Ptitle,byte[] Pbytes,int Poffs,int Plen)//~1Ab9I~//~1Ad8R~
	{                                                              //~1Ab9I~
    	if (exceptionOnly)                                         //~1Ab9I~
        	return;                                                //~1Ab9I~
	    println(Ptitle+" : size="+Pbytes.length+"=0x"+Integer.toHexString(Pbytes.length)+",offs=0x"+Integer.toHexString(Poffs)+",len=0x"+Integer.toHexString(Plen));//~1Ab9I~
        StringBuffer sb=new StringBuffer(dumpfmt);                 //~1Ab9I~
        int intch,intch2;                                          //~1Ab9I~
        int fillsz=((Plen+15)/16)*16;                              //~1Ab9I~
        int lastinpoffs=0;                                         //~1Ab9I~
        for (int ii=0,offs=0;ii<fillsz;ii++,offs+=3)               //~1Ab9I~
        {                                                          //~1Ab9I~
        	if (ii!=0)                                             //~1Ab9I~
            {                                                      //~1Ab9I~
                if (ii%16==0)                                      //~1Ab9I~
                {                                                  //~1Ab9I~
                    byte2string(sb,(3*4+2)*4+1,Pbytes,Poffs+ii-16,16);//~1Ab9I~
                    String s=sb.toString();                        //~1Ab9I~
                    if (Out!=null)                                 //~1Ab9I~
                        Out.println(s);                            //~1Ab9I~
                    if (Terminal)                                  //~1Ab9I~
                        System.out.println(s);                     //~1Ab9I~
	                offs=0;                                        //~1Ab9I~
                    lastinpoffs=ii;                                //~1Ab9I~
                }                                                  //~1Ab9I~
                else                                               //~1Ab9I~
                if (ii%4==0)                                       //~1Ab9I~
                    offs+=2;                                       //~1Ab9I~
            }                                                      //~1Ab9I~
            if (ii<Plen)                                           //~1Ab9I~
            {                                                      //~1Ab9I~
            	intch=(Pbytes[Poffs+ii] & 0xff);                   //~1Ab9I~
            	intch2=intch/16;                                   //~1Ab9I~
                if (intch2<10)                                     //~1Ab9I~
            		sb.setCharAt(offs,(char) ('0'+intch2));        //~1Ab9I~
                else                                               //~1Ab9I~
            		sb.setCharAt(offs,(char) ('a'+intch2-10));     //~1Ab9I~
            	intch2=intch%16;                                   //~1Ab9I~
                if (intch2<10)                                     //~1Ab9I~
            		sb.setCharAt(offs+1,(char) ('0'+intch2));      //~1Ab9I~
                else                                               //~1Ab9I~
            		sb.setCharAt(offs+1,(char) ('a'+intch2-10));   //~1Ab9I~
            }                                                      //~1Ab9I~
            else                                                   //~1Ab9I~
            {                                                      //~1Ab9I~
            	sb.setCharAt(offs,' ');                            //~1Ab9I~
            	sb.setCharAt(offs+1,' ');                          //~1Ab9I~
            }                                                      //~1Ab9I~
        }                                                          //~1Ab9I~
        if (lastinpoffs<Plen)	//remaining char dump              //~1Ab9I~
        {                                                          //~1Ab9I~
	    	byte2string(sb,(3*4+2)*4+1,Pbytes,lastinpoffs,Plen-lastinpoffs);//~1Ab9I~
            String s=sb.toString();                                //~1Ab9I~
            if (Out!=null)                                         //~1Ab9I~
            	Out.println(s);                                    //~1Ab9I~
            if (Terminal)                                          //~1Ab9I~
            	System.out.println(s);                             //~1Ab9I~
        }                                                          //~1Ab9I~
	}                                                              //~1Ab9I~
    //**************************************************************//~1Ad8I~
	//** Exception Dump                                                //~1309I~//~1Ad8I~
    //**************************************************************//~1Ad8I~
	public synchronized static void println(Exception e,String s)  //~1309I~
	{                                                              //~1309I~
		println(false,e,s);                        //~1Ad8I~
	}                                                              //~1309I~
    //**************************************************************//~1Ad8I~
	//** Exception Dump                                            //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public synchronized static void printlnNoMsg(Exception e,String s)//~1Ad8I~
	{                                                              //~1Ad8I~
    	swNoMsg=true;                                              //~1Ad8I~
		println(false,e,s);                                        //~1Ad8I~
    	swNoMsg=false;                                             //~1Ad8I~
	}                                                              //~1Ad8I~
    //**************************************************************//~1Ad8I~
	//** Exception Dump                                            //~1Ad8R~
	//** is swmsg=true:show message dialog, false:show toast       //~1Ad8I~
    //**************************************************************//~1Ad8I~
	public synchronized static void println(Boolean Pswmsgdialog,Exception e,String s)//~1Ad8I~
	{                                                              //~1Ad8I~
	    String tidts=Utils.getThreadTimeStamp();                   //~1Ad8I~
        StringWriter sw=new StringWriter();                        //~1Ad8I~
        PrintWriter pw= new PrintWriter(sw);                       //~1Ad8I~
        e.printStackTrace(pw);                                     //~1Ad8I~
		String sst=sw.toString();                                 //~1Ad8I~
        pw.close();                                                //~1Ad8I~
        if (Terminal)                                              //~1Ad8I~
        {                                                          //~1Ad8I~
			System.out.println(tidts+"Dump.Exception:"+s+"\n"+sst);//~1Ad8R~
        }                                                          //~1Ad8I~
        else                                                       //~1Ad8I~
  		if (Out!=null)                                             //~1Ad8I~
        {                                                          //~1Ad8I~
			Out.println(tidts+"Dump.Exception:"+s+"\n"+sst);       //~1Ad8R~
			Out.flush();                                           //~1Ad8I~
        }                                                          //~1Ad8I~
//        if (Pswmsgdialog)                                        //~1Ad8R~
//            Alert.showMessage("Dump.Exception:"+s,e.toString()); //~1Ad8R~
//        else                                                     //~1Ad8R~
//        if (!swNoMsg)                                            //~1Ad8R~
//            UView.showToastLong(R.string.ErrExceptionDetected,":"+s+":"+e.toString());//~1Ad8R~
        if (!swNoMsg)                                              //~1Ad8I~
            showToastLong(R.string.ErrExceptionDetected,":"+s+":"+e.toString());//~1Ad8I~
	}                                                              //~1Ad8I~
//**********************************************************       //~1Ad8I~
    public static void showToastLong(int Presid,String Ptext)      //~1Ad8I~
    {                                                              //~1Ad8I~
        String msg=Utils.getStr(Presid)+Ptext;                     //~1Ad8I~
		Toast.makeText(AG.context,msg,Toast.LENGTH_LONG).show();  //~1Ad8I~
    }                                                              //~1Ad8I~
    //**************************************************************//~1B0gI~//~1Ad8I~
	public synchronized static void println(OutOfMemoryError e,String s)//~1B0gI~//~1Ad8I~
	{                                                              //~1B0gI~//~1Ad8I~
	    String tidts=Utils.getThreadTimeStamp();              //~1B0gI~//~1Ad8I~
        if (Terminal)                                              //~1Ad8I~
        {                                                          //~1Ad8I~
            StringWriter sw=new StringWriter();                    //~1Ad8I~
            PrintWriter pw= new PrintWriter(sw);                   //~1Ad8I~
            e.printStackTrace(pw);                                 //~1Ad8I~
			System.out.println(tidts+"Dump.Exception:"+s+"\n"+sw.toString());//~1Ad8R~
			pw.close();                                            //~1Ad8I~
        }                                                          //~1Ad8I~
        else                                                       //~1Ad8I~
  		if (Out!=null)                                             //~1B0gI~//~1Ad8I~
        {                                                          //~1B0gI~//~1Ad8I~
            StringWriter sw=new StringWriter();                    //~1B0gI~//~1Ad8I~
            PrintWriter pw= new PrintWriter(sw);                   //~1B0gI~//~1Ad8I~
            e.printStackTrace(pw);                                 //~1B0gI~//~1Ad8I~
			Out.println(tidts+"Dump.Exception:"+s+"\n"+sw.toString());//~1Ad8R~
			Out.flush();                                           //~1B0gI~//~1Ad8I~
			pw.close();                                            //~1B0gI~//~1Ad8I~
        }                                                          //~1B0gI~//~1Ad8I~
	}                                                              //~1B0gI~//~1Ad8I~
    //**************************************************************//~1Ad8I~
	/** dump a string without linefeed */
	public static void print (String s)
	{                                                              //~1504R~
    	if (exceptionOnly)                                         //~1504I~
        	return;                                                //~1504I~
		if (Out!=null) Out.print(s);                               //~1504I~
		if (Terminal) System.out.print(s);
	}
    //**************************************************************//~1Ad8I~
	/** close the dump file */
	public static void close ()
	{                                                              //~1503R~
		if (Out!=null)                                             //~1503I~
        {                                                          //~1503I~
        	println("Dump closed");                               //~1503I~
			Out.close();                                           //~1503I~
        }                                                          //~1503I~
    	Out=null;                                                  //~1425I~
	}
    //**************************************************************//~1Ad8I~
    public static void setOption(boolean Pflag)                    //~1507I~//~v@@@R~
    {                                                              //~1507I~//~v@@@R~
        println("DumpOption Changed to"+Pflag);                    //~1507I~//~v@@@R~
        Y=Pflag;    //debug dump                                   //~1507I~//~v@@@R~
        exceptionOnly=!Pflag;                                      //~1507I~//~v@@@R~
    }                                                              //~1507I~//~v@@@R~
    //**************************************************************//~1Ad8I~
    public static void println(String Pcmt,int[][] Pintss)         //~1Ad8I~
    {                                                              //~1Ad8I~
    	for (int ii=0;ii<Pintss.length;ii++)                       //~1Ad8I~
	        println(Pcmt+" ii="+ii+"="+Arrays.toString(Pintss[ii]));//~1Ad8R~
    }                                                              //~1Ad8I~
}
