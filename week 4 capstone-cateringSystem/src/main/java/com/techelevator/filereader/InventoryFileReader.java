package com.techelevator.filereader;

import com.techelevator.items.CateringItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    This class should contain any and all details of access to the inventory file
 */
public class InventoryFileReader {

    public List<CateringItem> read() throws FileNotFoundException {

        List<CateringItem> cateringItem = new ArrayList<CateringItem>();
        String fileName = "cateringsystem.csv";
        File file = new File(fileName);

        try (Scanner fileScanner = new Scanner(file)) {

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();
                String[] parts = line.split("\\|");
                CateringItem itemLoadedFromLineOfFile = new CateringItem();
                if(parts[0].equals("B")){
                    itemLoadedFromLineOfFile.setProductType("Beverages");
                }else if (parts[0].equals("A")){
                    itemLoadedFromLineOfFile.setProductType("Appetizers");
                }else if (parts[0].equals("E")){
                    itemLoadedFromLineOfFile.setProductType("Entrees");
                }else if (parts[0].equals("D")){
                    itemLoadedFromLineOfFile.setProductType("Desserts");
                }
                itemLoadedFromLineOfFile.setProductCode(parts[1]);
                itemLoadedFromLineOfFile.setProductName(parts[2]);
                itemLoadedFromLineOfFile.setProductPrice(Double.parseDouble(parts[3]));
                cateringItem.add(itemLoadedFromLineOfFile);
            }
        }
        return cateringItem;
    }
}
