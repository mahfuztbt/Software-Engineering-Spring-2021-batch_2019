package com.mitonal.edu.common.service;

import com.mitonal.edu.common.data.specification.AbstractQueryCondition;
import com.mitonal.edu.common.data.BaseDao;
import com.mitonal.edu.common.data.specification.SpecificationBuilder;
import com.mitonal.edu.common.entities.AbstractDomainEntity;
import com.mitonal.edu.common.exception.ResourceNotFoundException;
//import com.mitonal.edu.education.entities.StudentInfor;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * 抽象类型化服务
 */
public class AbstractTypedService<T extends AbstractDomainEntity, IdType> {

	/**
	 * 配套的类型化dao
	 */
	protected BaseDao<T, IdType> dataContext;

	// #region 实体类型化访问

	@ApiOperation("根据id获取数据对象")
	public T getById(IdType id) {
		return dataContext.getOne(id);
	}

	@ApiOperation("根据id获取数据对象, 如果没有找到则引发一个异常")
	public T getByIdNotNull(IdType id) {
		return dataContext.findById(id).orElseThrow(() -> new ResourceNotFoundException("无法找到请求的数据"));
	}

	@ApiOperation("删除数据对象")
	public List<T> findByIds(Collection<IdType> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new LinkedList<>();
		}
		return dataContext.findByPrimaryKeyIn(ids);
	}

	/**
	 * 查询全部
	 * @return
	 */
	@ApiOperation("创建数据对象")
	public List<T> findAll() {
		return dataContext.findAll();
	}

	/**
	 * 查询全部
	 * @return
	 */
	@ApiOperation("根据条件查询数据对象")
	public List<T> findBy(AbstractQueryCondition condition, @Nullable Sort sort) {
		var sb = condition.builderCondition();

		if (sort == null) {
			sort = Sort.by(Sort.Direction.DESC, "createDate");
		}

		return dataContext.findAll(sb, sort);
	}

	/**
	 * 分页查询数据对象
	 * @param pageable 分页符
	 * @return
	 */
	@ApiOperation("分页查询数据对象")
	public Page<T> pageQuery(Pageable pageable) {
		return this.pageQuery(pageable, null);
	}

	/**
	 * 分页查询数据对象
	 * @param pageable 分页符
	 * @param condition 查询条件
	 * @return
	 */
	@ApiOperation("分页查询数据对象")
	public Page<T> pageQuery(Pageable pageable, @Valid AbstractQueryCondition condition) {

		/*
		 *
		 * if (Objects.equals(condition.getIncludeSub(), true)) { List<OrganizationPo>
		 * queryOrgs = organizationQueryService
		 * .findSubOrgsIncludeSelf(condition.getOrganizationId());
		 * condition.setOrganizationId(null);
		 * condition.setOrganizationIds(queryOrgs.stream().map(i ->
		 * i.getId()).collect(Collectors.toList())); }
		 */

		/*
		 *
		 * if (StringUtils.hasLength(condition.getUsername())) {
		 * specificationBuilder.andLike(UserPo_.USERNAME, "%" + condition.getUsername() +
		 * "%"); }
		 *
		 * if (condition.getIncludeSub()) { if
		 * (CollectionUtils.isEmpty(condition.getOrganizationIds())) { return new
		 * PageImpl<>(new LinkedList<>(), pageable, 0L); }
		 * specificationBuilder.andIn(UserPo_.ORGANIZATION_ID,
		 * condition.getOrganizationIds()); } else { if (condition.getOrganizationId() !=
		 * null && condition.getOrganizationId() > 0) {
		 * specificationBuilder.andEqual(UserPo_.ORGANIZATION_ID,
		 * condition.getOrganizationId()); } }
		 *
		 */

		// ToDo:这个部分需要思考如何进行.
		var sb = condition.builderCondition();
		// builderCondition(condition);

		return dataContext.queryPage(pageable, sb);
	}

	@ApiOperation("创建数据对象")
	public T create(T item) {
		return dataContext.save(item);
	}

	@ApiOperation("批量创建数据对象")
	public List<T> batchCreate(List<T> items) {

		return dataContext.saveAll(items);

		// for (T it : items) {
		// dataContext.saveAll(items)
		// // dataContext.updateByPrimaryKeySelective(it);
		// }
	}

	@ApiOperation("更新数据对象")
	public T update(T item) {
		return dataContext.save(item);
	}

	@ApiOperation("更新数据对象")
	public List<T> batchUpdate(List<T> items) {

		return dataContext.saveAll(items);

		// for (T it : items) {
		// dataContext.saveAll(items)
		// // dataContext.updateByPrimaryKeySelective(it);
		// }
	}

	@ApiOperation("删除数据对象")
	public void delete(IdType id) {
		T item = getByIdNotNull(id);
		dataContext.delete(item);
	}

	@ApiOperation("删除数据对象")
	public int deleteAll(Collection<IdType> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return 0;
		}
		return dataContext.deleteByIdIn(ids);
	}

	// #endregion

	/**
	 * @param condition
	 * @return
	 */
	private SpecificationBuilder<T> builderCondition1(AbstractQueryCondition condition) {
		SpecificationBuilder<T> sb = SpecificationBuilder.builder();
		//
		/*
		 * for (var cd : condition.getCondition()) { var cdt = cd.getCondition(); //条件
		 *
		 * switch (cdt) { case "like": sb.orLike(cd.getProperty(), "%" +
		 * cd.getValue().toString() + "%"); break; case "or": sb.orEqual(cd.getProperty(),
		 * cd.getValue()); break; case "and": default: { sb.andEqual(cd.getProperty(),
		 * cd.getValue()); break; } }
		 *
		 * }
		 */
		return sb;
	}

}
