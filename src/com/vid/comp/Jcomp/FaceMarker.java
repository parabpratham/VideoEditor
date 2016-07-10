package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class FaceMarker extends AbstractComp {

	public FaceMarker() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(AnnotationMarkers.class.getName());
		setAnnName("Face Marker");
		setControllerClass("com.vid.controller.comp.FaceMarkersAddController");
		setFXMLPath("fxml/addcompcont/Markers_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/facetag.png");
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
		annot += "<DisplayString>" + getDisplayString() + "</DisplayString> \n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

}
