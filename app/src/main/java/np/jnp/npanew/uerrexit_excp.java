package np.jnp.npanew;                                                //~2718R~//+0528R~
abstract class uerrexit_aexcp extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	abstract public String getmsg();
}                  //~5930I~
class uerrexit_excp extends uerrexit_aexcp{                        //~5930I~
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errmsg;                                         //~5930I~
    public uerrexit_excp(String Pstr)                              //~5930I~
    {                                                              //~5930I~
    	errmsg=Pstr;                                               //~5930I~
    }                                                              //~5930I~
    public String getmsg()                                         //~5930R~
    {                                                              //~5930I~
    	return errmsg;                                             //~5930I~
    }                                                              //~5930I~
}//class uerrexit_excp                                             //~5930I~
