package com.vid.comp.Scomp;

import java.net.URL;

import com.vid.comp.Jcomp.StaticComponent;
import com.vid.overlay.comp.master.SHAPE_TYPE;

import javafx.scene.image.Image;

public class RectangleComp extends StaticComponent {

	private double width;
	private double height;
	private double startX;
	private double startY;

	public RectangleComp() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type(RectangleComp.class.getName());
		setAnnName("Rectangle");
		setControllerClass("com.vid.controller.comp.RectangleAddController");
		setFXMLPath("fxml/addcompcont/Rectangle_add_popup.fxml");
		setShapeType(SHAPE_TYPE.RECTANGLE);

	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/rectangular43.png");
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
		annot += "<Width>" + getWidth() + "</Width> \n";
		annot += "<Height>" + getHeight() + "</Height> \n";
		annot += "<BgColor>" + getBgColor() + "</BgColor> \n";
		annot += "<FillShape>" + isFillShape() + "</FillShape> \n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getStartX() {
		return startX;
	}

	public void setStartX(double startX) {
		this.startX = startX;
	}

	public double getStartY() {
		return startY;
	}

	public void setStartY(double startY) {
		this.startY = startY;
	}

}
