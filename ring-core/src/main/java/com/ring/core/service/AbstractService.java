package com.ring.core.service;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.github.pagehelper.PageHelper;
import com.ring.core.mapper.base.Mapper;
import org.apache.commons.collections4.ListUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 功能描述:
 * <p/>
 *
 * @author chaoshibin 新增日期：2017/10/1
 * @author chaoshibin 修改日期：2017/10/1
 * @version 1.0.0
 * @since 1.0.0
 */
public abstract class AbstractService<entity, example> implements Service<entity, example> {
    protected static final Log LOG = LogFactory.get();
    /**
     * 分页默认大小
     */
    private static final int MYBATIS_DEFAULT_PAGESIZE = 20;
    /**
     * 批量插入默认值
     */
    private static final int BATCH_OPERATE_SIZE = 500;

    /**
     * 子类必须实现，获取对应的mapper接口
     *
     * @return mybatis Mapper接口
     */
    protected abstract Mapper<entity, example> getMapper();

    @Override
    public int countByExample(example example) {
        return getMapper().countByExample(example);
    }

    @Override
    public int deleteByExample(example example) {
        return getMapper().deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(String id) {
        return getMapper().deleteByPrimaryKey(id);
    }

    @Override
    public int insert(entity record) {
        return getMapper().insert(record);
    }

    @Override
    public int insertSelective(entity record) {
        return getMapper().insertSelective(record);
    }

    @Override
    public List<entity> selectByExample(example example) {
        return getMapper().selectByExample(example);
    }

    @Override
    public entity selectByPrimaryKey(String id) {
        return getMapper().selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(entity record, example example) {
        return getMapper().updateByExampleSelective(record, example);
    }

    @Override
    public int updateByExample(entity record, example example) {
        return getMapper().updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(entity record) {
        return getMapper().updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(entity record) {
        return getMapper().updateByPrimaryKey(record);
    }

    @Override
    public List<entity> queryPageByExample(example example, int pageNum, int pageSize) {
        return this.queryPageByExample(example, pageNum, pageSize, null);
    }

    @Override
    public List<entity> queryPageByExample(example example, int pageNum, int pageSize, String orderBy) {
        if (pageSize < 1) {
            pageSize = MYBATIS_DEFAULT_PAGESIZE;
        }
        if (StringUtils.isEmpty(orderBy)) {
            PageHelper.startPage(pageNum, pageSize);
        } else {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        return this.selectByExample(example);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int batchInsert(List<entity> list) {
        List<List<entity>> lists = ListUtils.partition(list, BATCH_OPERATE_SIZE);
        int result = 0;
        for (List<entity> element : lists) {
            result += getMapper().batchInsert(element);
        }
        return result;
    }

    @Override
    public List<entity> selectAll(entity entity) {
        return getMapper().selectAll(entity);
    }
}
