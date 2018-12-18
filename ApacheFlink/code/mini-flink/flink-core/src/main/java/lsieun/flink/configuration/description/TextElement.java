package lsieun.flink.configuration.description;


import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

/**
 * Represents a text block in the {@link Description}.
 */
public class TextElement implements BlockElement, InlineElement {
    private final String format;
    private final List<InlineElement> elements;
    private final EnumSet<TextStyle> textStyles = EnumSet.noneOf(TextStyle.class);

    private TextElement(String format, List<InlineElement> elements) {
        this.format = format;
        this.elements = elements;
    }

    /**
     * Creates a block of text with placeholders ("%s") that will be replaced with proper string representation of
     * given {@link InlineElement}. For example:
     *
     * <p>{@code text("This is a text with a link %s", link("https://somepage", "to here"))}
     *
     * @param format text with placeholders for elements
     * @param elements elements to be put in the text
     * @return block of text
     */
    public static TextElement text(String format, InlineElement... elements) {
        return new TextElement(format, Arrays.asList(elements));
    }

    /**
     * Creates a simple block of text.
     *
     * @param format a simple block of text
     * @return block of text
     */
    public static TextElement text(String format) {
        return new TextElement(format, Collections.emptyList());
    }

    /**
     * Creates a block of text formatted as code.
     *
     * @param format a block of text that will be formatted as code
     * @return block of text formatted as code
     */
    public static TextElement code(String format) {
        TextElement element = text(format);
        element.textStyles.add(TextStyle.CODE);
        return element;
    }

    public String getFormat() {
        return format;
    }

    public List<InlineElement> getElements() {
        return elements;
    }

    public EnumSet<TextStyle> getStyles() {
        return textStyles;
    }

    @Override
    public void format(Formatter formatter) {
        formatter.format(this);
    }

    /**
     * Styles that can be applied to {@link TextElement} e.g. code, bold etc.
     */
    public enum TextStyle {
        CODE
    }
}
