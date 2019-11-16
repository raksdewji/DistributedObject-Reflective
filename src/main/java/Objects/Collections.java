package Objects;

import java.util.ArrayList;

public class Collections {
  private ArrayList<Primitives> objects;

  public Collections() {}

  public  Collections(ArrayList<Primitives> array) {
    this.objects = array;
  }

  public ArrayList<Primitives> getObjectArray() { return objects; }
}
