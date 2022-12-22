package com.zhu.service;

import com.zhu.spring.*;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/21 21:08
 */
@Component
@Scope("singleton")
public class UserService implements BeanNameAware, InitializingBean {

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

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("initializing bean ----afterPropertiesSet");
    }
}