package net.boruvka.idea.tunnellij.action;

import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.ui.Icons;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * @author boruvka
 * @since
 */
public class StartAction extends AnAction {

    public StartAction() {
        super("Start tunnellij", "Start tunnellij", Icons.ICON_START);
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) return;
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel(project);
        try {
            tunnelPanel.start();
        } catch (Exception e) {
            Messages.showMessageDialog("Error when starting server: "
                    + e.getMessage(), "Error", Messages.getErrorIcon());
        }

    }

    public void update(AnActionEvent event) {
        Project project = event.getProject();
        Presentation p = event.getPresentation();
        if (project == null) {
            p.setEnabled(false);
            return;
        }
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel(project);
        p.setEnabled(tunnelPanel != null && !tunnelPanel.isRunning());
        p.setVisible(true);
    }
}
