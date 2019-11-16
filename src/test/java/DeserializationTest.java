import Objects.Primitives;
import org.jdom2.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeserializationTest {

  @Test
  public void deserializeObject() throws Exception {
    Primitives primObject = new Primitives();
    primObject.x = 1;
    primObject.y = 2.0;

    Document doc = new Serializer().serialize(primObject);
    Deserializer d = new Deserializer();
    Primitives deserial = (Primitives) d.deserialize(doc);

    assertNotNull(deserial);
    assertEquals(primObject.getX(), deserial.getX());
  }
}
