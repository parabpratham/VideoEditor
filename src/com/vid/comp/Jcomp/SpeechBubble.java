package com.vid.comp.Jcomp;

import java.net.URL;

import javafx.scene.image.Image;

public class SpeechBubble extends AbstractComp {

	public SpeechBubble() {
		setTextIncluded(false);
		setBgImageOption(false);
		setInfopanel(true);
		setAnn_type("com.vid.comp.Jcomp.SpeechBubble");
		setAnnName("SpeechBubble");
		setControllerClass("com.vid.controller.comp.SpeechBubbleAddController");
		setFXMLPath("fxml/addcompcont/SpeechBubble_add_popup.fxml");
	}

	@Override
	public Image getGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/speech.png");
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
		annot += "<DisplayStringColor> " + getDisplayString() + " </DisplayStringColor> \n";
		annot += "<Font>" + getFont() + "</Font> \n";
		annot += "<FontSize>" + getFont_size() + "</FontSize> \n";
		annot += "<bold>" + isBold() + "</bold>\n";
		annot += "<italic>" + isItalic() + "</italic>\n";
		annot += "<strikethrough>" + isStrikethrough() + "</strikethrough>\n";
		annot += "<underline>" + isUnderline() + "</underline>\n";
		annot += "</parameters> \n";
		annot += "</annotation> \n";
		return annot;
	}

	public static void main(String[] args) {
		SpeechBubble label = new SpeechBubble();
		label.getGraphic();
	}

}
