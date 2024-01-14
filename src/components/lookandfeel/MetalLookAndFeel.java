package components.lookandfeel;

import javax.swing.*;
import java.awt.*;

public class MetalLookAndFeel {

    public static void setMetalLookAndFeel
        (Component...args){

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            for (Component arg : args) {
                SwingUtilities.updateComponentTreeUI(arg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
