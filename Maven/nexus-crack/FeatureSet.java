package org.sonatype.licensing.feature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.sonatype.licensing.feature.Feature;

public class FeatureSet implements Iterable<Feature> {
    private final List<Feature> aaz = new ArrayList();

    public FeatureSet() {
    }

    public void addFeature(Feature feature) {
        if(feature == null) {
            throw new NullPointerException("feature must not be null!");
        } else {
            this.aaz.add(feature);
        }
    }

    public boolean hasFeature(Feature feature) {
        HashSet featureSet = new HashSet();
        Iterator it = this.aaz.iterator();

        while(it.hasNext()) {
            Feature curFeature = (Feature)it.next();
            featureSet.add(curFeature.getId());
            if(curFeature.getSubFeatureIds() != null) {
                featureSet.addAll(curFeature.getSubFeatureIds());
            }
        }

        return featureSet.contains(feature.getId());
    }

    public boolean hasFeatures() {
        return this.aaz.size() > 0;
    }

    /** 为什么叫chr()呢？
    Collections.unmodifiableList();也需要学一学
     */
    List<Feature> chr() {
        return Collections.unmodifiableList(this.aaz);
    }

    public Iterator<Feature> iterator() {
        return this.chr().iterator();
    }

    public int size() {
        return this.aaz.size();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("FeatureSet{");
        sb.append("features=").append(this.aaz);
        sb.append('}');
        return sb.toString();
    }
}