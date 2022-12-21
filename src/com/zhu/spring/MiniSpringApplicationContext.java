package com.zhu.spring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.URL;

/**
 * @author by zhuhcong
 * @descr
 * @date 2022/12/21 21:12
 */
public class MiniSpringApplicationContext {

    private Class configClass;

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
            System.out.println(file);
            if(file.isDirectory()){
                File[] files = file.listFiles();
                for (File f : files) {
                    String absolutePath = f.getAbsolutePath();
                    System.out.println(absolutePath);
                    if(absolutePath.endsWith(".class")){
                        //real load class


                            // /Users/knight/IdeaProjects/mini-spring/out/production/mini-spring/com/zhu/service/UserService ---> com.zhu.service.UserService

                            //com/zhu/service/UserService
                            String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));

                            //com.zhu.service.UserService
                            className = className.replace("/", ".");
                            System.out.println(className);

                        try {
                            Class<?> aClass = classLoader.loadClass(className);

                            if(aClass.isAnnotationPresent(Component.class)){
                                //bean
                                System.out.println("bean is " + aClass.getName());
                            }


                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }

        }

    }

    public Object getBean(String beanName){

        return null;
    }
}