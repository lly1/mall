package com.mall.controller;

import com.mall.shiro.WebToken;
import com.mall.api.constant.Constants;
import com.mall.common.BaseController;
import com.mall.api.entity.pc.user.User;
import com.mall.service.user.UserService;
import com.mall.api.utils.CommonUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/auth")
public class LoginController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String submitLogin(HttpServletRequest request,Model model) {
        LOGGER.info("跳到这边的路径为:"+request.getRequestURI());
        Subject s = SecurityUtils.getSubject();
        LOGGER.info("是否记住登录--->"+s.isRemembered()+"<-----是否有权限登录----->"+s.isAuthenticated()+"<----");
        if(s.isAuthenticated()){
            User user = (User) session.getAttribute(Constants.USER_SESSION);
            model.addAttribute("user",user);
            return "oliveIndex";
        }else {
            return "login";
        }
    }
    @RequestMapping(value = "/login/main")
    public String login(String username, String password, Model model){
        this.logAllRequestParams();
        if(CommonUtil.isBlank(username) || CommonUtil.isBlank(password)){
            return "redirect:/auth/login";
        }
        String loginSrc = request.getParameter("loginSrc");
        WebToken webToken = new WebToken(username,password.toCharArray(),false,null,null,loginSrc);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(webToken);
            User currentUser = userService.getCurrentUser();
            if (currentUser != null) {
                model.addAttribute("user",currentUser);
                System.out.println("用户[" + username + "]登录认证通过");
                return "redirect:/auth/login";
            }
        } catch (Exception ex) {
            final String message = ex.getMessage();
            this.getSession().setAttribute("message", message);
            return "redirect:/auth/login";
        }
        return "redirect:/auth/login";
    }

    //被踢出后跳转的页面
    @RequestMapping(value = "/kickout", method = RequestMethod.GET)
    public String kickOut() {
        System.out.println("out");
        return "login";
    }

    @GetMapping("/logout")
    @ResponseBody
    public Boolean logOut() {
        SecurityUtils.getSubject().logout();
        return true;
    }

}

