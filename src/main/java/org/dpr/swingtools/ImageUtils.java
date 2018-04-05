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

package org.dpr.swingtools;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.swing.*;
import java.awt.*;

/**
 * Images Utilities.
 *
 */
public class ImageUtils {

    private static final Log log = LogFactory.getLog(ImageUtils.class);
	/** Returns an ImageIcon, or null if the path is invalid. */
	public static ImageIcon createImageIcon(String path) {
		log.trace(path + " in");
		java.net.URL imgURL = ImageUtils.class.getResource(path);
		log.trace(path + " out " + imgURL);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			log.error("Couldn't find file: " + path);
			return null;
		}
	}

	public static Image getImage(String path) {
		log.trace(path + " in");
		Image img = Toolkit.getDefaultToolkit().getImage(
				ImageUtils.class.getResource(path));

		log.trace(path + " out " + img);
		if (img != null) {
			return img;
		} else {
			log.error("Couldn't find file: " + path);
			return null;
		}
	}

}
