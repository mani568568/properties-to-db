package com.loader;

import com.loader.service.DatabaseService;
import com.loader.service.FileService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyFileLoader {

    public static void main(String[] args) throws IOException {

        if(args.length!=7)
        {
            throw new RuntimeException("Invalid Arguments" +
                    "Enter the Arguments in following order" +
                    "format: java PropertyFileLoader filePath tableName keyColumnName valueColumnName jdbcURL userName password" +
                    "Ex: java PropertyFileLoader C:\\test\\simple.properties key_value key_col value_col jdbc:oracle:thin:@localhost:1521:oracle system gayi");
        }

        String filePath = args[0];
        String tableName = args[1];
        String keyColumnName = args[2];
        String valueColumnName = args[3];
        String url = args[4];
        String username = args[5];
        String password = args[6];

        DatabaseService databaseService = new DatabaseService();
        databaseService.initialize(username,password,url);
        databaseService.loadMetaData(keyColumnName,valueColumnName,tableName);
        FileService fileService = new FileService(filePath);
        HashMap<String,String> keyValues = fileService.extractKeyValues();
        for(Map.Entry<String,String> entry:keyValues.entrySet())
        {
            boolean processed = databaseService.publishToDatabase(entry.getKey(),entry.getValue());
            if(processed)
            {
                System.out.println("Successfully processed the Key :"+entry.getKey()+" with value :"+entry.getValue()+" in Database");
            }
            else
            {
                System.out.println("Not able to Process the Key :"+entry.getKey()+" with value :"+entry.getValue()+" in Database");
            }
        };
    }

}
