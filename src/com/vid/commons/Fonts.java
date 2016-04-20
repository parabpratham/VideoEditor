 package com.vid.commons;

import java.awt.Font;
import java.awt.GraphicsEnvironment;

public class Fonts {

	private static final String[] families = GraphicsEnvironment.getLocalGraphicsEnvironment()
			.getAvailableFontFamilyNames();

	private static final String[] stylenames = { "Plain", "Italic", "Bold", "Bold Italic" };

	private static Font appFont;

	private static String[] fonts;

	private static String[] fontIndex;

	public Fonts() {
		fonts = new String[families.length];
		fontIndex = new String[families.length];
		int fontId = 0;
		for (int f = 0; f < families.length; f++) { // for each family
			fonts[fontId] = families[f];
			fontIndex[fontId] = "" + fontId;
			fontId++;
		}
	}

	public static Font getFont(String name, int style, int size) {
		return new Font(name, style, size);
	}

	public static Font getAppFont() {
		if (appFont == null) {
			appFont = getFont(SANSERIF, Font.BOLD, 18);
		}
		return appFont;
	}

	public static void setAppFont(Font appFont) {
		Fonts.appFont = appFont;
	}

	public static void main(String[] args) {
		Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();

		for (Font font : allFonts) {
			System.out.println("public static final String " + font.getFamily().replace(" ", "_").toUpperCase()
					+ " = \"" + font.getFamily() + "\";");
		}
	}

	public static final String SANSERIF = "Sansserif";

	public static final String AGENCY_FB = "Agency FB";
	public static final String ALGERIAN = "Algerian";
	public static final String ARIAL = "Arial";
	public static final String ARIAL_BLACK = "Arial Black";
	public static final String ARIAL_NARROW = "Arial Narrow";
	public static final String ARIAL_ROUNDED_MT_BOLD = "Arial Rounded MT Bold";
	public static final String ARIAL_UNICODE_MS = "Arial Unicode MS";
	public static final String BASKERVILLE_OLD_FACE = "Baskerville Old Face";
	public static final String BAUHAUS_93 = "Bauhaus 93";
	public static final String BELL_MT = "Bell MT";
	public static final String BERLIN_SANS_FB = "Berlin Sans FB";
	public static final String BERLIN_SANS_FB_DEMI = "Berlin Sans FB Demi";
	public static final String BERNARD_MT_CONDENSED = "Bernard MT Condensed";
	public static final String BLACKADDER_ITC = "Blackadder ITC";
	public static final String BODONI_MT_BLACK = "Bodoni MT Black";
	public static final String BODONI_MT = "Bodoni MT";
	public static final String BODONI_MT_CONDENSED = "Bodoni MT Condensed";
	public static final String BODONI_MT_POSTER_COMPRESSED = "Bodoni MT Poster Compressed";
	public static final String BOOK_ANTIQUA = "Book Antiqua";
	public static final String BOOKMAN_OLD_STYLE = "Bookman Old Style";
	public static final String BRADLEY_HAND_ITC = "Bradley Hand ITC";
	public static final String BRITANNIC_BOLD = "Britannic Bold";
	public static final String BROADWAY = "Broadway";
	public static final String BRUSH_SCRIPT_MT = "Brush Script MT";
	public static final String CALIBRI = "Calibri";
	public static final String CALIBRI_LIGHT = "Calibri Light";
	public static final String CALIFORNIAN_FB = "Californian FB";
	public static final String CALISTO_MT = "Calisto MT";
	public static final String CAMBRIA = "Cambria";
	public static final String CAMBRIA_MATH = "Cambria Math";
	public static final String CANDARA = "Candara";
	public static final String CASTELLAR = "Castellar";
	public static final String CENTAUR = "Centaur";
	public static final String CENTURY = "Century";
	public static final String CENTURY_GOTHIC = "Century Gothic";
	public static final String CENTURY_SCHOOLBOOK = "Century Schoolbook";
	public static final String CHILLER = "Chiller";
	public static final String COLONNA_MT = "Colonna MT";
	public static final String COMIC_SANS_MS = "Comic Sans MS";
	public static final String CONSOLAS = "Consolas";
	public static final String CONSTANTIA = "Constantia";
	public static final String COOPER_BLACK = "Cooper Black";
	public static final String COPPERPLATE_GOTHIC_BOLD = "Copperplate Gothic Bold";
	public static final String COPPERPLATE_GOTHIC_LIGHT = "Copperplate Gothic Light";
	public static final String CORBEL = "Corbel";
	public static final String COURIER_NEW = "Courier New";
	public static final String CURLZ_MT = "Curlz MT";
	public static final String DIALOG = "Dialog";
	public static final String DIALOGINPUT = "DialogInput";
	public static final String EBRIMA = "Ebrima";
	public static final String EDWARDIAN_SCRIPT_ITC = "Edwardian Script ITC";
	public static final String ELEPHANT = "Elephant";
	public static final String ENGRAVERS_MT = "Engravers MT";
	public static final String ERAS_BOLD_ITC = "Eras Bold ITC";
	public static final String ERAS_DEMI_ITC = "Eras Demi ITC";
	public static final String ERAS_LIGHT_ITC = "Eras Light ITC";
	public static final String ERAS_MEDIUM_ITC = "Eras Medium ITC";
	public static final String FELIX_TITLING = "Felix Titling";
	public static final String FOOTLIGHT_MT_LIGHT = "Footlight MT Light";
	public static final String FORTE = "Forte";
	public static final String FRANKLIN_GOTHIC_BOOK = "Franklin Gothic Book";
	public static final String FRANKLIN_GOTHIC_DEMI = "Franklin Gothic Demi";
	public static final String FRANKLIN_GOTHIC_DEMI_COND = "Franklin Gothic Demi Cond";
	public static final String FRANKLIN_GOTHIC_HEAVY = "Franklin Gothic Heavy";
	public static final String FRANKLIN_GOTHIC_MEDIUM = "Franklin Gothic Medium";
	public static final String FRANKLIN_GOTHIC_MEDIUM_COND = "Franklin Gothic Medium Cond";
	public static final String FREESTYLE_SCRIPT = "Freestyle Script";
	public static final String FRENCH_SCRIPT_MT = "French Script MT";
	public static final String GABRIOLA = "Gabriola";
	public static final String GADUGI = "Gadugi";
	public static final String GARAMOND = "Garamond";
	public static final String GEORGIA = "Georgia";
	public static final String GIGI = "Gigi";
	public static final String GILL_SANS_MT = "Gill Sans MT";
	public static final String GILL_SANS_MT_CONDENSED = "Gill Sans MT Condensed";
	public static final String GILL_SANS_MT_EXT_CONDENSED_BOLD = "Gill Sans MT Ext Condensed Bold";
	public static final String GILL_SANS_ULTRA_BOLD = "Gill Sans Ultra Bold";
	public static final String GILL_SANS_ULTRA_BOLD_CONDENSED = "Gill Sans Ultra Bold Condensed";
	public static final String GLOUCESTER_MT_EXTRA_CONDENSED = "Gloucester MT Extra Condensed";
	public static final String GOUDY_OLD_STYLE = "Goudy Old Style";
	public static final String GOUDY_STOUT = "Goudy Stout";
	public static final String HP_SIMPLIFIED = "HP Simplified";
	public static final String HP_SIMPLIFIED_LIGHT = "HP Simplified Light";
	public static final String HAETTENSCHWEILER = "Haettenschweiler";
	public static final String HARLOW_SOLID_ITALIC = "Harlow Solid Italic";
	public static final String HARRINGTON = "Harrington";
	public static final String HIGH_TOWER_TEXT = "High Tower Text";
	public static final String IMPACT = "Impact";
	public static final String IMPRINT_MT_SHADOW = "Imprint MT Shadow";
	public static final String INFORMAL_ROMAN = "Informal Roman";
	public static final String JAVANESE_TEXT = "Javanese Text";
	public static final String JOKERMAN = "Jokerman";
	public static final String JUICE_ITC = "Juice ITC";
	public static final String KRISTEN_ITC = "Kristen ITC";
	public static final String KRUTI_DEV_050 = "Kruti Dev 050";
	public static final String KRUTI_DEV_140 = "Kruti Dev 140";
	public static final String KRUTI_DEV_400 = "Kruti Dev 400";
	public static final String KUNSTLER_SCRIPT = "Kunstler Script";
	public static final String LEELAWADEE_UI = "Leelawadee UI";
	public static final String LEELAWADEE_UI_SEMILIGHT = "Leelawadee UI Semilight";
	public static final String LUCIDA_BRIGHT = "Lucida Bright";
	public static final String LUCIDA_CALLIGRAPHY = "Lucida Calligraphy";
	public static final String LUCIDA_CONSOLE = "Lucida Console";
	public static final String LUCIDA_FAX = "Lucida Fax";
	public static final String LUCIDA_HANDWRITING = "Lucida Handwriting";
	public static final String LUCIDA_SANS = "Lucida Sans";
	public static final String LUCIDA_SANS_TYPEWRITER = "Lucida Sans Typewriter";
	public static final String LUCIDA_SANS_UNICODE = "Lucida Sans Unicode";
	public static final String MV_BOLI = "MV Boli";
	public static final String MAGNETO = "Magneto";
	public static final String MAIANDRA_GD = "Maiandra GD";
	public static final String MALGUN_GOTHIC = "Malgun Gothic";
	public static final String MALGUN_GOTHIC_SEMILIGHT = "Malgun Gothic Semilight";
	public static final String MARLETT = "Marlett";
	public static final String MATURA_MT_SCRIPT_CAPITALS = "Matura MT Script Capitals";
	public static final String MICROSOFT_HIMALAYA = "Microsoft Himalaya";
	public static final String MICROSOFT_JHENGHEI = "Microsoft JhengHei";
	public static final String MICROSOFT_JHENGHEI_LIGHT = "Microsoft JhengHei Light";
	public static final String MICROSOFT_JHENGHEI_UI = "Microsoft JhengHei UI";
	public static final String MICROSOFT_JHENGHEI_UI_LIGHT = "Microsoft JhengHei UI Light";
	public static final String MICROSOFT_NEW_TAI_LUE = "Microsoft New Tai Lue";
	public static final String MICROSOFT_PHAGSPA = "Microsoft PhagsPa";
	public static final String MICROSOFT_SANS_SERIF = "Microsoft Sans Serif";
	public static final String MICROSOFT_TAI_LE = "Microsoft Tai Le";
	public static final String MICROSOFT_YAHEI = "Microsoft YaHei";
	public static final String MICROSOFT_YAHEI_LIGHT = "Microsoft YaHei Light";
	public static final String MICROSOFT_YAHEI_UI = "Microsoft YaHei UI";
	public static final String MICROSOFT_YAHEI_UI_LIGHT = "Microsoft YaHei UI Light";
	public static final String MICROSOFT_YI_BAITI = "Microsoft Yi Baiti";
	public static final String MINGLIU_EXTB = "MingLiU-ExtB";
	public static final String MINGLIU_HKSCS_EXTB = "MingLiU_HKSCS-ExtB";
	public static final String MISTRAL = "Mistral";
	public static final String MODERN_NO_20 = "Modern No. 20";
	public static final String MONGOLIAN_BAITI = "Mongolian Baiti";
	public static final String MONOSPACED = "Monospaced";
	public static final String MONOTYPE_CORSIVA = "Monotype Corsiva";
	public static final String MYANMAR_TEXT = "Myanmar Text";
	public static final String NSIMSUN = "NSimSun";
	public static final String NIAGARA_ENGRAVED = "Niagara Engraved";
	public static final String NIAGARA_SOLID = "Niagara Solid";
	public static final String NIRMALA_UI = "Nirmala UI";
	public static final String NIRMALA_UI_SEMILIGHT = "Nirmala UI Semilight";
	public static final String OCR_A_EXTENDED = "OCR A Extended";
	public static final String OLD_ENGLISH_TEXT_MT = "Old English Text MT";
	public static final String ONYX = "Onyx";
	public static final String PMINGLIU_EXTB = "PMingLiU-ExtB";
	public static final String PALACE_SCRIPT_MT = "Palace Script MT";
	public static final String PALATINO_LINOTYPE = "Palatino Linotype";
	public static final String PAPYRUS = "Papyrus";
	public static final String PARCHMENT = "Parchment";
	public static final String PERPETUA = "Perpetua";
	public static final String PERPETUA_TITLING_MT = "Perpetua Titling MT";
	public static final String PLAYBILL = "Playbill";
	public static final String POOR_RICHARD = "Poor Richard";
	public static final String PRISTINA = "Pristina";
	public static final String RAGE_ITALIC = "Rage Italic";
	public static final String RAVIE = "Ravie";
	public static final String ROBOTO = "Roboto";
	public static final String ROBOTO_TH = "Roboto Th";
	public static final String ROCKWELL = "Rockwell";
	public static final String ROCKWELL_CONDENSED = "Rockwell Condensed";
	public static final String ROCKWELL_EXTRA_BOLD = "Rockwell Extra Bold";
	public static final String SANSSERIF = "SansSerif";
	public static final String SCRIPT_MT_BOLD = "Script MT Bold";
	public static final String SEGOE_MDL2_ASSETS = "Segoe MDL2 Assets";
	public static final String SEGOE_PRINT = "Segoe Print";
	public static final String SEGOE_UI = "Segoe UI";
	public static final String SEGOE_UI_BLACK = "Segoe UI Black";
	public static final String SEGOE_UI_EMOJI = "Segoe UI Emoji";
	public static final String SEGOE_UI_HISTORIC = "Segoe UI Historic";
	public static final String SEGOE_UI_LIGHT = "Segoe UI Light";
	public static final String SEGOE_UI_SEMIBOLD = "Segoe UI Semibold";
	public static final String SEGOE_UI_SEMILIGHT = "Segoe UI Semilight";
	public static final String SEGOE_UI_SYMBOL = "Segoe UI Symbol";
	public static final String SERIF = "Serif";
	public static final String SHOWCARD_GOTHIC = "Showcard Gothic";
	public static final String SIMSUN = "SimSun";
	public static final String SIMSUN_EXTB = "SimSun-ExtB";
	public static final String SITKA_BANNER = "Sitka Banner";
	public static final String SITKA_DISPLAY = "Sitka Display";
	public static final String SITKA_HEADING = "Sitka Heading";
	public static final String SITKA_SMALL = "Sitka Small";
	public static final String SITKA_SUBHEADING = "Sitka Subheading";
	public static final String SITKA_TEXT = "Sitka Text";
	public static final String SNAP_ITC = "Snap ITC";
	public static final String STENCIL = "Stencil";
	public static final String SYLFAEN = "Sylfaen";
	public static final String SYMBOL = "Symbol";
	public static final String TAHOMA = "Tahoma";
	public static final String TEAMVIEWER10 = "TeamViewer10";
	public static final String TEMPUS_SANS_ITC = "Tempus Sans ITC";
	public static final String TIMES_NEW_ROMAN = "Times New Roman";
	public static final String TREBUCHET_MS = "Trebuchet MS";
	public static final String TW_CEN_MT = "Tw Cen MT";
	public static final String TW_CEN_MT_CONDENSED = "Tw Cen MT Condensed";
	public static final String TW_CEN_MT_CONDENSED_EXTRA_BOLD = "Tw Cen MT Condensed Extra Bold";
	public static final String VERDANA = "Verdana";
	public static final String VINER_HAND_ITC = "Viner Hand ITC";
	public static final String VIVALDI = "Vivaldi";
	public static final String VLADIMIR_SCRIPT = "Vladimir Script";
	public static final String WEBDINGS = "Webdings";
	public static final String WIDE_LATIN = "Wide Latin";
	public static final String WINGDINGS = "Wingdings";
	public static final String WINGDINGS_2 = "Wingdings 2";
	public static final String WINGDINGS_3 = "Wingdings 3";
	public static final String YU_GOTHIC_LIGHT = "Yu Gothic Light";
	public static final String YU_GOTHIC_MEDIUM = "Yu Gothic Medium";
	public static final String YU_GOTHIC = "Yu Gothic";
	public static final String YU_GOTHIC_UI = "Yu Gothic UI";
	public static final String YU_GOTHIC_UI_LIGHT = "Yu Gothic UI Light";
	public static final String YU_GOTHIC_UI_SEMIBOLD = "Yu Gothic UI Semibold";
	public static final String YU_GOTHIC_UI_SEMILIGHT = "Yu Gothic UI Semilight";
	public static final String YU_MINCHO_DEMIBOLD = "Yu Mincho Demibold";
	public static final String YU_MINCHO_LIGHT = "Yu Mincho Light";
	public static final String YU_MINCHO = "Yu Mincho";

	public static String[] getFonts() {
		return fonts;
	}

	public static String[] getFontIndex() {
		return fontIndex;
	}

	public static String[] getFamilies() {
		return families;
	}

}
