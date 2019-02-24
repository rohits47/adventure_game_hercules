/**
* @author Rohit Sanbhadti
* @version Oct 27 2010
* summary: 
* revision history: 
*/

import java.util.ArrayList;

public class Thing
{
    // constants
    public static final int HEAVY  = 0;
    public static final int MOBILE = 1;

    // instance variables
    private String name;
    private int type;
    private Object owner;

    //creates a new thing with given name and of given type
    public Thing(String aName, int aType)
    {
        name = aName;
        type = aType;
        owner = null;
    }

    //returns this thing's name
    public String getName()
    {
        return name;
    }

    //returns the type of this thing
    public int getType()
    {
        return type;
    }
    
    // returns true if thing is heavy, false otherwise
    public boolean isHeavy()
    {
        if (type == HEAVY)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    // returns the owner of the thing
    public Object getOwner()
    {
        return owner;
    }
    
    // changes the owner of the thing to the owner specified in the parameter
    public void changeOwner(Object newOwner)
    {
        if (owner != null) // remove thing from old owner
        {
            if (owner instanceof Room)
            {
                ((Room)owner).remove(this);
            }
            else // instanceof Person
            {
                ((Person)owner).remove(this);
            }
        }
        owner = newOwner; // change owner
        if (newOwner != null) //  add thing to new owner
        {
            if (owner instanceof Room)
            {
                ((Room)owner).add(this);
            }
            else // instanceof Person
            {
                ((Person)owner).add(this);
            }
        }
    }
}