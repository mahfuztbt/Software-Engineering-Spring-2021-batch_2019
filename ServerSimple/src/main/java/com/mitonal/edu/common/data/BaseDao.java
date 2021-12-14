package com.mitonal.edu.common.data;

import com.mitonal.edu.common.data.specification.AbstractQueryCondition;
import com.mitonal.edu.common.data.specification.SpecificationBuilder;
import com.sun.istack.Nullable;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.*;

/**
 * jpa数据访问基类
 *
 * @param <T>
 * @param <ID>
 */
@NoRepositoryBean
public interface BaseDao<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

	/**
	 * 该接口依赖于各个jpa的底层实现，不是一个稳定接口，故废弃
	 * @param id
	 * @return
	 * @see JpaRepository#getOne(Object)
	 */
	//@Deprecated
	@Override
	T getOne(ID id);

	default List<T> findByPrimaryKeyIn(Collection<ID> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return new LinkedList<>();
		}
		return this.findAllById(ids);
	}

	default List<T> findByPrimaryKeyNotIn(Collection<ID> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return this.findAll();
		}
		Field field = PersistenceHelper.getPrimaryKey(this);
		return this.findAll(((root, cb, cq) -> cq.not(root.get(field.getName()).in(ids))));
	}

	default <S extends T> List<S> safeSaveAll(Collection<S> list) {
		if (CollectionUtils.isEmpty(list)) {
			return new LinkedList<>();
		}
		return this.saveAll(list);
	}

	default void safeDeleteAll(Collection<T> entities) {
		if (CollectionUtils.isEmpty(entities)) {
			return;
		}
		this.deleteAll(entities);
	}

	/**
	 * 根据id删除所有
	 * @param ids id集合
	 * @return 删除的数量
	 */
	int deleteByIdIn(Collection<ID> ids);

	default void deleteByPrimaryKeyIn(Collection<ID> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return;
		}
		List<T> list = this.findAllById(ids);
		deleteAll(list);
	}

	@SneakyThrows
	default Map<ID, T> findMapByPrimaryKeyIn(Collection<ID> ids) {
		List<T> list = findByPrimaryKeyIn(ids);
		if (CollectionUtils.isEmpty(list)) {
			return new LinkedHashMap<>();
		}
		Field field = PersistenceHelper.getPrimaryKey(this);
		Map<ID, T> map = new HashMap<>(list.size());
		for (T t : list) {
			ID id = (ID) field.get(t);
			map.put(id, t);
		}
		return map;
	}

	/**
	 * @param pageable
	 * @return
	 */
	default Page<T> queryPage(Pageable pageable, @Nullable Specification<T> spec) {
		if (spec == null) {
			spec = (Specification<T>) SpecificationBuilder.builder().build();
		}

		return this.findAll(spec, pageable);
	}

	// /**
	// * @param pageable
	// * @param condition
	// * @return
	// */
	// default Page<T> queryPageByCondition(Pageable pageable, AbstractQueryCondition
	// condition) {
	// SpecificationBuilder<T> specificationBuilder = SpecificationBuilder.builder();
	// //
	// for (var cd : condition.getCondition()) {
	// var cdt = cd.getCondition(); //条件
	// if (cdt != null) {
	// specificationBuilder.andEqual(cd.getProperty(), cd.getValue());
	// }
	// }
	//
	// return this.queryPage(pageable, specificationBuilder);
	// }

}
