package com.nnk.springboot.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

/*implementation of methods declared 
 * in the CurveService interface
 */
@Service
public class CurveServiceImpl implements CurveService {

	@Autowired
	private CurvePointRepository curvePointRepository;

	@Override
	public List<CurvePoint> findAllCurvePoints() {
		return curvePointRepository.findAll();
	}

	@Override
	public CurvePoint save(CurvePoint curvePoint) {
		return curvePointRepository.save(curvePoint);
	}

	@Override
	public Optional<CurvePoint> findById(Integer id) {
		return curvePointRepository.findById(id);
	}

	@Override
	public void delete(CurvePoint curvePoint) {
		curvePointRepository.delete(curvePoint);
	}
}
