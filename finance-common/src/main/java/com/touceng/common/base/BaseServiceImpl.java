package com.touceng.common.base;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import com.touceng.common.constant.TouCengConstant;
import com.touceng.common.utils.SnowFlakeUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

/**
 * @author Wu, Hua-Zheng
 * @version v1.0.0
 * @classDesc: 功能描述: (service基础实现类)
 * @createTime 2018年6月29日 上午11:01:17
 * @copyright: 上海投嶒网络技术有限公司
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

	@Autowired
	public SnowFlakeUtils snowFlake;

	@Autowired
	public BaseMapper<T> baseMapper;

	public void setMapper(BaseMapper<T> baseMapper) {
		this.baseMapper = baseMapper;
	}

	@Override
	public int save(T entity) {
		entity.setCreateTime(new Date());
		return baseMapper.insert(entity);
	}

	@Override
	public int batchSave(List<T> recordList) {
		return baseMapper.insertList(recordList);
	}

	@Override
	public int delete(String id) {

		return baseMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int delete(T entity) {

		return baseMapper.delete(entity);
	}

	@Override
	public int updateByPrimaryKey(T entity) {

		return baseMapper.updateByPrimaryKey(entity);
	}

	@Override
	public int update(T entity) {

		return baseMapper.updateByPrimaryKeySelective(entity);
	}

	@Override
	public T get(String id) {

		return baseMapper.selectByPrimaryKey(id);
	}

	@Override
	public T selectOne(T entity) {

		return baseMapper.selectOne(entity);
	}

	@Override
	public List<T> getAll() {

		return baseMapper.selectAll();
	}

	@Override
	public List<T> select(T entity) {

		return baseMapper.select(entity);
	}

	@Override
	public int setUnavailableByExample(List<String> ids, T entity) {

		Example example = new Example(entity.getClass());
		Criteria criteria = example.createCriteria();
		criteria.andIn(TouCengConstant.FIELD_ID_PARAM, new HashSet<String>(ids));
		criteria.andEqualTo(TouCengConstant.FIELD_IS_AVAILABLE_PARAM, Boolean.TRUE);
		return baseMapper.updateByExampleSelective(entity, example);
	}

	@Override
	public T getAccountByExample(String fieldName, String fieldData, String currency, Class<T> targetClass) {

		Example example = new Example(targetClass);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo(fieldName, fieldData);
		criteria.andEqualTo("currency", currency);
		List<T> tempList = baseMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(tempList)) {
			return tempList.get(TouCengConstant.ZERO);
		}

		return null;
	}

	@Override
	public int deleteByExample(List<String> ids, Class<T> targetClass) {

		Example example = new Example(targetClass);
		Criteria criteria = example.createCriteria();
		criteria.andIn(TouCengConstant.FIELD_ID_PARAM, new HashSet<String>(ids));
		return baseMapper.deleteByExample(example);
	}

}
