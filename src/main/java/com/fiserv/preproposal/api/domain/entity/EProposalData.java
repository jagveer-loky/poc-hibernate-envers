package com.fiserv.preproposal.api.domain.entity;

import com.fiserv.preproposal.api.domain.dtos.DReport1;
import com.fiserv.preproposal.api.domain.dtos.DReport2;
import com.fiserv.preproposal.api.domain.dtos.DReport3;
import com.fiserv.preproposal.api.domain.dtos.DReport4;
import com.fiserv.preproposal.api.domain.dtos.DReport5;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;



@Entity
@Table(name = "TB_PROPOSAL_DATA")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SqlResultSetMappings(value = {
        @SqlResultSetMapping(
                name="report1Mapping",
                classes={
                        @ConstructorResult(
                                targetClass= DReport1.class,
                                columns={
                                        @ColumnResult(name="CPFCNPJ", type = String.class),
                                        @ColumnResult(name="FANTASYNAME", type = String.class),
                                        @ColumnResult(name="SOCIALREASON", type = String.class),
                                        @ColumnResult(name="VOUCHERNAME", type = String.class),
                                        @ColumnResult(name="USERID", type = String.class),
                                        @ColumnResult(name="AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name="INSTITUTION", type = String.class),
                                        @ColumnResult(name="SERVICECONTRACT", type = String.class),
                                        @ColumnResult(name="SUBCHANNEL", type = String.class),
                                        @ColumnResult(name="TECNOLOGY", type = String.class),
                                        @ColumnResult(name="INCLUDEDATE", type = String.class),
                                        @ColumnResult(name="CONCLUSIONDATE", type = String.class),
                                        @ColumnResult(name="PROPOSALNUMBER", type = String.class),
                                        @ColumnResult(name="FISERVSTATUS", type = String.class),
                                        @ColumnResult(name="CEFSTATUS", type = String.class),
                                        @ColumnResult(name="CEFMESSAGE", type = String.class),
                                        @ColumnResult(name="SUBMITIONDATE", type = String.class)

                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name="report2Mapping",
                classes={
                        @ConstructorResult(
                                targetClass= DReport2.class,
                                columns={
                                        @ColumnResult(name="FILENAME", type = String.class),
                                        @ColumnResult(name="INSTITUTION", type = String.class),
                                        @ColumnResult(name="PROCESSINGDATE", type = String.class),
                                        @ColumnResult(name="FILESTATUS", type = String.class),
                                        @ColumnResult(name="NUMPROPOSALS", type = Long.class),
                                        @ColumnResult(name="NUMONLINEREJECT", type = Long.class),
                                        @ColumnResult(name="NUMPENDINGINONLINE", type = Long.class),
                                        @ColumnResult(name="NUMPENDINGCOMPLEMENT", type = Long.class),
                                        @ColumnResult(name="NUMERRORPREPROPOSAL", type = Long.class),
                                        @ColumnResult(name="NUMFINISHED", type = Long.class),
                                        @ColumnResult(name="NUMTMPCANCELED", type = Long.class),
                                        @ColumnResult(name="NUMCREDONLINE", type = Long.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name="report3Mapping",
                classes={
                        @ConstructorResult(
                                targetClass= DReport3.class,
                                columns={
                                        @ColumnResult(name="FILENAME", type = String.class),
                                        @ColumnResult(name="INSTITUTION", type = String.class),
                                        @ColumnResult(name="SERVICECONTRACT", type = Integer.class),
                                        @ColumnResult(name="USERID", type = String.class),
                                        @ColumnResult(name="AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name="PREPROPOSALID", type = Long.class),
                                        @ColumnResult(name="PROPOSALNUMBER", type = String.class),
                                        @ColumnResult(name="MERCHANT", type = String.class),
                                        @ColumnResult(name="PROCESSINGDATE", type = String.class),
                                        @ColumnResult(name="ERROR", type = String.class),
                                        @ColumnResult(name="ERRORDESCRIPTION", type = String.class),
                                        @ColumnResult(name="ERRORMESSAGE", type = String.class),
                                        @ColumnResult(name="DETAIL", type = String.class)
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name="report4Mapping",
                classes={
                        @ConstructorResult(
                                targetClass= DReport4.class,
                                columns={
                                        @ColumnResult(name="PREPROPOSALID", type = Long.class),
                                        @ColumnResult(name="PROPOSALNUMBER", type = String.class),
                                        @ColumnResult(name="MERCHANT", type = String.class),
                                        @ColumnResult(name="USERID", type = String.class),
                                        @ColumnResult(name="AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name="INSTITUTION", type = String.class),
                                        @ColumnResult(name="SERVICECONTRACT", type = Integer.class),
                                        @ColumnResult(name="SUBCHANNEL", type = String.class),
                                        @ColumnResult(name="TECNOLOGY", type = String.class),
                                        @ColumnResult(name="FISERVSTATUS", type = String.class),
                                        @ColumnResult(name="CAIXASTATUS", type = String.class),
                                        @ColumnResult(name="CAIXAMESSAGE", type = String.class),
                                        @ColumnResult(name="INCLUSIONDATE", type = String.class),
                                        @ColumnResult(name="CONCLUSIONDATE1", type = String.class),
                                        @ColumnResult(name="ONLINESUBMITIONDATE", type = String.class),
                                        @ColumnResult(name="PERSONTYPE", type = String.class),
                                        @ColumnResult(name="CPFCNPJ", type = String.class),
                                        @ColumnResult(name="FANTASYNAME", type = String.class),
                                        @ColumnResult(name="SOCIALREASON", type = String.class),
                                        @ColumnResult(name="VOUNCHERNAME", type = String.class),
                                        @ColumnResult(name="MONTHLYBILLING", type = String.class),
                                        @ColumnResult(name="BIRTHDATE", type = String.class),
                                        @ColumnResult(name="GNERE", type = String.class),
                                        @ColumnResult(name="TREATMENTPRONOUN", type = String.class),
                                        @ColumnResult(name="BIRTHPLACE", type = String.class),
                                        @ColumnResult(name="NACIONALITY", type = String.class),
                                        @ColumnResult(name="FULLNAME", type = String.class),
                                        @ColumnResult(name="MUNICIPALREGISTRATION", type = String.class),
                                        @ColumnResult(name="STATEREGISTRATION", type = String.class),
                                        @ColumnResult(name="CONSTITUTIONFORM", type = String.class),
                                        @ColumnResult(name="OPENDATE", type = String.class),
                                        @ColumnResult(name="ANNUAL_BILLING_VOL", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_CASH", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_SALES_CARD", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_SALES_CARD_GROUP", type = String.class),
                                        @ColumnResult(name="AVERAGE_TICKET", type = String.class),
                                        @ColumnResult(name="BACENPERMISSION", type = String.class),
                                        @ColumnResult(name="CAMPAINGCODE", type = String.class),
                                        @ColumnResult(name="CNAE", type = String.class),
                                        @ColumnResult(name="ECOMMERCE", type = String.class),
                                        @ColumnResult(name="FOREIGNCARD", type = String.class),
                                        @ColumnResult(name="MANUAL_PREPAYMENT_ENABLED", type = String.class),
                                        @ColumnResult(name="BOARDING_BRANCHING", type = String.class),
                                        @ColumnResult(name="PERC_CARD_NOT_PRESENT", type = String.class),
                                        @ColumnResult(name="PERC_CARD_PRESENT", type = String.class),
                                        @ColumnResult(name="PERC_INTERNET", type = String.class),
                                        @ColumnResult(name="PREPAYMENT_INDICATOR", type = String.class),
                                        @ColumnResult(name="RECURRINGTRANSACTION", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_0", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_15_30", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_1_7", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_8_14", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_OVER_30", type = String.class),
                                        @ColumnResult(name="PENDINGBWDATE", type = String.class),
                                        @ColumnResult(name="CONCLUSIONDATE", type = String.class),
                                }
                        )
                }
        ),
        @SqlResultSetMapping(
                name="report5Mapping",
                classes={
                        @ConstructorResult(
                                targetClass= DReport5.class,
                                columns={
                                        @ColumnResult(name="PREPROPOSALID", type = Long.class),
                                        @ColumnResult(name="PROPOSALNUMBER", type = String.class),
                                        @ColumnResult(name="MERCHANT", type = String.class),
                                        @ColumnResult(name="USERID", type = String.class),
                                        @ColumnResult(name="AGENTCPFCNPJ", type = String.class),
                                        @ColumnResult(name="INSTITUTION", type = String.class),
                                        @ColumnResult(name="SERVICECONTRACT", type = Integer.class),
                                        @ColumnResult(name="SUBCHANNEL", type = String.class),
                                        @ColumnResult(name="TECHNOLOGY", type = String.class),
                                        @ColumnResult(name="TERMINALSNUMBER", type = Long.class),
                                        @ColumnResult(name="UNITARYVALUE", type = String.class),
                                        @ColumnResult(name="FISERVSTATUS", type = String.class),
                                        @ColumnResult(name="CAIXASTATUS", type = String.class),
                                        @ColumnResult(name="CAIXAMESSAGE", type = String.class),
                                        @ColumnResult(name="INCLUDEIN", type = String.class),
                                        @ColumnResult(name="FINISHEDIN", type = String.class),
                                        @ColumnResult(name="SUBMISSIONONLINEDATE", type = String.class),
                                        @ColumnResult(name="PERSONTYPE", type = String.class),
                                        @ColumnResult(name="CPFCNPJ", type = String.class),
                                        @ColumnResult(name="FANTASYNAME", type = String.class),
                                        @ColumnResult(name="SOCIALREASON", type = String.class),
                                        @ColumnResult(name="PLATELETNAME", type = String.class),
                                        @ColumnResult(name="MONTHLYBILLING", type = String.class),
                                        @ColumnResult(name="BIRTHDATE", type = String.class),
                                        @ColumnResult(name="GNERE", type = String.class),
                                        @ColumnResult(name="TREATMENTPRONOUN", type = String.class),
                                        @ColumnResult(name="BIRTHPLACE", type = String.class),
                                        @ColumnResult(name="NACIONALITY", type = String.class),
                                        @ColumnResult(name="FULLNAME", type = String.class),
                                        @ColumnResult(name="MUNICIPALREGISTRATION", type = String.class),
                                        @ColumnResult(name="STATEREGISTRATION", type = String.class),
                                        @ColumnResult(name="CONSTITUTIONFORM", type = String.class),
                                        @ColumnResult(name="OPENDATE", type = String.class),
                                        @ColumnResult(name="ANNUAL_BILLING_VOL", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_CASH", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_SALES_CARD", type = String.class),
                                        @ColumnResult(name="ANNUAL_VOL_SALES_CARD_GROUP", type = String.class),
                                        @ColumnResult(name="AVERAGE_TICKET", type = String.class),
                                        @ColumnResult(name="BACENPERMISSION", type = String.class),
                                        @ColumnResult(name="CAMPAINGID", type = String.class),
                                        @ColumnResult(name="CNAE", type = String.class),
                                        @ColumnResult(name="ECOMMERCE", type = String.class),
                                        @ColumnResult(name="FOREIGNCARD", type = String.class),
                                        @ColumnResult(name="MANUAL_PREPAYMENT_ENABLED", type = String.class),
                                        @ColumnResult(name="BOARDING_BRANCHING", type = String.class),
                                        @ColumnResult(name="PERC_CARD_NOT_PRESENT", type = String.class),
                                        @ColumnResult(name="PERC_CARD_PRESENT", type = String.class),
                                        @ColumnResult(name="PERC_INTERNET", type = String.class),
                                        @ColumnResult(name="PREPAYMENT_INDICATOR", type = String.class),
                                        @ColumnResult(name="RECURRINGTRANSACTION", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_0", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_15_30", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_1_7", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_8_14", type = String.class),
                                        @ColumnResult(name="SERVICE_DAY_OVER_30", type = String.class),
                                        @ColumnResult(name="PENDINGBWDATE", type = String.class),
                                        @ColumnResult(name="INSTALLATIONDATE", type = String.class),
                                        @ColumnResult(name="ADDRESSTYPE", type = String.class),
                                        @ColumnResult(name="CEP", type = String.class),
                                        @ColumnResult(name="STREET", type = String.class),
                                        @ColumnResult(name="NUMBER", type = String.class),
                                        @ColumnResult(name="DISTRICT", type = String.class),
                                        @ColumnResult(name="COMPLEMENT", type = String.class),
                                        @ColumnResult(name="CITY", type = String.class),
                                        @ColumnResult(name="STATE", type = String.class),
                                        @ColumnResult(name="PARTNERTYPE", type = String.class),
                                        @ColumnResult(name="CPFCNPJPARTNER", type = String.class),
                                        @ColumnResult(name="PARTNERBIRTHDATE", type = String.class),
                                        @ColumnResult(name="CONSTITUTIONTYPE", type = String.class),
                                        @ColumnResult(name="CONTRACT", type = String.class),
                                        @ColumnResult(name="PARTNERNAME", type = String.class),
                                        @ColumnResult(name="PRONOUNTREATMENTPARTNER", type = String.class),
                                        @ColumnResult(name="PARTNERNACIONALITY", type = String.class),
                                        @ColumnResult(name="PARTNERFUNCTION", type = String.class),
                                        @ColumnResult(name="PERCPARTNER", type = String.class),
                                        @ColumnResult(name="CONTACTBIRTHDATE", type = String.class),
                                        @ColumnResult(name="CONTACTCPF", type = String.class),
                                        @ColumnResult(name="CONTACTEMAIL", type = String.class),
                                        @ColumnResult(name="CONTACTNAME", type = String.class),
                                        @ColumnResult(name="CONTACTPHONE", type = String.class),
                                        @ColumnResult(name="CONTACTCELLPHONE", type = String.class),
                                        @ColumnResult(name="DESCRIPTION", type = String.class),
                                        @ColumnResult(name="DISCOUNT_FEE", type = String.class),
                                        @ColumnResult(name="FEE", type = String.class),
                                        @ColumnResult(name="FEE_ID_NUMBER", type = String.class),
                                        @ColumnResult(name="BANKCODE", type = String.class),
                                        @ColumnResult(name="AGENCY", type = String.class),
                                        @ColumnResult(name="ACCOUNTDIGIT", type = String.class),
                                        @ColumnResult(name="ACCOUNTNUMBER", type = String.class),
                                        @ColumnResult(name="ACCOUNTOWNER", type = String.class),
                                        @ColumnResult(name="ACCOUNTTYPE", type = String.class),
                                        @ColumnResult(name="WORKDAYS", type = String.class)
                                }
                        )
                }
        )

})
@NamedNativeQueries(value = {
        @NamedNativeQuery(name = "getReport1", query = "SELECT\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.CPF\n" +
                "       ELSE tpplp.CNPJ END AS \"CPFCNPJ\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.FANTASY_NAME\n" +
                "       ELSE tpplp.FANTASY_NAME END AS \"FANTASYNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.NAME || ' ' || tppp.SURNAME\n" +
                "       ELSE tpplp.SOCIAL_REASON END AS \"SOCIALREASON\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.PLATE_NAME\n" +
                "       ELSE tpplp.PLATE_NAME END AS \"VOUCHERNAME\",\n" +
                "       tpd.AGENT_CHANNEL AS \"USERID\",\n" +
                "       tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\",\n" +
                "       tfc.institution AS \"INSTITUTION\",\n" +
                "       tfc.service_contract AS \"SERVICECONTRACT\",\n" +
                "       tpd.SUB_CHANNEL AS \"SUBCHANNEL\", \n" +
                "       tcs.service_id || '-' || TCS.TECHNOLOGY AS \"TECNOLOGY\",\n" +
                "       TO_CHAR(tpd.INSERTION_DATE, 'dd/MM/yyyy hh:mm')  AS \"INCLUDEDATE\",\n" +
                "       TO_CHAR(tpd.CONCLUSION_DATE, 'dd/MM/yyyy hh:mm')  AS \"CONCLUSIONDATE\",\n" +
                "       tpd.proposal_number AS \"PROPOSALNUMBER\",\n" +
                "       tpd.merchant_id AS \"MERCHANT\",\n" +
                "       tpps.CODE || '-' || tpps.PT_DESCRIPTION AS \"FISERVSTATUS\",\n" +
                "       tpcs.code || '-' || tpcs.PT_DESCRIPTION AS \"CEFSTATUS\",\n" +
                "       tpcs.message_code || '-' || tpcs.message_description AS \"CEFMESSAGE\",\n" +
                "       TO_CHAR(tpd.ONLINE_DATE, 'dd/MM/yyyy hh:mm') AS \"SUBMITIONDATE\"\n" +
                "FROM tb_proposal_data tpd\n" +
                "\t   LEFT JOIN TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA\n" +
                "\t   LEFT JOIN TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta\n" +
                "       LEFT JOIN TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control\n" +
                "       LEFT JOIN tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID\n" +
                "       LEFT JOIN tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "       LEFT JOIN tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id\n" +
                "WHERE tfc.INSTITUTION = ?1 \n" +
                "       AND tfc.SERVICE_CONTRACT = ?2 \n" +
                "       AND tpd.INSERTION_DATE BETWEEN ?3 AND ?4 \n" +
                "       AND (?5 IS NULL OR tpps.CODE IN ?5)", resultSetMapping = "report1Mapping"),
        @NamedNativeQuery(name = "getReport2", query = "  SELECT  \n" +
                "            tfc.file_name AS \"FILENAME\",\n" +
                "            tfc.INSTITUTION AS \"INSTITUTION\", \n" +
                "            tfc.SERVICE_CONTRACT,\n" +
                "            TO_CHAR(tfc.READ_DATE, 'dd/MM/yyyy hh:mm')  AS \"PROCESSINGDATE\",\n" +
                "            CASE tfc.IS_VALID WHEN 0 THEN 'Não foi possivel ler o arquivo - Erro de formatação'\n" +
                "            ELSE 'Lido com sucesso' END AS \"FILESTATUS\",\n" +
                "            COUNT(tpd.id) AS \"NUMPROPOSALS\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('01','02','03','04','06','-1','14','15','16','17','20','23','24','25','27','34','37','500','768','880')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMONLINEREJECT\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('PRE4','11','12','13','18','21','22','33','38','30','05','07','08','09','19','888','870','765','860','077','260','769','270','31','32','26','28','35','36')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMPENDINGINONLINE\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('PRE1')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMPENDINGCOMPLEMENT\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('PRE2')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMERRORPREPROPOSAL\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('PRE3')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMFINISHED\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('MCT126','MCT128','MCT127','MCT131','MCT130','MCT129','MCT133','MCT134','MCT132')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMTMPCANCELED\",\n" +
                "            (SELECT COUNT(tpd2.id) \n" +
                "              from TB_FILE_CONTROL TFC2\n" +
                "              join tb_proposal_data tpd2 ON TFC2.ID = tpd2.id_file_control\n" +
                "              join tb_pre_proposal_status tpps2 on tpps2.id = tpd2.status_id\n" +
                "             Where tpps2.CODE in ('39','39','400','00','40','41')\n" +
                "             and TFC.id = TFC2.id\n" +
                "            ) AS \"NUMCREDONLINE\"\n" +
                "       from TB_FILE_CONTROL TFC\n" +
                "  left join tb_proposal_data tpd ON TFC.ID = tpd.id_file_control\n" +
                "  left join tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "  WHERE tfc.INSTITUTION = ?1 \n" +
                "       AND tfc.SERVICE_CONTRACT = ?2 \n" +
                "       AND tpd.INSERTION_DATE BETWEEN ?3 AND ?4 \n" +
                "       AND (?5 IS NULL OR tpps.CODE IN ?5) \n" +
                "   group by tfc.file_name,tfc.INSTITUTION,tfc.SERVICE_CONTRACT,tfc.READ_DATE,tfc.IS_VALID, TFC.id", resultSetMapping = "report2Mapping"),
        @NamedNativeQuery(name = "getReport3", query = "SELECT   \n" +
                "    tfc.file_name AS \"FILENAME\",\n" +
                "    tfc.INSTITUTION AS \"INSTITUTION\", \n" +
                "    tfc.SERVICE_CONTRACT AS \"SERVICECONTRACT\",\n" +
                "    tpd.AGENT_CHANNEL AS \"USERID\", \n" +
                "    tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\",\n" +
                "    tpd.id AS \"PREPROPOSALID\",\n" +
                "    tpd.proposal_number AS \"PROPOSALNUMBER\",\n" +
                "    tpd.merchant_id AS \"MERCHANT\",\n" +
                "    TO_CHAR(tfc.READ_DATE, 'dd/MM/yyyy hh:mm')  AS \"PROCESSINGDATE\",\n" +
                "    tpphe.field AS \"ERROR\",\n" +
                "    tpphe.field_description AS \"ERRORDESCRIPTION\",\n" +
                "    tpphe.message AS \"ERRORMESSAGE\",\n" +
                "    tpphe.message_detail AS \"DETAIL\"\n" +
                "from  tb_pre_proposal_history tpph\n" +
                "Left join TB_PRE_PROPOSAL_HISTORY_ERROR tpphe on tpph.id = tpphe.id_proposal_history\n" +
                "Left join tb_proposal_data tpd on tpd.id = tpph.id_proposal_data\n" +
                "Left join TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control\n" +
                "group by tfc.file_name,tfc.INSTITUTION,tfc.SERVICE_CONTRACT,tpd.AGENT_CHANNEL,tpd.AGENT_CPF_CNPJ,tpd.id,proposal_number,merchant_id,tfc.READ_DATE,tfc.IS_VALID,tpphe.field,tpphe.field_description,tpphe.message,tpphe.message_detail", resultSetMapping = "report3Mapping"),
        @NamedNativeQuery(name = "getReport4", query = "SELECT  \n" +
                "       tpd.id AS \"PREPROPOSALID\",\n" +
                "       tpd.proposal_number AS \"PROPOSALNUMBER\",\n" +
                "       tpd.merchant_id AS \"MERCHANT\",\n" +
                "       tpd.AGENT_CHANNEL AS \"USERID\", \n" +
                "       tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\",\n" +
                "       tfc.institution AS \"INSTITUTION\",\n" +
                "       tfc.service_contract AS \"SERVICECONTRACT\",\n" +
                "       tpd.SUB_CHANNEL AS \"SUBCHANNEL\", \n" +
                "       tcs.service_id || '-' || TCS.TECHNOLOGY AS \"TECNOLOGY\",\n" +
                "       tpps.CODE || '-' || tpps.PT_DESCRIPTION AS \"FISERVSTATUS\",\n" +
                "       tpcs.code || '-' || tpcs.PT_DESCRIPTION AS \"CAIXASTATUS\",\n" +
                "       tpcs.message_code || '-' || tpcs.message_description AS \"CAIXAMESSAGE\",         \n" +
                "       TO_CHAR(tpd.INSERTION_DATE, 'dd/MM/yyyy hh:mm')  AS \"INCLUSIONDATE\",\n" +
                "       TO_CHAR(tpd.CONCLUSION_DATE, 'dd/MM/yyyy hh:mm')  AS \"CONCLUSIONDATE1\",\n" +
                "       TO_CHAR(tpd.ONLINE_DATE, 'dd/MM/yyyy hh:mm') AS \"ONLINESUBMITIONDATE\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN 'Fisica'\n" +
                "       ELSE 'Juridica' END AS \"PERSONTYPE\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.CPF\n" +
                "       ELSE tpplp.CNPJ END AS \"CPFCNPJ\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.FANTASY_NAME\n" +
                "       ELSE tpplp.FANTASY_NAME END AS \"FANTASYNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.NAME || ' ' || tppp.SURNAME\n" +
                "       ELSE tpplp.SOCIAL_REASON END AS \"SOCIALREASON\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.PLATE_NAME\n" +
                "       ELSE tpplp.PLATE_NAME END AS \"VOUNCHERNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.MONTHLY_INCOME\n" +
                "       ELSE tpplp.MONTH_AVAREGE END AS \"MONTHLYBILLING\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.BIRTH_DATE\n" +
                "       ELSE tpplp.DATE_CONSTITUTION END AS \"BIRTHDATE\",\n" +
                "       tppp.GENDER AS \"GNERE\",\n" +
                "       tppp.TREATMENT_PRONOUN AS \"TREATMENTPRONOUN\",\n" +
                "       tppp.LOCAL_BIRTH AS \"BIRTHPLACE\",\n" +
                "       tppp.NATIONALITY AS \"NACIONALITY\",\n" +
                "       tppp.name || ' ' || tppp.surname AS \"FULLNAME\",\n" +
                "       tpplp.CITY_INCRIPTION AS \"MUNICIPALREGISTRATION\",\n" +
                "       tpplp.STATE_INSCRIPTION AS \"STATEREGISTRATION\",\n" +
                "       'Sociedade Empresária' AS \"CONSTITUTIONFORM\",\n" +
                "       tpplp.OPEN_DATE AS \"OPENDATE\",\n" +
                "       tpd.ANNUAL_BILLING_VOL, \n" +
                "       tpd.ANNUAL_VOL_CASH, \n" +
                "       tpd.ANNUAL_VOL_SALES_CARD, \n" +
                "       tpd.ANNUAL_VOL_SALES_CARD_GROUP, \n" +
                "       tpd.AVERAGE_TICKET,\n" +
                "       CASE tpd.BACEN_PERMISSION WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"BACENPERMISSION\",\n" +
                "       tpd.CAMPAING_ID AS \"CAMPAINGCODE\", \n" +
                "       tpd.CNAE, \n" +
                "       tpd.ECOMMERCE, \n" +
                "       CASE tpd.FOREING_CARD WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"FOREIGNCARD\",\n" +
                "       CASE tpd.MANUAL_PREPAYMENT_ENABLED WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"MANUAL_PREPAYMENT_ENABLED\",        \n" +
                "       tpd.BOARDING_BRANCHING, \n" +
                "       tpd.PERC_CARD_NOT_PRESENT , \n" +
                "       tpd.PERC_CARD_PRESENT ,\n" +
                "       tpd.PERC_INTERNET , \n" +
                "       CASE tpd.PREPAYMENT_INDICATOR WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"PREPAYMENT_INDICATOR\",  \n" +
                "       CASE tpd.RECURRING_TRANSACTION WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"RECURRINGTRANSACTION\", \n" +
                "       tpd.SERVICE_DAY_0, \n" +
                "       tpd.SERVICE_DAY_15_30,\n" +
                "       tpd.SERVICE_DAY_1_7, \n" +
                "       tpd.SERVICE_DAY_8_14, \n" +
                "       tpd.SERVICE_DAY_OVER_30, \n" +
                "       tpd.BW_DATE AS \"PENDINGBWDATE\", \n" +
                "       tpd.BW_CONCLUSION_DATE AS \"CONCLUSIONDATE\"\n" +
                "       from tb_proposal_data tpd\n" +
                "  LEFT join TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA\n" +
                "  LEFT join TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta\n" +
                "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control\n" +
                "  LEFT join tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID\n" +
                "  LEFT join tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "  LEFT join tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id", resultSetMapping = "report4Mapping"),
        @NamedNativeQuery(name = "getReport5",query = "SELECT  \n" +
                "       tpd.id AS \"PREPROPOSALID\",\n" +
                "      tpd.proposal_number AS \"PROPOSALNUMBER\",\n" +
                "       tpd.merchant_id AS \"MERCHANT\",\n" +
                "       tpd.AGENT_CHANNEL AS \"USERID\", \n" +
                "       tpd.AGENT_CPF_CNPJ AS \"AGENTCPFCNPJ\",\n" +
                "       tfc.institution AS \"INSTITUTION\",\n" +
                "       tfc.service_contract AS \"SERVICECONTRACT\",\n" +
                "       tpd.SUB_CHANNEL AS \"SUBCHANNEL\", \n" +
                "       tcs.service_id || '-' || TCS.TECHNOLOGY AS \"TECHNOLOGY\",\n" +
                "       tcs.TERMINALS_NUMBER AS \"TERMINALSNUMBER\", \n" +
                "       tcs.VALUE AS \"UNITARYVALUE\", \n" +
                "       tpps.CODE || '-' || tpps.PT_DESCRIPTION AS \"FISERVSTATUS\",\n" +
                "       tpcs.code || '-' || tpcs.PT_DESCRIPTION AS \"CAIXASTATUS\",\n" +
                "       tpcs.message_code || '-' || tpcs.message_description AS \"CAIXAMESSAGE\",         \n" +
                "       TO_CHAR(tpd.INSERTION_DATE, 'dd/MM/yyyy hh:mm')  AS \"INCLUDEIN\",\n" +
                "       TO_CHAR(tpd.CONCLUSION_DATE, 'dd/MM/yyyy hh:mm')  AS \"FINISHEDIN\",\n" +
                "       TO_CHAR(tpd.ONLINE_DATE, 'dd/MM/yyyy hh:mm') AS \"SUBMISSIONONLINEDATE\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN 'Fisica'\n" +
                "       ELSE 'Juridica' END AS \"PERSONTYPE\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.CPF\n" +
                "       ELSE tpplp.CNPJ END AS \"CPFCNPJ\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.FANTASY_NAME\n" +
                "       ELSE tpplp.FANTASY_NAME END AS \"FANTASYNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.NAME || ' ' || tppp.SURNAME\n" +
                "       ELSE tpplp.SOCIAL_REASON END AS \"SOCIALREASON\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.PLATE_NAME\n" +
                "       ELSE tpplp.PLATE_NAME END AS \"PLATELETNAME\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.MONTHLY_INCOME\n" +
                "       ELSE tpplp.MONTH_AVAREGE END AS \"MONTHLYBILLING\",\n" +
                "       CASE tpd.proposal_type WHEN 'F' THEN tppp.BIRTH_DATE\n" +
                "       ELSE tpplp.DATE_CONSTITUTION END AS \"BIRTHDATE\",\n" +
                "       tppp.GENDER AS \"GNERE\",\n" +
                "       tppp.TREATMENT_PRONOUN AS \"TREATMENTPRONOUN\",\n" +
                "       tppp.LOCAL_BIRTH AS \"BIRTHPLACE\",\n" +
                "       tppp.NATIONALITY AS \"NACIONALITY\",\n" +
                "       tppp.name || ' ' || tppp.surname AS \"FULLNAME\",\n" +
                "       tpplp.CITY_INCRIPTION AS \"MUNICIPALREGISTRATION\",\n" +
                "       tpplp.STATE_INSCRIPTION AS \"STATEREGISTRATION\",\n" +
                "       'Sociedade Empresária' AS \"CONSTITUTIONFORM\",\n" +
                "       tpplp.OPEN_DATE AS \"OPENDATE\",\n" +
                "       tpd.ANNUAL_BILLING_VOL, \n" +
                "       tpd.ANNUAL_VOL_CASH, \n" +
                "       tpd.ANNUAL_VOL_SALES_CARD, \n" +
                "       tpd.ANNUAL_VOL_SALES_CARD_GROUP, \n" +
                "       tpd.AVERAGE_TICKET, \n" +
                "       CASE tpd.BACEN_PERMISSION WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"BACENPERMISSION\",\n" +
                "       tpd.CAMPAING_ID AS \"CAMPAINGID\", \n" +
                "       tpd.CNAE, \n" +
                "       tpd.ECOMMERCE, \n" +
                "       CASE tpd.FOREING_CARD WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"FOREIGNCARD\",\n" +
                "       CASE tpd.MANUAL_PREPAYMENT_ENABLED WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"MANUAL_PREPAYMENT_ENABLED\",        \n" +
                "       tpd.BOARDING_BRANCHING, \n" +
                "       tpd.PERC_CARD_NOT_PRESENT , \n" +
                "       tpd.PERC_CARD_PRESENT ,\n" +
                "       tpd.PERC_INTERNET , \n" +
                "       CASE tpd.PREPAYMENT_INDICATOR WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"PREPAYMENT_INDICATOR\",  \n" +
                "       CASE tpd.RECURRING_TRANSACTION WHEN 'true' THEN 'SIM'\n" +
                "       ELSE 'NÃO' END AS \"RECURRINGTRANSACTION\", \n" +
                "       tpd.SERVICE_DAY_0, \n" +
                "       tpd.SERVICE_DAY_15_30,\n" +
                "       tpd.SERVICE_DAY_1_7, \n" +
                "       tpd.SERVICE_DAY_8_14, \n" +
                "       tpd.SERVICE_DAY_OVER_30, \n" +
                "       tpd.BW_DATE AS \"PENDINGBWDATE\", \n" +
                "       tpd.BW_CONCLUSION_DATE AS \"INSTALLATIONDATE\",\n" +
                "       CASE TPPA.ADDRESS_TYPE WHEN 'BUSINESS' THEN 'Comercial'\n" +
                "       WHEN 'TRADING' THEN 'Entrega'\n" +
                "       ELSE 'Contato' END AS \"ADDRESSTYPE\",\n" +
                "       TPPA.ZIP_CODE AS \"CEP\",\n" +
                "       TPPA.STREET AS \"STREET\",\n" +
                "       TPPA.ADDRESS_NUMBER AS \"NUMBER\",\n" +
                "       TPPA.NEIGHBORHOOD AS \"DISTRICT\",\n" +
                "       TPPA.COMPLEMENT AS \"COMPLEMENT\",\n" +
                "       TPPA.CITY AS \"CITY\",\n" +
                "       TPPA.STATE AS \"STATE\",\n" +
                "       TPCP.TYPE AS \"PARTNERTYPE\" ,\n" +
                "       TPCP.CNPJ_CPF AS \"CPFCNPJPARTNER\" ,\n" +
                "       TPCP.BIRTH_DATE AS \"PARTNERBIRTHDATE\" ,\n" +
                "       TPCP.CONSTITUTION_TYPE AS \"CONSTITUTIONTYPE\" ,\n" +
                "       TPCP.DDD || TPCP.PHONE As \"CONTRACT\",\n" +
                "       TPCP.FIRST_NAME || ' ' || TPCP.LAST_NAME AS \"PARTNERNAME\",\n" +
                "       TPCP.TREATMENT_PRONOUN AS \"PRONOUNTREATMENTPARTNER\",\n" +
                "       TPCP.NATIONALITY AS \"PARTNERNACIONALITY\",\n" +
                "       TPCP.FUNCTION AS \"PARTNERFUNCTION\", \n" +
                "       TPCP.perc_part_soc AS \"PERCPARTNER\",\n" +
                "       tc.BIRTH_DATE AS \"CONTACTBIRTHDATE\", \n" +
                "       tc.CPF AS \"CONTACTCPF\", \n" +
                "       tc.EMAIL AS \"CONTACTEMAIL\", \n" +
                "       tc.FIRST_NAME || ' ' || tc.LAST_NAME AS \"CONTACTNAME\", \n" +
                "       tc.FIXED_DDD || tc.FIXED_NUMBER AS \"CONTACTPHONE\", \n" +
                "       tc.MOBILE_DDD || tc.MOBILE_NUMBER AS \"CONTACTCELLPHONE\",\n" +
                "       taf.DESCRIPTION, \n" +
                "       taf.DISCOUNT_FEE, \n" +
                "       taf.FEE, \n" +
                "       taf.FEE_ID_NUMBER,\n" +
                "       tba.BANK_NUMBER AS \"BANKCODE\",\n" +
                "       tba.AGENCY_NUMBER || tba.AGENCY_DIGIT AS \"AGENCY\",\n" +
                "       tba.ACCOUNT_DIGIT AS \"ACCOUNTDIGIT\", \n" +
                "       tba.ACCOUNT_NUMBER AS \"ACCOUNTNUMBER\", \n" +
                "       tba.ACCOUNT_OWNER_NAME AS \"ACCOUNTOWNER\", \n" +
                "       CASE tba.ACCOUNT_TYPE WHEN '0' THEN 'Conta corrente'\n" +
                "       ELSE 'Poupança' END AS \"ACCOUNTTYPE\",\n" +
                "       CASE tpwa.WEEK_DAY \n" +
                "        WHEN 'MONDAY' THEN 'Segunda das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "        WHEN 'Tuesday' THEN ' Terça-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "        WHEN 'Wednesday' THEN ' Quarta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "        WHEN 'Thursday' THEN ' Quinta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "        WHEN 'Friday' THEN ' Sexta-feira das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "        WHEN 'Saturday' THEN 'Sábado das ' || tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO\n" +
                "       ELSE 'Domingo'|| tpwa.DAY_FROM || ' as ' ||  tpwa.DAY_TO END AS \"WORKDAYS\"\n" +
                "       from tb_proposal_data tpd\n" +
                "  LEFT join TB_PROPOSAL_PHYSICAL_PERSON tppp on tpd.proposal_type = 'F' and tpd.id = tppp.ID_FILE_PROPOSAL_DTA\n" +
                "  LEFT join TB_PRE_PROPOSAL_LEGAL_PERSON tpplp on tpd.proposal_type = 'J' and tpd.id = tpplp.id_file_proposal_dta\n" +
                "  LEFT join TB_FILE_CONTROL TFC ON TFC.ID = tpd.id_file_control\n" +
                "  LEFT join tb_capture_solution TCS ON tcs.id_proposal_data = TPD.ID\n" +
                "  LEFT join tb_pre_proposal_status tpps on tpps.id = tpd.status_id\n" +
                "  LEFT join tb_proposal_cx_status tpcs on tpcs.status_id = tpps.id\n" +
                "  LEFT JOIN TB_PROPOSAL_COMPANY_PARTNERS TPCP ON TPCP.ID_PROPOSAL_PJ_DTA = tpplp.id\n" +
                "  LEFT JOIN TB_PRE_PROPOSAL_ADDRESS TPPA ON TPPA.ID_FILE_PROPOSAL_DTA = tpd.ID\n" +
                "  LEFT JOIN TB_CONTACT tc on tc.ID_PROPOSAL_DATA = tpd.ID\n" +
                "  LEFT JOIN TB_ACCOUNT_FREE taf on taf.ID_FILE_PROPOSAL_DTA = tpd.ID\n" +
                "  LEFT JOIN TB_BANK_ACCOUNT tba on tba.ID_FILE_PROPOSAL_DTA = tpd.ID\n" +
                "  left join TB_PRE_WORKING_DAY tpwa on tpwa.ID_PROPOSAL_DATA = tpd.ID",resultSetMapping = "report5Mapping")
})
public class EProposalData {

    @Id
    @Column(name = "ID", nullable = false)
    private Long id;

}
