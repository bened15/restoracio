package com.jbd.utils;

import java.awt.print.PrinterJob;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.Finishings;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.Sides;

import com.jbd.model.RestOrder;

public class Printer {

	@SuppressWarnings("finally")
	public boolean printOrders(List<RestOrder> orders) {

		boolean status 								= true;
		DocFlavor flavor							= DocFlavor.INPUT_STREAM.AUTOSENSE;
		PrintRequestAttributeSet aset 				= new HashPrintRequestAttributeSet();
		HashMap<String,OrderToPrint> orderList		= new HashMap<String,OrderToPrint>();
		HashMap<String,List<OrderToPrint>> itemList	= new HashMap<String,List<OrderToPrint>>();

		String tableName 							= new String();
		String attendantName 						= new String();
		String clientAmount 						= new String();
		String orderDateTime 						= new String();
		String printingHeader 						= new String("\r\n");

		System.out.println("Total orders to print [" + orders.size() + "]\n");

		if(orders.size() > 0){

			/** HEADER - Assuming same values for all items to print **/
			tableName 				= orders.get(0).getRestTableAccount().getRestTable().getTableName();
			clientAmount			= orders.get(0).getRestTableAccount().getGuestNum().toString();
			attendantName 			= orders.get(0).getAttendant();
			orderDateTime 			= orders.get(0).getEntryDate().toString();

			/** Giving format to text to print **/
			printingHeader += "Mesa: " 			+ tableName.toUpperCase()	+ " ";
			printingHeader += "[" 				+ clientAmount 				+ " Clientes]\r\n";
			printingHeader += "Fecha/Hora: "	+ orderDateTime 			+ "\r\n";
			printingHeader += "Mesero: " 		+ attendantName 			+ "\r\n";
			printingHeader += "--------------------------------\r\n";

			/** Grouping orders by quantity and discarting
			 * those which doesn't have a printer configured  **/
			for (RestOrder order : orders){
				OrderToPrint otp		= new OrderToPrint();

				String itemName 		= new String();
				String comments	 		= new String();
				String printerName 		= new String();

				printerName				= order.getRestMenuItem().getRestKitchen().getKitchenPrinter();
				itemName 				= order.getRestMenuItem().getMenuItemShortName();
				comments	 			= order.getOrderComment();

				if(printerName != null && printerName != ""){

					otp.setPrinterName(printerName);
					otp.setItemName(itemName);
					otp.setComments(comments);
					otp.addQuantity();

					if(orderList.get(otp.getClassValue()) == null){
						orderList.put(otp.getClassValue(), otp);
					}else{
						orderList.get(otp.getClassValue()).addQuantity();
					}
				}

			}
		}

		/** Create List based on Printers available **/
		for (OrderToPrint otp : orderList.values()) {
			List<OrderToPrint> otps;

			otps = itemList.get(otp.getPrinterName());

			if(otps == null)
				otps = new ArrayList<OrderToPrint>();

			otps.add(otp);
			itemList.put(otp.getPrinterName(), otps);
		}


		for (String keyPrinter : itemList.keySet()) {
	        String printingDetail 	= new String();
	        List<OrderToPrint> otps	= itemList.get(keyPrinter);

	        for (OrderToPrint otp : otps){
	        	printingDetail 		+= otp.getQuantity() + " " + otp.getItemName() + "\r\n";
				if(otp.getComments() != null && !"".equals(otp.getComments())){
					printingDetail 	+= "   -" + otp.getComments() + "\r\n";

				}
	        }

	        printingDetail 	+= "\r\n";

	        try {
				PrintService pservice = findPrintService(keyPrinter);

				/** Configuring Doc object for printing **/
				aset.add(MediaSizeName.ISO_A7);
				aset.add(new Copies(1));
				aset.add(Sides.ONE_SIDED);
				aset.add(Finishings.STAPLE);

				if (pservice != null) {
					/* create a print job for the chosen service */
					DocPrintJob pj = pservice.createPrintJob();

					/** Create a Doc object to hold the print data **/
					Doc doc = new SimpleDoc(new ByteArrayInputStream((printingHeader+printingDetail).getBytes()), flavor, null);

					/* print the doc as specified */
//					pj.print(doc, aset);
					System.out.println(printingHeader+printingDetail);
				}
			} catch (Exception e) {
				status = false;
				System.err.println(e);
			}

	    }

		return status;

	}

	public PrintService findPrintService(String printerName) {

		PrintService service = null;

		// Get array of all print services - sort order NOT GUARANTEED!
		PrintService[] services = PrinterJob.lookupPrintServices();

		// Retrieve specified print service from the array
		for (int index = 0; service == null && index < services.length; index++) {

			if (services[index].getName().equalsIgnoreCase(printerName)) {

				service = services[index];
			}
		}

		// Return the print service
		return service;
	}
}
