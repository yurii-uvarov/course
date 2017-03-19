package ua.nure.web;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nure.model.Discount;
import ua.nure.model.Order;
import ua.nure.model.Tour;
import ua.nure.model.User;
import ua.nure.service.DiscountService;
import ua.nure.service.OrderService;
import ua.nure.service.TourService;
import ua.nure.service.UserService;
import ua.nure.service.impl.TourServiceImpl;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private TourService tourService;

    @RequestMapping(value = {"/doOrder"}, method = RequestMethod.POST)
    public String viewTour(String idTour, String idUser, Model model) {
        Tour tour = tourService.getById(idTour);
        Discount discount = discountService.findByUser(idUser);
        User user = userService.getById(idUser);
        model.addAttribute("user", user);
        model.addAttribute("discount", discount);
        model.addAttribute("tour", tour);
        userService.addUser(model);
        System.err.println("THERE");
        return "jsp/user/previewOrder";
    }

    @RequestMapping(value = {"/confirm"}, method = RequestMethod.POST)
    public String confirm(String userId,String tourId, Double bill, Model model) {
        Order order = new Order();
        order.setUserId(userId);
        order.setTourId(tourId);
        order.setBill(bill);

        System.err.println(order);
        String id = orderService.createOrder(order);
        System.err.println(id);
        userService.addUser(model);
        return "redirect:/index";
    }


}