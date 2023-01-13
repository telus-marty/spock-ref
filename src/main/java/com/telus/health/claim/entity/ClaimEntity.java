package com.telus.health.claim.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;

@Data
@Entity( name = "claim" )
@Table( name = "claim" )
public class ClaimEntity
{
  @Id
  @Column( name = "CLAIMID" )
  @GeneratedValue( strategy = GenerationType.IDENTITY )
  private Integer key;
  @Column( name = "recipientid" )
  private Integer recipientId;
  @Column( name = "CLAIMED_CODE" )
  private String claimedCode;
  @Column( name = "CLAIMED_AMOUNT" )
  private Double claimedAmount;
  @Column( name = "BENEFIT_AMOUNT" )
  private Double benefitAmount;
  @Column( name = "CREATEBY" )
  private Integer createBy;
  @Column( name = "createdatetime" )
  private Date createdDate;
  @Column( name = "RECORDSTATUS" )
  private String recordStatus;
  @Column( name = "LASTMODBY" )
  private Integer lastModBy;
  @Column( name = "LASTMODDATETIME" )
  private Date lastModDate;
}
