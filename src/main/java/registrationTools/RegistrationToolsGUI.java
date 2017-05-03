package registrationTools;

import ij.IJ;
import ij.ImagePlus;
import registrationTools.logging.IJLazySwingLogger;
import registrationTools.logging.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by tischi on 30/04/17.
 */
public class RegistrationToolsGUI extends JFrame implements ActionListener, FocusListener, ItemListener {

    public final static String IMAGEPLUS = "from ImageJ";
    public final static String ELASTIX = "Elastix";

    JTextField tfElastixFolder = new JTextField(40);
    JTextField tfTmpFolder = new JTextField(40);
    JTextField tfNumIterations = new JTextField(12);
    JTextField tfNumSpatialSamples = new JTextField(12);
    JTextField tfResolutionPyramid = new JTextField(12);
    JTextField tfNumWorkers = new JTextField(3);
    JTextField tfReference = new JTextField(3);
    JTextField tfFirst = new JTextField(3);
    JTextField tfLast = new JTextField(3);
    JCheckBox cbSnake = new JCheckBox();

    JButton btRunElastix = new JButton("Run Registration");

    RegistrationSettings settings = new RegistrationSettings();

    Logger logger = new IJLazySwingLogger();

    /**
     *
     * input: current image, folder
     * output: new image, folder
     *
     * method: Elastix
     *
     * Elastix settings: ...
     *
     * actions: [Run registration]
     *
     */
    public RegistrationToolsGUI()
    {

    }

    public void showDialog()
    {
        JTabbedPane jtp = new JTabbedPane();
        ArrayList<JPanel> panels = new ArrayList<>();
        ArrayList<JPanel> tabs = new ArrayList<>();

        settings.last = IJ.getImage().getNFrames();

        // suggest some settings based on current image
        //
        ImagePlus imp = IJ.getImage();
        if ( imp != null )
        {
            double dimRatio = (imp.getWidth() + imp.getHeight()) / imp.getNSlices();
            int zBinning = (int)Math.ceil(10/dimRatio);
            settings.resolutionPyramid = imp.getNSlices() > 1 ? "10 10 "+zBinning+"; 2 2 1" : "10 10; 2 2";
            settings.bitDepth = imp.getBitDepth();
        }

        // suggest some settings based on current OS
        //
        logger.info(System.getProperty("os.name"));
        if ( System.getProperty("os.name").startsWith("Mac") )
        {
            settings.folderElastix = "/Users/tischi/Downloads/elastix_macosx64_v4.8/";
            settings.folderTmp = "/tmp/";
        }
        if ( System.getProperty("os.name").startsWith("Windows") )
        {
            settings.folderElastix = "C:\\Program Files\\elastix_v4.8\\";
            settings.folderTmp = "C:\\tmp\\";
        }

        addTabPanel(tabs);
        addHeader(panels, tabs, "INSTALLATION");
        addTextField(panels, tabs, tfElastixFolder, "Elastix Installation Folder", "" + settings.folderElastix);
        addTextField(panels, tabs, tfTmpFolder, "Temp Folder", "" + settings.folderTmp);

        addHeader(panels, tabs, "PARAMETERS");
        addTextField(panels, tabs, tfNumIterations, "Iterations", "" + settings.iterations);
        addTextField(panels, tabs, tfNumSpatialSamples, "Spatial Samples", ""+settings.spatialSamples);
        addTextField(panels, tabs, tfResolutionPyramid, "Resolution Pyramid", settings.resolutionPyramid);
        addTextField(panels, tabs, tfNumWorkers, "Threads", "" + settings.workers);
        addTextField(panels, tabs, tfReference, "Reference", "" + settings.reference);
        addTextField(panels, tabs, tfFirst, "First", "" + settings.first);
        addTextField(panels, tabs, tfLast, "Last", "" + settings.last);
        addCheckBox(panels, tabs, cbSnake, "Snake", settings.snake);

        addHeader(panels, tabs, "RUN");
        addButton(panels, tabs, btRunElastix);
        jtp.add("Elastix", tabs.get(tabs.size() - 1));

        // Show
        //
        setTitle("Registration Tools");
        add(jtp);
        setVisible(true);
        pack();
    }


    public static void addTabPanel(ArrayList<JPanel> tabs)
    {
        tabs.add(new JPanel());
        tabs.get(tabs.size()-1).setLayout(new BoxLayout(tabs.get(tabs.size()-1), BoxLayout.PAGE_AXIS));
    }


    public static void addHeader(ArrayList<JPanel> panels, ArrayList<JPanel> tabs, String label)
    {
        panels.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
        panels.get(panels.size()-1).add(new JLabel(label));
        tabs.get(tabs.size()-1).add(panels.get(panels.size() - 1));
    }

    public void addTextField(ArrayList<JPanel> panels, ArrayList<JPanel> tabs, JTextField textField,
                             String label, String defaultValue)
    {
        panels.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)));
        textField.setActionCommand(label);
        textField.addActionListener(this);
        textField.addFocusListener(this);
        textField.setText(defaultValue);
        panels.get(panels.size()-1).add(new JLabel(label));
        panels.get(panels.size()-1).add(textField);
        tabs.get(tabs.size()-1).add(panels.get(panels.size()-1));
    }

    public void addCheckBox(ArrayList<JPanel> panels, ArrayList<JPanel> tabs, JCheckBox checkBox,
                             String label, Boolean defaultValue)
    {
        panels.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)));
        checkBox.setSelected(defaultValue);
        panels.get(panels.size()-1).add(new JLabel(label));
        panels.get(panels.size()-1).add(checkBox);
        tabs.get(tabs.size()-1).add(panels.get(panels.size()-1));
    }


    public void addButton(ArrayList<JPanel> panels, ArrayList<JPanel> tabs, JButton button)
    {
        panels.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)));
        button.setActionCommand(button.getText());
        button.addActionListener(this);
        panels.get(panels.size()-1).add(button);
        tabs.get(tabs.size()-1).add(panels.get(panels.size()-1));
    }

    public void addComboBox(ArrayList<JPanel> panels, int iPanel, Container c, JComboBox comboBox, String comboBoxLabel)
    {
        panels.add(new JPanel(new FlowLayout(FlowLayout.RIGHT)));
        panels.get(iPanel).add(new JLabel(comboBoxLabel));
        panels.get(iPanel).add(comboBox);
        c.add(panels.get(iPanel));
    }

    public void actionPerformed(ActionEvent e)
    {
        if ( e.getActionCommand().equals(btRunElastix.getText()) )
        {

            settings.method = RegistrationToolsGUI.ELASTIX;
            settings.first = 0;
            settings.last = IJ.getImage().getNFrames() - 1;
            settings.snake = cbSnake.isSelected();
            settings.iterations = Integer.parseInt(tfNumIterations.getText());
            settings.spatialSamples = tfNumSpatialSamples.getText();
            settings.workers = Integer.parseInt(tfNumWorkers.getText());
            settings.resolutionPyramid = tfResolutionPyramid.getText();
            settings.last = Integer.parseInt(tfLast.getText())-1;
            settings.first = Integer.parseInt(tfFirst.getText())-1;
            settings.reference = Integer.parseInt(tfReference.getText())-1;

            String inputImages = RegistrationToolsGUI.IMAGEPLUS;
            String outputImages = RegistrationToolsGUI.IMAGEPLUS;

            RegistrationTools registrationTools = new RegistrationTools(inputImages,
                    outputImages, settings);
            registrationTools.run();
        }
    }

    @Override
    public void focusGained(FocusEvent e)
    {

    }

    @Override
    public void focusLost(FocusEvent e)
    {
        JTextField tf = (JTextField) e.getSource();
        if ( tf != null )
        {
            tf.postActionEvent();
        }
    }

    @Override
    public void itemStateChanged(ItemEvent e)
    {

    }
}