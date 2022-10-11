package laba1;

import java.io.*;
import java.util.Scanner;

public class Test  
{  
    public static void main(String args[])  
    {  
    	
    	System.out.print("1. Run obfuscationr\n");  
    	System.out.print("2. Run deobfuscation\n");  
    	
    	int age = -1;
    	
    	while ((age!=1) && (age !=2))
    			{
		    		@SuppressWarnings("resource")
					Scanner scanner = new Scanner(System.in);
		        	String name = scanner.nextLine();
		        	
		        	if (isNumber(name)) 
		        		age = Integer.parseInt(name);    	
	    		}
    	
    	switch (age) {
        case  (1):
        	System.out.print("\nEnd of program");  
            break;
        case (2):
        	System.out.print("\nEnd of program");  
            break;


    	}

    
    }  
    
    
    public static boolean isNumber(String str) {
        if (str == null || str.isEmpty()) return false;
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
            	  System.out.print("Wrong character\n");  
            	  return false;
            } 
        }
        return true;
    }
} 