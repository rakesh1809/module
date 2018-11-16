package org.cms.hios.common.logging.util;

import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Filters the Audit logs and adds it to the appropriate logger based on the 
 * properties set.
 * @author svisvana
 *
 */
public class AuditLogFilter extends Filter {


	
	
	
	 boolean acceptOnMatch = true;
	 boolean denyOnNonMatch=true;
	  String stringToMatch;

	  /** @deprecated */
	  public String[] getOptionStrings()
	  {
	    return new String[] { "StringToMatch", "AcceptOnMatch","DenyOnNonMatch" };
	  }

	  /** @deprecated */
	  public void setOption(String key, String value)
	  {
	    if (key.equalsIgnoreCase("StringToMatch"))
	      this.stringToMatch = value;
	    else if (key.equalsIgnoreCase("AcceptOnMatch"))
	      this.acceptOnMatch = OptionConverter.toBoolean(value, this.acceptOnMatch);
	    else if (key.equalsIgnoreCase("DenyOnNonMatch"))
		      this.denyOnNonMatch = OptionConverter.toBoolean(value, this.denyOnNonMatch);
	  }

	  public void setStringToMatch(String s)
	  {
	    this.stringToMatch = s;
	  }

	  public String getStringToMatch()
	  {
	    return this.stringToMatch;
	  }

	  public void setAcceptOnMatch(boolean acceptOnMatch)
	  {
	    this.acceptOnMatch = acceptOnMatch;
	  }

	  public boolean getAcceptOnMatch()
	  {
	    return this.acceptOnMatch;
	  }
	  
		public boolean isDenyOnNonMatch() {
			return denyOnNonMatch;
		}

		public void setDenyOnNonMatch(boolean denyOnNonMatch) {
			this.denyOnNonMatch = denyOnNonMatch;
		}

	  public int decide(LoggingEvent event)
	  {
	    String msg = event.getRenderedMessage();

	    if ((msg == null) || (this.stringToMatch == null)) {
	      if(denyOnNonMatch) return -1;
	      else return 0;
	    }
	    
	    if (msg.indexOf(this.stringToMatch) == -1) {
	    	 if(denyOnNonMatch) return -1;
		      else return 0;	   
	    }
	    else{ 
	    	if (this.acceptOnMatch) {
	    		return 1;
	    	}
	    	
	    }
	    return -1;
	  }


}
