package com.mall.pc.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.pc.common.BaseController;
import com.mall.api.entity.base.FrontPage;
import com.mall.api.entity.base.RtnMessage;
import com.mall.api.entity.base.RtnPageInfo;
import com.mall.api.entity.pc.menu.Menu;
import com.mall.api.entity.pc.menu.SidebarMenu;
import com.mall.service.menu.MenuService;
import com.mall.api.utils.CommonUtil;
import com.mall.api.utils.ResourceUtil;
import com.mall.api.utils.RtnMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.LinkedList;
import java.util.List;

@Controller
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;
    /**
     * 通过角色ID获取权限
     *
     * @return
     */
    @RequestMapping(value="role/showSidebarMenu")
    @ResponseBody
    public List<SidebarMenu> showSidebarMenu(String roleId) {
        this.logAllRequestParams();
        List<Menu> resourceList = new LinkedList<>();
        String[] arg = roleId.split(",");
        if(arg.length > 1){
            for (String s : arg) {
                resourceList.addAll(this.menuService.getMenuByRole(s));
            }
        }else {
            resourceList.addAll(this.menuService.getMenuByRole(arg[0]));
        }
        return ResourceUtil.convertToSidebarMenuVo(resourceList);
    }



    @RequestMapping("/sys/menu/index.WS")
    public String index() {
        return "views/sys/menu";
    }

    @RequestMapping("/sys/menu/page")
    @ResponseBody
    public RtnPageInfo<Menu> findPage(FrontPage<Menu> page) {
        Page<Menu> userPage = menuService.page(page.getPagePlus());

        return new RtnPageInfo<>(userPage);
    }

    @RequestMapping("/sys/menu/save")
    @ResponseBody
    public RtnMessage save(Menu menu, String pageType, HttpSession session){
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mName", menu.getMName());
        Menu m = this.menuService.getOne(queryWrapper);
        try {
            if (pageType.equals("add")) {
                if (CommonUtil.isBlank(m)) {
                    m = new Menu();
                    m.setMName(menu.getMName());
                    m.setMPath(menu.getMPath());
                    m.setParentId(menu.getParentId());
                    m.setSeqNo(menu.getSeqNo());
                } else {
                    return RtnMessageUtils.buildFailed("菜单名已存在，请重新输入");
                }
            } else {
                m.setMName(menu.getMName());
                m.setMPath(menu.getMPath());
                m.setParentId(menu.getParentId());
                m.setSeqNo(menu.getSeqNo());
            }
            this.menuService.saveOrUpdate(m);
            return RtnMessageUtils.buildSuccess("保存成功");
        }catch (Exception e){
            /*将异常打印到日志*/
            this.logger.error(e.getMessage());
            e.printStackTrace();
            return RtnMessageUtils.buildFailed("保存失败");
        }
    }



}
