package by.teachmeskills.shop.controllers;

import by.teachmeskills.shop.enums.PagesPathEnum;
import by.teachmeskills.shop.enums.RequestParamsEnum;
import by.teachmeskills.shop.domain.Order;
import by.teachmeskills.shop.domain.OrderStatus;
import by.teachmeskills.shop.domain.User;
import by.teachmeskills.shop.services.OrderService;
import by.teachmeskills.shop.services.UserService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.teachmeskills.shop.enums.ShopConstants.USER;

@RestController
@SessionAttributes({USER})
@RequestMapping("/account")
public class UserAccountController {
    private final OrderService orderService;
    private final UserService userService;

    public UserAccountController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView openAccountPage(User user) {
        ModelMap model = new ModelMap();

        model.addAttribute(RequestParamsEnum.NAME.getValue(), user.getName());
        model.addAttribute(RequestParamsEnum.SURNAME.getValue(), user.getSurname());
        model.addAttribute(RequestParamsEnum.BIRTHDAY.getValue(), user.getBirthday().toString());
        model.addAttribute(RequestParamsEnum.EMAIL.getValue(), user.getEmail());

        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        model.addAttribute(RequestParamsEnum.ACTIVE_ORDERS.getValue(), orders.stream().filter(order -> order.getOrderStatus() == OrderStatus.ACTIVE).collect(Collectors.toList()));
        model.addAttribute(RequestParamsEnum.FINISHED_ORDERS.getValue(), orders.stream().filter(order -> order.getOrderStatus() == OrderStatus.FINISHED).collect(Collectors.toList()));

        return new ModelAndView(PagesPathEnum.USER_ACCOUNT_PAGE.getPath(), model);
    }

    @PostMapping
    public ModelAndView updateUserData(User user, @RequestParam Map<String,String> allParams) {
        return userService.updateData(user, allParams);
    }
}
