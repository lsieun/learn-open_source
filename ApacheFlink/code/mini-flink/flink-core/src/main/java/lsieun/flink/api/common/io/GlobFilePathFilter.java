package lsieun.flink.api.common.io;


import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lsieun.flink.annotation.Internal;
import lsieun.flink.core.fs.Path;
import lsieun.flink.util.Preconditions;

/**
 * Class for determining if a particular file should be included or excluded
 * based on a set of include and exclude glob filters.
 *
 * Glob filter support the following expressions:
 * <ul>
 *     <li>* - matches any number of any characters including none</li>
 *     <li>** - matches any file in all subdirectories</li>
 *     <li>? - matches any single character</li>
 *     <li>[abc] - matches one of the characters listed in a brackets</li>
 *     <li>[a-z] - matches one character from the range given in the brackets</li>
 * </ul>
 *
 * <p> If does not match an include pattern it is excluded. If it matches and include
 * pattern but also matches an exclude pattern it is excluded.
 *
 * <p> If no patterns are provided all files are included
 */
@Internal
public class GlobFilePathFilter extends FilePathFilter {

    private static final long serialVersionUID = 1L;

    private final List<String> includePatterns;
    private final List<String> excludePatterns;

    // Path matchers are not serializable so we are delaying their
    // creation until they are used
    private transient ArrayList<PathMatcher> includeMatchers;
    private transient ArrayList<PathMatcher> excludeMatchers;

    /**
     * Constructor for GlobFilePathFilter that will match all files
     */
    public GlobFilePathFilter() {
        this(Collections.<String>emptyList(), Collections.<String>emptyList());
    }

    /**
     * Constructor for GlobFilePathFilter
     *
     * @param includePatterns glob patterns for files to include
     * @param excludePatterns glob patterns for files to exclude
     */
    public GlobFilePathFilter(List<String> includePatterns, List<String> excludePatterns) {
        this.includePatterns = Preconditions.checkNotNull(includePatterns);
        this.excludePatterns = Preconditions.checkNotNull(excludePatterns);
    }

    private ArrayList<PathMatcher> buildPatterns(List<String> patterns) {
        FileSystem fileSystem = FileSystems.getDefault();
        ArrayList<PathMatcher> matchers = new ArrayList<>(patterns.size());

        for (String patternStr : patterns) {
            matchers.add(fileSystem.getPathMatcher("glob:" + patternStr));
        }

        return matchers;
    }

    @Override
    public boolean filterPath(Path filePath) {
        if (getIncludeMatchers().isEmpty() && getExcludeMatchers().isEmpty()) {
            return false;
        }

        // compensate for the fact that Flink paths are slashed
        final String path = filePath.hasWindowsDrive() ?
                filePath.getPath().substring(1) :
                filePath.getPath();

        final java.nio.file.Path nioPath = Paths.get(path);

        for (PathMatcher matcher : getIncludeMatchers()) {
            if (matcher.matches(nioPath)) {
                return shouldExclude(nioPath);
            }
        }

        return true;
    }

    private ArrayList<PathMatcher> getIncludeMatchers() {
        if (includeMatchers == null) {
            includeMatchers = buildPatterns(includePatterns);
        }
        return includeMatchers;
    }

    private ArrayList<PathMatcher> getExcludeMatchers() {
        if (excludeMatchers == null) {
            excludeMatchers = buildPatterns(excludePatterns);
        }
        return excludeMatchers;
    }

    private boolean shouldExclude(java.nio.file.Path nioPath) {
        for (PathMatcher matcher : getExcludeMatchers()) {
            if (matcher.matches(nioPath)) {
                return true;
            }
        }
        return false;
    }

}
