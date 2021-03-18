package de.embl.cba.elastixwrapper.settings;

import de.embl.cba.elastixwrapper.commandline.settings.Settings;
import de.embl.cba.elastixwrapper.commandline.settings.TransformixSettings;

import java.io.File;
import java.util.ArrayList;

public class TransformixWrapperSettings extends Settings {

    public enum OutputModality
    {
        Show_images,
        Save_as_tiff,
        Save_as_BigDataViewer_xml_h5
    }

    // public String elastixDirectory;
    // public String tmpDir;

    public String transformationFilePath;
    // before staging
    public String movingImageFilePath;
    // after staging
    public ArrayList<String> stagedMovingImageFilePaths;

    public OutputModality outputModality;
    public File outputFile;

    // public int numWorkers;
    
    // surely this == tmpdir?
    // public File outputDirectory;
    // ?? how determined
    // public String transformationOutputFilePath;

}