//
// * @author     Jonny Elliott
// * @year       2015
// * @copyright  GNU General Public License v2
// * @credits
// * @version    0.1
// * @status     Development
//
// This class is meant to be passed to basicConsume method of the RabbitMQ Java implementation. This pattern
// creates a callback based on the DefaultConsumer, where you override the handleDelivery method. Unfortunately,
// nothing seems to work correctly if you place any calls before the basicAck method call. It seemingly fails,
// and redelivers the message to the queue it got it from. Therefore, it is kept here for reference in case it
// is required in the future.
//
// This class parses content from a YAML config file. Currently it expects certain values to exist, so change
// with caution. i.e., if you delete content from the YAML file, it will definitely complain that things are
// not as expected
//
// Uses YAMLBeans and not SnakeYAML as it allowed a nice formatting of the input data. HashMaps can be used instead
// if it is preferred.
//
package org.adslabs.adsfulltext;

import com.esotericsoftware.yamlbeans.YamlReader;

import java.io.FileReader;
import java.util.Map;


public class ConfigLoader {

    // Variable declaration
    // -------------------------------------------------------------------------------------------
    private String configFileName;

    // Getters and setters
    public void setConfigFileName (String configFileName) {this.configFileName = configFileName; }
    public String getConfigFileName () {return this.configFileName; }

    public YamlConfig data;
    // -------------------------------------------------------------------------------------------


    // Class Constructor
    //
    public ConfigLoader () {
        this.setConfigFileName("/settings.yml");
    }


    // Load the config, currently it is taken from the /resources/ folder, this should eventually be updated
    // so that it sits in the head directory, so that it can be easily modified by users
    //
    public void loadConfig () {

        try {
            String resource = getClass().getResource(this.getConfigFileName()).getFile();
            FileReader newInputFile = new FileReader(resource);

            YamlReader reader = new YamlReader(newInputFile);
            this.data = reader.read(YamlConfig.class);

        } catch (java.io.FileNotFoundException error) {
            System.out.println("FileNotFoundError: " + error.getStackTrace());

        } catch (com.esotericsoftware.yamlbeans.YamlException error) {
            System.out.println("YamlExceptionError: " + error.getStackTrace());

        }
    }
}