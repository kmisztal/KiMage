package kimage.plugins;

import kimage.image.Image;
import kimage.utils.Attributes;
import kimage.utils.Mask;

/**
 *
 * @author Krzysztof
 */
public abstract class Plugin {
    
    protected Attributes attributes = new Attributes();

    /**
     * Executes the algorithm.
     *
     * @param imgIn	input image.
     * @param imgOut	output image.
     * @param attrOut	output attributes.
     * @param mask	mask containing what pixels should be considered.
     */
    public abstract void process(
            Image imgIn,
            Image imgOut,
            Attributes attrOut,
            Mask mask
    );

    /**
     * Executes the algorithm.
     *
     * @param imgIn	input image.
     * @param imgOut	output image.
     * @param mask
     */
    public final void process(
            Image imgIn,
            Image imgOut,
            Mask mask
    ) {
        process(imgIn, imgOut, null, mask);
    }
    
    /**
     * Executes the algorithm.
     *
     * @param imgIn	input image.
     * @param mask
     */
    public final void process(
            Image imgIn,
            Mask mask
    ) {
        process(imgIn, imgIn, null, mask);
    }

    /**
     * Executes the algorithm.
     *
     * @param imgIn	input image.
     * @param imgOut	output image.
     * @param attrOut
     */
    public final void process(
            Image imgIn,
            Image imgOut,
            Attributes attrOut
    ) {
        process(imgIn, imgOut, attrOut, null);
    }

    /**
     * Executes the algorithm.
     *
     * @param imgIn	input image.
     * @param imgOut	output image.
     */
    public final void process(
            Image imgIn,
            Image imgOut
    ) {
        process(imgIn, imgOut, null, null);
    }

    /**
     * Executes the algorithm.
     *
     * @param imgInAndOut	input and output image.
     */
    public final void process(
            Image imgInAndOut
    ) {
        process(imgInAndOut, imgInAndOut, null, null);
    }

    
    public Object getAttribute(String key){
        return attributes.get(key);
    }
    
    public void setAttribute(String key, Object value){
        attributes.set(key, value);
    } 

    public String getName() {
        String[] name = this.getClass().getName().split("\\.");
        return name[name.length - 1];
    }
    
    public String getInfo(){
        String ret  = "<html><body><strong>Plugin:<br/></strong> " + getName() + "<br/>";
        if(!attributes.isEmpty()){
            ret += "<strong>Attributes:</strong><br/>";
            ret += attributes.toString();
        }
        return ret;
    }
}
