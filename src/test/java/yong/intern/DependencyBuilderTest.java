package yong.intern;

import org.junit.Assert;
import org.junit.Test;

public class DependencyBuilderTest {

    @Test
    public void buildWithSeqAndIndentations() {
        String raw = "libraryDependencies ++= Seq(\n    )";
        String dependency = "hello % hi % greeting";
        DependencyBuilder builder = new DependencyBuilder(raw, dependency);
        int i = builder.getInsertOffset();
        String text = builder.getInsertText();
        Assert.assertEquals("    \"hello\" % \"hi\" % \"greeting\",\n", text);

        String res = raw.substring(0, i + 1) + text + raw.substring(i + 1, raw.length());
        Assert.assertEquals("libraryDependencies ++= Seq(\n" +
                "     \"hello\" % \"hi\" % \"greeting\",\n" +
                "   )", res);
    }

    @Test
    public void buildWithSeqNotIncluded() {
        String raw = "libraryDependencies += net.sourceforge.htmlcleaner % htmlcleaner % 2.4";
        String dependency = "hello % hi % greeting";
        DependencyBuilder builder = new DependencyBuilder(raw, dependency);
        int i = builder.getInsertOffset();
        String text = builder.getInsertText();
        String res = raw.substring(0, i + 1) + text + raw.substring(i + 1, raw.length());
        Assert.assertEquals("libraryDependencies += net.sourceforge.htmlcleaner % htmlcleaner % 2.4,\n" +
                "libraryDependencies += hello % hi % greeting", res);
    }

    @Test
    public void buildWithLibrariesNotFound() {
        String raw = "name := helloscala";
        String dependency = "hello % hi % greeting";
        DependencyBuilder builder = new DependencyBuilder(raw, dependency);
        int i = builder.getInsertOffset();
        String text = builder.getInsertText();

        String res = raw.substring(0, i) + text + raw.substring(i, raw.length());
        System.out.println(res);
        Assert.assertEquals("name := helloscala\n" +
                "\n" +
                "libraryDependencies += hello % hi % greeting", res);
    }

    @Test
    public void buildWithEmptyString() {
        String raw = "";
        String dependency = "hello % hi % greeting";
        DependencyBuilder builder = new DependencyBuilder(raw, dependency);
        int i = builder.getInsertOffset();
        String text = builder.getInsertText();

        Assert.assertEquals(0, i);
        Assert.assertEquals("libraryDependencies += hello % hi % greeting", text);
    }
}