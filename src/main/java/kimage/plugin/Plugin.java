package kimage.plugin;

import kimage.image.Image;
import kimage.image.ImageType;
import kimage.plugin.extras.Attributes;

/**
 * @author Krzysztof
 */
public abstract class Plugin {

    protected Attributes attributes = new Attributes();

    /**
     * Executes the algorithm.
     *
     * @param imgIn  input image.
     * @param imgOut output image
     */
    public abstract void process(
            Image imgIn,
            Image imgOut
    );

    /**
     * Executes the algorithm.
     *
     * @param imgInAndOut input and output image.
     */
    public final void process(
            Image imgInAndOut
    ) {
        process(imgInAndOut, imgInAndOut);
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.set(key, value);
    }

    public String getName() {
        String[] name = this.getClass().getName().split("\\.");
        return name[name.length - 1];
    }

    public String getInfo() {
        String ret = "<html><body><strong>Plugin:<br/></strong> " + getName() + "<br/>";
        if (!attributes.isEmpty()) {
            ret += "<strong>Attributes:</strong><br/>";
            ret += attributes.toString();
        }
        return ret;
    }

    public void checkImageType(Image image, ImageType cl) {
        if (!image.getClass().equals(cl.getClassType())) {
            throw new RuntimeException("Wrong image type for plugin " + this.getName());
        }
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

}
