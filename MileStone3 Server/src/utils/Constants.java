package utils;

import java.util.Scanner;

public interface Constants {
    int minQuantity = 40;
    String date = "February 3, 2019";

    static int generateFiveDigit(){
         return (int)Math.round(Math.random() * 89999) + 10000;
    }

}
