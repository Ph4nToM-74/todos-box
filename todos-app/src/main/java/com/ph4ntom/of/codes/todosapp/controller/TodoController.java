package com.ph4ntom.of.codes.todosapp.controller;

import com.ph4ntom.of.codes.todosapp.model.Todo;
import com.ph4ntom.of.codes.todosapp.repository.TodoRepository;
import java.util.List;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

  private final TodoRepository todoRepository;

  public TodoController(final TodoRepository todoRepository) {

    this.todoRepository = todoRepository;
  }

  @GetMapping("/todos")
  public String getTodos(final Model model, final @Param("keyword") String keyword,
                         final @RequestParam(defaultValue = "1") int page,
                         final @RequestParam(defaultValue = "6") int size,
                         final @RequestParam(defaultValue = "id,asc") String[] sort) {
    try {

      final Page<Todo> pageTodos;

      final String sortField = sort[0];
      final String sortDirection = sort[1];

      final Direction direction = sortDirection.equals("desc") ? Direction.DESC : Direction.ASC;
      final Order order = new Order(direction, sortField);

      final Pageable pageable = PageRequest.of(page - 1, size, Sort.by(order));

      if (Objects.isNull(keyword)) {

        pageTodos = todoRepository.findAll(pageable);

      } else {

        pageTodos = todoRepository.findByTitleContainingIgnoreCase(keyword, pageable);

        model.addAttribute("keyword", keyword);
      }

      model.addAttribute("pageSize", size);
      model.addAttribute("todos", pageTodos.getContent());

      model.addAttribute("totalItems", pageTodos.getTotalElements());
      model.addAttribute("currentPage", pageTodos.getNumber() + 1);
      model.addAttribute("totalPages", pageTodos.getTotalPages());

      model.addAttribute("sortField", sortField);
      model.addAttribute("sortDirection", sortDirection);
      model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");

    } catch (final Exception exc) {

      model.addAttribute("message", exc.getMessage());
    }

    return "todos";
  }

  @GetMapping("/todos/new")
  public String addTodo(final Model model) {

    final Todo todo = new Todo();

    model.addAttribute("todo", todo);
    model.addAttribute("pageTitle", "Create new Todo");

    return "todo_form";
  }

  @PostMapping("/todos/save")
  public String saveTodo(final Todo todo, final RedirectAttributes redirectAttrs) {

    try {

      todoRepository.save(todo);

      final String message = "The Todo has been saved.";

      redirectAttrs.addFlashAttribute("message", message);

    } catch (final Exception exc) {

      redirectAttrs.addAttribute("message", exc.getMessage());
    }

    return "redirect:/todos";
  }

  @GetMapping("/todos/{id}")
  public String editTodo(final @PathVariable("id") Integer id,
         final Model model, final RedirectAttributes redirectAttrs) {

    try {

      final Todo todo = todoRepository.findById(id).get();

      model.addAttribute("todo", todo);
      model.addAttribute("pageTitle", "Edit Todo (ID: " + id + ")");

      return "todo_form";

    } catch (final Exception exc) {

      redirectAttrs.addFlashAttribute("message", exc.getMessage());

      return "redirect:/todos";
    }
  }

  @GetMapping("/todos/delete/{id}")
  public String deleteTodo(final @PathVariable("id") Integer id,
                           final RedirectAttributes redirectAttrs) {
    try {

      todoRepository.deleteById(id);

      final String message = "The Todo with <id=" + id + "> has been deleted.";

      redirectAttrs.addFlashAttribute("message", message);

    } catch (final Exception exc) {

      redirectAttrs.addFlashAttribute("message", exc.getMessage());
    }

    return "redirect:/todos";
  }

  @GetMapping("/todos/{id}/done/{status}")
  public String updateTodoDoneStatus(final @PathVariable("id") Integer id,
                                     final @PathVariable("status") boolean done,
                                     final RedirectAttributes redirectAttrs) {
    try {

      todoRepository.updateDoneStatus(id, done);

      final String status = done ? "done" : "disabled";
      final String message = "The Todo with <id=" + id + "> has been " + status;

      redirectAttrs.addFlashAttribute("message", message);

    } catch (final Exception exc) {

      redirectAttrs.addFlashAttribute("message", exc.getMessage());
    }

    return "redirect:/todos";
  }
}
