package com.vid.commons;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class FontRenderer extends DefaultListCellRenderer {

	private static final long serialVersionUID = 1203372645544348813L;

	private String[] fonts;

	private static final String[] families = Fonts.getFamilies();

	public FontRenderer(String[] fonts) {
		this.fonts = fonts;
		setOpaque(true);
	}

	public Component getListCellRendererComponent(JList jc, Object val, int idx, boolean isSelected,
			boolean cellHasFocus) {

		int f = Integer.parseInt(val.toString());

		setText(fonts[f]);
		if (isSelected)
			setBackground(Color.LIGHT_GRAY);
		else
			setBackground(Color.WHITE);

		Font font = new Font(families[f], Font.PLAIN, 18); // createfont
		setFont(font);

		return this;
	}
}
