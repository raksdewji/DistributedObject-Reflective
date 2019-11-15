import org.jdom2.Document;
import org.jdom2.Element;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;

public class Deserializer {
  private HashMap<String, Object> hashmap = new HashMap<>();

  public Object deserialize (Document serialized) throws Exception {
    List<Element> elements = serialized.getRootElement().getChildren();

    for (Element element : elements) {
      String className = element.getAttribute("Class").getValue();
      Class c = Class.forName(className);
      Object obj;

      if (c.isArray()) {
        int length = Integer.parseInt(element.getAttribute("Length").getValue());
        obj = Array.newInstance(c.getComponentType(), length);
      } else {
        Constructor cons = c.getDeclaredConstructor(null);
        cons.setAccessible(true);
        obj = cons.newInstance(null);
      }
      hashmap.put(element.getAttribute("ID").getValue(), obj);
    }

    for (Element element : elements) {
      Object elementInstance = hashmap.get(element.getAttributeValue("ID"));
      List<Element> fields = element.getChildren();

      if (elementInstance.getClass().isArray()) {
        Class component = elementInstance.getClass().getComponentType();

        for (int i = 0; i < fields.size(); i++) {
          Element field = fields.get(i);

          if (field.getText().equals("null"))
            Array.set(elementInstance, i, null);
          else if (field.getName().equals("Reference"))
            Array.set(elementInstance, i, hashmap.get(field.getText()));
          else if (field.getName().equals("Value"))
            Array.set(elementInstance, i, getFieldValue(component, fields.get(i)));
        }
      } else {
        for (Element fieldElement : fields) {
          Class c = Class.forName(fieldElement.getAttributeValue("DeclaringClass"));
          Field field = c.getDeclaredField(fieldElement.getAttribute("Name").getValue());

          if (!Modifier.isFinal(field.getModifiers())) { break; }

          field.setAccessible(true);
          Element value = fieldElement.getChildren().get(0);

          if (value.getName().equals("Reference"))
            field.set(elementInstance, hashmap.get(value.getText()));
          else if (value.getName().equals("Value"))
            field.set(elementInstance, getFieldValue(field.getType(), value));
        }
      }
    }
    return hashmap.get("0");
  }

  private Object getFieldValue(Class field, Element valueElement) {
    if (field.equals(int.class))
      return Integer.valueOf(valueElement.getText());
    else if (field.equals(double.class))
      return Double.valueOf(valueElement.getText());
    else if (field.equals(boolean.class))
      return Boolean.valueOf(valueElement.getText());
    else if (field.equals(byte.class))
      return Byte.valueOf(valueElement.getText());
    else
      return valueElement.getText();
  }
}
