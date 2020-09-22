package eu.europa.esig.dss.asic.xades.validation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.diagnostic.SignatureWrapper;
import eu.europa.esig.dss.diagnostic.jaxb.XmlSignatureScope;
import eu.europa.esig.dss.enumerations.SignatureScopeType;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.FileDocument;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.validation.AdvancedSignature;
import eu.europa.esig.dss.validation.SignedDocumentValidator;

public class ASiCEWithCAdESCounterSignatureTest extends AbstractASiCWithXAdESTestValidation {

	@Override
	protected DSSDocument getSignedDocument() {
		return new FileDocument("src/test/resources/validation/container-with-counter-signature.asice");
	}
	
	@Override
	protected void checkSignatureScopes(DiagnosticData diagnosticData) {
		super.checkSignatureScopes(diagnosticData);
		
		for (SignatureWrapper signature : diagnosticData.getSignatures()) {
			List<XmlSignatureScope> signatureScopes = signature.getSignatureScopes();
			assertTrue(Utils.isCollectionNotEmpty(signatureScopes));
			
			boolean fullDocumentFound = false;
			for (XmlSignatureScope signatureScope : signatureScopes) {
				if (SignatureScopeType.FULL.equals(signatureScope.getScope())) {
					fullDocumentFound = true;
					if (signature.isCounterSignature()) {
						assertEquals("service-body.json", signatureScope.getName());
					} else {
						assertEquals("service-body-excl-debtor.json", signatureScope.getName());
					}
				}
			}
			assertTrue(fullDocumentFound);
		}
	}
	
	@Override
	protected void verifyOriginalDocuments(SignedDocumentValidator validator, DiagnosticData diagnosticData) {
		List<AdvancedSignature> signatures = validator.getSignatures();
		assertEquals(1, signatures.size());
		
		AdvancedSignature signature = signatures.iterator().next();
		List<DSSDocument> originalDocuments = validator.getOriginalDocuments(signatures.iterator().next());
		assertEquals(0, originalDocuments.size());
		// a custom type is defined
		
		List<AdvancedSignature> counterSignatures = signature.getCounterSignatures();
		assertEquals(1, counterSignatures.size());
		
		originalDocuments = validator.getOriginalDocuments(counterSignatures.iterator().next());
		assertEquals(1, originalDocuments.size());
		assertEquals("service-body.json", originalDocuments.get(0).getName());
	}

}
