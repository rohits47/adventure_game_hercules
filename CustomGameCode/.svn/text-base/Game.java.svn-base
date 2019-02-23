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
		Room snakeHouse = new Room("MedusasLair");
		Room lair = new Room("HydrasLair");
		Room den = new Room("LionsDen");
		Room olympus = new Room("MountOlympus");
		Room underworld = new Room("Underworld");
		Room village = new Room("Village");
		Room labyrinth = new Room("Labyrinth");
		Room cretes = new Room("Cretes");
		Room doghouse = new Room("DogHouse");

		// initialise room exits (north, east, south, west)
		snakeHouse.setExits(null, null, lair, null);
		lair.setExits(snakeHouse, null, null, village);
		den.setExits(null, null, village, null);
		olympus.setExits(null, null, null, null); // only accesible through finishing tasks
		underworld.setExits(null, null, null, null);
		village.setExits(den, lair, labyrinth, null);
		labyrinth.setExits(village, null, null, cretes);
		cretes.setExits(doghouse, labyrinth, null, null);
		doghouse.setExits(null, null, cretes, null);
		
		// put all the rooms in the array list
		rooms = new ArrayList<Room>();
		rooms.add(snakeHouse);
		rooms.add(lair);
		rooms.add(den);
		rooms.add(olympus);
		rooms.add(underworld);
		rooms.add(village);
		rooms.add(cretes);
		rooms.add(doghouse);

		// create the people
		hero = new Person("Hercules", Person.HERO);
		hero.changeRoom(village);

		Person poseidon = new Person("Poseidon", Person.GOD);
		poseidon.changeRoom(olympus);
		
		Person zeus = new Person("Zeus", Person.GOD );
		zeus.changeRoom(olympus);

		Person hydra = new Person("LerneanHydra", Person.ROBOT);
		hydra.changeRoom(lair);

		Person minos = new Person("KingMinos", Person.ROBOT);
		minos.changeRoom(cretes);
		
		Person cereberus = new Person("Cereberus", Person.ROBOT);
		cereberus.changeRoom(doghouse);
		
		Person medusa = new Person("Medusa", Person.MONSTER);
		medusa.changeRoom(snakeHouse);

		Person lion = new Person("NemeanLion", Person.MONSTER);
		lion.changeRoom(den);

		Person minotaur = new Person("Minotaur", Person.MONSTER);
		minotaur.changeRoom(labyrinth);
		
		Person cyclops = new Person("Cyclops", Person.ROBOT);
		cyclops.changeRoom(getRandomRoom());
		
		Person harpie = new Person("Harpie", Person.ROBOT);
		harpie.changeRoom(getRandomRoom());
		
		Person sphinx = new Person("Sphinx", Person.ROBOT);
		sphinx.changeRoom(getRandomRoom());
		
		Person hades = new Person("Hades", Person.GOD);
		hades.changeRoom(underworld);

		// put all the other people in the array list
		otherPeople = new ArrayList<Person>();
		otherPeople.add(poseidon);
		otherPeople.add(hydra);
		otherPeople.add(minos);
		otherPeople.add(cereberus);
		otherPeople.add(lion);
		otherPeople.add(minotaur);
		otherPeople.add(zeus);
		otherPeople.add(cyclops);
		otherPeople.add(harpie);
		otherPeople.add(sphinx);
		otherPeople.add(hades);
		otherPeople.add(medusa);
		
		// create things
		Thing trident = new Thing("Trident", Thing.MOBILE);
		trident.changeOwner(poseidon);
		
		Thing elixir = new Thing("Elixir", Thing.MOBILE);
		elixir.changeOwner(getRandomRoom());
		
		Thing boulder = new Thing("Boulder", Thing.HEAVY);
		boulder.changeOwner(village);
		
		Thing horns = new Thing("GoldenHorns", Thing.MOBILE);
		horns.changeOwner(minotaur);
		
		Thing head = new Thing("MedusasHead", Thing.MOBILE);
		head.changeOwner(medusa);
		
		Thing club = new Thing("Club", Thing.MOBILE);
		club.changeOwner(cyclops);
		
		Thing sword = new Thing("Sword", Thing.MOBILE);
		sword.changeOwner(hero);
		
		Thing tonic = new Thing("TonicOfImmortality", Thing.MOBILE);
		tonic.changeOwner(zeus);
		
		Thing hide = new Thing("NemeanLionHide", Thing.MOBILE);
		hide.changeOwner(lion);
		
		Thing bolt = new Thing("LightningBolt", Thing.MOBILE);
		bolt.changeOwner(hydra);
		
		Thing intelligence = new Thing("Intelligence", Thing.MOBILE);
		intelligence.changeOwner(sphinx);
		
		// put all the things in the array list
		things = new ArrayList<Thing>();
		things.add(trident);
		things.add(elixir);
		things.add(boulder);
		things.add(horns);
		things.add(head);
		things.add(club);
		things.add(sword);
		things.add(tonic);
		things.add(hide);
		things.add(bolt);
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
		System.out.println("Welcome to Ancient Greece!");
		System.out.println("You are Hercules. You must join your father Zeus in Mount Olympus, but first, you must complete certain tasks.");
		System.out.println("Your first task is to find and kill the NemeanLion, take its hide, and give it to Hades.");
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
			printHelp(command);
		else if (commandWord.equals("go"))
			goRoom(command);
		else if (commandWord.equals("suicide"))
		{
			suicide();
			System.out.println(hero.getRoom().longDescription());
		}
		else if (commandWord.equals("fly"))
			fly(command);
		else if (commandWord.equals("eat"))
		{
			eat(command);
			System.out.println(hero.getRoom().longDescription());
		}
		else if (commandWord.equals("kill"))
		{
			kill(command);
			System.out.println(hero.getRoom().longDescription());
		}
		else if (commandWord.equals("give"))
		{
			give(command);
			if ((command.getSecondWord()).equals("Hades") || (command.getFourthWord()).equals("Hades"))
			{
				if ((command.getSecondWord()).equals("LightningBolt") || (command.getThirdWord()).equals("LightningBolt")) // determines if hero has completed all tasks and moves him to olympus
				{
					for (int i = 0; i < rooms.size(); i++)
					{
						Room temp = rooms.get(i);
						if ((temp.getName()).equals("MountOlympus"))
						{
							hero.changeRoom(rooms.get(i));
						}
					}
				}
				else if ((command.getSecondWord()).equals("MedusasHead") || (command.getThirdWord()).equals("MedusasHead"))
				{
					for (int i = 0; i < rooms.size(); i++)
					{
						Room temp = rooms.get(i);
						if ((temp.getName()).equals("Village")) // resets hero to starting place for next task
						{
							hero.changeRoom(rooms.get(i));
						}
					}
				}
				else if ((command.getSecondWord()).equals("GoldenHorns") || (command.getThirdWord()).equals("GoldenHorns"))
				{
					for (int i = 0; i < rooms.size(); i++)
					{
						Room temp = rooms.get(i);
						if ((temp.getName()).equals("Village"))// resets hero to starting place for next task
						{
							hero.changeRoom(rooms.get(i));
						}
					}
				}
				else if ((command.getSecondWord()).equals("NemeanLionHide") || (command.getThirdWord()).equals("NemeanLionHide"))
				{
					for (int i = 0; i < rooms.size(); i++)
					{
						Room temp = rooms.get(i);
						if ((temp.getName()).equals("Village"))// resets hero to starting place for next task
						{
							hero.changeRoom(rooms.get(i));
						}
					}
				}
				else if ((command.getSecondWord()).equals("Cereberus") || (command.getThirdWord()).equals("Cereberus"))
				{
					for (int i = 0; i < rooms.size(); i++)
					{
						Room temp = rooms.get(i);
						if ((temp.getName()).equals("Village"))// resets hero to starting place for next task
						{
							hero.changeRoom(rooms.get(i));
						}
					}
				}
			}
			System.out.println(hero.getRoom().longDescription());
		}
		else if (commandWord.equals("take"))
		{
			take(command);
			System.out.println(hero.getRoom().longDescription());
		}
		else if (commandWord.equals("quit"))
		{
			if(command.hasSecondWord())
				System.out.println("Quit what?");
			else
				return true;  // signal that we want to quit
		}
		if (hero.getType() == Person.GOD)
		{
			System.out.println("Congratulations! You have won!");
			return true;
		}
		return false;
	}

	// implementations of user commands:

	/**
	 * Print out some help information.
	 * Here we print a helpful message and a list of the
	 * command words.
	 */
	private void printHelp(Command command)
	{
		if (command.hasSecondWord())
		{
			if ((command.getSecondWord()).equals("elixir")) // tells player the in-built cheat to winning
			{
				System.out.println("Fine, fine, you want to know the cheap way to win the game? Find and take the elixir then commit suicide. Conratulations. I hope you feel happy that you bested the game. -.-");
				System.out.println();
			}
		}
		System.out.println("You are Hercules. Your ultimate goal is to get to Mount Olympus.");
		System.out.println();
		System.out.println("Here are the things you can do:");
		parser.showCommands();
	}
	
	/**
	 * preconditions: none
	 * postconditions: Hero must have died
	 */
	private void suicide()
	{
		hero.heroSuicide();
		if (hero.getType() == Person.GOD) // checks is cheat condition has been satisfied
		{
			for (int i = 0; i < rooms.size(); i++)
			{
				Room temp = rooms.get(i);
				if ((temp.getName()).equals("MountOlympus"))
				{
					hero.changeRoom(rooms.get(i));
				}
			}
		}
/*		else
		{
			for (int i = 0; i < rooms.size(); i++)
			{
				Room temp = rooms.get(i);
				if ((temp.getName()).equals("Underworld")) // when you die you go to the underworld, makes sense no?
				{
					hero.changeRoom(rooms.get(i));
				}
			}
		}
*/	}
	
	/**
	 * preconditions: none
	 * postconditions: if hero has sword, target must have died
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
	 * postconditions: if target was not god or monster, target must have died
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
	 * postconditions: person must have flown to room
	 */
	private void fly(Command command)
	{
		if(!command.hasSecondWord())
		{
			System.out.println("fly where?"); // if there is no second word, we don't know where to fly to...
		}
		else if ((command.getSecondWord()).equals("MountOlympus")) // prevents a specific cheat
		{
			System.out.println("No cheating! Yeah, that's right, I anticipated people like you. NOT SO SMART NOW HUH? Besides, there are other ways of getting to the finish line without truly beating the game (hint: elixir)");
		}
		else if ((command.getSecondWord()).equals("anywhere")) // fly to a random room
		{
			hero.changeRoom(getRandomRoom());
			System.out.println(hero.getRoom().longDescription());
		}
		else // fly to a specific room
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
			if (!success) // the room to fly too does not exist
			{
				System.out.println("There is no place called " + command.getSecondWord());
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
			System.out.println("Give what?"); // if there is no second word, we don't know who to give to...
		}
		else if (!command.hasThirdWord())
		{
			System.out.println("Give " + command.getSecondWord() + " to who?"); // if there is no third word, we don't know what to give
		}
		else if ((command.getThirdWord()).equals("to")) // if the third word is "to", then the "give blank to blank" logic still works
		{
			if (!command.hasFourthWord())
			{
				System.out.println("Give " + command.getSecondWord() + " to who?"); // if there is no third word, we don't know who to give the person/thing to
			}
			else if ((hero.getRoom().getThing(command.getSecondWord())) != null) // runs if person is giving a thing
			{
				Thing ownedThing = hero.getRoom().getThing(command.getSecondWord());
				if ((hero.getRoom().getPerson(command.getFourthWord())) != null) // determines if person to give thing to exists
				{
					hero.give(ownedThing, hero.getRoom().getPerson(command.getFourthWord())); // gives ownedThing to person
				}
				else
				{
					System.out.println(command.getFourthWord() + " does not exist!");
				}
			}
			else
			{
				if ((hero.getRoom().getPerson(command.getSecondWord())) != null) // runs if person is giving a person
				{
					Person ownedPerson = hero.getRoom().getPerson(command.getSecondWord());
					if ((hero.getRoom().getPerson(command.getFourthWord())) != null) // determines if person to give person to exists
					{
						hero.give(ownedPerson, hero.getRoom().getPerson(command.getFourthWord())); // gives ownedPerson to person
					}
					else
					{
						System.out.println(command.getFourthWord() + " does not exist!");
					}
				}
				else
				{
					System.out.println(command.getSecondWord() + " does not exist!");
				}
			}
		}
		else if ((hero.getRoom().getPerson(command.getSecondWord())) != null) // determines if person to give thing to exists
		{
			if ((hero.getRoom().getThing(command.getThirdWord())) != null) // runs if person is giving a thing
			{
				Thing ownedThing = hero.getRoom().getThing(command.getThirdWord());
				hero.give(ownedThing, hero.getRoom().getPerson(command.getSecondWord())); // gives ownedThing to person
			}
			else
			{
				if ((hero.getRoom().getPerson(command.getThirdWord())) != null) // runs if person is giving a person
				{
					Person ownedPerson = hero.getRoom().getPerson(command.getThirdWord());
					hero.give(ownedPerson, hero.getRoom().getPerson(command.getSecondWord())); // gives ownedPerson to person
				}
				else
				{
					System.out.println(command.getThirdWord() + " does not exist!");
				}
			}
		}
		else
		{
			System.out.println(command.getSecondWord() + " does not exist!"); // person to give thing to does not exist
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
				System.out.println("There is no pathway!");
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
		while ((room.getName()).equals("MountOlympus")) // stops random object and people from being in olympus, where only gods are allowed
		{
			index = Game.random(rooms.size());
			room = (Room)rooms.get(index);
		} // if loop gets past here, room is not olympus
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