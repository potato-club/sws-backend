package com.sws.sws.repository;

import com.sws.sws.entity.FriendsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<FriendsEntity,Long> {

}
