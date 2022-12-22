package com.zhu.spring;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/22 22:15
 */
public class BeanDefinition {
    private Class type;

    private String scope;

    public Class getType() {
        return type;
    }

    public void setType(Class type) {
        this.type = type;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}