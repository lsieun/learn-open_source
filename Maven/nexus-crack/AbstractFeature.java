package org.sonatype.licensing.feature;

import java.util.Set;
import org.sonatype.licensing.feature.Feature;

public abstract class AbstractFeature implements Feature {
    public AbstractFeature() {
    }

    public boolean equals(Object var1) {
        if(var1 != null && var1 instanceof Feature) {
            Feature var2 = (Feature)var1;
            return this.getId().equals(var2.getId());
        } else {
            return false;
        }
    }

    public int hashCode() {
        return this.getId().hashCode();
    }

    public String toString() {
        return "Id: " + this.getId() + ", Name: " + this.getName() + ", Description: " + this.getDescription();
    }

    public Set<String> getSubFeatureIds() {
        return null;
    }
}