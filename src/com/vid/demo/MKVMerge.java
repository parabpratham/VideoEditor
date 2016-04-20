package com.vid.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MKVMerge {

	String mtnFolder = "C:/Program Files/MKVToolNix/";

	public MKVMerge(String path) {
		mtnFolder = path;
		if (!mtnFolder.endsWith("/"))
			mtnFolder += "/";
	}

	public MKVMerge() {
		// TODO Auto-generated constructor stub
	}

	private String attachMentPath = "I:/MkvMergeTest/att";

	public ArrayList<Attachment> getAttachments() {
		String mergePath = getMergePath();
		ArrayList<Attachment> list = new ArrayList();
		if (!attachMentPath.toLowerCase().endsWith(".mkv"))
			return list;
		String s = null;
		String results = null;
		try {
			Runtime rt = Runtime.getRuntime();
			// System.out.println("running: "+mergePath+"-i "+path);
			Process p = rt.exec(mergePath + " -i \"" + attachMentPath + "\"");
			BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// read the output from the command
			// System.out.println("Here is the standard output of the
			// command:\n");
			while ((s = stdInput.readLine()) != null) {
				// System.out.println(s);
				results = results + s;
				if (s.startsWith("Attachment"))
					list.add(new Attachment(attachMentPath, s));
			}
			// read any errors from the attempted command
			// System.out.println("Here is the standard error of the command (if
			// any):\n");
			while ((s = stdError.readLine()) != null) {
				System.out.println(s);
				results = results + s;
			}
			// return results.toString();
			stdInput.close();
			stdError.close();

		} catch (Exception ex) {

		}

		return list;
	}

	public ArrayList<Attachment> getImageAttachments() {
		ArrayList<Attachment> list = new ArrayList();
		ArrayList<Attachment> allAttach = getAttachments();
		for (Attachment att : allAttach) {
			if (att.getType().contains("image"))
				list.add(att);
		}
		return list;
	}

	public ArrayList<File> getImageAttachmentFiles(ArrayList<Attachment> attachments, String savePath) {
		ArrayList<File> fileList = new ArrayList();
		// ArrayList<Attachment> attachments = getImageAttachments(path);

		File tmpDir = new File(savePath);
		tmpDir.mkdir();

		for (Attachment att : attachments) {
			try {
				Runtime rt = Runtime.getRuntime();
				String command = getExtractPath() + " attachments \"" + att.getFileName() + "\" " + att.getUID() + ":"
						+ tmpDir.getPath() + "/" + att.getName();
				// System.out.println("running: "+command);
				Process p = rt.exec(command);
				fileList.add(new File(tmpDir.getPath() + "/" + att.getName()));
			} catch (Exception ex) {
				System.out.println("Error running mkvextract.exe: " + ex);
			}
		}

		return fileList;
	}

	public String getInfoPath() {
		return mtnFolder + "mkvinfo.exe";
	}

	public String getExtractPath() {
		return mtnFolder + "mkvextract.exe";
	}

	public String getMergePath() {
		return mtnFolder + "mkvmerge.exe";
	}

	public class Attachment {
		String file;
		String name;
		String type;
		String size;
		String UID;

		public Attachment(String f, String s) {
			file = f;
			String[] idContent = s.split(": ");
			UID = idContent[0].split(" ")[2];
			String[] attributes = idContent[1].split(", ");
			type = attributes[0].replace("type '", "").replace("'", "");
			size = attributes[1].replace("size ", "");
			int skip = (attributes[2].startsWith("description")) ? 1 : 0;
			name = attributes[2 + skip].replace("file name '", "").replace("'", "");
		}

		public String getName() {
			return name;
		}

		public String getType() {
			return type;
		}

		public String getSize() {
			return size;
		}

		public String getUID() {
			return UID;
		}

		public String getFileName() {
			return file;
		}

		@Override
		public String toString() {
			return "Attachment ID " + UID + ": type '" + type + ", size " + size + ", file name '" + name + "'";
		}
	}

	public boolean isValid() {
		File file = new File(getMergePath());
		if (!file.exists()) {
			return false;
		}
		file = new File(getInfoPath());
		if (!file.exists()) {
			return false;
		}
		file = new File(getExtractPath());
		if (!file.exists()) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		MKVMerge merge = new MKVMerge();
		merge.getAttachments();
	}
}