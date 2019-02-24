/*
 * Class Room - a room in an adventure game.
 *
 * Michael Kolling's Zork code, modified to resemble MIT's 6.001 Adventure Game.
 *
 * This class is part of Hark. Hark is a simple, text based adventure game.
 *
 * "Room" represents one location in the scenery of the game.  It is
 * connected to at most four other rooms via exits.	 The exits are labelled
 * north, east, south, west.  For each direction, the room stores a reference
 * to the neighbouring room, or null if there is no exit in that direction.
 */

import java.util.ArrayList;

public class Room
{
    private String name;
    private Room northExit;
    private Room eastExit;
    private Room southExit;
    private Room westExit;
    private ArrayList<Person> people;
    private ArrayList<Thing> things;

    /**
     * Create a room described "description". Initially, it has no exits.
     * "description" is something like "a kitchen" or "an open court yard".
     */
    public Room(String description)
    {
        name = description;
        people = new ArrayList<Person>();
        things = new ArrayList<Thing>();
    }

    /**
     * Define the exits of this room.  Every direction either leads to
     * another room or is null (no exit there).
     */
    public void setExits(Room north, Room east, Room south, Room west)
    {
        northExit = north;
        eastExit = east;
        southExit = south;
        westExit = west;
    }

    /**
     * Return the description of the room (the one that was defined in the
     * constructor).
     */
    public String getName()
    {
        return name;
    }

    // returns a duplicate array list of people in the room, preserving encapsulation
    public ArrayList<Person> getPeople()
    {
        ArrayList<Person> duplicateList = new ArrayList<Person>();
        for (int i = 0; i < people.size(); i++)
        {
            Person temp = people.get(i);
            duplicateList.add(new Person(temp.getName(), temp.getType()));
        }
        return duplicateList;
    }
    
    // returns arraylist of all things in the room
    private ArrayList<Thing> getAllThings()
    {
        ArrayList<Thing> allThings = new ArrayList<Thing>();
        ArrayList<Thing> personsThings = new ArrayList<Thing>();
        for (int i = 0; i < people.size(); i++)
        {
            Person tempPerson = people.get(i);
            personsThings = tempPerson.getRealThings();
            for (int z = 0; z < personsThings.size(); z++)
            {
                allThings.add(personsThings.get(z));
            }
        }
        for (int x = 0; x < things.size(); x++)
        {
            allThings.add(things.get(x));
        }
        return allThings;
    }
    
    // returns arraylist of all things in the room
    private ArrayList<Person> getAllPeople()
    {
        ArrayList<Person> allPeople = new ArrayList<Person>();
        ArrayList<Person> peoplesPeople = new ArrayList<Person>();
        for (int i = 0; i < people.size(); i++)
        {
            Person tempPerson = people.get(i);
            peoplesPeople = tempPerson.getPeople();
            for (int z = 0; z < peoplesPeople.size(); z++)
            {
                allPeople.add(peoplesPeople.get(z));
            }
        }
        for (int x = 0; x < people.size(); x++)
        {
            allPeople.add(people.get(x));
        }
        return allPeople;
    }
    
    // return the person with the specified name, or null if the person cannot be found
    public Person getPerson(String personName)
    {
        for (int i = 0; i < getAllPeople().size(); i++)
        {
            Person temp = getAllPeople().get(i);
            if (personName.equals(temp.getName()))
            {
                return temp;
            }
        }
        return null;
    }
    
    // return the thing with the specified name, or null if the thing cannot be found
    public Thing getThing(String thingName)
    {
        for (int i = 0; i < getAllThings().size(); i++)
        {
            Thing temp = getAllThings().get(i);
            if (thingName.equals(temp.getName()))
            {
                return temp;
            }
        }
        return null;
    }
    
    /**
     * Return a long description of this room, on the form:
     *	   You are in the kitchen.
     *	   Exits: north west
     */
    public String longDescription()
    {
        return "You are in " + name + ".\n" + exitString() + "\n" + otherPeopleString() + "\n" + thingString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west ".
     */
    private String exitString()
    {
        String returnString = "Exits:";

        if (northExit != null)
            returnString = returnString + " North: " + northExit.getName() + ", ";
        if (eastExit != null)
            returnString = returnString + " East: " + eastExit.getName() + ", ";
        if (southExit != null)
            returnString = returnString + " South: " + southExit.getName() + ", ";
        if (westExit != null)
            returnString = returnString + " West: " + westExit.getName() + ", ";

        return returnString;
    }
    
    // returns string listing all people in the room
    private String otherPeopleString()
    {
        if (getAllPeople().size() == 1)
            return "No one is here.";

        String returnString = "People here:";

        for (int i = 0; i < getAllPeople().size(); i++)
        {
            Person person = (Person)getAllPeople().get(i);
            if (person.getType() != Person.HERO)
                returnString = returnString + " " + person.getName() + ",";
        }

        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     */
    public Room nextRoom(String direction)
    {
        if (direction.equals("north"))
            return northExit;
        else if (direction.equals("east"))
            return eastExit;
        else if (direction.equals("south"))
            return southExit;
        else if (direction.equals("west"))
            return westExit;
        else
            return null;
    }

    //adds the given person to the room
    public void add(Person person)
    {
        people.add(person);
    }
    
    // adds the given thing to the room
    public void add(Thing thing)
    {
        things.add(thing);
    }
    
    // returns string listing all things in room
    public String thingString()
    {
        if (getAllThings().size() == 0)
        {
            return "There's nothing here.";
        }
        String returnString = "Things here:";
        for (int i = 0; i < getAllThings().size(); i++)
        {
            Thing thing = (Thing)getAllThings().get(i);
            returnString = returnString + " " + thing.getName();
        }
        return returnString;
    }
    
    // returns a random thing in the room
    public Thing getRandomThing()
    {
        if (getAllThings().size() > 0)
        {
            int index = Game.random(getAllThings().size());
            return getAllThings().get(index);
        }
        else
        {
            return null;
        }
    }
    
    //precondition:	 person must be in the room
    //postcondition: person has been removed from the room
    public void remove(Person person)
    {
        boolean found = false;
        for (int i = 0; i < people.size() && !found; i++)
        {
            if (people.get(i) == person)
            {
                people.remove(i);
                found = true;
            }
        }

        if(!found) throw new IllegalArgumentException("Person not found:  " + person);
    }
    
    // precondition:  thing must be in the room
    // postcondition: thing has been removed from the room
    public void remove(Thing thing)
    {
        boolean found = false;
        for (int i = 0; i < things.size() && !found; i++)
        {
            if (things.get(i) == thing)
            {
                things.remove(i);
                found = true;
            }
        }

        if(!found) throw new IllegalArgumentException("Thing not found:  " + thing);
    }
    
    //precondition:	 room contains at least one person
    //postcondition: returns a random person in the room
    public Person getRandomPerson()
    {
        int index = Game.random(people.size());
        return (Person)people.get(index);
    }
}