package pl.edu.uj.kimage.image;

/**
 * @author Krzysztof
 */
public abstract class AbstractImage {

    protected static int type;
    // Dimension
    protected int width;
    protected int height;
    protected String descrition = "";

    public String getDescrition() {
        return descrition;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public abstract AbstractImage copy();

    public final int getHeight() {
        return height;
    }

    public final int getWidth() {
        return width;
    }

    protected boolean checkX(final int x) {
        return (x >= 0 && x <= width);
    }

    protected boolean checkY(final int y) {
        return (y >= 0 && y <= height);
    }

}
