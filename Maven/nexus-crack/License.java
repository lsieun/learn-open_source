package org.sonatype.licensing;

import java.util.Date;
import java.util.List;
import org.sonatype.licensing.feature.FeatureSet;

public interface License {
    /* 联系方式 */
    String getContactName();
    void setContactName(String var1);

    String getContactEmailAddress();
    void setContactEmailAddress(String var1);

    String getContactTelephone();
    void setContactTelephone(String var1);

    String getContactCompany();
    void setContactCompany(String var1);

    String getContactCountry();
    void setContactCountry(String var1);

    /* 是否是评估版本 */
    boolean isEvaluation();
    void setEvaluation(boolean var1);

    /* 功能集合 */
    List<String> getRawFeatures();

    FeatureSet getFeatureSet();
    void setFeatureSet(FeatureSet var1);

    /* 日期 */
    Date getEffectiveDate();
    void setEffectiveDate(Date var1);

    Date getExpirationDate();
    void setExpirationDate(Date var1);
}