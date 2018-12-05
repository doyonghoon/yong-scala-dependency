package yong.intern;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Lets user search dependency and select from the list.
 */
public class SearchDialog extends DialogWrapper {

    private static final int TEXT_FIELD_COLUMNS = 50;
    private static final int TEXT_FIELD_HEIGHT = 50;
    private static final int MAX_WIDTH = 550;

    private JPanel mainPanel;
    private JTextField searchField;
    private DefaultListModel<String> listModel;
    private JButton searchButton;
    private JScrollPane scrollPane;
    private JList dependencyList;

    protected SearchDialog(@Nullable Project project) {
        super(project);
        setTitle("Add Scala Dependency");
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        listModel = new DefaultListModel<>();
        dependencyList = new JList();
        scrollPane = new JScrollPane();

        scrollPane.setViewportView(dependencyList);
        dependencyList.setModel(listModel);
        dependencyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JPanel searchPanel = createSearchPanel();

        mainPanel.add(searchPanel);
        mainPanel.add(scrollPane);

        init();
    }

    /**
     * A helper function to get the selected item from the list.
     *
     * @return a selected dependency.
     */
    public String getSelectedDependency() {
        int i = dependencyList.getSelectedIndex();
        if (i > -1) {
            return listModel.getElementAt(i);
        }

        return "";
    }

    /**
     * A helper function to set up the search panel.
     */
    private JPanel createSearchPanel() {
        Dimension dim = new Dimension(MAX_WIDTH, TEXT_FIELD_HEIGHT);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setMinimumSize(dim);
        panel.setMaximumSize(dim);
        searchField = new JTextField(TEXT_FIELD_COLUMNS);
        searchButton = new JButton("search");
        searchButton.addActionListener(e -> bindData(DependencyRequest.requestDependency(searchField.getText())));

        panel.add(searchField);
        panel.add(searchButton);
        return panel;
    }

    /**
     * A helper function to bind a list of dependencies to the view.
     */
    private void bindData(List<String> list) {
        if (list == null) {
            return;
        }

        listModel.clear();
        list.forEach(listModel::addElement);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return mainPanel;
    }
}
