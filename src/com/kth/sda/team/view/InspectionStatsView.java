
package com.kth.sda.team.view;

import java.util.List;

import com.kth.sda.team.integration.InspectionCheckListDTO;
import com.kth.sda.team.integration.InspectionCheckListObserver;
import com.kth.sda.team.model.Inspection;


/**
 * @author memmi
 *
 */
public class InspectionStatsView implements InspectionCheckListObserver{
	private int success=0;
	private int fail=0;

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
