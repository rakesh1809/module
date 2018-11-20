package org.cms.hios.common.util;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cms.hios.common.report.util.FileUtil;
import org.junit.Test;



public class FileNameUtilTest {

    @Test
    public void testIdentifyDuplicateAndRenameFiles() {
	List<String> fileNames = new ArrayList<String>();
	fileNames.add("C:\\test\\test.txt");
	fileNames.add("C:\\test\\test.txt");
	fileNames.add("C:\\test\\aa.txt");
	fileNames.add("C:\\test\\aa.txt");
	fileNames.add("C:\\test\\aa.txt");
	
	List<String> updatedFileNames=FileNameUtil.identifyDuplicateAndRenameFiles(fileNames);
	Set<String> unique = new HashSet<String>();
	boolean result =unique.addAll(updatedFileNames);
	for(String n: updatedFileNames){
	    System.out.println(n);
	}
	assertTrue(result);
	System.out.println("-----------------");
	fileNames = new ArrayList<String>();
	fileNames.add("C:\\test\\test.txt");
	
	fileNames.add("C:\\test\\aa.txt");
	fileNames.add("C:\\test\\bb.txt");
	fileNames.add("C:\\test\\cc.txt");
	
	updatedFileNames=FileNameUtil.identifyDuplicateAndRenameFiles(fileNames);
	 unique = new HashSet<String>();
	result =unique.addAll(updatedFileNames);
	for(String n: updatedFileNames){
	    System.out.println(n);
	}
	assertTrue(result);
	System.out.println("------------");
	List<File> ff = new ArrayList<File>();
	ff.add(new File("C:\\test\\test.txt"));
	ff.add(new File("C:\\test\\test.txt"));
	ff.add(new File("C:\\test\\aa.txt"));
	ff.add(new File("C:\\test\\aa.txt"));
	
	List<File> updatedFiles=FileNameUtil.identifyDuplicateAndRenameFiles(ff);
	Set<File> uniqueFiles = new HashSet<File>();
	 result =uniqueFiles.addAll(updatedFiles);
	for(File n: uniqueFiles){
	    System.out.println(n.getPath());
	    
	}
	assertTrue(result);
	System.out.println("done");
	
	System.out.println("-----------------");
	Map<String, String> tt = new HashMap<String, String>();
	tt.put(String.valueOf(1),"C:\\test\\test.txt");
	
	tt.put(String.valueOf(2),"C:\\test\\aa.txt");
	tt.put(String.valueOf(3),"C:\\test\\aa.txt");
	tt.put(String.valueOf(4), "C:\\test\\aa.txt");
	tt.put(String.valueOf(5), "C:\\test\\test.txt");
	for(String n: tt.keySet()){
	    
	    System.out.println("Before key::"+n+"\t value::"+tt.get(n));
	}

	Map<String, String> yy=FileNameUtil.identifyDuplicateAndRenameFiles(tt);
	 unique = new HashSet<String>();
	
	for(String n: yy.keySet()){
	    
	    System.out.println("After key::"+n+"\t value::"+yy.get(n));
	}
	assertTrue(result);
	
    }

}

