package com.vid.overlay.comp.master;

public enum JComponentType {

	SPEECH_BUBLE, CUSTOM_LABEL, SPOT_LIGHT, ENTIRE_VIDEO_COMMENT;

	public static JComponentType getFromName(String name) {
		JComponentType[] values = values();
		for (JComponentType jcomponent_TYPE : values) {
			if (jcomponent_TYPE.name().equalsIgnoreCase(name))
				return jcomponent_TYPE;
		}
		return null;
	}

}
