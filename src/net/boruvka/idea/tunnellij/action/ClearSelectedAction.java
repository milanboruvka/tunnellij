package net.boruvka.idea.tunnellij.action;

import net.boruvka.idea.tunnellij.TunnelPlugin;
import net.boruvka.idea.tunnellij.ui.Icons;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * @author boruvka
 * @since
 */
public class ClearSelectedAction extends AnAction {

    public ClearSelectedAction() {
        super("Remove selected call", "Remove selected call", Icons.ICON_REMOVE);
    }

    public void actionPerformed(AnActionEvent event) {
        Project project = (Project) event.getDataContext().getData("project");
        TunnelPanel tunnelPanel = TunnelPlugin.getTunnelPanel(project);
        tunnelPanel.clearSelected();
    }
}
