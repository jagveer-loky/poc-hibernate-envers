package com.fiserv.preproposal.api.domain.entity;

import com.fiserv.preproposal.api.domain.dtos.BasicReport;
import com.fiserv.preproposal.api.domain.dtos.CompleteReport;
import com.fiserv.preproposal.api.domain.dtos.QuantitativeReport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "TB_PROPOSAL_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(
                name = "basicReportMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = BasicReport.class,
                                columns = {
                                        @ColumnResult(name = "ID", type = String.class),
                                        @ColumnResult(name = "PROPOSALNUMBER", type = String.class),
                                        @ColumnResult(name = "CPFCNPJ", type = String.class),
                                        @ColumnResult(name = "FANTASYNAME", type = String.class),
                                        @ColumnResult(name = "SOCIALREASON", type = String.class),
                                        @ColumnResult(name = "VOUCHERNAME", type = String.class),
                                        @ColumnResult(name = "RESPONSETYPE", type = String.class),
                                        @ColumnResult(name = "USERID", type = String.class),
                                        @ColumnResult(name = "AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name = "INSTITUTION", type = String.class),
                                        @ColumnResult(name = "SERVICECONTRACT", type = String.class),
                                        @ColumnResult(name = "OPTIN", type = String.class),
                                        @ColumnResult(name = "SELLERREGISTRATION", type = String.class),
                                        @ColumnResult(name = "SUBCHANNEL", type = String.class),
                                        @ColumnResult(name = "TECNOLOGY", type = String.class),
                                        @ColumnResult(name = "INCLUDEDATE", type = String.class),
                                        @ColumnResult(name = "CONCLUSIONDATE", type = String.class),
                                        @ColumnResult(name = "MERCHANTID", type = String.class),
                                        @ColumnResult(name = "FISERVSTATUS", type = String.class),
                                        @ColumnResult(name = "CEFSTATUS", type = String.class),
                                        @ColumnResult(name = "CEFMESSAGE", type = String.class),
                                        @ColumnResult(name = "ERRORS", type = String.class),
                                        @ColumnResult(name = "SUBMITIONDATE", type = String.class),
                                        @ColumnResult(name = "ERROR_FIELD", type = String.class),
                                        @ColumnResult(name = "ERROR_DESCRIPTION", type = String.class),
                                        @ColumnResult(name = "ERROR_MESSAGE", type = String.class),
                                        @ColumnResult(name = "DETAIL", type = String.class),
                                        @ColumnResult(name = "STEP", type = String.class),
                                        @ColumnResult(name = "MORE_INFORMATION", type = String.class),
                                        @ColumnResult(name = "FISERV_ONLINE_RESPONSE", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "quantitativeReportMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = QuantitativeReport.class,
                                columns = {
                                        @ColumnResult(name = "FILENAME", type = String.class),
                                        @ColumnResult(name = "INSTITUTION", type = String.class),
                                        @ColumnResult(name = "PROCESSINGDATE", type = String.class),
                                        @ColumnResult(name = "FILESTATUS", type = String.class),
                                        @ColumnResult(name = "NUMPROPOSALS", type = Long.class),
                                        @ColumnResult(name = "NUMONLINEREJECT", type = Long.class),
                                        @ColumnResult(name = "NUMPENDINGINONLINE", type = Long.class),
                                        @ColumnResult(name = "NUMPENDINGCOMPLEMENT", type = Long.class),
                                        @ColumnResult(name = "NUMERRORPREPROPOSAL", type = Long.class),
                                        @ColumnResult(name = "NUMFINISHED", type = Long.class),
                                        @ColumnResult(name = "NUMTMPCANCELED", type = Long.class),
                                        @ColumnResult(name = "NUMCREDONLINE", type = Long.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name = "completeReportMapping",
                classes = {
                        @ConstructorResult(
                                targetClass = CompleteReport.class,
                                columns = {
                                        @ColumnResult(name = "PREPROPOSALID", type = String.class),
                                        @ColumnResult(name = "PROPOSALNUMBER", type = Long.class),
                                        @ColumnResult(name = "MERCHANT", type = String.class),
                                        @ColumnResult(name = "USERID", type = String.class),
                                        @ColumnResult(name = "AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name = "INSTITUTION", type = String.class),
                                        @ColumnResult(name = "SERVICECONTRACT", type = Integer.class),
                                        @ColumnResult(name = "OPTIN", type = String.class),
                                        @ColumnResult(name = "SELLERREGISTRATION", type = String.class),
                                        @ColumnResult(name = "SUBCHANNEL", type = String.class),
                                        @ColumnResult(name = "TECHNOLOGY", type = String.class),
                                        @ColumnResult(name = "TERMINALSNUMBER", type = Long.class),
                                        @ColumnResult(name = "UNITARYVALUE", type = String.class),
                                        @ColumnResult(name = "FISERVSTATUS", type = String.class),
                                        @ColumnResult(name = "CAIXASTATUS", type = String.class),
                                        @ColumnResult(name = "CAIXAMESSAGE", type = String.class),
//                                        @ColumnResult(name = "ERRORS", type = String.class),
                                        @ColumnResult(name = "INCLUDEIN", type = String.class),
                                        @ColumnResult(name = "FINISHEDIN", type = String.class),
                                        @ColumnResult(name = "SUBMISSIONONLINEDATE", type = String.class),
                                        @ColumnResult(name = "PERSONTYPE", type = String.class),
                                        @ColumnResult(name = "CPFCNPJ", type = String.class),
                                        @ColumnResult(name = "FANTASYNAME", type = String.class),
                                        @ColumnResult(name = "SOCIALREASON", type = String.class),
                                        @ColumnResult(name = "PLATELETNAME", type = String.class),
                                        @ColumnResult(name = "MONTHLYBILLING", type = String.class),
                                        @ColumnResult(name = "BIRTHDATE", type = String.class),
                                        @ColumnResult(name = "GENDER", type = String.class),
                                        @ColumnResult(name = "TREATMENTPRONOUN", type = String.class),
                                        @ColumnResult(name = "BIRTHPLACE", type = String.class),
                                        @ColumnResult(name = "NACIONALITY", type = String.class),
                                        @ColumnResult(name = "FULLNAME", type = String.class),
                                        @ColumnResult(name = "MUNICIPALREGISTRATION", type = String.class),
                                        @ColumnResult(name = "STATEREGISTRATION", type = String.class),
                                        @ColumnResult(name = "CONSTITUTIONFORM", type = String.class),
                                        @ColumnResult(name = "OPENDATE", type = String.class),
                                        @ColumnResult(name = "ANNUAL_BILLING_VOL", type = String.class),
                                        @ColumnResult(name = "ANNUAL_VOL_CASH", type = String.class),
                                        @ColumnResult(name = "ANNUAL_VOL_SALES_CARD", type = String.class),
                                        @ColumnResult(name = "ANNUAL_VOL_SALES_CARD_GROUP", type = String.class),
                                        @ColumnResult(name = "AVERAGE_TICKET", type = String.class),
                                        @ColumnResult(name = "BACENPERMISSION", type = String.class),
                                        @ColumnResult(name = "CAMPAINGID", type = String.class),
                                        @ColumnResult(name = "CNAE", type = String.class),
                                        @ColumnResult(name = "ECOMMERCE", type = String.class),
                                        @ColumnResult(name = "FOREIGNCARD", type = String.class),
                                        @ColumnResult(name = "MANUAL_PREPAYMENT_ENABLED", type = String.class),
                                        @ColumnResult(name = "BOARDING_BRANCHING", type = String.class),
                                        @ColumnResult(name = "PERC_CARD_NOT_PRESENT", type = String.class),
                                        @ColumnResult(name = "PERC_CARD_PRESENT", type = String.class),
                                        @ColumnResult(name = "PERC_INTERNET", type = String.class),
                                        @ColumnResult(name = "PREPAYMENT_INDICATOR", type = String.class),
                                        @ColumnResult(name = "RECURRINGTRANSACTION", type = String.class),
                                        @ColumnResult(name = "SERVICE_DAY_0", type = String.class),
                                        @ColumnResult(name = "SERVICE_DAY_15_30", type = String.class),
                                        @ColumnResult(name = "SERVICE_DAY_1_7", type = String.class),
                                        @ColumnResult(name = "SERVICE_DAY_8_14", type = String.class),
                                        @ColumnResult(name = "SERVICE_DAY_OVER_30", type = String.class),
                                        @ColumnResult(name = "PENDINGBWDATE", type = String.class),
                                        @ColumnResult(name = "INSTALLATIONDATE", type = String.class),
                                        @ColumnResult(name = "ADDRESSTYPE", type = String.class),
                                        @ColumnResult(name = "CEP", type = String.class),
                                        @ColumnResult(name = "STREET", type = String.class),
                                        @ColumnResult(name = "NUMBER", type = String.class),
                                        @ColumnResult(name = "DISTRICT", type = String.class),
                                        @ColumnResult(name = "COMPLEMENT", type = String.class),
                                        @ColumnResult(name = "CITY", type = String.class),
                                        @ColumnResult(name = "STATE", type = String.class),
                                        @ColumnResult(name = "PARTNERTYPE", type = String.class),
                                        @ColumnResult(name = "CPFCNPJPARTNER", type = String.class),
                                        @ColumnResult(name = "PARTNERBIRTHDATE", type = String.class),
                                        @ColumnResult(name = "CONSTITUTIONTYPE", type = String.class),
                                        @ColumnResult(name = "CONTRACT", type = String.class),
                                        @ColumnResult(name = "PARTNERNAME", type = String.class),
                                        @ColumnResult(name = "PRONOUNTREATMENTPARTNER", type = String.class),
                                        @ColumnResult(name = "PARTNERNACIONALITY", type = String.class),
                                        @ColumnResult(name = "PARTNERFUNCTION", type = String.class),
                                        @ColumnResult(name = "PERCPARTNER", type = String.class),
                                        @ColumnResult(name = "CONTACTBIRTHDATE", type = String.class),
                                        @ColumnResult(name = "CONTACTCPF", type = String.class),
                                        @ColumnResult(name = "CONTACTEMAIL", type = String.class),
                                        @ColumnResult(name = "CONTACTNAME", type = String.class),
                                        @ColumnResult(name = "CONTACTPHONE", type = String.class),
                                        @ColumnResult(name = "CONTACTCELLPHONE", type = String.class),
                                        @ColumnResult(name = "DESCRIPTION", type = String.class),
                                        @ColumnResult(name = "DISCOUNT_FEE", type = String.class),
                                        @ColumnResult(name = "FEE", type = String.class),
                                        @ColumnResult(name = "FEE_ID_NUMBER", type = String.class),
                                        @ColumnResult(name = "BANKCODE", type = String.class),
                                        @ColumnResult(name = "AGENCY", type = String.class),
                                        @ColumnResult(name = "ACCOUNTDIGIT", type = String.class),
                                        @ColumnResult(name = "ACCOUNTNUMBER", type = String.class),
                                        @ColumnResult(name = "ACCOUNTOWNER", type = String.class),
                                        @ColumnResult(name = "ACCOUNTTYPE", type = String.class),
                                        @ColumnResult(name = "WORKDAYS", type = String.class),
                                        @ColumnResult(name = "RESPONSETYPE", type = String.class),
                                        @ColumnResult(name = "TYPEOFESTABLISHMENT", type = String.class),
                                        @ColumnResult(name = "ACCEPTTERM", type = String.class)
                                }
                        )
                }
        )

})
@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "getBasicReport", query = "SELECT\n" +
                "       tpd.proposal_number AS \"ID\",\n" +
                "       tpd.id AS \"PROPOSALNUMBER\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.CPF\n" +
                "       ELSE tpplp.CNPJ END AS \"CPFCNPJ\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.FANTASY_NAME\n" +
                "       ELSE tpplp.FANTASY_NAME END AS \"FANTASYNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.NAME || ' ' || tppp.SURNAME\n" +
                "       ELSE tpplp.SOCIAL_REASON END AS \"SOCIALREASON\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.PLATE_NAME\n" +
                "       ELSE tpplp.PLATE_NAME END AS \"VOUCHERNAME\",\n" +
                "       tpd.RESPONSE_TYPE AS \"RESPONSETYPE\",\n" +
                "       tpd.AGENT_CHANNEL AS \"USERID\",\n" +
                "       tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\",\n" +
                "       tfc.institution AS \"INSTITUTION\",\n" +
                "       tfc.service_contract AS \"SERVICECONTRACT\",\n" +
                "       tpd.optin AS \"OPTIN\"," +
                "       tpd.seller_registration AS \"SELLERREGISTRATION\"," +
                "       tpd.SUB_CHANNEL AS \"SUBCHANNEL\", \n" +
                "       tcs.service_id || '-' || TCS.TECHNOLOGY AS \"TECNOLOGY\",\n" +
                "       TO_CHAR(tpd.INSERTION_DATE, 'dd/MM/yyyy hh:mm')  AS \"INCLUDEDATE\",\n" +
                "       TO_CHAR(tpd.CONCLUSION_DATE, 'dd/MM/yyyy hh:mm')  AS \"CONCLUSIONDATE\",\n" +
                "       tpd.merchant_id AS \"MERCHANTID\",\n" +
                "       tpps.CODE || '-' || tpps.PT_DESCRIPTION AS \"FISERVSTATUS\",\n" +
                "       tpcs.code || '-' || tpcs.PT_DESCRIPTION AS \"CEFSTATUS\",\n" +
                "       tpcs.message_code || '-' || tpcs.message_description AS \"CEFMESSAGE\",\n" +
                "       (" +
                "           SELECT LISTAGG" +
                "               (" +
                "                   (" +
                "                       CASE" +
                "                           WHEN TB_PRE_PROPOSAL_HISTORY_ERROR.FIELD IS NULL THEN " +
                "                               TB_PRE_PROPOSAL_HISTORY.STATUS || ': ' ||  TB_PRE_PROPOSAL_HISTORY_ERROR.MESSAGE" +
                "                           ELSE" +
                "                               TB_PRE_PROPOSAL_HISTORY.STATUS || '(' || TB_PRE_PROPOSAL_HISTORY_ERROR.FIELD || '): ' ||  TB_PRE_PROPOSAL_HISTORY_ERROR.MESSAGE " +
                "                       END" +
                "                   ), '; '" +
                "               ) WITHIN GROUP (ORDER BY TB_PRE_PROPOSAL_HISTORY.STATUS) \"ERRORS\"" +
                "           FROM TB_PRE_PROPOSAL_HISTORY" +
                "               LEFT OUTER JOIN TB_PRE_PROPOSAL_HISTORY_ERROR TB_PRE_PROPOSAL_HISTORY_ERROR on TB_PRE_PROPOSAL_HISTORY_ERROR.ID_PROPOSAL_HISTORY = TB_PRE_PROPOSAL_HISTORY.id" +
                "           WHERE TB_PRE_PROPOSAL_HISTORY.ID_PROPOSAL_DATA = tpd.id AND TB_PRE_PROPOSAL_HISTORY.STATUS LIKE '%_ERROR'" +
                "       ) AS \"ERRORS\"," +
                "       TO_CHAR(tpd.ONLINE_DATE, 'dd/MM/yyyy hh:mm') AS \"SUBMITIONDATE\"," +
                "       tpphe.field AS \"ERROR_FIELD\"," +
                "       tpphe.field_description AS \"ERROR_DESCRIPTION\"," +
                "       tpphe.message AS \"ERROR_MESSAGE\"," +
                "       tpphe.message_detail AS \"DETAIL\"," +
                "       (select a.STATUS from (SELECT id_proposal_data, STATUS, rank() over (partition by ID_PROPOSAL_DATA order by INSERT_DATA desc) rnk FROM TB_PRE_PROPOSAL_HISTORY order by rnk asc) a where a.id_proposal_data = tpd.id and rownum = 1) AS \"STEP\"," +
                "       (select a.DESCRIPTION from (SELECT id_proposal_data, DESCRIPTION, rank() over (partition by ID_PROPOSAL_DATA order by INSERT_DATA desc) rnk FROM TB_PRE_PROPOSAL_HISTORY order by rnk asc) a where a.id_proposal_data = tpd.id and rownum = 1) AS \"MORE_INFORMATION\"," +
                "       dbms_lob.substr(json.RESPONSE_JSON,32767) AS \"FISERV_ONLINE_RESPONSE\"" +
                "FROM tb_proposal_data tpd" +
                "       LEFT JOIN TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA\n" +
                "       LEFT JOIN TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta\n" +
                "       LEFT JOIN TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control\n" +
                "       LEFT JOIN tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID\n" +
                "       LEFT JOIN tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "       LEFT JOIN tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id\n" +
                "       left join tb_pre_proposal_history tpph on tpd.id = tpph.id_proposal_data\n" +
                "       Left join TB_PRE_PROPOSAL_HISTORY_ERROR tpphe on tpph.id = tpphe.id_proposal_history\n" +
                "       Left join tb_proposal_json json on json.proposal_id = tpd.id\n" +
                "  WHERE tfc.INSTITUTION = :institution \n" +
                "       AND tfc.SERVICE_CONTRACT = :serviceContract \n" +
                "       AND tpd.INSERTION_DATE BETWEEN :initialDate AND :finalDate \n" +
                "       AND (" +
                "               (" +
                "                   (:notIn = 0) " +
                "                       AND (" +
                "                           COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type IN (:responsesTypes)" +
                "                       )" +
                "               )" +
                "           OR" +
                "               (" +
                "                   (:notIn = 1) " +
                "                       AND (" +
                "                           COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type NOT IN (:responsesTypes)" +
                "                       )" +
                "               )" +
                "       )" +
                "       AND (COALESCE(:status, NULL) IS NULL OR tpps.CODE IN (:status))"
                ,
                resultSetMapping = "basicReportMapping"),
        @NamedNativeQuery(name = "getCountBasicReport", query =
                "   SELECT COUNT(*) " +
                        "   FROM tb_proposal_data tpd " +
                        "       LEFT JOIN TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA " +
                        "       LEFT JOIN TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta " +
                        "       LEFT JOIN TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control " +
                        "       LEFT JOIN tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID " +
                        "       LEFT JOIN tb_pre_proposal_status tpps on tpps.id = tpd.status_id " +
                        "       LEFT JOIN tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id " +
                        "       left join tb_pre_proposal_history tpph on tpd.id = tpph.id_proposal_data" +
                        "       Left join TB_PRE_PROPOSAL_HISTORY_ERROR tpphe on tpph.id = tpphe.id_proposal_history" +
                        "       Left join tb_proposal_json json on json.proposal_id = tpd.id" +
                        "   WHERE tfc.INSTITUTION = :institution " +
                        "       AND tfc.SERVICE_CONTRACT = :serviceContract " +
                        "       AND tpd.INSERTION_DATE BETWEEN :initialDate AND :finalDate " +
                        "       AND (" +
                        "               (" +
                        "                   (:notIn = 0) " +
                        "                       AND (" +
                        "                           COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type IN (:responsesTypes)" +
                        "                       )" +
                        "               )" +
                        "           OR" +
                        "               (" +
                        "                   (:notIn = 1) " +
                        "                       AND (" +
                        "                           COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type NOT IN (:responsesTypes)" +
                        "                       )" +
                        "               )" +
                        "       )" +
                        "       AND (COALESCE(:status, NULL) IS NULL OR tpps.CODE IN (:status))"),
        @NamedNativeQuery(name = "getQuantitativeReport", query = "  SELECT  \n" +
                "            tfc.file_name AS \"FILENAME\",\n" +
                "            tfc.INSTITUTION AS \"INSTITUTION\", \n" +
                "            tfc.SERVICE_CONTRACT,\n" +
                "            TO_CHAR(tfc.READ_DATE, 'dd/MM/yyyy hh:mm')  AS \"PROCESSINGDATE\",\n" +
                "            CASE tfc.IS_VALID WHEN 0 THEN 'Não foi possivel ler o arquivo - Erro de formatação'\n" +
                "            ELSE 'Lido com sucesso' END AS \"FILESTATUS\",\n" +
                "            COUNT(tpd.id) AS \"NUMPROPOSALS\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('01','02','03','04','06','-1','14','15','16','17','20','23','24','25','27','34','37','500','768','880') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMONLINEREJECT\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('PRE4','11','12','13','18','21','22','33','38','30','05','07','08','09','19','888','870','765','860','077','260','769','270','31','32','26','28','35','36') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMPENDINGINONLINE\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('PRE1') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMPENDINGCOMPLEMENT\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('PRE2') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMERRORPREPROPOSAL\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('PRE3') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMFINISHED\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('MCT126','MCT128','MCT127','MCT131','MCT130','MCT129','MCT133','MCT134','MCT132') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMTMPCANCELED\",\n" +
                "            (SELECT COUNT(tpd.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd ON TFC2.ID = tpd.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd.status_id\n" +
                "             Where tpps2.CODE in ('39','39','400','00','40','41') " +
                "               AND (" +
                "                       (" +
                "                           (:notIn = 0) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 1) " +
                "                               AND (" +
                "                                   COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                               )" +
                "                       )" +
                "                   OR" +
                "                       (" +
                "                           (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "                       )" +
                "               )" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMCREDONLINE\"\n" +
                "       from TB_FILE_CONTROL TFC\n" +
                "           left join tb_proposal_data tpd ON TFC.ID = tpd.id_file_control\n" +
                "           left join tb_pre_proposal_history tpph on tpd.id = tpph.id_proposal_data\n" +
                "           Left join TB_PRE_PROPOSAL_HISTORY_ERROR tpphe on tpph.id = tpphe.id_proposal_history\n" +
                "           Left join tb_proposal_json json on json.proposal_id = tpd.id\n" +
                "           left join tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "  WHERE tfc.INSTITUTION = :institution \n" +
                "       AND tfc.SERVICE_CONTRACT = :serviceContract \n" +
                "       AND tpd.INSERTION_DATE BETWEEN :initialDate AND :finalDate \n" +
                "       AND (" +
                "               (" +
                "                   (:notIn = 0) " +
                "                       AND (" +
                "                           COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type IN (:responsesTypes)" +
                "                       )" +
                "               )" +
                "           OR" +
                "               (" +
                "                   (:notIn = 1) " +
                "                       AND (" +
                "                           COALESCE(:responsesTypes, NULL) IS NOT NULL AND tpd.response_type NOT IN (:responsesTypes)" +
                "                       )" +
                "               )" +
                "           OR" +
                "               (" +
                "                   (:notIn = 0) AND COALESCE(:responsesTypes, NULL) IS NULL " +
                "               )" +
                "       )" +
                "       AND (COALESCE(:status, NULL) IS NULL OR tpps.CODE in (:status))" +
                "   group by tfc.file_name,tfc.INSTITUTION,tfc.SERVICE_CONTRACT,tfc.READ_DATE,tfc.IS_VALID, TFC.id", resultSetMapping = "quantitativeReportMapping"),
        @NamedNativeQuery(name = "getCountQuantitativeReport", query =
                "SELECT " +
                        "        COUNT(*) " +
                        "FROM " +
                        "        ( " +
                        "                SELECT " +
                        "                   tfc.file_name,tfc.INSTITUTION,tfc.SERVICE_CONTRACT,tfc.READ_DATE,tfc.IS_VALID, TFC.id " +
                        "                FROM tb_proposal_data tpd " +
                        "                   LEFT JOIN TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA " +
                        "                   LEFT JOIN TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta " +
                        "                   LEFT JOIN TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control " +
                        "                   LEFT JOIN tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID " +
                        "                   LEFT JOIN tb_pre_proposal_status tpps on tpps.id = tpd.status_id " +
                        "                   LEFT JOIN tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id " +
                        "               WHERE " +
                        "                       tfc.INSTITUTION = :institution \n" +
                        "                   AND tfc.SERVICE_CONTRACT = :serviceContract \n" +
                        "                   AND tpd.INSERTION_DATE BETWEEN :initialDate AND :finalDate \n" +
                        "                   AND (" +
                        "                           (" +
                        "                               (:notIn = 0) " +
                        "                                   AND (" +
                        "                                       COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type IN (:responsesTypes)" +
                        "                                   )" +
                        "                           )" +
                        "                       OR" +
                        "                           (" +
                        "                               (:notIn = 1) " +
                        "                                   AND (" +
                        "                                       COALESCE(:responsesTypes, NULL) IS NULL OR tpd.response_type NOT IN (:responsesTypes)" +
                        "                                   )" +
                        "                           )" +
                        "                   )" +
                        "                   AND (COALESCE(:status, NULL) IS NULL OR tpps.CODE in (:status))" +
                        "               GROUP BY tfc.file_name,tfc.INSTITUTION,tfc.SERVICE_CONTRACT,tfc.READ_DATE,tfc.IS_VALID, TFC.id " +
                        "        )"
        ),
        @NamedNativeQuery(name = "getCompleteReport", query = "SELECT  " +
                "       TB_PROPOSAL_DATA.PROPOSAL_NUMBER AS \"PREPROPOSALID\"," +
                "       TB_PROPOSAL_DATA.id AS \"PROPOSALNUMBER\"," +
                "       TB_PROPOSAL_DATA.merchant_id AS \"MERCHANT\"," +
                "       TB_PROPOSAL_DATA.AGENT_CHANNEL AS \"USERID\", " +
                "       TB_PROPOSAL_DATA.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\"," +
                "       tfc.institution AS \"INSTITUTION\"," +
                "       tfc.service_contract AS \"SERVICECONTRACT\"," +
                "       TB_PROPOSAL_DATA.optin AS \"OPTIN\"," +
                "       TB_PROPOSAL_DATA.seller_registration AS \"SELLERREGISTRATION\"," +
                "       TB_PROPOSAL_DATA.SUB_CHANNEL AS \"SUBCHANNEL\", " +
                "       tcs.service_id || '-' || TCS.TECHNOLOGY AS \"TECHNOLOGY\"," +
                "       tcs.TERMINALS_NUMBER AS \"TERMINALSNUMBER\", " +
                "       tcs.VALUE AS \"UNITARYVALUE\", " +
                "       tpps.CODE || '-' || tpps.PT_DESCRIPTION AS \"FISERVSTATUS\"," +
                "       tpcs.code || '-' || tpcs.PT_DESCRIPTION AS \"CAIXASTATUS\"," +
                "       tpcs.message_code || '-' || tpcs.message_description AS \"CAIXAMESSAGE\",         " +
//                "       (" +
//                "           SELECT LISTAGG" +
//                "               (" +
//                "                   (" +
//                "                       CASE" +
//                "                           WHEN TB_PRE_PROPOSAL_HISTORY_ERROR.FIELD IS NULL THEN " +
//                "                               TB_PRE_PROPOSAL_HISTORY.STATUS || ': ' ||  TB_PRE_PROPOSAL_HISTORY_ERROR.MESSAGE" +
//                "                           ELSE" +
//                "                               TB_PRE_PROPOSAL_HISTORY.STATUS || '(' || TB_PRE_PROPOSAL_HISTORY_ERROR.FIELD || '): ' ||  TB_PRE_PROPOSAL_HISTORY_ERROR.MESSAGE " +
//                "                       END" +
//                "                   ), '; '" +
//                "               ) WITHIN GROUP (ORDER BY TB_PRE_PROPOSAL_HISTORY.STATUS) \"ERRORS\"" +
//                "           FROM TB_PRE_PROPOSAL_HISTORY" +
//                "               LEFT OUTER JOIN TB_PRE_PROPOSAL_HISTORY_ERROR TB_PRE_PROPOSAL_HISTORY_ERROR on TB_PRE_PROPOSAL_HISTORY_ERROR.ID_PROPOSAL_HISTORY = TB_PRE_PROPOSAL_HISTORY.id" +
//                "           WHERE TB_PRE_PROPOSAL_HISTORY.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.id AND TB_PRE_PROPOSAL_HISTORY.STATUS LIKE '%_ERROR'" +
//                "       ) AS \"ERRORS\"," +
                "       TO_CHAR(TB_PROPOSAL_DATA.INSERTION_DATE, 'dd/MM/yyyy hh:mm')  AS \"INCLUDEIN\"," +
                "       TO_CHAR(TB_PROPOSAL_DATA.CONCLUSION_DATE, 'dd/MM/yyyy hh:mm')  AS \"FINISHEDIN\"," +
                "       TO_CHAR(TB_PROPOSAL_DATA.ONLINE_DATE, 'dd/MM/yyyy hh:mm') AS \"SUBMISSIONONLINEDATE\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN 'Fisica'" +
                "       ELSE 'Juridica' END AS \"PERSONTYPE\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.CPF" +
                "       ELSE tpplp.CNPJ END AS \"CPFCNPJ\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.FANTASY_NAME" +
                "       ELSE tpplp.FANTASY_NAME END AS \"FANTASYNAME\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.NAME || ' ' || tppp.SURNAME" +
                "       ELSE tpplp.SOCIAL_REASON END AS \"SOCIALREASON\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.PLATE_NAME" +
                "       ELSE tpplp.PLATE_NAME END AS \"PLATELETNAME\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.MONTHLY_INCOME" +
                "       ELSE tpplp.MONTH_AVAREGE END AS \"MONTHLYBILLING\"," +
                "       CASE TB_PROPOSAL_DATA.proposal_type WHEN 'F' THEN tppp.BIRTH_DATE" +
                "       ELSE tpplp.DATE_CONSTITUTION END AS \"BIRTHDATE\"," +
                "       tppp.GENDER AS \"GENDER\"," +
                "       tppp.TREATMENT_PRONOUN AS \"TREATMENTPRONOUN\"," +
                "       tppp.LOCAL_BIRTH AS \"BIRTHPLACE\"," +
                "       tppp.NATIONALITY AS \"NACIONALITY\"," +
                "       tppp.name || ' ' || tppp.surname AS \"FULLNAME\"," +
                "       tpplp.CITY_INCRIPTION AS \"MUNICIPALREGISTRATION\"," +
                "       tpplp.STATE_INSCRIPTION AS \"STATEREGISTRATION\"," +
                "       'Sociedade Empresária' AS \"CONSTITUTIONFORM\"," +
                "       tpplp.OPEN_DATE AS \"OPENDATE\"," +
                "       TB_PROPOSAL_DATA.ANNUAL_BILLING_VOL, " +
                "       TB_PROPOSAL_DATA.ANNUAL_VOL_CASH, " +
                "       TB_PROPOSAL_DATA.ANNUAL_VOL_SALES_CARD, " +
                "       TB_PROPOSAL_DATA.ANNUAL_VOL_SALES_CARD_GROUP, " +
                "       TB_PROPOSAL_DATA.AVERAGE_TICKET, " +
                "       CASE TB_PROPOSAL_DATA.BACEN_PERMISSION WHEN 'true' THEN 'SIM'" +
                "       ELSE 'NÃO' END AS \"BACENPERMISSION\"," +
                "       TB_PROPOSAL_DATA.CAMPAING_ID AS \"CAMPAINGID\", " +
                "       TB_PROPOSAL_DATA.CNAE, " +
                "       TB_PROPOSAL_DATA.ECOMMERCE, " +
                "       CASE TB_PROPOSAL_DATA.FOREING_CARD WHEN 'true' THEN 'SIM'" +
                "       ELSE 'NÃO' END AS \"FOREIGNCARD\"," +
                "       CASE TB_PROPOSAL_DATA.MANUAL_PREPAYMENT_ENABLED WHEN 'true' THEN 'SIM'" +
                "       ELSE 'NÃO' END AS \"MANUAL_PREPAYMENT_ENABLED\",        " +
                "       TB_PROPOSAL_DATA.BOARDING_BRANCHING, " +
                "       TB_PROPOSAL_DATA.PERC_CARD_NOT_PRESENT , " +
                "       TB_PROPOSAL_DATA.PERC_CARD_PRESENT ," +
                "       TB_PROPOSAL_DATA.PERC_INTERNET , " +
                "       CASE TB_PROPOSAL_DATA.PREPAYMENT_INDICATOR WHEN 'true' THEN 'SIM'" +
                "       ELSE 'NÃO' END AS \"PREPAYMENT_INDICATOR\",  " +
                "       CASE TB_PROPOSAL_DATA.RECURRING_TRANSACTION WHEN 'true' THEN 'SIM'" +
                "       ELSE 'NÃO' END AS \"RECURRINGTRANSACTION\", " +
                "       TB_PROPOSAL_DATA.SERVICE_DAY_0, " +
                "       TB_PROPOSAL_DATA.SERVICE_DAY_15_30," +
                "       TB_PROPOSAL_DATA.SERVICE_DAY_1_7, " +
                "       TB_PROPOSAL_DATA.SERVICE_DAY_8_14, " +
                "       TB_PROPOSAL_DATA.SERVICE_DAY_OVER_30, " +
                "       TB_PROPOSAL_DATA.BW_DATE AS \"PENDINGBWDATE\", " +
                "       TB_PROPOSAL_DATA.BW_CONCLUSION_DATE AS \"INSTALLATIONDATE\"," +
                "       CASE TPPA.ADDRESS_TYPE WHEN 'BUSINESS' THEN 'Comercial'" +
                "       WHEN 'TRADING' THEN 'Entrega'" +
                "       ELSE 'Contato' END AS \"ADDRESSTYPE\"," +
                "       TPPA.ZIP_CODE AS \"CEP\"," +
                "       TPPA.STREET AS \"STREET\"," +
                "       TPPA.ADDRESS_NUMBER AS \"NUMBER\"," +
                "       TPPA.NEIGHBORHOOD AS \"DISTRICT\"," +
                "       TPPA.COMPLEMENT AS \"COMPLEMENT\"," +
                "       TPPA.CITY AS \"CITY\"," +
                "       TPPA.STATE AS \"STATE\"," +
                "       TPCP.TYPE AS \"PARTNERTYPE\" ," +
                "       TPCP.CNPJ_CPF AS \"CPFCNPJPARTNER\" ," +
                "       TPCP.BIRTH_DATE AS \"PARTNERBIRTHDATE\" ," +
                "       TPCP.CONSTITUTION_TYPE AS \"CONSTITUTIONTYPE\" ," +
                "       TPCP.DDD || TPCP.PHONE As \"CONTRACT\"," +
                "       TPCP.FIRST_NAME || ' ' || TPCP.LAST_NAME AS \"PARTNERNAME\"," +
                "       TPCP.TREATMENT_PRONOUN AS \"PRONOUNTREATMENTPARTNER\"," +
                "       TPCP.NATIONALITY AS \"PARTNERNACIONALITY\"," +
                "       TPCP.FUNCTION AS \"PARTNERFUNCTION\", " +
                "       TPCP.perc_part_soc AS \"PERCPARTNER\"," +
                "       tc.BIRTH_DATE AS \"CONTACTBIRTHDATE\", " +
                "       tc.CPF AS \"CONTACTCPF\", " +
                "       tc.EMAIL AS \"CONTACTEMAIL\", " +
                "       tc.FIRST_NAME || ' ' || tc.LAST_NAME AS \"CONTACTNAME\", " +
                "       tc.FIXED_DDD || tc.FIXED_NUMBER AS \"CONTACTPHONE\", " +
                "       tc.MOBILE_DDD || tc.MOBILE_NUMBER AS \"CONTACTCELLPHONE\"," +
                "       taf.DESCRIPTION, " +
                "       taf.DISCOUNT_FEE, " +
                "       taf.FEE, " +
                "       taf.FEE_ID_NUMBER," +
                "       tba.BANK_NUMBER AS \"BANKCODE\"," +
                "       tba.AGENCY_NUMBER || tba.AGENCY_DIGIT AS \"AGENCY\"," +
                "       tba.ACCOUNT_DIGIT AS \"ACCOUNTDIGIT\", " +
                "       tba.ACCOUNT_NUMBER AS \"ACCOUNTNUMBER\", " +
                "       tba.ACCOUNT_OWNER_NAME AS \"ACCOUNTOWNER\", " +
                "       CASE tba.ACCOUNT_TYPE WHEN '0' THEN 'Conta corrente'" +
                "       ELSE 'Poupança' END AS \"ACCOUNTTYPE\"," +
                "       CASE tpwa.WEEK_DAY " +
                "        WHEN 'MONDAY' THEN 'Segunda das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "        WHEN 'Tuesday' THEN ' Terça-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "        WHEN 'Wednesday' THEN ' Quarta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "        WHEN 'Thursday' THEN ' Quinta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "        WHEN 'Friday' THEN ' Sexta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "        WHEN 'Saturday' THEN 'Sábado das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO" +
                "       ELSE 'Domingo'|| tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO END AS \"WORKDAYS\"," +
                "       'Comercial' AS \"TYPEOFESTABLISHMENT\"," +
                "       'Sim' AS \"ACCEPTTERM\"," +
                "       TB_PROPOSAL_DATA.RESPONSE_TYPE AS \"RESPONSETYPE\"" +
                "       FROM TB_PROPOSAL_DATA" +
                "  LEFT join TB_PROPOSAL_PHYSICAL_PERSON tppp on TB_PROPOSAL_DATA.proposal_type = 'F' and TB_PROPOSAL_DATA.id = tppp.ID_FILE_PROPOSAL_DTA" +
                "  LEFT join TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on TB_PROPOSAL_DATA.proposal_type = 'J' and TB_PROPOSAL_DATA.id = tpplp.id_file_proposal_dta" +
                "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = TB_PROPOSAL_DATA.id_file_control" +
                "  LEFT join tb_capture_solution TCS ON tcs.id_proposal_data = TB_PROPOSAL_DATA.ID" +
                "  LEFT join tb_pre_proposal_status tpps on tpps.id = TB_PROPOSAL_DATA.status_id" +
                "  LEFT join tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id" +
                "  LEFT JOIN TB_PROPOSAL_COMPANY_PARTNERS TPCP ON TPCP.ID_PROPOSAL_PJ_DTA = tpplp.id" +
                "  LEFT JOIN TB_PRE_PROPOSAL_ADDRESS TPPA ON TPPA.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_CONTACT tc on tc.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_ACCOUNT_FREE taf on taf.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_BANK_ACCOUNT tba on tba.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  left join TB_PRE_WORKING_DAY tpwa on tpwa.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
                "  WHERE tfc.INSTITUTION = :institution " +
                "       AND tfc.SERVICE_CONTRACT = :serviceContract "
//             +   "       AND TB_PROPOSAL_DATA.INSERTION_DATE BETWEEN :initialDate AND :finalDate " +
//                "       AND (" +
//                "               (" +
//                "                   (:notIn = 0) " +
//                "                       AND (" +
//                "                           COALESCE(:responsesTypes, NULL) IS NULL OR TB_PROPOSAL_DATA.response_type IN (:responsesTypes)" +
//                "                       )" +
//                "               )" +
//                "           OR" +
//                "               (" +
//                "                   (:notIn = 1) " +
//                "                       AND (" +
//                "                           COALESCE(:responsesTypes, NULL) IS NULL OR TB_PROPOSAL_DATA.response_type NOT IN (:responsesTypes)" +
//                "                       )" +
//                "               )" +
//                "       )" +
//                "       AND (COALESCE(:status, NULL) IS NULL OR tpps.CODE in (:status))"
                , resultSetMapping = "completeReportMapping"),/*
        @NamedNativeQuery(name = "getCompleteReport.count", query = "SELECT COUNT(TB_PROPOSAL_DATA.id) " +
                "       from TB_PROPOSAL_DATA " +
                "  LEFT join TB_PROPOSAL_PHYSICAL_PERSON tppp on TB_PROPOSAL_DATA.proposal_type = 'F' and TB_PROPOSAL_DATA.id = tppp.ID_FILE_PROPOSAL_DTA" +
                "  LEFT join TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on TB_PROPOSAL_DATA.proposal_type = 'J' and TB_PROPOSAL_DATA.id = tpplp.id_file_proposal_dta" +
                "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = TB_PROPOSAL_DATA.id_file_control" +
                "  LEFT join tb_capture_solution TCS ON tcs.id_proposal_data = TB_PROPOSAL_DATA.ID" +
                "  LEFT join tb_pre_proposal_status tpps on tpps.id = TB_PROPOSAL_DATA.status_id" +
                "  LEFT join tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id" +
                "  LEFT JOIN TB_PROPOSAL_COMPANY_PARTNERS TPCP ON TPCP.ID_PROPOSAL_PJ_DTA = tpplp.id" +
                "  LEFT JOIN TB_PRE_PROPOSAL_ADDRESS TPPA ON TPPA.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_CONTACT tc on tc.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_ACCOUNT_FREE taf on taf.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  LEFT JOIN TB_BANK_ACCOUNT tba on tba.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
                "  left join TB_PRE_WORKING_DAY tpwa on tpwa.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
                "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ")*/
})
//@NamedNativeQuery(name = "getCompleteReport.count", query = "SELECT COUNT(TB_PROPOSAL_DATA.id) " +
//        "       from TB_PROPOSAL_DATA " +
//        "  LEFT join TB_PROPOSAL_PHYSICAL_PERSON tppp on TB_PROPOSAL_DATA.proposal_type = 'F' and TB_PROPOSAL_DATA.id = tppp.ID_FILE_PROPOSAL_DTA" +
//        "  LEFT join TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on TB_PROPOSAL_DATA.proposal_type = 'J' and TB_PROPOSAL_DATA.id = tpplp.id_file_proposal_dta" +
//        "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = TB_PROPOSAL_DATA.id_file_control" +
//        "  LEFT join tb_capture_solution TCS ON tcs.id_proposal_data = TB_PROPOSAL_DATA.ID" +
//        "  LEFT join tb_pre_proposal_status tpps on tpps.id = TB_PROPOSAL_DATA.status_id" +
//        "  LEFT join tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id" +
//        "  LEFT JOIN TB_PROPOSAL_COMPANY_PARTNERS TPCP ON TPCP.ID_PROPOSAL_PJ_DTA = tpplp.id" +
//        "  LEFT JOIN TB_PRE_PROPOSAL_ADDRESS TPPA ON TPPA.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
//        "  LEFT JOIN TB_CONTACT tc on tc.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
//        "  LEFT JOIN TB_ACCOUNT_FREE taf on taf.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
//        "  LEFT JOIN TB_BANK_ACCOUNT tba on tba.ID_FILE_PROPOSAL_DTA = TB_PROPOSAL_DATA.ID" +
//        "  left join TB_PRE_WORKING_DAY tpwa on tpwa.ID_PROPOSAL_DATA = TB_PROPOSAL_DATA.ID" +
//        "  WHERE tfc.INSTITUTION = :institution AND tfc.SERVICE_CONTRACT = :serviceContract ")
public class EProposalData {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "RESPONSE_TYPE")
    private String responseType;

    @Column(name = "ID_ORIGIN")
    private Long idOrigin;

}
