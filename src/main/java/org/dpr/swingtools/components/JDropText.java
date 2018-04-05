/*
 * Copyright 2018 org.dpr & croger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dpr.swingtools.components;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;

/**
 * Component used to select a file or a directory.
 * textfield with drop capabilities associated with a right button for file selection.
 */
public class JDropText extends JPanel {

    private JDropTextField textfield;
    private JButton chooseButton;
    JFileChooser jfc = new JFileChooser();

    public JDropText(JComponent jc1, JComponent jc2) {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        add(jc1);
        add(jc2);
    }

    public JDropText(JComponent jc1, JComponent jc2, int position) {
        setLayout(new FlowLayout(position));
        add(jc1);
        add(jc2);
    }

    public JDropText() {
        setLayout(new FlowLayout(FlowLayout.LEADING));
        textfield = new JDropTextField("", 20);
        textfield.setEditable(false);
        chooseButton = new JButton("...");
        add(textfield);
        add(chooseButton);
        initComponent();
    }

    private void initComponent() {

        chooseButton.addActionListener(new GenericAction());
        chooseButton.setActionCommand("CHOOSE_IN");

        DropTarget dropTarget = new DropTarget(textfield, textfield);
    }

    private void updateOutput() {
        // not implemented

    }

    /**
     * @return the value of the text field
     */
    public String getText() {
        return textfield.getText();
    }

    public boolean isEditable() {
        return textfield.isEditable();
    }

    public void setEditable(boolean var1) {
        textfield.setEditable(var1);
    }

    public void setFileSelectionMode(int selectionMode){jfc.setFileSelectionMode(selectionMode);}

    public void addListener(DocumentListener myListener) {
        textfield.getDocument().addDocumentListener(myListener);
    }

    private class TextListener implements DocumentListener {

        public void changedUpdate(DocumentEvent e) {
            updateOutput();

        }

        public void insertUpdate(DocumentEvent e) {

            updateOutput();

        }

        public void removeUpdate(DocumentEvent e) {
            updateOutput();

        }

    }

    class GenericAction extends AbstractAction {

        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();


            if (command.equals("CHOOSE_IN")) {


                if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    textfield.setText(jfc.getSelectedFile().getAbsolutePath());

                }

            }
        }
    }

}
