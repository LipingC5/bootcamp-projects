package com.techelevator.filereader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
/*
    This class should contain any and all details of access to the log file
 */
public class LogFileWriter {

    public void getLogFile(String action, double moneyDifference, double balance) {
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss a");
        Date date = new Date();

        try {
            File file = new File("Log.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(formatter.format(date) + "  " + action + "  $" + moneyDifference + "  $" + balance + "\n");
            bufferedWriter.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
