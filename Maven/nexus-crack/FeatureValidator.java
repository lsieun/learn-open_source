package org.sonatype.licensing.feature;

import org.sonatype.licensing.LicenseKey;
import org.sonatype.licensing.LicensingException;
import org.sonatype.licensing.feature.Feature;

public interface FeatureValidator {
    boolean isValid(Feature var1, LicenseKey var2);

    void validate(Feature var1, LicenseKey var2) throws LicensingException;
}