package GasBooking;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import Customers.GasConnection;
import GasSupplier.GasAgency;


public class Booking {
    int connNo;
    Date bookingDate;
    String otp;
    double amount;
    String status;

    public Booking(int connNo, String bookingDate, String otp, double amount, String status) {
        this.connNo = connNo;
        try {
            this.bookingDate = new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        this.otp = otp;
        this.amount = amount;
        this.status = status;
    }

    public int getConnNo() {
        return connNo;
    }

    public void setConnNo(int connNo) {
        this.connNo = connNo;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        try {
            this.bookingDate = new SimpleDateFormat("dd/MM/yyyy").parse(bookingDate);
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
