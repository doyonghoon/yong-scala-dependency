package yong.intern;

public class DependencyBuilder {

    private final String raw;
    private final String dependency;
    private int insertOffset;
    private String insertText;

    public DependencyBuilder(String raw, String dependency) {
        this.raw = raw;
        this.dependency = dependency;
        build();
    }

    private void build() {
        StringBuilder builder = new StringBuilder();
        int i = raw.indexOf("libraryDependencies");
        int j = raw.indexOf('\n', i);
        insertOffset = j + 1;
        if (raw.substring(i, j).contains("Seq")) {
            int k = j + 1;
            while (raw.charAt(k) == ' ' || raw.charAt(k) == '\t') {
                // insert indentations.
                builder.append(raw.charAt(k) == ' ' ? ' ' : '\t');
                k++;
            }

            String split[] = dependency.split("%");
            for (int s = 0; s < split.length; s++) {
                builder.append("\"");
                builder.append(split[s].replaceAll("\\s", ""));
                builder.append("\"");

                if (s + 1 < split.length) {
                    builder.append(" % ");
                }
            }

            builder.append(",\n");
        } else {
            insertOffset = j;
            builder.append(",\n");
            int k = i - 1;
            while (k >= 0 && raw.charAt(k) != '\n') {
                builder.append(' ');
                k--;
            }

            builder.append("libraryDependencies += ").append(dependency);
        }

        insertText = builder.toString();
    }

    public int getInsertOffset() {
        return insertOffset;
    }

    public String getInsertText() {
        return insertText;
    }
}