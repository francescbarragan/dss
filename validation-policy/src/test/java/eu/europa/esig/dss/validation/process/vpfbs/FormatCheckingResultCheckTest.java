package eu.europa.esig.dss.validation.process.vpfbs;

import eu.europa.esig.dss.detailedreport.jaxb.XmlConclusion;
import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlFC;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.detailedreport.jaxb.XmlValidationProcessBasicSignature;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.Indication;
import eu.europa.esig.dss.enumerations.SubIndication;
import eu.europa.esig.dss.policy.jaxb.Level;
import eu.europa.esig.dss.policy.jaxb.LevelConstraint;
import eu.europa.esig.dss.validation.process.bbb.AbstractTestCheck;
import eu.europa.esig.dss.validation.process.vpfbs.checks.FormatCheckingResultCheck;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FormatCheckingResultCheckTest extends AbstractTestCheck {

    @Test
    public void validTest() throws Exception {
        XmlFC xmlFC = new XmlFC();
        XmlConclusion xmlConclusion = new XmlConclusion();
        xmlConclusion.setIndication(Indication.PASSED);
        xmlFC.setConclusion(xmlConclusion);

        LevelConstraint constraint = new LevelConstraint();
        constraint.setLevel(Level.FAIL);

        XmlValidationProcessBasicSignature result = new XmlValidationProcessBasicSignature();
        FormatCheckingResultCheck fcrc = new FormatCheckingResultCheck<>(
                i18nProvider, result, xmlFC, new SignatureWrapper(new XmlSignature()), constraint);
        fcrc.execute();

        List<XmlConstraint> constraints = result.getConstraint();
        assertEquals(1, constraints.size());
        assertEquals(XmlStatus.OK, constraints.get(0).getStatus());
    }

    @Test
    public void invalidTest() throws Exception {
        XmlFC xmlFC = new XmlFC();
        XmlConclusion xmlConclusion = new XmlConclusion();
        xmlConclusion.setIndication(Indication.FAILED);
        xmlConclusion.setSubIndication(SubIndication.FORMAT_FAILURE);
        xmlFC.setConclusion(xmlConclusion);

        LevelConstraint constraint = new LevelConstraint();
        constraint.setLevel(Level.FAIL);

        XmlValidationProcessBasicSignature result = new XmlValidationProcessBasicSignature();
        FormatCheckingResultCheck fcrc = new FormatCheckingResultCheck<>(
                i18nProvider, result, xmlFC, new SignatureWrapper(new XmlSignature()), constraint);
        fcrc.execute();

        List<XmlConstraint> constraints = result.getConstraint();
        assertEquals(1, constraints.size());
        assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
    }

}
