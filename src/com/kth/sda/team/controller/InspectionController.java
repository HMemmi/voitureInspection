package com.kth.sda.team.controller;

import com.kth.sda.team.integration.*;
import com.kth.sda.team.model.Inspection;
import com.kth.sda.team.model.Payment;

import Exception.NullInspectionException;

import java.util.ArrayList;
import java.util.List;

/**
 * The inspection controller that will be passed to the inspection view.
 * 
 * @author tmpuser-10229
 *
 */
public class InspectionController {
	private QueNumberDisplay displayNumber;
	private InspectionRegistry inspectionRegistry;
	private Printer printer;
	private ExternalPaymentAuthorizationSystem externalauthSystemOfPayment;
	private GarageDoor garageDoor;
	private List<InspectionCheckListDTO> inspectionCheckListTMP;
	private Inspection inspection;
	private InspectionCheckListObserver inspectionCheckListObserver;

	/**
	 * The Inspection controller's constructor.
	 * 
	 * @param inspectionChecklistDto
	 *            The operation of the inspection that the inspector will
	 *            perform on the car.
	 * @param inspectionRegistry
	 *            The database of the inspections that are available for the
	 *            inspections.
	 * @param displayNumber
	 *            The display that placed out side the garage.
	 * @param printer
	 *            The printer device.
	 * @param externalauthSystemOfPayment
	 *            The External Payment Authorization System to check the credit
	 *            card if its real or not.
	 * @param garageDoor
	 *            The garage door.
	 * @param ins
	 */

	public InspectionController(InspectionCheckListDTO inspectionChecklistDto, InspectionRegistry inspectionRegistry,
			QueNumberDisplay displayNumber, Printer printer,
			ExternalPaymentAuthorizationSystem externalauthSystemOfPayment, GarageDoor garageDoor,
			InspectionCheckListObserver inspectionCheckListObserver) {
		this.displayNumber = displayNumber;
		this.inspectionRegistry = inspectionRegistry;
		this.printer = printer;
		this.externalauthSystemOfPayment = externalauthSystemOfPayment;
		this.garageDoor = garageDoor;
		this.inspectionCheckListObserver= inspectionCheckListObserver;
	}

	/**
	 * Start new Inspection for a new costumer.
	 */
	public int startNewInspection() {
		return displayNumber.increment();
	}

	/**
	 * The inspector will close the garage door.
	 */
	public void closeDoor() {
		garageDoor.close();
	}

	/**
	 * The inspector will open the garage door.
	 */
	public void openDoor() {
		garageDoor.open();
	}

	/**
	 * Search for car's license number in database.
	 * 
	 * @param inspectionDTO
	 *            The license number to be searched.
	 * @return null If we didn't find the car's license in the database. found
	 *         If the found the car's license number.
	 */
	public InspectionDTO searchMatchingLicenseNo(InspectionDTO inspectionDTO) throws NullInspectionException {
		InspectionDTO found;
		inspectionRegistry.access();
		found = inspectionRegistry.searchMatching(inspectionDTO);
		return found;
	}

	/**
	 * After checking that the license number is correct the program will print
	 * the receipt for the customer.
	 * 
	 * @param inspectionDTO
	 *            The inspection car object that will get its cost.
	 * @param creditCardDTO
	 *            The costumer credit card to be checked.
	 */
	public void payInspection(InspectionDTO inspectionDTO, CreditCardDTO creditCardDTO) {
		int amount = inspectionDTO.getCost();
		Payment payment = new Payment();
		payment.pay(creditCardDTO, amount);
	}

	/**
	 * Create new inspection to be performed on a car.
	 * 
	 * @param found
	 *            The inspection DTO that will be used to create a new
	 *            inspection.
	 * @return inspection The value of the new created inspection.
	 */

	public List<InspectionCheckListDTO> createInspectionListfromDTO(InspectionDTO inspectionDTO) {
		List<InspectionCheckListDTO> inspectionCheckList;
		
		inspection = new Inspection(inspectionDTO,inspectionCheckListObserver);
		inspectionCheckList = inspection.getInspectionCheckList();

		return inspectionCheckList;
	}

	/**
	 * This method will be used to print out the final result of the
	 * inspections.
	 * 
	 * @param inspections
	 *            The list of the final inspections that have been performed on
	 *            a car.
	 */
	public void printResult(List<InspectionCheckListDTO> inspections) {
		printer.printResultInspection(inspections);
	}

	/**
	 * Check the credit card if it is valid or not.
	 * 
	 * @param creditCardDTO
	 *            This object will contain the costumer credit card information.
	 * @return true If the credit card is valid. false If the credit card is not
	 *         valid.
	 */
	public boolean checkStatusOfPayment(CreditCardDTO creditCardDTO) {
		return externalauthSystemOfPayment.request(creditCardDTO);
	}

	public void setResultToInspection(int operationNumber, InspectionCheckListDTO inspectionCheckListDTO, boolean b) {
		
		inspection.setInspectionCheckList(operationNumber,b);
		
		
	}


}
