package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class Title extends AbstractComp {

	public Title() {
		setTextIncluded(true);
		setBgImageOption(false);
		setAnn_type("com.vid.comp.Jcomp.Title");
		setAnnName("Title");
		setControllerClass("com.vid.controller.comp.TitleAddController");
		setFXMLPath("fxml/addcompcont/Title_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/TitleComment.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	@Override
	public String toXml() {
		String annot = "<annotation id=\"" + getId() + "\" type=\"" + getAnn_type() + "\">\n";
		annot += "<starttime>" + 0 + "</starttime>\n";
		annot += "<endtime> " + 0 + "</endtime>";
		annot += "<comp_type>JCOMPONENT</comp_type>";
		annot += "<parameters set=\"" + 1 + "\"> \n";
		annot += "<StartX>" + getStartX() + "</StartX> \n";
		annot += "<StartY>" + getStartY() + "</StartY> \n";
		annot += "<Popupinterval>" + getPopupinterval() + "</Popupinterval> \n";
		annot += "<Underline>" + isUnderline() + "</Underline>\n";
		annot += "<Width>" + getWidth() + "</Width> \n";
		annot += "<Height>" + getHeight() + "</Height> \n";
		annot += "<BgColor>" + getBgColor() + "</BgColor> \n";
		annot += "<DisplayString>" + getDisplayString() + "</DisplayString> \n";
		annot += "<DisplayStringColor> " + getDisplayStringColor() + "</DisplayStringColor> \n";
		annot += "<Font>" + getFont() + "</Font> \n";
		annot += "<Font_size>" + getFont_size() + "</Font_size> \n";
		annot += "<Bold>" + isBold() + "</Bold>\n";
		annot += "<Italic>" + isItalic() + "</Italic>\n";
		annot += "<Strikethrough>" + isStrikethrough() + "</Strikethrough>\n";
		annot += "<Underline>" + isUnderline() + "</Underline>\n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

	public static void main(String[] args) {
		Title label = new Title();
		label.getGraphic();
	}

}
