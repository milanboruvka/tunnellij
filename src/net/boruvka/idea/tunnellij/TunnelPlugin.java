package net.boruvka.idea.tunnellij;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.Disposable;

/**
 * @author boruvka
 */
@Service
public final class TunnelPlugin implements Disposable {

    private static final Map<Project, TunnelPanel> tunnelPanels = new ConcurrentHashMap<>();

    public static Properties PROPERTIES = new Properties();

    private static final String PROPERTIES_FILE_NAME = "tunnellij.properties";

    private static final File PROPERTIES_FILE = new File(System.getProperty("user.home"),
            PROPERTIES_FILE_NAME);

    public static TunnelPlugin getInstance() {
        return ApplicationManager.getApplication().getService(TunnelPlugin.class);
    }

    public void loadProperties() {
        if (PROPERTIES_FILE.exists()) {
            try {
                InputStream is = new FileInputStream(PROPERTIES_FILE);
                PROPERTIES.load(is);
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dispose() {
        try {
            OutputStream os = new FileOutputStream(PROPERTIES_FILE);
            PROPERTIES.store(os, "TunnelliJ plugin");
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setTunnelPanel(Project project, TunnelPanel panel) {
        tunnelPanels.put(project, panel);
    }

    public static TunnelPanel getTunnelPanel(Project project) {
        return tunnelPanels.get(project);
    }

    public static class TunnelConfig {

        public static final int BUFFER_LENGTH = 4096;

        public static final String DST_HOST = "tunnellij.dst.hostname";

        public static final String DST_PORT = "tunnellij.dst.port";

        public static final String SRC_PORT = "tunnellij.src.port";

        public static String getDestinationString() {
            return PROPERTIES.getProperty(DST_HOST, "localhost");
        }

        public static String getDestinationPort() {
            return PROPERTIES.getProperty(DST_PORT, "6060");
        }

        public static String getSourcePort() {
            return PROPERTIES.getProperty(SRC_PORT, "4444");
        }
    }

}
