package com.vid.overlay.comp.master;

public enum SHAPE_TYPE {

	CIRCLE, RECTANGLE, LINE, OVAL, POLYGON, STRING, IMAGE, ARROW, ROUNDED_RECTANGLE,TEXT;

	public static SHAPE_TYPE getFromName(String name) {
		SHAPE_TYPE[] values = values();
		for (SHAPE_TYPE jcomponent_TYPE : values) {
			if (jcomponent_TYPE.name().equalsIgnoreCase(name))
				return jcomponent_TYPE;
		}
		
		return null;
	}

}
