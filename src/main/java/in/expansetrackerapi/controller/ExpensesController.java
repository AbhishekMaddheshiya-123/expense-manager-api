package in.expansetrackerapi.controller;

import java.sql.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import in.expansetrackerapi.entity.Expenses;
import in.expansetrackerapi.service.ServiceExpense;
import in.expansetrackerapi.service.ServiceExpensesImpl;


// this is how create a REST end point for Expenses
@RestController
public class ExpensesController 
{  
	@Autowired
	private ServiceExpense expenseService;
	
	@GetMapping("/expenses")
   public List<Expenses> getAllExpenses(Pageable page)
   {
		int number =2;
		calculateFactorial(number);
		
	   return expenseService.getAllExpenses(page).toList() ;
   }
	
	@GetMapping("/expenses/{id}")
	public Expenses getExpensesById(@PathVariable Long id)
	{
		return expenseService.getExpensesById(id);
	}
	

	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	@DeleteMapping("/expenses")
	public void deleteExpensesById(@RequestParam("id") Long id)
	{
		expenseService.deleteExpenseById(id);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/expenses")
	public Expenses saveExpensesDetails(@Valid @RequestBody Expenses expenses)
	{
		return expenseService.saveExpenseDetails(expenses); 
		
	}
	
	@PutMapping("/expenses/{id}")
	public Expenses updateExpensesDetails(@RequestBody Expenses expenses, @PathVariable Long id)
	{
		return expenseService.updateExpensesDetails(id, expenses);
	}
	
	public int calculateFactorial(int number)
	{
		if(number==0)
		{                   
//		if i do not put this base case then it will throw an stacKoverflow exception
			return 1;
		}
		
		int i =number*calculateFactorial(number-1);
		
		return i;
		
	}
	
	@GetMapping("/expenses/category")
	public List<Expenses> getExpensesByCategory(@RequestParam String category, Pageable page)
	{
		 return expenseService.readByCategeory(category, page);
	}
	
	@GetMapping("/expenses/name")
	public List<Expenses> getallExpensesByName(@RequestParam String Keyword, Pageable page)
	{
		return  expenseService.readByKeyword(Keyword, page) ;
		
	}
	
	@GetMapping("/expenses/date")   // required false means optional hai .
	public List<Expenses> getAllExpensesByDate(@RequestParam(required =false) Date startDate, @RequestParam(required =false) Date endDate,Pageable page)
	{
		return expenseService.readByDate(startDate, endDate, page);
	}
}


