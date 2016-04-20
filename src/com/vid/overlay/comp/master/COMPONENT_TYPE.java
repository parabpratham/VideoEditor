package com.vid.overlay.comp.master;

public enum COMPONENT_TYPE {

	JCOMPONENT, SHAPE, IMAGE, ENTIREVIDEOCOMMENT, ANNOTATIONMARKER;

	public static COMPONENT_TYPE getFromName(String name) {
		COMPONENT_TYPE[] values = values();
		for (COMPONENT_TYPE component_TYPE : values) {
			if (component_TYPE.name().equalsIgnoreCase(name))
				return component_TYPE;
		}
		return null;
	}
}
