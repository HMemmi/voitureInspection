package com.kth.sda.team.view;

import java.util.List;
import java.util.Scanner;

import com.kth.sda.team.controller.InspectionController;
import com.kth.sda.team.integration.CreditCardDTO;
import com.kth.sda.team.integration.InspectionCheckListDTO;
import com.kth.sda.team.integration.InspectionCheckListObserver;
import com.kth.sda.team.integration.InspectionDTO;

import Exception.NullInspectionException;

/**
 * 
 * @author tmpuser-10229
 * 
 */
public class InspectionView implements InspectionCheckListObserver{

	/**
	 *  
	 * @param inspectionController
	 */
	
	private int success=0;
	private int fail=0;
	public InspectionView(InspectionController inspectionController) {


		inspectionController.startNewInspection();
		inspectionController.closeDoor();
		
		InspectionDTO inspectionDTO = new InspectionDTO("127");
		InspectionDTO found;
		try
		{
		 found = inspectionController.searchMatchingLicenseNo(inspectionDTO);
		CreditCardDTO creditCardDTO = new CreditCardDTO("1234", "123","123456789", "Alaa", 1234);
			
			
			boolean status = inspectionController.checkStatusOfPayment(creditCardDTO);
			if (status) {
				
				inspectionController.payInspection(found, creditCardDTO);
				List<InspectionCheckListDTO> inspections = inspectionController.createInspectionListfromDTO(found);
				 //inspections = inspection.getInspectionCheckList();

				Scanner scanner = new Scanner(System.in);

				int operationNumber = 0;
				boolean defaults = true;
				while ((defaults == true) && (operationNumber < inspections.size())) {
					String answer;
					do {
						System.out.println(inspections.get(operationNumber).getOperation()
								+ " please choose yes, no or exit");
						answer = scanner.next();
					} while ((answer.equals("yes") == false)
							&& (answer.equals("no") == false)
							&& (answer.equals("exit") == false));

					if (answer.equals("yes")) {
						System.out
								.println("would you like to check it or no! please enter true or false");
						boolean b = scanner.nextBoolean();
				
						
						inspectionController.setResultToInspection(operationNumber,inspections.get(operationNumber), b);
						
						
						
						
						
						
						
					}
					if (answer.equals("exit")) {
						defaults = false;
						
					}
					operationNumber++;
				}
				inspectionController.printResult(inspections);

				inspectionController.openDoor();
				inspectionController.closeDoor();
			} else {
				System.out.println("The CreditCard number is wrong.");
			}
		}
	

		
	catch(RuntimeException e){
		System.out.println("Runtime there is no inspection");
		e.printStackTrace();
	}
	catch(Exception e){
		System.out.println("Exception there is no inspection");
	
	}	
	}
	@Override
	public void updateForOne(InspectionCheckListDTO inspectionCheckListDTO) {
		
		showData(inspectionCheckListDTO);
		
	}

	private void showData(InspectionCheckListDTO inspectionCheckListDTO) {

	
		if(inspectionCheckListDTO.getResult()){
			success++;
		}
		else{fail++;}
		
		System.out.println("success= "+ success+ " fail= "+ fail );
		
		
		
	}

	}


		
	

