package org.o7planning.sbsecurity.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.o7planning.sbsecurity.entity.Employee;
import org.o7planning.sbsecurity.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@GetMapping(value = "/")
	public String home() {
		return "redirect:/employee";
	}

	@GetMapping(value = "/employee")
	public String index(Model model, HttpServletRequest request, RedirectAttributes redirect) {
		request.getSession().setAttribute("employees", null);
		if (model.asMap().get("success") != null)
			redirect.addFlashAttribute("success", model.asMap().get("success").toString());
		return "redirect:/employee/page/1";
	}

	@GetMapping(value = "/employee/page/{pageNumber}")
	public String showEmployeePage(HttpServletRequest request, @PathVariable int pageNumber, Model model) {

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");
		int pagesize = 3;
		List<Employee> list =(List<Employee>) employeeService.findAll();
		System.out.println(list.size());
		if (pages == null) {
			pages = new PagedListHolder<>(list);
			pages.setPageSize(pagesize);
		} else {
			final int goToPage = pageNumber - 1;
			if (goToPage <= pages.getPageCount() && goToPage >= 0) {
				pages.setPage(goToPage);
			}
		}
		request.getSession().setAttribute("employeelist", pages);
		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/employee/page/";

		model.addAttribute("beginIndex", 
				begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("employees", pages);

		return "list";
	}

	@GetMapping(value = "/employee/create")
	public String create(Model model) {
		model.addAttribute("employee", new Employee());
		return "form";
	}

	@GetMapping(value = "/employee/edit/{id}")
	public String edit(Model model, @PathVariable Long id) {
		model.addAttribute("employee", employeeService.findOne(id));
		return "form";
	}

	@PostMapping(value = "/employee/save")
	public String save(@Valid Employee employee, BindingResult result, RedirectAttributes redirect) {
		if (result.hasErrors()) {
			return "form";
		}
		employeeService.save(employee);
		redirect.addFlashAttribute("success", "Saved employee successfully!");
		return "redirect:/employee";
	}

	@GetMapping(value = "/employee/delete/{id}")
	public String delete(RedirectAttributes redirect, @PathVariable long id) {
		Employee em = employeeService.findOne(id);
		employeeService.delete(em);
		redirect.addFlashAttribute("success", "Deleted employee successfully!");
		return "redirect:/employee";
	}

	@GetMapping(value = "/employee/search/{pageNumber}")
	public String search(@RequestParam("s") String s, Model model, HttpServletRequest request,
			@PathVariable int pageNumber) {
		if (s.equals("")) {
			return "redirect:/employee";

		}
		List<Employee> list = employeeService.search(s);
		if (list == null) {
			return "redirect:/employee";
		}

		PagedListHolder<?> pages = (PagedListHolder<?>) request.getSession().getAttribute("employeelist");

		int pagesize = 3;
		pages = new PagedListHolder<>(list);
		pages.setPageSize(pagesize);

		final int goToPage = pageNumber - 1;
		if (goToPage <= pages.getPageCount() && goToPage >= 0) {
			pages.setPage(goToPage);
		}

		int current = pages.getPage() + 1;
		int begin = Math.max(1, current - list.size());
		int end = Math.min(begin + 5, pages.getPageCount());
		int totalPageCount = pages.getPageCount();
		String baseUrl = "/employee/page/";

		model.addAttribute("beginIndex", begin);
		model.addAttribute("endIndex", end);
		model.addAttribute("currentIndex", current);
		model.addAttribute("totalPageCount", totalPageCount);
		model.addAttribute("baseUrl", baseUrl);
		model.addAttribute("employees", pages);

		return "list";
	}

}
