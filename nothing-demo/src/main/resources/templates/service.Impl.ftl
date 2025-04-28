package ${implPackage};

import ${entityPackage}.${className};
import ${mapperPackage}.${className}Mapper;
import ${servicePackage}.${className}Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ${className}ServiceImpl implements ${className}Service {
    
    private final ${className}Mapper ${className?uncap_first}Mapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int save(${className} record) {
        return ${className?uncap_first}Mapper.insert(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveBatch(List<${className}> list) {
        return ${className?uncap_first}Mapper.batchInsert(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int saveOrUpdateBatch(List<${className}> list) {
        return ${className?uncap_first}Mapper.batchInsertOrUpdate(list);
    }

    @Override
    public ${className} getById(Long id) {
        return ${className?uncap_first}Mapper.selectById(id);
    }

    @Override
    public List<${className}> listByIds(List<Long> ids) {
        return ${className?uncap_first}Mapper.selectByIds(ids);
    }

    @Override
    public List<${className}> listByCondition(${className} condition) {
        return ${className?uncap_first}Mapper.selectByCondition(condition);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateById(${className} record) {
        return ${className?uncap_first}Mapper.updateById(record);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateBatch(List<${className}> list) {
        return ${className?uncap_first}Mapper.batchUpdate(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeById(Long id) {
        return ${className?uncap_first}Mapper.deleteById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int removeBatch(List<Long> ids) {
        return ${className?uncap_first}Mapper.batchDelete(ids);
    }
}