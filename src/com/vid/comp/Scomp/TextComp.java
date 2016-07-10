package com.vid.comp.Scomp;

import java.net.URL;

import com.vid.comp.Jcomp.StaticComponent;
import com.vid.overlay.comp.master.SHAPE_TYPE;

import javafx.scene.image.Image;

public class TextComp extends StaticComponent {

	public TextComp() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(TextComp.class.getName());
		setAnnName("Text");
		setControllerClass("com.vid.controller.comp.TextAddController");
		setFXMLPath("fxml/addcompcont/Text_add_popup.fxml");
		setShapeType(SHAPE_TYPE.TEXT);

	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/Text.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	@Override
	public String toXml() {
		String annot = "<annotation id=\"" + getId() + "\" type=\"" + getAnn_type() + "\">\n";
		annot += "<starttime>" + getStartTime() + "</starttime>\n";
		annot += "<endtime> " + getEndTime() + "</endtime>";
		annot += "<comp_type>JCOMPONENT</comp_type>";
		annot += "<parameters set=\"" + 1 + "\"> \n";
		annot += "<StartX>" + getStartX() + "</StartX> \n";
		annot += "<StartY>" + getStartY() + "</StartY> \n";
		annot += "<DisplayString>" + getDisplayString() + "</DisplayString> \n";
		annot += "<DisplayStringColor> " + getDisplayStringColor() + "</DisplayStringColor> \n";
		annot += "<Underline>" + isUnderline() + "</Underline>\n";
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
		TextComp label = new TextComp();
		label.getGraphic();
	}

}
