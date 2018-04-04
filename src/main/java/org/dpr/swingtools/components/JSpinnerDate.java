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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class JSpinnerDate extends JSpinner {

    private SpinnerDateModel model;

	public JSpinnerDate(String format, Date initDate) {
		super();
		init(format, initDate);

	}

	private void init(String format, Date initDate) {

		Calendar calendar = new GregorianCalendar();
		if (initDate == null) {
			initDate = calendar.getTime();
		}
		calendar.add(Calendar.YEAR, -100);
		Date earliestDate = calendar.getTime();
		calendar.add(Calendar.YEAR, 200);
		Date latestDate = calendar.getTime();
		model = new SpinnerDateModel(initDate, earliestDate, latestDate,
				Calendar.YEAR);
		this.setModel(model);
		this.setEditor(new DateEditor(this, format));

	}

}
