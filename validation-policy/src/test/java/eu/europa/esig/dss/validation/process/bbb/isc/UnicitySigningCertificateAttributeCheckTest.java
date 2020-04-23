package eu.europa.esig.dss.validation.process.bbb.isc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlISC;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificateRef;
import eu.europa.esig.dss.diagnostic.jaxb.XmlFoundCertificates;
import eu.europa.esig.dss.diagnostic.jaxb.XmlOrphanCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlOrphanCertificateToken;
import eu.europa.esig.dss.diagnostic.jaxb.XmlRelatedCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.CertificateRefOrigin;
import eu.europa.esig.dss.policy.jaxb.Level;
import eu.europa.esig.dss.policy.jaxb.LevelConstraint;
import eu.europa.esig.dss.validation.process.bbb.AbstractTestCheck;
import eu.europa.esig.dss.validation.process.bbb.isc.checks.UnicitySigningCertificateAttributeCheck;

public class UnicitySigningCertificateAttributeCheckTest extends AbstractTestCheck {

	@Test
	public void singleSigningCertRefAttributePresentCheck() throws Exception {
		XmlCertificateRef xmlCertificateRef = new XmlCertificateRef();
		xmlCertificateRef.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		
		XmlRelatedCertificate xmlRelatedCertificate = new XmlRelatedCertificate();
		xmlRelatedCertificate.setCertificate(new XmlCertificate());
		xmlRelatedCertificate.getCertificateRefs().add(xmlCertificateRef);
		
		XmlFoundCertificates xmlFoundCertificates = new XmlFoundCertificates();
		xmlFoundCertificates.getRelatedCertificates().add(xmlRelatedCertificate);
		
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(xmlFoundCertificates);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlISC result = new XmlISC();
		UnicitySigningCertificateAttributeCheck uscac = new UnicitySigningCertificateAttributeCheck(i18nProvider, result,
				new SignatureWrapper(sig), constraint);
		uscac.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.OK, constraints.get(0).getStatus());
	}

	@Test
	public void noSigningCertRefAttributePresentCheck() throws Exception {
		XmlCertificateRef xmlCertificateRef = new XmlCertificateRef();
		xmlCertificateRef.setOrigin(CertificateRefOrigin.COMPLETE_CERTIFICATE_REFS);
		
		XmlRelatedCertificate xmlRelatedCertificate = new XmlRelatedCertificate();
		xmlRelatedCertificate.setCertificate(new XmlCertificate());
		xmlRelatedCertificate.getCertificateRefs().add(xmlCertificateRef);
		
		XmlFoundCertificates xmlFoundCertificates = new XmlFoundCertificates();
		xmlFoundCertificates.getRelatedCertificates().add(xmlRelatedCertificate);
		
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(xmlFoundCertificates);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlISC result = new XmlISC();
		UnicitySigningCertificateAttributeCheck uscac = new UnicitySigningCertificateAttributeCheck(i18nProvider, result,
				new SignatureWrapper(sig), constraint);
		uscac.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
	}
	
	@Test
	public void multipleSigningCertRefAttributePresentCheck() throws Exception {
		XmlCertificateRef xmlCertificateRef = new XmlCertificateRef();
		xmlCertificateRef.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		
		XmlRelatedCertificate xmlRelatedCertificate = new XmlRelatedCertificate();
		xmlRelatedCertificate.setCertificate(new XmlCertificate());
		xmlRelatedCertificate.getCertificateRefs().add(xmlCertificateRef);
		
		XmlFoundCertificates xmlFoundCertificates = new XmlFoundCertificates();
		xmlFoundCertificates.getRelatedCertificates().add(xmlRelatedCertificate);
		
		XmlOrphanCertificate xmlOrphanCertificate = new XmlOrphanCertificate();
		xmlOrphanCertificate.setToken(new XmlOrphanCertificateToken());
		xmlOrphanCertificate.getCertificateRefs().add(xmlCertificateRef);
		
		xmlFoundCertificates.getOrphanCertificates().add(xmlOrphanCertificate);
		
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(xmlFoundCertificates);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlISC result = new XmlISC();
		UnicitySigningCertificateAttributeCheck uscac = new UnicitySigningCertificateAttributeCheck(i18nProvider, result,
				new SignatureWrapper(sig), constraint);
		uscac.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
	}
	
}