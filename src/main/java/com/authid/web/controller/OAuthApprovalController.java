package com.authid.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Chris Campo
 */
@Controller
@SessionAttributes("authorizationRequest")
public class OAuthApprovalController {

    @RequestMapping("/oauth/confirm_access")
    public ModelAndView getAccessConfirmation(final Map<String, Object> model, final HttpServletRequest request)
            throws Exception {
        model.put("title", "AuthID - Home");
        if (!model.containsKey("scopes") && request.getAttribute("scopes") != null) {
            model.put("scopes", request.getAttribute("scopes"));
        }
        if (request.getAttribute("_csrf") != null) {
            model.put("_csrf", request.getAttribute("_csrf"));
        }
        return new ModelAndView("oauth_approval", model);
    }
}
