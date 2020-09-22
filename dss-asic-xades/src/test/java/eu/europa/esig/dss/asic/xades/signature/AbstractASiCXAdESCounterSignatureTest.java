package eu.europa.esig.dss.asic.xades.signature;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import eu.europa.esig.dss.asic.xades.ASiCWithXAdESSignatureParameters;
import eu.europa.esig.dss.diagnostic.DiagnosticData;
import eu.europa.esig.dss.enumerations.ASiCContainerType;
import eu.europa.esig.dss.enumerations.SignatureLevel;
import eu.europa.esig.dss.model.DSSDocument;
import eu.europa.esig.dss.model.MimeType;
import eu.europa.esig.dss.test.signature.AbstractCounterSignatureTest;
import eu.europa.esig.dss.utils.Utils;
import eu.europa.esig.dss.xades.XAdESTimestampParameters;
import eu.europa.esig.dss.xades.signature.XAdESCounterSignatureParameters;

public abstract class AbstractASiCXAdESCounterSignatureTest extends AbstractCounterSignatureTest<ASiCWithXAdESSignatureParameters, 
					XAdESTimestampParameters, XAdESCounterSignatureParameters> {

	@Override
	protected MimeType getExpectedMime() {
		if (ASiCContainerType.ASiC_S.equals(getSignatureParameters().aSiC().getContainerType())) {
			return MimeType.ASICS;
		}
		return MimeType.ASICE;
	}

	@Override
	protected List<DSSDocument> getOriginalDocuments() {
		return Collections.singletonList(getDocumentToSign());
	}

	@Override
	protected boolean isBaselineT() {
		SignatureLevel signatureLevel = getSignatureParameters().getSignatureLevel();
		return SignatureLevel.XAdES_BASELINE_LTA.equals(signatureLevel) || SignatureLevel.XAdES_BASELINE_LT.equals(signatureLevel)
				|| SignatureLevel.XAdES_BASELINE_T.equals(signatureLevel);
	}

	@Override
	protected boolean isBaselineLTA() {
		return SignatureLevel.XAdES_BASELINE_LTA.equals(getSignatureParameters().getSignatureLevel());
	}
	
	@Override
	protected void checkContainerInfo(DiagnosticData diagnosticData) {
		assertNotNull(diagnosticData.getContainerInfo());
		assertEquals(getSignatureParameters().aSiC().getContainerType(), diagnosticData.getContainerType());
		assertNotNull(diagnosticData.getMimetypeFileContent());
		assertTrue(Utils.isCollectionNotEmpty(diagnosticData.getContainerInfo().getContentFiles()));
	}

}
