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

import org.dpr.swingtools.components.JDropText;

import javax.swing.*;

public class ShowComponents {

    public static void main(String[] args) {
        JFrame frame = new JFrame("ShowComponents");
        frame.setContentPane(new ShowComponents().Panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel Panel1;
    private JDropText JDropText1;
    private JTable table1;

    private void createUIComponents() {
        // TODO: place custom component creation code here
        JDropText1  = new JDropText();
        JDropText1.setEditable(true);
        JDropText1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }
}
