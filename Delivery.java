package GasBooking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Customers.GasConnection;
import GasSupplier.GasAgency;
import GasBooking.Booking;

public class Delivery {

    GasConnection cust;
    Booking booking;
    Date deliveryDate;
    String dName;
    String dPhoneNo;

    public Delivery(GasConnection cust, Booking booking, String deliveryDate, String dName , String dPhoneNo) {
        this.cust = cust;
        this.booking = booking;
        try {
            this.deliveryDate = new SimpleDateFormat("dd/MM/yyyy").parse(deliveryDate);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        this.dName = dName;
        this.dPhoneNo = dPhoneNo;
        verifyOTP();
    }

    private void verifyOTP() {
        if(booking.getStatus() == "B") {
            System.out.print("Enter OTP for Connection No. " + cust.getConnNo() + ": ");
            String OTP = new Scanner(System.in).nextLine();
            if(OTP.equals(booking.getOtp())) {
                booking.setStatus("D");
            } else {
                booking.setStatus("C");
            }
        } 
    }

    public GasConnection getCust() {
        return cust;
    }

    public void setCust(GasConnection cust) {
        this.cust = cust;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdPhoneNo() {
        return dPhoneNo;
    }

    public void setdPhoneNo(String dPhoneNo) {
        this.dPhoneNo = dPhoneNo;
    }
    

}