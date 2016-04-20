package com.vid.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import com.vid.comp.Jcomp.AbstractComp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class AddAnnoController implements Initializable {

	// Abstract comp
	private AbstractComp comp;

	@FXML
	private Tab tab_text;
	@FXML
	private TextField anntype;
	@FXML
	private ColorPicker bgcolor;
	@FXML
	private TextField bgfilepath;
	@FXML
	private Button filechooser;
	@FXML
	private TextField width;
	@FXML
	private TextField height;
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
	private ToggleButton wraptext;
	@FXML
	private ToggleButton font_bold;
	@FXML
	private ToggleButton font_U;
	@FXML
	private ToggleButton font_I;
	@FXML
	private ToggleButton font_strikeout;
	@FXML
	private Button jestifyalign;
	@FXML
	private Button leftalign;
	@FXML
	private Button centeralign;
	@FXML
	private Button rightalign;
	@FXML
	private GridPane gridAnno;
	@FXML
	private Button save;
	@FXML
	private Button cancel;

	private static List<String> families = javafx.scene.text.Font.getFamilies();

	private static Integer[] font_array = { 8, 9, 10, 11, 12, 14, 16, 18, 24, 32, 44 };

	private Font font;

	public AddAnnoController() {
		font = new Font("Arial", 10);

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList fontList;
		fontList = FXCollections.observableList(families);
		ObservableList fontsize_List;
		List<Integer> fontsizelist = new ArrayList<>();
		Collections.addAll(fontsizelist, font_array);
		fontsize_List = FXCollections.observableList(fontsizelist);
		fonts.setItems(fontList);
		font_size.setItems(fontsize_List);

		fonts.getSelectionModel().selectFirst();
		font_size.getSelectionModel().selectFirst();

		width.setDisable(true);
		height.setDisable(true);
		anntype.setDisable(true);

		getOpacity().setValue(100);

	}

	public Tab getTab_text() {
		return tab_text;
	}

	public void setTab_text(Tab tab_text) {
		this.tab_text = tab_text;
	}

	public TextField getAnntype() {
		return anntype;
	}

	public void setAnntype(TextField anntype) {
		this.anntype = anntype;
	}

	public ColorPicker getBgcolor() {
		return bgcolor;
	}

	public void setBgcolor(ColorPicker bgcolor) {
		this.bgcolor = bgcolor;
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

	public TextField getWidth() {
		return width;
	}

	public void setWidth(TextField width) {
		this.width = width;
	}

	public TextField getHeight() {
		return height;
	}

	public void setHeight(TextField height) {
		this.height = height;
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

	public ToggleButton getWraptext() {
		return wraptext;
	}

	public void setWraptext(ToggleButton wraptext) {
		this.wraptext = wraptext;
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

	public Button getJestifyalign() {
		return jestifyalign;
	}

	public void setJestifyalign(Button jestifyalign) {
		this.jestifyalign = jestifyalign;
	}

	public Button getLeftalign() {
		return leftalign;
	}

	public void setLeftalign(Button leftalign) {
		this.leftalign = leftalign;
	}

	public Button getCenteralign() {
		return centeralign;
	}

	public void setCenteralign(Button centeralign) {
		this.centeralign = centeralign;
	}

	public Button getRightalign() {
		return rightalign;
	}

	public void setRightalign(Button rightalign) {
		this.rightalign = rightalign;
	}

	public GridPane getGridAnno() {
		return gridAnno;
	}

	public void setGridAnno(GridPane gridAnno) {
		this.gridAnno = gridAnno;
	}

	public Button getSave() {
		return save;
	}

	public void setSave(Button save) {
		this.save = save;
	}

	public Button getCancel() {
		return cancel;
	}

	public void setCancel(Button cancel) {
		this.cancel = cancel;
	}

	public ComboBox<String> getFonts() {
		return fonts;
	}

	public void setFonts(ComboBox<String> fonts) {
		this.fonts = fonts;
	}

	public Font getFont() {
		return font;
	}

	public Font getFont(String family, int fontSizeIndex) {
		font = new Font(family, font_array[fontSizeIndex]);
		return font;
	}

	public Font getFont(int fontIndex) {
		font = new Font(families.get(fontIndex), font.getSize());
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public AbstractComp getComp() {
		return comp;
	}

	public void setComp(AbstractComp comp) {
		this.comp = comp;
	}

	public static List<String> getFamilies() {
		return families;
	}

	public static void setFamilies(List<String> families) {
		AddAnnoController.families = families;
	}

	public static Integer[] getFont_array() {
		return font_array;
	}

	public static void setFont_array(Integer[] font_array) {
		AddAnnoController.font_array = font_array;
	}
}
