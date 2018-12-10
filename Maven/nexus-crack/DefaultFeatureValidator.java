package org.sonatype.licensing.internal;

import javax.inject.Named;
import org.sonatype.licensing.LicenseKey;
import org.sonatype.licensing.LicensingException;
import org.sonatype.licensing.feature.Feature;
import org.sonatype.licensing.feature.FeatureValidator;

@Named("licensing.default")
public class DefaultFeatureValidator implements FeatureValidator {
    public DefaultFeatureValidator() {
    }

    public boolean isValid(Feature var1, LicenseKey var2) {
        return var2.isEvaluation() || var2.getFeatureSet().hasFeature(var1);
    }

    public void validate(Feature var1, LicenseKey var2) throws LicensingException {
        if(!this.isValid(var1, var2)) {
            throw new LicensingException("License does not permit use of feature \'" + var1.getId() + "\'");
        }
    }
}