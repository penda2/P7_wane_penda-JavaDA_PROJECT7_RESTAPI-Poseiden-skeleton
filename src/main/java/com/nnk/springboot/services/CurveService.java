package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import com.nnk.springboot.domain.CurvePoint;

public interface CurveService {
	// Method declarations that will be implemented in CurveServiceImpl class

	List<CurvePoint> findAllCurvePoints();

	CurvePoint save(CurvePoint curvePoint);

	Optional<CurvePoint> findById(Integer id);

	void delete(CurvePoint curvePoint);
}
