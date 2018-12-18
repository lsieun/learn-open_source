package lsieun.flink.configuration.description;


import java.util.ArrayList;
import java.util.List;

/**
 * Description for {@link lsieun.flink.configuration.ConfigOption}. Allows providing multiple rich formats.
 */
public class Description {

    private final List<BlockElement> blocks;

    private Description(List<BlockElement> blocks) {
        this.blocks = blocks;
    }

    public List<BlockElement> getBlocks() {
        return blocks;
    }

    public static DescriptionBuilder builder() {
        return new DescriptionBuilder();
    }

    /**
     * Builder for {@link Description}. Allows adding a rich formatting like lists, links, linebreaks etc.
     * For example:
     * <pre>{@code
     * Description description = Description.builder()
     * 	.text("This is some list: ")
     * 	.list(
     * 		text("this is first element of list"),
     * 		text("this is second element of list with a %s", link("https://link")))
     * 	.build();
     * }</pre>
     */
    public static class DescriptionBuilder {

        private final List<BlockElement> blocks = new ArrayList<BlockElement>();

        /**
         * Creates description representation.
         */
        public Description build() {
            return new Description(blocks);
        }

        /**
         * Adds a block of text with placeholders ("%s") that will be replaced with proper string representation of
         * given {@link InlineElement}. For example:
         *
         * <p>{@code text("This is a text with a link %s", link("https://somepage", "to here"))}
         *
         * @param format   text with placeholders for elements
         * @param elements elements to be put in the text
         * @return description with added block of text
         */
        public DescriptionBuilder text(String format, InlineElement... elements) {
            blocks.add(TextElement.text(format, elements));
            return this;
        }

        /**
         * Creates a simple block of text.
         *
         * @param text a simple block of text
         * @return block of text
         */
        public DescriptionBuilder text(String text) {
            blocks.add(TextElement.text(text));
            return this;
        }

        /**
         * Block of description add.
         *
         * @param block block of description to add
         * @return block of description
         */
        public DescriptionBuilder add(BlockElement block) {
            blocks.add(block);
            return this;
        }

        /**
         * Creates a line break in the description.
         */
        public DescriptionBuilder linebreak() {
            blocks.add(LineBreakElement.linebreak());
            return this;
        }

        /**
         * Adds a bulleted list to the description.
         */
        public DescriptionBuilder list(InlineElement... elements) {
            blocks.add(ListElement.list(elements));
            return this;
        }

    }
}
