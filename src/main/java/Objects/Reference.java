package Objects;

public class Reference {
  private Primitives primitive;

  public Reference(Primitives prim) {
    this.primitive = prim;
  }

  public Primitives getPrimitive() { return primitive; }
}
