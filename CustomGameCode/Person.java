
//A person or character in our game.  There will be at least three kinds of people:
//a hero (you), monsters (who eat other people), and robots (who walk around and do their own thing).

import java.util.ArrayList;

public class Person {
    // types of people
    public static final int HERO = 0;
    public static final int MONSTER = 1;
    public static final int ROBOT = 2;
    public static final int GOD = 3;

    // instance variables
    private String name;
    private int type;
    private Room room;
    private Person owner;
    private int health;
    private ArrayList<Thing> things;
    private ArrayList<Person> people;

    // creates a new person with given name and of given type
    public Person(String aName, int aType) {
        name = aName;
        type = aType;
        health = 12;
        owner = null;
        things = new ArrayList<Thing>();
        people = new ArrayList<Person>();
    }

    // returns this person's name
    public String getName() {
        return name;
    }

    // returns the type of this person
    public int getType() {
        return type;
    }

    // returns the health the person has
    public int getHealth() {
        return health;
    }

    // returns the room the person is in
    public Room getRoom() {
        return room;
    }

    // sets room to room
    public void setRoom(Room room) {
        this.room = room;
    }

    // changes the type
    public void setType(int aType) {
        if (aType >= 0 && aType <= 3) {
            type = aType;
        }
    }

    // returns the person owner of the person
    public Person getOwner() {
        return owner;
    }

    // returns the people that the person owns
    public ArrayList<Person> getPeople() {
        ArrayList<Person> realPeople = new ArrayList<Person>();
        for (int i = 0; i < people.size(); i++) {
            Person temp = people.get(i);
            realPeople.add(temp);
        }
        return realPeople;
    }

    // hero says a single string of all owned peoples names
    public void getPeoplesNames() {
        if (people.size() > 0) {
            String str = new String("");
            for (int i = 0; i < people.size(); i++) {
                Person temp = people.get(i);
                str = str + temp.getName() + " ";
            }
            say(str);
        }
    }

    // adds the given thing to the room
    public void add(Thing thing) {
        things.add(thing);
    }

    // adds the given person to the room
    public void add(Person person) {
        people.add(person);
    }

    // moves the person to the given room
    public void changeRoom(Room nextRoom) {
        // remove the person from the old room (if already in one)
        if (room != null && this != null)
            room.remove(this);

        // change my room to be the next one
        room = nextRoom;

        // add myself to the new room
        room.add(this);
    }

    // moves the person to the given room
    public void changeOwner(Person person) {
        // remove the person from the old room (if already in one)
        if (room != null) {
            room.remove(this);
            // change my room to null, I am not in a room
            room = null;
        }
        if (owner != null) {
            owner.remove(this);
        }

        // change owner instance field
        owner = person;

        // add myself to the new owner's list of owned people
        person.add(this);
    }

    // precondition: person is not the hero
    // postcondition: person has acted, by moving, eating people, etc.
    public void act() {
        // dead people can't act
        if (room != null) {
            if ((this.getName()).equals("Medusa") || (this.getName()).equals("Cereberus")
                    || (this.getName()).equals("Minotaur") || (this.getName()).equals("NemeanLion")
                    || (this.getName()).equals("LerneanHydra")) // prevents task-related people from moving
            {
                // doesn’t do anything
                // this may be bad coding form, since I have an empty if statement, but I don't
                // know how else to specify that if this is medusa, hydra, minotaur, or nemean
                // lion
                // then I don't want it to move or do anything
            } else if (type == MONSTER) {
                move();
                eatSomeone();
            } else if (type == ROBOT) {
                move();
                takeSomething();
            }
        }
    }

    // returns a random object from the room and gives it to the robot calling this
    // method
    public void takeSomething() {
        Thing temp = room.getRandomThing();
        if (temp != null) {
            if ((temp.getOwner()) instanceof Room) {
                take(temp);
            } else // instance of Person
            {
                String tempName = ((Person) (temp.getOwner())).getName();
                if (tempName.equals("Medusa") || (tempName.equals("Minotaur")) || (tempName.equals("NemeanLion"))
                        || (tempName.equals("LerneanHydra")) || (tempName.equals("Cereberus"))) // prevents items
                                                                                                // related to the tasks
                                                                                                // from changing
                                                                                                // ownership
                {
                    temp = null;
                }
                if (temp != null) {
                    take(temp);
                }
            }
        }
    }

    // returns a random person from the room and gives it to the person calling this
    // method
    public void takeSomeone() {
        Person temp = room.getRandomPerson();
        if (temp != null) {
            take(temp);
        }
    }

    // returns a list of all the things that the person is carrying while preserving
    // encapsulation
    public ArrayList<Thing> getThings() {
        ArrayList<Thing> duplicateList = new ArrayList<Thing>();
        for (int i = 0; i < things.size(); i++) {
            Thing temp = things.get(i);
            duplicateList.add(new Thing(temp.getName(), temp.getType()));
        }
        return duplicateList;
    }

    // returns a list of references to the things that the person is carrying
    public ArrayList<Thing> getRealThings() {
        ArrayList<Thing> realThings = new ArrayList<Thing>();
        for (int i = 0; i < things.size(); i++) {
            Thing temp = things.get(i);
            realThings.add(temp);
        }
        return realThings;
    }

    // return the thing with the specified name, or null if the thing cannot be
    // found
    public Thing getThing(String thingName) {
        for (int i = 0; i < things.size(); i++) {
            Thing temp = things.get(i);
            if (thingName.equals(temp.getName())) {
                return temp;
            }
        }
        return null;
    }

    // return the person with the specified name, or null if the person cannot be
    // found
    public Person getPerson(String personName) {
        for (int i = 0; i < people.size(); i++) {
            Person temp = people.get(i);
            if (personName.equals(temp.getName())) {
                return temp;
            }
        }
        return null;
    }

    // person attempts to take thing and returns appropriate message depending on
    // success
    public void take(Thing thing) {
        if (thing.getType() == Thing.HEAVY) {
            // say("I try but cannot take " + thing.getName());
        } else {
            if ((thing.getOwner()) instanceof Room) // if thing is owned by a room
            {
                say("I take " + thing.getName() + " from " + ((Room) (thing.getOwner())).getName());
                thing.changeOwner(this);
                if ((thing.getName()).equals("NemeanLionHide")) {
                    System.out.println(
                            "After taking the hide, it might be helpful to find a way to get the hide to Hades.");
                    System.out.println("Hint 1: Hades lives in the Underworld.");
                    System.out.println(
                            "Hint 2: Check your commands list to see if you can find a way of getting places.");
                }
            } else {
                Person personOwner = ((Person) (thing.getOwner()));
                if (personOwner == this) {
                    System.out.println(getName() + " already has that!"); // if person tries to take a thing that person
                                                                          // already owns
                } else {
                    say("I take " + thing.getName() + " from " + personOwner.getName());
                    if ((thing.getName()).equals("LightningBolt") && (personOwner.getName()).equals("LerneanHydra")) {
                        personOwner.say("YOU DARE TAKE ZEUS' LIGHTNING BOLT‽ TAKE THIS " + name + "!!!");
                        System.out.println(personOwner.getName() + " violently attacks " + name + ".");
                        thing.changeOwner(this);
                        suffer(12);
                        System.out.println(name + " has lost 12 health. " + name + " now has " + health + " left.");
                        if (health <= 0) {
                            System.out.println("You are now dead.");
                        }
                    } else if ((thing.getName()).equals("NemeanLionHide")
                            && (personOwner.getName()).equals("NemeanLion")) {
                        personOwner.say("RRRROOOOOOOOOAAAAAAAARRRRRRR!!!!!!!");
                        System.out.println("The ground quakes from the strength of the lions roar.");
                        System.out.println(personOwner.getName() + " violently attacks " + name + ".");
                        System.out.println(name + " has died. Next time, " + name
                                + " might not want to take the hide of a LIVING lion.");
                        if (things.size() > 0) // death
                        {
                            for (int i = 0; i < things.size(); i++) {
                                Thing temp = things.get(i);
                                temp.changeOwner(room);
                            }
                        }
                        System.out.println("Thank you for playing.	Good bye. Better luck next time.");
                        System.exit(0);
                    } else if ((thing.getName()).equals("GoldenHorns") && (personOwner.getName()).equals("Minotaur")) {
                        personOwner.say(
                                "SNORT. My horns have more strength than that, should you wish to rip them from my thick skull.");
                        System.out.println(personOwner.getName() + " gores " + name + " on his golden horns.");
                        System.out.println(name + " has died. Next time, " + name
                                + " might not want to take the horns of a LIVING minotaur.");
                        if (things.size() > 0) // death
                        {
                            for (int i = 0; i < things.size(); i++) {
                                Thing temp = things.get(i);
                                temp.changeOwner(room);
                            }
                        }
                        System.out.println("Thank you for playing.	Good bye. Better luck next time.");
                        System.exit(0);
                    } else if ((thing.getName()).equals("MedusasHead") && (personOwner.getName()).equals("Medusa")) {
                        personOwner.say("YOU WOULD DARE TO RIP MY HEAD OFF OF MY LIVING BODY‽‽");
                        System.out.println(personOwner.getName() + "'s head of snakes tears your to pieces.");
                        System.out.println(name + " has died. Next time, " + name
                                + " might not want to take the head off of a LIVING medusa.");
                        if (things.size() > 0) // death
                        {
                            for (int i = 0; i < things.size(); i++) {
                                Thing temp = things.get(i);
                                temp.changeOwner(room);
                            }
                        }
                        System.out.println("Thank you for playing.	Good bye. Better luck next time.");
                        System.exit(0);
                    } else if ((thing.getName()).equals("TonicOfImmortality")) {
                        personOwner.say("Son, " + name + ", now you have proven that you are worthy to be a god.");
                        setType(GOD);
                        System.out
                                .println(name + " has been promoted to the status of God. " + name + " is now a God.");
                    } else {
                        // personOwner.say("I lose " + thing.getName());
                        // System.out.println(personOwner.getName() + " attacks " + name);
                        suffer(1);
                        // System.out.println(name + " loses 1 health. " + name + " now has " + health +
                        // " health.");
                        thing.changeOwner(this);
                    }
                }
            }
        }
    }

    // person attempts to take thing and returns appropriate message depending on
    // success
    public void take(Person person) {
        if (person.getType() == Person.MONSTER) {
            say("I cannot take a monster!");
            System.out.println(name + " tries to run away, but " + person.getName() + " grabs " + name
                    + " and swallows " + name + " in one gulp.");
            person.say("BUUUUUUURRRP");
            room.remove(this);
            if (things.size() > 0) {
                for (int i = 0; i < things.size(); i++) {
                    Thing temp = things.get(i);
                    temp.changeOwner(room);
                }
            }
            if (type == HERO) {
                System.out.println("Thank you for playing.	Good bye. Better luck next time.");
                System.exit(0);
            }
        } else if (person.getType() == Person.HERO) {
            say("The Hero is too awesome for me too take! I cannot take him!");
        } else if (person.getType() == Person.ROBOT) {
            if (getPerson(person.getName()) == null) // checks to see if person is already owned by person
            {
                if (person.getOwner() != null) // checks to see if person has a personowner already
                {
                    say("I take " + person.getName() + " from " + person.getOwner().getName());
                    person.changeOwner(this);
                } else // person is in a room, not owned by any person
                {
                    say("I take " + person.getName() + " from " + person.getRoom().getName());
                    person.changeOwner(this);
                }
            } else {
                System.out.println(getName() + " already has that!");
            }
        } else // person.getType() == Person.GOD
        {
            say("Am I seriously trying to take a God captive? Really? I should reconsider...");
        }
    }

    // person attempts to give thing to person and returns appropriate message
    // depending on success
    public void give(Thing thing, Person gPerson) {
        if (gPerson != this) {
            thing.changeOwner(gPerson);
            say("I give " + thing.getName() + " to " + gPerson.getName());
        } else {
            System.out.println("You cannot give " + thing.getName() + " to yourself!");
        }
        if ((thing.getName()).equals("NemeanLionHide") && (gPerson.getName()).equals("Hades")) {
            System.out.println("You have succesfully finished your first task.");
            gPerson.say("Your next task is to capture Cereberus and give him to me.");
        }
        if ((thing.getName()).equals("GoldenHorns") && (gPerson.getName()).equals("Hades")) {
            System.out.println("You have succesfully finished your third task.");
            gPerson.say("Your next task is to acquire MedusasHead and give it to me.");
        }
        if ((thing.getName()).equals("MedusasHead") && (gPerson.getName()).equals("Hades")) {
            System.out.println("You have succesfully finished your fourth task.");
            gPerson.say("Your next task is to acquire Zeus' Lightning Bolt and give it to me.");
        }
        if ((thing.getName()).equals("LightningBolt") && (gPerson.getName()).equals("Hades")) {
            System.out.println("You have succesfully finished your fifth task.");
            gPerson.say(
                    "Thank you Hercules. As a token of my gratitude, I will send you to Mount Olympus to meet your father, Zeus.");
            System.out.println("Hercules finds himself magically transported to Mount Olympus.");
            System.out.println("At MountOlympus " + name + " says - "
                    + "Father! I have finally proved my valor, and in exchange, you brother Hades has sent me here.");
            System.out.println("Zeus invites you to take the Tonic of Immortality.");
        }
    }

    // person attempts to give person to person and returns appropriate message
    // depending on success
    public void give(Person oPerson, Person gPerson) {
        if (gPerson != this) {
            oPerson.changeOwner(gPerson);
            say("I give " + oPerson.getName() + " to " + gPerson.getName());
            System.out.println(oPerson.getOwner().getName());
        } else {
            System.out.println("You cannot give " + oPerson.getName() + " to yourself!");
        }
        if ((oPerson.getName()).equals("Cereberus") && (gPerson.getName()).equals("Hades")) {
            System.out.println("You have succesfully finished your second task.");
            gPerson.say("Your next task is to take the Minotaurs GoldenHorns and give them to me.");
        }
    }

    // causes the person to move to a random neighbor (or stay still)
    private void move() {
        int num = Game.random(4);
        String direction;
        if (num == 0)
            direction = "north";
        else if (num == 1)
            direction = "east";
        else if (num == 2)
            direction = "south";
        else // num == 3
            direction = "west";
        Room nextRoom = room.nextRoom(direction);
        if (nextRoom != null)
            changeRoom(nextRoom);
        ArrayList<Person> mPeople = new ArrayList<Person>();
        mPeople = room.getPeople();
        if (mPeople.size() > 1) {
            String str = new String("Hi ");
            for (int i = 0; i < mPeople.size(); i++) {
                Person temp = mPeople.get(i);
                if (temp.getName() != getName()) {
                    str = str + " " + temp.getName();
                }
            }
            say(str);
        }
    }

    // precondition: person must be owned by person
    // postcondition: person has been removed from person
    public void remove(Person person) {
        boolean found = false;
        for (int i = 0; i < people.size() && !found; i++) {
            if (people.get(i) == person) {
                people.remove(i);
                found = true;
            }
        }

        if (!found)
            throw new IllegalArgumentException("Person not found:  " + person);
    }

    // precondition: thing must be owned by person
    // postcondition: thing has been removed from person
    public void remove(Thing thing) {
        boolean found = false;
        for (int i = 0; i < things.size() && !found; i++) {
            if (things.get(i) == thing) {
                things.remove(i);
                found = true;
            }
        }

        if (!found)
            throw new IllegalArgumentException("Thing not found:  " + thing);
    }

    // sometimes causes this person to eat a random person in the room
    private void eatSomeone() {
        Person victim = room.getRandomPerson();
        if (victim != this) // don't eat yourself
        {
            say("Grrr! I eat tasty " + victim.getName() + "!");
            victim.suffer(Game.random(3) + 2);
        }
    }

    // causes the person to say the given message
    public void say(String message) {
        System.out.println("At " + room.getName() + " " + name + " says - " + message);
    }

    // causes this person to suffer the given amount of damage
    public void suffer(int hits) {
        String str = " hit";
        if (hits > 1) {
            str = str + "s are";
        }
        if (hits == 1) {
            str = str + " is";
        }
        say("Ouch! " + hits + str + " more than I want!");
        health = health - hits;
        say("I now have " + health + " health.");
        if (health <= 0) {
            if (getThing("LightningBolt") != null) // gives a helpful hint if the cause of death was hydra
            {
                System.out.println(
                        "If you died when taking the lightning bolt from the LerneanHydra, here's a hint: You need to eat someone before taking the bolt otherwise the Hydra will drain the health that you were created with. If you eat someone, you have extra health.");
            }
            if (things.size() > 0) {
                for (int i = 0; i < things.size(); i++) {
                    Thing temp = things.get(i);
                    temp.changeOwner(room);
                }
            }
            room.remove(this);

            room = null;

            if (type == HERO) {
                System.out.println("Thank you for playing.	Good bye.");
                System.exit(0);
            }
        }
    }

    // causes this person to die
    public void die() {
        say("SHREEEEK! I, uh, suddenly feel very faint...");

        System.out.println("An earth-shattering, soul-piercing, blood-curdling, heart wrending scream is heard...");
        if (things.size() > 0) {
            for (int i = 0; i < things.size(); i++) {
                Thing temp = things.get(i);
                temp.changeOwner(room);
            }
        }
        room.remove(this);

        room = null;

        if (type == HERO) {
            System.out.println("Thank you for playing.	Good bye.");
            System.exit(0);
        }
    }

    // causes the person to suicide
    public void heroSuicide() {
        if (getThing("Elixir") != null) // this is a cheat that allows the user to bypass the game and win immediately
        {
            System.out.println(name + " has Elixir and is therefore immortal! " + name + " cannot suicide!");
            System.out.println("Wait...if " + name + " is immortal, why is " + name + " not in olympus? Also, " + name
                    + " should be a god since only gods are immortal.");
            System.out.println("Let's fix that.");
            System.out.println(name
                    + " is now in Mount Olympus, and is a God, hence the objective of the game has been achieved.");
            setType(GOD);
        } else // a quick and easy way, if slightly gory, for the hero to die
        {
            say("I have decided to leave this world and never return.");
            System.out
                    .println(name + " plunges his sword into his heart, convulses, and lies motionless on the floor.");
            if (things.size() > 0) {
                for (int i = 0; i < things.size(); i++) {
                    Thing temp = things.get(i);
                    temp.changeOwner(room);
                }
            }
            room.remove(this);

            room = null;

            System.out.println("Thank you for playing.	Good bye. Better luck next time.");
            System.exit(0);
        }
    }

    // called when the victim is killed by HERO
    public void kill(Person victim) {
        if (getThing("Sword") != null) {
            if (victim.getThings().size() > 0) {
                for (int i = 0; i < victim.getThings().size(); i++) {
                    Thing temp = victim.getThings().get(i);
                    Thing real = new Thing(temp.getName(), temp.getType());
                    real.changeOwner(room);
                    if ((real.getName()).equals("LightningBolt")) {
                        real.changeOwner(null);
                    }
                }
            }
            say("I attempt to kill " + victim.getName() + ".");
            if ((victim.getName()).equals("LerneanHydra")) {
                victim.say("YOU DARE TRY TO KILL ME‽ TAKE THIS " + name + "!!!");
                System.out.println(victim.getName() + " violently attacks " + name + ".");
                System.out.println(name + " manages to slay " + victim.getName());
                victim.say("FOOL! If I go, the LightningBolt goes with me!");
                room.remove(victim);
                victim.setRoom(null);
                suffer(2);
                System.out.println(name + " has lost 2 health." + name + " now has " + health + " left.");
            } else if ((victim.getName()).equals("NemeanLion")) {
                victim.say("RRRROOOOOOOOOAAAAAAAARRRRRRR!!!!!!!");
                System.out.println("The ground quakes from the strength of the lions roar.");
                System.out.println(victim.getName() + " violently attacks " + name + ".");
                System.out.println(name + " manages to slay " + victim.getName());
                room.remove(victim);
                victim.setRoom(null);
                suffer(2);
                System.out.println(name + " has lost 2 health." + name + " now has " + health + " left.");
            } else if ((victim.getName()).equals("Minotaur")) {
                victim.say("SNORT. My horns can defend me well, should you attack me.");
                System.out
                        .println(victim.getName() + " charges and attempts to gore " + name + " on his golden horns.");
                System.out.println(name + " manages to slay " + victim.getName());
                room.remove(victim);
                victim.setRoom(null);
                suffer(2);
                System.out.println(name + " has lost 2 health." + name + " now has " + health + " left.");
            } else if ((victim.getName()).equals("Medusa")) {
                victim.say("My snake head shall thwart your attempts to kill me!");
                System.out.println(victim.getName() + "'s head of snakes attacks you.");
                System.out.println(name + " manages to slay " + victim.getName());
                room.remove(victim);
                victim.setRoom(null);
                suffer(2);
                System.out.println(name + " has lost 2 health." + name + " now has " + health + " left.");
            } else if (victim.getType() == MONSTER) {
                victim.say("HOW DARE YOU TRY TO KILL A MONSTER‽");
                System.out.println("A violent battle ensues, with both " + victim.getName() + " and " + name
                        + " attempting to kill each other.");
                System.out.println(name + " manages to slay " + victim.getName());
                room.remove(victim);
                victim.setRoom(null);
                health = health / 2;
                System.out.println(
                        name + "'s health has been halved during the fight. " + name + "'s health is now " + health);
            } else if (victim.getType() == ROBOT) {
                say("I utterly and completely destroy " + victim.getName());
                System.out.println(victim.getName() + " has been killed by " + name);
                room.remove(victim);
                victim.setRoom(null);
            } else if (victim.getType() == HERO) {
                System.out.println("You can't kill the hero that easily!");
            } else // victim.getType() == GOD
            {
                System.out.println("FOOL! You cannot kill a god.");
                health -= 4;
                System.out.println(
                        name + " loses 4 health for trying to kill a god." + name + " now has " + health + " health.");
            }
        } else {
            System.out.println(
                    name + " has lost his sword! " + name + " must get it back before attempting to kill anyone.");
        }
    }

    // called when the victim is eaten by HERO
    public void eatPerson(Person victim) {
        say("I attempt to eat " + victim.getName() + ".");
        if (victim.getType() != MONSTER && victim.getType() != GOD) // victim is a robot, gets eaten and gives health to
                                                                    // hero
        {
            if (victim.getThings().size() > 0) // victim leaves all things in room before dying
            {
                for (int i = 0; i < victim.getThings().size(); i++) {
                    Thing temp = victim.getThings().get(i);
                    Thing real = new Thing(temp.getName(), temp.getType());
                    real.changeOwner(room);
                }
            }
            victim.say("Hey! I'm not edible!");
            System.out.println(victim.getName() + " makes an angry face at " + name + ".");
            say("I eat " + victim.getName() + " regardless of the fact that " + victim.getName() + " is inedible.");
            System.out.println(victim.getName() + " has been succesfully eaten by " + name + ".");
            health += (victim.getHealth());
            System.out.println(name + " has increased health by " + victim.getHealth() + ". " + name
                    + "'s health is now " + health);
        } else if (victim.getType() == MONSTER) {
            say("EEEEK! I can't eat a monster!");
            System.out.println(name + " attempts to abort consumption of " + victim.getName() + ", but "
                    + victim.getName() + " grabs " + name + " and eats " + name);
            System.out.println(victim.getName() + " fails to digest " + name + " and both " + victim.getName() + " and "
                    + name + " die."); // person dies from trying to kill a monster
            if (things.size() > 0) // person trying to eat monster drops all things to room
            {
                for (int i = 0; i < things.size(); i++) {
                    Thing temp = things.get(i);
                    temp.changeOwner(room);
                }
            }
            if (victim.getThings().size() > 0) // victim leaves all things in room before dying
            {
                for (int i = 0; i < victim.getThings().size(); i++) {
                    Thing temp = victim.getThings().get(i);
                    Thing real = new Thing(temp.getName(), temp.getType());
                    real.changeOwner(room);
                }
            }
            room.remove(this);

            room = null;

            System.out.println("Thank you for playing.	Good bye."); // game ends
            System.exit(0);
        } else // victim.getType() == GOD
        {
            say("AAAAAHHHHHH!!!!!! I can't eat a GOD!");
            System.out.println(victim.getName() + " utterly annhilates " + name + " .");
            if (things.size() > 0) // person trying to eat monster drops all things to room
            {
                for (int i = 0; i < things.size(); i++) {
                    Thing temp = things.get(i);
                    temp.changeOwner(room);
                }
            }
            room.remove(this);

            room = null;

            System.out.println("Thank you for playing.	Good bye."); // game ends
            System.exit(0);
        }
        room.remove(victim);
        victim.setRoom(null);
    }
}