package com.ubicompsystem.experiments.java.zip;

import java.io.*;
import java.util.zip.*;

public class ZipUtil{
    
    public static boolean unzip( String destPath, String zipFilePath ) throws IOException {
        return unzip( new File( destPath ), new File( zipFilePath ) );
    }

    public static boolean unzip( final File destDirectory, final File zipFile ) throws IOException {

        if( destDirectory == null ) throw new IllegalArgumentException( "Destination Directory is null" );
        if( !destDirectory.exists() && !destDirectory.mkdirs() ) throw new IllegalArgumentException( "Destination Directory cannot be created " );
        if( destDirectory.exists() && !destDirectory.isDirectory() ) throw new IllegalArgumentException( "Destination is not a directory" );

        ZipFile zip = new ZipFile( zipFile );
        System.out.println( "Entries... " + zip.size() );
        if( zip.size() == 0 ) return false;

        zip.stream().forEach( entry->{
            try{
                processEntry( destDirectory, zip, entry );
            } catch( IOException e ){
                System.out.println( "Error processing entry " + entry.getName() );
            }
        });
        return true;
    }

    public static void writeToFile( File targetFile, InputStream isr ) throws IOException {
        try( FileOutputStream fos = new FileOutputStream(targetFile) ){
            byte[] buffer = new byte[1024];
            int len = 0;
            do {
                len = isr.read(buffer);
                if( len > 0 ){
                    fos.write(buffer, 0, len );
                }
            } while( len > 0 );
        }
    }

    public static File createEntryFile( File destination, ZipEntry entry ) throws IOException {
        if( entry == null ) return null;

        File entryFile = new File( destination, entry.getName() );

        String destDirectoryPath = destination.getCanonicalPath();
        String entryFilePath = entryFile.getCanonicalPath();

        if( !entryFilePath.startsWith( destDirectoryPath + File.separator ) ) throw new IOException( "Entry is outside of the target directory" );
        return entryFile;
    }

    public static void processEntry( File destination, ZipFile zip, ZipEntry entry ) throws IOException {
        if( entry == null || destination == null ) return;

        File entryFile = createEntryFile( destination, entry );
        writeToFile( entryFile, zip.getInputStream(entry) );
    }
}
