package com.vid.controller.comp;

import com.vid.controller.AbstractAddController;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Slider;

public class LineAddController extends AbstractAddController {
	@FXML
	private ColorPicker bgcolor;
	@FXML
	private Slider opacity;
	@FXML
	private CheckBox fillShape;
	
	//Length is taken as width

	public CheckBox getFillShape() {
		return fillShape;
	}

	public void setFillShape(CheckBox fillShape) {
		this.fillShape = fillShape;
	}

	public ColorPicker getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(ColorPicker bgcolor) {
		this.bgcolor = bgcolor;
	}

	public Slider getOpacity() {
		return opacity;
	}

	public void setOpacity(Slider opacity) {
		this.opacity = opacity;
	}

}
