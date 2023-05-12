package com.ubicompsystem.experiments.java.zip;

import org.junit.Test;

import java.io.*;
import java.net.*;

public class ZipUtilTest{

    @Test
    public void unzipZipFileTest() throws Exception {
        System.out.println( "===== START Test Unzip Zip File =====" );
        URL fileURL = getClass().getClassLoader().getResource("test-root.zip");
        File zipFile = new File(fileURL.toURI());

        File targetDir = zipFile.getParentFile().getParentFile();

        System.out.println( zipFile.getCanonicalPath() );
        System.out.println( "Target Folder : " + targetDir.getCanonicalPath() );
        String zipOutput = targetDir.getCanonicalPath() + File.separator + "zipOutput";
        File zipOutputDir = new File( zipOutput );
        if( !zipOutputDir.exists() && !zipOutputDir.mkdirs() ) throw new IOException( "Unable to create directory " + zipOutputDir );

        runTest( zipOutputDir, zipFile );
        System.out.println( "===== END Test Unzip Zip File =====\n" );
    }

    @Test
    public void unzipRarFileTest() throws Exception {
        System.out.println( "===== START Test Unzip Rar File =====" );
        URL fileURL = getClass().getClassLoader().getResource("test.rar");
        File zipFile = new File(fileURL.toURI());

        File targetDir = zipFile.getParentFile().getParentFile();

        System.out.println( zipFile.getCanonicalPath() );
        System.out.println( "Target Folder : " + targetDir.getCanonicalPath() );

        String rarOutput = targetDir.getCanonicalPath() + File.separator + "rarOutput";
        File rarOutputDir = new File( rarOutput );
        if( !rarOutputDir.exists() && !rarOutputDir.mkdirs() ) throw new IOException( "Unable to create directory " + rarOutputDir );
        runTest( rarOutputDir, zipFile );
        System.out.println( "===== END Test Unzip Rar File =====\n" );
    }

    protected void runTest( File outputDir, File zipFile ) throws Exception {
        try{
            ZipUtil.unzip( outputDir, zipFile );
        } catch( Exception ex ){
            System.err.println( "Unable to unzip file " + zipFile.getPath() + " : " + ex.getMessage() );
            throw ex;
        }
    }

}
