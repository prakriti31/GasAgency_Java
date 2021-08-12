package Customers;

import java.lang.*;
import java.util.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

class Customer {
    String name;
    String mobileno;
    String street;
    String area;
    String pincode;

    public Customer(String name, String mobileno, String street, String area, String pincode) {
        this.name = name;
        this.mobileno = mobileno;
        this.street = street;
        this.area = area;
        this.pincode = pincode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

}

public class GasConnection extends Customer {
    static int connNo = 1;
    int connectionNo;
    int noOfCylinders;
    Date lastBooking;

    static int seqGenerator() {
        return connNo++;
    }

    public GasConnection(String name, String mobileno, String street, String area, String pincode, int noOfCylinders, String lastBooking) {
        super(name, mobileno, street, area, pincode);
        connectionNo = seqGenerator();
        this.noOfCylinders = noOfCylinders;
        try {
            this.lastBooking = new SimpleDateFormat("dd/MM/yyyy").parse(lastBooking);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public int getConnNo() {
        return connectionNo;
    }

    public int getNoOfCylinders() {
        return noOfCylinders;
    }

    public void setNoOfCylinders(int noOfCylinders) {
        this.noOfCylinders = noOfCylinders;
    }

    public Date getLastBooking() {
        return lastBooking;
    }

    public void setLastBooking(Date lastBooking) {
        this.lastBooking = lastBooking;
    }

}