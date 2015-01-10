package kimage.plugin.extras;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 *
 * @author Krzysztof
 */
public class Attributes {

    protected final LinkedHashMap<String, Object> hashAttributes;

    /**
     * Constructor
     */
    public Attributes() {
        hashAttributes = new LinkedHashMap<>();
    }

    /**
     * Set an attribute.
     *
     * @param name	attribute name.
     * @param value	attribute value.
     */
    public void set(String name, Object value) {
        hashAttributes.put(name, value);
    }

    /**
     * Set a list of parameters. Format: (String)name, (Object)value...
     *
     * @param params
     */
    public void set(Object... params) {
        for (int i = 0; i < params.length; i += 2) {
            hashAttributes.put((String) params[i], params[i + 1]);
        }
    }

    /**
     * Get an attribute by its name.
     *
     * @param name	attribute's name.
     * @return the specified attribute as an Object.
     */
    public Object get(String name) {
        return hashAttributes.get(name);
    }

    public Object get(String name, Object defaultValue) {
        Object o = get(name);
        if (o != null) {
            return o;
        }
        return defaultValue;
    }

    /**
     * Returns all attributes' name and value as a String array.
     *
     * @return string array with all attributes' name and value.
     */
    public String[] toStringArray() {
        String attrs[] = new String[hashAttributes.size() * 2];
        String[] keys = hashAttributes.keySet().toArray(new String[0]);
        for (int x = 0; x < keys.length; x++) {
            attrs[(x * 2)] = keys[x];
            attrs[(x * 2) + 1] = "" + arrayString(hashAttributes.get(keys[x]));
        }
        return attrs;
    }

    String arrayString(Object o) {
        if (o instanceof double[][]) {
            return Arrays.deepToString((double[][]) o);
        } else {
            return "" + o;
        }
    }

    /**
     * returns an array containing the attrbiute values
     *
     * @return
     */
    public Object[] getValues() {
        Object o[] = hashAttributes.entrySet().toArray(new Object[0]);
        return o;
    }

    /**
     * Clones a MarvinAttributes Object.
     *
     * @return
     */
    @Override
    public Attributes clone() {
        Attributes attrs = new Attributes();
        String[] keys = hashAttributes.keySet().toArray(new String[0]);
        for (String key : keys) {
            attrs.set(key, hashAttributes.get(key));
        }
        return attrs;
    }

    public boolean isEmpty() {
        return hashAttributes.isEmpty();
    }

    @Override
    public String toString() {
        String ret = "";
        Iterator<String> keySetIterator = hashAttributes.keySet().iterator();

        while (keySetIterator.hasNext()) {
            String key = keySetIterator.next();
            ret += key + " : ";
            Object o = hashAttributes.get(key);
            if (o instanceof Number) {
                ret += o + "\n";
            } else if (o instanceof Point) {
                final Point p = (Point) o;
                ret += "[" + p.getX() + ";" + p.getY() + "]\n";
            } else if (o instanceof Color) {
                ret += o + "\n";
            } else if(o instanceof int[]) {
                ret += Arrays.toString((int[])o);
            } else if(o instanceof Mask){
                ret += "Mask is set\n";
            } else if (o instanceof int[][]) {
                ret += "2D array\n";
            } else {
//                System.err.println("Not supported type of attribute : " + key);
                ret += "" + o + "\n";
            }
        }

        return ret;
    }


}
