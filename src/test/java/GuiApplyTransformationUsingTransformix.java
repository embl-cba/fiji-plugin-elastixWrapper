import de.embl.cba.elastixwrapper.commands.TransformixCommand;
import net.imagej.ImageJ;

public class GuiApplyTransformationUsingTransformix
{
    // Main
    public static void main(final String... args) throws Exception
    {

        final ImageJ ij = new ImageJ();
        ij.ui().showUI();
        ij.command().run( TransformixCommand.class, true );

    }

}
