package org.sonatype.licensing.feature;

import java.util.Set;

public interface Feature {
    String getId();

    String getName();

    String getDescription();

    String getShortName();

    Set<String> getSubFeatureIds();
}