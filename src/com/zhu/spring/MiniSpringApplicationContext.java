package com.zhu.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/21 21:12
 */
public class MiniSpringApplicationContext {

    private Class configClass;

    private Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private Map<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public MiniSpringApplicationContext(Class configClass) {
        this.configClass = configClass;

        //scan the class decorate by @ComponentScan
        if(configClass.isAnnotationPresent(ComponentScan.class)){

            ComponentScan componentScanAnnotation = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            //scan path, eg: com.zhu.service
            String path = componentScanAnnotation.value();
            //  com.zhu.service  ----> com/zhu/service
            path = path.replace(".", "/");

            //find absolute path from MiniSpringApplicationContext context
            ClassLoader classLoader = MiniSpringApplicationContext.class.getClassLoader();
            // get url , /Users/knight/IdeaProjects/mini-spring/out/production/mini-spring/com/zhu/service
            URL resource = classLoader.getResource(path);

            File file = new File(resource.getFile());
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    if(absolutePath.endsWith(".class")){
                        //real load class


                            // /Users/knight/IdeaProjects/mini-spring/out/production/mini-spring/com/zhu/service/UserService ---> com.zhu.service.UserService

                            //com/zhu/service/UserService
                            String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));

                            //com.zhu.service.UserService
                            className = className.replace("/", ".");

                        try {
                            Class<?> clazz = classLoader.loadClass(className);

                            if(clazz.isAnnotationPresent(Component.class)){

                                if(BeanPostProcessor.class.isAssignableFrom(clazz)){
                                    BeanPostProcessor instance = (BeanPostProcessor)clazz.newInstance();
                                    beanPostProcessorList.add(instance);
                                }

                                Component componentAnnotation = clazz.getAnnotation(Component.class);
                                String beanName = componentAnnotation.value();
                                if("".equals(beanName)){
                                    //transfer name ,eg: Service->service SService->SService, SerR->serR
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }
                                //generate BeanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                beanDefinition.setType(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                }else{
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }

                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

        //create bean
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if(beanDefinition.getScope().equals("singleton")){
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }

    }

    private Object createBean(String beanName, BeanDefinition beanDefinition){

        Class clazz = beanDefinition.getType();

        try {
            Object instance = clazz.getConstructor().newInstance();

            // simple dependency injection
            for (Field field : clazz.getDeclaredFields()) {

                if (field.isAnnotationPresent(Autowired.class)) {
                    //change true ,can assign private
                    field.setAccessible(true);
                    String fieldName = field.getName();
                    Object bean = getBean(fieldName);
                    field.set(instance, bean);
                }
            }

            //check bean name aware
            if (instance instanceof BeanNameAware) {
                //force cast to BeanNameAare and call its method
                ((BeanNameAware)instance).setBeanName(beanName);
            }

            //before init
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName);
            }

            //check initalizing bean
            if (instance instanceof InitializingBean) {
                //force cast to BeanNameAare and call its method
                ((InitializingBean)instance).afterPropertiesSet();
            }


            //after init
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName);
            }


            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName){

        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);

        if(beanDefinition == null){
            throw new RuntimeException("class not found with bean name:"+beanName);
        }

        String scope = beanDefinition.getScope();
        if("singleton".equals(scope)){
            Object bean = singletonObjects.get(beanName);
            if(bean == null){
                Object createdBean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, createdBean);
                return createdBean;
            }
            return bean;
        }else{
            return createBean(beanName, beanDefinition);
        }
    }
}