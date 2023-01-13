package com.telus.health.claim.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.telus.health.claim.data.ClaimRepository;
import com.telus.health.claim.entity.ClaimEntity;

@Service
public class ClaimService
{
  private final ClaimRepository claimRepository;

  @Autowired
  public ClaimService( ClaimRepository claimRepository )
  {
    this.claimRepository = claimRepository;
  }

  public Double getRecipientBenefitAccumulation( Integer recipientId )
  {
    List<ClaimEntity> claims = claimRepository.getRecipientClaims( recipientId );
    return Double.valueOf( claims.stream().mapToDouble( c -> c.getBenefitAmount().doubleValue() ).sum() );
  }
}
