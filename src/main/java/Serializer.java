import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.IdentityHashMap;

public class Serializer {
  private IdentityHashMap<Object, Integer> hashMap = new IdentityHashMap<>();
  private Integer id = 0;
  private Document doc;

  public Document serialize(Object obj) throws  IllegalAccessException {
    doc = new Document(new Element("Serialized"));
    serializeObject(obj);

    try {
      new XMLOutputter().output(doc, System.out);
      XMLOutputter output = new XMLOutputter();
      output.setFormat(Format.getPrettyFormat());
      output.output(doc, new FileWriter("serialized.xml"));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return doc;
  }

  private void serializeObject(Object obj) throws IllegalAccessException {
    Class c = obj.getClass();
    Integer objID = getID(obj);
    Element objElement = new Element("Object");
    objElement.setAttribute(new Attribute("Class", c.getName()));
    objElement.setAttribute(new Attribute("ID", objID.toString()));

    if (c.isArray()) {
      objElement = addArrayElement(obj);
      Class type = obj.getClass().getComponentType();

      for (int i = 0; i < Array.getLength(obj); i++) {
        if (Array.get(obj, i) == null) {
          Element nullEl = addValueElement(null);
          objElement.addContent(nullEl);
        } else if (type.isPrimitive()) {
          Element valueEl = addValueElement(Array.get(obj, i));
          objElement.addContent(valueEl);
        } else {
          Element refEl = addReferenceElement(getID(Array.get(obj, i)).toString());
          objElement.addContent(refEl);
          serializeObject(Array.get(obj, i));
        }
      }
    } else {
      Field[] fields = obj.getClass().getDeclaredFields();
      for (Field f : fields) {
        f.setAccessible(true);
        Element fieldElem = new Element("Field");       // TODO refactor
        fieldElem.setAttribute(new Attribute("Name", f.getName()));
        fieldElem.setAttribute(new Attribute("DeclaringClass", f.getDeclaringClass().getName()));

        Object value = f.get(obj);

        if (f.getType().isPrimitive()) {
          fieldElem.addContent(addValueElement(value));
        } else {
          Element refElement = addReferenceElement(getID(value).toString());
          fieldElem.addContent(refElement);
          serializeObject(value);
        }
        objElement.addContent(fieldElem);
      }
    }
    doc.getRootElement().addContent(objElement);
  }

  private Element addArrayElement(Object obj) {
    Element arrayElement = new Element("Object");
    arrayElement.setAttribute(new Attribute("Class", obj.getClass().getName()));
    arrayElement.setAttribute(new Attribute("ID", getID(obj).toString()));
    arrayElement.setAttribute(new Attribute("Length", Integer.toString(Array.getLength(obj))));
    return arrayElement;
  }

  private Element addValueElement(Object obj) {
    Element value = new Element("Value");

    if (obj == null)
      value.setText("null");
    else
      value.setText(obj.toString());
    return value;
  }

  private Element addReferenceElement (String id) {
    Element reference = new Element("Reference");
    reference.setText(id);
    return reference;
  }

  private Integer getID (Object obj) {
    int objID = id;

    if (!hashMap.containsKey(obj)) {
      hashMap.put(obj, objID);
      id++;
    } else
      objID = hashMap.get(obj);
    return objID;
  }
}
