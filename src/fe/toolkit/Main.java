package fe.toolkit;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Administrator
 * @version 1.0 alpha Front End Developer Tool made by JAVA language
 */
public class Main {

	private Map<String, String> config = new HashMap<String, String>();
	
	private boolean rename;
	
	private boolean md5;
	
	public static void main(String[] path) throws IOException {
		Map<String, String> config = Configer.getConfig();
		for (String key : config.keySet()) {
			System.out.println(config.get(key));
		}
	}
}
