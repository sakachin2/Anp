//*CID://+DATER~:                             update#=   13;       //~1A08R~//~9C01R~
//*************************************************************************//~1A08I~
//2020/04/27 va06:BGM                                              //~0427I~
//*************************************************************************//~0427I~
package np.jnp.npanew.utils;                                          //~0523I~//+0528R~
                                                                   //~1A08M~
public class Tables
{
    public String name;                                            //~1A08R~
    private int id;                                                //~1A08I~
    private Object obj;                                            //~1A08R~
    private int numKey,numValue;                                   //~9C03I~
//****************
    public Tables(String Pname,int Pid,Object Pobj)
    {
        name=Pname; id=Pid; obj=Pobj;
    }
//****************
    public Tables(String Pname,int Pid)
    {
        this(Pname,Pid,null);
    }
//****************                                                 //~9C03I~
    public Tables(int PnumKey,int Pid)                             //~9C03I~
    {                                                              //~9C03I~
        numKey=PnumKey; id=Pid;                                    //~9C03I~
    }                                                              //~9C03I~
//****************
	public static int find(Tables[] Ptbl,String Pname)
    {
    	int idx=-1,sz=Ptbl.length;
    	for (int ii=0;ii<sz;ii++)
    		if (Pname.equals(Ptbl[ii].name))
            {
            	idx=ii;
                break;
            }
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",idx="+idx);
    	return idx;
    }
//****************                                                 //~9C02I~
	public static int find(Tables[] Ptbl,int Pid)                  //~9C02I~
    {                                                              //~9C02I~
    	int idx=-1,sz=Ptbl.length;                                 //~9C02I~
    	for (int ii=0;ii<sz;ii++)                                  //~9C02I~
    		if (Pid==Ptbl[ii].id)                                 //~9C02I~
            {                                                      //~9C02I~
            	idx=ii;                                            //~9C02I~
                break;                                             //~9C02I~
            }                                                      //~9C02I~
    	if (Dump.Y) Dump.println("Tables:find id="+Pid+",idx="+idx);//~9C02I~
    	return idx;                                                //~9C02I~
    }                                                              //~9C02I~
//****************                                                 //~9C03I~
	public static int findByNumKey(Tables[] Ptbl,int PnumKey)      //~9C03I~
    {                                                              //~9C03I~
    	int idx=-1,sz=Ptbl.length;                                 //~9C03I~
    	for (int ii=0;ii<sz;ii++)                                  //~9C03I~
    		if (PnumKey==Ptbl[ii].numKey)                          //~9C03I~
            {                                                      //~9C03I~
            	idx=ii;                                            //~9C03I~
                break;                                             //~9C03I~
            }                                                      //~9C03I~
    	if (Dump.Y) Dump.println("Tables:findByNumKey numkey="+PnumKey+",idx="+idx);//~9C03I~//~0427R~
    	return idx;                                                //~9C03I~
    }                                                              //~9C03I~
//****************                                                 //~0427I~
	public static int getNumKeyByNumValue(Tables[] Ptbl,int PnumValue,int Pdefault)//~0427I~
    {                                                              //~0427I~
    	int numK=Pdefault,sz=Ptbl.length;                          //~0427I~
    	for (int ii=0;ii<sz;ii++)                                  //~0427I~
    		if (PnumValue==Ptbl[ii].numValue)                      //~0427I~
            {                                                      //~0427I~
            	numK=Ptbl[ii].numKey;                              //~0427I~
                break;                                             //~0427I~
            }                                                      //~0427I~
    	if (Dump.Y) Dump.println("Tables:getNumKeyByNumValue numValue="+PnumValue+",numKey="+numK);//~0427I~
    	return numK;                                               //~0427I~
    }                                                              //~0427I~
//****************                                                 //~9C02I~
	public static String findName(Tables[] Ptbl,int Pid,String PnotFoundName)//~9C02I~
    {                                                              //~9C02I~
		int idx=find(Ptbl,Pid);                                    //~9C02I~
        String nm=idx==-1 ? PnotFoundName : Ptbl[idx].name;        //~9C02I~
    	if (Dump.Y) Dump.println("Tables:findName id="+Pid+",name="+nm);//~9C02I~
    	return nm;                                                 //~9C02I~
    }                                                              //~9C02I~
//****************
	public static int find(Tables[] Ptbl,String Pname,int Pnotfoundid)
    {
    	int id;
    	int idx=find(Ptbl,Pname);
        if (idx==-1)
        	id=Pnotfoundid;
        else
        	id=Ptbl[idx].id;
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",id="+id);
        return id;
    }
//****************
	public static Object find(Tables[] Ptbl,String Pname,Object Pnotfoundid)
    {
    	Object obj;
    	int idx=find(Ptbl,Pname);
        if (idx==-1)
        	obj=Pnotfoundid;
        else
        	obj=Ptbl[idx].obj;
    	if (Dump.Y) Dump.println("Tables:find name="+Pname+",obj="+(obj==null?"null":obj.toString()));
        return obj;
    }
//****************                                                 //~9C03I~
	public static int find(Tables[] Ptbl,Object Pobj)              //~9C03I~
    {                                                              //~9C03I~
    	Object obj;                                                //~9C03I~
    	int idx=-1,sz=Ptbl.length;                                 //~9C03I~
    	for (int ii=0;ii<sz;ii++)                                  //~9C03I~
    		if (Pobj==Ptbl[ii].obj)                                //~9C03I~
            {                                                      //~9C03I~
            	idx=ii;                                            //~9C03I~
                break;                                             //~9C03I~
            }                                                      //~9C03I~
    	if (Dump.Y) Dump.println("Tables:find by obj="+Utils.toString(Pobj)+",rc="+idx);//~9C03I~
        return idx;                                                //~9C03I~
    }                                                              //~9C03I~
//****************                                                 //~9C02I~
	public static Object find(Tables[] Ptbl,int Pid,Object Pnotfoundid)//~9C02I~
    {                                                              //~9C02I~
    	Object obj;                                                //~9C02I~
    	int idx=find(Ptbl,Pid);                                    //~9C02I~
        if (idx==-1)                                               //~9C02I~
        	obj=Pnotfoundid;                                       //~9C02I~
        else                                                       //~9C02I~
        	obj=Ptbl[idx].obj;                                     //~9C02I~
    	if (Dump.Y) Dump.println("Tables:find id="+Pid+",obj="+(obj==null?"null":obj.toString()));//~9C02I~
        return obj;                                                //~9C02I~
    }                                                              //~9C02I~
//****************                                                 //~9C03I~
	public static int findNumValue(Tables[] Ptbl,int PnumKey,int Pnotfoundid)//~9C03I~
    {                                                              //~9C03I~
    	int val;                                                   //~9C03I~
    	int idx=findByNumKey(Ptbl,PnumKey);                        //~9C03I~
        if (idx==-1)                                               //~9C03I~
        	val=Pnotfoundid;                                       //~9C03I~
        else                                                       //~9C03I~
        	val=Ptbl[idx].numValue;                                //~9C03I~
    	if (Dump.Y) Dump.println("Tables:findNumValue numKey="+PnumKey+",idx="+idx+",rc="+val);//~9C03I~
        return val;                                                //~9C03I~
    }                                                              //~9C03I~
//****************                                                 //~1A08I~
	public void setObject(Object Pobj)                             //~1A08R~
    {                                                              //~1A08I~
    	obj=Pobj;                                                  //~1A08I~
    }                                                              //~1A08I~
//****************                                                 //~9C03I~
	public void setNumValue(int PnumValue)                         //~9C03I~
    {                                                              //~9C03I~
    	numValue=PnumValue;                                        //~9C03I~
    }                                                              //~9C03I~
//****************                                                 //~1A08I~
	public Object getObject()                                      //~1A08I~
    {                                                              //~1A08I~
    	return obj;                                                //~1A08I~
    }                                                              //~1A08I~
//****************                                                 //~1A08I~
	public int getId()                                             //~1A08I~
    {                                                              //~1A08I~
    	return id;                                                 //~1A08I~
    }                                                              //~1A08I~
//****************
}
