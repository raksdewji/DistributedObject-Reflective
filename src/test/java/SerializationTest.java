import Objects.Primitives;
import org.jdom2.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SerializationTest {
  @Test
  public void serializeObject() throws  Exception {
    Primitives primObject = new Primitives();
    primObject.x = 1;
    primObject.y = 1.0;

    Serializer serializer = new Serializer();
    Document document = serializer.serialize(primObject);

    assertNotNull(document);
  }
}
