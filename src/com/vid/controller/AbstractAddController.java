package com.vid.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.PopOver.ArrowLocation;

import com.vid.comp.Jcomp.AbstractComp;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AbstractAddController implements Initializable {

	// Abstract comp
	private AbstractComp comp;

	@FXML
	private TextField anntype;

	@FXML
	private TextField width;
	@FXML
	private TextField height;

	@FXML
	private GridPane gridAnno;
	@FXML
	private Button save;
	@FXML
	private Button cancel;

	private ColorPicker bgcolor;
	private Slider opacity;
	private Tab tab_text;
	private TextArea textbox;
	private ComboBox<String> fonts;
	private ChoiceBox<Integer> font_size;
	private ColorPicker font_colour;
	private ToggleButton font_bold;
	private ToggleButton font_U;
	private ToggleButton font_I;
	private ToggleButton font_strikeout;
	private TextField bgfilepath;
	private Button filechooser;
	private TextField popupinterval;
	private CheckBox showonhover;
	private CheckBox fillbg;
	private Slider arrow_size;
	private Slider arrow_indent;
	private Slider corner_radius;
	private CheckBox initially_detached;
	private CheckBox detachable;
	private ComboBox<ArrowLocation> arrow_loaction;
	private CheckBox fillShape;

	public CheckBox getFillShape() {
		return fillShape;
	}

	public void setFillShape(CheckBox fillShape) {
		this.fillShape = fillShape;
	}

	private static List<String> families = javafx.scene.text.Font.getFamilies();

	private static Integer[] font_array = { 8, 9, 10, 11, 12, 14, 16, 18, 32, 36 };

	private static List<Integer> fontsizelist = null;

	private Font font;

	public AbstractAddController() {
		font = new Font("Arial", 10);
		if (fontsizelist == null) {
			fontsizelist = new ArrayList<>();
			Collections.addAll(fontsizelist, font_array);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (getTextbox() != null)
			getTextbox().setFont(font);

		if (getFont_colour() != null)
			getFont_colour().setValue(Color.BLACK);

		if (getBgcolor() != null)
			getBgcolor().setValue(Color.WHITE);

		if (getFonts() != null) {
			getFonts().setValue("Arial");
			getFont_size().setValue(10);
			getFonts().setItems(FXCollections.observableList(getFamilies()));
			getFont_size().setItems(FXCollections.observableList(getFontsizelist()));
		}

		if(width!=null){
		width.setDisable(true);
		height.setDisable(true);
		}
		anntype.setDisable(true);

		if (opacity != null) {
			opacity.setMax(1.0);
			opacity.setMin(0);
			opacity.setValue(50);
		}

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

	public Font getFont() {
		return font;
	}

	public Font getFont(boolean newVal) {
		FontPosture fp = getFont_I().isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
		FontWeight fw = getFont_bold().isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
		font = Font.font(font.getFamily(), fw, fp, font.getSize());
		System.out.println(font);
		return font;

	}

	public Font getFont(String family, int fontSizeIndex) {
		FontPosture fp = getFont_I().isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
		FontWeight fw = getFont_bold().isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
		font = Font.font(family, fw, fp, font_array[fontSizeIndex]);
		return font;
	}

	public Font getFont(int fontIndex) {
		FontPosture fp = getFont_I().isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
		FontWeight fw = getFont_bold().isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
		font = Font.font(families.get(fontIndex), fw, fp, font.getSize());
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
		AbstractAddController.families = families;
	}

	public static Integer[] getFont_array() {
		return font_array;
	}

	public static void setFont_array(Integer[] font_array) {
		AbstractAddController.font_array = font_array;
	}

	public static List<Integer> getFontsizelist() {
		return fontsizelist;
	}

	public static void setFontsizelist(List<Integer> fontsizelist) {
		AbstractAddController.fontsizelist = fontsizelist;
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

	public TextField getPopupinterval() {
		return popupinterval;
	}

	public void setPopupinterval(TextField popupinterval) {
		this.popupinterval = popupinterval;
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

	public Slider getArrow_size() {
		return arrow_size;
	}

	public void setArrow_size(Slider arrow_size) {
		this.arrow_size = arrow_size;
	}

	public Slider getArrow_indent() {
		return arrow_indent;
	}

	public void setArrow_indent(Slider arrow_indent) {
		this.arrow_indent = arrow_indent;
	}

	public Slider getCorner_radius() {
		return corner_radius;
	}

	public void setCorner_radius(Slider corner_radius) {
		this.corner_radius = corner_radius;
	}

	public CheckBox getInitially_detached() {
		return initially_detached;
	}

	public void setInitially_detached(CheckBox initially_detached) {
		this.initially_detached = initially_detached;
	}

	public CheckBox getDetachable() {
		return detachable;
	}

	public void setDetachable(CheckBox detachable) {
		this.detachable = detachable;
	}

	public ComboBox<ArrowLocation> getArrow_loaction() {
		return arrow_loaction;
	}

	public void setArrow_loaction(ComboBox<ArrowLocation> arrow_loaction) {
		this.arrow_loaction = arrow_loaction;
	}
}
