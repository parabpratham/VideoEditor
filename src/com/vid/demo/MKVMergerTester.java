package com.vid.demo;

import java.io.File;
import java.io.IOException;

import com.vid.controller.VideoEditorController;

public class MKVMergerTester {

	//

	private static final String outDir = "K:/Test/Out/";
	private static final String mkvToolPath = "mkvmerge.exe";
	private static final String inDir = "I:/workspace/VideoPlayerWorkSpace/SampleInOut/In/";

	public static void main(String[] args) throws IOException {
		convertToMKV();
	}

	private static void convertToMKV() {

		try {
			int[] count = { 10, 50, 100, 200, 400, 600, 800, 1000 };
			for (Integer i = 0; i < count.length; i++) {
				File file = new File(inDir);
				for (File video : file.listFiles()) {
					String fileName = video.getName();
					String opFilePath = outDir + fileName.substring(0, fileName.length() - 4) + "_" + count[i];
					String cmd = "mkvmerge.exe -o " + (opFilePath + ".mkv") + " " + inDir + "" + fileName
							+ " --attachment-description \"Annotations list file\" --attachment-mime-type \"xml/txt\" --attach-file "
							+ (opFilePath + ".xml");
					System.out.println(cmd);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

}
