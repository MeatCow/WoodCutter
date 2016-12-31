package gui;

import EnumsScript.Banks;
import scripts.Bank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by Matt on 25/12/2016.
 */
public class Options extends JFrame {
    private JComboBox optionsBox;
    private JComboBox whereBox;
    private JButton startButton;
    private JList treeList;
    private JPanel mainPanel;
    private boolean isReady;

    public static enum Methods {
        BANK,
        DROP;
    }

    public Options() {
        setTitle("WoodCutting Chop Chop!");
        setPreferredSize(new Dimension(350,250));
        init();
    }

    private void init() {

        setContentPane(mainPanel);
        setLocationRelativeTo(null);

        optionsBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                bankingChoiceChanged(e);
            }
        });
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isReady = true;
            }
        });

        pack();
    }

    public boolean isReady() {
        return isReady;
    }

    private void bankingChoiceChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED) {
            if (e.getItem().equals("Bank")) {
                whereBox.setEnabled(true);
            }
            else {
                whereBox.setEnabled(false);
            }
        }
    }

    public Banks getBankChoices() {
        Banks choice = null;
        if ((Options.Methods) optionsBox.getSelectedItem() == Methods.BANK) {
            choice = (Banks) whereBox.getSelectedItem();
        }
        System.out.println(choice);
        return choice;
    }

}
