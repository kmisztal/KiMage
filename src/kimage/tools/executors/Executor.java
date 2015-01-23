package kimage.tools.executors;

import java.util.ArrayList;
import java.util.List;
import kimage.image.Image;
import kimage.plugin.Plugin;

/**
 *
 * @author Krzysztof
 */
public abstract class Executor {

    protected final Image originalImage;
    protected Image currentImage;
    protected int currentPlugin = 0;

    private ArrayList<Plugin> plugins = new ArrayList<>();

    public Executor(String filename) {
        originalImage = new Image(filename);
        currentImage = originalImage.copy();
    }

    
    public Executor(Image img) {
        originalImage = img.copy();
        currentImage = originalImage.copy();
    }
    
    /**
     * add plugin 
     * @param plugin 
     */
    public void add(Plugin plugin) {
        plugins.add(plugin);
    }

    /**
     * methods to add new plugin with its attributes
     * @param plugin - Plugin class
     * @param params - attributes of given plugin
     */
    public void add(Plugin plugin, Object... params) {
        if (params.length % 2 != 0) {
            throw new RuntimeException("Wrong plugin initialization");
        }
        for (int i = 0; i < params.length;) {
            plugin.setAttribute((String) params[i++], params[i++]);
        }
        plugins.add(plugin);
    }

    public abstract void executeCase();
    
    public final void execute(){
        executeCase();
        currentPlugin = plugins.size();
    }
    
    /**
     * invoke the save methods from Image class
     * @param filename - destination file name
     */
    public void save(String filename) {
        currentImage.save(filename);
    }

    /**
     * 
     * @return original image
     */
    public Image getOriginalImage() {
        return originalImage;
    }

    /**
     * 
     * @return result image after applying all plugins
     */
    public Image getResultImage() {
        return currentImage;
    }

    /**
     * 
     * @return - number of plugins to apply
     */
    public int getLengthOfTask() {
        return plugins.size();
    }

    public List<Plugin> getPlugins() {        
        return plugins.subList(currentPlugin, plugins.size());
    }
    
    public Plugin getRecentPlugin() {        
        return plugins.get(plugins.size()-1);
    }
    
}
