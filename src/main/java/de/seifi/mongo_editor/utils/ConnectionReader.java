package de.seifi.mongo_editor.utils;

import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionReader {
    private final Map<String, MongoDbConnection> connections = new HashMap<>();

    public boolean read() throws IOException {

        File settingFile = getSettingFile();
        connections.clear();
        if(!settingFile.exists()){
            initSettings();
            return true;
        }

        Ini ini = new Ini(settingFile);
        for(String key: ini.keySet()){
            String name = ini.get(key, "name");
            String host = ini.get(key, "host");
            String port = ini.get(key, "port");
            String username = ini.get(key, "username");
            String password = ini.get(key, "password");
            Integer iPort = Integer.parseInt(port);

            MongoDbConnection connection = new MongoDbConnection(name, host, iPort, username, password);
            connections.put(name, connection);
        }

        return true;
    }

    private void initSettings() throws IOException {
        File settingFile = getSettingFile();
        settingFile.createNewFile();

    }

    private File getSettingFile() {
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        Path settingsPath = Paths.get(s, "connections.cfg");
        return settingsPath.toFile();
    }

    public MongoDbConnection addConnection(MongoDbConnection connection, boolean saveSettings) throws IOException {
        connections.put(connection.getName(), connection);
        if(saveSettings){
            if(save()){
                return connection;
            }
            return null;
        }
        else{
            return connection;
        }

    }

    public boolean save() throws IOException {
        File settingFile = getSettingFile();
        if(!settingFile.exists()){
            initSettings();
        }

        Ini ini = new Ini(settingFile);
        ini.clear();

        for(String key: connections.keySet()){

            MongoDbConnection connection = connections.get(key);
            String section = "connection_" + key;
            section = section.replace(" ", "_");
            ini.put(section, "name", connection.getName());
            ini.put(section, "host", connection.getHost());
            ini.put(section, "port", connection.getPort());
            ini.put(section, "username", connection.getUsername());
            ini.put(section, "password", connection.getPassword());
        }

        ini.store();

        return true;
    }

    public Map<String, MongoDbConnection> getConnections() {
        return connections;
    }

    public MongoDbConnection getConnection(String name) {
        return connections.get(name);
    }
}
