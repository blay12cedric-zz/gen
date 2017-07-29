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
import domain.BuildTool;
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

    private static final String TEMPLATE_PATH = "src/main/resources/template/";
    private static final String JAVA_FILE_EXTENSION = "java";
    private static final String XML_FILE_EXTENSION = "xml";
    private static final String MAP_FILE_EXTENSION = "jhm";
    private static final String HS_FILE_EXTENSION = "hs";
    private static final String HTML_FILE_EXTENSION = "html";
    private static final String PNG_FILE_EXTENSION = "png";

    private static final Logger LOGGER = Logger.getLogger(AddonGenerator.class);

    private Addon addon = null;
    private VelocityContext context = null;
    private VelocityEngine engine = null;
    private static AddonGenerator generator = null;


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

            //Get the Context contained our domain.Addon Properties...
            context = AddonGenerator.getInstance().generateContext(addon);

            if (addon != null) {

                switch (addon.getBuildTool().getId()) {

                    case 1:
                        //Gradle used as Build Tool...

                        break;

                    case 2:
                        //Maven used as Build Tool...

                        break;

                    case 3:
                        //Ant + Ivy used as Build Tool...

                        break;

                    default:
                        //No Build Tool defined...

                        //Create domain.Addon Parent Directory and the other directories...
                        //Create resources directory...
                        //Create the Default i18n file named Messages.properties...
                        AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName(), "resources");
                        File file = new File("Output/" + addon.getPackageName() + "/resources/Messages.properties");
                        file.createNewFile();

                        //Create Libraries Directory...
                        if (addon.isUseLib()) {
                            AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName(), "lib");
                        }

                        //Create Other Files Directory...
                        if (addon.isUseOtherFiles()) {
                            AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName(), "files");
                        }

                        //Generate the domain.Addon Main Class...
                        AddonGenerator.getInstance().generateFile(context, addon.getName(), "addonMainClass.vm",
                                this.JAVA_FILE_EXTENSION);

                        //Generate Xml files...
                        AddonGenerator.getInstance().generateFile(context, "ZapAddOn", "zapAddonDescFile.vm",
                                this.XML_FILE_EXTENSION);

                        //move Java file...
                        AddonGenerator.getInstance().moveFileToDir(addon.getName(), "Output/" + addon.getPackageName(),
                                "java");

                        //move XML file...
                        AddonGenerator.getInstance().moveFileToDir("ZapAddOn", "Output/" + addon.getPackageName(),
                                "xml");

                        //Create Help files Default Directory and its files...
                        if (addon.isUseHelpFile()) {
                            AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName(), "resources/help");

                            //Create Help files Directory...
                            AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName() + "/resources/help",
                                    "contents");
                            AddonGenerator.getInstance().createDir("Output/" + addon.getPackageName() + "/resources/help/contents",
                                    "images");

                            //Create Help Files and move them to Help Files Directory...
                            //Generate HTML file and move it...
                            AddonGenerator.getInstance().generateFile(context, addon.getPackageName(), "addonPage.vm",
                                    this.HTML_FILE_EXTENSION);
                            AddonGenerator.getInstance().moveFileToDir(addon.getPackageName(), "Output/" + addon.getPackageName()
                                    + "/resources/help/contents", this.HTML_FILE_EXTENSION);

                            //Generate map File and move it...
                            AddonGenerator.getInstance().generateFile(context, "map", "mapFile.vm",
                                    this.MAP_FILE_EXTENSION);
                            AddonGenerator.getInstance().moveFileToDir("map", "Output/" + addon.getPackageName()
                                    + "/resources/help", this.MAP_FILE_EXTENSION);

                            //Generate Helpset file and move it...
                            AddonGenerator.getInstance().generateFile(context, "helpset", "helpset.vm",
                                    this.HS_FILE_EXTENSION);
                            AddonGenerator.getInstance().moveFileToDir("helpset", "Output/" + addon.getPackageName()
                                    + "/resources/help", this.HS_FILE_EXTENSION);

                            //Generate index File and move it...
                            AddonGenerator.getInstance().generateFile(context, "index", "index.vm",
                                    this.XML_FILE_EXTENSION);
                            AddonGenerator.getInstance().moveFileToDir("index", "Output/" + addon.getPackageName()
                                    + "/resources/help", this.XML_FILE_EXTENSION);

                            //Generate toc File and move it...
                            AddonGenerator.getInstance().generateFile(context, "toc", "toc.vm",
                                    this.XML_FILE_EXTENSION);
                            AddonGenerator.getInstance().moveFileToDir("toc", "Output/" + addon.getPackageName()
                                    + "/resources/help", this.XML_FILE_EXTENSION);

                            //Move Default domain.Addon Icon...
                            AddonGenerator.getInstance().moveFileToDir("cake", "Output/" + addon.getPackageName()
                                    + "/resources/help/contents/images", this.PNG_FILE_EXTENSION);
                        }
                }

            }else{
                LOGGER.log(Level.FATAL, "Verify if you fill generator.properties with your Add-on details",
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

        LOGGER.log(Level.INFO, "File " + fileName + "." + fileExtension + " was generated");
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

        //Make UpperCase the first Character of our domain.Addon name to use it as Class name...
        String car = String.valueOf((addon.getName().toString()).charAt(0)).toUpperCase();
        addon.setName((car.concat(addon.getName().substring(1))));

        //Insert the Content of our properties file in VelocityContext Key/Value for our Template...
        context.put("name", addon.getName());
        context.put("pack", addon.getPackageName());
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
        if (properties.getProperty("addon.name").length() != 0 && properties.getProperty("addon.nbversion").length() != 0) {
            addon = new Addon();
            addon.setName(properties.getProperty("addon.name"));
            addon.setPackageName(properties.getProperty("addon.name"));

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
                addon.setNbversion(" ");
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
            if (properties.getProperty("addon.build.tool").length() != 0) {
                addon.setBuildTool(BuildTool.getBuildTool(Integer.valueOf(properties.getProperty("addon.build.tool"))));
            }

            LOGGER.log(Level.INFO, "Extract our Add-on property and set them into our domain.Addon Object");
            return addon;
        }

        LOGGER.log(Level.FATAL, "Verify if you fill generator.properties with your Add-on details",
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
}