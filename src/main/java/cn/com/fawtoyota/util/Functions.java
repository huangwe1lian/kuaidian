package cn.com.fawtoyota.util;

import org.gelivable.dao.GeliOrm;
import org.gelivable.dao.GeliUtils;
import org.gelivable.dao.ValueGetter;
import org.gelivable.web.EnvUtils;
import org.springframework.dao.EmptyResultDataAccessException;


public class Functions {
	/**
     * 获取对象 指定 字段中的refer注解类， 并根据字段的内容获取对象
     *
     * @param entity
     * @param referField
     * @return
     */
    public static Object refer(Object entity, String referField) {
        Class type = entity.getClass();
        try {
            ValueGetter getter = EnvUtils.getEnv().getBean(GeliOrm.class).getFieldGetter(type, referField);
            if (getter != null && getter.getReferType() != null) {
                Object id = getter.get(entity);
                if (id == null) {
                    return null;
                }
                Object ref = GeliUtils.getDao().find(getter.getReferType(), id);
                return ref;
            } else {
                return null;
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
