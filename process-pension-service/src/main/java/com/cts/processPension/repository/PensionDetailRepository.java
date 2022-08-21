package com.cts.processPension.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cts.processPension.model.PensionDetail;
@Repository
public interface PensionDetailRepository extends JpaRepository<PensionDetail, String>  {

}
