package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class AnnotationMarkers extends AbstractComp {

	public AnnotationMarkers() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(AnnotationMarkers.class.getName());
		setAnnName("Marker");
		setControllerClass("com.vid.controller.comp.MarkersAddController");
		setFXMLPath("fxml/addcompcont/Markers_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/marker.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	@Override
	public String toXml() {
		String annot = "<annotation id=\"" + getId() + "\" \n type=\"" + getAnn_type() + "\">\n";
		annot += "<starttime>" + getStartTime() + "</starttime>\n";
		annot += "<endtime> " + getEndTime() + "</endtime>";
		annot += "<comp_type>JCOMPONENT</comp_type>";
		annot += "<parameters set=\"" + 1 + "\"> \n";
		annot += "<StartX>" + getStartX() + "</StartX> \n";
		annot += "<StartY>" + getStartY() + "</StartY> \n";
		annot += "<Width>" + getWidth() + "</Width> \n";
		annot += "<Height>" + getHeight() + "</Height> \n";
		annot += "<Bgfilepath>" + getBGFileName() + "</Bgfilepath> \n";
		annot += "<DisplayString>" + getDisplayString() + "</DisplayString> \n";
		annot += "<DisplayStringColor>" + getDisplayStringColor() + "</DisplayStringColor> \n";
		annot += "<Font>" + getFont() + "</Font> \n";
		annot += "<Font_size>" + getFont_size() + "</Font_size> \n";
		annot += "<Bold>" + isBold() + "</Bold>\n";
		annot += "<Italic>" + isItalic() + "</Italic>\n";
		annot += "<Strikethrough>" + isStrikethrough() + "</Strikethrough>\n";
		annot += "<Detachable>" + isDetachable() + "</Detachable>\n";
		annot += "<Initially_detached>" + isInitially_detached() + "</Initially_detached>\n";
		annot += "<Auto_position>" + isAuto_position() + "</Auto_position>\n";
		annot += "<Arrow_indent>" + getArrow_indent() + "</Arrow_indent>\n";
		annot += "<Arrow_size>" + getArrow_size() + "</Arrow_size>\n";
		annot += "<Corner_radius>" + getCorner_radius() + "</Corner_radius>\n";
		annot += "<Arrow_loaction>" + getArrow_loaction() + "</Arrow_loaction>\n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

}
