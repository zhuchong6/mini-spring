package com.zhu.service;

import com.zhu.spring.Autowired;
import com.zhu.spring.BeanNameAware;
import com.zhu.spring.Component;
import com.zhu.spring.Scope;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/21 21:08
 */
@Component
@Scope("singleton")
public class UserService implements BeanNameAware {

    @Autowired
    private OrderService orderService;

    private String beanName;

    public void test(){
        System.out.println(orderService+"|"+beanName);
    }

    @Override
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}