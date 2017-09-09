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

import domain.Addon;
import domain.BuildTools;
import domain.FileType;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.*;
import java.util.Properties;

/**
 * Created by Cedric Achi on 13/07/2017.
 *
 * @author Cedric Achi
 * @version 01 - Alpha
 * @since 17/07/2017
 */
public class AddonGenerator {

    public static final String TEMPLATE_PATH = "src/main/resources/template/";

    private static final Logger LOGGER = Logger.getLogger(AddonGenerator.class);

    private Addon addon = null;
    private VelocityContext context = null;
    private VelocityEngine engine = null;
    private static AddonGenerator generator = null;
    private File file = null;


    public static void main(String[] args) {
        AddonGenerator.getInstance().Run();
    }

    /**
     * Run
     * This method contains instructions executed to generate the domain.Addon...
     */
    public void Run() {
        engine = new VelocityEngine();
        engine.init();
        context = new VelocityContext();


        try {
            addon = AddonGenerator.getInstance().extractAddonProperties(LoadProperties.getInstance());

            //Get the Context contained our Addon Properties...
            context = AddonGenerator.getInstance().generateContext(addon);

            if (addon != null) {

                switch (addon.getBuildTool().getId()) {

                    case 1:
                        //Gradle used as Build Tool...

                        //Create Addon Parent Directory and the other directories...
                        //Create resources directory...
                        //Create the Default i18n file named Messages.properties...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName() + "/src/main",
                                "resources");
                        file = new File("output/" + addon.getPackageName() + "/src/main/resources/Messages.properties");
                        file.createNewFile();

                        //Create the Source Directory to store the java files...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(),
                                "src/main/java/org/zaproxy/zap/extension");

                        //Create Other Files Directory...
                        if (addon.isUseOtherFiles()) {
                            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName() +
                                    "/src/main/resources", "files");
                        }

                        //Generate the Addon Main Class...//move Java file...
                        AddonGenerator.getInstance().generateFile(context, addon.getClassName(), "addonMainClass.vm",
                                FileType.JAVA_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir(addon.getClassName(), "output/" + addon.getPackageName() +
                                "/src/main/java/org/zaproxy/zap/extension", FileType.JAVA_FILE_EXTENSION.getFileExtension());

                        //Generate Xml files...//move XML file...
                        AddonGenerator.getInstance().generateFile(context, "ZapAddOn", "zapAddonDescFile.vm",
                                FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("ZapAddOn", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());

                        //Generate Help files directory and their files...
                        AddonGenerator.getInstance().generateHelpFiles(BuildTools.GRADLE.getResourcesPathName());

                        //Generate Gradle Build Tool files and it...
                        AddonGenerator.getInstance().generateBuildFile(context, "build", "build.vm",
                                BuildTools.GRADLE.getTemplateLocation(), FileType.GRADLE_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("build", "output/" + addon.getPackageName(),
                                FileType.GRADLE_FILE_EXTENSION.getFileExtension());

                        break;

                    case 2:
                        //Maven used as Build Tool...

                        //Create Addon Parent Directory and the other directories...
                        //Create resources directory...
                        //Create the Default i18n file named Messages.properties...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName() + "/src/main",
                                "resources");
                        file = new File("output/" + addon.getPackageName() + "/src/main/resources/Messages.properties");
                        file.createNewFile();

                        //Create the Source Directory to store the java files...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(),
                                "src/main/java/org/zaproxy/zap/extension");

                        //Create Other Files Directory...
                        if (addon.isUseOtherFiles()) {
                            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName() +
                                    "/src/main/resources", "files");
                        }

                        //Generate the Addon Main Class...//move Java file...
                        AddonGenerator.getInstance().generateFile(context, addon.getClassName(), "addonMainClass.vm",
                                FileType.JAVA_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir(addon.getClassName(), "output/" + addon.getPackageName() +
                                "/src/main/java/org/zaproxy/zap/extension", FileType.JAVA_FILE_EXTENSION.getFileExtension());

                        //Generate Xml files...//move XML file...
                        AddonGenerator.getInstance().generateFile(context, "ZapAddOn", "zapAddonDescFile.vm",
                                FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("ZapAddOn", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());

                        //Generate Help files directory and their files...
                        AddonGenerator.getInstance().generateHelpFiles(BuildTools.MAVEN.getResourcesPathName());

                        //Generate Maven Build Tool files and it...
                        AddonGenerator.getInstance().generateBuildFile(context, "pom1", "pom.vm",
                                BuildTools.MAVEN.getTemplateLocation(), FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("pom1", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());
                        File file1 = new File("output/" + addon.getPackageName() + "/pom1.xml");
                        file1.renameTo(new File("output/" + addon.getPackageName() + "/pom.xml"));

                        break;

                    case 3:
                        //Ant used as Build Tool...

                        //Create Addon Parent Directory and the other directories...
                        //Create resources directory...
                        //Create the Default i18n file named Messages.properties...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "resources");
                        file = new File("output/" + addon.getPackageName() + "/resources/Messages.properties");
                        file.createNewFile();

                        //Create Other Files Directory...
                        if (addon.isUseOtherFiles()) {
                            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "files");
                        }

                        //Create Libraries Directory and Move the Zap core to it...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "lib");
                        AddonGenerator.getInstance().moveFileToDir("zap-2.6.0", "output/" + addon.getPackageName() +
                        "/lib", FileType.JAR_FILE_EXTENSION.getFileExtension());

                        //Generate the Addon Main Class...//move Java file...
                        //Create src directory...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "src");
                        AddonGenerator.getInstance().generateFile(context, addon.getClassName(), "addonMainClass.vm",
                                FileType.JAVA_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir(addon.getClassName(), "output/" + addon.getPackageName() +
                                "/src", FileType.JAVA_FILE_EXTENSION.getFileExtension());

                        //Generate Xml files...//move XML file...
                        AddonGenerator.getInstance().generateFile(context, "ZapAddOn", "zapAddonDescFile.vm",
                                FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("ZapAddOn", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());

                        //Generate Ant Build Tool files and move it...
                        AddonGenerator.getInstance().generateBuildFile(context, "build", "build.vm",
                                BuildTools.ANT.getTemplateLocation(), FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("build", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());

                        //Generate the Help files...
                        AddonGenerator.getInstance().generateHelpFiles(BuildTools.DEFAULT.getResourcesPathName());

                        break;

                    default:
                        //No Build Tool defined...

                        //Create Addon Parent Directory and the other directories...
                        //Create resources directory...
                        //Create the Default i18n file named Messages.properties...
                        AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "resources");
                        file = new File("output/" + addon.getPackageName() + "/resources/Messages.properties");
                        file.createNewFile();

                        //Create Libraries Directory...
                        if (addon.isUseLib()) {
                            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "lib");
                        }

                        //Create Other Files Directory...
                        if (addon.isUseOtherFiles()) {
                            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName(), "files");
                        }

                        //Generate the Addon Main Class...//move Java file...
                        AddonGenerator.getInstance().generateFile(context, addon.getClassName(), "addonMainClass.vm",
                                FileType.JAVA_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir(addon.getClassName(), "output/" + addon.getPackageName(),
                                FileType.JAVA_FILE_EXTENSION.getFileExtension());

                        //Generate Xml files...//move XML file...
                        AddonGenerator.getInstance().generateFile(context, "ZapAddOn", "zapAddonDescFile.vm",
                                FileType.XML_FILE_EXTENSION.getFileExtension());
                        AddonGenerator.getInstance().moveFileToDir("ZapAddOn", "output/" + addon.getPackageName(),
                                FileType.XML_FILE_EXTENSION.getFileExtension());

                        //Generate Help Files...
                        AddonGenerator.getInstance().generateHelpFiles(BuildTools.DEFAULT.getResourcesPathName());
                }

            } else {
                LOGGER.log(Level.FATAL, "Verify if you fill the generator.properties with your Add-on details",
                        new NullPointerException(Addon.class.getName()));
            }

        } catch (IOException e) {
            LOGGER.log(Level.FATAL, e.getMessage(), e);
        }
    }

    /**
     * moveFileToDir
     * This method is used to move all files generated...
     *
     * @param fileName
     * @param destPath
     * @param fileExtension
     * @return
     * @throws IOException
     */
    public boolean moveFileToDir(String fileName, String destPath, String fileExtension) throws IOException {
        File file1 = new File(fileName + "." + fileExtension);
        File file2 = new File(destPath + "/" + fileName + "." + fileExtension);

        InputStream inputStream = new FileInputStream(file1);
        OutputStream outputStream = new FileOutputStream(file2);

        byte[] buffer = new byte[1024];

        int length;
        //copy the file content in bytes...
        while ((length = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, length);
        }

        inputStream.close();
        outputStream.close();

        //Delete the original file...
        if (!fileExtension.equals("png"))
            file1.delete();

        LOGGER.log(Level.INFO, "Move File " + file1 + " to " + file2);
        return true;
    }

    /**
     * CreateDir
     * This method is used to create our domain.Addon directories...
     *
     * @param parentName - PathName
     * @param childName  - Directory Name
     * @return
     */
    public boolean createDir(String parentName, String childName) {
        File dir = new File(parentName + "/" + childName);
        dir.mkdirs();

        LOGGER.log(Level.INFO, "Directory " + parentName + "/" + childName + " was created");
        return true;
    }

    /**
     * generateXmlFile
     * This method is used to generate XML files...
     *
     * @param context
     * @param fileName
     * @param templateName
     * @return
     * @throws IOException
     */
    public boolean generateFile(VelocityContext context, String fileName, String templateName, String fileExtension)
            throws IOException {
        Template template = Velocity.getTemplate(TEMPLATE_PATH + templateName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "." + fileExtension));
        template.merge(context, writer);

        writer.flush();
        writer.close();

        LOGGER.log(Level.INFO, "The File " + fileName + "." + fileExtension + " was generated");
        return true;
    }

    public boolean generateBuildFile(VelocityContext context, String fileName, String templateName, String templatePath
            , String fileExtension) throws IOException {
        Template template = Velocity.getTemplate(templatePath + templateName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + "." + fileExtension));
        template.merge(context, writer);

        writer.flush();
        writer.close();

        LOGGER.log(Level.INFO, "Build Tool File " + fileName + "." + fileExtension + " was generated");
        return true;
    }

    /**
     * generateContext
     * THis method is used to put our properties in VelocityContext for
     * template modification.
     *
     * @param addon
     * @return VelocityContext which provides our domain.Addon properties for the different Templates...
     */
    public VelocityContext generateContext(Addon addon) {
        VelocityContext context = new VelocityContext();

        //Insert the Content of our properties file in VelocityContext Key/Value for our Template...
        context.put("name", addon.getName());
        context.put("pack", addon.getPackageName());
        context.put("class", addon.getClassName());
        context.put("description", addon.getDescription());
        context.put("author", addon.getAuthor());
        context.put("url", addon.getUrl());
        context.put("nbversion", addon.getNbversion());
        context.put("nfversion", addon.getNfversion());

        LOGGER.log(Level.INFO, "Create a VelocityContext and add it our add-on details");
        return context;
    }

    /**
     * extractAddonProperties
     * This method is used to extract all properties of our domain.Addon
     *
     * @param properties
     * @return domain.Addon which is an domain.Addon Object
     */
    public Addon extractAddonProperties(Properties properties) {
        Addon addon = null;

        //Verify the Properties domain.Addon.name and domain.Addon.nbversion....
        if (properties.getProperty("addon.name").length() != 0 && properties.getProperty("addon.nbversion").length() != 0
                && !(formatAndVerifyAddonName(properties.getProperty("addon.name")).length() <= 0)) {

            addon = new Addon();

            //Format Addon Class name...
            addon.setName(formatName(formatAndVerifyAddonName(properties.getProperty("addon.name"))));
            addon.setPackageName(formatPackageName(formatAndVerifyAddonName(properties.getProperty("addon.name"))));
            addon.setClassName(formatAndVerifyAddonName(properties.getProperty("addon.name")));

            if (properties.getProperty("addon.description").length() != 0) {
                addon.setDescription(properties.getProperty("addon.description"));
            } else {
                addon.setDescription(" ");
            }

            if (properties.getProperty("addon.url").length() != 0) {
                addon.setUrl(properties.getProperty("addon.url"));
            } else {
                addon.setUrl(" ");
            }

            if (properties.getProperty("addon.author").length() != 0) {
                addon.setAuthor(properties.getProperty("addon.author"));
            } else {
                addon.setAuthor("ZAP Team");
            }

            if (properties.getProperty("addon.nfversion").length() != 0) {
                addon.setNfversion(properties.getProperty("addon.nfversion"));
            } else {
                addon.setNfversion("2.6.0");
            }

            if (properties.getProperty("addon.nbversion").length() != 0) {
                addon.setNbversion(properties.getProperty("addon.nbversion"));
            } else {
                addon.setNbversion("");
            }

            if (properties.getProperty("addon.uselib").length() != 0 && properties.getProperty("addon.uselib")
                    .toLowerCase().charAt(0) == 'y') {
                addon.setUseLib(true);
            }

            if (properties.getProperty("addon.help.file").length() != 0 && properties.getProperty("addon.help.file")
                    .toLowerCase().charAt(0) == 'y') {
                addon.setUseHelpFile(true);
            }

            if (properties.getProperty("addon.file.other").length() != 0 && properties.getProperty("addon.file.other")
                    .toLowerCase().charAt(0) == 'y') {
                addon.setUseOtherFiles(true);
            }

            //Verify if the User selected a Build Tool
            if (properties.getProperty("addon.build.tool").length() != 0 && properties.getProperty("addon.build.tool") != null) {
                addon.setBuildTool(Addon.getBuildToolById(Integer.valueOf(properties.getProperty("addon.build.tool"))));
            } else {
                addon.setBuildTool(BuildTools.DEFAULT);
            }

            LOGGER.log(Level.INFO, "Extract our Add-on property and set them into our domain.Addon Object");
            return addon;
        }

        LOGGER.log(Level.FATAL, "Verify if you fill generator.properties or verify if " +
                        "your Addon.name does not contain a special Character",
                new NullPointerException(Addon.class.getName()));

        return addon;
    }

    /**
     * getInstance
     * this method is used to create an unique instance of the AddonGenerator..
     *
     * @return AddonGenerator
     */
    public static AddonGenerator getInstance() {

        if (generator == null) {
            generator = new AddonGenerator();
            LOGGER.log(Level.INFO, "Create AddonGenerator Object");
        }

        return generator;
    }

    /**
     * generateHelpFiles
     * this method is used to generat Help files and their directories..
     */
    public void generateHelpFiles(String resourcesPathName) {

        //Create Help file Default Directory...
        if (addon.isUseHelpFile()) {
            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName()
                    + "/" + resourcesPathName, "help");

            //Create Help files Directory...
            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName()
                    + "/" + resourcesPathName + "/help", "contents");
            AddonGenerator.getInstance().createDir("output/" + addon.getPackageName()
                    + "/" + resourcesPathName + "/help/contents", "images");

            //Create Help Files and move them to Help Files Directory...
            //Generate HTML file and move it...
            try {
                AddonGenerator.getInstance().generateFile(context, addon.getPackageName(), "addonPage.vm",
                        FileType.HTML_FILE_EXTENSION.getFileExtension());
                AddonGenerator.getInstance().moveFileToDir(addon.getPackageName(), "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help/contents", FileType.HTML_FILE_EXTENSION.getFileExtension());

                //Generate map File and move it...
                AddonGenerator.getInstance().generateFile(context, "map", "mapFile.vm",
                        FileType.MAP_FILE_EXTENSION.getFileExtension());
                AddonGenerator.getInstance().moveFileToDir("map", "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help", FileType.MAP_FILE_EXTENSION.getFileExtension());

                //Generate Helpset file and move it...
                AddonGenerator.getInstance().generateFile(context, "helpset", "helpset.vm",
                        FileType.HS_FILE_EXTENSION.getFileExtension());
                AddonGenerator.getInstance().moveFileToDir("helpset", "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help", FileType.HS_FILE_EXTENSION.getFileExtension());

                //Generate index File and move it...
                AddonGenerator.getInstance().generateFile(context, "index", "index.vm",
                        FileType.XML_FILE_EXTENSION.getFileExtension());
                AddonGenerator.getInstance().moveFileToDir("index", "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help", FileType.XML_FILE_EXTENSION.getFileExtension());

                //Generate toc File and move it...
                AddonGenerator.getInstance().generateFile(context, "toc", "toc.vm",
                        FileType.XML_FILE_EXTENSION.getFileExtension());
                AddonGenerator.getInstance().moveFileToDir("toc", "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help", FileType.XML_FILE_EXTENSION.getFileExtension());

                //Move Default domain.Addon Icon...
                AddonGenerator.getInstance().moveFileToDir("cake", "output/" + addon.getPackageName()
                        + "/" + resourcesPathName + "/help/contents/images", FileType.PNG_FILE_EXTENSION.getFileExtension());

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * formatAndVerifyAddonName
     * This method is used to delete all special characters contained on the Addon name...
     *
     * @param term
     * @return
     */
    public String formatAndVerifyAddonName(String term) {
        boolean isSpecial = false;
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < term.toCharArray().length; i++) {

            if (i == 0)
                isSpecial = true;

            if ((String.valueOf(term.charAt(i))).matches(".*[^A-Za-z].*")) {
                isSpecial = true;

            } else {

                if (isSpecial == true) {
                    builder.append(Character.toUpperCase(term.charAt(i)));
                    isSpecial = false;

                } else {
                    builder.append(term.charAt(i));
                }
            }

        }

        String s = builder.toString();

        return s;
    }

    /**
     * @param term
     * @return
     */
    public String formatName(String term) {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < term.toCharArray().length; i++) {

            if (i > 0 && (String.valueOf(term.charAt(i))).matches("([A-Z])")) {
                builder.append(" ");
                builder.append(Character.toUpperCase(term.charAt(i)));

            } else {

                builder.append(Character.toLowerCase(term.charAt(i)));
            }

        }

        String s = builder.toString();
        return s;
    }

    /**
     * @param term
     * @return
     */
    public String formatPackageName(String term) {
        StringBuilder builder = new StringBuilder();


        for (int i = 0; i < term.toCharArray().length; i++) {

            if (i > 0 && (String.valueOf(term.charAt(i))).matches("([A-Z])")) {
                builder.append(Character.toUpperCase(term.charAt(i)));

            } else {

                builder.append(Character.toLowerCase(term.charAt(i)));
            }

        }

        String s = builder.toString();
        return s;
    }
}