package com.skshieldus.esecurity.repository.sysmanage;

import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxDTO;
import com.skshieldus.esecurity.model.sysmanage.ApprovalBoxSearchDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ApprovalBoxRepository {

    List<ApprovalBoxDTO> selectApprovalBoxApprovalList(ApprovalBoxSearchDTO approvalBoxSearch);

    List<ApprovalBoxDTO> selectApprovalBoxRequestList(ApprovalBoxSearchDTO approvalBoxSearch);

    List<ApprovalBoxDTO> selectApprovalBoxIdCardList(ApprovalBoxSearchDTO approvalBoxSearch);

}