package net.boruvka.idea.tunnellij;

import java.awt.BorderLayout;

import javax.swing.UIManager;

import net.boruvka.idea.tunnellij.action.AboutAction;
import net.boruvka.idea.tunnellij.action.ClearAction;
import net.boruvka.idea.tunnellij.action.ClearSelectedAction;
import net.boruvka.idea.tunnellij.action.StartAction;
import net.boruvka.idea.tunnellij.action.StopAction;
import net.boruvka.idea.tunnellij.action.WrapAction;
import net.boruvka.idea.tunnellij.ui.TunnelPanel;

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import org.jetbrains.annotations.NotNull;

public class TunnelToolWindowFactory implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        TunnelPlugin.getInstance().loadProperties();

        TunnelPanel tunnelPanel = createTunnelPanel();
        TunnelPlugin.setTunnelPanel(project, tunnelPanel);

        DefaultActionGroup actionGroup = initToolbarActionGroup();
        ActionToolbar toolBar = ActionManager.getInstance()
                .createActionToolbar("tunnellij.Toolbar", actionGroup, false);
        toolBar.setTargetComponent(tunnelPanel);

        tunnelPanel.add(toolBar.getComponent(), BorderLayout.WEST);

        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(tunnelPanel, "", false);
        toolWindow.getContentManager().addContent(content);
    }

    private TunnelPanel createTunnelPanel() {
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
}
