package com.vid.comp.Jcomp;

import java.net.URL;

import org.controlsfx.control.InfoOverlay;
import org.controlsfx.control.PopOver;
import org.controlsfx.control.PopOver.ArrowLocation;

import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class AbstractComp {

	private boolean textIncluded = false;
	private boolean resizeble = false;
	private boolean bgImageOption = false;
	private boolean infopanel = false;

	private String FXMLPath = "fxml/Ann_add_popup.fxml";
	private String controllerClass;

	private String annName;
	private String ann_type;
	private String id;

	private int startTime;
	private int endTime;

	// Bounds
	private double startX;
	private double startY;
	private double width;
	private double height;

	private String displayString;
	private String displayStringColor; // Stored as RGB,alpha
	private String font;// Stored as fontfamily
	private String font_size;

	private boolean bold = false;
	private boolean italic = false;
	private boolean strikethrough = false;
	private boolean wrapper = false;
	private boolean underline = false;

	private TextArea textbox;
	private InfoOverlay infoOverlay;

	// background
	private String bgColor; // stored as RGB
	private Double bgOpacity;
	private String bgfilepath;

	// popup time in seconds in title
	private int popupinterval;

	// spotlight
	private boolean showonhover;
	private boolean fillbg;

	// Marker
	private boolean detachable;
	private boolean initially_detached;
	private boolean auto_position;
	private ArrowLocation arrow_loaction;
	private double arrow_size;
	private double arrow_indent;
	private double corner_radius;

	private MarkerPopUp markerPopup;

	public void setBounds(double startX, double startY, double width, double height) {
		setStartX(startX);
		setStartY(startY);
		setWidth(width);
		setHeight(height);
		// System.out.println(startX + " " + startY + " " + width + " " +
		// height);
	}

	public void setBackgroundOptions(String bgColor) {
		setBgColor(bgColor);
		saveInfo();
	}

	public void setTextOptions(String displayString, String displayStringColor, String font, String font_size,
			boolean bold, boolean italic, boolean strikethrough, boolean underline) {
		setDisplayString(displayString);
		setDisplayStringColor(displayStringColor);
		setFont(font);
		setFont_size(font_size);
		setBold(bold);
		setItalic(italic);
		setStrikethrough(strikethrough);
		setUnderline(underline);

	}

	public void setBackgroundOptions(String bgColor, Double alpha, String bgfilepath) {
		setBgColor(bgColor);
		setAlpha(alpha);
		setBgfilepath(bgfilepath);
		saveInfo();
	}

	public void setMarkerProperties(boolean detachable, boolean initially_detached, boolean auto_position,
			ArrowLocation arrow_loaction, double arrow_size, double arrow_indent, double corner_radius) {
		setDetachable(detachable);
		setInitially_detached(initially_detached);
		setAuto_position(auto_position);
		setArrow_indent(arrow_indent);
		setArrow_loaction(arrow_loaction);
		setArrow_size(arrow_size);
		setCorner_radius(corner_radius);
		saveInfo();
	}

	public void addComponent(BoundedRectangle baseRectWithScroll) {
	}

	public void saveInfo() {
		System.out.println(toXml());
	}

	public String toXml() {
		return null;
	}

	public String getDisplayString() {
		return displayString;
	}

	public void setDisplayString(String displayString) {
		this.displayString = displayString;
	}

	public String getDisplayStringColor() {
		return displayStringColor;
	}

	public void setDisplayStringColor(String displayStringColor) {
		this.displayStringColor = displayStringColor;
	}

	public String getFont() {
		return font;
	}

	public void setFont(String font) {
		this.font = font;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public boolean isResizeble() {
		return resizeble;
	}

	public void setResizeble(boolean resizeble) {
		this.resizeble = resizeble;
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

	public String getBgColor() {
		return bgColor;
	}

	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getFont_size() {
		return font_size;
	}

	public void setFont_size(String font_size) {
		this.font_size = font_size;
	}

	public boolean isBold() {
		return bold;
	}

	public void setBold(boolean bold) {
		this.bold = bold;
	}

	public boolean isItalic() {
		return italic;
	}

	public void setItalic(boolean italic) {
		this.italic = italic;
	}

	public boolean isStrikethrough() {
		return strikethrough;
	}

	public void setStrikethrough(boolean strikethrough) {
		this.strikethrough = strikethrough;
	}

	public boolean isWrapper() {
		return wrapper;
	}

	public void setWrapper(boolean wrapper) {
		this.wrapper = wrapper;
	}

	public boolean isUnderline() {
		return underline;
	}

	public void setUnderline(boolean underline) {
		this.underline = underline;
	}

	public boolean isTextIncluded() {
		return textIncluded;
	}

	public void setTextIncluded(boolean textIncluded) {
		this.textIncluded = textIncluded;
	}

	public boolean isBgImageOption() {
		return bgImageOption;
	}

	public void setBgImageOption(boolean bgImageOption) {
		this.bgImageOption = bgImageOption;
	}

	public TextArea getTextbox() {
		return textbox;
	}

	public void setTextbox(TextArea textbox) {
		this.textbox = textbox;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}

	public Double getAlpha() {
		return bgOpacity;
	}

	public void setAlpha(Double alpha) {
		this.bgOpacity = alpha;
	}

	public String getBgfilepath() {
		return bgfilepath;
	}

	public void setBgfilepath(String bgfilepath) {
		this.bgfilepath = bgfilepath;
	}

	public String getAnn_type() {
		return ann_type;
	}

	public void setAnn_type(String ann_type) {
		this.ann_type = ann_type;
	}

	// overriden by each component to get the icon for list
	public Image getGraphic() {
		return null;
	}

	public String getAnnName() {
		return annName;
	}

	public void setAnnName(String annName) {
		this.annName = annName;
	}

	public String getFXMLPath() {
		return FXMLPath;
	}

	public void setFXMLPath(String fXMLPath) {
		FXMLPath = fXMLPath;
	}

	public Double getBgOpacity() {
		return bgOpacity;
	}

	public void setBgOpacity(Double bgOpacity) {
		this.bgOpacity = bgOpacity;
	}

	public String getControllerClass() {
		return controllerClass;
	}

	public void setControllerClass(String controllerClass) {
		this.controllerClass = controllerClass;
	}

	public int getPopupinterval() {
		return popupinterval;
	}

	public void setPopupinterval(int popupinterval) {
		this.popupinterval = popupinterval;
	}

	public Image getDeleteGraphic() {
		URL resource = getClass().getClassLoader().getResource("icons/glyphicons-17-bin.png");
		Image image = new Image("file:" + resource.getPath());
		return image;
	}

	public InfoOverlay getInfoOverlay() {
		return infoOverlay;
	}

	public void setInfoOverlay(InfoOverlay infoOverlay) {
		this.infoOverlay = infoOverlay;
	}

	public boolean isInfopanel() {
		return infopanel;
	}

	public void setInfopanel(boolean infopanel) {
		this.infopanel = infopanel;
	}

	public boolean isShowonhover() {
		return showonhover;
	}

	public void setShowonhover(boolean showonhover) {
		this.showonhover = showonhover;
	}

	public boolean isFillbg() {
		return fillbg;
	}

	public void setFillbg(boolean fillbg) {
		this.fillbg = fillbg;
	}

	public boolean isDetachable() {
		return detachable;
	}

	public void setDetachable(boolean detachable) {
		this.detachable = detachable;
	}

	public boolean isInitially_detached() {
		return initially_detached;
	}

	public void setInitially_detached(boolean initially_detached) {
		this.initially_detached = initially_detached;
	}

	public boolean isAuto_position() {
		return auto_position;
	}

	public void setAuto_position(boolean auto_position) {
		this.auto_position = auto_position;
	}

	public ArrowLocation getArrow_loaction() {
		return arrow_loaction;
	}

	public void setArrow_loaction(ArrowLocation arrow_loaction) {
		this.arrow_loaction = arrow_loaction;
	}

	public double getArrow_size() {
		return arrow_size;
	}

	public void setArrow_size(double arrow_size) {
		this.arrow_size = arrow_size;
	}

	public double getArrow_indent() {
		return arrow_indent;
	}

	public void setArrow_indent(double arrow_indent) {
		this.arrow_indent = arrow_indent;
	}

	public double getCorner_radius() {
		return corner_radius;
	}

	public void setCorner_radius(double corner_radius) {
		this.corner_radius = corner_radius;
	}

	// Added class for the Marker control
	public class MarkerPopUp {

		ImageView view;
		PopOver popOver;
		TextArea contentText;

		public MarkerPopUp(ImageView view, PopOver popOver) {
			this.view = view;
			this.popOver = popOver;
			contentText = new TextArea();
			contentText.setMaxHeight(100);
			contentText.setMaxWidth(100);
			contentText.setWrapText(true);
			contentText.setDisable(true);
		}

		public ImageView getView() {
			return view;
		}

		public void setView(ImageView view) {
			this.view = view;
		}

		public PopOver getPopOver() {
			return popOver;
		}

		public void setPopOver(PopOver popOver) {
			this.popOver = popOver;
		}

		public void setContentText(TextArea contentText) {
			this.contentText = contentText;
		}

		public TextArea getContentText() {
			return contentText;
		}

	}

	public void setMarkerPopup(MarkerPopUp markerPopup) {
		this.markerPopup = markerPopup;
	}

	public MarkerPopUp getMarkerPopup() {
		return markerPopup;
	}

}
