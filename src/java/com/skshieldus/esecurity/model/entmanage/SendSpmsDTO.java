package com.skshieldus.esecurity.model.entmanage;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.skshieldus.esecurity.common.model.CommonDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@JsonInclude(value = Include.NON_NULL)
@Schema(description = "이천/청주 차량관제 프로시저 호출")
public class SendSpmsDTO extends CommonDTO {

    @Schema(description = "Division")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String Division;

    @Schema(description = "MemberType")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String MemberType;

    @Schema(description = "VehicleNumber")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String VehicleNumber;

    @Schema(description = "VehicleName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String VehicleName;

    @Schema(description = "EnterDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String EnterDateTime;

    @Schema(description = "NoEntryDateTime")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String NoEntryDateTime;

    @Schema(description = "VisitPurpose")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String VisitPurpose;

    @Schema(description = "ApproverName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ApproverName;

    @Schema(description = "VisitorName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String VisitorName;

    @Schema(description = "AffiliatedCompany")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String AffiliatedCompany;

    @Schema(description = "DeptName")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String DeptName;

    @Schema(description = "ContactNumber")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String ContactNumber;

    @Schema(description = "Destination")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String Destination;

    @Schema(description = "Location")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String Location;

    @Schema(description = "BirthDay")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String BirthDay;

    @Schema(description = "Note")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String Note;

    @Schema(description = "SecurityID")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String SecurityID;

    @Schema(description = "UnionNumber")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String UnionNumber;

    @Schema(description = "SumVstPlcGate")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer SumVstPlcGate;

    @Schema(description = "lid")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Integer Lid;

    public String getDivision() {
        return Division;
    }

    public void setDivision(String division) {
        Division = division;
    }

    public String getMemberType() {
        return MemberType;
    }

    public void setMemberType(String memberType) {
        MemberType = memberType;
    }

    public String getVehicleNumber() {
        return VehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        VehicleNumber = vehicleNumber;
    }

    public String getVehicleName() {
        return VehicleName;
    }

    public void setVehicleName(String vehicleName) {
        VehicleName = vehicleName;
    }

    public String getEnterDateTime() {
        return EnterDateTime;
    }

    public void setEnterDateTime(String enterDateTime) {
        EnterDateTime = enterDateTime;
    }

    public String getNoEntryDateTime() {
        return NoEntryDateTime;
    }

    public void setNoEntryDateTime(String noEntryDateTime) {
        NoEntryDateTime = noEntryDateTime;
    }

    public String getVisitPurpose() {
        return VisitPurpose;
    }

    public void setVisitPurpose(String visitPurpose) {
        VisitPurpose = visitPurpose;
    }

    public String getApproverName() {
        return ApproverName;
    }

    public void setApproverName(String approverName) {
        ApproverName = approverName;
    }

    public String getVisitorName() {
        return VisitorName;
    }

    public void setVisitorName(String visitorName) {
        VisitorName = visitorName;
    }

    public String getAffiliatedCompany() {
        return AffiliatedCompany;
    }

    public void setAffiliatedCompany(String affiliatedCompany) {
        AffiliatedCompany = affiliatedCompany;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getContactNumber() {
        return ContactNumber;
    }

    public void setContactNumber(String contactNumber) {
        ContactNumber = contactNumber;
    }

    public String getDestination() {
        return Destination;
    }

    public void setDestination(String destination) {
        Destination = destination;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getBirthDay() {
        return BirthDay;
    }

    public void setBirthDay(String birthDay) {
        BirthDay = birthDay;
    }

    public Integer getSumVstPlcGate() {
        return SumVstPlcGate;
    }

    public void setSumVstPlcGate(Integer sumVstPlcGate) {
        SumVstPlcGate = sumVstPlcGate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getSecurityID() {
        return SecurityID;
    }

    public void setSecurityID(String securityID) {
        SecurityID = securityID;
    }

    public String getUnionNumber() {
        return UnionNumber;
    }

    public void setUnionNumber(String unionNumber) {
        UnionNumber = unionNumber;
    }

    public Integer getLid() {
        return Lid;
    }

    public void setLid(Integer lid) {
        this.Lid = lid;
    }

}
