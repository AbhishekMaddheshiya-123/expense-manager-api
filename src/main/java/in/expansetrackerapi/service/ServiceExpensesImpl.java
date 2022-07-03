package in.expansetrackerapi.service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.expansetrackerapi.entity.Expenses;
import in.expansetrackerapi.excpetions.ResourseNotFoundException;
import in.expansetrackerapi.repository.ExpenseRepository;

@Service
public class ServiceExpensesImpl implements ServiceExpense
{

	@Autowired
	private ExpenseRepository expensesRepo;
	@Autowired
	private UserService userService;
	@Override
	public Page<Expenses> getAllExpenses(Pageable page)
	{
		
//		return expensesRepo.findAll(page);
		return expensesRepo.findByUserId(userService.getLoggedInUser().getId(), page);
	}
	
	@Override
	public Expenses getExpensesById(Long id) 
	{
	
//			Optional<Expenses> findById = expensesRepo.findById(id);
			Optional<Expenses> findById = expensesRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);
			if(findById.isPresent())
			{
				return findById.get();
			}
		
		throw new ResourseNotFoundException("the expenses id is not found" + id) ;
		
	

		
		
	}

	@Override
	public void deleteExpenseById(Long id)
	{
		
		Expenses expense = getExpensesById(id);  // this part for handling exception if id s wrong 
		expensesRepo.delete(expense);
	}

	@Override
	public Expenses saveExpenseDetails(Expenses expenses) 
	{
	    expenses.setUser(userService.getLoggedInUser());
		return expensesRepo.save(expenses);
	}

	@Override
	public Expenses updateExpensesDetails(Long id, Expenses expenses) 
	{  
		Expenses existingExpenses = getExpensesById(id); 
		existingExpenses.setName(expenses.getName()!=null ? expenses.getName() : existingExpenses.getName());
		existingExpenses.setName(expenses.getDescription()!=null ? expenses.getDescription() : existingExpenses.getDescription());
		existingExpenses.setName(expenses.getCategory()!=null ? expenses.getCategory() : existingExpenses.getCategory());
		existingExpenses.setAmmount(expenses.getAmmount()!=null ? expenses.getAmmount() : existingExpenses.getAmmount());
		existingExpenses.setDate(expenses.getDate()!=null ? expenses.getDate() : existingExpenses.getDate());
		
		return expensesRepo.save(existingExpenses);
	}
	
//  fitter the expenses by its category
	@Override
	public List<Expenses> readByCategeory(String category, Pageable page) 
	{
		
		return expensesRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(),category, page).toList();
	}

	
    
//	 filter the expenses by its keyword
	
	 
	@Override
	public List<Expenses> readByKeyword(String keyword, Pageable page)
	{
		
		return expensesRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(),keyword, page).toList();
	}

//	Filter the Expenses by its Date 
	@Override
	public List<Expenses> readByDate(Date startDate, Date endDate , Pageable page) 
	{
		if(startDate == null)
		{
			 startDate= new Date(0);
		}
		if(endDate == null)
		{
			endDate = new Date(System.currentTimeMillis());
		}
		
		return expensesRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(),startDate, endDate, page).toList();
	}
}
