package com.kth.sda.team.integration;

import java.util.List;

import com.kth.sda.team.model.Inspection;

public interface InspectionCheckListObserver {

	void updateForOne(InspectionCheckListDTO inspectionCheckListDTO);

}
