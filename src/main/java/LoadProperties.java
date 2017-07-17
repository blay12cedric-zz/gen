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

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Cedric Achi on 13/07/2017.
 * @author Cedric Achi
 * @version 01 - Alpha
 * @since 17/07/2017
 */
public class LoadProperties {

    private static Properties properties;
    private static final Logger LOGGER = Logger.getLogger(LoadProperties.class);

    public static Properties getInstance() throws IOException {
        InputStream stream;

        if(null == properties){
            properties = new Properties();
            stream = LoadProperties.class.getResourceAsStream("generator.properties");
            properties.load(stream);
            LOGGER.log(Level.INFO, "----- Object Properties created -----");
        }
        return properties;
    }
}