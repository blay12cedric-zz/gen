package domain;

public enum BuildTools {

    GRADLE(1, "src/main/java", "/src/main/resources", "src/main/resources/buildToolTemplate/gradle/"),
    MAVEN(2, "src/main/java", "/src/main/resources", "src/main/resources/buildToolTemplate/maven/"),
    ANT(3, "src", "resources", "src/main/resources/buildToolTemplate/ant/"),
    DEFAULT(0, "src", "resources", "src/main/resources/template/");

    private int id;
    private String srcPathName;
    private String resourcesPathName;
    private String templateLocation;

    BuildTools(int id, String srcPathName, String resourcesPathName, String templateLocation){
        this.id = id;
        this.srcPathName =srcPathName;
        this.resourcesPathName = resourcesPathName;
        this.templateLocation = templateLocation;
    }

    public int getId() {
        return id;
    }

    public String getSrcPathName() {
        return srcPathName;
    }

    public String getResourcesPathName() {
        return resourcesPathName;
    }

    public String getTemplateLocation() {
        return templateLocation;
    }

}
