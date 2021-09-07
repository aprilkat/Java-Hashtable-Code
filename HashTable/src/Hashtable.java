/* 
*   Author: April Bollinger
*   Program: Phone Book 
*   Purpose: to implement file reading and hash tables
*   Date: 04/19/2021
*   Resources:
*   https://www.w3schools.com/java/java_files_read.asp
*   https://java2blog.com/fix-cannot-make-static-reference-to-non-static-method/
*   https://www.geeksforgeeks.org/implementing-our-own-hash-table-with-separate-chaining-in-java/
*   https://www.javatpoint.com/java-string-to-char
*   https://www.javatpoint.com/java-object-to-string
*
*/

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;



class Hashtable<K, PN> {

    // Node key;
    // Named the value the wrong thing, I only noticed after I wrote the program.
    // Node Phone;
    //Node next;


    // A lot of trouble with converting Objects to other types and back.
    // I am getting an error for input string "Gates", so i must have something going in the wrong place.

    private ArrayList<Node<K, PN>> arrayBucket;
    private int NumofBuckets;
    private int size;

    // Sets up the original settings of the hashtable and creates empty chains.
    public Hashtable(){
        arrayBucket = new ArrayList<>();
        NumofBuckets = 500;
        size = 0;

        //Creating empty chains
        // for Collsion.

        for (int i = 0; i < NumofBuckets; i++){
            arrayBucket.add(null);
        }
    }
    // Initializing the size of the hash table.
    public int size(){
        return size;
    }


    // Method to return the HashTable to it's original state. Not really needed for this project.
    public boolean isEmpty(){
        return size() == 0;
    }

    // Trying to convert the key to an ascii value.
    private K getBucketKey(K key){
        // Unknown error occuring here.
        String temp = key.toString();
        int sum = 0;
        int b = 0;
        for(int i = 0; i < temp.length(); i++){
            char c = temp.charAt(i);
            int ascii = c;
            sum = ascii + b;
            // soon to be previous value.
            b = ascii;
            
        }
        // Returning the sum of all of the ascii characters as the key.
        String x = "" + sum;
        x = String.valueOf(key);
        return key;
    }


    // Getting the value which is the phone number.

    public PN getValue(K key){
        String temp = key.toString();
        int x = Integer.parseInt(temp);
        int indexOfBucket = getBucketKey(x);
        Node<K, PN> top = arrayBucket.get(indexOfBucket);

        // Searching for the key in the chain
        while(top != null){
            if (top.key.equals(key)){
                return top.Phone
                ;
            }
            top = top.next;
        }

        // If it can not find the key return null.
        return null;
    }


    private int getBucketKey(int x) {
        return 0;
    }

    // Method that adds a key/Phone number pair to the HashTable.
    public void add(K key, PN Phone){
        // Find the head of the chain.
        String temp = key.toString();
        int x = Integer.parseInt(temp);
        int indexOfBucket = getBucketKey(x);
        Node<K, PN> top = arrayBucket.get(indexOfBucket);

        // Check to see if the ley you want to add is already present.
        while( top != null){
            if(top.key.equals(key)){
                top.Phone = Phone
                ;
                return;
            }
            
            top = top.next;
        }
        // Adding on to the size.
        size++;
        top = arrayBucket.get(indexOfBucket);
        Node<K, PN> newNode = new Node<K, PN>(key, Phone);
        newNode.next = top;
        arrayBucket.set(indexOfBucket, newNode);

        // If there are too many items in the hashtable, double the size.
        if(1.0 * size / NumofBuckets >= 0.7){
            ArrayList<Node<K, PN> > temp1 = arrayBucket;
            arrayBucket = new ArrayList<>();
            NumofBuckets = 2 * NumofBuckets;
            size = 0;
            for(int i = 0; i < NumofBuckets; i++){
                arrayBucket.add(null);

            }
            for(Node<K, PN> topNode : temp1){
                while(topNode != null);
                add(topNode.key, topNode.Phone);
                topNode = topNode.next;


            }

        }
    }

    public void convertAndSearch(K key){
        // Had to crfeate an object so that I could call my Non-Static methods.
        Hashtable HT = new Hashtable();

        // Conversions
        String temp = key.toString();
        int x = Integer.parseInt(temp);
        int indexOfBucket = getBucketKey(x); 
        
        
        Node<K, PN> top = arrayBucket.get(indexOfBucket);

        System.out.println("Please enter the last name of the person you are trying to contact. ");
        Scanner scan = new Scanner(System.in);
        String answer = scan.nextLine();
        int hashCode = Integer.parseInt(answer);
        int userhash = hashCode % NumofBuckets;
        // since the hashcode could be negative we must multiply it by negative
        // one if it is.

        // A kind of search function.
        while (top != null) {
            if (top.key.equals(userhash)) {
                System.out.println("We found it!");
                System.out.println(top.Phone);
            }

            top = top.next;
        }

    }
    



    // Reading the file and adding to the hashtable.
    public static void fileRead(Object Person){{
            try {
                Hashtable HT = new Hashtable();
                File file = new File("src/PhoneBook.txt");
                Scanner scanfile = new Scanner(file);
                while(scanfile.hasNextLine()){
                    Person = scanfile.nextLine();

                    // Converting between Strings and Objects.
                    String text = Person.toString();
                    String [] hold = text.split(",");
                    String keys = hold[0];
                    Object tempval = String.valueOf(keys);
                    String y = hold[1];
                    Person = String.valueOf(y);
                   
                    HT.add(tempval, Person);
                   
                }
            // Try/Catch statement is very useful when reading from files.
            } catch (FileNotFoundException e) {
                System.out.println("The File was not Found!");
                e.printStackTrace();
            }

        
    }
                       
    }

    // Main method.
       public static void main(String[] args){
           // Another Object so that I could call a method.
           Hashtable HT = new Hashtable();

            Object Person = new Object();
            Hashtable<Object, Object> hash = new Hashtable();
            fileRead(Person);
            // Object filler for the parameters to match.
            Object obj = new Object();
            HT.convertAndSearch(obj);
            
       }
}


