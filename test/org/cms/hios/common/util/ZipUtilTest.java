package org.cms.hios.common.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.cms.hios.common.report.util.ZipUtil;
import org.cms.hios.common.transfer.ZipFileContentTO;
import org.junit.Test;

public class ZipUtilTest {

    @Test
    public void testUnZipFile() throws Exception {
	byte[] zipFileContent = getFileContent();
	List<ZipFileContentTO> files =  ZipUtil.unzip(zipFileContent);
	int i=1;
	for(ZipFileContentTO file: files){
	    assertTrue(file.getFileContent().length>0);
	    //save the file...
	    byte[] fileCnt = file.getFileContent();
	    String fileName = file.getFileName();
	    
	    
	    Path path = Paths.get("c://test//"+fileName);
	    Files.write(path, fileCnt);
	    
	   
	}

    }

    private byte[] getFileContent() throws Exception {
	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	File f = new File(
		"C://Workspaces//LatestTPFWorkspace//HIOS-COMMON//test-resources//MLR_RC_Templates.zip");
	InputStream is = new FileInputStream(f);
	byte[] fileContent = new byte[(int) f.length()];
	if (is != null) {
	    fileContent = new byte[is.available()];
	    is.read(fileContent);
	    is.close();
	}
	return fileContent;
    }
}
