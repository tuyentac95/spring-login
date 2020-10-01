package com.codegym.repository;

import com.codegym.model.Member;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MemberRepository extends PagingAndSortingRepository<Member, Long> {
    Member findByEmail(String email);
}
