import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import Customers.GasConnection;
import GasSupplier.GasAgency;

import GasBooking.Delivery;
import GasBooking.Booking;

public class DA1 implements GasAgency {

    public void display() {
        System.out.println("Agency Name      : " + agencyname);
        System.out.println("Agency Code      : " + agencycode);
        System.out.println("Agency Phone No. : " + phoneno);
    }

    public long validateDate(String startDate, String endDate) {
        Date firstDate = null, secondDate = null;
        try {
            firstDate = new SimpleDateFormat("dd/MM/yyyy").parse(startDate);
            secondDate = new SimpleDateFormat("dd/MM/yyyy").parse(endDate);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

        return diff;

    }

    public double validateAmount(String bookingDate, String deliveryDate) {
        if (validateDate(bookingDate, deliveryDate) <= 7) {
            return 825.0;
        }
        return (825.0 - (825.0 * 0.05));
    }

    public String validateCylinder(int cylinder, String lastBooking, String currBooking) {
        if (cylinder == 1 && validateDate(lastBooking, currBooking) < 30) {
            return "C";
        } else if (cylinder == 1 && validateDate(lastBooking, currBooking) >= 30) {
            return "B";
        } else if (cylinder == 2 && validateDate(lastBooking, currBooking) < 50) {
            return "C";
        } else if (cylinder == 2 && validateDate(lastBooking, currBooking) >= 50) {
            return "B";
        } else {
            return "Error! Invalid Booking and Last Booking Dates";
        }
    }

    public static void main(String[] args) {

        DA1 da = new DA1();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String[] lastBooking = new String[] { "03/04/2021", "01/07/2021", "31/12/2020", "12/02/2021", "12/08/2020" };
        String[] currBooking = new String[] { "10/04/2021", "30/08/2021", "02/01/2021", "02/03/2021", "02/01/2021" };
        String[] deliveryDate = new String[] { "18/04/2021", "02/09/2021", "09/01/2021", "11/03/2021", "10/01/2021" };

        // Declaring GasConnection
        GasConnection[] gc = new GasConnection[5];
        gc[0] = new GasConnection("ABC", "9800987675", "Fourth Avenue", "Mumbai-Agra NH", "422009", 2, lastBooking[0]);
        gc[1] = new GasConnection("DEF", "9759943075", "Fifth Avenue", "Nashik", "423009", 1, lastBooking[1]);
        gc[2] = new GasConnection("GHI", "7892021133", "Sixth Avenue", "Nashik", "423009", 1, lastBooking[2]);
        gc[3] = new GasConnection("JKL", "9800321000", "Fourth Avenue", "Mumbai-Agra NH", "422009", 2, lastBooking[3]);
        gc[4] = new GasConnection("MNO", "9887231123", "Fifth Avenue", "Agra NH", "422349", 1, lastBooking[4]);

        // Declaring Booking
        Booking[] bk = new Booking[5];
        bk[0] = new Booking(gc[0].getConnNo(), currBooking[0], "123456", 0.0, "");
        bk[1] = new Booking(gc[1].getConnNo(), currBooking[1], "099876", 0.0, "");
        bk[2] = new Booking(gc[2].getConnNo(), currBooking[2], "134519", 0.0, "");
        bk[3] = new Booking(gc[3].getConnNo(), currBooking[3], "028384", 0.0, "");
        bk[4] = new Booking(gc[4].getConnNo(), currBooking[4], "002233", 0.0, "");

        // Verifing No of Cylinders
        for (int i = 0; i < 5; i++) {
            bk[i].setStatus(da.validateCylinder(gc[i].getNoOfCylinders(), sdf.format(gc[i].getLastBooking()),
                    sdf.format(bk[i].getBookingDate())));
        }

        // Declaring Delivery
        Delivery[] del = new Delivery[5];
        del[0] = new Delivery(gc[0], bk[0], deliveryDate[0], "Ramesh", "9930137507");
        del[1] = new Delivery(gc[1], bk[1], deliveryDate[1], "Akshara", "8080927343");
        del[2] = new Delivery(gc[2], bk[2], deliveryDate[2], "Aviral", "9955551299");
        del[3] = new Delivery(gc[3], bk[3], deliveryDate[3], "Ramesh", "9762226171");
        del[4] = new Delivery(gc[4], bk[4], deliveryDate[4], "Ramesh", "9587656700");

        for (int i = 0; i < 5; i++) {
            bk[i].setAmount(
                    da.validateAmount(sdf.format(bk[i].getBookingDate()), sdf.format(del[i].getDeliveryDate())));
        }
        System.out.println("\n===================================QUERY1===================================\n");
        da.query1(del);
        System.out.println("\n===================================QUERY2===================================\n");
        da.query2(del);
        System.out.println("\n===================================QUERY3===================================\n");
        da.query3(gc, 1);
        System.out.println("\n===================================QUERY4===================================\n");
        System.out.print("Enter Delivery Person Name: ");
        da.query4(del, new Scanner(System.in).nextLine(), gc);
        System.out.println("\n===================================QUERY5===================================\n");
        System.out.print("Printing the report : ");
        da.report(bk);
        System.out.println("\n=================================QUERY5[A]=================================\n");
        da.query5a(bk, del, gc);
        System.out.println("\n=================================QUERY5[B]=================================\n");
        da.query5b(bk, del, gc);
        System.out.println("\n=================================QUERY5[C]=================================\n");
        da.query5c(bk, del, gc);
        System.out.println("\n=================================QUERY5[D]=================================\n");
        da.query5d(bk, del, gc);
        System.out.println("\n====================================QUERY6==================================\n");
        da.query6(bk, gc, del);
    }

    private void query1(Delivery[] del) {
        int[] month = new int[12];
        String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };
        ArrayList<String> area = new ArrayList<String>();
        for (int i = 0; i < del.length; i++) {
            if (!area.contains(del[i].getCust().getArea())) {
                area.add(del[i].getCust().getArea());
            }
        }

        for (String a : area) {
            for (int i = 0; i < del.length; i++) {
                if (del[i].getBooking().getStatus().equals("D") && del[i].getCust().getArea().equals(a)) {
                    int cyl = del[i].getCust().getNoOfCylinders();
                    month[del[i].getDeliveryDate().getMonth()] += 1;
                }
            }
            boolean headingPrinted = false;
            for (int i = 0; i < 12; i++) {
                if (month[i] != 0 && !headingPrinted) {
                    System.out.println(a + " : \n");
                    System.out.printf("%-9s : %d\n", months[i], month[i]);
                    headingPrinted = true;
                } else if (month[i] != 0) {
                    System.out.printf("%-9s : %d\n", months[i], month[i]);
                }
            }
        }
    }

    private void query2(Delivery[] del) {
        int[] month = new int[12];
        String[] months = new String[] { "January", "February", "March", "April", "May", "June", "July", "August",
                "September", "October", "November", "December" };

        for (int i = 0; i < del.length; i++) {
            if (del[i].getBooking().getStatus().equals("D") && del[i].getBooking().getAmount() == (783.75)) {
                month[del[i].getDeliveryDate().getMonth()] += 1;
                int cyl = del[i].getCust().getNoOfCylinders();
            }
        }

        for (int i = 0; i < 12; i++) {
            if (month[i] != 0) {
                System.out.printf("%-9s : %d\n", months[i], month[i]);
            }
        }
    }

    private void query3(GasConnection[] gc, int cyl) {
        System.out.printf("%s\t%-10s\t %-11s\n", "Conn No", "Name", "Mobile No");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < gc.length; i++) {
            if (gc[i].getNoOfCylinders() == cyl) {
                System.out.printf("%2d\t%-10s\t%11s\n", gc[i].getConnNo(), gc[i].getName(), gc[i].getMobileno());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private void query4(Delivery[] del, String name, GasConnection[] gc) {
        System.out.println("\nDetails are as follows : \n");
        System.out.printf("%s\t%-10s\t %-11s\n", "CustName", "Street", "Area");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < del.length; i++) {
            if (name.equals(del[i].getdName())) {
                System.out.printf("%2s\t%-10s\t%11s\n", gc[i].getName(), gc[i].getStreet(), gc[i].getArea(),
                        gc[i].getPincode());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private void query5a(Booking[] bk, Delivery[] del, GasConnection[] gc) {
        System.out.printf("%s\t%-10s\t %-11s \t %-10s\n", "ConnNo", "Name", "Mobile No", "BookingDate");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < del.length; i++) {
            if (del[i].getBooking().getStatus().equals("D")) {
                System.out.printf("%2d\t%-10s\t%11s\t%tB %<te, %<tY\n", gc[i].getConnNo(), gc[i].getName(),
                        gc[i].getMobileno(), bk[i].getBookingDate());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private void query5b(Booking[] bk, Delivery[] del, GasConnection[] gc) {
        System.out.printf("%s\t%-10s\t %-11s \t %-10s\n", "ConnNo", "Name", "Mobile No", "BookingDate");
        System.out.println("--------------------------------------------------------");
        boolean noPendingPrinted = false;
        for (int i = 0; i < del.length; i++) {
            if (del[i].getBooking().getStatus().equals("P")) {
                System.out.printf("%2d\t%-10s\t%11s\t%tB %<te, %<tY\n", gc[i].getConnNo(), gc[i].getName(),
                        gc[i].getMobileno(), bk[i].getBookingDate());
                noPendingPrinted = false;
            } else {
                noPendingPrinted = true;
            }
        }
        if (noPendingPrinted)
            System.out.println("No pending bookings!");
        System.out.println("--------------------------------------------------------");
    }

    private void query5c(Booking[] bk, Delivery[] del, GasConnection[] gc) {
        System.out.printf("%s\t%-10s\t %-11s \t %-10s\n", "ConnNo", "Name", "Mobile No", "BookingDate");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < del.length; i++) {
            if (del[i].getBooking().getStatus().equals("C")) {
                System.out.printf("%2d\t%-10s\t%11s\t%tB %<te, %<tY\n", gc[i].getConnNo(), gc[i].getName(),
                        gc[i].getMobileno(), bk[i].getBookingDate());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private void query5d(Booking[] bk, Delivery[] del, GasConnection[] gc) {
        System.out.printf("%s\t%-10s\t %-11s \t %-10s\n", "ConnNo", "DeliveryPerson", "Mobile No", "DeliveryDate");
        System.out.println("--------------------------------------------------------");
        ArrayList<String> dNames = new ArrayList<String>();
        for (int i = 0; i < del.length; i++) {
            if (!dNames.contains(del[i].getdName())) {
                dNames.add(del[i].getdName());
                System.out.printf("%2d\t%-10s\t%-11s\t%tB %<te, %<tY\n", gc[i].getConnNo(), del[i].getdName(),
                        del[i].getdPhoneNo(), del[i].getDeliveryDate());
            }
        }
        System.out.println("--------------------------------------------------------");
    }

    private void report(Booking[] bk) {
        int p, d, c, b;
        p = d = c = b = 0;
        for (int i = 0; i < bk.length; i++) {
            if (bk[i].getStatus().equals("P")) {
                p++;
            } else if (bk[i].getStatus().equals("D")) {
                d++;
            } else if (bk[i].getStatus().equals("C")) {
                c++;
            } else if (bk[i].getStatus().equals("B")) {
                b++;
            } else {
                System.out.println("Invalid status was updated. Check again.");
            }
        }
        System.out.println("\n-------------------");
        System.out.println("Delivered     : " + d);
        System.out.println("-------------------");
        System.out.println("Booked        : " + b);
        System.out.println("-------------------");
        System.out.println("Pending       : " + p);
        System.out.println("-------------------");
        System.out.println("Cancelled     : " + c);
        System.out.println("-------------------");
    }

    private void query6(Booking[] bk, GasConnection[] gc, Delivery[] del) {
        int zero = 0;
        DateTimeFormatter sdf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDateTime now = LocalDateTime.now();
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        System.out.println("----------------------------------------------------------------------------");
        for (int i = 0; i < del.length; i++) {
            if (bk[i].getStatus().equals("D")) {
                Date bookdate = bk[i].getBookingDate();
                Date deldate = del[i].getDeliveryDate();
                System.out.println("----------------------------------------------------------------------------");
                System.out.println(
                        "                                 " + "Receipt No" + i + "                            ");
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("Gas Agency Code   : " + agencycode + "                 " + "Date Of Invoice      : "
                        + sdf.format(now));
                // System.out.print("\n");
                System.out.println(
                        "Gas Agency Name   : " + agencyname + "         " + "Agency Phone Number  : " + phoneno + "|");
                System.out.println("Gas Conn Num      : " + gc[i].getConnNo() + "                     "
                        + "Customer Name        : " + gc[i].getName());
                System.out.println("Booking Date      : " + f.format(bookdate) + "            "
                        + "Cust MobNum          :" + gc[i].getMobileno());
                System.out.println("----------------------------------------------------------------------------");
                if (bk[i].getAmount() == 825.0) {
                    System.out.println("Amount            : " + bk[i].getAmount() + " Rupees");
                    System.out.println("Refund            : " + zero + " Rupees");
                    System.out.println("Total Amount      : " + (bk[i].getAmount()) + " Rupees");
                } else {
                    System.out.println("Amount            : " + bk[i].getAmount() + " Rupees");
                    System.out.println("Refund            : " + (bk[i].getAmount() * 0.05) + " Rupees ");
                    System.out.println(
                            "Total Amount      : " + ((bk[i].getAmount() - (bk[i].getAmount() * 0.05))) + " Rupees");
                }
                System.out.println("----------------------------------------------------------------------------");
                System.out.println("Del Person's Name : " + del[i].getdName() + "               "
                        + "Del Person's Mob    : " + del[i].getdPhoneNo());
                System.out.println("Delivery Date     : " + f.format(deldate));
                System.out
                        .println("============================================================================\n\n\n");
            }
        }
    }
}
