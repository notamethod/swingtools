/*
 * Copyright 2019 org.dpr & croger
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
import java.awt.*;

public class SWLabel {
    JLabel jl;

    public SWLabel(String label) {
         jl = new JLabel(label);

    }

    public SWLabel(String label, Style style) {
        jl = new JLabel(label);
        switch (style){
            case BOLD:
                Font f = jl.getFont();
                // bold
                jl.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
                break;
            default:
                break;
        }

    }

    public JLabel getJLabel() {
        return jl;
    }

    public enum Style{
        BOLD, DEFAULT;
    }
}
