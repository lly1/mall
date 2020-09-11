package com.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnMessage;
import com.mall.common.RtnPageInfo;
import com.mall.api.entity.pc.menu.Menu;
import com.mall.api.entity.pc.menu.RoleMenu;
import com.mall.api.entity.pc.user.Role;
import com.mall.service.menu.MenuService;
import com.mall.service.menu.RoleMenuService;
import com.mall.service.role.RoleService;
import com.mall.api.utils.CommonUtil;
import com.mall.api.utils.RtnMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/sys/role")
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleMenuService roleMenuService;

	@RequestMapping(value ="/index.WS")
	public String index() {
		return "/views/sys/role";
	}

    @RequestMapping("/page")
    @ResponseBody
    public RtnPageInfo<Role> findPage(FrontPage<Role> page) throws Exception {
        Page<Role> userPage = roleService.page(page.getPagePlus());
        //查询角色下拥有的权限菜单
        List<Role> roleList = userPage.getRecords();
        for (Role role: roleList){
            List<Menu> menuList = menuService.getMenuByRole(role.getRoleId());
            List<String> mNameList = new ArrayList<>();
            for (Menu menu: menuList){
                mNameList.add(menu.getMName());
            }
            role.setAuthNames(String.join(",", mNameList));
        }

        return new RtnPageInfo<>(userPage);
    }

    @RequestMapping(value="/findResource")
    @ResponseBody
    public List<Menu> findResource(String roleId,String pageType) throws Exception {
        List<Menu> menuList = menuService.getAll();
        return menuList;
    }

    @RequestMapping(value = "/searchByOwnerId")
    @ResponseBody
    public List<Menu> searchByOwnerId(String ownerId)throws Exception{
        this.logAllRequestParams();
        List<Menu> resourceList = menuService.list(new QueryWrapper<Menu>().eq("parentId", ownerId));
        return resourceList;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Role> list() throws Exception {
        List<Role> roles = roleService.list();
        if (CommonUtil.isNotBlank(roles)) {
            return roles;
        }
        return null;
    }

    @RequestMapping("/save")
    @ResponseBody
    public RtnMessage save(Role role) throws Exception {
        //不做操作
	    return RtnMessageUtils.buildSuccess(role);
    }


    @RequestMapping(value ="/editPage")
    public ModelAndView editPage(String roleId) throws Exception {
        this.logAllRequestParams();
        Role role = roleService.getOne(new QueryWrapper<Role>().eq("role_id",roleId));
        ModelAndView mv = new ModelAndView("/views/sys/role_edit");
        mv.addObject("pageType","edit");
        mv.addObject("role",role);
        return mv;
    }

    @RequestMapping(value ="/addPage")
    public ModelAndView addPage() throws Exception {
        ModelAndView mv = new ModelAndView("/views/sys/role_edit");
        mv.addObject("pageType","add");
        mv.addObject("role",new Role());
        return mv;
    }

    @RequestMapping(value="/addAuth")
    @ResponseBody
    public RtnMessage addAuth(RoleMenu roleMenu) throws Exception {
        this.logAllRequestParams();
        roleMenuService.save(roleMenu);
        return RtnMessageUtils.buildSuccess("保存成功");

    }

    @RequestMapping(value="/deleteAuth")
    @ResponseBody
    public RtnMessage deleteAuth(RoleMenu roleMenu) throws Exception {
        roleMenuService.remove(new QueryWrapper<RoleMenu>(roleMenu));
        return RtnMessageUtils.buildSuccess("删除成功");
    }
}
