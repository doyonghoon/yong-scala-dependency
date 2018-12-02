package yong.intern;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

public class SearchBarAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        SearchDialog dialog = new SearchDialog(project);
        dialog.show();
        if (dialog.isOK()) {
            updateBuildBST(e.getProject(), getBuildSBTFile(e.getProject()), dialog.getSelectedDependency());
        }
    }

    /**
     * Only visible to the project having build.sbt PsiFile.
     */
    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        e.getPresentation().setEnabledAndVisible(getBuildSBTFile(e.getProject()) != null);
    }

    private void updateBuildBST(Project project, PsiFile file, String dependency) {
        if (project == null || file == null || dependency == null) {
            return;
        }

        Document doc = file.getViewProvider().getDocument();
        DependencyBuilder builder = new DependencyBuilder(doc.getText(), dependency);
        WriteCommandAction.runWriteCommandAction(project, () -> doc.insertString(builder.getInsertOffset(), builder.getInsertText()));
    }

    private PsiFile getBuildSBTFile(Project p) {
        PsiFile files[] = FilenameIndex.getFilesByName(p, "build.sbt", GlobalSearchScope.allScope(p));
        if (files != null && files.length > 0) {
            return files[0];
        }

        return null;
    }
}
