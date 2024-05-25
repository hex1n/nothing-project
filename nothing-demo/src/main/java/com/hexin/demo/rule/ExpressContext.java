package com.hexin.demo.rule;

import com.ql.util.express.IExpressContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author hex1n
 * @Date 2024/5/26/00:18
 * @Description
 **/
public class ExpressContext extends HashMap<String,Object> implements IExpressContext<String,Object> {

    public ExpressContext(Map<String,Object> params){
        super(params);
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return super.put(key, value);
    }
}
