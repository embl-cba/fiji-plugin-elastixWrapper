package de.embl.cba.elastixwrapper.settings;

import de.embl.cba.elastixwrapper.elastix.ElastixParameters;

import java.util.HashMap;
import java.util.Map;

import static de.embl.cba.elastixwrapper.elastix.ElastixParameters.FINAL_RESAMPLER_LINEAR;

public class ElastixParametersSettings extends Settings {
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
}
