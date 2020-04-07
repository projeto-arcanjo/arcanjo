package edu.nps.moves.disenum;

import java.util.HashMap;

import edu.nps.moves.siso.EnumNotFoundException;

// RPR ENUMERATIONS XML FOM :: DamageStatusEnum32

public enum DamageState {
    NODAMAGE(0, "No Damage"),
    SLIGHT_DAMAGE(1, "Slight Damage"),
    MODERATE_DAMAGE(2, "Moderate Damage"),
    DESTROYED(3, "Destroyed");
	
    /** The enumerated value */
    public final int value;

    /** Text/english description of the enumerated value */
    public final String description;
    
    static public DamageState lookup[] = new DamageState[4];
    static private HashMap<Integer, DamageState>enumerations = new HashMap<Integer, DamageState>();
    
    /* initialize the array and hash table at class load time */
    static 
    {
        for(DamageState anEnum:DamageState.values())
        {
            lookup[anEnum.value] = anEnum;
            enumerations.put(new Integer(anEnum.getValue()), anEnum);
        }
    } 
    
    /** Constructor */
    DamageState(int value, String description)
    {
        this.value = value;
        this.description = description;
    }
    
    /** Returns the string description associated with the enumerated instance with this value. 
     * If there is no enumerated instance for this value, the string Invalid enumeration: <val> is returned.
    */
    static public String getDescriptionForValue(int aVal)
    {
      String desc;

      DamageState val = enumerations.get(new Integer(aVal));
          if(val == null)
            desc = "Invalid enumeration: " + (new Integer(aVal)).toString();
          else
             desc = val.getDescription();

          return desc;
    }    
    
    /** Returns the enumerated instance with this value. 
     * If there is no enumerated instance for this value, the exception is thrown.
    */
    static public DamageState getEnumerationForValue(int aVal) throws EnumNotFoundException
    {
    	DamageState val;
          val = enumerations.get(new Integer(aVal));
          if(val == null)
             throw new EnumNotFoundException("no enumeration found for value " + aVal + " of enumeration AggregateState");
          return val;
    }    
    
    
    /** Returns true if there is an enumerated instance for this value, false otherwise. 
    */
    static public boolean enumerationForValueExists(int aVal)
    {
    	DamageState val;
          val = enumerations.get(new Integer(aVal));
          if(val == null)
             return false;
          return true;
    }

    /** Returns the enumerated value for this enumeration */
    public int getValue()
    {
      return value;
    }  
    
    /** Returns a text descriptioni for this enumerated value. This is usually used as the basis for the enumeration name. */
    public String getDescription()
    {
      return description;
    }    
}
