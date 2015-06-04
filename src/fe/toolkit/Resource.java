package fe.toolkit;

import java.io.File;

public class Resource {

	private String originalPath;

	private String fileName;

	private File file;

	private String absPath;

	private String fileType;

	public Resource() {
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getAbsPath() {
		return absPath;
	}

	public void setAbsPath(String absPath) {
		this.absPath = absPath;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getOriginalPath() {
		return originalPath;
	}

	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}

	public static Resource parseURI(String absUri, String origin) {
		Resource resource = new Resource();
		resource.setOriginalPath(origin);
		resource.setAbsPath(absUri);
		resource.setFile(new File(absUri));
		int queryIndex = absUri.lastIndexOf("?") == -1 ? absUri.length()
				: absUri.lastIndexOf("?");
		int slashIndex = absUri.lastIndexOf("/") == -1 ? 0 : absUri
				.lastIndexOf("/");
		resource.setFileType(absUri.substring(absUri.lastIndexOf(".") + 1,
				queryIndex));
		resource.setFileName(absUri.substring(slashIndex + 1, queryIndex));
		return resource;
	}
	
	public static Resource parseURI(String absUri){
		return parseURI(absUri, absUri);
	}

}
