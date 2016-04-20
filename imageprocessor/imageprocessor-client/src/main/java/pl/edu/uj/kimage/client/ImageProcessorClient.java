package pl.edu.uj.kimage.client;

import pl.edu.uj.kimage.api.ProcessingResultEvent;
import pl.edu.uj.kimage.api.Task;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Interface for client facade
 */
public interface ImageProcessorClient {
    /**
     * Returns list of names for available plugins
     *
     * @return list of plugin names
     */
    Set<String> getListOfPluginName();

    /**
     * Gets list of object types that are required for a plugin with the given name
     *
     * @param pluginName
     * @return list of required objects' class names
     */
    Set<String> getPluginRequiredTypes(String pluginName);

    /**
     * Gets list of object types that are produced by a plugin with the given name
     *
     * @param pluginName
     * @return list of produced objects' class names
     */
    Set<String> getPluginProducedTypes(String pluginName);

    /**
     * Posts task to the imageprocessor and executes the callback on finish
     * @param task correctly constructed task
     * @param callback callback for result
     */
    void publishTask(Task task, Consumer<ProcessingResultEvent> callback);
}
