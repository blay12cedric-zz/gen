/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package domain;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created by Cedric Achi on 14/07/2017.
 * @author Cedric Achi
 * @version 01 - Alpha
 * @since 17/07/2017
 */
public class Addon {

    private String name;
    private String packageName;
    private String description;
    private String author;
    private String url;
    private String nbversion;
    private String nfversion;

    private boolean useLib;
    private boolean useHelpFile;
    private boolean useOtherFiles;
    private BuildTools buildTool;
    private static final Logger LOGGER = Logger.getLogger(Addon.class);

    public Addon(){
        this.useLib = false;
        this.useHelpFile = false;
        this.useOtherFiles = false;
        this.buildTool = BuildTools.DEFAULT;
        LOGGER.log(Level.INFO, "Create Addon Object");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNbversion() {
        return nbversion;
    }

    public void setNbversion(String nbversion) {
        this.nbversion = nbversion;
    }

    public String getNfversion() {
        return nfversion;
    }

    public void setNfversion(String nfversion) {
        this.nfversion = nfversion;
    }

    public boolean isUseLib() {
        return useLib;
    }

    public void setUseLib(boolean useLib) {
        this.useLib = useLib;
    }

    public boolean isUseHelpFile() {
        return useHelpFile;
    }

    public void setUseHelpFile(boolean useHelpFile) {
        this.useHelpFile = useHelpFile;
    }

    public boolean isUseOtherFiles() {
        return useOtherFiles;
    }

    public void setUseOtherFiles(boolean useOtherFiles) {
        this.useOtherFiles = useOtherFiles;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public BuildTools getBuildTool() {
        return buildTool;
    }

    public void setBuildTool(BuildTools buildTool) {
        this.buildTool = buildTool;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Addon addon = (Addon) o;

        if (useLib != addon.useLib) return false;
        if (useHelpFile != addon.useHelpFile) return false;
        if (useOtherFiles != addon.useOtherFiles) return false;
        if (!name.equals(addon.name)) return false;
        if (packageName != null ? !packageName.equals(addon.packageName) : addon.packageName != null) return false;
        if (description != null ? !description.equals(addon.description) : addon.description != null) return false;
        if (author != null ? !author.equals(addon.author) : addon.author != null) return false;
        if (url != null ? !url.equals(addon.url) : addon.url != null) return false;
        if (!nbversion.equals(addon.nbversion)) return false;
        if (nfversion != null ? !nfversion.equals(addon.nfversion) : addon.nfversion != null) return false;
        return buildTool == addon.buildTool;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (packageName != null ? packageName.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + nbversion.hashCode();
        result = 31 * result + (nfversion != null ? nfversion.hashCode() : 0);
        result = 31 * result + (useLib ? 1 : 0);
        result = 31 * result + (useHelpFile ? 1 : 0);
        result = 31 * result + (useOtherFiles ? 1 : 0);
        result = 31 * result + buildTool.hashCode();
        return result;
    }

    public static BuildTools getBuildToolById(int id){
        BuildTools tools = null;

        switch (id){
            case 1:
                tools = BuildTools.GRADLE;
                break;

            case 2:
                tools = BuildTools.MAVEN;
                break;

            case 3:
                tools = BuildTools.ANTIVY;
                break;

            default:
                tools = BuildTools.DEFAULT;
        }
        return tools;
    }
}
