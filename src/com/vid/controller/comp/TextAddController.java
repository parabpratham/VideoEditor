package com.vid.controller.comp;

import java.net.URL;
import java.util.ResourceBundle;

import com.vid.controller.AbstractAddController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;

public class TextAddController extends AbstractAddController {

	@FXML
	private Slider opacity;
	@FXML
	private TextArea textbox;
	@FXML
	private ComboBox<String> fonts;
	@FXML
	private ChoiceBox<Integer> font_size;
	@FXML
	private ColorPicker font_colour;
	@FXML
	private ToggleButton font_bold;
	@FXML
	private ToggleButton font_U;
	@FXML
	private ToggleButton font_I;
	@FXML
	private ToggleButton font_strikeout;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		super.initialize(location, resources);

		fonts.setItems(FXCollections.observableList(getFamilies()));
		font_size.setItems(FXCollections.observableList(getFontsizelist()));
	}

	public Slider getOpacity() {
		return opacity;
	}

	public void setOpacity(Slider opacity) {
		this.opacity = opacity;
	}

	public TextArea getTextbox() {
		return textbox;
	}

	public void setTextbox(TextArea textbox) {
		this.textbox = textbox;
	}

	public ComboBox<String> getFonts() {
		return fonts;
	}

	public void setFonts(ComboBox<String> fonts) {
		this.fonts = fonts;
	}

	public ChoiceBox<Integer> getFont_size() {
		return font_size;
	}

	public void setFont_size(ChoiceBox<Integer> font_size) {
		this.font_size = font_size;
	}

	public ColorPicker getFont_colour() {
		return font_colour;
	}

	public void setFont_colour(ColorPicker font_colour) {
		this.font_colour = font_colour;
	}

	public ToggleButton getFont_bold() {
		return font_bold;
	}

	public void setFont_bold(ToggleButton font_bold) {
		this.font_bold = font_bold;
	}

	public ToggleButton getFont_U() {
		return font_U;
	}

	public void setFont_U(ToggleButton font_U) {
		this.font_U = font_U;
	}

	public ToggleButton getFont_I() {
		return font_I;
	}

	public void setFont_I(ToggleButton font_I) {
		this.font_I = font_I;
	}

	public ToggleButton getFont_strikeout() {
		return font_strikeout;
	}

	public void setFont_strikeout(ToggleButton font_strikeout) {
		this.font_strikeout = font_strikeout;
	}

}
