/**
 * Class Game - the main class of the "Hark" game.
 * 091608
 *
 *	Michael Kolling's Zork code, modified to resemble MIT's 6.001 Adventure Game.
 *
 *	This class is the main class of the "Hark" application. Hark is a very
 *	simple, text based adventure game.	Users can walk around some scenery.
 *	That's all. It should really be extended to make it more interesting!
 *
 *	To play this game, create an instance of this class and call the "play"
 *	routine.
 *
 *	This main class creates and initialises all the others: it creates all
 *	rooms, creates the parser and starts the game.	It also evaluates the
 *	commands that the parser returns.
 */

import java.util.ArrayList;

public class Game
{
	private Parser parser;
	private Person hero;
	private ArrayList<Person> otherPeople;
	private ArrayList<Room> rooms;
	private ArrayList<Thing> things;

	public static void main(String[] args)
	{
		Game game = new Game();
		game.play();
	}

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game()
	{
		setup();
		parser = new Parser();
	}

	/**
	 * Create all the rooms, link their exits together, and populate them.
	 */
	private void setup()
	{

		// create the rooms
		Room shah = new Room("Shah_Hall");
		Room main = new Room("The_Main_Building");
		Room manzanita = new Room("Manzanita_Hall");
		Room edge = new Room("The_Edge");
		Room bookStore = new Room("The_book_store");
		Room library = new Room("The_library");
		Room gym = new Room("The_gym");
		Room dobbins = new Room("Dobbins_Hall");
		Room parking = new Room("The_parking_lot");
		Room field = new Room("Rosenthal_Field");
		Room nichols = new Room("Nichols_Hall");
		Room cave = new Room("a_dark_cave");
		Room roomForImprovement = new Room("Room_for_Improvement");
		Room mushroom = new Room("Mushroom");

		// initialise room exits (north, east, south, west)
		shah.setExits(null, main, mushroom, null);
		main.setExits(null, manzanita, library, shah);
		manzanita.setExits(null, edge, gym, main);
		edge.setExits(null, bookStore, dobbins, manzanita);
		bookStore.setExits(null, null, roomForImprovement, edge);
		library.setExits(main, gym, null, mushroom);
		gym.setExits(manzanita, null, field, library);
		dobbins.setExits(edge, roomForImprovement, nichols, null);
		parking.setExits(null, field, null, null);
		field.setExits(gym, nichols, null, parking);
		nichols.setExits(dobbins, cave, null, field);
		cave.setExits(roomForImprovement, null, null, nichols);
		roomForImprovement.setExits(bookStore, null, cave, dobbins);
		mushroom.setExits(shah, library, null, null);

		// put all the rooms in the array list
		rooms = new ArrayList<Room>();
		rooms.add(shah);
		rooms.add(main);
		rooms.add(manzanita);
		rooms.add(edge);
		rooms.add(bookStore);
		rooms.add(library);
		rooms.add(gym);
		rooms.add(dobbins);
		rooms.add(parking);
		rooms.add(field);
		rooms.add(nichols);
		rooms.add(cave);
		rooms.add(roomForImprovement);
		rooms.add(mushroom);

		// create the people
		hero = new Person("Rohit", Person.HERO);
		hero.changeRoom(getRandomRoom());

		Person mrP = new Person("MrPage", Person.ROBOT);
		mrP.changeRoom(getRandomRoom());

		Person mrSpenner = new Person("MrSpenner", Person.ROBOT);
		mrSpenner.changeRoom(getRandomRoom());

		Person pacman = new Person("PacMan", Person.ROBOT);
		pacman.changeRoom(getRandomRoom());
		
		Person einstein = new Person("AlbertEinstein", Person.ROBOT );
		einstein.changeRoom(nichols);

		Person georgeBush = new Person("GeorgeBush", Person.ROBOT);
		georgeBush.changeRoom(getRandomRoom());

		Person mrNikoloff = new Person("MrNikoloff", Person.ROBOT);
		mrNikoloff.changeRoom(main);
		
		Person drNelson = new Person("DrNelson", Person.ROBOT);
		drNelson.changeRoom(nichols);
		
		Person mrDaren = new Person("MrDaren", Person.MONSTER);
		mrDaren.changeRoom(manzanita);

		Person grendel = new Person("Grendel", Person.MONSTER);
		grendel.changeRoom(cave);

		Person schrodinger = new Person("ErwinSchrödinger", Person.ROBOT);
		schrodinger.changeRoom(getRandomRoom());
		
		Person bohr = new Person("NielsBohr", Person.ROBOT);
		bohr.changeRoom(getRandomRoom());
		
		Person homunculus = new Person("Homunculus", Person.ROBOT);
		homunculus.changeRoom(mushroom);
		
		Person troy = new Person("MrThiele", Person.ROBOT);
		troy.changeRoom(dobbins);
		
		Person julie = new Person("MsWheeler", Person.ROBOT);
		julie.changeRoom(shah);

		// put all the other people in the array list
		otherPeople = new ArrayList<Person>();
		otherPeople.add(mrP);
		otherPeople.add(mrSpenner);
		otherPeople.add(pacman);
		otherPeople.add(georgeBush);
		otherPeople.add(mrNikoloff);
		otherPeople.add(drNelson);
		otherPeople.add(grendel);
		otherPeople.add(schrodinger);
		otherPeople.add(einstein);
		otherPeople.add(bohr);
		otherPeople.add(homunculus);
		otherPeople.add(troy);
		otherPeople.add(julie);
		otherPeople.add(mrDaren);
		
		// create things
		Thing sat = new Thing("SAT", Thing.MOBILE);
		sat.changeOwner(roomForImprovement);
		
		Thing tritium = new Thing("Tritium", Thing.MOBILE);
		tritium.changeOwner(getRandomRoom());
		
		Thing lhc = new Thing("LargeHadronCollider", Thing.HEAVY);
		lhc.changeOwner(roomForImprovement);
		
		Thing sCat = new Thing("SchrödingersCat", Thing.MOBILE);
		sCat.changeOwner(schrodinger);
		
		Thing topaz = new Thing("Topaz", Thing.MOBILE);
		topaz.changeOwner(cave);
		
		Thing tgp = new Thing("TheGodParticle", Thing.MOBILE);
		tgp.changeOwner(drNelson);
		
		Thing dreamliner = new Thing("Boeing787Dreamliner", Thing.HEAVY);
		dreamliner.changeOwner(parking);
		
		Thing fungus = new Thing("Fungus", Thing.MOBILE);
		fungus.changeOwner(mushroom);
		
		Thing atom = new Thing("Atom", Thing.MOBILE);
		atom.changeOwner(bohr);
		
		Thing iridium = new Thing("Iridium", Thing.MOBILE);
		iridium.changeOwner(getRandomRoom());
		
		Thing intelligence = new Thing("Intelligence", Thing.MOBILE);
		intelligence.changeOwner(troy);
		
		// put all the things in the array list
		things = new ArrayList<Thing>();
		things.add(sat);
		things.add(tritium);
		things.add(lhc);
		things.add(sCat);
		things.add(topaz);
		things.add(tgp);
		things.add(dreamliner);
		things.add(fungus);
		things.add(atom);
		things.add(iridium);
		things.add(intelligence);
	}

	/**
	 *	Main play routine.	Loops until end of play.
	 */
	public void play()
	{
		printWelcome();

		// Enter the main command loop.	 Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (! finished)
		{
			Command command = parser.getCommand();
			finished = processCommand(command);
		}
		System.out.println("Thank you for playing.	Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome()
	{
		System.out.println();
		System.out.println("Welcome to Quark!");
		System.out.println("Quark is a new, incredibly engaging physics game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		System.out.println(hero.getRoom().longDescription());
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * If this command ends the game, true is returned, otherwise false is
	 * returned.
	 */
	private boolean processCommand(Command command)
	{
		if(command.isUnknown())
		{
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help"))
			printHelp();
		else if (commandWord.equals("go"))
			goRoom(command);
		else if (commandWord.equals("suicide"))
			suicide();
		else if (commandWord.equals("teleport"))
			teleport(command);
		else if (commandWord.equals("eat"))
			eat(command);
		else if (commandWord.equals("kill"))
			kill(command);
		else if (commandWord.equals("give"))
			give(command);
		else if (commandWord.equals("take"))
			take(command);
		else if (commandWord.equals("quit"))
		{
			if(command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true;  // signal that we want to quit
		}
		return false;
	}

	// implementations of user commands:

	/**
	 * Print out some help information.
	 * Here we print some stupid, cryptic message and a list of the
	 * command words.
	 */
	private void printHelp()
	{
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around The Harker Upper School.");
		System.out.println();
		System.out.println("Your command words are:");
		parser.showCommands();
	}
	
	/**
	 * preconditions: none
	 * postconditions: Hero must have died
	 */
	private void suicide()
	{
		hero.heroSuicide();
	}
	
	/**
	 * preconditions: none
	 * postconditions: Hero must have died
	 */
	private void kill(Command command)
	{
		if (!command.hasSecondWord())
		{
			System.out.println("Kill who?");
		}
		else if (hero.getRoom().getPerson(command.getSecondWord()) == null)
		{
			System.out.println(command.getSecondWord() + " is not here!");
		}
		else
		{
			hero.kill(hero.getRoom().getPerson(command.getSecondWord()));
		}
	}
	
	/**
	 * preconditions: none
	 * postconditions: Hero must have died
	 */
	private void eat(Command command)
	{
		if (!command.hasSecondWord())
		{
			System.out.println("Eat what?");
		}
		else if (hero.getRoom().getPerson(command.getSecondWord()) == null)
		{
			System.out.println(command.getSecondWord() + " is not here!");
		}
		else
		{
			hero.eatPerson(hero.getRoom().getPerson(command.getSecondWord()));
		}
	}
	
	/**
	 * preconditions: none
	 * postconditions: person must have teleported to random room
	 */
	private void teleport(Command command)
	{
		if(!command.hasSecondWord())
		{
			System.out.println("Teleport where?"); // if there is no second word, we don't know where to teleport to...
			System.out.println("To teleport to a random room, type: teleport anywhere");
		}
		else if ((command.getSecondWord()).equals("anywhere")) // teleport to a random room
		{
			hero.changeRoom(getRandomRoom());
			System.out.println(hero.getRoom().longDescription());
		}
		else // teleport to a specific room
		{
			boolean success = false;		
			for (int i = 0; i < rooms.size(); i++)
			{
				Room temp = rooms.get(i);
				if ((command.getSecondWord()).equals(temp.getName()))
				{
					hero.changeRoom(rooms.get(i));
					System.out.println(hero.getRoom().longDescription());
					success = true;
				}
			}
			if (!success) // the room to teleport too does not exist
			{
				System.out.println("There is no room called " + command.getSecondWord());
			}
		}
	}
	
	/**
	 * Person gives thing or person to a new owner
	 */
	private void give(Command command)
	{
		if(!command.hasSecondWord())
		{
			System.out.println("Give what?"); // if there is no second word, we don't know what to give...
		}
		else if (!command.hasThirdWord())
		{
			System.out.println("Give " + command.getSecondWord() + " to who?"); // if there is no third word, we don't know who to give the person/thing to
		}
		else if ((hero.getRoom().getThing(command.getSecondWord())) != null) // runs if person is giving a thing
		{
			Thing ownedThing = hero.getRoom().getThing(command.getSecondWord());
			if ((hero.getRoom().getPerson(command.getThirdWord())) != null) // determines if person to give thing to exists
			{
				hero.give(ownedThing, hero.getRoom().getPerson(command.getThirdWord())); // gives ownedThing to person
			}
			else
			{
				System.out.println(command.getThirdWord() + " does not exist!");
			}
		}
		else
		{
			if ((hero.getRoom().getPerson(command.getSecondWord())) != null) // runs if person is giving a person
			{
				Person ownedPerson = hero.getRoom().getPerson(command.getSecondWord());
				if ((hero.getRoom().getPerson(command.getThirdWord())) != null) // determines if person to give person to exists
				{
					hero.give(ownedPerson, hero.getRoom().getPerson(command.getThirdWord())); // gives ownedPerson to person
				}
				else
				{
					System.out.println(command.getThirdWord() + " does not exist!");
				}
			}
			else
			{
				System.out.println(command.getSecondWord() + " does not exist!");
			}
		}
	}

	/**
	 * Try to go to one direction. If there is an exit, enter the new
	 * room and make all other people act, otherwise print an error message.
	 */
	private void goRoom(Command command)
	{
		if(!command.hasSecondWord())
		{
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
		}
		else
		{
			String direction = command.getSecondWord();
		
			// Try to leave current room.
			Room nextRoom = hero.getRoom().nextRoom(direction);
		
			if (nextRoom == null)
				System.out.println("There is no door!");
			else
			{
				hero.changeRoom(nextRoom);
				moveOtherPeople();
				System.out.println(nextRoom.longDescription());
			}
		}
	}
		
	private void take(Command command)
	{
		if(!command.hasSecondWord())
		{
			// if there is no second word, we don't know what to take
			System.out.println("Take what?");
		}
		else if ((hero.getRoom().getThing(command.getSecondWord())) == null)
		{
			if ((hero.getRoom().getPerson(command.getSecondWord())) == null)
			{
				System.out.println(command.getSecondWord() + " is not here!"); // person takes nothing
			}
			else
			{
				hero.take(hero.getRoom().getPerson(command.getSecondWord())); // person takes a person
			}
		}
		else
		{
			hero.take(hero.getRoom().getThing(command.getSecondWord())); // person takes a thing
		}
	}

	//returns a random room in the game
	private Room getRandomRoom()
	{
		int index = Game.random(rooms.size());
		Room room = (Room)rooms.get(index);
		return room;
	}

	//cause other people to act (which typically means moving)
	private void moveOtherPeople()
	{
		for (int i = 0; i < otherPeople.size(); i++)
		{
			Person person = (Person)otherPeople.get(i);
			person.act();
		}
	}

	//returns a random integer from 0 to n-1
	public static int random(int n)
	{
		return (int)(Math.random() * n);
	}
}