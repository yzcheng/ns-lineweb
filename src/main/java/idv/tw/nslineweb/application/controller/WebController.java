/*
 * Copyright 2016 LINE Corporation
 *
 * LINE Corporation licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package idv.tw.nslineweb.application.controller;

import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import idv.tw.nslineweb.db.LineEmpMap;
import idv.tw.nslineweb.db.LineEmpMapService;
import idv.tw.nslineweb.infra.line.api.v2.LineAPIService;
import idv.tw.nslineweb.infra.line.api.v2.response.AccessToken;
import idv.tw.nslineweb.infra.line.api.v2.response.IdToken;
import idv.tw.nslineweb.infra.utils.CommonUtils;

/**
 * <p>user web application pages</p>
 */
@Controller
public class WebController {

    private static final String LINE_WEB_LOGIN_STATE = "lineWebLoginState";
    static final String ACCESS_TOKEN = "accessToken";
    private static final Logger logger = LoggerFactory.getLogger(WebController.class);
    private static final String NONCE = "nonce";

    @Autowired
    private LineAPIService lineAPIService;

    @Autowired
    private LineEmpMapService lineEmpMapService;
    
    /**
     * <p>LINE Login Button Page
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/")
    public String login() {
        return "user/login";
    }

    /**
     * <p>Redirect to LINE Login Page</p>
     */
    @RequestMapping(value = "/gotoauthpage")
    public String goToAuthPage(HttpSession httpSession){
        final String state = CommonUtils.getToken();
        final String nonce = CommonUtils.getToken();
        httpSession.setAttribute(LINE_WEB_LOGIN_STATE, state);
        httpSession.setAttribute(NONCE, nonce);
        final String url = lineAPIService.getLineWebLoginUrl(state, nonce, Arrays.asList("openid", "profile"));
        return "redirect:" + url;
    }

    /**
     * <p>Redirect Page from LINE Platform</p>
     * <p>Login Type is to log in on any desktop or mobile website
     */
    @RequestMapping("/auth")
    public String auth(
            HttpSession httpSession,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "state", required = false) String state,
            @RequestParam(value = "scope", required = false) String scope,
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "errorCode", required = false) String errorCode,
            @RequestParam(value = "errorMessage", required = false) String errorMessage) {

        if (logger.isDebugEnabled()) {
            logger.debug("parameter code : " + code);
            logger.debug("parameter state : " + state);
            logger.debug("parameter scope : " + scope);
            logger.debug("parameter error : " + error);
            logger.debug("parameter errorCode : " + errorCode);
            logger.debug("parameter errorMessage : " + errorMessage);
        }

        if (error != null || errorCode != null || errorMessage != null){
            return "redirect:/loginCancel";
        }

        if (!state.equals(httpSession.getAttribute(LINE_WEB_LOGIN_STATE))){
            return "redirect:/sessionError";
        }

        httpSession.removeAttribute(LINE_WEB_LOGIN_STATE);
        AccessToken token = lineAPIService.accessToken(code);
        if (logger.isDebugEnabled()) {
            logger.debug("scope : " + token.scope);
            logger.debug("access_token : " + token.access_token);
            logger.debug("token_type : " + token.token_type);
            logger.debug("expires_in : " + token.expires_in);
            logger.debug("refresh_token : " + token.refresh_token);
            logger.debug("id_token : " + token.id_token);
        }
        httpSession.setAttribute(ACCESS_TOKEN, token);
        return "redirect:/success";
    }

    /**
    * <p>login success Page
    */
    @RequestMapping("/success")
    public String success(HttpSession httpSession, Model model) {

        AccessToken token = (AccessToken)httpSession.getAttribute(ACCESS_TOKEN);
        if (token == null){
            return "redirect:/";
        }

        if (!lineAPIService.verifyIdToken(token.id_token, (String) httpSession.getAttribute(NONCE))) {
            // verify failed
            return "redirect:/";
        }

        httpSession.removeAttribute(NONCE);
        IdToken idToken = lineAPIService.idToken(token.id_token);
        if (logger.isDebugEnabled()) {
            logger.debug("userId : " + idToken.sub);
            logger.debug("displayName : " + idToken.name);
            logger.debug("pictureUrl : " + idToken.picture);
        }
        model.addAttribute("idToken", idToken);
        
        LineEmpMap lineEmp = lineEmpMapService.findById(idToken.sub).orElse(new LineEmpMap());
		if(StringUtils.isBlank(lineEmp.getLineUid()))
		{
			lineEmp.setLineUid(idToken.sub);
			lineEmp.setDisplayName(idToken.name);
			lineEmp.setStatus("W");
			lineEmp.setCreateDt(new Date());
			lineEmp.setLastLoginDt(new Date());
			lineEmpMapService.saveAndFlush(lineEmp);
		}
		else
		{
			lineEmp.setDisplayName(idToken.name);
			lineEmp.setLastLoginDt(new Date());
			lineEmpMapService.saveAndFlush(lineEmp);
		}
        
        return "user/success";
    }

    /**
    * <p>login Cancel Page
    */
    @RequestMapping("/loginCancel")
    public String loginCancel() {
        return "user/login_cancel";
    }

    /**
    * <p>Session Error Page
    */
    @RequestMapping("/sessionError")
    public String sessionError() {
        return "user/session_error";
    }

}
