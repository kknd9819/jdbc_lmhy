package com.zz.controller;

/**
 * PathController - 路径基类
 *
 * @author longhaung
 * @version 1.0
 */
public class BasePathController {

    private static final String VIEW = "view";

    private static final String ADD = "add";

    private static final String EDIT = "edit";

    private static final String LIST = "list";

    protected static final String REDIRECT_LIST="redirect:list.jhtml";

    /**
     * 返回模块查看路径
     * @param modulePath  模块路径
     * @return 返回模块查看路径：模块路径+"view"
     */
    protected static String getViewPath(String modulePath){
        return getOperatePath(modulePath,VIEW);
    }

    /**
     * 返回模块添加路径
     * @param modulePath  模块路径
     * @return 返回模块添加路径：模块路径+"add"
     */
    protected static String getAddPath(String modulePath){
        return getOperatePath(modulePath,ADD);
    }

    /**
     * 返回查询编辑路径
     * @param modulePath  模块路径
     * @return 返回模块查询路径：模块路径+"list"
     */
    protected static String getListPath(String modulePath){
        return getOperatePath(modulePath,LIST);
    }

    /**
     * 返回模块查询路径
     * @param modulePath  模块路径
     * @return 返回模块编辑路径：模块路径+"list"
     */
    protected static String getEditPath(String modulePath){
        return getOperatePath(modulePath,EDIT);
    }

    /**
     * 返回操作模块操作路径
     * @param modulePath 模块路径：如:/admin/member/
     * @param operateName 操作名称：如：view（查看）
     * @return 返回操作路径：模块路径+操作名称（/admin/member/view）
     */
    protected static String getOperatePath(String modulePath, String operateName) {
        StringBuilder path = new StringBuilder(100);
        if(null != modulePath){
            path.append(modulePath.trim());
        }
        if(null != operateName){
            path.append(operateName.trim());
        }
        return path.toString();
    }

}
