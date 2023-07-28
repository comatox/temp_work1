package com.skshieldus.esecurity.service.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxSearchDTO;
import java.util.List;

public interface ApprovalBoxService {

    List<ApprovalBoxDTO> selectApprovalBoxRequestList(ApprovalBoxSearchDTO approvalBoxSearch);

    List<ApprovalBoxDTO> selectApprovalBoxApprovalList(ApprovalBoxSearchDTO approvalBoxSearch);

    List<ApprovalBoxDTO> selectApprovalBoxIdCardList(ApprovalBoxSearchDTO approvalBoxSearch);

}