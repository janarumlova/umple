/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.21.0.4666 modeling language!*/


import java.util.*;

// line 7 "Phone.ump"
public class PhoneSystemSimulation
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static PhoneSystemSimulation theInstance = null;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PhoneSystemSimulation Associations
  private List<PhoneLine> lines;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  private PhoneSystemSimulation()
  {
    lines = new ArrayList<PhoneLine>();
    // line 11 "Phone.ump"
    addLine(new PhoneLine("line1","Bruce",this));
        addLine(new PhoneLine("line2","Ralph",this));
        addLine(new PhoneLine("line3","Victoria",this));
        addLine(new PhoneLine("line4","Vicki",this));
        addLine(new PhoneLine("line5","Agnes",this));
  }

  public static PhoneSystemSimulation getInstance()
  {
    if(theInstance == null)
    {
      theInstance = new PhoneSystemSimulation();
    }
    return theInstance;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public PhoneLine getLine(int index)
  {
    PhoneLine aLine = lines.get(index);
    return aLine;
  }

  /**
   * lazy PhoneLine[] lines;
   */
  public List<PhoneLine> getLines()
  {
    List<PhoneLine> newLines = Collections.unmodifiableList(lines);
    return newLines;
  }

  public int numberOfLines()
  {
    int number = lines.size();
    return number;
  }

  public boolean hasLines()
  {
    boolean has = lines.size() > 0;
    return has;
  }

  public int indexOfLine(PhoneLine aLine)
  {
    int index = lines.indexOf(aLine);
    return index;
  }

  public static int minimumNumberOfLines()
  {
    return 0;
  }

  public PhoneLine addLine(String aId, String aVoice)
  {
    return new PhoneLine(aId, aVoice, this);
  }

  public boolean addLine(PhoneLine aLine)
  {
    boolean wasAdded = false;
    if (lines.contains(aLine)) { return false; }
    PhoneSystemSimulation existingPhoneSystemSimulation = aLine.getPhoneSystemSimulation();
    boolean isNewPhoneSystemSimulation = existingPhoneSystemSimulation != null && !this.equals(existingPhoneSystemSimulation);
    if (isNewPhoneSystemSimulation)
    {
      aLine.setPhoneSystemSimulation(this);
    }
    else
    {
      lines.add(aLine);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLine(PhoneLine aLine)
  {
    boolean wasRemoved = false;
    //Unable to remove aLine, as it must always have a phoneSystemSimulation
    if (!this.equals(aLine.getPhoneSystemSimulation()))
    {
      lines.remove(aLine);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  public boolean addLineAt(PhoneLine aLine, int index)
  {  
    boolean wasAdded = false;
    if(addLine(aLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLines()) { index = numberOfLines() - 1; }
      lines.remove(aLine);
      lines.add(index, aLine);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLineAt(PhoneLine aLine, int index)
  {
    boolean wasAdded = false;
    if(lines.contains(aLine))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLines()) { index = numberOfLines() - 1; }
      lines.remove(aLine);
      lines.add(index, aLine);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLineAt(aLine, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=lines.size(); i > 0; i--)
    {
      PhoneLine aLine = lines.get(i - 1);
      aLine.delete();
    }
  }

  // line 22 "Phone.ump"
   public static  void main(String [] args){
    Thread.currentThread().setUncaughtExceptionHandler(new UmpleExceptionHandler());
    Thread.setDefaultUncaughtExceptionHandler(new UmpleExceptionHandler());
    s = getInstance();
    PhoneLine lineA;
    PhoneLine lineB;
    int choiceA, choiceB;
    
    while(true) {
    
      // Randomly choose who initiates
      choiceA = (int)Math.floor(Math.random()*5);
      choiceB = (choiceA + 1 + (int)Math.floor(Math.random()*4)) % 5;
      lineA = s.getLine(choiceA);
      lineB = s.getLine(choiceB);

    
      // Initate a call
      lineA.startDialing();
      lineA.pause(200);
      lineA.setOtherParty(lineB);
      lineA.completeNumber();
        lineA.p(); lineB.p(); // Will be done by tracing associations

      // The other party can choose whether to pick up or not.
      if(Math.random() > 0.5) {
        lineB.pickUp();
      
        // While talking either party can hang up
        if(Math.random() > 0.5) {
          lineA.hangUp();
          lineB.hangUp(); // assuming it doesn't forget
        }
        else {
          lineB.hangUp();
          lineA.hangUp(); // assuming it doesn't forget
        }
      }
      else {
        // Hang up while waiting and the other will too
        lineA.hangUp();
      }
    }
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 19 Phone.ump
  static PhoneSystemSimulation s ;

  