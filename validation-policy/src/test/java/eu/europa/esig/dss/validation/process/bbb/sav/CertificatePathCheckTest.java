package eu.europa.esig.dss.validation.process.bbb.sav;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import eu.europa.esig.dss.detailedreport.jaxb.XmlConstraint;
import eu.europa.esig.dss.detailedreport.jaxb.XmlSAV;
import eu.europa.esig.dss.detailedreport.jaxb.XmlStatus;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlCertificateRef;
import eu.europa.esig.dss.diagnostic.jaxb.XmlChainItem;
import eu.europa.esig.dss.diagnostic.jaxb.XmlFoundCertificates;
import eu.europa.esig.dss.diagnostic.jaxb.XmlRelatedCertificate;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignature;
import eu.europa.esig.dss.enumerations.CertificateRefOrigin;
import eu.europa.esig.dss.policy.jaxb.Level;
import eu.europa.esig.dss.policy.jaxb.LevelConstraint;
import eu.europa.esig.dss.validation.process.bbb.AbstractTestCheck;
import eu.europa.esig.dss.validation.process.bbb.sav.checks.CertificatePathCheck;

public class CertificatePathCheckTest extends AbstractTestCheck {

	@Test
	public void certificatePathCheckTest() throws Exception {
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(new XmlFoundCertificates());
		
		sig.getCertificateChain().add(getXmlChainItem("C-Id-1"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-2"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-3"));
		
		XmlRelatedCertificate xmlRelatedCertificateOne = getXmlRelatedCertificate("C-Id-1");
		xmlRelatedCertificateOne.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateTwo = getXmlRelatedCertificate("C-Id-2");
		xmlRelatedCertificateTwo.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateThree = getXmlRelatedCertificate("C-Id-3");
		xmlRelatedCertificateThree.getCertificateRefs().add(getSigningCertificateRef());
		
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateOne);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateTwo);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateThree);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlSAV result = new XmlSAV();
		CertificatePathCheck cpc = new CertificatePathCheck(i18nProvider, result, new SignatureWrapper(sig), constraint);
		cpc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.OK, constraints.get(0).getStatus());
	}

	@Test
	public void failedCertificatePathCheckTest() throws Exception {
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(new XmlFoundCertificates());
		
		sig.getCertificateChain().add(getXmlChainItem("C-Id-1"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-2"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-3"));
		
		XmlRelatedCertificate xmlRelatedCertificateOne = getXmlRelatedCertificate("C-Id-1");
		xmlRelatedCertificateOne.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateTwo = getXmlRelatedCertificate("C-Id-2");
		xmlRelatedCertificateTwo.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateThree = getXmlRelatedCertificate("C-Id-4");
		xmlRelatedCertificateThree.getCertificateRefs().add(getSigningCertificateRef());
		
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateOne);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateTwo);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateThree);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlSAV result = new XmlSAV();
		CertificatePathCheck cpc = new CertificatePathCheck(i18nProvider, result, new SignatureWrapper(sig), constraint);
		cpc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
	}

	@Test
	public void additionalReferenceCertificatePathCheckTest() throws Exception {
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(new XmlFoundCertificates());
		
		sig.getCertificateChain().add(getXmlChainItem("C-Id-1"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-2"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-3"));
		
		XmlRelatedCertificate xmlRelatedCertificateOne = getXmlRelatedCertificate("C-Id-1");
		xmlRelatedCertificateOne.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateTwo = getXmlRelatedCertificate("C-Id-2");
		xmlRelatedCertificateTwo.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateThree = getXmlRelatedCertificate("C-Id-3");
		xmlRelatedCertificateThree.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateFour = getXmlRelatedCertificate("C-Id-4");
		xmlRelatedCertificateFour.getCertificateRefs().add(getSigningCertificateRef());
		
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateOne);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateTwo);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateThree);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateFour);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlSAV result = new XmlSAV();
		CertificatePathCheck scrc = new CertificatePathCheck(i18nProvider, result, new SignatureWrapper(sig), constraint);
		scrc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.OK, constraints.get(0).getStatus());
	}

	@Test
	public void additionalCertificateCertificatePathCheckTest() throws Exception {
		XmlSignature sig = new XmlSignature();
		sig.setFoundCertificates(new XmlFoundCertificates());
		
		sig.getCertificateChain().add(getXmlChainItem("C-Id-1"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-2"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-3"));
		sig.getCertificateChain().add(getXmlChainItem("C-Id-4"));
		
		XmlRelatedCertificate xmlRelatedCertificateOne = getXmlRelatedCertificate("C-Id-1");
		xmlRelatedCertificateOne.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateTwo = getXmlRelatedCertificate("C-Id-2");
		xmlRelatedCertificateTwo.getCertificateRefs().add(getSigningCertificateRef());
		XmlRelatedCertificate xmlRelatedCertificateThree = getXmlRelatedCertificate("C-Id-3");
		xmlRelatedCertificateThree.getCertificateRefs().add(getSigningCertificateRef());
		
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateOne);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateTwo);
		sig.getFoundCertificates().getRelatedCertificates().add(xmlRelatedCertificateThree);

		LevelConstraint constraint = new LevelConstraint();
		constraint.setLevel(Level.FAIL);

		XmlSAV result = new XmlSAV();
		CertificatePathCheck scrc = new CertificatePathCheck(i18nProvider, result, new SignatureWrapper(sig), constraint);
		scrc.execute();

		List<XmlConstraint> constraints = result.getConstraint();
		assertEquals(1, constraints.size());
		assertEquals(XmlStatus.NOT_OK, constraints.get(0).getStatus());
	}
	
	private XmlChainItem getXmlChainItem(String id) {
		XmlChainItem xmlChainItem = new XmlChainItem();
		xmlChainItem.setCertificate(getXmlCertificate(id));
		return xmlChainItem;
	}
	
	private XmlCertificate getXmlCertificate(String id) {
		XmlCertificate xmlCertificate = new XmlCertificate();
		xmlCertificate.setId(id);
		return xmlCertificate;
	}
	
	private XmlRelatedCertificate getXmlRelatedCertificate(String id) {
		XmlRelatedCertificate xmlRelatedCertificate = new XmlRelatedCertificate();
		xmlRelatedCertificate.setCertificate(getXmlCertificate(id));
		return xmlRelatedCertificate;
	}
	
	private XmlCertificateRef getSigningCertificateRef() {
		XmlCertificateRef xmlCertificateRef = new XmlCertificateRef();
		xmlCertificateRef.setOrigin(CertificateRefOrigin.SIGNING_CERTIFICATE);
		return xmlCertificateRef;
	}
	
}
