package com.palm.newbenefit.Module

class MapAddress(
    network_hospital_id: String?,
    policy_no: String?,
    HOSIDNO1: String?,
    ZONE_NAME: String?,
    PARTNER_ID: String?,
    HOSPITAL_NAME: String?,
    ADDRESS1: String?,
    ADDRESS2: String?,
    CITY_NAME: String?,
    STATE_NAME: String?,
    phoneNo: String?,
    PIN_CODE: String?,
    LANDMARK_1: String?,
    LANDMARK_2: String?,
    EMAIL: String?,
    LEVEL_OF_CARE: String?,
    ISHOSPITALACTIVE: String?,
    insurance_Company: String?,
    hospIRDACode: String?,
    HOSP_CREATED_ON: String?,
    HOSP_MODIFIED_ON: String?
) {
    var network_hospital_id: String? = null
    var policy_no: String? = null
    var hOSIDNO1: String? = null
    var zONE_NAME: String? = null
    var pARTNER_ID: String? = null
    var hOSPITAL_NAME: String? = null
    var aDDRESS1: String? = null
    var aDDRESS2: String? = null
    var cITY_NAME: String? = null
    var sTATE_NAME: String? = null
    var phoneNo: String? = null
    var pIN_CODE: String? = null
    var lANDMARK_1: String? = null
    var lANDMARK_2: String? = null
    var eMAIL: String? = null
    var lEVEL_OF_CARE: String? = null
    var iSHOSPITALACTIVE: String? = null
    var insurance_Company: String? = null
    var hospIRDACode: String? = null
    var hOSP_CREATED_ON: String? = null
    var hOSP_MODIFIED_ON: String? = null

    override fun toString(): String {
        return "MapAddress{" +
                "network_hospital_id='" + network_hospital_id + '\'' +
                ", policy_no='" + policy_no + '\'' +
                ", HOSIDNO1='" + hOSIDNO1 + '\'' +
                ", ZONE_NAME='" + zONE_NAME + '\'' +
                ", PARTNER_ID='" + pARTNER_ID + '\'' +
                ", HOSPITAL_NAME='" + hOSPITAL_NAME + '\'' +
                ", ADDRESS1='" + aDDRESS1 + '\'' +
                ", ADDRESS2='" + aDDRESS2 + '\'' +
                ", CITY_NAME='" + cITY_NAME + '\'' +
                ", STATE_NAME='" + sTATE_NAME + '\'' +
                ", PhoneNo='" + phoneNo + '\'' +
                ", PIN_CODE='" + pIN_CODE + '\'' +
                ", LANDMARK_1='" + lANDMARK_1 + '\'' +
                ", LANDMARK_2='" + lANDMARK_2 + '\'' +
                ", EMAIL='" + eMAIL + '\'' +
                ", LEVEL_OF_CARE='" + lEVEL_OF_CARE + '\'' +
                ", ISHOSPITALACTIVE='" + iSHOSPITALACTIVE + '\'' +
                ", Insurance_Company='" + insurance_Company + '\'' +
                ", HospIRDACode='" + hospIRDACode + '\'' +
                ", HOSP_CREATED_ON='" + hOSP_CREATED_ON + '\'' +
                ", HOSP_MODIFIED_ON='" + hOSP_MODIFIED_ON + '\'' +
                '}'
    }

    init {
        this.network_hospital_id = network_hospital_id
        this.policy_no = policy_no
        hOSIDNO1 = HOSIDNO1
        zONE_NAME = ZONE_NAME
        pARTNER_ID = PARTNER_ID
        hOSPITAL_NAME = HOSPITAL_NAME
        aDDRESS1 = ADDRESS1
        aDDRESS2 = ADDRESS2
        cITY_NAME = CITY_NAME
        sTATE_NAME = STATE_NAME
        this.phoneNo = phoneNo
        pIN_CODE = PIN_CODE
        lANDMARK_1 = LANDMARK_1
        lANDMARK_2 = LANDMARK_2
        eMAIL = EMAIL
        lEVEL_OF_CARE = LEVEL_OF_CARE
        iSHOSPITALACTIVE = ISHOSPITALACTIVE
        this.insurance_Company = insurance_Company
        this.hospIRDACode = hospIRDACode
        hOSP_CREATED_ON = HOSP_CREATED_ON
        hOSP_MODIFIED_ON = HOSP_MODIFIED_ON
    }
}