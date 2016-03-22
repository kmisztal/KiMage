package kimage.plugin.extras;

import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Krzysztof
 */
public class Attributes {

    public static final String SIZE = "size";
    public static final String SIGMA = "sigma";
    public static final String COLOR = "color";
    public static final String THRESHOLDS = "thresholds";
    public static final String LEVELS = "levels";
    public static final String MIN = "min";
    public static final String MAX = "max";

    private final Map<String, Object> hashAttributes;

    /**
     * Constructor
     */
    public Attributes() {
        hashAttributes = new LinkedHashMap<>();
    }

    /**
     * Set an attribute.
     *
     * @param name  attribute name.
     * @param value attribute value.
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
     * @param name attribute's name.
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
     */
    public Object[] getValues() {
        return hashAttributes.entrySet().toArray(new Object[0]);
    }

    /**
     * Clones a MarvinAttributes Object.
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
        StringBuilder ret = new StringBuilder();

        for (String key : hashAttributes.keySet()) {
            ret.append(key).append(" : ");
            Object o = hashAttributes.get(key);
            if (o instanceof Number) {
                ret.append(o).append("\n");
            } else if (o instanceof Point) {
                final Point p = (Point) o;
                ret.append("[").append(p.getX()).append(";").append(p.getY()).append("]\n");
            } else if (o instanceof Color) {
                ret.append(o).append("\n");
            } else if (o instanceof int[]) {
                ret.append(Arrays.toString((int[]) o));
            } else if (o instanceof int[][]) {
                ret.append("2D array\n");
            } else {
                //                System.err.println("Not supported type of attribute : " + key);
                ret.append(o).append("\n");
            }
        }

        return ret.toString();
    }
}
