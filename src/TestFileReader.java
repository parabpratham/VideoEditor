import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class TestFileReader {

	public static void main(String[] args) {

		File inputFile = new File("C:/ip.txt");
		File outputFile = new File("I:/op.txt");

		BufferedWriter bufferedWriter = null;
		Scanner scanner = null;
		String opTest = "";
		try {
			bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
			scanner = new Scanner(new FileInputStream(inputFile));
			while (scanner.hasNextLine()) {
				String readLine = scanner.nextLine();
				System.out.println(readLine);
				String[] words = readLine.split(" ");
				for (String word : words) {
					bufferedWriter.write(word);
					bufferedWriter.newLine();
				}
			}

			// System.out.println("----------------------");
			// System.out.println(opTest);
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (bufferedWriter != null)
					bufferedWriter.close();
				if (scanner != null)
					scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
