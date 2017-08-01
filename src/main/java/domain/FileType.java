package domain;

public enum  FileType {

    JAVA_FILE_EXTENSION("java"),
    XML_FILE_EXTENSION("xml"),
    MAP_FILE_EXTENSION("jhm"),
    HS_FILE_EXTENSION("hs"),
    HTML_FILE_EXTENSION("html"),
    PNG_FILE_EXTENSION("png"),
    GRADLE_FILE_EXTENSION("gradle");

    private String fileExtension;

    FileType(String fileExtension){
        this.fileExtension = fileExtension;
    }

    public String getFileExtension() {
        return fileExtension;
    }
}
