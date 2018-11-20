package org.cms.hios.common.pdf.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.cms.hios.common.pdf.util.PDFGenerator;
import org.cms.hios.common.pdf.util.PDFGeneratorFromHTMLSource;
import org.junit.Test;

public class PDFGeneratorFromHTMLSourceTest {


	
	@Test
	public void testGeneratePdf() throws Exception{
		PDFGenerator instance = new PDFGeneratorFromHTMLSource();
		((PDFGeneratorFromHTMLSource)instance).setFontDirectory("C:\\Windows\\Fonts");
		((PDFGeneratorFromHTMLSource)instance).setPdfFileName("C:\\sang\\Tasks\\PdfGeneration\\Test_pdf.pdf");
		List<String> cssFileNames = new ArrayList<String>(1);
		cssFileNames.add("C:\\sang\\Tasks\\PdfGeneration\\GreenGrid.css");
		((PDFGeneratorFromHTMLSource)instance).setCssFileNames(cssFileNames);
		((PDFGeneratorFromHTMLSource)instance).setPdfInputStream(new FileInputStream("C:\\sang\\Tasks\\PdfGeneration\\Data_Html.html"));
		((PDFGeneratorFromHTMLSource)instance).setImageDirectory("C:\\sang\\Tasks\\PdfGeneration\\");
		
		instance.generatePdf();
		
		File f = new File("C:\\sang\\Tasks\\PdfGeneration\\Test_pdf.pdf");
		assertTrue(f.exists());
		
		
		
		
	}

}
