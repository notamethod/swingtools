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


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

import static org.dpr.swingtools.ImageUtils.createImageIcon;

//import org.jdesktop.swingx.VerticalLayout;

public class JStringList extends JPanel {

    private static final Log log = LogFactory.getLog(JStringList.class);
    JList stringList;
    DefaultListModel listModel = new DefaultListModel();
    private JButton addButton;
    private JButton removeButton;
    private String label;
    private String message;
    public JStringList(String label) {

        this(label, Position.TOP);
    }

    public JStringList(String label, Position pos) {
        this.label = label;
        // setLayout(new VerticalLayout());
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        JPanel panelButtons = new JPanel();
        switch (pos) {
            case TOP:
                panelButtons.setLayout(new FlowLayout(FlowLayout.LEADING));
                setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
                break;
            case RIGHT:
                panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.PAGE_AXIS));
                setLayout(new FlowLayout(FlowLayout.LEADING));
                break;
            default:
                break;
        }

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEADING));
        JPanel panel3 = new JPanel();

        panel3.setLayout(new BoxLayout(panel3, BoxLayout.PAGE_AXIS));
        DialogAction dAction = new DialogAction();
        addButton = new JButton(createImageIcon("/images/add-simple.png"));
        Border emptyBorder = BorderFactory.createEmptyBorder();
        addButton.setBorder(emptyBorder);

        addButton.addActionListener(dAction);
        addButton.setActionCommand("ADD");

        removeButton = new JButton(createImageIcon("/images/clear-simple.png"));
        removeButton.setBorder(null);
        removeButton.addActionListener(dAction);
        removeButton.setActionCommand("REMOVE");

        stringList = new JList(listModel); //data has type Object[]
        stringList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        stringList.setLayoutOrientation(JList.VERTICAL);
        stringList.setVisibleRowCount(-1);

        panelButtons.add(addButton);
        panelButtons.add(removeButton);
        JScrollPane listScroller = new JScrollPane(stringList);
        listScroller.setPreferredSize(new Dimension(180, 50));
        switch (pos) {
            case TOP:
                add(panelButtons);
                add(listScroller);

                break;
            case RIGHT:
                add(listScroller);
                add(panelButtons);
                break;
            default:
                break;
        }



        initComponent();
    }

    public static void main(String[] args) {
        JDialog jd = new JDialog();
        jd.getContentPane().add(new JStringList("ddd"));
        jd.pack();
        jd.setVisible(true);
    }

    public DefaultListModel getListModel() {
        return listModel;
    }

    private void initComponent() {

//        addButton.addActionListener(new JStringList.GenericAction());
//        addButton.setActionCommand("CHOOSE_IN");

    }

    private void updateOutput() {
        // not implemented

    }

    /**
     * @return the value of the text field
     */
    public String getText() {
        return null;
    }


    public enum Position {
        TOP,
        RIGHT
    }

    class DialogAction extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent event) {
            String command = event.getActionCommand();
            if (command.equals("ADD")) {

                SwingUtilities.invokeLater(() -> {
                    String value = JOptionPane.showInputDialog(null, label);
                    listModel.addElement(value);

                });


            } else if (command.equals("REMOVE")) {
                if (stringList.getSelectedIndex() != -1)
                    listModel.remove(stringList.getSelectedIndex());
            }

        }
    }

}
