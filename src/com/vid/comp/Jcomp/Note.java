package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class Note extends AbstractComp {

	public Note() {
		setTextIncluded(true);
		setBgImageOption(false);
		setAnn_type("com.vid.comp.Jcomp.Note");
		setAnnName("Note");
		setControllerClass("com.vid.controller.comp.NoteAddController");
		setFXMLPath("fxml/addcompcont/Note_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/note.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	@Override
	public String toXml() {
		String annot = "<annotation id=\"" + getId() + "\" \n type=\"" + getAnn_type() + "\">\n" + "<starttime>"
				+ getStartTime() + "</starttime>\n";
		annot += "<endtime> " + getEndTime() + "</endtime>";
		annot += "<comp_type>JCOMPONENT</comp_type>";
		annot += "<parameters set=\"" + 1 + "\"> \n";
		annot += "<StartX> " + getStartX() + "</StartX> \n";
		annot += "<StartY>" + getStartY() + "</StartY> \n";
		annot += "<Width>" + getWidth() + "</Width> \n";
		annot += "<Height>" + getHeight() + "</Height> \n";
		annot += "<BgColor>" + getBgColor() + "</BgColor> \n";
		annot += "<DisplayString> " + getDisplayString() + " </DisplayString> \n";
		annot += "<DisplayStringColor> " + getDisplayStringColor() + " </DisplayStringColor> \n";
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
		Note label = new Note();
		label.getGraphic();
	}

}
