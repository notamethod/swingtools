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



import org.dpr.swingtools.FrameModel;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class LabelValuePanel extends JPanel implements DocumentListener {

    private final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy HH:mm";
    /**
     *
     */
    private static final long serialVersionUID = 2550655426888407968L;
    int tfSize;
    private int nbRows;
    private FrameModel model = null;
    private int nbCols;
    private String dateFormat = DEFAULT_DATE_FORMAT;
    private ExtendedMap<String, Object> elements;
    private Map<String, Object> components;
    private List<LabelValuePanel> child = new ArrayList<>();

    public LabelValuePanel() {
        super();
        this.setLayout(new SpringLayout());
        this.elements = new ExtendedHashMap<>();
        this.components = new HashMap<>();
    }

    public LabelValuePanel(Map<String, String> elements2, int cols) {
        this.nbCols = cols;
    }

    public LabelValuePanel(FrameModel model) {
        super();
        this.setLayout(new SpringLayout());
        this.elements = new ExtendedHashMap<>();
        this.components = new HashMap<>();
    }

    @Override
    public void setVisible(boolean aFlag) {
        if (!aFlag) {
            SpringUtilities.makeCompactGrid(this, 0, 2, 3, 3, 3, 3);
        } else {
            SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
        }
        super.setVisible(aFlag);
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public List<LabelValuePanel> getChild() {
        return child;
    }

    public void addChild(LabelValuePanel lvPanel) {
        child.add(lvPanel);
    }

    /**
     * add a label: textfield to the panel
     * @param label label of the component
     * @param key
     * @param defaultValue
     */
    public void put(String label, String key, String defaultValue) {
        put(label, JTextField.class, key, defaultValue, true);
    }

    public void putDisabled(String label, String id, String defaultValue) {
        put(label, JTextField.class, id, defaultValue, false);
    }

    public void put(String label, Class<?> class1, String keyValue, Map<String, String> values) {
        JLabel jl = new JLabel(label);

        JComponent component = null;
        if (class1.getName().equals(JComboBox.class.getName())) {
            component = putCombo(keyValue, values, "");
        }

        this.add(jl);
        this.add(component);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
    }

    public void putList(String label, Class<?> class1, String keyValue) {
        putList(label, class1, keyValue, null);
    }
    public void putList(String label, Class<?> class1, String keyValue, JStringList.Position pos) {
        JLabel jl = new JLabel(label);

        JComponent component = null;
        if (class1.getName().equals(JStringList.class.getName())) {
            component = putJStringList(label, keyValue, pos);
        }

        this.add(jl);
        this.add(component);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
    }

    public void put(SWComponent swComponent) {
        put(swComponent.getLabel(), swComponent.getClasse(), swComponent.getKeyValue(), swComponent.getValues(),
                swComponent.getDefaultValue(), swComponent.getListener());
    }

    public void put(String label, Class<?> class1, String keyValue, Map<String, String> values, String defaultValue) {
        put(label, class1, keyValue, values, defaultValue, null);
    }

    private void put(String label, Class<?> class1, String keyValue, Map<String, String> values, String defaultValue,
                     ActionListener listener) {
        JLabel jl = new JLabel(label);
        if (model != null) {
            model.add(keyValue, keyValue);
        }
        JComponent component = null;
        List<JComponent> components = null;
        if (class1.getName().equals(JComboBox.class.getName())) {
            component = putCombo(keyValue, values, defaultValue, listener);
        } else if (class1.getName().equals(ButtonGroup.class.getName())) {
            components = putRadios(keyValue, values, defaultValue);
        }

        if (component != null) {
            this.add(jl);
            this.components.put(keyValue, component);
            this.add(component);
            nbRows++;
        } else {
            for (int i = 0; i < components.size(); i++) {
                this.add(i == 0 ? new JLabel(label) : new JLabel(""));
                this.add(components.get(i));

                nbRows++;
            }
        }

        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);

    }

    private JComponent putCombo(String keyValue, Map<String, String> values, String defaultValue) {
        return putCombo(keyValue, values, defaultValue, null);
    }

    private JComponent putCombo(String keyValue, Map<String, String> values, String defaultValue,
                                ActionListener listener) {
        final String globalKey = keyValue;
        final Map<String, String> map = values;
        JComboBox combo = new JComboBox();
        Set<String> keys = values.keySet();
        for (String key : keys) {
            combo.addItem(key);
            if (elements.get(globalKey) == null) {
                elements.put(globalKey, map.get(key));
            }
        }

        combo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JComboBox combo = (JComboBox) e.getSource();
                String key = (String) combo.getSelectedItem();
                elements.put(globalKey, map.get(key));
            }
        });
        combo.setSelectedItem(defaultValue);

        if (listener != null) {
            combo.addActionListener(listener);
        }
        return combo;
    }

    private JComponent putJStringList(String label, String keyValue, JStringList.Position pos) {
        final String globalKey = keyValue;

        JStringList jsList = new JStringList(label, pos);

            if (elements.get(globalKey) == null) {
                elements.put(globalKey, jsList.getListModel());
            }

        return jsList;
    }

    private List<JComponent> putRadios(String keyValue, Map<String, String> values, String defaultValue) {

        final String globalKey = keyValue;
        final Map<String, String> map = values;
        // JComboBox combo = new JComboBox();
        ButtonGroup bg = new ButtonGroup();

        List<JComponent> radios = new ArrayList<>();
        Set<String> keys = values.keySet();
        for (String key : keys) {
            JRadioButton jradio = new JRadioButton(key);
            radios.add(jradio);
            if (elements.get(globalKey) == null) {
                elements.put(globalKey, map.get(key));
                jradio.setSelected(true);
            }
            jradio.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JRadioButton radio = (JRadioButton) e.getSource();
                    if (radio.isSelected()) {
                        String key = radio.getText();
                        elements.put(globalKey, map.get(key));
                    }
                }
            });
            bg.add(jradio);
        }

        // combo.addActionListener(new ActionListener() {
        // public void actionPerformed(ActionEvent e) {
        // JComboBox combo = (JComboBox) e.getSource();
        // String key = (String) combo.getSelectedItem();
        // elements.put(globalKey, map.get(key));
        // }
        // });
        // combo.setSelectedItem(defaultValue);
        return radios;
    }

    public LabelValuePanel put(String label, Class<?> class1, String keyValue, Object value, boolean isEditable) {
        SWLabel jl = new SWLabel(label);
       return put(jl, class1,  keyValue,  value,  isEditable);
    }
    public LabelValuePanel put(SWLabel swLabel, Class<?> class1, String keyValue, Object value, boolean isEditable) {
        JLabel jl = swLabel.getJLabel();
        String label = jl.getText();
        String strValue = null;
        if (value != null) {

            strValue = value.toString();

        }
        final String globalKey = keyValue;
        JComponent component = null;
        if (class1.getName().equals(JLabel.class.getName())) {
            JLabel labelValue = new JLabel();
            labelValue.setText(strValue);

            component = labelValue;
            this.add(component, globalKey);
            // JtextField
        } else if (class1.getName().equals(JTextField.class.getName())) {

            JTextField field = new JTextField(20);
            field.setText(strValue);
            field.setName(keyValue);

            elements.put(globalKey, strValue);

            field.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    String value = null;
                    try {
                        value = e.getDocument().getText(0, e.getDocument().getLength());
                    } catch (BadLocationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    elements.put(globalKey, value);
                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changedUpdate(e);

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changedUpdate(e);
                }
            });

            field.setEditable(isEditable);
            if (!isEditable) {
                field.setBorder(null);
            }

            component = field;
            this.add(component, globalKey);

        } else if (class1.getName().equals(JPasswordField.class.getName())) {
            JPasswordField pwdField = new JPasswordField(strValue);
            pwdField.setEditable(isEditable);
            pwdField.setColumns(16);
            elements.put(globalKey, value);
            pwdField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    String value = null;
                    try {
                        value = e.getDocument().getText(0, e.getDocument().getLength());
                    } catch (BadLocationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    elements.put(globalKey, value);

                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changedUpdate(e);

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changedUpdate(e);
                }
            });

            component = pwdField;
            this.add(component, globalKey);
        } else if (class1.getName().equals(JTextArea.class.getName())) {
            JTextArea textArea = new JTextArea(5, 20);
            textArea.setText(strValue);
            textArea.setLineWrap(true);
            textArea.setEditable(isEditable);
            JScrollPane scrollPane = new JScrollPane(textArea);

            textArea.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void changedUpdate(DocumentEvent e) {
                    String value = null;
                    try {
                        value = e.getDocument().getText(0, e.getDocument().getLength());
                    } catch (BadLocationException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    elements.put(globalKey, value);

                }

                @Override
                public void insertUpdate(DocumentEvent e) {
                    changedUpdate(e);

                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    changedUpdate(e);
                }
            });
            this.add(textArea, globalKey);
            component = scrollPane;
        } else if (class1.getName().equals(JSpinnerDate.class.getName())) {
            // DateFormat df = DateFormat.getDateInstance();

            Date date = (Date) value;

            if (!isEditable) {
                DateFormat dateFormat = new SimpleDateFormat(this.dateFormat);
                put(label, JTextField.class, keyValue, dateFormat.format(date), false);
                return this;
            }

            JSpinnerDate spinner = new JSpinnerDate(this.dateFormat, date);

            SpinnerModel dateModel = spinner.getModel();
            if (dateModel instanceof SpinnerDateModel) {
                elements.put(globalKey, ((SpinnerDateModel) dateModel).getDate());
            }

            spinner.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSpinnerDate dateSpinner = (JSpinnerDate) e.getSource();
                    SpinnerModel dateModel = dateSpinner.getModel();
                    if (dateModel instanceof SpinnerDateModel) {
                        elements.put(globalKey, ((SpinnerDateModel) dateModel).getDate());

                    }

                }
            });

            component = spinner;
        } else if (class1.getName().equals(JCheckBox.class.getName())) {

            boolean valCheck = Boolean.parseBoolean(strValue);
            JCheckBox checkbox = new JCheckBox("", valCheck);
            checkbox.setEnabled(isEditable);
            elements.put(globalKey, checkbox.isSelected());

            checkbox.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JCheckBox checkbox = (JCheckBox) e.getSource();

                    elements.put(globalKey, checkbox.isSelected());
                }
            });

            component = checkbox;
        }

        // component.setName(globalKey);
        this.add(jl);
        this.add(component);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
        return this;

    }

    // public void set(String keyValue,
    // Object value) {
    // //boolean isEditable=true;
    // Component[] comps = this.getComponents();
    // JComponent component;
    // for (Component c : comps) {
    // if (c.getName().equals(keyValue)) {
    // // if (c instanceof JTextField) {
    // // ((JTextField) c).setText((String) obj);
    // // }
    // component = (JComponent)c;
    // break;
    // }
    // }
    // if (component==null){
    // return;
    // }
    //
    // String strValue = null;
    // if (value != null) {
    // if (value instanceof String) {
    // strValue = (String) value;
    // } else {
    // strValue = value.toString();
    // }
    // }
    //
    // final String globalKey = keyValue;
    //
    // if (component.getClass().getName().equals(JLabel.class.getName())) {
    // JLabel labelValue = new JLabel();
    // ((JLabel)component).setText(strValue);
    //
    // } else if (component.getClass().equals(JTextField.class.getName())) {
    //
    //
    // ((JTextField)component).setText(strValue);
    //
    //
    // elements.put(globalKey, strValue);
    //
    // } else if (component.getName().equals(JPasswordField.class.getName())) {
    //
    // elements.put(globalKey, value);
    // //todo
    // } else if (component.getName().equals(JTextArea.class.getName())) {
    //
    // ((JTextArea)component).setText(strValue);
    //
    // } else if (component.getName().equals(JSpinnerDate.class.getName())) {
    // // DateFormat df = DateFormat.getDateInstance();
    //
    // Date date = (Date) value;
    //
    //
    // JSpinnerDate spinner = new JSpinnerDate(this.dateFormat, date);
    //
    // SpinnerModel dateModel = spinner.getModel();
    // if (dateModel instanceof SpinnerDateModel) {
    // elements.put(globalKey, ((SpinnerDateModel) dateModel)
    // .getDate());
    // }
    // spinner.addChangeListener(new ChangeListener() {
    //
    // @Override
    // public void stateChanged(ChangeEvent e) {
    // JSpinnerDate dateSpinner = (JSpinnerDate) e.getSource();
    // SpinnerModel dateModel = dateSpinner.getModel();
    // if (dateModel instanceof SpinnerDateModel) {
    // elements.put(globalKey, ((SpinnerDateModel) dateModel)
    // .getDate());
    //
    // }
    //
    // }
    // });
    //
    // component = spinner;
    // }
    //
    //
    //
    // }

    public void set(String keyValue, Object value) {
        String strValue = null;
        if (value != null) {
            if (value instanceof String) {
                strValue = (String) value;
            } else if (value instanceof Date) {
                DateFormat dateFormat = new SimpleDateFormat(this.dateFormat);
                strValue = dateFormat.format(value);
            } else {
                strValue = value.toString();
            }

        }
        final String globalKey = keyValue;

        JComponent component = (JComponent) components.get(globalKey);

        if (component == null)
            return;

        if (component instanceof JLabel) {

            JLabel field = (JLabel) component;
            field.setText(strValue);
        } else if (component instanceof JTextComponent) {

            //JTextField field = (JTextField) component;
            ((JTextComponent) component).setText(strValue);


        } else if (component instanceof JComboBox) {

            JComboBox field = (JComboBox) component;
            field.setSelectedItem(strValue);
        }
    }

    public JComponent getComponent(String key) {
        return (JComponent) components.get(key);
    }
    public void put(String label, JComponent component, boolean isEditable) {
        JLabel jl = new JLabel(label);
        this.add(jl);
        this.add(component);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);

    }

    public void putEmptyLine() {
        JLabel jl = new JLabel(" ");
        JLabel jl2 = new JLabel(" ");

        this.add(jl);
        this.add(jl2);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
    }

    public LabelValuePanel addTitle(String title) {
        JLabel jl = new JLabel(title);
        Font f = jl.getFont();
        // bold
        jl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        JLabel jl2 = new JLabel(" ");

        this.add(jl);
        this.add(jl2);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
        return this;
    }

    /**
     * add a (colored) title to the panel
     * @param title
     * @param bgColor
     * @return
     */
    public LabelValuePanel addTitle(String title, Color bgColor) {
        JLabel jl = new JLabel(title);
        Font f = jl.getFont();
        // bold
        jl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        JLabel jl2 = new JLabel(" ");
        jl.setOpaque(true);
        jl.setBackground(bgColor);

        this.add(jl);
        this.add(jl2);
        nbRows++;
        SpringUtilities.makeCompactGrid(this, nbRows, 2, 3, 3, 3, 3);
        return this;
    }

    public String getStringValue(String name) {
        return (String) elements.get(name);
    }

    public Object getValue(String name) {
        return elements.get(name);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

    }

    /**
     * @return the elements
     */
    public ExtendedMap<String, Object> getElements() {
        if (!child.isEmpty())
            child.forEach(item -> elements.putAll(item.getElements()));
        return elements;


    }

    /**
     * @param elements the elements to set
     */
    public void setElements(ExtendedMap<String, Object> elements) {
        this.elements = elements;
    }

    public void setValue(String strName, Object obj) {
        Component[] comps = this.getComponents();
        for (Component c : comps) {
            if (c.getName().equals(strName)) {
                if (c instanceof JTextField) {
                    ((JTextField) c).setText((String) obj);
                }
            }
        }
    }

    private void add(Component comp, String key) {
        // TODO Auto-generated method stub
        comp.setName(key);
        // super.add(comp);
        if (components.get(key) == null) {
            components.put(key, comp);
        }
    }

    /**
     *
     * @param keyValue
     * @param value
     * @deprecated :
     */
    @Deprecated
    public void put(String keyValue, String value) {
        final String globalKey = keyValue;

        if (elements.get(globalKey) == null) {
            elements.put(globalKey, value);
        }

    }


    @Deprecated
    public void put(JComponent cbDuration) {
        cbDuration.setVisible(false);
        this.add(cbDuration);

    }

    /**
     * Add a pair "label: component" in the panel
     * @param label
     * @param component
     */
    public void put(String label, JComponent component) {

        this.add(component);
        nbRows++;

    }

}
