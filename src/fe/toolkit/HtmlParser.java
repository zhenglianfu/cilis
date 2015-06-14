package fe.toolkit;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author Administrator
 * @version 1.0 alpha html parser made in JAVA
 */
public class HtmlParser {
	
	private static final Pattern SRC_TAG_PATTERN = Pattern.compile("<(img|link|script).* (src|href)\\s*=\\s*[\"|'](.+)[\"|']", Pattern.CASE_INSENSITIVE);
	
	private static final Pattern SRC_PATTERN     = Pattern.compile("(src|href)\\s*=\\s*[\"|'](.+)[\"|']", Pattern.CASE_INSENSITIVE);
	
	private Map<String, Resource> resourceMap;
	
	private FileReader reader;
	
	private HtmlParser() {}
	
	public static HtmlParser getInstance(File html) throws FileNotFoundException {
		return getInstance(new FileReader(html));
	}
	
	public static HtmlParser getInstance(FileReader html){
		HtmlParser htmlParser = new HtmlParser();
		htmlParser.reader = html;
		return htmlParser;
	}
	
	public Map<String, Resource> parseResource(String root, String parent) throws IOException {
		root = root == null ? "" : root;
		parent = parent == null ? "" : root;
		this.resourceMap = new HashMap<String, Resource>();
		BufferedReader bufferReader = new BufferedReader(this.reader);
		String line = bufferReader.readLine();
		List<String> uris = new ArrayList<String>();
		while(line != null){
			Matcher match = SRC_TAG_PATTERN.matcher(line);
			if (match.find()) {
				String piece = match.group();
				Matcher pieceMatch = SRC_PATTERN.matcher(piece);
				if (pieceMatch.find()) {
					String attr  = pieceMatch.group();
					String[] items = attr.split("=");
					uris.add(trimQuoteRound(join(slice(items, 1), "=")));
				}
			}
			line = bufferReader.readLine();
		}
		// calculate path
		Path path = new Path();
		path.setParent(parent);
		path.setRoot(root);
		for (int i = 0; i < uris.size(); i ++) {
			String uri = path.caculatePath(uris.get(i));
			this.resourceMap.put(uri, Resource.parseURI(uri, uris.get(i)));
		}
		return this.resourceMap;
	}
	
	public void closeReader() throws IOException{
	 	if (null != this.reader) {
	 		this.reader.close();
	 	}
	}
	
	private String trimQuoteRound(String input){
		input = input.trim();
		if (input.charAt(0) == '\'' || input.charAt(0) == '"') {
			input = input.substring(1);
		}
		if (input.charAt(input.length() - 1) == '\'' || input.charAt(input.length() - 1) == '"') {
			input = input.substring(0, input.length() - 1);
		}
		return input;
	}
	
	private String[] slice(String[] arr, int start){
		return slice(arr, start, arr.length);
	}
	
	private String[] slice(String[] arr, int start, int end){
		String[] temp = new String[Math.abs(end - start)];
		int i = 0;
		for (; start < end; start++) {
			temp[i++] = arr[start];
		}
		return temp;
	}
	
	private String join(String[] arr, String separator){
		String s = "";
		separator = separator == null ? "" : separator;
		for (int i = 0; i < arr.length; i++) {
			s += arr[i] + separator;
		}
		return s.substring(0, s.length() - separator.length());
	}
	
	public static void main(String[] args) throws IOException {
		String root = Configer.getConfig().get("root");
		root = Util.isEmpty(root) ? new File("").getAbsolutePath().replace(File.separator, Path.separator) : root; 
		File html = new File("src/resource/test.html");
		HtmlParser parser = HtmlParser.getInstance(html);
		System.out.println(parser.parseResource(root, root));
	}

}
