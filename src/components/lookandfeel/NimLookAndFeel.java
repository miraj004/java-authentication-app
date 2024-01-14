package components.lookandfeel;

import javax.swing.*;
import java.awt.*;

public class NimLookAndFeel {

    public static void setNimLookAndFeel(Component ...args){

            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                for (Component arg : args) {
                    SwingUtilities.updateComponentTreeUI(arg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }




    }

}
