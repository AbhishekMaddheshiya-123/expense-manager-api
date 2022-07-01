package in.expansetrackerapi.repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.expansetrackerapi.entity.Expenses;
@Repository
public interface ExpenseRepository extends JpaRepository<Expenses, Long>
{
	// SELECT * FROM tbl_expenses WHERE category=?
//  Page<Expenses> findByCategory(String category, Pageable page);
	Page<Expenses> findByUserIdAndCategory(Long userId,String category, Pageable page);
	
//  SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
//  Page<Expenses> findByNameContaining(String Keyord, Pageable page);
  Page<Expenses> findByUserIdAndNameContaining(Long userId,String Keyord, Pageable page);
  
//   SELECT * FROM tbl_expenses WHERE date BETWEEN 'startDate' AND 'endDate'
//    Page<Expenses>findByDateBetween(Date startDate, Date endDate, Pageable  page);
      Page<Expenses>findByUserIdAndDateBetween(Long userId,Date startDate, Date endDate, Pageable  page);
      
      
   Page<Expenses> findByUserId(Long id, Pageable page);
  
   Optional<Expenses> findByUserIdAndId(Long userId, Long expensesId);
}
