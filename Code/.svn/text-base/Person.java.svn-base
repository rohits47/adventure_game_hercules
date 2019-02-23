//A person or character in our game.  There will be at least three kinds of people:
//a hero (you), monsters (who eat other people), and robots (who walk around and do their own thing).

import java.util.ArrayList;

public class Person
{
	//types of people
	public static final int HERO	= 0;
	public static final int MONSTER = 1;
	public static final int ROBOT	= 2;

	//instance variables
	private String name;
	private int type;
	private Room room;
	private Person owner;
	private int health;
	private ArrayList<Thing> things;
	private ArrayList<Person> people;

	//creates a new person with given name and of given type
	public Person(String aName, int aType)
	{
		name = aName;
		type = aType;
		health = 12;
		owner = null;
		things = new ArrayList<Thing>();
		people = new ArrayList<Person>();
	}

	//returns this person's name
	public String getName()
	{
		return name;
	}

	//returns the type of this person
	public int getType()
	{
		return type;
	}

	//returns the room the person is in
	public Room getRoom()
	{
		return room;
	}
	
	// sets room to room
	public void setRoom(Room room)
	{
		this.room = room;
	}
	
	// returns the person owner of the person
	public Person getOwner()
	{
		return owner;
	}
	
	// returns the people that the person owns
	public ArrayList<Person> getPeople()
	{
		ArrayList<Person> realPeople = new ArrayList<Person>();
		for (int i = 0; i < people.size(); i++)
		{
			Person temp = people.get(i);
			realPeople.add(temp);
		}
		return realPeople;
	}
	
	// hero says a single string of all owned peoples names
	public void getPeoplesNames()
	{
		if (people.size() > 0)
		{
			String str = new String ("");
			for (int i = 0; i < people.size(); i++)
			{
				Person temp = people.get(i);
				str = str + temp.getName() + " ";
			}
			say(str);
		}
	}
	
	// adds the given thing to the room
	public void add(Thing thing)
	{
		things.add(thing);
	}
	
	// adds the given person to the room
	public void add(Person person)
	{
		people.add(person);
	}

	//moves the person to the given room
	public void changeRoom(Room nextRoom)
	{
		//remove the person from the old room (if already in one)
		if (room != null && this !=null)
			room.remove(this);

		//change my room to be the next one
		room = nextRoom;

		//add myself to the new room
		room.add(this);
	}

	//moves the person to the given room
	public void changeOwner(Person person)
	{
		//remove the person from the old room (if already in one)
		if (room != null)
		{
			room.remove(this);
			//change my room to null, I am not in a room
			room = null;
		}
		if (owner != null)
		{
			owner.remove(this);
		}
		
		// change owner instance field
		owner = person;
		
		//add myself to the new owner's list of owned people
		person.add(this);
	}

	//precondition:	 person is not the hero
	//postcondition: person has acted, by moving, eating people, etc.
	public void act()
	{	
		// dead people can't act
		if (room != null)
		{
			move();
			if (type == MONSTER)
			{
				eatSomeone();
			}
			if (type == ROBOT)
			{
				takeSomething();
			}
		}
	}
	
	// returns a random object from the room and gives it to the robot calling this method
	public void takeSomething()
	{
		Thing temp = room.getRandomThing();
		if (temp != null)
		{
			take(temp);
		}
	}
	
	// returns a random person from the room and gives it to the person calling this method
	public void takeSomeone()
	{
		Person temp = room.getRandomPerson();
		if (temp != null)
		{
			take(temp);
		}
	}
	
	// returns a list of all the things that the person is carrying while preserving encapsulation
	public ArrayList<Thing> getThings()
	{
		ArrayList<Thing> duplicateList = new ArrayList<Thing>();
		for (int i = 0; i < things.size(); i++)
		{
			Thing temp = things.get(i);
			duplicateList.add(new Thing(temp.getName(), temp.getType()));
		}
		return duplicateList;
	}
	
	// returns a list of references to the things that the person is carrying 
	public ArrayList<Thing> getRealThings()
	{
		ArrayList<Thing> realThings = new ArrayList<Thing>();
		for (int i = 0; i < things.size(); i++)
		{
			Thing temp = things.get(i);
			realThings.add(temp);
		}
		return realThings;
	}
	
	// return the thing with the specified name, or null if the thing cannot be found
	public Thing getThing(String thingName)
	{
		for (int i = 0; i < things.size(); i++)
		{
			Thing temp = things.get(i);
			if (thingName.equals(temp.getName()))
			{
				return temp;
			}
		}
		return null;
	}
	
	// return the person with the specified name, or null if the person cannot be found
	public Person getPerson(String personName)
	{
		for (int i = 0; i < people.size(); i++)
		{
			Person temp = people.get(i);
			if (personName.equals(temp.getName()))
			{
				return temp;
			}
		}
		return null;
	}
	
	// person attempts to take thing and returns appropriate message depending on success
	public void take(Thing thing)
	{
		if (thing.getType() == Thing.HEAVY)
		{
			say("I try but cannot take " + thing.getName());
		}
		else
		{
			if ((thing.getOwner()) instanceof Room)
			{
				say("I take " + thing.getName() + " from " + ((Room)(thing.getOwner())).getName());
				thing.changeOwner(this);
			}
			else
			{
				if (((Person)(thing.getOwner())) == this)
				{
					System.out.println(getName() + " already has that!");
				}
				else
				{
					say("I take " + thing.getName() + " from " + ((Person)(thing.getOwner())).getName());
					((Person)(thing.getOwner())).say("I lose " + thing.getName());
					((Person)(thing.getOwner())).say("I want " + thing.getName() + " back!");
					System.out.println(((Person)(thing.getOwner())).getName() + " attacks " + name);
					suffer(1);
					System.out.println(name + " loses 1 health.");
					thing.changeOwner(this);
				}
			}
		}
	}
	
	// person attempts to take thing and returns appropriate message depending on success
	public void take(Person person)
	{
		if (person.getType() == Person.MONSTER)
		{
			say("I cannot take a monster!");
			System.out.println(name + " tries to run away, but " + person.getName() + " grabs " + name + " and swallows " + name + " in one gulp.");
			person.say("BUUUUUUURRRP");
			room.remove(this);
			if (things.size() > 0)
			{
				for (int i = 0; i < things.size(); i++)
				{
					Thing temp = things.get(i);
					temp.changeOwner(room);
				}
			}
			if (type == HERO)
			{
				System.out.println("Thank you for playing.	Good bye.");
				System.exit(0);
			}
		}
		else if (person.getType() == Person.HERO)
		{
			say("The Hero is too awesome for me too take! I cannot take him!");
		}
		else // person.getType() == Person.ROBOT
		{
			if (getPerson(person.getName()) == null) // checks to see if person is already owned by person
			{
				if (person.getOwner() != null) // checks to see if person has a personowner already
				{
					say("I take " + person.getName() + " from " + person.getOwner().getName());
					person.changeOwner(this);
					System.out.println("At " + room.getName() + " " + person.getName() + " says - " + "WHY AGAIN‽‽ I'm now being held by " + name + ". Will this incarceration never end‽");
					say("MWAAHAHAHAHAHAHAHAHA!!!!!! I have custody of " + person.getName() + "!");
				}
				else // person is in a room, not owned by any person
				{
					say("I take " + person.getName() + " from " + person.getRoom().getName());
					person.changeOwner(this);
					System.out.println("At " + room.getName() + " " + person.getName() + " says - " + "EEEPPP! HELP! I'm being held by " + name);
					say("MWAAHAHAHAHAHAHAHAHA!!!!!! I have custody of " + person.getName() + "!");
				}
			}
			else
			{
				System.out.println(getName() + " already has that!");
			}
		}
	}
	
	// person attempts to give thing to person and returns appropriate message depending on success
	public void give(Thing thing, Person gPerson)
	{
		if (gPerson != this)
		{
			thing.changeOwner(gPerson);
			say("I give " + thing.getName() + " to " + gPerson.getName());
		}
		else
		{
			System.out.println("You cannot give " + thing.getName() + " to yourself!");
		}
	}

	// person attempts to give person to person and returns appropriate message depending on success
	public void give(Person oPerson, Person gPerson)
	{
		if (gPerson != this)
		{
			oPerson.changeOwner(gPerson);
			say("I give " + oPerson.getName() + " to " + gPerson.getName());
			System.out.println("At " + room.getName() + " " + oPerson.getName() + " says - " + "NOOOOOOOO!! The binds of ownership continue to shackle me!!!! I WISH TO BE FREEEEEEE!!!!!!!!");
			System.out.println(oPerson.getOwner().getName());
		}
		else
		{
			System.out.println("You cannot give " + oPerson.getName() + " to yourself!");
		}
	}

	//causes the person to move to a random neighbor (or stay still)
	private void move()
	{
		int num = Game.random(4);
		String direction;
		if (num == 0)
			direction = "north";
		else if (num == 1)
			direction = "east";
		else if (num == 2)
			direction = "south";
		else //num == 3
			direction = "west";
		Room nextRoom = room.nextRoom(direction);
		if (nextRoom != null)
			changeRoom(nextRoom);
		ArrayList<Person> mPeople = new ArrayList<Person>();
		mPeople = room.getPeople();
		if (mPeople.size() > 1)
		{
			String str = new String ("Hi ");
			for (int i = 0; i < mPeople.size(); i++)
			{
				Person temp = mPeople.get(i);
				if (temp.getName() != getName())
				{
					str = str + " " + temp.getName();
				}
			}
			say(str);
		}
	}
	
	// precondition:  person must be owned by person
	// postcondition: person has been removed from person
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
	
	// precondition:  thing must be owned by person
	// postcondition: thing has been removed from person
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

	//sometimes causes this person to eat a random person in the room
	private void eatSomeone()
	{
		Person victim = room.getRandomPerson();
		if (victim != this) //don't eat yourself
		{
			say("Grrr! I eat tasty " + victim.getName() + "!");
			victim.suffer(Game.random(3) + 2);
		}
	}

	//causes the person to say the given message
	public void say(String message)
	{
		System.out.println("At " + room.getName() + " " + name + " says - " + message);
	}

	//causes this person to suffer the given amount of damage
	public void suffer(int hits)
	{
		String str = " hit";
		if (hits > 1)
		{
			str = str + "s are";
		}
		if (hits == 1)
		{
			str = str + " is";
		}
		say("Ouch! " + hits + str + " more than I want!");
		health = health - hits;
		if (health <= 0)
			die();
	}

	//causes this person to die
	public void die()
	{
		say("SHREEEEK! I, uh, suddenly feel very faint...");

		System.out.println("An earth-shattering, soul-piercing, blood-curdling, heart wrending scream is heard...");
		if (things.size() > 0)
		{
			for (int i = 0; i < things.size(); i++)
			{
				Thing temp = things.get(i);
				temp.changeOwner(room);
			}
		}
		room.remove(this);

		room = null;

		if (type == HERO)
		{
			System.out.println("Thank you for playing.	Good bye.");
			System.exit(0);
		}
	}
	
	// causes the person to suicide
	public void heroSuicide()
	{
		say("I have decided to leave this world and never return.");
		System.out.println(name + " plunges a dagger into his heart, convulses, and lies motionless on the floor.");
		if (things.size() > 0)
		{
			for (int i = 0; i < things.size(); i++)
			{
				Thing temp = things.get(i);
				temp.changeOwner(room);
			}
		}
		room.remove(this);

		room = null;

		System.out.println("Thank you for playing.	Good bye.");
		System.exit(0);
	}
	
	// called when the victim is eaten by HERO
	public void kill(Person victim)
	{
		if (victim.getThings().size() > 0)
		{
			for (int i = 0; i < victim.getThings().size(); i++)
			{
				Thing temp = victim.getThings().get(i);
				Thing real = new Thing(temp.getName(), temp.getType());
				real.changeOwner(room);
			}
		}
		say("I attempt to kill " + victim.getName() + ".");
		if (victim.getType() == MONSTER)
		{
			victim.say("HOW DARE YOU TRY TO KILL A MONSTER‽");
			System.out.println("A violent battle ensues, with both " + victim.getName() + " and " + name + " attempting to kill each other.");
			System.out.println(name + " manages to slay " + victim.getName());
			room.remove(victim);
			victim.setRoom(null);
			health = health/2;
			System.out.println(name + "'s health has been halved during the fight. " + name + "'s health is now " + health);
		}
		else if (victim.getType() != HERO)
		{
			say("I utterly and completely destroy " + victim.getName());
			System.out.println(victim.getName() + " has been killed by " + name);
			room.remove(victim);
			victim.setRoom(null);
		}
		else
		{
			System.out.println("You can't kill the hero that easily!");
		}
	}
	
	// called when the victim is eaten by HERO
	public void eatPerson(Person victim)
	{
		if (victim.getThings().size() > 0)
		{
			for (int i = 0; i < victim.getThings().size(); i++)
			{
				Thing temp = victim.getThings().get(i);
				Thing real = new Thing(temp.getName(), temp.getType());
				real.changeOwner(room);
			}
		}
		say("I attempt to eat " + victim.getName() + ".");
		if (victim.getType() != MONSTER)
		{
			victim.say("Hey! I'm not edible!");
			System.out.println(victim.getName() + " makes an angry face at " + name + ".");
			say("I eat " + victim.getName() + " regardless of the fact that " + victim.getName() + " is inedible.");
			System.out.println(victim.getName() + " has been succesfully eaten by " + name + ".");
			health += 3;
			System.out.println(name + " has increased health by 3. " + name + "'s health is now " + health);
		}
		else
		{
			say("EEEEK! I can't eat a monster!");
			System.out.println(name + " attempts to abort consumption of " + victim.getName() + ", but " + victim.getName() + " grabs " + name + " and eats " + name);
			System.out.println(victim.getName() + " fails to digest " + name + " and both " + victim.getName() + " and " + name + " die.");
			if (things.size() > 0)
			{
				for (int i = 0; i < things.size(); i++)
				{
					Thing temp = things.get(i);
					temp.changeOwner(room);
				}
			}
			room.remove(this);

			room = null;

			System.out.println("Thank you for playing.	Good bye.");
			System.exit(0);
		}
		room.remove(victim);
		victim.setRoom(null);
	}
}