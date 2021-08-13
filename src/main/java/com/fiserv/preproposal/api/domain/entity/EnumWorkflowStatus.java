package com.fiserv.preproposal.api.domain.entity;


public enum EnumWorkflowStatus {

	DOCUMENT_ERROR("Documento nao identificado para PF nem para PJ"),
    VALIDATION_ERROR("Erro na validação dos dados lidos do arquivo"),
    PENDING_CEP("Integração com a API de CEP pendente"),
    CEP_ERROR("CEP invalido"),
    PENDING_DATABASE("Pendente as informacoes complempementares do M360"),
    DATABASE_ERROR("Agent Channel nao encontado"),
    PENDING_CONFIG("Integração com a API de CONFIG pendente"),
    CONFIG_ERROR("Service Contract invalido"),
    PENDING_CNAE("Captura de CNAE pendente"),
    CNAE_ERROR("Erro ao processar a captura do CNAE"),
    PENDING_FISERV_SUBMISSION("Pendente envio da proposta para o Fiserv Online"),
    CAMPAING_ERROR("Erro ao buscar informacoes com base na Campanha"),
    RANGE_ERROR("Erro ao busscar o Range"),
    FISERV_SUBMISSION_ERROR("Erro ao enviar proposta apra o Fiserv Online"),
    PENDING_FISERV_RETURN("Pendente retorno do Fiserv Online"),
    FISERV_RETURN_ERROR("Erro no retorno do Fiserv Online"),
    PENDING_BW ("Integração com o BW pendente"),
    BW_ERROR ("Item nao encontrado no BW"),
    ACTIVE_BW ("Proposta ativa no BW"),
    PENDING_TMP ("Integração com o TMP pendente"),
    PROPOSAL_LEAD("Proposta LEAD tera que consultar baso do M360"),
    TMP_ERROR ("Todos os tickets do TMP foram cancelados"),
    COMPLETED("Completo");

    private final String description;

    public String getDescription(){
        return description;
    }

    EnumWorkflowStatus(String description) {
        this.description = description;
    }
}
