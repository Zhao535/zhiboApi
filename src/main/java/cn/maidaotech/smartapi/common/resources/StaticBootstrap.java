package cn.maidaotech.smartapi.common.resources;

import cn.maidaotech.smartapi.common.L;
import cn.maidaotech.smartapi.common.utils.ProcessUtils;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Set;

@Service
public class StaticBootstrap {
    @PostConstruct
    public void init() {
        Reflections reflections = new Reflections(new ConfigurationBuilder().forPackages("cn.maidaotech.smartapi"));
        {
            Set<Class<?>> staticInitClasses = reflections.getTypesAnnotatedWith(StaticInit.class);
            for (Class<?> clazz : staticInitClasses) {
                try {
                    if (L.isInfoEnabled()) {
                        L.info("[StaticBootstrap] calling: " + clazz.getCanonicalName());
                    }
                    Class.forName(clazz.getCanonicalName());
                } catch (Throwable t) {
                    ProcessUtils.exitWithMessage(null, t);
                }
            }
        }
        if (L.isInfoEnabled()) {
            L.info("[StaticBootstrap] done");
        }
    }

}
