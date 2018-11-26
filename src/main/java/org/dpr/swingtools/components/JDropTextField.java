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

import org.dpr.swingtools.TextEventListener;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Text field with drop capability.
 */
class JDropTextField extends JTextField implements DropTargetListener {

    public List<TextEventListener> getListeners() {
        return listeners;
    }

    private List<TextEventListener> listeners = new ArrayList<>();
	public JDropTextField(String string, int i) {
		super(string, i);
	}

	public JDropTextField(int i) {
		super(i);
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		//
	}

	public void dragExit(DropTargetEvent dte) {
		//
	}

	public void dragOver(DropTargetDragEvent dtde) {
		//
	}

	public void drop(DropTargetDropEvent dtde) {
		// Check the drop action
		if ((dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
			// Accept the drop and get the transfer data
			dtde.acceptDrop(dtde.getDropAction());
			Transferable transferable = dtde.getTransferable();

			try {
				boolean result = false;

				if (dtde.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
					result = dropFile(transferable);
				} else {
					result = false;
				}
				dtde.dropComplete(result);

			} catch (Exception e) {
				System.out.println("Exception while handling drop " + e);
				dtde.rejectDrop();
			}
		} else {
			System.out.println("Drop target rejected drop");
			dtde.dropComplete(false);
		}

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// System.out.println("dropActionChanged");
	}

	// This method handles a drop for a list of files
	private boolean dropFile(Transferable transferable) throws IOException,
			UnsupportedFlavorException {
		List fileList = (List) transferable
				.getTransferData(DataFlavor.javaFileListFlavor);
		File transferFile = (File) fileList.get(0);

		final String transferURL = transferFile.getAbsolutePath();
		// System.out.println("File URL is " + transferURL);
		this.setText(transferURL);
		notifyTextChanged(transferURL);

		return true;
	}

    public void addListener(TextEventListener listener) {
        listeners.add(listener);
    }

	private void notifyTextChanged(String transferURL) {
		for (TextEventListener listener : listeners) {
			listener.textChanged(transferURL);
		}
	}
}