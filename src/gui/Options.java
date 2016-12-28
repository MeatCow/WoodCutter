package gui;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Matt on 25/12/2016.
 */
public class Options {
    private JComboBox optionsBox;
    private JComboBox whereBox;
    private JButton startButton;
    private JList list1;

    public Options() {
        optionsBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    if (e.getItem().equals("Bank")) {
                        whereBox.setEnabled(true);
                    }
                    else {
                        whereBox.setEnabled(false);
                    }
                }
            }
        });
    }
}
