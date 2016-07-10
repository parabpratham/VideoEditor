package com.vid.demo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SampleXMLGenerator {
	public static void main(String[] args) {

		int[] count = { 10, 50, 100, 200, 400, 600, 800, 1000 };
		for (Integer k = 1; k <= 6; k++) {
			for (Integer i = 0; i < count.length; i++) {
				StringBuilder xmlText = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>  \n");
				xmlText.append("<Video_information> \n");
				xmlText.append("<Video_anno_data>\n");
				for (int j = 0; j < count[i]; j++) {
					xmlText.append("<annotation id=\"" + j + "\" type=\"com.vid.comp.Scomp.TextComp\">\n");
					xmlText.append("<starttime>33897</starttime>\n");
					xmlText.append("<endtime> 61796</endtime> \n"
							+ "<comp_type>JCOMPONENT</comp_type><parameters set=\"1\"> \n");
					xmlText.append("<StartX>189.0</StartX> \n");
					xmlText.append("<StartY>45.0</StartY> \n");
					xmlText.append("<DisplayString>Shaktiman</DisplayString> \n");
					xmlText.append("<DisplayStringColor> rgba(255,255,255,72.76422764227642)</DisplayStringColor> \n");
					xmlText.append("<Underline>false</Underline>\n");
					xmlText.append("<Font>Arial</Font>\n ");
					xmlText.append("<Font_size>36</Font_size>\n");
					xmlText.append("<Bold>false</Bold>\n");
					xmlText.append("<Italic>false</Italic>\n");
					xmlText.append("<Strikethrough>false</Strikethrough>\n");
					xmlText.append("<Underline>false</Underline>\n");
					xmlText.append("</parameters> \n");
					xmlText.append("</annotation>\n");
				}
				xmlText.append("</Video_anno_data>\n");
				xmlText.append("<Video_tag_data>  \n");
				xmlText.append("<Video_tags>  \n");
				xmlText.append("<videotag id=\"1\"> \n");
				xmlText.append("<starttime>7231.0</starttime> \n");
				xmlText.append("<endtime> 8369.0</endtime><parameters set=\"1\">  \n");
				xmlText.append("<TagDescription>Desc</TagDescription>  \n");
				xmlText.append("<TagKeyWords>0</TagKeyWords>  \n");
				xmlText.append("</parameters>  \n");
				xmlText.append("</videotag>  \n");
				xmlText.append("</Video_tags>  \n");
				xmlText.append("<KeyWords>  \n");
				xmlText.append("<keyword id=\"0\">  \n");
				xmlText.append("<parameters set=\"1\">  \n");
				xmlText.append("<Word>K1</Word>  \n");
				xmlText.append("</parameters>  \n");
				xmlText.append("</keyword>  \n");
				xmlText.append("</KeyWords>  \n");
				xmlText.append("</Video_tag_data>  \n");
				xmlText.append("</Video_information> \n");
				File overlayOp = new File("K:\\Test\\Out\\test" + k + "_" + count[i] + ".xml");
				FileWriter writter = null;
				BufferedWriter bufferedWriter = null;
				try {
					writter = new FileWriter(overlayOp);
					bufferedWriter = new BufferedWriter(writter);
					bufferedWriter.write(xmlText.toString());
					bufferedWriter.flush();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (bufferedWriter != null)
							bufferedWriter.close();
						if (writter != null)
							writter.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

}
