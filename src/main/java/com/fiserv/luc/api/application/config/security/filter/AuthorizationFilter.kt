package com.fiserv.luc.api.application.config.security.filter

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.fiserv.luc.api.application.aspect.exceptions.FDSecurityException
import com.fiserv.luc.api.application.config.security.fd.DSecurity
import com.fiserv.luc.api.application.config.security.fd.FDSecurity
import com.fiserv.luc.api.application.config.security.fd.FDSecurityEnum
import com.fiserv.luc.api.infrastructure.aid.enums.ApplicationEnum
import com.fiserv.luc.api.infrastructure.aid.enums.EventActivityEnum
import com.fiserv.luc.api.infrastructure.aid.util.LogUtil
import org.apache.commons.lang3.exception.ExceptionUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.filter.GenericFilterBean
import org.springframework.web.servlet.HandlerExceptionResolver
import java.io.IOException
import java.io.Serializable
import java.util.*
import java.util.logging.Logger
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(val fdSecurity: FDSecurity, private val resolver: HandlerExceptionResolver) : GenericFilterBean() {

    @Autowired
    private val objectMapper: ObjectMapper? = null

    @kotlin.Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val respo = response as HttpServletResponse
        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Starting request for :" + req.requestURI))
        var fdsecurityResp: DSecurity? = null
        try {
            //O APIGEE fará a validação e enviarra este atributo no header identificando que a API já foi validada
            if (req.getHeader("x-security-check") == null || !java.lang.Boolean.valueOf(req.getHeader("x-security-check"))) {
                fdsecurityResp = fdSecurity.canAccessUser(req, FDSecurityEnum.TOKEN)
            }
            chain.doFilter(request, response)

            val token = extractTokenFromRequest(request)
            val jsonString = extractJsonStringFromToken(token)
            val test: User = extractUserFromJsonString(jsonString)
            println(test.username)

        } catch (e: FDSecurityException) {
            LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "FDSecurity Exception"))
            LOGGER.info(ExceptionUtils.getStackTrace(e))
            resolver.resolveException(req, respo, null, e)
        } catch (e: Exception) {
            LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Exception"))
            LOGGER.info(ExceptionUtils.getStackTrace(e))
            resolver.resolveException(req, respo, null, e)
        }
        LOGGER.info(LogUtil.buildMessage(ApplicationEnum.SBA, req, EventActivityEnum.AUDIT_REQUEST, "Committing request for :" + req.requestURI))
    }

    override fun destroy() {}

    data class User(val username: String): Serializable

    companion object {
        private val LOGGER = Logger.getLogger(AuthorizationFilter::class.java.name)

        /**
         * @param token String
         * @return String
         */
        fun extractJsonStringFromToken(token: String): String {
            val chunks: Array<String> = token.split(".").toTypedArray()
            val decoder = Base64.getDecoder()
            return String(decoder.decode(chunks[1]))
        }

        /**
         * @param request ServletRequest
         * @return String
         */
        fun extractTokenFromRequest(request: ServletRequest): String {
            val httpRequest = request as HttpServletRequest
            return httpRequest.getHeader("authorization")
        }

    }

    /**
     * @param jsonString String
     * @return User
     */
    private fun extractUserFromJsonString(jsonString: String): User {
        return objectMapper!!.readValue(jsonString)
    }
}