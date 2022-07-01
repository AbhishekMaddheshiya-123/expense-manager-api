package in.expansetrackerapi.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.AssertFalse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="tbl_expenses")
public class Expenses 
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(name="expenses_name")
  @NotBlank(message = "Expense name should not be null")
  @Size(min = 3, message = "Expense Name should be at leat 3 character")
  private String  name;
  
  private String  description;
  
  @Column(name = "expenses_ammount")
  @NotNull(message = "ammount should not be null")
  private BigDecimal ammount;
  
  @NotBlank(message = "category should not be null")
  private String category;
  
  @NotNull(message = "Date should be entered")
  private Date date;
  
  @Column(name="created_At" , nullable = false, updatable = false)
  @CreationTimestamp
  private Timestamp createdAt;
  
  @Column(name="updated_At")
  @UpdateTimestamp
  private Timestamp updateAt;
  
  @OnDelete(action = OnDeleteAction.CASCADE) // on delete of the user it will delete expenses as well which is associated with particular user
  @ManyToOne(fetch = FetchType.LAZY, optional = false)   // unidirectional mapping
  @JoinColumn(name = "user_id", nullable=false)
  @JsonIgnore  // when we fetch the expense so we are not going t fetch the user we will hide this
  private User user;
}
