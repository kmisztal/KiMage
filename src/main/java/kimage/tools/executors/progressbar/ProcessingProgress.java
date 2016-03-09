package kimage.tools.executors.progressbar;

import javax.swing.*;
import java.awt.*;

/**
 * @author Krzysztof
 */
public class ProcessingProgress {
    private final ProgressMonitor monitor;
    private final int stepNumber;
    private int progress;

    public ProcessingProgress(Component parent, int stepsNumber) throws HeadlessException {
        this.monitor = new ProgressMonitor(parent, "Image Processing", "Getting Started...", 0, stepsNumber);
        this.monitor.setMillisToDecideToPopup(0);
        this.monitor.setMillisToPopup(0);
        this.progress = 0;
        this.stepNumber = stepsNumber;
    }

    public void increment() {
        this.progress += 1;
        this.monitor.setProgress(progress);
        this.monitor.setNote("Step " + progress + " / " + stepNumber + " completed");
    }
}
