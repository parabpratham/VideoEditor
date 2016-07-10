package com.vid.controller.comp;

import java.net.URL;
import java.util.ResourceBundle;

import com.vid.controller.AbstractAddController;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class SpotLightAddController extends AbstractAddController {

	@FXML
	private ColorPicker bgcolor;
	@FXML
	private Slider opacity;

	@FXML
	private Tab tab_text;
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
	@FXML
	private CheckBox showonhover;
	@FXML
	private CheckBox fillbg;
	@FXML
	private TextField bgfilepath;
	@FXML
	private Button filechooser;

	public SpotLightAddController() {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		super.initialize(location, resources);

		fonts.setItems(FXCollections.observableList(getFamilies()));
		font_size.setItems(FXCollections.observableList(getFontsizelist()));

		fonts.getSelectionModel().selectFirst();
		font_size.getSelectionModel().selectFirst();

		getShowonhover().setSelected(true);

	}

	public Tab getTab_text() {
		return tab_text;
	}

	public void setTab_text(Tab tab_text) {
		this.tab_text = tab_text;
	}

	public TextArea getTextbox() {
		return textbox;
	}

	public void setTextbox(TextArea textbox) {
		this.textbox = textbox;
	}

	public ChoiceBox<Integer> getFont_size() {
		System.out.println("Note getFontSize");
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

	public ComboBox<String> getFonts() {
		return fonts;
	}

	public void setFonts(ComboBox<String> fonts) {
		this.fonts = fonts;
	}

	public TextField getBgfilepath() {
		return bgfilepath;
	}

	public void setBgfilepath(TextField bgfilepath) {
		this.bgfilepath = bgfilepath;
	}

	public Button getFilechooser() {
		return filechooser;
	}

	public void setFilechooser(Button filechooser) {
		this.filechooser = filechooser;
	}

	public void setShowonhover(CheckBox showonhover) {
		this.showonhover = showonhover;
	}

	public void setFillbg(CheckBox fillbg) {
		this.fillbg = fillbg;
	}

	public CheckBox getShowonhover() {
		return showonhover;
	}

	public CheckBox getFillbg() {
		return fillbg;
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
