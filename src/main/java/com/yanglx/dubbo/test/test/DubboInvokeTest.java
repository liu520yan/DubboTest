package com.yanglx.dubbo.test.test;

import com.alibaba.dubbo.config.*;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.yanglx.dubbo.test.CacheInfo;
import com.yanglx.dubbo.test.DubboSetingState;
import com.yanglx.dubbo.test.dubbo.DubboApiLocator;
import com.yanglx.dubbo.test.dubbo.DubboMethodEntity;
import com.yanglx.dubbo.test.utils.JsonUtils;
import com.yanglx.dubbo.test.utils.PluginUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * @program: DubboTest
 * @description: test
 * @author: liuyan41
 * @create: 2023-03-22 22:35
 **/
public class DubboInvokeTest {

    public static void main(String[] args) {
        ReferenceConfig<GenericService> reference = new ReferenceConfig<GenericService>();
        reference.setApplication(new ApplicationConfig("generic-consumer"));
        reference.setInterface("com.lenovo.ofp.order.middleware.service.BBYAutoReturnServiceImpl");
        RegistryConfig registryConfig = new RegistryConfig();
        String address = "zookeeper://10.62.188.145:2181";
        Map<String, String> param = new HashMap<>();
        param.put("dubbo.application.service-discovery.migration", "APPLICATION_FIRST");
        registryConfig.setParameters(param);
        registryConfig.setAddress(address);
        reference.setRegistry(registryConfig);
        reference.setGeneric(true);
        GenericService genericService = reference.get();
        try {
            List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
            Map<String, Object> user = new HashMap<String, Object>();
            user.put("class", "com.alibaba.dubbo.config.api.User");
            user.put("name", "actual.provider");
            users.add(user);
            users = (List<Map<String, Object>>) genericService.$invoke("getUsers", new String[]{List.class.getName()}, new Object[]{users});
        } finally {
            reference.destroy();
        }
    }
}
