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

import java.awt.event.ActionListener;
import java.util.Map;

class SWComponent
{



    private String label;
    private Class<?> classe;
    private String keyValue;
    private Map<String, String> values;
    private String defaultValue;
    private ActionListener listener;

    public SWComponent(String label, Class<?> classe, String keyValue, Map<String, String> values, String defaultValue,
            ActionListener listener)
    {
        super();
        this.label = label;
        this.classe = classe;
        this.keyValue = keyValue;
        this.values = values;
        this.defaultValue = defaultValue;
        this.listener = listener;
    }
    
    public String getLabel()
    {
        return label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }

    public Class<?> getClasse()
    {
        return classe;
    }

    public void setClasse(Class<?> classe)
    {
        this.classe = classe;
    }

    public String getKeyValue()
    {
        return keyValue;
    }

    public void setKeyValue(String keyValue)
    {
        this.keyValue = keyValue;
    }

    public Map<String, String> getValues()
    {
        return values;
    }

    public void setValues(Map<String, String> values)
    {
        this.values = values;
    }

    public String getDefaultValue()
    {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue)
    {
        this.defaultValue = defaultValue;
    }

    public ActionListener getListener()
    {
        return listener;
    }

    public void setListener(ActionListener listener)
    {
        this.listener = listener;
    }


}
