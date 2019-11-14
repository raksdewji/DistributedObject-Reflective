package Objects;

public class ReferenceArray {
  private Primitives[] primitiveArray;

  public ReferenceArray(Primitives[] array) {
    this.primitiveArray = array;
  }

  public Primitives[] getPrimitiveArray() { return primitiveArray; }
}
