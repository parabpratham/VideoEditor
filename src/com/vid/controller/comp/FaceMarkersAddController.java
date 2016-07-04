package com.vid.controller.comp;

import java.net.URL;
import java.util.ResourceBundle;

import com.vid.controller.AbstractAddController;

import javafx.scene.control.TextArea;

public class FaceMarkersAddController extends AbstractAddController {

	private TextArea textbox;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);
	}

	public TextArea getTextbox() {
		return textbox;
	}

	public void setTextbox(TextArea textbox) {
		this.textbox = textbox;
	}

}
