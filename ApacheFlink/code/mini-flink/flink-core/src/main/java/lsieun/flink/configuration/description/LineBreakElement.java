package lsieun.flink.configuration.description;


/**
 * Represents a line break in the {@link Description}.
 */
public class LineBreakElement implements BlockElement {

    private LineBreakElement() {
    }

    /**
     * Creates a line break in the description.
     */
    public static LineBreakElement linebreak() {
        return new LineBreakElement();
    }

    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }
}
