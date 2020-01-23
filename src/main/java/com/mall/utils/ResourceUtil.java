package com.mall.utils;

import com.mall.entity.menu.Menu;
import com.mall.entity.menu.SidebarMenu;
import com.mall.entity.menu.TreeNode;

import java.util.*;

public class ResourceUtil {

	public static List<TreeNode> convertToVo(List<Menu> resourceList) {
		List<TreeNode> parentNodes = new ArrayList<TreeNode>();
		for(Menu resource : resourceList) {
			if(resource.getSeqNo() != 0)
				continue;
			TreeNode parent = wrapNode(resource,true);
			parentNodes.add(parent);
		}
		for(TreeNode node : parentNodes) {
			for(Menu child : resourceList) {
				if(child.getParentId().equals(node.getId())) {//如果该孩子节点的ID为该父节点
			        TreeNode childNode = wrapNode(child,false);
			        node.addChild(childNode);
				}
			}
		}
		
		return parentNodes;
	}

	private static TreeNode wrapNode(Menu resource, boolean isParent) {
		TreeNode node = new TreeNode();
		node.setId(String.valueOf(resource.getId()));
		node.setText(resource.getMName());
		Map<String,Object> attributes = new HashMap<String,Object>();
		attributes.put("url", resource.getMPath());
		attributes.put("parentId", resource.getParentId());
		node.setAttributes(attributes);
		node.setIconCls(resource.getIconCls());
		if(isParent && resource.getChildren() != null && resource.getChildren().size() > 0) {
			node.setState("closed");
			Collections.sort(resource.getChildren(), new MenuComparator());//对子菜单进行排序
			for(Menu r : resource.getChildren()) {
				TreeNode childNode = wrapNode(r,false);
				node.addChild(childNode);
			}
		}
		return node;
	}

    public static List<SidebarMenu> convertToSidebarMenuVo(List<Menu> resourceList) {
        List<SidebarMenu> parentNodes = new ArrayList<SidebarMenu>();
        for(Menu resource : resourceList) {
            if(resource.getSeqNo() != 0)
                continue;
            SidebarMenu parent = wrapSidebarMenu(resource, true);
            parentNodes.add(parent);
        }
        for(SidebarMenu node : parentNodes) {
            for(Menu child : resourceList) {
                if(child.getParentId().equals(node.getId())) {//如果该孩子节点的ID为该父节点
                    SidebarMenu childNode = wrapSidebarMenu(child,false);
                    node.addChild(childNode);
                }
            }
        }

        return parentNodes;
    }

    private static SidebarMenu wrapSidebarMenu(Menu resource, boolean isParent) {
        SidebarMenu node = new SidebarMenu();
        node.setId(resource.getCode());
        node.setText(resource.getMName());
        node.setUrl(resource.getMPath());
        node.setIcon(resource.getIconCls());
        if(isParent && resource.getChildren() != null && resource.getChildren().size() > 0) {
            Collections.sort(resource.getChildren(), new MenuComparator());//对子菜单进行排序
            for(Menu r : resource.getChildren()) {
                SidebarMenu childNode = wrapSidebarMenu(r, false);
                node.addChild(childNode);
            }
        }
        return node;
    }
}
class MenuComparator implements Comparator<Menu> {

	@Override
	public int compare(Menu res1, Menu res2) {
		int i1 = res1.getSeqNo()!=null ? res1.getSeqNo():-1;
		int i2 = res2.getSeqNo()!=null ? res2.getSeqNo():-1;
		return i1-i2;
	}
}

class MenuIdComparator implements Comparator<Menu> {

	@Override
	public int compare(Menu res1, Menu res2) {
		return res1.getCode().compareTo(res2.getCode());

	}
}

