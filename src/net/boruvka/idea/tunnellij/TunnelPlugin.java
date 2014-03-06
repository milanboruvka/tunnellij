package net.boruvka.idea.tunnellij;

import java.awt.BorderLayout;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.UIManager;

import net.boruvka.idea.tunnellij.action.AboutAction;
import net.boruvka.idea.tunnellij.action.ClearAction;
import net.boruvka.idea.tunnellij.action.ClearSelectedAction;
import net.boruvka.idea.tunnellij.action.StartAction;
import net.boruvka.idea.tunnellij.action.StopAction;
import net.boruvka.idea.tunnellij.action.WrapAction;
import net.boruvka.idea.tunnellij.ui.Icons;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * @author boruvka
 * @since
 */
public class TunnelPlugin implements ProjectComponent {

    private static TunnelPanel tunnelPanel;

    public static Properties PROPERTIES;

    private static final String PROPERTIES_FILE_NAME = "tunnellij.properties";

    private static File PROPERTIES_FILE;

    private static final String COMPONENT_NAME = "net.boruvka.idea.tunnellij.TunnelWindow";

    private static final String TOOL_WINDOW_ID = TunnelBundle.getBundle()
            .getString("TunnelliJ.version");

    static {
        PROPERTIES_FILE = new File(System.getProperty("user.home"),
                PROPERTIES_FILE_NAME);
        PROPERTIES = new Properties();
    }

    private ToolWindow tunnelWindow;

    private Project project;

    public TunnelPlugin(Project project) {
        this.project = project;
    }

    public void projectOpened() {
        initToolWindow();
    }

    public void projectClosed() {
        unregisterToolWindow();
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public synchronized void initComponent() {
        if (PROPERTIES_FILE.exists()) {
            try {
                InputStream is = new FileInputStream(PROPERTIES_FILE);
                PROPERTIES.load(is);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void disposeComponent() {
        try {
            OutputStream os = new FileOutputStream(PROPERTIES_FILE);
            PROPERTIES.store(os, "TunnelliJ plugin");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initToolWindow() {

        ToolWindowManager toolWindowManager = ToolWindowManager
                .getInstance(project);
        tunnelPanel = createTunnelPanel();
        tunnelWindow = toolWindowManager.registerToolWindow(TOOL_WINDOW_ID,
                tunnelPanel, ToolWindowAnchor.BOTTOM);
        tunnelWindow.setIcon(Icons.ICON_WATCH);

        DefaultActionGroup actionGroup = initToolbarActionGroup();
        ActionToolbar toolBar = ActionManager.getInstance()
                .createActionToolbar("tunnellij.Toolbar", actionGroup, false);

        tunnelPanel.add(toolBar.getComponent(), BorderLayout.WEST);
    }

    private void unregisterToolWindow() {
        ToolWindowManager toolWindowManager = ToolWindowManager
                .getInstance(project);
        toolWindowManager.unregisterToolWindow(TOOL_WINDOW_ID);
    }

    private static TunnelPanel createTunnelPanel() {
        TunnelPanel panel = new TunnelPanel();
        panel.setBackground(UIManager.getColor("Tree.textBackground"));
        return panel;
    }

    private DefaultActionGroup initToolbarActionGroup() {
        DefaultActionGroup actionGroup = new DefaultActionGroup();

        AnAction startAction = new StartAction();
        AnAction stopAction = new StopAction();
        AnAction clearAction = new ClearAction();
        AnAction clearSelectedAction = new ClearSelectedAction();
        AnAction aboutAction = new AboutAction();
        ToggleAction wrapAction = new WrapAction();

        actionGroup.add(startAction);
        actionGroup.add(stopAction);
        actionGroup.add(clearSelectedAction);
        actionGroup.add(clearAction);
        actionGroup.add(wrapAction);
        actionGroup.add(aboutAction);

        return actionGroup;
    }

    public static TunnelPanel getTunnelPanel(Project project) {
        return tunnelPanel;
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
