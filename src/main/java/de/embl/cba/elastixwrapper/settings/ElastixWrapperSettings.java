package de.embl.cba.elastixwrapper.settings;

import de.embl.cba.elastixwrapper.elastix.DefaultElastixParametersCreator.ParameterStyle;
import de.embl.cba.elastixwrapper.elastix.ElastixParameters;

import java.util.HashMap;
import java.util.Map;

import static de.embl.cba.elastixwrapper.elastix.ElastixParameters.FINAL_RESAMPLER_LINEAR;

public class ElastixWrapperSettings extends ElastixSettings
{
    public static final String STAGING_FILE_TYPE = "mhd";

    // before staging - do we need to keep this??
    public String initialFixedImageFilePath = "";
    public String initialMovingImageFilePath = "";
    public String initialFixedMaskPath = "";
    public String initialMovingMaskPath = "";

    public ParameterStyle elastixParametersStyle = ParameterStyle.Default;
    public String resultImageFileType = STAGING_FILE_TYPE;
    public int numChannels = 1;
    public double imageWidthMillimeter;

    // path to copy calculated transformation to
    public String transformationOutputFilePath;

    public ElastixParameters.TransformationType transformationType;
    public int iterations = 1000;
    public String spatialSamples = "10000";
    public String downSamplingFactors = "10 10 10";
    public String bSplineGridSpacing = "50 50 50";
    public String finalResampler = FINAL_RESAMPLER_LINEAR;

    //TODO - determined form image
    public int movingImageBitDepth = 8;
    // specific to multichannel images
    // TODO - determined from image
    public Map< Integer, Integer > fixedToMovingChannel = new HashMap<>(  );
    public double[] channelWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,};
    public boolean stageImages;

}
