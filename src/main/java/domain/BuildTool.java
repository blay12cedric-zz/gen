package domain;

import java.util.HashMap;

/**
 * Created by Cedric Achi on 29/07/2017.
 */
public class BuildTool {
    private static final String GRADLE_FILE_EXTENSION = "gradle";

    private int id;
    private String buildToolName;

    private String srcPathName;
    private String resourcesPathName;
    private String extFilesPathName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBuildToolName() {
        return buildToolName;
    }

    public void setBuildToolName(String buildToolName) {
        this.buildToolName = buildToolName;
    }

    public String getSrcPathName() {
        return srcPathName;
    }

    public void setSrcPathName(String srcPathName) {
        this.srcPathName = srcPathName;
    }

    public String getResourcesPathName() {
        return resourcesPathName;
    }

    public void setResourcesPathName(String resourcesPathName) {
        this.resourcesPathName = resourcesPathName;
    }

    public String getExtFilesPathName() {
        return extFilesPathName;
    }

    public void setExtFilesPathName(String extFilesPathName) {
        this.extFilesPathName = extFilesPathName;
    }

    public static HashMap<Integer, BuildTool> getBuildToolList(){
        HashMap<Integer, BuildTool> map = new HashMap<Integer, BuildTool>();
        BuildTool buildTool = null;

        for(int i = 0; i <= 3 ; i++ ){

            switch (i){
                case 1:
                    buildTool = new BuildTool();
                    buildTool.setId(i);
                    buildTool.setBuildToolName("GRADLE");
                    buildTool.setSrcPathName("src");
                    buildTool.setResourcesPathName("resources");
                    buildTool.setExtFilesPathName("files");

                    map.put(buildTool.getId(), buildTool);
                    break;

                case 2:
                    buildTool = new BuildTool();
                    buildTool.setId(i);
                    buildTool.setBuildToolName("MAVEN");
                    buildTool.setSrcPathName("src");
                    buildTool.setResourcesPathName("resources");
                    buildTool.setExtFilesPathName("files");

                    map.put(buildTool.getId(), buildTool);
                    break;

                case 3:
                    buildTool = new BuildTool();
                    buildTool.setId(i);
                    buildTool.setBuildToolName("ANT + IVY");
                    buildTool.setSrcPathName("src");
                    buildTool.setResourcesPathName("resources");
                    buildTool.setExtFilesPathName("files");

                    map.put(buildTool.getId(), buildTool);
                    break;

                    default:
                        buildTool = null;

                        map.put(i, buildTool);
            }
        }

        return map;
    }

    public static BuildTool getBuildTool(int id){
        BuildTool tool = new BuildTool();

        if(id == 0 || id == 1 || id == 3)
            return tool = BuildTool.getBuildToolList().get(id);
        else
            return tool = BuildTool.getBuildToolList().get(0);
    }
}