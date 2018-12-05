package yong.intern;

/**
 * Figures out the offset of inserting text and also produces an inserting text.
 */
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

    public int getInsertOffset() {
        return insertOffset;
    }

    public String getInsertText() {
        return insertText;
    }

    /**
     * Depending on the existing libraryDependencies, it follows the same structure so that will maintain the syntax valid.
     *
     * @return it returns nothing though, modifies the local variables; insertOffset and insertText.
     */
    private void build() {
        StringBuilder builder = new StringBuilder();
        int i = raw.lastIndexOf("libraryDependencies");
        if (i == -1) {
            if (raw.length() > 0) {
                builder.append("\n\n");
            }

            builder.append("libraryDependencies += ");
            generateDependencyFormat(builder, dependency.split("%"));
            insertOffset = raw.isEmpty() ? 0 : raw.length();
            insertText = builder.toString();
            return;
        }

        int j = raw.indexOf('\n', i);
        if (j == -1) {
            j = raw.length() - 1;
        }

        String line = raw.substring(i, j);
        if (line.contains("Seq")) {
            int k = j + 1;
            while (raw.charAt(k) == ' ' || raw.charAt(k) == '\t') {
                // insert indentations.
                builder.append(raw.charAt(k) == ' ' ? ' ' : '\t');
                k++;
            }

            generateDependencyFormat(builder, dependency.split("%"));

            insertOffset = j + 1;
            builder.append(",\n");
        } else {
            insertOffset = j + 1;
            builder.append("\n");
            int k = i - 1;
            while (k >= 0 && raw.charAt(k) != '\n') {
                builder.append(' ');
                k--;
            }

            builder.append("libraryDependencies += ");
            generateDependencyFormat(builder, dependency.split("%"));
        }

        insertText = builder.toString();
    }

    /**
     * Generates the dependency following a valid syntanx: putting double-quotes to each part.
     */
    private void generateDependencyFormat(StringBuilder builder, String[] split) {
        for (int s = 0; s < split.length; s++) {
            builder.append("\"");
            builder.append(split[s].replaceAll("\\s", ""));
            builder.append("\"");

            if (s + 1 < split.length) {
                builder.append(" % ");
            }
        }
    }
}
