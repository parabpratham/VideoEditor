package com.vid.comp.Scomp;

import java.net.URL;

import com.vid.comp.Jcomp.StaticComponent;
import com.vid.overlay.comp.master.SHAPE_TYPE;

import javafx.scene.image.Image;

public class LineComp extends StaticComponent {

	private double length;

	public LineComp() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(LineComp.class.getName());
		setAnnName("Line");
		setControllerClass("com.vid.controller.comp.LineAddController");
		setFXMLPath("fxml/addcompcont/Line_add_popup.fxml");
		setShapeType(SHAPE_TYPE.LINE);

	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/line.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	@Override
	public String toXml() {
		String annot = "<annotation id=\"" + getId() + "\" \n type=\"" + getAnn_type() + "\">\n";
		annot += "<starttime>" + getStartTime() + "</starttime>\n";
		annot += "<endtime>" + getEndTime() + "</endtime>\n";
		annot += "<comp_type>SCOMPONENT</comp_type>\n";
		annot += "<parameters set=\"" + 1 + "\"> \n";
		annot += "<StartX>" + getStartX() + "</StartX> \n";
		annot += "<StartY>" + getStartY() + "</StartY> \n";
		annot += "<EndX>" + getEndX() + "</EndX> \n";
		annot += "<EndY>" + getEndY() + "</EndY> \n";
		annot += "<Height>" + getHeight() + "</Height> \n";
		annot += "<BgColor>" + getBgColor() + "</BgColor> \n";
		annot += "<FillShape>" + isFillShape() + "</FillShape> \n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

	private double getEndY() {
		return getStartY();
	}

	private double getEndX() {
		return getStartX() + getLength();
	}

	public double getLength() {
		return length;
	}

	public void setLength(double length) {
		this.length = length;
	}

}
