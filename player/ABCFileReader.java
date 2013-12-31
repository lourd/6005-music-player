package player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ABCFileReader {
	private Scanner scanner;

	/* Header properties */
	private Map<String, String> headerMap;
	private List<String> voices;
	private String abcText;

	public ABCFileReader(String filename) throws InvalidFileException,
			IOException {
		headerMap = new HashMap<String, String>();
		voices = new ArrayList<String>();

		// Open the file for scanning
		scanner = new Scanner(new File(filename));

		processHeader();
		processBody();
	}

	public Map<String, String> header() {
		return headerMap;
	}

	public List<String> voices() {
		return voices;
	}

	public String abcData() {
		return abcText;
	}

	private void processBody() {
		abcText = "";

		scanner.useDelimiter("\\n");

		while (scanner.hasNext()) {

			String strLine = scanner.next();
			if (strLine.contains("%")) {
				String parts[] = strLine.split("%");
				if (parts.length > 0) {
					strLine = parts[0];
				} else {
					continue;
				}
			}

			if (strLine.equals("")) {
				continue;
			}

			abcText += strLine + "\n";
		}
	}

	private void processHeader() throws InvalidFileException, IOException {
		scanner.useDelimiter("\\r?\\n");

		// X:
		if (scanner.hasNext("X:.+")) {
			String field = scanner.next("X:.+").split(":")[1].trim();

			if (field.contains("%")) {
				field = field.split("%")[0];
			}

			if (field.equals("")) {
				throw new InvalidFileException(
						"This ABC header field has a blank value.");
			}

			headerMap.put("X", field);
		} else {
			throw new InvalidFileException(
					"This ABC file does not start with X:");
		}

		// T:
		if (scanner.hasNext("T:.+")) {
			String field = scanner.next("T:.+").split(":")[1].trim();
			if (field.contains("%")) {
				field = field.split("%")[0];
			}

			if (field.equals("")) {
				throw new InvalidFileException(
						"This ABC header field has a blank value.");
			}

			headerMap.put("T", field);
		} else {
			throw new InvalidFileException(
					"This ABC file does not have T: as the second header field.");
		}

		// ?:
		while (scanner.hasNext("[a-z]|[A-Z]:.+")) {
			String match[] = scanner.next(".+:.+").split(":");

			String key = match[0].trim();
			String value = match[1].trim();

			if (value.contains("%")) {
				value = value.split("%")[0].trim();
			}

			if (value.equals("")) {
				throw new InvalidFileException(
						"This ABC header field has a blank value.");
			}

			if (key.equals("V")) {
				voices.add(value);
			} else {
				headerMap.put(key, value);
			}
		}

		// Die if no K:
		if (!headerMap.containsKey("K")) {
			throw new InvalidFileException(
					"This ABC file does not have a K: key field.");
		}

	}

	public void printHeader() {
		// Prints header
		System.out.println("Voices: " + voices.toString());
		for (String key : headerMap.keySet()) {
			System.out.println(key + ": " + headerMap.get(key));
		}
	}

}
