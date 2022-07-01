package in.expansetrackerapi.service;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import in.expansetrackerapi.entity.Expenses;

public interface ServiceExpense 
{
  public Page<Expenses> getAllExpenses(Pageable page);
  
  public Expenses getExpensesById(Long id);
  
  void deleteExpenseById(Long id);
  
  public Expenses saveExpenseDetails(Expenses expenses);
  
  public Expenses updateExpensesDetails(Long id, Expenses expenses);
  
  public   List<Expenses> readByCategeory(String category, Pageable page);
     
   public  List<Expenses> readByKeyword(String Keyword, Pageable page);
    
   public  List<Expenses>readByDate(Date startDate, Date endDate, Pageable page);
}
