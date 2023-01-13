package com.telus.health.claim.data;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.telus.health.claim.entity.ClaimEntity;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimEntity, Integer>
{
  @Query( value = "select c from claim c where c.recipientId = :recipientId")
  List<ClaimEntity> getRecipientClaims( @Param( "recipientId" ) Integer recipientId );
}
