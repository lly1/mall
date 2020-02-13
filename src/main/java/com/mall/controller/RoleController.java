package com.mall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mall.common.BaseController;
import com.mall.common.FrontPage;
import com.mall.common.RtnMessage;
import com.mall.common.RtnPageInfo;
import com.mall.entity.menu.Menu;
import com.mall.entity.menu.RoleMenu;
import com.mall.entity.user.Role;
import com.mall.entity.user.User;
import com.mall.service.menu.MenuService;
import com.mall.service.menu.RoleMenuService;
import com.mall.service.role.RoleService;
import com.mall.utils.CommonUtil;
import com.mall.utils.RtnMessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
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
    public RtnMessage save(Role role, String pageType, HttpSession session) throws Exception {
        Role r = this.roleService.getOne(new QueryWrapper<Role>().eq("role_name", role.getRoleName()));
        try {
            if (pageType.equals("add")) {
                if (CommonUtil.isBlank(r)) {
                    r = new Role();
                    r.setRoleName(role.getRoleName());
                    r.setRoleId(role.getRoleId());
                } else {
                    return RtnMessageUtils.buildFailed("角色名已存在，请重新输入");
                }
            } else {
                r.setRoleName(role.getRoleName());
                r.setRoleId(role.getRoleId());
            }
            roleService.saveOrUpdate(r);
            //获取入参中的菜单列表字符串并更新roleMenu表
            List<String> menulist = Arrays.asList(role.getAuthNames().split(","));
            //先删除roleMenu表中此角色的所有权限再新增
            roleMenuService.remove(new QueryWrapper<RoleMenu>().eq("role_id",role.getRoleId()));
            for (String mName: menulist){
                QueryWrapper<Menu> menuQueryWrapper = new QueryWrapper<>();
                menuQueryWrapper.eq("mName", mName);
                Menu menu = menuService.getOne(menuQueryWrapper);
                if (null != menu){
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setRoleId(role.getRoleId());
                    roleMenu.setMenuId(menu.getId());
                    roleMenuService.save(roleMenu);
                }
            }
            return RtnMessageUtils.buildSuccess("保存成功");
        }catch (Exception e){
            /*将异常打印到日志*/
            this.logger.error(e.getMessage());
            e.printStackTrace();
            return RtnMessageUtils.buildFailed("保存失败");
        }
    }
}
