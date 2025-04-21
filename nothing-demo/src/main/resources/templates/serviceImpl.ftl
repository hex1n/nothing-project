package ${package};

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import ${mapperPackage}.${className}Mapper;
import ${entityPackage}.${className};
import java.util.List;

@Service
public class ${className}ServiceImpl implements ${className}Service {
@Resource
private ${className}Mapper mapper;

@Override
public int save(${className} record) {
return mapper.insert(record);
}

@Override
@Transactional(rollbackFor = Exception.class)
public int saveBatch(List<${className}> list) {
return mapper.batchInsert(list);
}

@Override
@Transactional(rollbackFor = Exception.class)
public int saveOrUpdateBatch(List<${className}> list) {
return mapper.batchInsertOrUpdate(list);
}

@Override
public ${className} getById(Long id) {
return mapper.selectById(id);
}

@Override
public List<${className}> listByIds(List<Long> ids) {
    return mapper.selectByIds(ids);
    }

    @Override
    public List<${className}> listByCondition(${className} condition) {
    return mapper.selectByCondition(condition);
    }

    @Override
    public int updateById(${className} record) {
    return mapper.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<${className}> list) {
    return mapper.batchUpdate(list);
    }

    @Override
    public int removeById(Long id) {
    return mapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeBatch(List<Long> ids) {
        return mapper.batchDelete(ids);
        }
        }