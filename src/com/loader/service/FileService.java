package com.loader.service;

import com.loader.InValidFileException;

import java.io.*;
import java.util.HashMap;

public class FileService {

    String filePath  = "";

    public FileService(String filePath) throws FileNotFoundException, InValidFileException
    {
        if(filePath!=null && !filePath.endsWith(".properties"))
        {
            throw new InValidFileException("Please enter a valid .properties file");
        }

        if(filePath==null || (filePath!=null && ! (new File(filePath).exists())))
        {
            throw new FileNotFoundException("Enter a File Path which ends with .properties");
        }

        this.filePath = filePath;
    }

    public HashMap<String,String> extractKeyValues() throws IOException {
        HashMap<String,String> returnMap = new HashMap<>();
        File file = new File(filePath);
        if(file!=null && file.exists())
        {
            FileReader fr=new FileReader(file);   //reads the file
            BufferedReader br=new BufferedReader(fr);  //creates a buffering character input stream
            String line;
            while((line=br.readLine())!=null)
            {
                String cleanLine = line.trim();
                if(cleanLine!=null)
                {
                    String[] keyValues = cleanLine.split("=");
                    if(keyValues!=null && keyValues.length>=2)
                    {
                        returnMap.put(keyValues[0].trim(),keyValues[1].trim());
                    }
                }
            }
        }
        return returnMap;
    }
}
