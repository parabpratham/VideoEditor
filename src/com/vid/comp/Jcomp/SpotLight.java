package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class SpotLight extends AbstractComp {

	public SpotLight() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type("com.vid.comp.Jcomp.SpotLight");
		setAnnName("SpotLight");
		setControllerClass("com.vid.controller.comp.SpotLightAddController");
		setFXMLPath("fxml/addcompcont/SpotLight_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/spot_light.png");
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
		annot += "<Bgfilepath>" + getBgfilepath() + "</Bgfilepath> \n";
		annot += "<Showonhover>" + isShowonhover() + "</Showonhover> \n";
		annot += "<Fillbg>" + isFillbg() + "</Fillbg> \n";
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
		SpotLight label = new SpotLight();
		label.getGraphic();
	}

}
