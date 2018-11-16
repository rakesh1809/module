package org.cms.hios.common.email.util;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.cms.hios.common.util.CommonUtilFunctions;

public class CommonUtils {
	private static final Logger logger = Logger.getLogger(CommonUtils.class);
	private static final String MM_DD_YY_H_MM_SS_A_FORMAT = "MM/dd/yyyy h:mm:ss a";
	
	/* ----------------------------------------------------------------- File Related Utilities ----------------------------------------------------------------- */
	 
	 /**
	 * Get all CSV, PDF and DOC extension files from folder
	 * 
	 * @param directoryName
	 * @return
	 */
	public static List<File> getAllFiles(String directoryName){
	
		File dir = new File(directoryName);

		List<File> list = Arrays.asList(dir.listFiles(new FilenameFilter(){
	        @Override
	        public boolean accept(File dir, String name) {
	            return (name.endsWith(CommonConstants.CSV_EXT) 
	            		|| name.endsWith(CommonConstants.DOC_EXT) 
	            		|| name.endsWith(CommonConstants.PDF_EXT)); 
	        }}));
		return list;
	}
	
	/**
	 * Utility to write content into file
	 * 
	 * @param in
	 * @param out
	 * @throws Exception
	 */
	public static void writeToFile(InputStream in, OutputStream out) throws Exception{
		byte[] buffer = new byte[1024];
		int length;
		
		while((length = in.read(buffer)) > 0){
		       out.write(buffer, 0, length);
		}
		
	}
	
	/**
	 * Static utility to create a zip file
	 * 
	 * @param fileList
	 * @param zout
	 * @throws Exception
	 */
	public static void createZipFile(String dumpDir, String zipFileName) throws Exception{
		FileOutputStream fout = null;
	    ZipOutputStream zout  = null;
		try {
			//delete all zip files first
			deleteFiles(dumpDir, CommonConstants.ZIP_EXT);
    		
			//create file output stream	
    		fout = new FileOutputStream(zipFileName);

			//create object of ZipOutputStream from FileOutputStream
			zout = new ZipOutputStream(fout);
			zout.setComment(new Date().toString());
    		
			List<File> fileList = CommonUtils.getAllFiles(dumpDir);
			
			for (Iterator<File> iterator = fileList.iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();

				//create object of FileInputStream for source file
			    FileInputStream fin = new FileInputStream(file);
			    zout.putNextEntry(new ZipEntry(file.getName()));
			    writeToFile(fin, zout);
			    
			    //close each zip entry before creating new one
			    zout.closeEntry();
			}
			zout.close();
		} catch (Exception e) {
			logger.error("Error:" + e);
			throw e;
		}finally{
			try {
				if(zout!=null){
					zout.close();
				}
				if(fout!=null){
					fout.close();
				}
			} catch (Exception e) {
				logger.error("Error while closing I/O resources " + e);
				throw e;
			}
		}
	}
	
	/**
	 * Generates a text file
	 * 
	 * @param records
	 * @param rootFolder
	 * @return
	 * @throws IOException
	 */
	public static String createTxtFile(List<String> records, String rootFolder) throws IOException{
		
		StringBuilder sb = new StringBuilder().append(CommonConstants.FEIN_FILE_NAME).append(getDateInMMddyyyyFormat()).append(CommonConstants.TXT_EXT);
		
		File file = new File(rootFolder+sb.toString());
		//new line character
		String newLineChar = System.getProperty("line.separator");
		StringBuilder recordSb = new StringBuilder();
		
		for (int i = 0; i< records.size(); i++ ) {
			recordSb.append(records.get(i)).append(newLineChar);
		}
		
		FileUtils.writeStringToFile(file, recordSb.toString());
		return sb.toString();
	}
	
	/**
	 * Deletes all files for the given file type and directory
	 * 
	 * @param files
	 */
	public static void deleteFiles(String dir, final String ext){
		File[] files = listFileWithExtesions(dir, ext);
		//delete files one-by-one
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}
	
	/**
	 * Deletes a file
	 * 
	 * @param rootDir
	 * @param genFileName
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String rootDir, String genFileName) throws Exception{
		File file = new File(new StringBuilder().append(rootDir).append(genFileName).toString());
		if(file.delete())
			return true;
		else
			return false;
	}
	
	/**
	 * Removes the file extension to retrieve the name of the file
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getFileName(String fileName) {
		String[] outFileName = fileName.split("\\.");
		return outFileName[0];
	}
	
	/**
	 * Retrieve all extensions of files within given directory
	 * 
	 * @param dir
	 * @param ext
	 * @return
	 */
	public static File[] listFileWithExtesions(String dir, final String ext){
		File fileDir = new File(dir);
		File[] files = fileDir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().endsWith(ext);
		    }
		});
		
		return files;
	}
	
	/**
	 * This method retrieves latest file name based on last modified date and time.
	 *  
	 * @param dir directory to be processed
	 * @param fileName filename to be matched
	 * @return list of files. 
	 */
	public static String getLastModifiedFile(String dir, final String fileName) throws Exception{
		String result = "";	
		File fileDir = new File(dir);
		
		File[] files = fileDir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		    	return name.startsWith(fileName);
		    }
		});
		if(files!=null && files.length > 0){
			Arrays.sort(files, new Comparator<File>() {

				@Override
				public int compare(File f1, File f2) {
					 	long l1 = f1.lastModified();
			            long l2 = f2.lastModified();
			            return l1 > l2 ? -1 : l1 < l2 ? 1 : 0;
				}
			});
			result = files[0].getName();
		}
		return result;
	}
	
	/* ----------------------------------------------------------------- Date Related Utilities ----------------------------------------------------------------- */
	
	/**
	 * Retrieves current date as String
	 * 
	 * @param format
	 * @return
	 */
	public static String getCurrentDate(String format){
		SimpleDateFormat simpleDtFmt = new SimpleDateFormat(format);
		String date = simpleDtFmt.format(System.currentTimeMillis());
		return date;
	}
	
	/**
	 * Get Validation Expiration date time.
	 * Current grace period is 5 days - make it configurable
	 * 
	 * @param format
	 * @return
	 */
	public static String getVldtnExprDt(String format){
		SimpleDateFormat simpleDtFmt = new SimpleDateFormat(format);
		
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		cal.add(Calendar.DATE, 5);
		
		String date = simpleDtFmt.format(cal.getTimeInMillis());
		return date;
	}
	
	/**
	 * Retrieves date in yyyyMMddHHmmssSSS format
	 * 
	 * @return
	 */
	public static String getDateTimeUniqueID(){
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		Date now = new Date();
	    String strDate = sdfDate.format(now);
	    return strDate;
	}
	
	/**
	 *  Retrieves date in MMddyyyy format
	 *  
	 * @return
	 */
	public static String getDateInMMddyyyyFormat(){
        //used for the daily zip file name
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy");
        
        return sdf.format(new Date());
    }
	
	/**
	 * Add timestamp in the format of yyyyMMdd'_'HHmmss
	 *  
	 * @return
	 */
	public static String appendDateToFileName(String formatting){
        //used for the daily zip file name
		/* Types of formats: 
		 * "yyyyMMdd'_'HHmmss"
		 * "MMddyyyy"
		 * "yyyyMMddHHmmssSSS"
		 * */
		
        SimpleDateFormat sdf = new SimpleDateFormat(formatting);
        return "_" + sdf.format(new Date());
    }
	
	public static String convertDateToMMddyyWithAMPMMarker(Date date) {
		SimpleDateFormat format = new SimpleDateFormat(MM_DD_YY_H_MM_SS_A_FORMAT);
		return (format.format(date));
	}
	
	/* ----------------------------------------------------------------- String Parsing Related Utilities ----------------------------------------------------------------- */
	
	/**
	 * Convert list into comma delimited string
	 * 
	 * @param list
	 * @return
	 */
	 public static String convertToCommaDelimited(String[] list) {
	        StringBuffer ret = new StringBuffer("");
	        for (int i = 0; list != null && i < list.length; i++) {
	            ret.append(list[i]);
	            if (i < list.length - 1) {
	                ret.append(',');
	            }
	        }
	        return ret.toString();
	 }
	 
	 /**
	  * Calculates the length of the passed in string
	  * 
	 * @param strCommaSep
	 * @return
	 */
	public static int calculateLength(String strCommaSep){
		 List<String> strList = Arrays.asList(strCommaSep.split(","));
		 
		 return strList.size();
	 }
	
	/**
	 * Valides incoming string against regex val
	 * 
	 * @param input
	 * @return
	 */
	public static String validateText(String input){
		String regEx = "[^\\w \\s- &]";
		
		if(CommonUtilFunctions.isNotNullAndNotEmpty(input)){
			String result = input.replaceAll(regEx, "");				
			return result.substring(0, Math.min(result.length(), 40));
		}
		else
			return input;
	}
	
	/**
	 * Validates if incoming date string is expired (against curr date)
	 * 
	 * @param vldtnExpTs
	 * @return
	 * @throws Exception
	 */
	public static boolean isFeinAlreadyExpired(String vldtnExpTs) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat(CommonConstants.DATE_TIME_MILI_SEC_FORMAT);
		Date vldtnExpDt = dateFormat.parse(vldtnExpTs);			
		
		if(vldtnExpDt.getTime() <= new Date().getTime())
			return true;
		else
			return false;
	}
	
	/**
	 * @param trueOrFalse
	 * @return
	 */
	public static String booleanToString(boolean trueOrFalse) {
	    return trueOrFalse ? "Yes" : "No";
	}
	
	/**
	 * Checks a String for null or empty 
	 * @param String - str
	 * @return boolean - true if the given string is not null and not empty
	 */
	public static boolean isNotNullAndNotEmpty(String str) {
		if(str !=  null && str.trim().length() != 0) {
			return true;	
		}
		return false;
	}
	
	/**
	 * Checks if the List is null or empty
	 * 
	 * @param lst
	 * @return boolean - true if the given list is not null and not empty
	 */
	public static boolean isListNotNullAndNotEmpty(List lst) {
		if(lst !=  null && lst.size() != 0) {
			return true;	
		}
		return false;
	}
	
	/**
	 * 
	 * @param str
	 * @return boolean - true if the given string is  null or empty
	 */
	public static boolean isNullOrEmpty(String str){
		return !isNotNullAndNotEmpty(str);
	}

	/**
	 * Returns either the string value or N/A
	 *
	 * @param str
	 * @return str
	 */
	public static String rtrnNAifEmpty(String value){
		return isNotNullAndNotEmpty(value) ? value : "N/A";
	}
	
	/**
	 * returns number week days from today to give number of days 
	 * @param intWarningDays
	 * @return
	 */
	public static int getBusinessdaysCount(int intWarningDays){
		Date date1=new Date();
	    Calendar calendar = Calendar.getInstance();
	    date1=calendar.getTime(); 
	    SimpleDateFormat s;
	    s=new SimpleDateFormat("MM/dd/yy");

	    for(int i=0;i<intWarningDays;)
	    {
	        calendar.add(Calendar.DAY_OF_MONTH, 1);
	        //here even sat and sun are added
	        //but at the end it goes to the correct week day.
	        //because i is only increased if it is week day
	        if(calendar.get(Calendar.DAY_OF_WEEK)<=5)
	        {
	            i++;
	        }

	    }
	    Date date2=new Date();
	    date2=calendar.getTime(); 
	    s=new SimpleDateFormat("MMM dd, yyyy");
	    int numOfdays = daysBetween(date1, date2);
		return numOfdays;
		
	}
	
	/**
	 * @param d1
	 * @param d2
	 * @return number days between dates
	 */
	public static int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}
