package com.vid.comp.Scomp;

import java.net.URL;

import com.vid.comp.Jcomp.StaticComponent;
import com.vid.overlay.comp.master.SHAPE_TYPE;

import javafx.scene.image.Image;

public class CircleComp extends StaticComponent {

	private double radius;
	private double centerX;
	private double centerY;

	public double getCenterX() {
		return centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public CircleComp() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(CircleComp.class.getName());
		setAnnName("Circle");
		setControllerClass("com.vid.controller.comp.CircleAddController");
		setFXMLPath("fxml/addcompcont/Circle_add_popup.fxml");
		setShapeType(SHAPE_TYPE.CIRCLE);

	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/circle95.png");
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
		annot += "<CenterX>" + getCenterX() + "</CenterX> \n";
		annot += "<CenterY>" + getCenterY() + "</CenterY> \n";
		annot += "<Radius>" + getRadius() + "</Radius> \n";
		annot += "<BgColor>" + getBgColor() + "</BgColor> \n";
		annot += "<FillShape>" + isFillShape() + "</FillShape> \n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

}
