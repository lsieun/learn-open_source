package com.sonatype.nexus.licensing.ext;

import com.google.common.collect.Sets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Singleton;
import org.sonatype.licensing.feature.AbstractFeature;

@Named("NexusProfessional")
@Singleton
public class NexusProfessionalFeature extends AbstractFeature {
    public static final String ID = "NexusProfessional";
    public static final String DESCRIPTION = "Nexus Professional Edition";
    public static final String NAME = "Professional";
    public static final String SHORT_NAME = "PRO";
    public static final String F_CROWD = "crowd";
    public static final String F_USERTOKEN = "usertoken";
    public static final String F_STAGING = "staging";
    public static final String F_TAGS = "tags";
    private static final Set<String> SUBFEATURES;

    static {
        HashSet features = Sets.newHashSet(new String[]{"crowd", "usertoken", "staging", "tags"});
        SUBFEATURES = Collections.unmodifiableSet(features);
    }

    public NexusProfessionalFeature() {
    }

    public String getId() {
        return "NexusProfessional";
    }

    public String getName() {
        return "Professional";
    }

    public String getDescription() {
        return "Nexus Professional Edition";
    }

    public String getShortName() {
        return "PRO";
    }

    public Set<String> getSubFeatureIds() {
        return SUBFEATURES;
    }
}