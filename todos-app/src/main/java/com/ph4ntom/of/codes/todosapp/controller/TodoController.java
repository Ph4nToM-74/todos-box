package com.ph4ntom.of.codes.todosapp.controller;

import com.ph4ntom.of.codes.todosapp.entity.Todo;
import com.ph4ntom.of.codes.todosapp.repository.TodoRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class TodoController {

  private final TodoRepository todoRepository;

  public TodoController(final TodoRepository todoRepository) {

    this.todoRepository = todoRepository;
  }

  @GetMapping("/todos")
  public String getTodos(final Model model, final @Param("keyword") String keyword) {

    try {

      final List<Todo> todos = new ArrayList<>();

      if (Objects.isNull(keyword)) {

        todos.addAll(todoRepository.findAll());

      } else {

        todos.addAll(todoRepository.findByTitleContainingIgnoreCase(keyword));

        model.addAttribute("keyword", keyword);
      }

      model.addAttribute("todos", todos);

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

      redirectAttrs.addFlashAttribute("message", "The Todo has been saved.");

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
