import java.sql.Ref;
import java.util.ArrayList;
import java.util.Scanner;
import Objects.*;

public class ObjectCreator {
  private ArrayList<Object> objectList = new ArrayList<>();
  private Scanner input = new Scanner(System.in);

  public static void main(String[] args) {
    ObjectCreator obj = new ObjectCreator();
    obj.runObjectCreator();
  }

  private void runObjectCreator() {
    boolean runProgram = true;

    while (runProgram) {
      printMenu();
      int choice = input.nextInt();

      if (choice == 1) {
        objectList.add(createPrimitiveObject());
      } else if (choice == 2) {
        objectList.add(createReferenceObject());
      } else if (choice == 3) {
        objectList.add(createPrimitiveArray());
      } else if (choice == 4) {
        objectList.add(createReferenceArray());
      } else if (choice == 5) {
        objectList.add(createCollectionsObject());
      } else if (choice == 6) {
        runProgram = false;
      } else {
        System.out.println("Invalid choice, choose from 1-6 choices");
      }
    }
  }

  private void printMenu() {
    System.out.println("Select object to create:");
    System.out.println("1) Create an object with only primitives");
    System.out.println("2) Create an object that contains references to other objects");
    System.out.println("3) Create an object that contains an array of primitives");
    System.out.println("4) Create an object that contains an array of object references");
    System.out.println("5) Create an object that uses an instance of Java's collection classes to refer other objects");
    System.out.println("6) Exit program");
    System.out.println("Choice: ");
  }

  /**
   * Creates an object with only primitives for instance variables
   *
   * @return Primitives
   */
  private Primitives createPrimitiveObject() {
    int inX;
    double inY;

    System.out.println("Creating primitive object");

    System.out.println("Enter a int value: ");
    inX = input.nextInt();

    System.out.println("Enter double value: ");
    inY = input.nextDouble();

    return new Primitives(inX, inY);
  }

  /**
   *  Creates an object that references other objects
   *
   * @return Reference
   */
  private Reference createReferenceObject() {
    System.out.println("Creating reference object");
    Primitives userPrimitive = createPrimitiveObject();
    return new Reference(userPrimitive);                // TODO refactor
  }

  /**
   * Creates an object that has an array of primitives
   *
   * @return PrimitiveArray
   */
  private PrimitiveArray createPrimitiveArray() {
    System.out.println("Creating primitive array object");

    System.out.println("Enter a size of array: ");
    int size = input.nextInt();                         // TODO refactor
    int [] intArray = new int[size];

    System.out.println("Arbitrary setting values in array");

    for (int i = 0; i < size; i++) {
      System.out.println("Enter value for array index " + i + ".");
      intArray[i] = input.nextInt();
    }

    return new PrimitiveArray(intArray);
  }

  /**
   * Creates an array of object references
   *
   * @return ReferenceArray
   */
  private ReferenceArray createReferenceArray() {
    System.out.println("Creating primitive array object");

    System.out.println("Enter a size of array: ");
    int size = input.nextInt();
    Primitives [] array = new Primitives[size];

    System.out.println("Arbitrary setting values in array");

    for (int i = 0; i < size; i++) {
      array[i] = createPrimitiveObject();
    }

    return new ReferenceArray(array);
  }

  private Collections createCollectionsObject() {
    System.out.println("Creating object with collection");
    ArrayList<Primitives> array = new ArrayList<>();
    String choice;

    do {
      array.add(createPrimitiveObject());
      System.out.println("Enter y/yes to add another object or q to quit");
      input.nextLine();
      choice = input.nextLine();

    } while (choice.equals("y") || choice.equals("yes"));

    return new Collections(array);
  }
}
